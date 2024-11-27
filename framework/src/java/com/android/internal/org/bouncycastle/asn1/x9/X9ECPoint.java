package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X9ECPoint extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.math.ec.ECCurve c;
    private final com.android.internal.org.bouncycastle.asn1.ASN1OctetString encoding;
    private com.android.internal.org.bouncycastle.math.ec.ECPoint p;

    public X9ECPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, boolean z) {
        this.p = eCPoint.normalize();
        this.encoding = new com.android.internal.org.bouncycastle.asn1.DEROctetString(eCPoint.getEncoded(z));
    }

    public X9ECPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, byte[] bArr) {
        this.c = eCCurve;
        this.encoding = new com.android.internal.org.bouncycastle.asn1.DEROctetString(com.android.internal.org.bouncycastle.util.Arrays.clone(bArr));
    }

    public X9ECPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString) {
        this(eCCurve, aSN1OctetString.getOctets());
    }

    public byte[] getPointEncoding() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.encoding.getOctets());
    }

    public synchronized com.android.internal.org.bouncycastle.math.ec.ECPoint getPoint() {
        if (this.p == null) {
            this.p = this.c.decodePoint(this.encoding.getOctets()).normalize();
        }
        return this.p;
    }

    public boolean isPointCompressed() {
        byte[] octets = this.encoding.getOctets();
        if (octets == null || octets.length <= 0) {
            return false;
        }
        return octets[0] == 2 || octets[0] == 3;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.encoding;
    }
}
