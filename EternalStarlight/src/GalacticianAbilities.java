public class GalacticianAbilities extends PlayerAbilities {
	private int[][] indicator;

	public GalacticianAbilities() {

		double[] cdList = new double[4];
		cdList[0] = 0.5;
		cdList[1] = 1.0;
		cdList[2] = 0.7;
		cdList[3] = 1.2;
		setCDs(cdList);

		indicator = new int[0][0];

	}

	public void generateIndicator(int abFocus) {
		// TODO Use algorithms to determine the stuff

		switch (abFocus) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			indicator = new int[0][0];
			break;
		}

	}

	public int[][] getIndicator() {

		// TODO this
		return null;
	}
	
	
	
}
