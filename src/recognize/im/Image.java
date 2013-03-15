package recognize.im;

/**
 * The container for image data.
 *
 */
public class Image {
	protected byte [] data;		///< The file data.
	protected int width;		///< The width of the image.
	protected int height;		///< The height of the image.
	
	public byte[] getData() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image(byte[] data, int width, int height) {
		super();
		this.data = data;
		this.width = width;
		this.height = height;
	}
	

}
