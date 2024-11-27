package com.android.internal.view;

/* loaded from: classes5.dex */
public class ListViewCaptureHelper implements com.android.internal.view.ScrollCaptureViewHelper<android.widget.ListView> {
    private static final java.lang.String TAG = "LVCaptureHelper";
    private int mOverScrollMode;
    private boolean mScrollBarWasEnabled;
    private int mScrollDelta;

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public /* bridge */ /* synthetic */ void onScrollRequested(android.widget.ListView listView, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer consumer) {
        onScrollRequested2(listView, rect, rect2, cancellationSignal, (java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult>) consumer);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public boolean onAcceptSession(android.widget.ListView listView) {
        return listView.isVisibleToUser() && (listView.canScrollVertically(-1) || listView.canScrollVertically(1));
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForStart(android.widget.ListView listView, android.graphics.Rect rect) {
        this.mScrollDelta = 0;
        this.mOverScrollMode = listView.getOverScrollMode();
        listView.setOverScrollMode(2);
        this.mScrollBarWasEnabled = listView.isVerticalScrollBarEnabled();
        listView.setVerticalScrollBarEnabled(false);
    }

    /* renamed from: onScrollRequested, reason: avoid collision after fix types in other method */
    public void onScrollRequested2(android.widget.ListView listView, android.graphics.Rect rect, android.graphics.Rect rect2, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<com.android.internal.view.ScrollCaptureViewHelper.ScrollResult> consumer) {
        android.util.Log.d(TAG, "-----------------------------------------------------------");
        android.util.Log.d(TAG, "onScrollRequested(scrollBounds=" + rect + ", requestRect=" + rect2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        com.android.internal.view.ScrollCaptureViewHelper.ScrollResult scrollResult = new com.android.internal.view.ScrollCaptureViewHelper.ScrollResult();
        scrollResult.requestedArea = new android.graphics.Rect(rect2);
        scrollResult.scrollDelta = this.mScrollDelta;
        scrollResult.availableArea = new android.graphics.Rect();
        if (!listView.isVisibleToUser() || listView.getChildCount() == 0) {
            android.util.Log.w(TAG, "listView is empty or not visible, cannot continue");
            consumer.accept(scrollResult);
            return;
        }
        android.graphics.Rect transformFromRequestToContainer = com.android.internal.view.ScrollCaptureViewSupport.transformFromRequestToContainer(this.mScrollDelta, rect, rect2);
        android.graphics.Rect rect3 = new android.graphics.Rect();
        listView.getLocalVisibleRect(rect3);
        android.graphics.Rect rect4 = new android.graphics.Rect(transformFromRequestToContainer);
        int height = rect3.height() - transformFromRequestToContainer.height();
        if (height > 0) {
            rect4.inset(0, (-height) / 2);
        }
        int computeScrollAmount = com.android.internal.view.ScrollCaptureViewSupport.computeScrollAmount(rect3, rect4);
        if (computeScrollAmount < 0) {
            android.util.Log.d(TAG, "About to scroll UP (content moves down within parent)");
        } else if (computeScrollAmount > 0) {
            android.util.Log.d(TAG, "About to scroll DOWN (content moves up within parent)");
        }
        android.util.Log.d(TAG, "scrollAmount: " + computeScrollAmount);
        android.view.View findScrollingReferenceView = com.android.internal.view.ScrollCaptureViewSupport.findScrollingReferenceView(listView, computeScrollAmount);
        int top = findScrollingReferenceView.getTop();
        listView.scrollListBy(computeScrollAmount);
        int top2 = top - findScrollingReferenceView.getTop();
        android.util.Log.d(TAG, "Parent view has scrolled vertically by " + top2 + " px");
        this.mScrollDelta += top2;
        scrollResult.scrollDelta = this.mScrollDelta;
        if (top2 != 0) {
            android.util.Log.d(TAG, "Scroll delta is now " + this.mScrollDelta + " px");
        }
        android.graphics.Rect rect5 = new android.graphics.Rect(com.android.internal.view.ScrollCaptureViewSupport.transformFromRequestToContainer(this.mScrollDelta, rect, rect2));
        listView.getLocalVisibleRect(rect3);
        if (rect5.intersect(rect3)) {
            scrollResult.availableArea = com.android.internal.view.ScrollCaptureViewSupport.transformFromContainerToRequest(this.mScrollDelta, rect, rect5);
        }
        android.util.Log.d(TAG, "-----------------------------------------------------------");
        consumer.accept(scrollResult);
    }

    @Override // com.android.internal.view.ScrollCaptureViewHelper
    public void onPrepareForEnd(android.widget.ListView listView) {
        listView.scrollListBy(-this.mScrollDelta);
        listView.setOverScrollMode(this.mOverScrollMode);
        listView.setVerticalScrollBarEnabled(this.mScrollBarWasEnabled);
    }
}
