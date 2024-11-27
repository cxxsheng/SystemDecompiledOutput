package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationExpandButton extends android.widget.FrameLayout {
    private int mDefaultPillColor;
    private int mDefaultTextColor;
    private boolean mExpanded;
    private int mHighlightPillColor;
    private int mHighlightTextColor;
    private android.widget.ImageView mIconView;
    private int mNumber;
    private android.widget.TextView mNumberView;
    private android.graphics.drawable.Drawable mPillDrawable;

    public NotificationExpandButton(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public NotificationExpandButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationExpandButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationExpandButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPillDrawable = ((android.graphics.drawable.LayerDrawable) findViewById(com.android.internal.R.id.expand_button_pill).getBackground()).findDrawableByLayerId(com.android.internal.R.id.expand_button_pill_colorized_layer);
        this.mNumberView = (android.widget.TextView) findViewById(com.android.internal.R.id.expand_button_number);
        this.mIconView = (android.widget.ImageView) findViewById(com.android.internal.R.id.expand_button_icon);
    }

    @Override // android.view.View
    public void getBoundsOnScreen(android.graphics.Rect rect, boolean z) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) getParent();
        if (viewGroup != null && viewGroup.getId() == 16909007) {
            viewGroup.getBoundsOnScreen(rect, z);
        } else {
            super.getBoundsOnScreen(rect, z);
        }
    }

    @Override // android.view.View
    public boolean pointInView(float f, float f2, float f3) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) getParent();
        if (viewGroup != null && viewGroup.getId() == 16909007) {
            return true;
        }
        return super.pointInView(f, f2, f3);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(android.widget.Button.class.getName());
    }

    @android.view.RemotableViewMethod
    public void setExpanded(boolean z) {
        this.mExpanded = z;
        updateExpandedState();
    }

    private void updateExpandedState() {
        int i;
        int i2;
        if (this.mExpanded) {
            i = com.android.internal.R.drawable.ic_collapse_notification;
            i2 = com.android.internal.R.string.expand_button_content_description_expanded;
        } else {
            i = com.android.internal.R.drawable.ic_expand_notification;
            i2 = com.android.internal.R.string.expand_button_content_description_collapsed;
        }
        setContentDescription(this.mContext.getText(i2));
        this.mIconView.lambda$setImageURIAsync$2(getContext().getDrawable(i));
        updateNumber();
    }

    private void updateNumber() {
        java.lang.String format;
        if (shouldShowNumber()) {
            if (this.mNumber >= 100) {
                format = getResources().getString(com.android.internal.R.string.unread_convo_overflow, 99);
            } else {
                format = java.lang.String.format(java.util.Locale.getDefault(), "%d", java.lang.Integer.valueOf(this.mNumber));
            }
            this.mNumberView.lambda$setTextAsync$0(format);
            this.mNumberView.setVisibility(0);
        } else {
            this.mNumberView.setVisibility(8);
        }
        updateColors();
    }

    private void updateColors() {
        if (shouldShowNumber()) {
            if (this.mHighlightPillColor != 0) {
                this.mPillDrawable.setTintList(android.content.res.ColorStateList.valueOf(this.mHighlightPillColor));
            }
            this.mIconView.setColorFilter(this.mHighlightTextColor);
            if (this.mHighlightTextColor != 0) {
                this.mNumberView.setTextColor(this.mHighlightTextColor);
                return;
            }
            return;
        }
        if (this.mDefaultPillColor != 0) {
            this.mPillDrawable.setTintList(android.content.res.ColorStateList.valueOf(this.mDefaultPillColor));
        }
        this.mIconView.setColorFilter(this.mDefaultTextColor);
        if (this.mDefaultTextColor != 0) {
            this.mNumberView.setTextColor(this.mDefaultTextColor);
        }
    }

    private boolean shouldShowNumber() {
        return !this.mExpanded && this.mNumber > 1;
    }

    @android.view.RemotableViewMethod
    public void setDefaultTextColor(int i) {
        this.mDefaultTextColor = i;
        updateColors();
    }

    @android.view.RemotableViewMethod
    public void setDefaultPillColor(int i) {
        this.mDefaultPillColor = i;
        updateColors();
    }

    @android.view.RemotableViewMethod
    public void setHighlightTextColor(int i) {
        this.mHighlightTextColor = i;
        updateColors();
    }

    @android.view.RemotableViewMethod
    public void setHighlightPillColor(int i) {
        this.mHighlightPillColor = i;
        updateColors();
    }

    @android.view.RemotableViewMethod
    public void setNumber(int i) {
        if (this.mNumber != i) {
            this.mNumber = i;
            updateNumber();
        }
    }
}
