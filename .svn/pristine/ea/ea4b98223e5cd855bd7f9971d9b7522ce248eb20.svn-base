package com.citsamex.service.action;

import java.rmi.RemoteException;
import java.util.List;

import com.citsamex.app.action.BaseAction;
import com.citsamex.app.cfg.Element;
import com.citsamex.service.ICustomerService;
import com.citsamex.service.IProductService;
import com.citsamex.service.ServiceMessage;
import com.citsamex.ws.PsService;
import com.citsamex.ws.PsServiceProxy;

public class CustomerAction extends BaseAction {

	private static final long serialVersionUID = -6555360390410789370L;

	private long id;
	private String companyId;
	private String companyName;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private Customer customer;
	private ICustomerService service;

	private List<Product> productlist;

	private IProductService prdService;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ICustomerService getService() {
		return service;
	}

	public void setService(ICustomerService service) {
		this.service = service;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String search() {
		return SUCCESS;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public IProductService getPrdService() {
		return prdService;
	}

	public void setPrdService(IProductService prdService) {
		this.prdService = prdService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String display() {
		ServiceMessage sm = service.findCustomer(id);
		if (sm.isSuccess()) {
			customer = (Customer) sm.getData();
			ServiceMessage sm2 = prdService.listProduct(map);
			productlist = (List) sm2.getData();
		} else
			this.addMessage("message", sm.getServiceMessage());

		return SUCCESS;
	}

	public String update() {
		// check if name is empty.
		service.updateCustomer(customer);

//		add by fans.fan 功能去掉 140801
//		String[] tmrs = customer.getDepartments();
//		for (int i = 0; i < tmrs.length; i++) {
//			String[] tmrparts = tmrs[i].split(";");
//			if (tmrparts.length < 4) {
//				continue;
//			}
//
//			String tmrNo = tmrparts[0];
//			String tmrStr = tmrparts[1];
//			String tmrGmaxNo = tmrparts[2];
//			String tmrName = tmrparts[3];
//
//			String tmp[] = new String[] { "", "", "", "", "" };
//			String tmrLvs[] = tmrStr.split("-");
//
//			for (int j = 0; j < tmrLvs.length; j++) {
//				tmp[j] = tmrLvs[j];
//			}
//
//			String tmr1 = tmp[0];
//			String tmr2 = tmp[1];
//			String tmr3 = tmp[2];
//			String tmr4 = tmp[3];
//			String tmr5 = tmp[4];
//
//			PsService service = new PsServiceProxy();
//			try {
//				service.createTmrPar(tmrGmaxNo, tmrName, tmrNo, tmr1, tmr2, tmr3, tmr4, tmr5);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		end by fans.fan

		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	public String create() {
		if (customer == null)
			return INPUT;
		ServiceMessage sm = service.createCustomer(customer);

		if (sm.isSuccess()) {
			customer = (Customer) sm.getData();
		} else {
			this.addMessage("message", sm.getServiceMessage());
			return INPUT;
		}

//		modify by fans.fan 功能去掉 140801
//		String[] tmrs = customer.getDepartments();
//		for (int i = 0; i < tmrs.length; i++) {
//			String[] tmrparts = tmrs[i].split(";");
//			if (tmrparts.length < 4) {
//				continue;
//			}
//
//			String tmrNo = tmrparts[0];
//			String tmrStr = tmrparts[1];
//			String tmrGmaxNo = tmrparts[2];
//			String tmrName = tmrparts[3];
//
//			String tmp[] = new String[] { "", "", "", "", "" };
//			String tmrLvs[] = tmrStr.split("-");
//
//			for (int j = 0; j < tmrLvs.length; j++) {
//				tmp[j] = tmrLvs[j];
//			}
//
//			String tmr1 = tmp[0];
//			String tmr2 = tmp[1];
//			String tmr3 = tmp[2];
//			String tmr4 = tmp[3];
//			String tmr5 = tmp[4];
//
//			PsService service = new PsServiceProxy();
//			try {
//				service.createTmrPar(tmrGmaxNo, tmrName, tmrNo, tmr1, tmr2, tmr3, tmr4, tmr5);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//		end by fans.fan

		return SUCCESS;
	}

	public String cfgCust() {
		Element element = service.findElement("COMPANY", companyName);
		if (element == null) {
			element = service.createBarCfg("COMPANY", companyName, "", "", "", "", "", "", "");
		}
		companyId = String.valueOf(element.getId());
		return SUCCESS;
	}

}
