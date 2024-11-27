package com.android.internal.widget;

/* loaded from: classes5.dex */
public class TextViewInputDisabler {
    private android.text.InputFilter[] mDefaultFilters;
    private android.text.InputFilter[] mNoInputFilters = {new android.text.InputFilter() { // from class: com.android.internal.widget.TextViewInputDisabler.1
        @Override // android.text.InputFilter
        public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            return "";
        }
    }};
    private android.widget.TextView mTextView;

    public TextViewInputDisabler(android.widget.TextView textView) {
        this.mTextView = textView;
        this.mDefaultFilters = this.mTextView.getFilters();
    }

    public void setInputEnabled(boolean z) {
        this.mTextView.setFilters(z ? this.mDefaultFilters : this.mNoInputFilters);
    }
}
