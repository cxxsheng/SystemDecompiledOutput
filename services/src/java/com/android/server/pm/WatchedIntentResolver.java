package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class WatchedIntentResolver<F extends com.android.server.pm.WatchedIntentFilter, R extends com.android.server.pm.WatchedIntentFilter> extends com.android.server.IntentResolver<F, R> implements com.android.server.utils.Watchable, com.android.server.utils.Snappable {
    private static final java.util.Comparator<com.android.server.pm.WatchedIntentFilter> sResolvePrioritySorter = new java.util.Comparator<com.android.server.pm.WatchedIntentFilter>() { // from class: com.android.server.pm.WatchedIntentResolver.2
        @Override // java.util.Comparator
        public int compare(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, com.android.server.pm.WatchedIntentFilter watchedIntentFilter2) {
            int priority = watchedIntentFilter.getPriority();
            int priority2 = watchedIntentFilter2.getPriority();
            if (priority > priority2) {
                return -1;
            }
            return priority < priority2 ? 1 : 0;
        }
    };
    private final com.android.server.utils.Watchable mWatchable = new com.android.server.utils.WatchableImpl();
    private final com.android.server.utils.Watcher mWatcher = new com.android.server.utils.Watcher() { // from class: com.android.server.pm.WatchedIntentResolver.1
        @Override // com.android.server.utils.Watcher
        public void onChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
            com.android.server.pm.WatchedIntentResolver.this.dispatchChange(watchable);
        }
    };

    @Override // com.android.server.utils.Watchable
    public void registerObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(@android.annotation.NonNull com.android.server.utils.Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(@android.annotation.Nullable com.android.server.utils.Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    protected void onChanged() {
        dispatchChange(this);
    }

    @Override // com.android.server.IntentResolver
    public void addFilter(@android.annotation.Nullable com.android.server.pm.snapshot.PackageDataSnapshot packageDataSnapshot, F f) {
        super.addFilter(packageDataSnapshot, (com.android.server.pm.snapshot.PackageDataSnapshot) f);
        f.registerObserver(this.mWatcher);
        onChanged();
    }

    @Override // com.android.server.IntentResolver
    public void removeFilter(F f) {
        f.unregisterObserver(this.mWatcher);
        super.removeFilter((com.android.server.pm.WatchedIntentResolver<F, R>) f);
        onChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public void removeFilterInternal(F f) {
        f.unregisterObserver(this.mWatcher);
        super.removeFilterInternal((com.android.server.pm.WatchedIntentResolver<F, R>) f);
        onChanged();
    }

    @Override // com.android.server.IntentResolver
    protected void sortResults(java.util.List<R> list) {
        java.util.Collections.sort(list, sResolvePrioritySorter);
    }

    public java.util.ArrayList<F> findFilters(com.android.server.pm.WatchedIntentFilter watchedIntentFilter) {
        return super.findFilters(watchedIntentFilter.getIntentFilter());
    }

    protected void copyFrom(com.android.server.pm.WatchedIntentResolver watchedIntentResolver) {
        super.copyFrom((com.android.server.IntentResolver) watchedIntentResolver);
    }
}
