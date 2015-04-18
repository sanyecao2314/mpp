package com.citsamex.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.citsamex.service.action.Traveler;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.vo.CreditCard;

public interface ITravelerService {

	public ServiceMessage listTraveler(Map<String, Object> map);

	public ServiceMessage deleteTraveler(Long id);

	public ServiceMessage listTraveler();

	public ServiceMessage create(Traveler traveler);

	public ServiceMessage findTraveler(Long id);

	public ServiceMessage findTraveler(String name);

	public ServiceMessage updateTraveler(Traveler traveler);

	public ServiceMessage createAddress(String addressType, String address,
			String city, String zipCode, Long companyId);

	public ServiceMessage listAddress(Long companyId);

	public ServiceMessage findAddress(Long id);

	public ServiceMessage updateAddress(Long id, String address, String city,
			String zipCode);

	public ServiceMessage updateCreditCard(Long id, String content);

	public ServiceMessage createCreditCard(String content);

	public ServiceMessage createCreditCard(CreditCard credit);

	public ServiceMessage createContact(String firstname, String lastname,
			String firstname_en, String lastname_en, String homephone,
			String officephone, String title, String faxno, String email,
			String mobile, Long companyId);

	public void transport2Esb(String custno, String xml);

	public ServiceMessage listContact(Long companyId);

	public ServiceMessage listCreditCard(Long companyId);

	public ServiceMessage findContact(Long id);

	public ServiceMessage deleteContact(Long id);

	public ServiceMessage deleteAddress(Long id);

	public ServiceMessage deleteCreditCard(Long id);
	
	public ServiceMessage updateContact(Long id, String firstname,
			String lastname, String firstname_en, String lastname_en,
			String homephone, String officephone, String title, String faxno,
			String email, String mobile);

	public ServiceMessage createEBill(String fieldName, String defaultValue,
			String mandatory, Long companyId);

	public ServiceMessage updateEBill(Long id, String fieldName,
			String defaultValue, String mandatory);

	public ServiceMessage deleteEBill(Long id);

	public ServiceMessage listEBill(Long companyId);

	public ServiceMessage countTraveler(String companyName, String quicksearch,
			String quicksearchName, String quicksearchCreate,
			String quicksearchModify, Integer eBilling, String axo);

	public ServiceMessage listTraveler(Integer currentPage, String companyName,
			String quicksearch, String quicksearchName,
			String quicksearchCreate, String quicksearchModify,
			Integer eBilling, String axo);

	public ServiceMessage createMember(String prodType, String memberNo,
			String memberName, String memberCode, String memberIssue,
			String expire, String remark, Long companyId);

	public ServiceMessage updateMember(Long id, String prodType,
			String memberNo, String memberName, String memberCode,
			String memberIssue, String expire, String remark);

	public ServiceMessage deleteMember(Long id);

	public ServiceMessage listMember(Long companyId);

	public ServiceMessage createExtra(String level, String fieldName,
			String mandatory, String action, String displayOrder,
			String defaultValue, Long companyId);

	public ServiceMessage updateExtra(Long id, String level, String fieldName,
			String mandatory, String action, String displayOrder,
			String defaultValue);

	public ServiceMessage deleteExtra(Long id);

	public ServiceMessage listExtra(Long companyId);

	public ServiceMessage createPrefer(String catalog, String type,
			String value1, String value2, String remark, Long travId);

	public ServiceMessage updatePrefer(Long id, String catalog, String type,
			String value1, String value2, String remark);

	public ServiceMessage deletePrefer(Long id);

	public ServiceMessage listPrefer(Long companyId);

	public String getMaxTravelerNo(String companyNo);

	public String getMaxPotentialTravelerNo(String travelerNo, String prefix);

	public ServiceMessage listFeeCode(Long customerId);

	public ServiceMessage createFeeCode(String varname, String feeType,
			String fareCode, String groupId, Long companyId);

	public ServiceMessage updateFeeCode(Long id, String groupId,
			String defaultGroup);

	public ServiceMessage deleteFeeCode(Long id);

	public ServiceMessage listFeeCodeDetail(Long companyId);

	// public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String groupId);

	public ServiceMessage listFeeCodeDetail(Long companyId, Long feeCodeId,
			String period, String groupId);

	public ServiceMessage createFeeCodeDetail(String groupId,
			String validPeriod, String productCode, Double moneyRangeFrom,
			Double moneyRangeTo, Double feeAmt, Double feePercent,
			String feeMethod, String feeType, Long companyId, Long feeCodeId);

	public ServiceMessage updateFeeCodeDetail(Long id, String groupId,
			String validPeriod, String productCode, Double moneyRangeFrom,
			Double moneyRangeTo, Double feeAmt, Double feePercent,
			String feeMethod, String feeType);

	public ServiceMessage deleteFeeCodeDetail(Long id);

	public ServiceMessage copyDiscount(String srcNo, String destId);

	public ServiceMessage copyTerms(String srcNo, String destId);

	public ServiceMessage activeTraveler(Long id);

	public Object lock(String customerNo);

	public Object unlock(String customerNo);

	public Object synchronize(String name, Integer hasChild,
			Integer hasRelation, String systems);
	
	public Object synchronize(String name, Integer hasChild,
			Integer hasRelation, String systems,String priority);
	
	public Object synchronize(TravelerPo travelerPo, Integer hasChild,
			Integer hasRelation, String systems, String priority);
	
	public Object synchronize(TravelerPo travelerPo, Integer hasChild,
			Integer hasRelation, String systems);

	public ServiceMessage updateStatusRemark(Long id, String remark,
			String status);

	public String loadPar(String groupname, String parNo);

	public void upload(File files) throws Exception;

	public String getErrorMessage();

	public List<CompanyPo> getCompanySyncStatus(String companyNos);

	public List<TravelerPo> getTravelerSyncStatus(String travNos);

	public Object setDirty(String customerNo, Integer dirty);

	public String changeBar(String travelerNo, String newParNo, String newBar,
			Integer isActiveCur, String remark);

	public String changeBar(String travelerNo, String newParNo, String newBar, String tmrPar,
			Integer isInActiveCur, String statusRemark);

	public ServiceMessage createVisa(String country, String visaType,
			String visaEntry, String visaNo, String visaissuedate,
			String visaexpiredate, String visaRemark, String purpose,
			String noOfEntry, Long companyId);

	public ServiceMessage updateVisa(Long id, String country, String visaType,
			String visaEntry, String visaNo, String visaissuedate,
			String visaexpiredate, String visaRemark, String purpose,
			String noOfEntry);

	public ServiceMessage listVisa(Long companyId);

	public ServiceMessage findVisa(Long id);

	public ServiceMessage deleteVisa(Long id);

	public ServiceMessage validate(String objectName);

	public void resetValidator();

	public String buildTravelerXML(String customerNo, boolean hasRelation,
			String system);
	
	public String buildTravelerXML(String customerNo, boolean hasRelation,
			String system, boolean axoBarPathOnly);
	
	public String buildTravelerXML(TravelerPo travelerPo, boolean hasRelation,
			String system);

	public String[] preProcessUpload(File file) throws Exception;

	public List initUploadColumnMapp();

	public String processUpload(Integer taskid, String filename, String mappings)
			throws Exception;

	public String checkUpload(String filename, String mappings)
			throws Exception;
	public ServiceMessage findByCompanyNo(String companyNo);
	public String getCardTypeByCardbin(String cardbin);
	public ServiceMessage updateCreditCardPo(CreditCardPo po);
}
