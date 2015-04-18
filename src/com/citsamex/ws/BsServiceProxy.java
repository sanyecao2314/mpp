package com.citsamex.ws;

public class BsServiceProxy implements com.citsamex.ws.BsService {
  private String _endpoint = null;
  private com.citsamex.ws.BsService bsService = null;
  
  public BsServiceProxy() {
    _initBsServiceProxy();
  }
  
  public BsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initBsServiceProxy();
  }
  
  private void _initBsServiceProxy() {
    try {
      bsService = (new com.citsamex.ws.BsServiceServiceLocator()).getBsServicePort();
      if (bsService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bsService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bsService != null)
      ((javax.xml.rpc.Stub)bsService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.citsamex.ws.BsService getBsService() {
    if (bsService == null)
      _initBsServiceProxy();
    return bsService;
  }
  
  public java.lang.String sync(java.lang.String xml) throws java.rmi.RemoteException{
    if (bsService == null)
      _initBsServiceProxy();
    return bsService.sync(xml);
  }
  
  public java.lang.String syncStatus(java.lang.String custno, java.lang.String status, java.lang.String remark) throws java.rmi.RemoteException{
    if (bsService == null)
      _initBsServiceProxy();
    return bsService.syncStatus(custno, status, remark);
  }
  
  public java.lang.String findTravelerId(java.lang.String travelerNo) throws java.rmi.RemoteException{
    if (bsService == null)
      _initBsServiceProxy();
    return bsService.findTravelerId(travelerNo);
  }
  
  public java.lang.String loadTraveler(java.lang.String barno) throws java.rmi.RemoteException{
    if (bsService == null)
      _initBsServiceProxy();
    return bsService.loadTraveler(barno);
  }
  
  
}