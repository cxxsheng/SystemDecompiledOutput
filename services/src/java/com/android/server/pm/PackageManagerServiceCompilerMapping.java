package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageManagerServiceCompilerMapping {
    public static final java.lang.String[] REASON_STRINGS = {"first-boot", "boot-after-ota", "post-boot", "install", "install-fast", "install-bulk", "install-bulk-secondary", "install-bulk-downgraded", "install-bulk-secondary-downgraded", "bg-dexopt", "ab-ota", "inactive", "cmdline", "boot-after-mainline-update", "shared"};
    static final int REASON_SHARED_INDEX = REASON_STRINGS.length - 1;

    static {
        if (15 != REASON_STRINGS.length) {
            throw new java.lang.IllegalStateException("REASON_STRINGS not correct");
        }
        if (!"shared".equals(REASON_STRINGS[REASON_SHARED_INDEX])) {
            throw new java.lang.IllegalStateException("REASON_STRINGS not correct because of shared index");
        }
    }

    private static java.lang.String getSystemPropertyName(int i) {
        if (i < 0 || i >= REASON_STRINGS.length) {
            throw new java.lang.IllegalArgumentException("reason " + i + " invalid");
        }
        return "pm.dexopt." + REASON_STRINGS[i];
    }

    private static java.lang.String getAndCheckValidity(int i) {
        java.lang.String str = android.os.SystemProperties.get(getSystemPropertyName(i));
        if (str == null || str.isEmpty() || (!str.equals(com.android.server.pm.dex.DexoptOptions.COMPILER_FILTER_NOOP) && !dalvik.system.DexFile.isValidCompilerFilter(str))) {
            throw new java.lang.IllegalStateException("Value \"" + str + "\" not valid (reason " + REASON_STRINGS[i] + ")");
        }
        if (!isFilterAllowedForReason(i, str)) {
            throw new java.lang.IllegalStateException("Value \"" + str + "\" not allowed (reason " + REASON_STRINGS[i] + ")");
        }
        return str;
    }

    private static boolean isFilterAllowedForReason(int i, java.lang.String str) {
        return (i == REASON_SHARED_INDEX && dalvik.system.DexFile.isProfileGuidedCompilerFilter(str)) ? false : true;
    }

    static void checkProperties() {
        java.lang.String systemPropertyName;
        java.lang.IllegalStateException illegalStateException = null;
        for (int i = 0; i <= 14; i++) {
            try {
                systemPropertyName = getSystemPropertyName(i);
            } catch (java.lang.Exception e) {
                if (illegalStateException == null) {
                    illegalStateException = new java.lang.IllegalStateException("PMS compiler filter settings are bad.");
                }
                illegalStateException.addSuppressed(e);
            }
            if (systemPropertyName == null || systemPropertyName.isEmpty()) {
                throw new java.lang.IllegalStateException("Reason system property name \"" + systemPropertyName + "\" for reason " + REASON_STRINGS[i]);
            }
            getAndCheckValidity(i);
        }
        if (illegalStateException != null) {
            throw illegalStateException;
        }
    }

    public static java.lang.String getCompilerFilterForReason(int i) {
        return getAndCheckValidity(i);
    }

    public static java.lang.String getDefaultCompilerFilter() {
        java.lang.String str = android.os.SystemProperties.get("dalvik.vm.dex2oat-filter");
        if (str == null || str.isEmpty() || !dalvik.system.DexFile.isValidCompilerFilter(str) || dalvik.system.DexFile.isProfileGuidedCompilerFilter(str)) {
            return "speed";
        }
        return str;
    }

    public static java.lang.String getReasonName(int i) {
        if (i < 0 || i >= REASON_STRINGS.length) {
            throw new java.lang.IllegalArgumentException("reason " + i + " invalid");
        }
        return REASON_STRINGS[i];
    }
}
