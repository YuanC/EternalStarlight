import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.ImageIcon;

public class Battle_Player {
	private int q, r, mx, my, health, maxHealth, CDR, strength, hx, hy;
	private double angle, speed, recoverCD;
	private static double x, y;
	private double hpAngle;
	private ImageIcon[] down, left, right, up;
	private boolean moving;

	public Battle_Player(int health) {
		q = 0;
		r = 0;
		y = Hextile.n_padding
				+ (Hextile.screen_y - Hextile.n_padding - Hextile.s_padding)
				/ 2;
		x = Hextile.h_padding + (Hextile.screen_x - Hextile.h_padding * 2) / 2;
		angle = 0;
		speed = 60;
		mx = (int) x;
		my = (int) y;
		hx = 1220;
		hy = 60;
		recoverCD = 2;

		this.maxHealth = health;
		this.health = health;

		moving = true;

		// designates the images
		down = new ImageIcon[2];
		down[0] = new ImageIcon("lvlimages/down.gif");
		down[1] = new ImageIcon("lvlimages/StarchildDOWN.png");

		left = new ImageIcon[2];
		left[0] = new ImageIcon("lvlimages/left.gif");
		left[1] = new ImageIcon("lvlimages/StarchildLEFT.png");

		right = new ImageIcon[2];
		right[0] = new ImageIcon("lvlimages/right.gif");
		right[1] = new ImageIcon("lvlimages/StarchildRIGHT.png");

		up = new ImageIcon[2];
		up[0] = new ImageIcon("lvlimages/up.gif");
		up[1] = new ImageIcon("lvlimages/StarchildUP.png");

	}

	// Updates the player object position and status
	public void update(MouseStatus mouse, Hextile[][] hextiles, double delta) {

		moving = true;

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
					moving = false;
				}
			} else {
				moving = false;
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
				moving = false;
			}
		}

		hpAngle = (hpAngle + 0.01) % (Math.PI * 2);

		if (recoverCD > 0) {
			recoverCD -= delta / 3;
		}
		// System.out.println(recoverCD);
		if (recoverCD <= 0) {
			if (collisionCal(EnemyHandler.getList(1), EnemyHandler.getList(2),
					EnemyHandler.getList(3), ProjectileHandler.getEShots())) {
				startRecover();
				health--;

				EnemyHandler.removeInHex(q, r);
				EnemyHandler.removeInHex(q + 1, r);
				EnemyHandler.removeInHex(q + 1, r - 1);
				EnemyHandler.removeInHex(q, r - 1);
				EnemyHandler.removeInHex(q - 1, r);
				EnemyHandler.removeInHex(q - 1, r + 1);
				EnemyHandler.removeInHex(q, r + 1);
			}
		}
	}

	// Checks if it shares the same spot as an enemy or enemy projectile
	private boolean collisionCal(int[][] list, int[][] list2, int[][] list3,
			int[][] eShots) {

		int[] qr = { q, r };

		for (int i = 0; i < list.length; i++) {
			if (list[i][0] == qr[0] && list[i][1] == qr[1])
				return true;
		}
		for (int i = 0; i < list2.length; i++) {
			if (list2[i][0] == qr[0] && list2[i][1] == qr[1])
				return true;
		}
		for (int i = 0; i < list3.length; i++) {
			if (list3[i][0] == qr[0] && list3[i][1] == qr[1])
				return true;
		}
		for (int i = 0; i < eShots.length; i++) {
			if (eShots[i][0] == qr[0] && eShots[i][1] == qr[1])
				return true;
		}
		return false;
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

	public void startRecover() {
		recoverCD = 1;
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

	// draws the player
	public void draw(Graphics2D g) {

		int stance = 1;
		if (moving == true) {
			stance = 0;
		}

		angle = (angle + Math.PI * 2) % (Math.PI * 2);

		g.drawOval((int) x - 10, (int) y - 5, 20, 10);
		g.drawLine((int) x, (int) y, (int) (x + Math.cos(angle) * 10),
				(int) (y + Math.sin(angle) * 5));

		if ((int) (recoverCD * 10) % 2 == 1 || recoverCD <= 0) {
			// Player debug image

			if (angle < Math.PI / 4 || angle > Math.PI * 7 / 4) {
				g.drawImage(right[stance].getImage(), (int) x - 19,
						(int) y - 48, null);

			} else if (angle < Math.PI * 3 / 4) {
				g.drawImage(down[stance].getImage(), (int) x - 19,
						(int) y - 48, null);

			} else if (angle < Math.PI * 5 / 4) {
				g.drawImage(left[stance].getImage(), (int) x - 19,
						(int) y - 48, null);

			} else {
				g.drawImage(up[stance].getImage(), (int) x - 19, (int) y - 48,
						null);
			}

		}
		if (recoverCD >= 0) {
			Hextile.drawShakeHex(g);
		}
	}

	// RECURSION DRAWS HEALTH BAR THING
	public void drawhealth(Graphics2D g, int lvl, int current) {
		if (lvl <= 0)
			return;

		int radius = 10 * lvl, sides = lvl + 2;

		double tAngle;
		int[] xverts = new int[sides], yverts = new int[sides];

		for (int i = 0; i < sides; i++) {
			if (lvl % 2 == 0) {
				tAngle = Math.PI * 2 / (sides) * i + hpAngle;
			} else {
				tAngle = Math.PI * 2 / (sides) * i - hpAngle;
			}
			xverts[i] = (int) (Math.cos(tAngle) * radius) + hx;
			yverts[i] = (int) (Math.sin(tAngle) * radius) + hy;
		}

		if (lvl > current) {
			g.setColor(Color.lightGray);
			g.drawPolygon(new Polygon(xverts, yverts, sides));
		} else if (lvl == current) {
			g.setColor(Color.white);
			g.fillPolygon(new Polygon(xverts, yverts, sides));
		} else {
			g.setColor(Color.gray);
			g.drawPolygon(new Polygon(xverts, yverts, sides));
		}

		drawhealth(g, lvl - 1, current);
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	public static int getY() {
		// TODO Auto-generated method stub
		return (int) y;
	}

	public static int getX() {
		// TODO Auto-generated method stub
		return (int) x;
	}
}
