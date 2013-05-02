package com.project2.maddash;


public class Ground {

	private int y;
	private double x, speedX;
	private double distance;
	
	public Ground(int x, int y){
		this.x = x;
		this.y = y;
		distance = 0;
		speedX = GameScreen.getBg1().getSpeedX() * 3;
	}
	
	public void update() {
		x += speedX;
		distance -= speedX;

		if (x <= -960){
			x += 1920;
		}
	}

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
