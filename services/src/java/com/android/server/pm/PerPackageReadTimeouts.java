package com.android.server.pm;

/* loaded from: classes2.dex */
class PerPackageReadTimeouts {
    public final java.lang.String packageName;
    public final byte[] sha256certificate;
    public final com.android.server.pm.PerPackageReadTimeouts.Timeouts timeouts;
    public final com.android.server.pm.PerPackageReadTimeouts.VersionCodes versionCodes;

    static long tryParseLong(java.lang.String str, long j) {
        try {
            return java.lang.Long.parseLong(str);
        } catch (java.lang.NumberFormatException e) {
            return j;
        }
    }

    static byte[] tryParseSha256(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return com.android.internal.util.HexDump.hexStringToByteArray(str);
        } catch (java.lang.RuntimeException e) {
            return null;
        }
    }

    static class Timeouts {
        public static final com.android.server.pm.PerPackageReadTimeouts.Timeouts DEFAULT = new com.android.server.pm.PerPackageReadTimeouts.Timeouts(3600000000L, 3600000000L, 3600000000L);
        public final long maxPendingTimeUs;
        public final long minPendingTimeUs;
        public final long minTimeUs;

        private Timeouts(long j, long j2, long j3) {
            this.minTimeUs = j;
            this.minPendingTimeUs = j2;
            this.maxPendingTimeUs = j3;
        }

        static com.android.server.pm.PerPackageReadTimeouts.Timeouts parse(java.lang.String str) {
            java.lang.String[] split = str.split(":", 3);
            if (split.length != 3) {
                return DEFAULT;
            }
            long tryParseLong = com.android.server.pm.PerPackageReadTimeouts.tryParseLong(split[0], DEFAULT.minTimeUs);
            long tryParseLong2 = com.android.server.pm.PerPackageReadTimeouts.tryParseLong(split[1], DEFAULT.minPendingTimeUs);
            long tryParseLong3 = com.android.server.pm.PerPackageReadTimeouts.tryParseLong(split[2], DEFAULT.maxPendingTimeUs);
            if (0 <= tryParseLong && tryParseLong <= tryParseLong2 && tryParseLong2 <= tryParseLong3) {
                return new com.android.server.pm.PerPackageReadTimeouts.Timeouts(tryParseLong, tryParseLong2, tryParseLong3);
            }
            return DEFAULT;
        }
    }

    static class VersionCodes {
        public static final com.android.server.pm.PerPackageReadTimeouts.VersionCodes ALL_VERSION_CODES = new com.android.server.pm.PerPackageReadTimeouts.VersionCodes(Long.MIN_VALUE, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME);
        public final long maxVersionCode;
        public final long minVersionCode;

        private VersionCodes(long j, long j2) {
            this.minVersionCode = j;
            this.maxVersionCode = j2;
        }

        static com.android.server.pm.PerPackageReadTimeouts.VersionCodes parse(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return ALL_VERSION_CODES;
            }
            java.lang.String[] split = str.split("-", 2);
            switch (split.length) {
                case 1:
                    try {
                        long parseLong = java.lang.Long.parseLong(split[0]);
                        return new com.android.server.pm.PerPackageReadTimeouts.VersionCodes(parseLong, parseLong);
                    } catch (java.lang.NumberFormatException e) {
                        return ALL_VERSION_CODES;
                    }
                case 2:
                    long tryParseLong = com.android.server.pm.PerPackageReadTimeouts.tryParseLong(split[0], ALL_VERSION_CODES.minVersionCode);
                    long tryParseLong2 = com.android.server.pm.PerPackageReadTimeouts.tryParseLong(split[1], ALL_VERSION_CODES.maxVersionCode);
                    if (tryParseLong <= tryParseLong2) {
                        return new com.android.server.pm.PerPackageReadTimeouts.VersionCodes(tryParseLong, tryParseLong2);
                    }
                    break;
            }
            return ALL_VERSION_CODES;
        }
    }

    private PerPackageReadTimeouts(java.lang.String str, byte[] bArr, com.android.server.pm.PerPackageReadTimeouts.VersionCodes versionCodes, com.android.server.pm.PerPackageReadTimeouts.Timeouts timeouts) {
        this.packageName = str;
        this.sha256certificate = bArr;
        this.versionCodes = versionCodes;
        this.timeouts = timeouts;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static com.android.server.pm.PerPackageReadTimeouts parse(java.lang.String str, com.android.server.pm.PerPackageReadTimeouts.VersionCodes versionCodes, com.android.server.pm.PerPackageReadTimeouts.Timeouts timeouts) {
        byte[] bArr;
        java.lang.String str2;
        java.lang.String[] split = str.split(":", 4);
        switch (split.length) {
            case 1:
                bArr = null;
                str2 = split[0];
                if (android.text.TextUtils.isEmpty(str2)) {
                    break;
                }
                break;
            case 2:
                bArr = tryParseSha256(split[1]);
                str2 = split[0];
                if (android.text.TextUtils.isEmpty(str2)) {
                }
                break;
            case 3:
                versionCodes = com.android.server.pm.PerPackageReadTimeouts.VersionCodes.parse(split[2]);
                bArr = tryParseSha256(split[1]);
                str2 = split[0];
                if (android.text.TextUtils.isEmpty(str2)) {
                }
                break;
            case 4:
                timeouts = com.android.server.pm.PerPackageReadTimeouts.Timeouts.parse(split[3]);
                versionCodes = com.android.server.pm.PerPackageReadTimeouts.VersionCodes.parse(split[2]);
                bArr = tryParseSha256(split[1]);
                str2 = split[0];
                if (android.text.TextUtils.isEmpty(str2)) {
                }
                break;
        }
        return null;
    }

    @android.annotation.NonNull
    static java.util.List<com.android.server.pm.PerPackageReadTimeouts> parseDigestersList(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str2)) {
            return java.util.Collections.emptyList();
        }
        com.android.server.pm.PerPackageReadTimeouts.VersionCodes versionCodes = com.android.server.pm.PerPackageReadTimeouts.VersionCodes.ALL_VERSION_CODES;
        com.android.server.pm.PerPackageReadTimeouts.Timeouts parse = com.android.server.pm.PerPackageReadTimeouts.Timeouts.parse(str);
        java.lang.String[] split = str2.split(",");
        java.util.ArrayList arrayList = new java.util.ArrayList(split.length);
        for (java.lang.String str3 : split) {
            com.android.server.pm.PerPackageReadTimeouts parse2 = parse(str3, versionCodes, parse);
            if (parse2 != null) {
                arrayList.add(parse2);
            }
        }
        return arrayList;
    }
}
