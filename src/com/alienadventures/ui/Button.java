package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.Resources;
import com.alienadventures.input.Input;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Button extends ScreenObject {
	enum State {
		NORMAL,
		HOVER,
		HELD,
		CLICKED
	}

	public static final double ANIMATIONSCALEMAX = 1.2;
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	protected State state = State.NORMAL, prevState;
	protected BufferedImage normalImage, hoverImage, clickedImage;
	protected Camera camera;
	protected Window window;
	protected double scaleCounter = 0, scaleFactor = 1;
	protected ObjectListener callback;

	public Button(float x, float y, Camera camera, ObjectListener callback) {
		super(x * WIDTH, y * WIDTH);
		this.camera = camera;
		this.callback = callback;
	}

	public Button(float x, float y, BufferedImage image, int type, Camera camera) {
		this((int)(x * WIDTH), (int)(y * HEIGHT), image, type, camera);
	}

	public Button(int x, int y, BufferedImage image, int type, Camera camera) {
		super(x, y, Resources.buttonImages.get(type * 3).getWidth(), Resources.buttonImages.get(type * 3).getHeight());
		this.camera = camera;
	}

//	public Button(float x, float y, String text, int type, Window window) {
//		this((int) (x * WIDTH), (int) (y * HEIGHT), text, type, window);
//	}
//
////	public Button(int x, int y, String text, int type, Window window) {
////		super(x, y, Resources.buttonImages.get(type * 3).getWidth(), Resources.buttonImages.get(type * 3).getHeight());
////		this.window = window;
////		makeImages(type, LetterMaker.makeSentence(text, 2));
////		this.text = text;
////	}

	protected void makeImages(int type, BufferedImage img) {
		normalImage = Resources.copyImage(Resources.buttonImages.get(type * 3));
		hoverImage = Resources.copyImage(Resources.buttonImages.get(type * 3 + 1));
		clickedImage = Resources.copyImage(Resources.buttonImages.get(type * 3 + 2));
		if (img != null) {
			Graphics g = normalImage.getGraphics();
			Resources.drawCentered(g, img, normalImage.getWidth() / 2, normalImage.getHeight() / 2);
			g.dispose();
			g = hoverImage.getGraphics();
			Resources.drawCentered(g, img, hoverImage.getWidth() / 2, hoverImage.getHeight() / 2);
			g.dispose();
			g = clickedImage.getGraphics();
			Resources.drawCentered(g, img, normalImage.getWidth() / 2 - 3, normalImage.getHeight() / 2 + 3);
			g.dispose();
		}
	}

	@Override
	public void update() {
		Camera camera = window == null ? this.camera : window.getWindowCamera();
		Rectangle screenBounds = new Rectangle(screenX(camera), screenY(camera), (int)getWidth(), (int)getHeight());
		if (screenBounds.contains(Input.mx, Input.my)) {
			if (Input.mousePressed(0)) {
				state = State.HELD;
				if (callback != null) callback.held(this);
			} else {
				state = State.HOVER;
				if (callback != null) callback.hovered(this);
			}

			if (prevState == State.HELD && Input.mouseUp(0)) {
				state = State.CLICKED;
				if (callback != null) callback.clicked(this);
			}
		} else {
			state = State.NORMAL;
		}
		prevState = state;

		if (state == State.NORMAL || state == State.CLICKED) {
			scaleCounter = 0;
			scaleFactor = 1;
		} else if (state == State.HOVER) {
			if (scaleCounter <= 50) {
				scaleCounter++;
				scaleFactor += (ANIMATIONSCALEMAX - scaleFactor) / 2;
			}
		} else if (state == State.HELD) {
			scaleCounter = 0;
			scaleFactor = 1;
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State s) {
		state = s;
	}

	@Override
	public void render(Graphics g, Camera camera) {
		if (state == State.NORMAL || state == State.CLICKED) {
			g.drawImage(normalImage, screenX(camera), screenY(camera), null);
		} else if (state == State.HOVER) {
			Resources.drawCentered(g, Resources.scale(hoverImage, scaleFactor), screenX(camera) + hoverImage.getWidth() / 2, screenY(camera) + hoverImage.getHeight() / 2);
		} else if (state == State.HELD) {
			g.drawImage(clickedImage, screenX(camera), screenY(camera), null);
		}
	}
}
