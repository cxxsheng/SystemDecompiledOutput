package com.android.internal.view;

/* loaded from: classes5.dex */
public interface ScrollCaptureViewHelper<V extends android.view.View> {
    public static final int DOWN = 1;
    public static final int UP = -1;

    boolean onAcceptSession(V v);

    void onPrepareForEnd(V v);

    void onPrepareForStart(V v, android.graphics.Rect rect);

    void onScrollRequested(V v, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult> consumer);

    public static class ScrollResult {
        public android.graphics.Rect availableArea;
        public android.graphics.Rect requestedArea;
        public int scrollDelta;

        public java.lang.String toString() {
            return "ScrollResult{requestedArea=" + this.requestedArea + ", availableArea=" + this.availableArea + ", scrollDelta=" + this.scrollDelta + '}';
        }
    }

    default android.graphics.Rect onComputeScrollBounds(V v) {
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, v.getWidth(), v.getHeight());
        if ((v instanceof android.view.ViewGroup) && ((android.view.ViewGroup) v).getClipToPadding()) {
            rect.inset(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
        }
        return rect;
    }
}
