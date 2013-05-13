package de.nerogar.minecart;

public class ShanaMap {

	private MapImage mapImage;
	public static final boolean DEBUG = true;
	public static final boolean UPPERCASE = false;

	public ShanaMap() {

	}

	public void renderMap() {
		mapImage = new MapImage("/shanaMap.png");
		System.out.print("importing data");
		MinecartNetworkLoader loader = new MinecartNetworkLoader("res/data/daten.txt");
		System.out.println(" - done");

		System.out.print("processing");
		for (Connection connection : loader.connections) {
			mapImage.renderRail(connection);
		}

		for (Station station : loader.stations) {
			mapImage.renderStation(station);
		}

		for (Intersection intersection : loader.intersections) {
			mapImage.renderIntersection(intersection);
		}
		System.out.println(" - done");

		int currentTimestamp = (int) (System.nanoTime() / 1000000000);
		if (DEBUG) {
			mapImage.saveAsImage("res/rendered/shanaMapRendered" + currentTimestamp + ".bmp");
		} else {
			mapImage.saveAsImage("res/rendered/shanaMapRendered" + currentTimestamp + ".png");
		}

	}

	public static void main(String[] args) {
		ShanaMap shanaMap = new ShanaMap();
		shanaMap.renderMap();
	}
}
