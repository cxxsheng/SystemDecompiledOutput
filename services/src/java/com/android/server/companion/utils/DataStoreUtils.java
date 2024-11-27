package com.android.server.companion.utils;

/* loaded from: classes.dex */
public final class DataStoreUtils {
    private static final java.lang.String TAG = "CDM_DataStoreUtils";

    public static boolean isStartOfTag(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        return xmlPullParser.getEventType() == 2 && str.equals(xmlPullParser.getName());
    }

    public static boolean isEndOfTag(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        return xmlPullParser.getEventType() == 3 && str.equals(xmlPullParser.getName());
    }

    @android.annotation.NonNull
    public static android.util.AtomicFile createStorageFileForUser(int i, java.lang.String str) {
        return new android.util.AtomicFile(getBaseStorageFileForUser(i, str));
    }

    @android.annotation.NonNull
    private static java.io.File getBaseStorageFileForUser(int i, java.lang.String str) {
        return new java.io.File(android.os.Environment.getDataSystemDeDirectory(i), str);
    }

    public static void writeToFileSafely(@android.annotation.NonNull android.util.AtomicFile atomicFile, @android.annotation.NonNull com.android.internal.util.FunctionalUtils.ThrowingConsumer<java.io.FileOutputStream> throwingConsumer) {
        try {
            atomicFile.write(throwingConsumer);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error while writing to file " + atomicFile, e);
        }
    }

    @android.annotation.NonNull
    public static byte[] fileToByteArray(@android.annotation.NonNull android.util.AtomicFile atomicFile) {
        if (!atomicFile.getBaseFile().exists()) {
            android.util.Slog.d(TAG, "File does not exist");
            return new byte[0];
        }
        try {
            java.io.FileInputStream openRead = atomicFile.openRead();
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openRead.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        openRead.close();
                        return byteArray;
                    }
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error while reading requests file", e);
            return new byte[0];
        }
    }

    private DataStoreUtils() {
    }
}
