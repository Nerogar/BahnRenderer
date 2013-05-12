package de.nerogar.minecart;

public class Intersection {

	public String internalName;
	public Position pos;
	public int connections = 0;

	public Intersection(String internalName, Position pos) {
		this.internalName = internalName;
		this.pos = pos;
	}
}
