package com.citsamex.service.db;

import com.citsamex.app.cfg.Element;
import com.citsamex.app.cfg.ElementMap;
import com.citsamex.app.util.CommonUtil;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.*;

public abstract class BaseDao extends HibernateDaoSupport {

    protected final static CommonUtil uti = new CommonUtil();

    public Object find(@SuppressWarnings("rawtypes") Class clz, long id) {
        return getSession().createCriteria(clz).add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    public void save(Object o) {
        getHibernateTemplate().save(o);
        if (o instanceof MppBasePo) {
            Object his = ((MppBasePo) o).createHisObject();
            if (his != null) {
                uti.copyProperties(his, o);
                uti.setValue(his, "logdatetime", Calendar.getInstance());
                getHibernateTemplate().save(his);
            }

        }
    }

    public void update(Object o) {
        getHibernateTemplate().update(o);
        

        if (o instanceof MppBasePo) {
            Object his = ((MppBasePo) o).createHisObject();
            if (his != null) {
                uti.copyProperties(his, o);
                uti.setValue(his, "logdatetime", Calendar.getInstance());
                getHibernateTemplate().save(his);
            }

        }
    }

    public void saveOrupdate(Object o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public void delete(Object o) {
        getHibernateTemplate().delete(o);
        this.updateActionDataMapping(o.getClass().getName(), CommonUtil
                .getValue(o, "id").toString());
    }

    public Object get(@SuppressWarnings("rawtypes") Class clz, long id) {
        return getHibernateTemplate().get(clz, id);
    }

    public Object load(@SuppressWarnings("rawtypes") Class clz, long id) {
        return getHibernateTemplate().load(clz, id);
    }

    public Transaction getTransaction() {
        return this.getSession().getTransaction();
    }

    public boolean isEmpty(String a) {
        if (a == null || a.isEmpty())
            return true;
        return false;
    }

    @SuppressWarnings("rawtypes")
    public Object findRelation(Class clazz, Long id) {
        return find(clazz, id);
    }

    @SuppressWarnings("rawtypes")
    public List listRelation(String classname, String rId) {
        String hql = "from " + classname + " a where a.relationId = :rid";
        Query query = getSession().createQuery(hql);
        return query.setString("rid", rId).list();
    }

    @SuppressWarnings("rawtypes")
    public void deleteRelation(String classname, String rId) {
        String hql = "delete from " + classname
                + " a where a.relationId = :rid";

        Query query = getSession().createQuery(hql);
        int retVal = query.setString("rid", rId).executeUpdate();
    }

    @SuppressWarnings("rawtypes")
    public List listRelation(String classname, String[] rId) {
        if (rId == null)
            rId = new String[0];
        if (rId.length == 0) {
            rId = new String[]{""};
        }
        String hql = "from " + classname + " a where a.relationId in (:rId)";
        Query query = getSession().createQuery(hql)
                .setParameterList("rId", rId);
        return query.list();
    }

    @SuppressWarnings("rawtypes")
    public List listFeeCodeDetail(String rId, Long feeCodeId, String groupid) {
        String hql = "from FeeCodeDetailPo a where a.groupId=:groupid";
        Query query = getSession().createQuery(hql);
        return query.setString("groupid", groupid).list();
    }

    // @SuppressWarnings("rawtypes")
    // public List listFeeCodeDetail(String rId, Long feeCodeId) {
    // String hql =
    // "from FeeCodeDetailPo a where a.relationId = :rid and a.feeCodeId=:feeCodeId";
    // Query query = getSession().createQuery(hql);
    // return query.setString("rid", rId).setLong("feeCodeId",
    // feeCodeId).list();
    // }

    @SuppressWarnings("rawtypes")
    public Object getLateHis(String classname, Long id) {
        String hql = "from " + classname + " where id=:id order by seq desc";
        Query query = getSession().createQuery(hql).setLong("id", id);
        List list = query.list();
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @SuppressWarnings("rawtypes")
    public List listFeeCodeDetail(String rId, Long feeCodeId,
                                  String validPeriod, String groupid) {
        String hql = "from FeeCodeDetailPo a where validPeriod=:validPeriod and groupId=:groupid";
        Query query = getSession().createQuery(hql);
        return query.setString("validPeriod", validPeriod)
                .setString("groupid", groupid).list();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public List listFeeCodeDetailCurrent(String rId, Long feeCodeId,
                                         String groupid) {
        List ret = new ArrayList();
        String hql = "from FeeCodeDetailPo a where a.groupId=:groupid";
        Query query = getSession().createQuery(hql);
        List list = query.setString("groupid", groupid).list();
        for (int i = 0; i < list.size(); i++) {
            FeeCodeDetailPo po = (FeeCodeDetailPo) list.get(i);
            String[] validPeriod = po.getValidPeriod().split(" to ");
            CommonUtil util = new CommonUtil();

            Calendar start = util.parseDate(validPeriod[0]);
            Calendar end = util.parseDate(validPeriod[1]);

            Calendar cur = Calendar.getInstance();

            if (cur.before(end) && cur.after(start)) {
                ret.add(po);
            }
        }

        return ret;
    }

    public Map<String, DataMapping> listDataMapping(String catalog,
                                                    String[] mppId) {
        String hql = "from DataMapping a where catalog = :catalog and a.mpp in (:mppId)";
        Query query = getSession().createQuery(hql);
        List list = query.setString("catalog", catalog)
                .setParameterList("mppId", mppId).list();
        Map<String, DataMapping> map = new HashMap<String, DataMapping>();
        for (int i = 0; i < list.size(); i++) {
            DataMapping data = (DataMapping) list.get(i);
            map.put(data.getMpp(), data);
        }
        return map;
    }

    public Map<String, DataMapping> listDataMapping(String custno, String action) {
        String hql = "from DataMapping where custno = :custno and action=:action";
        Query query = getSession().createQuery(hql);
        List list = query.setString("custno", custno)
                .setString("action", action).list();
        Map<String, DataMapping> map = new HashMap<String, DataMapping>();
        for (int i = 0; i < list.size(); i++) {
            DataMapping data = (DataMapping) list.get(i);
            map.put(data.getMpp(), data);
        }
        return map;
    }
    
    public List<DataMapping> listDataMapping2(String catalog, String custno) {
        String hql = "from DataMapping where catalog = :catalog and custno = :custno";
        Query query = getSession().createQuery(hql);
        List list = query.setString("custno", custno)
        		.setString("catalog", catalog)
        		.list();
        
        return list;
    }

    public int deleteDataMapping(String catalog, String mppId) {
        String hql = "delete from DataMapping where catalog = :catalog and mpp =:mppId";
        Query query = getSession().createQuery(hql);
        int retVal = query.setString("catalog", catalog)
                .setString("mppId", mppId).executeUpdate();

        return retVal;
    }

    public int deleteDataMapping(String custno) {
        String hql = "delete from DataMapping where custno = :custno";
        Query query = getSession().createQuery(hql);
        int retVal = query.setString("custno", custno).executeUpdate();

        return retVal;
    }

    public int updateActionDataMapping(String catalog, String mppId) {
        String hql = "update DataMapping set action='delete' where catalog = :catalog and mpp =:mppId";
        Query query = getSession().createQuery(hql);
        int retVal = query.setString("catalog", catalog)
                .setString("mppId", mppId).executeUpdate();

        return retVal;
    }

    public void deleteRelations(String rId) {
        String[] pos = {"AddressPo", "ContactPo", "CreditCardPo",
                "DiscountPo", "EBillingPo", "ExtraPo", "FeeCodeDetailPo",
                "FeeCodePo", "GmaxNoPo", "MemberPo", "PreferencePo",
                "PurposeCodePo", "ReasonCodePo"};
        for (int i = 0; i < pos.length; i++) {
            String hql = "from " + pos[i] + " where relationId=:relationId";
            Query query = getSession().createQuery(hql).setString("relationId",
                    rId);
            List list = query.list();
            for (int j = 0; j < list.size(); j++) {
                Object obj = list.get(j);
                String id = CommonUtil.getValue(obj, "id").toString();
                updateActionDataMapping("com.citsamex.service.db." + pos[i], id);
                delete(obj);
            }

        }
    }

    public void deleteRelations(String rId, boolean withoutUpdateDataMap) {
        String[] pos = {"AddressPo", "ContactPo", "CreditCardPo",
                "DiscountPo", "EBillingPo", "ExtraPo", "FeeCodeDetailPo",
                "FeeCodePo", "GmaxNoPo", "MemberPo", "PreferencePo",
                "PurposeCodePo", "ReasonCodePo"};
        for (int i = 0; i < pos.length; i++) {
            String hql = "from " + pos[i] + " where relationId=:relationId";
            Query query = getSession().createQuery(hql).setString("relationId",
                    rId);
            List list = query.list();
            for (int j = 0; j < list.size(); j++) {
                Object obj = list.get(j);
                if (!withoutUpdateDataMap) {
                    String id = CommonUtil.getValue(obj, "id").toString();
                    updateActionDataMapping(
                            "com.citsamex.service.db." + pos[i], id);
                }
                delete(obj);
            }

        }
    }

    public Element findElement(String name, String value) {
        return (Element) getSession().createCriteria(Element.class)
                .add(Restrictions.eq("name", name))
                .add(Restrictions.eq("text", value)).uniqueResult();

    }

    public List listElement(String companyName) {
        return getSession().createCriteria(Element.class)
                .add(Restrictions.eq("company", companyName))
                .addOrder(Order.asc("seq")).list();
    }
    
    public List listElementMain(String companyName,String parent) {
        return getSession().createCriteria(Element.class)
                .add(Restrictions.eq("company", companyName))
                .add(Restrictions.eq("path", parent)).list();
    }
    
    public List listElementSub(String companyName,String parent) {
        List list = getSession().createCriteria(Element.class)
                        .add(Restrictions.eq("company", companyName))
                        .add(Restrictions.like("path2", "%" + parent + "%")).list();
        return list;
    }

    public List listElementMap(String companyName) {
        return getSession().createCriteria(ElementMap.class)
                .add(Restrictions.eq("cpid", companyName)).list();
    }
    
    public List listCardbin() {
        return getSession().createCriteria(CardbinPo.class).list();
    }
}