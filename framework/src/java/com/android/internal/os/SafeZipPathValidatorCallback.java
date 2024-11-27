package com.android.internal.os;

/* loaded from: classes4.dex */
public class SafeZipPathValidatorCallback implements dalvik.system.ZipPathValidator.Callback {
    public static final long VALIDATE_ZIP_PATH_FOR_PATH_TRAVERSAL = 242716250;

    @Override // dalvik.system.ZipPathValidator.Callback
    public void onZipEntryAccess(java.lang.String str) throws java.util.zip.ZipException {
        if (str.startsWith("/")) {
            throw new java.util.zip.ZipException("Invalid zip entry path: " + str);
        }
        if (str.contains("..")) {
            for (java.io.File file = new java.io.File(str); file != null; file = file.getParentFile()) {
                if (file.getName().equals("..")) {
                    throw new java.util.zip.ZipException("Invalid zip entry path: " + str);
                }
            }
        }
    }
}
