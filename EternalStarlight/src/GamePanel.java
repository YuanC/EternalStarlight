import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {
	private boolean gameRunning = true;
	private Hextile[][] hexTiles;

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

			updateGame(delta);
			repaint();

			try {
				Thread.sleep((optimalDelta - (lastTime - System.nanoTime())) / 1000000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updateGame(double delta) {

	}

	public GamePanel() throws IOException {
		hexTiles = Hextile.fillHexGrid(1);
		

	}

	public void paintComponent(Graphics g) {
		// super.paint(g);
		g.drawLine(10, 10, 50, 50);
	}

	public void setGameRunningFalse() {
		gameRunning = false;
	}
}
