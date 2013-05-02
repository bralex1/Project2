package com.project2.maddash;

import android.graphics.Rect;

public class Player {
	final int JUMPSPEED = -15;
	
	private int cornerX = 50;
	private int cornerY = 382;
	
	private boolean jumped = false;
	
	private int speedY = 0;
	
	
	public static Rect rect = new Rect(0, 0, 0, 0);
	
	public void update(){
		cornerY += speedY;
		speedY += 1;
		if(speedY > 3){
			jumped = true;
		}
		
		if(cornerY < 382){
			cornerY = 382;
		}
		
		rect.set(cornerX, cornerY, cornerX + 50, cornerY + 68);
	}
	
	public void jump(){
		if(jumped == false){
			speedY = JUMPSPEED;
			jumped = true;
		}
	}
	
	

	public int getJUMPSPEED() {
		return JUMPSPEED;
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
