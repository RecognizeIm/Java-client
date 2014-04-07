package recognize.im;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class Point implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9128397965804939727L;
	protected int x;
	protected int y;

	public Point(JSONObject jsonObject) throws JSONException {
		this.x = jsonObject.getInt("x");
		this.y = jsonObject.getInt("y");
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
