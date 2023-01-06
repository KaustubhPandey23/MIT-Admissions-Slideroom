package Tests;

import gameEngine.IIID.Vector3d;

public class ang {
	public static void main(String[] args) {
		int x1 = 5, y1 = 5, z1 = 0, x2 = 8, y2 = 0, z2 = 0;
		Vector3d v1 = new Vector3d(x1, y1, z1).normalized();
		Vector3d v2 = new Vector3d(x2, y2, z2).normalized();
		System.out.println(v1.cross(v2).length());
		System.out.println(1.0 / Math.sqrt(2));
		
		System.out.println(Math.toDegrees(Math.acos(   (Math.cos(Math.toRadians(23)))    )));
	}
}