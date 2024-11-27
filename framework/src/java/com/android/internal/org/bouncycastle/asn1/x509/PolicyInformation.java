package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class PolicyInformation extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier policyIdentifier;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence policyQualifiers;

    private PolicyInformation(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1 || aSN1Sequence.size() > 2) {
            throw new java.lang.IllegalArgumentException("Bad sequence size: " + aSN1Sequence.size());
        }
        this.policyIdentifier = com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() > 1) {
            this.policyQualifiers = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(1));
        }
    }

    public PolicyInformation(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.policyIdentifier = aSN1ObjectIdentifier;
    }

    public PolicyInformation(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.policyIdentifier = aSN1ObjectIdentifier;
        this.policyQualifiers = aSN1Sequence;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation) obj;
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.PolicyInformation(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getPolicyIdentifier() {
        return this.policyIdentifier;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Sequence getPolicyQualifiers() {
        return this.policyQualifiers;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        aSN1EncodableVector.add(this.policyIdentifier);
        if (this.policyQualifiers != null) {
            aSN1EncodableVector.add(this.policyQualifiers);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("Policy information: ");
        stringBuffer.append(this.policyIdentifier);
        if (this.policyQualifiers != null) {
            java.lang.StringBuffer stringBuffer2 = new java.lang.StringBuffer();
            for (int i = 0; i < this.policyQualifiers.size(); i++) {
                if (stringBuffer2.length() != 0) {
                    stringBuffer2.append(", ");
                }
                stringBuffer2.append(com.android.internal.org.bouncycastle.asn1.x509.PolicyQualifierInfo.getInstance(this.policyQualifiers.getObjectAt(i)));
            }
            stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            stringBuffer.append(stringBuffer2);
            stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return stringBuffer.toString();
    }
}
