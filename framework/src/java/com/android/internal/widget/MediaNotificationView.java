package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MediaNotificationView extends android.widget.FrameLayout {
    private java.util.ArrayList<com.android.internal.widget.MediaNotificationView.VisibilityChangeListener> mListeners;

    public interface VisibilityChangeListener {
        void onAggregatedVisibilityChanged(boolean z);
    }

    public MediaNotificationView(android.content.Context context) {
        this(context, null);
    }

    public MediaNotificationView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MediaNotificationView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MediaNotificationView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (this.mListeners != null) {
            for (int i = 0; i < this.mListeners.size(); i++) {
                this.mListeners.get(i).onAggregatedVisibilityChanged(z);
            }
        }
    }

    public void addVisibilityListener(com.android.internal.widget.MediaNotificationView.VisibilityChangeListener visibilityChangeListener) {
        if (this.mListeners == null) {
            this.mListeners = new java.util.ArrayList<>();
        }
        if (!this.mListeners.contains(visibilityChangeListener)) {
            this.mListeners.add(visibilityChangeListener);
        }
    }

    public void removeVisibilityListener(com.android.internal.widget.MediaNotificationView.VisibilityChangeListener visibilityChangeListener) {
        if (this.mListeners != null) {
            this.mListeners.remove(visibilityChangeListener);
        }
    }
}
