package com.alienadventures.ui;

import com.alienadventures.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends ScreenObject implements ObjectListener {
	
	private ArrayList<ScreenObject> objects;
	private Button xButton;
	private Camera windowCamera;
	private int animationCounter, animationCounterMax;
	private BufferedImage winSurf;
	private Graphics2D gWin;
	private String title;
	private boolean killmyself, killing;
	double scale;
	
	public Window(int x, int y) { this(x, y, ""); }
	public Window(int x, int y, String title) {
		objects = new ArrayList<ScreenObject>();
		windowCamera = new Camera(-x, -y);
		this.title = title;
		animationCounter = 2;
		animationCounterMax = 30;
		scale = 0.01;
		winSurf = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		gWin = (Graphics2D) winSurf.getGraphics();
		killmyself = killing = false;
		
		xButton = new Button(Resources.buttonImages.get(24).getWidth() - 10 * 4, -2 * 4, " ", 7, this);
		addElement(xButton);
		addElement(new Button(3.0f, 3.0f, "TESTING", 0, this));
	}
	
	public void addElement(ScreenObject obj) {
		objects.add(obj);
	}
	
	public Camera getWindowCamera() {
		return windowCamera;
	}
	
	public boolean isDead() {
		return killmyself;
	}
	
	public void kill() {
		killing = true;
		animationCounter--;
	}
	
	@Override
	public void update() {
		if (2 <= animationCounter && animationCounter < animationCounterMax) {
			animationCounter += killing ? -1 : 1;
			double k = (2*Math.PI)/(Math.PI - Math.asin(1/1.1));
			scale = 1.1 * Math.sin((2*Math.PI)*animationCounter / (k*animationCounterMax));
		} else {
			if (killing) killmyself = true;
			else {
				for (ScreenObject obj : objects) {
					obj.update();
				}
			}
		}
		if (xButton.getState() == ButtonManager.State.CLICKED) {
			kill();
			xButton.setState(ButtonManager.State.NORMAL);
		}
		gWin = (Graphics2D) winSurf.getGraphics();
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		gWin.setBackground(new Color(0, 0, 0, 0));
		gWin.clearRect(0, 0, winSurf.getWidth(), winSurf.getHeight());
		
		gWin.drawImage(Resources.buttonImages.get(24), -(int) windowCamera.getOffsetX(), -(int) windowCamera.getOffsetY(), null);
		gWin.drawImage(LetterMaker.makeSentence(title, 2), -(int)windowCamera.getOffsetX() + 16, -(int)windowCamera.getOffsetY()+4, null);
		for (ScreenObject obj : objects) {
			obj.render(gWin, windowCamera);
		}
		gWin.dispose();
		g.setColor(new Color(0, 0, 0, animationCounter * 3));
		g.fillRect(0, 0, winSurf.getWidth(), winSurf.getHeight());
		Resources.drawCentered(g, animationCounter == animationCounterMax ? winSurf : Resources.scale(winSurf, scale), Game.WIDTH / 2, Game.HEIGHT / 2);
	}
	
	@Override
	public void clicked(ScreenObject obj) {
	
	}
	
	@Override
	public void hovered(ScreenObject obj) {
	
	}
	
	@Override
	public void held(ScreenObject obj) {
	
	}
}
