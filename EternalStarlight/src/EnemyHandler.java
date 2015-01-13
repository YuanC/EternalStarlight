import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyHandler {
	private static ArrayList<Enemy> enemyList;
	private static ArrayList<double[]> deathList;
	int enemyCnt;
	double spawnCD, spawnTimer;

	public EnemyHandler(int difficulty) {
		enemyList = new ArrayList<Enemy>();
		deathList = new ArrayList<double[]>();
		enemyCnt = difficulty * 10;
		spawnCD = 6;
	}

	public void drawCnt(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString(enemyCnt + SpawnAndCast.spawnSize() + enemyList.size()
				+ "", 620, 50);
	}

	public void update(double delta) {

		if (spawnTimer <= 0) {
			spawnTimer = spawnCD;
			enemyCnt = Math.max(enemyCnt - 5, 0);
		} else if (enemyCnt > 0) {
			spawnTimer -= delta;
		}

		for (int i = 0; i < deathList.size(); i++) {
			deathList.get(i)[0] += delta;
			if (deathList.get(i)[0] > 1) {
				deathList.remove(i);
				i--;
			}
		}

		// enemyCnt
	}

	// where i is the type of spawning pattern
	public static void addEnemy(int i) {

		if (i == 1) {
			// enemyList.add(new Enemy(spawnCD, spawnCD, spawnCD, spawnCD));
		} else if (i == 2) {

		} else if (i == 3) {

		} else if (i == 4) {

		}

	}

	public static void addDeath(int q, int r) {

		double[] arr = { 0, q, r };
		deathList.add(arr);
	}
}
