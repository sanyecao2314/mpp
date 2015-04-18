/**
 * BsServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citsamex.ws;

import com.citsamex.app.util.ServiceConfig;

public class BsServiceServiceLocator extends org.apache.axis.client.Service implements com.citsamex.ws.BsServiceService {

	public BsServiceServiceLocator() {
	}

	public BsServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public BsServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for BsServicePort
	private java.lang.String BsServicePort_address = ServiceConfig.getProperty("BS_SERVICE");

	public java.lang.String getBsServicePortAddress() {
		return BsServicePort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String BsServicePortWSDDServiceName = "BsServicePort";

	public java.lang.String getBsServicePortWSDDServiceName() {
		return BsServicePortWSDDServiceName;
	}

	public void setBsServicePortWSDDServiceName(java.lang.String name) {
		BsServicePortWSDDServiceName = name;
	}

	public com.citsamex.ws.BsService getBsServicePort() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(BsServicePort_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getBsServicePort(endpoint);
	}

	public com.citsamex.ws.BsService getBsServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.citsamex.ws.BsServiceServiceSoapBindingStub _stub = new com.citsamex.ws.BsServiceServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getBsServicePortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setBsServicePortEndpointAddress(java.lang.String address) {
		BsServicePort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (com.citsamex.ws.BsService.class.isAssignableFrom(serviceEndpointInterface)) {
				com.citsamex.ws.BsServiceServiceSoapBindingStub _stub = new com.citsamex.ws.BsServiceServiceSoapBindingStub(
						new java.net.URL(BsServicePort_address), this);
				_stub.setPortName(getBsServicePortWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface)
			throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("BsServicePort".equals(inputPortName)) {
			return getBsServicePort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://ws.citsamex.com/", "BsServiceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://ws.citsamex.com/", "BsServicePort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("BsServicePort".equals(portName)) {
			setBsServicePortEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
