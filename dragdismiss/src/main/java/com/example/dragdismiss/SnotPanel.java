package com.example.dragdismiss;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * The container to combine your content views with a snot monitor;
 * <p>
 * your content view should be a part of this container;
 *
 * @author zjh
 * @description
 * @date 16/6/2.
 */
public class SnotPanel extends RelativeLayout {

    private ArrayList<View> snots;
    private View selectedSnot;

    SnotView snotView;


    public SnotPanel(Context context) {
        super(context);
    }
}
