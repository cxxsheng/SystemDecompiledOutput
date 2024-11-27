package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class PBKDF2KeySpec extends javax.crypto.spec.PBEKeySpec {
    private static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier defaultPRF = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier prf;

    public PBKDF2KeySpec(char[] cArr, byte[] bArr, int i, int i2, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        super(cArr, bArr, i, i2);
        this.prf = algorithmIdentifier;
    }

    public boolean isDefaultPrf() {
        return defaultPRF.equals(this.prf);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getPrf() {
        return this.prf;
    }
}
