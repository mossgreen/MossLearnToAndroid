package com.example.avjindersinghsekhon.minimaltodo;


import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/*
@moss
TextInputLayout是一个能够把EditText包裹在当中的一个布局，当输入文字时，它可以把Hint文字飘到EditText的上方。
TextInputLayout is a layout which wraps an EditText to show a floating label
when the hint is hidden due to the user inputting text.
Also supports showing an error via setErrorEnabled(boolean) and [setError(CharSequence)]

实现步骤
0x01. 添加依赖
0x02. UI代码 使用TextInputLayout包裹住EditText
0x03. 添加逻辑判断 在EditText中添加输入监听代码，注意在onTextChanged中调用才有实时效果

 */
public class CustomTextInputLayout extends TextInputLayout {

    private boolean mIsHintSet;
    private CharSequence mHint;

    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            // Since hint will be nullify on EditText once on parent addView, store hint value locally
            mHint = ((EditText)child).getHint();
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsHintSet && ViewCompat.isLaidOut(this)) {
            // We have to reset the previous hint so that equals check pass
            setHint(null);

            // In case that hint is changed programatically
            CharSequence currentEditTextHint =  getEditText().getHint();

            if (currentEditTextHint != null && currentEditTextHint.length() > 0) {
                mHint = currentEditTextHint;
            }
            setHint(mHint);
            mIsHintSet = true;
        }
    }

}

