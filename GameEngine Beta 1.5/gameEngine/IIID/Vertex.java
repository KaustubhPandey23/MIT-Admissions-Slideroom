package gameEngine.IIID;

import java.awt.Color;

public class Vertex {

	private Vector3d pos = new Vector3d(0, 0, 0);
	private Vector2d drawCoord = new Vector2d(0, 0);
	private Vector2d texCoord = new Vector2d(0, 0);
	private Vector3d normal = new Vector3d(0, 0, 0);
	private Vector3d tangent = new Vector3d(0, 0, 0);
	private Color color;
	private boolean draw;

	public Vertex(Vertex v) {
		set(v);
	}

	public Vertex(Vector3d pos) {
		set(pos, new Vector2d(0, 0));
	}

	public Vertex(Vector3d pos, Vector2d drawCoord) {
		set(pos, drawCoord, new Vector2d(0, 0));
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord) {
		set(pos, drawCoord, texCoord, new Vector3d(0, 0, 0));
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal) {
		set(pos, drawCoord, texCoord, normal, new Vector3d(0, 0, 0));
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Vector3d tangent) {
		set(pos, drawCoord, texCoord, normal, tangent, new Color(0, 0, 0, 0));
	}

	public Vertex(Vector3d pos, Color c) {
		set(pos, new Vector2d(0, 0), c);
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Color c) {
		set(pos, drawCoord, new Vector2d(0, 0), c);
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Color c) {
		set(pos, drawCoord, texCoord, new Vector3d(0, 0, 0), c);
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Color c) {
		set(pos, drawCoord, texCoord, normal, new Vector3d(0, 0, 0), c);
	}

	public Vertex(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Vector3d tangent, Color c) {
		set(pos, drawCoord, texCoord, normal, tangent, c);
	}

	public Vertex get() {
		return new Vertex(getPos(), getDrawCoord(), getTexCoord(), getNormal(), getTangent(), getColor());
	}

	public Vertex set(Vertex v) {
		return set(v.getPos(), v.getDrawCoord(), v.getTexCoord(), v.getNormal(), v.getTangent(), v.getColor());
	}

	public Vertex set(Vector3d pos) {
		return set(pos, new Vector2d(0, 0));
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord) {
		return set(pos, drawCoord, new Vector2d(0, 0));
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord) {
		return set(pos, drawCoord, texCoord, new Vector3d(0, 0, 0));
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal) {
		return set(pos, drawCoord, texCoord, normal, new Vector3d(0, 0, 0));
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Vector3d tangent) {
		return set(pos, drawCoord, texCoord, normal, tangent, new Color(0, 0, 0, 0));
	}

	public Vertex set(Vector3d pos, Color c) {
		return set(pos, new Vector2d(0, 0), c);
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Color c) {
		return set(pos, drawCoord, new Vector2d(0, 0), c);
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Color c) {
		return set(pos, drawCoord, texCoord, new Vector3d(0, 0, 0), c);
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Color c) {
		return set(pos, drawCoord, texCoord, normal, new Vector3d(0, 0, 0), c);
	}

	public Vertex set(Vector3d pos, Vector2d drawCoord, Vector2d texCoord, Vector3d normal, Vector3d tangent, Color c) {
		setPos(pos);
		setDrawCoord(drawCoord);
		setTexCoord(texCoord);
		setNormal(normal);
		setTangent(tangent);
		setColor(c);
		this.draw = true;
		return this;
	}

	public Vector3d getPos() {
		return pos.get();
	}

	public void setPos(Vector3d pos) {
		this.pos.set(pos);
	}

	public Vector2d getDrawCoord() {
		return drawCoord.get();
	}

	public void setDrawCoord(Vector2d drawCoord) {
		this.drawCoord.set(drawCoord);
	}

	public Vector2d getTexCoord() {
		return texCoord.get();
	}

	public void setTexCoord(Vector2d texCoord) {
		this.texCoord.set(texCoord);
	}

	public Vector3d getNormal() {
		return normal.get();
	}

	public void setNormal(Vector3d normal) {
		this.normal.set(normal);
	}

	public Vector3d getTangent() {
		return tangent.get();
	}

	public void setTangent(Vector3d tangent) {
		this.tangent.set(tangent);
	}

	public Color getColor() {
		return new Color(color.getRGB());
	}

	public void setColor(Color color) {
		this.color = new Color(color.getRGB());
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}
}
