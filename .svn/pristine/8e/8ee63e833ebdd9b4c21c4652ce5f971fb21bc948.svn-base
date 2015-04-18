package com.citsamex.service.db;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class CompanyDao extends BaseDao {
	public CompanyPo find(String companyNo) {
		String hql = "from CompanyPo c where c.companyNo = :companyNo";
		Query query = getSession().createQuery(hql);
		return (CompanyPo) query.setString("companyNo", companyNo).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<CompanyPo> find(String[] companyNo) {
		String hql = "from CompanyPo c where c.companyNo in (:companyNo)";
		Query query = getSession().createQuery(hql);
		return query.setParameterList("companyNo", companyNo).list();
	}

	public CompanyPo find(Long id) {
		return (CompanyPo) find(CompanyPo.class, id);
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
		c.addOrder(Order.asc("companyNo"));
		return c.list();
	}

	public int count(Map<String, Object> map) {
		Criteria criteria = this.createCriteria(map);
		criteria.setProjection(Projections.rowCount());
		return ((Integer) criteria.list().get(0)).intValue();

	}

	private Criteria createCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(CompanyPo.class);
		String custId = (String) map.get("companyName");
		if (!isEmpty(custId))
			c.add(Restrictions.eq("custId", Long.parseLong(custId)));
		String companyNo = (String) map.get("companyNo");
		if (!isEmpty(companyNo)) {
			c.add(Restrictions.eq("companyNo", companyNo));
		}

		String quicksearch = (String) map.get("quicksearch");
		if (!isEmpty(quicksearch)) {
			quicksearch = quicksearch.replaceAll("\\*", "%");
			c.add(Restrictions.like("companyNo", quicksearch, MatchMode.START));
		}

		String quicksearchName = (String) map.get("quicksearchName");
		if (!isEmpty(quicksearchName))
			c.add(Restrictions.like("companyName", quicksearchName, MatchMode.ANYWHERE));

		return c;

	}

	@SuppressWarnings("rawtypes")
	public List listAddress(String rId) {
		String hql = "from AddressPo a where a.relationId = :rid";
		Query query = getSession().createQuery(hql);
		return query.setString("rid", rId).list();
	}

	public AddressPo findAddress(Long id) {
		return (AddressPo) find(AddressPo.class, id);
	}

	public int updateTravelerStatus(String companyNo, String status) {
		String hql = "update TravelerPo t set t.status = :status where t.travelerNo like :companyNo";
		Query query = getSession().createQuery(hql);
		query.setString("status", status).setString("companyNo", companyNo + "%").executeUpdate();

		return 0;
	}

	public int deleteTravelers(String companyNo) {
		String hql = "from TravelerPo t where t.travelerNo like :companyNo";
		Query query = getSession().createQuery(hql);

		List list = query.setString("companyNo", companyNo + "%").list();

		for (int i = 0; i < list.size(); i++) {
			TravelerPo tpo = (TravelerPo) list.get(i);
			deleteRelations("T" + tpo.getId(), true);
			delete(tpo);
		}

		String hql_datamapping = "delete DataMapping mp "
				+ "where mp.custno like :companyNo and mp.custno <> :companyNo2";
		Query query2 = getSession().createQuery(hql_datamapping);
		query2.setString("companyNo", companyNo + "%").setString("companyNo2", companyNo).executeUpdate();

		return 0;
	}

	public void deleteCompany(String companyNo) {
		String sql = "delete from T_ADDRESS where RELATIONID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_CCARD where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_CONTACT where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_DISCOUNT where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_EBILL where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_EXTRA_DATA where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_FEE_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_FEE_CODE_DETAIL where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_MEMBER where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_PREFER where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_PURPOSE_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_REASON_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_VISA where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_S_DATA_MAP where CUSTNO like '" + companyNo + "%'";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_TRAVELER where TRAVELER_NO like '" + companyNo + "%'";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_COMPANY where COMPANY_NO like '" + companyNo + "%'";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

	}
}
