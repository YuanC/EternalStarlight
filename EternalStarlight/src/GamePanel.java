import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {
	private boolean gameRunning = true;
	private Hextile[][] hextiles;

	// The main game loop capped at ~120 fps
	public void runGameLoop() {
		long lastTime = System.nanoTime(), fpsTimer = 0, currentTime, updateLength;
		final int optimalFPS = 120;
		final long optimalDelta = 1000000000 / optimalFPS;
		double delta;
		int fpsCnt = 0;

		while (gameRunning) {
			currentTime = System.nanoTime();
			updateLength = currentTime - lastTime;
			lastTime = currentTime;
			delta = updateLength / ((double) optimalFPS);

			fpsTimer += updateLength;
			fpsCnt++;

			if (fpsTimer >= 1000000000) {
				System.out.println("(FPS: " + fpsCnt + ")");
				fpsTimer = 0;
				fpsCnt = 0;
			}

			// delta is change in time in nanoseconds
			updateGame(delta);
			repaint();

			try {
				Thread.sleep((optimalDelta - (lastTime - System.nanoTime())) / 1000000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//
	private void updateGame(double delta) {

	}

	public GamePanel() throws IOException {

		// Fills the grid up
		hextiles = Hextile.fillHexGrid(1);

		// debug code for saving the grid data
		for (int i = 0; i < hextiles.length; i++) {
			for (int j = 0; j < hextiles[i].length; j++) {
				if (hextiles[i][j] == null) {
					System.out.print("X");
				} else {
					System.out.print("O");
				}
			}
			System.out.println();
		}
	}

	public void paintComponent(Graphics g) {
		// super.paint(g);

		for (Hextile[] hr : hextiles) {
			for (Hextile ht : hr) {
				ht.draw(g);
			}
		}
	}

	public void setGameRunningFalse() {
		gameRunning = false;
	}
}
