import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseStatus implements MouseListener, MouseMotionListener {
	private boolean clicked, inPanel;
	private int mx, my;

	public MouseStatus() {
		clicked = false;
		inPanel = true;
		mx = 0;
		my = 0;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
