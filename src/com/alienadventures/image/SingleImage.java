package com.alienadventures.image;

import java.awt.image.BufferedImage;

public class SingleImage extends ImageType {

	private BufferedImage image;

	public SingleImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void update() {}

	@Override
	public BufferedImage getImage() { return image; }
}
