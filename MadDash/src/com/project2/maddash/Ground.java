package com.project2.maddash;

import com.project2.framework.Graphics;

public class Ground {

	private Tile[] pieces;
	private double distance;
	
	public Ground() {
		pieces = new Tile[28];
		distance = 0;
		
		for (int i = 0; i < pieces.length; i++) {
			pieces[i] = new Tile(30 * i, 450, 1);
		}
	}
	
	public void update() {
		for (int i = 0; i < pieces.length; i++) {
			pieces[i].update();
			if (pieces[i].getX() < -30) {
				int j = i - 1;
				
				if (i == 0) {
					j = pieces.length - 1;
				}
				
				pieces[i].setX(pieces[j].getX() + 30);
			}
		}
		
		distance -= pieces[0].getSpeedX();
	}
	
	public void paint(Graphics g) {
		for (Tile tile : pieces) {
			g.drawImage(tile.getImage(), tile.getX(), tile.getY());
		}
	}
	
	public double getDistance() {
		return distance;
	}
	
	
	
}
