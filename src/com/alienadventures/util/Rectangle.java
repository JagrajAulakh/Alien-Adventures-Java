package com.alienadventures.util;

public class Rectangle {
	private double x, y, width, height;

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(Rectangle other) {
		return !(x + width < other.x || x > other.x + other.width || y + width < other.y || y > other.y + other.height);

	}

	public boolean contains(Point other) {
		return (x < other.getX() && other.getX() <= x + width && y < other.getY() && other.getY() <= y + height);
	}

	public double getX() { return x; }
	public double getY() { return y; }
	public void setPos(double x, double y) { this.x = x; this.y = y; }
	public void setRect(double x, double y, double width, double height) { setPos(x, y); this.width = width; this.height = height; }
	public void setWidth(double width) { this.width = width; }
	public void setHeight(double height) { this.height = height; }
	public double getWidth() { return width; }
	public double getHeight() { return height; }
}
