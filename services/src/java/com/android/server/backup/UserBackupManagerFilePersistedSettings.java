package com.android.server.backup;

/* loaded from: classes.dex */
final class UserBackupManagerFilePersistedSettings {
    private static final java.lang.String BACKUP_ENABLE_FILE = "backup_enabled";

    UserBackupManagerFilePersistedSettings() {
    }

    static boolean readBackupEnableState(int i) {
        boolean readBackupEnableState = readBackupEnableState(com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(i));
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "user:" + i + " readBackupEnableState enabled:" + readBackupEnableState);
        return readBackupEnableState;
    }

    static void writeBackupEnableState(int i, boolean z) {
        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "user:" + i + " writeBackupEnableState enable:" + z);
        writeBackupEnableState(com.android.server.backup.UserBackupManagerFiles.getBaseStateDir(i), z);
    }

    private static boolean readBackupEnableState(java.io.File file) {
        java.io.File file2 = new java.io.File(file, BACKUP_ENABLE_FILE);
        if (file2.exists()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file2);
                try {
                    int read = fileInputStream.read();
                    if (read != 0 && read != 1) {
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unexpected enabled state:" + read);
                    }
                    boolean z = read != 0;
                    fileInputStream.close();
                    return z;
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Cannot read enable state; assuming disabled");
            }
        } else {
            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "isBackupEnabled() => false due to absent settings file");
        }
        return false;
    }

    private static void writeBackupEnableState(java.io.File file, boolean z) {
        java.io.File file2 = new java.io.File(file, BACKUP_ENABLE_FILE);
        java.io.File file3 = new java.io.File(file, "backup_enabled-stage");
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file3);
            try {
                fileOutputStream.write(z ? 1 : 0);
                fileOutputStream.close();
                if (!file3.renameTo(file2)) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Write enable failed as could not rename staging file to actual");
                }
                fileOutputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.RuntimeException e) {
            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to record backup enable state; reverting to disabled: " + e.getMessage());
            file2.delete();
            file3.delete();
        }
    }
}
