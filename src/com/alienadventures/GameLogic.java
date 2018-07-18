package com.alienadventures;

import com.alienadventures.input.Input;
import com.alienadventures.state.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameLogic {
	private GameStateManager gsm;
	public GameLogic() {
		gsm = new GameStateManager();
//		gsm.push(new MenuState(true));
		gsm.push(new PlayState());
	}

	public void update() {
		if (gsm.currentState() instanceof MenuState) {
			if (Input.keyUpOnce(KeyEvent.VK_ENTER)) {
				gsm.set(new PlayState());
			}
		} else if (gsm.currentState() instanceof PlayState) {
			if (Input.keyUpOnce(KeyEvent.VK_ENTER)) {
				gsm.set(new MenuState(false));
			}
		}
		gsm.currentState().update();
	}

	public void render(Graphics g) {
		gsm.currentState().render(g);
	}
}
