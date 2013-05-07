package com.project2.maddash;

/**
 * Tracks position and speed of the background.
 */

public class Background {
	
	private int bgY; // y position (shouldn't change)
	private double bgX, speedX; // x position and speed
	
	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = -0.4;
	}
	
	/**
	 * Update position based on current speed.
	 */
	public void update() {
		bgX += speedX;

		// shift background over if it moves entirely off-screen
		if (bgX <= -2160){
			bgX += 4320;
		}
	}

	
	/* Accessors and mutators. */
	
	public int getBgX() {
		return (int) bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
}
