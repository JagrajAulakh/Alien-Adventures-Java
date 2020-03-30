package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.entity.Particle;

import java.awt.*;
import java.util.ArrayList;

public class ParticleManager implements Manager {

	private ArrayList<Particle> particles, toRemove;

	public ParticleManager() {
		particles = new ArrayList<Particle>();
		toRemove = new ArrayList<Particle>();
	}

	public void add(Particle p) {
		particles.add(p);
	}
	public void remove(Particle p) {
		toRemove.add(p);
	}

	@Override
	public void update() {
		for (Particle p:toRemove) {
			particles.remove(p);
		}

		for (Particle p:particles) {
			p.update();
			if (p.isDead()) {
				remove(p);
			}
		}
	}

	@Override
	public void render(Graphics g, Camera camera) {
		for (Particle p:particles) {
			p.render(g, camera);
		}
	}
}
