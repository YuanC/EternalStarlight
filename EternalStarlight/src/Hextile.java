import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hextile {
	private int q, r, x, y;
	public static final int n_padding = 130, s_padding = 50, h_padding = 100,
			screen_x = 1280, screen_y = 720, tileHGap = 2, tileVGap = 1;
	private static double tiles_h, tiles_w;
	public static int size;
	public static int[] mouseTile;
	private int[][] verts;
	private Polygon hexagon;

	public Hextile(int i, int j) {
		setQ(i - size / 2);
		setR(j - size / 2);

		// This part took so long >.> Calculates the coordinates
		x = (int) ((q + size / 2) * tiles_w * 3 / 4.0 + tiles_w / 2.0)
				+ h_padding;
		y = (int) ((Math.abs(q) + (r + Math.min(0, q) + size / 2) * 2)
				* tiles_h / 2.0 + tiles_h / 2.0)
				+ n_padding;

		verts = new int[6][6];

		fillverts();

	}

	private void fillverts() {
		double h = tiles_h / 2 - tileVGap, w = tiles_w / 2 - tileHGap;

		verts[0][0] = (int) (x + w);
		verts[1][0] = (int) (y);

		verts[0][1] = (int) (x + w / 2);
		verts[1][1] = (int) (y - h);

		verts[0][2] = (int) (x - w / 2);
		verts[1][2] = (int) (y - h);

		verts[0][3] = (int) (x - w);
		verts[1][3] = (int) (y);

		verts[0][4] = (int) (x - w / 2);
		verts[1][4] = (int) (y + h);

		verts[0][5] = (int) (x + w / 2);
		verts[1][5] = (int) (y + h);
		hexagon = new Polygon(verts[0], verts[1], 6);
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
					hextiles[i][j] = null; // TODO add barrier tiles

				} else {
					hextiles[i][j] = null; // The space is empty

				}
			}
		}

		br.close();
		return hextiles;
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

	public void draw(Graphics2D g) {
		// TODO draw methods (sprite? polygon?)
		if (mouseTile != null && q == mouseTile[0] && r == mouseTile[1])
			g.fillPolygon(hexagon);
		else
			g.drawPolygon(hexagon);
	}

	// Calculates where the point is on the grid. Null if not on grid
	public static int[] hexContainCal(Hextile[][] hextiles, int x, int y) {

		int[] qr = new int[2];
		int c;
		c = (int) ((x - h_padding) / (tiles_w / 4));
		int p = Math.min(c / 3, size - 1);

		if (c < size * 3 + 1 && c >= 0 /*
										 * && y > n_padding && y > screen_y -
										 * s_padding && x > h_padding && x <
										 * screen_x - h_padding
										 */) {
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

	public static void setMouseTile(int mx, int my, Hextile[][] hextiles) {

		int[] qr = hexContainCal(hextiles, mx, my);

		if (qr != null)
			mouseTile = qr;
		else
			mouseTile = null;
	}

	public Polygon getHexagon() {
		return hexagon;
	}

}
