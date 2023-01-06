package gameEngine.IIID;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class DPolygon {
	private Vertex[] v = new Vertex[0];

	private Image savedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage polyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
			fittedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	private Vector2d[] polyTexCoord = new Vector2d[v.length];
	private Vertex[] fittedTexCoord = new Vertex[4];

	public PolygonObject DrawablePolygon = null;
	private boolean imageStored = false;

	private int right, left, top, bottom;// vector2d extreme coords for vertices on poly image; or, extreme coords for
											// polyTexCoords.
	private Polygon P = new Polygon();// a polygon made up by coordinates of polyTexCoords.
	private boolean singleTexCoord = false;// whether texCoords of vertices are determined on the first among them only
											// or all of them.

	public static double zoom = Calculator.getViewTo().sub(Calculator.getViewFrom()).length() * 3.0;
	public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static final int SIMPLE_POLYGON = 0, MIXED_POLYGON = 1, PIXEL_POLYGON = 2;

	public DPolygon(Vertex[] v) {
		this(v, false);
	}

	public DPolygon(Vertex[] v, boolean singleTexCoord) {
		setVertex(v, singleTexCoord);
	}

	public DPolygon(Vector3d[] pos, Color[] c) {
		setVertex(pos, c);
	}

	public DPolygon(Vector3d[] pos, Color c) {
		setVertex(pos, c);
	}

	public DPolygon(double[] x, double[] y, double[] z, Color[] c) {
		setVertex(x, y, z, c);
	}

	public DPolygon(double[] x, double[] y, double[] z, Color c) {
		setVertex(x, y, z, c);
	}

	public DPolygon(Vertex[] v, Image img) {
		this(v, img, false);
	}

	public DPolygon(Vertex[] v, Image img, boolean singleTexCoord) {
		setVertex(v, singleTexCoord);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos, Vector2d texCoord, Color[] c, Image img) {
		setVertex(pos, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos, Vector2d[] texCoord, Color[] c, Image img) {
		setVertex(pos, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos, Vector2d texCoord, Color c, Image img) {
		setVertex(pos, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos, Vector2d[] texCoord, Color c, Image img) {
		setVertex(pos, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d texCoord, Color[] c, Image img) {
		setVertex(x, y, z, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d[] texCoord, Color[] c, Image img) {
		setVertex(x, y, z, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d texCoord, Color c, Image img) {
		setVertex(x, y, z, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d[] texCoord, Color c, Image img) {
		setVertex(x, y, z, texCoord, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double x2, double y2, Color[] c, Image img) {
		setVertex(x, y, z, x2, y2, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double[] x2, double[] y2, Color[] c, Image img) {
		setVertex(x, y, z, x2, y2, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double x2, double y2, Color c, Image img) {
		setVertex(x, y, z, x2, y2, c);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double[] x2, double[] y2, Color c, Image img) {
		setVertex(x, y, z, x2, y2, c);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos) {
		setVertex(pos);
	}

	public DPolygon(double[] x, double[] y, double[] z) {
		setVertex(x, y, z);
	}

	public DPolygon(Vector3d[] pos, Vector2d texCoord, Image img) {
		setVertex(pos, texCoord);
		setSavedImage(img);
	}

	public DPolygon(Vector3d[] pos, Vector2d[] texCoord, Image img) {
		setVertex(pos, texCoord);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d texCoord, Image img) {
		setVertex(x, y, z, texCoord);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, Vector2d[] texCoord, Image img) {
		setVertex(x, y, z, texCoord);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double x2, double y2, Image img) {
		setVertex(x, y, z, x2, y2);
		setSavedImage(img);
	}

	public DPolygon(double[] x, double[] y, double[] z, double[] x2, double[] y2, Image img) {
		setVertex(x, y, z, x2, y2);
		setSavedImage(img);
	}

	private void createPolygon() {
		DrawablePolygon = new PolygonObject(v);
		DrawablePolygon.setDPolygon(this);
	}

	public void updateFace() {
		for (int i = 0; i < v.length; i++) {
			double[] CalcPos = Calculator.CalculatePosition(v[i]);
			v[i].setDrawCoord(new Vector2d(ScreenSize.getWidth() / 2 + (CalcPos[0] * zoom),
					ScreenSize.getHeight() / 2 + (CalcPos[1] * zoom)));
			v[i].setDraw(!(Calculator.getT() < 0));
		}

		// setting polygon---
		DrawablePolygon.updatePolygon(v);

		// setting textures---
		setPolyTexCoord();
	}

	public void drawFace(Graphics g) {
		DrawablePolygon.drawPolygon(g);
	}

	public double getDist() {
		double total = 0;
		for (int i = 0; i < v.length; i++)
			total += getDistanceToP(i);
		return total / v.length;
	}// the average distance of the polygon from viewfrom position.

	private double getDistanceToP(int i) {
		return Calculator.getViewFrom().sub(v[i].getPos()).length();
	}

	public boolean MouseOver(int x, int y) {
		if (DrawablePolygon == null)
			return false;
		return DrawablePolygon.MouseOver(x, y);
	}

	public Polygon getPolygon() {
		return DrawablePolygon.getPolygon();
	}// the drawable polygon after all the calculations.

	public double getLighting() {
		return DrawablePolygon.getLighting();
	}// the extra or dim lighting on plane polygon.

	public void setLighting(double lighting) {
		DrawablePolygon.setLighting(lighting);
	}

	public boolean isVisible() {
		return DrawablePolygon.isVisible();
	}// whether the polygon should be visible or not.

	public void setVisible(boolean visible) {
		DrawablePolygon.setVisible(visible);
	}

	public boolean isOutline() {
		return DrawablePolygon.isOutline();
	}// whether the polygon should have a outline or not.

	public void setOutline(boolean outline) {
		DrawablePolygon.setOutline(outline);
	}

	boolean isFlipPixels() {
		return DrawablePolygon.isFlipPixels();
	}// whether the pixel mixture should be flipped or not.

	void setFlipPixels(boolean flipPixels) {
		DrawablePolygon.setFlipPixels(flipPixels);
	}

	public boolean isDrawImage() {
		return DrawablePolygon.isDrawImage();
	}// whether the image saved should be visible or not.

	public void setDrawImage(boolean drawImage) {
		if (!imageStored)
			drawImage = false;
		DrawablePolygon.setDrawImage(drawImage);
	}

	public int getDrawMethod() {
		return DrawablePolygon.getDrawMethod();
	}// the drawing method.

	public void setDrawMethod(int drawMethod) {
		DrawablePolygon.setDrawMethod(drawMethod);
	}

	public boolean isImageStored() {
		return imageStored;
	}// whether the texture image is saved or not.

	private void setImageStored(boolean imageStored) {
		this.imageStored = imageStored;
	}

	public boolean isSingleTexCoord() {
		return singleTexCoord;
	}

	public Image getSavedImage() {
		return savedImage;
	}// the image for texturing.

	public Vector2d[] getPolyTexCoord() {
		return polyTexCoord;
	}// coordinates of each vertex on the poly image as a vector2d.

	public Image getPolyImage() {
		return polyImage;
	}// savedImage converted into 2d image according to the vertices in this polygon
		// fitted according to the origin.

	public Vertex[] getFittedTexCoord() {
		return fittedTexCoord;
	}// pos---coordinates of each corner of poly image as a vector3d.
		// drawCoord---coordinates of fittedTexCoord3d in 2d according to viewvector.

	public Image getFittedImage() {
		return fittedImage;
	}// Image which is suitable to send to DrawablePolygon after which it will be
		// suitable to draw on screen for texture after all calculations.

	public Vertex[] getVertex() {
		return v;
	}// the vertices with positions.

	public void setSavedImage(Image img, int x, int y) {
		Vector2d texCoord = new Vector2d(x, y);
		setSavedImage(img, texCoord);
	}

	public void setSavedImage(Image img, Vector2d texCoord) {
		if (texCoord != null && v.length > 0)
			v[0].setTexCoord(texCoord);
		singleTexCoord = true;
		setSavedImage(img);
	}

	public void setSavedImage(Image img, int[] x, int[] y) {
		Vector2d[] texCoord = new Vector2d[x.length];
		for (int i = 0; i < texCoord.length; i++)
			texCoord[i] = new Vector2d(x[i], y[i]);
		setSavedImage(img, texCoord);
	}

	public void setSavedImage(Image img, Vector2d[] texCoord) {
		if (texCoord != null)
			for (int i = 0; i < texCoord.length; i++)
				v[i].setTexCoord(texCoord[i]);
		singleTexCoord = false;
		setSavedImage(img);
	}

	public void setSavedImage(Image img) {
		if (img == null)
			img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		this.savedImage = img;
		if (!(savedImage == new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)))
			setImageStored(true);
		else
			setImageStored(false);

		setPolyTexCoord();
	}

	private void setPolyTexCoord() {
		polyTexCoord = new Vector2d[v.length];

		if (v.length > 2) {
			P = new Polygon();
			left = 0;
			right = 0;
			top = 0;
			bottom = 0;

			Vector2d[] calc = new Vector2d[v.length];

			calc[0] = new Vector2d(v[0].getTexCoord());
			calc[1] = new Vector2d(calc[0].getX() + v[1].getPos().sub(v[0].getPos()).length() * zoom, calc[0].getY());

			for (int i = 2; i < calc.length; i++) {
				double dist = v[i].getPos().sub(v[i - 1].getPos()).length() * zoom;
				Vector2d p = calc[i - 2].sub(calc[i - 1]).normalized().mul(dist);
				double ang = Math.toDegrees(Math.acos(v[i].getPos().sub(v[i - 1].getPos()).normalized()
						.dot(v[i - 2].getPos().sub(v[i - 1].getPos()).normalized())));
				Vector2d q = calc[i - 1].add(p.rotate(ang));
				Vector2d r = calc[i - 1].add(p.rotate(-ang));

				if (i == 2)
					calc[i]=new Vector2d(q);
				else {
					double a = q.sub(calc[i - 3]).length();
					double b = r.sub(calc[i - 3]).length();
					double c = v[i].getPos().sub(v[i - 3].getPos()).length() * zoom;
					double x = Math.max(c, a) - Math.min(c, a);
					double y = Math.max(c, b) - Math.min(c, b);
					if (x <= y)
						calc[i] = new Vector2d(q);
					else
						calc[i] = new Vector2d(r);
				}
			}

			/*
			 * // This didn't work----------- { for (int i = 2; i < calc.length; i++) {
			 * double b = v[i].getPos().sub(v[i - 1].getPos()).length() * zoom; double c =
			 * v[i].getPos().sub(v[i - 2].getPos()).length() * zoom; Vector2d k = calc[i -
			 * 2].add(calc[i - 1].sub(calc[i - 2]).normalized().mul(c)); Vector2d l = calc[i
			 * - 1].add(calc[i - 2].sub(calc[i - 1]).normalized().mul(b)); double d =
			 * k.sub(l).length(); Vector2d m = l.sub(calc[i - 2]); m =
			 * m.normalized().mul(m.length() + d / 2.0); double e = m.length(); double ang =
			 * 1.0 / Math.cos(Math.toRadians(e / c)); // double ang = 1.0 /
			 * Math.cos(v[i].getPos().sub(v[i - 1].getPos()).dot(v[i - 2].getPos().sub(v[i -
			 * 1].getPos()))); Vector2d n = calc[i - 2].add((calc[i - 1].sub(calc[i -
			 * 2]).normalized().mul(c)).rotate(ang)); Vector2d o = calc[i - 2].add((calc[i -
			 * 1].sub(calc[i - 2]).normalized().mul(c)).rotate(-ang)); if (i == 2) calc[i] =
			 * n; else { Vector2d pos = calc[i - 3]; double f = n.sub(pos).length(); double
			 * g = o.sub(pos).length(); double h = v[i].getPos().sub(v[i -
			 * 3].getPos()).length() * zoom; double x = Math.max(h, f) - Math.min(h, f);
			 * double y = Math.max(h, g) - Math.min(h, g); calc[i] = x <= y ? n : o; } } }
			 */

			if (singleTexCoord)
				for (int i = 1; i < calc.length; i++)
					v[i].setTexCoord(calc[i]);

			for (int i = 1; i < calc.length; i++) {
				left = calc[left].getX() <= calc[i].getX() ? left : i;
				right = calc[right].getX() >= calc[i].getX() ? right : i;
				top = calc[top].getY() <= calc[i].getY() ? top : i;
				bottom = calc[bottom].getY() >= calc[i].getY() ? bottom : i;
			}

			for (int i = 0; i < calc.length; i++) {
				polyTexCoord[i] = new Vector2d(calc[i].getX() - calc[left].getX(), calc[i].getY() - calc[top].getY());
				P.addPoint((int) polyTexCoord[i].getX(), (int) polyTexCoord[i].getY());
			}

		}

		setPolyImage();
	}

	private void setPolyImage() {
		if (v.length > 2 && isImageStored()) {
			if (singleTexCoord) {
				BufferedImage img;
				if (P.xpoints[right] != 0 && P.ypoints[bottom] != 0)
					img = new BufferedImage(P.xpoints[right], P.ypoints[bottom], BufferedImage.TYPE_INT_ARGB);
				else
					img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

				for (int i = 0; i < img.getWidth(); i++)
					for (int j = 0; j < img.getHeight(); j++) {
						if (!P.contains(i, j))
							continue;
						int pix = (255 << 24) | (255 << 16) | (255 << 8) | 255;
						img.setRGB(i, j, pix);
					}

				Graphics graphics = img.getGraphics();
				graphics.drawImage(getSavedImage(), (int) (-(v[left].getTexCoord().getX())),
						(int) (-(v[top].getTexCoord().getY())), null);
				// graphics.setColor(Color.blue);for (int i = 0; i <
				// P.npoints;i++)graphics.drawString("" + i, P.xpoints[i], P.ypoints[i]);

				for (int i = 0; i < img.getWidth(); i++)
					for (int j = 0; j < img.getHeight(); j++) {
						if (P.contains(i, j))
							continue;
						int pix = (0 << 24) | (0 << 16) | (0 << 8) | 0;
						img.setRGB(i, j, pix);
					}

				polyImage = img;

			} else
				polyImage = (BufferedImage) ImageDraw.scalePolygon(this);
		} else
			polyImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		setFittedTexCoord();
	}

	private void setFittedTexCoord() {
		fittedTexCoord = new Vertex[4];

		if (v.length > 2) {

			Vector2d a, b, c, d;
			Vector3d e, f, g, h, k, l, q, r, s, t;
			double m, n, o, p;
			double ang1, ang2, ang3, ang4;

			a = polyTexCoord[left].sub(polyTexCoord[top]);
			b = polyTexCoord[right].sub(polyTexCoord[top]);
			c = polyTexCoord[left].sub(polyTexCoord[bottom]);
			d = polyTexCoord[right].sub(polyTexCoord[bottom]);

			m = polyTexCoord[top].getX() / zoom;
			n = (polyTexCoord[right].getX() - polyTexCoord[top].getX()) / zoom;
			o = polyTexCoord[bottom].getX() / zoom;
			p = (polyTexCoord[right].getX() - polyTexCoord[bottom].getX()) / zoom;

			ang1 = Math.toDegrees(Math.acos(new Vector2d(-m, 0).normalized().dot(a.normalized())));
			ang2 = Math.toDegrees(Math.acos(new Vector2d(n, 0).normalized().dot(b.normalized())));
			ang3 = Math.toDegrees(Math.acos(new Vector2d(-o, 0).normalized().dot(c.normalized())));
			ang4 = Math.toDegrees(Math.acos(new Vector2d(p, 0).normalized().dot(d.normalized())));

			e = v[left].getPos().sub(v[top].getPos());
			f = v[right].getPos().sub(v[top].getPos());
			g = v[left].getPos().sub(v[bottom].getPos());
			h = v[right].getPos().sub(v[bottom].getPos());

			k = v[top].getPos().sub(v[bottom].getPos());
			l = k.negative();// v[bottom].getPos().sub(v[top].getPos());

			q = (!v[left].getPos().equals(v[bottom].getPos()) && left != bottom) ? k : f.negative();
			r = (!v[right].getPos().equals(v[bottom].getPos()) && right != bottom) ? k : e.negative();
			s = (!v[left].getPos().equals(v[top].getPos()) && left != top) ? l : h.negative();
			t = (!v[right].getPos().equals(v[top].getPos()) && right != top) ? l : g.negative();

			fittedTexCoord[0] = new Vertex((e).rotate(e.cross(q), ang1));
			fittedTexCoord[0] = new Vertex(fittedTexCoord[0].getPos().normalized().mul(m));
			fittedTexCoord[0] = new Vertex(v[top].getPos().add(fittedTexCoord[0].getPos()));

			fittedTexCoord[1] = new Vertex((f).rotate(f.cross(r), ang2));
			fittedTexCoord[1] = new Vertex(fittedTexCoord[1].getPos().normalized().mul(n));
			fittedTexCoord[1] = new Vertex(v[top].getPos().add(fittedTexCoord[1].getPos()));

			fittedTexCoord[2] = new Vertex((g).rotate(g.cross(s), ang3));
			fittedTexCoord[2] = new Vertex(fittedTexCoord[2].getPos().normalized().mul(o));
			fittedTexCoord[2] = new Vertex(v[bottom].getPos().add(fittedTexCoord[2].getPos()));

			fittedTexCoord[3] = new Vertex((h).rotate(h.cross(t), ang4));
			fittedTexCoord[3] = new Vertex(fittedTexCoord[3].getPos().normalized().mul(p));
			fittedTexCoord[3] = new Vertex(v[bottom].getPos().add(fittedTexCoord[3].getPos()));

			for (int i = 0; i < fittedTexCoord.length; i++) {
				double[] CalcPos = Calculator.CalculatePosition(fittedTexCoord[i]);
				fittedTexCoord[i].setDrawCoord(new Vector2d(ScreenSize.getWidth() / 2 + (CalcPos[0] * zoom),
						ScreenSize.getHeight() / 2 + (CalcPos[1] * zoom)));
				fittedTexCoord[i].setDraw(!(Calculator.getT() < 0));
			}

		}

		setFittedImage();
	}

	private void setFittedImage() {
		if (v.length > 2 && isImageStored()) {
			// TODO for now, this.-------------------later, set this image according to 3d
			// view of polyImage.

			// NOTE - this will be fine; no actual need to change this.
			fittedImage = polyImage;

		} else
			fittedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		DrawablePolygon.setImg(fittedImage, fittedTexCoord);
	}

	public void setVertex(double[] x, double[] y, double[] z) {
		if (x == null || y == null || z == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos);
	}

	public void setVertex(Vector3d[] pos) {
		setVertex(pos, new Color(0, 0, 0));
	}

	public void setVertex(double[] x, double[] y, double[] z, Color c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, z, v);
	}

	public void setVertex(double[] x, double[] y, double[] z, Color[] c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos, c);
	}

	public void setVertex(Vector3d[] pos, Color c) {
		if (pos == null || c == null)
			return;
		Color[] v = new Color[pos.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(pos, v);
	}

	public void setVertex(Vector3d[] pos, Color[] c) {
		setVertex(pos, new Vector2d(0, 0), c);
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d texCoord) {
		if (x == null || y == null || z == null || texCoord == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos, texCoord);
	}

	public void setVertex(double[] x, double[] y, double[] z, double x2, double y2) {
		if (x == null || y == null || z == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		Vector2d texCoord = new Vector2d(x2, y2);
		setVertex(pos, texCoord);
	}

	public void setVertex(Vector3d[] pos, Vector2d texCoord) {
		setVertex(pos, texCoord, new Color(0, 0, 0));
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d texCoord, Color c) {
		if (x == null || y == null || z == null || texCoord == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, z, texCoord, v);
	}

	public void setVertex(double[] x, double[] y, double[] z, double x2, double y2, Color c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, z, x2, y2, v);
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d texCoord, Color[] c) {
		if (x == null || y == null || z == null || texCoord == null || c == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos, texCoord, c);
	}

	public void setVertex(double[] x, double[] y, double[] z, double x2, double y2, Color[] c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		Vector2d texCoord = new Vector2d(x2, y2);
		setVertex(pos, texCoord, c);
	}

	public void setVertex(Vector3d[] pos, Vector2d texCoord, Color c) {
		if (pos == null || texCoord == null || c == null)
			return;
		Color[] v = new Color[pos.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(pos, texCoord, v);
	}

	public void setVertex(Vector3d[] pos, Vector2d texCoord, Color[] c) {
		if (pos == null || texCoord == null || c == null)
			return;
		Vertex[] v = new Vertex[pos.length];
		for (int i = 0; i < v.length; i++) {
			if (i == 0)
				v[i] = new Vertex(pos[i], new Vector2d(0, 0), texCoord, c[i]);
			else
				v[i] = new Vertex(pos[i], c[i]);
		}
		setVertex(v, true);
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d[] texCoord) {
		if (x == null || y == null || z == null || texCoord == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos, texCoord);
	}

	public void setVertex(double[] x, double[] y, double[] z, double[] x2, double[] y2) {
		if (x == null || y == null || z == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		Vector2d[] texCoord = new Vector2d[x2.length];
		for (int i = 0; i < pos.length; i++) {
			texCoord[i] = new Vector2d(x2[i], y2[i]);
		}
		setVertex(pos, texCoord);
	}

	public void setVertex(Vector3d[] pos, Vector2d[] texCoord) {
		setVertex(pos, texCoord, new Color(0, 0, 0));
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d[] texCoord, Color c) {
		if (x == null || y == null || z == null || texCoord == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, z, texCoord, v);
	}

	public void setVertex(double[] x, double[] y, double[] z, double[] x2, double[] y2, Color c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Color[] v = new Color[x.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(x, y, z, x2, y2, v);
	}

	public void setVertex(double[] x, double[] y, double[] z, Vector2d[] texCoord, Color[] c) {
		if (x == null || y == null || z == null || texCoord == null || c == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		setVertex(pos, texCoord, c);
	}

	public void setVertex(double[] x, double[] y, double[] z, double[] x2, double[] y2, Color[] c) {
		if (x == null || y == null || z == null || c == null)
			return;
		Vector3d[] pos = new Vector3d[x.length];
		for (int i = 0; i < pos.length; i++) {
			pos[i] = new Vector3d(x[i], y[i], z[i]);
		}
		Vector2d[] texCoord = new Vector2d[x2.length];
		for (int i = 0; i < pos.length; i++) {
			texCoord[i] = new Vector2d(x2[i], y2[i]);
		}
		setVertex(pos, texCoord, c);
	}

	public void setVertex(Vector3d[] pos, Vector2d[] texCoord, Color c) {
		if (pos == null || texCoord == null || c == null)
			return;
		Color[] v = new Color[pos.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = c;
		}
		setVertex(pos, texCoord, v);
	}

	public void setVertex(Vector3d[] pos, Vector2d[] texCoord, Color[] c) {
		if (pos == null || texCoord == null || c == null)
			return;
		Vertex[] v = new Vertex[pos.length];
		for (int i = 0; i < v.length; i++) {
			v[i] = new Vertex(pos[i], new Vector2d(0, 0), texCoord[i], c[i]);
		}
		setVertex(v, false);
	}

	public void setVertex(Vertex[] v, boolean singleTexCoord) {
		this.singleTexCoord = singleTexCoord;
		setVertex(v);
	}

	public void setVertex(Vertex[] v) {
		if (v == null)
			return;
		this.v = v;
		if (DrawablePolygon == null)
			createPolygon();
		else
			DrawablePolygon.setVertex(v);

		setPolyTexCoord();
	}
}