package com.citsamex.service.action;

import java.util.List;

import com.citsamex.app.action.BaseAction;
import com.citsamex.service.IProductService;
import com.citsamex.service.ServiceMessage;

public class ProductAction extends BaseAction {

	private static final long serialVersionUID = -5624898997346908442L;
	private long id;

	private Product product;
	private List<Product> list;
	private IProductService service;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public IProductService getService() {
		return service;
	}

	public void setService(IProductService service) {
		this.service = service;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String search() {

		ServiceMessage sm = service.listProduct(map);
		if (!procServMessage(sm)) {
			return INPUT;
		}
		list = (List) sm.getData();

		return SUCCESS;
	}

	public String display() {

		ServiceMessage sm = service.findProduct(id);
		if (!procServMessage(sm)) {
			return INPUT;
		}

		product = (Product) sm.getData();

		return SUCCESS;
	}

	public String update() {

		ServiceMessage sm = service.updateClause(product);
		if (!procServMessage(sm)) {
			return INPUT;
		}

		product = (Product) sm.getData();

		return SUCCESS;
	}

	public String delete() {
		return SUCCESS;
	}

	public String create() {
		if (product == null)
			return INPUT;

		ServiceMessage sm = service.createProduct(product);
		if (!procServMessage(sm))
			return INPUT;

		product = (Product) sm.getData();
		return SUCCESS;
	}

}
