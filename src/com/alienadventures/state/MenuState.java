package com.alienadventures.state;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.entity.Particle;
import com.alienadventures.input.Input;
import com.alienadventures.ui.Button;
import com.alienadventures.ui.Image;
import com.alienadventures.ui.ScreenObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuState implements GameState {

	private ArrayList<Particle> particles;
	private ArrayList<ScreenObject> objects;
	private boolean intro;
	private int scrollCounter, h;
	private Camera camera;

	public MenuState() {
		this(true);
	}

	public MenuState(boolean intro) {
		camera = new Camera();
		particles = new ArrayList<Particle>();
		objects = new ArrayList<ScreenObject>();
		objects.add(new Button(0, 0, 0));
		this.intro = intro;
		scrollCounter = -300;
		h = Game.HEIGHT;
		if (intro) camera.setY(-h);
		makeObjects();
	}

	private int screenX(double x) { return (int)(x - camera.getOffsetX()); }

	private int screenY(double y) { return (int)(y - camera.getOffsetY()); }

	private void makeObjects() {
		objects.add(new Image(Resources.titleBanner, 100, Game.HEIGHT));
	}

	@Override
	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		if (Input.mouseDown(0)) {
			for (int i = 0; i < Math.random() * 8 + 2; i++) {
				double x = Input.mx + camera.getOffsetX();
				double y = Input.my + camera.getOffsetY();
				particles.add(new Particle(x, y));
			}
		}

		if (intro) {
			if (scrollCounter < 0) { // THIS IS TITLE
				scrollCounter += 2;
			} else { // THIS IS SCROLLING
				scrollCounter += 5;
				if (scrollCounter >= 0) {
					double y = -(h / 2 * Math.cos(Math.toRadians(scrollCounter / 2)) + h / 2);
					camera.setY(y);
				}
				if (scrollCounter >= 360) {
					intro = false;
				}
			}
		}

		for (int i = particles.size() - 1; i >= 0; i--) {
			Particle p = particles.get(i);
			p.update();
			if (p.dead()) particles.remove(i);
		}
		for (ScreenObject obj : objects) {
			obj.update();
		}
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g2d.drawImage(Resources.menuBack, 0 - (int)(camera.getOffsetX() / 10), -128 - (int)(camera.getOffsetY() / 10), null);

		if (intro) {
			float opacity = 1f;
			if (scrollCounter < 0) {
				if (-300 <= scrollCounter && scrollCounter <= -100) {
					opacity = (float)(scrollCounter + 300) / 200f;
				}
			}
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			Resources.drawCentered(g2d, Resources.titleImage, screenX(Game.WIDTH / 2), screenY(-Game.HEIGHT / 2));
		}

		for (ScreenObject obj : objects) {
			obj.render(g2d, camera);
		}

		for (Particle p : particles) {
			p.render(g, camera);
		}
	}
}
