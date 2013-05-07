package com.project2.maddash;

/**
 * This class is currently unused.
 */

import android.graphics.Rect;

import com.project2.framework.Image;

public class Tile {

	private int tileX, tileY;
	private double speedX;
	public int type;
	public Image tileImage;

	private Background bg = GameScreen.getBg1();

	private Rect r;

	public Tile(int x, int y, int typeInt) {


	}

	public void update() {
		speedX = bg.getSpeedX() * 3;
		tileX += speedX;
		r.set(tileX, tileY, tileX + 30, tileY + 30);
	}
	
	public int getX() {
		return tileX;
	}
	
	public int getY() {
		return tileY;
	}
	
	public Image getImage() {
		return tileImage;
	}
	
	public void setX(int x) {
		tileX = x;
	}
	
	public double getSpeedX() {
		return speedX;
	}

}