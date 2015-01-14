import java.util.ArrayList;

public class PlayerSpells {

	// each int array is [q, r, time elapsed, total time]
	private static ArrayList<double[]> wList;
	private static ArrayList<double[]> eList;
	private static ArrayList<double[]> rList;
	private static double qdmg, wdmg, edmg, rdmg;

	public PlayerSpells(int dmg) {
		wList = new ArrayList<double[]>();
		eList = new ArrayList<double[]>();
		rList = new ArrayList<double[]>();

		qdmg = dmg / 5 + 1;
		wdmg = dmg / 5 + 2;
		edmg = dmg / 5 + 2;
		rdmg = dmg / 5 + 10;
	}

	public void update(double delta) {
		for (int i = 0; i < wList.size(); i++) {

			wList.get(i)[2] += delta;

			if (wList.get(i)[2] > wList.get(i)[3]) {
				wList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < eList.size(); i++) {

			eList.get(i)[2] += delta;

			if (eList.get(i)[2] > eList.get(i)[3]) {
				eList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < rList.size(); i++) {

			rList.get(i)[2] += delta;

			if (rList.get(i)[2] > rList.get(i)[3]) {
				rList.remove(i);
				i--;
			}

		}
	}

	public static void addSpell(int i, int q, int r) {
		double[] arr = new double[4];
		if (i == 1) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.1;
			wList.add(arr);

		} else if (i == 2) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.1;
			eList.add(arr);
		} else if (i == 3) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.1;
			rList.add(arr);
		}

	}

	public static int[][] getList(int i) {

		// System.out.println(wList.size());
		int[][] intArray = null;
		if (i == 1) {
			intArray = new int[wList.size()][4];
			for (int j = 0; j < wList.size(); j++) {
				intArray[j][0] = (int) wList.get(j)[0];
				intArray[j][1] = (int) wList.get(j)[1];
				intArray[j][2] = (int) wList.get(j)[2];
				intArray[j][3] = (int) wList.get(j)[3];

			}
		} else if (i == 2) {
			intArray = new int[eList.size()][4];
			for (int j = 0; j < eList.size(); j++) {
				intArray[j][0] = (int) eList.get(j)[0];
				intArray[j][1] = (int) eList.get(j)[1];
				intArray[j][2] = (int) eList.get(j)[2];
				intArray[j][3] = (int) eList.get(j)[3];

			}
		} else {
			intArray = new int[rList.size()][4];
			for (int j = 0; j < rList.size(); j++) {
				intArray[j][0] = (int) rList.get(j)[0];
				intArray[j][1] = (int) rList.get(j)[1];
				intArray[j][2] = (int) rList.get(j)[2];
				intArray[j][3] = (int) rList.get(j)[3];

			}
		}
		return intArray;
	}

	public static double getDmg(int i) {
		switch (i) {
		case 0:
			return qdmg;
		case 1:
			return wdmg;
		case 2:
			return edmg;
		case 3:
			return rdmg;
		}
		return 0;

	}
}
