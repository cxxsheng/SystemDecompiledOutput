package com.android.internal.widget;

/* loaded from: classes5.dex */
final class GapWorker implements java.lang.Runnable {
    static final java.lang.ThreadLocal<com.android.internal.widget.GapWorker> sGapWorker = new java.lang.ThreadLocal<>();
    static java.util.Comparator<com.android.internal.widget.GapWorker.Task> sTaskComparator = new java.util.Comparator<com.android.internal.widget.GapWorker.Task>() { // from class: com.android.internal.widget.GapWorker.1
        @Override // java.util.Comparator
        public int compare(com.android.internal.widget.GapWorker.Task task, com.android.internal.widget.GapWorker.Task task2) {
            if ((task.view == null) != (task2.view == null)) {
                return task.view == null ? 1 : -1;
            }
            if (task.immediate != task2.immediate) {
                return task.immediate ? -1 : 1;
            }
            int i = task2.viewVelocity - task.viewVelocity;
            if (i != 0) {
                return i;
            }
            int i2 = task.distanceToItem - task2.distanceToItem;
            if (i2 != 0) {
                return i2;
            }
            return 0;
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    java.util.ArrayList<com.android.internal.widget.RecyclerView> mRecyclerViews = new java.util.ArrayList<>();
    private java.util.ArrayList<com.android.internal.widget.GapWorker.Task> mTasks = new java.util.ArrayList<>();

    GapWorker() {
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public com.android.internal.widget.RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

    static class LayoutPrefetchRegistryImpl implements com.android.internal.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        void setPrefetchVector(int i, int i2) {
            this.mPrefetchDx = i;
            this.mPrefetchDy = i2;
        }

        void collectPrefetchPositionsFromView(com.android.internal.widget.RecyclerView recyclerView, boolean z) {
            this.mCount = 0;
            if (this.mPrefetchArray != null) {
                java.util.Arrays.fill(this.mPrefetchArray, -1);
            }
            com.android.internal.widget.RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
            if (recyclerView.mAdapter != null && layoutManager != null && layoutManager.isItemPrefetchEnabled()) {
                if (z) {
                    if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                        layoutManager.collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
                    }
                } else if (!recyclerView.hasPendingAdapterUpdates()) {
                    layoutManager.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
                }
                if (this.mCount > layoutManager.mPrefetchMaxCountObserved) {
                    layoutManager.mPrefetchMaxCountObserved = this.mCount;
                    layoutManager.mPrefetchMaxObservedInInitialPrefetch = z;
                    recyclerView.mRecycler.updateViewCacheSize();
                }
            }
        }

        @Override // com.android.internal.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry
        public void addPosition(int i, int i2) {
            if (i2 < 0) {
                throw new java.lang.IllegalArgumentException("Pixel distance must be non-negative");
            }
            int i3 = this.mCount * 2;
            if (this.mPrefetchArray == null) {
                this.mPrefetchArray = new int[4];
                java.util.Arrays.fill(this.mPrefetchArray, -1);
            } else if (i3 >= this.mPrefetchArray.length) {
                int[] iArr = this.mPrefetchArray;
                this.mPrefetchArray = new int[i3 * 2];
                java.lang.System.arraycopy(iArr, 0, this.mPrefetchArray, 0, iArr.length);
            }
            this.mPrefetchArray[i3] = i;
            this.mPrefetchArray[i3 + 1] = i2;
            this.mCount++;
        }

        boolean lastPrefetchIncludedPosition(int i) {
            if (this.mPrefetchArray != null) {
                int i2 = this.mCount * 2;
                for (int i3 = 0; i3 < i2; i3 += 2) {
                    if (this.mPrefetchArray[i3] == i) {
                        return true;
                    }
                }
            }
            return false;
        }

        void clearPrefetchPositions() {
            if (this.mPrefetchArray != null) {
                java.util.Arrays.fill(this.mPrefetchArray, -1);
            }
        }
    }

    public void add(com.android.internal.widget.RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    public void remove(com.android.internal.widget.RecyclerView recyclerView) {
        this.mRecyclerViews.remove(recyclerView);
    }

    void postFromTraversal(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post(this);
        }
        recyclerView.mPrefetchRegistry.setPrefetchVector(i, i2);
    }

    private void buildTaskList() {
        com.android.internal.widget.GapWorker.Task task;
        int size = this.mRecyclerViews.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.widget.RecyclerView recyclerView = this.mRecyclerViews.get(i2);
            recyclerView.mPrefetchRegistry.collectPrefetchPositionsFromView(recyclerView, false);
            i += recyclerView.mPrefetchRegistry.mCount;
        }
        this.mTasks.ensureCapacity(i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            com.android.internal.widget.RecyclerView recyclerView2 = this.mRecyclerViews.get(i4);
            com.android.internal.widget.GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView2.mPrefetchRegistry;
            int abs = java.lang.Math.abs(layoutPrefetchRegistryImpl.mPrefetchDx) + java.lang.Math.abs(layoutPrefetchRegistryImpl.mPrefetchDy);
            for (int i5 = 0; i5 < layoutPrefetchRegistryImpl.mCount * 2; i5 += 2) {
                if (i3 >= this.mTasks.size()) {
                    task = new com.android.internal.widget.GapWorker.Task();
                    this.mTasks.add(task);
                } else {
                    task = this.mTasks.get(i3);
                }
                int i6 = layoutPrefetchRegistryImpl.mPrefetchArray[i5 + 1];
                task.immediate = i6 <= abs;
                task.viewVelocity = abs;
                task.distanceToItem = i6;
                task.view = recyclerView2;
                task.position = layoutPrefetchRegistryImpl.mPrefetchArray[i5];
                i3++;
            }
        }
        java.util.Collections.sort(this.mTasks, sTaskComparator);
    }

    static boolean isPrefetchPositionAttached(com.android.internal.widget.RecyclerView recyclerView, int i) {
        int unfilteredChildCount = recyclerView.mChildHelper.getUnfilteredChildCount();
        for (int i2 = 0; i2 < unfilteredChildCount; i2++) {
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolderInt = com.android.internal.widget.RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(i2));
            if (childViewHolderInt.mPosition == i && !childViewHolderInt.isInvalid()) {
                return true;
            }
        }
        return false;
    }

    private com.android.internal.widget.RecyclerView.ViewHolder prefetchPositionWithDeadline(com.android.internal.widget.RecyclerView recyclerView, int i, long j) {
        if (isPrefetchPositionAttached(recyclerView, i)) {
            return null;
        }
        com.android.internal.widget.RecyclerView.Recycler recycler = recyclerView.mRecycler;
        com.android.internal.widget.RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline = recycler.tryGetViewHolderForPositionByDeadline(i, false, j);
        if (tryGetViewHolderForPositionByDeadline != null) {
            if (tryGetViewHolderForPositionByDeadline.isBound()) {
                recycler.recycleView(tryGetViewHolderForPositionByDeadline.itemView);
            } else {
                recycler.addViewHolderToRecycledViewPool(tryGetViewHolderForPositionByDeadline, false);
            }
        }
        return tryGetViewHolderForPositionByDeadline;
    }

    private void prefetchInnerRecyclerViewWithDeadline(com.android.internal.widget.RecyclerView recyclerView, long j) {
        if (recyclerView == null) {
            return;
        }
        if (recyclerView.mDataSetHasChangedAfterLayout && recyclerView.mChildHelper.getUnfilteredChildCount() != 0) {
            recyclerView.removeAndRecycleViews();
        }
        com.android.internal.widget.GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(recyclerView, true);
        if (layoutPrefetchRegistryImpl.mCount != 0) {
            try {
                android.os.Trace.beginSection("RV Nested Prefetch");
                recyclerView.mState.prepareForNestedPrefetch(recyclerView.mAdapter);
                for (int i = 0; i < layoutPrefetchRegistryImpl.mCount * 2; i += 2) {
                    prefetchPositionWithDeadline(recyclerView, layoutPrefetchRegistryImpl.mPrefetchArray[i], j);
                }
            } finally {
                android.os.Trace.endSection();
            }
        }
    }

    private void flushTaskWithDeadline(com.android.internal.widget.GapWorker.Task task, long j) {
        com.android.internal.widget.RecyclerView.ViewHolder prefetchPositionWithDeadline = prefetchPositionWithDeadline(task.view, task.position, task.immediate ? Long.MAX_VALUE : j);
        if (prefetchPositionWithDeadline != null && prefetchPositionWithDeadline.mNestedRecyclerView != null) {
            prefetchInnerRecyclerViewWithDeadline(prefetchPositionWithDeadline.mNestedRecyclerView.get(), j);
        }
    }

    private void flushTasksWithDeadline(long j) {
        for (int i = 0; i < this.mTasks.size(); i++) {
            com.android.internal.widget.GapWorker.Task task = this.mTasks.get(i);
            if (task.view != null) {
                flushTaskWithDeadline(task, j);
                task.clear();
            } else {
                return;
            }
        }
    }

    void prefetch(long j) {
        buildTaskList();
        flushTasksWithDeadline(j);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            android.os.Trace.beginSection("RV Prefetch");
            if (this.mRecyclerViews.isEmpty()) {
                return;
            }
            long nanos = java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(this.mRecyclerViews.get(0).getDrawingTime());
            if (nanos == 0) {
                return;
            }
            prefetch(nanos + this.mFrameIntervalNs);
        } finally {
            this.mPostTimeNs = 0L;
            android.os.Trace.endSection();
        }
    }
}
