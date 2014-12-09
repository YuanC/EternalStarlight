import java.awt.Graphics2D;

public class Battle_Player {
	private int q, r, mx, my;
	private double angle, adjangle, speed, x, y;

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

	}

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
		if (!Hextile.getBigContainHex().contains((int) newx, (int) newy)) {
			// TODO: Stay in grid
		} else if (2 <= Math.sqrt(Math.pow(mx - x, 2)
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

	public int getQ() {
		return q;
	}

	public int getR() {
		return r;
	}

	private double angleCal(int x, int y, int mx, int my) {

		double theta;

		theta = angle = Math.atan2(my - y, mx - x);

		return theta;
	}

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
