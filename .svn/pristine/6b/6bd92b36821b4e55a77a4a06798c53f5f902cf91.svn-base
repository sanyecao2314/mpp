package com.citsamex.service.db;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class LoginDao extends HibernateDaoSupport {
	@SuppressWarnings("rawtypes")
	public String findUser(String username, String password) {
		String sql = "select EMP_BEDGENO from HR_EMPLOYEE "
				+ " where EMP_PASSWORD = master.dbo.udf_blowfishencrypt(:password,'CFTVEVEok2+x3TGfRu3ShA==')"
				+ " and EMP_BEDGENO = :username";

		Query query = getSession().createSQLQuery(sql).setString("password", password).setString("username", username);

		List list = query.list();
		if (list.size() == 0) {
			return "";
		}
		return username;

	}
}
