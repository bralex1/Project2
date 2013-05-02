package com.project2.maddash;

import com.project2.framework.Image;
import com.project2.framework.Music;
import com.project2.framework.Sound;

public class Assets {

	public static Image menu, splash, background;
	public static Image[] runner = new Image[29];
	public static Sound click;
	public static Music theme;

	public static void load(MainActivity game) {
		// TODO Auto-generated method stub
		theme = game.getAudio().createMusic("menutheme.mp3");
		theme.setLooping(true);
		theme.setVolume(0.85f);
		theme.play();
	}

}
