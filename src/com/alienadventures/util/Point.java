package com.alienadventures.util;

import java.io.Serializable;

public class Point implements Serializable {

	private double x, y;
	private Object data;

	public Point(double x, double y) { this(x, y, null); }
	public Point(double x, double y, Object data) {
		this.x = x;
		this.y = y;
		this.data = data;
	}

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public Object getData() { return data; }
}
