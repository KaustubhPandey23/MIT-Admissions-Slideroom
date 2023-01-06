package gameEngine.IIID;

public class Vector3d {
	private double x;
	private double y;
	private double z;

	public Vector3d(Vector3d v) {
		set(v);
	}

	public Vector3d(double[] v) {
		set(v[0], v[1], v[2]);
	}

	public Vector3d(double x, double y, double z) {
		set(x, y, z);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3d normalized() {
		double length = length();

		if (length > 0)
			return new Vector3d(x / length, y / length, z / length);

		return new Vector3d(0, 0, 0);
	}

	public double max() {
		return Math.max(x, Math.max(y, z));
	}

	public double dot(Vector3d r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	public Vector3d cross(Vector3d r) {
		double x_ = y * r.getZ() - z * r.getY();
		double y_ = z * r.getX() - x * r.getZ();
		double z_ = x * r.getY() - y * r.getX();

		return new Vector3d(x_, y_, z_);
	}

	public Vector3d lerp(Vector3d dest, double lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector3d rotate(Vector3d axis, double angle) {
		double rad = Math.toRadians(angle);
		double sinAngle = Math.sin(rad);
		double cosAngle = Math.cos(rad);

		Vector3d n = axis.normalized();

		return n.mul(this.dot(n) * (1 - cosAngle)).add(this.mul(cosAngle).add(n.cross(this).mul(sinAngle)));
	}

	public Vector3d rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3d(w.getX(), w.getY(), w.getZ());
	}

	public Vector3d add(Vector3d r) {
		return new Vector3d(x + r.getX(), y + r.getY(), z + r.getZ());
	}

	public Vector3d add(double r) {
		return new Vector3d(x + r, y + r, z + r);
	}

	public Vector3d sub(Vector3d r) {
		return new Vector3d(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	public Vector3d sub(double r) {
		return new Vector3d(x - r, y - r, z - r);
	}

	public Vector3d mul(Vector3d r) {
		return new Vector3d(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	public Vector3d mul(double r) {
		return new Vector3d(x * r, y * r, z * r);
	}

	public Vector3d div(Vector3d r) {
		return new Vector3d(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	public Vector3d div(double r) {
		return new Vector3d(x / r, y / r, z / r);
	}

	public Vector3d abs() {
		return new Vector3d(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	public Vector3d negative() {
		return new Vector3d(-x, -y, -z);
	}

	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	public Vector3d get() {
		return new Vector3d(getX(), getY(), getZ());
	}

	public Vector3d set(Vector3d r) {
		return set(r.getX(), r.getY(), r.getZ());
	}

	public Vector3d set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public boolean equals(Vector3d r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}
}
