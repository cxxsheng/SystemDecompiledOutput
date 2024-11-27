package com.android.server.statusbar;

/* loaded from: classes2.dex */
public class TileRequestTracker {

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_NUM_DENIALS = 3;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArrayMap<android.content.ComponentName, java.lang.Integer> mTrackingMap = new android.util.SparseArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<android.content.ComponentName> mComponentsToRemove = new android.util.ArraySet<>();
    private final android.content.BroadcastReceiver mUninstallReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.statusbar.TileRequestTracker.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                return;
            }
            java.lang.String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
            if (!intent.hasExtra("android.intent.extra.UID")) {
                return;
            }
            int userId = android.os.UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1));
            synchronized (com.android.server.statusbar.TileRequestTracker.this.mLock) {
                try {
                    com.android.server.statusbar.TileRequestTracker.this.mComponentsToRemove.clear();
                    int numElementsForKey = com.android.server.statusbar.TileRequestTracker.this.mTrackingMap.numElementsForKey(userId);
                    int indexOfKey = com.android.server.statusbar.TileRequestTracker.this.mTrackingMap.indexOfKey(userId);
                    for (int i = 0; i < numElementsForKey; i++) {
                        android.content.ComponentName componentName = (android.content.ComponentName) com.android.server.statusbar.TileRequestTracker.this.mTrackingMap.keyAt(indexOfKey, i);
                        if (componentName.getPackageName().equals(encodedSchemeSpecificPart)) {
                            com.android.server.statusbar.TileRequestTracker.this.mComponentsToRemove.add(componentName);
                        }
                    }
                    int size = com.android.server.statusbar.TileRequestTracker.this.mComponentsToRemove.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        com.android.server.statusbar.TileRequestTracker.this.mTrackingMap.delete(userId, (android.content.ComponentName) com.android.server.statusbar.TileRequestTracker.this.mComponentsToRemove.valueAt(i2));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    TileRequestTracker(android.content.Context context) {
        this.mContext = context;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mUninstallReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    boolean shouldBeDenied(int i, android.content.ComponentName componentName) {
        boolean z;
        synchronized (this.mLock) {
            z = ((java.lang.Integer) this.mTrackingMap.getOrDefault(i, componentName, 0)).intValue() >= 3;
        }
        return z;
    }

    void addDenial(int i, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            this.mTrackingMap.add(i, componentName, java.lang.Integer.valueOf(((java.lang.Integer) this.mTrackingMap.getOrDefault(i, componentName, 0)).intValue() + 1));
        }
    }

    void resetRequests(int i, android.content.ComponentName componentName) {
        synchronized (this.mLock) {
            this.mTrackingMap.delete(i, componentName);
        }
    }

    void dump(java.io.FileDescriptor fileDescriptor, final android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr) {
        indentingPrintWriter.println("TileRequestTracker:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            this.mTrackingMap.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.statusbar.TileRequestTracker$$ExternalSyntheticLambda0
                public final void accept(int i, java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.statusbar.TileRequestTracker.lambda$dump$0(indentingPrintWriter, i, (android.content.ComponentName) obj, (java.lang.Integer) obj2);
                }
            });
        }
        indentingPrintWriter.decreaseIndent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$0(android.util.IndentingPrintWriter indentingPrintWriter, int i, android.content.ComponentName componentName, java.lang.Integer num) {
        indentingPrintWriter.println("user=" + i + ", " + componentName.toShortString() + ": " + num);
    }
}
