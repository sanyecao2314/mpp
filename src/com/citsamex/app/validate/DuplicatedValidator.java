package com.citsamex.app.validate;

import java.util.List;

import org.apache.log4j.Logger;

import com.citsamex.app.validate.db.ValidDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.util.SpringUtil;

public class DuplicatedValidator extends AbstractValidator {

	@Override
	public boolean valid(Object object) {
		if (object == null) {
			return false;
		}
		String value = object.toString();
		ValidDao dao = (ValidDao) SpringUtil.getBean("validDao");

		String fields[] = value.split(";");
		String id = fields[0].substring(fields[0].indexOf("=") + 1);
		String travelerName = fields[1].substring(fields[1].indexOf("=") + 1);
		String email = fields[2].substring(fields[2].indexOf("=") + 1);
		String icid = fields[3].substring(fields[3].indexOf("=") + 1);
		String passport1 = fields[4].substring(fields[4].indexOf("=") + 1);
		String passport2 = fields[5].substring(fields[5].indexOf("=") + 1);
		String status = fields[6].substring(fields[6].indexOf("=") + 1);
		String travelerNo = fields[7].substring(fields[7].indexOf("=") + 1);

		if ("INACTIVE".equalsIgnoreCase(status)) {
			return true;
		}

		if (travelerNo.length() > 10) {
		    return true;
		}
		
		travelerName = travelerName.replace("'", "''");
		
//		String sql = "from TravelerPo where status='active' and id <> " + id
//				+ " and travelerName='" + travelerName + "' and email = '"
//				+ email + "'";

		// new style validate, void email
		// void traveler name ,cause if duplicate, will prompt the problem traveler no
        String sql = "from TravelerPo where status='active' and id <> " + id
                //+ " and travelerName='" + travelerName + "'"
                + " and travelerNo not like '%-F%'" ;
		
		String sql1 = "";
		if (icid != null && !icid.equals("")) {
			sql1 += "icidNo = '" + icid + "'";
		}

		if (passport1 != null && !passport1.trim().equals("")) {
			if (!sql1.equals("")) {
				sql1 += " or ";
			}
			sql1 += " passport1='" + passport1 + "'";
		}

		if (passport2 != null && !passport2.trim().equals("")) {
			if (!sql1.equals("")) {
				sql1 += " or ";
			}
			sql1 += " passport2='" + passport2 + "'";
		}

		if (!sql1.equals("")) {
			sql += " AND (" + sql1 + ")";
		}

		if (sql1.equals("")) {
		    return true;
		}
		Logger.getRootLogger().info(sql);
		List list = dao.list(sql);
		if (list.size() > 0) {
		    TravelerPo po = (TravelerPo) list.get(0);
		    setAttachMessage("-" + po.getTravelerNo());
			return false;
		}
		return true;
	}

	@Override
	public void setParameter(String params) {

	}
	
	public boolean validforAXO(TravelerPo po) {
	    ValidDao dao = (ValidDao) SpringUtil.getBean("validDao");
	    String id = po.getId() + "";
	    String icid = po.getIcidNo();
	    String passport1 = po.getPassport1();
	    String passport2 = po.getPassport2();
	    String status = po.getStatus();
	    
        if ("INACTIVE".equalsIgnoreCase(status)) {
            return true;
        }
	       
        String sql = "from TravelerPo where status='active' and id <> " + id
                + " and travelerNo not like '%-F%'" ;
        
        String sql1 = "";
        if (icid != null && !icid.equals("")) {
            sql1 += "icidNo = '" + icid + "'";
        }

        if (passport1 != null && !passport1.trim().equals("")) {
            if (!sql1.equals("")) {
                sql1 += " or ";
            }
            sql1 += " passport1='" + passport1 + "'";
        }

        if (passport2 != null && !passport2.trim().equals("")) {
            if (!sql1.equals("")) {
                sql1 += " or ";
            }
            sql1 += " passport2='" + passport2 + "'";
        }

        if (!sql1.equals("")) {
            sql += " AND (" + sql1 + ")";
        }

        if (sql1.equals("")) {
            return true;
        }
        Logger.getRootLogger().info(sql);
        List list = dao.list(sql);
        if (list.size() > 0) {
            TravelerPo t = (TravelerPo) list.get(0);
            setAttachMessage("-" + t.getTravelerNo());
            return false;
        }
        return true;
	}
}
