package com.android.server.pm;

/* loaded from: classes2.dex */
final class IncrementalProgressListener extends android.content.pm.IPackageLoadingProgressCallback.Stub {
    private final java.lang.String mPackageName;
    private final com.android.server.pm.PackageManagerService mPm;

    IncrementalProgressListener(java.lang.String str, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPackageName = str;
        this.mPm = packageManagerService;
    }

    public void onPackageLoadingProgressChanged(final float f) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(this.mPackageName);
        if (packageStateInternal != null && packageStateInternal.isLoading()) {
            this.mPm.commitPackageStateMutation(null, this.mPackageName, new java.util.function.Consumer() { // from class: com.android.server.pm.IncrementalProgressListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.pm.pkg.mutate.PackageStateWrite) obj).setLoadingProgress(f);
                }
            });
            if (java.lang.Math.abs(1.0f - f) < 1.0E-8f) {
                this.mPm.commitPackageStateMutation(null, this.mPackageName, new java.util.function.Consumer() { // from class: com.android.server.pm.IncrementalProgressListener$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.IncrementalProgressListener.lambda$onPackageLoadingProgressChanged$1((com.android.server.pm.pkg.mutate.PackageStateWrite) obj);
                    }
                });
                this.mPm.mIncrementalManager.unregisterLoadingProgressCallbacks(packageStateInternal.getPathString());
                this.mPm.scheduleWriteSettings();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPackageLoadingProgressChanged$1(com.android.server.pm.pkg.mutate.PackageStateWrite packageStateWrite) {
        packageStateWrite.setLoadingCompletedTime(java.lang.System.currentTimeMillis());
    }
}
