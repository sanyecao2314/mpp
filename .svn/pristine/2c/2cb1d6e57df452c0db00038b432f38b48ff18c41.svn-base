package com.citsamex.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.citsamex.annotation.Validate;
import com.citsamex.app.util.CommonUtil;
import com.citsamex.app.validate.IValidator;
import com.citsamex.app.validate.ValidatorManager;
import com.citsamex.service.db.CompanyDao;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.DataMapping;
import com.citsamex.service.db.MemberPo;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.util.SpringUtil;
import com.citsamex.vo.Address;
import com.citsamex.vo.Contact;
import com.citsamex.vo.CreditCard;
import com.citsamex.vo.Member;
import com.citsamex.vo.Preference;
import com.citsamex.ws.AxoService;
import com.citsamex.ws.AxoServiceProxy;

public class EipImpService {
	 private static CommonUtil uti = new CommonUtil();
	 private static final ConcurrentHashMap<String, String> pool = new ConcurrentHashMap<String, String>();
	  
	 public String transport(String xml) {
		  TravelerMpp trav = TravelerMpp.parse(xml);
		  Message message=this.isvalid(trav);
		  try{
			  if (message.isSuccess()) {
	              ITravelerService travelerService = (ITravelerService) SpringUtil
	                       .getBean("travelerService");
	              TravelerDao dao = (TravelerDao) SpringUtil
	                       .getBean("travelerDao");
	              CompanyDao cdao = (CompanyDao) SpringUtil.getBean("companyDao");
	              TravelerPo po = null;
	              //boolean changeBar = false;
	              String axoCustNo = trav.getTravelerNo();
	              String axoBarNo = trav.getNewCompanyNo();
	              String companyID = trav.getCompanyName();
	              String eipUserID = trav.getRefno2();
	              String axoDepartment2 = trav.getDepartment2();
	              Logger.getRootLogger().info("--------------------------------------------------");
	              Logger.getRootLogger().info("axoCustNo = " + axoCustNo);
	              Logger.getRootLogger().info("axoBarNo = " + axoBarNo);
	              Logger.getRootLogger().info("companyID = " + companyID);
	              Logger.getRootLogger().info("eipUserID = " + eipUserID);
	              Logger.getRootLogger().info("axoDepartment2 = " + axoDepartment2);
	              po = findExistTraveler(dao, axoCustNo, eipUserID);
	              if (po == null && StringUtils.isNotEmpty(axoCustNo) && !"##########".equals(axoCustNo)
	                      && axoCustNo.length() == 10) {
	                  po = dao.find(axoCustNo);
	                  if (po == null) {
	                      String servMsg = travelerService.loadPar(companyID, axoCustNo);
	                      if (servMsg.equalsIgnoreCase("success")) {
	                          po = dao.find(axoCustNo);
	                      }
	                  }
	              }
	              String newParNo = null;
	              CompanyPo cpo = null;
	
	              if (po == null || (axoBarNo != null && !po.getTravelerNo().startsWith(axoBarNo))) {
	                  cpo = cdao.find(axoBarNo);
	                  if (cpo == null) {
	                      message.setSuccess(false);
	                      message.setBody("Company infomation not found." + axoBarNo);
	                      Logger.getRootLogger().info("Company infomation not found." + axoBarNo);
	                      return message.toXML();
	                  }
	                  newParNo = travelerService.getMaxTravelerNo(axoBarNo);
	                  if (uti.isEmpty(newParNo)) {
	                      message.setSuccess(false);
	                      message.setBody("System can't create a new par no." + axoBarNo);
	                      Logger.getRootLogger().info("System can't create a new par no." + axoBarNo);
	                      return message.toXML();
	                  }
	              } else {
	                  newParNo = po.getTravelerNo();
	              }
	              Logger.getRootLogger().info("traveler infomation is normal, ready to save or update to mpp....");
	              message.setMessage(newParNo);
	              //changeBar = po != null && axoBarNo != null && !po.getTravelerNo().startsWith(axoBarNo);
	              if (po != null) {
	                  convert(po, trav);
	                  po.setUpdatedatetime(Calendar.getInstance());
	                  po.setUpdateuserno("eip");
	                  /*if (changeBar) {
	                      po.setStatusRemark("User change bar in eip.(" + newParNo + ")");
	                      po.setStatus("INACTIVE");
	                      po.setFeeBased(0);
	                  }*/
	                  dao.update(po);
	                  travelerService.synchronize(po.getTravelerNo(), 0, 1,
	          				"PS,BS,AXO");
	                  /*if (changeBar) {
	                      travelerService.synchronize(po, 0, 1, "PS,BS,AXO");
	                  }*/
	              } else if (cpo != null) {
	                  po = new TravelerPo();
	                  uti.copyProperties(po, trav);
	                  po.setTravelerNo(newParNo);
	                  po.setFeeBased(1);
	                  po.setReqStatement(1);
	                  po.setStatus("ACTIVE");
	                  Calendar now = Calendar.getInstance();
	                  po.setCreatedatetime(now);
	                  po.setCreateuserno("eip");
	                  po.setTravelerType("traveler");
	                  po.setCustType("Commercial");
	                  po.setCompanyName(cpo.getCompanyName());
	                  po.setCompanyId(cpo.getId());
	                  po.setEbilling(cpo.getEbilling());
	                  Logger.getRootLogger().info("EipImpService ready to save a traveler, " + po.toString());
	                  dao.save(po);
	              }
	
	              if ("NEW".equalsIgnoreCase(trav.getAction())) {
	                  pool.put(newParNo, newParNo);
	              }
	              if (trav.isUpdateContact()) {
	                    dao.deleteRelation("ContactPo", "T" + po.getId());

	                    List<Contact> contacts = trav.getContacts();
	                    for (int i = 0; i < contacts.size(); i++) {
	                        Contact contact = contacts.get(i);
	                        travelerService.createContact(contact.getFirstname(),
	                                contact.getLastname(),
	                                contact.getFirstname_en(),
	                                contact.getLastname_en(),
	                                contact.getHomephone(),
	                                contact.getOfficephone(), contact.getTitle(),
	                                contact.getFaxno(), contact.getEmail(),
	                                contact.getMobile(), po.getId());
	                    }
	                }

	                // pref
	                if (trav.isUpdatePref()) {
	                    dao.deleteRelation("PreferencePo", "T" + po.getId());
	                    List<Preference> prefs = trav.getPref();
	                    for (int i = 0; i < prefs.size(); i++) {
	                        Preference pref = prefs.get(i);
	                        travelerService.createPrefer(pref.getCatalog(),
	                                pref.getType(), pref.getValue1(),
	                                pref.getValue2(), pref.getRemark(), po.getId());
	                    }
	                }

	                if (trav.isUpdateAddress()) {
	                    dao.deleteRelation("AddressPo", "T" + po.getId());
	                    List<Address> addresses = trav.getAddresses();
	                    for (int i = 0; i < addresses.size(); i++) {
	                        Address address = addresses.get(i);
	                        travelerService.createAddress(address.getCatalog(),
	                                address.getAddress(), address.getCity(),
	                                address.getZipCode(), po.getId());
	                    }
	                }

	                List<CreditCard> creditCards = trav.getCreditCards();
	                if (trav.isUpdateCreditCard() && creditCards != null && creditCards.size() > 0) {
	                    dao.deleteRelation("CreditCardPo", "T" + po.getId());
	                    for (int i = 0; i < creditCards.size(); i++) {
	                        CreditCard ccd = creditCards.get(i);
	                        ccd.setRelationId("T" + po.getId());
	                        travelerService.createCreditCard(ccd);
	                    }
	                }

	                List<Member> members = trav.getMembers();
	                if (trav.isUpdateMember() && members != null && members.size() > 0) {
	                    dao.deleteRelation("MemberPo", "T" + po.getId());
	                    List<DataMapping>  dataMappingList = dao.listDataMapping2("com.citsamex.service.db.MemberPo", 
	                    		po.getTravelerNo());
	                    for (int i = 0; i < members.size(); i++) {
	                        Member member = members.get(i);
	                        SimpleDateFormat sdf = new SimpleDateFormat(
	                                "yyyy-MM-dd");
	                        String d1 = "";
	                        Calendar c1 = member.getMemberIssue();
	                        if (c1 != null) {
	                            d1 = sdf.format(c1.getTime());
	                        }

	                        String d2 = "";
	                        Calendar c2 = member.getMemberExpire();
	                        if (c2 != null) {
	                            d2 = sdf.format(c2.getTime());
	                        }

	                        ServiceMessage sm = travelerService.createMember(member.getProdType(),
	                                member.getMemberNo(), member.getMemberName(),
	                                member.getMemberCode(), d1, d2,
	                                member.getRemark(), po.getId());
	                        if (sm.isSuccess()) {
	                        	MemberPo memPo = (MemberPo) sm.getData();
	                        	/**
	                        	 * modify by fans.fan 从OP同步过来的没有已删除的member信息,mapping也没有更新,导致PS中的信息部会更新. 140620.
	                        	for (int j = 0; j < dataMappingList.size(); j++) {
	                        		DataMapping dataMapping =  dataMappingList.get(j);
	                        		if(dataMapping.getPs().contains(memPo.getMemberNo())){
	                        			StringBuffer sb = new StringBuffer();
	                        			sb.append("CUSTNO=" + po.getTravelerNo());
	                        			sb.append(",MEMTYPE=" + memPo.getProdType());
	                        			sb.append(",MEMNO=" + memPo.getMemberNo());
	                        			sb.append(",MEMSUPP=" + memPo.getMemberCode());
	                        			dataMapping.setMpp(String.valueOf(memPo.getId()));
	                        			dataMapping.setPs(sb.toString());
	                        			dao.update(dataMapping);
	                        		}
	                        	}
	                        	**/
	                        	//add by fans.fan
	                        	for (int j = 0; j < dataMappingList.size(); ) {
	                        		DataMapping dataMapping =  dataMappingList.get(j);
	                        		if(dataMapping.getPs().contains(memPo.getMemberNo())){
	                        			StringBuffer sb = new StringBuffer();
	                        			sb.append("CUSTNO=" + po.getTravelerNo());
	                        			sb.append(",MEMTYPE=" + memPo.getProdType());
	                        			sb.append(",MEMNO=" + memPo.getMemberNo());
	                        			sb.append(",MEMSUPP=" + memPo.getMemberCode());
	                        			dataMapping.setMpp(String.valueOf(memPo.getId()));
	                        			dataMapping.setPs(sb.toString());
	                        			dao.update(dataMapping);
	                        			//匹配上的更新后,从list里删除.
	                        			dataMappingList.remove(j);
	                        		}
	                        		j++;
	                        	}
	                        	//没有匹配上的都是要删除的.
	                        	for (int j = 0; j < dataMappingList.size();j++ ) {
	                        		DataMapping dataMapping =  dataMappingList.get(j);
	                        		dataMapping.setAction("delete");
	                        		dao.update(dataMapping);
	                        	}
	                        	//end by fans.fan
	                        }
	                        
	                        
	                    }   
	                }
	              /*
	              if (changeBar) {
	                  travelerService.changeBar(po.getTravelerNo(), newParNo, trav.getNewCompanyNo(),axoDepartment2, 0, "User change bar in eip.");
	              } 
	              DuplicatedValidator validator = new DuplicatedValidator();
	              boolean val = validator.validforAXO(po);
	              if (!val) {
	                  Sendmail.getInstance().send("Duplicate traveler from eip", validator.getMessage() + validator.getAttachMessage());
	              }*/
	          }
		  }catch(Exception e){
			  message.setSuccess(false);
	          message.setMessage(e.getMessage());
	          Logger.getRootLogger().error("EipImpService error, L212", e);
		  }
	      return message.toXML();
	 }
	 
	  private Message isvalid(com.citsamex.vo.Traveler trav) {
	        Message message = new Message();
	        message.setHead("to synchronize traveler");
	        if (trav != null) {
	            message.setSuccess(true);
	            message.setBody("processing");
	            StringBuffer buffer = new StringBuffer();
	            ValidatorManager validatorManager = (ValidatorManager) SpringUtil
	                    .getBean("validatorManager");
	            Field[] fields = trav.getClass().getDeclaredFields();
	            for (int i = 0; i < fields.length; i++) {
	                if (fields[i].isAnnotationPresent(Validate.class)) {
	                    String vname = fields[i].getAnnotation(Validate.class)
	                            .value();
	                    Object value = CommonUtil.getValue(trav,
	                            fields[i].getName());

	                    List<IValidator> validators = validatorManager
	                            .getValidators(vname);
	                    for (int j = 0; j < validators.size(); j++) {
	                        IValidator validator = validators.get(j);
	                        if (!validator.valid(value)) {
	                            buffer.append(validator.getMessage());
	                            buffer.append("\n");
	                        }
	                    }

	                }
	            }
	            if (!buffer.toString().isEmpty()) {
	                message.setSuccess(false);
	                message.setBody(buffer.toString());
	            }
	        } else {
	            message.setSuccess(false);
	            message.setBody("Failed to parse data transport from EIP");
	        }
	        return message;
	  }
	  
	  /*private void convert(TravelerPo po, TravelerMpp trav)throws Exception{
		  Class poCls=po.getClass();
		  Class trCls=trav.getClass();
		  Field[] trFields=trCls.getDeclaredFields();
		  for (Field field : trFields) {
			 String getMethodName=this.createGetMethodName(field);
			 String setMethodName=this.createSetMethodName(field);
			 try{
				 Method getMethod=trCls.getMethod(getMethodName);
				 Object methodValue=getMethod.invoke(trav);
				 Method setMethod=poCls.getMethod(setMethodName, field.getType());
				 setMethod.invoke(po, methodValue);
			 }catch (NoSuchMethodException e) {
				//只复制共有属性
			 }
		  }
	  }*/
	  
	  public String transportESB(String xmlStr) {
		  	String custno=this.parse(xmlStr);
	        Message message = new Message();
	        message.setHead("transport to esb");
	        message.setSuccess(true);
	        message.setBody("processing");

	        ITravelerService travelerService = (ITravelerService) SpringUtil.getBean("travelerService");
	        String xml = travelerService.buildTravelerXML(custno, true, "PS,BS,AXO", true);
	        travelerService.transport2Esb(custno, xml);

	        if (pool.containsKey(custno)) {
	            AxoService axoService = new AxoServiceProxy();
	            try {
	                axoService.initPasswd(custno);
	            } catch (RemoteException e) {
	                e.printStackTrace();
	            }
	            pool.remove(custno);
	        }

	        message.setSuccess(true);
	        message.setBody("success");
	        return message.toXML();
	    }
	  
	  private TravelerPo convert(TravelerPo po, TravelerMpp trav) {
	        if (trav.getCompanyName() != null)
	            po.setCompanyName(trav.getCompanyName());

	        if (trav.getTravelerNo() != null)
	            po.setTravelerNo(trav.getTravelerNo());

	        if (trav.getStatus() != null)
	            po.setStatus(trav.getStatus());

	        if (trav.getStatusRemark() != null)
	            po.setStatusRemark(trav.getStatusRemark());

	        if (trav.getFirstname() != null)
	            po.setFirstname(trav.getFirstname());

	        if (trav.getFirstnameEn() != null)
	            po.setFirstnameEn(trav.getFirstnameEn());

	        if (trav.getLastname() != null)
	            po.setLastname(trav.getLastname());

	        if (trav.getLastnameEn() != null)
	            po.setLastnameEn(trav.getLastnameEn());

	        if (trav.getTravelerNameCh() != null)
	            po.setTravelerName(trav.getTravelerNameCh());

	        if (trav.getTravelerNameCh() != null)
	            po.setTravelerName(trav.getTravelerNameCh());

	        if (trav.getGender() != null)
	            po.setGender(trav.getGender());

	        if (trav.getBirthday() != null)
	            po.setBirthday(trav.getBirthday());

	        if (trav.getEmail() != null)
	            po.setEmail(trav.getEmail());

//	        if (trav.getOfficePhone() != null)
	        po.setOfficePhone(trav.getOfficePhone());

	        if (trav.getMobile() != null)
	            po.setMobile(trav.getMobile());

	        if (trav.getNationality1() != null)
	            po.setNationality1(trav.getNationality1());

	        if (trav.getNationality2() != null)
	            po.setNationality2(trav.getNationality2());

	        if (trav.getIcidNo() != null)
	            po.setIcidNo(trav.getIcidNo());

//			if (trav.getPassport1() != null)
	        po.setPassport1(trav.getPassport1());

//			if (trav.getPassport2() != null)
	        po.setPassport2(trav.getPassport2());

//			if (trav.getExpiryDate1() != null)
	        po.setExpiryDate1(trav.getExpiryDate1());

//			if (trav.getExpiryDate2() != null)
	        po.setExpiryDate2(trav.getExpiryDate2());

//			if (trav.getIssueDate1() != null)
	        po.setIssueDate1(trav.getIssueDate1());

//			if (trav.getIssueDate2() != null)
	        po.setIssueDate2(trav.getIssueDate2());

//			if (trav.getOtherCard() != null)
	        po.setOtherCard(trav.getOtherCard());

//			if (trav.getOtherCardExpiryDate() != null)
	        po.setOtherCardExpiryDate(trav.getOtherCardExpiryDate());

//			if (trav.getOtherCardIssueDate() != null)
	        po.setOtherCardIssueDate(trav.getOtherCardIssueDate());

//			if (trav.getOtherCardType() != null)
	        po.setOtherCardType(trav.getOtherCardType());

//			if (trav.getExitPermitNo() != null)
	        po.setExitPermitNo(trav.getExitPermitNo());

//			if (trav.getExitPermitExpDate() != null)
	        po.setExitPermitExpDate(trav.getExitPermitExpDate());

	        if (trav.getProjectNumber() != null)
	            po.setProjectNumber(trav.getProjectNumber());

	        if (trav.getAccountNumber() != null)
	            po.setAccountNumber(trav.getAccountNumber());

	        if (trav.getEmployeeNo() != null)
	            po.setEmployeeNo(trav.getEmployeeNo());

	        if (trav.getDepartment1() != null)
	            po.setDepartment1(trav.getDepartment1());

	        if (trav.getDepartment2() != null)
	            po.setDepartment2(trav.getDepartment2());

	        if (trav.getJobTitle() != null)
	            po.setJobTitle(trav.getJobTitle());

	        if (trav.getCostcenter() != null)
	            po.setCostcenter(trav.getCostcenter());

	        if (trav.getTr() != null)
	            po.setTr(trav.getTr());

	        if (trav.getRefno() != null) {
	            po.setRefno(trav.getRefno());
	        }
	        if (trav.getRefno2() != null) {
	            po.setRefno2(trav.getRefno2());
	        }
	        return po;
	    }
	  
	  private String createGetMethodName(Field field){
		  String name=field.getName();
		  String getMethodName=name.substring(0,1).toUpperCase()+name.substring(1);
		  String fieldType=field.getType().getSimpleName();
		  if(fieldType.equalsIgnoreCase("boolean")){
			  getMethodName="is"+getMethodName;
		  }else{
			  getMethodName="get"+getMethodName;
		  }
		  return getMethodName;
	  }
	  
	  private String createSetMethodName(Field field){
		  String name=field.getName();
		  String setMethodName=name.substring(0,1).toUpperCase()+name.substring(1);
		  setMethodName="set"+setMethodName;
		  return setMethodName;
	  }
	  
	  protected TravelerPo findExistTraveler(TravelerDao dao, String custNo, String userID) {
	        TravelerPo foundPO = null;
	        List<TravelerPo> allExists = dao.findByRefno2(userID);
	        if (allExists != null && allExists.size() > 0) {
	            if (StringUtils.isNotBlank(custNo)) {
	                for (int i = 0; i < allExists.size(); i++) {
	                    TravelerPo existPO = allExists.get(i);
	                    if (existPO != null && custNo.equalsIgnoreCase(existPO.getTravelerNo())) {
	                        foundPO = existPO;
	                        break;
	                    }
	                }
	            } else {
	                foundPO = allExists.get(0);
	            }
	        }
	        return foundPO;

	    }
	 
	  public  String parse(String xml) {
		  	String custNo=null;
			try {
				XMLDecoder decoder=new XMLDecoder(new ByteArrayInputStream(xml.getBytes("UTF-8")));
				Message message=(Message)decoder.readObject();
				custNo=message.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return custNo;
		}
	  
}
