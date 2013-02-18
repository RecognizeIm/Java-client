package recognize.im;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.itraff.clapi.ITraffSoapProxy;

public class Recognize {

	// These are the credentials (you can find it at:
	// https://www.recognize.im/user/profile)
	static String clientId = "001"; // Client ID
	static String apiKey = "01xyz01"; // API Key
	static String clapiKey = "01abc01xyz01"; // CLAPI Key

	public static void main(String[] args) {

		byte[] imgData = readImageData("joker.jpg");
		if (imgData == null) {
			System.err.println("Given image could not be read");
			return;
		}
		try {

			// initialize proxy class (this perform authorisation)
			ITraffSoapProxy iTraffSaop = new ITraffSoapProxy(clientId, clapiKey, apiKey);

			// insert new image with 123# as ID and "Image name" as the name
			// (examplary response: {status=0})
			Map result = iTraffSaop.imageInsert("123#", "Image name", Base64.encodeBase64String(imgData));

			// apply changes (this needs to be invoked before the newly added
			// (examplary response: {status=0})
			// picture can be recognizable)
			result = iTraffSaop.indexBuild();

			// check progress of applying the changes (examplary response:
			// {status=0, data={progress=100, status=ok, uploading=0,
			// needUpdate=0}})
			result = iTraffSaop.indexStatus();

			// recognize image (this returns all the results from the
			// recognition)
			result = recognizeReturnAll(imgData);
			List recognizedIdsList = (List) result.get("id"); // list of
																// recognized
																// images ids

			// recognize image (this returns only the best result from the
			// recognition)
			result = recognizeReturnBest(imgData);
			List recognizedIdList = (List) result.get("id"); // list containing
																// the best
																// recognizion
																// result (size
																// == 1)
			// recognize image (in multiple mode). Remember to change the mode at recognize.im first.
			result = recognizeMulti(imgData);
			List recognizedIdMultiList = (List) result.get("id"); // list containing
																// the best
																// recognizion
																// result (size
																// == 1)


		} catch (RemoteException e) {
			// TODO handle the case when there was a proble with accessing the
			// remote SAOP service
		} catch (JSONException e) {
			// TODO handle the case when the response from the server was not in
			// the JSON format
		} catch (IOException e) {
			// TODO handle the case when there wa IO problem with sending the
			// request or receiving the response
		}

	}

	/**
	 * Reads image data
	 * 
	 * @param imageName
	 *            name of the image file to be read
	 * @return array of bytes containing image data, or null if image could not
	 *         be read
	 */
	private static byte[] readImageData(String imageName) {
		byte[] imgData = null;
		try {
			// read image data
			InputStream imgIs = new FileInputStream(imageName);
			imgData = new byte[imgIs.available()];
			imgIs.read(imgData);
			imgIs.close();
		} catch (IOException e) {
			// TODO handle IOException when there is a problem with reading
			// image data
		}
		return imgData;
	}

	/**
	 * Calls the recognition method which returnes only the best recognition
	 * result
	 * 
	 * @param imgData
	 *            array of bytes of image data
	 * @return map containing the result from the recognition service. It
	 *         contains "status" key (value for this key is a String) and "id"
	 *         keys (value for this key of a List) OR null if there was an
	 *         internal error.
	 * @throws IOException
	 *             when there was an IO problem with accessing the recognition
	 *             service
	 * @throws JSONException
	 *             when the result from the recognition service was not in JSON
	 *             format
	 */
	public static Map recognizeReturnBest(byte[] imgData) throws IOException, JSONException {
		URL url = new URL("http://recognize.im/recognize/" + clientId);
		return recognize(imgData, url);
	}

	/**
	 * Calls the recognition method which returnes all of the recognition
	 * results
	 * 
	 * @param imgData
	 *            array of bytes of image data
	 * @return map containing the result from the recognition service. It
	 *         contains "status" key (value for this key is a String) and "id"
	 *         keys (value for this key of a List) OR null if there was an
	 *         internal error.
	 * @throws IOException
	 *             when there was an IO problem with accessing the recognition
	 *             service
	 * @throws JSONException
	 *             when the result from the recognition service was not in JSON
	 *             format
	 */
	public static Map recognizeReturnAll(byte[] imgData) throws IOException, JSONException {
		URL url = new URL("http://recognize.im/recognize/allResults/" + clientId);
		return recognize(imgData, url);
	}
	
	/**
	 * Calls the recognition method in multi mode which returnes all of the recognition
	 * objects on the taken photo
	 * 
	 * @param imgData
	 *            array of bytes of image data
	 * @return map containing the result from the recognition service. It
	 *         contains "status" key (value for this key is a String) and "id"
	 *         keys (value for this key of a List) OR null if there was an
	 *         internal error.
	 * @throws IOException
	 *             when there was an IO problem with accessing the recognition
	 *             service
	 * @throws JSONException
	 *             when the result from the recognition service was not in JSON
	 *             format
	 */
	public static Map recognizeMulti(byte[] imgData) throws IOException, JSONException {
		URL url = new URL("http://recognize.im/recognize/multi/" + clientId);
		return recognize(imgData, url);
	}

	/**
	 * Calls the recognition service at the given url
	 * 
	 * @param imgData
	 *            array of bytes of image data
	 * @param url
	 *            URL of the recognition service
	 * @return map containing the result from the recognition service. It
	 *         contains "status" key (value for this key is a String) and "id"
	 *         keys (value for this key of a List) OR null if there was an
	 *         internal error.
	 * @throws IOException
	 *             when there was an IO problem with accessing the recognition
	 *             service
	 * @throws JSONException
	 *             when the result from the recognition service was not in JSON
	 *             format
	 */
	private static Map<String, Object> recognize(byte[] imgData, URL url) throws IOException, JSONException {
		String md5hash = getMD5FromKeyAndImage(apiKey, imgData);
		if (md5hash == null) {
			return null;
		}
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "image/jpeg");
		conn.addRequestProperty("x-itraff-hash", md5hash);

		OutputStream os = conn.getOutputStream();
		os.write(imgData);
		os.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		output = getResponseJson(br);

		JSONObject jdata = new JSONObject(output);

		// retrieving data from the response
		Iterator<String> nameItr = jdata.keys();
		HashMap<String, Object> outMap = new HashMap<String, Object>();
		while (nameItr.hasNext()) {
			String key = nameItr.next();
			if ("id".equals(key) && jdata.optJSONArray(key) != null) {
				JSONArray jsonArray = jdata.optJSONArray(key);
				List<String> idsList = new ArrayList<String>();
				for (int i = 0; i < jsonArray.length(); i++) {
					idsList.add((String) jsonArray.get(i));
				}
				outMap.put(key, idsList);
			} else if ("id".equals(key)) {
				List<String> idList = new ArrayList<String>();
				idList.add(jdata.getString(key));
				outMap.put(key, idList);
			} else {
				outMap.put(key, jdata.getString(key));
			}
		}
		conn.disconnect();
		return outMap;
	}

	/**
	 * Generates MD5 hash from the client key and image data
	 * 
	 * @param clientKey
	 *            client key
	 * @param image
	 *            array of bytes of image data
	 * @return generated MD5 hash or null if hash could not be generated
	 */
	private static String getMD5FromKeyAndImage(String clientKey, byte[] image) {
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
		} catch (NoSuchAlgorithmException e) {
			hash = null;
		} catch (UnsupportedEncodingException e) {
			hash = null;
		}
		return hash;
	}

	/**
	 * Reads the JSON response from the given input
	 * 
	 * @param in
	 *            input
	 * @return JSON response
	 * @throws IOException
	 *             when response could not be retrieved from the input
	 */
	private static String getResponseJson(BufferedReader in) throws IOException {
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
