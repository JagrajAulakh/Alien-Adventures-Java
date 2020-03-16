package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.Resources;
import com.alienadventures.input.Input;

import static com.alienadventures.ui.ButtonManager.State;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Button extends ScreenObject {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	private State state, prevState;
	private String text;
	private BufferedImage normalImage, hoverImage, clickedImage;
	
	public Button(float x, float y, String text, int type) {
		this((int) (x * WIDTH), (int) (y * HEIGHT), text, type);
	}
	
	public Button(float x, float y, BufferedImage image, int type) {
		this((int) (x * WIDTH), (int) (y * HEIGHT), image, type);
	}
	
	public Button(int x, int y, String text, int type) {
		super(x, y, Resources.buttonImages.get(type * 3).getWidth(), Resources.buttonImages.get(type * 3).getHeight());
		makeImages(type, LetterMaker.makeSentence(text, 2));
		this.text = text;
	}
	
	public Button(int x, int y, BufferedImage image, int type) {
		super(x, y, Resources.buttonImages.get(type * 3).getWidth(), Resources.buttonImages.get(type * 3).getHeight());
		makeImages(type, image);
	}
	
	private void makeImages(int type, BufferedImage img) {
		normalImage = Resources.copyImage(Resources.copyImage(Resources.buttonImages.get(type * 3)));
		Graphics g = normalImage.getGraphics();
		Resources.drawCentered(g, img, normalImage.getWidth() / 2, normalImage.getHeight() / 2);
		g.dispose();
		hoverImage = Resources.copyImage(Resources.copyImage(Resources.buttonImages.get(type * 3 + 1)));
		g = hoverImage.getGraphics();
		Resources.drawCentered(g, img, hoverImage.getWidth() / 2, hoverImage.getHeight() / 2);
		g.dispose();
		clickedImage = Resources.copyImage(Resources.copyImage(Resources.buttonImages.get(type * 3 + 2)));
		g = clickedImage.getGraphics();
		Resources.drawCentered(g, img, normalImage.getWidth() / 2 - 3, normalImage.getHeight() / 2 + 3);
		g.dispose();
	}
	
	@Override
	public void update() {
		if (bounds.contains(Input.mx, Input.my)) {
			if (Input.mousePressed(0)) {
				state = State.HELD;
			} else {
				state = State.HOVER;
			}
			
			if (prevState == State.HELD && Input.mouseUp(0)) {
				state = State.CLICKED;
			}
		} else {
			state = State.NORMAL;
		}
		prevState = state;
	}
	
	public State getState() {
		return state;
	}
	
	public State getPreviousState() {
		return prevState;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		if (state == State.NORMAL || state == State.CLICKED) {
			g.drawImage(normalImage, screenX(camera), screenY(camera), null);
		} else if (state == State.HOVER) {
			g.drawImage(hoverImage, screenX(camera), screenY(camera), null);
		} else if (state == State.HELD) {
			g.drawImage(clickedImage, screenX(camera), screenY(camera), null);
		}
	}
}
