/**
 * FindTravelerId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.citsamex.ws;

public class FindTravelerId  implements java.io.Serializable {
    private java.lang.String travelerNo;

    public FindTravelerId() {
    }

    public FindTravelerId(
           java.lang.String travelerNo) {
           this.travelerNo = travelerNo;
    }


    /**
     * Gets the travelerNo value for this FindTravelerId.
     * 
     * @return travelerNo
     */
    public java.lang.String getTravelerNo() {
        return travelerNo;
    }


    /**
     * Sets the travelerNo value for this FindTravelerId.
     * 
     * @param travelerNo
     */
    public void setTravelerNo(java.lang.String travelerNo) {
        this.travelerNo = travelerNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FindTravelerId)) return false;
        FindTravelerId other = (FindTravelerId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.travelerNo==null && other.getTravelerNo()==null) || 
             (this.travelerNo!=null &&
              this.travelerNo.equals(other.getTravelerNo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTravelerNo() != null) {
            _hashCode += getTravelerNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FindTravelerId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.citsamex.com/", "findTravelerId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("travelerNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "travelerNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
