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
		playerShots.add(new Projectile(800, x, y, theta));
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
