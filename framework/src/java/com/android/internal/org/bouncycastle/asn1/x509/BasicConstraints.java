package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class BasicConstraints extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Boolean cA;
    com.android.internal.org.bouncycastle.asn1.ASN1Integer pathLenConstraint;

    public static com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints) {
            return (com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.X509Extension) {
            return getInstance(com.android.internal.org.bouncycastle.asn1.x509.X509Extension.convertValueToObject((com.android.internal.org.bouncycastle.asn1.x509.X509Extension) obj));
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.basicConstraints));
    }

    private BasicConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        if (aSN1Sequence.size() == 0) {
            this.cA = null;
            this.pathLenConstraint = null;
            return;
        }
        if (aSN1Sequence.getObjectAt(0) instanceof com.android.internal.org.bouncycastle.asn1.ASN1Boolean) {
            this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(0));
        } else {
            this.cA = null;
            this.pathLenConstraint = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
        }
        if (aSN1Sequence.size() > 1) {
            if (this.cA != null) {
                this.pathLenConstraint = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
                return;
            }
            throw new java.lang.IllegalArgumentException("wrong sequence in constructor");
        }
    }

    public BasicConstraints(boolean z) {
        this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        if (z) {
            this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true);
        } else {
            this.cA = null;
        }
        this.pathLenConstraint = null;
    }

    public BasicConstraints(int i) {
        this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(false);
        this.pathLenConstraint = null;
        this.cA = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true);
        this.pathLenConstraint = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
    }

    public boolean isCA() {
        return this.cA != null && this.cA.isTrue();
    }

    public java.math.BigInteger getPathLenConstraint() {
        if (this.pathLenConstraint != null) {
            return this.pathLenConstraint.getValue();
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        if (this.cA != null) {
            aSN1EncodableVector.add(this.cA);
        }
        if (this.pathLenConstraint != null) {
            aSN1EncodableVector.add(this.pathLenConstraint);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public java.lang.String toString() {
        if (this.pathLenConstraint == null) {
            return "BasicConstraints: isCa(" + isCA() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        return "BasicConstraints: isCa(" + isCA() + "), pathLenConstraint = " + this.pathLenConstraint.getValue();
    }
}
