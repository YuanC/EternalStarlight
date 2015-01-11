import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

public class ProjectileHandler {
	private static ArrayList<Projectile> playerShots, enemyShots;
	double playerShotSpeed, enemyShotSpeed;

	public ProjectileHandler() {
		playerShots = new ArrayList<Projectile>();
		enemyShots = new ArrayList<Projectile>();
		playerShotSpeed = 140;
		enemyShotSpeed = 140;
	}

	public void update(double delta, Polygon containHex, Hextile[][] hextiles) {

		for (int i = 0; i < playerShots.size(); i++) {
			playerShots.get(i).update(delta, hextiles);
			if (!containHex.contains(playerShots.get(i).getX(), playerShots
					.get(i).getY()))
				playerShots.remove(i--);
		}

		for (int i = 0; i < enemyShots.size(); i++) {
			enemyShots.get(i).update(delta, hextiles);
			if (!containHex.contains(enemyShots.get(i).getX(), enemyShots
					.get(i).getY()))
				enemyShots.remove(i--);
		}
	}

	public static void addPlayerShot(int x, int y, double theta) {
		playerShots.add(new Projectile(400, x, y, theta));
	}

	public static void addEnemyShot(int x, int y, double theta) {
		enemyShots.add(new Projectile(150, x, y, theta));
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < playerShots.size(); i++) {
			playerShots.get(i).drawP(g);
		}
		for (int i = 0; i < enemyShots.size(); i++)
			enemyShots.get(i).drawE(g);
	}

	public ArrayList<Projectile> getPShots() {
		return playerShots;
	}

	public ArrayList<Projectile> getEShots() {
		return enemyShots;
	}
}
