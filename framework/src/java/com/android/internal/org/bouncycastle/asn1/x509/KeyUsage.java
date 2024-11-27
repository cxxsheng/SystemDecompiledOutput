package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class KeyUsage extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    public static final int cRLSign = 2;
    public static final int dataEncipherment = 16;
    public static final int decipherOnly = 32768;
    public static final int digitalSignature = 128;
    public static final int encipherOnly = 1;
    public static final int keyAgreement = 8;
    public static final int keyCertSign = 4;
    public static final int keyEncipherment = 32;
    public static final int nonRepudiation = 64;
    private com.android.internal.org.bouncycastle.asn1.DERBitString bitString;

    public static com.android.internal.org.bouncycastle.asn1.x509.KeyUsage getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.KeyUsage) {
            return (com.android.internal.org.bouncycastle.asn1.x509.KeyUsage) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.KeyUsage(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.KeyUsage fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.keyUsage));
    }

    public KeyUsage(int i) {
        this.bitString = new com.android.internal.org.bouncycastle.asn1.DERBitString(i);
    }

    private KeyUsage(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        this.bitString = dERBitString;
    }

    public boolean hasUsages(int i) {
        return (this.bitString.intValue() & i) == i;
    }

    public byte[] getBytes() {
        return this.bitString.getBytes();
    }

    public int getPadBits() {
        return this.bitString.getPadBits();
    }

    public java.lang.String toString() {
        byte[] bytes = this.bitString.getBytes();
        if (bytes.length == 1) {
            return "KeyUsage: 0x" + java.lang.Integer.toHexString(bytes[0] & 255);
        }
        return "KeyUsage: 0x" + java.lang.Integer.toHexString((bytes[0] & 255) | ((bytes[1] & 255) << 8));
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.bitString;
    }
}
