package Tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gameEngine.IIID.Calculator;
import gameEngine.IIID.DPolygon;
import gameEngine.IIID.Vector2d;
import gameEngine.IIID.Vector3d;
import gameEngine.IIID.Vertex;

public class PolygonTesting extends JPanel {
	static PolygonTesting obj = new PolygonTesting();
	DPolygon poly;
	Polygon p = new Polygon();
	BufferedImage img;

	public PolygonTesting() {
		img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		g.setColor(Color.green);
		g.fillRect(100, 100, img.getWidth() - 100, img.getHeight() - 100);
		g.setColor(Color.magenta);
		g.fillOval(250, 250, 50, 50);
		g.dispose();

		Vector3d ViewFrom = new Vector3d(10, 10, 10), ViewTo = new Vector3d(1, 1, 1.5);
		Vertex[] v = new Vertex[] {
				new Vertex(new Vector3d(0, 0, 0), new Vector2d(0, 0), new Vector2d(0,0), Color.GRAY),
				new Vertex(new Vector3d(2, 0, 0), Color.BLUE), new Vertex(new Vector3d(2, 2, 0), Color.GRAY),
				new Vertex(new Vector3d(0, 2, 0), Color.GRAY), new Vertex(new Vector3d(0, 0, 3), Color.RED),
				new Vertex(new Vector3d(2, 0, 3), Color.CYAN), new Vertex(new Vector3d(2, 2, 3), Color.GREEN),
				new Vertex(new Vector3d(0, 2, 3), Color.GRAY) };

		Calculator.SetPrederterminedInfo(ViewFrom, ViewTo);

		Vertex[] v1 = new Vertex[] { v[0], v[1], v[2], v[3] };
		Vertex[] v2 = new Vertex[] { v[4], v[5], v[6], v[7] };
		Vertex[] v3 = new Vertex[] { v[0], v[1], v[5], v[4] };
		Vertex[] v4 = new Vertex[] { v[3], v[2], v[6], v[7] };
		Vertex[] v5 = new Vertex[] { v[0], v[3], v[7], v[4] };
		Vertex[] v6 = new Vertex[] { v[1], v[2], v[6], v[5] };
		poly = new DPolygon(v1// new Vertex[] { v[0], v[2], v[4] }
				, img, true);

	}

	public void paintComponent(Graphics g) {
		p.addPoint((int) poly.getFittedTexCoord()[0].getDrawCoord().getX(),
				(int) poly.getFittedTexCoord()[0].getDrawCoord().getX());
		p.addPoint((int) poly.getFittedTexCoord()[1].getDrawCoord().getX(),
				(int) poly.getFittedTexCoord()[1].getDrawCoord().getX());
		p.addPoint((int) poly.getFittedTexCoord()[2].getDrawCoord().getX(),
				(int) poly.getFittedTexCoord()[2].getDrawCoord().getX());
		p.addPoint((int) poly.getFittedTexCoord()[3].getDrawCoord().getX(),
				(int) poly.getFittedTexCoord()[3].getDrawCoord().getX());
		g.drawPolygon(p);
		g.drawImage(poly.getFittedImage(), 90, 90, null);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.add(obj);
		f.setUndecorated(true);
		f.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
