package com.russell.calmmusic.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Russell on 16/1/27.
 */
public class PlayController extends View {
    private Paint paint;
    public PlayController(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,100,90,paint);
    }
}
