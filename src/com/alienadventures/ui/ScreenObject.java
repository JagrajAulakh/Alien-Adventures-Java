package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.util.Rectangle;

import java.awt.*;

public abstract class ScreenObject {
	protected Rectangle bounds;

	public ScreenObject(double x, double y, double width, double height) {
		this.bounds = new Rectangle(x, y, width, height);
	}

	public double getX() { return bounds.getX(); }
	public double getY() { return bounds.getY(); }
	public double getWidth() { return bounds.getWidth(); }
	public double getHeight() { return bounds.getHeight(); }
	protected void setWidth(double width) { this.bounds.setWidth(width); }
	protected void setHeight(double height) { this.bounds.setHeight(height); }

	protected int screenX(Camera camera) { return (int)(getX() - camera.getOffsetX()); }
	protected int screenY(Camera camera) { return (int)(getY() - camera.getOffsetY()); }

	public abstract void update();
	public abstract void render(Graphics g, Camera camera);
}
