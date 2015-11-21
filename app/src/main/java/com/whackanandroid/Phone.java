package com.whackanandroid;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Phone {
	private ImageView image;
	private Handler handler;
	private Random random;
	private State state;
	private MediaPlayer dingPlayer;
	private MediaPlayer dongPlayer;
	private boolean paused;

	private Runnable appearTask = new Runnable () {
		@Override
		public void run() {
			if (!paused) {
				if (random.nextInt (3) < 2) {
					setState (State.ANDROID);
				} else {
					setState (State.APPLE);
				}
				handler.postDelayed (disapperTask, random.nextInt (1000) + 700);
			}
		}
	};

	private Runnable disapperTask = new Runnable () {
		@Override
		public void run() {
			if (!paused) {
				setState (State.NONE);
				handler.postDelayed (appearTask, random.nextInt (2000) + 1000);
			}
		}
	};


	private View.OnClickListener imageOnClick = new View.OnClickListener () {
		@Override
		public void onClick(View v) {
			if (!Game.getInstance ().isPaused ()) {
				if (Phone.this.getState () == State.ANDROID) {
					Game.getInstance ().addScore (10);
					Game.getInstance ().getCountDown ().addTime (0.1F);
					dingPlayer.start ();
				} else if (Phone.this.getState () == State.APPLE) {
					Game.getInstance ().addScore (-50);
					Game.getInstance ().getCountDown ().addTime (-5F);
					dongPlayer.start ();
				} else {
					Game.getInstance ().addScore (-10);
					dongPlayer.start ();
				}
				Phone.this.setState (State.NONE);
			}
		}
	};

	public void setState (State value) {
		state = value;
		switch (state) {
			case NONE:
				image.setImageResource (R.drawable.phone);
				break;
			case ANDROID:
				image.setImageResource (R.drawable.androidphone);
				break;
			case APPLE:
				image.setImageResource (R.drawable.applephone);
				break;
		}
	}

	public State getState () {
		return state;
	}

	public void stopTimers () {
		paused = true;
	}

	public void startTimers () {
		paused = false;
		if (getState () == State.NONE) {
			handler.postDelayed (appearTask, random.nextInt (2000) + 1000);
		} else {
			handler.postDelayed (disapperTask, random.nextInt (1000) + 700);
		}
	}

	public Phone (ImageView view, Random r) {
		view.setOnClickListener (imageOnClick);
		image = view;
		random = r;
		handler = new Handler ();
		image.setImageResource (R.drawable.phone);
		dingPlayer = MediaPlayer.create (view.getContext (), R.raw.android_ding);
		dingPlayer.stop ();
		dingPlayer.prepareAsync ();
		dingPlayer.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop ();
				mp.prepareAsync ();
			}
		});

		dongPlayer = MediaPlayer.create (view.getContext (), R.raw.apple_dong);
		dongPlayer.stop ();
		dongPlayer.prepareAsync ();
		dingPlayer.setOnCompletionListener (new MediaPlayer.OnCompletionListener () {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.stop ();
				mp.prepareAsync ();
			}
		});
	}
}
