package com.android.server.uri;

/* loaded from: classes2.dex */
final class UriMetricsHelper {
    private static final android.app.StatsManager.PullAtomMetadata DAILY_PULL_METADATA = new android.app.StatsManager.PullAtomMetadata.Builder().setCoolDownMillis(java.util.concurrent.TimeUnit.DAYS.toMillis(1)).build();
    private final android.content.Context mContext;
    private final com.android.server.uri.UriMetricsHelper.PersistentUriGrantsProvider mPersistentUriGrantsProvider;

    interface PersistentUriGrantsProvider {
        java.util.ArrayList<com.android.server.uri.UriPermission> providePersistentUriGrants();
    }

    UriMetricsHelper(android.content.Context context, com.android.server.uri.UriMetricsHelper.PersistentUriGrantsProvider persistentUriGrantsProvider) {
        this.mContext = context;
        this.mPersistentUriGrantsProvider = persistentUriGrantsProvider;
    }

    void registerPuller() {
        ((android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class)).setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, DAILY_PULL_METADATA, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.uri.UriMetricsHelper$$ExternalSyntheticLambda0
            public final int onPullAtom(int i, java.util.List list) {
                int lambda$registerPuller$0;
                lambda$registerPuller$0 = com.android.server.uri.UriMetricsHelper.this.lambda$registerPuller$0(i, list);
                return lambda$registerPuller$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$registerPuller$0(int i, java.util.List list) {
        reportPersistentUriPermissionsPerPackage(list);
        return 0;
    }

    void reportPersistentUriFlushed(int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_FLUSHED, i);
    }

    private void reportPersistentUriPermissionsPerPackage(java.util.List<android.util.StatsEvent> list) {
        java.util.ArrayList<com.android.server.uri.UriPermission> providePersistentUriGrants = this.mPersistentUriGrantsProvider.providePersistentUriGrants();
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        int size = providePersistentUriGrants.size();
        for (int i = 0; i < size; i++) {
            com.android.server.uri.UriPermission uriPermission = providePersistentUriGrants.get(i);
            sparseArray.put(uriPermission.targetUid, java.lang.Integer.valueOf(((java.lang.Integer) sparseArray.get(uriPermission.targetUid, 0)).intValue() + 1));
        }
        int size2 = sparseArray.size();
        for (int i2 = 0; i2 < size2; i2++) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.PERSISTENT_URI_PERMISSIONS_AMOUNT_PER_PACKAGE, sparseArray.keyAt(i2), ((java.lang.Integer) sparseArray.valueAt(i2)).intValue()));
        }
    }
}
