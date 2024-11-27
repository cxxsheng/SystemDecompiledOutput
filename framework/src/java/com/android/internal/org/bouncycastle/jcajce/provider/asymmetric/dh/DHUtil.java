package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
class DHUtil {
    DHUtil() {
    }

    static java.lang.String privateKeyToString(java.lang.String str, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        java.math.BigInteger modPow = dHParameters.getG().modPow(bigInteger, dHParameters.getP());
        stringBuffer.append(str);
        stringBuffer.append(" Private Key [").append(generateKeyFingerprint(modPow, dHParameters)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("              Y: ").append(modPow.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    static java.lang.String publicKeyToString(java.lang.String str, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append(str);
        stringBuffer.append(" Public Key [").append(generateKeyFingerprint(bigInteger, dHParameters)).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).append(lineSeparator);
        stringBuffer.append("             Y: ").append(bigInteger.toString(16)).append(lineSeparator);
        return stringBuffer.toString();
    }

    private static java.lang.String generateKeyFingerprint(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        return new com.android.internal.org.bouncycastle.util.Fingerprint(com.android.internal.org.bouncycastle.util.Arrays.concatenate(bigInteger.toByteArray(), dHParameters.getP().toByteArray(), dHParameters.getG().toByteArray())).toString();
    }
}
