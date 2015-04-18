package com.citsamex.service.db;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ClauseDao extends BaseDao {
	public ClausePo find(String catalog, String no) {
		String hql = "from ClausePo c where c.catalog=:catalog and c.no = :no";
		Query query = getSession().createQuery(hql);
		return (ClausePo) query.setString("catalog", catalog).setString("no", no).uniqueResult();
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
		String catalog_no = (String) map.get("catalog_no");
		if (!isEmpty(catalog_no)) {
			if (catalog_no.indexOf("-") != -1) {
				// process query with format 'catalog-001'
				String catalog = catalog_no.substring(0, catalog_no.indexOf("-"));
				c.add(Restrictions.eq("catalog", catalog));

				String no = catalog_no.substring(catalog_no.indexOf("-") + 1, catalog_no.length());
				c.add(Restrictions.like("no", no, MatchMode.END));

			} else {
				// process query with just catalog.
				c.add(Restrictions.like("catalog", catalog_no, MatchMode.END));
			}
		}
		c.addOrder(Order.asc("catalog")).addOrder(Order.asc("no"));
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List listByString(Map<String, Object> map) {

		String hql = "from ClausePo p where (p.catalog || '-' || p.no) in ";

		String cataNoinMap = (String) map.get("catalog_no");

		String cataNoStr = "";
		if (!isEmpty(cataNoinMap)) {
			String[] cataNos = cataNoinMap.split(",");
			for (int i = 0; i < cataNos.length; i++) {
				cataNoStr += "'" + cataNos[i] + "'";
				if (i < cataNos.length - 1) {
					cataNoStr += ",";
				}
			}
			hql += "(" + cataNoStr + ")";
		} else {
			hql += "('')";
		}
		hql += " order by p.catalog, p.no";
		Query query = this.getSession().createQuery(hql);

		return query.list();
	}

	private Criteria createCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(ClausePo.class);
		String no = (String) map.get("no");
		if (!isEmpty(no)) {
			c.add(Restrictions.eq("no", no));
		}
		return c;
	}

}
