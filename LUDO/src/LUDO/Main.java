package LUDO;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main extends JFrame {
	static JFrame frame = new Main();

	Main() {
		LUDO ludo = new LUDO();
		add(ludo);
		setUndecorated(true);
		setSize(ludo.boardSizeX, ludo.boardSizeY);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
	}
}
