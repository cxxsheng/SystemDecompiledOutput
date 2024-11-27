package com.android.server.textclassifier;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public final class FixedSizeQueue<E> {
    private final java.util.Queue<E> mDelegate;
    private final int mMaxSize;

    @android.annotation.Nullable
    private final com.android.server.textclassifier.FixedSizeQueue.OnEntryEvictedListener<E> mOnEntryEvictedListener;

    public interface OnEntryEvictedListener<E> {
        void onEntryEvicted(@android.annotation.NonNull E e);
    }

    public FixedSizeQueue(int i, @android.annotation.Nullable com.android.server.textclassifier.FixedSizeQueue.OnEntryEvictedListener<E> onEntryEvictedListener) {
        com.android.internal.util.Preconditions.checkArgument(i > 0, "maxSize (%s) must > 0", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        this.mDelegate = new java.util.ArrayDeque(i);
        this.mMaxSize = i;
        this.mOnEntryEvictedListener = onEntryEvictedListener;
    }

    public int size() {
        return this.mDelegate.size();
    }

    public boolean add(@android.annotation.NonNull E e) {
        java.util.Objects.requireNonNull(e);
        if (size() == this.mMaxSize) {
            E remove = this.mDelegate.remove();
            if (this.mOnEntryEvictedListener != null) {
                this.mOnEntryEvictedListener.onEntryEvicted(remove);
            }
        }
        this.mDelegate.add(e);
        return true;
    }

    @android.annotation.Nullable
    public E poll() {
        return this.mDelegate.poll();
    }

    public boolean remove(@android.annotation.NonNull E e) {
        java.util.Objects.requireNonNull(e);
        return this.mDelegate.remove(e);
    }

    public boolean isEmpty() {
        return this.mDelegate.isEmpty();
    }
}
