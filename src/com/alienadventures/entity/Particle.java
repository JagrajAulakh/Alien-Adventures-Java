package com.alienadventures.entity;

import com.alienadventures.Game;

import java.awt.*;

public class Particle extends GameObject {
	public Particle(double x, double y) {
		super(x, y);
		setWidth(10);
		setHeight(10);
	}

	@Override
	public void update() {
		applyGravity();
		if (y + height > Game.HEIGHT) {
			vel.y *= -0.5;
			y = Game.HEIGHT - height;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
