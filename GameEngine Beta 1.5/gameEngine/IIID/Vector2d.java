package gameEngine.IIID;

public class Vector2d {
	private double x;
	private double y;

	public Vector2d(Vector2d v) {
		set(v);
	}

	public Vector2d(double[] v) {
		set(v[0], v[1]);
	}

	public Vector2d(double x, double y) {
		set(x, y);
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2d normalized() {
		double length = length();

		if (length > 0)
			return new Vector2d(x / length, y / length);

		return new Vector2d(0, 0);
	}

	public double max() {
		return Math.max(x, y);
	}

	public double dot(Vector2d r) {
		return x * r.getX() + y * r.getY();
	}

	public double cross(Vector2d r) {
		return x * r.getY() - y * r.getX();
	}

	public Vector2d lerp(Vector2d dest, double lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector2d rotate(double angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2d((x * cos - y * sin), (y * cos + x * sin));
	}

	public Vector2d add(Vector2d r) {
		return new Vector2d(x + r.getX(), y + r.getY());
	}

	public Vector2d add(double r) {
		return new Vector2d(x + r, y + r);
	}

	public Vector2d sub(Vector2d r) {
		return new Vector2d(x - r.getX(), y - r.getY());
	}

	public Vector2d sub(double r) {
		return new Vector2d(x - r, y - r);
	}

	public Vector2d mul(Vector2d r) {
		return new Vector2d(x * r.getX(), y * r.getY());
	}

	public Vector2d mul(double r) {
		return new Vector2d(x * r, y * r);
	}

	public Vector2d div(Vector2d r) {
		return new Vector2d(x / r.getX(), y / r.getY());
	}

	public Vector2d div(double r) {
		return new Vector2d(x / r, y / r);
	}

	public Vector2d abs() {
		return new Vector2d(Math.abs(x), Math.abs(y));
	}

	public Vector2d negative() {
		return new Vector2d(-x, -y);
	}

	public String toString() {
		return "(" + x + " " + y + ")";
	}

	public Vector2d get() {
		return new Vector2d(getX(), getY());
	}

	public Vector2d set(Vector2d r) {
		return set(r.getX(), r.getY());
	}

	public Vector2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean equals(Vector2d r) {
		return x == r.getX() && y == r.getY();
	}
}
