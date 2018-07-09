package com.alienadventures;

import com.alienadventures.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private GameLogic logic;
	private JFrame frame;
	private boolean running = true;
	private Input input;

	public Game() {
		try {
			Resources.load();
		} catch (Exception e) { e.printStackTrace(); }
		logic = new GameLogic();
		frame = new JFrame("Alien Adventures");
		Dimension d = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(d);
		setPreferredSize(d);

		input = new Input();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addMouseWheelListener(input);

		setFocusable(true);
		requestFocus();

		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
		run();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				Input.update();
				tick();
				delta--;
			}

			if (running) {
				render();
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	private void tick() {
		logic.update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		logic.render(g);

		g.dispose();
		bs.show();
	}
}