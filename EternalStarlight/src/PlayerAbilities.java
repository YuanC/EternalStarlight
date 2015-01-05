import java.awt.Graphics2D;

//The super class for the player's abilities
public class PlayerAbilities {

	private double qCD, wCD, eCD, rCD;
	private static double[] cdTimers;
	private static int abFocus; // The ability being used

	public PlayerAbilities() {
		cdTimers = new double[4];
		resetCDs();
		abFocus = 4;
	}

	// Updates the cooldown timers
	public void updateCD(double delta) {
		for (int i = 0; i < 4; i++)
			cdTimers[i] = Math.max(0, cdTimers[i] - delta);
	}

	public void resetCDs() {
		for (int i = 0; i < 4; i++)
			cdTimers[i] = 0;
	}

	public void startCD(int i) {
		switch (i) {
		case 0:
			cdTimers[0] = qCD;
			break;

		case 1:
			cdTimers[1] = wCD;
			break;

		case 2:
			cdTimers[2] = eCD;
			break;

		case 3:
			cdTimers[3] = rCD;
			break;
		}
	}

	// Sets the lengths of the ability cooldowns
	public void setCDs(double[] cdList) {
		qCD = cdList[0];
		wCD = cdList[1];
		eCD = cdList[2];
		rCD = cdList[3];

		for (int i = 0; i < 4; i++) {
			startCD(i);
		}
	}

	// Draws the cooldowns
	public void drawCooldown(Graphics2D g) {
		g.drawString(cdTimers[0] + "", 5, 30);
		g.drawString(cdTimers[1] + " ", 5, 40);
		g.drawString(cdTimers[2] + " ", 5, 50);
		g.drawString("" + cdTimers[3], 5, 60);
		g.drawString("" + abFocus, 5, 70);
		// TODO draw animation and graphic;
	}

	public static void setFoc(int i) {
		switch (i) {
		case 0:
			if (abFocus != 0 && cdTimers[0] == 0)
				abFocus = i;
			break;
		case 1:
			if (abFocus != 1 && cdTimers[1] == 0)
				abFocus = i;
			break;
		case 2:
			if (abFocus != 2 && cdTimers[2] == 0)
				abFocus = i;
			break;
		case 3:
			if (abFocus != 3 && cdTimers[3] == 0)
				abFocus = i;
			break;
		case 4:
			abFocus = 4;
			break;
		}
	}

	public int getFoc() {
		return abFocus;
	}
}
