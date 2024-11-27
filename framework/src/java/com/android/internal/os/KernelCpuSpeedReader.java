package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelCpuSpeedReader {
    private static final java.lang.String TAG = "KernelCpuSpeedReader";
    private final long[] mDeltaSpeedTimesMs;
    private final long mJiffyMillis = 1000 / android.system.Os.sysconf(android.system.OsConstants._SC_CLK_TCK);
    private final long[] mLastSpeedTimesMs;
    private final int mNumSpeedSteps;
    private final java.lang.String mProcFile;

    public KernelCpuSpeedReader(int i, int i2) {
        this.mProcFile = java.lang.String.format("/sys/devices/system/cpu/cpu%d/cpufreq/stats/time_in_state", java.lang.Integer.valueOf(i));
        this.mNumSpeedSteps = i2;
        this.mLastSpeedTimesMs = new long[i2];
        this.mDeltaSpeedTimesMs = new long[i2];
    }

    public long[] readDelta() {
        java.io.BufferedReader bufferedReader;
        java.lang.String readLine;
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            try {
                bufferedReader = new java.io.BufferedReader(new java.io.FileReader(this.mProcFile));
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to read cpu-freq: " + e.getMessage());
                java.util.Arrays.fill(this.mDeltaSpeedTimesMs, 0L);
            }
            try {
                android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(' ');
                for (int i = 0; i < this.mLastSpeedTimesMs.length && (readLine = bufferedReader.readLine()) != null; i++) {
                    simpleStringSplitter.setString(readLine);
                    simpleStringSplitter.next();
                    long parseLong = java.lang.Long.parseLong(simpleStringSplitter.next()) * this.mJiffyMillis;
                    if (parseLong < this.mLastSpeedTimesMs[i]) {
                        this.mDeltaSpeedTimesMs[i] = parseLong;
                    } else {
                        this.mDeltaSpeedTimesMs[i] = parseLong - this.mLastSpeedTimesMs[i];
                    }
                    this.mLastSpeedTimesMs[i] = parseLong;
                }
                bufferedReader.close();
                android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
                return this.mDeltaSpeedTimesMs;
            } catch (java.lang.Throwable th) {
                try {
                    bufferedReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
            throw th3;
        }
    }

    public long[] readAbsolute() {
        java.io.BufferedReader bufferedReader;
        java.lang.String readLine;
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        long[] jArr = new long[this.mNumSpeedSteps];
        try {
            try {
                bufferedReader = new java.io.BufferedReader(new java.io.FileReader(this.mProcFile));
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Failed to read cpu-freq: " + e.getMessage());
                java.util.Arrays.fill(jArr, 0L);
            }
            try {
                android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(' ');
                for (int i = 0; i < this.mNumSpeedSteps && (readLine = bufferedReader.readLine()) != null; i++) {
                    simpleStringSplitter.setString(readLine);
                    simpleStringSplitter.next();
                    jArr[i] = java.lang.Long.parseLong(simpleStringSplitter.next()) * this.mJiffyMillis;
                }
                bufferedReader.close();
                return jArr;
            } catch (java.lang.Throwable th) {
                try {
                    bufferedReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
