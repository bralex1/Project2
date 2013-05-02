package com.project2.maddash;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;

import com.project2.framework.Game;
import com.project2.framework.Graphics;
import com.project2.framework.Image;
import com.project2.framework.Input.TouchEvent;
import com.project2.framework.Screen;

public class GameScreen extends Screen {

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	GameState state = GameState.Ready;

	private static Background bg1, bg2;
	private Image currentSprite;
	private Image[] runner;
	private Animation anim;

	int livesLeft = 1;
	Paint paint;

	public GameScreen(Game game) {
		super(game);

		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

		runner = Assets.runner;

		anim = new Animation();
		for (int i = 0; i < runner.length; i++) {
			anim.addFrame(runner[i], 50);
		}

		currentSprite = anim.getImage();

		paint = new Paint();
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

	}

	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		if (state == GameState.Ready)
			updateReady(touchEvents);
		if (state == GameState.Running)
			updateRunning(touchEvents);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);

	}

	private void updateReady(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents) {

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {

			}

			if (event.type == TouchEvent.TOUCH_UP) {

			}
		}

		if (livesLeft == 0) {
			state = GameState.GameOver;
		}

		bg1.update();
		bg2.update();

		currentSprite = anim.getImage();

		animate();

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 300 && event.x < 980 && event.y > 100
						&& event.y < 500) {
					nullify();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}

		}

	}

	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, bg1.getBgX(), bg1.getBgY());
		g.drawImage(Assets.background, bg2.getBgX(), bg2.getBgY());
		g.drawImage(currentSprite, 50, 380);

		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	private void nullify() {

		paint = null;
		bg1 = null;
		bg2 = null;
		currentSprite = null;
		anim = null;
		runner = null;
		System.gc();

	}

	private void animate() {
		anim.update(50);
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawARGB(155, 0, 0, 0);
		g.drawString("Ready?", 400, 240, paint);

	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		// Darken the entire screen so you can display the Paused screen.
		g.drawARGB(155, 0, 0, 0);

	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		g.drawString("GAME OVER.", 400, 240, paint);

	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public synchronized void backButton() {
		pause();
		nullify();
		game.setScreen(new MainMenuScreen(game));
	}

}
