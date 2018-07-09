package com.alienadventures;

import com.alienadventures.input.Input;
import com.alienadventures.ui.LetterMaker;

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
		g.setColor(new Color(100));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		world.render(g);

//		g.drawImage(LetterMaker.makeSentence("THANKS FOR WATCHING"), 100, 100, null);
		Resources.drawCentered(g, LetterMaker.makeSentence("Alien adventures"), Game.WIDTH/2, Game.HEIGHT/2);
	}
}
