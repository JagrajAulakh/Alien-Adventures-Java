package com.alienadventures;

import com.alienadventures.entity.GameObject;
import com.alienadventures.entity.Particle;
import com.alienadventures.util.Vector;

public class Camera {
	private Vector offset;

	public Camera() {
		this(0, 0);
	}

	public Camera(double x, double y) {
		offset = new Vector(x, y);
	}

	public double getOffsetX() {
		return offset.getX();
	}

	public double getOffsetY() {
		return offset.getY();
	}

	public void setY(double y) { offset.y = y; }
	public void setX(double x) { offset.x = x; }

	public void centerOn(GameObject obj) { centerOn(obj, true); }
	public void centerOn(GameObject obj, boolean interpolation) {
		centerOn(obj.getX(), obj.getY(), interpolation);
	}
	public void centerOn(double wx, double wy, boolean interpolation) {
		double goBy = interpolation ? 1.0/10 : 1.0;
		double newOffsetX = wx - Game.WIDTH/2;
		double newOffsetY = wy - Game.HEIGHT/2;
		offset.x += (newOffsetX - offset.x)*goBy;
		offset.y += (newOffsetY - offset.y)*goBy;
	}
}
