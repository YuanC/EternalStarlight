import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

//Handles the abilities and generates for the player
public class StarchildAbilities extends PlayerAbilities {
	private static int[][] indicator;
	private static double qAngle;

	public StarchildAbilities(int cdr) {
		super();

		double[] cdList = new double[4];
		cdList[0] = 0.5 * (1 - cdr / 100);
		cdList[1] = 4 * (1 - cdr / 100.0);
		cdList[2] = 3 * (1 - cdr / 100.0);
		cdList[3] = 15 * (1 - cdr / 100.0);
		setCDs(cdList);

		indicator = new int[0][0];
		qAngle = 0;
	}

	// draws the indicators for all the abilities
	public void drawIndicator(Graphics2D g, Hextile[][] hextiles, int q, int r,
			int mq, int mr, int x, int y, int mx, int my) {

		double theta;
		int[] end;
		ArrayList<int[]> points;
		switch (getAbFocus()) {
		case 0:
			g.setColor(Color.white);
			theta = Math.atan2((double) (my - y), (double) (mx - x))
					% (Math.PI * 2);
			qAngle = theta;
			end = genLine(x, y, theta, Hextile.getBigContainHex(), hextiles);

			g.drawLine(x, y, end[0], end[1]);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);
			break;
		case 1:
			g.setColor(Color.white);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);

			int hx = hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
					.getX();
			int hy = hextiles[q + Hextile.size / 2][r + Hextile.size / 2]
					.getY();

			theta = (Math.atan2((double) (my - hy), (double) (mx - hx)) + Math.PI * 2)
					% (Math.PI * 2);

			genDir(hx, hy, q, r, theta, hextiles);
			break;
		case 2:
			g.setColor(Color.white);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);

			points = genHex(hextiles, 2, mq, mr);

			indicator = new int[points.size()][];

			for (int i = 0; i < indicator.length; i++) {
				indicator[i] = points.get(i);
			}

			break;
		case 3:
			g.setColor(Color.white);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);

			points = genHex(hextiles, 6, q, r);

			indicator = new int[points.size()][];

			for (int i = 0; i < indicator.length; i++) {
				indicator[i] = points.get(i);
			}
			break;
		case 4:
			indicator = new int[0][0];
			break;
		}
	}

	// generates path for E and R ability
	private ArrayList<int[]> genHex(Hextile[][] hextiles, int size, int mq,
			int mr) {

		if (size <= 0) {
			ArrayList<int[]> centre = new ArrayList<int[]>();
			int[] c = { mq, mr };
			centre.add(c);
			return centre;
		}

		ArrayList<int[]> points = new ArrayList<int[]>();
		ArrayList<int[]> pointsToAdd = (genHex(hextiles, size - 1, mq, mr));

		for (int i = 0; i < pointsToAdd.size(); i++) {
			if (pointsToAdd.get(i) != null)
				points.add(pointsToAdd.get(i));
		}

		int[] point = new int[2];
		int j, k, tq = mq, tr = mr;

		tq = mq + size;
		tr = mr;
		point[0] = tq;
		point[1] = tr;

		j = point[0] + Hextile.size / 2;
		k = point[1] + Hextile.size / 2;

		if (j >= 0 && j < Hextile.size && k >= 0 && k < Hextile.size
				&& hextiles[j][k] != null && !points.contains(point))
			points.add(point);

		for (int i = 0; i < 6; i++) {// TODO Proper hex rendering with tq,tr

			for (int f = 0; f < size; f++) {
				switch (i) {
				case 0:
					tq--;
					tr++;
					break;
				case 1:
					tq--;
					break;
				case 2:
					tr--;
					break;
				case 3:
					tq++;
					tr--;
					break;
				case 4:
					tq++;
					break;
				case 5:
					tr++;
					break;
				}
				point = new int[2];
				point[0] = tq;
				point[1] = tr;

				j = point[0] + Hextile.size / 2;
				k = point[1] + Hextile.size / 2;

				if (j >= 0 && j < Hextile.size && k >= 0 && k < Hextile.size
						&& hextiles[j][k] != null && !points.contains(point))
					points.add(point);
			}
		}

		return points;

	}

	// Generates the line and indicator highlight hexagon list for "q" ability
	private int[] genLine(int x, int y, double theta, Polygon bigContainHex,
			Hextile[][] hextiles) {
		double endx = x, endy = y, newx = x, newy = y;
		ArrayList<int[]> points = new ArrayList<int[]>();
		int[] point;
		while (bigContainHex.contains(newx, newy)) {
			endx = newx;
			endy = newy;

			newx += Math.cos(theta) * 5;
			newy += Math.sin(theta) * 5;

			point = Hextile.hexContainCal(hextiles, (int) endx, (int) endy);

			if (point != null && !points.contains(point)) {
				points.add(point);
			}
		}

		indicator = new int[points.size()][];

		for (int i = 0; i < indicator.length; i++) {
			indicator[i] = points.get(i);
		}

		int end[] = { (int) endx, (int) endy };

		return end;
	}

	// generates path for W ability
	private void genDir(int hx, int hy, int q, int r, double theta,
			Hextile[][] hextiles) {

		int i = q + Hextile.size / 2;
		int j = r + Hextile.size / 2;

		double h = hextiles[i][j].getTilesh();
		double w = hextiles[i][j].getTilesw() / 2;

		int tq = q;
		int tr = r;
		int[] point, point2, point3;

		double[] hAngles = new double[6];
		hAngles[0] = Math.atan2((double) (h), (double) (w));
		hAngles[1] = Math.atan2((double) (h), (double) (-w));
		hAngles[2] = Math.atan2((double) (0), (double) (-w * 2));
		hAngles[3] = (Math.atan2((double) (-h), (double) (-w)) + Math.PI * 2)
				% (Math.PI * 2);
		hAngles[4] = (Math.atan2((double) (-h), (double) (w)) + Math.PI * 2)
				% (Math.PI * 2);

		ArrayList<int[]> points = new ArrayList<int[]>();

		while (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
				&& hextiles[i][j] != null) {

			point = new int[2];
			point2 = new int[2];
			point3 = new int[2];

			if (theta < hAngles[0]) {
				point2[0] = tq + 1;
				point2[1] = tr - 1;
				point3[0] = tq;
				point3[1] = tr + 1;
				tq++;
			} else if (theta < hAngles[1]) {
				point2[0] = tq + 1;
				point2[1] = tr;
				point3[0] = tq - 1;
				point3[1] = tr + 1;
				tr++;
			} else if (theta < hAngles[2]) {
				point2[0] = tq;
				point2[1] = tr + 1;
				point3[0] = tq - 1;
				point3[1] = tr;
				tq--;
				tr++;
			} else if (theta < hAngles[3]) {
				point2[0] = tq - 1;
				point2[1] = tr + 1;
				point3[0] = tq;
				point3[1] = tr - 1;
				tq--;
			} else if (theta < hAngles[4]) {
				point2[0] = tq + 1;
				point2[1] = tr - 1;
				point3[0] = tq - 1;
				point3[1] = tr;
				tr--;
			} else {
				point2[0] = tq + 1;
				point2[1] = tr;
				point3[0] = tq;
				point3[1] = tr - 1;
				tq++;
				tr--;
			}

			point[0] = tq;
			point[1] = tr;

			if (!points.contains(point))
				points.add(point);

			i = point2[0] + Hextile.size / 2;
			j = point2[1] + Hextile.size / 2;

			if (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
					&& hextiles[i][j] != null && !points.contains(point2))
				points.add(point2);

			i = point3[0] + Hextile.size / 2;
			j = point3[1] + Hextile.size / 2;

			if (i >= 0 && i < Hextile.size && j >= 0 && j < Hextile.size
					&& hextiles[i][j] != null && !points.contains(point3))
				points.add(point3);

			i = tq + Hextile.size / 2;
			j = tr + Hextile.size / 2;

		}

		indicator = new int[points.size()][];

		for (int f = 0; f < indicator.length; f++) {
			indicator[f] = points.get(f);
		}
	}

	public static int[][] getIndicator() {

		return indicator;
	}

	public static double getqAngle() {
		return qAngle;
	}

}
