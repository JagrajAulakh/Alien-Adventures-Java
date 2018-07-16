package com.alienadventures;

import com.alienadventures.ui.LetterMaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
	public static BufferedImage fontSheet, menuBack, titleImage;

	public static synchronized void load() throws IOException {
		new LetterMaker();
		fontSheet = ImageIO.read(new File("fonts/font1.png"));
		menuBack = scale(ImageIO.read(new File("images/menu_back_2.png")), 2);
		titleImage = scale(ImageIO.read(new File("images/title.png")), 4);
	}

	public static BufferedImage scale(BufferedImage original, int newWidth, int newHeight) {
		BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.drawImage(original, 0, 0, newWidth, newHeight, 0, 0, original.getWidth(), original.getHeight(), null);
		g.dispose();
		return resized;
	}

	public static BufferedImage scale(BufferedImage image, double factor) {
		return scale(image, (int)(image.getWidth() * factor), (int)(image.getHeight() * factor));
	}

	public static BufferedImage flip(BufferedImage original, boolean hz, boolean vt) {
		if (!hz && !vt) {
			return original;
		}
		int w = original.getWidth();
		int h = original.getHeight();
		BufferedImage flipped = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = flipped.createGraphics();
		if (hz && vt) {
			g.drawImage(original, 0, 0, w, h, w, h, 0, 0, null);
		} else if (hz) {
			g.drawImage(original, 0, 0, w, h, w, 0, 0, h, null);
		} else if (vt) {
			g.drawImage(original, 0, 0, w, h, 0, h, w, 0, null);
		}
		g.dispose();
		return flipped;
	}

	public static void drawCentered(Graphics2D g, BufferedImage img, int x, int y) {
		int sx = x - img.getWidth() / 2;
		int sy = y - img.getHeight() / 2;
		g.drawImage(img, sx, sy, null);
	}
}
