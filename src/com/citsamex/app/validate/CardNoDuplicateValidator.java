package com.citsamex.app.validate;

import java.util.List;

import org.apache.log4j.Logger;

import com.citsamex.app.validate.db.ValidDao;
import com.citsamex.service.db.CompanyPo;
import com.citsamex.service.db.CreditCardPo;
import com.citsamex.service.db.TravelerDao;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.util.SpringUtil;

public class CardNoDuplicateValidator extends AbstractValidator {

    @Override
    public boolean valid(Object object) {
        if (object == null) {
            return false;
        }
        ValidDao dao = (ValidDao) SpringUtil.getBean("validDao");
        TravelerDao tdao = (TravelerDao) SpringUtil.getBean("travelerDao");
        String value = object.toString();
        
        String hql = "from CreditCardPo where cardNo='" + value + "'";
        List list = dao.list(hql);
        Logger.getRootLogger().info("credit card no validate," + hql);
        boolean hasCardno = false;
        for ( int i = 0; i < list.size(); i++) {
            setAttachMessage("");
            CreditCardPo cardPo = (CreditCardPo) list.get(i);
            String relationid = cardPo.getRelationId();
            if (relationid != null && relationid.startsWith("T")) {
                relationid = relationid.substring(1);
                TravelerPo travPo = (TravelerPo) tdao.get(TravelerPo.class, new Long(relationid));
                if (travPo != null && travPo.getStatus().equals("ACTIVE")) {
                    setAttachMessage("-" + travPo.getTravelerNo());
                    hasCardno = true;
                    break;
                }
                
            } else if (relationid != null && relationid.startsWith("C")) {
                relationid = relationid.substring(1);
                CompanyPo company = (CompanyPo) tdao.get(CompanyPo.class,new Long(relationid));
                if (company != null && company.getStatus().equals("ACTIVE")) {
                    setAttachMessage("-" + company.getCompanyNo());
                    hasCardno = true;
                    break;
                }
            }
        }
        
        if (hasCardno) {
            return false;
        }
        return true;
    }

    @Override
    public void setParameter(String params) {
        // TODO Auto-generated method stub

    }

}
