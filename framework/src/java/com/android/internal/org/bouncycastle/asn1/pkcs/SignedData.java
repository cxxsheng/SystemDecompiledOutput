package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class SignedData extends com.android.internal.org.bouncycastle.asn1.ASN1Object implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers {
    private com.android.internal.org.bouncycastle.asn1.ASN1Set certificates;
    private com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set crls;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set digestAlgorithms;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set signerInfos;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.SignedData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.SignedData) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.SignedData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set3, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set4) {
        this.version = aSN1Integer;
        this.digestAlgorithms = aSN1Set;
        this.contentInfo = contentInfo;
        this.certificates = aSN1Set2;
        this.crls = aSN1Set3;
        this.signerInfos = aSN1Set4;
    }

    public SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.version = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement();
        this.digestAlgorithms = (com.android.internal.org.bouncycastle.asn1.ASN1Set) objects.nextElement();
        this.contentInfo = com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) objects.nextElement();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Primitive;
                switch (aSN1TaggedObject.getTagNo()) {
                    case 0:
                        this.certificates = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    case 1:
                        this.crls = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("unknown tag value " + aSN1TaggedObject.getTagNo());
                }
            } else {
                this.signerInfos = (com.android.internal.org.bouncycastle.asn1.ASN1Set) aSN1Primitive;
            }
        }
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getVersion() {
        return this.version;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getDigestAlgorithms() {
        return this.digestAlgorithms;
    }

    public com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo getContentInfo() {
        return this.contentInfo;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getCertificates() {
        return this.certificates;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getCRLs() {
        return this.crls;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Set getSignerInfos() {
        return this.signerInfos;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(6);
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.digestAlgorithms);
        aSN1EncodableVector.add(this.contentInfo);
        if (this.certificates != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.certificates));
        }
        if (this.crls != null) {
            aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.crls));
        }
        aSN1EncodableVector.add(this.signerInfos);
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }
}
