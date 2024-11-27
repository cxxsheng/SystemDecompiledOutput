package android.drm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DrmUtils {
    static byte[] readBytes(java.lang.String str) throws java.io.IOException {
        return readBytes(new java.io.File(str));
    }

    static byte[] readBytes(java.io.File file) throws java.io.IOException {
        byte[] bArr;
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(fileInputStream);
        try {
            int available = bufferedInputStream.available();
            if (available <= 0) {
                bArr = null;
            } else {
                bArr = new byte[available];
                bufferedInputStream.read(bArr);
            }
            return bArr;
        } finally {
            quietlyDispose(bufferedInputStream);
            quietlyDispose(fileInputStream);
        }
    }

    static void writeToFile(java.lang.String str, byte[] bArr) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream;
        if (str != null && bArr != null) {
            java.io.FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new java.io.FileOutputStream(str);
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                fileOutputStream.write(bArr);
                quietlyDispose(fileOutputStream);
            } catch (java.lang.Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                quietlyDispose(fileOutputStream2);
                throw th;
            }
        }
    }

    static void removeFile(java.lang.String str) throws java.io.IOException {
        new java.io.File(str).delete();
    }

    private static void quietlyDispose(java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.io.IOException e) {
            }
        }
    }

    public static android.drm.DrmUtils.ExtendedMetadataParser getExtendedMetadataParser(byte[] bArr) {
        return new android.drm.DrmUtils.ExtendedMetadataParser(bArr);
    }

    public static class ExtendedMetadataParser {
        java.util.HashMap<java.lang.String, java.lang.String> mMap;

        private int readByte(byte[] bArr, int i) {
            return bArr[i];
        }

        private java.lang.String readMultipleBytes(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i];
            int i3 = 0;
            int i4 = i2;
            while (i4 < i2 + i) {
                bArr2[i3] = bArr[i4];
                i4++;
                i3++;
            }
            return new java.lang.String(bArr2);
        }

        private ExtendedMetadataParser(byte[] bArr) {
            this.mMap = new java.util.HashMap<>();
            int i = 0;
            while (i < bArr.length) {
                int readByte = readByte(bArr, i);
                int i2 = i + 1;
                int readByte2 = readByte(bArr, i2);
                int i3 = i2 + 1;
                java.lang.String readMultipleBytes = readMultipleBytes(bArr, readByte, i3);
                int i4 = i3 + readByte;
                java.lang.String readMultipleBytes2 = readMultipleBytes(bArr, readByte2, i4);
                if (readMultipleBytes2.equals(" ")) {
                    readMultipleBytes2 = "";
                }
                i = i4 + readByte2;
                this.mMap.put(readMultipleBytes, readMultipleBytes2);
            }
        }

        public java.util.Iterator<java.lang.String> iterator() {
            return this.mMap.values().iterator();
        }

        public java.util.Iterator<java.lang.String> keyIterator() {
            return this.mMap.keySet().iterator();
        }

        public java.lang.String get(java.lang.String str) {
            return this.mMap.get(str);
        }
    }
}
