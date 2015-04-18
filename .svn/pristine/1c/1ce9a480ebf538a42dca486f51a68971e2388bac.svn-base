package com.citsamex.service;

import java.util.List;

public interface ICommonService extends BaseInterface {

	public List<Code> listPassportType();

	public List<Code> listCardVendor();

	public List<Code> listCardType();

	public List<Code> listMerchantNo();

	public List<Code> listCountry();

	public List<Code> listDBIKey();

	public List<Code> listInstituteKey();

	public List<Code> listPreferKey(String key);

	public Integer addTask();

	public Integer getTaskStatus(Integer taskid);

	public void updateFieldDesc(String parno, String field, String value, String lv);

	public List<Code> queryFieldsDesc(String parno);

	public List<Code> listBarConfigField(String type);

	public String findPolyphone1(String src, String pinyin);

	public String findPolyphone2(String src, String pinyin);
	
	public String findTravelerEnname(String nationality1, String pinyin);
}
