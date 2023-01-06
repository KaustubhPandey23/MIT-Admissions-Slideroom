package gameEngine.IIID;

public class Calculator {
	private static double l, t = 0;
	private static Vector3d vt, vf, p, vv, dv, vtp, p1, p2;

	private static double[] CalcFocusPos = new double[2];

	public static double getT() {
		return t;
	}

	public static double[] CalculatePosition(Vertex v) {
		return CalculatePosition(v.getPos());
	}

	public static double[] CalculatePosition(Vector3d P) {
		return CalculatePosition(new double[] { P.getX(), P.getY(), P.getZ() });
	}

	public static double[] CalculatePosition(double[] P) {
		return CalculatePosition(P[0], P[1], P[2]);
	}

	public static double[] CalculatePosition(double x, double y, double z) {
		double[] p = CalculatePositionP(x, y, z);

		return new double[] { p[0] - CalcFocusPos[0], p[1] - CalcFocusPos[1] };
	}

	public static void SetPrederterminedInfo(Vertex ViewFrom, Vertex ViewTo) {
		SetPrederterminedInfo(ViewFrom.getPos(), ViewTo.getPos());
	}

	public static void SetPrederterminedInfo(Vector3d ViewFrom, Vector3d ViewTo) {
		SetPrederterminedInfo(new double[] { ViewFrom.getX(), ViewFrom.getY(), ViewFrom.getZ() },
				new double[] { ViewTo.getX(), ViewTo.getY(), ViewTo.getZ() });
	}

	public static void SetPrederterminedInfo(double[] ViewFrom, double[] ViewTo) {
		vf = new Vector3d(ViewFrom[0], ViewFrom[1], ViewFrom[2]);
		vt = new Vector3d(ViewTo[0], ViewTo[1], ViewTo[2]);
		vv = vt.sub(vf);
		l = vv.length();
		vv = vv.normalized();// direction of view field
		dv = GetRotationVector();// base vector to obtain plains
		p1 = vv.cross(dv);// direction of line mutually perpendicular to both vectors
		p2 = vv.cross(p1);// direction of line mutually perpendicular to both vectors

		CalcFocusPos = Calculator.CalculatePositionP(ViewTo);
	}

	private static double[] CalculatePositionP(double[] P) {
		return CalculatePositionP(P[0], P[1], P[2]);

	}

	private static double[] CalculatePositionP(double x, double y, double z) {
		p = new Vector3d(x, y, z);
		vtp = p.sub(vf).normalized();
		t = l / vv.dot(vtp);
		double DrawX = p2.dot(vf.add(vtp.mul(t)));
		double DrawY = p1.dot(vf.add(vtp.mul(t)));
		return new double[] { DrawX, DrawY };

	}

	private static Vector3d GetRotationVector() {
		double yRot = Math.abs(vf.getX() - vt.getX());
		double xRot = Math.abs(vf.getY() - vt.getY());

		if (vf.getY() > vt.getY())
			xRot = -xRot;
		if (vf.getX() < vt.getX())
			yRot = -yRot;

		return new Vector3d(xRot, yRot, 0).normalized();
	}

	public static double getDist(Vertex v) {
		return getDist(v.getPos());
	}

	public static double getDist(Vector3d v) {
		return getViewFrom().sub(v).length();
	}

	public static Vector3d getViewTo() {
		return vt;
	}

	public static Vector3d getViewFrom() {
		return vf;
	}
}