package Tests;

public class swap {

	static swap obj = new swap();

	public swap() {
		ObjectTests o1 = new ObjectTests(1, 2, 3);
		ObjectTests o2 = new ObjectTests(4, 5, 6);

		ObjectTests o3;

		o3 = o1;
		//o1 = o2;
		//o2 = o3;
		
		//o2.i=9;
		
		o2=o1;

		System.out.println("1 i: " + o1.i);
		System.out.println("1 j: " + o1.j);
		System.out.println("1 k: " + o1.k);

		System.out.println();

		System.out.println("2 i: " + o2.i);
		System.out.println("2 j: " + o2.j);
		System.out.println("2 k: " + o2.k);

		System.out.println();

		System.out.println("3 i: " + o3.i);
		System.out.println("3 j: " + o3.j);
		System.out.println("3 k: " + o3.k);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
