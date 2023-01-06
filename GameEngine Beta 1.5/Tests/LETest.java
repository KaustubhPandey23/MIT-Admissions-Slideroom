package Tests;

import gameEngine.IIID.LinearEquation;

public class LETest {
	public static void main(String[] args) {
		String a = "5/(t+4)=4";
		System.out.println(new LinearEquation(a).a);
	}
}