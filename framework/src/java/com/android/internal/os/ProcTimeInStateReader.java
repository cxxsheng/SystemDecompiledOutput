package com.android.internal.os;

/* loaded from: classes4.dex */
public class ProcTimeInStateReader {
    private static final java.lang.String TAG = "ProcTimeInStateReader";
    private long[] mFrequenciesKhz;
    private int[] mTimeInStateTimeFormat;
    private static final int[] TIME_IN_STATE_LINE_FREQUENCY_FORMAT = {8224, 10};
    private static final int[] TIME_IN_STATE_LINE_TIME_FORMAT = {32, 8202};
    private static final int[] TIME_IN_STATE_HEADER_LINE_FORMAT = {10};

    public ProcTimeInStateReader(java.nio.file.Path path) throws java.io.IOException {
        initializeTimeInStateFormat(path);
    }

    public long[] getUsageTimesMillis(java.nio.file.Path path) {
        int length = this.mFrequenciesKhz.length;
        long[] jArr = new long[length];
        if (!android.os.Process.readProcFile(path.toString(), this.mTimeInStateTimeFormat, null, jArr, null)) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            jArr[i] = jArr[i] * 10;
        }
        return jArr;
    }

    public long[] getFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    private void initializeTimeInStateFormat(java.nio.file.Path path) throws java.io.IOException {
        byte[] readAllBytes = java.nio.file.Files.readAllBytes(path);
        android.util.IntArray intArray = new android.util.IntArray();
        android.util.IntArray intArray2 = new android.util.IntArray();
        int i = 0;
        int i2 = 0;
        while (i < readAllBytes.length) {
            if (!java.lang.Character.isDigit(readAllBytes[i])) {
                intArray.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
                intArray2.addAll(TIME_IN_STATE_HEADER_LINE_FORMAT);
            } else {
                intArray.addAll(TIME_IN_STATE_LINE_FREQUENCY_FORMAT);
                intArray2.addAll(TIME_IN_STATE_LINE_TIME_FORMAT);
                i2++;
            }
            while (i < readAllBytes.length && readAllBytes[i] != 10) {
                i++;
            }
            i++;
        }
        if (i2 == 0) {
            throw new java.io.IOException("Empty time_in_state file");
        }
        long[] jArr = new long[i2];
        if (!android.os.Process.parseProcLine(readAllBytes, 0, readAllBytes.length, intArray.toArray(), null, jArr, null)) {
            throw new java.io.IOException("Failed to parse time_in_state file");
        }
        this.mTimeInStateTimeFormat = intArray2.toArray();
        this.mFrequenciesKhz = jArr;
    }
}
