public class StarchildAbilities extends PlayerAbilities {
	private int[][] indicator;
	private int type;

	public StarchildAbilities() {
		super();

		double[] cdList = new double[4];
		cdList[0] = 0.5;
		cdList[1] = 1.0;
		cdList[2] = 0.7;
		cdList[3] = 1.2;
		setCDs(cdList);

		indicator = new int[0][0];

	}

	public void generateIndicator(int abFocus, int q, int r, int mq, int mr) {
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
			// indicator = new int[0][0];
			break;
		}

	}

	public int[][] getIndicator() {

		// TODO this
		return indicator;
	}

}
