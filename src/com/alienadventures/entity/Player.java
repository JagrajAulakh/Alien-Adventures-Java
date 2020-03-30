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
	
	private static final int COLLISION_CHECKING_RANGE = 100;
	
	private boolean onGround, ducking;
	private int type;
	
	public Player(World world) { this(world, Game.WIDTH / 2.0, -Game.HEIGHT, PLAYER_GREEN); }
	public Player(World world, int type) { this(world, Game.WIDTH / 2.0, -Game.HEIGHT, type); }

	public Player(World world, double x, double y, int type) {
		super(world, x, y);
		this.image = Resources.playerImages.get(0)[0];
		setWidth(image.getImage().getWidth());
		setHeight(image.getImage().getHeight());
		onGround = false;
		this.type = type;
		updateHitBox();
	}
	
	public int getType() { return type; }

	public void respawn() {
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		vel.x = vel.y = acc.x = acc.y = 0;
		updateHitBox();
	}

	private void determineImage() {
		int t = type * 9;
		if (!onGround) {
			if (vel.x > 0) {
				image = Resources.playerImages.get(t + 3)[0];
			} else {
				image = Resources.playerImages.get(t + 3)[1];
			}
		} else if (ducking) {
			if (Math.abs(vel.x) > 0.5) {
				if (vel.x > 0) {
					image = Resources.playerImages.get(t + 8)[0];
				} else {
					image = Resources.playerImages.get(t + 8)[1];
				}
			} else {
				if (vel.x > 0) {
					image = new SingleImage(((Animation)Resources.playerImages.get(t + 4)[0]).getFrame(0));
				} else {
					image = new SingleImage(((Animation)Resources.playerImages.get(t + 4)[1]).getFrame(0));
				}
			}
		} else {
			if (Math.abs(vel.x) > 0.5) {
				if (vel.x > 0) {
					image = Resources.playerImages.get(t + 1)[0];
				} else {
					image = Resources.playerImages.get(t + 1)[1];
				}
			} else {
				image = Resources.playerImages.get(t)[0];
			}
		}
	}

	private void collisionX(ArrayList<Rectangle> rectangles) {
		for (Rectangle r : rectangles) {
			GameObject o = (GameObject)r.getData();
			if (collides(o)) {
				if (o instanceof Platform) {
					if (vel.x > 0) {
						x = o.getX() - width;
						vel.x = acc.x = 0;
					} else if (vel.x < 0) {
						x = o.getX() + o.getWidth();
						vel.x = acc.x = 0;
					}
				} else if (o instanceof Box) {
					if (vel.x > 0) {
						o.setVelX(vel.x / World.FRICTION + acc.x);
					} else if (vel.x < 0) {
						o.setVelX(vel.x / World.FRICTION - acc.x);
					}
					o.setVelX(vel.x / World.FRICTION);
				}
				updateHitBox();
			}
		}
	}

	private void collisionY(ArrayList<Rectangle> rectangles) {
		if (y - height > Game.HEIGHT) {
			respawn();
		}
		for (Rectangle r : rectangles) {
			GameObject o = (GameObject)r.getData();
			if (collides((o))) {
				if (!(o instanceof Particle)) {
					if (vel.y > 0) {
						if (vel.y > 5) {
							for (int i = 0; i < Math.pow(2, vel.y/6.0); i++) {
								PlayState.world.addObject(new Particle(world, getCenterX(), getCenterY(), type));
							}
						}
						y = o.getY() - height;
						vel.y = acc.y = 0;
						onGround = true;
					} else if (vel.y < 0) {
						y = o.getY() + o.getHeight();
						vel.y = acc.y = 0;
					}
				}
//				else if (o instanceof Box) {
//					if (vel.y > 0) {
//						y = o.getY() - height;
//						vel.y = acc.y = 0;
//						onGround = true;
//					}
//				}
			}
			updateHitBox();
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
//				if (Math.abs(vel.x) < 0.01) vel.x = 0;
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

		int range = COLLISION_CHECKING_RANGE;
		ArrayList<Rectangle> rectangles = World.tree.query(new Rectangle(getCenterX() - range, getCenterY() - range, range * 2, range * 2));

		double friction = World.FRICTION;
		if (ducking) {
			friction = World.FRICTION/6;
		}
		setHeight(image.getImage().getHeight());
		applyVelY();
		updateHitBox();
		collisionY(rectangles);
		updateHitBox();

		applyVelX(friction);
		updateHitBox();
		collisionX(rectangles);
		updateHitBox();
		
		ImageType prevImage = image;
		determineImage();
		if (prevImage != image) {
			image.reset();
		}
		image.update();
	}

	@Override
	public void render(Graphics g, Camera camera) {
		Graphics2D g2d = (Graphics2D)g;
		double x = screenX(camera) + this.width / 2;
		double y = screenY(camera) + this.height;
		Resources.drawCentered(g, image.getImage(), (int)x, (int)(y - image.getImage().getHeight() / 2));
//		g2d.setColor(Resources.playerColors[type][0]);
//		g2d.drawRect(world.screenX(getCenterX())-COLLISION_CHECKING_RANGE, world.screenY(getCenterY())-COLLISION_CHECKING_RANGE, COLLISION_CHECKING_RANGE*2, COLLISION_CHECKING_RANGE*2);
	}

	@Override
	public String toString() {
		return "Player{X: " + this.x + ", Y: " + this.y + "}";
	}
}
