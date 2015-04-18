package com.citsamex.service;

import java.util.List;

import com.citsamex.service.action.Company;
import com.citsamex.service.db.AddressPo;
import com.citsamex.service.db.DBIPo;
import com.citsamex.service.db.TravelerPo;

public interface ICompanyService {

	public ServiceMessage create(Company company);

	public ServiceMessage findCustomer(String name);

	public ServiceMessage findCompany(String companyNo);

	public ServiceMessage findCompany(Long id);

	public ServiceMessage updateCompany(Company company);

	public ServiceMessage createAddress(String addressType, String city, String zipCode, String address, Long companyId);

	public ServiceMessage listAddress(Long companyId);

	public ServiceMessage findAddress(Long id);

	public ServiceMessage updateAddress(Long id, String address);

	public ServiceMessage createContact(String firstname, String lastname, String firstname_en, String lastname_en,
			String homephone, String officephone, String title, String faxno, String email, String mobile,
			Long companyId);

	public ServiceMessage listContact(Long companyId);

	public ServiceMessage findContact(Long id);

	public ServiceMessage updateContact(Long id, String firstname, String lastname, String firstname_en,
			String lastname_en, String homephone, String officephone, String title, String faxno, String email,
			String mobile);

	public ServiceMessage deleteAddress(Long id);

	public ServiceMessage deleteContact(Long id);

	public ServiceMessage createDiscount(String varname, String prodId, String remark, String discountRate,
			Long companyId);

	public ServiceMessage listClauseByString(Long compId, String CatalogNoString);

	public ServiceMessage listDiscount(Long companyId);

	public ServiceMessage deleteDiscount(Long id);

	public ServiceMessage updateDiscount(Long id, String varname, String prodId, String remark, String discountRate);

	public ServiceMessage createEBill(String fieldName, String defaultValue, String mandatory, Long companyId);

	public ServiceMessage updateEBill(Long id, String fieldName, String defaultValue, String mandatory);

	public ServiceMessage deleteEBill(Long id);

	public ServiceMessage listEBill(Long companyId);

	public ServiceMessage countCompany(String companyName, String quicksearch, String quicksearchName);

	public ServiceMessage listCompany(Integer currentPage, String companyName, String quicksearch,
			String quicksearchName);

	public ServiceMessage deleteCompany(Long id);

	public ServiceMessage listFeeCode(Long companyId);

	// public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId);

	public ServiceMessage createFeeCode(String varname, String feeType, String fareCode, String groupId, Long companyId);

	public ServiceMessage updateFeeCode(Long id, String groupId, String defaultGroup);

	public ServiceMessage deleteFeeCode(Long id);

	public ServiceMessage listFeeCodeDetail(Long companyId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String groupId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId, String period, String groupId);

	public ServiceMessage createFeeCodeDetail(String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType, Long companyId, Long feeCodeId);

	public ServiceMessage updateFeeCodeDetail(Long id, String groupId, String validPeriod, String productCode,
			Double moneyRangeFrom, Double moneyRangeTo, Double feeAmt, Double feePercent, String feeMethod,
			String feeType);

	public ServiceMessage deleteFeeCodeDetail(Long id);

	public ServiceMessage updateCreditCard(Long id, String content);

	public ServiceMessage createCreditCard(String content);

	public ServiceMessage listCreditCard(Long companyId);

	public ServiceMessage deleteCreditCard(Long id);

	public ServiceMessage createReasonCode(String reasonCode, String productCode, String description, Long companyId);

	public ServiceMessage updateReasonCode(Long id, String reasonCode, String productCode, String description);

	public ServiceMessage deleteReasonCode(Long id);

	public ServiceMessage listReasonCode(Long companyId);

	public ServiceMessage createPurposeCode(String purposeCode, String description, Long companyId);

	public ServiceMessage updatePrposeCode(Long id, String purposeCode, String description);

	public ServiceMessage deletePurposeCode(Long id);

	public ServiceMessage listPurposeCode(Long companyId);

	public ServiceMessage activeCompany(Long id);

	public void updateDBI(DBIPo po);

	public ServiceMessage listDBI(Long companyId);

	public ServiceMessage updateStatusRemark(Long id, String remark, String status, String travelerList);
	
	void updateAndSynchTraveler(List<TravelerPo> travelToUpdateAndSynch, String status, String remark);

	public ServiceMessage createDbi(DBIPo po);

	public ServiceMessage createAddress(AddressPo po, String compId);

	public ServiceMessage updateAddress(Long id, String address, String city, String zipCode);

	public String loadBar(Integer taskid, String groupname, String barno);

	public String[] listCompanyOnline();
}
