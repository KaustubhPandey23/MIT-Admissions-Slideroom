package Tests;

import gameEngine.IIID.Vector3d;

public class Test {
	public static void main(String[] args) {
		Vector3d x = new Vector3d(3,8,45);
		System.out.println((new Vector3d(0, 0, 5).normalized()).dot(new Vector3d(0, 5, 0).normalized()));
		System.out.println((x.normalized()).mul(x.length()));
		System.out.println(1 / Math.sqrt(2));
	}
}
