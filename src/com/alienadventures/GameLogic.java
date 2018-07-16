package com.alienadventures;

import com.alienadventures.input.Input;
import com.alienadventures.state.*;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameLogic {
	private GameStateManager gsm;
	public GameLogic() {
		gsm = new GameStateManager();
//		gsm.push(new MenuState(false));
		gsm.push(new PlayState());
	}

	public void update() {
		gsm.currentState().update();
	}

	public void render(Graphics g) {
		gsm.currentState().render(g);
	}
}
