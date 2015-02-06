import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	private boolean gameRunning = true;
	private static Hextile[][] hextiles;
	private double fps;
	private MouseStatus mouse;
	private StarchildAbilities abilities;
	private int difficulty;
	private static Battle_Player player;
	private static ProjectileHandler projectiles;
	private static EnemyHandler enemies;
	private static SpawnAndCast spawncast;
	private static PlayerSpells spells;

	private long lastTime = System.nanoTime(), fpsTimer = 0, currentTime,
			updateLength;
	private final int optimalFPS = 60;
	private final long optimalDelta = 1000000000 / optimalFPS;
	private double delta;
	private int fpsCnt = 0;
	private Timer timer;
	private boolean showInstructions;
	private ImageIcon instructions;

	public GamePanel(int health, int cdr, int attack, int difficulty)
			throws IOException {
		setFocusable(true);
		addKeyListener(this);
		mouse = new MouseStatus();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		// Fills the grid up and creades the bounding polygon
		hextiles = Hextile.fillHexGrid(1);
		Hextile.createBigContainHex(hextiles);

		// sets the instrcutions image and variable
		showInstructions = false;
		instructions = new ImageIcon("lvlimages/Instructions_LVL.png");

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
		abilities = new StarchildAbilities(cdr);
		projectiles = new ProjectileHandler();
		enemies = new EnemyHandler(difficulty, hextiles);
		spawncast = new SpawnAndCast();
		spells = new PlayerSpells(attack);

		timer = new Timer(15, this);
		timer.start();
	}

	// Checks for win conditions
	public void winCheck() {
		if (player.getHealth() <= 0) {
			System.out.println("dead");
			gameRunning = false;
			
			myFrame.c.removeAll();
			myFrame.c.add(myFrame.main,BorderLayout.CENTER);
			myFrame.map = new mapPanel();
			menuPanel.myTimer.start();
			myFrame.game.setVisible(false);
			myFrame.main.setVisible(true);
			
			
		}
		if (enemies.getWaveCnt() + enemies.getListCnt()
				+ spawncast.getSpawnSize() <= 0) {

			// DANIEL DOES HIS HOCUS POCUS MAJIC STUFF HERE

			System.out.println("You win");
			gameRunning = false;
			mapPanel.ap+=difficulty*2;
			
			myFrame.c.removeAll();
			timer.stop();
			myFrame.game.setVisible(false);
			myFrame.c.add(myFrame.map,BorderLayout.CENTER);
			myFrame.map.mapTimer.start();
			myFrame.map.setVisible(true);
			
			
		}

	}

	// Updates all the units in the game, where delta is change in time
	private void updateGame(double delta) {
		if (!showInstructions) {
			mouse.updateMouseTile(hextiles);
			player.update(mouse, hextiles, delta);
			mouse.updateClicks(delta);
			abilities.updateCD(delta);
			projectiles.update(delta, Hextile.getBigContainHex(), hextiles);
			spawncast.update(delta);
			spells.update(delta);
			enemies.update(delta);
			winCheck();
		}
	}

	// Paints everything
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (showInstructions) {
			g2d.drawImage(instructions.getImage(), 0, 0, null);
		} else {
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

						int[] tArr = new int[2];
						tArr[0] = hextiles[i][j].getQ();
						tArr[1] = hextiles[i][j].getR();

						// Draws the enemy projectiles
						g2d.setColor(new Color(35, 35, 35));
						if (search2DArray(projectiles.getEShots(), tArr)) {
							hextiles[i][j].drawEShot(g2d, 0);
						}

						if (search2DArray(EnemyHandler.getList(1), tArr)
								|| search2DArray(EnemyHandler.getList(2), tArr)
								|| search2DArray(EnemyHandler.getList(3), tArr)) {
							hextiles[i][j].drawEShot(g2d, 0);
						}
						g2d.setColor(Color.gray);
						// Draws the player indicator
						if (hextiles[i][j].getQ() == player.getQ()
								&& hextiles[i][j].getR() == player.getR()) {

							hextiles[i][j].drawPlayerOcc(g2d);
						}

						// Draws the casting indicators
						g2d.setColor(Color.white);
						for (int k = 1; k < 4; k++) {
							if (search2DArray(spawncast.getList(k), tArr)) {
								hextiles[i][j].drawCasting(g2d,
										spawncast.getProgress(k));
							}
						}

						// Drawing the enemy spawning indicators
						for (int k = 1; k < 4; k++) {
							if (search2DArray(spawncast.getSpawnList(), tArr)) {
								hextiles[i][j].drawSpawning(g2d,
										spawncast.getSpawningProgress());
							}
						}

						// Draws the player projectiles
						g2d.setColor(Color.gray);
						if (search2DArray(projectiles.getPShots(), tArr)) {
							hextiles[i][j].drawPShot(g2d, 0);
						}

						// Drawing the spell animations
						for (int k = 1; k < 4; k++) {
							if (search2DArray(spells.getList(k), tArr)) {
								hextiles[i][j].drawSpells(g2d, k);
							}
						}

						// TODO Draws the ability area indicators
						if (search2DArray(abilities.getIndicator(), tArr)) {
							hextiles[i][j].drawIndicatorOcc(g2d);
						}

					}
				}
			}

			// draws the indicators for the abilities
			abilities.drawIndicator(g2d, hextiles, player.getQ(),
					player.getR(), mouse.getQ(), mouse.getR(), player.getX(),
					player.getY(), mouse.getMx(), mouse.getMy());

			g2d.setColor(Color.white);
			Hextile.drawBigContainHex(g2d);

			// draws all the abilities and the projectiles
			enemies.draw(g2d);
			projectiles.draw(g2d);

			// draws the player and all the clicks
			player.draw(g2d);
			g2d.setColor(Color.white);
			mouse.drawClicks(g2d);

			// Draws the frames per second
			g2d.drawString("FPS: " + fps, 2, 20);
			abilities.drawCooldown(g2d);

		}
	}

	// searches for an array in a specified 2d array
	public boolean search2DArray(int[][] arr2d, int[] arr) {

		if (arr2d == null || arr.length == 0) {
			return false;
		}

		for (int i = 0; i < arr2d.length; i++) {
			if (arr2d[i][0] == arr[0] && arr2d[i][1] == arr[1])
				return true;
		}

		return false;

	}

	public static Hextile[][] getHextiles() {
		return hextiles;
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
		case KeyEvent.VK_I:
			showInstructions = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_I) {
			showInstructions = false;
		}
	}

	// THe modified game loop based on timer
	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameRunning) {
			
			this.requestFocus();

			currentTime = System.nanoTime();
			updateLength = currentTime - lastTime;
			lastTime = currentTime;
			delta = updateLength / 1000000000.0;

			fpsTimer += updateLength;
			fpsCnt++;

			if (fpsTimer >= 1000000000) {
				fps = fpsCnt;
				fpsTimer = 0;
				fpsCnt = 0;
			}

			// delta is change in time in seconds
			updateGame(delta);
			repaint();

			// limits the framerate
			/*
			 * try { Thread.sleep((optimalDelta - (lastTime -
			 * System.nanoTime())) / 1000000); } catch (Exception e) {
			 * e.printStackTrace(); }
			 */

		}

	}

	// The main game loop capped at ~120 frames/second (variable timestep loop)
	public void runGameLoop() {
		/*
		 * if (gameRunning) { long lastTime = System.nanoTime(), fpsTimer = 0,
		 * currentTime, updateLength; final int optimalFPS = 60; final long
		 * optimalDelta = 1000000000 / optimalFPS; double delta; int fpsCnt = 0;
		 * 
		 * while (gameRunning) { currentTime = System.nanoTime(); updateLength =
		 * currentTime - lastTime; lastTime = currentTime; delta = updateLength
		 * / 1000000000.0;
		 * 
		 * fpsTimer += updateLength; fpsCnt++;
		 * 
		 * if (fpsTimer >= 1000000000) { fps = fpsCnt; fpsTimer = 0; fpsCnt = 0;
		 * }
		 * 
		 * // delta is change in time in seconds updateGame(delta); repaint();
		 * 
		 * // limits the framerate try { Thread.sleep((optimalDelta - (lastTime
		 * - System.nanoTime())) / 1000000); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * } }
		 */
	}

}
