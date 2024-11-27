package com.android.server.stats.pull;

/* loaded from: classes2.dex */
public final class IonMemoryUtil {
    private static final java.lang.String DEBUG_SYSTEM_ION_HEAP_FILE = "/sys/kernel/debug/ion/heaps/system";
    private static final java.util.regex.Pattern ION_HEAP_SIZE_IN_BYTES = java.util.regex.Pattern.compile("\n\\s*total\\s*(\\d+)\\s*\n");
    private static final java.util.regex.Pattern PROCESS_ION_HEAP_SIZE_IN_BYTES = java.util.regex.Pattern.compile("\n\\s+\\S+\\s+(\\d+)\\s+(\\d+)");
    private static final java.lang.String TAG = "IonMemoryUtil";

    private IonMemoryUtil() {
    }

    public static long readSystemIonHeapSizeFromDebugfs() {
        return parseIonHeapSizeFromDebugfs(readFile(DEBUG_SYSTEM_ION_HEAP_FILE));
    }

    @com.android.internal.annotations.VisibleForTesting
    static long parseIonHeapSizeFromDebugfs(java.lang.String str) {
        if (str.isEmpty()) {
            return 0L;
        }
        java.util.regex.Matcher matcher = ION_HEAP_SIZE_IN_BYTES.matcher(str);
        try {
            if (matcher.find()) {
                return java.lang.Long.parseLong(matcher.group(1));
            }
            return 0L;
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Failed to parse value", e);
            return 0L;
        }
    }

    public static java.util.List<com.android.server.stats.pull.IonMemoryUtil.IonAllocations> readProcessSystemIonHeapSizesFromDebugfs() {
        return parseProcessIonHeapSizesFromDebugfs(readFile(DEBUG_SYSTEM_ION_HEAP_FILE));
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.util.List<com.android.server.stats.pull.IonMemoryUtil.IonAllocations> parseProcessIonHeapSizesFromDebugfs(java.lang.String str) {
        if (str.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        java.util.regex.Matcher matcher = PROCESS_ION_HEAP_SIZE_IN_BYTES.matcher(str);
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        while (matcher.find()) {
            try {
                int parseInt = java.lang.Integer.parseInt(matcher.group(1));
                long parseLong = java.lang.Long.parseLong(matcher.group(2));
                com.android.server.stats.pull.IonMemoryUtil.IonAllocations ionAllocations = (com.android.server.stats.pull.IonMemoryUtil.IonAllocations) sparseArray.get(parseInt);
                if (ionAllocations == null) {
                    ionAllocations = new com.android.server.stats.pull.IonMemoryUtil.IonAllocations();
                    sparseArray.put(parseInt, ionAllocations);
                }
                ionAllocations.pid = parseInt;
                ionAllocations.totalSizeInBytes += parseLong;
                ionAllocations.count++;
                ionAllocations.maxSizeInBytes = java.lang.Math.max(ionAllocations.maxSizeInBytes, parseLong);
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Failed to parse value", e);
            }
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add((com.android.server.stats.pull.IonMemoryUtil.IonAllocations) sparseArray.valueAt(i));
        }
        return arrayList;
    }

    private static java.lang.String readFile(java.lang.String str) {
        try {
            return android.os.FileUtils.readTextFile(new java.io.File(str), 0, null);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read file", e);
            return "";
        }
    }

    public static final class IonAllocations {
        public int count;
        public long maxSizeInBytes;
        public int pid;
        public long totalSizeInBytes;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.stats.pull.IonMemoryUtil.IonAllocations.class != obj.getClass()) {
                return false;
            }
            com.android.server.stats.pull.IonMemoryUtil.IonAllocations ionAllocations = (com.android.server.stats.pull.IonMemoryUtil.IonAllocations) obj;
            if (this.pid == ionAllocations.pid && this.totalSizeInBytes == ionAllocations.totalSizeInBytes && this.count == ionAllocations.count && this.maxSizeInBytes == ionAllocations.maxSizeInBytes) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.pid), java.lang.Long.valueOf(this.totalSizeInBytes), java.lang.Integer.valueOf(this.count), java.lang.Long.valueOf(this.maxSizeInBytes));
        }

        public java.lang.String toString() {
            return "IonAllocations{pid=" + this.pid + ", totalSizeInBytes=" + this.totalSizeInBytes + ", count=" + this.count + ", maxSizeInBytes=" + this.maxSizeInBytes + '}';
        }
    }
}
