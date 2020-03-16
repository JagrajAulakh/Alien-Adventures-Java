package com.alienadventures.state;

import com.alienadventures.image.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WindowState implements GameState {
	Animation growCounter;
	public WindowState() {
		growCounter = new Animation(1, false);
	}
	
	@Override
	public void update() {
	
	}
	
	@Override
	public void render(Graphics g) {
	
	}
}
