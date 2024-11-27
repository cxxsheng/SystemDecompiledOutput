package com.android.server.utils;

/* loaded from: classes2.dex */
public class WatchableImpl implements com.android.server.utils.Watchable {
    protected final java.util.ArrayList<com.android.server.utils.Watcher> mObservers = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mObservers"})
    private boolean mSealed = false;

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        java.util.Objects.requireNonNull(watcher, "observer may not be null");
        synchronized (this.mObservers) {
            try {
                if (!this.mObservers.contains(watcher)) {
                    this.mObservers.add(watcher);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        java.util.Objects.requireNonNull(watcher, "observer may not be null");
        synchronized (this.mObservers) {
            this.mObservers.remove(watcher);
        }
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        boolean contains;
        synchronized (this.mObservers) {
            contains = this.mObservers.contains(watcher);
        }
        return contains;
    }

    public int registeredObserverCount() {
        return this.mObservers.size();
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        synchronized (this.mObservers) {
            try {
                if (this.mSealed) {
                    throw new java.lang.IllegalStateException("attempt to change a sealed object");
                }
                int size = this.mObservers.size();
                for (int i = 0; i < size; i++) {
                    this.mObservers.get(i).onChange(watchable);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void seal() {
        synchronized (this.mObservers) {
            this.mSealed = true;
        }
    }

    public boolean isSealed() {
        boolean z;
        synchronized (this.mObservers) {
            z = this.mSealed;
        }
        return z;
    }
}
