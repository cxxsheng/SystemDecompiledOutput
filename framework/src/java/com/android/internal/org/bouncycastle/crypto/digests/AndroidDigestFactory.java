package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public final class AndroidDigestFactory {
    private static final com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface BC = new com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryBouncyCastle();
    private static final com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface CONSCRYPT;

    static {
        if (java.security.Security.getProvider("AndroidOpenSSL") != null) {
            CONSCRYPT = new com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryOpenSSL();
        } else {
            if (java.lang.System.getProperty("java.vendor", "").toLowerCase(java.util.Locale.US).contains("android")) {
                throw new java.lang.AssertionError("Provider AndroidOpenSSL must exist");
            }
            CONSCRYPT = null;
        }
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getMD5() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getMD5();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getMD5();
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getSHA1() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getSHA1();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getSHA1();
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getSHA224() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getSHA224();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getSHA224();
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getSHA256() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getSHA256();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getSHA256();
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getSHA384() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getSHA384();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getSHA384();
    }

    public static com.android.internal.org.bouncycastle.crypto.Digest getSHA512() {
        if (CONSCRYPT != null) {
            try {
                return CONSCRYPT.getSHA512();
            } catch (java.lang.Exception e) {
            }
        }
        return BC.getSHA512();
    }
}
