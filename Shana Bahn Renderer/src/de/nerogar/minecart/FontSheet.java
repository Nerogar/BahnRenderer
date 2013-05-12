package de.nerogar.minecart;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FontSheet {
	public BufferedImage image;
	public int[] pixels;
	public int tileSize;
	public int width;
	public int height;

	public int xCount;
	public int yCount;

	public final String font = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "abcdefghijklmnopqrstuvwxyz      " + "0123456789                      " + ".:,;/\\()!?_";

	public int getCharIndex(char c) {
		return font.indexOf(c);
	}

	public FontSheet(String filename) {
		try {
			image = ImageIO.read(FontSheet.class.getResourceAsStream(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		width = image.getWidth();
		height = image.getHeight();
		tileSize = width / 32;

		pixels = image.getRGB(0, 0, width, height, null, 0, width);

		xCount = image.getWidth() / tileSize;
		yCount = image.getHeight() / tileSize;
	}

	public int getPixel(Position pos) {
		if (pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height) { return pixels[pos.y * width + pos.x]; }
		return 0xff000000;
	}

}
