package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationVanishingFrameLayout extends android.widget.FrameLayout {
    public NotificationVanishingFrameLayout(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public NotificationVanishingFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationVanishingFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationVanishingFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (allChildrenGone()) {
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(0, 1073741824);
            super.onMeasure(makeMeasureSpec, makeMeasureSpec);
        } else {
            super.onMeasure(i, i2);
        }
    }

    private boolean allChildrenGone() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt != null && childAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }
}
