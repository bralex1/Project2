package com.project2.maddash;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
	private static Ground gr1, gr2;
	private static Player player;
	private static List<Obstacle> obstacles;
	
	
	private Image currentSprite;
	private Image[] runner;
	private Animation anim;

	private double distance;
	private double speedX;
	private Paint paint;
	private int obsCount;

	public GameScreen(Game game) {
		super(game);

		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		gr1 = new Ground(0, 450);
		gr2 = new Ground(960, 450);
		
		player = new Player();
		obstacles = new ArrayList<Obstacle>();

		runner = Assets.runner;
		
		obsCount = 0;

		anim = new Animation();
		for (int i = 0; i < runner.length; i++) {
			anim.addFrame(runner[i], 50);
		}

		currentSprite = anim.getImage();

		paint = new Paint();
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.LEFT);
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
		if (touchEvents.size() > 0) {
			state = GameState.Running;
			touchEvents.remove(0);
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents) {

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			if (event.type == TouchEvent.TOUCH_DOWN) {
				player.jump();
			}

			if (event.type == TouchEvent.TOUCH_UP) {

			}
		}


		bg1.update();
		bg2.update();
		gr1.update();
		gr2.update();
		player.update();

		distance = gr1.getDistance();

		speedX = getSpeed((int) distance);

		bg1.setSpeedX(speedX);
		bg2.setSpeedX(speedX);
		gr1.setSpeedX(speedX * 3);
		gr2.setSpeedX(speedX * 3);

		currentSprite = anim.getImage();
		
		for (int i = 0; i < (distance / 400) - obsCount; i++) {
			obstacles.add(Obstacle.nextObstacle());
			obsCount++;
		}
		
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle obs = obstacles.get(i);
			obs.update();
			
			if (Rect.intersects(player.rect, obs.rect)) {
				state = GameState.GameOver;
			}
			
			if (obs.getX() < -100) {
				obstacles.remove(obs);
			}
		}

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
		g.drawImage(Assets.ground, gr1.getX(), gr1.getY());
		g.drawImage(Assets.ground, gr2.getX(), gr2.getY());
		
		g.drawString("Score: " + (int) (distance / 100), 30, 30, paint);
		g.drawImage(currentSprite, player.getCornerX(), player.getCornerY());
		
		for (Obstacle obs : obstacles) {
			g.drawRect((int) obs.getX(), obs.getY(), obs.getWidth(), obs.getHeight(), Color.CYAN);
		}

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
		gr1 = null;
		gr2 = null;
		player = null;
		obstacles = null;
		currentSprite = null;
		anim = null;
		runner = null;
		System.gc();

	}

	private void animate() {
		anim.update((long) (-1 * speedX * 20));
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Ready?", 400, 240, paint);
		paint.setTextAlign(Paint.Align.LEFT);

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
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawString("GAME OVER.", 400, 220, paint);
		g.drawString("Final score: " + (int) (distance / 100), 400, 240, paint);

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
	public void backButton() {
		pause();
		nullify();
		game.setScreen(new MainMenuScreen(game));
		return;
	}

	public static Background getBg1() {
		return bg1;
	}

	public static double getSpeed(int distance) {
		double speed = 0;

		if (distance < 5000) {
			speed = -1.7;
		} else if (distance < 10000) {
			speed = -2.0;
		} else if (distance < 50000) {
			speed = -2.3;
		} else if (distance < 500000) {
			speed = -2.9;
		} else if (distance < 750000) {
			speed = -3.4;
		} else if (distance < 1500000) {
			speed = -3.9;
		} else {
			speed = -4.3;
		}

		return speed;
	}
	
	public static Ground getGr1() {
		return gr1;
	}

}
