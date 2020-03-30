package com.alienadventures.ui;

import com.alienadventures.*;
import com.alienadventures.ui.*;

import java.awt.image.BufferedImage;

public class ImageButton extends Button {
	
	public ImageButton(float x, float y, int type, Camera camera, ObjectListener callback) {
		super(x, y, camera, callback);
		makeImages(type, null);
		setWidth(normalImage.getWidth());
		setHeight(normalImage.getHeight());
	}
}
