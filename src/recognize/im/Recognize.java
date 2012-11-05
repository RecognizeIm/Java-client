package recognize.im;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import pl.itraff.clapi.ITraffSoapProxy;

public class Recognize {
	public static void main(String[] args) {

		ITraffSoapProxy iTraff = null;
		try {
			String clientId = "30859", //API User ID
					apiKey = "9f7jbf23e2", //API user key
					clapiKey = "edfe3949ec0r443d4c1434c9bavvb029"; //CLAPI user key
			
			//initialize proxy class
			iTraff = new ITraffSoapProxy(clientId, clapiKey, apiKey);
		
			HashMap result = null;
			
			//read image data
			InputStream imgIs = new FileInputStream("joker.jpg");
			byte[] imgData = new byte[imgIs.available()];
			imgIs.read(imgData);
			imgIs.close();
			
			//insert new image
			result = iTraff.imageInsert("123#", "Image name", Base64.encodeBase64String(imgData));
			
			//apply changes
			result = iTraff.indexBuild();
			
			//check progress of applying changes
			result = iTraff.indexStatus();
			
			//recognize image
			result = iTraff.recognize(imgData);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
