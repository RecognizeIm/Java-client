package recognize.im;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 511802605991633060L;
	protected String id;
	protected String name;
	protected ArrayList<Point> location;

	public APIObject(JSONObject object) throws JSONException {
		location = new ArrayList<Point>();
		this.id = object.getString("id");
		this.name = object.getString("name");
		JSONArray loc = object.getJSONArray("location");
		for (int i = 0; i < loc.length(); ++i) {
			location.add(new Point(loc.getJSONObject(i)));
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Point> getLocation() {
		return location;
	}

	public void setLocation(ArrayList<Point> location) {
		this.location = location;
	}

}
