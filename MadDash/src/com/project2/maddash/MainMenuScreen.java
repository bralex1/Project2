package com.project2.maddash;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.project2.framework.Game;
import com.project2.framework.Graphics;
import com.project2.framework.Input.TouchEvent;
import com.project2.framework.Screen;

public class MainMenuScreen extends Screen {

	private boolean helping; // user is looking at help text
	private boolean options; // user is looking at options menu
	private Paint p;

	public MainMenuScreen(Game game) {
		super(game);
		
		helping = false;
		options = false;
		p = new Paint();
		
		p.setColor(Color.WHITE);
		p.setTextAlign(Paint.Align.CENTER);
		p.setTextSize(30);
		p.setAntiAlias(true);
	}

	/**
	 * Update the menu screen based on touch events.
	 */
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		
		// checks all user taps
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				// find where the user has tapped (base screen)
				if (!helping && !options) {
					// play game
					if (inBounds(event, 175, 296, 221, 57)) {
						game.setScreen(new GameScreen(game));
					}

					// get help
					if (inBounds(event, 98, 368, 113, 60)) {
						helping = true;
					}

					// options
					if (inBounds(event, 233, 368, 163, 61)) {
						options = true;
					}
					
				// otherwise, already looking at help/options and wants to close
				} else {
					helping = false;
					options = false;
				}

			}
		}
	}

	/**
	 * Determines if a touch event is within specific bounds.
	 */
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {

		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;

	}

	/**
	 * Display graphics of the menu screen.
	 */
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		
		// draw menu
		g.drawImage(Assets.menu, 0, 0);

		// draw help text
		if (helping) {
			g.drawARGB(155, 0, 0, 0);
			g.drawString("Run as far as you can!", 400, 200, p);
			g.drawString("Tap to jump over obstacles!", 400, 240, p);
			g.drawString("Be careful, the game speeds up!", 400, 280, p);
		}
		
		// draw options menu
		if (options) {
			g.drawARGB(155, 0, 0, 0);
			g.drawString("Options have not yet been implemented in this version.", 400, 240, p);
		}
	}

	
	public void pause() {

	}

	public void resume() {

	}

	public void dispose() {

	}

	public void backButton() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}


}
