package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Resources;
import com.alienadventures.World;
import com.alienadventures.block.Platform;
import com.alienadventures.image.SingleImage;
import com.alienadventures.util.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Box extends GameObject {
	public Box(World world, double x, double y) {
		super(world, x, y);
		image = new SingleImage(Resources.boxImage);
		setWidth(image.getImage().getWidth());
		setHeight(image.getImage().getHeight());
	}

	private void collisionX(ArrayList<Rectangle> rectangles) {
		for (Rectangle r:rectangles) {
			GameObject o = (GameObject)r.getData();
			if (collides(o)) {
				if (o instanceof Platform) {
					if (vel.x > 0) {
						x = o.getX() - width;
						vel.x = acc.x = 0;
					} else if (vel.x < 0){
						x = o.getX() + o.getWidth();
						vel.x = acc.x = 0;
					}
				}
			}
			updateHitBox();
		}
	}

	private void collisionY(ArrayList<Rectangle> rectangles) {
		for (Rectangle r:rectangles) {
			GameObject o = (GameObject)r.getData();
			if (collides(o) && this != o) {
				if (o instanceof Platform) {
					if (collides((o))) {
						if (vel.y > 0) {
							y = o.getY() - height;
							vel.y = acc.y = 0;
						} else if (vel.y < 0) {
							y = o.getY() + o.getHeight();
							vel.y = acc.y = 0;
						}
					}
				} else if (o instanceof Box) {
					vel.x = o.vel.x*2;
				}
			}
			updateHitBox();
		}
	}

	@Override
	public void update() {
		applyGravity();
		setAccX(0);

		double range = 100;
		ArrayList<Rectangle> rectangles = World.tree.query(new Rectangle(getCenterX() - range, getCenterY() - range, range * 2, range * 2));
		applyVelY();
		updateHitBox();
		collisionY(rectangles);
		updateHitBox();

		applyVelX();
		updateHitBox();
		collisionX(rectangles);
		updateHitBox();

		image.update();
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(image.getImage(), screenX(camera), screenY(camera), null);
	}
}
