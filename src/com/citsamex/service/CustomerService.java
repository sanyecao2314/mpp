package com.citsamex.service;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.citsamex.app.Server;
import com.citsamex.app.action.Page;
import com.citsamex.app.cfg.Element;
import com.citsamex.app.cfg.ElementMap;
import com.citsamex.app.util.DBUtil;
import com.citsamex.app.util.ServiceConfig;
import com.citsamex.service.action.Clause;
import com.citsamex.service.action.Customer;
import com.citsamex.service.db.ClauseDao;
import com.citsamex.service.db.ClausePo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.CustomerDao;
import com.citsamex.service.db.CustomerPo;
import com.citsamex.service.db.DiscountPo;
import com.citsamex.service.db.FeeCodeDetailPo;
import com.citsamex.service.db.FeeCodePo;
import com.citsamex.service.db.GmaxNoPo;
import com.citsamex.service.db.ReasonCodePo;

public class CustomerService extends CommonService implements ICustomerService {

	private CustomerDao dao;
	private ClauseDao clauseDao;

	public ClauseDao getClauseDao() {
		return clauseDao;
	}

	public void setClauseDao(ClauseDao clauseDao) {
		this.clauseDao = clauseDao;
	}

	public CustomerDao getDao() {
		return dao;
	}

	public void setDao(CustomerDao dao) {
		this.dao = dao;
	}

	@Override
	public ServiceMessage listCustomer(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<CustomerPo> polist = dao.list(map);
		List<Customer> list = new ArrayList<Customer>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Customer customer = new Customer();
			customer.setPo(polist.get(i));
			list.add(customer);
		}

		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage deleteCustomer(Long id) {
		CustomerPo po = (CustomerPo) dao.find(CustomerPo.class, id);
		if (po == null) {
			return new ServiceMessage("1003");
		}
		dao.deleteTravelers(po.getName());
		dao.deleteCompanys(po.getId());
		dao.delete(po);
		dao.deleteRelations("S" + po.getId());

		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(po.getId());
		return sm;
	}

	// active the customer and sub bar & par.
	@Override
	public ServiceMessage activeCustomer(Long id) {
		CustomerPo po = (CustomerPo) dao.find(CustomerPo.class, id);
		if (po == null) {
			return new ServiceMessage("1003");
		}
		if (po.getStatus().toUpperCase().equals("ACTIVE")) {
			po.setStatus("INACTIVE");
		} else {
			po.setStatus("ACTIVE");
		}
		dao.update(po);
		// dao.updateCompanyStatus(po.getName(), po.getStatus());
		// dao.updateTravelerStatus(po.getName(), po.getStatus());

		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage listCustomer() {
		@SuppressWarnings("unchecked")
		List<CustomerPo> polist = dao.list(new HashMap<String, Object>());
		List<Customer> list = new ArrayList<Customer>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Customer customer = new Customer();
			customer.setPo(polist.get(i));
			list.add(customer);
		}
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServiceMessage listClause(String CatalogNo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("catalog_no", CatalogNo);
		List<ClausePo> polist = clauseDao.list(map);
		List<Clause> list = new ArrayList<Clause>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Clause clause = new Clause();
			clause.setPo(polist.get(i));
			list.add(clause);
		}
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServiceMessage listClauseByString(Long compId, String CatalogNoString) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("catalog_no", CatalogNoString);
		List<ClausePo> polist = clauseDao.listByString(map);
		Map<String, String> variable = new HashMap<String, String>();

		List<DiscountPo> discounts = dao.listRelation("DiscountPo", "S" + compId);
		for (int i = 0; i < discounts.size(); i++) {
			DiscountPo disc = discounts.get(i);
			String varname = disc.getVarname();
			String value = disc.getDiscountRate();
			variable.put("${discount." + varname + "}", value);
		}

		List<FeeCodePo> feeCodes = dao.listRelation("FeeCodePo", "S" + compId);
		for (int i = 0; i < feeCodes.size(); i++) {
			FeeCodePo po = feeCodes.get(i);
			String varname = po.getVarname();
			String groupId = po.getGroupId();

			List<FeeCodeDetailPo> feeCodeDetails = dao.listFeeCodeDetailCurrent("S" + compId, po.getId(), groupId);

			for (int j = 0; j < feeCodeDetails.size(); j++) {
				FeeCodeDetailPo fd = feeCodeDetails.get(j);
				String prod = fd.getProductCode();
				String value = "";
				if (fd.getFeeMethod().equals("By Amt")) {
					value = String.valueOf(fd.getFeeAmt());
				} else {
					value = String.valueOf(fd.getFeePercent());
				}

				variable.put("${terms." + varname + "." + groupId + "." + prod + "}", value);
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
					content = content.replaceAll("\\$\\{" + oldVal.substring(2, oldVal.length() - 1) + "\\}", newVal);
				}
			}
			clause.setContent(content);

			list.add(clause);
		}
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ServiceMessage listCustomer(Integer currentPage, String quickSearch) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPage", currentPage);
		map.put("pageSize", Server.PROPS_PAGESIZE);
		map.put("quickSearch", quickSearch);
		List<CustomerPo> polist = dao.list(map);
		List<Customer> list = new ArrayList<Customer>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Customer customer = new Customer();
			customer.setPo(polist.get(i));
			list.add(customer);
		}
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(list);
		return sm;
	}

	// count total count of customer with conditions.
	@Override
	public ServiceMessage countCustomer(String quickSearch) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quickSearch", quickSearch);
		int count = dao.count(map);

		Page page = new Page(count);
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(page);
		return sm;
	}

	@Override
	public ServiceMessage createCustomer(Customer customer) {

		if (isEmpty(customer.getName())) {
			return new ServiceMessage("1001");
		}

		if (dao.find(customer.getName()) != null) {
			return new ServiceMessage("1002");
		}

		CustomerPo po = customer.buildCustomerPo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage("1000");
		customer.setPo(po);
		sm.setData(customer);
		return sm;
	}

	@Override
	public ServiceMessage updateCustomer(Customer customer) {
		CustomerPo po = customer.buildCustomerPo();
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage("1000");
		customer.setPo(po);
		sm.setData(customer);
		return sm;
	}

	@Override
	public ServiceMessage findCustomer(Long id) {
		CustomerPo po = (CustomerPo) dao.find(CustomerPo.class, id);
		Customer customer = new Customer();
		customer.setPo(po);
		ServiceMessage sm = new ServiceMessage("1000");
		sm.setData(customer);
		return sm;
	}

	@Override
	public ServiceMessage findCustomer(String name) {
		Customer customer = null;
		CustomerPo po = dao.find(name);
		ServiceMessage sm = new ServiceMessage("1000");
		if (po != null) {
			customer = new Customer();
			customer.setPo(po);
		} else {
			sm.setCode("1001");
		}

		sm.setData(customer);
		return sm;
	}

	@Override
	public ServiceMessage createClause(Clause clause) {
		if (isEmpty(clause.getNo()) || isEmpty(clause.getCatalog())) {
			return new ServiceMessage("2001");
		}

		// check if name is duplicated.
		if (clauseDao.find(clause.getCatalog(), clause.getNo()) != null) {
			return new ServiceMessage("2002");
		}
		ClausePo po = clause.buildClausePo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage("2000");
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage createClause(String catalog, String no, String content, String status) {
		Clause clause = new Clause();
		clause.setNo(no);
		clause.setContent(content);
		clause.setCatalog(catalog);
		clause.setStatus(status);

		if (isEmpty(clause.getNo()) || isEmpty(clause.getCatalog())) {
			return new ServiceMessage("2001");
		}

		// check if name is duplicated.
		if (clauseDao.find(catalog, no) != null) {
			return new ServiceMessage("2002");
		}

		ClausePo po = clause.buildClausePo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage("2000");
		clause.setPo(po);
		sm.setData(clause);
		return sm;
	}

	@Override
	public ServiceMessage updateClause(String catalog, String no, String content, String status) {

		ClausePo po = clauseDao.find(catalog, no);
		if (po == null) {
			return new ServiceMessage("2003");
		}
		po.setContent(content);
		po.setStatus(status);

		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());

		clauseDao.update(po);

		ServiceMessage sm = new ServiceMessage("2000");
		Clause clause = new Clause();
		clause.setPo(po);
		sm.setData(clause);
		return sm;
	}

	@Override
	public ServiceMessage updateClause(Clause clause) {
		ClausePo po = clause.buildClausePo();
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage("2000");
		clause.setPo(po);

		sm.setData(clause);
		return sm;
	}

	@Override
	public ServiceMessage deleteClause(String catalog, String no) {
		ClausePo po = clauseDao.find(catalog, no);
		if (po == null) {
			return new ServiceMessage("2003");
		}
		clauseDao.delete(po);

		ServiceMessage sm = new ServiceMessage("2000");
		sm.setData(po.getId());
		return sm;
	}

	@Override
	public ServiceMessage findClause(Long id) {
		Clause clause = null;
		ClausePo po = (ClausePo) dao.find(ClausePo.class, id);
		if (po != null) {
			clause = new Clause();
			clause.setPo(po);
		}
		ServiceMessage sm = new ServiceMessage("2000");
		sm.setData(clause);
		return sm;

	}

	@Override
	public ServiceMessage findClause(String catalog, String no) {
		Clause clause = null;
		ClausePo po = clauseDao.find(catalog, no);
		if (po != null) {
			clause = new Clause();
			clause.setPo(po);
		}
		ServiceMessage sm = new ServiceMessage("2000");
		sm.setData(clause);
		return sm;

	}

	@Override
	public ServiceMessage listClasue(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<ClausePo> polist = clauseDao.list(map);
		List<Clause> list = new ArrayList<Clause>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Clause clause = new Clause();
			clause.setPo(polist.get(i));
			list.add(clause);
		}
		ServiceMessage sm = new ServiceMessage("2000");
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCode(Long customerId) {
		List list = dao.listRelation("FeeCodePo", "S" + customerId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage createFeeCode(String varname, String feeType, String fareCode, String groupId, Long customerId) {
		FeeCodePo po = new FeeCodePo();
		po.setGroupId(groupId);
		po.setFareCode(fareCode);
		po.setFeeType(feeType);
		po.setVarname(varname);
		po.setRelationId("S" + customerId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateFeeCode(Long id, String groupId, String defaultGroup) {
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

	@Override
	public ServiceMessage deleteFeeCode(Long id) {
		FeeCodePo po = (FeeCodePo) dao.findRelation(FeeCodePo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	// public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId) {
	// @SuppressWarnings("rawtypes")
	// List list = dao.listFeeCodeDetail("S" + companyId, feeCodeId);
	// ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
	// sm.setData(list);
	// return sm;
	// }

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String groupId) {
		List list = dao.listFeeCodeDetail("S" + companyId, feeCodeId, groupId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String period, String groupId) {
		List list = dao.listFeeCodeDetail("S" + companyId, feeCodeId, period, groupId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listFeeCodeDetail(Long companyId) {
		List list = dao.listRelation("FeeCodeDetailPo", "S" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage createFeeCodeDetail(String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType, Long companyId, Long feeCodeId) {
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
		po.setRelationId("S" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateFeeCodeDetail(Long id, String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType) {
		FeeCodeDetailPo po = (FeeCodeDetailPo) dao.findRelation(FeeCodeDetailPo.class, id);
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

	@Override
	public ServiceMessage deleteFeeCodeDetail(Long id) {
		FeeCodeDetailPo po = (FeeCodeDetailPo) dao.findRelation(FeeCodeDetailPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_FEECODE_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage createDiscount(String varname, String prodId, String remark, String discountRate,
			Long companyId) {
		DiscountPo po = new DiscountPo();
		po.setProdId(prodId);
		po.setRemark(remark);
		po.setVarname(varname);
		po.setDiscountRate(discountRate);

		po.setRelationId("S" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateDiscount(Long id, String varname, String prodId, String remark, String discountRate) {
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

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listDiscount(Long companyId) {
		List list = dao.listRelation("DiscountPo", "S" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage deleteDiscount(Long id) {
		DiscountPo po = (DiscountPo) dao.findRelation(DiscountPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_DISCOUNT_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage createGmaxNo(String tmrId, String gmaxNo, String clientName, String parentName,
			String pricingMode, String remark, Long companyId) {
		GmaxNoPo po = new GmaxNoPo();
		po.setTmrId(tmrId);
		po.setGmaxNo(gmaxNo);
		po.setClientName(clientName);
		po.setParentName(parentName);
		po.setPricingMode(pricingMode);
		po.setRemark(remark);

		po.setRelationId("S" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_GMAXNO_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateGmaxNo(Long id, String tmrId, String gmaxNo, String clientName, String parentName,
			String pricingMode, String remark) {
		GmaxNoPo po = (GmaxNoPo) dao.findRelation(GmaxNoPo.class, id);
		po.setTmrId(tmrId);
		po.setGmaxNo(gmaxNo);
		po.setClientName(clientName);
		po.setParentName(parentName);
		po.setPricingMode(pricingMode);
		po.setRemark(remark);

		po.setUpdatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage(CODE_GMAXNO_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listGmaxNo(Long companyId) {
		List list = dao.listRelation("GmaxNoPo", "S" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_GMAXNO_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage deleteGmaxNo(Long id) {
		GmaxNoPo po = (GmaxNoPo) dao.findRelation(GmaxNoPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_GMAXNO_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage findGmaxNo(String gmaxNo) {
		GmaxNoPo po = dao.findGmaxNo(gmaxNo);
		ServiceMessage sm = new ServiceMessage(CODE_GMAXNO_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateStatusRemark(Long id, String remark) {
		CustomerPo customer = (CustomerPo) dao.find(CustomerPo.class, id);
		customer.setStatusRemark(remark);
		dao.update(customer);

		ServiceMessage sm = new ServiceMessage(CODE_CUSTOMER_SUCCESS);
		sm.setData(customer);
		return sm;
	}

	@Override
	public ServiceMessage createReasonCode(String reasonCode, String productCode, String description, Long companyId) {
		ReasonCodePo po = new ReasonCodePo();
		po.setReasonCode(reasonCode);
		po.setDescription(description);
		po.setProductCode(productCode);
		po.setRelationId("S" + companyId);
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage updateReasonCode(Long id, String reasonCode, String productCode, String description) {
		ReasonCodePo po = (ReasonCodePo) dao.findRelation(ReasonCodePo.class, id);
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

	@Override
	public ServiceMessage deleteReasonCode(Long id) {
		ReasonCodePo po = (ReasonCodePo) dao.findRelation(ReasonCodePo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listReasonCode(Long companyId) {
		List list = dao.listRelation("ReasonCodePo", "S" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_REASON_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage updateCreditCard(Long id, String content) {

		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new ByteArrayInputStream(content.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		CreditCardPo po = (CreditCardPo) dao.findRelation(CreditCardPo.class, id);

		CreditCardPo vo = (CreditCardPo) decoder.readObject();

		po.setVendor(vo.getVendor());
		po.setInstitution(vo.getInstitution());
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

	@Override
	public ServiceMessage createCreditCard(String content) {
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(new ByteArrayInputStream(content.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		CreditCardPo po = (CreditCardPo) decoder.readObject();
		po.setRelationId("S" + po.getRelationId());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdatedatetime(Calendar.getInstance());
		po.setCreateuserno(getCurrentUser());
		po.setUpdateuserno(getCurrentUser());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ServiceMessage listCreditCard(Long companyId) {
		List list = dao.listRelation("CreditCardPo", "S" + companyId);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage deleteCreditCard(Long id) {
		CreditCardPo po = (CreditCardPo) dao.findRelation(CreditCardPo.class, id);
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage(CODE_CREDITCARD_SUCCESS);
		sm.setData(po);
		return sm;
	}

	@Override
	public Element createBarCfg(String name, String value, String left, String top, String path, String path2,
			String seq, String company, String description) {
		Element element = new Element();
		element.setName(name);
		element.setText(value.trim());
		element.setColor("");
		element.setBgColor("");
		element.setLeft(left);
		element.setTop(top);
		element.setPath(path);
		element.setPath2(path2);
		element.setSeq(seq);
		element.setCompany(company);
		element.setDescription(description);
		element.setCreatedatetime(Calendar.getInstance());
		element.setUpdatedatetime(Calendar.getInstance());
		element.setCreateusername(getCurrentUser());
		element.setUpdateusername(getCurrentUser());
		dao.save(element);
		return element;
	}

	@Override
	public void createElementMap(String field, String psfield, String company) {
		ElementMap elementMap = new ElementMap();
		elementMap.setField(field);
		elementMap.setPsfield(psfield);
		elementMap.setCpid(company);

		dao.saveOrupdate(elementMap);
	}

	@Override
	public List listElementMap(String company) {
		return dao.listElementMap(company);
	}

	@Override
	public Element updateBarCfg(Long id, String value) {
		Element element = (Element) dao.find(Element.class, id);
		element.setText(value.trim());
        element.setUpdatedatetime(Calendar.getInstance());
        element.setUpdateusername(getCurrentUser());		
		dao.update(element);
		return element;
	}

	@Override
	public Element updateBarCfg(Long id, String name, String value) {
		Element element = (Element) dao.find(Element.class, id);
		element.setName(name);
		element.setText(value.trim());
		element.setUpdatedatetime(Calendar.getInstance());
		element.setUpdateusername(getCurrentUser());
		dao.update(element);
		return element;
	}

	@Override
	public Element deleteBarCfg(Long id) {
		Element element = (Element) dao.find(Element.class, id);

		dao.deleteBarCfg(element.getId(), element.getPath2());
		return element;
	}

	@Override
	public Element findElement(String name, String value) {

		return dao.findElement(name, value);
	}

	@Override
	public List listBarCfg(String companyName) {
		return dao.listElement(companyName);
	}

	@Override
	public void transportBarCfg(String companyName) {
		Element company = dao.findElement("COMPANY", companyName);

		Map<String, List> map = new HashMap<String, List>();
		List<Element> list = dao.listElement(companyName);
		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			String path = element.getPath();
			List subLs = null;
			if (map.containsKey(path)) {
				subLs = (List) map.get(path);
			} else {
				subLs = new ArrayList();
				map.put(path, subLs);
			}
			subLs.add(element);
		}
		String xml = buildCfgXml(company, map);
		writeCfg(xml);
		transportBarConfigRelationMap2AXO(companyName);

	}

	private String buildCfgXml(Element element, Map map) {

		String tagName = element.getName();
		String props = "";

		if (element.getName().startsWith("FIELD")) {
			tagName = "FIELD";
			String name = element.getName().substring(6);
			props = "name=\"" + charFilter(name) + "\"";
			if (!isEmpty(element.getText())) {
				props += " type=\"OPTION\"";
				props += " value=\"" + charFilter(element.getText()) + "\"";
			} else {
				props += " type=\"TEXT\"";
				props += " value=\"\"";
			}

		} else {
			props = "name=\"" + charFilter(element.getText()) + "\"";
		}

		props += " description=\"" + charFilter(element.getDescription()) + "\"";

		StringBuffer buffer = new StringBuffer();
		String path = String.valueOf(element.getId());
		if (map.containsKey(path)) {
			List list = (List) map.get(path);
			for (int i = 0; i < list.size(); i++) {
				Element ele1 = (Element) list.get(i);
				buffer.append(buildCfgXml(ele1, map));
			}
		}
		return "<" + tagName + " " + props + ">" + buffer.toString() + "</" + tagName + ">";

	}

	private void writeCfg(String xml) {
		FileOutputStream fos = null;

		xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
		try {
			String pipe = ServiceConfig.getProperty("CFG_PIPENAME");
			long now = new Date().getTime();
			String filename = now + ".xml";
			fos = new FileOutputStream(pipe + "/" + filename);
			fos.write(xml.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String charFilter(String xml) {
		if (xml == null || xml.equals(""))
			return "";
		xml = xml.replaceAll("&#x0;", "");
		xml = xml.replaceAll("&lt;", "<");
		xml = xml.replaceAll("&gt;", ">");
		xml = xml.replaceAll("&apos;", "'");
		xml = xml.replaceAll("&quot;", "\"");
		xml = xml.replaceAll("&amp;", "&");

		xml = xml.replaceAll("&", "&amp;");
		xml = xml.replaceAll("<", "&lt;");
		xml = xml.replaceAll(">", "&gt;");
		xml = xml.replaceAll("'", "&apos;");
		xml = xml.replaceAll("\"", "&quot;");

		return xml;
	}

    @Override
    public List listBarCfgMain(String customer, String parent) {
        return dao.listElementMain(customer, parent);
    }

    @Override
    public List listBarCfgSub(String customer, String parent) {
        return dao.listElementMain(customer, parent);
    }
    
    private void transportBarConfigRelationMap2AXO(String cpid){
    	Map<String, String> fieldsRelationMap = new HashMap<String, String>();
    	fieldsRelationMap.put("UESR_JOBTITLE", "jobTitle");
    	//fieldsRelationMap.put("USER_BAR_PATH", "department2");
    	fieldsRelationMap.put("USER_COSTCENTER", "costcenter");
    	fieldsRelationMap.put("USER_DEPARTMENT", "department1");
    	fieldsRelationMap.put("USER_EMPNO", "employeeNo");
    	fieldsRelationMap.put("USER_TR", "tr");
    	
    	String sql = "SELECT FIELD, PS_FIELD FROM T_BAR_CFG_MAP WHERE PS_FIELD !='' AND CP_ID='" + cpid + "'";
    	String insertTmp = "INSERT INTO BAR_CONFIG_MAP (FIELD, PS_FIELD, FIELD_MAPPING, CP_ID) VALUES (";
    	String mappingSql;
    	try {
			DBUtil.excuteUpdate("DELETE FROM BAR_CONFIG_MAP WHERE CP_ID='" + cpid + "'", "AXO_PRD");
		} catch (Exception e1) {
			Logger.getRootLogger().error("axo bar config mapping", e1);
		}
    	List rs = DBUtil.querySql(sql, "MPP");
    	for (int i =0; i < rs.size(); i++) {
    		Map row = (Map) rs.get(i);
    		String field = (String) row.get("FIELD");
    		String psField = (String) row.get("PS_FIELD");
    		String mppField = fieldsRelationMap.get(psField);
    		if (mppField != null) {
    			mappingSql = insertTmp + "'" + field + "'," + "'" + psField + "',";
    			mappingSql = mappingSql + "'" + mppField + "'," + "'" + cpid + "')";
    			try {
					DBUtil.excuteUpdate(mappingSql, "AXO_PRD");
				} catch (Exception e) {
					Logger.getRootLogger().error("axo bar config mapping", e);
				}
    		}
    	}
    	
    }
}
