package com.citsamex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.citsamex.app.Server;
import com.citsamex.app.util.CommonUtil;
import com.citsamex.service.db.CommonDao;

public class CommonService extends Base implements ICommonService {

	// customer
	public final static String CODE_CUSTOMER_SUCCESS = "1000";
	// company
	public final static String CODE_COMPANY_SUCCESS = "4000";
	// address.
	public final static String CODE_ADDRESS_SUCCESS = "5000";
	public final static String CODE_DISCOUNT_SUCCESS = "6000";
	public final static String CODE_EBILLING_SUCCESS = "7000";
	public final static String CODE_TRAVELER_SUCCESS = "8000";
	public final static String CODE_CREDITCARD_SUCCESS = "9000";
	public final static String CODE_MEMBER_SUCCESS = "11000";
	public final static String CODE_EXTRA_SUCCESS = "12000";
	public final static String CODE_PREFERENCE_SUCCESS = "13000";
	public final static String CODE_FEECODE_SUCCESS = "14000";
	public final static String CODE_REASON_SUCCESS = "15000";
	public final static String CODE_PURPOSE_SUCCESS = "16000";
	public final static String CODE_GMAXNO_SUCCESS = "17000";
	public final static String CODE_DBI_SUCCESS = "18000";
	public final static String CODE_VISA_SUCCESS = "19000";
	// error
	public final static String CODE_DISCOUNT_ERROR = "6001";
	public final static String CODE_FEECODE_ERROR = "14001";

	public static JSONObject polyphone1 = null;
	public static JSONObject polyphone2 = null;

	public final static String filename1 = "polyphone.dql";
	public final static String filename2 = "polyphone.dqf";

	private static long modifydate1;
	private static long modifydate2;

	protected CommonUtil uti = new CommonUtil();

	public boolean isEmpty(String a) {
		if (a == null || a.isEmpty())
			return true;
		return false;
	}

	public String getCurrentUser() {
		return Server.getCurrentUsername();
	}

	private CommonDao commonDao;

	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	// list code;
	@SuppressWarnings("rawtypes")
	private List<Code> listCode(String key, String value, String sql) {
		List list = commonDao.execute(sql);
		List<Code> codes = new ArrayList<Code>(list.size());

		for (int i = 0; i < list.size(); i++) {
			Map entity = (Map) list.get(i);
			codes.add(new Code((String) entity.get(key), (String) entity.get(value)));
		}

		return codes;
	}

	@Override
	public List<Code> listPassportType() {
		String sql = "SELECT PASSPORT_KEY, PASSPORT_VALUE FROM T_C_PASSPORT_TYPE";
		return listCode("PASSPORT_KEY", "PASSPORT_VALUE", sql);
	}

	@Override
	public List<Code> listCardVendor() {
		String sql = "SELECT CREDIT_VENDOR_KEY, CREDIT_VENDOR_VALUE FROM T_C_CREDIT_VENDOR";
		return listCode("CREDIT_VENDOR_KEY", "CREDIT_VENDOR_VALUE", sql);
	}

	@Override
	public List<Code> listCardType() {
		String sql = "SELECT CREDIT_TYPE_KEY, CREDIT_TYPE_VALUE FROM T_C_CREDIT_TYPE";
		return listCode("CREDIT_TYPE_KEY", "CREDIT_TYPE_VALUE", sql);
	}

	@Override
	public List<Code> listMerchantNo() {
		String sql = "SELECT MERCHANT_NO_KEY, MERCHANT_NO_VALUE FROM T_C_MERCHANT_NO";
		return listCode("MERCHANT_NO_KEY", "MERCHANT_NO_VALUE", sql);
	}

	@Override
	public List<Code> listCountry() {
		String sql = "SELECT COUNTRY_KEY, COUNTRY_VALUE FROM T_C_COUNTRY_CODE";
		return listCode("COUNTRY_KEY", "COUNTRY_VALUE", sql);
	}

	@Override
	public List<Code> listDBIKey() {
		String sql = "SELECT DBI_KEY, DBI_VALUE FROM T_C_DBI";
		return listCode("DBI_KEY", "DBI_VALUE", sql);
	}

	@Override
	public List<Code> listInstituteKey() {
		String sql = "SELECT ID, CREDIT_INST_KEY, CREDIT_INST_VALUE FROM T_C_CREDIT_INSTITUTE";
		return listCode("CREDIT_INST_KEY", "CREDIT_INST_VALUE", sql);
	}

	@Override
	public List<Code> listPreferKey(String key) {
		String sql = "SELECT CATEGORY, VALUE FROM T_C_PREFER WHERE CATEGORY='" + key + "'";
		return listCode("CATEGORY", "VALUE", sql);
	}

	@Override
	public List<Code> listBarConfigField(String type) {
		String sql = "SELECT FIELDNAME, FIELDVALUE FROM T_C_BAR_CONFIG WHERE TYPE='" + type + "'";
		return listCode("FIELDNAME", "FIELDVALUE", sql);
	}

	@Override
	public void updateFieldDesc(String parno, String field, String description, String lv) {
		String sql = "";

		sql = "DELETE FROM T_C_FIELD_DESC WHERE ENTITYNO='" + parno + "' AND FIELDNAME='" + field + "'";
		commonDao.executeUpdate(sql);
		if (lv.indexOf("P") != -1) {
			sql = "INSERT INTO T_C_FIELD_DESC( ENTITYNO, FIELDNAME, DESCRIPTION)  VALUES('" + parno + "', '" + field
					+ "','" + description + "')";
			commonDao.executeUpdate(sql);
		}

		String barno = parno.substring(0, 6);
		sql = "DELETE FROM T_C_FIELD_DESC WHERE ENTITYNO='" + barno + "' AND FIELDNAME='" + field + "'";
		commonDao.executeUpdate(sql);
		if (lv.indexOf("B") != -1) {
			sql = "INSERT INTO T_C_FIELD_DESC( ENTITYNO, FIELDNAME, DESCRIPTION)  VALUES('" + barno + "', '" + field
					+ "','" + description + "')";
			commonDao.executeUpdate(sql);
		}

		String group = barno.substring(3, 6);

		if (lv.indexOf("G") != -1) {
			sql = "DELETE FROM T_C_FIELD_DESC WHERE ENTITYNO='" + group + "' AND FIELDNAME='" + field + "'";
			commonDao.executeUpdate(sql);

			sql = "INSERT INTO T_C_FIELD_DESC( ENTITYNO, FIELDNAME, DESCRIPTION)  VALUES('" + group + "', '" + field
					+ "','" + description + "')";
			commonDao.executeUpdate(sql);
		}
	}

	public List<Code> queryFieldsDesc(String parno) {
		String barno = parno.substring(0, 6);
		String group = barno.substring(3, 6);
		String sql = "SELECT ENTITYNO, FIELDNAME, DESCRIPTION FROM T_C_FIELD_DESC WHERE ENTITYNO IN ('" + parno + "','"
				+ barno + "','" + group + "') ORDER BY LEN(ENTITYNO) DESC";

		List list = commonDao.execute(sql);
		List<Code> codes = new ArrayList<Code>(list.size());

		HashMap map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			Map entity = (Map) list.get(i);
			String key = (String) entity.get("FIELDNAME");
			String value = (String) entity.get("DESCRIPTION");

			if (!map.containsKey(key)) {
				map.put(key, key);
				codes.add(new Code(key, value));
			}
		}

		return codes;
	}

	@Override
	public String findPolyphone1(String src, String pinyin) {
		String val = "";

		val = findDict(filename1, src);
		if (val.isEmpty()) {
			val = pinyin;
		}
		saveDict(filename1, src, pinyin);

		return val;
	}

	@Override
	public String findPolyphone2(String src, String pinyin) {
		String val = "";

		String[] pys = pinyin.split(";");
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (c > '\u4E00' && c < '\u9FA5') {
				String oVal = findDict(filename2, String.valueOf(c));
				if (oVal.isEmpty()) {
					val += pys[i];
				} else {
					val += oVal;
				}
				saveDict(filename2, String.valueOf(c), pys[i]);
				
				if (i < src.length() - 1) {
					val += " ";
				}
			} else {
				val += pys[i];
			}
		}

		return val;
	}

	private synchronized String findDict(String filename, String keyword) {

		String val = "";
		File file = new File(filename);
		boolean isModify = false;
		if (filename.equals(filename1)) {
			isModify = file.exists() && file.lastModified() > modifydate1;
			if (polyphone1 == null || isModify) {
				polyphone1 = JSONObject.fromObject(readJson(filename));
			}
			if (polyphone1.containsKey(keyword)) {
				val = (String) polyphone1.get(keyword);
			}
			modifydate1 = file.lastModified();

		} else {
			isModify = file.exists() && file.lastModified() > modifydate2;

			if (polyphone2 == null || isModify) {
				polyphone2 = JSONObject.fromObject(readJson(filename));
			}
			if (polyphone2.containsKey(keyword)) {
				val = (String) polyphone2.get(keyword);
			}
			modifydate2 = file.lastModified();
		}

		return val;
	}

	private String readJson(String filename) {
		String json = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			byte[] bs = new byte[fis.available()];
			fis.read(bs);
			json = new String(bs);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (json == null || json.trim().equals(""))
			json = "{}";
		return json;
	}

	private void writeJson(String filename, String json) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
			fos.write(json.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void saveDict(String filename, String keyword, String value) {

		if (value.isEmpty() || value == null)
			return;

		value = value.toUpperCase();

		if (filename.equals(filename1)) {
			if (polyphone1.containsKey(keyword)) {
				String oVal = (String) polyphone1.get(keyword);
				if (oVal.equals(value) || oVal.startsWith(value + "/") || oVal.endsWith("/" + value)
						|| oVal.indexOf("/" + value + "/") != -1) {
					return;
				} else {
					value = oVal + "/" + value;
				}
			}
			polyphone1.put(keyword, value);
			writeJson(filename, polyphone1.toString());
		} else {
			if (polyphone2.containsKey(keyword)) {
				String oVal = (String) polyphone2.get(keyword);
				if (oVal.equals(value) || oVal.startsWith(value + "/") || oVal.endsWith("/" + value)
						|| oVal.indexOf("/" + value + "/") != -1) {
					return;
				} else {
					value = oVal + "/" + value;
				}
			}
			polyphone2.put(keyword, value);
			writeJson(filename, polyphone2.toString());
		}
	}

	@Override
	public Integer addTask() {
		return Server.addTask();
	}

	@Override
	public Integer getTaskStatus(Integer taskid) {
		return Server.getTaskStatus(taskid);
	}

	@Override
	public String findTravelerEnname(String nationality1, String pinyin) {
		String ret = "";
		if (nationality1 != null && nationality1.toUpperCase().equals("CN")) {
			ret = pinyin.replace(";", " ");
		} else {
			ret = pinyin.replace(";", " ");
		}
		return ret;
	}

}
