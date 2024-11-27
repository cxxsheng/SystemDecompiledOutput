package com.android.server.biometrics;

/* loaded from: classes.dex */
public class HardwareAuthTokenUtils {
    public static byte[] toByteArray(android.hardware.keymaster.HardwareAuthToken hardwareAuthToken) {
        byte[] bArr = new byte[69];
        bArr[0] = 0;
        writeLong(hardwareAuthToken.challenge, bArr, 1);
        writeLong(hardwareAuthToken.userId, bArr, 9);
        writeLong(hardwareAuthToken.authenticatorId, bArr, 17);
        writeInt(flipIfNativelyLittle(hardwareAuthToken.authenticatorType), bArr, 25);
        writeLong(flipIfNativelyLittle(hardwareAuthToken.timestamp.milliSeconds), bArr, 29);
        java.lang.System.arraycopy(hardwareAuthToken.mac, 0, bArr, 37, hardwareAuthToken.mac.length);
        return bArr;
    }

    public static android.hardware.keymaster.HardwareAuthToken toHardwareAuthToken(byte[] bArr) {
        android.hardware.keymaster.HardwareAuthToken hardwareAuthToken = new android.hardware.keymaster.HardwareAuthToken();
        hardwareAuthToken.challenge = getLong(bArr, 1);
        hardwareAuthToken.userId = getLong(bArr, 9);
        hardwareAuthToken.authenticatorId = getLong(bArr, 17);
        hardwareAuthToken.authenticatorType = flipIfNativelyLittle(getInt(bArr, 25));
        android.hardware.keymaster.Timestamp timestamp = new android.hardware.keymaster.Timestamp();
        timestamp.milliSeconds = flipIfNativelyLittle(getLong(bArr, 29));
        hardwareAuthToken.timestamp = timestamp;
        hardwareAuthToken.mac = new byte[32];
        java.lang.System.arraycopy(bArr, 37, hardwareAuthToken.mac, 0, 32);
        return hardwareAuthToken;
    }

    private static long flipIfNativelyLittle(long j) {
        if (java.nio.ByteOrder.LITTLE_ENDIAN == java.nio.ByteOrder.nativeOrder()) {
            return java.lang.Long.reverseBytes(j);
        }
        return j;
    }

    private static int flipIfNativelyLittle(int i) {
        if (java.nio.ByteOrder.LITTLE_ENDIAN == java.nio.ByteOrder.nativeOrder()) {
            return java.lang.Integer.reverseBytes(i);
        }
        return i;
    }

    private static void writeLong(long j, byte[] bArr, int i) {
        bArr[i + 0] = (byte) j;
        bArr[i + 1] = (byte) (j >> 8);
        bArr[i + 2] = (byte) (j >> 16);
        bArr[i + 3] = (byte) (j >> 24);
        bArr[i + 4] = (byte) (j >> 32);
        bArr[i + 5] = (byte) (j >> 40);
        bArr[i + 6] = (byte) (j >> 48);
        bArr[i + 7] = (byte) (j >> 56);
    }

    private static void writeInt(int i, byte[] bArr, int i2) {
        bArr[i2 + 0] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
    }

    private static long getLong(byte[] bArr, int i) {
        long j = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            j += (bArr[i2 + i] & 255) << (i2 * 8);
        }
        return j;
    }

    private static int getInt(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            i2 += (bArr[i3 + i] & 255) << (i3 * 8);
        }
        return i2;
    }
}
