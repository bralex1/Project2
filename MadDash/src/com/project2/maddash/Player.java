package com.project2.maddash;

/**
 * Tracks information about the player.
 */

import android.graphics.Rect;

public class Player {
	final int JUMPSPEED = -15; // initial jump speed

	private int cornerX = 50; // x coordinate
	private int cornerY = 382; // y coordinate

	private boolean jumped = false; // if player has jumped

	private int speedY = 0; // y speed

	public Rect rect = new Rect(0, 0, 0, 0); // bounding box

	/**
	 * Update position when the player has jumped.
	 */
	public void update() {

		// make sure player does not "fall through the ground"
		if (cornerY + speedY >= 382) {
			cornerY = 382;
			
		// update position
		} else {
			cornerY += speedY;
		}

		// updates speed when jumped
		if (jumped == true) {
			
			speedY += 1;

			// if player reaches the ground, reset jump values
			if (cornerY + speedY >= 382) {
				cornerY = 382;
				speedY = 0;
				jumped = false;
			}

		}

		// modify bounding box
		rect.set(cornerX + 19, cornerY, cornerX + 42, cornerY + 60);
	}

	/**
	 * Set variables for jump conditions.
	 */
	public void jump() {
		if (jumped == false) {
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	
	/* Accessors and mutators. */
	
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
