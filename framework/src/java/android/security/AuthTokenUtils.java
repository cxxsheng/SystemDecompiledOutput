package android.security;

/* loaded from: classes3.dex */
public class AuthTokenUtils {
    private AuthTokenUtils() {
    }

    public static android.hardware.security.keymint.HardwareAuthToken toHardwareAuthToken(byte[] bArr) {
        android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken = new android.hardware.security.keymint.HardwareAuthToken();
        hardwareAuthToken.challenge = java.nio.ByteBuffer.wrap(bArr, 1, 8).order(java.nio.ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.userId = java.nio.ByteBuffer.wrap(bArr, 9, 8).order(java.nio.ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.authenticatorId = java.nio.ByteBuffer.wrap(bArr, 17, 8).order(java.nio.ByteOrder.nativeOrder()).getLong();
        hardwareAuthToken.authenticatorType = java.nio.ByteBuffer.wrap(bArr, 25, 4).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
        android.hardware.security.secureclock.Timestamp timestamp = new android.hardware.security.secureclock.Timestamp();
        timestamp.milliSeconds = java.nio.ByteBuffer.wrap(bArr, 29, 8).order(java.nio.ByteOrder.BIG_ENDIAN).getLong();
        hardwareAuthToken.timestamp = timestamp;
        hardwareAuthToken.mac = new byte[32];
        java.lang.System.arraycopy(bArr, 37, hardwareAuthToken.mac, 0, 32);
        return hardwareAuthToken;
    }
}
