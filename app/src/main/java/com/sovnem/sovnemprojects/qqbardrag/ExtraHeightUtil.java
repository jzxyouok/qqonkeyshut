package com.sovnem.sovnemprojects.qqbardrag;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Rect;

public class ExtraHeightUtil {
	public static int getStatusBarHeight(Activity act) {
		Rect frame = new Rect();
		act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

	public static int getActionBarHeight(Activity act) {
		ActionBar actionBar = act.getActionBar();
		int actH = 0;
		if (actionBar != null) {
			actH = actionBar.getHeight();
		}
		return actH;
	}

	public static int getExtraHeight(Activity act) {
		return getActionBarHeight(act) + getStatusBarHeight(act);
	}
}
