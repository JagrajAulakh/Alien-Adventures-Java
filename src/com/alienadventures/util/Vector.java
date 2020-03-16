package com.alienadventures.util;

import java.io.Serializable;

public class Vector implements Serializable {
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

	public void multiply(double a) {
		this.x *= a;
		this.y *= a;
	}

	public double magnitude() {
		return Math.hypot(this.x, this.y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
