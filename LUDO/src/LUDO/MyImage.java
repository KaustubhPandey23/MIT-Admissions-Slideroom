package LUDO;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class MyImage {

	public static void main(String[] args) throws IOException {
		BufferedImage image = new LUDO().boardImage();
		File f;
		try {
			f = new File("C:\\Users\\ACER\\Desktop\\eclipse-workspace\\LUDO\\src\\res\\images\\boardImage.png");
			ImageIO.write(image, "png", f);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
