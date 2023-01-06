package gameEngine.IIID;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public final class Screen extends Controls {

	private ArrayList<DPolygon> DPolygons = new ArrayList<DPolygon>();

	public Screen(Vector3d ViewFrom, Vector3d ViewTo, Dimension ScreenSize, ArrayList<DPolygon> DPolygons) {
		super(ViewFrom, ViewTo, ScreenSize);
		this.DPolygons = DPolygons;
	}

	public Screen(double[] ViewFrom, double[] ViewTo, Dimension ScreenSize, ArrayList<DPolygon> DPolygons) {
		super(ViewFrom, ViewTo, ScreenSize);
		this.DPolygons = DPolygons;
	}

	public void paintComponent(Graphics g) {

		g.clearRect(0, 0, ScreenSize.width, ScreenSize.height);

		cameraMovement();
		for (int i = 0; i < DPolygons.size(); i++)
			DPolygons.get(i).updateFace();

		setOrder(DPolygons);

		for (int i = 0; i < NewOrder.length; i++)
			DPolygons.get(NewOrder[i]).drawFace(g);
		sleepAndRefresh();
	}

	@Override
	public void drawMouseAim(Graphics g) {
		// TODO Auto-generated method stub

	}
	
	//Use this method in MainClass.
	public static void main(String[] args) {
		JFrame F = new JFrame();
		Screen ScreenObject = new Screen(new double[]{0}, new double[]{0}, null, null);
		F.add(ScreenObject);
		F.setUndecorated(true);
		F.setSize(ScreenObject.ScreenSize);
		F.setVisible(true);
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
