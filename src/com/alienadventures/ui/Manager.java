package com.alienadventures.ui;

import com.alienadventures.Camera;

import java.awt.*;

public interface Manager {

	void update();

	void render(Graphics g, Camera camera);
}
