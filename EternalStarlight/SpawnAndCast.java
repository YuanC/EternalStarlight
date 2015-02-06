import java.util.ArrayList;

//managing all the spawning and casting animations and patterns
public class SpawnAndCast {

	// each int array is [q, r, time elapsed, total cast time]
	private static ArrayList<double[]> castWList;
	private static ArrayList<double[]> castEList;
	private static ArrayList<double[]> castRList;

	// [q, r, type(int), time elapsed, total spawning time]
	private static ArrayList<double[]> spawnList;

	public SpawnAndCast() {
		castWList = new ArrayList<double[]>();
		castEList = new ArrayList<double[]>();
		castRList = new ArrayList<double[]>();
		spawnList = new ArrayList<double[]>();

	}

	// updates all the arraylist elements
	public void update(double delta) {
		for (int i = 0; i < castWList.size(); i++) {
			castWList.get(i)[2] += delta;
			if (castWList.get(i)[2] > castWList.get(i)[3]) {
				PlayerSpells.addSpell(1, (int) castWList.get(i)[0],
						(int) castWList.get(i)[1]);
				castWList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < castEList.size(); i++) {

			castEList.get(i)[2] += delta;

			if (castEList.get(i)[2] > castEList.get(i)[3]) {
				PlayerSpells.addSpell(2, (int) castEList.get(i)[0],
						(int) castEList.get(i)[1]);
				castEList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < castRList.size(); i++) {
			castRList.get(i)[2] += delta;
			if (castRList.get(i)[2] > castRList.get(i)[3]) {
				PlayerSpells.addSpell(3, (int) castRList.get(i)[0],
						(int) castRList.get(i)[1]);
				castRList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < spawnList.size(); i++) {
			spawnList.get(i)[3] += delta;
			if (spawnList.get(i)[3] > spawnList.get(i)[4]) {
				if (spawnList.get(i)[2] == 0) {
					ProjectileHandler.addEnemySpell((int) spawnList.get(i)[0],
							(int) spawnList.get(i)[1]);
				} else
					EnemyHandler.addEnemy((int) spawnList.get(i)[0],
							(int) spawnList.get(i)[1],
							(int) spawnList.get(i)[2]);
				spawnList.remove(i--);
			}
		}
	}

	// Adds casting indicator to list. I is the ability
	public static void addCast(int i, int q, int r) {
		double[] arr = new double[4];

		if (i == 1) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.7;
			castWList.add(arr);

		} else if (i == 2) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 1;
			castEList.add(arr);
		} else if (i == 3) {

			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.5;
			castRList.add(arr);
		}

	}

	// gives the arraylist of abilities as 2d int array
	public static int[][] getList(int i) {

		// System.out.println(castWList.size());
		int[][] intArray = null;
		if (i == 1) {
			intArray = new int[castWList.size()][4];
			for (int j = 0; j < castWList.size(); j++) {
				intArray[j][0] = (int) castWList.get(j)[0];
				intArray[j][1] = (int) castWList.get(j)[1];
				intArray[j][2] = (int) castWList.get(j)[2];
				intArray[j][3] = (int) castWList.get(j)[3];

			}
			return intArray;
		} else if (i == 2) {
			intArray = new int[castEList.size()][4];
			for (int j = 0; j < castEList.size(); j++) {
				intArray[j][0] = (int) castEList.get(j)[0];
				intArray[j][1] = (int) castEList.get(j)[1];
				intArray[j][2] = (int) castEList.get(j)[2];
				intArray[j][3] = (int) castEList.get(j)[3];

			}
			return intArray;
		}

		intArray = new int[castRList.size()][4];
		for (int j = 0; j < castRList.size(); j++) {
			intArray[j][0] = (int) castRList.get(j)[0];
			intArray[j][1] = (int) castRList.get(j)[1];
			intArray[j][2] = (int) castRList.get(j)[2];
			intArray[j][3] = (int) castRList.get(j)[3];

		}
		return intArray;
	}

	public int[][] getSpawnList() {
		int[][] intArray = new int[spawnList.size()][5];
		for (int j = 0; j < spawnList.size(); j++) {
			intArray[j][0] = (int) spawnList.get(j)[0];
			intArray[j][1] = (int) spawnList.get(j)[1];
			intArray[j][2] = (int) spawnList.get(j)[2];
			intArray[j][3] = (int) spawnList.get(j)[3];
			intArray[j][4] = (int) spawnList.get(j)[4];

		}
		return intArray;
	}

	// Adds enemies to the enemy spawning indicator
	// [q, r, type(int), time elapsed, total spawning time]
	public static void addEnemies(int type) {
		double[] spawns = new double[5];

		int size = Hextile.size;

		if (type == 0) {
			Enemy3.getAngle();
			int q = Enemy3.getQR()[0];
			int r = Enemy3.getQR()[1];
			double[][] arr = genDir(GamePanel.getHextiles()[q + Hextile.size
					/ 2][r + Hextile.size / 2].getX(),
					GamePanel.getHextiles()[q + Hextile.size / 2][r
							+ Hextile.size / 2].getY(), q, r,
					Enemy3.getAngle(), GamePanel.getHextiles());
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}
		} else if (type == 1) {
			double[][] arr = { { 0, -10, 1, 0, 3 }, { -10, 0, 1, 0, 3 },
					{ -10, 10, 1, 0, 3 }, { 0, 10, 1, 0, 3 },
					{ 10, 0, 1, 0, 3 }, { 10, -10, 1, 0, 3 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}

		} else if (type == 2) {
			double[][] arr = { { -10, 10, 1, 0, 3 }, { -10, 0, 2, 0, 3 },
					{ 10, 0, 2, 0, 3 }, { 10, -10, 2, 1, 3 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}

		} else if (type == 3) {
			double[][] arr = { { 0, -10, 1, 0, 3 }, { 0, 10, 1, 0, 3 },
					{ 0, 0, 2, 0, 3 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}
		} else if (EnemyHandler.getE3Size() != 1) {
			double[][] arr = { { 0, -10, 3, 0, 3 }, { -10, 0, 2, 0, 3 },
					{ -10, 10, 2, 0, 3 }, { 0, 10, 1, 0, 3 },
					{ 10, 0, 1, 0, 3 }, { 10, -10, 1, 0, 3 }, { 0, 0, 2, 0, 3 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}
		} else {
			double[][] arr = { { 0, -10, 1, 0, 3 }, { -10, 0, 2, 0, 3 },
					{ -10, 10, 2, 0, 3 }, { 0, 10, 1, 0, 3 },
					{ 10, 0, 1, 0, 3 }, { 10, -10, 2, 0, 3 }, { 0, 0, 1, 0, 3 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}
		}

	}

	public static int spawnSize() {
		return spawnList.size();
	}

	// Gives the progress of the casting animation
	public double getProgress(int i) {
		if (i == 1) {
			return castWList.get(0)[2] / castWList.get(0)[3];
		} else if (i == 2) {
			return castEList.get(0)[2] / castEList.get(0)[3];
		} else {
			return castRList.get(0)[2] / castRList.get(0)[3];
		}
	}

	// Gets the progress of the spawning animations
	public double getSpawningProgress() {
		return spawnList.get(0)[3] / spawnList.get(0)[4];
	}

	// generates directional hexagonal path for dragon
	private static double[][] genDir(int hx, int hy, int q, int r,
			double theta, Hextile[][] hextiles) {

		int i = q + Hextile.size / 2;
		int j = r + Hextile.size / 2;

		double h = hextiles[i][j].getTilesh();
		double w = hextiles[i][j].getTilesw() / 2;

		int tq = q;
		int tr = r;
		double[] point, point2, point3;

		double[] hAngles = new double[6];
		hAngles[0] = Math.atan2((double) (h), (double) (w));
		hAngles[1] = Math.atan2((double) (h), (double) (-w));
		hAngles[2] = Math.atan2((double) (0), (double) (-w * 2));
		hAngles[3] = (Math.atan2((double) (-h), (double) (-w)) + Math.PI * 2)
				% (Math.PI * 2);
		hAngles[4] = (Math.atan2((double) (-h), (double) (w)) + Math.PI * 2)
				% (Math.PI * 2);

		ArrayList<double[]> points = new ArrayList<double[]>();

		while (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
				&& hextiles[i][j] != null) {

			point = new double[5];
			point2 = new double[5];
			point3 = new double[5];

			if (theta < hAngles[0]) {
				point2[0] = tq + 1;
				point2[1] = tr - 1;
				point3[0] = tq;
				point3[1] = tr + 1;
				tq++;
			} else if (theta < hAngles[1]) {
				point2[0] = tq + 1;
				point2[1] = tr;
				point3[0] = tq - 1;
				point3[1] = tr + 1;
				tr++;
			} else if (theta < hAngles[2]) {
				point2[0] = tq;
				point2[1] = tr + 1;
				point3[0] = tq - 1;
				point3[1] = tr;
				tq--;
				tr++;
			} else if (theta < hAngles[3]) {
				point2[0] = tq - 1;
				point2[1] = tr + 1;
				point3[0] = tq;
				point3[1] = tr - 1;
				tq--;
			} else if (theta < hAngles[4]) {
				point2[0] = tq + 1;
				point2[1] = tr - 1;
				point3[0] = tq - 1;
				point3[1] = tr;
				tr--;
			} else {
				point2[0] = tq + 1;
				point2[1] = tr;
				point3[0] = tq;
				point3[1] = tr - 1;
				tq++;
				tr--;
			}

			point[0] = tq;
			point[1] = tr;
			point[2] = point2[2] = point3[2] = 0;
			point[3] = point2[3] = point3[3] = 0;
			point[4] = point2[4] = point3[4] = 1;

			if (!points.contains(point))
				points.add(point);

			i = (int) (point2[0] + Hextile.size / 2);
			j = (int) (point2[1] + Hextile.size / 2);

			if (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
					&& hextiles[i][j] != null && !points.contains(point2))
				points.add(point2);

			i = (int) (point3[0] + Hextile.size / 2);
			j = (int) (point3[1] + Hextile.size / 2);

			if (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
					&& hextiles[i][j] != null && !points.contains(point3))
				points.add(point3);

			i = tq + Hextile.size / 2;
			j = tr + Hextile.size / 2;

		}

		double[][] indicator = new double[points.size()][];

		for (int f = 0; f < indicator.length; f++) {
			indicator[f] = points.get(f);
		}
		return indicator;
	}

	public int getSpawnSize() {
		return spawnList.size();
	}
}
