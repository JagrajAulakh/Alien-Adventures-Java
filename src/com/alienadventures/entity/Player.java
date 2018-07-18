package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.image.ImageType;
import com.alienadventures.input.Input;
import com.alienadventures.state.PlayState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
	public static final double SPEED = 8;
	public static final double JUMP_HEIGHT = 10;
	public static final int PLAYER_GREEN = 0;
	public static final int PLAYER_BLUE = 1;
	public static final int PLAYER_PINK = 2;
	public static final int PLAYER_YELLOW = 3;
	public static final int PLAYER_BROWN = 4;

	private boolean jumping, onGround;
	private int type;
	private ImageType prevImage, image;

	public Player() { this(PLAYER_GREEN); }

	public Player(int type) {
		super(Game.WIDTH / 2, Game.HEIGHT / 2);
		setWidth(1);
		setHeight(1);
		jumping = true;
		onGround = false;
		this.type = type;
		this.image = Resources.playerImages.get(type * 8)[0];
		updateHitBox();
	}

	private void determineImage() {
		if (jumping) {
			if (vel.x > 0) {
				image = Resources.playerImages.get(type * 8 + 3)[0];
			} else {
				image = Resources.playerImages.get(type * 8 + 3)[1];
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

	@Override
	public void update() {
		applyGravity(1);
		setAccX(0);
		if (Input.keyDown(KeyEvent.VK_D)) {
			vel.x = SPEED;
		}
		if (Input.keyDown(KeyEvent.VK_A)) {
			vel.x = -SPEED;
		}

		if (Input.keyDown(KeyEvent.VK_SPACE)) {
			if (!jumping) {
				jumping = true;
				vel.y = -JUMP_HEIGHT;
			}
		}
		applyVel(true);
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
			jumping = false;
		}
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
