package Tests;

public class MainClass {
	static MainClass a = new MainClass();

	public MainClass() {
		ObjectTests o1 = new ObjectTests();
		ObjectTests o2 = new ObjectTests();
		ObjectTests o3 = o1;
		ObjectTests o4 = o2;

		o1.i++;
		o3.i++;
		o4.i++;

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

		System.out.println();

		System.out.println("4 i: " + o4.i);
		System.out.println("4 j: " + o4.j);
		System.out.println("4 k: " + o4.k);
	}

	public static void main(String[] args) {
	}

}
