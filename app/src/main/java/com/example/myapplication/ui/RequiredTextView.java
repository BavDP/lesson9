package com.example.myapplication.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class RequiredTextView extends androidx.appcompat.widget.AppCompatTextView {
    public RequiredTextView(Context context) {
        super(context);
    }

    public RequiredTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RequiredTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        String aster = "*";
        if ((int)text.charAt(text.length() - 1) != (int)aster.charAt(0)) {
            StringBuilder s = new StringBuilder(text.toString()).append(" *");
            super.setText(s.subSequence(0, s.length()), type);
        } else {
            super.setText(text, type);
        }
    }

}
