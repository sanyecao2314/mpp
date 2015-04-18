package com.citsamex.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.citsamex.app.util.ServiceConfig;
import com.citsamex.app.validate.ExcelDownlaodValidator;
import com.citsamex.service.ITravelerService;
import com.citsamex.service.db.CompanyDao;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;

@SuppressWarnings("serial")
public class UploadAction extends BaseAction {

	private Long companyId;
	private String companyNo;
	private String contentType;
	private String contentDisposition;
	private String filename;
	private File files;
	private int excelCurX;
	private int excelCurY;
	private int sheetNo;
	private String errorMessage;

	// columns in upload file mapping to po in db.
	private String[] columns;
	// upload file saved on server.
	private String serverFilename;

	public String getServerFilename() {
		return serverFilename;
	}

	public void setServerFilename(String serverFilename) {
		this.serverFilename = serverFilename;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	private ITravelerService travService;

	public ITravelerService getTravService() {
		return travService;
	}

	public void setTravService(ITravelerService travService) {
		this.travService = travService;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public File getFiles() {
		return files;
	}

	public void setFiles(File files) {
		this.files = files;
	}

	private CompanyDao compdao;
	private TravelerDao travdao;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public CompanyDao getCompdao() {
		return compdao;
	}

	public void setCompdao(CompanyDao compdao) {
		this.compdao = compdao;
	}

	public TravelerDao getTravdao() {
		return travdao;
	}

	public void setTravdao(TravelerDao travdao) {
		this.travdao = travdao;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String upload() {
		try {
			travService.upload(files);
		} catch (Exception ex) {
			if (!travService.getErrorMessage().equals("")) {
				errorMessage = travService.getErrorMessage();
			} else {
				errorMessage = "upload failded.";
			}

		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String download() throws Exception {
		Date date = new Date();
		this.filename = companyNo + " " + date.getTime() + ".xls";

		WritableWorkbook workbook = null;
		try {
			OutputStream os = new FileOutputStream(filename);
			workbook = Workbook.createWorkbook(os);

			/**
			 * company sheet.
			 */

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("companyNo", companyNo);

			WritableSheet sheet0 = workbook.createSheet("Company", 0);
			List list0 = compdao.list(map);
			proc(list0, sheet0);

			WritableSheet sheet1 = workbook.createSheet("Traveler", 1);
			List list1 = travdao.list(map);
			proc(list1, sheet1);

			String[] rId = new String[list1.size()];
			for (int i = 0; i < list1.size(); i++) {
				TravelerPo po = (TravelerPo) list1.get(i);
				rId[i] = "T" + po.getId();
			}

			WritableSheet sheet2 = workbook.createSheet("Address", 2);
			List list2 = travdao.listRelation("AddressPo", rId);
			proc(list2, sheet2);

			WritableSheet sheet3 = workbook.createSheet("CreditCard", 3);
			List list3 = travdao.listRelation("CreditCardPo", rId);
			proc(list3, sheet3);

			WritableSheet sheet4 = workbook.createSheet("Discount", 4);
			List list4 = travdao.listRelation("DiscountPo", rId);
			proc(list4, sheet4);

			WritableSheet sheet5 = workbook.createSheet("RequiredData", 5);
			List list5 = travdao.listRelation("ExtraPo", rId);
			proc(list5, sheet5);

			WritableSheet sheet6 = workbook.createSheet("Member", 6);
			List list6 = travdao.listRelation("MemberPo", rId);
			proc(list6, sheet6);

			WritableSheet sheet7 = workbook.createSheet("Preference", 7);
			List list7 = travdao.listRelation("PreferencePo", rId);
			proc(list7, sheet7);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.write();
				workbook.close();
			}
		}

		File file = new File(filename);
		file.createNewFile();
		this.contentDisposition += "attachment;filename=" + filename;
		this.contentType = "application/vnd.ms-excel";

		return SUCCESS;
	}

	public InputStream getFile() throws Exception {
		return new FileInputStream(filename);
	}

	private static final int BUFFER_SIZE = 16 * 1024;

	private void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void proc(List list, WritableSheet sheet0) throws Exception {
		// format
		jxl.write.WritableFont f12br = new jxl.write.WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);
		f12br.setColour(Colour.RED);
		jxl.write.WritableFont f12 = new jxl.write.WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, true);
		jxl.write.WritableCellFormat cfr = new jxl.write.WritableCellFormat(f12br);
		jxl.write.WritableCellFormat cf = new jxl.write.WritableCellFormat(f12);

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			ExcelDownlaodValidator validator = new ExcelDownlaodValidator();
			validator.valid(obj);
			String value[] = validator.getValue();
			String message[] = validator.getMessage();
			String fieldname[] = validator.getFieldName();

			if (i == 0)
				for (int j = 0; j < fieldname.length; j++) {
					Label label = new jxl.write.Label(j, 0, fieldname[j], cfr);

					sheet0.addCell(label);
				}

			for (int j = 0; j < value.length; j++) {
				Label label = null;
				if (isEmpty(message[j])) {
					label = new jxl.write.Label(j, i + 1, value[j], cf);
				} else {
					label = new jxl.write.Label(j, i + 1, value[j] + " - " + message[j], cfr);
				}

				sheet0.addCell(label);
			}

		}
	}

	public String preUpload() {
		if (files == null) {
			return INPUT;
		}
		try {
			columns = travService.preProcessUpload(files);
		} catch (Exception ex) {
			if (!travService.getErrorMessage().equals("")) {
				errorMessage = travService.getErrorMessage();
			} else {
				errorMessage = "upload failded.";
			}
			return INPUT;
		}
		String servDir = ServiceConfig.getProperty("UPLOAD_DIR");
		File dir = new File(servDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		serverFilename = System.currentTimeMillis() + ".tmp";
		File servFile = new File(servDir + serverFilename);

		copy(files, servFile);
		return SUCCESS;
	}
}