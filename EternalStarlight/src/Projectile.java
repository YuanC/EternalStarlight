import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;

//a projectile class for all player and enemy projectiles in the game
public class Projectile {
	private double x, y, angle, speed, lifetime;
	private int q, r;

	public Projectile(double speed, double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.angle = angle;
		lifetime = 0;
	}

	public void update(double delta, Hextile[][] hextiles) {
		if (speed > 0) {

			x = x + Math.cos(angle) * speed * delta
					* (1 - 0.5 * Math.abs(Math.sin(angle)));
			y = y + Math.sin(angle) * speed * delta
					* (1 - 0.5 * Math.abs(Math.sin(angle)));

			updateTilePos(hextiles);
		} else
			lifetime += delta;
	}

	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

	// draws an enemy projectile
	public void drawE(Graphics2D g, ImageIcon fire) {
		if (speed > 0) {
			g.setColor(Color.white);
			g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
					(int) (y + Math.sin(angle) * 10));

			g.setStroke(new BasicStroke(2));
			g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 6),
					(int) (y + Math.sin(angle) * 6));

			g.setStroke(new BasicStroke(4));
			g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 2),
					(int) (y + Math.sin(angle) * 2));
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(1));
		} else {
			g.drawImage(fire.getImage(), (int) x - 25, (int) y - 40, null);
		}
	}

	// draws a player projectile
	public void drawP(Graphics2D g) {

		g.setColor(Color.cyan);
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
				(int) (y + Math.sin(angle) * 10));

		g.setStroke(new BasicStroke(2));
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 6),
				(int) (y + Math.sin(angle) * 6));

		g.setStroke(new BasicStroke(4));
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 2),
				(int) (y + Math.sin(angle) * 2));
		g.setColor(Color.white);
		g.setStroke(new BasicStroke(1));

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

	public double getLifetime() {
		return lifetime;
	}
}
