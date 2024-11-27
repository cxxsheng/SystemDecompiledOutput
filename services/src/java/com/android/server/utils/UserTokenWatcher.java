package com.android.server.utils;

/* loaded from: classes2.dex */
public final class UserTokenWatcher {
    private final com.android.server.utils.UserTokenWatcher.Callback mCallback;
    private final android.os.Handler mHandler;
    private final java.lang.String mTag;

    @com.android.internal.annotations.GuardedBy({"mWatchers"})
    private final android.util.SparseArray<android.os.TokenWatcher> mWatchers = new android.util.SparseArray<>(1);

    public interface Callback {
        void acquired(int i);

        void released(int i);
    }

    public UserTokenWatcher(com.android.server.utils.UserTokenWatcher.Callback callback, android.os.Handler handler, java.lang.String str) {
        this.mCallback = callback;
        this.mHandler = handler;
        this.mTag = str;
    }

    public void acquire(android.os.IBinder iBinder, java.lang.String str, int i) {
        synchronized (this.mWatchers) {
            try {
                android.os.TokenWatcher tokenWatcher = this.mWatchers.get(i);
                if (tokenWatcher == null) {
                    tokenWatcher = new com.android.server.utils.UserTokenWatcher.InnerTokenWatcher(i, this.mHandler, this.mTag);
                    this.mWatchers.put(i, tokenWatcher);
                }
                tokenWatcher.acquire(iBinder, str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void release(android.os.IBinder iBinder, int i) {
        synchronized (this.mWatchers) {
            try {
                android.os.TokenWatcher tokenWatcher = this.mWatchers.get(i);
                if (tokenWatcher != null) {
                    tokenWatcher.release(iBinder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAcquired(int i) {
        boolean z;
        synchronized (this.mWatchers) {
            try {
                android.os.TokenWatcher tokenWatcher = this.mWatchers.get(i);
                z = tokenWatcher != null && tokenWatcher.isAcquired();
            } finally {
            }
        }
        return z;
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mWatchers) {
            for (int i = 0; i < this.mWatchers.size(); i++) {
                try {
                    int keyAt = this.mWatchers.keyAt(i);
                    android.os.TokenWatcher valueAt = this.mWatchers.valueAt(i);
                    if (valueAt.isAcquired()) {
                        printWriter.print("User ");
                        printWriter.print(keyAt);
                        printWriter.println(":");
                        valueAt.dump(new com.android.internal.util.IndentingPrintWriter(printWriter, " "));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class InnerTokenWatcher extends android.os.TokenWatcher {
        private final int mUserId;

        private InnerTokenWatcher(int i, android.os.Handler handler, java.lang.String str) {
            super(handler, str);
            this.mUserId = i;
        }

        @Override // android.os.TokenWatcher
        public void acquired() {
            com.android.server.utils.UserTokenWatcher.this.mCallback.acquired(this.mUserId);
        }

        @Override // android.os.TokenWatcher
        public void released() {
            com.android.server.utils.UserTokenWatcher.this.mCallback.released(this.mUserId);
            synchronized (com.android.server.utils.UserTokenWatcher.this.mWatchers) {
                try {
                    android.os.TokenWatcher tokenWatcher = (android.os.TokenWatcher) com.android.server.utils.UserTokenWatcher.this.mWatchers.get(this.mUserId);
                    if (tokenWatcher != null && !tokenWatcher.isAcquired()) {
                        com.android.server.utils.UserTokenWatcher.this.mWatchers.remove(this.mUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
