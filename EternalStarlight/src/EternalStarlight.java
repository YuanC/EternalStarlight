import java.awt.*;

import javax.swing.*;

public class EternalStarlight {
	private static JPanel cards;
	private static CardLayout cardLayout;

	public static void main(String args[]) {

		JFrame gameScreen = new JFrame();
		GamePanel gamePanel = new GamePanel();
		MenuPanel menuPanel = new MenuPanel();
		MapPanel mapPanel = new MapPanel();

		cards = new JPanel(new CardLayout());
		cardLayout = (CardLayout) cards.getLayout();

		cards.add(menuPanel, "menuPanel");
		cards.add(gamePanel, "gamePanel");
		cards.add(mapPanel, "mapPanel");
		cardLayout.show(cards, "gamePanel");
		
		gameScreen.setSize(800, 600);
		gameScreen.setTitle("Game");
		gameScreen.setLocationRelativeTo(null);
		gameScreen.setResizable(false);
		gameScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gameScreen.add(cards);
		gameScreen.setVisible(true);
		
		gamePanel.runGameLoop();
	}
}
