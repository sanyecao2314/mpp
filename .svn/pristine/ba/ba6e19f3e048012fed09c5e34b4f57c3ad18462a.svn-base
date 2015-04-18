package com.citsamex.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.citsamex.service.action.Product;
import com.citsamex.service.db.ProductDao;
import com.citsamex.service.db.ProductPo;

public class ProductService extends CommonService implements IProductService {

	private ProductDao dao;

	public ProductDao getDao() {
		return dao;
	}

	public void setDao(ProductDao dao) {
		this.dao = dao;
	}

	@Override
	public ServiceMessage deleteProduct(Long id) {
		ProductPo po = (ProductPo) dao.get(ProductPo.class, id);
		if (po == null) {
			return new ServiceMessage("3003");
		}
		dao.delete(po);
		ServiceMessage sm = new ServiceMessage("3000");
		sm.setData(po.getId());
		return sm;
	}

	@Override
	public ServiceMessage listProduct(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<ProductPo> polist = dao.list(map);
		List<Product> list = new ArrayList<Product>(polist.size());

		for (int i = 0; i < polist.size(); i++) {
			Product product = new Product();
			product.setPo(polist.get(i));
			list.add(product);
		}
		ServiceMessage sm = new ServiceMessage("3000");
		sm.setData(list);
		return sm;
	}

	@Override
	public ServiceMessage findProduct(Long id) {
		Product product = null;
		ProductPo po = (ProductPo) dao.find(ProductPo.class, id);
		if (po != null) {
			product = new Product();
			product.setPo(po);
		}
		ServiceMessage sm = new ServiceMessage("3000");
		sm.setData(product);
		return sm;
	}

	@Override
	public ServiceMessage updateClause(Product product) {
		ProductPo po = product.buildPo();

		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage("3000");
		product.setPo(po);
		sm.setData(product);
		return sm;
	}

	@Override
	public ServiceMessage createProduct(Product product) {
		if (isEmpty(product.getProductNo())) {
			return new ServiceMessage("3001");
		}

		if (dao.find(product.getProductNo()) != null) {
			return new ServiceMessage("3002");
		}

		ProductPo po = product.buildPo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage("3000");
		sm.setData(po);
		return sm;
	}

	@Override
	public ServiceMessage createProduct(String productNo, String productName, String productDesc, String venderName) {
		Product product = new Product();
		product.setProductNo(productNo);
		product.setProductName(productName);
		product.setVenderName(venderName);
		product.setProductDesc(productDesc);
		if (isEmpty(product.getProductNo())) {
			return new ServiceMessage("3001");
		}

		if (dao.find(product.getProductNo()) != null) {
			return new ServiceMessage("3002");
		}

		ProductPo po = product.buildPo();
		po.setCreateuserno(getCurrentUser());
		po.setCreatedatetime(Calendar.getInstance());
		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.save(po);

		ServiceMessage sm = new ServiceMessage("3000");
		product.setPo(po);
		sm.setData(product);
		return sm;
	}

	@Override
	public ServiceMessage updateProduct(Long id, String productNo, String productName, String productDesc,
			String venderName) {
		ProductPo po = (ProductPo) dao.find(ProductPo.class, id);
		if (po != null) {
			po.setProductNo(productNo);
			po.setProductDesc(productDesc);
			po.setProductName(productName);
			po.setVenderName(venderName);
		}

		po.setUpdateuserno(getCurrentUser());
		po.setUpdatedatetime(Calendar.getInstance());
		dao.update(po);

		ServiceMessage sm = new ServiceMessage("3000");
		Product product = new Product();
		product.setPo(po);
		sm.setData(product);
		return sm;
	}

}
