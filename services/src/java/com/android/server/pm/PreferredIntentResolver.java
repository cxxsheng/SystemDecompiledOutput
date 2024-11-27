package com.android.server.pm;

/* loaded from: classes2.dex */
public class PreferredIntentResolver extends com.android.server.pm.WatchedIntentResolver<com.android.server.pm.PreferredActivity, com.android.server.pm.PreferredActivity> implements com.android.server.utils.Snappable {
    final com.android.server.utils.SnapshotCache<com.android.server.pm.PreferredIntentResolver> mSnapshot;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public com.android.server.pm.PreferredActivity[] newArray(int i) {
        return new com.android.server.pm.PreferredActivity[i];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public boolean isPackageForFilter(java.lang.String str, com.android.server.pm.PreferredActivity preferredActivity) {
        return str.equals(preferredActivity.mPref.mComponent.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public void dumpFilter(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.pm.PreferredActivity preferredActivity) {
        preferredActivity.mPref.dump(printWriter, str, preferredActivity);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull com.android.server.pm.PreferredActivity preferredActivity) {
        return preferredActivity.getIntentFilter();
    }

    public boolean shouldAddPreferredActivity(com.android.server.pm.PreferredActivity preferredActivity) {
        java.util.ArrayList<com.android.server.pm.PreferredActivity> findFilters = findFilters(preferredActivity);
        if (findFilters == null || findFilters.isEmpty()) {
            return true;
        }
        if (!preferredActivity.mPref.mAlways) {
            return false;
        }
        int size = findFilters.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.PreferredActivity preferredActivity2 = findFilters.get(i);
            if (preferredActivity2.mPref.mAlways && preferredActivity2.mPref.mMatch == (preferredActivity.mPref.mMatch & 268369920) && preferredActivity2.mPref.sameSet(preferredActivity.mPref)) {
                return false;
            }
        }
        return true;
    }

    public PreferredIntentResolver() {
        this.mSnapshot = makeCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.IntentResolver
    public com.android.server.pm.PreferredActivity snapshot(com.android.server.pm.PreferredActivity preferredActivity) {
        if (preferredActivity == null) {
            return null;
        }
        return preferredActivity.snapshot();
    }

    private PreferredIntentResolver(com.android.server.pm.PreferredIntentResolver preferredIntentResolver) {
        copyFrom((com.android.server.pm.WatchedIntentResolver) preferredIntentResolver);
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    private com.android.server.utils.SnapshotCache makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.PreferredIntentResolver>(this, this) { // from class: com.android.server.pm.PreferredIntentResolver.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.PreferredIntentResolver createSnapshot() {
                return new com.android.server.pm.PreferredIntentResolver();
            }
        };
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.PreferredIntentResolver snapshot() {
        return this.mSnapshot.snapshot();
    }
}
