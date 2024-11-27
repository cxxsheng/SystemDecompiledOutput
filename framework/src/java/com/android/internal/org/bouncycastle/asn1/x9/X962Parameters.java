package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public class X962Parameters extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive params;

    public static com.android.internal.org.bouncycastle.asn1.x9.X962Parameters getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x9.X962Parameters)) {
            return (com.android.internal.org.bouncycastle.asn1.x9.X962Parameters) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Primitive) {
            return new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) obj);
        }
        if (obj instanceof byte[]) {
            try {
                return new com.android.internal.org.bouncycastle.asn1.x9.X962Parameters(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("unable to parse encoded data: " + e.getMessage());
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance()");
    }

    public static com.android.internal.org.bouncycastle.asn1.x9.X962Parameters getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public X962Parameters(com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters) {
        this.params = null;
        this.params = x9ECParameters.toASN1Primitive();
    }

    public X962Parameters(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.params = null;
        this.params = aSN1ObjectIdentifier;
    }

    public X962Parameters(com.android.internal.org.bouncycastle.asn1.ASN1Null aSN1Null) {
        this.params = null;
        this.params = aSN1Null;
    }

    private X962Parameters(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        this.params = null;
        this.params = aSN1Primitive;
    }

    public boolean isNamedCurve() {
        return this.params instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
    }

    public boolean isImplicitlyCA() {
        return this.params instanceof com.android.internal.org.bouncycastle.asn1.ASN1Null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getParameters() {
        return this.params;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.params;
    }
}
