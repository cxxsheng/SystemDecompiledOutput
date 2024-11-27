package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class DistributionPoint extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.x509.GeneralNames cRLIssuer;
    com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint;
    com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags reasons;

    public static com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint)) {
            return (com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) obj);
        }
        throw new java.lang.IllegalArgumentException("Invalid DistributionPoint: " + obj.getClass().getName());
    }

    public DistributionPoint(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.distributionPoint = com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.reasons = new com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags(com.android.internal.org.bouncycastle.asn1.DERBitString.getInstance(aSN1TaggedObject, false));
                    break;
                case 2:
                    this.cRLIssuer = com.android.internal.org.bouncycastle.asn1.x509.GeneralNames.getInstance(aSN1TaggedObject, false);
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown tag encountered in structure: " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public DistributionPoint(com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPointName, com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags reasonFlags, com.android.internal.org.bouncycastle.asn1.x509.GeneralNames generalNames) {
        this.distributionPoint = distributionPointName;
        this.reasons = reasonFlags;
        this.cRLIssuer = generalNames;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName getDistributionPoint() {
        return this.distributionPoint;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags getReasons() {
        return this.reasons;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralNames getCRLIssuer() {
        return this.cRLIssuer;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(3);
        if (this.distributionPoint != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(0, this.distributionPoint));
        }
        if (this.reasons != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.reasons));
        }
        if (this.cRLIssuer != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, this.cRLIssuer));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public java.lang.String toString() {
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("DistributionPoint: [");
        stringBuffer.append(lineSeparator);
        if (this.distributionPoint != null) {
            appendObject(stringBuffer, lineSeparator, "distributionPoint", this.distributionPoint.toString());
        }
        if (this.reasons != null) {
            appendObject(stringBuffer, lineSeparator, "reasons", this.reasons.toString());
        }
        if (this.cRLIssuer != null) {
            appendObject(stringBuffer, lineSeparator, "cRLIssuer", this.cRLIssuer.toString());
        }
        stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        stringBuffer.append(lineSeparator);
        return stringBuffer.toString();
    }

    private void appendObject(java.lang.StringBuffer stringBuffer, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        stringBuffer.append("    ");
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }
}
