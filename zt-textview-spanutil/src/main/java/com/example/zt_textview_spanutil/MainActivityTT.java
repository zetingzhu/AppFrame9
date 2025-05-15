package com.example.zt_textview_spanutil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityTT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();

        initView2();
        initView3();

        LinearLayout ll_anim = findViewById(R.id.ll_anim);
        startRockingAnimation(ll_anim);


        LinearLayout ll_anim_start = findViewById(R.id.ll_anim_start);
        LinearLayout ll_anim_start_1 = findViewById(R.id.ll_anim_start_1);
        LinearLayout ll_anim_end = findViewById(R.id.ll_anim_end);
        LinearLayout ll_anim_bottom = findViewById(R.id.ll_anim_bottom);
        Button btn_start_stop_anim = findViewById(R.id.btn_start_stop_anim);
        btn_start_stop_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_anim_start.getVisibility() == View.VISIBLE) {
                    slideOutToLeft(ll_anim_start, true);
                    slideOutToRight(ll_anim_end);
                    slideOutToLeft(ll_anim_start_1, false);
                    slideOutToBottom(ll_anim_bottom);

                } else {
                    slideInFromLeft(ll_anim_start);
                    slideInFromRight(ll_anim_end);
                    slideInFromLeft(ll_anim_start_1);
                    slideInFromBottom(ll_anim_bottom);

                }
            }
        });
    }

    public static void slideInFromLeft(View view) {
        // 先将视图移动到左边屏幕外
        view.setTranslationX(-view.getWidth());
        view.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public static void slideOutToLeft(View view, boolean canGone) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
                "translationX", -view.getWidth());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (canGone) {
                    view.setVisibility(View.GONE); // 动画结束后隐藏视图
                }
            }
        });
        animator.start();
    }

    public static void slideInFromRight(View view) {
        // 先将视图移动到右边屏幕外
        view.setTranslationX(view.getWidth());
        view.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator()); // 减速效果
        animator.start();
    }

    public static void slideOutToRight(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
                "translationX", view.getWidth());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator()); // 加速效果
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                view.setTranslationX(0); // 重置位置
            }
        });
        animator.start();
    }


    ObjectAnimator animator;

    public void startRockingAnimation(View view) {
        // 设置视图的旋转中心为自身中心（默认就是中心，无需特别设置）

        // 创建旋转动画
        animator = ObjectAnimator.ofFloat(view, "rotation", -15f, 15f);

        // 设置动画属性
        animator.setDuration(200); // 单次动画时长1秒
        animator.setRepeatCount(ObjectAnimator.INFINITE); // 无限重复
        animator.setRepeatMode(ObjectAnimator.REVERSE); // 往复模式
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // 加减速插值器
        // 开始动画
        animator.start();
    }

    public static void slideInFromBottom(View view) {
        // 先将视图移动到屏幕下方外
        view.setTranslationY(view.getHeight());
        view.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator()); // 减速效果
        animator.start();
    }

    public static void slideOutToBottom(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
                "translationY", view.getHeight());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator()); // 加速效果
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                view.setTranslationY(0); // 重置位置
            }
        });
        animator.start();
    }

    private void pauseAnimation() {
        if (animator != null && animator.isRunning()) {
            animator.pause();
        }
    }

    private void resumeAnimation() {
        if (animator != null) {
            if (animator.isPaused()) {
                animator.resume();
            } else if (!animator.isRunning()) {
                // 如果既没运行也没暂停(比如首次启动)
                animator.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator.cancel();
        }
    }

    private void initView() {
        TextView textView = findViewById(R.id.long_text_view);
        String originalText = textView.getText().toString();

        if (originalText.length() >= 2) {
            Paint textPaint = textView.getPaint();
            Paint.FontMetrics fm = textPaint.getFontMetrics();  // 获取字体度量信息

            // 计算文本实际占用的高度（包含上下留白）
            float textHeight = fm.descent - fm.ascent;
            // 前两个字符的总宽度
            float textWidth = textPaint.measureText(originalText, 0, 2);

            int padding = dp2px(2);  // 背景与文本的边距（上下左右）
            int radius = dp2px(4);   // 4dp圆角

            // 创建圆角背景Drawable
            GradientDrawable bgDrawable = new GradientDrawable();
            bgDrawable.setColor(Color.parseColor("#FFF3B0"));
            bgDrawable.setCornerRadius(radius);
            // 设置Drawable尺寸（宽度=字符宽度+2*padding，高度=文本高度+2*padding）
            bgDrawable.setBounds(0, 0,
                    (int) (textWidth + padding * 2),
                    (int) (textHeight + padding * 2));

            // 自定义ImageSpan修正绘制位置
            ImageSpan imageSpan = new ImageSpan(bgDrawable) {
                @Override
                public void draw(Canvas canvas, CharSequence text, int start, int end,
                                 float x, int top, int y, int bottom, Paint paint) {
                    Drawable drawable = getDrawable();

                    // 计算背景垂直位置：基线(y)向上到ascent，向下到descent，加上padding
                    // 最终背景顶部位置 = y（基线） + fm.ascent（负数） - padding
                    int drawableTop = (int) (y + fm.ascent - padding);

                    // 调整背景位置（水平居中于字符，x为当前字符起始位置）
                    canvas.save();
                    // 平移到背景左上角位置（x - padding：字符左侧padding）
                    canvas.translate(x - padding, drawableTop);
                    drawable.draw(canvas);
                    canvas.restore();

                    // 绘制原始文本（保持原有颜色和位置）
                    paint.setColor(textView.getCurrentTextColor());
                    canvas.drawText(text, start, end, x, y, paint);
                }
            };

            // 应用Span到前两个字符
            SpannableString spannableString = new SpannableString(originalText);
            spannableString.setSpan(imageSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        }
    }

    // dp转px工具方法
    private int dp2px(float dpValue) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }


    private void initView2() {

        TextView textView = findViewById(R.id.textView);

        // 创建包含图片和文本的 SpannableString
        SpannableString spannableString = new SpannableString(
                "这是一张居中对齐的图片: [IMAGE] 这是后续文本");

        // 获取图片资源
        Drawable drawable = getResources().getDrawable(R.drawable.bg1);

        // 创建自定义 ImageSpan（使用居中对齐）
        CustomImageSpan imageSpan = new CustomImageSpan(
                this, drawable, CustomImageSpan.ALIGN_CENTER);

        // 设置图片尺寸（按比例缩放）
        imageSpan.setDrawableSize(100, 200); // 目标宽度100px，最大高度200px

        // 设置文本大小（用于垂直对齐计算）
        imageSpan.setTextSize((int) textView.getTextSize());

        // 将 ImageSpan 应用到特定位置的文本
        spannableString.setSpan(imageSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置到 TextView
        textView.setText(spannableString);
    }

    public void initView3() {
        TextView textView = findViewById(R.id.textView3);
        String fullText = "这是一个长文本示例，我们将为前两个字设置圆角背景。";

// 创建SpannableString
        SpannableString spannableString = new SpannableString(fullText);

// 获取前两个字符（考虑中文是单个字符）
        if (fullText.length() >= 2) {
            // 创建圆角背景Span
            RoundBackgroundSpan roundBackgroundSpan = new RoundBackgroundSpan(
                    Color.parseColor("#FF5722"), // 背景颜色
                    Color.WHITE,                 // 文字颜色
                    dpToPx(4),                  // 圆角半径
                    dpToPx(4)                   // 左右padding
            );

            // 应用到前两个字符
            spannableString.setSpan(
                    roundBackgroundSpan,
                    0, 2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        textView.setText(spannableString);


    }

    // dp转px工具方法
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}