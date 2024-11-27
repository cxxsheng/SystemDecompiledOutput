package com.android.server.compat.overrides;

/* loaded from: classes.dex */
final class AppCompatOverridesParser {
    private static final java.util.regex.Pattern BOOLEAN_PATTERN = java.util.regex.Pattern.compile("true|false", 2);
    static final java.lang.String FLAG_OWNED_CHANGE_IDS = "owned_change_ids";
    static final java.lang.String FLAG_REMOVE_OVERRIDES = "remove_overrides";
    private static final java.lang.String TAG = "AppCompatOverridesParser";
    private static final java.lang.String WILDCARD_NO_OWNED_CHANGE_IDS_WARNING = "Wildcard can't be used in 'remove_overrides' flag with an empty owned_change_ids' flag";
    private static final java.lang.String WILDCARD_SYMBOL = "*";
    private final android.content.pm.PackageManager mPackageManager;

    AppCompatOverridesParser(android.content.pm.PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    java.util.Map<java.lang.String, java.util.Set<java.lang.Long>> parseRemoveOverrides(java.lang.String str, java.util.Set<java.lang.Long> set) {
        if (str.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        if (str.equals("*")) {
            if (set.isEmpty()) {
                android.util.Slog.w(TAG, WILDCARD_NO_OWNED_CHANGE_IDS_WARNING);
                return java.util.Collections.emptyMap();
            }
            java.util.Iterator<android.content.pm.ApplicationInfo> it = this.mPackageManager.getInstalledApplications(4194304).iterator();
            while (it.hasNext()) {
                arrayMap.put(it.next().packageName, set);
            }
            return arrayMap;
        }
        android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
        try {
            keyValueListParser.setString(str);
            for (int i = 0; i < keyValueListParser.size(); i++) {
                java.lang.String keyAt = keyValueListParser.keyAt(i);
                java.lang.String string = keyValueListParser.getString(keyAt, "");
                if (!string.equals("*")) {
                    for (java.lang.String str2 : string.split(":")) {
                        try {
                            ((java.util.Set) arrayMap.computeIfAbsent(keyAt, new java.util.function.Function() { // from class: com.android.server.compat.overrides.AppCompatOverridesParser$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final java.lang.Object apply(java.lang.Object obj) {
                                    java.util.Set lambda$parseRemoveOverrides$0;
                                    lambda$parseRemoveOverrides$0 = com.android.server.compat.overrides.AppCompatOverridesParser.lambda$parseRemoveOverrides$0((java.lang.String) obj);
                                    return lambda$parseRemoveOverrides$0;
                                }
                            })).add(java.lang.Long.valueOf(java.lang.Long.parseLong(str2)));
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.w(TAG, "Invalid change ID in 'remove_overrides' flag: " + str2, e);
                        }
                    }
                } else if (set.isEmpty()) {
                    android.util.Slog.w(TAG, WILDCARD_NO_OWNED_CHANGE_IDS_WARNING);
                } else {
                    arrayMap.put(keyAt, set);
                }
            }
            return arrayMap;
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Slog.w(TAG, "Invalid format in 'remove_overrides' flag: " + str, e2);
            return java.util.Collections.emptyMap();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$parseRemoveOverrides$0(java.lang.String str) {
        return new android.util.ArraySet();
    }

    static java.util.Set<java.lang.Long> parseOwnedChangeIds(java.lang.String str) {
        if (str.isEmpty()) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str2 : str.split(",")) {
            try {
                arraySet.add(java.lang.Long.valueOf(java.lang.Long.parseLong(str2)));
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.w(TAG, "Invalid change ID in 'owned_change_ids' flag: " + str2, e);
            }
        }
        return arraySet;
    }

    java.util.Map<java.lang.Long, android.app.compat.PackageOverride> parsePackageOverrides(java.lang.String str, java.lang.String str2, long j, java.util.Set<java.lang.Long> set) {
        if (str.isEmpty()) {
            return java.util.Collections.emptyMap();
        }
        com.android.server.compat.overrides.AppCompatOverridesParser.PackageOverrideComparator packageOverrideComparator = new com.android.server.compat.overrides.AppCompatOverridesParser.PackageOverrideComparator(j);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.Pair<java.lang.String, java.lang.String> extractSignatureFromConfig = extractSignatureFromConfig(str);
        if (extractSignatureFromConfig == null) {
            return java.util.Collections.emptyMap();
        }
        java.lang.String str3 = (java.lang.String) extractSignatureFromConfig.first;
        java.lang.String str4 = (java.lang.String) extractSignatureFromConfig.second;
        if (!verifySignature(str2, str3)) {
            return java.util.Collections.emptyMap();
        }
        for (java.lang.String str5 : str4.split(",")) {
            java.util.List asList = java.util.Arrays.asList(str5.split(":", 4));
            if (asList.size() != 4) {
                android.util.Slog.w(TAG, "Invalid change override entry: " + str5);
            } else {
                try {
                    long parseLong = java.lang.Long.parseLong((java.lang.String) asList.get(0));
                    if (!set.contains(java.lang.Long.valueOf(parseLong))) {
                        java.lang.String str6 = (java.lang.String) asList.get(1);
                        java.lang.String str7 = (java.lang.String) asList.get(2);
                        java.lang.String str8 = (java.lang.String) asList.get(3);
                        if (BOOLEAN_PATTERN.matcher(str8).matches()) {
                            android.app.compat.PackageOverride.Builder enabled = new android.app.compat.PackageOverride.Builder().setEnabled(java.lang.Boolean.parseBoolean(str8));
                            try {
                                if (!str6.isEmpty()) {
                                    enabled.setMinVersionCode(java.lang.Long.parseLong(str6));
                                }
                                if (!str7.isEmpty()) {
                                    enabled.setMaxVersionCode(java.lang.Long.parseLong(str7));
                                }
                                try {
                                    android.app.compat.PackageOverride build = enabled.build();
                                    if (arrayMap.containsKey(java.lang.Long.valueOf(parseLong)) && packageOverrideComparator.compare(build, (android.app.compat.PackageOverride) arrayMap.get(java.lang.Long.valueOf(parseLong))) >= 0) {
                                    }
                                    arrayMap.put(java.lang.Long.valueOf(parseLong), build);
                                } catch (java.lang.IllegalArgumentException e) {
                                    android.util.Slog.w(TAG, "Failed to build PackageOverride", e);
                                }
                            } catch (java.lang.NumberFormatException e2) {
                                android.util.Slog.w(TAG, "Invalid min/max version code in override entry: " + str5, e2);
                            }
                        } else {
                            android.util.Slog.w(TAG, "Invalid enabled string in override entry: " + str5);
                        }
                    }
                } catch (java.lang.NumberFormatException e3) {
                    android.util.Slog.w(TAG, "Invalid change ID in override entry: " + str5, e3);
                }
            }
        }
        return arrayMap;
    }

    @android.annotation.Nullable
    private static android.util.Pair<java.lang.String, java.lang.String> extractSignatureFromConfig(java.lang.String str) {
        java.util.List asList = java.util.Arrays.asList(str.split("~"));
        if (asList.size() == 1) {
            return android.util.Pair.create("", str);
        }
        if (asList.size() > 2) {
            android.util.Slog.w(TAG, "Only one signature per config is supported. Config: " + str);
            return null;
        }
        return android.util.Pair.create((java.lang.String) asList.get(0), (java.lang.String) asList.get(1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001e, code lost:
    
        android.util.Slog.w(com.android.server.compat.overrides.AppCompatOverridesParser.TAG, r6 + " did not have expected signature: " + r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean verifySignature(java.lang.String str, java.lang.String str2) {
        try {
            boolean z = true;
            if (!str2.isEmpty() && !this.mPackageManager.hasSigningCertificate(str, libcore.util.HexEncoding.decode(str2), 1)) {
                z = false;
            }
            return z;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Unable to verify signature " + str2 + " for " + str, e);
            return false;
        }
    }

    private static final class PackageOverrideComparator implements java.util.Comparator<android.app.compat.PackageOverride> {
        private final long mVersionCode;

        PackageOverrideComparator(long j) {
            this.mVersionCode = j;
        }

        @Override // java.util.Comparator
        public int compare(android.app.compat.PackageOverride packageOverride, android.app.compat.PackageOverride packageOverride2) {
            boolean isVersionInRange = isVersionInRange(packageOverride, this.mVersionCode);
            if (isVersionInRange != isVersionInRange(packageOverride2, this.mVersionCode)) {
                return isVersionInRange ? -1 : 1;
            }
            boolean isVersionAfterRange = isVersionAfterRange(packageOverride, this.mVersionCode);
            if (isVersionAfterRange != isVersionAfterRange(packageOverride2, this.mVersionCode)) {
                return isVersionAfterRange ? -1 : 1;
            }
            return java.lang.Long.compare(getVersionProximity(packageOverride, this.mVersionCode), getVersionProximity(packageOverride2, this.mVersionCode));
        }

        private static boolean isVersionInRange(android.app.compat.PackageOverride packageOverride, long j) {
            return packageOverride.getMinVersionCode() <= j && j <= packageOverride.getMaxVersionCode();
        }

        private static boolean isVersionAfterRange(android.app.compat.PackageOverride packageOverride, long j) {
            return packageOverride.getMaxVersionCode() < j;
        }

        private static boolean isVersionBeforeRange(android.app.compat.PackageOverride packageOverride, long j) {
            return packageOverride.getMinVersionCode() > j;
        }

        private static long getVersionProximity(android.app.compat.PackageOverride packageOverride, long j) {
            if (isVersionAfterRange(packageOverride, j)) {
                return j - packageOverride.getMaxVersionCode();
            }
            if (isVersionBeforeRange(packageOverride, j)) {
                return packageOverride.getMinVersionCode() - j;
            }
            return 0L;
        }
    }
}
