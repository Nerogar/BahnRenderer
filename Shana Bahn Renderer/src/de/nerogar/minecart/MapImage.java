package de.nerogar.minecart;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MapImage {
	public BufferedImage image;
	public int[] pixels;
	public int width;
	public int height;
	public String filename;
	public FontSheet fontSheet;
	public Position spawn;
	public Position spawnCoordinates;
	public Position center;

	public MapImage(String filename) {
		fontSheet = new FontSheet("/data/font16.png");
		spawn = new Position(3749, 3553);
		spawnCoordinates = new Position(-207, 10);
		center = new Position(spawn.x + spawnCoordinates.y, spawn.y - spawnCoordinates.x);

		try {
			System.out.print("loading map image");
			image = ImageIO.read(MapImage.class.getResourceAsStream(filename));
			System.out.println(" - done");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("importing map image");
		this.filename = filename;
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		System.out.println(" - done");
	}

	public void saveAsImage(String newFileName) {
		System.out.print("exporting");

		if (ShanaMap.debug) {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		} else {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		}

		image.setRGB(0, 0, width, height, pixels, 0, width);
		System.out.println(" - done");
		try {
			System.out.print("saving");
			FileOutputStream out = new FileOutputStream(newFileName);
			if (ShanaMap.debug) {
				ImageIO.write(image, "BMP", out);
			} else {
				ImageIO.write(image, "PNG", out);
			}

			out.close();
			System.out.println(" - done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPixel(Position pos, int color) {
		if (pos.x >= 0 && pos.x <= width && pos.y >= 0 && pos.y <= height) {
			pixels[pos.y * width + pos.x] = color;
		}
	}

	private void renderCoordinate(Position pos, int color) {
		setPixel(new Position(center.y - pos.y, center.x + pos.x), color);
	}

	private void renderSquare(Position pos, int width, int color) {
		Position posMin = new Position(pos.x, pos.y);
		Position posMax = new Position(pos.x, pos.y);

		posMin.subtract(new Position(width, width));
		posMax.add(new Position(width, width));

		for (int i = posMin.x; i <= posMax.x; i++) {
			for (int j = posMin.y; j <= posMax.y; j++) {
				renderCoordinate(new Position(i, j), color);
			}
		}
	}

	private void renderLine(Position pos1, Position pos2, int lineWidth, int color) {
		int xMin = pos1.x < pos2.x ? pos1.x : pos2.x;
		int xMax = pos1.x > pos2.x ? pos1.x : pos2.x;

		int yMin = pos1.y < pos2.y ? pos1.y : pos2.y;
		int yMax = pos1.y > pos2.y ? pos1.y : pos2.y;

		int xDist = xMax - xMin;
		int yDist = yMax - yMin;

		int xDiff = pos2.x - pos1.x;
		int yDiff = pos2.y - pos1.y;

		if (xDist > yDist) {
			for (int i = 0; i < xDist; i++) {
				int y = (int) (((float) yDiff / xDist) * i + (yDiff < 0 ? yMax : yMin));
				renderSquare(new Position(i + xMin, y), lineWidth, color);
			}
		} else {
			for (int i = 0; i < yDist; i++) {
				int x = (int) (((float) xDiff / yDist) * i + (xDiff < 0 ? xMax : xMin));
				renderSquare(new Position(x, i + yMin), lineWidth, color);
			}

		}
	}

	private void renderMapIcon(MapIcon icon, Position pos) {
		for (int i = 0; i < icon.width; i++) {
			for (int j = 0; j < icon.height; j++) {
				int color = icon.getPixel(new Position(i, j));

				int x = j - icon.center.x + pos.x;
				int y = i - icon.center.y + pos.y;
				//int x = i - icon.center.x + pos.x;
				//int y = j - icon.center.y + pos.y;

				Position renderPosition = new Position(x, y);
				if (color != 0xffff00ff) {
					//System.out.println(renderPosition);
					renderCoordinate(renderPosition, color);
				}
			}
		}
	}

	public void renderFont(String text, Position pos, int color) {
		for (int charIndex = 0; charIndex < text.length(); charIndex++) {
			int charPosition = fontSheet.getCharIndex(text.charAt(charIndex));
			for (int i = 0; i < fontSheet.tileSize; i++) {
				for (int j = 0; j < fontSheet.tileSize; j++) {
					int fontMask = fontSheet.getPixel(new Position((charPosition % fontSheet.xCount) * fontSheet.tileSize - i + fontSheet.tileSize - 1, j + (charPosition / fontSheet.yCount) * fontSheet.tileSize));

					int x = j + pos.x - (charIndex * 2);
					int y = i + pos.y - (charIndex * fontSheet.tileSize);
					//int x = i - icon.center.x + pos.x;
					//int y = j - icon.center.y + pos.y;

					Position renderPosition = new Position(x, y);
					if (fontMask != 0xffffffff) {
						//System.out.println(renderPosition);
						renderCoordinate(renderPosition, color);
					}
				}
			}
		}
	}

	public void renderStation(Station station) {

		if (station.turningpoint) {
			renderMapIcon(Station.stationTurningpointIcon, station.pos);
		} else {
			renderMapIcon(Station.stationIcon, station.pos);
		}

		//0xff666666
		//0xff00ff00

		Position fontOffset = new Position(23, 19);

		renderFont(station.name, new Position(station.pos.x - fontOffset.x, station.pos.y - fontOffset.y), 0xff666666);
		renderFont(station.name, new Position(station.pos.x - fontOffset.x - 1, station.pos.y - fontOffset.y + 1), 0xff000000);

	}

	public void renderIntersection(Intersection intersection) {
		if (ShanaMap.debug) {
			Position fontOffset = new Position(23, 19);

			renderFont(intersection.internalName, new Position(intersection.pos.x - fontOffset.x, intersection.pos.y - fontOffset.y), 0xff666666);
			renderFont(intersection.internalName, new Position(intersection.pos.x - fontOffset.x - 1, intersection.pos.y - fontOffset.y + 1), 0xff006600);
		}
		
		if(intersection.connections==1){
			renderSquare(intersection.pos, 2, 0xffff0000);
		}
	}

	public void renderRail(Connection connection) {
		int lineWidth = 2;
		int color = Connection.DEF_COLOR;

		Position pos1 = connection.intersection1.pos;
		Position pos2 = connection.intersection2.pos;

		renderLine(pos1, pos2, lineWidth, color);
		if (connection.nvv) renderLine(pos1, pos2, 1, Connection.NVV_COLOR);
		if (connection.server) renderLine(pos1, pos2, 1, Connection.SERVER_COLOR);
	}
}
