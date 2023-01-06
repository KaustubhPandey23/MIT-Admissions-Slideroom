package gameEngine.IIID;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public abstract class Controls extends JPanel
		implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	public Dimension ScreenSize;
	protected Vector3d ViewFrom, ViewTo, ViewVector;
	private double drawFPS, MaxFPS = 30, SleepTime = 1000.0 / MaxFPS, LastRefresh = 0, LastFPSCheck = 0, Checks = 0;
	private static double MinZoom = 500, MaxZoom = 2500, MovementSpeed = 5, ZoomSpeed = 25;
	private double HorRotSpeed = 20, VertRotSpeed = 20;
	private Robot r;
	protected int[] NewOrder;
	private boolean[] Keys = new boolean[256];

	public Controls(Vector3d ViewFrom, Vector3d ViewTo, Dimension ScreenSize) {
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		this.ViewFrom = ViewFrom;
		this.ViewTo = ViewTo;
		this.ViewVector = this.ViewTo.sub(this.ViewFrom);
		this.invisibleMouse();
		if (ScreenSize != null) {
			this.ScreenSize = ScreenSize;
			if (ScreenSize.getWidth() == 0 || ScreenSize.getHeight() == 0)
				this.ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		} else
			this.ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		DPolygon.ScreenSize = this.ScreenSize;
		DPolygon.zoom = ViewVector.length() * 3.0;
	}

	public Controls(double[] ViewFrom, double[] ViewTo, Dimension ScreenSize) {
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		this.ViewFrom = new Vector3d(ViewFrom[0], ViewFrom[1], ViewFrom[2]);
		this.ViewTo = new Vector3d(ViewTo[0], ViewTo[1], ViewTo[2]);
		this.ViewVector = this.ViewTo.sub(this.ViewFrom);
		this.invisibleMouse();
		if (ScreenSize != null) {
			this.ScreenSize = ScreenSize;
			if (ScreenSize.getWidth() == 0 || ScreenSize.getHeight() == 0)
				this.ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		} else
			this.ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		DPolygon.ScreenSize = this.ScreenSize;
		DPolygon.zoom = ViewVector.length() * 3.0;
	}

	protected void setOrder(ArrayList<DPolygon> DPolygons) {
		double[] k = new double[DPolygons.size()];
		NewOrder = new int[DPolygons.size()];

		for (int i = 0; i < DPolygons.size(); i++) {
			k[i] = DPolygons.get(i).getDist();
			NewOrder[i] = i;
		}

		double temp;
		int tempr;
		for (int a = 0; a < k.length - 1; a++)
			for (int b = 0; b < k.length - 1; b++)
				if (k[b] < k[b + 1]) {
					temp = k[b];
					k[b] = k[b + 1];
					k[b + 1] = temp;

					tempr = NewOrder[b];
					NewOrder[b] = NewOrder[b + 1];
					NewOrder[b + 1] = tempr;
				}
	}

	protected void sleepAndRefresh() {
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh);

		Checks++;
		if (Checks >= 15) {
			drawFPS = Checks / ((System.currentTimeMillis() - LastFPSCheck) / 1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
		}

		if (timeSLU < 1000.0 / MaxFPS) {
			try {
				Thread.sleep((long) (SleepTime - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		LastRefresh = System.currentTimeMillis();

		repaint();
	}

	protected void cameraMovement() {
		Vector3d VerticalVector = new Vector3d(0, 0, 1).normalized(), SideViewVector = ViewVector.cross(VerticalVector);
		double xMove = 0, yMove = 0, zMove = 0;

		if (Keys[0]) {
			xMove += ViewVector.getX();
			yMove += ViewVector.getY();
			zMove += ViewVector.getZ();
		}

		if (Keys[2]) {
			xMove -= ViewVector.getX();
			yMove -= ViewVector.getY();
			zMove -= ViewVector.getZ();
		}

		if (Keys[1]) {
			xMove += SideViewVector.getX();
			yMove += SideViewVector.getY();
			zMove += SideViewVector.getZ();
		}

		if (Keys[3]) {
			xMove -= SideViewVector.getX();
			yMove -= SideViewVector.getY();
			zMove -= SideViewVector.getZ();
		}

		Vector3d MoveVector = ((new Vector3d(xMove, yMove, zMove).div(10)).mul(MovementSpeed)).normalized();
		movevf(MoveVector);
		Calculator.SetPrederterminedInfo(ViewFrom, ViewTo);
	}

	private void mouseMovement(double NewMouseX, double NewMouseY) {
		Vector3d VerticalVector = new Vector3d(0, 0, 1).normalized(), SideViewVector, ret;

		double difX = (ScreenSize.getWidth() / 2 - NewMouseX);
		double difY = (ScreenSize.getHeight() / 2 - NewMouseY);
		double ang;

		ret = ViewVector;

		ang = -(difX / 360 * HorRotSpeed);
		ret = ret.rotate(VerticalVector, ang);

		SideViewVector = VerticalVector.cross(ret);

		ang = -(difY / 360 * VertRotSpeed);
		if ((difY > 0 && (VerticalVector.rotate(SideViewVector, -ang).sub(ret.normalized())).normalized().getZ() > 0)
				|| (difY < 0 && (VerticalVector.rotate(SideViewVector, 179 - ang).sub(ret.normalized())).normalized()
						.getZ() < 0))
			ret = ret.rotate(SideViewVector, ang);

		ret = ViewFrom.add(ret);
		ret = ret.sub(ViewTo);
		movevt(ret);

	}

	private void movevf(Vector3d v) {
		ViewFrom = ViewFrom.add(v);
		movevt(v);
	}

	private void movevt(Vector3d v) {
		ViewTo = ViewTo.add(v);
		ViewVector = ViewTo.sub(ViewFrom);
	}

	private void recenterMouse() {
		try {
			r = new Robot();
			r.mouseMove((int) ScreenSize.getWidth() / 2, (int) ScreenSize.getHeight() / 2);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W)
			Keys[0] = true;
		if (e.getKeyCode() == KeyEvent.VK_A)
			Keys[1] = true;
		if (e.getKeyCode() == KeyEvent.VK_S)
			Keys[2] = true;
		if (e.getKeyCode() == KeyEvent.VK_D)
			Keys[3] = true;
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
		e.consume();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W)
			Keys[0] = false;
		if (e.getKeyCode() == KeyEvent.VK_A)
			Keys[1] = false;
		if (e.getKeyCode() == KeyEvent.VK_S)
			Keys[2] = false;
		if (e.getKeyCode() == KeyEvent.VK_D)
			Keys[3] = false;
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	public void mouseDragged(MouseEvent arg0) {
		mouseMovement(arg0.getX(), arg0.getY());
		recenterMouse();
		arg0.consume();
	}

	public void mouseMoved(MouseEvent arg0) {
		mouseMovement(arg0.getX(), arg0.getY());
		recenterMouse();
		arg0.consume();
	}

	public void mouseClicked(MouseEvent arg0) {
		arg0.consume();
	}

	public void mouseEntered(MouseEvent arg0) {
		arg0.consume();
	}

	public void mouseExited(MouseEvent arg0) {
		arg0.consume();
	}

	public void mousePressed(MouseEvent arg0) {
		arg0.consume();
	}

	public void mouseReleased(MouseEvent arg0) {
		arg0.consume();
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if (arg0.getUnitsToScroll() > 0) {
			if (DPolygon.zoom > MinZoom)
				DPolygon.zoom -= ZoomSpeed * arg0.getUnitsToScroll();
		} else {
			if (DPolygon.zoom < MaxZoom)
				DPolygon.zoom -= ZoomSpeed * arg0.getUnitsToScroll();
		}
		arg0.consume();
	}

	public void invisibleMouse() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "InvisibleCursor");
		setCursor(invisibleCursor);
	}

	public abstract void drawMouseAim(Graphics g);

	public double getDrawFPS() {
		return drawFPS;
	}

	public Vector3d getViewFrom() {
		return ViewFrom;
	}

	public void setViewFrom(Vector3d viewFrom) {
		ViewFrom = viewFrom;
		ViewVector = ViewTo.sub(ViewFrom);
	}

	public Vector3d getViewTo() {
		return ViewTo;
	}

	public void setViewTo(Vector3d viewTo) {
		ViewTo = viewTo;
		ViewVector = ViewTo.sub(ViewFrom);
	}

	public Vector3d getViewVector() {
		return ViewVector;
	}

	public void setViewVector(Vector3d viewVector) {
		ViewVector = viewVector;
		ViewTo = ViewFrom.add(ViewVector);
	}
}
