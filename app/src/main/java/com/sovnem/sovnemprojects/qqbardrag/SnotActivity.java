package com.sovnem.sovnemprojects.qqbardrag;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.sovnem.qqbardrag.R;

/**
 * @Description: TODO
 * @author monkey-d-wood
 * @date 2015-7-23 上午10:09:19
 */
public class SnotActivity extends Activity {

	private AnimationDrawable animationDrawable;
	private TextView tView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(new TextCenter(this));
		setContentView(R.layout.activity_main);
		initView();
	}

	boolean isIn;

	private void initView() {
		getHeights();
		tView = (TextView) findViewById(R.id.textView1);
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		animationDrawable = (AnimationDrawable) imageView.getDrawable();
	}

	boolean hasgetHeight;

	private void getHeights() {
		exHeight = (int) ExtraHeightUtil.getExtraHeight(this);
		Log.i("info", "extraheight：" + exHeight);
	}

	public void go(View view) {
		ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationX", 0, 400).setDuration(1000);
		anim.setInterpolator(new OvershootInterpolator(4));
		anim.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Intent intent = new Intent(SnotActivity.this, ListviewActivity.class);
				startActivity(intent);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});
		anim.start();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	boolean hasAddView = false;
	int[] location = new int[2];

	boolean isTouching;
	private int exHeight;

}
