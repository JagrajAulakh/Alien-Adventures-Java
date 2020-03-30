package com.alienadventures.ui;

import com.alienadventures.Camera;
import static com.alienadventures.ui.Button.State;

import java.awt.*;
import java.util.ArrayList;

public class ButtonManager implements Manager {

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

	@Override
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
			}
			if (b.getState() == State.HELD) {
				for (ObjectListener l : listeners) {
					l.held(b);
				}
			} else if (b.getState() == State.HOVER) {
				for (ObjectListener l : listeners) {
					l.hovered(b);
				}
			}
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		for (Button b : buttons) {
			b.render(g, camera);
		}
	}
}
