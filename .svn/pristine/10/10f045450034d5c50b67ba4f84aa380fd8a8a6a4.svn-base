package com.citsamex.ws;

public class PsServiceProxy implements com.citsamex.ws.PsService {
  private String _endpoint = null;
  private com.citsamex.ws.PsService psService = null;
  
  public PsServiceProxy() {
    _initPsServiceProxy();
  }
  
  public PsServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initPsServiceProxy();
  }
  
  private void _initPsServiceProxy() {
    try {
      psService = (new com.citsamex.ws.PsServiceServiceLocator()).getPsServicePort();
      if (psService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)psService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)psService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (psService != null)
      ((javax.xml.rpc.Stub)psService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.citsamex.ws.PsService getPsService() {
    if (psService == null)
      _initPsServiceProxy();
    return psService;
  }
  
  public java.lang.String deleteTmrPar(java.lang.String tmrNo) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.deleteTmrPar(tmrNo);
  }
  
  public java.lang.String createBarProperties(java.lang.String barNo, java.lang.String string) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.createBarProperties(barNo, string);
  }
  
  public java.lang.String getDependant(java.lang.String custno) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.getDependant(custno);
  }
  
  public java.lang.String sync(java.lang.String custno, java.lang.String xml) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.sync(custno, xml);
  }
  
  public java.lang.String getPar(java.lang.String text) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.getPar(text);
  }
  
  public java.lang.String createTmrPar(java.lang.String gMaxNo, java.lang.String tmrName, java.lang.String tmrNo, java.lang.String tmr1, java.lang.String tmr2, java.lang.String tmr3, java.lang.String tmr4, java.lang.String tmr5) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.createTmrPar(gMaxNo, tmrName, tmrNo, tmr1, tmr2, tmr3, tmr4, tmr5);
  }
  
  public java.lang.String syncStatus(java.lang.String custno, java.lang.String status, java.lang.String remark) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.syncStatus(custno, status, remark);
  }
  
  public java.lang.String getNewParNo(java.lang.String text) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.getNewParNo(text);
  }
  
  public java.lang.String getTMRPar(java.lang.String tmrPar) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.getTMRPar(tmrPar);
  }
  
  public java.lang.String getBar(java.lang.String text) throws java.rmi.RemoteException{
    if (psService == null)
      _initPsServiceProxy();
    return psService.getBar(text);
  }
  
  
}