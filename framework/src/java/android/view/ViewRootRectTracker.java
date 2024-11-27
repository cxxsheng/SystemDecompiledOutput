package android.view;

/* loaded from: classes4.dex */
class ViewRootRectTracker {
    private final java.util.function.Function<android.view.View, java.util.List<android.graphics.Rect>> mRectCollector;
    private boolean mViewsChanged = false;
    private boolean mRootRectsChanged = false;
    private java.util.List<android.graphics.Rect> mRootRects = java.util.Collections.emptyList();
    private java.util.List<android.view.ViewRootRectTracker.ViewInfo> mViewInfos = new java.util.ArrayList();
    private java.util.List<android.graphics.Rect> mRects = java.util.Collections.emptyList();

    ViewRootRectTracker(java.util.function.Function<android.view.View, java.util.List<android.graphics.Rect>> function) {
        this.mRectCollector = function;
    }

    public void updateRectsForView(android.view.View view) {
        boolean z;
        java.util.Iterator<android.view.ViewRootRectTracker.ViewInfo> it = this.mViewInfos.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            android.view.ViewRootRectTracker.ViewInfo next = it.next();
            android.view.View view2 = next.getView();
            if (view2 == null || !view2.isAttachedToWindow() || !view2.isAggregatedVisible()) {
                this.mViewsChanged = true;
                it.remove();
            } else if (view2 == view) {
                next.mDirty = true;
                z = true;
                break;
            }
        }
        if (!z && view.isAttachedToWindow()) {
            this.mViewInfos.add(new android.view.ViewRootRectTracker.ViewInfo(view));
            this.mViewsChanged = true;
        }
    }

    public java.util.List<android.graphics.Rect> computeChangedRects() {
        if (computeChanges()) {
            return this.mRects;
        }
        return null;
    }

    public boolean computeChanges() {
        boolean z = this.mRootRectsChanged;
        java.util.Iterator<android.view.ViewRootRectTracker.ViewInfo> it = this.mViewInfos.iterator();
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mRootRects);
        while (it.hasNext()) {
            android.view.ViewRootRectTracker.ViewInfo next = it.next();
            switch (next.update()) {
                case 0:
                    z = true;
                    break;
                case 2:
                    this.mViewsChanged = true;
                    it.remove();
                    continue;
            }
            arrayList.addAll(next.mRects);
        }
        if (z || this.mViewsChanged) {
            this.mViewsChanged = false;
            this.mRootRectsChanged = false;
            if (!this.mRects.equals(arrayList)) {
                this.mRects = arrayList;
                return true;
            }
        }
        return false;
    }

    public java.util.List<android.graphics.Rect> getLastComputedRects() {
        return this.mRects;
    }

    public void setRootRects(java.util.List<android.graphics.Rect> list) {
        com.android.internal.util.Preconditions.checkNotNull(list, "rects must not be null");
        this.mRootRects = list;
        this.mRootRectsChanged = true;
    }

    public java.util.List<android.graphics.Rect> getRootRects() {
        return this.mRootRects;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.graphics.Rect> getTrackedRectsForView(android.view.View view) {
        java.util.List<android.graphics.Rect> apply = this.mRectCollector.apply(view);
        return apply == null ? java.util.Collections.emptyList() : apply;
    }

    private class ViewInfo {
        public static final int CHANGED = 0;
        public static final int GONE = 2;
        public static final int UNCHANGED = 1;
        boolean mDirty = true;
        java.util.List<android.graphics.Rect> mRects = java.util.Collections.emptyList();
        private final java.lang.ref.WeakReference<android.view.View> mView;

        ViewInfo(android.view.View view) {
            this.mView = new java.lang.ref.WeakReference<>(view);
        }

        public android.view.View getView() {
            return this.mView.get();
        }

        public int update() {
            android.view.View view = getView();
            if (view == null || !view.isAttachedToWindow() || !view.isAggregatedVisible()) {
                return 2;
            }
            java.util.List trackedRectsForView = android.view.ViewRootRectTracker.this.getTrackedRectsForView(view);
            java.util.ArrayList arrayList = new java.util.ArrayList(trackedRectsForView.size());
            java.util.Iterator it = trackedRectsForView.iterator();
            while (it.hasNext()) {
                android.graphics.Rect rect = new android.graphics.Rect((android.graphics.Rect) it.next());
                android.view.ViewParent parent = view.getParent();
                if (parent != null && parent.getChildVisibleRect(view, rect, null)) {
                    arrayList.add(rect);
                }
            }
            if (this.mRects.equals(trackedRectsForView)) {
                return 1;
            }
            this.mRects = arrayList;
            return 0;
        }
    }
}
