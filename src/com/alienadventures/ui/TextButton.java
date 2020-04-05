package com.alienadventures.ui;

import com.alienadventures.*;

public class TextButton extends Button {

	private String text;

	public TextButton(float x, float y, String text, int type, Window window, ObjectListener callback) {
		this(x, y, text, type, window.getWindowCamera(), callback);
	}

	public TextButton(float x, float y, String text, int type, Camera camera, ObjectListener callback) {
		super(x, y, camera, callback);
		this.text = text;
		makeImages(type, LetterMaker.makeSentence(text, 2));
		setWidth(normalImage.getWidth());
		setHeight(normalImage.getHeight());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
