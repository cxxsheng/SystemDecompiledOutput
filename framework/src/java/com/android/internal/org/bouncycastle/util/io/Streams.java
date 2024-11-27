package com.android.internal.org.bouncycastle.util.io;

/* loaded from: classes4.dex */
public final class Streams {
    private static int BUFFER_SIZE = 4096;

    public static void drain(java.io.InputStream inputStream) throws java.io.IOException {
        int i = BUFFER_SIZE;
        while (inputStream.read(new byte[i], 0, i) >= 0) {
        }
    }

    public static byte[] readAll(java.io.InputStream inputStream) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        pipeAll(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAllLimited(java.io.InputStream inputStream, int i) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        pipeAllLimited(inputStream, i, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int readFully(java.io.InputStream inputStream, byte[] bArr) throws java.io.IOException {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static int readFully(java.io.InputStream inputStream, byte[] bArr, int i, int i2) throws java.io.IOException {
        int i3 = 0;
        while (i3 < i2) {
            int read = inputStream.read(bArr, i + i3, i2 - i3);
            if (read < 0) {
                break;
            }
            i3 += read;
        }
        return i3;
    }

    public static void pipeAll(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        int i = BUFFER_SIZE;
        byte[] bArr = new byte[i];
        while (true) {
            int read = inputStream.read(bArr, 0, i);
            if (read >= 0) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static long pipeAllLimited(java.io.InputStream inputStream, long j, java.io.OutputStream outputStream) throws java.io.IOException {
        int i = BUFFER_SIZE;
        byte[] bArr = new byte[i];
        long j2 = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, i);
            if (read < 0) {
                return j2;
            }
            long j3 = read;
            if (j - j2 < j3) {
                throw new com.android.internal.org.bouncycastle.util.io.StreamOverflowException("Data Overflow");
            }
            j2 += j3;
            outputStream.write(bArr, 0, read);
        }
    }

    public static void writeBufTo(java.io.ByteArrayOutputStream byteArrayOutputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        byteArrayOutputStream.writeTo(outputStream);
    }
}
