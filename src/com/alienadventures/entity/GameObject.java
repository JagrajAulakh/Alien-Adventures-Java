package com.alienadventures.entity;

import com.alienadventures.Camera;
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

	protected void setWidth(double width) {
		this.width = width;
	}

	protected void setHeight(double height) {
		this.height = height;
	}

	public Vector getVel() { return vel; }

	public void setVel(double x, double y) { this.vel.set(x, y); }

	public void setVelX(double x) { this.vel.x = x; }

	public void setVelY(double y) { this.vel.y = y; }

	public void setAccX(double x) { this.vel.x = x; }

	public void setAccY(double y) { this.vel.y = y; }

	public double getX() { return x; }

	public double getY() { return y; }

	public double getWidth() { return width; }

	public double getHeight() { return height; }

	protected void applyVel() { applyVel(false); }
	protected void applyVel(boolean friction) {
		if (friction) acc.x -= vel.x * World.FRICTION;
		vel.x += acc.x;
		vel.y += acc.y;
		x += vel.x;
		y += vel.y;
	}

	protected void applyGravity() {
		applyGravity(1);
	}

	protected void applyGravity(double factor) {
		acc.set(0, World.GRAVITY * factor);
	}

	public double screenX(Camera offset) { return offset == null ? this.x : this.x - offset.getOffsetX(); }

	public double screenY(Camera offset) { return offset == null ? this.y : this.y - offset.getOffsetY(); }

	public abstract void update();

	public abstract void render(Graphics g, Camera offset);
}
