package com.sovnem.qqbardrag;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.sovnem.sovnemprojects.qqbardrag.SnotView;
import com.sovnem.sovnemprojects.qqbardrag.View.RoundBgTextview;

public class SnotContainer extends RelativeLayout {

	private SnotView snotView;// 用于显示鼻涕的view
	private ArrayList<RoundBgTextview> snots;// 所有可以鼻涕化的view集合
	private RoundBgTextview inView;// 被选中的孩子

	public SnotContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		snots = new ArrayList<RoundBgTextview>();
		setWillNotDraw(true);
	}

	public SnotContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SnotContainer(Context context) {
		this(context, null);
	}

	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		super.addView(child, params);
		if (child instanceof RoundBgTextview) {
			if (snotView == null) {
				snotView = new SnotView(getContext());
				addView(snotView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				snotView.bringToFront();
				snotView.setVisibility(View.GONE);
			}
			snots.add((RoundBgTextview) child);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("info", "snotContainer:dispatchTouchEvent");
		if (inView == null) {
			inView = isInViews(ev);
			int[] location = new int[2];
			getLocationInWindow(location);
			if (inView != null) {
				snotView.handlerTvTouchEvent2(ev, inView, location[1]);
				return true;
			}
		} else {

			snotView.handlerTvTouchEvent2(ev, inView, 0);
			if (ev.getAction() == MotionEvent.ACTION_UP) {
				inView = null;
			}
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	private RoundBgTextview isInViews(MotionEvent ev) {
		float eX = ev.getRawX();
		float eY = ev.getRawY();
		for (RoundBgTextview snoTextview : snots) {
			int[] loc = new int[2];
			snoTextview.getLocationOnScreen(loc);
			if (ev.getAction() == MotionEvent.ACTION_DOWN && eX >= loc[0] && eX <= loc[0] + snoTextview.getWidth() && eY >= loc[1] && eY <= loc[1] + snoTextview.getHeight()) {
				Log.i("info", "被选中的view的高度:" + (loc[0] + "," + loc[1]));
				snoTextview.setVisibility(View.GONE);
				return snoTextview;
			}
		}
		return null;
	}

}
