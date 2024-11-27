package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class IssuingDistributionPoint extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPoint;
    private boolean indirectCRL;
    private boolean onlyContainsAttributeCerts;
    private boolean onlyContainsCACerts;
    private boolean onlyContainsUserCerts;
    private com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags onlySomeReasons;
    private com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;

    public static com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint) {
            return (com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.IssuingDistributionPoint(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public IssuingDistributionPoint(com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPointName, boolean z, boolean z2, com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags reasonFlags, boolean z3, boolean z4) {
        this.distributionPoint = distributionPointName;
        this.indirectCRL = z3;
        this.onlyContainsAttributeCerts = z4;
        this.onlyContainsCACerts = z2;
        this.onlyContainsUserCerts = z;
        this.onlySomeReasons = reasonFlags;
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(6);
        if (distributionPointName != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(true, 0, distributionPointName));
        }
        if (z) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true)));
        }
        if (z2) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true)));
        }
        if (reasonFlags != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 3, reasonFlags));
        }
        if (z3) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 4, com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true)));
        }
        if (z4) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 5, com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(true)));
        }
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    public IssuingDistributionPoint(com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName distributionPointName, boolean z, boolean z2) {
        this(distributionPointName, false, false, null, z, z2);
    }

    private IssuingDistributionPoint(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        for (int i = 0; i != aSN1Sequence.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.distributionPoint = com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName.getInstance(aSN1TaggedObject, true);
                    break;
                case 1:
                    this.onlyContainsUserCerts = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                    break;
                case 2:
                    this.onlyContainsCACerts = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                    break;
                case 3:
                    this.onlySomeReasons = new com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags(com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags.getInstance(aSN1TaggedObject, false));
                    break;
                case 4:
                    this.indirectCRL = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                    break;
                case 5:
                    this.onlyContainsAttributeCerts = com.android.internal.org.bouncycastle.asn1.ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unknown tag in IssuingDistributionPoint");
            }
        }
    }

    public boolean onlyContainsUserCerts() {
        return this.onlyContainsUserCerts;
    }

    public boolean onlyContainsCACerts() {
        return this.onlyContainsCACerts;
    }

    public boolean isIndirectCRL() {
        return this.indirectCRL;
    }

    public boolean onlyContainsAttributeCerts() {
        return this.onlyContainsAttributeCerts;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.DistributionPointName getDistributionPoint() {
        return this.distributionPoint;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags getOnlySomeReasons() {
        return this.onlySomeReasons;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }

    public java.lang.String toString() {
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("IssuingDistributionPoint: [");
        stringBuffer.append(lineSeparator);
        if (this.distributionPoint != null) {
            appendObject(stringBuffer, lineSeparator, "distributionPoint", this.distributionPoint.toString());
        }
        if (this.onlyContainsUserCerts) {
            appendObject(stringBuffer, lineSeparator, "onlyContainsUserCerts", booleanToString(this.onlyContainsUserCerts));
        }
        if (this.onlyContainsCACerts) {
            appendObject(stringBuffer, lineSeparator, "onlyContainsCACerts", booleanToString(this.onlyContainsCACerts));
        }
        if (this.onlySomeReasons != null) {
            appendObject(stringBuffer, lineSeparator, "onlySomeReasons", this.onlySomeReasons.toString());
        }
        if (this.onlyContainsAttributeCerts) {
            appendObject(stringBuffer, lineSeparator, "onlyContainsAttributeCerts", booleanToString(this.onlyContainsAttributeCerts));
        }
        if (this.indirectCRL) {
            appendObject(stringBuffer, lineSeparator, "indirectCRL", booleanToString(this.indirectCRL));
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

    private java.lang.String booleanToString(boolean z) {
        return z ? "true" : "false";
    }
}
