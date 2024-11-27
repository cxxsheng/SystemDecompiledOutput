package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSSignedDataGenerator extends com.android.internal.org.bouncycastle.cms.CMSSignedGenerator {
    private java.util.List signerInfs = new java.util.ArrayList();

    public com.android.internal.org.bouncycastle.cms.CMSSignedData generate(com.android.internal.org.bouncycastle.cms.CMSTypedData cMSTypedData) throws com.android.internal.org.bouncycastle.cms.CMSException {
        return generate(cMSTypedData, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.internal.org.bouncycastle.cms.CMSSignedData generate(com.android.internal.org.bouncycastle.cms.CMSTypedData cMSTypedData, boolean z) throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.BEROctetString bEROctetString;
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set;
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2;
        java.io.ByteArrayOutputStream byteArrayOutputStream;
        if (!this.signerInfs.isEmpty()) {
            throw new java.lang.IllegalStateException("this method can only be used with SignerInfoGenerator");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        this.digests.clear();
        for (com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation : this._signers) {
            aSN1EncodableVector.add(com.android.internal.org.bouncycastle.cms.CMSSignedHelper.INSTANCE.fixAlgID(signerInformation.getDigestAlgorithmID()));
            aSN1EncodableVector2.add(signerInformation.toASN1Structure());
        }
        com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier contentType = cMSTypedData.getContentType();
        if (cMSTypedData.getContent() != null) {
            if (!z) {
                byteArrayOutputStream = null;
            } else {
                byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            }
            java.io.OutputStream safeOutputStream = com.android.internal.org.bouncycastle.cms.CMSUtils.getSafeOutputStream(com.android.internal.org.bouncycastle.cms.CMSUtils.attachSignersToOutputStream(this.signerGens, byteArrayOutputStream));
            try {
                cMSTypedData.write(safeOutputStream);
                safeOutputStream.close();
                if (z) {
                    bEROctetString = new com.android.internal.org.bouncycastle.asn1.BEROctetString(byteArrayOutputStream.toByteArray());
                    for (com.android.internal.org.bouncycastle.cms.SignerInfoGenerator signerInfoGenerator : this.signerGens) {
                        com.android.internal.org.bouncycastle.asn1.cms.SignerInfo generate = signerInfoGenerator.generate(contentType);
                        aSN1EncodableVector.add(generate.getDigestAlgorithm());
                        aSN1EncodableVector2.add(generate);
                        byte[] calculatedDigest = signerInfoGenerator.getCalculatedDigest();
                        if (calculatedDigest != null) {
                            this.digests.put(generate.getDigestAlgorithm().getAlgorithm().getId(), calculatedDigest);
                        }
                    }
                    if (this.certs.size() != 0) {
                        aSN1Set = null;
                    } else {
                        aSN1Set = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(this.certs);
                    }
                    if (this.crls.size() != 0) {
                        aSN1Set2 = null;
                    } else {
                        aSN1Set2 = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(this.crls);
                    }
                    return new com.android.internal.org.bouncycastle.cms.CMSSignedData(cMSTypedData, new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.signedData, new com.android.internal.org.bouncycastle.asn1.cms.SignedData(new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector), new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(contentType, bEROctetString), aSN1Set, aSN1Set2, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2))));
                }
            } catch (java.io.IOException e) {
                throw new com.android.internal.org.bouncycastle.cms.CMSException("data processing exception: " + e.getMessage(), e);
            }
        }
        bEROctetString = null;
        while (r3.hasNext()) {
        }
        if (this.certs.size() != 0) {
        }
        if (this.crls.size() != 0) {
        }
        return new com.android.internal.org.bouncycastle.cms.CMSSignedData(cMSTypedData, new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.signedData, new com.android.internal.org.bouncycastle.asn1.cms.SignedData(new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector), new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(contentType, bEROctetString), aSN1Set, aSN1Set2, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2))));
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationStore generateCounterSigners(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation) throws com.android.internal.org.bouncycastle.cms.CMSException {
        return generate(new com.android.internal.org.bouncycastle.cms.CMSProcessableByteArray(null, signerInformation.getSignature()), false).getSignerInfos();
    }
}
