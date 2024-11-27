package android.content.res;

/* loaded from: classes.dex */
abstract class ThemedResourceCache<T> {
    public static final int UNDEFINED_GENERATION = -1;
    private int mGeneration;
    private android.util.LongSparseArray<java.lang.ref.WeakReference<T>> mNullThemedEntries;
    private android.util.ArrayMap<android.content.res.Resources.ThemeKey, android.util.LongSparseArray<java.lang.ref.WeakReference<T>>> mThemedEntries;
    private android.util.LongSparseArray<java.lang.ref.WeakReference<T>> mUnthemedEntries;

    protected abstract boolean shouldInvalidateEntry(T t, int i);

    ThemedResourceCache() {
    }

    public void put(long j, android.content.res.Resources.Theme theme, T t, int i) {
        put(j, theme, t, i, true);
    }

    public void put(long j, android.content.res.Resources.Theme theme, T t, int i, boolean z) {
        android.util.LongSparseArray<java.lang.ref.WeakReference<T>> themedLocked;
        if (t == null) {
            return;
        }
        synchronized (this) {
            if (!z) {
                themedLocked = getUnthemedLocked(true);
            } else {
                themedLocked = getThemedLocked(theme, true);
            }
            if (themedLocked != null && (i == this.mGeneration || i == -1)) {
                themedLocked.put(j, new java.lang.ref.WeakReference<>(t));
            }
        }
    }

    public int getGeneration() {
        return this.mGeneration;
    }

    public T get(long j, android.content.res.Resources.Theme theme) {
        java.lang.ref.WeakReference<T> weakReference;
        java.lang.ref.WeakReference<T> weakReference2;
        synchronized (this) {
            android.util.LongSparseArray<java.lang.ref.WeakReference<T>> themedLocked = getThemedLocked(theme, false);
            if (themedLocked != null && (weakReference2 = themedLocked.get(j)) != null) {
                return weakReference2.get();
            }
            android.util.LongSparseArray<java.lang.ref.WeakReference<T>> unthemedLocked = getUnthemedLocked(false);
            if (unthemedLocked != null && (weakReference = unthemedLocked.get(j)) != null) {
                return weakReference.get();
            }
            return null;
        }
    }

    public void onConfigurationChange(int i) {
        synchronized (this) {
            pruneLocked(i);
            this.mGeneration++;
        }
    }

    private android.util.LongSparseArray<java.lang.ref.WeakReference<T>> getThemedLocked(android.content.res.Resources.Theme theme, boolean z) {
        if (theme == null) {
            if (this.mNullThemedEntries == null && z) {
                this.mNullThemedEntries = new android.util.LongSparseArray<>(1);
            }
            return this.mNullThemedEntries;
        }
        if (this.mThemedEntries == null) {
            if (z) {
                this.mThemedEntries = new android.util.ArrayMap<>(1);
            } else {
                return null;
            }
        }
        android.content.res.Resources.ThemeKey key = theme.getKey();
        android.util.LongSparseArray<java.lang.ref.WeakReference<T>> longSparseArray = this.mThemedEntries.get(key);
        if (longSparseArray == null && z) {
            android.util.LongSparseArray<java.lang.ref.WeakReference<T>> longSparseArray2 = new android.util.LongSparseArray<>(1);
            this.mThemedEntries.put(key.m926clone(), longSparseArray2);
            return longSparseArray2;
        }
        return longSparseArray;
    }

    private android.util.LongSparseArray<java.lang.ref.WeakReference<T>> getUnthemedLocked(boolean z) {
        if (this.mUnthemedEntries == null && z) {
            this.mUnthemedEntries = new android.util.LongSparseArray<>(1);
        }
        return this.mUnthemedEntries;
    }

    private boolean pruneLocked(int i) {
        if (this.mThemedEntries != null) {
            for (int size = this.mThemedEntries.size() - 1; size >= 0; size--) {
                if (pruneEntriesLocked(this.mThemedEntries.valueAt(size), i)) {
                    this.mThemedEntries.removeAt(size);
                }
            }
        }
        pruneEntriesLocked(this.mNullThemedEntries, i);
        pruneEntriesLocked(this.mUnthemedEntries, i);
        return this.mThemedEntries == null && this.mNullThemedEntries == null && this.mUnthemedEntries == null;
    }

    private boolean pruneEntriesLocked(android.util.LongSparseArray<java.lang.ref.WeakReference<T>> longSparseArray, int i) {
        if (longSparseArray == null) {
            return true;
        }
        for (int size = longSparseArray.size() - 1; size >= 0; size--) {
            java.lang.ref.WeakReference<T> valueAt = longSparseArray.valueAt(size);
            if (valueAt == null || pruneEntryLocked(valueAt.get(), i)) {
                longSparseArray.removeAt(size);
            }
        }
        return longSparseArray.size() == 0;
    }

    private boolean pruneEntryLocked(T t, int i) {
        return t == null || (i != 0 && shouldInvalidateEntry(t, i));
    }

    public synchronized void clear() {
        if (this.mThemedEntries != null) {
            this.mThemedEntries.clear();
        }
        if (this.mUnthemedEntries != null) {
            this.mUnthemedEntries.clear();
        }
        if (this.mNullThemedEntries != null) {
            this.mNullThemedEntries.clear();
        }
    }
}
