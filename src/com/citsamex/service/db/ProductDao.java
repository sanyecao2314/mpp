package com.citsamex.service.db;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ProductDao extends BaseDao {
	public ProductPo find(String no) {
		String hql = "from ProductPo p where p.productNo = :no";
		Query query = getSession().createQuery(hql);
		return (ProductPo) query.setString("no", no).uniqueResult();
	}

	@SuppressWarnings("rawtypes")
	public List list(Map<String, Object> map) {

		Criteria c = this.createCriteria(map);
		if (map.get("currentPage") != null && map.get("pageSize") != null) {

			Integer currentPage = (Integer) map.get("currentPage");

			Integer pageSize = (Integer) map.get("pageSize");
			c.setFirstResult((currentPage - 1) * pageSize);
			c.setMaxResults(pageSize);
		}
		c.addOrder(Order.asc("venderName")).addOrder(Order.asc("productNo"));
		return c.list();
	}

	private Criteria createCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(ProductPo.class);
		String productNo = (String) map.get("productNo");
		if (!isEmpty(productNo)) {
			c.add(Restrictions.eq("productNo", productNo));
		}
		return c;
	}

}
