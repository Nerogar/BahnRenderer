package de.nerogar.minecart;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapIcon {
	public BufferedImage image;
	public int[] pixels;
	public int width;
	public int height;
	public String filename;
	public Position center;

	public MapIcon(String filename) {
		try {
			image = ImageIO.read(MapImage.class.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.filename = filename;
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
	}

	public int getPixel(Position pos) {
		if (pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height) { return pixels[pos.y * width + pos.x]; }
		return 0xff000000;
	}
}
