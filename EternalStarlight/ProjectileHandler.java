import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//manages all the projectiles and projectile arraylists
public class ProjectileHandler {
	private static ArrayList<Projectile> playerShots, enemyShots;
	private double playerShotSpeed, enemyShotSpeed;
	private static ImageIcon fire;

	public ProjectileHandler() {
		playerShots = new ArrayList<Projectile>();
		enemyShots = new ArrayList<Projectile>();
		playerShotSpeed = 140;
		enemyShotSpeed = 140;
		fire = new ImageIcon("lvlImages/fire.gif");

	}

	// updates all the projectiles
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
					.get(i).getY())
					|| enemyShots.get(i).getLifetime() > 1) {
				enemyShots.remove(i--);
			}
		}
	}

	// Adds projectiles
	public static void addPlayerShot(int x, int y, double theta) {
		playerShots.add(new Projectile(600, x, y, theta));
	}

	public static void addEnemyShot(int x, int y, double theta) {
		enemyShots.add(new Projectile(50, x, y, theta));
	}

	public static void addEnemySpell(int q, int r) {
		if (q + Hextile.size / 2 < 21
				&& q + Hextile.size / 2 >= 0
				&& r + Hextile.size / 2 < 21
				&& r + Hextile.size / 2 >= 0
				&& GamePanel.getHextiles()[q + Hextile.size / 2][r
						+ Hextile.size / 2] != null) {
			enemyShots.add(new Projectile(0, GamePanel.getHextiles()[q
					+ Hextile.size / 2][r + Hextile.size / 2].getX(), GamePanel
					.getHextiles()[q + Hextile.size / 2][r + Hextile.size / 2]
					.getY(), 0));
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < playerShots.size(); i++) {
			playerShots.get(i).drawP(g);
		}
		for (int i = 0; i < enemyShots.size(); i++)
			enemyShots.get(i).drawE(g, fire);
	}

	public static int[][] getEShots() {
		int[][] intArray = new int[enemyShots.size()][4];
		for (int j = 0; j < enemyShots.size(); j++) {
			intArray[j][0] = (int) enemyShots.get(j).getQ();
			intArray[j][1] = (int) enemyShots.get(j).getR();

		}
		return intArray;
	}

	// Returns a list of the location of player projectiles
	public static int[][] getPShots() {

		int[][] intArray = new int[playerShots.size()][4];
		for (int j = 0; j < playerShots.size(); j++) {
			intArray[j][0] = (int) playerShots.get(j).getQ();
			intArray[j][1] = (int) playerShots.get(j).getR();

		}
		return intArray;
	}
}
