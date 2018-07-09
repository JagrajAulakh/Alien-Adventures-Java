package com.alienadventures.entity;

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

	public abstract void update();
	public abstract void render(Graphics g);
}
