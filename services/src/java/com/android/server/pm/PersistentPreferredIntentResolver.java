package com.android.server.pm;

/* loaded from: classes2.dex */
public class PersistentPreferredIntentResolver extends com.android.server.pm.WatchedIntentResolver<com.android.server.pm.PersistentPreferredActivity, com.android.server.pm.PersistentPreferredActivity> implements com.android.server.utils.Snappable {
    final com.android.server.utils.SnapshotCache<com.android.server.pm.PersistentPreferredIntentResolver> mSnapshot;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public com.android.server.pm.PersistentPreferredActivity[] newArray(int i) {
        return new com.android.server.pm.PersistentPreferredActivity[i];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity) {
        return persistentPreferredActivity.getIntentFilter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public boolean isPackageForFilter(java.lang.String str, com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity) {
        return str.equals(persistentPreferredActivity.mComponent.getPackageName());
    }

    public PersistentPreferredIntentResolver() {
        this.mSnapshot = makeCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public com.android.server.pm.PersistentPreferredActivity snapshot(com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity) {
        if (persistentPreferredActivity == null) {
            return null;
        }
        return persistentPreferredActivity.snapshot();
    }

    private PersistentPreferredIntentResolver(com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredIntentResolver) {
        copyFrom((com.android.server.pm.WatchedIntentResolver) persistentPreferredIntentResolver);
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    private com.android.server.utils.SnapshotCache makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.PersistentPreferredIntentResolver>(this, this) { // from class: com.android.server.pm.PersistentPreferredIntentResolver.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.PersistentPreferredIntentResolver createSnapshot() {
                return new com.android.server.pm.PersistentPreferredIntentResolver();
            }
        };
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.PersistentPreferredIntentResolver snapshot() {
        return this.mSnapshot.snapshot();
    }
}
