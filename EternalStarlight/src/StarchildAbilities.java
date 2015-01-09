import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;

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

	public void generateIndicator(int q, int r, int mq, int mr) {
		// TODO Use algorithms to determine the stuff

		switch (getAbFocus()) {
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

	public void drawIndicator(Graphics2D g, Hextile[][] hextiles, int q, int r,
			int mq, int mr, int x, int y, int mx, int my) {

		double theta;

		int[] end;

		switch (getAbFocus()) {
		case 0:
			g.setColor(Color.white);
			theta = Math.atan2((double) (my - y), (double) (mx - x));
			end = genLine(x, y, theta, Hextile.getBigContainHex(), hextiles);

			g.drawLine(x, y, end[0], end[1]);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);
			break;
		case 1:
			break;
		case 2:
			g.setColor(Color.white);

			theta = Math.atan2((double) (my - y), (double) (mx - x));

			end = genLine(x, y, theta, Hextile.getBigContainHex(), hextiles);

			g.drawLine(x, y, end[0], end[1]);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);
			break;
		case 3:
			break;
		case 4:
			indicator = new int[0][0];
			break;
		}
	}

	private int[] genLine(int x, int y, double theta, Polygon bigContainHex,
			Hextile[][] hextiles) {
		double endx = x, endy = y, newx = x, newy = y;
		ArrayList<int[]> points = new ArrayList<int[]>();

		while (bigContainHex.contains(newx, newy)) {
			endx = newx;
			endy = newy;

			newx += Math.cos(theta) * 5;
			newy += Math.sin(theta) * 5;

			int[] point = Hextile.hexContainCal(hextiles, (int) endx,
					(int) endy);

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

	private int[] genDir() {
		// TODO

		return null;

	}

	public int[][] getIndicator() {

		return indicator;
	}

}
