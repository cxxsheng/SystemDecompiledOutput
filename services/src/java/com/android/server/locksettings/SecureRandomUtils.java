package com.android.server.locksettings;

/* loaded from: classes2.dex */
public class SecureRandomUtils {
    private static final java.security.SecureRandom RNG = new java.security.SecureRandom();

    public static byte[] randomBytes(int i) {
        byte[] bArr = new byte[i];
        RNG.nextBytes(bArr);
        return bArr;
    }

    public static long randomLong() {
        return RNG.nextLong();
    }
}
