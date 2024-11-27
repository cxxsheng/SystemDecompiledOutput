package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class NameConstraints extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] excluded;
    private com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] permitted;

    public static com.android.internal.org.bouncycastle.asn1.x509.NameConstraints getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.NameConstraints) {
            return (com.android.internal.org.bouncycastle.asn1.x509.NameConstraints) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.NameConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    private NameConstraints(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(objects.nextElement());
            switch (aSN1TaggedObject.getTagNo()) {
                case 0:
                    this.permitted = createArray(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, false));
                    break;
                case 1:
                    this.excluded = createArray(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(aSN1TaggedObject, false));
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown tag encountered: " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public NameConstraints(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr, com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr2) {
        this.permitted = cloneSubtree(generalSubtreeArr);
        this.excluded = cloneSubtree(generalSubtreeArr2);
    }

    private com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] createArray(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr = new com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[size];
        for (int i = 0; i != size; i++) {
            generalSubtreeArr[i] = com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return generalSubtreeArr;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] getPermittedSubtrees() {
        return cloneSubtree(this.permitted);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] getExcludedSubtrees() {
        return cloneSubtree(this.excluded);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(2);
        if (this.permitted != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, new com.android.internal.org.bouncycastle.asn1.DERSequence(this.permitted)));
        }
        if (this.excluded != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, new com.android.internal.org.bouncycastle.asn1.DERSequence(this.excluded)));
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] cloneSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr) {
        if (generalSubtreeArr != null) {
            int length = generalSubtreeArr.length;
            com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr2 = new com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[length];
            java.lang.System.arraycopy(generalSubtreeArr, 0, generalSubtreeArr2, 0, length);
            return generalSubtreeArr2;
        }
        return null;
    }
}
