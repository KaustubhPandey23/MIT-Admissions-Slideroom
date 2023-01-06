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

public class ImageRotTesting extends JPanel {
	static ImageRotTesting obj = new ImageRotTesting();
	Image img;
	BufferedImage img2;

	public ImageRotTesting() {
		img2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img2.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.magenta);
		g.fillOval(250, 250, 50, 50);
		g.dispose();
		img = ImageDraw.flipHor(img2);
		img = ImageDraw.flipVert(img);
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
