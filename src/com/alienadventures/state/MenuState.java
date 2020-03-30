package com.alienadventures.state;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.GameLogic;
import com.alienadventures.Resources;
import com.alienadventures.entity.Particle;
import com.alienadventures.input.Input;
import com.alienadventures.ui.*;
import com.alienadventures.ui.Button;
import com.alienadventures.ui.Image;
import com.alienadventures.ui.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MenuState implements GameState, ObjectListener {
	
	private Game game;
	private GameStateManager gsm;
	private ParticleManager particles;
	private ArrayList<ScreenObject> objects;
	private WindowManager windows;
	private ButtonManager buttons;
	private boolean intro;
	private int scrollCounter, h;
	private Camera camera;
	
	public MenuState(Game game, GameStateManager gsm) {
		this(game, gsm, true);
	}
	
	public MenuState(Game game, GameStateManager gsm, boolean intro) {
		this.game = game;
		this.gsm = gsm;
		camera = new Camera();
		particles = new ParticleManager();
		objects = new ArrayList<ScreenObject>();
		windows = new WindowManager();
		buttons = new ButtonManager();
		buttons.addListener(this);
		
		this.intro = intro;
		h = Game.HEIGHT;
		if (intro) {
			camera.setY(-h);
			scrollCounter = -400;
		} else {
			scrollCounter = 0;
		}
		makeObjects();
	}
	
	private int screenX(double x) {
		return (int) (x - camera.getOffsetX());
	}
	
	private int screenY(double y) {
		return (int) (y - camera.getOffsetY());
	}
	
	private void makeObjects() {
		objects.add(new Image(Resources.titleBanner, 13.5f, 0.5f));
		buttons.add(new Button(20f, 8f, "START", 0, camera));
		buttons.add(new Button(20f, 11f, "OPTIONS", 0, camera));
//		buttons.add(new Button(3f, 3f, " ", 2, camera));
	}
	
	@Override
	public void clicked(ScreenObject obj) {
		if (obj instanceof Button) {
			Button button = (Button) obj;
			if (button.getText().toLowerCase().equals("start")) {
				GameLogic.gsm.push(new PlayState());
			} else if (button.getText().toLowerCase().equals("options")) {
				windows.add(new Window(128, 128, "OPTIONS"));
			}
		}
	}
	
	@Override
	public void hovered(ScreenObject obj) {
	
	}
	
	@Override
	public void held(ScreenObject obj) {
	
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
				particles.add(new Particle(null, x, y));
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
		
		particles.update();
		for (ScreenObject obj : objects) {
			obj.update();
		}
		if (windows.isEmpty()) buttons.update();
		windows.update();
	}
	
	@Override
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		if (-400 <= scrollCounter && scrollCounter <= -300) {
			double i = (scrollCounter + 400.0) / 100.0 * 255;
			g2d.drawImage(Resources.darken(Resources.menuBack, 255 - (int) i), -(int) (camera.getOffsetX() / 10), -128 - (int) (camera.getOffsetY() / 10), null);
		} else {
			g2d.drawImage(Resources.menuBack, -(int) (camera.getOffsetX() / 10), -128 - (int) (camera.getOffsetY() / 10), null);
		}
		
		if (intro) {
			float opacity = 1f;
			if (scrollCounter < 0) {
				if (scrollCounter < -300) opacity = 0;
				if (-300 <= scrollCounter && scrollCounter <= -200) {
					opacity = (float) (scrollCounter + 300) / 100f;
				}
			}
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			double i = 5 * Math.sin(Math.toRadians(Game.frameCount * 4));
			Resources.drawCentered(g2d, Resources.titleImage, screenX(Game.WIDTH / 2), screenY(-Game.HEIGHT / 2) + (int) i);
		}
		
		for (ScreenObject obj : objects) {
			obj.render(g2d, camera);
		}
		buttons.render(g2d, camera);
		windows.render(g2d, camera);
		particles.render(g2d, camera);

//		Resources.drawCentered(g, LetterMaker.makeSentence("TEST SENT", 4), Game.WIDTH / 2, Game.HEIGHT / 2);

//		g.setColor(new Color(0, 0, 0, 50));
//		for (int x = 0; x < Game.WIDTH; x += Button.WIDTH) {
//			int sx = (int) (x - camera.getOffsetX() % Game.WIDTH);
//			g.drawLine(sx, 0, sx, Game.HEIGHT);
//		}
//		for (int y = 0; y < Game.HEIGHT; y += Button.HEIGHT) {
//			int sy = (int) (y - camera.getOffsetY() % Game.HEIGHT);
//			g.drawLine(0, sy, Game.WIDTH, sy);
//		}
	}
}
