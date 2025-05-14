package com.example.zt_textview_spanutil;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class RoundBackgroundSpan extends ReplacementSpan {
    private int backgroundColor;
    private int textColor;
    private float cornerRadius;
    private float padding;

    public RoundBackgroundSpan(int backgroundColor, int textColor, float cornerRadius, float padding) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.cornerRadius = cornerRadius;
        this.padding = padding;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        // 保存原始颜色
        int originalColor = paint.getColor();

        // 测量文本宽度
        float textWidth = paint.measureText(text, start, end);

        // 绘制圆角矩形背景
        paint.setColor(backgroundColor);
        RectF rect = new RectF(x - padding, top, x + textWidth + padding * 2, bottom);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        // 绘制文本
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + padding, y, paint);

        // 恢复原始颜色
        paint.setColor(originalColor);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end) + Math.round(padding * 2));
    }
}