package com.citsamex.service.action;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.citsamex.app.action.BaseAction;
import com.citsamex.service.ICompanyService;
import com.citsamex.service.ICustomerService;
import com.citsamex.service.IProductService;
import com.citsamex.service.ITravelerService;
import com.citsamex.service.ServiceMessage;
import com.citsamex.service.db.AddressPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.EBillingPo;
import com.citsamex.service.db.FeeCodePo;
import com.citsamex.ws.PsService;
import com.citsamex.ws.PsServiceProxy;

public class TravelerAction extends BaseAction {

	private static final long serialVersionUID = -3877542998891634278L;
	private long id = -1;
	private Customer customer;
	private Company company;
	private Traveler traveler;
	private Product product;
	private List<Product> productlist;
	private ICompanyService compService;
	private ICustomerService custService;
	private ITravelerService service;
	private IProductService prdService;
	private String parno;
	private String systems;

	public String getSystems() {
		return systems;
	}

	public void setSystems(String systems) {
		this.systems = systems;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ITravelerService getService() {
		return service;
	}

	public String getParno() {
		return parno;
	}

	public void setParno(String parno) {
		this.parno = parno;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public ICompanyService getCompService() {
		return compService;
	}

	public void setCompService(ICompanyService compService) {
		this.compService = compService;
	}

	public void setService(ITravelerService service) {
		this.service = service;
	}

	public ICustomerService getCustService() {
		return custService;
	}

	public void setCustService(ICustomerService custService) {
		this.custService = custService;
	}

	public IProductService getPrdService() {
		return prdService;
	}

	public void setPrdService(IProductService prdService) {
		this.prdService = prdService;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String display() {
		ServiceMessage sm = service.findTraveler(id);

		if (sm.isSuccess()) {
			traveler = (Traveler) sm.getData();

			company = (Company) compService
					.findCompany(traveler.getCompanyId()).getData();

			customer = (Customer) custService.findCustomer(company.getCustId())
					.getData();

			Map map = new HashMap();
			map.put("relationId", "T" + traveler.getId());

			ServiceMessage smPrd = prdService.listProduct(map);
			productlist = (List) smPrd.getData();
		} else
			this.addMessage("message", sm.getServiceMessage());

		return SUCCESS;
	}

	@SuppressWarnings("unused")
	public String update() {

		// traveler.setDirty(1);
		if (!"ACTIVE".equals(traveler.getStatus())) {
			traveler.setFeeBased(0);
		}

		String div = traveler.getDepartment2();
		if (div.indexOf(":") != -1) {
			div = div.substring(0, div.indexOf(":"));
		}

		PsService ps = new PsServiceProxy();
		try {
			String divStr = ps.getTMRPar(div);
			if (divStr.indexOf(";") != -1) {
				String tmrpar = divStr.substring(0, divStr.indexOf(";"));
				if (tmrpar.length() == 10)
					traveler.setDepartment2(tmrpar);
			}

			boolean isExist = false;
			if (!isEmpty(divStr)) {
				Company company = (Company) compService.findCompany(
						traveler.getCompanyId()).getData();
				Customer customer = (Customer) custService.findCustomer(
						company.getCustId()).getData();
				String divs[] = customer.getDepartments();
				Arrays.sort(divs);
				if (Arrays.binarySearch(divs, divStr) >= 0) {
					isExist = true;
				}
				if (!isExist) {
					String departments[] = new String[customer.getDepartments().length + 1];
					for (int i = 0; i < customer.getDepartments().length; i++) {
						departments[i] = customer.getDepartments()[i];
					}
					departments[customer.getDepartments().length] = divStr;
					customer.setDepartments(departments);
					custService.updateCustomer(customer);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		    Logger.getRootLogger().error("update traveler failure, travelerAction L208", e);
		}

		if (traveler.getAlias().equals("CHANGEBAR")) {
			traveler.setAlias("");
		}
		
		ServiceMessage smCreditCard =  service.listCreditCard(traveler.getId());
		if (smCreditCard.isSuccess()) {
			List list = (List) smCreditCard.getData();
			CreditCardPo cardPo;
			String cardType;
			for (int i = 0; i < list.size(); i++) {
				cardPo = (CreditCardPo) list.get(i);
				cardType = service.getCardTypeByCardbin(cardPo.getCardNo());
				if (isEmpty(cardType) || cardType.equals("error")) {
					cardType = "NONE";
				}
				cardPo.setType(cardType.split(",")[0]);
				service.updateCreditCardPo(cardPo);
			}
			
		}
		
		ServiceMessage sm = service.updateTraveler(traveler);
		if (!sm.isSuccess()) {
			return INPUT;
		}
		traveler = (Traveler) sm.getData();
		Object object = service.synchronize(traveler.getTravelerNo(), 0, 1,
				systems);
		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	// traveler type: family or work-in.
	public String createAttachedTraveler() {

		if (company == null)
			return "step1";

		ServiceMessage smcp = compService.findCompany(company.getCompanyNo());
		if (!smcp.isSuccess()) {
			return "step1";
		}
		company = (Company) smcp.getData();
		String prefix = null;

		if (traveler.getTravelerType().toLowerCase().equals("family")) {
			prefix = "F";
		} else if (traveler.getTravelerType().toLowerCase().equals("work-in")) {
			prefix = "W";
		} else {
			prefix = "N";
		}

		String no = service.getMaxPotentialTravelerNo(traveler.getTravelerNo(),
				prefix);
		traveler.setTravelerNo(no);

		return INPUT;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String create() {

		if (company == null)
			return "step1";

		ServiceMessage smcp = compService.findCompany(company.getCompanyNo());
		if (!smcp.isSuccess()) {
			return "step1";
		}
		company = (Company) smcp.getData();

		if (traveler == null) {
			traveler = new Traveler();
			traveler.setTravelerNo(service.getMaxTravelerNo(company
					.getCompanyNo()));
			traveler.setTravelerType("traveler");
			return INPUT;
		}

		traveler.setDirty(0);
		ServiceMessage sm = service.create(traveler);

		if (sm.isSuccess()) {
			traveler = (Traveler) sm.getData();
			customer = (Customer) custService.findCustomer(company.getCustId())
					.getData();

			// copy ebill from company to traveler
			List list = (List) compService.listEBill(company.getId()).getData();
			for (int i = 0; i < list.size(); i++) {
				EBillingPo bill = (EBillingPo) list.get(i);
				service.createEBill(bill.getFieldName(),
						bill.getDefaultValue(), bill.getMandatory(),
						traveler.getId());
			}

			// copy address from company to traveler
			List list2 = (List) compService.listAddress(company.getId())
					.getData();
			for (int i = 0; i < list2.size(); i++) {
				AddressPo po = (AddressPo) list2.get(i);
				if (po.getCatalog().toUpperCase().equals("STATEMENT")) {
					continue;
				}
				service.createAddress(po.getCatalog(), po.getAddress(),
						po.getCity(), po.getZipCode(), traveler.getId());
			}

			List listFeecode = (List) compService.listFeeCode(company.getId())
					.getData();
			for (int i = 0; i < listFeecode.size(); i++) {
				FeeCodePo po = (FeeCodePo) listFeecode.get(i);
				FeeCodePo newPo = (FeeCodePo) service.createFeeCode(
						po.getVarname(), po.getFeeType(), po.getFareCode(),
						po.getGroupId(), traveler.getId()).getData();

			}

		} else {
			this.addMessage("message", sm.getServiceMessage());
			if (sm.getCode().equals("4002")) {
				if (isEmpty(traveler.getTravelerType())
						|| traveler.getTravelerType().toLowerCase()
								.equals("traveler")) {
					// get max no of par if the type is 'private' or null

					String travelerNo = traveler.getTravelerNo().substring(6);
					try {
						Integer num = Integer.parseInt(travelerNo);
						if (num >= 999) {
							travelerNo = traveler.getTravelerNo().substring(0,
									6)
									+ (num + 1);
						} else if (num >= 99) {
							travelerNo = traveler.getTravelerNo().substring(0,
									6)
									+ "0" + (num + 1);
						} else if (num >= 9) {
							travelerNo = traveler.getTravelerNo().substring(0,
									6)
									+ "00" + (num + 1);
						} else {
							travelerNo = traveler.getTravelerNo().substring(0,
									6)
									+ "000" + (num + 1);
						}
					} catch (Exception ex) {

					}
					traveler.setTravelerNo(travelerNo);
				} else {
					// get max famliy or work-in no if type is not private and
					// not null.
					String prefix = null;
					if (traveler.getTravelerType().toLowerCase()
							.equals("family")) {
						prefix = "F";
					} else if (traveler.getTravelerType().toLowerCase()
							.equals("work-in")) {
						prefix = "W";
					} else {
						prefix = "N";
					}
					if (traveler.getTravelerNo().indexOf("-") != -1) {
						traveler.setTravelerNo(traveler.getTravelerNo()
								.substring(0,
										traveler.getTravelerNo().indexOf("-")));
					}

					if (!prefix.startsWith("N")) {
						traveler.setTravelerNo(service
								.getMaxPotentialTravelerNo(
										traveler.getTravelerNo(), prefix));
					}

				}

			}
			return INPUT;
		}

		// Object object = service.synchronize(traveler.getTravelerNo(), 0, 1);

		return SUCCESS;
	}
}
