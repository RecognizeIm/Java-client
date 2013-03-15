package recognize.im;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
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

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.itraff.clapi.ITraffSoapProxy;

public class Recognize {

	// These are the credentials (you can find it at:
	// https://www.recognize.im/user/profile)
	static String clientId = "";	// Client ID
	static String apiKey = "";		// API Key
	static String clapiKey = "";	// CLAPI Key
	
	//These are the limits for query images:
	//for SingleIR
	final static float SINGLEIR_MAX_FILE_SIZE = 500.f;		//KBytes
	final static int SINGLEIR_MIN_DIMENSION = 100;			//pix
	final static float SINGLEIR_MIN_IMAGE_SURFACE = 0.05f;	//Mpix
	final static float SINGLEIR_MAX_IMAGE_SURFACE = 0.31f;	//Mpix
	
	//for MultipleIR
	final static float MULTIPLEIR_MAX_FILE_SIZE = 3500.f;	//KBytes
	final static int MULTIPLEIR_MIN_DIMENSION = 100;		//pix
	final static float MULTIPLEIR_MIN_IMAGE_SURFACE = 0.1f;	//Mpix
	final static float MULTIPLEIR_MAX_IMAGE_SURFACE = 5.1f;	//Mpix

	public static void main(String[] args) {

		Image image = readImageData("joker.jpg");
		if (image == null || image.getData() == null) {
			System.err.println("Given image could not be read");
			return;
		}
		try {

			// initialize proxy class (this perform authorisation)
			ITraffSoapProxy iTraffSaop = new ITraffSoapProxy(clientId, clapiKey, apiKey);

			// insert new image with 123# as ID and "Image name" as the name
			// (examplary response: {status=0})
			Map result = iTraffSaop.imageInsert("123#", "Image name", Base64.encodeBase64String(image.getData()));

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
			result = recognizeReturnAll(image);
			List recognizedIdsList = (List) result.get("id"); // list of
																// recognized
																// images ids

			// recognize image (this returns only the best result from the
			// recognition)
			result = recognizeReturnBest(image);
			List recognizedIdList = (List) result.get("id"); // list containing
																// the best
																// recognizion
																// result (size
																// == 1)
			
			// recognize image (in multiple mode). Remember to change the mode at recognize.im first.
			result = recognizeMulti(image);
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
	private static Image readImageData(String imageName) {
		byte[] imgData = null;
		BufferedImage img = null;
		try {
			//read image data
			InputStream imgIs = new FileInputStream(imageName);
			imgData = new byte[imgIs.available()];
			imgIs.read(imgData);
			imgIs.close();
			
			//read image dimensions
			img = ImageIO.read(new File(imageName));
			
		} catch (IOException e) {
			// TODO handle IOException when there is a problem with reading
			return null;
		}
		return new Image(imgData, img.getWidth(), img.getHeight());
	}

	/**
	 * Calls the recognition method which returns only the best recognition
	 * result
	 * 
	 * @param image
	 *            the image data
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
	public static Map recognizeReturnBest(Image image) throws IOException, JSONException {
		String recognizeMode = "single";
		URL url = new URL("http://recognize.im/recognize/" + clientId);
		return recognize(image, url, recognizeMode);
	}

	/**
	 * Calls the recognition method which returns all of the recognition
	 * results
	 * 
	 * @param image
	 *            the image data
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
	public static Map recognizeReturnAll(Image image) throws IOException, JSONException {
		String recognizeMode = "single";
		URL url = new URL("http://recognize.im/recognize/allResults/" + clientId);
		return recognize(image, url, recognizeMode);
	}
	
	/**
	 * Calls the recognition method in multi mode which returns all of the recognition
	 * objects on the taken photo
	 * 
	 * @param image
	 *            the image data
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
	public static Map recognizeMulti(Image image) throws IOException, JSONException {
		String recognizeMode = "multi";
		URL url = new URL("http://recognize.im/recognize/" + recognizeMode + "/" + clientId);
		return recognize(image, url, recognizeMode);
	}

	/**
	 * Calls the recognition service at the given url
	 * 
	 * @param image
	 *            the image data
	 * @param url
	 *            URL of the recognition service
	 * @param recognizeMode
	 * 			the current recognize mode
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
	private static Map<String, Object> recognize(Image image, URL url, String recognizeMode) throws IOException, JSONException {
		if (!checkImageLimits(image, recognizeMode)) {
			return new HashMap<String, Object>();
		}
		
		String md5hash = getMD5FromKeyAndImage(apiKey, image.getData());
		if (md5hash == null) {
			return new HashMap<String, Object>();
		}
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "image/jpeg");
		conn.addRequestProperty("x-itraff-hash", md5hash);

		OutputStream os = conn.getOutputStream();
		os.write(image.getData());
		os.flush();

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		output = getResponseJson(br);
		
		System.out.println(output);

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
	 * Checks the image limits for the current recognize mode
	 * 
	 * @param image
	 *            the image data
	 * @param recognizeMode
	 * 			the current recognize mode
	 * @return true if the image fulfills the limits, otherwise false
	 */
	private static boolean checkImageLimits(Image image, String recognizeMode) {
		final float imageSurface = (float)(image.getHeight() * image.getHeight()) / 1000000.f;
		final float fileSize = (float)image.getData().length / 1000.f;
			
		if (recognizeMode.equalsIgnoreCase("single")) {			
			if (fileSize > SINGLEIR_MAX_FILE_SIZE  ||
					image.getHeight() < SINGLEIR_MIN_DIMENSION ||
					image.getWidth() < SINGLEIR_MIN_DIMENSION ||
					imageSurface < SINGLEIR_MIN_IMAGE_SURFACE ||
					imageSurface > SINGLEIR_MAX_IMAGE_SURFACE) {
				return false;
			}
			
		} else if (recognizeMode.equalsIgnoreCase("multi")) {
			if (fileSize > MULTIPLEIR_MAX_FILE_SIZE  ||
					image.getHeight() < MULTIPLEIR_MIN_DIMENSION ||
					image.getWidth() < MULTIPLEIR_MIN_DIMENSION ||
					imageSurface < MULTIPLEIR_MIN_IMAGE_SURFACE ||
					imageSurface > MULTIPLEIR_MAX_IMAGE_SURFACE) {
				return false;
			}
			
		} else {
			System.out.println("Unknown recognize mode selected.");
		}
		
		return true;
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
