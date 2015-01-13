public class Enemy {
	double x, y, speed, angle, health;
	int q, r;

	public Enemy(double speed, double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void update(double delta, Hextile[][] hextiles) {
		x = x + Math.cos(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));
		y = y + Math.sin(angle) * speed * delta
				* (1 - 0.5 * Math.abs(Math.sin(angle)));

		updateTilePos(hextiles);
	}

	private void updateTilePos(Hextile[][] hextiles) {
		int[] pos;
		pos = Hextile.hexContainCal(hextiles, (int) x, (int) y);

		if (pos != null) {
			q = pos[0];
			r = pos[1];
		}
	}

}
