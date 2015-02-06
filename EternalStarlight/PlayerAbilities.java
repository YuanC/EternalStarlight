import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

//The super class for the player's abilities
public class PlayerAbilities {

	private static double qCD, wCD, eCD, rCD;
	private static double[] cdTimers;
	private static int abFocus; // The ability being used
	private static double abFocState;
	private static ImageIcon qpic, wpic, epic, rpic;

	public PlayerAbilities() {
		cdTimers = new double[4];
		resetCDs();
		setAbFocus(4);
		abFocState = 0;
		qpic = new ImageIcon("lvlimages/starchildQthumb.png");
		wpic = new ImageIcon("lvlimages/starchildWthumb.png");
		epic = new ImageIcon("lvlimages/starchildEthumb.png");
		rpic = new ImageIcon("lvlimages/starchildRthumb.png");
	}

	// Updates the cooldown timers
	public void updateCD(double delta) {
		for (int i = 0; i < 4; i++)
			cdTimers[i] = Math.max(0, cdTimers[i] - delta);
		abFocState = (abFocState + delta * 20) % 6.0;
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

		g.setColor(Color.WHITE);
		if (abFocus == 0) {
			g.drawOval(95, 25, 80, 80);
		} else if (abFocus == 1) {
			g.drawOval(185, 25, 80, 80);
		} else if (abFocus == 2) {
			g.drawOval(275, 25, 80, 80);
		} else if (abFocus == 3) {
			g.drawOval(365, 25, 80, 80);
		}

		g.drawImage(qpic.getImage(), 100, 30, null);
		g.drawImage(wpic.getImage(), 190, 30, null);
		g.drawImage(epic.getImage(), 280, 30, null);
		g.drawImage(rpic.getImage(), 370, 30, null);

		g.drawOval(100, 30, 70, 70);
		g.drawOval(190, 30, 70, 70);
		g.drawOval(280, 30, 70, 70);
		g.drawOval(370, 30, 70, 70);

		g.drawString("Press \"i\" for instructions", 95, 680);

		g.drawString("Q " + Math.round(cdTimers[0] * 100) / 100.0 + " ", 128,
				130);
		g.drawString("W " + Math.round(cdTimers[1] * 100) / 100.0 + " ", 218,
				130);
		g.drawString("E " + Math.round(cdTimers[2] * 100) / 100.0 + " ", 308,
				130);
		g.drawString("R " + "" + Math.round(cdTimers[3] * 100) / 100.0, 398,
				130);
		// g.setColor(new Color(51, 51, 51));
		if (cdTimers[0] > 0) {
			g.fillArc(100, 30, 70, 70, 90,
					(int) ((360 * cdTimers[0] / qCD)) % 360);
		}
		if (cdTimers[1] > 0) {
			g.fillArc(190, 30, 70, 70, 90,
					(int) ((360 * cdTimers[1] / wCD)) % 360);
		}
		if (cdTimers[2] > 0) {
			g.fillArc(280, 30, 70, 70, 90,
					(int) ((360 * cdTimers[2] / eCD)) % 360);
		}
		if (cdTimers[3] > 0) {
			g.fillArc(370, 30, 70, 70, 90,
					(int) ((360 * cdTimers[3] / rCD)) % 360);
		}

	}

	//sets the focus of the abilities
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

	public static double getAbFocState() {
		return abFocState;
	}

}
