import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

//handles all of the lists of enemies and their behaviours
public class EnemyHandler {
	private static ArrayList<Enemy1> enemy1List;
	private static ArrayList<Enemy2> enemy2List;
	private static ArrayList<Enemy3> enemy3List;
	private static ArrayList<double[]> deathList;
	private static int waveCnt, difficulty;
	private static Hextile[][] hextiles;
	private double spawnCD, spawnTimer;
	private static ImageIcon[] slime, dragon;
	private static ImageIcon golem;

	public EnemyHandler(int difficulty, Hextile[][] hextiles) {
		enemy1List = new ArrayList<Enemy1>();
		enemy2List = new ArrayList<Enemy2>();
		enemy3List = new ArrayList<Enemy3>();
		deathList = new ArrayList<double[]>();
		waveCnt = 50;// difficulty * 2;
		spawnCD = 12 - difficulty;
		this.difficulty = difficulty;
		this.hextiles = hextiles;

		slime = new ImageIcon[2];
		slime[0] = new ImageIcon("lvlimages/leftSlime.gif");
		slime[1] = new ImageIcon("lvlimages/slimeRight.gif");

		golem = new ImageIcon("lvlimages/golem.png");

		dragon = new ImageIcon[2];
		dragon[0] = new ImageIcon("lvlimages/dragon.png");
		dragon[1] = new ImageIcon("lvlimages/dragonFast.gif");

	}

	// updates all the arraylists and elements
	public void update(double delta) {

		if (spawnTimer <= 0) {
			if (waveCnt > 0) {
				spawnTimer = spawnCD;
				waveCnt--;
				if (difficulty == 1) {
					SpawnAndCast.addEnemies((int) (Math.random() * 2 + 1));
				} else if (difficulty == 2) {
					SpawnAndCast.addEnemies((int) (Math.random() * 3 + 1));
				} else {
					SpawnAndCast.addEnemies((int) (Math.random() * 3 + 2));
				}
			}
		} else if (waveCnt > 0) {
			spawnTimer -= delta;
		}

		for (int i = 0; i < deathList.size(); i++) {
			deathList.get(i)[2] += delta * 2;
			if (deathList.get(i)[2] > 1) {
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
			enemy3List.get(i).update(delta, hextiles, Battle_Player.getX(),
					Battle_Player.getY());
			enemy3List.get(i).addDamage(
					colCheck(enemy3List.get(i).getQR(), qList, wList, eList,
							rList));
			if (enemy3List.get(i).getHP() <= 0) {
				addDeath(enemy3List.get(i).getX(), enemy3List.get(i).getY());
				enemy3List.remove(i);
				i--;
			}
		}
	}

	// removes all objects inside occupying the specified hexagon tile
	public static void removeInHex(int q, int r) {

		int[] qr = { q, r };

		for (int i = 0; i < enemy1List.size(); i++) {
			if (enemy1List.get(i).getQR()[0] == qr[0]
					&& enemy1List.get(i).getQR()[1] == qr[1]) {
				addDeath(enemy1List.get(i).getX(), enemy1List.get(i).getY());
				enemy1List.remove(i);
				i--;
			}

		}

		for (int i = 0; i < enemy2List.size(); i++) {
			if (enemy2List.get(i).getQR()[0] == qr[0]
					&& enemy2List.get(i).getQR()[1] == qr[1]) {
				addDeath(enemy2List.get(i).getX(), enemy2List.get(i).getY());
				enemy2List.remove(i);
				i--;
			}

		}

		/*
		 * for (int i = 0; i < enemy3List.size(); i++) { if
		 * (enemy3List.get(i).getQR()[0] == qr[0] &&
		 * enemy3List.get(i).getQR()[1] == qr[1]) {
		 * addDeath(enemy3List.get(i).getX(), enemy3List.get(i).getY());
		 * enemy3List.remove(i); i--; } }
		 */
	}

	// generates the total spell damage in a specifid tile
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

	public int[][] getDeaths() {

		int[][] intArray = new int[deathList.size()][5];
		for (int j = 0; j < deathList.size(); j++) {
			intArray[j][0] = (int) deathList.get(j)[0];
			intArray[j][1] = (int) deathList.get(j)[1];
			intArray[j][2] = (int) deathList.get(j)[2];
			intArray[j][3] = (int) deathList.get(j)[3];
			intArray[j][4] = (int) deathList.get(j)[4];

		}
		return intArray;

	}

	// draws all the enemies
	public void draw(Graphics2D g) {

		for (int i = 0; i < enemy1List.size(); i++) {
			enemy1List.get(i).draw(g, slime);
		}

		for (int i = 0; i < enemy2List.size(); i++) {
			enemy2List.get(i).draw(g, golem);
		}

		for (int i = 0; i < enemy3List.size(); i++) {
			enemy3List.get(i).draw(g, dragon);
		}

		drawDeaths(g);
		drawCnt(g);
	}

	public void drawCnt(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 60));
		g.drawString(waveCnt + "", 600, 80);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
	}

	// draws the enemy death animations
	public void drawDeaths(Graphics2D g) {

		double width = 50;
		double height = 25;

		// System.out.println(deathList.size());
		for (int i = 0; i < deathList.size(); i++) {
			g.drawOval(
					(int) deathList.get(i)[0]
							- (int) (width * deathList.get(i)[2]) / 2,
					(int) deathList.get(i)[1]
							- (int) (height * deathList.get(i)[2]) / 2,
					(int) (width * deathList.get(i)[2]),
					(int) (height * deathList.get(i)[2]));
		}
	}

	public static int[][] getList(int i) {
		int[][] intArray = null;
		if (i == 1) {
			intArray = new int[enemy1List.size()][2];
			for (int j = 0; j < enemy1List.size(); j++) {
				intArray[j][0] = (int) enemy1List.get(j).getQR()[0];
				intArray[j][1] = (int) enemy1List.get(j).getQR()[1];
			}
		} else if (i == 2) {
			intArray = new int[enemy2List.size()][2];
			for (int j = 0; j < enemy2List.size(); j++) {
				intArray[j][0] = (int) enemy2List.get(j).getQR()[0];
				intArray[j][1] = (int) enemy2List.get(j).getQR()[1];
			}
		} else {
			intArray = new int[enemy3List.size()][2];
			for (int j = 0; j < enemy3List.size(); j++) {
				intArray[j][0] = (int) enemy3List.get(j).getQR()[0];
				intArray[j][1] = (int) enemy3List.get(j).getQR()[1];
			}
		}
		return intArray;
	}

	public static int getE3Size() {
		return enemy3List.size();
	}

	public int getListCnt() {
		return enemy1List.size() + enemy2List.size() + enemy3List.size();
	}

	public int getWaveCnt() {
		return waveCnt;
	}
}
