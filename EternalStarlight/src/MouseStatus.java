import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MouseStatus implements MouseListener, MouseMotionListener {
	private boolean pressed, inPanel;
	private int mx, my, q, r;
	private ArrayList<Double> clicklist = new ArrayList<Double>();

	public MouseStatus() {
		pressed = false;
		inPanel = true;
		my = Hextile.n_padding
				+ (Hextile.screen_y - Hextile.n_padding - Hextile.s_padding)
				/ 2;
		mx = Hextile.h_padding + (Hextile.screen_x - Hextile.h_padding * 2) / 2;
		q = 0;
		r = 0;
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

	public int getMx() {
		return mx;
	}

	public int getMy() {
		return my;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		inPanel = false;

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;

	}

	public boolean isPressed() {
		return pressed;
	}

	public void drawClicks(Graphics2D g) {
		// TODO: Draw the clicks
	}

	public void setMouseTile(int mx, int my, Hextile[][] hextiles) {

		int[] mouseTile = Hextile.hexContainCal(hextiles, mx, my);
		if (mouseTile != null) {
			q = mouseTile[0];
			r = mouseTile[1];
		}

	}
}
