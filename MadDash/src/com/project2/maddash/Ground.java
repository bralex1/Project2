package com.project2.maddash;

/**
 * Class used to track position and motion of the ground.
 */

public class Ground {

	private int y; // y position (shouldn't change)
	private double x, speedX; // x position and speed
	private double distance; // total distance moved
	
	public Ground(int x, int y){
		this.x = x;
		this.y = y;
		distance = 0;
		// make ground move 3 times faster than background to allow
		// proper perspective
		speedX = GameScreen.getBg1().getSpeedX() * 3;
	}
	
	/**
	 * Updates the position of the ground based on the speed.
	 */
	public void update() {
		x += speedX;
		distance -= speedX;

		// shift ground over if it moves entirely off-screen
		if (x <= -960){
			x += 1920;
		}
	}

	
	/* Accessors and mutators. */
	
	public int getX() {
		return (int) x;
	}

	public int getY() {
		return y;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}
	
	public double getDistance() {
		return distance;
	}
	
}
