package com.alienadventures.block;

import com.alienadventures.Camera;
import com.alienadventures.entity.GameObject;

import java.awt.*;

public class Platform extends GameObject {

	public Platform(double x, double y) { this(x, y, 100, 20); }
	public Platform(double x, double y, double width, double height) {
		super(x, y);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.setColor(new Color(255, 246, 71));
		g.fillRect((int)screenX(camera), (int)screenY(camera), (int)width, (int)height);
	}
}
