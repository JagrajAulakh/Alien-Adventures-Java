package com.alienadventures.block;

import com.alienadventures.Camera;
import com.alienadventures.World;
import com.alienadventures.entity.GameObject;

import java.awt.*;

public class Platform extends GameObject {

	public Platform(World world, double x, double y) { this(world, x, y, 100, 20); }
	public Platform(World world, double x, double y, double width, double height) {
		super(world, x, y, width, height);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.setColor(Color.BLACK);
		g.fillRect(screenX(camera), screenY(camera), (int)width, (int)height);
	}
}
