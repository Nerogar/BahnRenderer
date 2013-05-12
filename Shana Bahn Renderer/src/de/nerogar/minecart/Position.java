package de.nerogar.minecart;

public class Position {

	public int x;
	public int y;

	public Position(int x, int z) {
		this.x = x;
		this.y = z;
	}

	public void add(Position newPosition) {
		x += newPosition.x;
		y += newPosition.y;
	}

	public void subtract(Position newPosition) {
		x -= newPosition.x;
		y -= newPosition.y;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Position) {
			Position position = (Position) object;
			if (position.x == this.x && position.y == this.y) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(X: " + x + "|Z: " + y + ")";
	}

	@Override
	public int hashCode() {
		return (this.x & 0xffff) | ((this.y & 0xffff) << 16);
	}
}
