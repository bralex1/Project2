package com.project2.maddash;

/**
 * Basic class that stores references to all external files used
 * in the game. Any item listed here must be loaded by a loading
 * screen before using.
 */

import com.project2.framework.Image;
import com.project2.framework.Music;
import com.project2.framework.Sound;

public class Assets {

	public static Image menu, splash, background, ground;
	public static Image[] runner = new Image[29];
	public static Sound click;
	public static Music theme;

	public static void load(MainActivity game) {
		theme = game.getAudio().createMusic("maintheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}

}
