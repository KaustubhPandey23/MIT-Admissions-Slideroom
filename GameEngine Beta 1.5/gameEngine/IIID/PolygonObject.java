package gameEngine.IIID;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class PolygonObject {
	private DPolygon Dpolygon = null;
	private Vertex[] v = new Vertex[0];
	private Polygon P = new Polygon();
	private Image img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	private double lighting = 1;
	private boolean visible = true, outline = true, flipPixels = false, drawImage = false;
	private Vertex[] imageDrawCoords;

	private int drawMethod = 2;

	// Constructor visibility needs to be package.
	PolygonObject(Vertex[] v) {
		setVertex(v);
	}

	PolygonObject(Vector2d[] drawCoord, Color[] c) {
		setVertex(drawCoord, c);
	}

	PolygonObject(Vector2d[] drawCoord, Color c) {
		setVertex(drawCoord, c);
	}

	PolygonObject(double[] x, double[] y, Color[] c) {
		setVertex(x, y, c);
	}

	PolygonObject(double[] x, double[] y, Color c) {
		setVertex(x, y, c);
	}

	PolygonObject(Vector2d[] drawCoord) {
		setVertex(drawCoord);
	}

	PolygonObject(double[] x, double[] y) {
		setVertex(x, y);
	}

	public void updatePolygon(Vertex[] v) {
		if (v == null)
			return;
		Vector2d[] drawCoord;
		int counter = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i].isDraw())
				counter++;
		}
		drawCoord = new Vector2d[counter];
		counter = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i].isDraw())
				drawCoord[counter++] = v[i].getDrawCoord();
		}
		/*
		 * Vector2d[] drawCoord = new Vector2d[v.length]; for (int i = 0; i < v.length;
		 * i++) { drawCoord[i] = v[i].getDrawCoord(); }
		 */
		updatePolygon(drawCoord);
	}

	public void updatePolygon(Vector2d[] v) {
		if (v == null)
			return;
		double[] x = new double[v.length], y = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			x[i] = v[i].getX();
			y[i] = v[i].getY();
		}
		updatePolygon(x, y);
	}

	public void updatePolygon(double[] x, double[] y) {
		if (x == null || y == null)
			return;
		P.reset();
		for (int i = 0; i < x.length; i++)
			P.addPoint((int) x[i], (int) y[i]);
	}

	void drawPolygon(Graphics g) {
		if (drawImage) {
			drawImage(g);
		} else {
			switch (drawMethod) {
			case 1:
				drawPolyMix(g);
				break;
			case 2:
				drawPixPoly(g);
				break;
			default:
				drawPoly(g);
			}
		}
	}// visibility needs to be package.

	private void drawImage(Graphics g) {
		if (g == null)
			return;

		Vector2d draw = new Vector2d(0, 0);

		if (visible)
			g.drawImage(ImageDraw.scaleImage(img, imageDrawCoords, draw), (int) draw.getX(), (int) draw.getY(), null);
	}// draws texture in the polygon.

	private void drawPixPoly(Graphics g) {
		if (g == null)
			return;

		Vector2d draw = new Vector2d(0, 0);

		if (visible)
			g.drawImage(ImageDraw.draw(v, flipPixels, draw), (int) draw.getX(), (int) draw.getY(), null);

		/*
		 * int counter = 0; for (int i = 0; i < v.length; i++) { if (v[i].isDraw())
		 * counter++; } Color[] color = new Color[counter]; counter = 0; for (int i = 0;
		 * i < v.length; i++) { if (v[i].isDraw()) color[counter++] = v[i].getColor(); }
		 * 
		 * Vector2d draw=new Vector2d(0,0);
		 * 
		 * if (visible) g.drawImage(ImageDraw.draw(P, color,flipPixels,draw),
		 * (int)draw.getX(),(int)draw.getY(), null);
		 */
	}// draws the polygon in pixel color format.

	private void drawPoly(Graphics g) {
		if (g == null)
			return;
		Color c = v[0].getColor();
		if (visible) {
			g.setColor(new Color((int) (c.getRed() * lighting), (int) (c.getGreen() * lighting),
					(int) (c.getBlue() * lighting)));
			g.fillPolygon(P);
			if (outline) {
				g.setColor(Color.BLACK);
				g.drawPolygon(P);
			}
		}
	}// simply draws the polygon.

	private void drawPolyMix(Graphics g) {
		if (g == null)
			return;
		int ra = 0, ga = 0, ba = 0, aa = 0;
		for (int i = 0; i < v.length; i++) {
			ra += v[i].getColor().getRed();
			ga += v[i].getColor().getGreen();
			ba += v[i].getColor().getBlue();
			aa += v[i].getColor().getAlpha();
		}
		ra /= v.length;
		ga /= v.length;
		ba /= v.length;
		aa /= v.length;
		Color c = new Color(ra, ga, ba, aa);
		if (visible) {
			g.setColor(new Color((int) (c.getRed() * lighting), (int) (c.getGreen() * lighting),
					(int) (c.getBlue() * lighting)));
			g.fillPolygon(P);
			if (outline) {
				g.setColor(Color.BLACK);
				g.drawPolygon(P);
			}
		}
	}// simply draws the polygon with mixed colours of vertices.

	boolean MouseOver(int x, int y) {
		return P.contains(x, y);
	}// visibility needs to be package.

	Polygon getPolygon() {
		return P;
	}// visibility needs to be package.

	double getLighting() {
		return lighting;
	}// visibility needs to be package.

	void setLighting(double lighting) {
		this.lighting = lighting;
	}// visibility needs to be package.

	boolean isVisible() {
		return visible;
	}// visibility needs to be package.

	void setVisible(boolean visible) {
		this.visible = visible;
	}// visibility needs to be package.

	boolean isOutline() {
		return outline;
	}// visibility needs to be package.

	void setOutline(boolean outline) {
		this.outline = outline;
	}// visibility needs to be package.

	boolean isFlipPixels() {
		return flipPixels;
	}// visibility needs to be package.

	void setFlipPixels(boolean flipPixels) {
		this.flipPixels = flipPixels;
	}// visibility needs to be package.

	boolean isDrawImage() {
		return drawImage;
	}// visibility needs to be package.

	void setDrawImage(boolean drawImage) {
		if (Dpolygon.isImageStored())
			drawImage = false;
		if (v.length > 2 && Dpolygon.isImageStored() && imageDrawCoords != null)
			for (int i = 0; i < imageDrawCoords.length; i++)
				if (imageDrawCoords[i] != null)
					if (!imageDrawCoords[i].isDraw())
						drawImage = false;
		this.drawImage = drawImage;
	}// visibility needs to be package.

	int getDrawMethod() {
		return drawMethod;
	}// visibility needs to be package.

	void setDrawMethod(int drawMethod) {
		this.drawMethod = drawMethod;
	}// visibility needs to be package.

	void setImg(Image img, Vertex[] imageDrawCoords) {
		if (img == null || imageDrawCoords == null)
			return;
		this.img = img;
		this.imageDrawCoords = imageDrawCoords;
		setDrawImage(isDrawImage());
	}// visibility needs to be package.

	public DPolygon getDPolygon() {
		return Dpolygon;
	}

	public void setDPolygon(DPolygon Dpolygon) {
		if (Dpolygon == null)
			return;
		Dpolygon.DrawablePolygon = this;
		this.Dpolygon = Dpolygon;
		setVertex(Dpolygon.getVertex());
	}

	public Vertex[] getVertex() {
		return v;
	}// the vertices with drawCoordinates.

	public void setVertex(double[] x, double[] y) {
		if (x == null || y == null)
			return;
		Vector2d[] drawCoord = new Vector2d[x.length];
		for (int i = 0; i < drawCoord.length; i++) {
			drawCoord[i] = new Vector2d(x[i], y[i]);
		}
		setVertex(drawCoord);
	}

	public void setVertex(Vector2d[] drawCoord) {
		if (drawCoord == null)
			return;
		Vertex[] v = new Vertex[drawCoord.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = new Vertex(new Vector3d(0, 0, 0), drawCoord[i]);
		}
		setVertex(v);
	}

	public void setVertex(double[] x, double[] y, Color c) {
		if (x == null || y == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, v);
	}

	public void setVertex(double[] x, double[] y, Color[] c) {
		if (x == null || y == null || c == null)
			return;
		Vector2d[] drawCoord = new Vector2d[x.length];
		for (int i = 0; i < drawCoord.length; i++) {
			drawCoord[i] = new Vector2d(x[i], y[i]);
		}
		setVertex(drawCoord, c);
	}

	public void setVertex(Vector2d[] drawCoord, Color c) {
		if (drawCoord == null || c == null)
			return;
		Color[] v = new Color[drawCoord.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(drawCoord, v);
	}

	public void setVertex(Vector2d[] drawCoord, Color[] c) {
		if (drawCoord == null || c == null)
			return;
		Vertex[] v = new Vertex[drawCoord.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = new Vertex(new Vector3d(0, 0, 0), drawCoord[i], c[i]);
		}
		setVertex(v);
	}

	public void setVertex(Vertex[] v) {
		if (v == null)
			return;
		this.v = v;
		updatePolygon(v);
	}
}
