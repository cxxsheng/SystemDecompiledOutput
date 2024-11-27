package com.android.internal.os;

/* loaded from: classes4.dex */
public class CpuScalingPolicyReader {
    private static final java.lang.String CPUFREQ_DIR = "/sys/devices/system/cpu/cpufreq";
    private static final java.lang.String FILE_NAME_CPUINFO_CUR_FREQ = "cpuinfo_cur_freq";
    private static final java.lang.String FILE_NAME_RELATED_CPUS = "related_cpus";
    private static final java.lang.String FILE_NAME_SCALING_AVAILABLE_FREQUENCIES = "scaling_available_frequencies";
    private static final java.lang.String FILE_NAME_SCALING_BOOST_FREQUENCIES = "scaling_boost_frequencies";
    private static final java.util.regex.Pattern POLICY_PATTERN = java.util.regex.Pattern.compile("policy(\\d+)");
    private static final java.lang.String TAG = "CpuScalingPolicyReader";
    private final java.lang.String mCpuFreqDir;

    public CpuScalingPolicyReader() {
        this(CPUFREQ_DIR);
    }

    public CpuScalingPolicyReader(java.lang.String str) {
        this.mCpuFreqDir = str;
    }

    public com.android.internal.os.CpuScalingPolicies read() {
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        android.util.SparseArray sparseArray2 = new android.util.SparseArray();
        java.io.File[] listFiles = new java.io.File(this.mCpuFreqDir).listFiles();
        if (listFiles != null) {
            for (java.io.File file : listFiles) {
                java.util.regex.Matcher matcher = POLICY_PATTERN.matcher(file.getName());
                if (matcher.matches()) {
                    int[] readIntsFromFile = readIntsFromFile(new java.io.File(file, FILE_NAME_RELATED_CPUS));
                    if (readIntsFromFile.length != 0) {
                        int[] readIntsFromFile2 = readIntsFromFile(new java.io.File(file, FILE_NAME_SCALING_AVAILABLE_FREQUENCIES));
                        int[] readIntsFromFile3 = readIntsFromFile(new java.io.File(file, FILE_NAME_SCALING_BOOST_FREQUENCIES));
                        if (readIntsFromFile3.length != 0) {
                            int[] copyOf = java.util.Arrays.copyOf(readIntsFromFile2, readIntsFromFile2.length + readIntsFromFile3.length);
                            java.lang.System.arraycopy(readIntsFromFile3, 0, copyOf, readIntsFromFile2.length, readIntsFromFile3.length);
                            readIntsFromFile2 = copyOf;
                        }
                        if (readIntsFromFile2.length == 0) {
                            readIntsFromFile2 = readIntsFromFile(new java.io.File(file, FILE_NAME_CPUINFO_CUR_FREQ));
                            if (readIntsFromFile2.length == 0) {
                                readIntsFromFile2 = new int[]{0};
                            }
                        }
                        int parseInt = java.lang.Integer.parseInt(matcher.group(1));
                        sparseArray.put(parseInt, readIntsFromFile);
                        sparseArray2.put(parseInt, readIntsFromFile2);
                    }
                }
            }
        }
        if (sparseArray.size() == 0) {
            sparseArray.put(0, new int[]{0});
            sparseArray2.put(0, new int[]{0});
        }
        return new com.android.internal.os.CpuScalingPolicies(sparseArray, sparseArray2);
    }

    private static int[] readIntsFromFile(java.io.File file) {
        if (!file.exists()) {
            return libcore.util.EmptyArray.INT;
        }
        android.util.IntArray intArray = new android.util.IntArray(16);
        try {
            java.lang.String trim = android.os.FileUtils.readTextFile(file, 0, null).trim();
            java.lang.String[] split = trim.split(" ");
            intArray.clear();
            for (java.lang.String str : split) {
                try {
                    intArray.add(java.lang.Integer.parseInt(str));
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.e(TAG, "Unexpected file format " + file + ": " + trim, e);
                }
            }
            return intArray.toArray();
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Cannot read " + file, e2);
            return libcore.util.EmptyArray.INT;
        }
    }
}
