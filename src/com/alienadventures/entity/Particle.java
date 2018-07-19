package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.World;

import java.awt.*;

public class Particle extends GameObject {
	private int life;
	private Color color;

	public Particle(double x, double y) {
		this(x, y, null);
	}

	public Particle(double x, double y, Color color) {
		super(x, y);
		setWidth(10);
		setHeight(10);
		setVel(Math.random() * 10 - 5, Math.random() * 10 - 5);
		life = (int)(Math.random() * 60);

		if (color == null) {
			double r = Math.random();
			if (r <= 0.4) {
				this.color = new Color(107, 190, 0, 255);
			} else if (r <= 0.7) {
				this.color = new Color(176, 250, 20, 255);
			} else {
				this.color = new Color(70, 128, 0, 255);
			}
		} else {
			this.color = color;
		}
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
