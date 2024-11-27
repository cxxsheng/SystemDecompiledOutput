package com.android.internal.content.om;

/* loaded from: classes4.dex */
public class OverlayScanner {
    private final android.util.ArrayMap<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> mParsedOverlayInfos = new android.util.ArrayMap<>();
    private final java.util.List<android.util.Pair<java.lang.String, java.io.File>> mExcludedOverlayPackages = new java.util.ArrayList();

    public static class ParsedOverlayInfo {
        public final boolean isStatic;
        public final java.lang.String packageName;
        public final java.io.File path;
        public final java.io.File preInstalledApexPath;
        public final int priority;
        public final java.lang.String targetPackageName;
        public final int targetSdkVersion;

        public ParsedOverlayInfo(java.lang.String str, java.lang.String str2, int i, boolean z, int i2, java.io.File file, java.io.File file2) {
            this.packageName = str;
            this.targetPackageName = str2;
            this.targetSdkVersion = i;
            this.isStatic = z;
            this.priority = i2;
            this.path = file;
            this.preInstalledApexPath = file2;
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + java.lang.String.format("{packageName=%s, targetPackageName=%s, targetSdkVersion=%s, isStatic=%s, priority=%s, path=%s, preInstalledApexPath=%s}", this.packageName, this.targetPackageName, java.lang.Integer.valueOf(this.targetSdkVersion), java.lang.Boolean.valueOf(this.isStatic), java.lang.Integer.valueOf(this.priority), this.path, this.preInstalledApexPath);
        }

        public java.io.File getOriginalPartitionPath() {
            return this.preInstalledApexPath != null ? this.preInstalledApexPath : this.path;
        }
    }

    public final com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo getParsedInfo(java.lang.String str) {
        return this.mParsedOverlayInfos.get(str);
    }

    final java.util.Collection<com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> getAllParsedInfos() {
        return this.mParsedOverlayInfos.values();
    }

    final boolean isExcludedOverlayPackage(java.lang.String str, com.android.internal.content.om.OverlayConfigParser.OverlayPartition overlayPartition) {
        for (int i = 0; i < this.mExcludedOverlayPackages.size(); i++) {
            android.util.Pair<java.lang.String, java.io.File> pair = this.mExcludedOverlayPackages.get(i);
            if (pair.first.equals(str) && overlayPartition.containsOverlay(pair.second)) {
                return true;
            }
        }
        return false;
    }

    public void scanDir(java.io.File file) {
        com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parseOverlayManifest;
        if (!file.exists() || !file.isDirectory()) {
            return;
        }
        if (!file.canRead()) {
            android.util.Log.w("OverlayConfig", "Directory " + file + " cannot be read");
            return;
        }
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        for (java.io.File file2 : listFiles) {
            if (file2.isDirectory()) {
                scanDir(file2);
            }
            if (file2.isFile() && file2.getPath().endsWith(".apk") && (parseOverlayManifest = parseOverlayManifest(file2, this.mExcludedOverlayPackages)) != null) {
                this.mParsedOverlayInfos.put(parseOverlayManifest.packageName, parseOverlayManifest);
            }
        }
    }

    public com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parseOverlayManifest(java.io.File file, java.util.List<android.util.Pair<java.lang.String, java.io.File>> list) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(android.content.pm.parsing.result.ParseTypeImpl.forParsingWithoutPlatformCompat().reset(), file, 128);
        if (parseApkLite.isError()) {
            android.util.Log.w("OverlayConfig", "Got exception loading overlay.", parseApkLite.getException());
            return null;
        }
        android.content.pm.parsing.ApkLite result = parseApkLite.getResult();
        if (result.getTargetPackageName() == null) {
            return null;
        }
        java.lang.String requiredSystemPropertyName = result.getRequiredSystemPropertyName();
        java.lang.String requiredSystemPropertyValue = result.getRequiredSystemPropertyValue();
        if ((!android.text.TextUtils.isEmpty(requiredSystemPropertyName) || !android.text.TextUtils.isEmpty(requiredSystemPropertyValue)) && !android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(requiredSystemPropertyName, requiredSystemPropertyValue)) {
            list.add(android.util.Pair.create(result.getPackageName(), file));
            return null;
        }
        return new com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo(result.getPackageName(), result.getTargetPackageName(), result.getTargetSdkVersion(), result.isOverlayIsStatic(), result.getOverlayPriority(), new java.io.File(result.getPath()), null);
    }
}
