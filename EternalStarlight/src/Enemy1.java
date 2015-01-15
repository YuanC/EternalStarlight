import java.awt.Graphics2D;

import javax.swing.ImageIcon;

//The first type of enemy, follows the player
public class Enemy1 extends Enemy {
	double x, y, speed, angle, health, progress;
	int q, r;

	public Enemy1(int q, int r, int x, int y) {
		this.q = q;
		this.r = r;
		this.x = x;
		this.y = y;
		this.speed = 30;
		health = 5;
		angle = 0;

	}

	public void update(double delta, Hextile[][] hextiles) {
		this.speed = 25;
		angle = 0;
		progress = Math.random();

	}

	// draws the slimes
	public void draw(Graphics2D g, ImageIcon[] slime) {
		if (angle < Math.PI * 3 / 2 && angle > Math.PI / 2)
			g.drawImage(slime[0].getImage(), (int) x - 20, (int) y - 22, 40,
					25, null);
		else
			g.drawImage(slime[1].getImage(), (int) x - 20, (int) y - 22, 40,
					25, null);
		g.drawLine((int) x - 25, (int) y+5, (int) (x - 25 + 50 * health / 5),
				(int) y+5);
	}

	public void update(double delta, Hextile[][] hextiles, int px, int py) {
		angle = Math.atan2(py - y, px - x);
		progress = (progress + delta * 3) % 1;

		x = x + Math.cos(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		y = y + Math.sin(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));

		updateTilePos(hextiles);
	}

	public void addDamage(int dmg) {
		health -= dmg;
	}

	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

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

}
