package gameEngine.IIID;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainClass extends JFrame {

	static JFrame F = new MainClass();

	public static Screen ScreenObject;

	public MainClass() {
		Vector3d ViewFrom = new Vector3d(10, 10, 10), ViewTo = new Vector3d(1, 1, 1);
		Vertex[] v = new Vertex[] { new Vertex(new Vector3d(0, 0, 0), Color.GRAY),
				new Vertex(new Vector3d(2, 0, 0), Color.BLUE), new Vertex(new Vector3d(2, 2, 0), Color.GRAY),
				new Vertex(new Vector3d(0, 2, 0), Color.GRAY), new Vertex(new Vector3d(0, 0, 3), Color.RED),
				new Vertex(new Vector3d(2, 0, 3), Color.CYAN), new Vertex(new Vector3d(2, 2, 3), Color.GREEN),
				new Vertex(new Vector3d(0, 2, 3), Color.GRAY) };

		Calculator.SetPrederterminedInfo(ViewFrom, ViewTo);

		BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setColor(Color.green);
		g.fillRect(100, 100, img.getWidth() - 100, img.getHeight() - 100);
		g.setColor(Color.magenta);
		g.fillOval(250, 200, 50, 50);
		g.dispose();

		Vertex[] v1 = new Vertex[] { v[0], v[1], v[2], v[3] };
		Vertex[] v2 = new Vertex[] { v[4], v[5], v[6], v[7] };
		Vertex[] v3 = new Vertex[] { v[0], v[1], v[5], v[4] };
		Vertex[] v4 = new Vertex[] { v[3], v[2], v[6], v[7] };
		Vertex[] v5 = new Vertex[] { v[0], v[3], v[7], v[4] };
		Vertex[] v6 = new Vertex[] { v[1], v[2], v[6], v[5] };
		ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();
		DPolygons.add(new DPolygon(v1));

		DPolygons.add(new DPolygon(v2));

		DPolygons.add(new DPolygon(v3));

		DPolygons.add(new DPolygon(v4));

		DPolygons.add(new DPolygon(v5));

		DPolygons.add(new DPolygon(v6));

		/*
		for (int i = -4; i < 5; i++)
			for (int j = -4; j < 5; j++)
				DPolygons.add(new DPolygon(new double[] { i, i, i + 1, i + 1 }, new double[] { j, j + 1, j + 1, j },
						new double[] { 0, 0, 0, 0 }, Color.green));// */
		ScreenObject = new Screen(ViewFrom, ViewTo, null, DPolygons);

		add(ScreenObject);
		setUndecorated(true);
		setSize(ScreenObject.ScreenSize);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
	}

}
