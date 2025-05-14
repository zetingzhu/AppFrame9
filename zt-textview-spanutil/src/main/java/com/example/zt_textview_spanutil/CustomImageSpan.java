package com.example.zt_textview_spanutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public class CustomImageSpan extends ImageSpan {
    private final WeakReference<Context> contextRef;
    private final int verticalAlignment;
    private int drawableWidth;
    private int drawableHeight;
    private int textSize;

    // 支持多种垂直对齐方式的构造函数
    public CustomImageSpan(@NonNull Context context, @NonNull Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
        this.contextRef = new WeakReference<>(context);
        this.verticalAlignment = verticalAlignment;
    }

    // 设置图片尺寸（按比例缩放）
    public void setDrawableSize(int targetWidth, int maxHeight) {
        Drawable drawable = getCachedDrawable();
        if (drawable != null) {
            int originalWidth = drawable.getIntrinsicWidth();
            int originalHeight = drawable.getIntrinsicHeight();

            // 按比例缩放
            float ratio = (float) targetWidth / originalWidth;
            drawableWidth = targetWidth;
            drawableHeight = Math.min((int) (originalHeight * ratio), maxHeight);

            // 更新 Drawable 边界
            drawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }
    }

    // 设置文本大小（用于垂直对齐计算）
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       @Nullable Paint.FontMetricsInt fm) {
        Drawable drawable = getCachedDrawable();
        if (drawable != null) {
            Rect rect = drawable.getBounds();
            if (fm != null) {
                // 根据不同的垂直对齐方式调整字体度量
                switch (verticalAlignment) {
                    case ALIGN_BASELINE:
                        fm.descent = rect.height() / 2;
                        fm.bottom = fm.descent;
                        fm.ascent = -rect.height() + fm.descent;
                        fm.top = fm.ascent;
                        break;
                    case ALIGN_CENTER: // 自定义对齐方式：居中对齐
                        int halfTextSize = textSize / 2;
                        int halfDrawableHeight = rect.height() / 2;
                        fm.ascent = -halfTextSize - halfDrawableHeight;
                        fm.top = fm.ascent;
                        fm.descent = halfTextSize - halfDrawableHeight;
                        fm.bottom = fm.descent;
                        break;
                    default: // 默认对齐方式
                        fm.ascent = -rect.height();
                        fm.top = fm.ascent;
                        fm.descent = 0;
                        fm.bottom = 0;
                        break;
                }
            }
            return rect.right;
        }
        return 0;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, @NonNull Paint paint) {
        Drawable drawable = getCachedDrawable();
        if (drawable != null) {
            canvas.save();

            // 计算绘制位置（根据垂直对齐方式调整）
            int transY = 0;
            switch (verticalAlignment) {
                case ALIGN_BASELINE:
                    transY = y - drawable.getBounds().bottom;
                    break;
                case ALIGN_CENTER: // 自定义对齐方式：居中对齐
                    int halfTextSize = textSize / 2;
                    int halfDrawableHeight = drawable.getBounds().height() / 2;
                    transY = y - halfTextSize - halfDrawableHeight;
                    break;
                default: // 默认对齐方式
                    transY = top;
                    break;
            }

            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    // 获取缓存的 Drawable
    private Drawable getCachedDrawable() {
        Drawable drawable = getDrawable();
        Context context = contextRef.get();
        if (drawable == null && context != null) {
            // 如果 Drawable 为空，尝试重新获取（处理资源回收情况）
            drawable = getDrawable();
        }
        return drawable;
    }
}