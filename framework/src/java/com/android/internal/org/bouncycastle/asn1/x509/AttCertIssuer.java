package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class AttCertIssuer extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.ASN1Choice {
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive choiceObj;
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable obj;

    public static com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.V2Form) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer(com.android.internal.org.bouncycastle.asn1.x509.V2Form.getInstance(obj));
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer((com.android.internal.org.bouncycastle.asn1.x509.GeneralNames) obj);
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer(com.android.internal.org.bouncycastle.asn1.x509.V2Form.getInstance((com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) obj, false));
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(obj));
        }
        throw new java.lang.IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.AttCertIssuer getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(aSN1TaggedObject.getObject());
    }

    public AttCertIssuer(com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        this.obj = generalNames;
        this.choiceObj = this.obj.toASN1Primitive();
    }

    public AttCertIssuer(com.android.internal.org.bouncycastle.asn1.x509.V2Form v2Form) {
        this.obj = v2Form;
        this.choiceObj = new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.obj);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Encodable getIssuer() {
        return this.obj;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.choiceObj;
    }
}
