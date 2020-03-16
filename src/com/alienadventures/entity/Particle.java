package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.World;

import java.awt.*;

public class Particle extends GameObject {
	private int life;
	private Color color;

	public Particle(double x, double y) {
		this(x, y, null);
	}

	public Particle(double x, double y, int type) {
		super(x, y, 10, 10);
		setVel(Math.random() * 10 - 5, Math.random() * 10 - 5);
		life = randomLife();
		color = makeColor(type);
	}

	public Particle(double x, double y, Color color) {
		super(x, y, 10, 10);
		setVel(Math.random() * 10 - 5, Math.random() * 10 - 5);
		life = randomLife();
		this.color = color;
	}

	private int randomLife() { return (int)(Math.random() * 60); }

	private Color makeColor(int type) {
		Color c;
		double r = Math.random();
		if (r <= 0.4) {
			c = Resources.playerColors[type][0];
		} else if (r <= 0.7) {
			c = Resources.playerColors[type][1];
		} else {
			c = Resources.playerColors[type][2];
		}
		return c;
	}

	public boolean dead() {
		return life <= 0;
	}

	@Override
	public void update() {
		life--;
		applyGravity(0.2);
		applyVelY();
		applyVelX(0);
		if (y + height > Game.HEIGHT) {
			y = Game.HEIGHT - height;
			vel.y = vel.y * -0.8;
			acc.y = 0;
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.setColor(color);
		g.fillRect((int)screenX(camera), (int)screenY(camera), (int)width, (int)height);
	}
}
