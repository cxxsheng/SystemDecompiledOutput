package android.content.pm.dex;

/* loaded from: classes.dex */
public class DexMetadataHelper {
    private static final java.lang.String DEX_METADATA_FILE_EXTENSION = ".dm";
    private static final java.lang.String PROPERTY_DM_FSVERITY_REQUIRED = "pm.dexopt.dm.require_fsverity";
    private static final java.lang.String PROPERTY_DM_JSON_MANIFEST_REQUIRED = "pm.dexopt.dm.require_manifest";
    public static final java.lang.String TAG = "DexMetadataHelper";
    public static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    private DexMetadataHelper() {
    }

    public static boolean isDexMetadataFile(java.io.File file) {
        return isDexMetadataPath(file.getName());
    }

    private static boolean isDexMetadataPath(java.lang.String str) {
        return str.endsWith(".dm");
    }

    public static boolean isFsVerityRequired() {
        return com.android.internal.security.VerityUtils.isFsVeritySupported() && android.os.SystemProperties.getBoolean(PROPERTY_DM_FSVERITY_REQUIRED, false);
    }

    public static long getPackageDexMetadataSize(android.content.pm.parsing.PackageLite packageLite) {
        java.util.Iterator<java.lang.String> it = getPackageDexMetadata(packageLite).values().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += new java.io.File(it.next()).length();
        }
        return j;
    }

    public static java.io.File findDexMetadataForFile(java.io.File file) {
        java.io.File file2 = new java.io.File(buildDexMetadataPathForFile(file));
        if (file2.exists()) {
            return file2;
        }
        return null;
    }

    private static java.util.Map<java.lang.String, java.lang.String> getPackageDexMetadata(android.content.pm.parsing.PackageLite packageLite) {
        return buildPackageApkToDexMetadataMap(packageLite.getAllApkPaths());
    }

    public static java.util.Map<java.lang.String, java.lang.String> buildPackageApkToDexMetadataMap(java.util.List<java.lang.String> list) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int size = list.size() - 1; size >= 0; size--) {
            java.lang.String str = list.get(size);
            java.lang.String buildDexMetadataPathForFile = buildDexMetadataPathForFile(new java.io.File(str));
            if (java.nio.file.Files.exists(java.nio.file.Paths.get(buildDexMetadataPathForFile, new java.lang.String[0]), new java.nio.file.LinkOption[0])) {
                arrayMap.put(str, buildDexMetadataPathForFile);
            }
        }
        return arrayMap;
    }

    public static java.lang.String buildDexMetadataPathForApk(java.lang.String str) {
        if (!android.content.pm.parsing.ApkLiteParseUtils.isApkPath(str)) {
            throw new java.lang.IllegalStateException("Corrupted package. Code path is not an apk " + str);
        }
        return str.substring(0, str.length() - ".apk".length()) + ".dm";
    }

    private static java.lang.String buildDexMetadataPathForFile(java.io.File file) {
        if (android.content.pm.parsing.ApkLiteParseUtils.isApkFile(file)) {
            return buildDexMetadataPathForApk(file.getPath());
        }
        return file.getPath() + ".dm";
    }

    public static android.content.pm.parsing.result.ParseResult validateDexMetadataFile(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, java.lang.String str2, long j) {
        return validateDexMetadataFile(parseInput, str, str2, j, android.os.SystemProperties.getBoolean(PROPERTY_DM_JSON_MANIFEST_REQUIRED, false));
    }

    public static android.content.pm.parsing.result.ParseResult validateDexMetadataFile(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, java.lang.String str2, long j, boolean z) {
        if (DEBUG) {
            android.util.Log.v(TAG, "validateDexMetadataFile: " + str + ", " + str2 + ", " + j);
        }
        android.util.jar.StrictJarFile strictJarFile = null;
        try {
            try {
                android.util.jar.StrictJarFile strictJarFile2 = new android.util.jar.StrictJarFile(str, false, false);
                try {
                    android.content.pm.parsing.result.ParseResult validateDexMetadataManifest = validateDexMetadataManifest(parseInput, str, strictJarFile2, str2, j, z);
                    try {
                        strictJarFile2.close();
                    } catch (java.io.IOException e) {
                    }
                    return validateDexMetadataManifest;
                } catch (java.io.IOException e2) {
                    e = e2;
                    strictJarFile = strictJarFile2;
                    android.content.pm.parsing.result.ParseResult error = parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Error opening " + str, e);
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                    return error;
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
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e5) {
            e = e5;
        }
    }

    private static android.content.pm.parsing.result.ParseResult validateDexMetadataManifest(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, android.util.jar.StrictJarFile strictJarFile, java.lang.String str2, long j, boolean z) throws java.io.IOException {
        if (!z) {
            if (DEBUG) {
                android.util.Log.v(TAG, "validateDexMetadataManifest: " + str + " manifest.json check skipped");
            }
            return parseInput.success(null);
        }
        java.util.zip.ZipEntry findEntry = strictJarFile.findEntry("manifest.json");
        if (findEntry == null) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Missing manifest.json in " + str);
        }
        try {
            android.util.JsonReader jsonReader = new android.util.JsonReader(new java.io.InputStreamReader(strictJarFile.getInputStream(findEntry), android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8));
            jsonReader.beginObject();
            java.lang.String str3 = null;
            long j2 = -1;
            while (jsonReader.hasNext()) {
                java.lang.String nextName = jsonReader.nextName();
                if (nextName.equals("packageName")) {
                    str3 = jsonReader.nextString();
                } else if (nextName.equals("versionCode")) {
                    j2 = jsonReader.nextLong();
                } else {
                    jsonReader.skipValue();
                }
            }
            jsonReader.endObject();
            if (str3 == null || j2 == -1) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " is missing 'packageName' and/or 'versionCode'");
            }
            if (!str3.equals(str2)) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " has invalid packageName: " + str3 + ", expected: " + str2);
            }
            if (j != j2) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "manifest.json in " + str + " has invalid versionCode: " + j2 + ", expected: " + j);
            }
            if (DEBUG) {
                android.util.Log.v(TAG, "validateDexMetadataManifest: " + str + ", " + str2 + ", " + j + ": successful");
            }
            return parseInput.success(null);
        } catch (java.io.UnsupportedEncodingException e) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_FAILED_BAD_DEX_METADATA, "Error opening manifest.json in " + str, e);
        }
    }

    public static void validateDexPaths(java.lang.String[] strArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < strArr.length; i++) {
            if (android.content.pm.parsing.ApkLiteParseUtils.isApkPath(strArr[i])) {
                arrayList.add(strArr[i]);
            }
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (java.lang.String str : strArr) {
            if (isDexMetadataPath(str)) {
                boolean z = true;
                int size = arrayList.size() - 1;
                while (true) {
                    if (size < 0) {
                        z = false;
                        break;
                    } else if (str.equals(buildDexMetadataPathForFile(new java.io.File((java.lang.String) arrayList.get(size))))) {
                        break;
                    } else {
                        size--;
                    }
                }
                if (!z) {
                    arrayList2.add(str);
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            throw new java.lang.IllegalStateException("Unmatched .dm files: " + arrayList2);
        }
    }
}
