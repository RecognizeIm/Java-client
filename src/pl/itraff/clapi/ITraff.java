/**
 * ITraff.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.itraff.clapi;

public interface ITraff extends javax.xml.rpc.Service {
    public java.lang.String getiTraffSoapAddress();

    public pl.itraff.clapi.ITraffSoap getiTraffSoap() throws javax.xml.rpc.ServiceException;

    public pl.itraff.clapi.ITraffSoap getiTraffSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
