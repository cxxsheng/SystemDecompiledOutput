package com.android.internal.widget;

/* loaded from: classes5.dex */
class ChildHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ChildrenHelper";
    final com.android.internal.widget.ChildHelper.Callback mCallback;
    final com.android.internal.widget.ChildHelper.Bucket mBucket = new com.android.internal.widget.ChildHelper.Bucket();
    final java.util.List<android.view.View> mHiddenViews = new java.util.ArrayList();

    interface Callback {
        void addView(android.view.View view, int i);

        void attachViewToParent(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams);

        void detachViewFromParent(int i);

        android.view.View getChildAt(int i);

        int getChildCount();

        com.android.internal.widget.RecyclerView.ViewHolder getChildViewHolder(android.view.View view);

        int indexOfChild(android.view.View view);

        void onEnteredHiddenState(android.view.View view);

        void onLeftHiddenState(android.view.View view);

        void removeAllViews();

        void removeViewAt(int i);
    }

    ChildHelper(com.android.internal.widget.ChildHelper.Callback callback) {
        this.mCallback = callback;
    }

    private void hideViewInternal(android.view.View view) {
        this.mHiddenViews.add(view);
        this.mCallback.onEnteredHiddenState(view);
    }

    private boolean unhideViewInternal(android.view.View view) {
        if (this.mHiddenViews.remove(view)) {
            this.mCallback.onLeftHiddenState(view);
            return true;
        }
        return false;
    }

    void addView(android.view.View view, boolean z) {
        addView(view, -1, z);
    }

    void addView(android.view.View view, int i, boolean z) {
        int offset;
        if (i < 0) {
            offset = this.mCallback.getChildCount();
        } else {
            offset = getOffset(i);
        }
        this.mBucket.insert(offset, z);
        if (z) {
            hideViewInternal(view);
        }
        this.mCallback.addView(view, offset);
    }

    private int getOffset(int i) {
        if (i < 0) {
            return -1;
        }
        int childCount = this.mCallback.getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            int countOnesBefore = i - (i2 - this.mBucket.countOnesBefore(i2));
            if (countOnesBefore == 0) {
                while (this.mBucket.get(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += countOnesBefore;
        }
        return -1;
    }

    void removeView(android.view.View view) {
        int indexOfChild = this.mCallback.indexOfChild(view);
        if (indexOfChild < 0) {
            return;
        }
        if (this.mBucket.remove(indexOfChild)) {
            unhideViewInternal(view);
        }
        this.mCallback.removeViewAt(indexOfChild);
    }

    void removeViewAt(int i) {
        int offset = getOffset(i);
        android.view.View childAt = this.mCallback.getChildAt(offset);
        if (childAt == null) {
            return;
        }
        if (this.mBucket.remove(offset)) {
            unhideViewInternal(childAt);
        }
        this.mCallback.removeViewAt(offset);
    }

    android.view.View getChildAt(int i) {
        return this.mCallback.getChildAt(getOffset(i));
    }

    void removeAllViewsUnfiltered() {
        this.mBucket.reset();
        for (int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            this.mCallback.onLeftHiddenState(this.mHiddenViews.get(size));
            this.mHiddenViews.remove(size);
        }
        this.mCallback.removeAllViews();
    }

    android.view.View findHiddenNonRemovedView(int i) {
        int size = this.mHiddenViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.View view = this.mHiddenViews.get(i2);
            com.android.internal.widget.RecyclerView.ViewHolder childViewHolder = this.mCallback.getChildViewHolder(view);
            if (childViewHolder.getLayoutPosition() == i && !childViewHolder.isInvalid() && !childViewHolder.isRemoved()) {
                return view;
            }
        }
        return null;
    }

    void attachViewToParent(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams, boolean z) {
        int offset;
        if (i < 0) {
            offset = this.mCallback.getChildCount();
        } else {
            offset = getOffset(i);
        }
        this.mBucket.insert(offset, z);
        if (z) {
            hideViewInternal(view);
        }
        this.mCallback.attachViewToParent(view, offset, layoutParams);
    }

    int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    android.view.View getUnfilteredChildAt(int i) {
        return this.mCallback.getChildAt(i);
    }

    void detachViewFromParent(int i) {
        int offset = getOffset(i);
        this.mBucket.remove(offset);
        this.mCallback.detachViewFromParent(offset);
    }

    int indexOfChild(android.view.View view) {
        int indexOfChild = this.mCallback.indexOfChild(view);
        if (indexOfChild == -1 || this.mBucket.get(indexOfChild)) {
            return -1;
        }
        return indexOfChild - this.mBucket.countOnesBefore(indexOfChild);
    }

    boolean isHidden(android.view.View view) {
        return this.mHiddenViews.contains(view);
    }

    void hide(android.view.View view) {
        int indexOfChild = this.mCallback.indexOfChild(view);
        if (indexOfChild < 0) {
            throw new java.lang.IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        this.mBucket.set(indexOfChild);
        hideViewInternal(view);
    }

    void unhide(android.view.View view) {
        int indexOfChild = this.mCallback.indexOfChild(view);
        if (indexOfChild < 0) {
            throw new java.lang.IllegalArgumentException("view is not a child, cannot hide " + view);
        }
        if (!this.mBucket.get(indexOfChild)) {
            throw new java.lang.RuntimeException("trying to unhide a view that was not hidden" + view);
        }
        this.mBucket.clear(indexOfChild);
        unhideViewInternal(view);
    }

    public java.lang.String toString() {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    boolean removeViewIfHidden(android.view.View view) {
        int indexOfChild = this.mCallback.indexOfChild(view);
        if (indexOfChild == -1) {
            unhideViewInternal(view);
            return true;
        }
        if (this.mBucket.get(indexOfChild)) {
            this.mBucket.remove(indexOfChild);
            unhideViewInternal(view);
            this.mCallback.removeViewAt(indexOfChild);
            return true;
        }
        return false;
    }

    static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = Long.MIN_VALUE;
        long mData = 0;
        com.android.internal.widget.ChildHelper.Bucket mNext;

        Bucket() {
        }

        void set(int i) {
            if (i >= 64) {
                ensureNext();
                this.mNext.set(i - 64);
            } else {
                this.mData |= 1 << i;
            }
        }

        private void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new com.android.internal.widget.ChildHelper.Bucket();
            }
        }

        void clear(int i) {
            if (i >= 64) {
                if (this.mNext != null) {
                    this.mNext.clear(i - 64);
                    return;
                }
                return;
            }
            this.mData &= ~(1 << i);
        }

        boolean get(int i) {
            if (i < 64) {
                return (this.mData & (1 << i)) != 0;
            }
            ensureNext();
            return this.mNext.get(i - 64);
        }

        void reset() {
            this.mData = 0L;
            if (this.mNext != null) {
                this.mNext.reset();
            }
        }

        void insert(int i, boolean z) {
            if (i >= 64) {
                ensureNext();
                this.mNext.insert(i - 64, z);
                return;
            }
            boolean z2 = (this.mData & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            this.mData = (this.mData & j) | (((~j) & this.mData) << 1);
            if (z) {
                set(i);
            } else {
                clear(i);
            }
            if (z2 || this.mNext != null) {
                ensureNext();
                this.mNext.insert(0, z2);
            }
        }

        boolean remove(int i) {
            if (i >= 64) {
                ensureNext();
                return this.mNext.remove(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.mData & j) != 0;
            this.mData &= ~j;
            long j2 = j - 1;
            this.mData = (this.mData & j2) | java.lang.Long.rotateRight((~j2) & this.mData, 1);
            if (this.mNext != null) {
                if (this.mNext.get(0)) {
                    set(63);
                }
                this.mNext.remove(0);
            }
            return z;
        }

        int countOnesBefore(int i) {
            if (this.mNext == null) {
                if (i >= 64) {
                    return java.lang.Long.bitCount(this.mData);
                }
                return java.lang.Long.bitCount(this.mData & ((1 << i) - 1));
            }
            if (i < 64) {
                return java.lang.Long.bitCount(this.mData & ((1 << i) - 1));
            }
            return this.mNext.countOnesBefore(i - 64) + java.lang.Long.bitCount(this.mData);
        }

        public java.lang.String toString() {
            return this.mNext == null ? java.lang.Long.toBinaryString(this.mData) : this.mNext.toString() + "xx" + java.lang.Long.toBinaryString(this.mData);
        }
    }
}
