package com.alienadventures.ui;

import com.alienadventures.Camera;
import com.alienadventures.Game;
import com.alienadventures.Resources;
import com.alienadventures.image.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Window extends ScreenObject {
	
	private ArrayList<ScreenObject> objects;
	private Camera windowCamera;
	private int animationCounter, animationCounterMax;
	private BufferedImage winSurf;
	private Graphics gWin;
	double scale;
	
	public Window(int x, int y) {
		objects = new ArrayList<ScreenObject>();
		windowCamera = new Camera(-x, -y);
		animationCounter = 0;
		animationCounterMax = 40;
		scale = 0.01;
		winSurf = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		gWin = winSurf.getGraphics();
		
		addElement(new Button(500, 100, " ", 7, this));
		addElement(new Button(3.0f, 3.0f, "TESTING", 0, this));
	}
	
	public void addElement(ScreenObject obj) {
		objects.add(obj);
	}
	
	public Camera getWindowCamera() {
		return windowCamera;
	}
	
	@Override
	public void update() {
		if (animationCounter < animationCounterMax) {
			animationCounter++;
			scale += (1 - scale) / 10;
		} else {
			for (ScreenObject obj : objects) {
				obj.update();
			}
		}
		gWin = winSurf.getGraphics();
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		gWin.drawImage(Resources.buttonImages.get(24), -(int) windowCamera.getOffsetX(), -(int) windowCamera.getOffsetY(), null);
		for (ScreenObject obj : objects) {
			obj.render(gWin, windowCamera);
		}
		gWin.dispose();
		g.setColor(new Color(0, 0, 0, animationCounter*3));
		g.fillRect(0, 0, winSurf.getWidth(), winSurf.getHeight());
		Resources.drawCentered(g, animationCounter == animationCounterMax ? winSurf : Resources.scale(winSurf, scale), Game.WIDTH / 2, Game.HEIGHT / 2);
	}
}
