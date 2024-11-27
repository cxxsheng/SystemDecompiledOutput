package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ExtensionSessionStatsAggregator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = android.hardware.camera2.utils.ExtensionSessionStatsAggregator.class.getSimpleName();
    private final java.util.concurrent.ExecutorService mExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mIsDone = false;
    private final android.hardware.CameraExtensionSessionStats mStats = new android.hardware.CameraExtensionSessionStats();

    public ExtensionSessionStatsAggregator(java.lang.String str, boolean z) {
        this.mStats.key = "";
        this.mStats.cameraId = str;
        this.mStats.isAdvanced = z;
    }

    public void setClientName(java.lang.String str) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.clientName = str;
        }
    }

    public void setExtensionType(int i) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.type = i;
        }
    }

    public void commit(final boolean z) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.utils.ExtensionSessionStatsAggregator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.camera2.utils.ExtensionSessionStatsAggregator.this.lambda$commit$0(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$0(boolean z) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mIsDone = z;
            this.mStats.key = android.hardware.camera2.CameraManager.reportExtensionSessionStats(this.mStats);
        }
    }

    private static java.lang.String prettyPrintStats(android.hardware.CameraExtensionSessionStats cameraExtensionSessionStats) {
        return android.hardware.CameraExtensionSessionStats.class.getSimpleName() + ":\n  key: '" + cameraExtensionSessionStats.key + "'\n  cameraId: '" + cameraExtensionSessionStats.cameraId + "'\n  clientName: '" + cameraExtensionSessionStats.clientName + "'\n  type: '" + cameraExtensionSessionStats.type + "'\n  isAdvanced: '" + cameraExtensionSessionStats.isAdvanced + "'\n";
    }

    public java.lang.String getStatsKey() {
        return this.mStats.key;
    }
}
