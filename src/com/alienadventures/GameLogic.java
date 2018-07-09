package com.alienadventures;

import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameLogic {
	private World world;
	public GameLogic() {
		world = new World();
	}

	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		world.update();
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		world.render(g);
	}
}
