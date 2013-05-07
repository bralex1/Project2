package com.project2.maddash;

/**
 * In essence, a container class for holding frames of an
 * animation.
 */

import java.util.ArrayList;
import com.project2.framework.Image;


public class Animation {

	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	private long animTime;
	private long totalDuration;

	public Animation() {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	/**
	 * Add a frame to the total animation
	 * @param image Image to be displayed
	 * @param duration Duration of the new frame
	 */
	public synchronized void addFrame(Image image, long duration) {
		// include new frame in the total duration
		totalDuration += duration;
		// add frame to the list
		frames.add(new AnimFrame(image, totalDuration));
	}

	/**
	 * Updates the frame based on the elapsed time, allowing for
	 * frame-rate independent animation.
	 * @param elapsedTime Time since last update. 
	 */
	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;
			
			// scales the animation time
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			// search for the frame to be displayed
			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;
			}
		}
	}

	/**
	 * Retrieve the image of the current frame.
	 */
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currentFrame).image;
		}
	}

	/**
	 * Retrieve a specific frame.
	 * @param i Index of frame
	 */
	private AnimFrame getFrame(int i) {
		return frames.get(i);
	}

	/**
	 * Image and end time of one frame.
	 */
	private class AnimFrame {

		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}
