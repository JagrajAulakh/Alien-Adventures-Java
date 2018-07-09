package com.alienadventures.entity;

import com.alienadventures.World;
import com.alienadventures.util.Vector;

import java.awt.*;

public abstract class GameObject {
	protected double x, y, width, height;
	protected Vector vel, acc;

	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		vel = new Vector(0, 0);
		acc = new Vector(0, 0);
	}

	protected void setWidth(double width) { this.width = width; }
	protected void setHeight(double height) { this.height = height; }
	public Vector getVel() { return vel; }
	public void setVel(double x, double y) { this.vel.set(x, y); }
	public void setVelX(double x) { this.vel.x = x; }
	public void setVelY(double y) { this.vel.y = y; }

	protected void applyGravity() {
		acc.set(0, World.GRAVITY);
		vel.y += acc.y;
		y += vel.y;
	}

	public abstract void update();

	public abstract void render(Graphics g);
}
