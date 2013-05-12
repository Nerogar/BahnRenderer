package de.nerogar.minecart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MinecartNetworkLoader {

	public ArrayList<Station> stations = new ArrayList<Station>();
	public ArrayList<Intersection> intersections = new ArrayList<Intersection>();
	public ArrayList<Connection> connections = new ArrayList<Connection>();

	public MinecartNetworkLoader(String filename) {
		BufferedReader reader;

		try {

			reader = new BufferedReader(new FileReader(filename));

			String line;
			while ((line = reader.readLine()) != null) {

				String[] lineSplit = line.split(" ");

				if (line.startsWith("H")) { //Station
					int x = Integer.parseInt(lineSplit[2]);
					int y = Integer.parseInt(lineSplit[3]);
					String name = lineSplit[1];
					String internalName = lineSplit[0];

					Station station = new Station(internalName, new Position(x, y), name);
					stations.add(station);

					station.turningpoint = line.contains(Station.TURNINGPOINT_FLAG);
					station.vehicles_flag = line.contains(Station.VEHICLES_FLAG);

				} else if (line.startsWith("K")) { //Intersection
					int x = Integer.parseInt(lineSplit[1]);
					int y = Integer.parseInt(lineSplit[2]);
					String internalName = lineSplit[0];

					intersections.add(new Intersection(internalName, new Position(x, y)));
				} else if (line.startsWith("V")) { //Connection

					Intersection intersection1 = null;
					Intersection intersection2 = null;
					String internalName1 = lineSplit[1] + ":";
					String internalName2 = lineSplit[3] + ":";

					for (Intersection intersection : intersections) {
						if (intersection.internalName.equals(internalName1)) {
							intersection1 = intersection;
						}
						if (intersection.internalName.equals(internalName2)) {
							intersection2 = intersection;
						}
					}

					for (Intersection intersection : stations) {
						if (intersection.internalName.equals(internalName1)) {
							intersection1 = intersection;
						}
						if (intersection.internalName.equals(internalName2)) {
							intersection2 = intersection;
						}
					}

					if (intersection1 != null && intersection2 != null) {
						intersection1.connections++;
						intersection2.connections++;
						Connection connection = new Connection(intersection1, intersection2);
						connection.nvv = line.contains(Connection.NVV_FLAG);
						connection.server = line.contains(Connection.SERVER_FLAG);
						connections.add(connection);
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
