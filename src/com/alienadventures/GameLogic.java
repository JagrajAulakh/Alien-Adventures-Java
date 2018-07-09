package com.alienadventures;

import java.awt.*;

public class GameLogic {
	private World world;
	public GameLogic() {
		world = new World();
	}

	public void update() {
		System.out.println("HERE");
		world.update();
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		world.render(g);
	}
}
