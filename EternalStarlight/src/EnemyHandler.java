import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyHandler {
	private static ArrayList<Enemy> enemyList;
	private static ArrayList<double[]> deathList;
	private static int waveCnt, difficulty;
	double spawnCD, spawnTimer;

	public EnemyHandler(int difficulty) {
		enemyList = new ArrayList<Enemy>();
		deathList = new ArrayList<double[]>();
		waveCnt =50;// difficulty * 2;
		spawnCD = 0.5;
		this.difficulty = difficulty;
	}

	public void drawCnt(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString(waveCnt + " Wave(s)", 600, 50);
	}

	public void update(double delta) {

		if (spawnTimer <= 0) {
			if (waveCnt > 0) {
				spawnTimer = spawnCD;
				waveCnt--;
				if (difficulty == 1) {
					SpawnAndCast.addEnemies((int) (Math.random() * 2 * +1));
				} else if (difficulty == 2) {
					SpawnAndCast.addEnemies((int) (Math.random() * 3 * +1));
				} else {
					SpawnAndCast.addEnemies((int) (Math.random() * 3 * +2));
				}
			}
		} else if (waveCnt > 0) {
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

	// public static void

	public static void addDeath(int q, int r) {

		double[] arr = { 0, q, r };
		deathList.add(arr);
	}
}
