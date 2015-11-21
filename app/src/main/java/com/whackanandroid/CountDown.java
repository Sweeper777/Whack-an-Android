package com.whackanandroid;

import android.os.Handler;
import android.widget.TextView;

public class CountDown {
	private Handler handler;
	private int timeLeft;
	private TextView textView;
	private boolean paused;
	private CountDownListener listener;

	private Runnable countDownTask = new Runnable () {
		@Override
		public void run() {
			if (!paused) {
				timeLeft--;
				displayTime ();
				handler.postDelayed (this, 100);
				if (timeLeft == 0) {
					pauseCountDown ();
					if (listener != null)
						listener.onCountDownFinished ();
				}
			}
		}
	};

	private void displayTime () {
		textView.setText (Double.toString (timeLeft / 10.0));
	}

	public void pauseCountDown () {
		paused = true;
	}

	public void addTime (float amount) {
		timeLeft += (int)(amount * 10);
	}

	public void startCountDown () {
		paused = false;
		handler.post (countDownTask);
	}

	public void setCountDownListener (CountDownListener listener) {
		this.listener = listener;
	}

	public CountDown (TextView tv) {
		textView = tv;
		timeLeft = 600;
		handler = new Handler ();
	}
}
