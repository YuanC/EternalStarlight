import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Hextile {
	private int q, r;
	private boolean playerOn;

	public Hextile(int q, int r) {
		this.setQ(q);
		this.setR(r);
		setPlayerOn(false);

	}

	public static Hextile[][] fillHexGrid(int lvl) throws IOException {

		Hextile[][] hextiles = null;
		BufferedReader br;
		String filename = "";
		String[] line;
		;
		int size = 1;

		switch (lvl) {
		case 1:
			size = 11;
			filename = "levels/1.txt";
			break;
		}

		br = new BufferedReader(new FileReader(filename));

		hextiles = new Hextile[size][size];

		for (int i = 0; i < size; i++) {
			line = br.readLine().split(" ");
			for (int j = 0; j < size; j++) {
				if (line[j].equals("O")) {
					hextiles[i][j] = new Hextile(i - size / 2, j - size / 2);
				} else {

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

	public boolean isPlayerOn() {
		return playerOn;
	}

	public void setPlayerOn(boolean playerOn) {
		this.playerOn = playerOn;
	}
}
