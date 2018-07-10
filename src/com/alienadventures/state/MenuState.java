package com.alienadventures.state;

import com.alienadventures.Game;
import com.alienadventures.entity.Particle;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuState implements GameState {

	private ArrayList<Particle> particles;

	public MenuState() {
		particles = new ArrayList<Particle>();
	}

	@Override
	public void update() {
		if (Input.keyUpOnce(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		if (Input.mouseDown(0)) {
			for (int i = 0; i < Math.random() * 8 + 2; i++) {
				particles.add(new Particle(Input.mx, Input.my));
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
		g.setColor(new Color(7954129));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		for (Particle p : particles) {
			p.render(g);
		}
	}
}
