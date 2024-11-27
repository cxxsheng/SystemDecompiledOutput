package com.android.server.backup;

/* loaded from: classes.dex */
final class UserBackupManagerFiles {
    private static final java.lang.String BACKUP_PERSISTENT_DIR = "backup";
    private static final java.lang.String BACKUP_STAGING_DIR = "backup_stage";

    UserBackupManagerFiles() {
    }

    private static java.io.File getBaseDir(int i) {
        return android.os.Environment.getDataSystemCeDirectory(i);
    }

    static java.io.File getBaseStateDir(int i) {
        if (i != 0) {
            return new java.io.File(getBaseDir(i), "backup");
        }
        return new java.io.File(android.os.Environment.getDataDirectory(), "backup");
    }

    static java.io.File getDataDir(int i) {
        if (i != 0) {
            return new java.io.File(getBaseDir(i), BACKUP_STAGING_DIR);
        }
        return new java.io.File(android.os.Environment.getDownloadCacheDirectory(), BACKUP_STAGING_DIR);
    }

    static java.io.File getStateDirInSystemDir(int i) {
        return new java.io.File(getBaseStateDir(0), "" + i);
    }

    static java.io.File getStateFileInSystemDir(java.lang.String str, int i) {
        return new java.io.File(getStateDirInSystemDir(i), str);
    }
}
