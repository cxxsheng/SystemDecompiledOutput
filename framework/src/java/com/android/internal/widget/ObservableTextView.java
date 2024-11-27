package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ObservableTextView extends android.widget.TextView {
    private java.util.function.Consumer<java.lang.Integer> mOnVisibilityChangedListener;

    public ObservableTextView(android.content.Context context) {
        super(context);
    }

    public ObservableTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ObservableTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && this.mOnVisibilityChangedListener != null) {
            this.mOnVisibilityChangedListener.accept(java.lang.Integer.valueOf(i));
        }
    }

    public void setOnVisibilityChangedListener(java.util.function.Consumer<java.lang.Integer> consumer) {
        this.mOnVisibilityChangedListener = consumer;
    }
}
