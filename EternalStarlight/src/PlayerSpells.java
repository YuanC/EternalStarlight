import java.util.ArrayList;

public class PlayerSpells {

	// each int array is [q, r, time elapsed, total time]
	private static ArrayList<double[]> WList;
	private static ArrayList<double[]> EList;
	private static ArrayList<double[]> RList;
	private static int qdmg, wdmg, edmg, rdmg;

	public PlayerSpells(int dmg) {
		WList = new ArrayList<double[]>();
		EList = new ArrayList<double[]>();
		RList = new ArrayList<double[]>();

		qdmg = dmg;
		wdmg = dmg + 2;
		edmg = dmg + 3;
		rdmg = dmg + 5;
	}

	public void update(double delta) {
		for (int i = 0; i < WList.size(); i++) {

			WList.get(i)[2] += delta;

			if (WList.get(i)[2] > WList.get(i)[3]) {
				PlayerSpells.addSpell(1, (int) WList.get(i)[0],
						(int) WList.get(i)[1]);
				WList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < EList.size(); i++) {

			EList.get(i)[2] += delta;

			if (EList.get(i)[2] > EList.get(i)[3]) {
				PlayerSpells.addSpell(2, (int) EList.get(i)[0],
						(int) EList.get(i)[1]);
				EList.remove(i);
				i--;
			}

		}

		for (int i = 0; i < RList.size(); i++) {

			RList.get(i)[2] += delta;

			if (RList.get(i)[2] > RList.get(i)[3]) {
				PlayerSpells.addSpell(3, (int) RList.get(i)[0],
						(int) RList.get(i)[1]);
				RList.remove(i);
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
			arr[3] = 1.0;
			WList.add(arr);

		} else if (i == 2) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 1.0;
			EList.add(arr);
		} else if (i == 3) {
			arr[0] = q;
			arr[1] = r;
			arr[2] = 0;
			arr[3] = 1.0;
			RList.add(arr);
		}

	}

}
