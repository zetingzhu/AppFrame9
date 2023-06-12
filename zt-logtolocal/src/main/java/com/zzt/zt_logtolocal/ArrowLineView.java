package com.zzt.zt_logtolocal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: zeting
 * @date: 2023/5/30
 * 该代码将在 (100, 100) 和 (500, 500) 之间绘制一条直线，
 * 并在 (500 - 30cosθ, 500 - 30sinθ)、(500, 500) 和 (500 - 30cosθ', 500 - 30sinθ') 处绘制一个三角形，
 * 从而实现绘制带箭头的线。其中，distance 表示两点之间的距离，angle 表示两点之间的角度。您可以根据需要调整坐标和颜色等参数。
 */
public class ArrowLineView extends View {
    private Paint mPaint;

    public ArrowLineView(Context context) {
        super(context);
        init();
    }

    public ArrowLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startX = 100f;
        float startY = 100f;
        float endX = 500f;
        float endY = 500f;

        float distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        float angle = (float) Math.toDegrees(Math.atan2(endY - startY, endX - startX));

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);

        Path arrowPath = new Path();
        arrowPath.moveTo(endX - 30f * (float) Math.cos(Math.toRadians(angle - 30f)), endY - 30f * (float) Math.sin(Math.toRadians(angle - 30f)));
        arrowPath.lineTo(endX, endY);
        arrowPath.lineTo(endX - 30f * (float) Math.cos(Math.toRadians(angle + 30f)), endY - 30f * (float) Math.sin(Math.toRadians(angle + 30f)));

        canvas.drawPath(path, mPaint);
        canvas.drawPath(arrowPath, mPaint);
    }
}