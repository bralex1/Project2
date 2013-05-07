package com.project2.maddash;

/**
 * Main class that handles the running game.
 */

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

		// two backgrounds and grounds to allow for infinite scrolling
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		gr1 = new Ground(0, 450);
		gr2 = new Ground(960, 450);
		
		player = new Player();
		obstacles = new ArrayList<Obstacle>();

		runner = Assets.runner;
		
		obsCount = 0;

		// add each frame of the runner animation
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

	/**
	 * Update based on current game state.
	 */
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

	/**
	 * Waits for user to touch the screen to start the game.
	 */
	private void updateReady(List<TouchEvent> touchEvents) {
		if (touchEvents.size() > 0) {
			state = GameState.Running;
			touchEvents.remove(0);
		}
	}

	/**
	 * Continually checks touch events while the game is running.
	 */
	private void updateRunning(List<TouchEvent> touchEvents) {

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			
			// jump if screen is tapped
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (event.x > 745 && event.y < 55) {
					touchEvents.clear();
					state = GameState.Paused;
				} else {
					player.jump();					
				}
			}

		}

		// update world
		bg1.update();
		bg2.update();
		gr1.update();
		gr2.update();
		player.update();

		// determine total travel distance
		distance = gr1.getDistance();

		// set the speed of the world based on travel distance
		speedX = getSpeed((int) distance);

		// update speeds
		bg1.setSpeedX(speedX);
		bg2.setSpeedX(speedX);
		gr1.setSpeedX(speedX * 3);
		gr2.setSpeedX(speedX * 3);

		// update sprite
		currentSprite = anim.getImage();
		
		// adds a new obstacle every 400 pixels
		for (int i = 0; i < (distance / 400) - obsCount; i++) {
			obstacles.add(Obstacle.nextObstacle());
			obsCount++;
		}
		
		// checks for collisions
		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle obs = obstacles.get(i);
			obs.update();
			
			if (Rect.intersects(player.rect, obs.rect)) {
				touchEvents.clear();
				state = GameState.GameOver;
			}
			
			if (obs.getX() < -100) {
				obstacles.remove(obs);
			}
		}

		// animate
		animate();

	}

	/**
	 * Handles paused interaction.
	 */
	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				state = GameState.Running;
			}
		}
	}

	/**
	 * Handles game over interaction.
	 */
	private void updateGameOver(List<TouchEvent> touchEvents) {

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {
				// frees up memory
				nullify();
				// return to main menu
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	/**
	 * Displays all images.
	 */
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

		// draws additional UI
		if (state == GameState.Ready)
			drawReadyUI();
		if (state == GameState.Running)
			drawRunningUI();
		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	/**
	 * Frees unused space when game ends.
	 */
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

	/**
	 * Move to next frame.
	 */
	private void animate() {
		anim.update((long) (-1 * speedX * 20));
	}

	/**
	 * User interface for a ready game.
	 */
	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(40);
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Ready?", 400, 240, paint);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTextSize(20);

	}

	/**
	 * User interface for a running game.
	 */
	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.pause, 745, 5);
	}

	/**
	 * User interface for a paused game.
	 */
	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(40);
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Paused.", 400, 240, paint);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTextSize(20);

	}

	/**
	 * User interface for game over.
	 */
	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 1281, 801, Color.BLACK);
		paint.setTextSize(30);
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawString("GAME OVER", 400, 200, paint);
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
		nullify();
		game.setScreen(new MainMenuScreen(game));
		return;
	}

	public static Background getBg1() {
		return bg1;
	}

	private static double getSpeed(int distance) {
		double speed = 0;

		if (distance < 5000) {
			speed = -1.7;
		} else if (distance < 10000) {
			speed = -2.0;
		} else if (distance < 50000) {
			speed = -2.3;
		} else if (distance < 100000) {
			speed = -2.9;
		} else if (distance < 500000) {
			speed = -3.4;
		} else if (distance < 1000000) {
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
