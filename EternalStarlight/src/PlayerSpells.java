import java.util.ArrayList;

public class PlayerSpells {

	// each int array is [q, r, time elapsed, total time]
	private static ArrayList<double[]> wList;
	private static ArrayList<double[]> eList;
	private static ArrayList<double[]> rList;
	private static int qdmg, wdmg, edmg, rdmg;

	public PlayerSpells(int dmg) {
		wList = new ArrayList<double[]>();
		eList = new ArrayList<double[]>();
		rList = new ArrayList<double[]>();

		qdmg = dmg;
		wdmg = dmg + 2;
		edmg = dmg + 3;
		rdmg = dmg + 5;
	}

	public void update(double delta) {
		for (int i = 0; i < wList.size(); i++) {

			wList.get(i)[2] += delta;
			System.out.println(wList.get(i)[2]);

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
			arr[3] = 0.2;
			wList.add(arr);

		} else if (i == 2) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.2;
			eList.add(arr);
		} else if (i == 3) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 0.2;
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
			return intArray;
		} else if (i == 2) {
			intArray = new int[eList.size()][4];
			for (int j = 0; j < eList.size(); j++) {
				intArray[j][0] = (int) eList.get(j)[0];
				intArray[j][1] = (int) eList.get(j)[1];
				intArray[j][2] = (int) eList.get(j)[2];
				intArray[j][3] = (int) eList.get(j)[3];

			}
			return intArray;
		}

		intArray = new int[rList.size()][4];
		for (int j = 0; j < rList.size(); j++) {
			intArray[j][0] = (int) rList.get(j)[0];
			intArray[j][1] = (int) rList.get(j)[1];
			intArray[j][2] = (int) rList.get(j)[2];
			intArray[j][3] = (int) rList.get(j)[3];

		}
		return intArray;
	}
}
