package com.alienadventures;

import com.alienadventures.entity.GameObject;
import com.alienadventures.entity.Particle;

import java.awt.*;
import java.util.ArrayList;

public class World {
	public static final double GRAVITY = 0.8;

	private ArrayList<GameObject> objects;

	public World() {
		objects = new ArrayList<GameObject>();
		for (int i = 0; i < 100; i++) {
			Particle p = new Particle(Math.random() * Game.WIDTH, Math.random() * Game.HEIGHT);
			p.setVelY(-5);
			objects.add(p);
		}
	}

	public void update() {
		for (GameObject o:objects) {
			o.update();
		}
	}

	public void render(Graphics g) {
		for (GameObject o:objects) {
			o.render(g);
		}
	}
}
