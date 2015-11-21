package com.whackanandroid;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Game {
	private static Game instance;

	private int score;
	private int highscore;
	private GameActivity activity;
	private CountDown countDown;
	private Phone[] phones;
	private boolean paused;

	public boolean isPaused() {
		return paused;
	}

	public CountDown getCountDown() {
		return countDown;
	}

	public void addScore(int score) {
		this.score += score;
		if (this.score < 0)
			this.score = 0;
		activity.tvScoreInGame.setText ("Score: " + Integer.toString (this.score));
		if (this.score > getHighscore ()) {
			setHighscore (this.score);
		}
	}

	private void setHighscore(int highscore) {
		this.highscore = highscore;
		activity.tvHighscore.setText (Integer.toString (highscore));
		activity.getPreferences (Context.MODE_PRIVATE).edit().putInt("highscore", highscore).commit ();
	}

	public int getScore() {return score;}

	public int getHighscore() {return highscore;}

	public void pauseGame () {
		for (Phone phone : phones) {
			phone.stopTimers ();
		}

		countDown.pauseCountDown ();
		paused = true;
		activity.pauseText.setVisibility (View.VISIBLE);
	}

	public void startGame () {
		for (Phone phone : phones) {
			phone.startTimers ();
		}

		countDown.startCountDown ();
		paused = false;
		activity.pauseText.setVisibility (View.GONE);
	}
	
	public static Game getInstance() {
		return instance;
	}

	public static void InitializeGame (GameActivity activity) {
		instance = new Game (activity);
	}
	
	private Game(GameActivity activity) {
		this.activity = activity;
		countDown = new CountDown ((TextView)activity.findViewById (R.id.timerValue));
		countDown.setCountDownListener (new CountDownListener () {
			@Override
			public void onCountDownFinished() {
				pauseGame ();
				Game.this.activity.gameOver ();
			}
		});

		Random r = new Random ();
		phones = new Phone[8];
		for (int i = 0 ; i < 8 ; i++) {
			phones[i] = new Phone (activity.phones[i], r);
		}

		setHighscore (activity.getPreferences (Context.MODE_PRIVATE).getInt ("highscore", 0));
		activity.tvScoreInGame.setText ("Score: 0");
	}
}
