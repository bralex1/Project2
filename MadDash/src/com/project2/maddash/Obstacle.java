package com.project2.maddash;

import java.util.Random;

import android.graphics.Rect;

public class Obstacle {
	
	private double x, speedX;
	private int y, width, height;
	public Rect rect;
	
	public Obstacle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		speedX = GameScreen.getGr1().getSpeedX();
	}
	
	public void update() {
		speedX = GameScreen.getGr1().getSpeedX();
		x += speedX;
		rect = new Rect((int) x, y, (int) (x + width), y + height);
	}
	
	public static Obstacle nextObstacle() {
		Random rng = new Random();
		
		int width = rng.nextInt(15) + 30;
		int height = rng.nextInt(50) + 40;
		int y = 450 - height + 1;
		int x = 800;
		
		return new Obstacle(x, y, width, height);
	}

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
