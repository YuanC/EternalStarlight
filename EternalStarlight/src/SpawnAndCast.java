import java.util.ArrayList;

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
				EnemyHandler.addEnemy((int) spawnList.get(i)[0],
						(int) spawnList.get(i)[1], (int) spawnList.get(i)[2]);
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

		if (type == 1) {// TODO this stuff
			double[][] arr = { { 0, -10, 1, 0, 1 }, { -10, 0, 1, 0, 1 },
					{ -10, 10, 1, 0, 1 }, { 0, 10, 1, 0, 1 },
					{ 10, 0, 1, 0, 1 }, { 10, -10, 1, 0, 1 } };
			for (int i = 0; i < arr.length; i++) {

				spawnList.add(arr[i]);
			}

		} else if (type == 2) {
			double[][] arr = { { -10, 10, 2, 0, 1 }, { -10, 0, 2, 0, 1 },
					{ 10, 0, 2, 0, 1 }, { 10, -10, 2, 0, 1 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}

		} else if (type == 3) {
			double[][] arr = { { 0, -10, 2, 0, 1 }, { 0, 10, 2, 0, 1 },
					{ 0, 0, 3, 0, 1 } };
			for (int i = 0; i < arr.length; i++) {
				spawnList.add(arr[i]);
			}
		} else {
			double[][] arr = { { 0, -10, 1, 0, 1 }, { -10, 0, 2, 0, 1 },
					{ -10, 10, 2, 0, 1 }, { 0, 10, 1, 0, 1 },
					{ 10, 0, 2, 0, 1 }, { 10, -10, 2, 0, 1 }, { 0, 0, 3, 0, 1 } };
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
}
