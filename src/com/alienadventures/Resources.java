package com.alienadventures;

import com.alienadventures.image.Animation;
import com.alienadventures.image.ImageType;
import com.alienadventures.image.SingleImage;
import com.alienadventures.io.Reader;
import com.alienadventures.ui.LetterMaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Resources {
	public static final double SCALE = 3;
	public static BufferedImage menuBack, titleImage, titleBanner, fireBallImage;
	public static BufferedImage fontSheet, buttonSheet, miscSheet, playerSheet;
	public static ArrayList<BufferedImage> buttonImages;
	public static ArrayList<ImageType[]> playerImages;

	public static synchronized void load() throws IOException {
		new LetterMaker();
		fontSheet = ImageIO.read(new File("fonts/font1.png"));
		menuBack = scale(ImageIO.read(new File("images/menu_back.png")), 2);
		titleImage = scale(ImageIO.read(new File("images/title.png")), 3);
		titleBanner = scale(ImageIO.read(new File("images/title_banner.png")), 3);

		buttonSheet = ImageIO.read(new File("images/sheets/button_sheet.png"));
		ArrayList<int[]> buttonCo = Reader.getCo("data/sheets_cos/button_sheet_cos.txt");
		buttonImages = new ArrayList<BufferedImage>();
		for (int[] co : buttonCo) {
			buttonImages.add(scale(getImage(buttonSheet, co), 4));
		}

		miscSheet = ImageIO.read(new File("images/sheets/misc_sheet.png"));
		ArrayList<int[]> miscCo = Reader.getCo("data/sheets_cos/button_sheet_cos.txt");
		fireBallImage = scale(miscSheet.getSubimage(112, 80, 16, 16), 4);

		ArrayList<ImageType> playerImageList = new ArrayList<ImageType>();
		playerSheet = ImageIO.read(new File("images/sheets/player_sheet.png"));
		ArrayList<int[]> playerCo = Reader.getCo("data/sheets_cos/player_sheet_cos.txt");
		for (int p = 0; p < 5; p++) {
			int i = p * 14;
			// 0 STILL
			ImageType img = new SingleImage(scale(getImage(playerSheet, playerCo.get(i)), SCALE));
			playerImageList.add(img);
			// 1 WALKING
			img = new Animation();
			for (int j = 0; j < 4; j++) {
				((Animation)img).addFrame(scale(getImage(playerSheet, playerCo.get(i + j + 1)), SCALE));
			}
			playerImageList.add(img);
			// 2 ?
			img = new SingleImage(scale(getImage(playerSheet, playerCo.get(i + 5)), SCALE));
			playerImageList.add(img);
			// 3 JUMPING
			img = new SingleImage(scale(getImage(playerSheet, playerCo.get(i + 6)), SCALE));
			playerImageList.add(img);
			// 4 DUCKING
			img = new Animation(20);
			for (int j = 0; j < 2; j++) {
				((Animation)img).addFrame(scale(getImage(playerSheet, playerCo.get(i + j + 7)), SCALE));
			}
			playerImageList.add(img);
			// 5 HURT
			img = new SingleImage(scale(getImage(playerSheet, playerCo.get(i + 9)), SCALE));
			playerImageList.add(img);
			// 6 CLIMBING
			img = new Animation(20);
			for (int j = 0; j < 2; j++) {
				((Animation)img).addFrame(scale(getImage(playerSheet, playerCo.get(i + j + 10)), SCALE));
			}
			playerImageList.add(img);
			// 7 SWIMMING
			img = new Animation(20);
			for (int j = 0; j < 2; j++) {
				((Animation)img).addFrame(scale(getImage(playerSheet, playerCo.get(i + j + 12)), SCALE));
			}
			playerImageList.add(img);
		}
		playerImages = insertFlipped(playerImageList);


//		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static BufferedImage getImage(BufferedImage sheet, int[] co) {
		return sheet.getSubimage(co[0], co[1], co[2], co[3]);
	}

	private static ArrayList<ImageType[]> insertFlipped(ArrayList<ImageType> source) {
		ArrayList<ImageType[]> flipped = new ArrayList<ImageType[]>();
		for (ImageType i:source) {
			ImageType[] parts = new ImageType[] {i, i.flip(true, false)};
			flipped.add(parts);
		}
		return flipped;
	}

	public static BufferedImage scale(BufferedImage original, int newWidth, int newHeight) {
		BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
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

	public static void drawCentered(Graphics g, BufferedImage img, int x, int y) {
		int sx = x - img.getWidth() / 2;
		int sy = y - img.getHeight() / 2;
		g.drawImage(img, sx, sy, null);
	}

	public static BufferedImage copyImage(BufferedImage source) {
		BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.getGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return b;
	}

	public static BufferedImage darken(BufferedImage source) { return darken(source, 50); }

	public static BufferedImage darken(BufferedImage source, int amount) {
		BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = b.getGraphics();
		g.drawImage(source, 0, 0, null);
		g.setColor(new Color(0, 0, 0, amount));
		g.fillRect(0, 0, b.getWidth(), b.getHeight());
		return b;
	}
}
