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
			indicator = new int[1][2];
			indicator[0][0] = mq;
			indicator[0][1] = mr;
			break;
		case 1:
			break;
		case 2:
			indicator = new int[1][2];
			indicator[0][0] = mq;
			indicator[0][1] = mr;
			break;
		case 3:
			break;
		case 4:
			indicator = new int[0][0];
			break;
		}

	}

	public void drawIndicator(Graphics2D g, int q, int r, int mq, int mr,
			int x, int y, int mx, int my) {
		switch (getAbFocus()) {
		case 0:
			g.setColor(Color.white);
			int l = 100;
			double theta = Math.atan2((double) (my - y), (double) (mx - x));
			g.drawLine(
					x,
					y,
					(int) (x + Math.cos(theta) * l
							* (1 - 0.5 * Math.abs(Math.sin(theta)))),
					(int) (y + Math.sin(theta) * l
							* (1 - 0.5 * Math.abs(Math.sin(theta)))));
			int end[] = genLine(x, y, theta, Hextile.getBigContainHex());
			g.drawLine(x, y, end[0], end[1]);
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);
			break;
		case 1:
			break;
		case 2:
			indicator = new int[1][2];
			indicator[0][0] = mq;
			indicator[0][1] = mr;
			break;
		case 3:
			break;
		case 4:
			indicator = new int[0][0];
			break;
		}
	}

	private int[] genLine(int x, int y, double theta, Polygon bigContainHex) {
		double endx = x, endy = y, newx = x, newy = y;
		ArrayList<int[]> points = new ArrayList<int[]>();
		
		
		while (bigContainHex.contains(newx, newy)) {
			endx = newx;
			endy = newy;

			newx += Math.cos(theta) * 4;
			newy += Math.sin(theta) * 4;
		}

		int end[] = { (int) endx, (int) endy };

		return end;
	}

	public int[][] getIndicator() {

		return indicator;
	}

}
