package com.citsamex.service.db;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CustomerDao extends BaseDao {
	public CustomerPo find(String name) {
		String hql = "from CustomerPo c where c.name = :name";
		Query query = getSession().createQuery(hql);
		return (CustomerPo) query.setString("name", name).uniqueResult();
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
		c.addOrder(Order.asc("id"));
		return c.list();
	}

	public int count(Map<String, Object> map) {
		Criteria criteria = this.createCriteria(map);
		criteria.setProjection(Projections.rowCount());
		return ((Integer) criteria.list().get(0)).intValue();

	}

	private Criteria createCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(CustomerPo.class);
		String quickSearch = (String) map.get("quickSearch");

		if (!isEmpty(quickSearch)) {
			quickSearch = quickSearch.replaceAll("\\*", "%");
			c.add(Restrictions.like("name", quickSearch, MatchMode.ANYWHERE));
		}
		return c;
	}

	public int updateCompanyStatus(String companyName, String status) {
		String hql = "update CompanyPo c set c.status = :status where c.companyName=:companyName";
		Query query = getSession().createQuery(hql);
		return query.setString("status", status).setString("companyName", companyName).executeUpdate();
	}

	public int updateTravelerStatus(String companyName, String status) {
		String hql = "update TravelerPo t set t.status = :status where t.companyName=:companyName";
		Query query = getSession().createQuery(hql);
		return query.setString("status", status).setString("companyName", companyName).executeUpdate();
	}

	public int deleteCompanys(Long custid) {

		String hql = "from CompanyPo c where c.custId=:custid";
		List<CompanyPo> list = getSession().createQuery(hql).setLong("custid", custid).list();
		for (int i = 0; i < list.size(); i++) {
			CompanyPo company = list.get(i);
			String hql_datamapping = "update DataMapping mp set mp.action = 'delete' " + "where mp.custno = :companyNo";
			getSession().createQuery(hql_datamapping).setString("companyNo", company.getCompanyNo()).executeUpdate();
			this.delete(company);
			this.deleteRelations("C" + company.getId());
			this.deleteDataMapping(company.getCompanyNo());
		}
		return 0;
	}

	public int deleteTravelers(String companyName) {
		String hql = "from TravelerPo t where t.companyName = :companyName";
		List<TravelerPo> list = getSession().createQuery(hql).setString("companyName", companyName).list();

		for (int i = 0; i < list.size(); i++) {
			TravelerPo traveler = list.get(i);
			String hql_datamapping = "update DataMapping mp set mp.action = 'delete' "
					+ "where mp.custno = :travelerNo";
			getSession().createQuery(hql_datamapping).setString("travelerNo", traveler.getTravelerNo()).executeUpdate();
			this.delete(traveler);
			this.deleteRelations("T" + traveler.getId());
			this.deleteDataMapping(traveler.getTravelerNo());
		}

		return 0;

	}

	public GmaxNoPo findGmaxNo(String gmaxNo) {
		String hql = "from GmaxNoPo a where a.gmaxNo = :gmaxNo";
		Query query = getSession().createQuery(hql);
		GmaxNoPo po = (GmaxNoPo) query.setString("gmaxNo", gmaxNo).uniqueResult();
		if (po == null) {
			po = new GmaxNoPo();
			po.setGmaxNo(gmaxNo);
			this.save(po);
		}
		return po;
	}

	public void deleteBarCfg(long id, String path2) {
		String sql = "delete from T_BAR_CFG where id='" + id + "' or path2_ = '" + path2 + "-" + id
				+ "' or path2_ like '" + path2 + "-" + id + "-%'";

		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();

	}

}
