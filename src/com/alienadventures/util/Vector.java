package com.alienadventures.util;

public class Vector {
	public double x, y;

	public Vector() {
		this(0, 0);
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double magnitude() {
		return Math.hypot(this.x, this.y);
	}
}
