package com.android.internal.util;

/* loaded from: classes5.dex */
public class CallbackRegistry<C, T, A> implements java.lang.Cloneable {
    private static final java.lang.String TAG = "CallbackRegistry";
    private java.util.List<C> mCallbacks = new java.util.ArrayList();
    private long mFirst64Removed = 0;
    private int mNotificationLevel;
    private final com.android.internal.util.CallbackRegistry.NotifierCallback<C, T, A> mNotifier;
    private long[] mRemainderRemoved;

    public static abstract class NotifierCallback<C, T, A> {
        public abstract void onNotifyCallback(C c, T t, int i, A a);
    }

    public CallbackRegistry(com.android.internal.util.CallbackRegistry.NotifierCallback<C, T, A> notifierCallback) {
        this.mNotifier = notifierCallback;
    }

    public synchronized void notifyCallbacks(T t, int i, A a) {
        this.mNotificationLevel++;
        notifyRecurseLocked(t, i, a);
        this.mNotificationLevel--;
        if (this.mNotificationLevel == 0) {
            if (this.mRemainderRemoved != null) {
                for (int length = this.mRemainderRemoved.length - 1; length >= 0; length--) {
                    long j = this.mRemainderRemoved[length];
                    if (j != 0) {
                        removeRemovedCallbacks((length + 1) * 64, j);
                        this.mRemainderRemoved[length] = 0;
                    }
                }
            }
            if (this.mFirst64Removed != 0) {
                removeRemovedCallbacks(0, this.mFirst64Removed);
                this.mFirst64Removed = 0L;
            }
        }
    }

    private void notifyFirst64Locked(T t, int i, A a) {
        notifyCallbacksLocked(t, i, a, 0, java.lang.Math.min(64, this.mCallbacks.size()), this.mFirst64Removed);
    }

    private void notifyRecurseLocked(T t, int i, A a) {
        int size = this.mCallbacks.size();
        int length = this.mRemainderRemoved == null ? -1 : this.mRemainderRemoved.length - 1;
        notifyRemainderLocked(t, i, a, length);
        notifyCallbacksLocked(t, i, a, (length + 2) * 64, size, 0L);
    }

    private void notifyRemainderLocked(T t, int i, A a, int i2) {
        if (i2 < 0) {
            notifyFirst64Locked(t, i, a);
            return;
        }
        long j = this.mRemainderRemoved[i2];
        int i3 = (i2 + 1) * 64;
        int min = java.lang.Math.min(this.mCallbacks.size(), i3 + 64);
        notifyRemainderLocked(t, i, a, i2 - 1);
        notifyCallbacksLocked(t, i, a, i3, min, j);
    }

    private void notifyCallbacksLocked(T t, int i, A a, int i2, int i3, long j) {
        long j2 = 1;
        while (i2 < i3) {
            if ((j & j2) == 0) {
                this.mNotifier.onNotifyCallback(this.mCallbacks.get(i2), t, i, a);
            }
            j2 <<= 1;
            i2++;
        }
    }

    public synchronized void add(C c) {
        int lastIndexOf = this.mCallbacks.lastIndexOf(c);
        if (lastIndexOf < 0 || isRemovedLocked(lastIndexOf)) {
            this.mCallbacks.add(c);
        }
    }

    private boolean isRemovedLocked(int i) {
        int i2;
        if (i < 64) {
            return ((1 << i) & this.mFirst64Removed) != 0;
        }
        if (this.mRemainderRemoved != null && (i2 = (i / 64) - 1) < this.mRemainderRemoved.length) {
            return ((1 << (i % 64)) & this.mRemainderRemoved[i2]) != 0;
        }
        return false;
    }

    private void removeRemovedCallbacks(int i, long j) {
        long j2 = Long.MIN_VALUE;
        for (int i2 = (i + 64) - 1; i2 >= i; i2--) {
            if ((j & j2) != 0) {
                this.mCallbacks.remove(i2);
            }
            j2 >>>= 1;
        }
    }

    public synchronized void remove(C c) {
        if (this.mNotificationLevel == 0) {
            this.mCallbacks.remove(c);
        } else {
            int lastIndexOf = this.mCallbacks.lastIndexOf(c);
            if (lastIndexOf >= 0) {
                setRemovalBitLocked(lastIndexOf);
            }
        }
    }

    private void setRemovalBitLocked(int i) {
        if (i < 64) {
            this.mFirst64Removed = (1 << i) | this.mFirst64Removed;
            return;
        }
        int i2 = (i / 64) - 1;
        if (this.mRemainderRemoved == null) {
            this.mRemainderRemoved = new long[this.mCallbacks.size() / 64];
        } else if (this.mRemainderRemoved.length < i2) {
            long[] jArr = new long[this.mCallbacks.size() / 64];
            java.lang.System.arraycopy(this.mRemainderRemoved, 0, jArr, 0, this.mRemainderRemoved.length);
            this.mRemainderRemoved = jArr;
        }
        long j = 1 << (i % 64);
        long[] jArr2 = this.mRemainderRemoved;
        jArr2[i2] = j | jArr2[i2];
    }

    public synchronized java.util.ArrayList<C> copyListeners() {
        java.util.ArrayList<C> arrayList;
        arrayList = new java.util.ArrayList<>(this.mCallbacks.size());
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (!isRemovedLocked(i)) {
                arrayList.add(this.mCallbacks.get(i));
            }
        }
        return arrayList;
    }

    public synchronized boolean isEmpty() {
        if (this.mCallbacks.isEmpty()) {
            return true;
        }
        if (this.mNotificationLevel == 0) {
            return false;
        }
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (!isRemovedLocked(i)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void clear() {
        if (this.mNotificationLevel == 0) {
            this.mCallbacks.clear();
        } else if (!this.mCallbacks.isEmpty()) {
            for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
                setRemovalBitLocked(size);
            }
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public synchronized com.android.internal.util.CallbackRegistry<C, T, A> m7067clone() {
        com.android.internal.util.CallbackRegistry<C, T, A> callbackRegistry;
        java.lang.CloneNotSupportedException e;
        try {
            callbackRegistry = (com.android.internal.util.CallbackRegistry) super.clone();
            try {
                callbackRegistry.mFirst64Removed = 0L;
                callbackRegistry.mRemainderRemoved = null;
                callbackRegistry.mNotificationLevel = 0;
                callbackRegistry.mCallbacks = new java.util.ArrayList();
                int size = this.mCallbacks.size();
                for (int i = 0; i < size; i++) {
                    if (!isRemovedLocked(i)) {
                        callbackRegistry.mCallbacks.add(this.mCallbacks.get(i));
                    }
                }
            } catch (java.lang.CloneNotSupportedException e2) {
                e = e2;
                e.printStackTrace();
                return callbackRegistry;
            }
        } catch (java.lang.CloneNotSupportedException e3) {
            callbackRegistry = null;
            e = e3;
        }
        return callbackRegistry;
    }
}
