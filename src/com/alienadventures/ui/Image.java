package com.alienadventures.ui;

import com.alienadventures.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Image extends ScreenObject {

	private BufferedImage image;

	public Image(BufferedImage img, float x, float y) {
		this(img, (double)(x * Button.WIDTH), (double)(y * Button.HEIGHT));
	}

	public Image(BufferedImage img, double x, double y) {
		super(x, y, img.getWidth(), img.getHeight());
		this.image = img;
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(image, screenX(camera), screenY(camera), null);
	}
}
