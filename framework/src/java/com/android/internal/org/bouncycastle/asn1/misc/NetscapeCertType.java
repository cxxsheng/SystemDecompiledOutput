package com.android.internal.org.bouncycastle.asn1.misc;

/* loaded from: classes4.dex */
public class NetscapeCertType extends com.android.internal.org.bouncycastle.asn1.DERBitString {
    public static final int objectSigning = 16;
    public static final int objectSigningCA = 1;
    public static final int reserved = 8;
    public static final int smime = 32;
    public static final int smimeCA = 2;
    public static final int sslCA = 4;
    public static final int sslClient = 128;
    public static final int sslServer = 64;

    public NetscapeCertType(int i) {
        super(getBytes(i), getPadBits(i));
    }

    public NetscapeCertType(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        super(dERBitString.getBytes(), dERBitString.getPadBits());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1BitString
    public java.lang.String toString() {
        return "NetscapeCertType: 0x" + java.lang.Integer.toHexString(this.data[0] & 255);
    }
}
