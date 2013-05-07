package com.project2.maddash;

/**
 * Initializes the first screen of the application. All following
 * screens handle remainder of application activity.
 */

import com.project2.framework.Screen;
import com.project2.framework.implementation.AndroidGame;

public class MainActivity extends AndroidGame {

	public static String map;
	boolean firstTimeCreate = true;

	/**
	 * Sets up the application.
	 */
	public Screen getInitScreen() {

		// loads splash screen if first time initializing
		if (firstTimeCreate) {
			Assets.load(this);
			firstTimeCreate = false;
		}

		return new SplashLoadingScreen(this);

	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}


	@Override
	public void onResume() {
		super.onResume();
		Assets.theme.play();

	}

	@Override
	public void onPause() {
		super.onPause();
		Assets.theme.play();

	}

}
