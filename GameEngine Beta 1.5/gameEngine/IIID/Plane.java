package gameEngine.IIID;

public class Plane {
	private Vector3d V1, V2, NV;
	private Vertex V;

	public Plane(DPolygon DP) {
		V = DP.getVertex()[0];

		V1 = (DP.getVertex()[1].getPos()).sub(DP.getVertex()[0].getPos()).normalized();

		V2 = (DP.getVertex()[2].getPos()).sub(DP.getVertex()[0].getPos()).normalized();

		NV = V1.cross(V2);

		V.setTangent(V1);

		V.setNormal(NV);
	}

	public Plane(Vector3d VE1, Vector3d VE2, double[] Z) {
		V = new Vertex(new Vector3d(Z[0], Z[0], Z[0]));

		V1 = VE1;

		V2 = VE2;

		NV = V1.cross(V2);

		V.setTangent(V1);

		V.setNormal(NV);
	}

	public Vector3d getV1() {
		return V1;
	}

	public void setV1(Vector3d v1) {
		V1 = v1;
	}

	public Vector3d getV2() {
		return V2;
	}

	public void setV2(Vector3d v2) {
		V2 = v2;
	}

	public Vector3d getNV() {
		return NV;
	}

	public void setNV(Vector3d nV) {
		NV = nV;
	}

	public Vertex getVertex() {
		return V;
	}

	public void setVertex(Vertex v) {
		V = v;
	}
}
