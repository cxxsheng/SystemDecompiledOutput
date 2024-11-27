package com.android.internal.widget;

/* loaded from: classes5.dex */
public class MessagingPool<T extends android.view.View> implements android.util.Pools.Pool<T> {
    private static final boolean ENABLED = false;
    private static final java.lang.String TAG = "MessagingPool";
    private android.util.Pools.SynchronizedPool<T> mCurrentPool;
    private final int mMaxPoolSize;

    public MessagingPool(int i) {
        this.mMaxPoolSize = i;
    }

    @Override // android.util.Pools.Pool
    public T acquire() {
        return null;
    }

    @Override // android.util.Pools.Pool
    public boolean release(T t) {
        if (t.getParent() == null) {
            return false;
        }
        android.util.Log.wtf(TAG, "releasing " + t + " with parent " + t.getParent());
        return false;
    }

    public void clear() {
    }
}
