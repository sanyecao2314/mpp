package com.citsamex.service.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

public class CommonDao extends BaseDao {

	@SuppressWarnings("rawtypes")
	public List execute(String sql) {
		Session session = getSession();
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		return query.list();
	}

	public int executeUpdate(String sql) {
		Session session = getSession();
		int i = session.createSQLQuery(sql).executeUpdate();
		return i;
	}

	public int executeUpdate(String sql, String[] conditions, String[] values) {
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < conditions.length; i++) {
			query.setString(conditions[i], values[i]);
		}
		int i = query.executeUpdate();
		return i;
	}
}
