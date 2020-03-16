package com.alienadventures;

import com.alienadventures.block.Platform;
import com.alienadventures.entity.Box;
import com.alienadventures.entity.GameObject;
import com.alienadventures.entity.Particle;
import com.alienadventures.entity.Player;
import com.alienadventures.input.Input;
import com.alienadventures.util.Point;
import com.alienadventures.util.QuadTree;
import com.alienadventures.util.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class World {
	public static final double GRAVITY = 0.5;
	public static final double FRICTION = 0.4;

	public static QuadTree tree = new QuadTree(-Game.WIDTH, -Game.HEIGHT, Game.WIDTH * 2, Game.HEIGHT * 2, 1);

	private ArrayList<GameObject> objects, toRemove;
	private Camera camera;
	private Player player;
	private GameObject platform;
	private double scale = 1;
	private static Thread loadingThread;

	public World() {
		camera = new Camera();
		objects = new ArrayList<GameObject>();
		toRemove = new ArrayList<GameObject>();

		loadingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				loadWorld();
			}
		});
		loadingThread.start();
	}

	private void loadWorld() {
		player = new Player(Player.PLAYER_BROWN);
		for (int i = 0; i < 10; i++) {
			Particle p = new Particle(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
			objects.add(p);
		}
		platform = new Platform(0, Game.HEIGHT / 2, Game.WIDTH, 50);
		objects.add(platform);
		camera.centerOn(player, false);
//		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static boolean isLoading() { return loadingThread.isAlive(); }

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public void removeObject(GameObject obj) { toRemove.add(obj); }

	public int screenX(double x) { return (int)(x - camera.getOffsetX());}

	public int screenY(double y) { return (int)(y - camera.getOffsetY());}

	public void update() {
		if (isLoading()) {
		} else {
			if (Input.keyDown(KeyEvent.VK_RIGHT)) {
				camera.setX(camera.getOffsetX() + 10);
			} else if (Input.keyDown(KeyEvent.VK_LEFT)) {
				camera.setX(camera.getOffsetX() - 10);
			}
			if (Input.keyUpOnce(KeyEvent.VK_EQUALS)) scale += 0.05;
			if (Input.keyUpOnce(KeyEvent.VK_MINUS)) scale -= 0.05;
			for (GameObject o : toRemove) {
				objects.remove(o);
			}
			tree.clear();
			for (GameObject o : objects) {
				o.update();
				if (o instanceof Particle) {
					Particle p = (Particle)o;
					if (p.dead()) toRemove.add(o);
				}
				tree.insert(new Rectangle(o.getX(), o.getY(), o.getWidth(), o.getHeight(), o));
			}
			player.update();
			camera.centerOn(player, true);
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(93, 210, 255));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		Graphics2D g2d = (Graphics2D)g.create();
		if (isLoading()) {
			int x = Game.WIDTH - 64;
			int y = Game.HEIGHT - 64;
			g2d.rotate(Math.toRadians(Game.frameCount), x, y);
			Resources.drawCentered(g2d, Resources.fireBallImage, x, y);
		} else {
			Rectangle range = new Rectangle(camera.getOffsetX(), camera.getOffsetY(), Game.WIDTH, Game.HEIGHT);
			g2d.setColor(new Color(51, 51, 51));
			g2d.setStroke(new BasicStroke(5));
			g2d.drawRect(screenX(range.getX()), screenY(range.getY()), (int)range.getWidth(), (int)range.getHeight());
			ArrayList<Rectangle> rects = tree.query(range);

			g2d.scale(scale, scale);
//			g2d.translate(-Game.WIDTH/4, -Game.HEIGHT/4);
//			g2d.translate(100, 100);
			for (Rectangle r : rects) {
				GameObject o = (GameObject)r.getData();
				o.render(g2d, camera);
				g.setColor(Color.GREEN);
			}
			player.render(g2d, camera);
//			player.drawHitBox(g2d, camera);
			g2d.setColor(new Color(255, 100, 255));
			g2d.setColor(Color.RED);
//			tree.show(g2d, camera);
		}
	}
}
