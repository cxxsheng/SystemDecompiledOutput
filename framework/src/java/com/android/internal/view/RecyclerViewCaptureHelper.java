package com.android.internal.view;

/* loaded from: classes5.dex */
public class RecyclerViewCaptureHelper implements com.android.internal.view.ScrollCaptureViewHelper<android.view.ViewGroup> {
    private static final java.lang.String TAG = "RVCaptureHelper";
    private int mOverScrollMode;
    private boolean mScrollBarWasEnabled;
    private int mScrollDelta;

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
        this.mScrollDelta = 0;
        this.mOverScrollMode = viewGroup.getOverScrollMode();
        viewGroup.setOverScrollMode(2);
        this.mScrollBarWasEnabled = viewGroup.isVerticalScrollBarEnabled();
        viewGroup.setVerticalScrollBarEnabled(false);
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(android.view.ViewGroup viewGroup, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult> consumer) {
        com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult = new com.android.internal.view.ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new android.graphics.Rect(rect2);
        scrollResult.scrollDelta = this.mScrollDelta;
        scrollResult.availableArea = new android.graphics.Rect();
        if (!viewGroup.isVisibleToUser() || viewGroup.getChildCount() == 0) {
            android.util.Log.w(TAG, "recyclerView is empty or not visible, cannot continue");
            consumer.accept(scrollResult);
            return;
        }
        android.graphics.Rect rect3 = new android.graphics.Rect(rect2);
        rect3.offset(0, -this.mScrollDelta);
        rect3.offset(rect.left, rect.top);
        android.view.View findChildNearestTarget = findChildNearestTarget(viewGroup, rect3);
        if (findChildNearestTarget == null) {
            android.util.Log.w(TAG, "Failed to locate anchor view");
            consumer.accept(scrollResult);
            return;
        }
        android.graphics.Rect rect4 = new android.graphics.Rect(rect3);
        viewGroup.offsetRectIntoDescendantCoords(findChildNearestTarget, rect4);
        int top = findChildNearestTarget.getTop();
        android.graphics.Rect rect5 = new android.graphics.Rect(rect4);
        int height = ((viewGroup.getHeight() - viewGroup.getPaddingTop()) - viewGroup.getPaddingBottom()) - rect5.height();
        if (height > 0) {
            rect5.inset(0, (-height) / 2);
        }
        if (viewGroup.requestChildRectangleOnScreen(findChildNearestTarget, rect5, true)) {
            if (findChildNearestTarget.getParent() == null) {
                android.util.Log.w(TAG, "Bug: anchor view " + findChildNearestTarget + " is detached after scrolling");
                consumer.accept(scrollResult);
                return;
            } else {
                this.mScrollDelta += top - findChildNearestTarget.getTop();
                scrollResult.scrollDelta = this.mScrollDelta;
            }
        }
        rect3.set(rect4);
        viewGroup.offsetDescendantRectToMyCoords(findChildNearestTarget, rect3);
        android.graphics.Rect rect6 = new android.graphics.Rect(rect);
        viewGroup.getLocalVisibleRect(rect6);
        if (!rect3.intersect(rect6)) {
            consumer.accept(scrollResult);
            return;
        }
        android.graphics.Rect rect7 = new android.graphics.Rect(rect3);
        rect7.offset(-rect.left, -rect.top);
        rect7.offset(0, this.mScrollDelta);
        scrollResult.availableArea = rect7;
        consumer.accept(scrollResult);
    }

    static android.view.View findChildNearestTarget(android.view.ViewGroup viewGroup, android.graphics.Rect rect) {
        int height = (int) (rect.height() * 0.25f);
        viewGroup.getLocalVisibleRect(new android.graphics.Rect());
        android.graphics.Rect rect2 = new android.graphics.Rect();
        android.view.View view = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            android.view.View childAt = viewGroup.getChildAt(i2);
            childAt.getHitRect(rect2);
            if (childAt.getVisibility() == 0) {
                int abs = java.lang.Math.abs(rect.centerY() - rect2.centerY());
                if (abs < i) {
                    view = childAt;
                    i = abs;
                } else if (rect2.intersect(rect) && rect2.height() > height) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(android.view.ViewGroup viewGroup) {
        viewGroup.scrollBy(0, -this.mScrollDelta);
        viewGroup.setOverScrollMode(this.mOverScrollMode);
        viewGroup.setVerticalScrollBarEnabled(this.mScrollBarWasEnabled);
    }
}
