package com.project2.maddash;

/**
 * Tracks information about an obstacle object.
 */

import java.util.Random;

import android.graphics.Rect;

public class Obstacle {
	
	private double x, speedX; // x position and speed
	private int y, width, height; // y position and dimensions
	public Rect rect; // bounding box
	
	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// obstacles move with the ground
		speedX = GameScreen.getGr1().getSpeedX();
	}
	
	/**
	 * Update position and bounding box based on current speed.
	 */
	public void update() {
		speedX = GameScreen.getGr1().getSpeedX();
		x += speedX;
		rect = new Rect((int) x, y, (int) (x + width), y + height);
	}
	
	/**
	 * Randomly generates an obstacle of width 40-55 and height 40-90. 
	 */
	public static Obstacle nextObstacle() {
		Random rng = new Random();
		
		int width = rng.nextInt(15) + 40;
		int height = rng.nextInt(50) + 40;
		int y = 450 - height + 1;
		int x = 800;
		
		return new Obstacle(x, y, width, height);
	}

	/* Accessors. */
	
	public double getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
