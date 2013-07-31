/**
 * ITraffSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.itraff.clapi;

public interface ITraffSoap extends java.rmi.Remote {

    /**
     * Authorization of user
     */
    public java.util.HashMap auth(java.lang.String client_id, java.lang.String key_clapi, java.lang.String ip) throws java.rmi.RemoteException;
    public java.util.HashMap userGet() throws java.rmi.RemoteException;
    public java.util.HashMap userUpdate(java.util.HashMap info_billing, java.util.HashMap info_invoice) throws java.rmi.RemoteException;
    public java.util.HashMap userDelete() throws java.rmi.RemoteException;
    public java.util.HashMap userLimits() throws java.rmi.RemoteException;
    public java.util.HashMap paymentList() throws java.rmi.RemoteException;
    public java.util.HashMap imageCount() throws java.rmi.RemoteException;
    public java.util.HashMap imageMeta() throws java.rmi.RemoteException;
    public java.util.HashMap imageList(java.util.HashMap params) throws java.rmi.RemoteException;
    public java.util.HashMap imageInsert(java.lang.String id, java.lang.String name, java.lang.String data) throws java.rmi.RemoteException;
    public java.util.HashMap imageUpdate(java.lang.String ID, java.util.HashMap data) throws java.rmi.RemoteException;
    public java.util.HashMap imageDelete(java.lang.String ID) throws java.rmi.RemoteException;
    public java.util.HashMap imageGet(java.lang.String id) throws java.rmi.RemoteException;

    /**
     * Build index for current user
     */
    public java.util.HashMap indexBuild() throws java.rmi.RemoteException;
    public java.util.HashMap callback(org.apache.axis.types.URI callbackURL) throws java.rmi.RemoteException;

    /**
     * Check status of index-bulding process
     */
    public java.util.HashMap indexStatus() throws java.rmi.RemoteException;

    /**
     * Get clapi_key for current user. If param 'regenerate' is true,
     * generate new key before returning it
     */
    public java.util.HashMap keyGet(boolean regenerate) throws java.rmi.RemoteException;
}
