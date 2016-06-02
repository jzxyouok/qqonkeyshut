package com.example.dragdismiss;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;


/**
 *
 * A view panel show what the snot looks like at three states of the snot:
 *          1)The snot is dragged and hasn't been pulled apart;
 *          2)The snot is dragged and has been pulled apart but your finger hasn't left it;
 *          3)Your finger has left the snot alone,the snot is playing an boom animation;
 *
 * @author zjh
 * @description
 * @date 16/6/2.
 */
public class SnotMonitor extends View {

    private int snotState;

    public SnotMonitor(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


}
