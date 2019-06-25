package com.alienadventures.ui;

import com.alienadventures.Camera;

import java.awt.*;
import java.util.ArrayList;

public class ButtonManager {

	enum State {
		NORMAL,
		HOVER,
		CLICKED
	}

	private ArrayList<Button> buttons;
	private ArrayList<Button> toRemove;
	private ArrayList<ObjectListener> listeners;

	public ButtonManager() {
		buttons = new ArrayList<Button>();
		toRemove = new ArrayList<Button>();
		listeners = new ArrayList<ObjectListener>();
	}

	public void addListener(ObjectListener o) {
		listeners.add(o);
	}

	public void add(Button b) {
		buttons.add(b);
	}

	public void remove(Button b) {
		toRemove.add(b);
	}

	public void update() {
		for (Button b : toRemove) {
			buttons.remove(b);
		}
		for (Button b : buttons) {
			b.update();
			if (b.getState() == State.CLICKED) {
				for (ObjectListener l : listeners) {
					l.clicked(b);
				}
			} else if (b.getState() == State.HOVER) {
				for (ObjectListener l : listeners) {
					l.hovered(b);
				}
			}
		}
	}

	public void render(Graphics g, Camera camera) {
		for (Button b : buttons) {
			b.render(g, camera);
		}
	}
}
