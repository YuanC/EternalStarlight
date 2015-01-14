<<<<<<< HEAD
public class Enemy1 extends Enemy {
	double x, y, speed, angle, health;
=======
import java.awt.Graphics2D;

public class Enemy1 extends Enemy {
	double x, y, speed, angle, health, progress;
>>>>>>> origin/player-ablities
	int q, r;

	public Enemy1(int q, int r, int x, int y) {
		this.q = q;
		this.r = r;
		this.x = x;
		this.y = y;
<<<<<<< HEAD
		this.speed = 65;
		angle = 0;

	}

	public void update(double delta, Hextile[][] hextiles) {
=======
		this.speed = 25;
		angle = 0;
		progress = Math.random();
		health = 1;
	}

	public void draw(Graphics2D g) {
		g.drawString(1 + "", (int) x, (int) y);
	}

	public void update(double delta, Hextile[][] hextiles, int px, int py) {
		angle = Math.atan2(py - y, px - x);
		progress = (progress + delta * 3) % 1;

>>>>>>> origin/player-ablities
		x = x + Math.cos(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		y = y + Math.sin(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));

		updateTilePos(hextiles);
	}

<<<<<<< HEAD
=======
	public void addDamage(int dmg) {
		health -= dmg;
	}

>>>>>>> origin/player-ablities
	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

<<<<<<< HEAD
=======
	public int getHP() {
		return (int) health;
	}

	public int[] getQR() {
		int[] QR = { q, r };

		return QR;
	}

	public int getY() {
		return (int) y;
	}

	public int getX() {
		return (int) x;
	}

	public double getProgress() {
		return progress;
	}

>>>>>>> origin/player-ablities
}
