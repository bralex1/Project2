package com.project2.maddash;

public class Background {
	
	private int bgY;
	private double bgX, speedX;
	
	public Background(int x, int y){
		bgX = x;
		bgY = y;
		speedX = -0.4;
	}
	
	public void update() {
		bgX += speedX;

		if (bgX <= -2160){
			bgX += 4320;
		}
	}

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
