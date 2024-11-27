package com.android.internal.org.bouncycastle.jcajce.io;

/* loaded from: classes4.dex */
public class OutputStreamFactory {
    public static java.io.OutputStream createStream(java.security.Signature signature) {
        return new com.android.internal.org.bouncycastle.jcajce.io.SignatureUpdatingOutputStream(signature);
    }

    public static java.io.OutputStream createStream(java.security.MessageDigest messageDigest) {
        return new com.android.internal.org.bouncycastle.jcajce.io.DigestUpdatingOutputStream(messageDigest);
    }

    public static java.io.OutputStream createStream(javax.crypto.Mac mac) {
        return new com.android.internal.org.bouncycastle.jcajce.io.MacUpdatingOutputStream(mac);
    }
}
