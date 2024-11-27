package com.android.internal.content;

/* loaded from: classes4.dex */
public final class F2fsUtils {
    private static final java.lang.String COMPRESSION_FEATURE = "compression";
    private static final boolean DEBUG_F2FS = false;
    private static final java.lang.String TAG = "F2fsUtils";
    private static final java.io.File sKernelFeatures = new java.io.File("/sys/fs/f2fs/features");
    private static final java.io.File sUserDataFeatures = new java.io.File("/dev/sys/fs/by-name/userdata/features");
    private static final java.io.File sDataDirectory = android.os.Environment.getDataDirectory();
    private static final boolean sKernelCompressionAvailable = isCompressionEnabledInKernel();
    private static final boolean sUserDataCompressionAvailable = isCompressionEnabledOnUserData();

    private static native long nativeReleaseCompressedBlocks(java.lang.String str);

    public static void releaseCompressedBlocks(android.content.ContentResolver contentResolver, java.io.File file) {
        java.io.File[] filesToRelease;
        if (!sKernelCompressionAvailable || !sUserDataCompressionAvailable) {
            return;
        }
        if (!(android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.RELEASE_COMPRESS_BLOCKS_ON_INSTALL, 1) != 0) || !isCompressionAllowed(file) || (filesToRelease = getFilesToRelease(file)) == null || filesToRelease.length == 0) {
            return;
        }
        for (int length = filesToRelease.length - 1; length >= 0; length--) {
            nativeReleaseCompressedBlocks(filesToRelease[length].getAbsolutePath());
        }
    }

    private static boolean isCompressionAllowed(java.io.File file) {
        try {
            java.lang.String canonicalPath = file.getCanonicalPath();
            if (android.os.incremental.IncrementalManager.isIncrementalPath(canonicalPath) || !isChild(sDataDirectory, canonicalPath)) {
                return false;
            }
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    private static boolean isChild(java.io.File file, java.lang.String str) {
        try {
            java.io.File canonicalFile = file.getCanonicalFile();
            for (java.io.File canonicalFile2 = new java.io.File(str).getCanonicalFile(); canonicalFile2 != null; canonicalFile2 = canonicalFile2.getParentFile()) {
                if (canonicalFile.equals(canonicalFile2)) {
                    return true;
                }
            }
            return false;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    private static boolean isCompressionEnabledInKernel() {
        java.io.File[] listFiles = sKernelFeatures.listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return false;
        }
        for (int length = listFiles.length - 1; length >= 0; length--) {
            java.io.File file = listFiles[length];
            if (COMPRESSION_FEATURE.equals(listFiles[length].getName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCompressionEnabledOnUserData() {
        if (!sUserDataFeatures.exists() || !sUserDataFeatures.isFile() || !sUserDataFeatures.canRead()) {
            return false;
        }
        try {
            java.util.List<java.lang.String> readAllLines = java.nio.file.Files.readAllLines(sUserDataFeatures.toPath());
            if (readAllLines == null || readAllLines.size() > 1 || android.text.TextUtils.isEmpty(readAllLines.get(0))) {
                return false;
            }
            java.lang.String[] split = readAllLines.get(0).split(",");
            for (int length = split.length - 1; length >= 0; length--) {
                if (COMPRESSION_FEATURE.equals(split[length].trim())) {
                    return true;
                }
            }
            return false;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    private static java.util.List<java.io.File> getFilesRecursive(java.io.File file) {
        java.io.File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.io.File file2 : listFiles) {
            if (file2.isDirectory()) {
                arrayList.addAll(getFilesRecursive(file2));
            } else if (file2.isFile()) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    private static java.io.File[] getFilesToRelease(java.io.File file) {
        java.util.List<java.io.File> filesRecursive = getFilesRecursive(file);
        if (filesRecursive == null) {
            if (!file.isFile()) {
                return null;
            }
            return new java.io.File[]{file};
        }
        if (filesRecursive.size() == 0) {
            return null;
        }
        return (java.io.File[]) filesRecursive.toArray(new java.io.File[filesRecursive.size()]);
    }
}
