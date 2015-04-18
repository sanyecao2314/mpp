package com.citsamex.ws;

public class HRServiceProxy implements com.citsamex.ws.HRService {
  private String _endpoint = null;
  private com.citsamex.ws.HRService hRService = null;
  
  public HRServiceProxy() {
    _initHRServiceProxy();
  }
  
  public HRServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initHRServiceProxy();
  }
  
  private void _initHRServiceProxy() {
    try {
      hRService = (new com.citsamex.ws.HRServiceServiceLocator()).getHRServicePort();
      if (hRService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)hRService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)hRService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (hRService != null)
      ((javax.xml.rpc.Stub)hRService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.citsamex.ws.HRService getHRService() {
    if (hRService == null)
      _initHRServiceProxy();
    return hRService;
  }
  
  @Override
public java.lang.String find(java.lang.String employeeId, java.lang.String password) throws java.rmi.RemoteException{
    if (hRService == null)
      _initHRServiceProxy();
    return hRService.find(employeeId, password);
  }
  
  
}