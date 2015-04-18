package com.citsamex.ws;

import java.rmi.RemoteException;

public class AxoServiceProxy implements com.citsamex.ws.AxoService {
	private String _endpoint = null;
	private com.citsamex.ws.AxoService axoService = null;

	public AxoServiceProxy() {
		_initAxoServiceProxy();
	}

	public AxoServiceProxy(String endpoint) {
		_endpoint = endpoint;
		_initAxoServiceProxy();
	}

	private void _initAxoServiceProxy() {
		try {
			axoService = (new com.citsamex.ws.AxoServiceServiceLocator())
					.getAxoServicePort();
			if (axoService != null) {
				if (_endpoint != null)
					((javax.xml.rpc.Stub) axoService)
							._setProperty(
									"javax.xml.rpc.service.endpoint.address",
									_endpoint);
				else
					_endpoint = (String) ((javax.xml.rpc.Stub) axoService)
							._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (javax.xml.rpc.ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (axoService != null)
			((javax.xml.rpc.Stub) axoService)._setProperty(
					"javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public com.citsamex.ws.AxoService getAxoService() {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService;
	}

	public java.lang.String cleanCompanyInfo(java.lang.String custno)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.cleanCompanyInfo(custno);
	}

	public java.lang.String listCompanyOnline() throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.listCompanyOnline();
	}

	public java.lang.String sync(java.lang.String xml)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.sync(xml);
	}

	public void initPasswd(java.lang.String arg0)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		axoService.initPasswd(arg0);
	}

	public java.lang.String changebar(java.lang.String userid,
			java.lang.String custno, java.lang.String newcustno)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.changebar(userid, custno, newcustno);
	}
	
	public java.lang.String getCompanyID(java.lang.String oldbarno, java.lang.String newbarno)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.getCompanyID(oldbarno,newbarno);
	}

	public java.lang.String syncStatus(java.lang.String custno,
			java.lang.String status, java.lang.String remark)
			throws java.rmi.RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		return axoService.syncStatus(custno, status, remark);
	}

	@Override
	public String updateBarPathFields(String custno) throws RemoteException {
		if (axoService == null)
			_initAxoServiceProxy();
		
		return	axoService.updateBarPathFields(custno);
	}

}