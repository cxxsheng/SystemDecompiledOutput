package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class PsidGroupPermissions extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final java.math.BigInteger chainLengthRange;
    private final java.lang.Object eeType;
    private final java.math.BigInteger minChainLength;
    private final com.android.internal.org.bouncycastle.its.asn1.SubjectPermissions subjectPermissions;

    private PsidGroupPermissions(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new java.lang.IllegalArgumentException("sequence not length 2");
        }
        this.subjectPermissions = com.android.internal.org.bouncycastle.its.asn1.SubjectPermissions.getInstance(aSN1Sequence.getObjectAt(0));
        this.minChainLength = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1)).getValue();
        this.chainLengthRange = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(aSN1Sequence.getObjectAt(2)).getValue();
        this.eeType = com.android.internal.org.bouncycastle.its.asn1.EndEntityType.getInstance(aSN1Sequence.getObjectAt(3));
    }

    public static com.android.internal.org.bouncycastle.its.asn1.PsidGroupPermissions getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.PsidGroupPermissions) {
            return (com.android.internal.org.bouncycastle.its.asn1.PsidGroupPermissions) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.its.asn1.PsidGroupPermissions(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return null;
    }
}
