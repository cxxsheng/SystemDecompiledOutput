package android.content.pm.dex;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class ArtManager {
    public static final int PROFILE_APPS = 0;
    public static final int PROFILE_BOOT_IMAGE = 1;
    public static final int SNAPSHOT_FAILED_CODE_PATH_NOT_FOUND = 1;
    public static final int SNAPSHOT_FAILED_INTERNAL_ERROR = 2;
    public static final int SNAPSHOT_FAILED_PACKAGE_NOT_FOUND = 0;
    private static final java.lang.String TAG = "ArtManager";
    private final android.content.pm.dex.IArtManager mArtManager;
    private final android.content.Context mContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProfileType {
    }

    public static abstract class SnapshotRuntimeProfileCallback {
        public abstract void onError(int i);

        public abstract void onSuccess(android.os.ParcelFileDescriptor parcelFileDescriptor);
    }

    public ArtManager(android.content.Context context, android.content.pm.dex.IArtManager iArtManager) {
        this.mContext = context;
        this.mArtManager = iArtManager;
    }

    public void snapshotRuntimeProfile(int i, java.lang.String str, java.lang.String str2, java.util.concurrent.Executor executor, android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback) {
        android.util.Slog.d(TAG, "Requesting profile snapshot for " + str + ":" + str2);
        try {
            this.mArtManager.snapshotRuntimeProfile(i, str, str2, new android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallbackDelegate(snapshotRuntimeProfileCallback, executor), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isRuntimeProfilingEnabled(int i) {
        try {
            return this.mArtManager.isRuntimeProfilingEnabled(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SnapshotRuntimeProfileCallbackDelegate extends android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub {
        private final android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        private SnapshotRuntimeProfileCallbackDelegate(android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback, java.util.concurrent.Executor executor) {
            this.mCallback = snapshotRuntimeProfileCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mCallback.onSuccess(parcelFileDescriptor);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(final android.os.ParcelFileDescriptor parcelFileDescriptor) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.lambda$onSuccess$0(parcelFileDescriptor);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.content.pm.dex.ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.lambda$onError$1(i);
                }
            });
        }
    }

    public static java.lang.String getProfileName(java.lang.String str) {
        return str == null ? "primary.prof" : str + ".split.prof";
    }

    public static java.lang.String getCurrentProfilePath(java.lang.String str, int i, java.lang.String str2) {
        return new java.io.File(android.os.Environment.getDataProfilesDePackageDirectory(i, str), getProfileName(str2)).getAbsolutePath();
    }

    public static java.lang.String getReferenceProfilePath(java.lang.String str, int i, java.lang.String str2) {
        return new java.io.File(android.os.Environment.getDataRefProfilesDePackageDirectory(str), getProfileName(str2)).getAbsolutePath();
    }

    public static java.io.File getProfileSnapshotFileForName(java.lang.String str, java.lang.String str2) {
        return new java.io.File(android.os.Environment.getDataRefProfilesDePackageDirectory(str), str2 + ".snapshot");
    }
}
