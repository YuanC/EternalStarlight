import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hextile {
	private int q, r, x, y;
	public static final int n_padding = 140, s_padding = 40, h_padding = 100;
	public static int tiles_h, tiles_w, tiles_l, size;

	public Hextile(int q, int r) {
		this.setQ(q);
		this.setR(r);
	}

	public static Hextile[][] fillHexGrid(int lvl) throws IOException {

		Hextile[][] hextiles = null;
		BufferedReader br;
		String filename = "";
		String[] line;

		// Determines the level properties
		switch (lvl) {
		case 1:
			size = 11;
			filename = "levels/1.txt";
			break;
		}

		// Reads the text file and fills the grid
		br = new BufferedReader(new FileReader(filename));

		hextiles = new Hextile[size][size];

		for (int i = 0; i < size; i++) {
			line = br.readLine().split(" ");
			for (int j = 0; j < size; j++) {
				if (line[j].equals("O")) {
					hextiles[i][j] = new Hextile(i - size / 2, j - size / 2);
				} else if (line[j].equals("B")) {
					hextiles[i][j] = null;
				} else {
					hextiles[i][j] = null;
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

	public void draw(Graphics g) {
		// g.draw
	}
}
