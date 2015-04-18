package com.citsamex.service;

import java.util.List;
import java.util.Map;

import com.citsamex.app.cfg.Element;
import com.citsamex.service.action.Clause;
import com.citsamex.service.action.Customer;

public interface ICustomerService extends BaseInterface {
	public ServiceMessage createCustomer(Customer customer);

	public ServiceMessage updateCustomer(Customer customer);

	public ServiceMessage findCustomer(Long id);

	public ServiceMessage findCustomer(String name);

	public ServiceMessage deleteCustomer(Long id);

	public ServiceMessage listCustomer();

	public ServiceMessage listClause(String CatalogNo);

	public ServiceMessage listClauseByString(Long compId, String CatalogNoString);

	public ServiceMessage listCustomer(Integer currentPage, String quickSearch);

	public ServiceMessage listCustomer(Map<String, Object> map);

	public ServiceMessage countCustomer(String quickSearch);

	public ServiceMessage createClause(Clause clause);

	public ServiceMessage createClause(String no, String content, String catalog, String status);

	public ServiceMessage updateClause(Clause clause);

	public ServiceMessage findClause(Long id);

	public ServiceMessage findClause(String catalog, String no);

	public ServiceMessage listClasue(Map<String, Object> map);

	public ServiceMessage updateClause(String catalog, String no, String content, String status);

	public ServiceMessage deleteClause(String catalog, String no);

	public ServiceMessage listFeeCode(Long customerId);

	public ServiceMessage createFeeCode(String varname, String feeType, String fareCode, String groupId, Long companyId);

	public ServiceMessage updateFeeCode(Long id, String groupId, String defaultGroup);

	public ServiceMessage deleteFeeCode(Long id);

	public ServiceMessage listFeeCodeDetail(Long companyId);

	// public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String groupId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String period, String groupId);

	public ServiceMessage createFeeCodeDetail(String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType, Long companyId, Long feeCodeId);

	public ServiceMessage updateFeeCodeDetail(Long id, String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType);

	public ServiceMessage deleteFeeCodeDetail(Long id);

	public ServiceMessage createDiscount(String varname, String prodId, String remark, String discountRate,
			Long companyId);

	public ServiceMessage updateDiscount(Long id, String varname, String prodId, String remark, String discountRate);

	public ServiceMessage listDiscount(Long companyId);

	public ServiceMessage deleteDiscount(Long id);

	public ServiceMessage activeCustomer(Long id);

	public ServiceMessage createGmaxNo(String tmrId, String gmaxNo, String clientName, String parentName,
			String pricingMode, String remark, Long companyId);

	public ServiceMessage updateGmaxNo(Long id, String tmrId, String gmaxNo, String clientName, String parentName,
			String pricingMode, String remark);

	public ServiceMessage listGmaxNo(Long companyId);

	public ServiceMessage deleteGmaxNo(Long id);

	public ServiceMessage findGmaxNo(String gmaxNo);

	public ServiceMessage updateStatusRemark(Long id, String remark);

	public ServiceMessage createReasonCode(String reasonCode, String productCode, String description, Long companyId);

	public ServiceMessage updateReasonCode(Long id, String reasonCode, String productCode, String description);

	public ServiceMessage deleteReasonCode(Long id);

	public ServiceMessage listReasonCode(Long companyId);

	public ServiceMessage updateCreditCard(Long id, String content);

	public ServiceMessage createCreditCard(String content);

	public ServiceMessage listCreditCard(Long companyId);

	public ServiceMessage deleteCreditCard(Long id);

	public Element createBarCfg(String name, String value, String left, String top, String path, String path2,
			String seq, String company, String description);

	public Element updateBarCfg(Long id, String name, String value);

	public Element deleteBarCfg(Long id);

	public Element findElement(String name, String value);

	public List listBarCfg(String comanyName);
	
	public List listBarCfgMain(String customer, String parent);
	
	public List listBarCfgSub(String customer,String parent);

	public void transportBarCfg(String companyName);

	public Element updateBarCfg(Long id, String value);

	public void createElementMap(String field, String psfield, String company);

	public List listElementMap(String companyName);
}
