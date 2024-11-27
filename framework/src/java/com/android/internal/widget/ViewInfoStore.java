package com.android.internal.widget;

/* loaded from: classes5.dex */
class ViewInfoStore {
    private static final boolean DEBUG = false;
    final android.util.ArrayMap<com.android.internal.widget.RecyclerView.ViewHolder, com.android.internal.widget.ViewInfoStore.InfoRecord> mLayoutHolderMap = new android.util.ArrayMap<>();
    final android.util.LongSparseArray<com.android.internal.widget.RecyclerView.ViewHolder> mOldChangedHolders = new android.util.LongSparseArray<>();

    interface ProcessCallback {
        void processAppeared(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processDisappeared(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processPersistent(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void unused(com.android.internal.widget.RecyclerView.ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    void addToPreLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = com.android.internal.widget.ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.preInfo = itemHolderInfo;
        infoRecord.flags |= 4;
    }

    boolean isDisappearing(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 1) == 0) ? false : true;
    }

    com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 4);
    }

    com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromPostLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 8);
    }

    private com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, int i) {
        com.android.internal.widget.ViewInfoStore.InfoRecord valueAt;
        com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo;
        int indexOfKey = this.mLayoutHolderMap.indexOfKey(viewHolder);
        if (indexOfKey < 0 || (valueAt = this.mLayoutHolderMap.valueAt(indexOfKey)) == null || (valueAt.flags & i) == 0) {
            return null;
        }
        valueAt.flags &= ~i;
        if (i == 4) {
            itemHolderInfo = valueAt.preInfo;
        } else if (i == 8) {
            itemHolderInfo = valueAt.postInfo;
        } else {
            throw new java.lang.IllegalArgumentException("Must provide flag PRE or POST");
        }
        if ((valueAt.flags & 12) == 0) {
            this.mLayoutHolderMap.removeAt(indexOfKey);
            com.android.internal.widget.ViewInfoStore.InfoRecord.recycle(valueAt);
        }
        return itemHolderInfo;
    }

    void addToOldChangeHolders(long j, com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        this.mOldChangedHolders.put(j, viewHolder);
    }

    void addToAppearedInPreLayoutHolders(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = com.android.internal.widget.ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.flags |= 2;
        infoRecord.preInfo = itemHolderInfo;
    }

    boolean isInPreLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 4) == 0) ? false : true;
    }

    com.android.internal.widget.RecyclerView.ViewHolder getFromOldChangeHolders(long j) {
        return this.mOldChangedHolders.get(j);
    }

    void addToPostLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder, com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = com.android.internal.widget.ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.postInfo = itemHolderInfo;
        infoRecord.flags |= 8;
    }

    void addToDisappearedInLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            infoRecord = com.android.internal.widget.ViewInfoStore.InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecord);
        }
        infoRecord.flags |= 1;
    }

    void removeFromDisappearedInLayout(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            return;
        }
        infoRecord.flags &= -2;
    }

    void process(com.android.internal.widget.ViewInfoStore.ProcessCallback processCallback) {
        for (int size = this.mLayoutHolderMap.size() - 1; size >= 0; size--) {
            com.android.internal.widget.RecyclerView.ViewHolder keyAt = this.mLayoutHolderMap.keyAt(size);
            com.android.internal.widget.ViewInfoStore.InfoRecord removeAt = this.mLayoutHolderMap.removeAt(size);
            if ((removeAt.flags & 3) == 3) {
                processCallback.unused(keyAt);
            } else if ((removeAt.flags & 1) != 0) {
                if (removeAt.preInfo == null) {
                    processCallback.unused(keyAt);
                } else {
                    processCallback.processDisappeared(keyAt, removeAt.preInfo, removeAt.postInfo);
                }
            } else if ((removeAt.flags & 14) == 14) {
                processCallback.processAppeared(keyAt, removeAt.preInfo, removeAt.postInfo);
            } else if ((removeAt.flags & 12) == 12) {
                processCallback.processPersistent(keyAt, removeAt.preInfo, removeAt.postInfo);
            } else if ((removeAt.flags & 4) != 0) {
                processCallback.processDisappeared(keyAt, removeAt.preInfo, null);
            } else if ((removeAt.flags & 8) != 0) {
                processCallback.processAppeared(keyAt, removeAt.preInfo, removeAt.postInfo);
            } else {
                int i = removeAt.flags;
            }
            com.android.internal.widget.ViewInfoStore.InfoRecord.recycle(removeAt);
        }
    }

    void removeViewHolder(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        int size = this.mOldChangedHolders.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (viewHolder != this.mOldChangedHolders.valueAt(size)) {
                size--;
            } else {
                this.mOldChangedHolders.removeAt(size);
                break;
            }
        }
        com.android.internal.widget.ViewInfoStore.InfoRecord remove = this.mLayoutHolderMap.remove(viewHolder);
        if (remove != null) {
            com.android.internal.widget.ViewInfoStore.InfoRecord.recycle(remove);
        }
    }

    void onDetach() {
        com.android.internal.widget.ViewInfoStore.InfoRecord.drainCache();
    }

    public void onViewDetached(com.android.internal.widget.RecyclerView.ViewHolder viewHolder) {
        removeFromDisappearedInLayout(viewHolder);
    }

    static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static android.util.Pools.Pool<com.android.internal.widget.ViewInfoStore.InfoRecord> sPool = new android.util.Pools.SimplePool(20);
        int flags;
        com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
        com.android.internal.widget.RecyclerView.ItemAnimator.ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static com.android.internal.widget.ViewInfoStore.InfoRecord obtain() {
            com.android.internal.widget.ViewInfoStore.InfoRecord acquire = sPool.acquire();
            return acquire == null ? new com.android.internal.widget.ViewInfoStore.InfoRecord() : acquire;
        }

        static void recycle(com.android.internal.widget.ViewInfoStore.InfoRecord infoRecord) {
            infoRecord.flags = 0;
            infoRecord.preInfo = null;
            infoRecord.postInfo = null;
            sPool.release(infoRecord);
        }

        static void drainCache() {
            while (sPool.acquire() != null) {
            }
        }
    }
}
