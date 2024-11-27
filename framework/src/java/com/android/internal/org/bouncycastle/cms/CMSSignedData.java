package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSSignedData implements com.android.internal.org.bouncycastle.util.Encodable {
    private static final com.android.internal.org.bouncycastle.cms.CMSSignedHelper HELPER = com.android.internal.org.bouncycastle.cms.CMSSignedHelper.INSTANCE;
    com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo;
    private java.util.Map hashes;
    com.android.internal.org.bouncycastle.cms.CMSTypedData signedContent;
    com.android.internal.org.bouncycastle.asn1.cms.SignedData signedData;
    com.android.internal.org.bouncycastle.cms.SignerInformationStore signerInfoStore;

    private CMSSignedData(com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData) {
        this.signedData = cMSSignedData.signedData;
        this.contentInfo = cMSSignedData.contentInfo;
        this.signedContent = cMSSignedData.signedContent;
        this.signerInfoStore = cMSSignedData.signerInfoStore;
    }

    public CMSSignedData(byte[] bArr) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this(com.android.internal.org.bouncycastle.cms.CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(com.android.internal.org.bouncycastle.cms.CMSProcessable cMSProcessable, byte[] bArr) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this(cMSProcessable, com.android.internal.org.bouncycastle.cms.CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(java.util.Map map, byte[] bArr) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this(map, com.android.internal.org.bouncycastle.cms.CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(com.android.internal.org.bouncycastle.cms.CMSProcessable cMSProcessable, java.io.InputStream inputStream) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this(cMSProcessable, com.android.internal.org.bouncycastle.cms.CMSUtils.readContentInfo((java.io.InputStream) new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream)));
    }

    public CMSSignedData(java.io.InputStream inputStream) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this(com.android.internal.org.bouncycastle.cms.CMSUtils.readContentInfo(inputStream));
    }

    public CMSSignedData(final com.android.internal.org.bouncycastle.cms.CMSProcessable cMSProcessable, com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo) throws com.android.internal.org.bouncycastle.cms.CMSException {
        if (cMSProcessable instanceof com.android.internal.org.bouncycastle.cms.CMSTypedData) {
            this.signedContent = (com.android.internal.org.bouncycastle.cms.CMSTypedData) cMSProcessable;
        } else {
            this.signedContent = new com.android.internal.org.bouncycastle.cms.CMSTypedData() { // from class: com.android.internal.org.bouncycastle.cms.CMSSignedData.1
                @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
                public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
                    return com.android.internal.org.bouncycastle.cms.CMSSignedData.this.signedData.getEncapContentInfo().getContentType();
                }

                @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
                public void write(java.io.OutputStream outputStream) throws java.io.IOException, com.android.internal.org.bouncycastle.cms.CMSException {
                    cMSProcessable.write(outputStream);
                }

                @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
                public java.lang.Object getContent() {
                    return cMSProcessable.getContent();
                }
            };
        }
        this.contentInfo = contentInfo;
        this.signedData = getSignedData();
    }

    public CMSSignedData(java.util.Map map, com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.hashes = map;
        this.contentInfo = contentInfo;
        this.signedData = getSignedData();
    }

    public CMSSignedData(com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo) throws com.android.internal.org.bouncycastle.cms.CMSException {
        this.contentInfo = contentInfo;
        this.signedData = getSignedData();
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable content = this.signedData.getEncapContentInfo().getContent();
        if (content != null) {
            if (content instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
                this.signedContent = new com.android.internal.org.bouncycastle.cms.CMSProcessableByteArray(this.signedData.getEncapContentInfo().getContentType(), ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) content).getOctets());
                return;
            } else {
                this.signedContent = new com.android.internal.org.bouncycastle.cms.PKCS7ProcessableObject(this.signedData.getEncapContentInfo().getContentType(), content);
                return;
            }
        }
        this.signedContent = null;
    }

    private com.android.internal.org.bouncycastle.asn1.cms.SignedData getSignedData() throws com.android.internal.org.bouncycastle.cms.CMSException {
        try {
            return com.android.internal.org.bouncycastle.asn1.cms.SignedData.getInstance(this.contentInfo.getContent());
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("Malformed content.", e);
        } catch (java.lang.IllegalArgumentException e2) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("Malformed content.", e2);
        }
    }

    public int getVersion() {
        return this.signedData.getVersion().intValueExact();
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationStore getSignerInfos() {
        java.util.Map map;
        java.lang.Object algorithm;
        if (this.signerInfoStore == null) {
            com.android.internal.org.bouncycastle.asn1.ASN1Set signerInfos = this.signedData.getSignerInfos();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i != signerInfos.size(); i++) {
                com.android.internal.org.bouncycastle.asn1.cms.SignerInfo signerInfo = com.android.internal.org.bouncycastle.asn1.cms.SignerInfo.getInstance(signerInfos.getObjectAt(i));
                com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier contentType = this.signedData.getEncapContentInfo().getContentType();
                if (this.hashes == null) {
                    arrayList.add(new com.android.internal.org.bouncycastle.cms.SignerInformation(signerInfo, contentType, this.signedContent, null));
                } else {
                    if (this.hashes.keySet().iterator().next() instanceof java.lang.String) {
                        map = this.hashes;
                        algorithm = signerInfo.getDigestAlgorithm().getAlgorithm().getId();
                    } else {
                        map = this.hashes;
                        algorithm = signerInfo.getDigestAlgorithm().getAlgorithm();
                    }
                    arrayList.add(new com.android.internal.org.bouncycastle.cms.SignerInformation(signerInfo, contentType, null, (byte[]) map.get(algorithm)));
                }
            }
            this.signerInfoStore = new com.android.internal.org.bouncycastle.cms.SignerInformationStore(arrayList);
        }
        return this.signerInfoStore;
    }

    public boolean isDetachedSignature() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() > 0;
    }

    public boolean isCertificateManagementMessage() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() == 0;
    }

    public com.android.internal.org.bouncycastle.util.Store<com.android.internal.org.bouncycastle.cert.X509CertificateHolder> getCertificates() {
        return HELPER.getCertificates(this.signedData.getCertificates());
    }

    public com.android.internal.org.bouncycastle.util.Store<com.android.internal.org.bouncycastle.cert.X509CRLHolder> getCRLs() {
        return HELPER.getCRLs(this.signedData.getCRLs());
    }

    public com.android.internal.org.bouncycastle.util.Store<com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder> getAttributeCertificates() {
        return HELPER.getAttributeCertificates(this.signedData.getCertificates());
    }

    public java.util.Set<com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier> getDigestAlgorithmIDs() {
        java.util.HashSet hashSet = new java.util.HashSet(this.signedData.getDigestAlgorithms().size());
        java.util.Enumeration objects = this.signedData.getDigestAlgorithms().getObjects();
        while (objects.hasMoreElements()) {
            hashSet.add(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(objects.nextElement()));
        }
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    public java.lang.String getSignedContentTypeOID() {
        return this.signedData.getEncapContentInfo().getContentType().getId();
    }

    public com.android.internal.org.bouncycastle.cms.CMSTypedData getSignedContent() {
        return this.signedContent;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.ContentInfo toASN1Structure() {
        return this.contentInfo;
    }

    @Override // com.android.internal.org.bouncycastle.util.Encodable
    public byte[] getEncoded() throws java.io.IOException {
        return this.contentInfo.getEncoded();
    }

    public byte[] getEncoded(java.lang.String str) throws java.io.IOException {
        return this.contentInfo.getEncoded(str);
    }

    public static com.android.internal.org.bouncycastle.cms.CMSSignedData replaceSigners(com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData, com.android.internal.org.bouncycastle.cms.SignerInformationStore signerInformationStore) {
        com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData2 = new com.android.internal.org.bouncycastle.cms.CMSSignedData(cMSSignedData);
        cMSSignedData2.signerInfoStore = signerInformationStore;
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        for (com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation : signerInformationStore.getSigners()) {
            aSN1EncodableVector.add(com.android.internal.org.bouncycastle.cms.CMSSignedHelper.INSTANCE.fixAlgID(signerInformation.getDigestAlgorithmID()));
            aSN1EncodableVector2.add(signerInformation.toASN1Structure());
        }
        com.android.internal.org.bouncycastle.asn1.DERSet dERSet = new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector);
        com.android.internal.org.bouncycastle.asn1.DLSet dLSet = new com.android.internal.org.bouncycastle.asn1.DLSet(aSN1EncodableVector2);
        com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) cMSSignedData.signedData.toASN1Primitive();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector3 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector3.add(aSN1Sequence.getObjectAt(0));
        aSN1EncodableVector3.add(dERSet);
        for (int i = 2; i != aSN1Sequence.size() - 1; i++) {
            aSN1EncodableVector3.add(aSN1Sequence.getObjectAt(i));
        }
        aSN1EncodableVector3.add(dLSet);
        cMSSignedData2.signedData = com.android.internal.org.bouncycastle.asn1.cms.SignedData.getInstance(new com.android.internal.org.bouncycastle.asn1.BERSequence(aSN1EncodableVector3));
        cMSSignedData2.contentInfo = new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
        return cMSSignedData2;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.internal.org.bouncycastle.cms.CMSSignedData replaceCertificatesAndCRLs(com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData, com.android.internal.org.bouncycastle.util.Store store, com.android.internal.org.bouncycastle.util.Store store2, com.android.internal.org.bouncycastle.util.Store store3) throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set;
        com.android.internal.org.bouncycastle.asn1.ASN1Set aSN1Set2;
        com.android.internal.org.bouncycastle.cms.CMSSignedData cMSSignedData2 = new com.android.internal.org.bouncycastle.cms.CMSSignedData(cMSSignedData);
        if (store != null || store2 != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (store != null) {
                arrayList.addAll(com.android.internal.org.bouncycastle.cms.CMSUtils.getCertificatesFromStore(store));
            }
            if (store2 != null) {
                arrayList.addAll(com.android.internal.org.bouncycastle.cms.CMSUtils.getAttributeCertificatesFromStore(store2));
            }
            com.android.internal.org.bouncycastle.asn1.ASN1Set createBerSetFromList = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(arrayList);
            if (createBerSetFromList.size() != 0) {
                aSN1Set = createBerSetFromList;
                if (store3 != null) {
                    com.android.internal.org.bouncycastle.asn1.ASN1Set createBerSetFromList2 = com.android.internal.org.bouncycastle.cms.CMSUtils.createBerSetFromList(com.android.internal.org.bouncycastle.cms.CMSUtils.getCRLsFromStore(store3));
                    if (createBerSetFromList2.size() != 0) {
                        aSN1Set2 = createBerSetFromList2;
                        cMSSignedData2.signedData = new com.android.internal.org.bouncycastle.asn1.cms.SignedData(cMSSignedData.signedData.getDigestAlgorithms(), cMSSignedData.signedData.getEncapContentInfo(), aSN1Set, aSN1Set2, cMSSignedData.signedData.getSignerInfos());
                        cMSSignedData2.contentInfo = new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
                        return cMSSignedData2;
                    }
                }
                aSN1Set2 = null;
                cMSSignedData2.signedData = new com.android.internal.org.bouncycastle.asn1.cms.SignedData(cMSSignedData.signedData.getDigestAlgorithms(), cMSSignedData.signedData.getEncapContentInfo(), aSN1Set, aSN1Set2, cMSSignedData.signedData.getSignerInfos());
                cMSSignedData2.contentInfo = new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
                return cMSSignedData2;
            }
        }
        aSN1Set = null;
        if (store3 != null) {
        }
        aSN1Set2 = null;
        cMSSignedData2.signedData = new com.android.internal.org.bouncycastle.asn1.cms.SignedData(cMSSignedData.signedData.getDigestAlgorithms(), cMSSignedData.signedData.getEncapContentInfo(), aSN1Set, aSN1Set2, cMSSignedData.signedData.getSignerInfos());
        cMSSignedData2.contentInfo = new com.android.internal.org.bouncycastle.asn1.cms.ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
        return cMSSignedData2;
    }
}
