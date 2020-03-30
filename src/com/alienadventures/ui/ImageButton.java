package com.alienadventures.ui;

import com.alienadventures.*;
import com.alienadventures.ui.*;

import java.awt.image.BufferedImage;

public class ImageButton extends Button {
	private BufferedImage image;
	public ImageButton(float x, float y, BufferedImage image, Camera camera) {
		super(x, y, camera);
		this.image = image;
	}
}
