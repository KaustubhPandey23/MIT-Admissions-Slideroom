package gameEngine.IIID;

import java.awt.Color;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public final class ImageDraw {
	public static Image draw(Vertex[] v) {
		return draw(v, null);
	}

	public static Image draw(Vertex[] v, Vector2d draw) {
		return draw(v, false, draw);
	}

	public static Image draw(Vertex[] v, boolean flipPixels, Vector2d draw) {
		int counter = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i].isDraw())
				counter++;
		}

		Vector2d[] drawCoord = new Vector2d[counter];
		Color[] color = new Color[counter];
		counter = 0;
		for (int i = 0; i < v.length; i++) {
			if (v[i].isDraw()) {
				color[counter] = v[i].getColor();
				drawCoord[counter] = v[i].getDrawCoord();
				counter++;
			}
		}

		double[] x = new double[drawCoord.length], y = new double[drawCoord.length];
		for (int i = 0; i < drawCoord.length; i++) {
			x[i] = drawCoord[i].getX();
			y[i] = drawCoord[i].getY();
		}
		Polygon p = new Polygon();
		for (int i = 0; i < x.length; i++)
			p.addPoint((int) x[i], (int) y[i]);

		return draw(p, color, flipPixels, draw);
	}

	/*
	 * public static Image draw(Vertex[] v,boolean flipPixels,Vector2d draw) {
	 * boolean ret = false; int lefti = 0, righti = 0, topi = 0, bottomi = 0; int
	 * left = 0, right = 0, top = 0, bottom = 0; for (int i = 1; i < v.length; i++)
	 * { if (v[i].isDraw()) { lefti = i; righti = i; topi = i; bottomi = i; ret =
	 * true; } } if (!ret) return new BufferedImage(1, 1,
	 * BufferedImage.TYPE_INT_ARGB); int counter = 0; for (int i = 0; i < v.length;
	 * i++) { if (v[i].isDraw()) { left = v[lefti].getDrawCoord().getX() <
	 * v[i].getDrawCoord().getX() ? left : counter; right =
	 * v[righti].getDrawCoord().getX() > v[i].getDrawCoord().getX() ? right :
	 * counter; top = v[topi].getDrawCoord().getY() < v[i].getDrawCoord().getY() ?
	 * top : counter; bottom = v[bottomi].getDrawCoord().getY() >
	 * v[i].getDrawCoord().getY() ? bottom : counter;
	 * 
	 * lefti = v[lefti].getDrawCoord().getX() < v[i].getDrawCoord().getX() ? lefti :
	 * i; righti = v[righti].getDrawCoord().getX() > v[i].getDrawCoord().getX() ?
	 * righti : i; topi = v[topi].getDrawCoord().getY() < v[i].getDrawCoord().getY()
	 * ? topi : i; bottomi = v[bottomi].getDrawCoord().getY() >
	 * v[i].getDrawCoord().getY() ? bottomi : i; counter++; } } double[] x = new
	 * double[counter], y = new double[counter]; counter = 0; Polygon P=new
	 * Polygon(); for (int i = 0; i < v.length; i++) { if (v[i].isDraw()) {
	 * x[counter] = v[i].getDrawCoord().getX() - v[lefti].getDrawCoord().getX();
	 * y[counter] = v[i].getDrawCoord().getY() - v[topi].getDrawCoord().getY();
	 * P.addPoint((int) x[counter], (int) y[counter]); counter++; } } int DrawX =
	 * (int) v[lefti].getDrawCoord().getX(), DrawY = (int)
	 * v[topi].getDrawCoord().getY();
	 * 
	 * if(draw!=null) draw.set(DrawX,DrawY);
	 * 
	 * BufferedImage img = new BufferedImage((int) x[right], (int) y[bottom],
	 * BufferedImage.TYPE_INT_ARGB); for (int i = 0; i < img.getWidth(); i++) for
	 * (int j = 0; j < img.getHeight(); j++) { double div = 0, ra = 0, ga = 0, ba =
	 * 0, a = 0; for (int k = 0; k < counter; k++) { double mul = (new
	 * Vector2d(x[k], y[k])).sub(new Vector2d(i, j)).length(); if (mul == 0) mul =
	 * 1; if (!flipPixels) mul = 1 / mul; Color c = v[k].getColor(); int r =
	 * c.getRed(); int g = c.getGreen(); int b = c.getBlue(); a += c.getAlpha(); ra
	 * += r * mul; ga += g * mul; ba += b * mul; div += mul; } a /= counter; int rx
	 * = (int) (ra / div); int gx = (int) (ga / div); int bx = (int) (ba / div); int
	 * ax = 0; if (P.contains(i, j)) ax = (int) a; int p = (ax << 24) | (rx << 16) |
	 * (gx << 8) | bx; img.setRGB(i, j, p); } return img; }
	 */
	public static Image draw(Polygon p, Color[] color) {
		return draw(p, color, null);
	}

	public static Image draw(Polygon p, Color[] color, Vector2d draw) {
		return draw(p, color, false, draw);
	}

	public static Image draw(Polygon p, Color[] color, boolean flipPixels, Vector2d draw) {
		int left = 0, right = 0, top = 0, bottom = 0;
		for (int i = 1; i < p.npoints; i++) {
			left = p.xpoints[left] <= p.xpoints[i] ? left : i;
			right = p.xpoints[right] >= p.xpoints[i] ? right : i;
			top = p.ypoints[top] <= p.ypoints[i] ? top : i;
			bottom = p.ypoints[bottom] >= p.ypoints[i] ? bottom : i;
		}
		double[] x = new double[p.npoints], y = new double[p.npoints];
		Polygon P = new Polygon();
		for (int i = 0; i < p.npoints; i++) {
			x[i] = p.xpoints[i] - p.xpoints[left];
			y[i] = p.ypoints[i] - p.ypoints[top];
			P.addPoint((int) x[i], (int) y[i]);
		}
		int DrawX = p.xpoints[left], DrawY = p.ypoints[top];

		if (draw != null)
			draw.set(DrawX, DrawY);

		if (p.npoints < 3)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		if (x[right] <= 0 || y[bottom] <= 0)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		BufferedImage img = new BufferedImage((int) x[right], (int) y[bottom], BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				double div = 0, ra = 0, ga = 0, ba = 0, a = 0;
				for (int k = 0; k < p.npoints; k++) {
					double mul = (new Vector2d(x[k], y[k])).sub(new Vector2d(i, j)).length();
					if (mul == 0)
						mul = 1;
					if (!flipPixels)
						mul = 1 / mul;
					Color c = color[k];
					int r = c.getRed();
					int g = c.getGreen();
					int b = c.getBlue();
					a += c.getAlpha();
					ra += r * mul;
					ga += g * mul;
					ba += b * mul;
					div += mul;
				}
				a /= p.npoints;
				int rx = (int) (ra / div);
				int gx = (int) (ga / div);
				int bx = (int) (ba / div);
				int ax = 0;
				if (P.contains(i, j))
					ax = (int) a;
				int pix = (ax << 24) | (rx << 16) | (gx << 8) | bx;
				img.setRGB(i, j, pix);
			}
		return img;
	}

	public static Image scaleImagePercent(Image toScale, double d) {
		return scaleImage(toScale, d / 100.0);
	}

	public static Image scaleImage(Image toScale, double d) {
		return scaleImage(toScale, d, d);
	}

	public static Image scaleImagePercent(Image toScale, double w, double h) {
		return scaleImage(toScale, w / 100.0, h / 100.0);
	}

	public static Image scaleImage(Image toScale, double w, double h) {
		double[] x = new double[4], y = new double[4];

		x[0] = 0;
		y[0] = 0;
		x[1] = ((BufferedImage) toScale).getWidth() * w;
		y[1] = x[0];
		x[2] = x[0];
		y[2] = ((BufferedImage) toScale).getHeight() * h;
		x[3] = x[1];
		y[3] = y[2];

		return scaleImage(toScale, x, y);
	}

	public static Image scaleImage(DPolygon poly) {
		return scaleImage(poly, null);
	}

	public static Image scaleImage(Image toScale, Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
		return scaleImage(toScale, v1, v2, v3, v4, null);
	}

	public static Image scaleImage(Image toScale, Vertex[] v) {
		return scaleImage(toScale, v, null);
	}

	public static Image scaleImage(Image toScale, double x1, double y1, double x2, double y2, double x3, double y3,
			double x4, double y4) {
		return scaleImage(toScale, x1, y1, x2, y2, x3, y3, x4, y4, null);
	}

	public static Image scaleImage(Image toScale, double[] x, double[] y) {
		return scaleImage(toScale, x, y, null);
	}

	public static Image scaleImage(Image toScale, Vector2d xy1, Vector2d xy2, Vector2d xy3, Vector2d xy4) {
		return scaleImage(toScale, xy1, xy2, xy3, xy4, null);
	}

	public static Image scaleImage(Image toScale, Vector2d[] xy) {
		return scaleImage(toScale, xy, null);
	}

	public static Image scaleImage(DPolygon poly, Vector2d draw) {
		return scaleImage(poly.getFittedImage(), poly.getFittedTexCoord(), draw);
	}

	public static Image scaleImage(Image toScale, Vertex v1, Vertex v2, Vertex v3, Vertex v4, Vector2d draw) {
		return scaleImage(toScale, new Vertex[] { v1, v2, v3, v4 }, draw);
	}

	public static Image scaleImage(Image toScale, Vertex[] v, Vector2d draw) {
		Vector2d[] xy = new Vector2d[v.length];
		for (int i = 0; i < xy.length; i++)
			xy[i] = v[i].getDrawCoord();
		return scaleImage(toScale, xy, draw);
	}

	public static Image scaleImage(Image toScale, double x1, double y1, double x2, double y2, double x3, double y3,
			double x4, double y4, Vector2d draw) {
		return scaleImage(toScale, new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, draw);
	}

	public static Image scaleImage(Image toScale, double[] x, double[] y, Vector2d draw) {
		Vector2d[] xy = new Vector2d[x.length];
		for (int i = 0; i < xy.length; i++)
			xy[i] = new Vector2d(x[i], y[i]);
		return scaleImage(toScale, xy, draw);
	}

	public static Image scaleImage(Image toScale, Vector2d xy1, Vector2d xy2, Vector2d xy3, Vector2d xy4,
			Vector2d draw) {
		return scaleImage(toScale, new Vector2d[] { xy1, xy2, xy3, xy4 }, draw);
	}

	// xy is a 2d vector array in which relative location of
	// {[(0,0),(toScale.getWidth(),0),(0,toScale.getHeight()),(toScale.getWidth(),toScale.getHeight())]
	// coordinates of the image - scale} on the
	// screen is present.

	public static Image scaleImage(Image toScale, Vector2d[] xy, Vector2d draw) {
		if (xy.length < 4)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		BufferedImage scale = (BufferedImage) toScale;

		Vector2d x1 = xy[1].sub(xy[0]);
		Vector2d x2 = xy[3].sub(xy[2]);
		Vector2d y1 = xy[2].sub(xy[0]);
		Vector2d y2 = xy[3].sub(xy[1]);

		// flips the mechanism horizontally.
		if (x1.getX() < 0 || x2.getX() < 0)
			return flipHorForScaling(scale, xy, draw);

		// flips the mechanism vertically.
		if (y1.getY() < 0 || y2.getY() < 0)
			return flipVertForScaling(scale, xy, draw);

		int left = 0, right = 0, top = 0, bottom = 0;
		for (int i = 1; i < 4; i++) {
			left = xy[left].getX() <= xy[i].getX() ? left : i;
			right = xy[right].getX() >= xy[i].getX() ? right : i;
			top = xy[top].getY() <= xy[i].getY() ? top : i;
			bottom = xy[bottom].getY() >= xy[i].getY() ? bottom : i;
		}

		BufferedImage img = new BufferedImage((int) (xy[right].getX() - xy[left].getX()),
				(int) (xy[bottom].getY() - xy[top].getY()), BufferedImage.TYPE_INT_ARGB);

		BufferedImage scaleMod = new BufferedImage(scale.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

		double ratioX1 = x1.length() / scale.getWidth();
		double ratioX2 = x2.length() / scale.getWidth();

		BufferedImage[] lines = new BufferedImage[scale.getWidth()];
		double[] offset = new double[lines.length];

		for (int i = 0; i < lines.length; i++) {
			Vector2d yVector = xy[2].add(x2.normalized().mul(ratioX2 * i))
					.sub(xy[0].add(x1.normalized().mul(ratioX1 * i)));
			double yRatio = scale.getHeight() / yVector.getY();

			lines[i] = new BufferedImage(1, (int) Math.max(1, yVector.getY()), BufferedImage.TYPE_INT_ARGB);
			offset[i] = xy[0].sub(xy[top]).add(x1.normalized().mul(ratioX1 * i)).getY();

			for (int j = 0; j < lines[i].getHeight(); j++) {
				double count = yRatio * j;
				lines[i].setRGB(0, j, scale.getRGB(i, (int) (count)));
				// lines[i].getGraphics().drawImage(scale.getSubimage(i, (int) (count), 1,
				// scale.getHeight() - (int) (count)), 0,j, null);
			}
		}

		for (int i = 0; i < lines.length; i++)
			scaleMod.getGraphics().drawImage(lines[i], i, (int) offset[i], null);

		double ratioY1 = y1.length() / scaleMod.getHeight();
		double ratioY2 = y2.length() / scaleMod.getHeight();

		lines = new BufferedImage[scaleMod.getHeight()];
		offset = new double[lines.length];

		for (int i = 0; i < lines.length; i++) {
			Vector2d xVector = xy[1].add(y2.normalized().mul(ratioY2 * i))
					.sub(xy[0].add(y1.normalized().mul(ratioY1 * i)));
			double xRatio = scaleMod.getWidth() / xVector.getX();

			lines[i] = new BufferedImage((int) Math.max(1, xVector.getX()), 1, BufferedImage.TYPE_INT_ARGB);
			offset[i] = xy[0].sub(xy[left]).add(y1.normalized().mul(ratioY1 * i)).getX();

			for (int j = 0; j < lines[i].getWidth(); j++) {
				double count = xRatio * j;
				lines[i].setRGB(j, 0, scaleMod.getRGB((int) (count), i));
				// lines[i].getGraphics().drawImage(scaleMod.getSubimage((int) (count), i,
				// scaleMod.getWidth() - (int) (count), 1), j, 0, null);
			}
		}

		for (int i = 0; i < lines.length; i++)
			img.getGraphics().drawImage(lines[i], (int) offset[i], i, null);

		int DrawX = (int) xy[left].getX(), DrawY = (int) xy[top].getY();

		if (draw != null)
			draw.set(DrawX, DrawY);

		return img;
	}

	private static Image flipHorForScaling(BufferedImage scale, Vector2d[] xy, Vector2d draw) {
		Vector2d swap;

		swap = xy[0];
		xy[0] = xy[1];
		xy[1] = swap;

		swap = xy[2];
		xy[2] = xy[3];
		xy[3] = swap;

		Image img = flipHor(scale);

		return scaleImage(img, xy, draw);
	}

	private static Image flipVertForScaling(BufferedImage scale, Vector2d[] xy, Vector2d draw) {
		Vector2d swap;

		swap = xy[0];
		xy[0] = xy[2];
		xy[2] = swap;

		swap = xy[1];
		xy[1] = xy[3];
		xy[3] = swap;

		Image img = flipVert(scale);

		return scaleImage(img, xy, draw);
	}

	public static Image flipHor(Image toFlip) {
		BufferedImage flip = (BufferedImage) toFlip;

		BufferedImage img = new BufferedImage(flip.getWidth(), flip.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < img.getWidth(); i++)
			img.getGraphics().drawImage(flip.getSubimage(i, 0, 1, flip.getHeight()), img.getWidth() - i, 0, null);

		return img;
	}

	public static Image flipVert(Image toFlip) {
		BufferedImage flip = (BufferedImage) toFlip;

		BufferedImage img = new BufferedImage(flip.getWidth(), flip.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < img.getHeight(); i++)
			img.getGraphics().drawImage(flip.getSubimage(0, i, flip.getWidth(), 1), 0, img.getHeight() - i, null);

		return img;
	}

	public static Image addImage(Image fromadd, Image toadd) {
		BufferedImage fromAdd = (BufferedImage) fromadd;
		BufferedImage toAdd = (BufferedImage) toadd;

		BufferedImage img = new BufferedImage(Math.max(fromAdd.getWidth(), toAdd.getWidth()),
				Math.max(fromAdd.getHeight(), toAdd.getHeight()), BufferedImage.TYPE_INT_ARGB);

		fromAdd = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		fromAdd.getGraphics().drawImage(fromadd, 0, 0, null);
		toAdd = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		toAdd.getGraphics().drawImage(toadd, 0, 0, null);

		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1 = new Color(fromAdd.getRGB(i, j), true), c2 = new Color(toAdd.getRGB(i, j), true);
				int r = (int) (((double) (c1.getRed() * c1.getAlpha() + c2.getRed() * c2.getAlpha()))
						/ ((double) (c1.getAlpha() + c2.getAlpha())));
				int g = (int) (((double) (c1.getGreen() * c1.getAlpha() + c2.getGreen() * c2.getAlpha()))
						/ ((double) (c1.getAlpha() + c2.getAlpha())));
				int b = (int) (((double) (c1.getBlue() * c1.getAlpha() + c2.getBlue() * c2.getAlpha()))
						/ ((double) (c1.getAlpha() + c2.getAlpha())));
				int a = c1.getAlpha() > c2.getAlpha() ? c1.getAlpha() : c2.getAlpha();

				int pix = (a << 24) | (r << 16) | (g << 8) | b;
				img.setRGB(i, j, pix);
			}

		return img;
	}

	public static Image addImageAccordingtoTranspiracy(Image fromadd, Image toadd) {
		BufferedImage fromAdd = (BufferedImage) fromadd;
		BufferedImage toAdd = (BufferedImage) toadd;

		BufferedImage img = new BufferedImage(Math.max(fromAdd.getWidth(), toAdd.getWidth()),
				Math.max(fromAdd.getHeight(), toAdd.getHeight()), BufferedImage.TYPE_INT_ARGB);

		fromAdd = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		fromAdd.getGraphics().drawImage(fromadd, 0, 0, null);
		toAdd = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		toAdd.getGraphics().drawImage(toadd, 0, 0, null);

		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				Color c1 = new Color(fromAdd.getRGB(i, j), true), c2 = new Color(toAdd.getRGB(i, j), true);

				Color c = c1.getAlpha() >= c2.getAlpha() ? c1 : c2;
				int pix = c.getRGB();
				img.setRGB(i, j, pix);
			}

		return img;
	}

	public static Image cropImage(Image toCrop, Vertex[] v) {
		return cropImage(toCrop, v, null);
	}

	public static Image cropImage(Image toCrop, Vector2d[] v) {
		return cropImage(toCrop, v, null);
	}

	public static Image cropImage(Image toCrop, double[] x, double[] y) {
		return cropImage(toCrop, x, y, null);
	}

	public static Image cropImage(Image toCrop, Polygon p) {
		return cropImage(toCrop, p, null);
	}

	public static Image cropImage(Image toCrop, Vertex[] v, Vector2d draw) {
		Vector2d[] drawCoord = new Vector2d[v.length];
		for (int i = 0; i < v.length; i++)
			drawCoord[i] = v[i].getDrawCoord();
		return cropImage(toCrop, drawCoord, draw);
	}

	public static Image cropImage(Image toCrop, Vector2d[] v, Vector2d draw) {
		double[] x = new double[v.length], y = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			x[i] = v[i].getX();
			y[i] = v[i].getY();
		}
		return cropImage(toCrop, x, y, draw);
	}

	public static Image cropImage(Image toCrop, double[] x, double[] y, Vector2d draw) {
		Polygon p = new Polygon();
		for (int i = 0; i < x.length; i++)
			p.addPoint((int) x[i], (int) y[i]);
		return cropImage(toCrop, p, draw);
	}

	public static Image cropImage(Image toCrop, Polygon p, Vector2d draw) {
		BufferedImage crop = (BufferedImage) toCrop;

		int left = 0, right = 0, top = 0, bottom = 0;
		for (int i = 1; i < p.npoints; i++) {
			left = p.xpoints[left] <= p.xpoints[i] ? left : i;
			right = p.xpoints[right] >= p.xpoints[i] ? right : i;
			top = p.ypoints[top] <= p.ypoints[i] ? top : i;
			bottom = p.ypoints[bottom] >= p.ypoints[i] ? bottom : i;
		}
		double[] x = new double[p.npoints], y = new double[p.npoints];
		Polygon P = new Polygon();
		for (int i = 0; i < p.npoints; i++) {
			x[i] = p.xpoints[i] - p.xpoints[left];
			y[i] = p.ypoints[i] - p.ypoints[top];
			P.addPoint((int) x[i], (int) y[i]);
		}
		int DrawX = p.xpoints[left], DrawY = p.ypoints[top];

		if (draw != null)
			draw.set(DrawX, DrawY);

		if (x[right] <= 0 || y[bottom] <= 0)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		BufferedImage img2 = crop.getSubimage(p.xpoints[left], p.ypoints[top], (int) x[right], (int) y[bottom]);
		BufferedImage img = new BufferedImage(img2.getWidth(), img2.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				if (!P.contains(i, j))
					continue;
				Color c = new Color(img2.getRGB(i, j), true);
				int pix = (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
				img.setRGB(i, j, pix);
			}

		return img;
	}

	public static Image cropImageAccordingtoTranspiracy(Image toCrop, Vertex[] v) {
		Vector2d[] drawCoord = new Vector2d[v.length];
		for (int i = 0; i < v.length; i++)
			drawCoord[i] = v[i].getDrawCoord();
		return cropImageAccordingtoTranspiracy(toCrop, drawCoord);
	}

	public static Image cropImageAccordingtoTranspiracy(Image toCrop, Vector2d[] v) {
		double[] x = new double[v.length], y = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			x[i] = v[i].getX();
			y[i] = v[i].getY();
		}
		return cropImageAccordingtoTranspiracy(toCrop, x, y);
	}

	public static Image cropImageAccordingtoTranspiracy(Image toCrop, double[] x, double[] y) {
		Polygon p = new Polygon();
		for (int i = 0; i < x.length; i++)
			p.addPoint((int) x[i], (int) y[i]);
		return cropImageAccordingtoTranspiracy(toCrop, p);
	}

	public static Image cropImageAccordingtoTranspiracy(Image toCrop, Polygon p) {
		Vector2d draw = new Vector2d(0, 0);

		BufferedImage crop = (BufferedImage) cropImage(toCrop, p, draw);

		BufferedImage img = new BufferedImage(((BufferedImage) toCrop).getWidth(), ((BufferedImage) toCrop).getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		img.getGraphics().drawImage(crop, (int) draw.getX(), (int) draw.getY(), null);

		return img;
	}

	public static Image removeTranspiracy(Image toRemove) {
		return removeTranspiracy(toRemove, null);

	}

	public static Image removeTranspiracy(Image toRemove, int alphaValue) {
		return removeTranspiracy(toRemove, alphaValue, null);
	}

	public static Image removeTranspiracy(Image toRemove, Vector2d draw) {
		return removeTranspiracy(toRemove, 0, draw);
	}

	public static Image removeTranspiracy(Image toRemove, int alphaValue, Vector2d draw) {
		BufferedImage remove = (BufferedImage) toRemove;

		alphaValue = Math.max(0, alphaValue);
		alphaValue = Math.min(alphaValue, 255);

		int left = remove.getWidth() - 1, right = 0, top = remove.getHeight() - 1, bottom = 0;

		for (int j = 0; j < remove.getHeight(); j++) {
			for (int i = 0; i < remove.getWidth(); i++) {
				if (new Color(remove.getRGB(i, j), true).getAlpha() <= alphaValue)
					continue;
				left = left < i ? left : i;
				break;
			}
		}

		for (int i = 0; i < remove.getWidth(); i++) {
			for (int j = 0; j < remove.getHeight(); j++) {
				if (new Color(remove.getRGB(i, j), true).getAlpha() <= alphaValue)
					continue;
				top = top < j ? top : j;
				break;
			}
		}

		for (int j = remove.getHeight() - 1; j >= 0; j--) {
			for (int i = remove.getWidth() - 1; i >= 0; i--) {
				if (new Color(remove.getRGB(i, j), true).getAlpha() <= alphaValue)
					continue;
				right = right > i ? right : i;
				break;
			}
		}

		for (int i = remove.getWidth() - 1; i >= 0; i--) {
			for (int j = remove.getHeight() - 1; j >= 0; j--) {
				if (new Color(remove.getRGB(i, j), true).getAlpha() <= alphaValue)
					continue;
				bottom = bottom > j ? bottom : j;
				break;
			}
		}

		if (draw != null)
			draw.set(left, top);

		return remove.getSubimage(left, top, Math.max(right - left, 1), Math.max(bottom - top, 1));
	}

	public static Image scaleTriangle(Image toScale, Polygon fromTri, Polygon toTri) {
		return scaleTriangle(toScale, fromTri, toTri, null);
	}

	public static Image scaleTriangle(Image toScale, Vertex[] fromTri, Vertex[] toTri) {
		return scaleTriangle(toScale, fromTri, toTri, null);
	}

	public static Image scaleTriangle(Image toScale, double[] x, double[] y, double[] x2, double[] y2) {
		return scaleTriangle(toScale, x, y, x2, y2, null);
	}

	public static Image scaleTriangle(Image toScale, Vector2d[] fromTri, Vector2d[] toTri) {
		return scaleTriangle(toScale, fromTri, toTri, null);
	}

	public static Image scaleTriangle(Image toScale, Polygon fromTri, Polygon toTri, Vector2d draw) {
		double[] xpoints, ypoints, xpoints2, ypoints2;

		xpoints = new double[fromTri.npoints];
		for (int i = 0; i < xpoints.length; i++)
			xpoints[i] = fromTri.xpoints[i];

		ypoints = new double[fromTri.npoints];
		for (int i = 0; i < ypoints.length; i++)
			ypoints[i] = fromTri.ypoints[i];

		xpoints2 = new double[toTri.npoints];
		for (int i = 0; i < xpoints2.length; i++)
			xpoints2[i] = toTri.xpoints[i];

		ypoints2 = new double[toTri.npoints];
		for (int i = 0; i < ypoints2.length; i++)
			ypoints2[i] = toTri.ypoints[i];

		return scaleTriangle(toScale, xpoints, ypoints, xpoints2, ypoints2, draw);
	}

	public static Image scaleTriangle(Image toScale, Vertex[] fromTri, Vertex[] toTri, Vector2d draw) {
		Vector2d[] texCoord1 = new Vector2d[fromTri.length];
		for (int i = 0; i < fromTri.length; i++)
			texCoord1[i] = fromTri[i].getTexCoord();

		Vector2d[] texCoord2 = new Vector2d[toTri.length];
		for (int i = 0; i < toTri.length; i++)
			texCoord2[i] = toTri[i].getDrawCoord();

		return scaleTriangle(toScale, texCoord1, texCoord2, draw);
	}

	public static Image scaleTriangle(Image toScale, double[] x, double[] y, double[] x2, double[] y2, Vector2d draw) {
		Vector2d[] xy = new Vector2d[x.length];
		for (int i = 0; i < xy.length; i++)
			xy[i] = new Vector2d(x[i], y[i]);

		Vector2d[] xy2 = new Vector2d[x2.length];
		for (int i = 0; i < xy2.length; i++)
			xy2[i] = new Vector2d(x2[i], y2[i]);

		return scaleTriangle(toScale, xy, xy2, draw);
	}

	public static Image scaleTriangle(Image toScale, Vector2d[] fromTri, Vector2d[] toTri, Vector2d draw) {
		if (fromTri.length < 3 || toTri.length < 3)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		fromTri = new Vector2d[] { fromTri[0], fromTri[1], fromTri[2] };
		toTri = new Vector2d[] { toTri[0], toTri[1], toTri[2] };

		BufferedImage img = (BufferedImage) cropImage(toScale, fromTri);

		int left = 0, right = 0, top = 0, bottom = 0;
		for (int i = 1; i < 3; i++) {
			left = fromTri[left].getX() <= fromTri[i].getX() ? left : i;
			right = fromTri[right].getX() >= fromTri[i].getX() ? right : i;
			top = fromTri[top].getY() <= fromTri[i].getY() ? top : i;
			bottom = fromTri[bottom].getY() >= fromTri[i].getY() ? bottom : i;
		}

		Vector2d[] swap = {
				new Vector2d(fromTri[0].getX() - fromTri[left].getX(), fromTri[0].getY() - fromTri[top].getY()),
				new Vector2d(fromTri[1].getX() - fromTri[left].getX(), fromTri[1].getY() - fromTri[top].getY()),
				new Vector2d(fromTri[2].getX() - fromTri[left].getX(), fromTri[2].getY() - fromTri[top].getY()) };
		fromTri = swap;

		Vector2d a = fromTri[1].sub(fromTri[0]), b = fromTri[2].sub(fromTri[1]), c = fromTri[2].sub(fromTri[0]);

		BufferedImage ret = new BufferedImage((int) Math.max(1, a.length()), (int) Math.max(1, b.length()),
				BufferedImage.TYPE_INT_ARGB);

		Polygon P = new Polygon();
		P.addPoint(0, 0);
		P.addPoint(0, ret.getHeight());
		P.addPoint(ret.getWidth(), ret.getHeight());

		Vector2d slant = new Vector2d(ret.getWidth(), ret.getHeight());// Vector2d e=new Vector2d(a.length(),
																		// b.length());

		for (int i = 0; i < ret.getWidth(); i++)
			for (int j = 0; j < ret.getHeight(); j++) {
				if (!P.contains(i, j))
					continue;

				double ratio1 = (double) j / (double) ret.getHeight();
				double ratio2 = (double) i / (slant.normalized().mul(slant.length() * ratio1).getX());

				Vector2d m = fromTri[0].add(a.normalized().mul(a.length() * ratio1)),
						n = fromTri[0].add(c.normalized().mul(c.length() * ratio1)), o = n.sub(m),
						p = m.add(o.normalized().mul(o.length() * ratio2));

				ret.setRGB(i, j, img.getRGB((int) p.getX(), (int) p.getY()));
			}

		// if (true)return ret;

		Vector2d[] change = new Vector2d[4];

		change[0] = toTri[0];
		change[1] = toTri[0].add(toTri[2].sub(toTri[1]));
		change[2] = toTri[1];
		change[3] = toTri[2];

		return scaleImage(ret, change, draw);
	}

	public static Image scalePolygon(DPolygon poly) {
		Vertex[] v = poly.getVertex();
		Vector2d[] texCoord = new Vector2d[v.length];
		for (int i = 0; i < v.length; i++)
			texCoord[i] = v[i].getTexCoord();

		return scalePolygon(poly.getSavedImage(), texCoord, poly.getPolyTexCoord());
	}// returns polyImage.

	public static Image scalePolygon(Image toScale, Polygon fromTri, Polygon toTri) {
		return scalePolygon(toScale, fromTri, toTri, null);
	}

	public static Image scalePolygon(Image toScale, Vertex[] fromTri, Vertex[] toTri) {
		return scalePolygon(toScale, fromTri, toTri, null);
	}

	public static Image scalePolygon(Image toScale, double[] x, double[] y, double[] x2, double[] y2) {
		return scalePolygon(toScale, x, y, x2, y2, null);
	}

	public static Image scalePolygon(Image toScale, Vector2d[] fromTri, Vector2d[] toTri) {
		return scalePolygon(toScale, fromTri, toTri, null);
	}

	public static Image scalePolygon(Image toScale, Polygon fromTri, Polygon toTri, Vector2d draw) {
		double[] xpoints, ypoints, xpoints2, ypoints2;

		xpoints = new double[fromTri.npoints];
		for (int i = 0; i < xpoints.length; i++)
			xpoints[i] = fromTri.xpoints[i];

		ypoints = new double[fromTri.npoints];
		for (int i = 0; i < ypoints.length; i++)
			ypoints[i] = fromTri.ypoints[i];

		xpoints2 = new double[toTri.npoints];
		for (int i = 0; i < xpoints2.length; i++)
			xpoints2[i] = toTri.xpoints[i];

		ypoints2 = new double[toTri.npoints];
		for (int i = 0; i < ypoints2.length; i++)
			ypoints2[i] = toTri.ypoints[i];

		return scalePolygon(toScale, xpoints, ypoints, xpoints2, ypoints2, draw);
	}

	public static Image scalePolygon(Image toScale, Vertex[] fromTri, Vertex[] toTri, Vector2d draw) {
		Vector2d[] texCoord1 = new Vector2d[fromTri.length];
		for (int i = 0; i < fromTri.length; i++)
			texCoord1[i] = fromTri[i].getTexCoord();

		Vector2d[] texCoord2 = new Vector2d[toTri.length];
		for (int i = 0; i < toTri.length; i++)
			texCoord2[i] = toTri[i].getDrawCoord();

		return scalePolygon(toScale, texCoord1, texCoord2, draw);
	}

	public static Image scalePolygon(Image toScale, double[] x, double[] y, double[] x2, double[] y2, Vector2d draw) {
		Vector2d[] xy = new Vector2d[x.length];
		for (int i = 0; i < xy.length; i++)
			xy[i] = new Vector2d(x[i], y[i]);

		Vector2d[] xy2 = new Vector2d[x2.length];
		for (int i = 0; i < xy2.length; i++)
			xy2[i] = new Vector2d(x2[i], y2[i]);

		return scalePolygon(toScale, xy, xy2, draw);
	}

	public static Image scalePolygon(Image toScale, Vector2d[] fromPoly, Vector2d[] toPoly, Vector2d draw) {
		if (fromPoly.length < 3 || toPoly.length < 3)
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

		Polygon P = new Polygon();
		int left = 0, right = 0, top = 0, bottom = 0;
		for (int i = 0; i < toPoly.length; i++) {
			P.addPoint((int) toPoly[i].getX(), (int) toPoly[i].getY());
			left = toPoly[left].getX() <= toPoly[i].getX() ? left : i;
			right = toPoly[right].getX() >= toPoly[i].getX() ? right : i;
			top = toPoly[top].getY() <= toPoly[i].getY() ? top : i;
			bottom = toPoly[bottom].getY() >= toPoly[i].getY() ? bottom : i;
		}

		double lefty = toPoly[left].getX(), topy = toPoly[top].getY();

		if (draw != null)
			draw.set(lefty, topy);

		Vector2d[] swap = new Vector2d[toPoly.length];

		for (int i = 0; i < toPoly.length; i++) {
			swap[i] = new Vector2d(toPoly[i].getX() - lefty, toPoly[i].getY() - topy);

			P.addPoint((int) swap[i].getX(), (int) swap[i].getY());
		}
		toPoly = swap;

		BufferedImage ret = new BufferedImage((int) toPoly[right].getX(), (int) toPoly[bottom].getY(),
				BufferedImage.TYPE_INT_ARGB);

		for (int i = toPoly.length - 1; i >= 2; i--) {
			int a = (i == toPoly.length - 1) ? 0 : 1, b = (i == toPoly.length - 1) ? 1 : (i + 1), c = i;
			Vector2d drawit = new Vector2d(0, 0);

			BufferedImage img = new BufferedImage((int) toPoly[right].getX(), (int) toPoly[bottom].getY(),
					BufferedImage.TYPE_INT_ARGB);
			img.getGraphics().drawImage(
					scaleTriangle(toScale, new Vector2d[] { fromPoly[a], fromPoly[b], fromPoly[c] },
							new Vector2d[] { toPoly[a], toPoly[b], toPoly[c] }, drawit),
					(int) drawit.getX(), (int) drawit.getY(), null);

			ret = (BufferedImage) addImage(ret, img);
		}

		for (int i = 0; i < ret.getWidth(); i++)
			for (int j = 0; j < ret.getHeight(); j++)
				if (!P.contains(i, j))
					ret.setRGB(i, j, ((0 << 24) | (0 << 16) | (0 << 8) | 0));

		return ret;
	}

}