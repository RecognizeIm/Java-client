package recognize.im;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * The container for image data.
 * 
 */
public class Image {
	protected byte[] data; // /< The file data.
	protected int width; // /< The width of the image.
	protected int height; // /< The height of the image.

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

	public Image draw(APIResponse response) {
		if (response.getStatus() != 0)
			return null;
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		try {
			BufferedImage image = ImageIO.read(bis);
			Graphics2D g2d = image.createGraphics();
			FontMetrics fm = g2d.getFontMetrics();
			BasicStroke bs = new BasicStroke(10);
			Color bgColor = new Color(255, 255, 255, 128);
			g2d.setStroke(bs);
			for (APIObject obj : response.getObjects()) {
				ArrayList<Point> location = obj.getLocation();
				g2d.setColor(Color.BLACK);
				for (int i = 0; i < 4; ++i) {
					g2d.drawLine(location.get(i).getX(),
							location.get(i).getY(), location.get((i + 1) % 4)
									.getX(), location.get((i + 1) % 4).getY());
				}
				float x = (location.get(0).getX() + location.get(2).getX()) / 2;
				float y = (location.get(0).getY() + location.get(2).getY()) / 2;
				Rectangle2D rect = fm.getStringBounds(obj.getName(), g2d);
				g2d.setColor(bgColor);
				g2d.fillRect((int) x, (int) y - fm.getAscent(),
						(int) rect.getWidth(), (int) rect.getHeight());
				g2d.setColor(Color.BLACK);
				g2d.drawString(obj.getName(), x, y);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			return new Image(baos.toByteArray(), image.getWidth(),
					image.getHeight());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void save(String path) throws IOException {
		BufferedOutputStream bos = null;
		FileOutputStream fos = new FileOutputStream(new File(path));
		bos = new BufferedOutputStream(fos);
		bos.write(data);
	}
}
