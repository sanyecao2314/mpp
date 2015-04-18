package com.citsamex.service;

import java.util.Map;

import com.citsamex.service.action.Product;

public interface IProductService {

	public ServiceMessage listProduct(Map<String, Object> map);

	public ServiceMessage findProduct(Long id);

	public ServiceMessage updateClause(Product product);

	public ServiceMessage createProduct(Product product);

	public ServiceMessage createProduct(String productNo, String productName, String productDesc, String venderName);

	public ServiceMessage deleteProduct(Long id);

	public ServiceMessage updateProduct(Long id, String productNo, String productName, String productDesc,
			String venderName);
}
