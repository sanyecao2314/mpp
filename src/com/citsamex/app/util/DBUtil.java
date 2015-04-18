package com.citsamex.app.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

public class DBUtil {

    /**
     * 以 list 的形式返回   sql 语句的结果
     * @param sql sql statement
     * @param location  CAN BJS SHA
     * @return
     */
    public static List querySql(String sql, String location) {
        List list = new ArrayList();
        Map<String,Object> row = null;
        MultiDataSource ds = new MultiDataSource(location);
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            stat = conn.createStatement();
            Logger.getRootLogger().info("sql excute: " + sql);
            rs = stat.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            while (rs.next()) {
                row = new HashMap<String,Object>();
                for (int i = 1; i <= cols ; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        return list;
    }
    
    public static List<Object> querySqlUniqueResult(String sql, String location) {
        List<Object> list = new ArrayList<Object>();
        MultiDataSource ds = new MultiDataSource(location); 
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= cols ; i++) {
                    list.add(rs.getObject(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        return list;
        
    }
    
    public static int excuteUpdate(String sql, String location) throws Exception {
        MultiDataSource ds = new MultiDataSource(location); 
        Connection conn = null;
        Statement stat = null;
        int rowcount = -1;
        try {
            conn = ds.getConnection();
            stat = conn.createStatement();
            Logger.getRootLogger().info("sql excute: " + sql);
            rowcount = stat.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        return rowcount;
    }
    
    public static Connection getConnection(String location) {
        MultiDataSource ds = new MultiDataSource(location);
        Connection conn = null;
        
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return conn;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        List list = querySql("select top 5 invno,splittype,invstatus,billinvamt from arinv","SHA");
        int i = 0;
        while (i < list.size()) {
            HashMap<String,String> map =  (HashMap<String, String>) list.get(i);
            Set set = map.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Entry e = (Entry) it.next();
                System.out.print(e.getKey() + "= " + e.getValue());
                System.out.print("\t");
            }
            System.out.println();
            i++;
        }
    }

}
