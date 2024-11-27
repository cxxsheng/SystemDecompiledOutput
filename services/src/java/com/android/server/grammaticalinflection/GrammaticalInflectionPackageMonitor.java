package com.android.server.grammaticalinflection;

/* loaded from: classes.dex */
public class GrammaticalInflectionPackageMonitor extends com.android.internal.content.PackageMonitor {
    private com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper mBackupHelper;

    GrammaticalInflectionPackageMonitor(com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper grammaticalInflectionBackupHelper) {
        this.mBackupHelper = grammaticalInflectionBackupHelper;
    }

    public void onPackageAdded(java.lang.String str, int i) {
        this.mBackupHelper.onPackageAdded(str, i);
    }

    public void onPackageDataCleared(java.lang.String str, int i) {
        this.mBackupHelper.onPackageDataCleared();
    }

    public void onPackageRemoved(java.lang.String str, int i) {
        this.mBackupHelper.onPackageRemoved();
    }
}
