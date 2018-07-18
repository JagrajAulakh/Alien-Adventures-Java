package com.alienadventures;

import com.alienadventures.block.Platform;
import com.alienadventures.entity.GameObject;
import com.alienadventures.entity.Particle;
import com.alienadventures.entity.Player;
import com.alienadventures.input.Input;
import com.alienadventures.util.QuadTree;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class World {
	public static final double GRAVITY = 0.5;
	public static final double FRICTION = 0.4;

	public static QuadTree tree = new QuadTree();

	private ArrayList<GameObject> objects;
	private Camera camera;
	private Player player;
	private static Thread loadingThread;

	public World() {
		camera = new Camera();
		player = new Player();
		objects = new ArrayList<GameObject>();

		loadingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				loadWorld();
			}
		});
		loadingThread.start();
	}

	private void loadWorld() {
		for (int i = 0; i < 100; i++) {
			Particle p = new Particle(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
			objects.add(p);
		}
		objects.add(new Platform(0, Game.HEIGHT, Game.WIDTH, 20));
//		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static boolean isLoading() { return loadingThread.isAlive(); }

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public void update() {
		if (isLoading()) {
		} else {
			if (Input.keyDown(KeyEvent.VK_RIGHT)) {
				camera.setX(camera.getOffsetX() + 10);
			} else if (Input.keyDown(KeyEvent.VK_LEFT)) {
				camera.setX(camera.getOffsetX() - 10);
			}
			for (int i = objects.size() - 1; i >= 0; i--) {
				GameObject o = objects.get(i);
				o.update();
				if (o instanceof Particle) {
					Particle p = (Particle)o;
					if (p.dead()) objects.remove(o);
				}
			}
			player.update();
			camera.centerOn(player);
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(93, 210, 255));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		if (isLoading()) {
			Graphics2D g2d = (Graphics2D)g.create();
			int x = Game.WIDTH - 64;
			int y = Game.HEIGHT - 64;
			g2d.rotate(Math.toRadians(Game.frameCount), x, y);
			Resources.drawCentered(g2d, Resources.fireBallImage, x, y);
		} else {
			for (GameObject o : objects) {
				o.render(g, camera);
				g.setColor(Color.GREEN);
				o.drawHitBox((Graphics2D)g.create(), camera);
			}
			player.render(g, camera);
			player.drawHitBox((Graphics2D)g.create(), camera);
		}
	}
}
