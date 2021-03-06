package com.project2.maddash;

/**
 * Initializes all references in the Assets class to the
 * appropriate external files. All files must be stored in the
 * assets folder.
 */

import com.project2.framework.Game;
import com.project2.framework.Graphics;
import com.project2.framework.Graphics.ImageFormat;
import com.project2.framework.Screen;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

	/**
	 * Initializes the files.
	 */
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("menu.png", ImageFormat.RGB565);
		Assets.click = game.getAudio().createSound("pop.wav");
		Assets.background = g.newImage("background.png", ImageFormat.RGB565);
		Assets.ground = g.newImage("ground.png", ImageFormat.RGB565);
		Assets.pause = g.newImage("pause.png", ImageFormat.RGB565);
		
		for (int i = 1; i <= 29; i++) {
			Assets.runner[i - 1] = g.newImage("runner/runner" + i + ".png", ImageFormat.RGB565);
		}

		game.setScreen(new MainMenuScreen(game));
	}

	/**
	 * Displays the splash image loaded by the splash loading screen while all
	 * other files are loaded.
	 */
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
	}

	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

	public void backButton() {

	}

}
