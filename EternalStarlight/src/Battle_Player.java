import java.awt.Graphics2D;

public class Battle_Player {
	private int q, r, mx, my, health, maxHealth, CDR, strength;
	private double angle, speed, x, y;

	public Battle_Player() {
		q = 0;
		r = 0;
		y = Hextile.n_padding
				+ (Hextile.screen_y - Hextile.n_padding - Hextile.s_padding)
				/ 2;
		x = Hextile.h_padding + (Hextile.screen_x - Hextile.h_padding * 2) / 2;
		angle = 0;
		speed = 80;
		mx = (int) x;
		my = (int) y;
		// TODO maxhealth, cdr, strength(damage) implementation

	}

	// Updates the player object position and status
	public void update(MouseStatus mouse, Hextile[][] hextiles, double delta) {

		if (mouse.isPressed() && my != mouse.getMy() && mx != mouse.getMx()) {
			mx = mouse.getMx();
			my = mouse.getMy();
			angle = angleCal((int) x, (int) y, mx, my);
		}

		double newx = x + Math.cos(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		double newy = y + Math.sin(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		double[] alternatePath;

		// If the point is not in the big hexagon
		if (!Hextile.getBigContainHex().contains((int) newx, (int) newy)) {
			alternatePath = alternateRouteCal(delta);

			if (alternatePath != null) {
				if (1.5 <= Math.sqrt(Math.pow(mx - x, 2)
						+ Math.pow((my - y) / 2.0, 2))) {
					x = alternatePath[0];
					y = alternatePath[1];
					updateTilePos(hextiles);
				} else {
					x = mx;
					y = my;
					updateTilePos(hextiles);
				}
			}

		} else {
			if (1.5 <= Math.sqrt(Math.pow(mx - x, 2)
					+ Math.pow((my - y) / 2.0, 2))) {
				x = newx;
				y = newy;
				updateTilePos(hextiles);
			} else {
				x = mx;
				y = my;
				updateTilePos(hextiles);
			}
		}

	}

	// Moving along a wall calculations

	private double[] alternateRouteCal(double delta) {

		double newx, newy;
		double tAngle;

		for (int i = 1; i < 90; i += 1) {

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tAngle = angle + Math.PI / 2 * i / 90;
				} else {
					tAngle = angle - Math.PI / 2 * i / 90;
				}
				newx = x + Math.cos(tAngle) * speed * delta;
				newy = y + Math.sin(tAngle) * speed * delta;

				if (Hextile.getBigContainHex().contains(newx, newy)) {
					double[] xy = new double[2];
					xy[0] = newx;
					xy[1] = newy;
					angle = angleCal((int) xy[0], (int) xy[1], mx, my);
					return xy;
				}
			}
		}
		return null;
	}

	public int getQ() {
		return q;
	}

	public int getR() {
		return r;
	}

	// Finds the angle the player's facing
	private double angleCal(int x, int y, int mx, int my) {

		double theta;
		theta = angle = Math.atan2(my - y, mx - x);

		return theta;
	}

	// Updates the tile position of the player
	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

	public void draw(Graphics2D g) {

		// Player debug image
		g.drawOval((int) x - 10, (int) y - 5, 20, 10);
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
				(int) (y + Math.sin(angle) * 5));
	}
}
