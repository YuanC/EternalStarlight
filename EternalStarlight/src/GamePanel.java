import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener {
	private boolean gameRunning = true;
	private Hextile[][] hextiles, displayTiles;
	private double fps;
	private MouseStatus mouse;
	private Battle_Player player;
	private StarchildAbilities abilities;
	private int difficulty;

	public GamePanel(int health, int cdr, int attack, int difficulty)
			throws IOException {
		setFocusable(true);
		addKeyListener(this);
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

		player = new Battle_Player(health);
		abilities = new StarchildAbilities();
	}

	// The main game loop capped at ~120 frames/second (variable timestep loop)
	public void runGameLoop() {
		long lastTime = System.nanoTime(), fpsTimer = 0, currentTime, updateLength;
		final int optimalFPS = 60;
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

			// delta is change in time in seconds
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
		mouse.updateMouseTile(hextiles);
		player.update(mouse, hextiles, delta);
		mouse.updateClicks(delta);
		abilities.updateCD(delta);
		abilities.generateIndicator(player.getQ(), player.getR(), mouse.getQ(),
				mouse.getR());

	}

	// Paints everything
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(51, 51, 51));
		g2d.fillRect(0, 0, 1280, 720);

		player.drawhealth(g2d, player.getMaxHealth(), player.getHealth());

		g2d.setColor(new Color(51, 51, 51));
		Hextile.fillBigContainHex(g2d);

		g2d.setColor(Color.gray);

		// Draws all the tiles in the grid
		for (int i = 0; i < hextiles.length; i++) {
			for (int j = 0; j < hextiles[i].length; j++) {
				if (hextiles[i][j] != null) {
					hextiles[i][j].draw(g2d);

					// Draws the player indicator
					if (hextiles[i][j].getQ() == player.getQ()
							&& hextiles[i][j].getR() == player.getR()) {

						hextiles[i][j].drawPlayerOcc(g2d);
					}

					int[] tArr = new int[2];
					tArr[0] = hextiles[i][j].getQ();
					tArr[1] = hextiles[i][j].getR();

					// TODO Draws the ability area indicators
					if (search2DArray(abilities.getIndicator(), tArr)) {

						hextiles[i][j].drawPlayerOcc(g2d);
					}
				}
			}
		}

		abilities.drawIndicator(g2d, player.getQ(), player.getR(),
				mouse.getQ(), mouse.getR(), player.getX(), player.getY(),
				mouse.getMx(), mouse.getMy());

		g2d.setColor(Color.white);
		Hextile.drawBigContainHex(g2d);
		player.draw(g2d);
		g2d.setColor(Color.white);
		mouse.drawClicks(g2d);

		// Draws the frames per second
		g2d.drawString("FPS: " + fps, 2, 12);
		abilities.drawCooldown(g2d);
	}

	public boolean search2DArray(int[][] arr2d, int[] arr) {

		if (arr2d == null || arr.length == 0)
			return false;

		for (int i = 0; i < arr2d.length; i++) {
			if (arr2d[i][0] == arr[0] && arr2d[i][1] == arr[1])
				return true;
		}

		return false;

	}

	// To stop the game loop
	public void setGameRunningFalse() {
		gameRunning = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			abilities.setFoc(0);
			break;

		case KeyEvent.VK_W:
			abilities.setFoc(1);
			break;

		case KeyEvent.VK_E:
			abilities.setFoc(2);
			break;

		case KeyEvent.VK_R:
			abilities.setFoc(3);
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			abilities.setFoc(0);
			break;
		case KeyEvent.VK_W:
			abilities.setFoc(1);
			break;
		case KeyEvent.VK_E:
			abilities.setFoc(2);
			break;
		case KeyEvent.VK_R:
			abilities.setFoc(3);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
