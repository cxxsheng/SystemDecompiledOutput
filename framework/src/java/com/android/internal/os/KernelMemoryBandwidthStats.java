package com.android.internal.os;

/* loaded from: classes4.dex */
public class KernelMemoryBandwidthStats {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KernelMemoryBandwidthStats";
    private static final java.lang.String mSysfsFile = "/sys/kernel/memory_state_time/show_stat";
    protected final android.util.LongSparseLongArray mBandwidthEntries = new android.util.LongSparseLongArray();
    private boolean mStatsDoNotExist = false;

    public void updateStats() {
        java.io.BufferedReader bufferedReader;
        if (this.mStatsDoNotExist) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.os.StrictMode.ThreadPolicy allowThreadDiskReads = android.os.StrictMode.allowThreadDiskReads();
        try {
            try {
                bufferedReader = new java.io.BufferedReader(new java.io.FileReader(mSysfsFile));
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(TAG, "No kernel memory bandwidth stats available");
                this.mBandwidthEntries.clear();
                this.mStatsDoNotExist = true;
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Failed to read memory bandwidth: " + e2.getMessage());
                this.mBandwidthEntries.clear();
            }
            try {
                parseStats(bufferedReader);
                bufferedReader.close();
                android.os.StrictMode.setThreadPolicy(allowThreadDiskReads);
                long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                if (uptimeMillis2 > 100) {
                    android.util.Slog.w(TAG, "Reading memory bandwidth file took " + uptimeMillis2 + "ms");
                }
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

    public void parseStats(java.io.BufferedReader bufferedReader) throws java.io.IOException {
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(' ');
        this.mBandwidthEntries.clear();
        while (true) {
            java.lang.String readLine = bufferedReader.readLine();
            if (readLine != null) {
                simpleStringSplitter.setString(readLine);
                simpleStringSplitter.next();
                int i = 0;
                do {
                    long j = i;
                    int indexOfKey = this.mBandwidthEntries.indexOfKey(j);
                    if (indexOfKey >= 0) {
                        this.mBandwidthEntries.put(j, this.mBandwidthEntries.valueAt(indexOfKey) + (java.lang.Long.parseLong(simpleStringSplitter.next()) / 1000000));
                    } else {
                        this.mBandwidthEntries.put(j, java.lang.Long.parseLong(simpleStringSplitter.next()) / 1000000);
                    }
                    i++;
                } while (simpleStringSplitter.hasNext());
            } else {
                return;
            }
        }
    }

    public android.util.LongSparseLongArray getBandwidthEntries() {
        return this.mBandwidthEntries;
    }
}
