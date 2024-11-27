package android.media;

/* loaded from: classes2.dex */
class ExifInterfaceUtils {
    private static final java.lang.String TAG = "ExifInterface";

    ExifInterfaceUtils() {
    }

    public static int copy(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        byte[] bArr = new byte[8192];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                i += read;
                outputStream.write(bArr, 0, read);
            } else {
                return i;
            }
        }
    }

    public static void copy(java.io.InputStream inputStream, java.io.OutputStream outputStream, int i) throws java.io.IOException {
        byte[] bArr = new byte[8192];
        while (i > 0) {
            int min = java.lang.Math.min(i, 8192);
            int read = inputStream.read(bArr, 0, min);
            if (read != min) {
                throw new java.io.IOException("Failed to copy the given amount of bytes from the inputstream to the output stream.");
            }
            i -= read;
            outputStream.write(bArr, 0, read);
        }
    }

    public static long[] convertToLongArray(java.lang.Object obj) {
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            long[] jArr = new long[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                jArr[i] = iArr[i];
            }
            return jArr;
        }
        if (obj instanceof long[]) {
            return (long[]) obj;
        }
        return null;
    }

    public static java.lang.String byteArrayToHexString(byte[] bArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(java.lang.String.format("%02x", java.lang.Byte.valueOf(b)));
        }
        return sb.toString();
    }

    public static boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length < bArr2.length || bArr.length == 0 || bArr2.length == 0) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void closeQuietly(java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.lang.RuntimeException e) {
                throw e;
            } catch (java.lang.Exception e2) {
            }
        }
    }

    public static void closeFileDescriptor(java.io.FileDescriptor fileDescriptor) {
        try {
            android.system.Os.close(fileDescriptor);
        } catch (android.system.ErrnoException e) {
            android.util.Log.e(TAG, "Error closing fd.", e);
        }
    }
}
