import java.awt.Graphics2D;
import javax.swing.ImageIcon;

//the dragon boss
public class Enemy3 extends Enemy {

	private double x, y, speed;
	private static double angle;
	private double theta;
	private double health;
	private double progress;
	private static int q;
	private static int r;
	private static int[][] indicator;

	// initializes the dragon
	public Enemy3(int q, int r, int x, int y) {
		this.q = q;
		this.r = r;
		this.x = x;
		this.y = y;
		speed = 20;
		angle = 0;
		health = 50;
		progress = Math.random();
	}

	// draws the dragon
	public void draw(Graphics2D g, ImageIcon[] dragon) {

		if (progress < 0.3 && progress > 0.2) {
			g.drawImage(dragon[1].getImage(), (int) x - 35, (int) y - 45, 60,
					60, null);

		} else {
			g.drawImage(dragon[0].getImage(), (int) x - 35, (int) y - 45, 60,
					60, null);
		}
		g.drawLine((int) x - 25, (int) y, (int) (x - 25 + 50 * health / 50),
				(int) y);
	}

	// updates the dragon
	public void update(double delta, Hextile[][] hextiles, int px, int py) {
		angle = Math.atan2(py - y, px - x);
		progress = (progress + delta / 20);

		if (progress > 1) {
			progress = 0;
			SpawnAndCast.addEnemies(0);
			theta = angle + Math.random() * Math.PI / 2 - Math.PI / 4;
		}

	}

	public void addDamage(int dmg) {
		health -= dmg;
	}

	public int getHP() {
		return (int) health;
	}

	public static int[] getQR() {
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

	public static double getAngle() {
		return angle;

	}

}
