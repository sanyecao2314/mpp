package com.citsamex.service.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.citsamex.app.action.BaseAction;
import com.citsamex.service.ICompanyService;
import com.citsamex.service.ICustomerService;
import com.citsamex.service.IProductService;
import com.citsamex.service.ITravelerService;
import com.citsamex.service.ServiceMessage;
import com.citsamex.service.db.DBIPo;
import com.citsamex.service.db.DiscountPo;
import com.citsamex.service.db.FeeCodePo;
import com.citsamex.service.db.TravelerPo;

@SuppressWarnings("serial")
public class CompanyAction extends BaseAction {

	private long id;
	private Company company;
	private Customer customer;
	private Product product;
	private List<Product> productlist;
	private ICompanyService service;
	private ITravelerService travelerService;
	private ICustomerService custService;
	private IProductService prdService;

	private long[] dbiId;
	private String[] dbiKey;
	private int[] dbiKeyValue;
	private String[] dbiValue1;
	private String[] dbiValue2;
	private int[] dbiOptional;

	public long[] getDbiId() {
		return dbiId;
	}

	public void setDbiId(long[] dbiId) {
		this.dbiId = dbiId;
	}

	public String[] getDbiKey() {
		return dbiKey;
	}

	public void setDbiKey(String[] dbiKey) {
		this.dbiKey = dbiKey;
	}

	public int[] getDbiKeyValue() {
		return dbiKeyValue;
	}

	public void setDbiKeyValue(int[] dbiKeyValue) {
		this.dbiKeyValue = dbiKeyValue;
	}

	public String[] getDbiValue1() {
		return dbiValue1;
	}

	public void setDbiValue1(String[] dbiValue1) {
		this.dbiValue1 = dbiValue1;
	}

	public String[] getDbiValue2() {
		return dbiValue2;
	}

	public void setDbiValue2(String[] dbiValue2) {
		this.dbiValue2 = dbiValue2;
	}

	public int[] getDbiOptional() {
		return dbiOptional;
	}

	public void setDbiOptional(int[] dbiOptional) {
		this.dbiOptional = dbiOptional;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ITravelerService getTravelerService() {
		return travelerService;
	}

	public void setTravelerService(ITravelerService travelerService) {
		this.travelerService = travelerService;
	}

	public ICompanyService getService() {
		return service;
	}

	public void setService(ICompanyService service) {
		this.service = service;
	}

	public String search() {
		return SUCCESS;
	}

	public ICustomerService getCustService() {
		return custService;
	}

	public void setCustService(ICustomerService custService) {
		this.custService = custService;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public IProductService getPrdService() {
		return prdService;
	}

	public void setPrdService(IProductService prdService) {
		this.prdService = prdService;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String display() {
		ServiceMessage sm = service.findCompany(id);

		if (sm.isSuccess()) {
			company = (Company) sm.getData();
			customer = (Customer) custService.findCustomer(company.getCustId())
					.getData();

			Map map = new HashMap();
			map.put("relationId", "C" + company.getId());
			ServiceMessage sm2 = prdService.listProduct(map);
			productlist = (List) sm2.getData();
		} else
			this.addMessage("message", sm.getServiceMessage());

		return SUCCESS;
	}

	@SuppressWarnings("unused")
	public String update() {
		// company.setDirty(1);
		ServiceMessage sm = service.updateCompany(company);

		if (!sm.isSuccess()) {
			return INPUT;
		}

		if (dbiKey != null) {
			for (int i = 0; i < dbiKey.length; i++) {
				DBIPo po = new DBIPo();
				po.setId(dbiId[i]);
				po.setDbiKey(dbiKey[i]);
				po.setDbiKeyValue(dbiKeyValue[i]);
				po.setOptional(dbiOptional[i]);
				po.setValue1(dbiValue1[i]);
				po.setValue2(dbiValue2[i]);
				po.setRelationId("C" + company.getId());
				service.updateDBI(po);
			}
		}

		company = (Company) sm.getData();

		Object object = travelerService.synchronize(company.getCompanyNo(), 0,
				1, "PS");
		
//		if (company.getEbilling() != company.getEbillingPrevious()) {
//			
//		}
//		ServiceMessage sm2 =  travelerService.findByCompanyNo(company.getCompanyNo());
//		if (sm2.isSuccess()) {
//			List<TravelerPo> travList = (List<TravelerPo>) sm2.getData();
//			for(TravelerPo t: travList) {
//				
//			}
//		}
		
		
		
		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public String create() {
		if (customer == null)
			return "step1";

		ServiceMessage sm1 = service.findCustomer(customer.getName());
		if (!sm1.isSuccess()) {
			return "step1";
		}
		customer = (Customer) sm1.getData();

		if (company == null)
			return INPUT;

		company.setDirty(1);
		ServiceMessage sm2 = service.create(company);

		if (sm2.isSuccess()) {
			company = (Company) sm2.getData();
			List list = (List) custService.listDiscount(customer.getId())
					.getData();
			for (int i = 0; i < list.size(); i++) {
				DiscountPo po = (DiscountPo) list.get(i);
				service.createDiscount(po.getVarname(), po.getProdId(),
						po.getRemark(), po.getDiscountRate(), company.getId());
			}

			List listFeecode = (List) custService.listFeeCode(customer.getId())
					.getData();
			for (int i = 0; i < listFeecode.size(); i++) {
				FeeCodePo po = (FeeCodePo) listFeecode.get(i);
				FeeCodePo newPo = (FeeCodePo) service.createFeeCode(
						po.getVarname(), po.getFeeType(), po.getFareCode(),
						po.getGroupId(), company.getId()).getData();

				// List listFeecodeDetail = (List)
				// custService.listFeeCodeDetail(customer.getId(),
				// po.getId()).getData();
				// for (int j = 0; j < listFeecodeDetail.size(); j++) {
				// FeeCodeDetailPo detailpo = (FeeCodeDetailPo)
				// listFeecodeDetail.get(j);
				//
				// service.createFeeCodeDetail(detailpo.getGroupId(),
				// detailpo.getValidPeriod(),
				// detailpo.getProductCode(), detailpo.getMoneyRangeFrom(),
				// detailpo.getMoneyRangeTo(),
				// detailpo.getFeeAmt(), detailpo.getFeePercent(),
				// detailpo.getFeeMethod(),
				// detailpo.getFeeType(), company.getId(), newPo.getId());
				// }

			}

		} else {
			this.addMessage("message", sm2.getServiceMessage());
			return INPUT;
		}

		Object object = travelerService.synchronize(company.getCompanyNo(), 0,
				1, "PS");

		return SUCCESS;
	}
}
