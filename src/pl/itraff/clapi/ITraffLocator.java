/**
 * ITraffLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.itraff.clapi;

public class ITraffLocator extends org.apache.axis.client.Service implements pl.itraff.clapi.ITraff {
    public ITraffLocator() {
    }

    public ITraffLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ITraffLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for iTraffSoap
    private java.lang.String iTraffSoap_address = "http://clapi.itraff.pl/";

    public java.lang.String getiTraffSoapAddress() {
        return iTraffSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String iTraffSoapWSDDServiceName = "iTraffSoap";

    public java.lang.String getiTraffSoapWSDDServiceName() {
        return iTraffSoapWSDDServiceName;
    }

    public void setiTraffSoapWSDDServiceName(java.lang.String name) {
        iTraffSoapWSDDServiceName = name;
    }

    public pl.itraff.clapi.ITraffSoap getiTraffSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(iTraffSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getiTraffSoap(endpoint);
    }

    public pl.itraff.clapi.ITraffSoap getiTraffSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pl.itraff.clapi.ITraffSoapStub _stub = new pl.itraff.clapi.ITraffSoapStub(portAddress, this);
            _stub.setPortName(getiTraffSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setiTraffSoapEndpointAddress(java.lang.String address) {
        iTraffSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pl.itraff.clapi.ITraffSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                pl.itraff.clapi.ITraffSoapStub _stub = new pl.itraff.clapi.ITraffSoapStub(new java.net.URL(iTraffSoap_address), this);
                _stub.setPortName(getiTraffSoapWSDDServiceName());
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
        if ("iTraffSoap".equals(inputPortName)) {
            return getiTraffSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://clapi.itraff.pl", "iTraff");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://clapi.itraff.pl", "iTraffSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("iTraffSoap".equals(portName)) {
            setiTraffSoapEndpointAddress(address);
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
