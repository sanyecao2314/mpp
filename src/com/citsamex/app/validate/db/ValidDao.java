package com.citsamex.app.validate.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.citsamex.service.db.BaseDao;

public class ValidDao extends BaseDao {

	public List<ValidatorPo> listValidators() {
		Criteria c = getSession().createCriteria(ValidatorPo.class);
		return c.list();
	}

	public List<ValidObjectPo> listValidObject() {
		Criteria c = getSession().createCriteria(ValidObjectPo.class);
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	public List list(String hql) {
		Query query = getSession().createQuery(hql);
		return query.list();
	}

}
