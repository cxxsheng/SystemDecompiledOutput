package com.android.server.am;

/* loaded from: classes.dex */
public class FgsTempAllowList<E> {
    private static final int DEFAULT_MAX_SIZE = 100;
    private final java.lang.Object mLock;
    private int mMaxSize;
    private final android.util.SparseArray<android.util.Pair<java.lang.Long, E>> mTempAllowList;

    public FgsTempAllowList() {
        this.mTempAllowList = new android.util.SparseArray<>();
        this.mMaxSize = 100;
        this.mLock = new java.lang.Object();
    }

    public FgsTempAllowList(int i) {
        this.mTempAllowList = new android.util.SparseArray<>();
        this.mMaxSize = 100;
        this.mLock = new java.lang.Object();
        if (i <= 0) {
            android.util.Slog.e("ActivityManager", "Invalid FgsTempAllowList maxSize:" + i + ", force default maxSize:100");
            this.mMaxSize = 100;
            return;
        }
        this.mMaxSize = i;
    }

    public void add(int i, long j, @android.annotation.Nullable E e) {
        synchronized (this.mLock) {
            try {
                if (j <= 0) {
                    android.util.Slog.e("ActivityManager", "FgsTempAllowList bad duration:" + j + " key: " + i);
                    return;
                }
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                int size = this.mTempAllowList.size();
                if (size > this.mMaxSize) {
                    android.util.Slog.w("ActivityManager", "FgsTempAllowList length:" + size + " exceeds maxSize" + this.mMaxSize);
                    for (int i2 = size + (-1); i2 >= 0; i2--) {
                        if (((java.lang.Long) this.mTempAllowList.valueAt(i2).first).longValue() < elapsedRealtime) {
                            this.mTempAllowList.removeAt(i2);
                        }
                    }
                }
                android.util.Pair<java.lang.Long, E> pair = this.mTempAllowList.get(i);
                long j2 = elapsedRealtime + j;
                if (pair == null || ((java.lang.Long) pair.first).longValue() < j2) {
                    this.mTempAllowList.put(i, new android.util.Pair<>(java.lang.Long.valueOf(j2), e));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.util.Pair<java.lang.Long, E> get(int i) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mTempAllowList.indexOfKey(i);
                if (indexOfKey < 0) {
                    return null;
                }
                if (((java.lang.Long) this.mTempAllowList.valueAt(indexOfKey).first).longValue() < android.os.SystemClock.elapsedRealtime()) {
                    this.mTempAllowList.removeAt(indexOfKey);
                    return null;
                }
                return this.mTempAllowList.valueAt(indexOfKey);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAllowed(int i) {
        return get(i) != null;
    }

    public void removeUid(int i) {
        synchronized (this.mLock) {
            this.mTempAllowList.remove(i);
        }
    }

    public void removeAppId(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mTempAllowList.size() - 1; size >= 0; size--) {
                    if (android.os.UserHandle.getAppId(this.mTempAllowList.keyAt(size)) == i) {
                        this.mTempAllowList.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void forEach(java.util.function.BiConsumer<java.lang.Integer, android.util.Pair<java.lang.Long, E>> biConsumer) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mTempAllowList.size(); i++) {
                try {
                    int keyAt = this.mTempAllowList.keyAt(i);
                    android.util.Pair<java.lang.Long, E> valueAt = this.mTempAllowList.valueAt(i);
                    if (valueAt != null) {
                        biConsumer.accept(java.lang.Integer.valueOf(keyAt), valueAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
