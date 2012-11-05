package recognize.im;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.HashMap;
import org.json.JSONException;
import pl.itraff.clapi.ITraffSoapProxy;

public class Recognize {
	public static void main(String[] args) {

		ITraffSoapProxy iTraff = null;
		try {
			iTraff = new ITraffSoapProxy("33", "c8081c87eff59f7f44a7db9372baaed2", "ea97a5495a");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		try {
			HashMap result = null;
			result = iTraff.indexStatus();
			
			System.out.println(result);
			
			InputStream imgIs = new FileInputStream("joker.jpg");
			byte[] imgData = new byte[imgIs.available()];
			imgIs.read(imgData);
			
			result = iTraff.recognize(imgData);
			System.out.println(result);
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
