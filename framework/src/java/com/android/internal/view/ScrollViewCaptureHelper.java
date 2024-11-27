package com.android.internal.view;

/* loaded from: classes5.dex */
public class ScrollViewCaptureHelper implements com.android.internal.view.ScrollCaptureViewHelper<android.view.ViewGroup> {
    private int mOverScrollMode;
    private boolean mScrollBarEnabled;
    private int mStartScrollY;

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(android.view.ViewGroup viewGroup, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer consumer) {
        onScrollRequested2(viewGroup, rect, rect2, cancellationSignal, (java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(android.view.ViewGroup viewGroup) {
        return viewGroup.isVisibleToUser() && (viewGroup.canScrollVertically(-1) || viewGroup.canScrollVertically(1));
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
        this.mStartScrollY = viewGroup.getScrollY();
        this.mOverScrollMode = viewGroup.getOverScrollMode();
        if (this.mOverScrollMode != 2) {
            viewGroup.setOverScrollMode(2);
        }
        this.mScrollBarEnabled = viewGroup.isVerticalScrollBarEnabled();
        if (this.mScrollBarEnabled) {
            viewGroup.setVerticalScrollBarEnabled(false);
        }
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(android.view.ViewGroup viewGroup, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult> consumer) {
        int scrollY = viewGroup.getScrollY() - this.mStartScrollY;
        com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult = new com.android.internal.view.ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new android.graphics.Rect(rect2);
        scrollResult.scrollDelta = scrollY;
        scrollResult.availableArea = new android.graphics.Rect();
        android.view.View childAt = viewGroup.getChildAt(0);
        if (childAt == null) {
            consumer.accept(scrollResult);
            return;
        }
        android.graphics.Rect rect3 = new android.graphics.Rect(rect2);
        rect3.offset(0, -scrollY);
        rect3.offset(rect.left, rect.top);
        android.graphics.Rect rect4 = new android.graphics.Rect(rect3);
        rect4.offset(viewGroup.getScrollX() - childAt.getLeft(), viewGroup.getScrollY() - childAt.getTop());
        android.graphics.Rect rect5 = new android.graphics.Rect(rect4);
        int height = ((viewGroup.getHeight() - viewGroup.getPaddingTop()) - viewGroup.getPaddingBottom()) - rect5.height();
        if (height > 0) {
            rect5.inset(0, (-height) / 2);
        }
        childAt.requestRectangleOnScreen(rect5, true);
        int scrollY2 = viewGroup.getScrollY() - this.mStartScrollY;
        scrollResult.scrollDelta = scrollY2;
        android.graphics.Point point = new android.graphics.Point();
        android.graphics.Rect rect6 = new android.graphics.Rect(rect4);
        if (!viewGroup.getChildVisibleRect(childAt, rect6, point)) {
            rect6.setEmpty();
            scrollResult.availableArea = rect6;
            consumer.accept(scrollResult);
        } else {
            rect6.offset(-point.x, -point.y);
            rect6.offset(childAt.getLeft() - viewGroup.getScrollX(), childAt.getTop() - viewGroup.getScrollY());
            rect6.offset(-rect.left, -rect.top);
            rect6.offset(0, scrollY2);
            scrollResult.availableArea = new android.graphics.Rect(rect6);
            consumer.accept(scrollResult);
        }
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(android.view.ViewGroup viewGroup) {
        viewGroup.scrollTo(0, this.mStartScrollY);
        if (this.mOverScrollMode != 2) {
            viewGroup.setOverScrollMode(this.mOverScrollMode);
        }
        if (this.mScrollBarEnabled) {
            viewGroup.setVerticalScrollBarEnabled(true);
        }
    }
}
