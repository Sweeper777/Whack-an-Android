package com.whackanandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class PeelsFontTextView extends TextView {
	
	public PeelsFontTextView(Context context, AttributeSet attrs) {
		super (context, attrs);
		setFont (context);
	}

	public PeelsFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super (context, attrs, defStyleAttr);
		setFont (context);
	}

	public PeelsFontTextView(Context context) {
		super (context);
		setFont (context);
	}

	private void setFont (Context context) {
		Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/peels.ttf");
		this.setTypeface(face);
	}
}
