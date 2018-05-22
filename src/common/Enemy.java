package common;

/**
 * A player with AI
 * 
 *
 */
public class Enemy extends Player {

	public Enemy(int x, int y, Kind identifier, Direction d) {
		super(x, y, identifier, d);
	}

	public Enemy(int x, int y, Kind identifier) {
		this(x, y, identifier, Direction.DOWN);
	}

	public void act(Map m, State s) {
		if (Math.random() > 1. / 30)
			return;
		int dir = (int) (Math.random() * 4);
		if (dir < 2) {
			this.moveX(dir == 0, m);
		} else {
			this.moveY(dir % 2 == 0, m);
		}
		Projectile p = this.fire(m);
		if (p != null)
			s.items.add(p);
	}

}
