package com.android.internal.os;

/* loaded from: classes4.dex */
public final class ProcStatsUtil {
    private static final boolean DEBUG = false;
    private static final int READ_SIZE = 1024;
    private static final java.lang.String TAG = "ProcStatsUtil";

    private ProcStatsUtil() {
    }

    public static java.lang.String readNullSeparatedFile(java.lang.String str) {
        java.lang.String readSingleLineProcFile = readSingleLineProcFile(str);
        if (readSingleLineProcFile == null) {
            return null;
        }
        int indexOf = readSingleLineProcFile.indexOf("\u0000\u0000");
        if (indexOf != -1) {
            readSingleLineProcFile = readSingleLineProcFile.substring(0, indexOf);
        }
        return readSingleLineProcFile.replace("\u0000", " ");
    }

    public static java.lang.String readSingleLineProcFile(java.lang.String str) {
        return readTerminatedProcFile(str, (byte) 10);
    }

    public static java.lang.String readTerminatedProcFile(java.lang.String str, byte b) {
        int allowThreadDiskReadsMask = android.os.StrictMode.allowThreadDiskReadsMask();
        try {
            return readTerminatedProcFileInternal(str, b);
        } finally {
            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskReadsMask);
        }
    }

    private static java.lang.String readTerminatedProcFileInternal(java.lang.String str, byte b) {
        boolean z;
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str);
            try {
                byte[] bArr = new byte[1024];
                java.io.ByteArrayOutputStream byteArrayOutputStream = null;
                do {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    int i = 0;
                    while (true) {
                        if (i >= read) {
                            i = -1;
                            break;
                        }
                        if (bArr[i] == b) {
                            break;
                        }
                        i++;
                    }
                    z = i != -1;
                    if (z && byteArrayOutputStream == null) {
                        java.lang.String str2 = new java.lang.String(bArr, 0, i);
                        fileInputStream.close();
                        return str2;
                    }
                    if (byteArrayOutputStream == null) {
                        byteArrayOutputStream = new java.io.ByteArrayOutputStream(1024);
                    }
                    if (z) {
                        read = i;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                } while (!z);
                if (byteArrayOutputStream == null) {
                    fileInputStream.close();
                    return "";
                }
                java.lang.String obj = byteArrayOutputStream.toString();
                fileInputStream.close();
                return obj;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }
}
