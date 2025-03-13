package com.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.zt_inputtext.R;


public class MTextInputLayoutV2 extends LinearLayout {

    private EditText editText; // 输入框
    private ImageView clearImageView; // 清除按钮对应的 ImageView
    private ImageView eyeImageView; // 眼睛图标对应的 ImageView，用于切换密码可见性
    private TextView errorTextView;// 显示错误信息的 TextView
    private LinearLayout passwordLayout;
    private Drawable clearDrawable;  // 清除按钮的图标
    private Drawable eyeDrawableSelector;   // 眼睛图标的 selector 资源
    private Drawable editTextBGDrawable; // 输入框的背景图
    private int editTextTextColor; // 输入框内文字颜色
    private boolean isPasswordVisible = false; // 密码是否可见的标志位
    private boolean passwordToggleEnabled = true;// 密码可见切换按钮是否可用的标志位
    private boolean clearEnabled = true;// 清除按钮是否可用的标志位
    private OnPasswordChangeListener onPasswordChangeListener;// 密码变化监听器


    public MTextInputLayoutV2(Context context) {
        super(context);
        init(context, null);
    }

    public MTextInputLayoutV2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MTextInputLayoutV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.custom_password_edittext, this);

        editText = findViewById(R.id.passwordEditText);
        clearImageView = findViewById(R.id.clearImageView);
        eyeImageView = findViewById(R.id.eyeImageView);
        errorTextView = findViewById(R.id.errorTextView);
        passwordLayout = findViewById(R.id.passwordLayout);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MTextInputLayoutV2);
        try {
            // 获取密码可见切换按钮是否可用的属性，默认为可用
            passwordToggleEnabled = a.getBoolean(R.styleable.MTextInputLayoutV2_mPasswordToggleEnabled, true);
            // 获取清除按钮是否可用的属性，默认为可用
            clearEnabled = a.getBoolean(R.styleable.MTextInputLayoutV2_mClearEnabled, true);
            // 获取清除按钮的图标属性
            clearDrawable = a.getDrawable(R.styleable.MTextInputLayoutV2_mClearDrawable);
            // 获取眼睛图标的 selector 资源属性
            eyeDrawableSelector = a.getDrawable(R.styleable.MTextInputLayoutV2_mPasswordToggleDrawable);
            // 获取错误信息的文本颜色属性，默认为系统自带的红色
            int errorTextColor = a.getColor(R.styleable.MTextInputLayoutV2_mErrorTextColor, getResources().getColor(android.R.color.holo_red_light));
            // 获取错误信息的文本大小属性，默认为默认的错误文本大小
            float errorTextSize = a.getDimension(R.styleable.MTextInputLayoutV2_mErrorTextSize, getResources().getDimension(R.dimen.default_error_text_size));
            // 获取输入框的背景图属性
            editTextBGDrawable = a.getDrawable(R.styleable.MTextInputLayoutV2_mEditTextBGDrawable);
            // 获取输入框内文字颜色属性，默认为系统默认文字颜色
            editTextTextColor = a.getColor(R.styleable.MTextInputLayoutV2_mEditTextTextColor, getResources().getColor(android.R.color.primary_text_light));

            // 如果没有设置清除按钮的图标，则使用系统自带的图标
            if (clearDrawable == null) {
                clearDrawable = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
            }
            // 如果没有设置眼睛图标的 selector 资源，则给出提示
            if (eyeDrawableSelector == null) {
                throw new IllegalArgumentException("Please provide a valid drawable selector for mPasswordToggleDrawable");
            }

            // 如果设置了输入框的背景图，则为输入框设置背景
            if (editTextBGDrawable != null) {
                editText.setBackground(null);// 清空输入框背景
                passwordLayout.setBackground(editTextBGDrawable);
            }
            // 设置输入框内文字颜色
            editText.setTextColor(editTextTextColor);

            // 设置错误信息的文本颜色
            errorTextView.setTextColor(errorTextColor);
            // 设置错误信息的文本大小
            errorTextView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, errorTextSize);

            // 设置眼睛图标的 selector 资源
            eyeImageView.setImageDrawable(eyeDrawableSelector);
        } finally {
            a.recycle();
        }

        // 根据密码是否可见设置眼睛图标的显示
        updateEyeDrawable();
        // 初始时隐藏清除按钮
        clearImageView.setVisibility(GONE);
        // 设置清楚按钮图片
        clearImageView.setImageDrawable(clearDrawable);
        // 根据密码可见切换按钮是否可用设置其显示状态
        eyeImageView.setVisibility(passwordToggleEnabled ? VISIBLE : GONE);
        // 根据清除按钮是否可用设置其显示状态
        clearImageView.setVisibility(clearEnabled ? GONE : INVISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框有文本且清除按钮可用时，显示清除按钮
                if (s.length() > 0 && clearEnabled) {
                    clearImageView.setVisibility(VISIBLE);
                } else {
                    // 否则隐藏清除按钮
                    clearImageView.setVisibility(GONE);
                }
                // 如果设置了密码变化监听器，则调用监听器的方法传递当前密码
                if (onPasswordChangeListener != null) {
                    onPasswordChangeListener.onPasswordChange(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 为清除按钮设置点击监听器，点击时清空输入框内容
        clearImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        // 为眼睛图标设置点击监听器，点击时切换密码的可见性和选中状态
        eyeImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordToggleEnabled) {
                    isPasswordVisible = !isPasswordVisible;
                    if (isPasswordVisible) {
                        // 密码可见时，设置输入框的输入类型为可见文本
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        // 密码不可见时，设置输入框的输入类型为密码
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    // 将光标移动到文本末尾
                    editText.setSelection(editText.getText().length());
                    // 更新眼睛图标的显示
                    updateEyeDrawable();
                }
            }
        });
    }

    // 根据密码是否可见更新眼睛图标的显示
    private void updateEyeDrawable() {
        eyeImageView.setSelected(!isPasswordVisible);
    }

    // 设置错误信息的方法
    public void setErrorText(String error) {
        errorTextView.setText(error);
        errorTextView.setVisibility(VISIBLE);
    }

    // 清除错误信息的方法
    public void clearErrorText() {
        errorTextView.setText("");
        errorTextView.setVisibility(GONE);
    }

    // 获取输入框中密码的方法
    public String getPassword() {
        return editText.getText().toString();
    }

    // 设置密码变化监听器的方法
    public void setOnPasswordChangeListener(OnPasswordChangeListener listener) {
        this.onPasswordChangeListener = listener;
    }

    // 密码变化监听器接口，用于回调密码变化事件
    public interface OnPasswordChangeListener {
        void onPasswordChange(String password);
    }

}