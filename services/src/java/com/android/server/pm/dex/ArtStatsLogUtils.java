package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public class ArtStatsLogUtils {
    private static final int ART_COMPILATION_FILTER_FAKE_RUN_FROM_APK_FALLBACK = 14;
    private static final int ART_COMPILATION_FILTER_FAKE_RUN_FROM_VDEX_FALLBACK = 15;
    private static final int ART_COMPILATION_REASON_INSTALL_BULK_DOWNGRADED = 15;
    private static final int ART_COMPILATION_REASON_INSTALL_BULK_SECONDARY = 14;
    private static final int ART_COMPILATION_REASON_INSTALL_BULK_SECONDARY_DOWNGRADED = 16;
    private static final java.util.Map<java.lang.String, java.lang.Integer> COMPILE_FILTER_MAP;
    private static final java.util.Map<java.lang.String, java.lang.Integer> ISA_MAP;
    private static final java.lang.String PROFILE_DEX_METADATA = "primary.prof";
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> STATUS_MAP;
    private static final java.lang.String VDEX_DEX_METADATA = "primary.vdex";
    private static final java.lang.String TAG = com.android.server.pm.dex.ArtStatsLogUtils.class.getSimpleName();
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> COMPILATION_REASON_MAP = new java.util.HashMap();

    static {
        COMPILATION_REASON_MAP.put(0, 3);
        COMPILATION_REASON_MAP.put(1, 17);
        COMPILATION_REASON_MAP.put(2, 11);
        COMPILATION_REASON_MAP.put(3, 5);
        COMPILATION_REASON_MAP.put(4, 12);
        COMPILATION_REASON_MAP.put(5, 13);
        COMPILATION_REASON_MAP.put(6, 14);
        COMPILATION_REASON_MAP.put(7, 15);
        COMPILATION_REASON_MAP.put(8, 16);
        COMPILATION_REASON_MAP.put(9, 6);
        COMPILATION_REASON_MAP.put(10, 7);
        COMPILATION_REASON_MAP.put(11, 8);
        COMPILATION_REASON_MAP.put(12, 19);
        COMPILATION_REASON_MAP.put(14, 9);
        COMPILE_FILTER_MAP = new java.util.HashMap();
        COMPILE_FILTER_MAP.put("error", 1);
        COMPILE_FILTER_MAP.put("unknown", 2);
        COMPILE_FILTER_MAP.put("assume-verified", 3);
        COMPILE_FILTER_MAP.put("extract", 4);
        COMPILE_FILTER_MAP.put("verify", 5);
        COMPILE_FILTER_MAP.put("quicken", 6);
        COMPILE_FILTER_MAP.put("space-profile", 7);
        COMPILE_FILTER_MAP.put("space", 8);
        COMPILE_FILTER_MAP.put("speed-profile", 9);
        COMPILE_FILTER_MAP.put("speed", 10);
        COMPILE_FILTER_MAP.put("everything-profile", 11);
        COMPILE_FILTER_MAP.put("everything", 12);
        COMPILE_FILTER_MAP.put("run-from-apk", 13);
        COMPILE_FILTER_MAP.put("run-from-apk-fallback", 14);
        COMPILE_FILTER_MAP.put("run-from-vdex-fallback", 15);
        ISA_MAP = new java.util.HashMap();
        ISA_MAP.put("arm", 1);
        ISA_MAP.put("arm64", 2);
        ISA_MAP.put("x86", 3);
        ISA_MAP.put("x86_64", 4);
        ISA_MAP.put("mips", 5);
        ISA_MAP.put("mips64", 6);
        STATUS_MAP = java.util.Map.of(-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 1, 6, 7);
    }

    public static void writeStatsLog(com.android.server.pm.dex.ArtStatsLogUtils.ArtStatsLogger artStatsLogger, long j, java.lang.String str, int i, long j2, java.lang.String str2, int i2, int i3, int i4, java.lang.String str3, java.lang.String str4) {
        int dexMetadataType = getDexMetadataType(str2);
        artStatsLogger.write(j, i, i2, str, 10, i3, dexMetadataType, i4, str3);
        artStatsLogger.write(j, i, i2, str, 11, getDexBytes(str4), dexMetadataType, i4, str3);
        artStatsLogger.write(j, i, i2, str, 12, j2, dexMetadataType, i4, str3);
    }

    public static int getApkType(final java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        if (str.equals(str2)) {
            return 1;
        }
        if (java.util.Arrays.stream(strArr).anyMatch(new java.util.function.Predicate() { // from class: com.android.server.pm.dex.ArtStatsLogUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getApkType$0;
                lambda$getApkType$0 = com.android.server.pm.dex.ArtStatsLogUtils.lambda$getApkType$0(str, (java.lang.String) obj);
                return lambda$getApkType$0;
            }
        })) {
            return 2;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getApkType$0(java.lang.String str, java.lang.String str2) {
        return str2.equals(str);
    }

    private static long getDexBytes(java.lang.String str) {
        android.util.jar.StrictJarFile strictJarFile = null;
        try {
            try {
                android.util.jar.StrictJarFile strictJarFile2 = new android.util.jar.StrictJarFile(str, false, false);
                try {
                    java.util.Iterator it = strictJarFile2.iterator();
                    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("classes(\\d)*[.]dex").matcher("");
                    long j = 0;
                    while (it.hasNext()) {
                        java.util.zip.ZipEntry zipEntry = (java.util.zip.ZipEntry) it.next();
                        matcher.reset(zipEntry.getName());
                        if (matcher.matches()) {
                            j += zipEntry.getSize();
                        }
                    }
                    try {
                        strictJarFile2.close();
                    } catch (java.io.IOException e) {
                    }
                    return j;
                } catch (java.io.IOException e2) {
                    strictJarFile = strictJarFile2;
                    android.util.Slog.e(TAG, "Error when parsing APK " + str);
                    if (strictJarFile == null) {
                        return -1L;
                    }
                    try {
                        strictJarFile.close();
                        return -1L;
                    } catch (java.io.IOException e3) {
                        return -1L;
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    strictJarFile = strictJarFile2;
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (java.io.IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e5) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private static int getDexMetadataType(java.lang.String str) {
        if (str == null) {
            return 4;
        }
        android.util.jar.StrictJarFile strictJarFile = null;
        try {
            try {
                android.util.jar.StrictJarFile strictJarFile2 = new android.util.jar.StrictJarFile(str, false, false);
                try {
                    boolean findFileName = findFileName(strictJarFile2, PROFILE_DEX_METADATA);
                    boolean findFileName2 = findFileName(strictJarFile2, VDEX_DEX_METADATA);
                    if (findFileName && findFileName2) {
                        try {
                            strictJarFile2.close();
                            return 3;
                        } catch (java.io.IOException e) {
                            return 3;
                        }
                    }
                    if (findFileName) {
                        try {
                            strictJarFile2.close();
                            return 1;
                        } catch (java.io.IOException e2) {
                            return 1;
                        }
                    }
                    if (!findFileName2) {
                        try {
                            strictJarFile2.close();
                        } catch (java.io.IOException e3) {
                        }
                        return 0;
                    }
                    try {
                        strictJarFile2.close();
                        return 2;
                    } catch (java.io.IOException e4) {
                        return 2;
                    }
                } catch (java.io.IOException e5) {
                    strictJarFile = strictJarFile2;
                    android.util.Slog.e(TAG, "Error when parsing dex metadata " + str);
                    if (strictJarFile == null) {
                        return 5;
                    }
                    try {
                        strictJarFile.close();
                        return 5;
                    } catch (java.io.IOException e6) {
                        return 5;
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    strictJarFile = strictJarFile2;
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (java.io.IOException e7) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e8) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private static boolean findFileName(android.util.jar.StrictJarFile strictJarFile, java.lang.String str) throws java.io.IOException {
        java.util.Iterator it = strictJarFile.iterator();
        while (it.hasNext()) {
            if (((java.util.zip.ZipEntry) it.next()).getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static class ArtStatsLogger {
        public void write(long j, int i, int i2, java.lang.String str, int i3, long j2, int i4, int i5, java.lang.String str2) {
            com.android.internal.art.ArtStatsLog.write(com.android.internal.art.ArtStatsLog.ART_DATUM_REPORTED, j, i, ((java.lang.Integer) com.android.server.pm.dex.ArtStatsLogUtils.COMPILE_FILTER_MAP.getOrDefault(str, 2)).intValue(), ((java.lang.Integer) com.android.server.pm.dex.ArtStatsLogUtils.COMPILATION_REASON_MAP.getOrDefault(java.lang.Integer.valueOf(i2), 2)).intValue(), android.os.SystemClock.uptimeMillis(), 1, i3, j2, i4, i5, ((java.lang.Integer) com.android.server.pm.dex.ArtStatsLogUtils.ISA_MAP.getOrDefault(str2, 0)).intValue(), 0, 0);
        }
    }

    public static class BackgroundDexoptJobStatsLogger {
        public void write(int i, int i2, long j) {
            com.android.internal.art.ArtStatsLog.write(com.android.internal.art.ArtStatsLog.BACKGROUND_DEXOPT_JOB_ENDED, ((java.lang.Integer) com.android.server.pm.dex.ArtStatsLogUtils.STATUS_MAP.getOrDefault(java.lang.Integer.valueOf(i), 0)).intValue(), i2, j, 0L, 0, 0, 0, 0);
        }
    }
}
