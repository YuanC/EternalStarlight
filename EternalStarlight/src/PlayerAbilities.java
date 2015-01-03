import java.awt.Graphics2D;

//The super class for the player's abilities
public class PlayerAbilities {

	private double qCD, wCD, eCD, rCD;
	private double[] cdTimers;

	public PlayerAbilities() {
		cdTimers = new double[4];
		resetCDs();

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

	// Sets the lengths of the ability cooldowns
	public void setCDs(int[] cdList) {
		qCD = cdList[0];
		wCD = cdList[1];
		eCD = cdList[2];
		rCD = cdList[3];
	}

	// Draws the cooldowns
	public void drawCooldown(Graphics2D g) {
		// TODO draw animation and graphic;
	}
}
