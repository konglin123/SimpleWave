package com.example.simplewaveview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SimpleWaveView extends View {

    private float startX;
    private float startY;
    private Paint paint;
    private int radios;
    private int alpha;

    public SimpleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    private void initPaint() {
        radios=5;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(radios/3);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            radios+=5;

            alpha = paint.getAlpha();
            alpha -=5;
            if (alpha <0){
                alpha =0;
            }
            paint.setAlpha(alpha);
            paint.setStrokeWidth(radios/3);
            invalidate();

        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(paint.getAlpha()>0&&startX>0){
            canvas.drawCircle(startX,startY,radios,paint);
            handler.sendEmptyMessageDelayed(0,50);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                //重新换一支画笔，因为上一支画笔alpha为0了，不会执行canvas.drawCircle了
                initPaint();
                invalidate();
                break;
        }
        return true;
    }
}
