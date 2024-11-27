package android.os;

/* loaded from: classes3.dex */
public class RemoteCallbackList<E extends android.os.IInterface> {
    private static final java.lang.String TAG = "RemoteCallbackList";
    private java.lang.Object[] mActiveBroadcast;
    private java.lang.StringBuilder mRecentCallers;
    android.util.ArrayMap<android.os.IBinder, android.os.RemoteCallbackList<E>.Callback> mCallbacks = new android.util.ArrayMap<>();
    private int mBroadcastCount = -1;
    private boolean mKilled = false;

    private final class Callback implements android.os.IBinder.DeathRecipient {
        final E mCallback;
        final java.lang.Object mCookie;

        Callback(E e, java.lang.Object obj) {
            this.mCallback = e;
            this.mCookie = obj;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (android.os.RemoteCallbackList.this.mCallbacks) {
                android.os.RemoteCallbackList.this.mCallbacks.remove(this.mCallback.asBinder());
            }
            android.os.RemoteCallbackList.this.onCallbackDied(this.mCallback, this.mCookie);
        }
    }

    public boolean register(E e) {
        return register(e, null);
    }

    public boolean register(E e, java.lang.Object obj) {
        synchronized (this.mCallbacks) {
            if (this.mKilled) {
                return false;
            }
            logExcessiveCallbacks();
            android.os.IBinder asBinder = e.asBinder();
            try {
                android.os.RemoteCallbackList<E>.Callback callback = new android.os.RemoteCallbackList.Callback(e, obj);
                unregister(e);
                asBinder.linkToDeath(callback, 0);
                this.mCallbacks.put(asBinder, callback);
                return true;
            } catch (android.os.RemoteException e2) {
                return false;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [E extends android.os.IInterface, android.os.IInterface] */
    public boolean unregister(E e) {
        synchronized (this.mCallbacks) {
            android.os.RemoteCallbackList<E>.Callback remove = this.mCallbacks.remove(e.asBinder());
            if (remove != null) {
                remove.mCallback.asBinder().unlinkToDeath(remove, 0);
                return true;
            }
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [E extends android.os.IInterface, android.os.IInterface] */
    public void kill() {
        synchronized (this.mCallbacks) {
            for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
                android.os.RemoteCallbackList<E>.Callback valueAt = this.mCallbacks.valueAt(size);
                valueAt.mCallback.asBinder().unlinkToDeath(valueAt, 0);
            }
            this.mCallbacks.clear();
            this.mKilled = true;
        }
    }

    public void onCallbackDied(E e) {
    }

    public void onCallbackDied(E e, java.lang.Object obj) {
        onCallbackDied(e);
    }

    public int beginBroadcast() {
        synchronized (this.mCallbacks) {
            if (this.mBroadcastCount > 0) {
                throw new java.lang.IllegalStateException("beginBroadcast() called while already in a broadcast");
            }
            int size = this.mCallbacks.size();
            this.mBroadcastCount = size;
            if (size <= 0) {
                return 0;
            }
            java.lang.Object[] objArr = this.mActiveBroadcast;
            if (objArr == null || objArr.length < size) {
                objArr = new java.lang.Object[size];
                this.mActiveBroadcast = objArr;
            }
            for (int i = 0; i < size; i++) {
                objArr[i] = this.mCallbacks.valueAt(i);
            }
            return size;
        }
    }

    public E getBroadcastItem(int i) {
        return ((android.os.RemoteCallbackList.Callback) this.mActiveBroadcast[i]).mCallback;
    }

    public java.lang.Object getBroadcastCookie(int i) {
        return ((android.os.RemoteCallbackList.Callback) this.mActiveBroadcast[i]).mCookie;
    }

    public void finishBroadcast() {
        synchronized (this.mCallbacks) {
            if (this.mBroadcastCount < 0) {
                throw new java.lang.IllegalStateException("finishBroadcast() called outside of a broadcast");
            }
            java.lang.Object[] objArr = this.mActiveBroadcast;
            if (objArr != null) {
                int i = this.mBroadcastCount;
                for (int i2 = 0; i2 < i; i2++) {
                    objArr[i2] = null;
                }
            }
            this.mBroadcastCount = -1;
        }
    }

    public void broadcast(java.util.function.Consumer<E> consumer) {
        int beginBroadcast = beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastItem(i));
            } finally {
                finishBroadcast();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <C> void broadcastForEachCookie(java.util.function.Consumer<C> consumer) {
        int beginBroadcast = beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                consumer.accept(getBroadcastCookie(i));
            } finally {
                finishBroadcast();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <C> void broadcast(java.util.function.BiConsumer<E, C> biConsumer) {
        int beginBroadcast = beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                biConsumer.accept(getBroadcastItem(i), getBroadcastCookie(i));
            } finally {
                finishBroadcast();
            }
        }
    }

    public int getRegisteredCallbackCount() {
        synchronized (this.mCallbacks) {
            if (this.mKilled) {
                return 0;
            }
            return this.mCallbacks.size();
        }
    }

    public E getRegisteredCallbackItem(int i) {
        synchronized (this.mCallbacks) {
            if (this.mKilled) {
                return null;
            }
            return (E) this.mCallbacks.valueAt(i).mCallback;
        }
    }

    public java.lang.Object getRegisteredCallbackCookie(int i) {
        synchronized (this.mCallbacks) {
            if (this.mKilled) {
                return null;
            }
            return this.mCallbacks.valueAt(i).mCookie;
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (this.mCallbacks) {
            printWriter.print(str);
            printWriter.print("callbacks: ");
            printWriter.println(this.mCallbacks.size());
            printWriter.print(str);
            printWriter.print("killed: ");
            printWriter.println(this.mKilled);
            printWriter.print(str);
            printWriter.print("broadcasts count: ");
            printWriter.println(this.mBroadcastCount);
        }
    }

    private void logExcessiveCallbacks() {
        long size = this.mCallbacks.size();
        if (size >= 3000) {
            if (size == 3000 && this.mRecentCallers == null) {
                this.mRecentCallers = new java.lang.StringBuilder();
            }
            if (this.mRecentCallers != null && this.mRecentCallers.length() < 1000) {
                this.mRecentCallers.append(android.os.Debug.getCallers(5));
                this.mRecentCallers.append('\n');
                if (this.mRecentCallers.length() >= 1000) {
                    android.util.Slog.wtf(TAG, "More than 3000 remote callbacks registered. Recent callers:\n" + this.mRecentCallers.toString());
                    this.mRecentCallers = null;
                }
            }
        }
    }
}
