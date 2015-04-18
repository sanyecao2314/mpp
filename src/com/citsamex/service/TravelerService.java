package com.citsamex.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.Validate;
import com.citsamex.app.Server;
import com.citsamex.app.action.Page;
import com.citsamex.app.prof.ProfileBuilder;
import com.citsamex.app.util.CommonUtil;
import com.citsamex.app.util.ServiceConfig;
import com.citsamex.app.validate.DuplicatedValidator;
import com.citsamex.app.validate.IValidator;
import com.citsamex.app.validate.ValidatorManager;
import com.citsamex.service.action.Traveler;
import com.citsamex.service.db.AddressHisPo;
import com.citsamex.service.db.AddressPo;
import com.citsamex.service.db.CardbinPo;
import com.citsamex.service.db.CommonDao;
import com.citsamex.service.db.CompanyDao;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.ContactPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.CustomerDao;
import com.citsamex.service.db.CustomerPo;
import com.citsamex.service.db.DataMapping;
import com.citsamex.service.db.DiscountPo;
import com.citsamex.service.db.EBillingPo;
import com.citsamex.service.db.ExtraPo;
import com.citsamex.service.db.FeeCodeDetailPo;
import com.citsamex.service.db.FeeCodePo;
import com.citsamex.service.db.MemberPo;
import com.citsamex.service.db.PreferenceHisPo;
import com.citsamex.service.db.PreferencePo;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.service.db.VisaPo;
import com.citsamex.util.SpringUtil;
import com.citsamex.vo.CreditCard;
import com.citsamex.vo.Extra;
import com.citsamex.ws.AxoService;
import com.citsamex.ws.AxoServiceProxy;
import com.citsamex.ws.BsService;
import com.citsamex.ws.BsServiceProxy;
import com.citsamex.ws.PsService;
import com.citsamex.ws.PsServiceProxy;

public class TravelerService extends CommonService implements ITravelerService {
	
	private final static Logger logger = Logger.getLogger(TravelerService.class);

	private TravelerDao dao;
	private CompanyDao compdao;
	private CustomerDao custdao;
	private CommonDao commondao;

	public CommonDao getCommondao() {
		return commondao;
	}

	public void setCommondao(CommonDao commondao) {
		this.commondao = commondao;
	}

	private ValidatorManager validatorManager;

	int sheetNo = 0;
	int excelCurX = 0;
	int excelCurY = 0;
	String errorMessage = "";

	private static Map<Integer, String> dataDefMap = null;
	private static Map<Integer, String> data2DefMap = null;

	public ValidatorManager getValidatorManager() {
		return validatorManager;
	}

	public void setValidatorManager(ValidatorManager validatorManager) {
		this.validatorManager = validatorManager;
	}

	public int getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}

	public int getExcelCurX() {
		return excelCurX;
	}

	public void setExcelCurX(int excelCurX) {
		this.excelCurX = excelCurX;
	}

	public int getExcelCurY() {
		return excelCurY;
	}

	public void setExcelCurY(int excelCurY) {
		this.excelCurY = excelCurY;
	}

	public String getErrorMessage() {
		return errorMessage + "SheetNo:" + sheetNo + ",RowNo:" + excelCurY
				+ ",ColumnNo:" + excelCurX;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CustomerDao getCustdao() {
		return custdao;
	}

	public void setCustdao(CustomerDao custdao) {
		this.custdao = custdao;
	}

	public TravelerDao getDao() {
		return dao;
	}

	public void setDao(TravelerDao dao) {
		this.dao = dao;
	}

	public CompanyDao getCompdao() {
		return compdao;
	}

	public void setCompdao(CompanyDao compdao) {
		this.compdao = compdao;
	}

	public ServiceMessage findTraveler(Long id) {
		Traveler traveler = null;
		TravelerPo po = (TravelerPo) dao.find(id);
		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		if (po != null) {
			traveler = new Traveler();
			traveler.setPo(po);

		} else {
			sm.setCode("4001");
		}

		sm.setData(traveler);
		return sm;
	}

	public ServiceMessage listTraveler(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<TravelerPo> polist = dao.list(map);
		List<Traveler> list = new ArrayList<Traveler>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Traveler traveler = new Traveler();
			traveler.setPo(polist.get(i));
			list.add(traveler);
		}

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage deleteTraveler(Long id) {
		TravelerPo po = (TravelerPo) dao.find(TravelerPo.class, id);
		if (po == null) {
			return new ServiceMessage("4003");
		}
		String travelerNo = po.getTravelerNo();

		PsService psService = new PsServiceProxy();
		com.citsamex.vo.Traveler traveler = new com.citsamex.vo.Traveler();

		if (travelerNo.length() == 10) {
			dao.deleteTraveler(travelerNo);
		} else {

			uti.copyProperties(traveler, po);
			traveler.setAction("DELETE");

			dao.deleteRelations("T" + po.getId());
			dao.deleteDataMapping(po.getTravelerNo());
			dao.delete(po);
			try {
				psService.sync(traveler.getTravelerNo(), traveler.toXML());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(po.getId());
		return sm;
	}

	public ServiceMessage activeTraveler(Long id) {
		TravelerPo po = (TravelerPo) dao.find(TravelerPo.class, id);
		if (po == null) {
			return new ServiceMessage("4003");
		}

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage listTraveler() {
		@SuppressWarnings("unchecked")
		List<TravelerPo> polist = dao.list(new HashMap<String, Object>());
		List<Traveler> list = new ArrayList<Traveler>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Traveler traveler = new Traveler();
			traveler.setPo(polist.get(i));
			list.add(traveler);
		}

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage create(Traveler traveler) {

		if (isEmpty(traveler.getTravelerNo())) {
			return new ServiceMessage("4001");
		}

		if (dao.find(traveler.getTravelerNo()) != null) {
			return new ServiceMessage("4002");
		}

		TravelerPo po = traveler.buildPo();
		po.setStatus("ACTIVE");
		po.setReqStatement(1);
		po.setFeeBased(1);
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());

		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		traveler.setPo(po);
		sm.setData(traveler);
		return sm;
	}

	public ServiceMessage findTraveler(String name) {
		Traveler traveler = null;
		TravelerPo po = (TravelerPo) dao.find(name);
		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		if (po != null) {
			traveler = new Traveler();
			traveler.setPo(po);

		} else {
			sm.setCode("1001");
		}

		sm.setData(traveler);
		return sm;
	}
	public ServiceMessage findByCompanyNo(String companyNo) {
		List<TravelerPo> poList = dao.findByCompanyNo(companyNo);
		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		if (poList != null) {

		} else {
			sm.setCode("1001");
		}
		sm.setData(poList);
		return sm;
	}
	public ServiceMessage updateTraveler(Traveler traveler) {
		TravelerPo po = dao.find(traveler.getId());

		if (po == null) {
			return new ServiceMessage("4003");
		}
		
//		int dirty = po.getDirty();
		
		uti.copyProperties(po, traveler);
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		
		//add by fans.fan.par信息重复效验.140508.
		DuplicatedValidator validator = new DuplicatedValidator();
	    boolean val = validator.validforAXO(po);
	    if (!val) {
	    	Logger.getRootLogger().error("Duplicate traveler from jsp page." + po.getTravelerNo());
	    	return new ServiceMessage("4002");
	    }	
	    //end by fans.fan
		try {
			dao.update(po);
//			TravelerHisPo lastHis = dao.findLastHisTraveler(po.getTravelerNo());
//			lastHis.setDirty(dirty);
//			dao.update(lastHis);
			
		} catch (Exception ex) {
			//ex.printStackTrace();
		    Logger.getRootLogger().error("update traveler po failure, travlerService L315",ex);
			
		}

		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(traveler);
		return sm;
	}

	public ServiceMessage createVisa(String country, String visaType,
			String visaEntry, String visaNo, String visaissuedate,
			String visaexpiredate, String visaRemark, String purpose,
			String noOfEntry, Long companyId) {
		VisaPo po = new VisaPo();
		po.setCountry(country);
		po.setVisaType(visaType);
		po.setVisaEntry(visaEntry);
		po.setVisaNo(visaNo);
		po.setVisaidate(uti.parseDate(visaissuedate));
		po.setVisaedate(uti.parseDate(visaexpiredate));
		po.setVisarem(visaRemark);
		po.setPurpose(purpose);
		po.setNoOfEntry(noOfEntry);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_VISA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateVisa(Long id, String country, String visaType,
			String visaEntry, String visaNo, String visaissuedate,
			String visaexpiredate, String visaRemark, String purpose,
			String noOfEntry) {
		VisaPo po = (VisaPo) dao.find(VisaPo.class, id);
		po.setCountry(country);
		po.setVisaType(visaType);
		po.setVisaEntry(visaEntry);
		po.setVisaNo(visaNo);
		po.setVisaidate(uti.parseDate(visaissuedate));
		po.setVisaedate(uti.parseDate(visaexpiredate));
		po.setVisarem(visaRemark);
		po.setPurpose(purpose);
		po.setNoOfEntry(noOfEntry);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_VISA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listVisa(Long companyId) {
		List list = dao.listRelation("VisaPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_VISA_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage findVisa(Long id) {
		VisaPo po = (VisaPo) dao.findRelation(VisaPo.class, id);
		ServiceMessage sm = new ServiceMessage(CODE_VISA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteVisa(Long id) {
		VisaPo po = (VisaPo) compdao.findRelation(VisaPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_VISA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createAddress(String addressType, String address,
			String city, String zipCode, Long companyId) {
		AddressPo po = new AddressPo();
		po.setCatalog(addressType);
		po.setAddress(address);
		po.setCity(city);
		po.setZipCode(zipCode);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listAddress(Long companyId) {
		List list = compdao.listAddress("T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage findAddress(Long id) {
		AddressPo po = compdao.findAddress(id);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateAddress(Long id, String address, String city,
			String zipCode) {
		AddressPo po = compdao.findAddress(id);
		po.setAddress(address);
		po.setCity(city);
		po.setZipCode(zipCode);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createContact(String firstname, String lastname,
			String firstname_en, String lastname_en, String homephone,
			String officephone, String title, String faxno, String email,
			String mobile, Long companyId) {
		ContactPo po = new ContactPo();
		po.setFirstname(firstname);
		po.setLastname(lastname);
		po.setFirstname_en(firstname_en);
		po.setLastname_en(lastname_en);
		po.setHomephone(homephone);
		po.setOfficephone(officephone);
		po.setTitle(title);
		po.setFaxno(faxno);
		po.setEmail(email);
		po.setMobile(mobile);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateCreditCard(Long id, String content) {

		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new ByteArrayInputStream(
					content.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CreditCardPo po = (CreditCardPo) dao.findRelation(CreditCardPo.class,
				id);

		CreditCardPo vo = (CreditCardPo) decoder.readObject();
		po.setInstitution(vo.getInstitution());
		po.setVendor(vo.getVendor());
		po.setCardNo(vo.getCardNo());
		po.setExpiry(vo.getExpiry());
		po.setInstitution(vo.getInstitution());
		po.setHolderName(vo.getHolderName());
		po.setType(vo.getType());
		po.setMerchantNo(vo.getMerchantNo());
		po.setBillType(vo.getBillType());
		po.setPreferenceCard(vo.getPreferenceCard());
		po.setRemark(vo.getRemark());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());

		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createCreditCard(String content) {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new ByteArrayInputStream(
					content.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CreditCardPo po = (CreditCardPo) decoder.readObject();
		po.setRelationId("T" + po.getRelationId());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createCreditCard(CreditCard credit) {

		CreditCardPo po = new CreditCardPo();
		uti.copyProperties(po, credit);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listContact(Long companyId) {
		List list = compdao.listRelation("ContactPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listCreditCard(Long companyId) {
		List list = compdao.listRelation("CreditCardPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage findContact(Long id) {
		ContactPo po = (ContactPo) compdao.findRelation(ContactPo.class, id);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteContact(Long id) {
		ContactPo po = (ContactPo) compdao.findRelation(ContactPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteAddress(Long id) {
		AddressPo po = (AddressPo) compdao.findRelation(AddressPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteCreditCard(Long id) {
		CreditCardPo po = (CreditCardPo) compdao.findRelation(
				CreditCardPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateContact(Long id, String firstname,
			String lastname, String firstname_en, String lastname_en,
			String homephone, String officephone, String title, String faxno,
			String email, String mobile) {
		ContactPo po = (ContactPo) compdao.findRelation(ContactPo.class, id);
		po.setFirstname(firstname);
		po.setLastname(lastname);
		po.setFirstname_en(firstname_en);
		po.setLastname_en(lastname_en);
		po.setHomephone(homephone);
		po.setOfficephone(officephone);
		po.setTitle(title);
		po.setFaxno(faxno);
		po.setEmail(email);
		po.setMobile(mobile);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createEBill(String fieldName, String defaultValue,
			String mandatory, Long travId) {
		EBillingPo po = new EBillingPo();
		po.setFieldName(fieldName);
		po.setDefaultValue(defaultValue);
		po.setMandatory(mandatory);
		po.setRelationId("T" + travId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		TravelerPo tpo = dao.find(travId);
		tpo.setEbilling(1);
		dao.update(tpo);

		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateEBill(Long id, String fieldName,
			String defaultValue, String mandatory) {
		EBillingPo po = (EBillingPo) compdao.findRelation(EBillingPo.class, id);

		po.setFieldName(fieldName);
		po.setDefaultValue(defaultValue);
		po.setMandatory(mandatory);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteEBill(Long id) {
		EBillingPo po = (EBillingPo) compdao.findRelation(EBillingPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listEBill(Long companyId) {
		List list = compdao.listRelation("EBillingPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage countTraveler(String companyName, String quicksearch,
			String quicksearchName, String quicksearchCreate,
			String quicksearchModify, Integer eBilling, String axo) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyName", companyName);
		map.put("quicksearch", quicksearch);
		map.put("quicksearchName", quicksearchName);
		map.put("ebilling", eBilling);
		map.put("quicksearchModify", quicksearchModify);
		map.put("quicksearchCreate", quicksearchCreate);
		map.put("axo", axo);

		int count = dao.count(map);

		Page page = new Page(count);
		ServiceMessage sm = new ServiceMessage(CODE_TRAVELER_SUCCESS);
		sm.setData(page);
		return sm;
	}

	@SuppressWarnings("unchecked")
	public ServiceMessage listTraveler(Integer currentPage, String companyName,
			String quicksearch, String quicksearchName,
			String quicksearchCreate, String quicksearchModify,
			Integer eBilling, String axo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageSize", Server.PROPS_PAGESIZE);
		map.put("companyName", companyName);
		map.put("quicksearch", quicksearch);
		map.put("quicksearchName", quicksearchName);
		map.put("ebilling", eBilling);
		map.put("quicksearchModify", quicksearchModify);
		map.put("quicksearchCreate", quicksearchCreate);
		map.put("axo", axo);

		List<TravelerPo> polist = dao.list(map);
		List<Traveler> list = new ArrayList<Traveler>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Traveler traveler = new Traveler();
			traveler.setPo(polist.get(i));
			list.add(traveler);
		}

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createMember(String prodType, String memberNo,
			String memberName, String memberCode, String memberIssue,
			String memberExpire, String remark, Long companyId) {
		MemberPo po = new MemberPo();
		po.setProdType(prodType);
		po.setMemberNo(memberNo);
		po.setMemberName(memberName);
		po.setMemberCode(memberCode);
		po.setMemberIssue(uti.parseDate(memberIssue));
		po.setMemberExpire(uti.parseDate(memberExpire));
		po.setRemark(remark);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_MEMBER_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateMember(Long id, String prodType,
			String memberNo, String memberName, String memberCode,
			String memberIssue, String memberExpire, String remark) {
		MemberPo po = (MemberPo) compdao.findRelation(MemberPo.class, id);
		po.setProdType(prodType);
		po.setMemberNo(memberNo);
		po.setMemberName(memberName);
		po.setMemberCode(memberCode);
		po.setMemberIssue(uti.parseDate(memberIssue));
		po.setMemberExpire(uti.parseDate(memberExpire));
		po.setRemark(remark);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());

		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_MEMBER_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteMember(Long id) {
		MemberPo po = (MemberPo) compdao.findRelation(MemberPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_MEMBER_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listMember(Long companyId) {
		List list = compdao.listRelation("MemberPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_MEMBER_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createExtra(String level, String fieldName,
			String mandatory, String action, String displayOrder,
			String defaultValue, Long companyId) {
		ExtraPo po = new ExtraPo();
		po.setLevel(level);
		po.setFieldName(fieldName);
		po.setMandatory(mandatory);
		po.setIsactive(action);
		po.setDisplayOrder(displayOrder);
		po.setDefaultValue(defaultValue);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_EXTRA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateExtra(Long id, String level, String fieldName,
			String mandatory, String action, String displayOrder,
			String defaultValue) {
		ExtraPo po = (ExtraPo) compdao.findRelation(ExtraPo.class, id);
		po.setLevel(level);
		po.setFieldName(fieldName);
		po.setMandatory(mandatory);
		po.setIsactive(action);
		po.setDisplayOrder(displayOrder);
		po.setDefaultValue(defaultValue);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());

		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_EXTRA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteExtra(Long id) {
		ExtraPo po = (ExtraPo) compdao.findRelation(ExtraPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_EXTRA_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listExtra(Long companyId) {
		List list = compdao.listRelation("ExtraPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_EXTRA_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createPrefer(String catalog, String type,
			String value1, String value2, String remark, Long travId) {

		PreferencePo po = new PreferencePo();
		po.setCatalog(catalog);
		po.setType(type);
		po.setValue1(value1);
		po.setValue2(value2);
		po.setRemark(remark);
		po.setRelationId("T" + travId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_PREFERENCE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updatePrefer(Long id, String catalog, String type,
			String value1, String value2, String remark) {
		PreferencePo po = (PreferencePo) compdao.findRelation(
				PreferencePo.class, id);
		po.setCatalog(catalog);
		po.setType(type);
		po.setValue1(value1);
		po.setValue2(value2);
		po.setRemark(remark);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());

		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_PREFERENCE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deletePrefer(Long id) {
		PreferencePo po = (PreferencePo) compdao.findRelation(
				PreferencePo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_PREFERENCE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listPrefer(Long companyId) {
		List list = compdao.listRelation("PreferencePo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_PREFERENCE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public String getMaxTravelerNo(String companyNo) {
		PsService psService = new PsServiceProxy();
		String newParNo = "1";
		try {
			newParNo = psService.getNewParNo(companyNo);
		} catch (RemoteException e) {
			Logger.getRootLogger().error("PS error, get new parno", e);
			return "";
		}
		String travelerNo = "";

		String maxTravNoInMpp = dao.getMaxTravelerNo(companyNo);
		//maxTravNoInMpp=>MBE1570050
		if (maxTravNoInMpp != null) {
			try {
				Integer mppNo = Integer.parseInt(maxTravNoInMpp
						.substring(6, 10));
				Integer psNo = Integer.parseInt(newParNo);
				if (mppNo >= psNo) {
					newParNo = String.valueOf(mppNo + 1);
				} else {
					newParNo = String.valueOf(psNo);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		if (newParNo.length() == 1) {
			travelerNo = "000" + newParNo;
		} else if (newParNo.length() == 2) {
			travelerNo = "00" + newParNo;
		} else if (newParNo.length() == 3) {
			travelerNo = "0" + newParNo;
		} else {
			travelerNo = newParNo;
		}

		return companyNo + travelerNo;
	}

	public String getMaxPotentialTravelerNo(String travelerNo, String prefix) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("petentialTravelerNo", travelerNo + "-" + prefix);
		int count = dao.count(map) + 1;

		String no = "";

		if (count < 10) {
			no = "0" + count;
		}

		return travelerNo + "-" + prefix + no;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCode(Long customerId) {
		List list = dao.listRelation("FeeCodePo", "T" + customerId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createFeeCode(String varname, String feeType,
			String fareCode, String groupId, Long customerId) {
		FeeCodePo po = new FeeCodePo();
		po.setGroupId(groupId);
		po.setFareCode(fareCode);
		po.setFeeType(feeType);
		po.setVarname(varname);
		po.setRelationId("T" + customerId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateFeeCode(Long id, String groupId,
			String defaultGroup) {
		FeeCodePo po = (FeeCodePo) dao.findRelation(FeeCodePo.class, id);
		po.setGroupId(groupId);
		po.setDefaultGroup(defaultGroup);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteFeeCode(Long id) {
		FeeCodePo po = (FeeCodePo) dao.findRelation(FeeCodePo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	// @SuppressWarnings("rawtypes")
	// public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId) {
	// List list = dao.listFeeCodeDetail("T" + companyId, feeCodeId);
	// ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
	// sm.setData(list);
	// return sm;
	// }

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String groupId) {
		List list = dao.listFeeCodeDetail("T" + companyId, feeCodeId, groupId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String period, String groupId) {
		List list = dao.listFeeCodeDetail("T" + companyId, feeCodeId, period,
				groupId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId) {
		List list = dao.listRelation("FeeCodeDetailPo", "T" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createFeeCodeDetail(String groupId,
			String validPeriod, String productCode, Double moneyRangeFrom,
			Double moneyRangeTo, Double feeAmt, Double feePercent,
			String feeMethod, String feeType, Long companyId, Long feeCodeId) {
		FeeCodeDetailPo po = new FeeCodeDetailPo();
		po.setGroupId(groupId);
		po.setValidPeriod(validPeriod);
		po.setProductCode(productCode);
		po.setMoneyRangeFrom(moneyRangeFrom);
		po.setMoneyRangeTo(moneyRangeTo);
		po.setFeeAmt(feeAmt);
		po.setFeePercent(feePercent);
		po.setFeeMethod(feeMethod);
		po.setFeeType(feeType);
		// po.setFeeCodeId(feeCodeId);
		po.setRelationId("T" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateFeeCodeDetail(Long id, String groupId,
			String validPeriod, String productCode, Double moneyRangeFrom,
			Double moneyRangeTo, Double feeAmt, Double feePercent,
			String feeMethod, String feeType) {
		FeeCodeDetailPo po = (FeeCodeDetailPo) dao.findRelation(
				FeeCodeDetailPo.class, id);
		po.setGroupId(groupId);
		po.setValidPeriod(validPeriod);
		po.setProductCode(productCode);
		po.setMoneyRangeFrom(moneyRangeFrom);
		po.setMoneyRangeTo(moneyRangeTo);
		po.setFeeAmt(feeAmt);
		po.setFeePercent(feePercent);
		po.setFeeMethod(feeMethod);
		po.setFeeType(feeType);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteFeeCodeDetail(Long id) {
		FeeCodeDetailPo po = (FeeCodeDetailPo) dao.findRelation(
				FeeCodeDetailPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage copyDiscount(String srcNo, String destId) {

		String srcId = "";
		ServiceMessage sm = null;
		if (!isEmpty(srcNo)) {
			if (srcNo.length() == 8) {
				CompanyPo po = compdao.find(srcNo);
				if (po != null) {
					srcId = "C" + po.getId();
				}
			} else if (srcNo.length() == 10) {
				TravelerPo po = dao.find(srcNo);
				if (po != null)
					srcId = "T" + po.getId();
			} else {
				CustomerPo po = custdao.find(srcNo);
				if (po != null)
					srcId = "S" + po.getId();
			}
		}

		if (srcId.equals("")) {
			sm = new ServiceMessage(CODE_DISCOUNT_ERROR);
		} else {
			List list = dao.listRelation("DiscountPo", srcId);
			for (int i = 0; i < list.size(); i++) {
				DiscountPo po = (DiscountPo) list.get(i);
				DiscountPo npo = new DiscountPo();
				uti.copyProperties(npo, po);
				npo.setRelationId(destId);
				dao.save(npo);
			}

		}
		sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage copyTerms(String srcNo, String destId) {
		String srcId = "";
		ServiceMessage sm = null;
		if (!isEmpty(srcNo)) {
			if (srcNo.length() == 6) {
				CompanyPo po = compdao.find(srcNo);
				if (po != null) {
					srcId = "C" + po.getId();
				}
			} else if (srcNo.length() == 10) {
				TravelerPo po = dao.find(srcNo);
				if (po != null)
					srcId = "T" + po.getId();
			} else {
				CustomerPo po = custdao.find(srcNo);
				if (po != null)
					srcId = "S" + po.getId();
			}
		}
		if (srcId.equals("")) {
			sm = new ServiceMessage(CODE_FEECODE_ERROR);
		} else {

			List listFeecode = dao.listRelation("FeeCodePo", srcId);
			for (int i = 0; i < listFeecode.size(); i++) {
				FeeCodePo po = (FeeCodePo) listFeecode.get(i);
				FeeCodePo nPo = new FeeCodePo();
				uti.copyProperties(nPo, po);
				nPo.setRelationId(destId);
				dao.save(nPo);

				// List listFeecodeDetail = dao.listFeeCodeDetail(srcId,
				// po.getId());
				// for (int j = 0; j < listFeecodeDetail.size(); j++) {
				// FeeCodeDetailPo detailpo = (FeeCodeDetailPo)
				// listFeecodeDetail.get(j);
				// FeeCodeDetailPo n2Po = new FeeCodeDetailPo();
				// uti.copyProperties(n2Po, detailpo);
				// n2Po.setRelationId(destId);
				// //n2Po.setFeeCodeId(nPo.getId());
				// dao.save(n2Po);
				// }

			}

		}
		sm = new ServiceMessage(CODE_FEECODE_SUCCESS);

		return sm;
	}

	public synchronized Object lock(String customerNo) {

		Object obj = null;

		synchronized (customerNo) {
			if (customerNo.equals(getBarno(customerNo))) {
				obj = compdao.find(customerNo);
			} else {
				obj = dao.find(customerNo);
			}

			int lock = (Integer) CommonUtil.getValue(obj, "lock");
			if (lock == 0) {
				CommonUtil.setValue(obj, "lock", 1);

				CommonUtil.setValue(obj, "lockuser", Server.getCurrentUser());
				compdao.update(obj);
			}

		}
		return obj;
	}

	public synchronized Object unlock(String customerNo) {

		Object obj = null;

		synchronized (customerNo) {
			if (customerNo.equals(getBarno(customerNo))) {
				obj = compdao.find(customerNo);
			} else {
				obj = dao.find(customerNo);
			}
			int lock = (Integer) CommonUtil.getValue(obj, "lock");
			String lockuser = (String) CommonUtil.getValue(obj, "lockuser");
			if (Server.getCurrentUser().equals(lockuser)) {
				if (lock == 1) {
					CommonUtil.setValue(obj, "lock", 0);

					CommonUtil.setValue(obj, "lockuser", "");
					compdao.update(obj);
				}
			}
		}
		return obj;
	}

	public Object setDirty(String customerNo, Integer dirty) {
		Object obj = null;

		synchronized (customerNo) {
			if (customerNo.equals(getBarno(customerNo))) {
				obj = compdao.find(customerNo);
			} else {
				obj = dao.find(customerNo);
			}

			CommonUtil.setValue(obj, "dirty", dirty);
			compdao.update(obj);
		}
		return obj;
	}
	
	public Object synchronize(TravelerPo travelerPo, Integer hasChild,
			Integer hasRelation, String systems) {
		final String custno = travelerPo.getTravelerNo();
		final boolean withChild = hasChild == 1;
		final boolean withRelation = hasRelation == 1;

		String xml = "";
		if (custno.length() == 10) {
			xml = buildTravelerXML(travelerPo, withRelation, systems);
		} else if (custno.indexOf("-") != -1) {
			String subNo = custno.substring(custno.indexOf("-") + 1);
			if (subNo.startsWith("F")) {
				xml = buildTravelerXML(travelerPo, withRelation, systems);
			} else {
				xml = "";
			}
		}

		transport2Esb(custno, xml);
		return custno;
		
	}
	
	public Object synchronize(String customerNo, Integer hasChild, Integer hasRelation, String systems){
		return synchronize(customerNo, hasChild, hasRelation, systems, false);
	}

	public Object synchronize(String customerNo, Integer hasChild, Integer hasRelation, String systems, boolean updateAXOBarPathOnly) {

		Object obj = null;

		if (customerNo.equals(getBarno(customerNo))) {
			obj = compdao.find(customerNo);
		} else {
			obj = dao.find(customerNo);
		}

		final String custno = customerNo;
		final boolean withChild = hasChild == 1;
		final boolean withRelation = hasRelation == 1;

		String xml = "";
		if (custno.length() == 10) {
			xml = buildTravelerXML(custno, withRelation, systems, updateAXOBarPathOnly);
		} else if (custno.length() == 6) {
			xml = buildCompanyXML(custno, withChild, withRelation, systems);
		} else if (custno.indexOf("-") != -1) {
			String subNo = custno.substring(custno.indexOf("-") + 1);
			if (subNo.startsWith("F")) {
				xml = buildTravelerXML(custno, withRelation, systems, updateAXOBarPathOnly);
			} else {
				xml = "";
			}
		}

		transport2Esb(custno, xml);
		return obj;
	}

	public void transport2Esb(String custno, String xml) {
		commondao
				.executeUpdate(
						"INSERT INTO T_JOB_SYNC_BPAR (CUSTNO, BPAR_XML, STATUS, PRIORITY, CREATEDATETIME) VALUES('"
								+ custno + "',:xml, 0, 100, GETDATE())",
						new String[] { "xml" }, new String[] { xml });
	}

	/**
	 * process synchronization return, if it's a create action in powersuite,
	 * the return value contains primary key in ps for sync next time.
	 * 
	 * @param custno
	 * @param str
	 * @return
	 */
	// private String processSync(String custno, String str) {
	// if (str.indexOf("[update]") == -1)
	// return str;
	//
	// String regExp = "\\[update\\].*?\\[/update\\]";
	// Pattern p = Pattern.compile(regExp);
	// Matcher m = p.matcher(str);
	//
	// while (m.find()) {
	// String update = m.group();
	// String id = update.substring(update.indexOf("[id]") + 4,
	// update.indexOf("[/id]"));
	// String ps = update.substring(update.indexOf("[ps]") + 4,
	// update.indexOf("[/ps]"));
	// String catalog = update.substring(update.indexOf("[catalog]") + 9,
	// update.indexOf("[/catalog]"));
	//
	// dao.deleteDataMapping(catalog, id);
	// DataMapping data = new DataMapping();
	// data.setCustno(custno);
	// data.setAction("update");
	// data.setCatalog(catalog);
	// data.setMpp(id);
	// data.setPs(ps);
	// dao.save(data);
	// }
	// str = str.replaceAll(regExp, "");
	// return str;
	// }

	/**
	 * process synchronization return, if it's a delete action in powersuite,
	 * the return value contains delete information.
	 * 
	 * @param custno
	 * @param str
	 * @return
	 */
	// private String processSync2(String custno, String str) {
	// if (str.indexOf("[delete]") == -1)
	// return str;
	//
	// String regExp = "\\[delete\\]\\S{1,}\\[/delete\\]";
	// Pattern p = Pattern.compile(regExp);
	// Matcher m = p.matcher(str);
	//
	// while (m.find()) {
	// String update = m.group();
	// String id = update.substring(update.indexOf("[id]") + 4,
	// update.indexOf("[/id]"));
	// // String ps = update.substring(update.indexOf("[ps]") + 4,
	// // update.indexOf("[/ps]"));
	// String catalog = update.substring(update.indexOf("[catalog]") + 9,
	// update.indexOf("[/catalog]"));
	//
	// dao.deleteDataMapping(catalog, id);
	// }
	// str = str.replaceAll(regExp, "");
	// return str;
	// }

	@SuppressWarnings({ "rawtypes", "static-access", "unchecked" })
	public String buildCompanyXML(String customerNo, boolean hasChild,
			boolean hasRelation, String systems) {
		StringBuffer buffer = new StringBuffer();
		// buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

		String barNo = this.getBarno(customerNo);
		CompanyPo companyPo = compdao.find(barNo);

		if (companyPo == null) {
			return "";
		}

		com.citsamex.vo.Company mpc = new com.citsamex.vo.Company();
		Map dataMap = dao.listDataMapping(companyPo.getClass().getName(),
				new String[] { String.valueOf(companyPo.getId()) });
		DataMapping entity = (DataMapping) dataMap.get(String.valueOf(companyPo
				.getId()));

		if (entity != null) {
			String primaryKey = entity.getPs();
			mpc.setPrimaryKey(primaryKey);
			mpc.setAction("update");
		} else {
			mpc.setPrimaryKey("CUSTNO=" + customerNo);
			mpc.setAction("create");
		}

		uti.copyProperties(mpc, companyPo);
		Map<Integer, String> data2Def = getData2Map();
		Iterator<Integer> it2 = data2Def.keySet().iterator();

		int sum = 0;
		while (it2.hasNext()) {
			Integer code = it2.next();
			String method = data2Def.get(code);

			Integer flag = (Integer) uti.getValue(mpc, method);
			if (flag == 1) {
				sum += code;
			}
		}
		mpc.setJobNumber(sum);

		List contacts = dao.listRelation("ContactPo", "C" + companyPo.getId());
		if (contacts.size() > 0) {
			ContactPo contact = (ContactPo) contacts.get(0);
			mpc.setTitle1(contact.getTitle());
			mpc.setMobile1(contact.getMobile());

			if (!isEmpty(contact.getOfficephone())
					&& contact.getOfficephone().indexOf("EXT.") != -1) {
				mpc.setOfficePhone1(contact.getOfficephone().substring(0,
						contact.getOfficephone().indexOf("EXT.")));
				mpc.setPager1(contact.getOfficephone().substring(
						contact.getOfficephone().indexOf("EXT.")));
			} else {
				mpc.setOfficePhone1(contact.getOfficephone());
				mpc.setPager1("");
			}

			mpc.setFaxNo1(contact.getFaxno());
			mpc.setEmail1(contact.getEmail());
			mpc.setContactfirstname1(contact.getFirstname());
			mpc.setContactname1(contact.getLastname());
		} else {
			mpc.setTitle1("");
			mpc.setMobile1("");
			mpc.setOfficePhone1("");
			mpc.setPager1("");
			mpc.setFaxNo1("");
			mpc.setEmail1("");
			mpc.setContactfirstname1("");
			mpc.setContactname1("");
		}

		if (contacts.size() > 1) {
			ContactPo contact = (ContactPo) contacts.get(1);
			mpc.setTitle2(contact.getTitle());
			mpc.setMobile2(contact.getMobile());

			if (!isEmpty(contact.getOfficephone())
					&& contact.getOfficephone().indexOf("EXT.") != -1) {
				mpc.setOfficePhone2(contact.getOfficephone().substring(0,
						contact.getOfficephone().indexOf("EXT.")));
				mpc.setPager2(contact.getOfficephone().substring(
						contact.getOfficephone().indexOf("EXT.")));
			} else {
				mpc.setOfficePhone2(contact.getOfficephone());
				mpc.setPager2("");
			}

			mpc.setFaxNo2(contact.getFaxno());
			mpc.setEmail2(contact.getEmail());
			mpc.setContactfirstname2(contact.getFirstname());
			mpc.setContactname2(contact.getLastname());
		} else {
			mpc.setTitle2("");
			mpc.setMobile2("");
			mpc.setOfficePhone2("");
			mpc.setPager2("");
			mpc.setFaxNo2("");
			mpc.setEmail2("");
			mpc.setContactfirstname2("");
			mpc.setContactname2("");
		}

		mpc.setSyncsystems(systems);
		buffer.append(mpc.toXML());

		Map<Integer, String> dataDef = getDataMap();

		Iterator<Integer> it = dataDef.keySet().iterator();
		while (it.hasNext()) {
			Integer code = it.next();
			String method = dataDef.get(code);
			Integer flag = (Integer) uti.getValue(mpc, method);

			Extra extra = new Extra();
			extra.setLevel("Billing");
			extra.setFieldName(String.valueOf(code));
			extra.setMandatory("1");
			extra.setIsactive("1");
			extra.setPrimaryKey("CUSTNO=" + customerNo
					+ ",DATA_LEVEL=2,DATA_ID=" + String.valueOf(code));

			if (flag == 1) {
				extra.setAction("update");
			} else {
				extra.setAction("delete");
			}
			buffer.append("<SEPERATOR/>");
			buffer.append(extra.toXML());

		}

		if (hasRelation) {
			String relationID = "C" + companyPo.getId();
			buffer.append(getRelationXML(customerNo,
					new String[] { relationID }));
		}

		buffer.append(getDelAction(companyPo.getCompanyNo()));

		if (hasChild) {
			Map map = new HashMap();
			map.put("companyNo", barNo);
			List<TravelerPo> list = dao.list(map);
			String[] rId = new String[list.size()];
			String[] travelerIDs = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				travelerIDs[i] = String.valueOf(list.get(i).getId());
			}
			if (travelerIDs.length != 0) {
				for (int i = 0; i < list.size(); i++) {
					TravelerPo po = list.get(i);
					String rid = "T" + po.getId();
					rId[i] = rid;

					com.citsamex.vo.Traveler mpt = new com.citsamex.vo.Traveler();
					uti.copyProperties(mpt, po);
					buffer.append("<SEPERATOR/>");
					buffer.append(mpt.toXML());
					buffer.append(getRelationXML(mpt.getTravelerNo(),
							new String[] { rid }));
				}

			}
		}
		return buffer.toString();
	}

	// get base xml when delete action.
	private String getDelAction(String custno) {
		StringBuffer buffer = new StringBuffer();
		Map<String, DataMapping> delActMap = dao.listDataMapping(custno,
				"delete");
		Iterator<String> it = delActMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			DataMapping data = delActMap.get(key);
			String catalog = data.getCatalog();
			String id = data.getMpp();
			buffer.append("<SEPERATOR/>");
			if (catalog.equals("com.citsamex.service.db.PreferencePo")) {
				String cat = "";
				PreferenceHisPo hPo = (PreferenceHisPo) dao.getLateHis(
						"PreferenceHisPo", Long.parseLong(id));
				if (hPo == null) {
					PreferencePo po = (PreferencePo) dao.get(
							PreferencePo.class, Long.parseLong(id));
					if (po == null) {
						this.dao.delete(data);
					} else {
						cat = po.getCatalog();
					}
				} else {
					cat = hPo.getCatalog();
				}

				if (cat.equalsIgnoreCase("Seat")
						|| cat.equalsIgnoreCase("Meal")) {
					com.citsamex.vo.Preference pref = new com.citsamex.vo.Preference();

					pref.setCatalog(hPo.getCatalog());
					pref.setValue1("");
					pref.setValue2("");
					pref.setPrimaryKey(data.getPs());
					pref.setId(Long.parseLong(id));
					pref.setAction("update");

					buffer.append(pref.toXML());
				} else {
					com.citsamex.vo.Preference2 base = new com.citsamex.vo.Preference2();
					base.setPrimaryKey(data.getPs());
					CommonUtil.setValue(base, "id", Long.parseLong(id));
					base.setAction(data.getAction());
					buffer.append(base.toXML());
				}

			} else if (catalog.equals("com.citsamex.service.db.AddressPo")) {
				String cat = "";
				AddressHisPo hPo = (AddressHisPo) dao.getLateHis(
						"AddressHisPo", Long.parseLong(id));
				if (hPo == null) {
					AddressPo po = (AddressPo) dao.get(AddressPo.class,
							Long.parseLong(id));
					if (po == null) {
						this.dao.delete(data);
					} else {
						cat = po.getCatalog();
					}
				} else {
					cat = hPo.getCatalog();
				}

				com.citsamex.vo.Address address = new com.citsamex.vo.Address();

				address.setCatalog(cat);
				address.setAddress("");
				address.setPrimaryKey(data.getPs());
				address.setId(Long.parseLong(id));
				address.setAction("update");

				buffer.append(address.toXML());
			} else {
				catalog = "com.citsamex.vo."
						+ catalog.substring(catalog.lastIndexOf(".") + 1,
								catalog.indexOf("Po"));
				com.citsamex.vo.Base base = (com.citsamex.vo.Base) CommonUtil
						.newInstance(catalog);
				base.setPrimaryKey(data.getPs());
				CommonUtil.setValue(base, "id", Long.parseLong(id));
				base.setAction(data.getAction());
				buffer.append(base.toXML());
			}
		}
		return buffer.toString();
	}

	@SuppressWarnings("rawtypes")
	public String buildTravelerXML(TravelerPo po, boolean hasRelation, String systems) {
		
		String customerNo = po.getTravelerNo();
		StringBuffer buffer = new StringBuffer();
		com.citsamex.vo.Traveler mpt = new com.citsamex.vo.Traveler();
		uti.copyProperties(mpt, po);
		mpt.setIgnoreMasterUpdate(po.isIgnoreMasterUpdate());

		CompanyPo companyPo = compdao.find(customerNo.substring(0, 6));
		String saleName = companyPo.getSales();
		mpt.setSales(saleName);
		mpt.setEbilling(companyPo.getEbilling());
		if (isEmpty(mpt.getGender())) {
			mpt.setGender("");
			mpt.setResTitle("");
		} else {
			if (mpt.getGender().equals("M")) {
				mpt.setResTitle("MR");
			} else {
				mpt.setResTitle("MS");
			}
		}

		boolean isEnglish = mpt.getLastname().equals(mpt.getLastnameEn());
		if (!isEmpty(mpt.getOfficePhone())) {
			if (mpt.getOfficePhone().lastIndexOf("EXT.") != -1) {
				String phone = mpt.getOfficePhone();
				mpt.setOfficePhone(phone.substring(0, phone.lastIndexOf("EXT.")));
				mpt.setPager(phone.substring(phone.lastIndexOf("EXT.")));
			}
		}

		if (isEnglish) {
			mpt.setTravelerNameCh("");
			mpt.setTravelerNameEn((isEmpty(mpt.getLastnameEn()) ? "" : mpt
					.getLastnameEn())
					+ " "
					+ (isEmpty(mpt.getFirstnameEn()) ? "" : mpt
							.getFirstnameEn()));
		} else {
			mpt.setTravelerNameCh((isEmpty(mpt.getLastname()) ? "" : mpt
					.getLastname())
					+ ""
					+ (isEmpty(mpt.getFirstname()) ? "" : mpt.getFirstname()));
			mpt.setTravelerNameEn((isEmpty(mpt.getLastnameEn()) ? "" : mpt
					.getLastnameEn())
					+ " "
					+ (isEmpty(mpt.getFirstnameEn()) ? "" : mpt
							.getFirstnameEn()));
		}

		if (!isEmpty(mpt.getDepartment2())) {
			String tmrStr = mpt.getDepartment2();
			mpt.setDivisionCode(tmrStr);
		} else {
			mpt.setDivisionCode("");
			mpt.setTmr1("");
			mpt.setTmr2("");
			mpt.setTmr3("");
			mpt.setTmr4("");
			mpt.setTmr5("");
		}

		Map dataMap = dao.listDataMapping(po.getClass().getName(),
				new String[] { String.valueOf(po.getId()) });
		DataMapping entity = (DataMapping) dataMap.get(String.valueOf(po
				.getId()));
		if (entity != null) {
			String primaryKey = entity.getPs();
			mpt.setPrimaryKey(primaryKey);
			mpt.setAction("update");
		} else {
			mpt.setPrimaryKey("CUSTNO=" + customerNo);
			mpt.setAction("create");
		}

		List contacts = dao.listRelation("ContactPo", "T" + po.getId());
		if (contacts.size() > 0) {
			ContactPo contact = (ContactPo) contacts.get(0);
			mpt.setTitle1(contact.getTitle());
			mpt.setMobile1(contact.getMobile());

			if (!isEmpty(contact.getOfficephone())
					&& contact.getOfficephone().lastIndexOf("EXT.") != -1) {
				mpt.setOfficePhone1(contact.getOfficephone().substring(0,
						contact.getOfficephone().lastIndexOf("EXT.")));
				mpt.setPager1(contact.getOfficephone().substring(
						contact.getOfficephone().lastIndexOf("EXT.")));
			} else {
				mpt.setOfficePhone1(contact.getOfficephone());
				mpt.setPager1("");
			}

			mpt.setFaxNo1(contact.getFaxno());
			mpt.setEmail1(contact.getEmail());
			mpt.setContactfirstname1(contact.getFirstname());
			mpt.setContactname1(contact.getLastname());
		} else {
			mpt.setTitle1("");
			mpt.setMobile1("");
			mpt.setOfficePhone1("");
			mpt.setPager1("");
			mpt.setFaxNo1("");
			mpt.setEmail1("");
			mpt.setContactfirstname1("");
			mpt.setContactname1("");
		}

		mpt.setSyncsystems(systems);
		buffer.append(mpt.toXML());
		// System.out.println(mpt.toJson());

		if (hasRelation) {
			String relationID = "T" + po.getId();
			buffer.append(getRelationXML(customerNo,
					new String[] { relationID }));
		}

		buffer.append(getDelAction(po.getTravelerNo()));

		return buffer.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public String buildTravelerXML(String customerNo, boolean hasRelation,
			String systems) {

		StringBuffer buffer = new StringBuffer();
		TravelerPo po = dao.find(customerNo);
		if (po == null) {
			return "";
		}else{
			return buildTravelerXML(po, hasRelation, systems);
		}

	}
	
	@SuppressWarnings("rawtypes")
	public String buildTravelerXML(String customerNo, boolean hasRelation,
			String systems, boolean updateAXOBarPathOnly) {

		StringBuffer buffer = new StringBuffer();
		TravelerPo po = dao.find(customerNo);

		if (po == null) {
			return "";
		} else{
			po.setIgnoreMasterUpdate(updateAXOBarPathOnly);
			return buildTravelerXML(po, hasRelation, systems);
		}

	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	private String getRelationXML(String custno, String relationID[]) {

		if (relationID.length == 0) {
			return "";
		}
		String[] relations = { "AddressPo", "ContactPo", "CreditCardPo",
				"DiscountPo", "EBillingPo", "MemberPo", "ExtraPo",
				"PreferencePo", "VisaPo" };
		String[] vos = { "Address", "Contact", "CreditCard", "Discount",
				"EBilling", "Member", "Extra", "Preference2", "Visa" };

		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < relations.length; i++) {
			List listCRela = dao.listRelation(relations[i], relationID);

			String[] datamapIds = new String[listCRela.size()];
			for (int j = 0; j < listCRela.size(); j++) {
				Object obj = listCRela.get(j);
				datamapIds[j] = CommonUtil.getValue(obj, "id").toString();
			}

			if (datamapIds.length == 0) {
				continue;
			}

			Map dataMap = dao.listDataMapping("com.citsamex.service.db."
					+ relations[i], datamapIds);

			for (int j = 0; j < listCRela.size(); j++) {
				Object obj = listCRela.get(j);

				com.citsamex.vo.Base mbase = null;
				if (relations[i].equals("PreferencePo")) {
					String category = (String) CommonUtil.getValue(obj,
							"catalog");

					if ("MEAL".equalsIgnoreCase(category)
							|| "SEAT".equalsIgnoreCase(category)) {
						mbase = (com.citsamex.vo.Base) uti
								.newInstance("com.citsamex.vo.Preference");
						String pref = (String) CommonUtil.getValue(obj,
								"value1");
						if (pref != null && pref.indexOf("/") != -1) {
							pref = pref.substring(0, pref.indexOf("/"));
							CommonUtil.setValue(obj, "value1", pref);
						}
					} else {
						mbase = (com.citsamex.vo.Base) uti
								.newInstance("com.citsamex.vo.Preference2");
					}
				} else {
					mbase = (com.citsamex.vo.Base) uti
							.newInstance("com.citsamex.vo." + vos[i]);
				}

				uti.copyProperties(mbase, obj);

				if (mbase instanceof com.citsamex.vo.Contact) {
					if (j == 0 && custno.length() == 10)
						continue;

					if ((j == 0 || j == 1) && custno.length() == 6)
						continue;

					String officephone = (String) CommonUtil.getValue(obj,
							"officephone");

					if (!isEmpty(officephone)
							&& officephone.indexOf("EXT.") != -1) {
						CommonUtil.setValue(
								mbase,
								"officephone",
								officephone.substring(0,
										officephone.indexOf("EXT.")));
						CommonUtil.setValue(mbase, "pager", officephone
								.substring(officephone.indexOf("EXT.")));
					}
				}

				if (mbase instanceof com.citsamex.vo.CreditCard) {

					String vendor = (String) CommonUtil.getValue(obj, "vendor");
					String institution = (String) CommonUtil.getValue(obj,
							"institution");
					CommonUtil.setValue(mbase, "vendor", vendor + "-"
							+ institution);
				}

				DataMapping entity = (DataMapping) dataMap.get(uti.getValue(
						obj, "id").toString());
				if (entity != null) {
					String primaryKey = entity.getPs();
					mbase.setPrimaryKey(primaryKey);
					mbase.setAction("update");
				} else {
					mbase.setPrimaryKey("CUSTNO=" + custno);
					mbase.setAction("create");
				}

				if (vos[i].equals("Address") || vos[i].equals("Preference")) {
					mbase.setAction("create");
				}

				buffer.append("<SEPERATOR/>");
				buffer.append(mbase.toXML());
				// System.out.println(mbase.toJson());
			}
		}
		return buffer.toString();
	}

	public ServiceMessage updateStatusRemark(Long id, String remark, String status) {
		TravelerPo po = (TravelerPo) dao.find(TravelerPo.class, id);
		po.setStatus(status);
		po.setStatusRemark(remark);
		if (!"ACTIVE".equals(status)) {
			po.setFeeBased(0);
		} else {
			po.setFeeBased(1);
		}
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_CUSTOMER_SUCCESS);
		sm.setData(po);

		final String travelerNo = po.getTravelerNo();
		final String travelerStatus = po.getStatus();
		final String travelerRemark = po.getStatusRemark();
		if (sm.isSuccess()) {
			
			AxoService axoService = new AxoServiceProxy();
			try{
			   axoService.syncStatus(travelerNo, travelerStatus, travelerRemark);
			}catch (RemoteException re){
				System.out.println("Update status for AXO failed " + travelerNo + "-" + travelerStatus);
				re.printStackTrace();
			}

			PsService service = new PsServiceProxy();
			try {
				service.syncStatus(travelerNo, travelerStatus, travelerRemark);
			} catch (RemoteException e) {
				System.out.println("Update status for PS failed " + travelerNo + "-" + travelerStatus);
				e.printStackTrace();
			}

			BsService service2 = new BsServiceProxy();
			try {
				service2.syncStatus(travelerNo, travelerStatus, travelerRemark);
			} catch (RemoteException e) {
				System.out.println("Update status for BS failed " + travelerNo + "-" + travelerStatus);
				e.printStackTrace();
			}
		}

		return sm;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private String processRelationXml(String className, String tagName,
			String relationId) {
		String xml = "";
		List list = dao.listRelation(className, relationId);
		for (int i = 0; i < list.size(); i++) {
			xml += ProfileBuilder.transform(tagName, list.get(i),
					new String[] {}, new String[] { "relationId" });
			xml += "</" + tagName + ">";
		}
		return xml;
	}

	@SuppressWarnings("static-access")
	public String loadPar(String groupname, String parno) {

		if (dao.find(parno) != null)
			return "Traveler exists in MPP.";

		CompanyPo company = compdao.find(getBarno(parno));
		long companyid = 0;
		if (company == null) {
			companyid = loadBar(groupname, getBarno(parno));
		} else {
			companyid = company.getId();
		}

		PsService service = new PsServiceProxy();
		String xml = "";
		try {
			xml = service.getPar(parno);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (!xml.equals("")) {
			List<com.citsamex.vo.Base> list = xml2Java(xml);

			com.citsamex.vo.Traveler traveler = null;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof com.citsamex.vo.Traveler) {
					traveler = (com.citsamex.vo.Traveler) list.get(i);
					traveler.setCompanyId(companyid);
					traveler.setCompanyName(groupname);
					traveler.setCreateuserno(getCurrentUser());
					traveler.setCreatedatetime(Calendar.getInstance());
					traveler.setUpdatedatetime(Calendar.getInstance());
					traveler.setUpdateuserno(getCurrentUser());
					break;
				}
			}
			if (traveler == null) {
				return "Traveler does not exist in PowerSuite.";
			}

			// if (!traveler.getStatus().equals("ACTIVE")) {
			// return "Traveler is inactive, which will not be import to mpp.";
			// }

			if (!isEmpty(traveler.getPager())) {
				traveler.setOfficePhone((isEmpty(traveler.getOfficePhone()) ? ""
						: (traveler.getOfficePhone() + ""))
						+ traveler.getPager());
			}

			if (!isEmpty(traveler.getDivisionCode())) {
				String tmr1 = traveler.getTmr1();
				String tmr2 = traveler.getTmr2();
				String tmr3 = traveler.getTmr3();
				String tmr4 = traveler.getTmr4();
				String tmr5 = traveler.getTmr5();

				// traveler.setDepartment2(traveler.getDivisionCode() + ":" +
				// tmr1 + "/" + tmr2 + "/" + tmr3 + "/" + tmr4
				// + "/" + tmr5 + "/");

				traveler.setDepartment2(traveler.getDivisionCode());
			}

			TravelerPo po = new TravelerPo();
			uti.copyProperties(po, traveler);

			po.setTravelerType("traveler");

			if (!isEmpty(traveler.getTravelerNameEn())) {
				if (traveler.getTravelerNameEn().indexOf(" ") != -1) {
					po.setLastnameEn(traveler.getTravelerNameEn().substring(0,
							traveler.getTravelerNameEn().indexOf(" ")));
					po.setFirstnameEn(traveler.getTravelerNameEn().substring(
							traveler.getTravelerNameEn().indexOf(" ") + 1));
				} else {
					po.setLastnameEn(traveler.getTravelerNameEn());
					po.setFirstnameEn("");
				}
			}

			if (!isEmpty(traveler.getLastname())) {
				po.setLastname(traveler.getLastname());
			} else {
				po.setLastname(po.getLastname());
			}

			if (!isEmpty(traveler.getFirstname())) {
				po.setFirstname(traveler.getFirstname());
			} else {
				po.setFirstname(po.getFirstname());
			}

			po.setTravelerName(po.getLastname() + " " + po.getFirstname());
			dao.save(po);

			if (!isEmpty(traveler.getTitle1())
					|| !isEmpty(traveler.getMobile1())
					|| !isEmpty(traveler.getOfficePhone1())
					|| !isEmpty(traveler.getPager1())
					|| !isEmpty(traveler.getFaxNo1())
					|| !isEmpty(traveler.getEmail1())
					|| !isEmpty(traveler.getContactfirstname1())
					|| !isEmpty(traveler.getContactname1())) {
				ContactPo contact = new ContactPo();
				contact.setTitle(traveler.getTitle1());
				contact.setMobile(traveler.getMobile1());
				contact.setOfficephone(traveler.getOfficePhone1()
						+ (isEmpty(traveler.getPager1()) ? "" : ("" + traveler
								.getPager1())));
				contact.setFaxno(traveler.getFaxNo1());
				contact.setEmail(traveler.getEmail1());
				contact.setFirstname(traveler.getContactfirstname1());
				contact.setLastname(traveler.getContactname1());
				contact.setRelationId("T" + po.getId());
				dao.save(contact);
			} else {
				ContactPo contact = new ContactPo();
				contact.setTitle("");
				contact.setMobile("");
				contact.setOfficephone("");
				contact.setFaxno("");
				contact.setEmail("");
				contact.setFirstname("");
				contact.setLastname("");
				contact.setRelationId("T" + po.getId());
				dao.save(contact);
			}

			DataMapping mapping = new DataMapping();
			mapping.setCustno(po.getTravelerNo());
			mapping.setAction("update");
			mapping.setCatalog(po.getClass().getName());
			mapping.setMpp("" + po.getId());
			mapping.setPs(traveler.getPrimaryKey());
			dao.save(mapping);

			String relationId = "T" + po.getId();
			for (int i = 0; i < list.size(); i++) {
				processBase(po.getTravelerNo(), list.get(i), relationId);
			}

			// get dependant.

			try {
				xml = service.getDependant(parno);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (!xml.equals("")) {
				list = xml2Java(xml);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) instanceof com.citsamex.vo.Traveler) {
						com.citsamex.vo.Traveler trav = (com.citsamex.vo.Traveler) list
								.get(i);

						TravelerPo travPo = new TravelerPo();
						uti.copyProperties(travPo, trav);
						TravelerPo masterTravPo = dao.find(parno);
						travPo.setCompanyId(masterTravPo.getCompanyId());
						travPo.setCompanyName(masterTravPo.getCompanyName());
						travPo.setCreateuserno(Server.getCurrentUsername());
						travPo.setCreatedatetime(Calendar.getInstance());
						travPo.setStatus("ACTIVE");
						travPo.setTravelerName(travPo.getLastname() + " "
								+ travPo.getFirstname());
						dao.save(travPo);

					} else if (list.get(i) instanceof com.citsamex.vo.Visa) {
						com.citsamex.vo.Visa vis = (com.citsamex.vo.Visa) list
								.get(i);
						VisaPo visaPo = new VisaPo();
						uti.copyProperties(visaPo, vis);
						visaPo.setCreateuserno(Server.getCurrentUsername());
						visaPo.setCreatedatetime(Calendar.getInstance());
						String travelerNo = visaPo.getRelationId();
						TravelerPo familyTrav = dao.find(travelerNo);
						if (familyTrav == null) {
							continue;
						}
						visaPo.setRelationId("T" + familyTrav.getId());
						dao.save(visaPo);
					} else if (list.get(i) instanceof com.citsamex.vo.Member) {
						com.citsamex.vo.Member mb = (com.citsamex.vo.Member) list
								.get(i);
						MemberPo memPo = new MemberPo();
						uti.copyProperties(memPo, mb);
						memPo.setCreateuserno(Server.getCurrentUsername());
						memPo.setCreatedatetime(Calendar.getInstance());
						String travelerNo = memPo.getRelationId();
						TravelerPo familyTrav = dao.find(travelerNo);
						if (familyTrav == null) {
							continue;
						}
						memPo.setRelationId("T" + familyTrav.getId());
						dao.save(memPo);
					}
				}
			}
			return "success";
		}

		return "Traveler does not exist in PowerSuite.";
	}

	private void loadDependant(String parno) {

	}

	private List<com.citsamex.vo.Base> xml2Java(String xml) {

		List<com.citsamex.vo.Base> list = new ArrayList<com.citsamex.vo.Base>();
		String xmls[] = xml.split("<SEPERATOR/>");
		for (int i = 0; i < xmls.length; i++) {
			if (isEmpty(xmls[i]))
				continue;
			XMLDecoder decoder = null;
			try {
				decoder = new XMLDecoder(new ByteArrayInputStream(
						xmls[i].getBytes("UTF-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			com.citsamex.vo.Base base = (com.citsamex.vo.Base) decoder
					.readObject();
			list.add(base);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	private void processBase(String custno, com.citsamex.vo.Base base,
			String relationId) {

		Object po = null;
		if (base instanceof com.citsamex.vo.Traveler) {
			return;
		} else if (base instanceof com.citsamex.vo.Company) {
			return;
		} else if (base instanceof com.citsamex.vo.Extra) {

			Map<Integer, String> dataDef = getDataMap();

			String dataLevel = ((com.citsamex.vo.Extra) base).getLevel();

			String dataId = ((com.citsamex.vo.Extra) base).getFieldName();
			int iDataId = Integer.parseInt(dataId);

			if (dataLevel.equals("Billing")) {
				String property = dataDef.get(iDataId);
				CompanyPo company = compdao.find(getBarno(custno));
				CommonUtil.setValue(company, property, 1);
				dao.update(company);
				return;
			}
			po = new ExtraPo();
		} else if (base instanceof com.citsamex.vo.Preference2) {
			po = new PreferencePo();
		} else {
			po = CommonUtil.newInstance("com.citsamex.service.db."
					+ base.getSingleClassname() + "Po");
		}

		uti.copyProperties(po, base);
		try {
			po.getClass()
					.getMethod("setRelationId",
							new Class[] { java.lang.String.class })
					.invoke(po, new Object[] { relationId });
			po.getClass()
					.getMethod("setCreateuserno",
							new Class[] { java.lang.String.class })
					.invoke(po, new Object[] { getCurrentUser() });
			po.getClass()
					.getMethod("setUpdateuserno",
							new Class[] { java.lang.String.class })
					.invoke(po, new Object[] { getCurrentUser() });
			po.getClass()
					.getMethod("setCreatedatetime",
							new Class[] { Calendar.class })
					.invoke(po, new Object[] { Calendar.getInstance() });
			po.getClass()
					.getMethod("setUpdatedatetime",
							new Class[] { Calendar.class })
					.invoke(po, new Object[] { Calendar.getInstance() });

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (po instanceof FeeCodeDetailPo) {
			String period = (String) CommonUtil.getValue(po, "validPeriod");
			String groupId = (String) CommonUtil.getValue(po, "groupId");
			List list = dao.listFeeCodeDetail(null, null, period, groupId);
			if (list.size() != 0) {
				return;
			}
		}

		dao.save(po);

		DataMapping mapping = new DataMapping();
		mapping.setCatalog(po.getClass().getName());
		mapping.setCustno(custno);
		mapping.setAction("update");
		try {
			mapping.setMpp(""
					+ po.getClass().getMethod("getId", new Class[] {})
							.invoke(po, new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mapping.setPs(base.getPrimaryKey());

		dao.save(mapping);
	}

	private String getBarno(String parno) {
		return parno.substring(0, 6);
	}

	@SuppressWarnings("static-access")
	private long loadBar(String groupname, String barno) {
		CustomerPo customer = custdao.find(groupname);

		if (customer == null) {
			customer = new CustomerPo();
			customer.setName(groupname);
			customer.setStatus("ACTIVE");
			custdao.save(customer);

		}

		PsService service = new PsServiceProxy();
		String xml = "";
		try {
			xml = service.getBar(barno);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (!xml.equals("")) {
			List<com.citsamex.vo.Base> list = xml2Java(xml);

			com.citsamex.vo.Company company = null;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof com.citsamex.vo.Company) {
					company = (com.citsamex.vo.Company) list.get(i);
					company.setCustId(customer.getId());
					company.setCreateuserno(getCurrentUser());
					company.setCreatedatetime(Calendar.getInstance());
					company.setUpdatedatetime(Calendar.getInstance());
					company.setUpdateuserno(getCurrentUser());
					break;
				}
			}

			if (company == null) {
				return 0;
			}

			CompanyPo po = new CompanyPo();
			uti.copyProperties(po, company);

			Integer sum = company.getJobNumber();

			Map<Integer, String> data2Def = getData2Map();
			Iterator<Integer> it2 = data2Def.keySet().iterator();

			while (it2.hasNext()) {
				Integer code = it2.next();
				String method = data2Def.get(code);

				if ((code & sum) != 0) {
					uti.setValue(po, method, 1);
				}
			}

			po.setPolicy("");
			dao.save(po);

			if (!isEmpty(company.getTitle1()) || !isEmpty(company.getMobile1())
					|| !isEmpty(company.getOfficePhone1())
					|| !isEmpty(company.getPager1())
					|| !isEmpty(company.getFaxNo1())
					|| !isEmpty(company.getEmail1())
					|| !isEmpty(company.getContactfirstname1())
					|| !isEmpty(company.getContactname1())) {
				ContactPo contact = new ContactPo();
				contact.setTitle(company.getTitle1());
				contact.setMobile(company.getMobile1());
				contact.setOfficephone(company.getOfficePhone1()
						+ (isEmpty(company.getPager1()) ? "" : ("-" + company
								.getPager1())));
				contact.setFaxno(company.getFaxNo1());
				contact.setEmail(company.getEmail1());
				contact.setFirstname(company.getContactfirstname1());
				contact.setLastname(company.getContactname1());
				contact.setRelationId("C" + po.getId());
				dao.save(contact);
			} else {
				ContactPo contact = new ContactPo();
				contact.setTitle("");
				contact.setMobile("");
				contact.setOfficephone("");
				contact.setFaxno("");
				contact.setEmail("");
				contact.setFirstname("");
				contact.setLastname("");
				contact.setRelationId("C" + po.getId());
				dao.save(contact);
			}

			if (!isEmpty(company.getTitle2()) || !isEmpty(company.getMobile2())
					|| !isEmpty(company.getOfficePhone2())
					|| !isEmpty(company.getPager2())
					|| !isEmpty(company.getFaxNo2())
					|| !isEmpty(company.getEmail2())
					|| !isEmpty(company.getContactfirstname2())
					|| !isEmpty(company.getContactname2())) {
				ContactPo contact = new ContactPo();
				contact.setTitle(company.getTitle2());
				contact.setMobile(company.getMobile2());
				contact.setOfficephone(company.getOfficePhone2()
						+ (isEmpty(company.getPager2()) ? "" : ("-" + company
								.getPager2())));
				contact.setFaxno(company.getFaxNo2());
				contact.setEmail(company.getEmail2());
				contact.setFirstname(company.getContactfirstname2());
				contact.setLastname(company.getContactname2());
				contact.setRelationId("C" + po.getId());
				dao.save(contact);

			} else {
				ContactPo contact = new ContactPo();
				contact.setTitle("");
				contact.setMobile("");
				contact.setOfficephone("");
				contact.setFaxno("");
				contact.setEmail("");
				contact.setFirstname("");
				contact.setLastname("");
				contact.setRelationId("C" + po.getId());
				dao.save(contact);
			}

			DataMapping mapping = new DataMapping();
			mapping.setCustno(po.getCompanyNo());
			mapping.setAction("update");
			mapping.setCatalog(po.getClass().getName());
			mapping.setMpp("" + po.getId());
			mapping.setPs(company.getPrimaryKey());
			dao.save(mapping);

			String relationId = "C" + po.getId();
			for (int i = 0; i < list.size(); i++) {
				processBase(po.getCompanyNo(), list.get(i), relationId);
			}

			return po.getId();
		}
		return 0;
	}

	public void upload(File files) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Workbook book = null;
		try {
			book = Workbook.getWorkbook(files);
			Sheet[] sheet = book.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				sheetNo = i + 1;
				procSheet(sheet[i], map);
			}
		} finally {
			if (book != null)
				book.close();
		}
	}

	public String checkUpload(String filename, String mappings)
			throws Exception {

		StringBuffer message = new StringBuffer();
		String[] fields = mappings.split(",");

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String serverDir = ServiceConfig.getProperty("UPLOAD_DIR");
		File file = new File(serverDir + filename);
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);

			for (int i = 1; i < sheet.getRows(); i++) {
			    System.out.println("row-->" + i);
				Map map = createUploadMap();
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					String value = null;
					if (cell.getType().equals(CellType.DATE)) {
						DateCell c1 = (DateCell) cell;
						try {
							value = format.format(c1.getDate());
						} catch (Exception ex) {
							value = "";
						}
					} else {
						value = cell.getContents();
						if (value != null) {
						    value = value.trim();
						}
					}

					if (fields.length <= j) {
						break;
					}

					if (fields[j].indexOf("_") == -1) {
						continue;
					}

					if ("[EMPTY]".equals(value)) {
						continue;
					}

					String objName = fields[j].substring(0,
							fields[j].indexOf("_"));
					String fieldName = fields[j].substring(fields[j]
							.indexOf("_") + 1);

					if (map.containsKey(objName)) {
						Object po = map.get(objName);

						uti.setValue(po, fieldName, value);
						uti.setValue(po, "relationId", "DIRTY");
					}
					// column end.
				}

				// process current row.
				TravelerPo travPo = (TravelerPo) map.get("traveler");
				
				// determine if has parNo.
				if (!isEmpty(travPo.getTravelerNo())) {
					CompanyPo compPo = compdao.find(travPo.getTravelerNo()
							.substring(0, 6));
					if (compPo == null) {
						message.append("<DIV>Company " + travPo.getTravelerNo()
								+ " does not exist.</DIV>");
					}
					
					//add by fans.fan travelerNo不等于10,代表是新增的数据.
					if (travPo.getTravelerNo().length() != 10) {
						TravelerPo tempPo = dao.findTravelerByICDCOrPassport(travPo.getNationality1(),travPo.getFirstnameEn(),travPo.getLastnameEn(),travPo.getIcidNo(), travPo.getPassport1(), travPo.getPassport2(), travPo.getOtherCard());
						if (tempPo != null) {
							message.append("<DIV>Row:" + (i + 1) + ", 存在相同证件号的par信息"+tempPo.getTravelerNo()+",请确认后再上传!" 
									+ "</DIV>");
							continue;
						}
					}
					//end by fans.fan.

					boolean isNew = false;
					String relationId = "T";
					if (travPo.getTravelerNo().length() == 6) {
						String travelerNo = getNewParNo(travPo.getTravelerNo());
						if (!isEmpty(travelerNo)) {
							travPo.setTravelerNo(travelerNo);
							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
						}

						String msg = isvalid(travPo);
						if (!isEmpty(msg))
							message.append("<DIV>Row:" + (i + 1) + ", " + msg
									+ "</DIV>");
						isNew = true;
						relationId += travPo.getId();
					} else if (travPo.getTravelerNo().length() == 10) {
						TravelerPo oldTravPo = dao.find(travPo.getTravelerNo());
						if (oldTravPo == null) {
							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
							String msg = isvalid(travPo);
							if (!isEmpty(msg))
								message.append("<DIV>Row:" + (i + 1) + ", "
										+ msg + "</DIV>");
							isNew = true;
							relationId += travPo.getId();
						} else {
							TravelerPo tmpPo = new TravelerPo();
							uti.copyProperties(tmpPo, oldTravPo);

							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
							uti.copyProperties(tmpPo, travPo, true);

							String msg = isvalid(tmpPo);
							if (!isEmpty(msg))
								message.append("<DIV>Row:" + (i + 1) + ", "
										+ msg + "</DIV>");
						}
					} else {
						message.append("<DIV>Row:" + (i + 1)
								+ ", ParNo 无效</DIV>");
					}

					if (isNew) {
						Iterator<String> it = map.keySet().iterator();
						while (it.hasNext()) {
							String key = it.next();
							if (key.equals("traveler")) {
								continue;
							}

							Object relaObj = map.get(key);

							String dirty = (String) uti.getValue(relaObj,
									"relationId");
							if ("DIRTY".equals(dirty)) {
								uti.setValue(relaObj, "relationId", relationId);
								uti.setValue(relaObj, "createuserno", "upload");
								uti.setValue(relaObj, "updateuserno", "upload");
								Calendar now = Calendar.getInstance();
								uti.setValue(relaObj, "createdatetime", now);
								uti.setValue(relaObj, "updatedatetime", now);

								String msg = isvalid(relaObj);
								if (!isEmpty(msg))
									message.append("<DIV>Row:" + (i + 1) + ", "
											+ msg + "</DIV>");
							}
						}
					}

				} else {
					message.append("<DIV>Row:" + (i + 1)
							+ ", PAR No could not be empty.</DIV>");
				}

				// row end.
			}
		} finally {
			if (book != null)
				book.close();
		}
		return message.toString();
	}

	// to validate if object is ok.
	private String isvalid(Object object) {

		StringBuffer buffer = new StringBuffer();

		ValidatorManager validatorManager = (ValidatorManager) SpringUtil
				.getBean("validatorManager");

		Field[] fields = object.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Validate.class)) {
				String vname = fields[i].getAnnotation(Validate.class).value();
				Object value = CommonUtil.getValue(object, fields[i].getName());

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

		return buffer.toString();
	}

	public String processUpload(Integer taskid, String filename, String mappings)
			throws Exception {

		StringBuffer message = new StringBuffer();
		String[] fields = mappings.split(",");

		String serverDir = ServiceConfig.getProperty("UPLOAD_DIR");
		File file = new File(serverDir + filename);
		Workbook book = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd   HH:mm ");

		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);

			// show processbar.
			int countOfPar = sheet.getRows();

			// company addr mapping
			Map addrMap = new HashMap();
			for (int i = 1; i < sheet.getRows(); i++) {

				double a = i;
				double b = countOfPar;
				double taskStatus = a / b * 100;
				Server.setTaskStatus(taskid, (int) taskStatus);

				Map map = createUploadMap();
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					String value = null;
					if (cell.getType().equals(CellType.DATE)) {

						DateCell c1 = (DateCell) cell;
						try {
							value = format.format(c1.getDate());
						} catch (Exception ex) {
							value = "";
						}

					} else {
						value = cell.getContents();
						if (value != null) {
						    value = value.trim();
						}
					}

					if (fields.length <= j) {
						break;
					}

					if (fields[j].indexOf("_") == -1) {
						continue;
					}

					if ("[EMPTY]".equals(value)) {
						continue;
					}

					String objName = fields[j].substring(0,
							fields[j].indexOf("_"));
					String fieldName = fields[j].substring(fields[j]
							.indexOf("_") + 1);

					if (map.containsKey(objName)) {
						Object po = map.get(objName);

						uti.setValue(po, fieldName, value);
						uti.setValue(po, "relationId", "DIRTY");
					}
					// column end.
				}

				// process current row.
				TravelerPo travPo = (TravelerPo) map.get("traveler");
				Logger.getRootLogger().info("ready to deal upload: " +travPo.getTravelerName() + "\t" + travPo.getEmail());
				String parNo = "";
				// determine if has parNo.
				if (!isEmpty(travPo.getTravelerNo())) {
					CompanyPo compPo = compdao.find(travPo.getTravelerNo()
							.substring(0, 6));
					if (compPo == null) {
						message.append("<DIV>Company " + travPo.getTravelerNo()
								+ " does not exist.</DIV>");
					}
					
					boolean isNew = false;
					String relationId = "T";
					if (travPo.getTravelerNo().length() == 6) {
						String travelerNo = getNewParNo(travPo.getTravelerNo());
						if (!isEmpty(travelerNo)) {
							travPo.setTravelerNo(travelerNo);
							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
						}

						dao.save(travPo);

						// auto get addrs for traveler
						
                        List list2 = null;
                        list2 = (List) addrMap.get(compPo.getId() + "");
                        if (list2 == null) {
                            list2 = (List) compdao.listAddress("C"
                                    + compPo.getId());
                            addrMap.put(compPo.getId() + "", list2);
                        }
                        for (int j = 0; j < list2.size(); j++) {
                            AddressPo po = (AddressPo) list2.get(j);
                            if (po.getCatalog().toUpperCase()
                                    .equals("STATEMENT")) {
                                continue;
                            }
                            createAddress(po.getCatalog(), po.getAddress(),
                                    po.getCity(), po.getZipCode(),
                                    travPo.getId());
                        }
						isNew = true;
						relationId += travPo.getId();
						parNo = travPo.getTravelerNo();
					} else if (travPo.getTravelerNo().length() == 10) {
						TravelerPo oldTravPo = dao.find(travPo.getTravelerNo());
						if (oldTravPo == null) {
							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
							dao.save(travPo);
							//System.out.println("new traveler sync to ps..." + travPo.getTravelerNo());
							//auto add contact title for par from bar
							// auto add mailling delivery bill addr
							travPo = dao.find(travPo.getTravelerNo());
							// copy address from company to traveler
							List list2 = null;
							list2 = (List) addrMap.get(compPo.getId()+"");
							if (list2 == null) {
								list2 = (List) compdao.listAddress("C" + compPo.getId());
								addrMap.put(compPo.getId()+"", list2);
							}
							for (int j = 0; j < list2.size(); j++) {
							    AddressPo address1  = (AddressPo) map.get("address1");
							    if (address1.getCatalog() != null && !address1.getCatalog().equals("")) {
							        break;
							    }
								AddressPo po = (AddressPo) list2.get(j);
								if (po.getCatalog().toUpperCase().equals("STATEMENT")) {
									continue;
								}
								createAddress(po.getCatalog(), po.getAddress(),
										po.getCity(), po.getZipCode(), travPo.getId());
								//System.out.println(po.getCatalog() + po.getAddress() + travPo.getId());
							}
							
							isNew = true;
							relationId += travPo.getId();
							parNo = travPo.getTravelerNo();
						} else {

							travPo.setCompanyId(compPo.getId());
							travPo.setCompanyName(compPo.getCompanyName());
							travPo.setEbilling(compPo.getEbilling());
							if (isEmpty(travPo.getStatus())) {
								travPo.setStatus("ACTIVE");
							}
							defaultUploadTrav(travPo);
							uti.copyProperties(oldTravPo, travPo, true);
							dao.update(oldTravPo);
							
							List addrs = (List) compdao.listAddress("T" + oldTravPo.getId());
							if (addrs != null && addrs.size() > 0) {
								
							} else {
								// add bar company address to traveler
								List travAddr =  (List) addrMap.get(compPo.getId()+"");
								if (travAddr == null) {
									travAddr = (List) compdao.listAddress("C" + compPo.getId());
									addrMap.put(compPo.getId()+"", travAddr);
								}
								for (int k = 0; k < travAddr.size(); k++) {
									AddressPo po = (AddressPo) travAddr.get(k);
									if (po.getCatalog().toUpperCase().equals("STATEMENT")) {
										continue;
									}
									createAddress(po.getCatalog(), po.getAddress(),
											po.getCity(), po.getZipCode(), oldTravPo.getId());
								}
							}
							
							parNo = oldTravPo.getTravelerNo();

						}
					} else {
						message.append("<DIV>Row:" + (i + 1)
								+ ", ParNo 无效:"+travPo.getTravelerNo()+"</DIV>");
					}

					// update relation objects.
					if (isNew) {
						Iterator<String> it = map.keySet().iterator();
						while (it.hasNext()) {
							String key = it.next();
							if (key.equals("traveler")) {
								continue;
							}

							Object relaObj = map.get(key);

							String dirty = (String) uti.getValue(relaObj,
									"relationId");
							if ("DIRTY".equals(dirty)) {
								uti.setValue(relaObj, "relationId", relationId);
								uti.setValue(relaObj, "createuserno", "upload");
								uti.setValue(relaObj, "updateuserno", "upload");
								Calendar now = Calendar.getInstance();
								uti.setValue(relaObj, "createdatetime", now);
								uti.setValue(relaObj, "updatedatetime", now);
								dao.save(relaObj);
							}
						}
					}

				} else {
					message.append("<DIV>Row:" + (i + 1)
							+ ", PAR No could not be empty.</DIV>");
				}

				// this.synchronize(parNo, 0, 1);
				// row end.
				if (i % 10 == 0) {
					System.out.println((i/20) + " times flush session !!!");
					dao.getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
				}
			}
		} catch (Exception e) {
			Logger.getRootLogger().warn(message.toString(), e);
			message.append("<DIV>Error occur when upload file.</DIV>");
		} finally {
			if (book != null)
				book.close();
		}
		Logger.getRootLogger().warn(message.toString());
		return message.toString();
	}

	private TravelerPo defaultUploadTrav(TravelerPo travPo) {
		travPo.setCustType("Commercial");
		if ("ACTIVE".equals(travPo.getStatus()))
			travPo.setFeeBased(1);
		else {
			travPo.setFeeBased(0);
		}
		travPo.setReqStatement(1);

		Calendar now = Calendar.getInstance();
		travPo.setCreatedatetime(now);
		travPo.setUpdatedatetime(now);
		travPo.setCreateuserno("upload");
		travPo.setUpdateuserno("upload");
		travPo.setTravelerType("traveler");
		return travPo;
	}

	private String getNewParNo(String barNo) {
		String newParNo = null;
		try {
			PsService psService = new PsServiceProxy();
			newParNo = psService.getNewParNo(barNo);
		} catch (RemoteException e) {
			e.printStackTrace();
			newParNo = null;
		}

		String maxTravNoInMpp = dao.getMaxTravelerNo(barNo);
		if (maxTravNoInMpp != null) {
			try {
				Integer mppNo = Integer.parseInt(maxTravNoInMpp
						.substring(6, 10));
				Integer psNo = Integer.parseInt(newParNo);
				if (mppNo >= psNo) {
					newParNo = String.valueOf(mppNo + 1);
				} else {
					newParNo = String.valueOf(psNo);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				newParNo = null;
			}
		}

		if (newParNo != null) {
			switch (newParNo.length()) {
			case 1:
				newParNo = barNo + "000" + newParNo;
				break;
			case 2:
				newParNo = barNo + "00" + newParNo;
				break;
			case 3:
				newParNo = barNo + "0" + newParNo;
				break;
			case 4:
				newParNo = barNo + newParNo;
				break;
			}
		}

		return newParNo;
	}

	/**
	 * 创建一定数量的PO对象，和上传页面上的字段对应后，更新这些对象。
	 * 如：地址有4个，上传的excel中只有一个地址，那么address1被设置，其他3个address的不会更新。
	 * 
	 */
	private Map createUploadMap() {
		HashMap map = new HashMap();

		TravelerPo traveler = new TravelerPo();
		map.put("traveler", traveler);

		for (int i = 1; i <= 4; i++) {
			AddressPo address = new AddressPo();
			map.put("address" + i, address);
		}

		for (int i = 1; i <= 10; i++) {
			ContactPo contact = new ContactPo();
			map.put("contact" + i, contact);
		}

		for (int i = 1; i <= 5; i++) {
			VisaPo visa = new VisaPo();
			map.put("visa" + i, visa);
		}

		for (int i = 1; i <= 20; i++) {
			PreferencePo preference = new PreferencePo();
			map.put("preference" + i, preference);
		}

		for (int i = 1; i <= 20; i++) {
			ExtraPo extra = new ExtraPo();
			map.put("extra" + i, extra);
		}

		for (int i = 1; i <= 20; i++) {
			MemberPo member = new MemberPo();
			map.put("member" + i, member);
		}

		for (int i = 1; i <= 10; i++) {
			CreditCardPo creditcard = new CreditCardPo();
			map.put("creditcard" + i, creditcard);
		}

		return map;
	}

	public String[] preProcessUpload(File file) throws Exception {

		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);

			String[] fieldname = new String[sheet.getColumns()];
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell cell = sheet.getCell(i, 0);
				fieldname[i] = cell.getContents();
			}
			return fieldname;
		} finally {
			if (book != null)
				book.close();
		}
	}

	private void procSheet(Sheet sheet, Map<String, Object> map)
			throws Exception {

		String[] fieldname = new String[sheet.getColumns()];
		String[] fieldvalue = new String[sheet.getColumns()];

		String objname = sheet.getName();

		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell cell = sheet.getCell(i, 0);
			fieldname[i] = cell.getContents();
		}

		CommonUtil uti = new CommonUtil();
		for (int i = 1; i < sheet.getRows(); i++) {
			Object obj = initPo(objname);
			excelCurY = i;
			for (int j = 0; j < sheet.getColumns(); j++) {
				excelCurX = j;
				Cell cell = sheet.getCell(j, i);
				fieldvalue[j] = cell.getContents();

				Field field = null;
				try {
					field = obj.getClass().getDeclaredField(fieldname[j]);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					errorMessage += "\nUnknown Upload Format.";
				}
				// if field is id and integer, to update. Or create.
				if (field.getType().getName().toLowerCase().equals("long")) {
					if (this.isEmpty(fieldvalue[j])) {
						fieldvalue[j] = "0";
					}
					CommonUtil.setValue(obj, fieldname[j],
							Long.parseLong(fieldvalue[j]));
				} else if (field.getType().getName().toLowerCase()
						.equals("int")
						|| field.getType().getName().toLowerCase()
								.equals("integer")) {
					if (this.isEmpty(fieldvalue[j])) {
						fieldvalue[j] = "0";
					}
					CommonUtil.setValue(obj, fieldname[j],
							Integer.parseInt(fieldvalue[j]));
				} else if (field.getType() == Calendar.class) {
					CommonUtil.setValue(obj, fieldname[j],
							uti.parseDate(fieldvalue[j]));
				} else {
					CommonUtil.setValue(obj, fieldname[j], fieldvalue[j]);
				}
			}

			CommonUtil.setValue(obj, "updateuserno",
					Server.getCurrentUsername());
			CommonUtil.setValue(obj, "updatedatetime", Calendar.getInstance());

			if (obj instanceof TravelerPo) {
				map.put(((TravelerPo) obj).getTravelerNo(), obj);
				String barno = ((TravelerPo) obj).getTravelerNo().substring(0,
						6);
				CompanyPo company = (CompanyPo) map.get(barno);
				if (company == null) {
					company = compdao.find(barno);
				}
				if (company == null) {
					throw new Exception("target '" + barno
							+ "' does not exist.");
				}
				((TravelerPo) obj).setCompanyId(company.getId());
				((TravelerPo) obj).setCompanyName(company.getAlias());
			} else if (obj instanceof CompanyPo) {
				map.put(((CompanyPo) obj).getCompanyNo(), obj);
			}

			setRelationId(obj, map);

			Object po = null;
			if (obj instanceof TravelerPo) {
				po = dao.find((String) CommonUtil.getValue(obj, "travelerNo"));
				if (po == null) {
					dao.save(obj);
				} else {
					uti.copyProperties(po, obj);
					dao.update(po);
				}
			} else if (obj instanceof CompanyPo) {
				po = compdao.find((String) CommonUtil
						.getValue(obj, "companyNo"));
				if (po == null) {
					String custname = CommonUtil.getValue(obj, "alias")
							.toString();
					CustomerPo cust = custdao.find(custname);
					if (cust == null) {
						cust = new CustomerPo();
						cust.setName(custname);
						custdao.save(cust);
					}
					CommonUtil.setValue(obj, "custId", cust.getId());
					dao.save(obj);
				} else {
					uti.copyProperties(po, obj);
					dao.update(po);
				}
			} else {
				if ((Long) CommonUtil.getValue(obj, "id") == 0) {
					dao.save(obj);
				} else {
					dao.update(obj);
				}
			}
		}
	}

	public List<CompanyPo> getCompanySyncStatus(String companyNos) {

		String[] companyNo = companyNos.split(",");
		List<CompanyPo> list = compdao.find(companyNo);

		return list;
	}

	public List<TravelerPo> getTravelerSyncStatus(String travNos) {

		String[] travNo = travNos.split(",");
		List<TravelerPo> list = dao.find(travNo);

		return list;
	}

	public String changeBar(String travelerNo, String newParNo, String newBar,
			Integer isInActiveCur, String statusRemark) {
		return changeBar(travelerNo, newParNo, newBar, null, isInActiveCur, statusRemark);
	}
	
	
	/**
	 * 存储两个bar在AXO中是否是同一个公司的比较信息.key是比较oldBarNo+newBarNo.value 1代表相同,0代表不同.
	 */
	private static Map<String, String> companyMap = new  ConcurrentHashMap<String, String>();

	public String changeBar(String travelerNo, String changedParNo, String newBar, String tmrPar,
			Integer isInActiveCur, String statusRemark) {
		String retVal = "";
		try {
			Logger.getRootLogger().info(" changeBar isInActiveCur = "+ isInActiveCur);
			String newParNo = changedParNo;
			if(StringUtils.isEmpty(changedParNo)){
				newParNo = getMaxTravelerNo(newBar);
			}
	
			if (newParNo == null) {
				retVal = "err-changebar-001";
				return retVal;
			} 
	
			TravelerPo travPo = dao.find(travelerNo);
			if (travPo == null) {
				retVal = "err-changebar-002";
			} else {
				//modified by fans.fan 跨集团和不跨集团changeBar,逻辑不同.往AXO的数据同步机制是不一样的.20140305.
				AxoService axoService = new AxoServiceProxy();
				String compareRes = null;
				String key = travelerNo.substring(0, 6) + newParNo.substring(0, 6);
				/*
				 *if (companyMap.containsKey(key)) {
					compareRes = companyMap.get(key);
					
					// add by fans.fan 140710.
					String[] res = compareRes.split(":");
					//判断结果是0的数据,有效期只是当天,过了就要重新查询.
					if (res[1].equals("1") || res[0].equals(DateFormatUtils.format(new Date(), "yyyy-MM-dd"))) {
						compareRes = res[1];
					} else {
						compareRes = axoService.getCompanyID(travelerNo,newParNo);
						if ("1".equals(compareRes) || "0".equals(compareRes)) {
							String value = DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ":" + compareRes;
							companyMap.put(key, value);
						}
					}
					// end by fans.fan
					
				}else{
					
					compareRes = axoService.getCompanyID(travelerNo,newParNo);
					//不等于0或1的就不记录的,-1都是发生异常了,发生异常的下次重新查询. fans.fan
					if ("1".equals(compareRes) || "0".equals(compareRes)) {
						String value = DateFormatUtils.format(new Date(), "yyyy-MM-dd") + ":" + compareRes;
						companyMap.put(key, value);
					}
					
				}
				 */
				// add by arvin.mi 141115.
				if (companyMap.containsKey(key)) {
					compareRes = companyMap.get(key);
					
					
					String[] res = compareRes.split(":");
					//判断结果是0的数据,有效期只有10小时,过了就要重新查询.
					if (res[1].equals("1") || Long.parseLong(res[0])>(new Date().getTime()-36000)) {
						compareRes = res[1];
					} else {
						compareRes = axoService.getCompanyID(travelerNo,newParNo);
						if ("1".equals(compareRes) || "0".equals(compareRes)) {
							String value = new Date().getTime() + ":" + compareRes;
							companyMap.put(key, value);
						}
					}
					
					
				}else{
					
					compareRes = axoService.getCompanyID(travelerNo,newParNo);
					//不等于0或1的就不记录的,-1都是发生异常了,发生异常的下次重新查询. fans.fan
					if ("1".equals(compareRes) || "0".equals(compareRes)) {
						String value = new Date().getTime() + ":" + compareRes;
						companyMap.put(key, value);
					}
					
				}
				// end by arvin.mi
				CompanyPo company = compdao.find(newBar);
				logger.info("oldBarNo= "+travelerNo+",newBarNo="+newParNo + ",compareRes=" + compareRes);
				
				if ("1".equals(compareRes)) {
					retVal = changeBarSyncCom(isInActiveCur, travPo, statusRemark, newParNo, travelerNo, company, tmrPar);
				} else if("0".equals(compareRes)){
					retVal = changeBarNotSyncCom(isInActiveCur, travPo, statusRemark, newParNo, travelerNo, company, tmrPar);
				} else{
					return retVal = "err-changebar-003";
				}
				//end by fans.fan
			}
	
			// synchronize family.
			Map fMap = new HashMap();
			fMap.put("petentialTravelerNo", travelerNo + "-");
			List<TravelerPo> families = dao.list(fMap);
			for (int i = 0; i < families.size(); i++) {
				TravelerPo newFamily = new TravelerPo();
				uti.copyProperties(newFamily, families.get(i));
				String fTravelerNo = families.get(i).getTravelerNo();
				String fIdxName = fTravelerNo.substring(fTravelerNo
						.lastIndexOf("-"));
	
				newFamily.setId(0);
				newFamily.setTravelerNo(newParNo + fIdxName);
				newFamily.setStatus("ACTIVE");
				dao.save(newFamily);
	
				final String parNo = newFamily.getTravelerNo();
				logger.info("synchronize(parNo, 0, 1, \"PS,BS\") start*********");
				synchronize(parNo, 0, 1, "PS,BS");
				
				logger.info("synchronize(parNo, 0, 1, \"PS,BS\") end*********");
			}
			if (families.size() > 0) {
				Sendmail.getInstance().send(newParNo + " change bar!!!", "请检查家属信息是否完整！");
			}
		} catch (Exception e) {
			logger.error("changerbar error", e);
		}
		return retVal;
	}
	
	/**
	 * 往其他公司changeBar
	 * fans.fan
	 * 20140305
	 * @param isInActiveCur
	 * @param travPo
	 * @param statusRemark
	 * @param newParNo
	 * @param travelerNo
	 * @param company
	 * @param tmrPar
	 * @return
	 */
	private String changeBarNotSyncCom(Integer isInActiveCur,
			TravelerPo travPo, String statusRemark, String newParNo,
			String travelerNo, CompanyPo company, String tmrPar) {
		//不同company,按新逻辑处理 
		if (isInActiveCur == 1) {
			travPo.setStatusRemark(statusRemark + "(" + newParNo + ")");
			travPo.setStatus("INACTIVE");
			travPo.setFeeBased(0);
			dao.update(travPo);
			synchronize(travPo, 0, 1, "PS,BS,AXO");
		}
	
		logger.info("not bar synchronize(parNo, 0, 1, \"PS,BS,AXO\") start*********");

//				AxoService axoService = new AxoServiceProxy();
//				try {
//					axoService.changebar(travPo.getRefno(), travelerNo, newParNo);
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}

		TravelerPo newTrav = new TravelerPo();
		uti.copyProperties(newTrav, travPo);

		newTrav.setId(0);
		newTrav.setTravelerNo(newParNo);
		newTrav.setStatus("ACTIVE");
		newTrav.setStatusRemark("");
		newTrav.setFeeBased(1);
		newTrav.setUpdatedatetime(null);
		newTrav.setUpdateuserno(null);
		newTrav.setCreatedatetime(Calendar.getInstance());
		//add by fans.fan.changeBar时,对应的公司也要更新.20140221.
		newTrav.setCompanyId(company.getId());
		newTrav.setCompanyName(company.getCompanyName());
		//不同集团,不再和原有数据关联
		newTrav.setRefno(null);
		newTrav.setRefno2(null);
		//end by fans.fan
		if (!isEmpty(tmrPar))
			newTrav.setDepartment2(tmrPar);
		else
			newTrav.setDepartment2("");
		dao.save(newTrav);


		String retVal = String.valueOf(newTrav.getId());
		copyRelation("AddressPo", "C" + company.getId(),
				"T" + newTrav.getId());
		copyRelation("ContactPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("CreditCardPo", "T" + travPo.getId(), "T"
				+ newTrav.getId());
		copyRelation("EBillingPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("MemberPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("ExtraPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("PreferencePo", "T" + travPo.getId(), "T"
				+ newTrav.getId());
		copyRelation("FeeCodePo", "T" + travPo.getId(),
				"T" + newTrav.getId());

		synchronize(newParNo, 0, 1, "PS,BS,AXO", false);
		logger.info("synchronize(newParNo, 0, 1, \"PS,BS,AXO\", false) end*********");
		return retVal;
	}

	/**
	 * 往相同公司changeBar.
	 * fans.fan
	 * 20140305.
	 * @param isInActiveCur
	 * @param travPo
	 * @param statusRemark
	 * @param newParNo
	 * @param travelerNo
	 * @param company
	 * @param tmrPar
	 * @return
	 */
	private String changeBarSyncCom(Integer isInActiveCur,TravelerPo travPo, 
			String statusRemark,String newParNo,String travelerNo,CompanyPo company,String tmrPar){
		//相同company,按原逻辑处理 
		String retVal = null;
		try {
		if (isInActiveCur == 1) {
			travPo.setStatusRemark(statusRemark + "(" + newParNo + ")");
			travPo.setStatus("INACTIVE");
			travPo.setFeeBased(0);
			Logger.getRootLogger().info("save old par = "+ travPo.getTravelerNo());
			dao.update(travPo);
			Logger.getRootLogger().info("save old par success = "+ travPo.getTravelerNo());
			synchronize(travPo, 0, 1, "PS,BS");
			Logger.getRootLogger().info("changebarsynchronize = ");
		}

		AxoService axoService = new AxoServiceProxy();
			String msg = axoService.changebar(travPo.getRefno(), travelerNo, newParNo);
			Logger.getRootLogger().info("changebar = "+msg);
		

		TravelerPo newTrav = new TravelerPo();
		uti.copyProperties(newTrav, travPo);
		Logger.getRootLogger().info("copy = "+travPo.toString());
		newTrav.setId(0);
		newTrav.setTravelerNo(newParNo);
		newTrav.setStatus("ACTIVE");
		newTrav.setStatusRemark("");
		newTrav.setFeeBased(1);
		newTrav.setUpdatedatetime(null);
		newTrav.setUpdateuserno(null);
		newTrav.setCreatedatetime(Calendar.getInstance());
		//add by fans.fan.changeBar时,对应的公司也要更新.20140221.
		newTrav.setCompanyId(company.getId());
		newTrav.setCompanyName(company.getCompanyName());
		//end by fans.fan
		if (!isEmpty(tmrPar))
			newTrav.setDepartment2(tmrPar);
		else
			newTrav.setDepartment2("");
		dao.save(newTrav);

		Logger.getRootLogger().info("save = "+newTrav.toString());
		
		retVal = String.valueOf(newTrav.getId());
		copyRelation("AddressPo", "C" + company.getId(),
				"T" + newTrav.getId());
		copyRelation("ContactPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("CreditCardPo", "T" + travPo.getId(), "T"
				+ newTrav.getId());
		copyRelation("EBillingPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("MemberPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("ExtraPo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		copyRelation("PreferencePo", "T" + travPo.getId(), "T"
				+ newTrav.getId());
		copyRelation("FeeCodePo", "T" + travPo.getId(),
				"T" + newTrav.getId());
		// copyRelation("FeeCodeDetailPo", "T" + travPo.getId(), "T" +
		// newTrav.getId());
		Logger.getRootLogger().info("copy relation end***************");
		synchronize(newParNo, 0, 1, "PS,BS,AXO", true);
		Logger.getRootLogger().info("synchronize(newParNo, 0, 1, \"PS,BS,AXO\", true) end***************");
		} catch (RemoteException e) {
			Logger.getRootLogger().error("changebar error= ",e);
			e.printStackTrace();
		}
		return retVal;
	}

	private void copyRelation(String classname, String rId, String newRId) {
		List list = dao.listRelation(classname, rId);
		for (int i = 0; i < list.size(); i++) {

			Object obj = CommonUtil.newInstance("com.citsamex.service.db."
					+ classname);
			uti.copyProperties(obj, list.get(i));
			CommonUtil.setValue(obj, "relationId", newRId);
			CommonUtil.setValue(obj, "id", 0);

			if (classname.equals("AddressPo")) {
				String catalog = (String) CommonUtil.getValue(obj, "catalog");
				if (catalog == null
						|| catalog.toUpperCase().equals("STATEMENT")) {
					continue;
				}
			}

			dao.save(obj);
		}
	}

	private void setRelationId(Object obj, Map<String, Object> map)
			throws Exception {
		// if relationid==0, then obj is a traveler or company.
		String relationId = (String) CommonUtil.getValue(obj, "relationId");
		if (relationId != null) {
			long iNumber = 0;
			String number = relationId.substring(1);
			try {
				iNumber = Long.parseLong(number);
			} catch (Exception ex) {

			}

			// if number == 0, it has a customer no such as 'AMEXSH' or
			// 'AMEXSH0001', then new, otherwise update.
			if (iNumber == 0) {
				Object parent = map.get(relationId);
				if (parent == null) {
					if (relationId.length() == 6) {
						parent = compdao.find(relationId);
					} else if (relationId.length() == 10) {
						parent = dao.find(relationId);
					}
				}

				if (parent == null) {
					throw new Exception("target '" + relationId
							+ "' does not exist.");
				}
				String t = "";
				if (parent instanceof TravelerPo) {
					t = "T";
				} else if (parent instanceof CompanyPo) {
					t = "C";
				}
				Long id = (Long) CommonUtil.getValue(parent, "id");
				CommonUtil.setValue(obj, "relationId", t + id);
			}

		}
	}

	private Object initPo(String objname) {
		Object obj = null;
		if (objname.equals("Company")) {
			obj = new CompanyPo();
		} else if (objname.equals("Traveler")) {
			obj = new TravelerPo();
		} else if (objname.equals("Address")) {
			obj = new AddressPo();
		} else if (objname.equals("CreditCard")) {
			obj = new CreditCardPo();
		} else if (objname.equals("Discount")) {
			obj = new DiscountPo();
		} else if (objname.equals("RequiredData")) {
			obj = new ExtraPo();
		} else if (objname.equals("Member")) {
			obj = new MemberPo();
		} else if (objname.equals("Preference")) {
			obj = new PreferencePo();
		}
		return obj;
	}

	private Map<Integer, String> getDataMap() {
		if (dataDefMap == null) {
			dataDefMap = new HashMap<Integer, String>();
			dataDefMap.put(0, "ebilling");
			dataDefMap.put(10012, "purposeCode");
			dataDefMap.put(10011, "lowestFareReasonCode");
			dataDefMap.put(10001, "tripDate");
			dataDefMap.put(10002, "dateOfBooking");
			dataDefMap.put(10005, "shortLongHaul");
			dataDefMap.put(10006, "fareType");
			dataDefMap.put(10008, "destination");
			dataDefMap.put(10009, "iataFare");
		}
		return dataDefMap;
	}

	private Map<Integer, String> getData2Map() {
		if (data2DefMap == null) {
			data2DefMap = new HashMap<Integer, String>();

			data2DefMap.put(1, "lowestFareReasonCode");
			data2DefMap.put(2, "customerRefernce");
			data2DefMap.put(4, "tripRequistition");
			data2DefMap.put(8, "jobNumber");
			data2DefMap.put(256, "purposeCode");
			data2DefMap.put(512, "ebilling");
			data2DefMap.put(1024, "projectNumber");
			data2DefMap.put(2048, "accountNumber");
		}
		return data2DefMap;
	}

	public ServiceMessage validate(String objectName) {
		ServiceMessage retVal = new ServiceMessage();

		List<String> messages = new ArrayList<String>();
		String[] objNames = objectName.split("<sp/>");

		for (int i = 0; i < objNames.length; i++) {

			if (isEmpty(objNames[i])) {
				continue;
			}

			String name = objNames[i].substring(0, objNames[i].indexOf("="));
			String objName = name;
			if (objNames[i].startsWith("_")) {
				name = name.substring(0, name.lastIndexOf("_"));
			}
			String value = objNames[i].substring(objNames[i].indexOf("=") + 1);

			List<IValidator> validators = validatorManager.getValidators(name);
			for (int j = 0; j < validators.size(); j++) {
				IValidator validator = validators.get(j);
				if (!validator.valid(value)) {
					String message = objName + "=" + validator.getMessage();
					message += validator.getAttachMessage();
					messages.add(message);
				}
			}
		}
		retVal.setData(messages);
		return retVal;

	}

	public void resetValidator() {
		validatorManager.setInitflag(false);
	}

	public List initUploadColumnMapp() {

		List<Code> list = new ArrayList<Code>();
		Field[] fields = TravelerPo.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(UploadMapping.class)) {
				String desc = fields[i].getAnnotation(UploadMapping.class)
						.value();
				Code code = new Code("traveler_" + fields[i].getName(), desc);
				list.add(code);
			}
		}

		// 四个地址。

		fields = AddressPo.class.getDeclaredFields();
		for (int j = 1; j <= 4; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("address" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}
		// 6个联系人
		fields = ContactPo.class.getDeclaredFields();
		for (int j = 1; j <= 5; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("contact" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}

		

		fields = PreferencePo.class.getDeclaredFields();
		for (int j = 1; j <= 8; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("preference" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}
		
		fields = MemberPo.class.getDeclaredFields();
		for (int j = 1; j <= 8; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("member" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}

		fields = CreditCardPo.class.getDeclaredFields();
		for (int j = 1; j <= 5; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("creditcard" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}
		fields = VisaPo.class.getDeclaredFields();
		for (int j = 1; j <= 3; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code(
							"visa" + j + "_" + fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}
		fields = ExtraPo.class.getDeclaredFields();
		for (int j = 1; j <= 5; j++) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(UploadMapping.class)) {
					String desc = fields[i].getAnnotation(UploadMapping.class)
							.value();
					Code code = new Code("extra" + j + "_"
							+ fields[i].getName(), desc + j);
					list.add(code);
				}
			}
		}
		return list;
	}

	
	@Override
	public Object synchronize(String customerNo, Integer hasChild,
			Integer hasRelation, String systems, String priority) {
		
		return synchronize(customerNo, hasChild, hasRelation, systems, false, priority);
	}
	
	public Object synchronize(String customerNo, Integer hasChild, Integer hasRelation, String systems, boolean updateAXOBarPathOnly, String priority) {

		Object obj = null;

		if (customerNo.equals(getBarno(customerNo))) {
			obj = compdao.find(customerNo);
		} else {
			obj = dao.find(customerNo);
		}

		final String custno = customerNo;
		final boolean withChild = hasChild == 1;
		final boolean withRelation = hasRelation == 1;

		String xml = "";
		if (custno.length() == 10) {
			xml = buildTravelerXML(custno, withRelation, systems, updateAXOBarPathOnly);
		} else if (custno.length() == 6) {
			xml = buildCompanyXML(custno, withChild, withRelation, systems);
		} else if (custno.indexOf("-") != -1) {
			String subNo = custno.substring(custno.indexOf("-") + 1);
			if (subNo.startsWith("F")) {
				xml = buildTravelerXML(custno, withRelation, systems, updateAXOBarPathOnly);
			} else {
				xml = "";
			}
		}

		transport2Esb(custno, xml, priority);
		return obj;
	}
	
	/**
	 * new property priority for delay the batch update
	 * @param custno
	 * @param xml
	 * @param priority
	 */
	public void transport2Esb(String custno, String xml, String priority) {
		commondao
				.executeUpdate(
						"INSERT INTO T_JOB_SYNC_BPAR (CUSTNO, BPAR_XML, STATUS, PRIORITY, CREATEDATETIME) VALUES('"
								+ custno + "',:xml, 0, :priority, GETDATE())",
						new String[] { "xml","priority" }, new String[] { xml,priority });
	}

	

	/**
	 * new property priority for delay the batch update
	 */
	@Override
	public Object synchronize(TravelerPo travelerPo, Integer hasChild,
			Integer hasRelation, String systems, String priority) {
		final String custno = travelerPo.getTravelerNo();
		final boolean withChild = hasChild == 1;
		final boolean withRelation = hasRelation == 1;

		String xml = "";
		if (custno.length() == 10) {
			xml = buildTravelerXML(travelerPo, withRelation, systems);
		} else if (custno.indexOf("-") != -1) {
			String subNo = custno.substring(custno.indexOf("-") + 1);
			if (subNo.startsWith("F")) {
				xml = buildTravelerXML(travelerPo, withRelation, systems);
			} else {
				xml = "";
			}
		}

		transport2Esb(custno, xml, priority);
		return custno;
		
	}

	private static Map<String, String> cardbinMap = null;
	/**
	 * 发卡机构
	 */
	private static Map<String, String> cardinstMap = null;
	
    @Override
    public String getCardTypeByCardbin(String cardno) {
        if (cardbinMap == null) {
            cardbinMap = new HashMap<String, String>();
            cardinstMap = new HashMap<String, String>();
            List cardbinList = dao.listCardbin();
            CardbinPo po;
            for (int i=0; i < cardbinList.size(); i++) {
                po = (CardbinPo) cardbinList.get(i);
                cardbinMap.put(po.getCardbin(), po.getCardtype());
                cardinstMap.put(po.getCardbin(), po.getCREDIT_INST_KEY());
            }
        }
        
        String cardbin;
        String cardtype = "error";
        String tmptype;
        int j = 4;
        while (j < 9) {
            cardbin = cardno.substring(0, j);
            tmptype = cardbinMap.get(cardbin);
            if (tmptype != null) {
                cardtype = tmptype;
                break;
            }
            j += 1;
        }
        if (cardtype.equals("error")) {
            if (cardno.startsWith("4")) {
                cardtype = "A02";
            } else if (cardno.startsWith("5")) {
                cardtype = "A03";
            } else if (cardno.startsWith("3")) {
                cardtype = "A01";
            } else if (cardno.startsWith("6")) {
                cardtype = "A02";
            }
        }
        
        //add by fans.fan,增加发卡机构的查询, 20140224.
        String tmpinst;
        j = 4;
        while (j < 9) {
            cardbin = cardno.substring(0, j);
            tmpinst = cardinstMap.get(cardbin);
            if (tmpinst != null) {
                cardtype = cardtype + "," +tmpinst;
                break;
            }
            if(j == 8){
            	cardtype = cardtype + ",Others";
            }
            j += 1;
        }
        //end by fans.fan

        return cardtype;
    }

	@Override
	public ServiceMessage updateCreditCardPo(CreditCardPo po) {
		dao.update(po);
		return null;
	}
	
	
	public static void main(String[] args) {
//		ApplicationContext ac = new ClassPathXmlApplicationContext(
//				"/metadata/applicationContext-*.xml");
//		ITravelerService ts = (ITravelerService) ac.getBean("travelerService");
//		ts.synchronize("3BA6740014", 1, 1, null);
//		System.out.println();
		
		PsService psService = new PsServiceProxy();
		try {
			for (int j = 0; j < 10; j++) {
				String newParNo = psService.getNewParNo("AMEXSH");
				System.out.println("newParNo" + j +"=" + newParNo);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
