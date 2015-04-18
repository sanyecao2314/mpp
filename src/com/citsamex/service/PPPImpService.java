package com.citsamex.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.citsamex.app.util.CommonUtil;
import com.citsamex.app.util.DBUtil;
import com.citsamex.app.util.ServiceConfig;
import com.citsamex.service.db.AddressPo;
import com.citsamex.service.db.CommonDao;
import com.citsamex.service.db.CompanyDao;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.ContactPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.DataMapping;
import com.citsamex.service.db.MemberPo;
import com.citsamex.service.db.PreferencePo;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.util.IpppErrMsg;
import com.citsamex.vo.Address;
import com.citsamex.vo.Contact;
import com.citsamex.vo.CreditCard;
import com.citsamex.vo.Member;
import com.citsamex.vo.Preference;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class PPPImpService {
	
	private static final Logger logger = Logger.getLogger(PPPImpService.class);
	
	private ITravelerService travelerService;
	
	private CompanyDao companyDao;
	
	private TravelerDao travelerDao ;
	
	public ITravelerService getTravelerService() {
		return travelerService;
	}

	public void setTravelerService(ITravelerService travelerService) {
		this.travelerService = travelerService;
	}


	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public TravelerDao getTravelerDao() {
		return travelerDao;
	}

	public void setTravelerDao(TravelerDao travelerDao) {
		this.travelerDao = travelerDao;
	}

	
	
	private ConcurrentHashMap<String, String>  barnoMap = new ConcurrentHashMap<String, String>();
	
	private String getBarno(String cardtype) throws Exception{
		String barno = null;
		if (barnoMap == null || !barnoMap.containsValue(cardtype)) {
			String sql = "select fvalue from t_ppp_config where fnumber='"+cardtype+"'";
			List list = DBUtil.querySqlUniqueResult(sql, "MPP");
			if (list != null && list.size() == 1) {
				barno = (String) list.get(0);
				barnoMap.put(cardtype, barno);
			}else{
				throw new Exception("getBarno error.");
			}
		}else{
			barno = barnoMap.get(cardtype);
		}
		return barno;
	}

	
	public void impDataFromPPP(HashMap rowmap) throws Exception{

		TravelerPo po = null;
		String action = (String) rowmap.get("faction");
		po = getParVOByMap(rowmap);
		String id = rowmap.get("travelerid") != null ? rowmap.get("travelerid").toString() : null;
		if ("UPDATE".equals(action) && "1".equals(String.valueOf(rowmap.get("issuccess")))) {
//			TravelerPo oldpo =travelerDao.find(Long.parseLong(id));
			String sql = "select COMPANY_ID,COMPANY_NAME,TRAVELER_NAME from t_traveler where id=" + id;
			List list = DBUtil.querySqlUniqueResult(sql, "MPP");
			po.setCompanyId(Long.parseLong(list.get(0)==null?"0":list.get(0).toString()));
			po.setCompanyName(list.get(1) == null?"":list.get(1).toString());
			po.setTravelerName(list.get(2) == null?"":list.get(2).toString());

			po.setId(Long.parseLong(id));
			travelerDao.update(po);
		}
		//需要做类似changebar操作的.
		else if("UPDATE".equals(action) && "2".equals(String.valueOf(rowmap.get("issuccess")))){
			
			String product = rowmap.get("product") != null ? rowmap.get("product").toString() : null;
			CompanyPo companyPo = companyDao.find(getBarno(product));
			po.setCompanyId(companyPo.getId());
			po.setCompanyName(companyPo.getCompanyName().length()>50?companyPo.getCompanyName().substring(0, 50):companyPo.getCompanyName());
			
			String travelerno = travelerService.getMaxTravelerNo(getBarno(product));
			if (StringUtils.isEmpty(travelerno)) {
				throw new Exception("getMaxTravelerNo error.");
			}
			po.setTravelerNo(travelerno);
			po.setTravelerName(po.getLastname()+po.getFirstname());
			travelerDao.save(po);
			
			//将原来的置为inactive
			TravelerPo oldpo =travelerDao.find(Long.parseLong(id));
			oldpo.setStatus("inactive");
			oldpo.setStatusRemark("card type change.new parno=" + travelerno);
			travelerDao.update(oldpo);
			travelerService.synchronize(oldpo, 0, 1, "PS,BS");
		}
		//新增
		else {
			String product = rowmap.get("product") != null ? rowmap.get("product").toString() : null;
			CompanyPo companyPo = companyDao.find(getBarno(product));
			po.setCompanyId(companyPo.getId());
			po.setCompanyName(companyPo.getCompanyName().length()>50?companyPo.getCompanyName().substring(0, 50):companyPo.getCompanyName());
			
			String travelerno = travelerService.getMaxTravelerNo(getBarno(product));
			if (StringUtils.isEmpty(travelerno)) {
				throw new Exception("getMaxTravelerNo error.");
			}
			/**
			 * 新的parno的生成规则变化.
			 * 原定:系统配置信息维护product对应的barno,该bar满后,自动在barno的后三位数字上+1.作为新的barno.
			 * 现在:系统配置信息维护product对应的barno,该bar满后,系统的导入停止.发送邮件提示系统维护人员.新建新的bar,再修改配置信息中的barno.系统晚一天处理.
			else if(travelerno.endsWith("9999")){		
				//最后一个parno.更新配置信息。
				Integer ibarno = Integer.parseInt(getBarno().substring(3, 6));
				String newbarno = String.valueOf(ibarno + 1);

				if (newbarno != null) {
					switch (newbarno.length()) {
					case 1:
						newbarno = "00" + newbarno;
						break;
					case 2:
						newbarno = "0" + newbarno;
						break;
					}
				}
				barno = newbarno;
				DBUtil.excuteUpdate("update t_ppp_config set fvalue='" + barno + "' where fnumber='barno'", "MPP");
				
			}
			 */
			
			po.setTravelerNo(travelerno);
			po.setTravelerName(po.getLastname()+po.getFirstname());
			
			travelerDao.save(po);
		}

        // 2.pref
        travelerDao.deleteRelation("PreferencePo", "T" + po.getId());
        ArrayList preferenceList = getPreferencePoByMap(rowmap);
        for (int j = 0; j < preferenceList.size(); j++) {
            PreferencePo pref = (PreferencePo) preferenceList.get(j);
            pref.setRelationId("T" + po.getId());
            travelerDao.save(pref);
        }

        //3.地址
        travelerDao.deleteRelation("AddressPo", "T" + po.getId());
        ArrayList addressList = getAddressPoByMap(rowmap);
        for (int j = 0; j < addressList.size(); j++) {
            AddressPo address = (AddressPo) addressList.get(j);
            address.setRelationId("T" + po.getId());
            travelerDao.save(address);
            
        }
        
        //4.creditCard
        travelerDao.deleteRelation("CreditCardPo", "T" + po.getId());
        ArrayList creditCardList = getCreditCardPoByMap(rowmap);
        for (int j = 0; j < creditCardList.size(); j++) {
            CreditCardPo ccd = (CreditCardPo) creditCardList.get(j);
            ccd.setRelationId("T" + po.getId());
            //add by fans.fan 数据没有cardtype和Institution,自动增加.
            String cardtype = travelerService.getCardTypeByCardbin(ccd.getCardNo());
            Logger.getRootLogger().info("===cardtype===" + cardtype);
            if (StringUtils.isNotEmpty(cardtype)) {
            	String[] strs = cardtype.split(",");
            	if (strs.length >= 2) {
            		ccd.setType(strs[0]);
            		ccd.setInstitution(strs[1]);
            		Logger.getRootLogger().info("set cardtype" + cardtype);
				}else{
					ccd.setType(cardtype);
				}
			}
            travelerDao.save(ccd);
        }

        //5.member
        travelerDao.deleteRelation("MemberPo", "T" + po.getId());
        List<DataMapping>  dataMappingList = travelerDao.listDataMapping2("com.citsamex.service.db.MemberPo", 
        		po.getTravelerNo());
        ArrayList memberList = getMemberPoByMap(rowmap);
        for (int m = 0; m < memberList.size(); m++) {
        	MemberPo member = (MemberPo) memberList.get(m);
            member.setRelationId("T" + po.getId());
            travelerDao.save(member);
            
			for (int j = 0; j < dataMappingList.size();) {
				DataMapping dataMapping = dataMappingList.get(j);
				if (dataMapping.getPs().contains(member.getMemberNo())) {
					StringBuffer sb = new StringBuffer();
					sb.append("CUSTNO=" + po.getTravelerNo())
					  .append(",MEMTYPE=" + member.getProdType())
					  .append(",MEMNO=" + member.getMemberNo())
					  .append(",MEMSUPP=" + member.getMemberCode());
					dataMapping.setMpp(String.valueOf(member.getId()));
					dataMapping.setPs(sb.toString());
					travelerDao.update(dataMapping);
					// 匹配上的更新后,从list里删除.
					dataMappingList.remove(j);
				} else{
					j++;
				}
			}
			// 没有匹配上的都是要删除的.
			for (int j = 0; j < dataMappingList.size(); j++) {
				DataMapping dataMapping = dataMappingList.get(j);
				dataMapping.setAction("delete");
				travelerDao.update(dataMapping);
			}
        }
        
        //更新中间表.
        
//        String updateMiddleSql = "update t_ppp_traveler_middle set issuccess=1 where id =" + rowmap.get("id");
//        DBUtil.excuteUpdate(updateMiddleSql, "MPP");
	
	}

	/**
	 * 信用卡信息.
	 * @param rowmap
	 */
	private ArrayList getCreditCardPoByMap(HashMap rowmap) {
		if (rowmap == null || rowmap.size() <= 0) {
			return null;
		}
		
		ArrayList arraylist = new ArrayList();
		String vendor = rowmap.get("vendor") != null ? rowmap.get("vendor").toString() : null;
		String product = rowmap.get("product") != null ? rowmap.get("product").toString() : "";
		String dknumber = rowmap.get("dknumber") != null ? rowmap.get("dknumber").toString() : "";
		String value = rowmap.get("ccard_no1") != null ? rowmap.get("ccard_no1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			CreditCardPo po = new CreditCardPo();
			po.setCardNo(value);
			
			value = rowmap.get("expirydate1") != null ? rowmap.get("expirydate1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setExpiry(value);
			}
			value = rowmap.get("holdername1") != null ? rowmap.get("holdername1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setHolderName(value);
			}
			if (StringUtils.isNotEmpty(vendor)) {
				po.setVendor(vendor);
			}
			if(StringUtils.isNotEmpty(product) || StringUtils.isNotEmpty(dknumber)){
				po.setRemark(product + ","+dknumber);
			}
			
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			
			arraylist.add(po);
		}
		
		value = rowmap.get("ccard_no2") != null ? rowmap.get("ccard_no2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			CreditCardPo po = new CreditCardPo();
			po.setCardNo(value);
			
			value = rowmap.get("expirydate2") != null ? rowmap.get("expirydate2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setExpiry(value);
			}
			value = rowmap.get("holdername2") != null ? rowmap.get("holdername2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setHolderName(value);
			}
			if (StringUtils.isNotEmpty(vendor)) {
				po.setVendor(vendor);
			}
			if(StringUtils.isNotEmpty(product) || StringUtils.isNotEmpty(dknumber)){
				po.setRemark(product + ","+dknumber);
			}
			
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		return arraylist;
	}

	/**
	 * 里程卡信息
	 * @param rowmap
	 */
	private ArrayList getMemberPoByMap(HashMap rowmap) {
		if (rowmap == null || rowmap.size() <= 0) {
			return null;
		}
		
		ArrayList arraylist = new ArrayList();
		String value = rowmap.get("member_no1") != null ? rowmap.get("member_no1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			MemberPo po = new MemberPo();
			po.setMemberNo(value);
			
			value = rowmap.get("prod_type1") != null ? rowmap.get("prod_type1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setProdType(value);
			}
			value = rowmap.get("member_code1") != null ? rowmap.get("member_code1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setMemberCode(value);
			}
			value = rowmap.get("member_expire1") != null ? rowmap.get("member_expire1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(dateformat.parse(value));
					po.setMemberExpire(c);
				} catch (Exception ex) {
					logger.error("setMemberExpire error. value=" + value, ex);
				}
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		value = rowmap.get("member_no2") != null ? rowmap.get("member_no2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			MemberPo po = new MemberPo();
			po.setMemberNo(value);
			
			value = rowmap.get("prod_type2") != null ? rowmap.get("prod_type2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setProdType(value);
			}
			value = rowmap.get("member_code2") != null ? rowmap.get("member_code2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setMemberCode(value);
			}
			value = rowmap.get("member_expire2") != null ? rowmap.get("member_expire2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(dateformat.parse(value));
					po.setMemberExpire(c);
				} catch (Exception ex) {
					logger.error("setMemberExpire error. value=" + value, ex);
				}
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		value = rowmap.get("member_no3") != null ? rowmap.get("member_no3").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			MemberPo po = new MemberPo();
			po.setMemberNo(value);
			
			value = rowmap.get("prod_type3") != null ? rowmap.get("prod_type3").toString() : "Air";
			if (StringUtils.isNotEmpty(value)) {
				po.setProdType(value);
			}
			value = rowmap.get("member_code3") != null ? rowmap.get("member_code3").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setMemberCode(value);
			}
			value = rowmap.get("member_expire3") != null ? rowmap.get("member_expire3").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(dateformat.parse(value));
					po.setMemberExpire(c);
				} catch (Exception ex) {
					logger.error("setMemberExpire error. value=" + value, ex);
				}
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		value = rowmap.get("member_no4") != null ? rowmap.get("member_no4").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			MemberPo po = new MemberPo();
			po.setMemberNo(value);
			
			value = rowmap.get("prod_type4") != null ? rowmap.get("prod_type4").toString() : "Air";
			if (StringUtils.isNotEmpty(value)) {
				po.setProdType(value);
			}
			value = rowmap.get("member_code4") != null ? rowmap.get("member_code4").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setMemberCode(value);
			}
			value = rowmap.get("member_expire4") != null ? rowmap.get("member_expire4").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(dateformat.parse(value));
					po.setMemberExpire(c);
				} catch (Exception ex) {
					logger.error("setMemberExpire error. value=" + value, ex);
				}
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		value = rowmap.get("member_no5") != null ? rowmap.get("member_no5").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			MemberPo po = new MemberPo();
			po.setMemberNo(value);
			
			value = rowmap.get("prod_type5") != null ? rowmap.get("prod_type5").toString() : "Air";
			if (StringUtils.isNotEmpty(value)) {
				po.setProdType(value);
			}
			value = rowmap.get("member_code5") != null ? rowmap.get("member_code5").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setMemberCode(value);
			}
			value = rowmap.get("member_expire5") != null ? rowmap.get("member_expire5").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Calendar c = Calendar.getInstance();
					c.setTime(dateformat.parse(value));
					po.setMemberExpire(c);
				} catch (Exception ex) {
					logger.error("setMemberExpire error. value=" + value, ex);
				}
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		
		return arraylist;
	}

	/**
	 * 餐食喜好信息
	 * @param rowmap
	 * @return
	 */
	private ArrayList getPreferencePoByMap(HashMap rowmap) {
		if (rowmap == null || rowmap.size() <= 0) {
			return null;
		}
		
		ArrayList arraylist = new ArrayList();
		String value = rowmap.get("catalog1") != null ? rowmap.get("catalog1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			PreferencePo po = new PreferencePo();
			po.setCatalog(value);		//偏好目录
			
			value = rowmap.get("type1") != null ? rowmap.get("type1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setValue1(value);		//偏好值
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		value = rowmap.get("catalog2") != null ? rowmap.get("catalog2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			PreferencePo po = new PreferencePo();
			po.setCatalog(value);	//偏好目录
			
			value = rowmap.get("type2") != null ? rowmap.get("type2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setValue1(value);		//偏好值
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		return arraylist;
	}

	/**
	 * 获取地址VO对象.
	 * @param rowmap
	 */
	private ArrayList getAddressPoByMap(HashMap rowmap) {
		if (rowmap == null || rowmap.size() <= 0) {
			return null;
		}

		ArrayList arraylist = new ArrayList();
		String value = rowmap.get("addresstype1") != null ? rowmap.get("addresstype1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			AddressPo po = new AddressPo();
			po.setCatalog(value);
			
			value = rowmap.get("address1") != null ? rowmap.get("address1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setAddress(value);
			}
			value = rowmap.get("city1") != null ? rowmap.get("city1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setCity(value);
			}
			value = rowmap.get("zipcode1") != null ? rowmap.get("zipcode1").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setZipCode(value);
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		value = rowmap.get("addresstype2") != null ? rowmap.get("addresstype2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			AddressPo po = new AddressPo();
			po.setCatalog(value);
			
			value = rowmap.get("address2") != null ? rowmap.get("address2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setAddress(value);
			}
			value = rowmap.get("city2") != null ? rowmap.get("city2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setCity(value);
			}
			value = rowmap.get("zipcode2") != null ? rowmap.get("zipcode2").toString() : null;
			if (StringUtils.isNotEmpty(value)) {
				po.setZipCode(value);
			}
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdatedatetime(Calendar.getInstance());
			po.setCreateuserno("ppp");
			po.setUpdateuserno("ppp");
			arraylist.add(po);
		}
		
		return arraylist;
	}
	
	private TravelerPo getParVOByMap(HashMap rowmap){
		TravelerPo po = new TravelerPo();

		String value = rowmap.get("travelerid") != null ? rowmap.get("travelerid").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
//			po.setId(Integer.parseInt(value));
			po.setUpdateuserno("ppp");
			po.setUpdatedatetime(Calendar.getInstance());
		}else{
			po.setReqStatement(1);
			po.setFeeBased(1);
			po.setCreateuserno("ppp");
			po.setCreatedatetime(Calendar.getInstance());
			po.setUpdateuserno("ppp");
			po.setUpdatedatetime(Calendar.getInstance());
		}
		value = rowmap.get("travelerno") != null ? rowmap.get("travelerno").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setTravelerNo(value);
		}
		value = rowmap.get("pppcmid") != null ? rowmap.get("pppcmid").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setPppcmid(value);
		}
		value = rowmap.get("firstname") != null ? rowmap.get("firstname").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setFirstname(value);
		}
		value = rowmap.get("lastname") != null ? rowmap.get("lastname").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setLastname(value);
		}
		value = rowmap.get("firstname_en") != null ? rowmap.get("firstname_en").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setFirstnameEn(value);
		}
		value = rowmap.get("lastname_en") != null ? rowmap.get("lastname_en").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setLastnameEn(value);
		}
		value = rowmap.get("gender") != null ? rowmap.get("gender").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setGender(value);
		}
		value = rowmap.get("email") != null ? rowmap.get("email").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setEmail(value);
		}
		value = rowmap.get("icid_no") != null ? rowmap.get("icid_no").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setIcidNo(value);
		}
		value = rowmap.get("nationality1") != null ? rowmap.get("nationality1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setNationality1(value);
		}
		value = rowmap.get("nationality2") != null ? rowmap.get("nationality2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setNationality2(value);
		}
		value = rowmap.get("birthday") != null ? rowmap.get("birthday").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setBirthday(value);
		}
		value = rowmap.get("passport1") != null ? rowmap.get("passport1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setPassport1(value);
		}
		value = rowmap.get("issue_date1") != null ? rowmap.get("issue_date1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setIssueDate1(value);
		}
		value = rowmap.get("expiry_date1") != null ? rowmap.get("expiry_date1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setExpiryDate1(value);
		}
		value = rowmap.get("passporttype1") != null ? rowmap.get("passporttype1").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setPassport1Type(value);
		}
		value = rowmap.get("passport2") != null ? rowmap.get("passport2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setPassport2(value);
		}
		value = rowmap.get("issue_date2") != null ? rowmap.get("issue_date2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setIssueDate2(value);
		}
		value = rowmap.get("expiry_date2") != null ? rowmap.get("expiry_date2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setExpiryDate2(value);
		}
		value = rowmap.get("passporttype2") != null ? rowmap.get("passporttype2").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setPassport2Type(value);
		}
		value = rowmap.get("fstatus") != null ? rowmap.get("fstatus").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setStatus(value);
		}
		value = rowmap.get("mobile") != null ? rowmap.get("mobile").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setMobile(value);
		}
		
		//将product信息先记录在MaritalStatus里.方便比较.
		value = rowmap.get("product") != null ? rowmap.get("product").toString() : null;
		if (StringUtils.isNotEmpty(value)) {
			po.setAlias(value);
		}
		
		
		
		return po;
	}


	/**
	 * 警告信息统一处理方法.
	 * fans.fan
	 * @param errmsg
	 */
	private void warningProcess(String errmsg){
		logger.warn(errmsg);
//		Sendmail.getInstance().send("PPP sync par error.", errmsg);
	}
	
	/**
	 * 错误信息统一处理方法.
	 * 1.写log.
	 * 2.将错误信息发送给运维人员.
	 * fans.fan
	 * @param errmsg
	 */
	private void errorProcess(String errmsg,boolean stoprun){
		logger.error(errmsg);
		Sendmail.getInstance().send("PPP sync par error.", errmsg);
		if (stoprun) {
		}
	}
	
	
}


