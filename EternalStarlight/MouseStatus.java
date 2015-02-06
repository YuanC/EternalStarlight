import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class MouseStatus implements MouseListener, MouseMotionListener {
	private boolean pressed, inPanel;
	private int mx, my, q, r;
	private ArrayList<double[]> clicklist;
	private double clickrad;

	public MouseStatus() {
		pressed = false;
		inPanel = true;
		my = Hextile.n_padding
				+ (Hextile.screen_y - Hextile.n_padding - Hextile.s_padding)
				/ 2;
		mx = Hextile.h_padding + (Hextile.screen_x - Hextile.h_padding * 2) / 2;
		q = 0;
		r = 0;
		clicklist = new ArrayList<double[]>();
		clickrad = 10;
	}

	// updates the radius of the click until it disappears
	public void updateClicks(double delta) {
		for (int i = 0; i < clicklist.size(); i++) {
			clicklist.get(i)[2] -= delta * 4;
			if (clicklist.get(i)[2] <= 0) {
				clicklist.remove(i);
				i--;
			}
		}
	}

	// Drawing the clicks
	public void drawClicks(Graphics2D g) {

		double[] t;
		double r;

		if (pressed) {
			g.drawOval((int) mx - 10, (int) my - 5, 20, 10);
		}

		for (int i = 0; i < clicklist.size(); i++) {
			t = clicklist.get(i);
			r = clickrad * t[2];
			g.drawOval((int) (t[0] - r), (int) (t[1] - r / 2), (int) (r * 2),
					(int) r);
		}

	}

	public void updateMouseTile(Hextile[][] hextiles) {

		int[] mouseTile = Hextile.hexContainCal(hextiles, mx, my);
		if (mouseTile != null) {
			q = mouseTile[0];
			r = mouseTile[1];
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		inPanel = false;

	}

	// executes actions caused by mouse click
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			pressed = true;
		} else if (SwingUtilities.isLeftMouseButton(e)) {

			// When an ability is used
			if (PlayerAbilities.getFoc() != 4) {
				PlayerAbilities.startCD(PlayerAbilities.getFoc());

				if (PlayerAbilities.getFoc() == 0) {
					ProjectileHandler.addPlayerShot(Battle_Player.getX(),
							Battle_Player.getY(),
							StarchildAbilities.getqAngle());
				} else {
					int[][] indicator = StarchildAbilities.getIndicator();

					for (int i = 0; i < indicator.length; i++) {
						SpawnAndCast.addCast(PlayerAbilities.getFoc(),
								indicator[i][0], indicator[i][1]);
					}
				}
			}
		}
		PlayerAbilities.setFoc(4);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			pressed = false;
			double[] i = new double[3];

			i[0] = mx;
			i[1] = my;
			i[2] = 1;
			clicklist.add(i);

		}
	}

	public boolean isPressed() {
		return pressed;
	}

	public int getMx() {
		return mx;
	}

	public int getMy() {
		return my;
	}

	public int getR() {
		return r;
	}

	public int getQ() {
		return q;
	}
}
