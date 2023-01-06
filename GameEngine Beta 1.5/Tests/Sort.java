package Tests;

public class Sort {
	static Sort obj = new Sort();

	public Sort() {
		int a[] = { 5, 12, 45, 6, 7 }, m, p;
		for (int i = 0; i < a.length - 1; i++) {
			m = a[i];
			p = i;
			for (int j = i + 1; j < a.length; j++)
				if (a[j] < m) {
					m = a[j];
					p = j;
				}
			a[p] = a[i];
			a[i] = m;
		}
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
