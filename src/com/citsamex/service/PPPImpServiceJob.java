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

public class PPPImpServiceJob {
	
	private static final Logger logger = Logger.getLogger(PPPImpServiceJob.class);
	
	/**
	 * 线程是否已启动的标示.0是未启动,1是已启动.
	 */
	private volatile int isRunning = 0;

	private ITravelerService travelerService;
	
	private CompanyDao companyDao;
	
	private TravelerDao travelerDao ;
	
	private PPPImpService PPPImpService;
	
	public ITravelerService getTravelerService() {
		return travelerService;
	}

	public void setTravelerService(ITravelerService travelerService) {
		this.travelerService = travelerService;
	}
	
	public PPPImpService getPPPImpService() {
		return PPPImpService;
	}

	public void setPPPImpService(PPPImpService pPPImpService) {
		PPPImpService = pPPImpService;
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

	public void start() {

		logger.info("PPPImpServiceJob start...");
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if ( hour >= 8 && hour <= 18) {
//		if ( hour >= 8 && hour <= 8) {
		    String keepgoing = ServiceConfig.getProperty("keepgoing");
		    if (keepgoing != null && keepgoing.equalsIgnoreCase("true")) {
		        
		    } else {
		    	logger.info("is wrong time.");
	            return;
		    }

		}

		if (isRunning == 1) {
			logger.info("PPPImpServiceJob is running.. break!");
			return;
		} else {
			isRunning = 1;
		}
		
		
		/**
		 * key 是seqno,value是行数据.
		 * 包含文件头数据.
		 */
		Map fileMap = readFile();
		if (fileMap == null || fileMap.size() <= 0 ) {
			logger.info("no file fund.....exit..");
			return;
		}
		

		Iterator it = fileMap.keySet().iterator();
		while (it.hasNext()) {
			try {
				String key = (String) it.next();
				ArrayList<String[]> filecontent = (ArrayList<String[]>) fileMap.get(key);
				
				String err = checkHeader(filecontent);
				if (StringUtils.isEmpty(err)) {
					impFileDataToMiddleTable(filecontent);
					checkBodyData(key);
					impDataToPar(key);
				}
	
				writeReCSV(filecontent,err,key);
				
				batchSync(key);
			} catch (Exception e) {
				logger.error("data process error.", e);
				errorProcess(e.getMessage(),false);
			}
		}
	
		moveFileToBack();
		
		isRunning = 0;
		
	}
	
	/**
	 * 插入成功的数据，批量同步
	 * @param key
	 * @throws Exception 
	 */
	private void batchSync(String key) throws Exception {
		String sql = "insert into T_JOB_PRESYNC_PAR(CUSTNO,STATUS,PRIORITY) select b.TRAVELER_NO,0,50 from t_ppp_traveler_middle a inner join T_TRAVELER b on a.pppcmid = b.pppcmid where a.sequencenumber ='" + key + "' and a.issuccess>=1 ";
		DBUtil.excuteUpdate(sql, "MPP");
	}

	/**
	 * 读取文件
	 * @return
	 */
	private Map readFile() {
		
		String path = ServiceConfig.getProperty("csv_requestfilepath");
//		String path = "E:\\fans\\workspace\\demofile";
		File filepath=new File(path);
		if (!filepath.isDirectory()) {		//不是一个路径.报错.退出.
			errorProcess(IpppErrMsg.ERR001,false);
			return null;
		}
		
		File[] filelist = filepath.listFiles();
		if(filelist == null || filelist.length == 0){
			warningProcess("没有任何数据文件");
			return null;
		}
		
		/**
		 * key 是seqno,value是行数据.
		 * 包含文件头数据.
		 */
		Map fileMap = new TreeMap();
		StringBuffer errStr = new StringBuffer();
		for (int i = 0; i < filelist.length; i++) {
			 if (filelist[i].isFile()) {
				 try {
					 String temp = readFileData(filelist[i],fileMap);
					 if (StringUtils.isNotEmpty(temp)) {
						errStr.append(temp);
					}
				} catch (IOException e) {
//					e.printStackTrace();
//					logger.error("readFileData error.", e);
					errorProcess("readFileData error.", false);
				}
			 }else{
				 errStr.append(filelist[i].getName()).append(IpppErrMsg.ERR002);
			 }
		}
		
		if (errStr.length() > 0) {		
			//警告信息.不退出.
			warningProcess(errStr.toString());
		}
		
		sortMapByKey(fileMap);
		
		return fileMap;
	}
	
	/**
	 * 检查文件头信息
	 * @param filecontent
	 * @return 错误信息.无错误返回null.
	 * @throws Exception 
	 */
	private String checkHeader(ArrayList<String[]> filecontent) throws Exception {
		if (filecontent == null || filecontent.size() < 5) {
			return IpppErrMsg.DC_0002;
		}
		
		StringBuffer errStr = new StringBuffer();
		
		String MessageTypeIdetifier = "";
		String Sequencenumber = "";
		String TransmittalDate = "";
		String TransmittalTime = "";
		String RecordsNo = "";

		List list = DBUtil.querySqlUniqueResult( "select fvalue from t_ppp_config where fnumber='seqno' ", "MPP");

		if (list == null || list.size() <= 0) {
			return IpppErrMsg.DC_0008;
		}

		String seqno = (String) list.get(0);
		String newseqno = "";

		Integer mppNo = Integer.parseInt(seqno);
		newseqno = String.valueOf(mppNo + 1);

		/**
		 * 
		if (newseqno != null) {
			switch (newseqno.length()) {
			case 1:
				newseqno = "0000" + newseqno;
				break;
			case 2:
				newseqno = "000" + newseqno;
				break;
			case 3:
				newseqno = "00" + newseqno;
				break;
			case 4:
				newseqno = "0" + newseqno;
				break;
			}
		}
		 */
		
		//比较seqno.
		String[] strs = filecontent.get(1);
		if (!newseqno.equals(strs[1])) {
			errStr.append("<Sequence Number:").append(IpppErrMsg.DC_0004).append(">");
		}
		
		strs = filecontent.get(4);
		if (strs == null || strs.length <2) {
			errStr.append("<Total Records:").append(IpppErrMsg.DC_0005).append(">");
		}else{
			int total = Integer.parseInt(strs[1]);
			if (total != filecontent.size()-6) {
				errStr.append("<Total Records:").append(IpppErrMsg.DC_0001).append(">");
			}
			
			DBUtil.excuteUpdate("update t_ppp_config set fvalue='" + newseqno + "' where fnumber='seqno' ", "MPP");
		}
		
		if (errStr != null && errStr.length() > 0) {
			return errStr.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * 要导入的数据列,1-列名,2-列类型(C:varchar,D:datetime),3-长度.
	 */
	private static Object[][] coltypes = {//{"SequenceNumber",		"C",5},
											//{"TransmittalDate",		"C",8},
											//{"TransmittalTime",		"C",6},
											{"ACTION",				"C",20},
											{"PPPCMID",				"C",36},
											{"FIRSTNAME_EN",		"C",150},
											{"LASTNAME_EN",			"C",150},
											{"FIRSTNAME",			"C",10},
											{"LASTNAME",			"C",10},
											{"GENDER",				"C",10},
											{"Email",				"C",50},
											{"ICID_NO",				"C",50},
											{"NATIONALITY1",		"C",50},
											{"NATIONALITY2",		"C",50},
											{"BIRTHDAY",			"D",10},
											{"PASSPORT1",			"C",50},
											{"ISSUE_DATE1",			"D",10},
											{"EXPIRY_DATE1",		"D",10},
											{"PassportType1",		"C",50},
											{"PASSPORT2",			"C",50},
											{"ISSUE_DATE2",			"D",10},
											{"EXPIRY_DATE2",		"D",10},
											{"PassportType2",		"C",50},
											{"STATUS",				"C",20},
											{"MOBILE",				"C",20},
											{"AddressType1",		"C",20},
											{"Address1",			"C",500},
											{"City1",				"C",50},
											{"Zipcode1",			"C",50},
											{"AddressType2",		"C",20},
											{"Address2",			"C",500},
											{"City2",				"C",50},
											{"Zipcode2",			"C",50},
											{"CATALOG1",			"C",20},
											{"TYPE1",				"C",20},
											{"CATALOG2",			"C",20},
											{"TYPE2",				"C",20},
											{"PROD_TYPE1",			"C",50},
											{"MEMBER_NO1",			"C",50},
											{"MEMBER_CODE1",		"C",50},
											{"MEMBER_EXPIRE1",		"D",10},
											{"PROD_TYPE2",			"C",50},
											{"MEMBER_NO2",			"C",50},
											{"MEMBER_CODE2",		"C",50},
											{"MEMBER_EXPIRE2",		"D",10},
											{"MEMBER_NO3",			"C",50},
											{"MEMBER_CODE3",		"C",50},
											{"MEMBER_EXPIRE3",		"D",10},
											{"MEMBER_NO4",			"C",50},
											{"MEMBER_CODE4",		"C",50},
											{"MEMBER_EXPIRE4",		"D",10},
											{"MEMBER_NO5",			"C",50},
											{"MEMBER_CODE5",		"C",50},
											{"MEMBER_EXPIRE5",		"D",10},
											{"VENDOR",				"C",50},
											{"Product",				"C",50},
											{"DKnumber",			"C",50},
											{"CCARD_NO1",			"C",50},
											{"EXPIRYDATE1",			"C",20},
											{"HOLDERNAME1",			"C",50},
											{"CCARD_NO2",			"C",50},
											{"EXPIRYDATE2",			"C",20},
											{"HOLDERNAME2",			"C",50}
//											{"errmsg","C",1024},
//											{"issuccess","N",10}
											};
	
	/**
	 * 导入数据到临时表,导入前会检查数据长度.有错误将错误信息页记录到临时表中.
	 * @param filecontent
	 * @throws SQLException 
	 */
	private void impFileDataToMiddleTable(ArrayList<String[]> filecontent) throws SQLException {
		if(filecontent == null || filecontent.size() < 6) return ;
		
		Connection conn = null;
		PreparedStatement pst  = null;
		Long beginTime = System.currentTimeMillis();    
		StringBuffer insertSqlBuf = new StringBuffer("INSERT t_ppp_traveler_middle(SequenceNumber,TransmittalDate,TransmittalTime,FACTION,PPPCMID,FIRSTNAME_EN,LASTNAME_EN,FIRSTNAME,LASTNAME,GENDER,Email,ICID_NO,NATIONALITY1,NATIONALITY2,BIRTHDAY,PASSPORT1,ISSUE_DATE1,EXPIRY_DATE1,passporttype1,PASSPORT2,ISSUE_DATE2,EXPIRY_DATE2,passporttype2,FSTATUS,MOBILE,AddressType1,Address1,City1,Zipcode1,AddressType2,Address2,City2,Zipcode2,CATALOG1,TYPE1,CATALOG2,TYPE2,PROD_TYPE1,MEMBER_NO1,MEMBER_CODE1,MEMBER_EXPIRE1,PROD_TYPE2,MEMBER_NO2,MEMBER_CODE2,MEMBER_EXPIRE2,MEMBER_NO3,MEMBER_CODE3,MEMBER_EXPIRE3,MEMBER_NO4,MEMBER_CODE4,MEMBER_EXPIRE4,MEMBER_NO5,MEMBER_CODE5,MEMBER_EXPIRE5,VENDOR,Product,DKnumber,CCARD_NO1,EXPIRYDATE1,HOLDERNAME1,CCARD_NO2,EXPIRYDATE2,HOLDERNAME2,errmsg,issuccess) VALUES("); 
		String insertSql = "INSERT t_ppp_traveler_middle(SequenceNumber,TransmittalDate,TransmittalTime,FACTION,PPPCMID,FIRSTNAME_EN,LASTNAME_EN,FIRSTNAME,LASTNAME,GENDER,Email,ICID_NO,NATIONALITY1,NATIONALITY2,BIRTHDAY,PASSPORT1,ISSUE_DATE1,EXPIRY_DATE1,passporttype1,PASSPORT2,ISSUE_DATE2,EXPIRY_DATE2,passporttype2,FSTATUS,MOBILE,AddressType1,Address1,City1,Zipcode1,AddressType2,Address2,City2,Zipcode2,CATALOG1,TYPE1,CATALOG2,TYPE2,PROD_TYPE1,MEMBER_NO1,MEMBER_CODE1,MEMBER_EXPIRE1,PROD_TYPE2,MEMBER_NO2,MEMBER_CODE2,MEMBER_EXPIRE2,MEMBER_NO3,MEMBER_CODE3,MEMBER_EXPIRE3,MEMBER_NO4,MEMBER_CODE4,MEMBER_EXPIRE4,MEMBER_NO5,MEMBER_CODE5,MEMBER_EXPIRE5,VENDOR,Product,DKnumber,CCARD_NO1,EXPIRYDATE1,HOLDERNAME1,CCARD_NO2,EXPIRYDATE2,HOLDERNAME2,errmsg,issuccess) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		try {
			conn = DBUtil.getConnection("MPP");
			conn.setAutoCommit(false);     
			pst = conn.prepareStatement(insertSql);  
			StringBuffer errsb = null;
			String SequenceNumber = ((String[])filecontent.get(1))[1];
			String TransmittalDate = ((String[])filecontent.get(2))[1];
			String TransmittalTime = ((String[])filecontent.get(3))[1];
			
			String tempStr = null;
			String[] pppdatas =  null;
			for (int i = 6; i < filecontent.size(); i++) {
				pppdatas =  filecontent.get(i);
				errsb = new StringBuffer();
				if (StringUtils.isNotEmpty(SequenceNumber) && (SequenceNumber.length() > 5)) {
					SequenceNumber = SequenceNumber.substring(0, 5);
					//errsb.append("<").append("SequenceNumber").append(":DC_DLEN>");
				}
				if (StringUtils.isNotEmpty(TransmittalDate) && (TransmittalDate.length() > 8)) {
					TransmittalDate = TransmittalDate.substring(0, 8);
					//errsb.append("<").append("TransmittalDate").append(":DC_DLEN>");
				}
				if (StringUtils.isNotEmpty(TransmittalTime) && (TransmittalTime.length() > 6)) {
					TransmittalTime = TransmittalTime.substring(0, 6);
					//errsb.append("<").append("TransmittalTime").append(":DC_DLEN>");
				}
				pst.setString(1,SequenceNumber);
				pst.setString(2,TransmittalDate);
				pst.setString(3,TransmittalTime);
//				pst.setString(4,null);
				insertSqlBuf.append("'").append(SequenceNumber).append("',");
				insertSqlBuf.append("'").append(TransmittalDate).append("',");
				insertSqlBuf.append("'").append(TransmittalTime).append("',");
				
				if (pppdatas != null && pppdatas.length != 60) {	//列数不够,只把action和pppcmid插入中间表.
					pst.setString(4, pppdatas[0]);
					pst.setString(5, pppdatas[1]);
					errsb.append("<").append(coltypes[0][0]).append(":DC_COL>");
					for (int j = 6; j < 65; j++) {
						pst.setString(j, null);
					}
				} else {
					for (int m = 0; m < coltypes.length; m++) {
						tempStr = pppdatas[m];
						if("C".equals(coltypes[m][1])){
							int length = Integer.parseInt(String.valueOf(coltypes[m][2]));
							if (StringUtils.isNotEmpty(tempStr) && (tempStr.length() > length)) {
								errsb.append("<").append(coltypes[m][0]).append(":DC_DLEN>");
								tempStr.substring(0, length);
							}else if("Email".equals(coltypes[m][0]) && !checkEmail(tempStr)){
								errsb.append("<").append(coltypes[m][0]).append(":DC_DLEN>");
							}else if("ICID_NO".equals(coltypes[m][0]) && !checkIDCD(tempStr)){
								errsb.append("<").append(coltypes[m][0]).append(":DC_DLEN>");
							}else if("MOBILE".equals(coltypes[m][0]) && !checkMobile(tempStr)){
								errsb.append("<").append(coltypes[m][0]).append(":DC_DLEN>");
							}
						}
						//date类型字段验证格式.
						else if("D".equals(coltypes[m][1])){
//							tempStr = pppdatas[m];
							if (StringUtils.isNotEmpty(tempStr) && tempStr.length() != 10 ) {
								errsb.append("<").append(coltypes[m][0]).append(":DC_DLEN>");
								tempStr.substring(0, tempStr.length() > 10 ? 10:tempStr.length());
							}else if(StringUtils.isNotEmpty(tempStr) && !validDate(tempStr)){
								errsb.append("<").append(coltypes[m][0]).append(":DC_DF>");
							}
						}
						pst.setString(m+4,tempStr);
						insertSqlBuf.append("'").append(tempStr).append("',");
					}
				}
				
				if (errsb != null && errsb.length() > 0) {
					pst.setString(64 ,errsb.toString());
					pst.setInt(65, 0);
					insertSqlBuf.append("'").append(errsb.toString()).append("',");
					insertSqlBuf.append("").append(0).append(")");
				} else {
					pst.setString(64 ,"");
					pst.setInt(65, 1);
					insertSqlBuf.append("'',");
					insertSqlBuf.append("").append(1).append(")");
				}
				
				pst.addBatch();  
				if (i % 100 == 0) {// 可以设置不同的大小；如50，100，500，1000等等
					pst.executeBatch();
					conn.commit();
					pst.clearBatch();
				}
			}
			System.out.println(insertSqlBuf.toString());
			pst.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" impFileData error.insert sql=" + insertSqlBuf.toString(), e);
		}finally{
			Long endTime = System.currentTimeMillis();     
			logger.info("pst+batch："+(endTime-beginTime)/1000+"秒");
			
			pst.close();     
			conn.close();     
		}
	}
	
	public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public static boolean validDate(String str){
	    try{
	        Date date = (Date)formatter.parse(str);
	        return str.equals(formatter.format(date));
	    }catch(Exception e){
	       return false;
	    }
	} 
	

	private void checkBodyData(String sequenceNumber) {
		
		//1.检查update的在t_traveler表是否有对应记录.
		String[] checkSqlS = {
				//1.验证约定值.
				"update t_ppp_traveler_middle set errmsg= errmsg + '<action:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "'  and (faction !='NEW' and faction != 'UPDATE' ) ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<GENDER:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "'  and (gender !='F' and gender != 'M' ) ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<STATUS:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "'  and (FSTATUS !='ACTIVE' and fSTATUS != 'INACTIVE' ) ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<AddressType1:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "'  and (AddressType1 !='' and AddressType1 !='Mailing' and AddressType1 != 'Delivery' ) ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<AddressType2:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "'  and (AddressType2 !='' and AddressType2 !='Mailing' and AddressType2 != 'Delivery' ) ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<CATALOG1:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and CATALOG1 is not null and catalog1 !=''  and (CATALOG1 !='MEAL' and CATALOG1 != 'SEAT' ) ",
				
				"update t_ppp_traveler_middle set errmsg= errmsg + '<pppcmid:DC_DLEN>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and (pppcmid is null  and pppcmid='' )",
				
				//2.关联系统表,验证type信息.餐食喜好信息.
				"update t_ppp_traveler_middle set errmsg= errmsg + '<PREFERENCE1:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and catalog1 = 'MEAL' AND type1 not in (SELECT LEFT(VALUE,4) FROM T_C_PREFER where CATEGORY ='MEAL')",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<PREFERENCE2:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and catalog1 = 'Seat' AND type2 not in (SELECT LEFT(VALUE,4) FROM T_C_PREFER where CATEGORY ='Seat') ",
				
				//必录项验证暂不验证.passport系列,birthday不验证.
				"update t_ppp_traveler_middle set errmsg= errmsg + '<FIRSTNAME_LOCAL:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and FIRSTNAME is  null and FIRSTNAME='' ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<LASTNAME_LOCAL:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and LASTNAME is  null  and LASTNAME='' ",
				
				//验证product
				"update t_ppp_traveler_middle set errmsg= errmsg + '<product:DC_NON>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and (product is  null or product=''  or product not in('Centurion','Platinum')) ",
				
				//检查在现有数据中是否存在相同值。
				"update a set a.errmsg = errmsg + '<PPPCMID:DC_DUP>',a.issuccess=0 from t_ppp_traveler_middle a ,T_TRAVELER b where b.STATUS='active' and a.faction='NEW' and len(b.traveler_no)=10 and a.SequenceNumber='" + sequenceNumber + "' and a.firstname_en+a.lastname_en+a.icid_no+a.passport1+a.passport2 = b.FIRSTNAME_EN+b.LASTNAME_EN+b.ICID_NO+b.PASSPORT1+b.PASSPORT2 ",
				
				//后续如需增加验证.直接往里加sql就可以..
				
				//3.更新的,先找到对应的travelerid和travelerno.
				"update a  set a.travelerid = b.id,a.travelerno = b.TRAVELER_NO from t_ppp_traveler_middle a , t_traveler b where a.pppcmid = b.pppcmid and b.status='ACTIVE' and a.faction='UPDATE' and SequenceNumber='" + sequenceNumber + "' ",
				"update t_ppp_traveler_middle set errmsg= errmsg + '<PPPCMID:DC_PNO>',issuccess=0 where SequenceNumber='" + sequenceNumber + "' and  faction = 'UPDATE' and (travelerid is null or travelerid='')",
			
				/**
				 * 检查update的数据,是否是需要changebar操作的.
				 */
				"update a set a.issuccess = 2  from t_ppp_traveler_middle a , t_traveler b where a.pppcmid = b.pppcmid and a.travelerid = b.id and a.faction='UPDATE' and b.ALIAS != a.product and SequenceNumber='" + sequenceNumber + "' "
				
			};
		for (int i = 0; i < checkSqlS.length; i++) {
			try {
				int  rowcount = DBUtil.excuteUpdate(checkSqlS[i], "MPP");
				System.out.println("rowcount=" + rowcount);
				logger.info("update rowcount=" + rowcount);
			} catch (Exception e) {
				logger.error("execute sql error.sql=" + checkSqlS[i], e);
				errorProcess(e.getMessage() +".sql=" + checkSqlS[i], false);
			}
		}
		
	}
	
	private ConcurrentHashMap<String, String>  barnoMap = new ConcurrentHashMap<String, String>();
	
	private String getBarno(String cardtype){
		String barno = null;
		if (barnoMap == null || !barnoMap.containsValue(cardtype)) {
			String sql = "select fvalue from t_ppp_config where fnumber='"+cardtype+"'";
			List list = DBUtil.querySqlUniqueResult(sql, "MPP");
			if (list != null && list.size() == 1) {
				barno = (String) list.get(0);
				barnoMap.put(cardtype, barno);
			}else{
				errorProcess("getBarno error.", false);
			}
		}else{
			barno = barnoMap.get(cardtype);
		}
		return barno;
	}

	/**
	 * 调用系统方法.把数据插入MPP.
	 * @param sequenceNumber
	 */
	private void impDataToPar(String sequenceNumber) {
		
		String sql = "select * from t_ppp_traveler_middle where SequenceNumber='" + sequenceNumber + "' and issuccess >= 1 ";
		List datalist = DBUtil.querySql(sql, "MPP");
		
		
		HashMap rowmap = null;
		for (int i = 0; i < datalist.size(); i++) {
			rowmap = (HashMap) datalist.get(i);
			try {
				getPPPImpService().impDataFromPPP(rowmap);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("impDataToPar error.", e);
				String updateMiddleSql = "update t_ppp_traveler_middle set errmsg='<pppcmid:IMP_ERR>',issuccess=0 where id =" + rowmap.get("id");
		        try {
					DBUtil.excuteUpdate(updateMiddleSql, "MPP");
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.error("updateMiddleSql error.sql= "+updateMiddleSql, e1);
				}
				errorProcess(e.getMessage(),false);
				
			}
		}
		
	}

	/**
	 * 生成回写的csv文件
	 * @param err	头信息检查出的错误.
	 */
	private void writeReCSV(ArrayList<String[]> filecontent,String err,String sequenceNumber) {
		
		if (filecontent == null || filecontent.size() < 5) {
			errorProcess(" when writeReCSV ,check filecontent error.",false);
			return ;
		}
		
		String path = ServiceConfig.getProperty("csv_responsefilepath");
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String recsvFilePath = path + "\\PPPTST-AROUT-CITS-"+ df.format(Calendar.getInstance().getTime()) +".csv";
		
		com.csvreader.CsvWriter cw = new com.csvreader.CsvWriter(recsvFilePath, ',', Charset.forName("UTF-8"));
		if (StringUtils.isNotEmpty(err)) {				//err不为空.说明头文件验证异常.
			try {
				cw.writeRecord(filecontent.get(0));
				cw.writeRecord(filecontent.get(1));
				cw.writeRecord(filecontent.get(2));
				cw.writeRecord(filecontent.get(3));
				cw.writeRecord(filecontent.get(4));
				cw.writeRecord(new String[]{"Reason",err});
				cw.writeRecord(new String[]{"Result","Failure"});
				cw.writeRecord(new String[]{"Total Number of Record successful",""});
				cw.writeRecord(new String[]{"Total Number of Record rejected",""});
				cw.writeRecord(new String[]{"PPP CM Id","Error Message"});
			} catch (Exception e) {
				errorProcess(e.getMessage(),false);
			} finally{
				cw.close();
			}
		} else {				//err 为空.回写表体错误信息.
			try {
				cw.writeRecord(filecontent.get(0));
				cw.writeRecord(filecontent.get(1));
				cw.writeRecord(filecontent.get(2));
				cw.writeRecord(filecontent.get(3));
				cw.writeRecord(filecontent.get(4));
				cw.writeRecord(new String[]{"Reason",err});
				cw.writeRecord(new String[]{"Result","SUCCESS"});
				String sql = "select count(1) from t_ppp_traveler_middle where SequenceNumber='" + sequenceNumber + "' and issuccess >= 1  ";
				List successCount = DBUtil.querySqlUniqueResult(sql, "MPP");
				cw.writeRecord(new String[]{"Total Number of Record successful","" + successCount.get(0)});
				sql = "select pppcmid,errmsg from t_ppp_traveler_middle where SequenceNumber='" + sequenceNumber + "' and issuccess = 0  ";
				List rejectedList = DBUtil.querySql(sql, "MPP");
				cw.writeRecord(new String[]{"Total Number of Record rejected","" + rejectedList.size()});
				cw.writeRecord(new String[]{"PPP CM Id","Error Message"});
				//添加pppcmid错误信息
				String pppcmid = null;
				String errmsg = null;
				for (int i = 0; i < rejectedList.size(); i++) {
					Map pppcmidMap =  (Map) rejectedList.get(i);
					pppcmid = String.valueOf(pppcmidMap.get("pppcmid"));
					errmsg = String.valueOf(pppcmidMap.get("errmsg"));
					cw.writeRecord(new String[]{pppcmid,errmsg});
				}
			} catch (Exception e) {
				errorProcess(e.getMessage(),false);
			} finally{
				cw.close();
			}
		}
		
	}

	/**
	 * 将处理过的文件,移到bak位置
	 */
	private void moveFileToBack() {
    	
    	String csvDir = ServiceConfig.getProperty("csv_requestfilepath");
//		String csvDir = "E:\\fans\\workspace\\demofile";
				
		File filepath = new File(csvDir);
		
		if (!filepath.isDirectory()) {
			Logger.getRootLogger().error("filepath :"+filepath + "is not real path!");
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());
		String rootDir = ServiceConfig.getProperty("csv_filebackpath");
//		String rootDir = "E:\\fans\\workspace\\filebackup\\";
		String invBakpath = rootDir + "backup_" + today + "\\";
		File invDir = new File(invBakpath);
		File dest;
		
		if (!invDir.exists()) {
			invDir.mkdirs();
		}
		
		File[] files = filepath.listFiles();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -13);
		long date = cal.getTimeInMillis();
        try {
            for (int i = 0,length = files.length; i < length; i++) {
                File tmp = files[i];
                if (!tmp.exists()) {
                    continue;
                }
                
                dest = new File(invBakpath + tmp.getName());
                tmp.renameTo(dest);
                tmp.delete();                   
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getRootLogger().error("move pdf file occur error", e);
        }
		
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
			isRunning = 0;
		}
	}
	
	/**
	 * 获取文件序列号
	 * fans.fan
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	private String readFileData(File file,Map fileMap) throws IOException{
		
		if (file == null || !file.getName().endsWith(".csv")) {
			errorProcess("exist file not .csv", false);
			return null;
		}
		
		ArrayList<String[]> filecontent = readeCsv(file.getAbsolutePath());
		
		if (filecontent != null && filecontent.size() >= 6 ) {
			String[] strs = filecontent.get(1);
			if (strs != null && strs.length >=2 ) {
				fileMap.put(strs[1], filecontent);
				return null;
			}else{
				return "filecontent error.";
			}
		}else{
			return "filecontent not exist.";
		}
		
		
	}
	
	
	/**
	 * 读取CSV文件
	 * 
	 * @throws IOException
	 */
	private ArrayList<String[]> readeCsv(String csvFilePath) throws IOException {

		ArrayList<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据
//		String csvFilePath = "e:/test.csv";
		CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8")); // 一般用这编码读就可以了

		// reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。

		while (reader.readRecord()) { // 逐行读入除表头的数据
			csvList.add(reader.getValues());
		}
		reader.close();
		
		return csvList;
	}
	
	/** 
     * 使用 Map按key进行排序 
     * @param map 
     * @return 
     */  
    public static Map<String, ArrayList<String[]>> sortMapByKey(Map<String, ArrayList<String[]>> map) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        Map<String, ArrayList<String[]>> sortMap = new TreeMap<String, ArrayList<String[]>>(new MapKeyComparator());  
        sortMap.putAll(map);  
        return sortMap;  
    }  
    
    //email 验证.
  	public static boolean checkEmail(String mail) {
  		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
  		return CommonUtil.checkText(mail, regex);
  	}
  	
  	//icdc 验证.
  	public static boolean checkIDCD(String idcd) {
  		//String regex = "\\d{18}|\\d{17}[Xx]|\\d{15}|\\d{14}[Xx]";
  	    
  	    // non support 15 length id no
  		String regex = "\\d{18}|\\d{17}[Xx]";
  		if ("".equals(idcd)) {
  		    return true;
  		}
  		return CommonUtil.checkText(idcd, regex);
  	}
  	
  	//mobile 验证.
  	public static boolean checkMobile(String mobile) {
  		String regex = "\\+{0,1}\\d{1,}";
  		return CommonUtil.checkText(mobile, regex);
  	}
  	
  	public boolean checkPhone(String phone) {
		String regex = "\\+{0,1}\\d{0,} {0,1}\\d{0,}-{0,1}\\d{0,}(EXT\\.\\d{1,10}){0,1}";
		return CommonUtil.checkText(phone, regex);
	}
	
}
//比较器类  
class MapKeyComparator implements Comparator<String>{  
	public int compare(String str1, String str2) {  
		return str1.compareTo(str2);  
	}  
}  

