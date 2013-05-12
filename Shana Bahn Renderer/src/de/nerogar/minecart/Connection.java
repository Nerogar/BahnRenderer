package de.nerogar.minecart;

public class Connection {
	public static final int DEF_COLOR = 0xff0000ff;
	public static final int NVV_COLOR = 0xff500075;
	public static final int SERVER_COLOR = 0xfff9CA0E;

	public static final String NVV_FLAG = "-nvv";
	public static final String SERVER_FLAG = "-server";

	Intersection intersection1;
	Intersection intersection2;

	public boolean nvv;
	public boolean server;

	public Connection(Intersection intersection1, Intersection intersection2) {
		this.intersection1 = intersection1;
		this.intersection2 = intersection2;
	}
}
