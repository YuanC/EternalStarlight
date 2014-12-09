import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {
	private boolean gameRunning = true;
	private Hextile[][] hextiles, displayTiles;
	private int mx, my;
	private double fps;
	private MouseStatus mouse;
	private Battle_Player player;

	// The main game loop capped at ~120 frames/second
	public void runGameLoop() {
		long lastTime = System.nanoTime(), fpsTimer = 0, currentTime, updateLength;
		final int optimalFPS = 144;
		final long optimalDelta = 1000000000 / optimalFPS;
		double delta;
		int fpsCnt = 0;

		while (gameRunning) {
			currentTime = System.nanoTime();
			updateLength = currentTime - lastTime;
			lastTime = currentTime;
			delta = updateLength / 1000000000.0;

			fpsTimer += updateLength;
			fpsCnt++;

			if (fpsTimer >= 1000000000) {
				// System.out.println("(FPS: " + fpsCnt + ")");
				fps = fpsCnt;
				fpsTimer = 0;
				fpsCnt = 0;
			}

			// delta is change in time in nanoseconds
			updateGame(delta);
			repaint();

			// limits the framerate

			try {
				Thread.sleep((optimalDelta - (lastTime - System.nanoTime())) / 1000000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	//
	private void updateGame(double delta) {
		mouse.setMouseTile(mouse.getMx(), mouse.getMy(), hextiles);
		player.update(mouse, hextiles, delta);
		mouse.updateClicks(delta);
	}

	public GamePanel() throws IOException {
		mouse = new MouseStatus();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		// Fills the grid up
		hextiles = Hextile.fillHexGrid(1);

		Hextile.createBigContainHex(hextiles);

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

		player = new Battle_Player();

	}

	// Paints everything
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.fillRect(0, 0, 1280, 720);
		g2d.setColor(Color.gray);

		// Draws all the tiles in the grid
		for (int i = 0; i < hextiles.length; i++) {
			for (int j = 0; j < hextiles[i].length; j++) {
				if (hextiles[i][j] != null) {
					hextiles[i][j].draw(g2d);

					if (hextiles[i][j].getQ() == player.getQ()
							&& hextiles[i][j].getR() == player.getR()) {
						hextiles[i][j].drawFilled(g);
					}
				}
			}
		}

		g2d.setColor(Color.white);
		player.draw(g2d);

		mouse.drawClicks(g2d);

		// Draws the frames per second
		g2d.drawString("FPS: " + fps, 2, 12);
	}

	// To stop the game loop
	public void setGameRunningFalse() {
		gameRunning = false;
	}
}
