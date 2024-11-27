package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class CRLNumber extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.math.BigInteger number;

    public CRLNumber(java.math.BigInteger bigInteger) {
        this.number = bigInteger;
    }

    public java.math.BigInteger getCRLNumber() {
        return this.number;
    }

    public java.lang.String toString() {
        return "CRLNumber: " + getCRLNumber();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.number);
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLNumber getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.CRLNumber) {
            return (com.android.internal.org.bouncycastle.asn1.x509.CRLNumber) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.CRLNumber(com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(obj).getValue());
        }
        return null;
    }
}
