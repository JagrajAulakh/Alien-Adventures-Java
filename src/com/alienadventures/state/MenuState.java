package com.alienadventures.state;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.entity.Particle;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuState implements GameState {

	private ArrayList<Particle> particles;
	private boolean scrolling;
	private int scrollCounter;
	private double h;
	private Camera camera;

	public MenuState() {
		this(true);
	}

	public MenuState(boolean scrolling) {
		camera = new Camera();
		particles = new ArrayList<Particle>();
		this.scrolling = scrolling;
		scrollCounter = -100;
		h = Resources.menuBack.getHeight();
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

		if (scrolling) {
			scrollCounter++;
			if (scrollCounter >= 0) {
				camera.setY(h / 2 * Math.cos(Math.toRadians(scrollCounter / 2)) - h / 2 - Game.HEIGHT);
			}
			if (scrollCounter >= 360) {
				scrolling = false;
			}
		}

		for (int i = particles.size() - 1; i >= 0; i--) {
			Particle p = particles.get(i);
			p.update();
			if (p.dead()) particles.remove(i);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(10, 188, 230));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		if (scrolling) {
			g.drawImage(Resources.menuBack, 0, (int)camera.getOffsetY(), null);
		} else {
			g.drawImage(Resources.menuBack, 0, (int)h - Game.HEIGHT, null);
		}

		for (Particle p : particles) {
			p.render(g, camera);
		}
	}
}
