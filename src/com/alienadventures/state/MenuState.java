package com.alienadventures.state;

import com.alienadventures.Game;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState implements GameState {

	public MenuState() {

	}

	@Override
	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(7954129));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
}
