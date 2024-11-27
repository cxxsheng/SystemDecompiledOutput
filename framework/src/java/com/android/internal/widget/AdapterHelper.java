package com.android.internal.widget;

/* loaded from: classes5.dex */
class AdapterHelper implements com.android.internal.widget.OpReorderer.Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final java.lang.String TAG = "AHT";
    final com.android.internal.widget.AdapterHelper.Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes;
    java.lang.Runnable mOnItemProcessedCallback;
    final com.android.internal.widget.OpReorderer mOpReorderer;
    final java.util.ArrayList<com.android.internal.widget.AdapterHelper.UpdateOp> mPendingUpdates;
    final java.util.ArrayList<com.android.internal.widget.AdapterHelper.UpdateOp> mPostponedList;
    private android.util.Pools.Pool<com.android.internal.widget.AdapterHelper.UpdateOp> mUpdateOpPool;

    interface Callback {
        com.android.internal.widget.RecyclerView.ViewHolder findViewHolder(int i);

        void markViewHoldersUpdated(int i, int i2, java.lang.Object obj);

        void offsetPositionsForAdd(int i, int i2);

        void offsetPositionsForMove(int i, int i2);

        void offsetPositionsForRemovingInvisible(int i, int i2);

        void offsetPositionsForRemovingLaidOutOrNewView(int i, int i2);

        void onDispatchFirstPass(com.android.internal.widget.AdapterHelper.UpdateOp updateOp);

        void onDispatchSecondPass(com.android.internal.widget.AdapterHelper.UpdateOp updateOp);
    }

    AdapterHelper(com.android.internal.widget.AdapterHelper.Callback callback) {
        this(callback, false);
    }

    AdapterHelper(com.android.internal.widget.AdapterHelper.Callback callback, boolean z) {
        this.mUpdateOpPool = new android.util.Pools.SimplePool(30);
        this.mPendingUpdates = new java.util.ArrayList<>();
        this.mPostponedList = new java.util.ArrayList<>();
        this.mExistingUpdateTypes = 0;
        this.mCallback = callback;
        this.mDisableRecycler = z;
        this.mOpReorderer = new com.android.internal.widget.OpReorderer(this);
    }

    com.android.internal.widget.AdapterHelper addUpdateOp(com.android.internal.widget.AdapterHelper.UpdateOp... updateOpArr) {
        java.util.Collections.addAll(this.mPendingUpdates, updateOpArr);
        return this;
    }

    void reset() {
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPendingUpdates.get(i);
            switch (updateOp.cmd) {
                case 1:
                    applyAdd(updateOp);
                    break;
                case 2:
                    applyRemove(updateOp);
                    break;
                case 4:
                    applyUpdate(updateOp);
                    break;
                case 8:
                    applyMove(updateOp);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        this.mPendingUpdates.clear();
    }

    void consumePostponedUpdates() {
        int size = this.mPostponedList.size();
        for (int i = 0; i < size; i++) {
            this.mCallback.onDispatchSecondPass(this.mPostponedList.get(i));
        }
        recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    private void applyMove(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        postponeAndUpdateViewHolders(updateOp);
    }

    private void applyRemove(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        boolean z;
        char c;
        int i = updateOp.positionStart;
        int i2 = updateOp.positionStart + updateOp.itemCount;
        int i3 = updateOp.positionStart;
        char c2 = 65535;
        int i4 = 0;
        while (i3 < i2) {
            if (this.mCallback.findViewHolder(i3) != null || canFindInPreLayout(i3)) {
                if (c2 != 0) {
                    z = false;
                } else {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(2, i, i4, null));
                    z = true;
                }
                c = 1;
            } else {
                if (c2 != 1) {
                    z = false;
                } else {
                    postponeAndUpdateViewHolders(obtainUpdateOp(2, i, i4, null));
                    z = true;
                }
                c = 0;
            }
            if (z) {
                i3 -= i4;
                i2 -= i4;
                i4 = 1;
            } else {
                i4++;
            }
            i3++;
            c2 = c;
        }
        if (i4 != updateOp.itemCount) {
            recycleUpdateOp(updateOp);
            updateOp = obtainUpdateOp(2, i, i4, null);
        }
        if (c2 == 0) {
            dispatchAndUpdateViewHolders(updateOp);
        } else {
            postponeAndUpdateViewHolders(updateOp);
        }
    }

    private void applyUpdate(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        int i = updateOp.positionStart;
        int i2 = updateOp.positionStart + updateOp.itemCount;
        char c = 65535;
        int i3 = 0;
        for (int i4 = updateOp.positionStart; i4 < i2; i4++) {
            if (this.mCallback.findViewHolder(i4) != null || canFindInPreLayout(i4)) {
                if (c == 0) {
                    dispatchAndUpdateViewHolders(obtainUpdateOp(4, i, i3, updateOp.payload));
                    i = i4;
                    i3 = 0;
                }
                c = 1;
            } else {
                if (c == 1) {
                    postponeAndUpdateViewHolders(obtainUpdateOp(4, i, i3, updateOp.payload));
                    i = i4;
                    i3 = 0;
                }
                c = 0;
            }
            i3++;
        }
        if (i3 != updateOp.itemCount) {
            java.lang.Object obj = updateOp.payload;
            recycleUpdateOp(updateOp);
            updateOp = obtainUpdateOp(4, i, i3, obj);
        }
        if (c == 0) {
            dispatchAndUpdateViewHolders(updateOp);
        } else {
            postponeAndUpdateViewHolders(updateOp);
        }
    }

    private void dispatchAndUpdateViewHolders(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        int i;
        boolean z;
        if (updateOp.cmd == 1 || updateOp.cmd == 8) {
            throw new java.lang.IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int updatePositionWithPostponed = updatePositionWithPostponed(updateOp.positionStart, updateOp.cmd);
        int i2 = updateOp.positionStart;
        switch (updateOp.cmd) {
            case 2:
                i = 0;
                break;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("op should be remove or update." + updateOp);
            case 4:
                i = 1;
                break;
        }
        int i3 = 1;
        for (int i4 = 1; i4 < updateOp.itemCount; i4++) {
            int updatePositionWithPostponed2 = updatePositionWithPostponed(updateOp.positionStart + (i * i4), updateOp.cmd);
            switch (updateOp.cmd) {
                case 2:
                    if (updatePositionWithPostponed2 == updatePositionWithPostponed) {
                        z = true;
                        break;
                    } else {
                        z = false;
                        break;
                    }
                case 3:
                default:
                    z = false;
                    break;
                case 4:
                    if (updatePositionWithPostponed2 == updatePositionWithPostponed + 1) {
                        z = true;
                        break;
                    } else {
                        z = false;
                        break;
                    }
            }
            if (z) {
                i3++;
            } else {
                com.android.internal.widget.AdapterHelper.UpdateOp obtainUpdateOp = obtainUpdateOp(updateOp.cmd, updatePositionWithPostponed, i3, updateOp.payload);
                dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp, i2);
                recycleUpdateOp(obtainUpdateOp);
                if (updateOp.cmd == 4) {
                    i2 += i3;
                }
                i3 = 1;
                updatePositionWithPostponed = updatePositionWithPostponed2;
            }
        }
        java.lang.Object obj = updateOp.payload;
        recycleUpdateOp(updateOp);
        if (i3 > 0) {
            com.android.internal.widget.AdapterHelper.UpdateOp obtainUpdateOp2 = obtainUpdateOp(updateOp.cmd, updatePositionWithPostponed, i3, obj);
            dispatchFirstPassAndUpdateViewHolders(obtainUpdateOp2, i2);
            recycleUpdateOp(obtainUpdateOp2);
        }
    }

    void dispatchFirstPassAndUpdateViewHolders(com.android.internal.widget.AdapterHelper.UpdateOp updateOp, int i) {
        this.mCallback.onDispatchFirstPass(updateOp);
        switch (updateOp.cmd) {
            case 2:
                this.mCallback.offsetPositionsForRemovingInvisible(i, updateOp.itemCount);
                return;
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            case 4:
                this.mCallback.markViewHoldersUpdated(i, updateOp.itemCount, updateOp.payload);
                return;
        }
    }

    private int updatePositionWithPostponed(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.mPostponedList.size() - 1; size >= 0; size--) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPostponedList.get(size);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart < updateOp.itemCount) {
                    i3 = updateOp.positionStart;
                    i4 = updateOp.itemCount;
                } else {
                    i3 = updateOp.itemCount;
                    i4 = updateOp.positionStart;
                }
                if (i >= i3 && i <= i4) {
                    if (i3 == updateOp.positionStart) {
                        if (i2 == 1) {
                            updateOp.itemCount++;
                        } else if (i2 == 2) {
                            updateOp.itemCount--;
                        }
                        i++;
                    } else {
                        if (i2 == 1) {
                            updateOp.positionStart++;
                        } else if (i2 == 2) {
                            updateOp.positionStart--;
                        }
                        i--;
                    }
                } else if (i < updateOp.positionStart) {
                    if (i2 == 1) {
                        updateOp.positionStart++;
                        updateOp.itemCount++;
                    } else if (i2 == 2) {
                        updateOp.positionStart--;
                        updateOp.itemCount--;
                    }
                }
            } else if (updateOp.positionStart <= i) {
                if (updateOp.cmd == 1) {
                    i -= updateOp.itemCount;
                } else if (updateOp.cmd == 2) {
                    i += updateOp.itemCount;
                }
            } else if (i2 == 1) {
                updateOp.positionStart++;
            } else if (i2 == 2) {
                updateOp.positionStart--;
            }
        }
        for (int size2 = this.mPostponedList.size() - 1; size2 >= 0; size2--) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp2 = this.mPostponedList.get(size2);
            if (updateOp2.cmd == 8) {
                if (updateOp2.itemCount == updateOp2.positionStart || updateOp2.itemCount < 0) {
                    this.mPostponedList.remove(size2);
                    recycleUpdateOp(updateOp2);
                }
            } else if (updateOp2.itemCount <= 0) {
                this.mPostponedList.remove(size2);
                recycleUpdateOp(updateOp2);
            }
        }
        return i;
    }

    private boolean canFindInPreLayout(int i) {
        int size = this.mPostponedList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPostponedList.get(i2);
            if (updateOp.cmd == 8) {
                if (findPositionOffset(updateOp.itemCount, i2 + 1) == i) {
                    return true;
                }
            } else if (updateOp.cmd == 1) {
                int i3 = updateOp.positionStart + updateOp.itemCount;
                for (int i4 = updateOp.positionStart; i4 < i3; i4++) {
                    if (findPositionOffset(i4, i2 + 1) == i) {
                        return true;
                    }
                }
            } else {
                continue;
            }
        }
        return false;
    }

    private void applyAdd(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        postponeAndUpdateViewHolders(updateOp);
    }

    private void postponeAndUpdateViewHolders(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        switch (updateOp.cmd) {
            case 1:
                this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
                return;
            case 2:
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(updateOp.positionStart, updateOp.itemCount);
                return;
            case 4:
                this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                return;
            case 8:
                this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown update op type for " + updateOp);
        }
    }

    boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    boolean hasAnyUpdateTypes(int i) {
        return (i & this.mExistingUpdateTypes) != 0;
    }

    int findPositionOffset(int i) {
        return findPositionOffset(i, 0);
    }

    int findPositionOffset(int i, int i2) {
        int size = this.mPostponedList.size();
        while (i2 < size) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPostponedList.get(i2);
            if (updateOp.cmd == 8) {
                if (updateOp.positionStart == i) {
                    i = updateOp.itemCount;
                } else {
                    if (updateOp.positionStart < i) {
                        i--;
                    }
                    if (updateOp.itemCount <= i) {
                        i++;
                    }
                }
            } else if (updateOp.positionStart > i) {
                continue;
            } else if (updateOp.cmd == 2) {
                if (i < updateOp.positionStart + updateOp.itemCount) {
                    return -1;
                }
                i -= updateOp.itemCount;
            } else if (updateOp.cmd == 1) {
                i += updateOp.itemCount;
            }
            i2++;
        }
        return i;
    }

    boolean onItemRangeChanged(int i, int i2, java.lang.Object obj) {
        if (i2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(4, i, i2, obj));
        this.mExistingUpdateTypes |= 4;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeInserted(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(1, i, i2, null));
        this.mExistingUpdateTypes |= 1;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeRemoved(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.mPendingUpdates.add(obtainUpdateOp(2, i, i2, null));
        this.mExistingUpdateTypes |= 2;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeMoved(int i, int i2, int i3) {
        if (i == i2) {
            return false;
        }
        if (i3 != 1) {
            throw new java.lang.IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.mPendingUpdates.add(obtainUpdateOp(8, i, i2, null));
        this.mExistingUpdateTypes |= 8;
        if (this.mPendingUpdates.size() != 1) {
            return false;
        }
        return true;
    }

    void consumeUpdatesInOnePass() {
        consumePostponedUpdates();
        int size = this.mPendingUpdates.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPendingUpdates.get(i);
            switch (updateOp.cmd) {
                case 1:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
                    break;
                case 2:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForRemovingInvisible(updateOp.positionStart, updateOp.itemCount);
                    break;
                case 4:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                    break;
                case 8:
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
                    break;
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
        }
        recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.mExistingUpdateTypes = 0;
    }

    public int applyPendingUpdatesToPosition(int i) {
        int size = this.mPendingUpdates.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = this.mPendingUpdates.get(i2);
            switch (updateOp.cmd) {
                case 1:
                    if (updateOp.positionStart <= i) {
                        i += updateOp.itemCount;
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (updateOp.positionStart <= i) {
                        if (updateOp.positionStart + updateOp.itemCount > i) {
                            return -1;
                        }
                        i -= updateOp.itemCount;
                        break;
                    } else {
                        continue;
                    }
                case 8:
                    if (updateOp.positionStart == i) {
                        i = updateOp.itemCount;
                        break;
                    } else {
                        if (updateOp.positionStart < i) {
                            i--;
                        }
                        if (updateOp.itemCount <= i) {
                            i++;
                            break;
                        } else {
                            break;
                        }
                    }
            }
        }
        return i;
    }

    boolean hasUpdates() {
        return (this.mPostponedList.isEmpty() || this.mPendingUpdates.isEmpty()) ? false : true;
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        java.lang.Object payload;
        int positionStart;

        UpdateOp(int i, int i2, int i3, java.lang.Object obj) {
            this.cmd = i;
            this.positionStart = i2;
            this.itemCount = i3;
            this.payload = obj;
        }

        java.lang.String cmdToString() {
            switch (this.cmd) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                case 4:
                    return android.media.MediaMetrics.Value.UP;
                case 8:
                    return "mv";
                default:
                    return "??";
            }
        }

        public java.lang.String toString() {
            return java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.widget.AdapterHelper.UpdateOp updateOp = (com.android.internal.widget.AdapterHelper.UpdateOp) obj;
            if (this.cmd != updateOp.cmd) {
                return false;
            }
            if (this.cmd == 8 && java.lang.Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == updateOp.positionStart && this.positionStart == updateOp.itemCount) {
                return true;
            }
            if (this.itemCount != updateOp.itemCount || this.positionStart != updateOp.positionStart) {
                return false;
            }
            if (this.payload != null) {
                if (!this.payload.equals(updateOp.payload)) {
                    return false;
                }
            } else if (updateOp.payload != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.cmd * 31) + this.positionStart) * 31) + this.itemCount;
        }
    }

    @Override // com.android.internal.widget.OpReorderer.Callback
    public com.android.internal.widget.AdapterHelper.UpdateOp obtainUpdateOp(int i, int i2, int i3, java.lang.Object obj) {
        com.android.internal.widget.AdapterHelper.UpdateOp acquire = this.mUpdateOpPool.acquire();
        if (acquire == null) {
            return new com.android.internal.widget.AdapterHelper.UpdateOp(i, i2, i3, obj);
        }
        acquire.cmd = i;
        acquire.positionStart = i2;
        acquire.itemCount = i3;
        acquire.payload = obj;
        return acquire;
    }

    @Override // com.android.internal.widget.OpReorderer.Callback
    public void recycleUpdateOp(com.android.internal.widget.AdapterHelper.UpdateOp updateOp) {
        if (!this.mDisableRecycler) {
            updateOp.payload = null;
            this.mUpdateOpPool.release(updateOp);
        }
    }

    void recycleUpdateOpsAndClearList(java.util.List<com.android.internal.widget.AdapterHelper.UpdateOp> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            recycleUpdateOp(list.get(i));
        }
        list.clear();
    }
}
