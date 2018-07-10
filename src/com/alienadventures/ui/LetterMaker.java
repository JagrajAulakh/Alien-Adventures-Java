package com.alienadventures.ui;

import com.alienadventures.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LetterMaker {
	public static final int SCALE = 5;
	public static final int SPACE_LENGTH = 24;
	private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,?!:;'/()[]{}+-=_lr%*<>";
	private static int[] sizes = new int[letters.length()];

	public LetterMaker() {
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = 8;
		}
		sizes[letters.indexOf('I')] = 5;
		sizes[letters.indexOf('K')] = 7;
		sizes[letters.indexOf('L')] = 7;
		sizes[letters.indexOf('T')] = 7;
		sizes[letters.indexOf('Y')] = 7;
		sizes[letters.indexOf('1')] = 7;
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] *= SCALE;
		}
	}

	private static int calculateWidth(String sent) {
		int width = 0;
		for (char c : sent.toCharArray()) {
			if (c == ' ') {
				width += SPACE_LENGTH;
			} else {
				width += sizes[letters.indexOf(c)];
			}
		}
		return width;
	}

	public static BufferedImage makeSentence(String sent) {
		int width = calculateWidth(sent);
		int height = 8 * SCALE;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();

		int x = 0;
		for (char letter : sent.toCharArray()) {
			if (letter == ' ') {
				x += SPACE_LENGTH;
			} else {
				int px = letters.indexOf(letter) * 8;
				BufferedImage letterImg = Resources.scale(Resources.fontSheet.getSubimage(px, 0, sizes[letters.indexOf(letter)]/SCALE, 8), SCALE);
				g.drawImage(letterImg, x, 0, null);
				x += sizes[letters.indexOf(letter)];
			}
		}
		g.dispose();
		return img;
	}
}
