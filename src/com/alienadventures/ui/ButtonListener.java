package com.alienadventures.ui;

import com.alienadventures.Camera;

import java.awt.*;

public interface ButtonListener {
	void update();
	
	void render(Graphics g, Camera camera);
}
