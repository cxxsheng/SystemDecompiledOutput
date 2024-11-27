package com.android.server.backup.utils;

/* loaded from: classes.dex */
public final class RandomAccessFileUtils {
    private static java.io.RandomAccessFile getRandomAccessFile(java.io.File file) throws java.io.FileNotFoundException {
        return new java.io.RandomAccessFile(file, "rwd");
    }

    public static void writeBoolean(java.io.File file, boolean z) {
        try {
            java.io.RandomAccessFile randomAccessFile = getRandomAccessFile(file);
            try {
                randomAccessFile.writeBoolean(z);
                randomAccessFile.close();
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Error writing file:" + file.getAbsolutePath(), e);
        }
    }

    public static boolean readBoolean(java.io.File file, boolean z) {
        try {
            java.io.RandomAccessFile randomAccessFile = getRandomAccessFile(file);
            try {
                boolean readBoolean = randomAccessFile.readBoolean();
                randomAccessFile.close();
                return readBoolean;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Error reading file:" + file.getAbsolutePath(), e);
            return z;
        }
    }
}
