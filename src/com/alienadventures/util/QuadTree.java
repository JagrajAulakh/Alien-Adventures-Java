package com.alienadventures.util;
import com.alienadventures.Camera;
import com.alienadventures.Game;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class QuadTree implements Serializable {
	private int capacity;
	private QuadTree[] nodes;
	private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	private Rectangle bounds;

	public QuadTree() {
		this(4);
	}
	public QuadTree(int cap) {
		this(0, 0, Game.WIDTH, Game.HEIGHT, cap);
	}
	public QuadTree(double x, double y, double width, double height) {
		this(x, y, width, height, 4);
	}
	public QuadTree(double x, double y, double width, double height, int cap) {
		this.capacity = cap;
		nodes = new QuadTree[4];
		bounds = new Rectangle(x, y, width, height);
	}

	public void clear() {
		nodes = new QuadTree[4];
		rectangles.clear();
	}

	private void subdivide() {
		nodes[0] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[1] = new QuadTree(bounds.getX(), bounds.getY(), bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[2] = new QuadTree(bounds.getX(), bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		nodes[3] = new QuadTree(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2, bounds.getWidth() / 2, bounds.getHeight() / 2, capacity);
		for (int i = 0; i < rectangles.size(); i++) {
			insert(rectangles.remove(0));
		}
	}

	public void insert(Rectangle r) {
		if (rectangles.size() >= capacity && nodes[0] == null) {
			subdivide();
		}

		if (nodes[0] == null) {
			rectangles.add(r);
		} else {
			boolean found = false;
			for (QuadTree n : nodes) {
				if (n.bounds.contains(r)) {
					n.insert(r);
					found = true;
					break;
				}
			}
			if (!found) rectangles.add(r);
		}
	}

	public void show(Graphics g, Camera camera) {
		g.drawRect((int)((int)bounds.getX()-camera.getOffsetX()), (int)(bounds.getY()-camera.getOffsetY()), (int)bounds.getWidth(), (int)bounds.getHeight());
		if (nodes[0] != null) {
			for (QuadTree n : nodes) {
				n.show(g, camera);
			}
		}
	}

	private void query(ArrayList<Rectangle> rectangles, Rectangle range) {
		if (bounds.intersects(range)) {
			for (Rectangle r : this.rectangles) {
				if (range.intersects(r)) rectangles.add(r);
			}
			if (nodes[0] != null) {
				for (QuadTree n : nodes) {
					n.query(rectangles, range);
				}
			}
		}
	}

	public ArrayList<Rectangle> query(Rectangle range) {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		query(rectangles, range);
		return rectangles;
	}
}
