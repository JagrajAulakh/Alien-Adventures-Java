package com.alienadventures.image;

import com.alienadventures.Resources;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation extends ImageType {

	private int delay, delayMax, frame;
	private ArrayList<BufferedImage> frames;
	private boolean repeat;

	public Animation() { this(6); }

	public Animation(int delay) { this(delay, true); }

	public Animation(int delay, boolean repeat) {
		this.delayMax = delay;
		this.delay = 0;
		frames = new ArrayList<BufferedImage>();
		this.repeat = repeat;
	}
	public int size() { return frames.size(); }
	public void addFrame(BufferedImage img) {
		frames.add(img);
	}
	
	public int getDelayCounter() { return delay; }
	
	@Override
	public void reset() {
		frame = delay = 0;
	}

	@Override
	public void update() {
		delay++;
		if (delay > delayMax) {
			delay = 0;
			frame++;
			if (frame >= frames.size()) {
				if (repeat) frame = 0;
				else frame--;
			}
		}
	}

	public BufferedImage getImage(int frame) {
		return frames.get(frame);
	}
	@Override
	public BufferedImage getImage() {
		return frames.get(frame);
	}

	@Override
	public ImageType flip(boolean h, boolean v) {
		Animation flipped = new Animation(delayMax);
		for (BufferedImage frame:frames) {
			flipped.addFrame(Resources.flip(frame, h, v));
		}
		return flipped;
	}
}
