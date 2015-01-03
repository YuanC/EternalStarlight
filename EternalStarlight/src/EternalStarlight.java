import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class EternalStarlight {
	private static JPanel cards;
	private static CardLayout cardLayout;

	public static void main(String args[]) throws IOException {

		// Initializes the JFrame and the JPanels
		JFrame gameScreen = new JFrame();
		GamePanel gamePanel = new GamePanel();
		MenuPanel menuPanel = new MenuPanel();
		MapPanel mapPanel = new MapPanel();

		// Adds the panels to the cardlayout
		cards = new JPanel	(new CardLayout());
		cardLayout = (CardLayout) cards.getLayout();

		cards.add(menuPanel, "menuPanel");
		cards.add(gamePanel, "gamePanel");
		cards.add(mapPanel, "mapPanel");
		cardLayout.show(cards, "gamePanel");

		// Sets up the JFrame
		gameScreen.setSize(1280, 720);
		gameScreen.setTitle("Eternal Starlight");
		gameScreen.setLocationRelativeTo(null);
		gameScreen.setResizable(false);
		gameScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gameScreen.add(cards);
		gameScreen.setVisible(true);

		gamePanel.runGameLoop();
	}
}
