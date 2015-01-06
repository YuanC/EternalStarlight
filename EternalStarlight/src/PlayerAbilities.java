import java.awt.Graphics2D;

//The super class for the player's abilities
public class PlayerAbilities {

	private static double qCD, wCD, eCD, rCD;
	private static double[] cdTimers;
	private static int abFocus; // The ability being used

	public PlayerAbilities() {
		cdTimers = new double[4];
		resetCDs();
		setAbFocus(4);
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

	public static void startCD(int i) {
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
		g.drawString("" + getAbFocus(), 5, 70);
		// TODO draw animation and graphic;
	}

	public static void setFoc(int i) {
		switch (i) {
		case 0:
			if (getAbFocus() != 0 && cdTimers[0] == 0)
				setAbFocus(i);
			break;
		case 1:
			if (getAbFocus() != 1 && cdTimers[1] == 0)
				setAbFocus(i);
			break;
		case 2:
			if (getAbFocus() != 2 && cdTimers[2] == 0)
				setAbFocus(i);
			break;
		case 3:
			if (getAbFocus() != 3 && cdTimers[3] == 0)
				setAbFocus(i);
			break;
		case 4:
			setAbFocus(4);
			break;
		}
	}

	public static int getFoc() {
		return getAbFocus();
	}

	public static int getAbFocus() {
		return abFocus;
	}

	public static void setAbFocus(int abFocus) {
		PlayerAbilities.abFocus = abFocus;
	}
}
