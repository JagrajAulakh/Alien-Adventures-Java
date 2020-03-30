package com.alienadventures;

import com.alienadventures.input.Input;
import com.alienadventures.ui.LetterMaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 640;
	
	private static final boolean RUNNINGLINUX = System.getProperty("os.name").toLowerCase().contains("linux");
	
	public static int frameCount = 0;
	private GameLogic logic;
	private JFrame frame;
	private boolean running = true;
	private boolean loading;
	private Input input;
	private Thread imageLoadingThread;
	private static BufferedImage fireballImage;

	public Game() {
		load();
		try {
			fireballImage = Resources.getImage(ImageIO.read(new File("assets/images/sheets/misc_sheet.png")), new int[]{112, 80, 16, 16});
			fireballImage = Resources.scale(fireballImage, 4);
		} catch (IOException e) { e.printStackTrace(); }
		frame = new JFrame("Alien Adventures");
		try {
			frame.setIconImage(ImageIO.read(new File("assets/images/icon.png")));
		} catch (IOException e) { e.printStackTrace(); }
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

	private void load() {
		Game game = this;
		imageLoadingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Resources.load();
					logic = new GameLogic(game);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		imageLoadingThread.start();
		loading = true;
		frameCount = 0;
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
		loading = imageLoadingThread.isAlive();
		frameCount++;
		if (!loading) {
			logic.update();
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if (running) {
			getToolkit().sync();
		}
		
		if (loading) {
			loadingAnimation(g);
		} else {
			logic.render(g);
		}

		g.dispose();
		bs.show();
	}
	
	public BufferedImage screenshot() {
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		paint(img.getGraphics());
		return img;
	}

	private void loadingAnimation(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Graphics2D g2d = (Graphics2D)g.create();

		int o = (int)(Math.round(6 * Math.sin(Math.toRadians(frameCount*4))));

		// FIREBALL
		final int shake = 2;
		int x = WIDTH - 64 + (int)Math.round(Math.random() * shake - shake / 2);
		int y = HEIGHT - 64 + (int)Math.round(Math.random() * shake - shake / 2) + o;
		g2d.rotate(Math.toRadians(frameCount * 5), x, y);
		Resources.drawCentered(g2d, fireballImage, x, y);

		// LOADING LETTERS
		try {
			Resources.drawCentered(g, LetterMaker.makeSentence("LOADING..."), WIDTH /2 + WIDTH/6, HEIGHT - 60 + o);
		} catch (NullPointerException e) { System.out.println("ERROR!"); }
	}
}