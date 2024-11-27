package com.android.internal.widget;

/* loaded from: classes5.dex */
public class RecyclerViewAccessibilityDelegate extends android.view.View.AccessibilityDelegate {
    final android.view.View.AccessibilityDelegate mItemDelegate = new android.view.View.AccessibilityDelegate() { // from class: com.android.internal.widget.RecyclerViewAccessibilityDelegate.1
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            if (!com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.shouldIgnore() && com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null) {
                com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfo);
            }
        }

        @Override // android.view.View.AccessibilityDelegate
        public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (!com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.shouldIgnore() && com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager() != null) {
                return com.android.internal.widget.RecyclerViewAccessibilityDelegate.this.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(view, i, bundle);
            }
            return false;
        }
    };
    final com.android.internal.widget.RecyclerView mRecyclerView;

    public RecyclerViewAccessibilityDelegate(com.android.internal.widget.RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    @Override // android.view.View.AccessibilityDelegate
    public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            return this.mRecyclerView.getLayoutManager().performAccessibilityAction(i, bundle);
        }
        return false;
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(com.android.internal.widget.RecyclerView.class.getName());
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        }
    }

    @Override // android.view.View.AccessibilityDelegate
    public void onInitializeAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(com.android.internal.widget.RecyclerView.class.getName());
        if ((view instanceof com.android.internal.widget.RecyclerView) && !shouldIgnore()) {
            com.android.internal.widget.RecyclerView recyclerView = (com.android.internal.widget.RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onInitializeAccessibilityEvent(accessibilityEvent);
            }
        }
    }

    public android.view.View.AccessibilityDelegate getItemDelegate() {
        return this.mItemDelegate;
    }
}
