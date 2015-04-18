package com.citsamex.service.db;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.citsamex.app.Server;
import com.citsamex.app.util.CommonUtil;

public class TravelerDao extends BaseDao {
	public TravelerPo find(String travelerNo) {
		String hql = "from TravelerPo t where t.travelerNo = :travelerNo";
		Query query = getSession().createQuery(hql);
		return (TravelerPo) query.setString("travelerNo", travelerNo).uniqueResult();
	}

	public TravelerPo find(String travelerNo, String status) {
		String hql = "from TravelerPo t where t.travelerNo = :travelerNo and t.status = :status";
		Query query = getSession().createQuery(hql);
		return (TravelerPo) query.setString("travelerNo", travelerNo)
				.setString("status", status).uniqueResult();
	}

	public List<TravelerPo>  findByRefno(String refno) {
		String hql = "from TravelerPo t where t.refno = :refno order by t.id desc ";
		Query query = getSession().createQuery(hql);
		return query.setString("refno", refno).list();
	}
	
	public List<TravelerPo>  findByRefno2(String refno2) {
		String hql = "from TravelerPo t where t.refno2 = :refno2 order by t.id desc ";
		Query query = getSession().createQuery(hql);
		return query.setString("refno2", refno2).list();
	}

	@SuppressWarnings("unchecked")
	public List<TravelerPo> findBetween(String travelerNo1, String travelerNo2) {
		String hql = "from TravelerPo t where t.travelerNo between :travelerNo1 and :travelerNo2";
		Query query = getSession().createQuery(hql);
		return query.setString("travelerNo1", travelerNo1).setString("travelerNo2", travelerNo2).list();
	}

	public TravelerPo find(Long id) {
		return (TravelerPo) find(TravelerPo.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TravelerPo> find(String[] travNos) {
		String hql = "from TravelerPo t where t.travelerNo in (:travNos)";
		Query query = getSession().createQuery(hql);
		return query.setParameterList("travNos", travNos).list();
	}

	@SuppressWarnings("unchecked")
	public List<TravelerPo> findByCompanyNo(String companyNo) {
		String hql = "from TravelerPo t where t.travelerNo like :companyNo%";
		Query query = getSession().createQuery(hql);
		return query.setString("companyNo", companyNo).list();
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

		c.addOrder(Order.asc("travelerNo"));
		return c.list();
	}

	public int count(Map<String, Object> map) {
		Criteria criteria = this.createCriteria(map);
		criteria.setProjection(Projections.rowCount());
		return ((Integer) criteria.list().get(0)).intValue();

	}

	public String getMaxTravelerNo(String company) {
		String travelerNo = (String) getSession()
				.createQuery("select max(travelerNo) from TravelerPo where travelerNo like :company")
				.setString("company", company + "%").uniqueResult();

		return travelerNo;
	}

	public int countOfPetentialTraveler(Map<String, Object> map) {
		Criteria criteria = this.createCriteria(map);

		criteria.setProjection(Projections.rowCount());
		return ((Integer) criteria.list().get(0)).intValue();

	}

	private Criteria createCriteria(Map<String, Object> map) {
		Criteria c = getSession().createCriteria(TravelerPo.class);
		String companyName = (String) map.get("companyName");
		if (!isEmpty(companyName))
			c.add(Restrictions.eq("companyName", companyName));

		String companyNo = (String) map.get("companyNo");
		if (!isEmpty(companyNo)) {
			companyNo = companyNo.replaceAll("\\*", "%");
			c.add(Restrictions.like("travelerNo", companyNo, MatchMode.START));
		}

		String quicksearch = (String) map.get("quicksearch");
		if (!isEmpty(quicksearch)) {
			quicksearch = quicksearch.replaceAll("\\*", "%");
			c.add(Restrictions.like("travelerNo", quicksearch, MatchMode.START));
		}

		Integer ebilling = (Integer) map.get("ebilling");
		if (ebilling != null && ebilling == 1) {
			c.add(Restrictions.eq("ebilling", ebilling));
		}

		String quicksearchName = (String) map.get("quicksearchName");
		if (!isEmpty(quicksearchName)) {

			if (quicksearchName.indexOf(" ") != -1) {
				String lastname = quicksearchName.substring(0, quicksearchName.indexOf(" "));
				String firstname = quicksearchName.substring(quicksearchName.indexOf(" ") + 1);
				c.add(Restrictions.or(
						Restrictions.like("travelerName", quicksearchName, MatchMode.ANYWHERE),
						Restrictions.and(Restrictions.like("lastnameEn", lastname, MatchMode.ANYWHERE),
								Restrictions.like("firstnameEn", firstname, MatchMode.ANYWHERE))));
			} else {
				c.add(Restrictions.or(Restrictions.like("travelerName", quicksearchName, MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.like("lastnameEn", quicksearchName, MatchMode.ANYWHERE),
								Restrictions.like("firstnameEn", quicksearchName, MatchMode.ANYWHERE))));
			}

		}
		String quicksearchModify = (String) map.get("quicksearchModify");
		if (!isEmpty(quicksearchModify)) {
			CommonUtil uti = new CommonUtil();
			Calendar c1 = uti.parseDatetime(quicksearchModify + " 00:00:00");
			Calendar c2 = uti.parseDatetime(quicksearchModify + " 23:59:59");
			c.add(Restrictions.between("updatedatetime", c1, c2));

			String username = Server.getCurrentUsername();
			if (!isEmpty(username)) {
				c.add(Restrictions.eq("updateuserno", username));
			}
		}

		String quicksearchCreate = (String) map.get("quicksearchCreate");
		if (!isEmpty(quicksearchCreate)) {
			CommonUtil uti = new CommonUtil();
			Calendar c1 = uti.parseDatetime(quicksearchCreate + " 00:00:00");
			Calendar c2 = uti.parseDatetime(quicksearchCreate + " 23:59:59");
			c.add(Restrictions.between("createdatetime", c1, c2));

			String username = Server.getCurrentUsername();
			if (!isEmpty(username)) {
				c.add(Restrictions.eq("createuserno", username));
			}
		}

		String axo = (String) map.get("axo");
		if (!isEmpty(axo)) {
			c.add(Restrictions.eq("updateuserno", axo));
		}

		String petentialTravelerNo = (String) map.get("petentialTravelerNo");
		if (!isEmpty(petentialTravelerNo)) {
			c.add(Restrictions.like("travelerNo", petentialTravelerNo, MatchMode.ANYWHERE));
		}
		return c;
	}

	public int batchUpdateTravelerStatus(String updateSQL){
		Query query = getSession().createSQLQuery(updateSQL);
		int r = query.executeUpdate();
		getSession().flush();
		return r;
	}
	
	public void deleteTraveler(String travelerNo) {
		String sql = "delete from T_ADDRESS where RELATIONID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_CCARD where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_CONTACT where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_DISCOUNT where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_EBILL where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_EXTRA_DATA where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_FEE_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_FEE_CODE_DETAIL where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_MEMBER where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_PREFER where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_PURPOSE_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_REASON_CODE where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_VISA where RELATION_ID in"
				+ " (select 'T'+rtrim(ltrim(str(ID))) from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%')";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_S_DATA_MAP where CUSTNO like '" + travelerNo + "%'";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

		sql = "delete from T_TRAVELER where TRAVELER_NO like '" + travelerNo + "%'";
		query = getSession().createSQLQuery(sql);
		query.executeUpdate();

	}

	public TravelerHisPo findLastHisTraveler(String travelerNo) {
        String hql = "from TravelerHisPo t where t.travelerNo = :travelerNo order by t.logdatetime desc";
        Query query = getSession().createQuery(hql); 
        query.setString("travelerNo", travelerNo);
        TravelerHisPo po = (TravelerHisPo) query.list().get(0);
        return po;
	}
	
	/**
	 * 根据英文名称,身份证,护照号和其他证件号查询par信息.
	 * @return
	 */
	public TravelerPo findTravelerByICDCOrPassport(String nationality,String firstname_en,String lastname_en,String icidno,String passport1,String passport2,String othercardno){
		if (StringUtils.isEmpty(icidno) && StringUtils.isEmpty(passport1) && StringUtils.isEmpty(passport2) && StringUtils.isEmpty(othercardno)) {
			return null;
		}
		StringBuffer hql = new StringBuffer("from TravelerPo t where status='ACTIVE' and LEN(travelerNo)=10 ");
		if (nationality != null && !"cn".equals(nationality.toLowerCase())) {
			if (StringUtils.isNotEmpty(firstname_en)) {
				hql.append("and firstnameEn='").append(firstname_en).append("' ");
			}
			if (StringUtils.isNotEmpty(lastname_en)) {
				hql.append("and lastnameEn='").append(lastname_en).append("' ");
			}
		}
		hql.append(" and ( 1=2 ");
		if (StringUtils.isNotEmpty(icidno)) {
			hql.append("or icidNo='").append(icidno).append("' ");
		}
		if (StringUtils.isNotEmpty(passport1)) {
			hql.append("or passport1='").append(passport1).append("' ");
		}
		if (StringUtils.isNotEmpty(passport2)) {
			hql.append("or passport2='").append(passport2).append("' ");
		}
		if (StringUtils.isNotEmpty(othercardno)) {
			hql.append("or otherCard='").append(othercardno).append("' ");
		}
		hql.append(" )");
		Query query = getSession().createQuery(hql.toString());
		List list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}else{
			return (TravelerPo) list.get(0); 
		}
	}
}
