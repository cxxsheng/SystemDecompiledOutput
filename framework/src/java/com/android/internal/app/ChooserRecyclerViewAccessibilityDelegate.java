package com.android.internal.app;

/* loaded from: classes4.dex */
class ChooserRecyclerViewAccessibilityDelegate extends com.android.internal.widget.RecyclerViewAccessibilityDelegate {
    private final int[] mConsumed;
    private final android.graphics.Rect mTempRect;

    ChooserRecyclerViewAccessibilityDelegate(com.android.internal.widget.RecyclerView recyclerView) {
        super(recyclerView);
        this.mTempRect = new android.graphics.Rect();
        this.mConsumed = new int[2];
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean onRequestSendAccessibilityEvent(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        boolean onRequestSendAccessibilityEvent = super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
        if (onRequestSendAccessibilityEvent && accessibilityEvent.getEventType() == 32768) {
            ensureViewOnScreenVisibility((com.android.internal.widget.RecyclerView) viewGroup, view);
        }
        return onRequestSendAccessibilityEvent;
    }

    private void ensureViewOnScreenVisibility(com.android.internal.widget.RecyclerView recyclerView, android.view.View view) {
        int i;
        android.view.View findContainingItemView = recyclerView.findContainingItemView(view);
        if (findContainingItemView == null) {
            return;
        }
        recyclerView.getBoundsOnScreen(this.mTempRect, true);
        int i2 = this.mTempRect.top;
        int i3 = this.mTempRect.bottom;
        findContainingItemView.getBoundsOnScreen(this.mTempRect);
        if (this.mTempRect.top < i2) {
            i = this.mTempRect.bottom - i3;
        } else if (this.mTempRect.bottom <= i3) {
            i = 0;
        } else {
            i = this.mTempRect.top - i2;
        }
        nestedVerticalScrollBy(recyclerView, i);
    }

    private void nestedVerticalScrollBy(com.android.internal.widget.RecyclerView recyclerView, int i) {
        if (i == 0) {
            return;
        }
        recyclerView.startNestedScroll(2);
        if (recyclerView.dispatchNestedPreScroll(0, i, this.mConsumed, null)) {
            i -= this.mConsumed[1];
        }
        recyclerView.scrollBy(0, i);
        recyclerView.stopNestedScroll();
    }
}
