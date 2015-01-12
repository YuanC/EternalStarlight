import java.awt.Graphics2D;
import java.awt.Point;

public class Projectile {
	double x, y, angle, speed;
	int q, r;

	public Projectile(double speed, double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.angle = angle;
	}

	public void update(double delta, Hextile[][] hextiles) {
		x = x + Math.cos(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		y = y + Math.sin(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));

		updateTilePos(hextiles);
	}

	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

	public void drawE(Graphics2D g) {
		g.drawOval((int) x - 10, (int) y - 5, 20, 10);
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
				(int) (y + Math.sin(angle) * 5));
	}

	public void drawP(Graphics2D g) {
		g.drawOval((int) x - 10, (int) y - 5, 20, 10);
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
				(int) (y + Math.sin(angle) * 5));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getQ() {
		return q;
	}
	public double getR() {
		return r;
	}
}
