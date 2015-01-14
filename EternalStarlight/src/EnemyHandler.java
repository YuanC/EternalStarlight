import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.UIManager;

public class EnemyHandler {
	private static ArrayList<Enemy1> enemy1List;
	private static ArrayList<Enemy2> enemy2List;
	private static ArrayList<Enemy3> enemy3List;
	private static ArrayList<double[]> deathList;
	private static int waveCnt, difficulty;
	private static Hextile[][] hextiles;
	double spawnCD, spawnTimer;

	public EnemyHandler(int difficulty, Hextile[][] hextiles) {
		enemy1List = new ArrayList<Enemy1>();
		enemy2List = new ArrayList<Enemy2>();
		enemy3List = new ArrayList<Enemy3>();
		deathList = new ArrayList<double[]>();
		waveCnt = 50;// difficulty * 2;
		spawnCD = 5;
		this.difficulty = difficulty;
		this.hextiles = hextiles;
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
			deathList.get(i)[0] += delta * 3;
			if (deathList.get(i)[0] > 1) {
				deathList.remove(i);
				i--;
			}
		}

		int[][] qList = ProjectileHandler.getPShots(), wList = PlayerSpells
				.getList(1), eList = PlayerSpells.getList(2), rList = PlayerSpells
				.getList(3);

		// Updates the game for all the enemies
		for (int i = 0; i < enemy1List.size(); i++) {
			enemy1List.get(i).update(delta, hextiles, Battle_Player.getX(),
					Battle_Player.getY());
			enemy1List.get(i).addDamage(
					colCheck(enemy1List.get(i).getQR(), qList, wList, eList,
							rList));
			if (enemy1List.get(i).getHP() <= 0) {
				addDeath(enemy1List.get(i).getX(), enemy1List.get(i).getY());
				enemy1List.remove(i);
				i--;
			}

		}

		for (int i = 0; i < enemy2List.size(); i++) {
			enemy2List.get(i).update(delta, hextiles, Battle_Player.getX(),
					Battle_Player.getY());
			enemy2List.get(i).addDamage(
					colCheck(enemy2List.get(i).getQR(), qList, wList, eList,
							rList));
			if (enemy2List.get(i).getHP() <= 0) {
				addDeath(enemy2List.get(i).getX(), enemy2List.get(i).getY());
				enemy2List.remove(i);
				i--;
			}
		}

		for (int i = 0; i < enemy3List.size(); i++) {

		}
	}

	public void removeInHex(int[] qr) {
		for (int i = 0; i < enemy1List.size(); i++) {
			if (enemy1List.get(i).getQR()[0] == qr[0]
					&& enemy1List.get(i).getQR()[1] == qr[1]) {
				addDeath(enemy1List.get(i).getX(), enemy1List.get(i).getY());
				enemy1List.remove(i);
				i--;
			}

		}

		for (int i = 0; i < enemy2List.size(); i++) {
			/*
			 * if (enemy2List.get(i).getQR()[0] == qr[0] &&
			 * enemy2List.get(i).getQR()[1] == qr[1]) {
			 * addDeath(enemy2List.get(i).getX(), enemy2List.get(i).getY());
			 * enemy2List.remove(i); i--; }
			 */
		}

		for (int i = 0; i < enemy3List.size(); i++) {
			/*
			 * if (enemy3List.get(i).getQR()[0] == qr[0] &&
			 * enemy3List.get(i).getQR()[1] == qr[1]) {
			 * addDeath(enemy3List.get(i).getX(), enemy3List.get(i).getY());
			 * enemy3List.remove(i); i--; }
			 */
		}
	}

	private int colCheck(int[] qr, int[][] qList, int[][] wList, int[][] eList,
			int[][] rList) {

		int dmg = 0;

		for (int i = 0; i < qList.length; i++) {
			if (qList[i][0] == qr[0] && qList[i][1] == qr[1])
				dmg += PlayerSpells.getDmg(0);
		}
		for (int i = 0; i < wList.length; i++) {
			if (wList[i][0] == qr[0] && wList[i][1] == qr[1])
				dmg += PlayerSpells.getDmg(1);
		}
		for (int i = 0; i < eList.length; i++) {
			if (eList[i][0] == qr[0] && eList[i][1] == qr[1])
				dmg += PlayerSpells.getDmg(2);
		}
		for (int i = 0; i < rList.length; i++) {
			if (rList[i][0] == qr[0] && rList[i][1] == qr[1])
				dmg += PlayerSpells.getDmg(3);
		}
		return dmg;

	}

	// where i is the type of spawning pattern
	public static void addEnemy(int q, int r, int type) {

		if (type == 1) {
			enemy1List.add(new Enemy1(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		} else if (type == 2) {
			enemy2List.add(new Enemy2(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		} else if (type == 3) {
			enemy3List.add(new Enemy3(q, r,
					(int) hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
							.getX(), (int) hextiles[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY()));
		}

	}

	// public static void
	public static void addDeath(int x, int y) {

		double[] arr = { x, y, 0 };
		deathList.add(arr);
	}

	public void draw(Graphics2D g) {
		// Updates the game for all the enemies
		for (int i = 0; i < enemy1List.size(); i++) {
			enemy1List.get(i).draw(g);
		}

		for (int i = 0; i < enemy2List.size(); i++) {
			enemy2List.get(i).draw(g);
		}

		for (int i = 0; i < enemy3List.size(); i++) {
			// enemy3List.get(i).draw(g);
		}

		drawDeaths(g);
		drawCnt(g);
	}

	public void drawCnt(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString(waveCnt + " Wave(s)", 600, 50);
		g.setFont(UIManager.getDefaults().getFont("TabbedPane.font"));
	}

	public void drawDeaths(Graphics2D g) {

		double width = 14;
		double height = 7;

		for (int i = 0; i < deathList.size(); i++) {
			g.drawOval((int) deathList.get(i)[0], (int) deathList.get(i)[1],
					(int) (width * deathList.get(i)[2]),
					(int) (height * deathList.get(i)[2]));
		}
	}

	public static void getList(int i) {

	}

}
