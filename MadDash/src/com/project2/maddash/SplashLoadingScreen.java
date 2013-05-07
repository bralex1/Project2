package com.project2.maddash;

/**
 * Only purpose is to load the splash image to be displayed
 * while the loading screen loads all other files.
 */

import com.project2.framework.Game;
import com.project2.framework.Graphics;
import com.project2.framework.Graphics.ImageFormat;
import com.project2.framework.Screen;

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	/**
	 * Initialize splash image and move to the loading screen.
	 */
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splash = g.newImage("splash.jpg", ImageFormat.RGB565);

		game.setScreen(new LoadingScreen(game));

	}

	@Override
	public void paint(float deltaTime) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}