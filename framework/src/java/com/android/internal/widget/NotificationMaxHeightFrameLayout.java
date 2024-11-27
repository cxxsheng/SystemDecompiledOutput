package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationMaxHeightFrameLayout extends android.widget.FrameLayout {
    private final int mNotificationMaxHeight;

    public NotificationMaxHeightFrameLayout(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public NotificationMaxHeightFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public NotificationMaxHeightFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationMaxHeightFrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNotificationMaxHeight = getFontScaledHeight(this.mContext, com.android.internal.R.dimen.notification_min_height);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (android.view.View.MeasureSpec.getSize(i2) > this.mNotificationMaxHeight) {
            i2 = android.view.View.MeasureSpec.makeMeasureSpec(this.mNotificationMaxHeight, android.view.View.MeasureSpec.getMode(i2));
        }
        super.onMeasure(i, i2);
    }

    private static int getFontScaledHeight(android.content.Context context, int i) {
        return (int) (context.getResources().getDimensionPixelSize(i) * java.lang.Math.max(1.0f, context.getResources().getDisplayMetrics().scaledDensity / context.getResources().getDisplayMetrics().density));
    }
}
