package com.alienadventures.entity;

import com.alienadventures.Game;

import java.awt.*;

public class Particle extends GameObject {
	public Particle(double x, double y) {
		super(x, y);
		setWidth(10);
		setHeight(10);
		setVel(Math.random()*10 - 5, Math.random()*-3-2);
	}

	@Override
	public void update() {
		applyGravity(0.2);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int)x, (int)y, (int)width, (int)height);
	}
}
