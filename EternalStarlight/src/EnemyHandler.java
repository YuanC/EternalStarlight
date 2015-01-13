import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyHandler {
	private static ArrayList<Enemy1> enemy1List;
	private static ArrayList<Enemy2> enemy2List;
	private static ArrayList<Enemy3> enemy3List;
	private static ArrayList<double[]> deathList;
	private static int waveCnt, difficulty;
	double spawnCD, spawnTimer;
	private static Hextile[][] hextiles;

	public EnemyHandler(int difficulty, Hextile[][] hextiles) {
		enemy1List = new ArrayList<Enemy1>();
		deathList = new ArrayList<double[]>();
		waveCnt = 50;// difficulty * 2;
		spawnCD = 1;
		this.difficulty = difficulty;
		this.hextiles = hextiles;
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
	public static void addEnemy(int q, int r, int i) {

		if (i == 1) {
			enemy1List.add(new Enemy1(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		} else if (i == 2) {
			enemy2List.add(new Enemy2(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		} else if (i == 3) {
			enemy3List.add(new Enemy3(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		}

	}

	// public static void

	public static void addDeath(int q, int r) {

		double[] arr = { 0, q, r };
		deathList.add(arr);
	}

	public int[][] getDeaths() {
		
			int[][] intArray = new int[deathList.size()][5];
			for (int j = 0; j < spawnList.size(); j++) {
				intArray[j][0] = (int) spawnList.get(j)[0];
				intArray[j][1] = (int) spawnList.get(j)[1];
				intArray[j][2] = (int) spawnList.get(j)[2];
				intArray[j][3] = (int) spawnList.get(j)[3];
				intArray[j][4] = (int) spawnList.get(j)[4];

			}
			return intArray;
		
	}
}
