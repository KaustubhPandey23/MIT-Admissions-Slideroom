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

public class ImageScale extends JPanel {
	static ImageScale obj = new ImageScale();
	Image img;
	BufferedImage img2;

	public ImageScale() {
		img2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img2.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.magenta);
		g.fillOval(0, 0, 300, 300);
		Vector2d a = new Vector2d(-400, -300), b = new Vector2d(-100, -220), c = new Vector2d(-320, 0),
				d = new Vector2d(100, 200), xy[] = { a, b, c, d };
		img = ImageDraw.scaleImage(img2, xy);
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
