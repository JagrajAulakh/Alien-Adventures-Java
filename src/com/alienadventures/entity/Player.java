package com.alienadventures.entity;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
	public static final double SPEED = 5;
	public static final double JUMP_HEIGHT = 10;

	private boolean jumping, onGround;

	public Player() {
		super(0, 0);
		setWidth(50);
		setHeight(50);
		jumping = true;
		onGround = false;
	}

	@Override
	public void update() {
		applyGravity(1);
		setAccX(0);
		if (Input.keyDown(KeyEvent.VK_D)) {
			vel.x = SPEED;
		} if (Input.keyDown(KeyEvent.VK_A)) {
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
			y = Game.HEIGHT - height;
			vel.y = 0;
			acc.y = 0;
			onGround = true;
			jumping = false;
		}
	}

	@Override
	public void render(Graphics g, Camera offset) {
		g.setColor(Color.WHITE);
		g.fillRect((int)screenX(offset), (int)screenY(offset), (int)width, (int)height);
	}
}
