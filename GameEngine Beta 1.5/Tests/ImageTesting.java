package Tests;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import gameEngine.IIID.ImageDraw;
import gameEngine.IIID.Vector2d;

public class ImageTesting extends JPanel {
	static ImageTesting obj = new ImageTesting();
	BufferedImage img;
	BufferedImage img2;
	BufferedImage img3;
	BufferedImage img4;

	Vector2d draw = new Vector2d(0, 0);

	public ImageTesting() {
		img2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img2.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.green);
		g.fillRect(100, 100, img2.getWidth() - 100, img2.getHeight() - 100);
		g.setColor(Color.magenta);
		g.fillOval(250, 250, 50, 50);
		g.dispose();
		Vector2d a = new Vector2d(50, 50), b = new Vector2d(0, 300), c = new Vector2d(300, 275),
				xy[] = new Vector2d[] { a, b, c };
		Vector2d a2 = new Vector2d(50, 50), b2 = new Vector2d(150, 300), c2 = new Vector2d(300, 275),
				xy2[] = new Vector2d[] { a2, b2, c2 };
		img = (BufferedImage) ImageDraw.cropImageAccordingtoTranspiracy(img2, xy);
		img3 = (BufferedImage) ImageDraw.cropImageAccordingtoTranspiracy(img2, xy2);
		img4 = (BufferedImage) ImageDraw.scaleTriangle(img2, xy2, xy, draw);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img2, 0, 0, null);
		g.drawImage(img, img2.getWidth() + 30, 0, null);
		g.drawImage(img3, img.getWidth() + img2.getWidth() + 60, 0, null);
		g.drawImage(img4, (int) (img3.getWidth() + img.getWidth() + img2.getWidth() + 90 + draw.getX()),
				(int) draw.getY(), null);
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
