package com.citsamex.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.citsamex.app.Server;
import com.citsamex.app.action.Page;
import com.citsamex.app.util.CommonUtil;
import com.citsamex.service.action.Clause;
import com.citsamex.service.action.Company;
import com.citsamex.service.action.Customer;
import com.citsamex.service.db.AddressPo;
import com.citsamex.service.db.ClauseDao;
import com.citsamex.service.db.ClausePo;
import com.citsamex.service.db.CompanyDao;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.ContactPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.CustomerDao;
import com.citsamex.service.db.CustomerPo;
import com.citsamex.service.db.DBIPo;
import com.citsamex.service.db.DataMapping;
import com.citsamex.service.db.DiscountPo;
import com.citsamex.service.db.EBillingPo;
import com.citsamex.service.db.ExtraPo;
import com.citsamex.service.db.FeeCodeDetailPo;
import com.citsamex.service.db.FeeCodePo;
import com.citsamex.service.db.MemberPo;
import com.citsamex.service.db.PreferencePo;
import com.citsamex.service.db.PurposeCodePo;
import com.citsamex.service.db.ReasonCodePo;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.service.db.VisaPo;
import com.citsamex.ws.AxoService;
import com.citsamex.ws.AxoServiceProxy;
import com.citsamex.ws.BsService;
import com.citsamex.ws.BsServiceProxy;
import com.citsamex.ws.ServMessage;
import com.citsamex.ws.PsService;
import com.citsamex.ws.PsServiceProxy;

public class CompanyService extends CommonService implements ICompanyService {

	private CompanyDao dao;
	private CustomerDao custdao;
	private ClauseDao clauseDao;
	private TravelerDao travdao;

	private ITravelerService travelerService;

	public ITravelerService getTravelerService() {
		return travelerService;
	}

	public void setTravelerService(ITravelerService travelerService) {
		this.travelerService = travelerService;
	}

	private static Map<Integer, String> dataDefMap = null;
	private static Map<Integer, String> data2DefMap = null;

	public TravelerDao getTravdao() {
		return travdao;
	}

	public void setTravdao(TravelerDao travdao) {
		this.travdao = travdao;
	}

	public ClauseDao getClauseDao() {
		return clauseDao;
	}

	public void setClauseDao(ClauseDao clauseDao) {
		this.clauseDao = clauseDao;
	}

	public CustomerDao getCustdao() {
		return custdao;
	}

	public void setCustdao(CustomerDao custdao) {
		this.custdao = custdao;
	}

	public CompanyDao getDao() {
		return dao;
	}

	public void setDao(CompanyDao dao) {
		this.dao = dao;
	}

	public ServiceMessage listCompay(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<CompanyPo> polist = dao.list(map);
		List<Company> list = new ArrayList<Company>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Company company = new Company();
			company.setPo(polist.get(i));
			list.add(company);
		}

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage deleteCompany(Long id) {
		CompanyPo po = (CompanyPo) dao.find(CompanyPo.class, id);
		if (po == null) {
			return new ServiceMessage("4003");
		}
		dao.deleteCompany(po.getCompanyNo());
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(po.getId());
		return sm;
	}

	public ServiceMessage activeCompany(Long id) {
		CompanyPo po = (CompanyPo) dao.find(CompanyPo.class, id);
		if (po == null) {
			return new ServiceMessage("4003");
		}

		// dao.updateTravelerStatus(po.getCompanyNo(), po.getStatus());
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage listCompany() {
		@SuppressWarnings("unchecked")
		List<CompanyPo> polist = dao.list(new HashMap<String, Object>());
		List<Company> list = new ArrayList<Company>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Company company = new Company();
			company.setPo(polist.get(i));
			list.add(company);
		}

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage create(Company company) {

		if (isEmpty(company.getCompanyNo())) {
			return new ServiceMessage("4001");
		}

		if (dao.find(company.getCompanyNo()) != null) {
			return new ServiceMessage("4002");
		}

		CompanyPo po = company.buildPo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		company.setPo(po);
		sm.setData(company);
		return sm;
	}

	@Override
	public ServiceMessage findCustomer(String name) {
		Customer customer = null;
		CustomerPo po = (CustomerPo) custdao.find(name);
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		if (po != null) {
			customer = new Customer();
			customer.setPo(po);

		} else {
			sm.setCode("1001");
		}

		sm.setData(customer);
		return sm;
	}

	public ServiceMessage findCompany(String companyNo) {
		Company company = null;
		CompanyPo po = (CompanyPo) dao.find(companyNo);
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		if (po != null) {
			company = new Company();
			company.setPo(po);

		} else {
			sm.setCode("1001");
		}

		sm.setData(company);
		return sm;
	}

	public ServiceMessage findCompany(Long id) {
		Company company = null;
		CompanyPo po = (CompanyPo) dao.find(id);
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		if (po != null) {
			company = new Company();
			company.setPo(po);

		} else {
			sm.setCode("4001");
		}

		sm.setData(company);
		return sm;
	}

	public ServiceMessage updateCompany(Company company) {
		CompanyPo po = dao.find(company.getId());

		if (po == null) {
			return new ServiceMessage("4003");
		}
		uti.copyProperties(po, company);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);

		sm.setData(company);
		return sm;
	}

	public ServiceMessage createAddress(String addressType, String address,
			String city, String zipCode, Long companyId) {
		AddressPo po = new AddressPo();
		po.setCatalog(addressType);
		po.setAddress(address);
		po.setCity(city);
		po.setZipCode(zipCode);
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createAddress(AddressPo po, String compId) {
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setRelationId("C" + compId);
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listAddress(Long companyId) {
		List list = dao.listAddress("C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage findAddress(Long id) {
		AddressPo po = dao.findAddress(id);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateAddress(Long id, String address) {
		AddressPo po = dao.findAddress(id);
		po.setAddress(address);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateAddress(Long id, String address, String city,
			String zipCode) {
		AddressPo po = dao.findAddress(id);
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

	public ServiceMessage updateDiscount(Long id, String varname,
			String prodId, String remark, String discountRate) {
		DiscountPo po = (DiscountPo) dao.findRelation(DiscountPo.class, id);
		po.setProdId(prodId);
		po.setRemark(remark);
		po.setVarname(varname);
		po.setDiscountRate(discountRate);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
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
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("unchecked")
	public ServiceMessage listClauseByString(Long compId, String CatalogNoString) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("catalog_no", CatalogNoString);
		List<ClausePo> polist = clauseDao.listByString(map);
		Map<String, String> variable = new HashMap<String, String>();

		List<DiscountPo> discounts = (List<DiscountPo>) dao.listRelation(
				"DiscountPo", "C" + compId);
		for (int i = 0; i < discounts.size(); i++) {
			DiscountPo disc = discounts.get(i);
			String varname = disc.getVarname();
			String value = disc.getDiscountRate();
			variable.put("${discount." + varname + "}", value);
		}

		List<FeeCodePo> feeCodes = (List<FeeCodePo>) dao.listRelation(
				"FeeCodePo", "C" + compId);
		for (int i = 0; i < feeCodes.size(); i++) {
			FeeCodePo po = feeCodes.get(i);
			String varname = po.getVarname();
			String groupId = po.getGroupId();

			List<FeeCodeDetailPo> feeCodeDetails = (List<FeeCodeDetailPo>) dao
					.listFeeCodeDetailCurrent("C" + compId, po.getId(), groupId);

			for (int j = 0; j < feeCodeDetails.size(); j++) {
				FeeCodeDetailPo fd = feeCodeDetails.get(j);
				String prod = fd.getProductCode();
				String value = "";
				if (fd.getFeeMethod().equals("By Amt")) {
					value = String.valueOf(fd.getFeeAmt());
				} else {
					value = String.valueOf(fd.getFeePercent());
				}

				variable.put("${terms." + varname + "." + groupId + "." + prod
						+ "}", value);
			}

		}

		List<Clause> list = new ArrayList<Clause>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Clause clause = new Clause();
			clause.setPo(polist.get(i));

			String content = clause.getContent();

			Pattern p = Pattern.compile("\\$\\{[(\\S|\\s)]*}");
			Matcher m = p.matcher(content);
			while (m.find()) {
				String oldVal = m.group();
				String newVal = variable.get(oldVal.replaceAll(" ", ""));

				if (newVal != null) {
					content = content.replaceAll(
							"\\$\\{" + oldVal.substring(2, oldVal.length() - 1)
									+ "\\}", newVal);
				}
			}
			clause.setContent(content);

			list.add(clause);
		}
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createDiscount(String varname, String prodId,
			String remark, String discountRate, Long companyId) {
		DiscountPo po = new DiscountPo();
		po.setProdId(prodId);
		po.setRemark(remark);
		po.setDiscountRate(discountRate);
		po.setVarname(varname);
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listContact(Long companyId) {
		List list = dao.listRelation("ContactPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listDiscount(Long companyId) {
		List list = dao.listRelation("DiscountPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage findContact(Long id) {
		ContactPo po = (ContactPo) dao.findRelation(ContactPo.class, id);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteContact(Long id) {
		ContactPo po = (ContactPo) dao.findRelation(ContactPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteAddress(Long id) {
		AddressPo po = (AddressPo) dao.findRelation(AddressPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_ADDRESS_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteDiscount(Long id) {
		DiscountPo po = (DiscountPo) dao.findRelation(DiscountPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateContact(Long id, String firstname,
			String lastname, String firstname_en, String lastname_en,
			String homephone, String officephone, String title, String faxno,
			String email, String mobile) {
		ContactPo po = (ContactPo) dao.findRelation(ContactPo.class, id);
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
			String mandatory, Long companyId) {
		EBillingPo po = new EBillingPo();
		po.setFieldName(fieldName);
		po.setDefaultValue(defaultValue);
		po.setMandatory(mandatory);
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		CompanyPo cpo = dao.find(companyId);
		cpo.setEbilling(1);
		dao.update(cpo);
		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateEBill(Long id, String fieldName,
			String defaultValue, String mandatory) {
		EBillingPo po = (EBillingPo) dao.findRelation(EBillingPo.class, id);

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
		EBillingPo po = (EBillingPo) dao.findRelation(EBillingPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listEBill(Long companyId) {
		List list = dao.listRelation("EBillingPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_EBILLING_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage countCompany(String companyName, String quicksearch,
			String quicksearchName) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyName", companyName);
		map.put("quicksearch", quicksearch);
		map.put("quicksearchName", quicksearchName);

		int count = dao.count(map);

		Page page = new Page(count);
		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(page);
		return sm;
	}

	@SuppressWarnings("unchecked")
	public ServiceMessage listCompany(Integer currentPage, String companyName,
			String quicksearch, String quicksearchName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageSize", Server.PROPS_PAGESIZE);
		map.put("companyName", companyName);
		map.put("quicksearch", quicksearch);
		map.put("quicksearchName", quicksearchName);
		List<CompanyPo> polist = dao.list(map);
		List<Company> list = new ArrayList<Company>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Company company = new Company();
			company.setPo(polist.get(i));
			list.add(company);
		}

		ServiceMessage sm = new ServiceMessage(CODE_COMPANY_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCode(Long companyId) {
		List list = dao.listRelation("FeeCodePo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createFeeCode(String varname, String feeType,
			String fareCode, String groupId, Long companyId) {
		FeeCodePo po = new FeeCodePo();
		po.setGroupId(groupId);
		po.setFareCode(fareCode);
		po.setFeeType(feeType);
		po.setVarname(varname);
		po.setRelationId("C" + companyId);
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

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId) {
		List list = dao.listRelation("FeeCodeDetailPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String groupId) {
		List list = dao.listFeeCodeDetail("C" + companyId, feeCodeId, groupId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String period, String groupid) {
		List list = dao.listFeeCodeDetail("C" + companyId, feeCodeId, period,
				groupid);
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
		po.setRelationId("C" + companyId);
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
		po.setRelationId("C" + po.getRelationId());
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
	public ServiceMessage listCreditCard(Long companyId) {
		List list = dao.listRelation("CreditCardPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage deleteCreditCard(Long id) {
		CreditCardPo po = (CreditCardPo) dao.findRelation(CreditCardPo.class,
				id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage createReasonCode(String reasonCode,
			String productCode, String description, Long companyId) {
		ReasonCodePo po = new ReasonCodePo();
		po.setReasonCode(reasonCode);
		po.setDescription(description);
		po.setProductCode(productCode);
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateReasonCode(Long id, String reasonCode,
			String productCode, String description) {
		ReasonCodePo po = (ReasonCodePo) dao.findRelation(ReasonCodePo.class,
				id);
		po.setReasonCode(reasonCode);
		po.setProductCode(productCode);
		po.setDescription(description);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deleteReasonCode(Long id) {
		ReasonCodePo po = (ReasonCodePo) dao.findRelation(ReasonCodePo.class,
				id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listReasonCode(Long companyId) {
		List list = dao.listRelation("ReasonCodePo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createPurposeCode(String purposeCode,
			String description, Long companyId) {
		PurposeCodePo po = new PurposeCodePo();
		po.setPurposeCode(purposeCode);
		po.setDescription(description);
		po.setRelationId("C" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_PURPOSE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updatePrposeCode(Long id, String purposeCode,
			String description) {
		PurposeCodePo po = (PurposeCodePo) dao.findRelation(
				PurposeCodePo.class, id);
		po.setPurposeCode(purposeCode);
		po.setDescription(description);
		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_PURPOSE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage deletePurposeCode(Long id) {
		PurposeCodePo po = (PurposeCodePo) dao.findRelation(
				PurposeCodePo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_PURPOSE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listPurposeCode(Long companyId) {
		List list = dao.listRelation("PurposeCodePo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_PURPOSE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public void updateDBI(DBIPo po) {

		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.getHibernateTemplate().saveOrUpdate(po);

	}

	@SuppressWarnings("rawtypes")
	public ServiceMessage listDBI(Long companyId) {
		List list = dao.listRelation("DBIPo", "C" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_DBI_SUCCESS);
		sm.setData(list);
		return sm;
	}

	public ServiceMessage createDbi(DBIPo po) {

		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_DBI_SUCCESS);
		sm.setData(po);
		return sm;
	}

	public ServiceMessage updateStatusRemark(Long id, String remark,
			String status, String travelerList) {
		travelerList = travelerList.toUpperCase().replaceAll("\n", "");
		CompanyPo po = (CompanyPo) dao.find(CompanyPo.class, id);
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

		PsService service = new PsServiceProxy();
		BsService service2 = new BsServiceProxy();
		if (sm.isSuccess()) {
			try {
				service.syncStatus(po.getCompanyNo(), po.getStatus(),
						po.getStatusRemark());
			} catch (RemoteException e) {
				Logger.getRootLogger().warn("sync status failure", e);
			}
		}

		if (!this.isEmpty(travelerList)) {
			List<TravelerPo> travelToUpdateAndSynch = new ArrayList<TravelerPo>();
			String list[] = travelerList.split(",");
			for (int i = 0; i < list.length; i++) {
				if (list[i].indexOf("-") != -1) {
					String travelerNo1 = list[i].substring(0,
							list[i].indexOf("-"));
					String travelerNo2 = list[i]
							.substring(list[i].indexOf("-") + 1);
					if (!travelerNo1.startsWith(po.getCompanyNo())
							|| !travelerNo2.startsWith(po.getCompanyNo())) {
						continue;
					}
					List<TravelerPo> travelers = travdao.findBetween(
							travelerNo1, travelerNo2);
					for (int j = 0; j < travelers.size(); j++) {
						TravelerPo travelerPo = travelers.get(j);
						travelToUpdateAndSynch.add(travelerPo);
					}
				} else {
					if (!list[i].startsWith(po.getCompanyNo())) {
						continue;
					}
					TravelerPo travelerPo = travdao.find(list[i]);
					if (travelerPo != null) {
						travelToUpdateAndSynch.add(travelerPo);
					}
				}
			}
			if(travelToUpdateAndSynch.size()> 0){
				updateAndSynchTraveler(travelToUpdateAndSynch, status, remark);
			}
			
		}

		return sm;
	}

	public ServiceMessage updateStatusRemark_new(Long id, String remark,
			String status, String travelerList) {
		travelerList = travelerList.toUpperCase().replaceAll("\n", "");
		CompanyPo po = (CompanyPo) dao.find(CompanyPo.class, id);
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

		PsService service = new PsServiceProxy();
		//BsService service2 = new BsServiceProxy();
		if (sm.isSuccess()) {
			try {
				service.syncStatus(po.getCompanyNo(), po.getStatus(),
						po.getStatusRemark());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
//		Session session = travdao.getHibernateTemplate().getSessionFactory().openSession();
//		session.beginTransaction();
		Session session = travdao.getHibernateTemplate().getSessionFactory().getCurrentSession();
		int inactiveAcc = 0;
		int activeAcc = 0;
		StringBuffer inactiveTidStr = new StringBuffer();
		StringBuffer activeTidStr = new StringBuffer();
		String inactiveSql = "update T_TRAVELER set STATUS='" + status + 
				"', STATUS_REMARK='" + remark +"', FEE_BASED=0 " +
				"where id in (";
		String activeSql = "update T_TRAVELER set STATUS='" + status + 
				"', STATUS_REMARK='" + remark +"', FEE_BASED=1 " +
				"where id in (";
		if (!this.isEmpty(travelerList)) {
			String list[] = travelerList.split(",");

			for (int i = 0; i < list.length; i++) {
				if (list[i].indexOf("-") != -1) {
					String travelerNo1 = list[i].substring(0,
							list[i].indexOf("-"));
					String travelerNo2 = list[i]
							.substring(list[i].indexOf("-") + 1);
					if (!travelerNo1.startsWith(po.getCompanyNo())
							|| !travelerNo2.startsWith(po.getCompanyNo())) {
						continue;
					}
					List<TravelerPo> travelers = travdao.findBetween(
							travelerNo1, travelerNo2);

					for (int j = 0; j < travelers.size(); j++) {
						TravelerPo travelerPo = travelers.get(j);
						travelerPo.setStatus(po.getStatus());
						travelerPo.setStatusRemark(remark);
						if (!status.equals("ACTIVE")) {
							travelerPo.setFeeBased(0);
							if (inactiveTidStr.length() == 0) {
								inactiveTidStr.append(travelerPo.getId());
								inactiveAcc += 1;
							} else {
								inactiveTidStr.append("," + travelerPo.getId());
								inactiveAcc += 1;								
							}
							
						} else {
							travelerPo.setFeeBased(1);
							if (activeTidStr.length() == 0) {
								activeTidStr.append(travelerPo.getId());
								activeAcc += 1;
							} else {
								activeTidStr.append("," + travelerPo.getId());
								activeAcc += 1;					
							}

							
						}
						
						// batch transaction
						// if id account over 50 ,  execute the sql
						if (inactiveAcc == 50) {
							inactiveSql = inactiveSql + inactiveTidStr.toString() + ")";
							session.createSQLQuery(inactiveSql).executeUpdate();
							inactiveAcc = 0;
							inactiveSql = "update T_TRAVELER set STATUS='" + status + 
									"', STATUS_REMARK='" + remark +"', FEE_BASED=0 " +
									"where id in (";
							inactiveTidStr = new StringBuffer();
						}
						
						if (activeAcc == 50) {
							activeSql = activeSql + activeTidStr.toString() + ")";
							session.createSQLQuery(activeSql).executeUpdate();
							activeAcc = 0;
							activeSql = "update T_TRAVELER set STATUS='" + status + 
									"', STATUS_REMARK='" + remark +"', FEE_BASED=1 " +
									"where id in (";
							activeTidStr = new StringBuffer();
						}
						
						
						
						//previous process 
						//travdao.update(travelerPo);
						// try {
						travelerService.synchronize(travelerPo.getTravelerNo(),
								0, 1, "PS,BS,AXO");
					}
				} else {
					if (!list[i].startsWith(po.getCompanyNo())) {
						continue;
					}
					TravelerPo travelerPo = travdao.find(list[i]);
					if (travelerPo != null) {
						travelerPo.setStatus(po.getStatus());
						travelerPo.setStatusRemark(remark);
						if (!status.equals("ACTIVE")) {
							travelerPo.setFeeBased(0);
							if (inactiveTidStr.length() == 0) {
								inactiveTidStr.append(travelerPo.getId());
								inactiveAcc += 1;
							} else {
								inactiveTidStr.append("," + travelerPo.getId());
								inactiveAcc += 1;								
							}
							
						} else {
							travelerPo.setFeeBased(1);
							if (activeTidStr.length() == 0) {
								activeTidStr.append(travelerPo.getId());
								activeAcc += 1;
							} else {
								activeTidStr.append("," + travelerPo.getId());
								activeAcc += 1;					
							}

							
						}
						// batch transaction
						// if id account over 50 ,  execute the sql
						if (inactiveAcc == 50) {
							inactiveSql = inactiveSql + inactiveTidStr.toString() + ")";
							session.createSQLQuery(inactiveSql).executeUpdate();
							inactiveAcc = 0;
							inactiveSql = "update T_TRAVELER set STATUS='" + status + 
									"', STATUS_REMARK='" + remark +"', FEE_BASED=0 " +
									"where id in (";
							inactiveTidStr = new StringBuffer();
						}
						
						if (activeAcc == 50) {
							activeSql = activeSql + activeTidStr.toString() + ")";
							session.createSQLQuery(activeSql).executeUpdate();
							activeAcc = 0;
							activeSql = "update T_TRAVELER set STATUS='" + status + 
									"', STATUS_REMARK='" + remark +"', FEE_BASED=1 " +
									"where id in (";
							activeTidStr = new StringBuffer();
						}
						
						travelerService.synchronize(travelerPo.getTravelerNo(),
								0, 1, "PS,BS,AXO");
					}
				}
			}
			
			// at last , execute the sql again
			//long stime = System.currentTimeMillis();
			if (status.equals("ACTIVE") && activeTidStr.length() > 0) {
				activeSql = activeSql + activeTidStr.toString() + ")";
				session.createSQLQuery(activeSql).executeUpdate();
			} else if (inactiveTidStr.length() > 0){
				inactiveSql = inactiveSql + inactiveTidStr.toString() + ")";
				session.createSQLQuery(inactiveSql).executeUpdate();
			}
			//long etime = System.currentTimeMillis();			
			//System.out.println("this update cost:" + (etime-stime));
		}

		return sm;
	}

	public void updateAndSynchTraveler(List<TravelerPo> travelToUpdateAndSynch, String status, String remark) {
		String feeBased = !"ACTIVE".equals(status)? "0":"1";
		StringBuffer travelerIDs = new StringBuffer();
		for(int i=0; i<travelToUpdateAndSynch.size(); i++){
			TravelerPo tpo = travelToUpdateAndSynch.get(i);
			travelerIDs.append(tpo.getId()+",");
		}
		travelerIDs.append("-1");
		String updateSQL = "update T_TRAVELER set status='" + status + "', FEE_BASED=" + feeBased+", STATUS_REMARK='" + remark + "' where ID in (" +travelerIDs.toString() +")";
		int records = travdao.batchUpdateTravelerStatus(updateSQL);
		System.out.println(records + " Travelers's fee base and status remark are updated!");
		
		//synch
		for(int i=0; i<travelToUpdateAndSynch.size(); i++){
			TravelerPo tpo = travelToUpdateAndSynch.get(i);

			//travelerService.synchronize(tpo, 0, 1, "PS,BS,AXO");
			// travelerPo's status did not change, so we need to invoke the follow method
			tpo.setFeeBased(new Integer(feeBased));
			tpo.setStatus(status);
			tpo.setStatusRemark(remark);
			travelerService.synchronize(tpo, 0, 1, "PS,BS,AXO","20");
		}
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

	@SuppressWarnings({ "static-access", "rawtypes" })
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
				CompanyPo company = dao.find(getBarno(custno));
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

	@SuppressWarnings("static-access")
	public String loadBar(Integer taskid, String groupname, String barno) {

		CustomerPo customer = custdao.find(groupname);

		if (customer == null) {
			customer = new CustomerPo();
			customer.setName(groupname);
			customer.setStatus("ACTIVE");
			custdao.save(customer);

		}

		if (dao.find(barno) != null)
			return "Company exists in MPP.";

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
					break;
				}
			}

			company.setCustId(customer.getId());
			company.setCreateuserno(getCurrentUser());
			company.setCreatedatetime(Calendar.getInstance());
			company.setUpdatedatetime(Calendar.getInstance());
			company.setUpdateuserno(getCurrentUser());

			CompanyPo po = new CompanyPo();
			uti.copyProperties(po, company);
			if (!isEmpty(po.getCostcenter())) {
				if (customer.getCostcenter() == null) {
					customer.setCostcenter("");
				}
				if (customer.getCostcenter().indexOf(po.getCostcenter()) == -1) {
					customer.setCostcenter(customer.getCostcenter() + ","
							+ po.getCostcenter());
				}
			}

			if (!isEmpty(po.getGmaxNo())) {
				if (customer.getGmax() == null) {
					customer.setGmax("");
				}
				if (customer.getGmax().indexOf(po.getGmaxNo()) == -1) {
					customer.setGmax(customer.getGmax() + "," + po.getGmaxNo());
				}
			}
			dao.update(customer);

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
				contact.setRelationId("C" + po.getId());
				contact.setFirstname(company.getContactfirstname1());
				contact.setLastname(company.getContactname1());
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

			int countOfPar = 0;
			try {
				String newParNo = service.getNewParNo(barno);
				countOfPar = Integer.parseInt(newParNo);
			} catch (Exception e) {
				countOfPar = 0;
			}

			for (int i = 1; i < countOfPar; i++) {
				double a = i;
				double b = countOfPar;
				double taskStatus = a / b * 100;
				Server.setTaskStatus(taskid, (int) taskStatus);
				try {
					String retVal = loadPar(groupname, barno + getParno(i));
					System.out.println(retVal);
				} catch (Exception e) {
					log.info("error when load par, Par No: " + barno
							+ getParno(i));
				}

			}
			return "success";
		}
		return "Company does not exist in PowerSuite.";

	}

	private String getParno(int i) {
		if (i < 10) {
			return "000" + i;
		} else if (i < 100) {
			return "00" + i;
		} else if (i < 1000) {
			return "0" + i;
		} else
			return "" + i;
	}

	@SuppressWarnings("static-access")
	private String loadPar(String groupname, String parno) {
		System.out.println(parno);
		if (dao.find(parno) != null)
			return "Traveler exists in MPP.";

		CompanyPo company = dao.find(getBarno(parno));

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
					traveler.setCompanyId(company.getId());
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

			if (!traveler.getStatus().equals("ACTIVE")) {
				return "Traveler is inactive, which will not be import to mpp.";
			}

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
						TravelerPo masterTravPo = travdao.find(parno);
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
						TravelerPo familyTrav = travdao.find(travelerNo);
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
						TravelerPo familyTrav = travdao.find(travelerNo);
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

	public String[] listCompanyOnline() {
		String[] companynames = new String[0];
		AxoService axoService = new AxoServiceProxy();
		try {
			ServMessage message = ServMessage.load(axoService.listCompanyOnline());
			if (message.isSuccess())
				companynames = message.getBody().toUpperCase().split(",");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return companynames;
	}

	private String getBarno(String parno) {
		return parno.substring(0, 6);
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
			data2DefMap.put(2, "customerRefernce");
			data2DefMap.put(4, "tripRequistition");
			data2DefMap.put(8, "jobNumber");
			data2DefMap.put(1024, "projectNumber");
			data2DefMap.put(2048, "accountNumber");
		}
		return data2DefMap;
	}
}
