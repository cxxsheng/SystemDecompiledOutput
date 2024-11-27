package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public class SignedData extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer VERSION_1 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1);
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer VERSION_3 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(3);
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer VERSION_4 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(4);
    private static final com.android.internal.org.bouncycastle.asn1.ASN1Integer VERSION_5 = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(5);
    private com.android.internal.org.bouncycastle.asn1.ASN1Set certificates;
    private boolean certsBer;
    private com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set crls;
    private boolean crlsBer;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set digestAlgorithms;
    private com.android.internal.org.bouncycastle.asn1.ASN1Set signerInfos;
    private com.android.internal.org.bouncycastle.asn1.ASN1Integer version;

    public static com.android.internal.org.bouncycastle.asn1.cms.SignedData getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.cms.SignedData) {
            return (com.android.internal.org.bouncycastle.asn1.cms.SignedData) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.cms.SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set3, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set4) {
        this.version = calculateVersion(contentInfo.getContentType(), aSN1Set2, aSN1Set3, aSN1Set4);
        this.digestAlgorithms = aSN1Set;
        this.contentInfo = contentInfo;
        this.certificates = aSN1Set2;
        this.crls = aSN1Set3;
        this.signerInfos = aSN1Set4;
        this.crlsBer = aSN1Set3 instanceof com.android.internal.org.bouncycastle.asn1.BERSet;
        this.certsBer = aSN1Set2 instanceof com.android.internal.org.bouncycastle.asn1.BERSet;
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Integer calculateVersion(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2, com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set3) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = false;
        if (aSN1Set == null) {
            z = false;
            z2 = false;
            z3 = false;
        } else {
            java.util.Enumeration objects = aSN1Set.getObjects();
            z = false;
            z2 = false;
            z3 = false;
            while (objects.hasMoreElements()) {
                java.lang.Object nextElement = objects.nextElement();
                if (nextElement instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                    com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject.getInstance(nextElement);
                    if (aSN1TaggedObject.getTagNo() == 1) {
                        z2 = true;
                    } else if (aSN1TaggedObject.getTagNo() == 2) {
                        z3 = true;
                    } else if (aSN1TaggedObject.getTagNo() == 3) {
                        z = true;
                    }
                }
            }
        }
        if (z) {
            return new com.android.internal.org.bouncycastle.asn1.ASN1Integer(5L);
        }
        if (aSN1Set2 != null) {
            java.util.Enumeration objects2 = aSN1Set2.getObjects();
            while (objects2.hasMoreElements()) {
                if (objects2.nextElement() instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                    z4 = true;
                }
            }
        }
        if (z4) {
            return VERSION_5;
        }
        if (z3) {
            return VERSION_4;
        }
        if (z2) {
            return VERSION_3;
        }
        if (checkForVersion3(aSN1Set3)) {
            return VERSION_3;
        }
        if (!com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.data.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
            return VERSION_3;
        }
        return VERSION_1;
    }

    private boolean checkForVersion3(com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set) {
        java.util.Enumeration objects = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            if (com.android.internal.org.bouncycastle.asn1.cms.SignerInfo.getInstance(objects.nextElement()).getVersion().intValueExact() == 3) {
                return true;
            }
        }
        return false;
    }

    private SignedData(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.version = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(objects.nextElement());
        this.digestAlgorithms = (com.android.internal.org.bouncycastle.asn1.ASN1Set) objects.nextElement();
        this.contentInfo = com.android.internal.org.bouncycastle.asn1.cms.ContentInfo.getInstance(objects.nextElement());
        while (objects.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = (com.android.internal.org.bouncycastle.asn1.ASN1Primitive) objects.nextElement();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) aSN1Primitive;
                switch (aSN1TaggedObject.getTagNo()) {
                    case 0:
                        this.certsBer = aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject;
                        this.certificates = com.android.internal.org.bouncycastle.asn1.ASN1Set.getInstance(aSN1TaggedObject, false);
                        break;
                    case 1:
                        this.crlsBer = aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject;
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

    public com.android.internal.org.bouncycastle.asn1.cms.ContentInfo getEncapContentInfo() {
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
            if (this.certsBer) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(false, 0, this.certificates));
            } else {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 0, this.certificates));
            }
        }
        if (this.crls != null) {
            if (this.crlsBer) {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.BERTaggedObject(false, 1, this.crls));
            } else {
                aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 1, this.crls));
            }
        }
        aSN1EncodableVector.add(this.signerInfos);
        return new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector);
    }
}
