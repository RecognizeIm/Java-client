package pl.itraff.clapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class ITraffSoapProxy implements pl.itraff.clapi.ITraffSoap {
  private String _endpoint = null;
  private pl.itraff.clapi.ITraffSoap iTraffSoap = null;
  private String clientId = null, clapiKey = null, apiKey = null;
  
  public ITraffSoapProxy() {
    _initITraffSoapProxy();
  }
  
  public ITraffSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initITraffSoapProxy();
  }
  
  public ITraffSoapProxy(String clientId, String clapiKey, String apiKey) throws RemoteException {
	  _initITraffSoapProxy();
	  this.clientId = clientId;
	  this.clapiKey = clapiKey;
	  this.apiKey = apiKey;
	  
	  this.auth(clientId, clapiKey, null);
  }
  
  private void _initITraffSoapProxy() {
    try {
      iTraffSoap = (new pl.itraff.clapi.ITraffLocator()).getiTraffSoap();
      if (iTraffSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iTraffSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iTraffSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
        ((org.apache.axis.client.Stub) iTraffSoap).setMaintainSession(true);
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iTraffSoap != null)
      ((javax.xml.rpc.Stub)iTraffSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pl.itraff.clapi.ITraffSoap getITraffSoap() {
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap;
  }
  
  public java.util.HashMap auth(java.lang.String client_id, java.lang.String key_clapi, java.lang.String ip) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.auth(client_id, key_clapi, ip);
  }
  
  public java.util.HashMap userGet() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.userGet();
  }
  
  public java.util.HashMap userUpdate(java.util.HashMap info_billing, java.util.HashMap info_invoice) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.userUpdate(info_billing, info_invoice);
  }
  
  public java.util.HashMap userDelete() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.userDelete();
  }
  
  public java.util.HashMap userPro() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.userPro();
  }
  
  public java.util.HashMap userLimits() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.userLimits();
  }
  
  public java.util.HashMap paymentList() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.paymentList();
  }
  
  public java.util.HashMap imageCount() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageCount();
  }
  
  public java.util.HashMap imageMeta() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageMeta();
  }
  
  public java.util.HashMap imageList(java.util.HashMap params) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageList(params);
  }
  
  public java.util.HashMap imageInsert(java.lang.String id, java.lang.String name, java.lang.String data) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageInsert(id, name, data);
  }
  
  public java.util.HashMap imageUpdate(java.lang.String ID, java.util.HashMap data) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageUpdate(ID, data);
  }
  
  public java.util.HashMap imageDelete(java.lang.String ID) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageDelete(ID);
  }
  
  public java.util.HashMap imageGet(java.lang.String id) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.imageGet(id);
  }
  
  public java.util.HashMap indexBuild() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.indexBuild();
  }
  
  public java.util.HashMap callback(org.apache.axis.types.URI callbackURL) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.callback(callbackURL);
  }
  
  public java.util.HashMap indexStatus() throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.indexStatus();
  }
  
  public java.util.HashMap keyGet(boolean regenerate) throws java.rmi.RemoteException{
    if (iTraffSoap == null)
      _initITraffSoapProxy();
    return iTraffSoap.keyGet(regenerate);
  }
  
  public java.util.HashMap recognize(byte[] imgData) throws IOException, JSONException {
	  String md5hash = getMD5FromKeyAndImage(apiKey, imgData);
		
		URL url = new URL("http://clapidev.pandora.itraff.pl/recognize/33");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "image/jpeg");
		conn.addRequestProperty("x-itraff-hash", md5hash);

		OutputStream os = conn.getOutputStream();
		os.write(imgData);
		os.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		String output;
		output = getResponseJson(br);
		
		JSONObject jdata = new JSONObject(output);
		
		Iterator<String> nameItr = jdata.keys();
		HashMap<String, String> outMap = new HashMap<String, String>();
		while(nameItr.hasNext()) {
			String key = nameItr.next();
		    outMap.put(key, jdata.getString(key));

		}
		conn.disconnect();
		return outMap;
  }
  
  private String getMD5FromKeyAndImage(String clientKey, byte[] image) {
		String hash = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.reset();
			md.update(clientKey.getBytes("UTF-8"));
			md.update(image);
			byte[] array = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			hash = sb.toString();
		} catch (Exception e) {
			hash = null;
		}
		return hash;
	}
  
  private String getResponseJson(BufferedReader in) throws IOException {
		StringBuffer sb = new StringBuffer("");
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = in.readLine()) != null) {
			sb.append(line + NL);
		}
		in.close();
		return sb.toString();
	}
}