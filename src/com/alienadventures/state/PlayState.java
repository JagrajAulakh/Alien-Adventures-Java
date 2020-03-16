package com.alienadventures.state;

import com.alienadventures.World;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayState implements GameState {

	public static World world;

	public PlayState() {
		world = new World();
	}

	@Override
	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		world.update();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}
}
