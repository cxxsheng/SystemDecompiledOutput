package com.android.internal.widget;

/* loaded from: classes5.dex */
public class NumericTextView extends android.widget.TextView {
    private static final double LOG_RADIX = java.lang.Math.log(10.0d);
    private static final int RADIX = 10;
    private int mCount;
    private com.android.internal.widget.NumericTextView.OnValueChangedListener mListener;
    private int mMaxCount;
    private int mMaxValue;
    private int mMinValue;
    private int mPreviousValue;
    private boolean mShowLeadingZeroes;
    private int mValue;

    public interface OnValueChangedListener {
        void onValueChanged(com.android.internal.widget.NumericTextView numericTextView, int i, boolean z, boolean z2);
    }

    public NumericTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinValue = 0;
        this.mMaxValue = 99;
        this.mMaxCount = 2;
        this.mShowLeadingZeroes = true;
        setHintTextColor(getTextColors().getColorForState(android.util.StateSet.get(0), 0));
        setFocusable(true);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            this.mPreviousValue = this.mValue;
            this.mValue = 0;
            this.mCount = 0;
            setHint(getText());
            lambda$setTextAsync$0("");
            return;
        }
        if (this.mCount == 0) {
            this.mValue = this.mPreviousValue;
            lambda$setTextAsync$0(getHint());
            setHint("");
        }
        if (this.mValue < this.mMinValue) {
            this.mValue = this.mMinValue;
        }
        setValue(this.mValue);
        if (this.mListener != null) {
            this.mListener.onValueChanged(this, this.mValue, true, true);
        }
    }

    public final void setValue(int i) {
        if (this.mValue != i) {
            this.mValue = i;
            updateDisplayedValue();
        }
    }

    public final int getValue() {
        return this.mValue;
    }

    public final void setRange(int i, int i2) {
        if (this.mMinValue != i) {
            this.mMinValue = i;
        }
        if (this.mMaxValue != i2) {
            this.mMaxValue = i2;
            this.mMaxCount = ((int) (java.lang.Math.log(i2) / LOG_RADIX)) + 1;
            updateMinimumWidth();
            updateDisplayedValue();
        }
    }

    public final int getRangeMinimum() {
        return this.mMinValue;
    }

    public final int getRangeMaximum() {
        return this.mMaxValue;
    }

    public final void setShowLeadingZeroes(boolean z) {
        if (this.mShowLeadingZeroes != z) {
            this.mShowLeadingZeroes = z;
            updateDisplayedValue();
        }
    }

    public final boolean getShowLeadingZeroes() {
        return this.mShowLeadingZeroes;
    }

    private void updateDisplayedValue() {
        java.lang.String str;
        if (this.mShowLeadingZeroes) {
            str = "%0" + this.mMaxCount + android.app.blob.XmlTags.ATTR_DESCRIPTION;
        } else {
            str = "%d";
        }
        lambda$setTextAsync$0(java.lang.String.format(str, java.lang.Integer.valueOf(this.mValue)));
    }

    private void updateMinimumWidth() {
        java.lang.CharSequence text = getText();
        int i = 0;
        for (int i2 = 0; i2 < this.mMaxValue; i2++) {
            lambda$setTextAsync$0(java.lang.String.format("%0" + this.mMaxCount + android.app.blob.XmlTags.ATTR_DESCRIPTION, java.lang.Integer.valueOf(i2)));
            measure(0, 0);
            int measuredWidth = getMeasuredWidth();
            if (measuredWidth > i) {
                i = measuredWidth;
            }
        }
        lambda$setTextAsync$0(text);
        setMinWidth(i);
        setMinimumWidth(i);
    }

    public final void setOnDigitEnteredListener(com.android.internal.widget.NumericTextView.OnValueChangedListener onValueChangedListener) {
        this.mListener = onValueChangedListener;
    }

    public final com.android.internal.widget.NumericTextView.OnValueChangedListener getOnDigitEnteredListener() {
        return this.mListener;
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return isKeyCodeNumeric(i) || i == 67 || super.onKeyDown(i, keyEvent);
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return isKeyCodeNumeric(i) || i == 67 || super.onKeyMultiple(i, i2, keyEvent);
    }

    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return handleKeyUp(i) || super.onKeyUp(i, keyEvent);
    }

    private boolean handleKeyUp(int i) {
        java.lang.String str;
        if (i == 67) {
            if (this.mCount > 0) {
                this.mValue /= 10;
                this.mCount--;
            }
        } else {
            if (!isKeyCodeNumeric(i)) {
                return false;
            }
            if (this.mCount < this.mMaxCount) {
                int numericKeyCodeToInt = (this.mValue * 10) + numericKeyCodeToInt(i);
                if (numericKeyCodeToInt <= this.mMaxValue) {
                    this.mValue = numericKeyCodeToInt;
                    this.mCount++;
                }
            }
        }
        if (this.mCount > 0) {
            str = java.lang.String.format("%0" + this.mCount + android.app.blob.XmlTags.ATTR_DESCRIPTION, java.lang.Integer.valueOf(this.mValue));
        } else {
            str = "";
        }
        lambda$setTextAsync$0(str);
        if (this.mListener != null) {
            this.mListener.onValueChanged(this, this.mValue, this.mValue >= this.mMinValue, this.mCount >= this.mMaxCount || this.mValue * 10 > this.mMaxValue);
        }
        return true;
    }

    private static boolean isKeyCodeNumeric(int i) {
        return i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 16;
    }

    private static int numericKeyCodeToInt(int i) {
        return i - 7;
    }
}
