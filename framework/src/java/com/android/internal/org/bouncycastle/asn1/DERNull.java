package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class DERNull extends com.android.internal.org.bouncycastle.asn1.ASN1Null {
    public static final com.android.internal.org.bouncycastle.asn1.DERNull INSTANCE = new com.android.internal.org.bouncycastle.asn1.DERNull();
    private static final byte[] zeroBytes = new byte[0];

    private DERNull() {
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return false;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() {
        return 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Null, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncoded(z, 5, zeroBytes);
    }
}
