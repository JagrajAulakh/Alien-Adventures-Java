package com.alienadventures.ui;

import com.alienadventures.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LetterMaker {
	public static final int SCALE = 5;
	public static final int SPACE_LENGTH = 5;
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
		sizes[letters.indexOf('.')] = 4;
	}

	private static int calculateWidth(String sent) {
		return calculateWidth(sent, SCALE);
	}

	private static int calculateWidth(String sent, double scale) {
		int width = 0;
		for (char c : sent.toCharArray()) {
			if (c == ' ') {
				width += Math.round(SPACE_LENGTH * scale);
			} else {
				width += Math.round(sizes[letters.indexOf(c)] * scale);
			}
		}
		return width;
	}

	public static BufferedImage makeSentence(String sent) {
		return makeSentence(sent, SCALE);
	}

	public static BufferedImage makeSentence(String sent, double scale) {
		int width = calculateWidth(sent, scale);
		int height = (int)Math.round(8 * scale);
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();

		int x = 0;
		char[] chars = sent.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char letter = chars[i];
			if (letter == ' ') {
				x += SPACE_LENGTH * scale;
			} else {
				int px = i * 8;
				BufferedImage letterImg = Resources.scale(Resources.fontSheet.getSubimage(px, 0, sizes[i], 8), scale);
				g.drawImage(letterImg, x, 0, null);
				x += sizes[letters.indexOf(letter)] * scale;
			}
		}
		g.dispose();
		return img;
	}
}
