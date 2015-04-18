/**
 * AxoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citsamex.ws;

public interface AxoService extends java.rmi.Remote {
    public java.lang.String cleanCompanyInfo(java.lang.String custno) throws java.rmi.RemoteException;
    public java.lang.String getCompanyID(java.lang.String oldbarno, java.lang.String newbarno) throws java.rmi.RemoteException;
    public java.lang.String updateBarPathFields(java.lang.String custno) throws java.rmi.RemoteException;
    public java.lang.String listCompanyOnline() throws java.rmi.RemoteException;
    public java.lang.String sync(java.lang.String xml) throws java.rmi.RemoteException;
    public void initPasswd(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String changebar(java.lang.String userid, java.lang.String custno, java.lang.String newcustno) throws java.rmi.RemoteException;
    public java.lang.String syncStatus(java.lang.String custno, java.lang.String status, java.lang.String remark) throws java.rmi.RemoteException;
}
