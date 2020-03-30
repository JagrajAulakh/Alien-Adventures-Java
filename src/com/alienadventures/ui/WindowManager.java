package com.alienadventures.ui;

import com.alienadventures.Camera;

import java.awt.*;
import java.util.ArrayList;

public class WindowManager implements Manager {
	private ArrayList<Window> windows;
	
	public WindowManager() {
		windows = new ArrayList<Window>();
	}
	
	public void add(Window w) {
		windows.add(w);
	}
	
	public boolean isEmpty() {
		return windows.size() == 0;
	}
	
	@Override
	public void update() {
		for (int i = windows.size()-1; i >= 0; i--) {
			windows.get(i).update();
			if (windows.get(i).isDead()) windows.remove(i);
		}
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		for (Window w : windows) {
			w.render(g, camera);
		}
	}
}
