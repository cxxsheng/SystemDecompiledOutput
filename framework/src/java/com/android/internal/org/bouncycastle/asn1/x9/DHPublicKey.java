package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class DHPublicKey extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer y;

    public static com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey)) {
            return (com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            return new com.android.internal.org.bouncycastle.asn1.x9.DHPublicKey((com.android.internal.org.bouncycastle.asn1.ASN1Integer) obj);
        }
        throw new java.lang.IllegalArgumentException("Invalid DHPublicKey: " + obj.getClass().getName());
    }

    private DHPublicKey(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        if (aSN1Integer == null) {
            throw new java.lang.IllegalArgumentException("'y' cannot be null");
        }
        this.y = aSN1Integer;
    }

    public DHPublicKey(java.math.BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new java.lang.IllegalArgumentException("'y' cannot be null");
        }
        this.y = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(bigInteger);
    }

    public java.math.BigInteger getY() {
        return this.y.getPositiveValue();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.y;
    }
}
