package de.nerogar.minecart;

public class Station extends Intersection {
	public static final String TURNINGPOINT_FLAG = "-w";
	public static final CharSequence VEHICLES_FLAG = "-v";

	public static final MapIcon stationIcon;
	public static final MapIcon stationTurningpointIcon;

	public String name;
	public boolean turningpoint = false;
	public boolean vehicles_flag = false;

	public Station(String internalName, Position pos, String name) {
		super(internalName, pos);
		this.name = name;
	}

	static {
		stationIcon = new MapIcon("/data/station.png");
		stationIcon.center = new Position(7, 7);
		stationTurningpointIcon = new MapIcon("/data/station_turningpoint.png");
		stationTurningpointIcon.center = new Position(7, 7);
	}
}
