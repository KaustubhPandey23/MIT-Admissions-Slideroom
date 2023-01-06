package gameEngine.IIID;

public class Quaternion {
	private double x;
	private double y;
	private double z;
	private double w;

	public Quaternion(Quaternion q) {
		set(q);
	}

	public Quaternion(double[] q) {
		set(q[0], q[1], q[2], q[3]);
	}

	public Quaternion(double x, double y, double z, double w) {
		set(x, y, z, w);
	}

	public Quaternion(Vector3d axis, double angle) {
		double sinHalfAngle = Math.sin(angle / 2);
		double cosHalfAngle = Math.cos(angle / 2);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public Quaternion normalized() {
		double length = length();

		if (length > 0)
			return new Quaternion(x / length, y / length, z / length, w / length);

		return new Quaternion(0, 0, 0, 0);
	}

	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}

	public Quaternion mul(double r) {
		return new Quaternion(x * r, y * r, z * r, w * r);
	}

	public Quaternion mul(Quaternion r) {
		double w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		double x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		double y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		double z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion mul(Vector3d r) {
		double w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		double x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		double y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		double z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	public Quaternion sub(Quaternion r) {
		return new Quaternion(x - r.getX(), y - r.getY(), z - r.getZ(), w - r.getW());
	}

	public Quaternion add(Quaternion r) {
		return new Quaternion(x + r.getX(), y + r.getY(), z + r.getZ(), w + r.getW());
	}

	public Matrix4d toRotationMatrix() {
		Vector3d forward = new Vector3d(2.0 * (x * z - w * y), 2.0 * (y * z + w * x), 1.0 - 2.0 * (x * x + y * y));
		Vector3d up = new Vector3d(2.0 * (x * y + w * z), 1.0 - 2.0 * (x * x + z * z), 2.0 * (y * z - w * x));
		Vector3d right = new Vector3d(1.0 - 2.0 * (y * y + z * z), 2.0 * (x * y - w * z), 2.0 * (x * z + w * y));

		return new Matrix4d().initRotation(forward, up, right);
	}

	public double dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	public Quaternion nLerp(Quaternion dest, double lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	public Quaternion sLerp(Quaternion dest, double lerpFactor, boolean shortest) {
		final double EPSILON = 1e3;

		double cos = this.dot(dest);
		Quaternion correctedDest = dest;

		if (shortest && cos < 0) {
			cos = -cos;
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
			return nLerp(correctedDest, lerpFactor, false);

		double sin = Math.sqrt(1.0 - cos * cos);
		double angle = Math.atan2(sin, cos);
		double invSin = 1.0 / sin;

		double srcFactor = Math.sin((1.0 - lerpFactor) * angle) * invSin;
		double destFactor = Math.sin((lerpFactor) * angle) * invSin;

		return this.mul(srcFactor).add(correctedDest.mul(destFactor));
	}

	// From Ken Shoemake's "Quaternion Calculus and Fast Animation" article
	public Quaternion(Matrix4d rot) {
		double trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0) {
			double s = 0.5 / Math.sqrt(trace + 1.0f);
			w = 0.25 / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else {
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
				double s = 2.0 * Math.sqrt(1.0 + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25 * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2)) {
				double s = 2.0 * Math.sqrt(1.0 + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25 * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else {
				double s = 2.0 * Math.sqrt(1.0 + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25 * s;
			}
		}

		double length = Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	public Vector3d getForward() {
		return new Vector3d(0, 0, 1).rotate(this);
	}

	public Vector3d getBack() {
		return new Vector3d(0, 0, -1).rotate(this);
	}

	public Vector3d getUp() {
		return new Vector3d(0, 1, 0).rotate(this);
	}

	public Vector3d getDown() {
		return new Vector3d(0, -1, 0).rotate(this);
	}

	public Vector3d getRight() {
		return new Vector3d(1, 0, 0).rotate(this);
	}

	public Vector3d getLeft() {
		return new Vector3d(-1, 0, 0).rotate(this);
	}

	public Quaternion get() {
		return new Quaternion(getX(), getY(), getZ(), getW());
	}

	public Quaternion set(Quaternion r) {
		return set(r.getX(), r.getY(), r.getZ(), r.getW());
	}

	public Quaternion set(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
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

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
}
