package com.android.server.backup.params;

/* loaded from: classes.dex */
public class AdbBackupParams extends com.android.server.backup.params.AdbParams {
    public boolean allApps;
    public com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules;
    public boolean doCompress;
    public boolean doWidgets;
    public boolean includeApks;
    public boolean includeKeyValue;
    public boolean includeObbs;
    public boolean includeShared;
    public boolean includeSystem;
    public java.lang.String[] packages;

    public AdbBackupParams(android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.fd = parcelFileDescriptor;
        this.includeApks = z;
        this.includeObbs = z2;
        this.includeShared = z3;
        this.doWidgets = z4;
        this.allApps = z5;
        this.includeSystem = z6;
        this.doCompress = z7;
        this.includeKeyValue = z8;
        this.packages = strArr;
        this.backupEligibilityRules = backupEligibilityRules;
    }
}
