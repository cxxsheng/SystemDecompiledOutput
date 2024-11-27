package com.android.server.am;

/* loaded from: classes.dex */
public final class MemoryStatUtil {
    private static final java.lang.String MEMORY_STAT_FILE_FMT = "/dev/memcg/apps/uid_%d/pid_%d/memory.stat";
    private static final int PGFAULT_INDEX = 9;
    private static final int PGMAJFAULT_INDEX = 11;
    private static final java.lang.String PROC_STAT_FILE_FMT = "/proc/%d/stat";
    private static final int RSS_IN_PAGES_INDEX = 23;
    private static final java.lang.String TAG = "ActivityManager";
    static final int PAGE_SIZE = (int) android.system.Os.sysconf(android.system.OsConstants._SC_PAGESIZE);
    private static final boolean DEVICE_HAS_PER_APP_MEMCG = android.os.SystemProperties.getBoolean("ro.config.per_app_memcg", false);
    private static final java.util.regex.Pattern PGFAULT = java.util.regex.Pattern.compile("total_pgfault (\\d+)");
    private static final java.util.regex.Pattern PGMAJFAULT = java.util.regex.Pattern.compile("total_pgmajfault (\\d+)");
    private static final java.util.regex.Pattern RSS_IN_BYTES = java.util.regex.Pattern.compile("total_rss (\\d+)");
    private static final java.util.regex.Pattern CACHE_IN_BYTES = java.util.regex.Pattern.compile("total_cache (\\d+)");
    private static final java.util.regex.Pattern SWAP_IN_BYTES = java.util.regex.Pattern.compile("total_swap (\\d+)");

    public static final class MemoryStat {
        public long cacheInBytes;
        public long pgfault;
        public long pgmajfault;
        public long rssInBytes;
        public long swapInBytes;
    }

    private MemoryStatUtil() {
    }

    @android.annotation.Nullable
    public static com.android.server.am.MemoryStatUtil.MemoryStat readMemoryStatFromFilesystem(int i, int i2) {
        return hasMemcg() ? readMemoryStatFromMemcg(i, i2) : readMemoryStatFromProcfs(i2);
    }

    @android.annotation.Nullable
    static com.android.server.am.MemoryStatUtil.MemoryStat readMemoryStatFromMemcg(int i, int i2) {
        return parseMemoryStatFromMemcg(readFileContents(java.lang.String.format(java.util.Locale.US, MEMORY_STAT_FILE_FMT, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2))));
    }

    @android.annotation.Nullable
    public static com.android.server.am.MemoryStatUtil.MemoryStat readMemoryStatFromProcfs(int i) {
        return parseMemoryStatFromProcfs(readFileContents(java.lang.String.format(java.util.Locale.US, PROC_STAT_FILE_FMT, java.lang.Integer.valueOf(i))));
    }

    private static java.lang.String readFileContents(java.lang.String str) {
        java.io.File file = new java.io.File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            return android.os.FileUtils.readTextFile(file, 0, null);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read file:", e);
            return null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.am.MemoryStatUtil.MemoryStat parseMemoryStatFromMemcg(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        com.android.server.am.MemoryStatUtil.MemoryStat memoryStat = new com.android.server.am.MemoryStatUtil.MemoryStat();
        memoryStat.pgfault = tryParseLong(PGFAULT, str);
        memoryStat.pgmajfault = tryParseLong(PGMAJFAULT, str);
        memoryStat.rssInBytes = tryParseLong(RSS_IN_BYTES, str);
        memoryStat.cacheInBytes = tryParseLong(CACHE_IN_BYTES, str);
        memoryStat.swapInBytes = tryParseLong(SWAP_IN_BYTES, str);
        return memoryStat;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.am.MemoryStatUtil.MemoryStat parseMemoryStatFromProcfs(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        java.lang.String[] split = str.split(" ");
        if (split.length < 24) {
            return null;
        }
        try {
            com.android.server.am.MemoryStatUtil.MemoryStat memoryStat = new com.android.server.am.MemoryStatUtil.MemoryStat();
            memoryStat.pgfault = java.lang.Long.parseLong(split[9]);
            memoryStat.pgmajfault = java.lang.Long.parseLong(split[11]);
            memoryStat.rssInBytes = java.lang.Long.parseLong(split[23]) * PAGE_SIZE;
            return memoryStat;
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Failed to parse value", e);
            return null;
        }
    }

    static boolean hasMemcg() {
        return DEVICE_HAS_PER_APP_MEMCG;
    }

    private static long tryParseLong(java.util.regex.Pattern pattern, java.lang.String str) {
        java.util.regex.Matcher matcher = pattern.matcher(str);
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
}
