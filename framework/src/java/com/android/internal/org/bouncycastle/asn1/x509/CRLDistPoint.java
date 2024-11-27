package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class CRLDistPoint extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    com.android.internal.org.bouncycastle.asn1.ASN1Sequence seq;

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint) {
            return (com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.CRLDistPoint fromExtensions(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        return getInstance(com.android.internal.org.bouncycastle.asn1.x509.Extensions.getExtensionParsedValue(extensions, com.android.internal.org.bouncycastle.asn1.x509.Extension.cRLDistributionPoints));
    }

    private CRLDistPoint(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        this.seq = null;
        this.seq = aSN1Sequence;
    }

    public CRLDistPoint(com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPointArr) {
        this.seq = null;
        this.seq = new com.android.internal.org.bouncycastle.asn1.DERSequence(distributionPointArr);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] getDistributionPoints() {
        com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPointArr = new com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[this.seq.size()];
        for (int i = 0; i != this.seq.size(); i++) {
            distributionPointArr[i] = com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint.getInstance(this.seq.getObjectAt(i));
        }
        return distributionPointArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.seq;
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        java.lang.String lineSeparator = com.android.internal.org.bouncycastle.util.Strings.lineSeparator();
        stringBuffer.append("CRLDistPoint:");
        stringBuffer.append(lineSeparator);
        com.android.internal.org.bouncycastle.asn1.x509.DistributionPoint[] distributionPoints = getDistributionPoints();
        for (int i = 0; i != distributionPoints.length; i++) {
            stringBuffer.append("    ");
            stringBuffer.append(distributionPoints[i]);
            stringBuffer.append(lineSeparator);
        }
        return stringBuffer.toString();
    }
}
