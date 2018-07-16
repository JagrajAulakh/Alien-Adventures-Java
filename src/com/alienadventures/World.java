package com.alienadventures;

import com.alienadventures.block.Platform;
import com.alienadventures.entity.GameObject;
import com.alienadventures.entity.Particle;
import com.alienadventures.entity.Player;
import com.alienadventures.input.Input;
import com.alienadventures.ui.LetterMaker;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class World {
	public static final double GRAVITY = 0.5;
	public static final double FRICTION = 0.4;

	private ArrayList<GameObject> objects;
	private Camera camera;
	private Player player;

	public World() {
		camera = new Camera();
		player = new Player();
		objects = new ArrayList<GameObject>();
//		for (int i = 0; i < 100; i++) {
//			Particle p = new Particle(Math.random() * Game.WIDTH, Math.random() * Game.HEIGHT);
//			p.setVelY(-5);
//			objects.add(p);
//		}
		objects.add(new Platform(0,Game.HEIGHT, Game.WIDTH, 20));
	}

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public void update() {
		if (Input.keyDown(KeyEvent.VK_RIGHT)) {
			camera.setX(camera.getOffsetX()+1);
		} else if (Input.keyDown(KeyEvent.VK_LEFT)) {
			camera.setX(camera.getOffsetX()-1);
		}
		for (int i = objects.size()-1; i >= 0; i--) {
			GameObject o = objects.get(i);
			o.update();
			if (o instanceof Particle) {
				Particle p = (Particle)o;
				if (p.dead()) objects.remove(o);
			}
		}
		player.update();
		camera.centerOn(player);
		System.out.println(objects.size());
	}

	public void render(Graphics g) {
		g.setColor(new Color(100));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		player.render(g, camera);
		for (GameObject o : objects) {
			o.render(g, camera);
		}
//		Resources.drawCentered(g, LetterMaker.makeSentence("lALIEN ADVENTURESr"), Game.WIDTH / 2, Game.HEIGHT / 2);
	}
}
