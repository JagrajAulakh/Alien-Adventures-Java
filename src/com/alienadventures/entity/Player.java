package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.World;
import com.alienadventures.block.Platform;
import com.alienadventures.image.Animation;
import com.alienadventures.image.ImageType;
import com.alienadventures.image.SingleImage;
import com.alienadventures.input.Input;
import com.alienadventures.state.PlayState;
import com.alienadventures.util.Point;
import com.alienadventures.util.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends GameObject {
	public static final double SPEED = 8;
	public static final double JUMP_HEIGHT = 10;
	public static final int PLAYER_GREEN = 0;
	public static final int PLAYER_BLUE = 1;
	public static final int PLAYER_PINK = 2;
	public static final int PLAYER_YELLOW = 3;
	public static final int PLAYER_BROWN = 4;

	private boolean onGround, ducking;
	private int type;
	private ImageType prevImage, image;

	public Player() { this(PLAYER_GREEN); }

	public Player(int type) {
		super(Game.WIDTH / 2, Game.HEIGHT / 2);
		setWidth(1);
		setHeight(1);
		onGround = false;
		this.type = type;
		this.image = Resources.playerImages.get(type * 8)[0];
		updateHitBox();
	}

	private void determineImage() {
		if (!onGround) {
			if (vel.x > 0) {
				image = Resources.playerImages.get(type * 8 + 3)[0];
			} else {
				image = Resources.playerImages.get(type * 8 + 3)[1];
			}
		} else if (ducking) {
			if (Math.abs(vel.x) > 0.5) {
				if (vel.x > 0) {
					image = Resources.playerImages.get(type * 8 + 8)[0];
				} else {
					image = Resources.playerImages.get(type * 8 + 8)[1];
				}
			} else {
				if (vel.x > 0) {
					image = new SingleImage(((Animation)Resources.playerImages.get(type * 8 + 4)[0]).getImage(0));
				} else {
					image = new SingleImage(((Animation)Resources.playerImages.get(type * 8 + 4)[1]).getImage(0));
				}
			}
		} else {
			if (Math.abs(vel.x) > 0.5) {
				if (vel.x > 0) {
					image = Resources.playerImages.get(type * 8 + 1)[0];
				} else {
					image = Resources.playerImages.get(type * 8 + 1)[1];
				}
			} else {
				image = Resources.playerImages.get(type * 8)[0];
			}
		}
	}

	private void collisionX(ArrayList<Point> points) {
		for (Point p:points) {
			GameObject o = (GameObject)p.getData();
			if (o instanceof Platform) {
				if (vel.x > 0) {
					x = o.getX() - width;
				} else {
					x = o.getX() + o.getWidth();
				}
			}
		}
	}

	private void collisionY(ArrayList<Point> points) {
		for (Point p:points) {
			GameObject o = (GameObject)p.getData();
			if (o instanceof Platform) {
				if (vel.x > 0) {
					x = o.getX() - width;
				} else {
					x = o.getX() + o.getWidth();
				}
			}
		}
	}

	@Override
	public void update() {
		applyGravity(1);
		setAccX(0);
		ducking = false;
		if (Input.keyDown(KeyEvent.VK_S)) {
			if (onGround) {
				ducking = true;
				if (Input.keyDownOnce(KeyEvent.VK_S)) {
					vel.multiply(3);
				}
			}
		}
		if (!ducking) {
			if (Input.keyDown(KeyEvent.VK_D)) {
				vel.x = SPEED;
			}
			if (Input.keyDown(KeyEvent.VK_A)) {
				vel.x = -SPEED;
			}
		}

		if (Input.keyDown(KeyEvent.VK_SPACE)) {
			if (onGround) {
				onGround = false;
				ducking = false;
				vel.y = -JUMP_HEIGHT;
			}
		}
		double friction = World.FRICTION;
		if (ducking) friction *= 0.1;
		applyVel(friction);
		if (y + height > Game.HEIGHT) {
			if (vel.y > 1) {
				for (int i = 0; i < vel.y / 3; i++) {
					PlayState.world.addObject(new Particle(x + width / 2, y + height, new Color(131, 69, 17)));
				}
			}
			y = Game.HEIGHT - height;
			vel.y = 0;
			acc.y = 0;
			onGround = true;
		}
		double range = 50;
		ArrayList<Point> points = World.tree.query(new Rectangle(getCenterX() - range, getCenterY() - range, getCenterX() + range, getCenterY() + range));
		collisionX(points);
		collisionY(points);
		updateHitBox();

		prevImage = image;
		determineImage();
		if (prevImage != image) {
			image.reset();
		}
		image.update();
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.setColor(Color.WHITE);
		g.fillRect((int)screenX(camera), (int)screenY(camera), (int)width, (int)height);
		double x = screenX(camera) + this.width / 2;
		double y = screenY(camera) + this.height;
		Resources.drawCentered(g, image.getImage(), (int)x, (int)(y - image.getImage().getHeight() / 2));
	}
}
