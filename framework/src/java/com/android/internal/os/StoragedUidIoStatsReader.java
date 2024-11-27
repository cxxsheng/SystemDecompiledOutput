package com.android.internal.os;

/* loaded from: classes4.dex */
public class StoragedUidIoStatsReader {
    private static final java.lang.String TAG = com.android.internal.os.StoragedUidIoStatsReader.class.getSimpleName();
    private static java.lang.String sUidIoFile = "/proc/uid_io/stats";

    public interface Callback {
        void onUidStorageStats(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10);
    }

    public StoragedUidIoStatsReader() {
    }

    public StoragedUidIoStatsReader(java.lang.String str) {
        sUidIoFile = str;
    }

    public void readAbsolute(com.android.internal.os.StoragedUidIoStatsReader.Callback callback) {
        int allowThreadDiskReadsMask = android.os.StrictMode.allowThreadDiskReadsMask();
        try {
            readAbsoluteInternal(callback);
        } finally {
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    private void readAbsoluteInternal(com.android.internal.os.StoragedUidIoStatsReader.Callback callback) {
        try {
            java.io.BufferedReader newBufferedReader = java.nio.file.Files.newBufferedReader(new java.io.File(sUidIoFile).toPath());
            while (true) {
                try {
                    java.lang.String readLine = newBufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    java.lang.String[] split = android.text.TextUtils.split(readLine, " ");
                    if (split.length != 11) {
                        android.util.Slog.e(TAG, "Malformed entry in " + sUidIoFile + ": " + readLine);
                    } else {
                        try {
                            java.lang.String str = split[0];
                            callback.onUidStorageStats(java.lang.Integer.parseInt(split[0], 10), java.lang.Long.parseLong(split[1], 10), java.lang.Long.parseLong(split[2], 10), java.lang.Long.parseLong(split[3], 10), java.lang.Long.parseLong(split[4], 10), java.lang.Long.parseLong(split[5], 10), java.lang.Long.parseLong(split[6], 10), java.lang.Long.parseLong(split[7], 10), java.lang.Long.parseLong(split[8], 10), java.lang.Long.parseLong(split[9], 10), java.lang.Long.parseLong(split[10], 10));
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.e(TAG, "Could not parse entry in " + sUidIoFile + ": " + e.getMessage());
                        }
                    }
                } finally {
                }
            }
            if (newBufferedReader != null) {
                newBufferedReader.close();
            }
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to read " + sUidIoFile + ": " + e2.getMessage());
        }
    }
}
