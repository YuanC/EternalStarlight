import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//The class representing each tile on the hexagonal map
public class Hextile {
	private int q, r, x, y;
	private int[][] displayVerts;
	private int[][] realVerts;
	private int[][] indVerts;
	private Polygon displayhex;
	private Polygon hexagon;
	private static Polygon bigContainHex, bigShakeHex;
	private static double tiles_h;
	private static double tiles_w;
	public static final int n_padding = 130, s_padding = 50, h_padding = 100,
			screen_x = 1280, screen_y = 720, tileHGap = 2, tileVGap = 1;
	public static int size;

	// Returns the map-encompassing shape
	public static Polygon getBigContainHex() {
		return bigContainHex;
	}

	// Algorithm to generate the map-encompassing boundary shape
	public static void createBigContainHex(Hextile[][] hextiles) {
		bigContainHex = new Polygon();
		Polygon tempHex;
		for (int i = 0; i < size / 2 + 1; i++) {
			tempHex = hextiles[size / 2 - i][i].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[2], tempHex.ypoints[2]);
			bigContainHex.addPoint(tempHex.xpoints[3], tempHex.ypoints[3]);
		}
		for (int i = 0; i < size / 2; i++) {
			tempHex = hextiles[0][size / 2 + 1 + i].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[2], tempHex.ypoints[2]);
			bigContainHex.addPoint(tempHex.xpoints[3], tempHex.ypoints[3]);
		}
		for (int i = 0; i < size / 2 + 1; i++) {
			tempHex = hextiles[i][size - 1].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[4], tempHex.ypoints[4]);
			bigContainHex.addPoint(tempHex.xpoints[5], tempHex.ypoints[5]);
		}
		for (int i = 0; i < size / 2 + 1; i++) {
			tempHex = hextiles[size / 2 + i][size - 1 - i].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[4], tempHex.ypoints[4]);
			bigContainHex.addPoint(tempHex.xpoints[5], tempHex.ypoints[5]);
		}
		for (int i = 0; i < size / 2 + 1; i++) {
			tempHex = hextiles[size - 1][size / 2 - i].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[0], tempHex.ypoints[0]);
			bigContainHex.addPoint(tempHex.xpoints[1], tempHex.ypoints[1]);
		}
		for (int i = 0; i < size / 2; i++) {
			tempHex = hextiles[size - 2 - i][0].getHexagon();
			bigContainHex.addPoint(tempHex.xpoints[0], tempHex.ypoints[0]);
			bigContainHex.addPoint(tempHex.xpoints[1], tempHex.ypoints[1]);
		}

		bigShakeHex = new Polygon(bigContainHex.xpoints, bigContainHex.ypoints,
				bigContainHex.npoints);
	}

	// Calculates the verticies for the hextile
	private void fillverts() {
		double h = tiles_h / 2 - tileVGap, w = tiles_w / 2 - tileHGap;

		displayVerts[0][0] = (int) (x + w);
		displayVerts[1][0] = (int) (y);

		displayVerts[0][1] = (int) (x + w / 2);
		displayVerts[1][1] = (int) (y - h);

		displayVerts[0][2] = (int) (x - w / 2);
		displayVerts[1][2] = (int) (y - h);

		displayVerts[0][3] = (int) (x - w);
		displayVerts[1][3] = (int) (y);

		displayVerts[0][4] = (int) (x - w / 2);
		displayVerts[1][4] = (int) (y + h);

		displayVerts[0][5] = (int) (x + w / 2);
		displayVerts[1][5] = (int) (y + h);
		displayhex = new Polygon(displayVerts[0], displayVerts[1], 6);

		h = tiles_h / 2;
		w = tiles_w / 2;

		realVerts[0][0] = (int) (x + w);
		realVerts[1][0] = (int) (y);

		realVerts[0][1] = (int) (x + w / 2);
		realVerts[1][1] = (int) (y - h);

		realVerts[0][2] = (int) (x - w / 2);
		realVerts[1][2] = (int) (y - h);

		realVerts[0][3] = (int) (x - w);
		realVerts[1][3] = (int) (y);

		realVerts[0][4] = (int) (x - w / 2);
		realVerts[1][4] = (int) (y + h);

		realVerts[0][5] = (int) (x + w / 2);
		realVerts[1][5] = (int) (y + h);
		hexagon = new Polygon(realVerts[0], realVerts[1], 6);

		h = tiles_h / 2 - tileVGap * 4;
		w = tiles_w / 2 - tileHGap * 4;

		indVerts[0][0] = (int) (x + w);
		indVerts[1][0] = (int) (y);

		indVerts[0][1] = (int) (x + w / 2);
		indVerts[1][1] = (int) (y - h);

		indVerts[0][2] = (int) (x - w / 2);
		indVerts[1][2] = (int) (y - h);

		indVerts[0][3] = (int) (x - w);
		indVerts[1][3] = (int) (y);

		indVerts[0][4] = (int) (x - w / 2);
		indVerts[1][4] = (int) (y + h);

		indVerts[0][5] = (int) (x + w / 2);
		indVerts[1][5] = (int) (y + h);
	}

	public static Hextile[][] fillHexGrid(int lvl) throws IOException {

		Hextile[][] hextiles = null;
		BufferedReader br;
		String filename = "";
		String[] line;

		// Determines the level properties based on the number
		switch (lvl) {
		case 1:
			size = 21;
			filename = "levels/1.txt";
			break;
		}

		// Calculates the width and height of a single tile
		tiles_w = (screen_x - h_padding * 2) / (size * 3 + 1.0) * 4;
		tiles_h = (screen_y - n_padding - s_padding) / ((double) size);

		// Reads the text file and fills the grid
		br = new BufferedReader(new FileReader(filename));
		hextiles = new Hextile[size][size];

		for (int i = 0; i < size; i++) {
			line = br.readLine().split(" ");

			for (int j = 0; j < size; j++) {
				if (line[j].equals("O")) { // The space is a tile
					hextiles[i][j] = new Hextile(i, j);

				} else if (line[j].equals("B")) {
					hextiles[i][j] = null; // TODO add barrier tiles?

				} else {
					hextiles[i][j] = null; // The space is empty

				}
			}
		}

		br.close();
		return hextiles;
	}

	public Hextile(int i, int j) {
		setQ(i - size / 2);
		setR(j - size / 2);

		// This part took so long >.> Calculates the coordinates
		x = (int) ((q + size / 2) * tiles_w * 3 / 4.0 + tiles_w / 2.0)
				+ h_padding;
		y = (int) ((Math.abs(q) + (r + Math.min(0, q) + size / 2) * 2)
				* tiles_h / 2.0 + tiles_h / 2.0)
				+ n_padding;

		displayVerts = new int[6][6];
		realVerts = new int[6][6];
		indVerts = new int[6][6];

		fillverts();

	}

	// Calculates where the point is on the grid. Null if not on grid
	public static int[] hexContainCal(Hextile[][] hextiles, int x, int y) {

		int[] qr = new int[2];
		int c;
		c = (int) ((x - h_padding) / (tiles_w / 4));
		int p = Math.min(c / 3, size - 1);

		if (c < size * 3 + 1 && c >= 0 && y > n_padding
				&& y < screen_y - s_padding && x > h_padding
				&& x < screen_x - h_padding) {
			if (c % 3 == 0) {

				for (int i = Math.max(p - 1, 0); i < p + 1; i++) {
					for (int j = 0; j < hextiles[i].length; j++) {
						if (hextiles[i][j] != null
								&& hextiles[i][j].getHexagon().contains(x, y)) {

							qr[0] = hextiles[i][j].getQ();
							qr[1] = hextiles[i][j].getR();
							return qr;
						}
					}
				}

			} else {

				for (int j = 0; j < hextiles[p].length; j++) {
					if (hextiles[p][j] != null
							&& hextiles[p][j].getHexagon().contains(x, y)) {

						qr[0] = hextiles[p][j].getQ();
						qr[1] = hextiles[p][j].getR();
						return qr;
					}
				}
			}
		}
		return null;
	}

	public void draw(Graphics2D g) {
		g.drawPolygon(displayhex);

	}

	public static void drawBigContainHex(Graphics2D g) {
		g.draw(bigContainHex);
	}

	public void drawFilled(Graphics2D g) {
		g.fillPolygon(displayhex);

	}

	public void drawPlayerOcc(Graphics2D g) {
		drawFilled(g);
		// TODOCreate actual animation
	}

	public Polygon getHexagon() {
		return hexagon;
	}

	// The setter/getter methods
	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public static void fillBigContainHex(Graphics2D g) {
		g.fillPolygon(bigContainHex);
	}

	// Draws the ability indicators
	public void drawIndicatorOcc(Graphics2D g) {
		g.setColor(Color.white);
		draw(g);

		int state = (int) PlayerAbilities.getAbFocState();

		if (state == 5)
			g.drawLine(indVerts[0][state], indVerts[1][state], indVerts[0][0],
					indVerts[1][0]);
		else
			g.drawLine(indVerts[0][state], indVerts[1][state],
					indVerts[0][state + 1], indVerts[1][state + 1]);
		g.setColor(Color.gray);
	}

	public void drawCasting(Graphics2D g, double progress) {
		drawIndicatorOcc(g);
	}

	public void drawPShot(Graphics2D g) {
		// TODO
	}

	public int getX() {
		return x;

	}

	public int getY() {
		return y;

	}

	public double getTilesh() {
		return tiles_h;
	}

	public double getTilesw() {
		return tiles_w;
	}
}
