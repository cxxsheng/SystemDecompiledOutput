package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class PolicyConstraints extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private java.math.BigInteger inhibitPolicyMapping;
    private java.math.BigInteger requireExplicitPolicyMapping;

    public PolicyConstraints(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this.requireExplicitPolicyMapping = bigInteger;
        this.inhibitPolicyMapping = bigInteger2;
    }

    private PolicyConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.requireExplicitPolicyMapping = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false).getValue();
            } else if (aSN1TaggedObject.getTagNo() == 1) {
                this.inhibitPolicyMapping = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1TaggedObject, false).getValue();
            } else {
                throw new java.lang.IllegalArgumentException("Unknown tag encountered.");
            }
        }
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.PolicyConstraints getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.PolicyConstraints) {
            return (com.android.internal.org.bouncycastle.asn1.x509.PolicyConstraints) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.PolicyConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.PolicyConstraints fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.policyConstraints));
    }

    public java.math.BigInteger getRequireExplicitPolicyMapping() {
        return this.requireExplicitPolicyMapping;
    }

    public java.math.BigInteger getInhibitPolicyMapping() {
        return this.inhibitPolicyMapping;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        if (this.requireExplicitPolicyMapping != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.requireExplicitPolicyMapping)));
        }
        if (this.inhibitPolicyMapping != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, new com.android.internal.org.bouncycastle.asn1.ASN1Integer(this.inhibitPolicyMapping)));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
