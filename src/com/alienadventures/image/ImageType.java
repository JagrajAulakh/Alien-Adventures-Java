package com.alienadventures.image;

import java.awt.image.BufferedImage;

public abstract class ImageType {
	public abstract void update();
	public abstract BufferedImage getImage();
	public abstract void reset();
	public abstract ImageType flip(boolean h, boolean v);
}
