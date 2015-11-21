package com.whackanandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameActivity extends Activity {
	public PeelsFontTextView tvHighscore;
	public LinearLayout cover;
	public ImageView[] phones;
	public PeelsFontTextView tvScoreInGame;
	public PeelsFontTextView tvScore;
	public PeelsFontTextView tvScoreText;
	public PeelsFontTextView pauseText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_game);
		tvHighscore = (PeelsFontTextView)findViewById (R.id.tvHighscore);
		cover = (LinearLayout)findViewById (R.id.game_cover);
		pauseText = (PeelsFontTextView)findViewById (R.id.pauseText);
		phones = new ImageView[] {
				(ImageView)findViewById (R.id.phone1),
				(ImageView)findViewById (R.id.phone2),
				(ImageView)findViewById (R.id.phone3),
				(ImageView)findViewById (R.id.phone4),
				(ImageView)findViewById (R.id.phone5),
				(ImageView)findViewById (R.id.phone6),
				(ImageView)findViewById (R.id.phone7),
				(ImageView)findViewById (R.id.phone8)
		};

		tvScoreInGame = (PeelsFontTextView)findViewById (R.id.tvScoreInGame);
		tvScore = (PeelsFontTextView)findViewById (R.id.tvScore);
		tvScoreText = (PeelsFontTextView)findViewById (R.id.tvScoreText);

		Game.InitializeGame (this);
	}

	public void removeCover (View view) {
		Animation anim = AnimationUtils.loadAnimation (this, R.anim.cover_move_out);
		anim.setAnimationListener (new Animation.AnimationListener () {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				GameActivity.this.cover.setVisibility (View.GONE);
				Game.getInstance ().startGame ();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		cover.startAnimation (anim);
	}

	public void gameOver () {
		tvScore.setText (Integer.toString (Game.getInstance ().getScore ()));
		tvHighscore.setText (Integer.toString (Game.getInstance ().getHighscore ()));
		tvScoreText.setVisibility (View.VISIBLE);
		tvScore.setVisibility (View.VISIBLE);

		Animation anim = AnimationUtils.loadAnimation (this, R.anim.cover_fade_in);
		anim.setAnimationListener (new Animation.AnimationListener () {
			@Override
			public void onAnimationStart(Animation animation) {
				GameActivity.this.cover.setClickable (false);
				GameActivity.this.cover.setVisibility (View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Game.InitializeGame (GameActivity.this);
				GameActivity.this.cover.setClickable (false);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		cover.startAnimation (anim);
	}

	public void pauseClick (View view) {
		if (Game.getInstance ().isPaused ()) {
			Game.getInstance ().startGame ();
		} else {
			Game.getInstance ().pauseGame ();
		}
	}

	@Override
	public void onPause () {
		super.onPause ();
		Game.getInstance ().pauseGame ();
	}
}
