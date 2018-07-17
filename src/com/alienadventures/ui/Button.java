package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.Resources;

import java.awt.*;

public class Button extends ScreenObject{
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;

	public Button(float x, float y, int type) {
		this((int)(x * WIDTH), (int)(y * HEIGHT), type);
	}

	public Button(int x, int y, int type) {
		super(x, y, Resources.buttonImages.get(type*3).getWidth(), Resources.buttonImages.get(type*3).getHeight());
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g, Camera camera) {

	}
}
