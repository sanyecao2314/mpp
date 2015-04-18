/**
 * PsServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citsamex.ws;

import com.citsamex.app.util.ServiceConfig;

public class PsServiceServiceLocator extends org.apache.axis.client.Service implements com.citsamex.ws.PsServiceService {

	public PsServiceServiceLocator() {
	}

	public PsServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public PsServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName)
			throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for PsServicePort
	private java.lang.String PsServicePort_address = ServiceConfig.getProperty("PS_SERVICE");

	public java.lang.String getPsServicePortAddress() {
		return PsServicePort_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String PsServicePortWSDDServiceName = "PsServicePort";

	public java.lang.String getPsServicePortWSDDServiceName() {
		return PsServicePortWSDDServiceName;
	}

	public void setPsServicePortWSDDServiceName(java.lang.String name) {
		PsServicePortWSDDServiceName = name;
	}

	public com.citsamex.ws.PsService getPsServicePort() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(PsServicePort_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getPsServicePort(endpoint);
	}

	public com.citsamex.ws.PsService getPsServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			com.citsamex.ws.PsServiceServiceSoapBindingStub _stub = new com.citsamex.ws.PsServiceServiceSoapBindingStub(
					portAddress, this);
			_stub.setPortName(getPsServicePortWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setPsServicePortEndpointAddress(java.lang.String address) {
		PsServicePort_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (com.citsamex.ws.PsService.class.isAssignableFrom(serviceEndpointInterface)) {
				com.citsamex.ws.PsServiceServiceSoapBindingStub _stub = new com.citsamex.ws.PsServiceServiceSoapBindingStub(
						new java.net.URL(PsServicePort_address), this);
				_stub.setPortName(getPsServicePortWSDDServiceName());
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
		if ("PsServicePort".equals(inputPortName)) {
			return getPsServicePort();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://ws.citsamex.com/", "PsServiceService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://ws.citsamex.com/", "PsServicePort"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("PsServicePort".equals(portName)) {
			setPsServicePortEndpointAddress(address);
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
