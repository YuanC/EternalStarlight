import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hextile {
	private int q, r, x, y;
	public static final int n_padding = 130, s_padding = 50, h_padding = 100,
			screen_x = 1280, screen_y = 720;
	private static double tiles_h, tiles_w;
	public static int size;

	public Hextile(int i, int j) {
		setQ(i - size / 2);
		setR(j - size / 2);

		// This part took so long >.> Calculates the coordinates
		x = (int) ((q + size / 2) * tiles_w * 3 / 4.0 + tiles_w / 2.0)
				+ h_padding;
		y = (int) ((Math.abs(q) + (r + Math.min(0, q) + size / 2) * 2)
				* tiles_h / 2.0 + tiles_h / 2.0)
				+ n_padding;

		// System.out.println(q + " " + r);
	}

	public static Hextile[][] fillHexGrid(int lvl) throws IOException {

		Hextile[][] hextiles = null;
		BufferedReader br;
		String filename = "";
		String[] line;

		// Determines the level properties based on the number
		switch (lvl) {
		case 1:
			size = 11;
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
		g.drawString("$", x, y);
	}
}
