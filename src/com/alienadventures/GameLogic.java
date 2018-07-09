package com.alienadventures;

import com.alienadventures.input.Input;
import com.alienadventures.state.*;
import com.alienadventures.ui.LetterMaker;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameLogic {
	private GameStateManager gsm;
	public GameLogic() {
		gsm = new GameStateManager();
		gsm.push(new PlayState());
	}

	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		gsm.currentState().update();
	}

	public void render(Graphics g) {
//		g.setColor(new Color(100));
//		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
//
//		Resources.drawCentered(g, LetterMaker.makeSentence("lALIEN ADVENTURESr"), Game.WIDTH/2, Game.HEIGHT/2);
		gsm.currentState().render(g);
	}
}
