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

public class ImageCrop extends JPanel {
	static ImageCrop obj = new ImageCrop();
	BufferedImage img;
	BufferedImage img2;
	BufferedImage img3;
	BufferedImage img4;

	public ImageCrop() {
		img2 = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img2.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.magenta);
		g.fillOval(0, 0, 300, 300);
		Vector2d a = new Vector2d(225, 0), b = new Vector2d(300, 0), c = new Vector2d(300, 75),
				d = new Vector2d(150, 150), xy[] = { a, b, c, d };
		img = (BufferedImage) ImageDraw.cropImageAccordingtoTranspiracy(img2, xy);
		img3 = (BufferedImage) ImageDraw.removeTranspiracy(img);
		img4 = (BufferedImage) ImageDraw.scaleImage(img3, 0, 0, img3.getWidth(), 0, 0 + 75, img3.getHeight(),
				img3.getWidth() + 75, img3.getHeight());
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img2, 0, 0, null);
		g.drawImage(img, img2.getWidth() + 30, 0, null);
		g.drawImage(img3, img2.getWidth() + img.getWidth() + 60, 0, null);
		g.drawImage(img4, img2.getWidth() + img.getWidth() + img3.getWidth() + 90, 0, null);
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
