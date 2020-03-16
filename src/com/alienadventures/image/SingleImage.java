package com.alienadventures.image;

import com.alienadventures.Resources;

import java.awt.image.BufferedImage;

public class SingleImage extends ImageType {

	private BufferedImage image;

	public SingleImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void reset() {}

	@Override
	public void update() {}

	@Override
	public ImageType flip(boolean h, boolean v) {
		return new SingleImage(Resources.flip(image, h, v));
	}

	@Override
	public BufferedImage getImage() { return image; }
}
