package com.alienadventures.util;
import com.alienadventures.Game;

import java.awt.*;
import java.util.ArrayList;

public class QuadTree {
	private int capacity;
	private QuadTree[] nodes;
	private ArrayList<Point> points = new ArrayList<Point>();
	private Rectangle bounds;

	public QuadTree() {
		this(0, 0, Game.WIDTH, Game.HEIGHT, 4);
	}

	public QuadTree(int cap) {
		this(0, 0, Game.WIDTH, Game.HEIGHT, cap);
	}

	public QuadTree(double x, double y, double width, double height, int cap) {
		this.capacity = cap;
		nodes = new QuadTree[4];
		bounds = new Rectangle(x, y, width, height);
	}

	public void clear() {
		nodes = new QuadTree[4];
		points.clear();
	}

	private void subdivide() {
		nodes[0] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[1] = new QuadTree(bounds.getX(), bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[2] = new QuadTree(bounds.getX(), bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[3] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		for (int i = 0; i < points.size(); i++) {
			insert(points.remove(0));
		}
	}

	public void insert(Point p) {
		if (points.size() >= capacity) {
			subdivide();
		}

		if (nodes[0] == null) {
			points.add(p);
		} else {
			for (QuadTree n : nodes) {
				if (n.bounds.contains(p)) {
					n.insert(p);
					break;
				}
			}
		}
	}

	public void show(Graphics g) {
		g.drawRect((int)bounds.getX(), (int)bounds.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
		if (nodes[0] != null) {
			for (QuadTree n : nodes) {
				n.show(g);
			}
		}
	}

	private void query(ArrayList<Point> points, Rectangle range) {
		if (bounds.intersects(range)) {
			for (Point p : this.points) {
				if (range.contains(p)) points.add(p);
			}
			if (nodes[0] != null) {
				for (QuadTree n : nodes) {
					n.query(points, range);
				}
			}
		}
	}

	public ArrayList<Point> query(Rectangle range) {
		ArrayList<Point> points = new ArrayList<Point>();
		query(points, range);
		return points;
	}
}
