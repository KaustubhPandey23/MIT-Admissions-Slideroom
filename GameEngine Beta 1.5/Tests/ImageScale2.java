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

public class ImageScale2 extends JPanel {
	static ImageScale2 obj = new ImageScale2();
	BufferedImage img;
	BufferedImage img2;

	public ImageScale2() {
		img2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img2.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.green);
		g.fillRect(100, 100, img2.getWidth() - 100, img2.getHeight() - 100);
		g.setColor(Color.magenta);
		g.fillOval(250, 250, 50, 50);
		g.dispose();
		Vector2d a = new Vector2d(0, 0), b = new Vector2d(300, 120), c = new Vector2d(80, 300),
				d = new Vector2d(500, 500), xy[] = { d, c, b, a };
		img = (BufferedImage) ImageDraw.scaleImage(img2, xy);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img2, 0, 0, null);
		g.drawImage(img, img2.getWidth() + 30, 0, null);
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
