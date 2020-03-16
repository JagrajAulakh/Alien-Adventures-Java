package com.alienadventures.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	private enum State {
		PRESSED,
		RELEASED,
		KEYDOWN,
		KEYUP
	}

	private static State[] keys = new State[KeyEvent.KEY_LAST];

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
