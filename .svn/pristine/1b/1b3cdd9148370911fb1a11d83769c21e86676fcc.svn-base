/**
 * AxoServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citsamex.ws;

import com.citsamex.app.util.ServiceConfig;

public class AxoServiceServiceLocator extends org.apache.axis.client.Service implements com.citsamex.ws.AxoServiceService {

    public AxoServiceServiceLocator() {
    }


    public AxoServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AxoServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AxoServicePort
    private java.lang.String AxoServicePort_address =  ServiceConfig.getProperty("AXO_SERVICE");

    public java.lang.String getAxoServicePortAddress() {
        return AxoServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AxoServicePortWSDDServiceName = "AxoServicePort";

    public java.lang.String getAxoServicePortWSDDServiceName() {
        return AxoServicePortWSDDServiceName;
    }

    public void setAxoServicePortWSDDServiceName(java.lang.String name) {
        AxoServicePortWSDDServiceName = name;
    }

    public com.citsamex.ws.AxoService getAxoServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AxoServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAxoServicePort(endpoint);
    }

    public com.citsamex.ws.AxoService getAxoServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.citsamex.ws.AxoServiceServiceSoapBindingStub _stub = new com.citsamex.ws.AxoServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getAxoServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAxoServicePortEndpointAddress(java.lang.String address) {
        AxoServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.citsamex.ws.AxoService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.citsamex.ws.AxoServiceServiceSoapBindingStub _stub = new com.citsamex.ws.AxoServiceServiceSoapBindingStub(new java.net.URL(AxoServicePort_address), this);
                _stub.setPortName(getAxoServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AxoServicePort".equals(inputPortName)) {
            return getAxoServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.citsamex.com/", "AxoServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.citsamex.com/", "AxoServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AxoServicePort".equals(portName)) {
            setAxoServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
