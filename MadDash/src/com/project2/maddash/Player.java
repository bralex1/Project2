package com.project2.maddash;

import android.graphics.Rect;

public class Player {
	final int JUMPSPEED = -15;

	private int cornerX = 50;
	private int cornerY = 382;

	private boolean jumped = false;

	private int speedY = 0;

	public Rect rect = new Rect(0, 0, 0, 0);

	public void update() {

		if (cornerY + speedY >= 382) {
			cornerY = 382;
		} else {
			cornerY += speedY;
		}

		// Handles Jumping
		if (jumped == true) {
			
			speedY += 1;

			if (cornerY + speedY >= 382) {
				cornerY = 382;
				speedY = 0;
				jumped = false;
			}

		}


		rect.set(cornerX + 19, cornerY, cornerX + 42, cornerY + 60);
	}

	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	public int getJUMPSPEED() {
		return JUMPSPEED;
	}

	public int getCornerX() {
		return cornerX;
	}

	public int getCornerY() {
		return cornerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCornerY(int cornerY) {
		this.cornerY = cornerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
}
