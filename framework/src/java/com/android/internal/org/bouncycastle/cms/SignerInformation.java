package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class SignerInformation {
    private final com.android.internal.org.bouncycastle.cms.CMSProcessable content;
    private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier contentType;
    protected final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier digestAlgorithm;
    protected final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier encryptionAlgorithm;
    protected final com.android.internal.org.bouncycastle.asn1.cms.SignerInfo info;
    private final boolean isCounterSignature;
    private byte[] resultDigest;
    private final com.android.internal.org.bouncycastle.cms.SignerId sid;
    private final byte[] signature;
    protected final com.android.internal.org.bouncycastle.asn1.ASN1Set signedAttributeSet;
    private com.android.internal.org.bouncycastle.asn1.cms.AttributeTable signedAttributeValues;
    protected final com.android.internal.org.bouncycastle.asn1.ASN1Set unsignedAttributeSet;
    private com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributeValues;

    SignerInformation(com.android.internal.org.bouncycastle.asn1.cms.SignerInfo signerInfo, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.cms.CMSProcessable cMSProcessable, byte[] bArr) {
        this.info = signerInfo;
        this.contentType = aSN1ObjectIdentifier;
        this.isCounterSignature = aSN1ObjectIdentifier == null;
        com.android.internal.org.bouncycastle.asn1.cms.SignerIdentifier sid = signerInfo.getSID();
        if (sid.isTagged()) {
            this.sid = new com.android.internal.org.bouncycastle.cms.SignerId(com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(sid.getId()).getOctets());
        } else {
            com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber issuerAndSerialNumber = com.android.internal.org.bouncycastle.asn1.cms.IssuerAndSerialNumber.getInstance(sid.getId());
            this.sid = new com.android.internal.org.bouncycastle.cms.SignerId(issuerAndSerialNumber.getName(), issuerAndSerialNumber.getSerialNumber().getValue());
        }
        this.digestAlgorithm = signerInfo.getDigestAlgorithm();
        this.signedAttributeSet = signerInfo.getAuthenticatedAttributes();
        this.unsignedAttributeSet = signerInfo.getUnauthenticatedAttributes();
        this.encryptionAlgorithm = signerInfo.getDigestEncryptionAlgorithm();
        this.signature = signerInfo.getEncryptedDigest().getOctets();
        this.content = cMSProcessable;
        this.resultDigest = bArr;
    }

    protected SignerInformation(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation) {
        this(signerInformation, signerInformation.info);
    }

    protected SignerInformation(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation, com.android.internal.org.bouncycastle.asn1.cms.SignerInfo signerInfo) {
        this.info = signerInfo;
        this.contentType = signerInformation.contentType;
        this.isCounterSignature = signerInformation.isCounterSignature();
        this.sid = signerInformation.getSID();
        this.digestAlgorithm = signerInfo.getDigestAlgorithm();
        this.signedAttributeSet = signerInfo.getAuthenticatedAttributes();
        this.unsignedAttributeSet = signerInfo.getUnauthenticatedAttributes();
        this.encryptionAlgorithm = signerInfo.getDigestEncryptionAlgorithm();
        this.signature = signerInfo.getEncryptedDigest().getOctets();
        this.content = signerInformation.content;
        this.resultDigest = signerInformation.resultDigest;
        this.signedAttributeValues = signerInformation.signedAttributeValues;
        this.unsignedAttributeValues = signerInformation.unsignedAttributeValues;
    }

    public boolean isCounterSignature() {
        return this.isCounterSignature;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    private byte[] encodeObj(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.io.IOException {
        if (aSN1Encodable != null) {
            return aSN1Encodable.toASN1Primitive().getEncoded();
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.cms.SignerId getSID() {
        return this.sid;
    }

    public int getVersion() {
        return this.info.getVersion().intValueExact();
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getDigestAlgorithmID() {
        return this.digestAlgorithm;
    }

    public java.lang.String getDigestAlgOID() {
        return this.digestAlgorithm.getAlgorithm().getId();
    }

    public byte[] getDigestAlgParams() {
        try {
            return encodeObj(this.digestAlgorithm.getParameters());
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("exception getting digest parameters " + e);
        }
    }

    public byte[] getContentDigest() {
        if (this.resultDigest == null) {
            throw new java.lang.IllegalStateException("method can only be called after verify.");
        }
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.resultDigest);
    }

    public java.lang.String getEncryptionAlgOID() {
        return this.encryptionAlgorithm.getAlgorithm().getId();
    }

    public byte[] getEncryptionAlgParams() {
        try {
            return encodeObj(this.encryptionAlgorithm.getParameters());
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("exception getting encryption parameters " + e);
        }
    }

    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable getSignedAttributes() {
        if (this.signedAttributeSet != null && this.signedAttributeValues == null) {
            this.signedAttributeValues = new com.android.internal.org.bouncycastle.asn1.cms.AttributeTable(this.signedAttributeSet);
        }
        return this.signedAttributeValues;
    }

    public com.android.internal.org.bouncycastle.asn1.cms.AttributeTable getUnsignedAttributes() {
        if (this.unsignedAttributeSet != null && this.unsignedAttributeValues == null) {
            this.unsignedAttributeValues = new com.android.internal.org.bouncycastle.asn1.cms.AttributeTable(this.unsignedAttributeSet);
        }
        return this.unsignedAttributeValues;
    }

    public byte[] getSignature() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.signature);
    }

    public com.android.internal.org.bouncycastle.cms.SignerInformationStore getCounterSignatures() {
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributes = getUnsignedAttributes();
        if (unsignedAttributes == null) {
            return new com.android.internal.org.bouncycastle.cms.SignerInformationStore(new java.util.ArrayList(0));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector all = unsignedAttributes.getAll(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.counterSignature);
        for (int i = 0; i < all.size(); i++) {
            com.android.internal.org.bouncycastle.asn1.ASN1Set attrValues = ((com.android.internal.org.bouncycastle.asn1.cms.Attribute) all.get(i)).getAttrValues();
            attrValues.size();
            java.util.Enumeration objects = attrValues.getObjects();
            while (objects.hasMoreElements()) {
                arrayList.add(new com.android.internal.org.bouncycastle.cms.SignerInformation(com.android.internal.org.bouncycastle.asn1.cms.SignerInfo.getInstance(objects.nextElement()), null, new com.android.internal.org.bouncycastle.cms.CMSProcessableByteArray(getSignature()), null));
            }
        }
        return new com.android.internal.org.bouncycastle.cms.SignerInformationStore(arrayList);
    }

    public byte[] getEncodedSignedAttributes() throws java.io.IOException {
        if (this.signedAttributeSet != null) {
            return this.signedAttributeSet.getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        }
        return null;
    }

    private boolean doVerify(com.android.internal.org.bouncycastle.cms.SignerInformationVerifier signerInformationVerifier) throws com.android.internal.org.bouncycastle.cms.CMSException {
        java.lang.String encryptionAlgName = com.android.internal.org.bouncycastle.cms.CMSSignedHelper.INSTANCE.getEncryptionAlgName(getEncryptionAlgOID());
        try {
            com.android.internal.org.bouncycastle.operator.ContentVerifier contentVerifier = signerInformationVerifier.getContentVerifier(this.encryptionAlgorithm, this.info.getDigestAlgorithm());
            try {
                java.io.OutputStream outputStream = contentVerifier.getOutputStream();
                if (this.resultDigest == null) {
                    com.android.internal.org.bouncycastle.operator.DigestCalculator digestCalculator = signerInformationVerifier.getDigestCalculator(getDigestAlgorithmID());
                    if (this.content != null) {
                        java.io.OutputStream outputStream2 = digestCalculator.getOutputStream();
                        if (this.signedAttributeSet == null) {
                            if (contentVerifier instanceof com.android.internal.org.bouncycastle.operator.RawContentVerifier) {
                                this.content.write(outputStream2);
                            } else {
                                com.android.internal.org.bouncycastle.util.io.TeeOutputStream teeOutputStream = new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(outputStream2, outputStream);
                                this.content.write(teeOutputStream);
                                teeOutputStream.close();
                            }
                        } else {
                            this.content.write(outputStream2);
                            outputStream.write(getEncodedSignedAttributes());
                        }
                        outputStream2.close();
                    } else if (this.signedAttributeSet != null) {
                        outputStream.write(getEncodedSignedAttributes());
                    } else {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("data not encapsulated in signature - use detached constructor.");
                    }
                    this.resultDigest = digestCalculator.getDigest();
                } else if (this.signedAttributeSet == null) {
                    if (this.content != null) {
                        this.content.write(outputStream);
                    }
                } else {
                    outputStream.write(getEncodedSignedAttributes());
                }
                outputStream.close();
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive singleValuedSignedAttribute = getSingleValuedSignedAttribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.contentType, "content-type");
                if (singleValuedSignedAttribute == null) {
                    if (!this.isCounterSignature && this.signedAttributeSet != null) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("The content-type attribute type MUST be present whenever signed attributes are present in signed-data");
                    }
                } else {
                    if (this.isCounterSignature) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("[For counter signatures,] the signedAttributes field MUST NOT contain a content-type attribute");
                    }
                    if (!(singleValuedSignedAttribute instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier)) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("content-type attribute value not of ASN.1 type 'OBJECT IDENTIFIER'");
                    }
                    if (!((com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) singleValuedSignedAttribute).equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) this.contentType)) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("content-type attribute value does not match eContentType");
                    }
                }
                com.android.internal.org.bouncycastle.asn1.cms.AttributeTable signedAttributes = getSignedAttributes();
                com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributes = getUnsignedAttributes();
                if (unsignedAttributes != null && unsignedAttributes.getAll(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.cmsAlgorithmProtect).size() > 0) {
                    throw new com.android.internal.org.bouncycastle.cms.CMSException("A cmsAlgorithmProtect attribute MUST be a signed attribute");
                }
                if (signedAttributes != null) {
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector all = signedAttributes.getAll(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.cmsAlgorithmProtect);
                    if (all.size() > 1) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("Only one instance of a cmsAlgorithmProtect attribute can be present");
                    }
                    if (all.size() > 0) {
                        com.android.internal.org.bouncycastle.asn1.cms.Attribute attribute = com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(all.get(0));
                        if (attribute.getAttrValues().size() == 1) {
                            com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection cMSAlgorithmProtection = com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection.getInstance(attribute.getAttributeValues()[0]);
                            if (!com.android.internal.org.bouncycastle.cms.CMSUtils.isEquivalent(cMSAlgorithmProtection.getDigestAlgorithm(), this.info.getDigestAlgorithm())) {
                                throw new com.android.internal.org.bouncycastle.cms.CMSException("CMS Algorithm Identifier Protection check failed for digestAlgorithm");
                            }
                            if (!com.android.internal.org.bouncycastle.cms.CMSUtils.isEquivalent(cMSAlgorithmProtection.getSignatureAlgorithm(), this.info.getDigestEncryptionAlgorithm())) {
                                throw new com.android.internal.org.bouncycastle.cms.CMSException("CMS Algorithm Identifier Protection check failed for signatureAlgorithm");
                            }
                        } else {
                            throw new com.android.internal.org.bouncycastle.cms.CMSException("A cmsAlgorithmProtect attribute MUST contain exactly one value");
                        }
                    }
                }
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive singleValuedSignedAttribute2 = getSingleValuedSignedAttribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.messageDigest, "message-digest");
                if (singleValuedSignedAttribute2 == null) {
                    if (this.signedAttributeSet != null) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("the message-digest signed attribute type MUST be present when there are any signed attributes present");
                    }
                } else {
                    if (!(singleValuedSignedAttribute2 instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString)) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSException("message-digest attribute value not of ASN.1 type 'OCTET STRING'");
                    }
                    if (!com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(this.resultDigest, ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) singleValuedSignedAttribute2).getOctets())) {
                        throw new com.android.internal.org.bouncycastle.cms.CMSSignerDigestMismatchException("message-digest attribute value does not match calculated value");
                    }
                }
                if (signedAttributes != null && signedAttributes.getAll(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.counterSignature).size() > 0) {
                    throw new com.android.internal.org.bouncycastle.cms.CMSException("A countersignature attribute MUST NOT be a signed attribute");
                }
                com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributes2 = getUnsignedAttributes();
                if (unsignedAttributes2 != null) {
                    com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector all2 = unsignedAttributes2.getAll(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.counterSignature);
                    for (int i = 0; i < all2.size(); i++) {
                        if (com.android.internal.org.bouncycastle.asn1.cms.Attribute.getInstance(all2.get(i)).getAttrValues().size() < 1) {
                            throw new com.android.internal.org.bouncycastle.cms.CMSException("A countersignature attribute MUST contain at least one AttributeValue");
                        }
                    }
                }
                try {
                    if (this.signedAttributeSet == null && this.resultDigest != null && (contentVerifier instanceof com.android.internal.org.bouncycastle.operator.RawContentVerifier)) {
                        com.android.internal.org.bouncycastle.operator.RawContentVerifier rawContentVerifier = (com.android.internal.org.bouncycastle.operator.RawContentVerifier) contentVerifier;
                        if (encryptionAlgName.equals(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA)) {
                            return rawContentVerifier.verify(new com.android.internal.org.bouncycastle.asn1.x509.DigestInfo(new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(this.digestAlgorithm.getAlgorithm(), com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE), this.resultDigest).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER), getSignature());
                        }
                        return rawContentVerifier.verify(this.resultDigest, getSignature());
                    }
                    return contentVerifier.verify(getSignature());
                } catch (java.io.IOException e) {
                    throw new com.android.internal.org.bouncycastle.cms.CMSException("can't process mime object to create signature.", e);
                }
            } catch (com.android.internal.org.bouncycastle.operator.OperatorCreationException e2) {
                throw new com.android.internal.org.bouncycastle.cms.CMSException("can't create digest calculator: " + e2.getMessage(), e2);
            } catch (java.io.IOException e3) {
                throw new com.android.internal.org.bouncycastle.cms.CMSException("can't process mime object to create signature.", e3);
            }
        } catch (com.android.internal.org.bouncycastle.operator.OperatorCreationException e4) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("can't create content verifier: " + e4.getMessage(), e4);
        }
    }

    public boolean verify(com.android.internal.org.bouncycastle.cms.SignerInformationVerifier signerInformationVerifier) throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.cms.Time signingTime = getSigningTime();
        if (signerInformationVerifier.hasAssociatedCertificate() && signingTime != null && !signerInformationVerifier.getAssociatedCertificate().isValidOn(signingTime.getDate())) {
            throw new com.android.internal.org.bouncycastle.cms.CMSVerifierCertificateNotValidException("verifier not valid at signingTime");
        }
        return doVerify(signerInformationVerifier);
    }

    public com.android.internal.org.bouncycastle.asn1.cms.SignerInfo toASN1Structure() {
        return this.info;
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive getSingleValuedSignedAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str) throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributes = getUnsignedAttributes();
        if (unsignedAttributes != null && unsignedAttributes.getAll(aSN1ObjectIdentifier).size() > 0) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("The " + str + " attribute MUST NOT be an unsigned attribute");
        }
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable signedAttributes = getSignedAttributes();
        if (signedAttributes == null) {
            return null;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector all = signedAttributes.getAll(aSN1ObjectIdentifier);
        switch (all.size()) {
            case 0:
                return null;
            case 1:
                com.android.internal.org.bouncycastle.asn1.ASN1Set attrValues = ((com.android.internal.org.bouncycastle.asn1.cms.Attribute) all.get(0)).getAttrValues();
                if (attrValues.size() != 1) {
                    throw new com.android.internal.org.bouncycastle.cms.CMSException("A " + str + " attribute MUST have a single attribute value");
                }
                return attrValues.getObjectAt(0).toASN1Primitive();
            default:
                throw new com.android.internal.org.bouncycastle.cms.CMSException("The SignedAttributes in a signerInfo MUST NOT include multiple instances of the " + str + " attribute");
        }
    }

    private com.android.internal.org.bouncycastle.asn1.cms.Time getSigningTime() throws com.android.internal.org.bouncycastle.cms.CMSException {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive singleValuedSignedAttribute = getSingleValuedSignedAttribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.signingTime, "signing-time");
        if (singleValuedSignedAttribute == null) {
            return null;
        }
        try {
            return com.android.internal.org.bouncycastle.asn1.cms.Time.getInstance(singleValuedSignedAttribute);
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("signing-time attribute value not a valid 'Time' structure");
        }
    }

    public static com.android.internal.org.bouncycastle.cms.SignerInformation replaceUnsignedAttributes(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation, com.android.internal.org.bouncycastle.asn1.cms.AttributeTable attributeTable) {
        com.android.internal.org.bouncycastle.asn1.DERSet dERSet;
        com.android.internal.org.bouncycastle.asn1.cms.SignerInfo signerInfo = signerInformation.info;
        if (attributeTable == null) {
            dERSet = null;
        } else {
            dERSet = new com.android.internal.org.bouncycastle.asn1.DERSet(attributeTable.toASN1EncodableVector());
        }
        return new com.android.internal.org.bouncycastle.cms.SignerInformation(new com.android.internal.org.bouncycastle.asn1.cms.SignerInfo(signerInfo.getSID(), signerInfo.getDigestAlgorithm(), signerInfo.getAuthenticatedAttributes(), signerInfo.getDigestEncryptionAlgorithm(), signerInfo.getEncryptedDigest(), dERSet), signerInformation.contentType, signerInformation.content, null);
    }

    public static com.android.internal.org.bouncycastle.cms.SignerInformation addCounterSigners(com.android.internal.org.bouncycastle.cms.SignerInformation signerInformation, com.android.internal.org.bouncycastle.cms.SignerInformationStore signerInformationStore) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector;
        com.android.internal.org.bouncycastle.asn1.cms.SignerInfo signerInfo = signerInformation.info;
        com.android.internal.org.bouncycastle.asn1.cms.AttributeTable unsignedAttributes = signerInformation.getUnsignedAttributes();
        if (unsignedAttributes != null) {
            aSN1EncodableVector = unsignedAttributes.toASN1EncodableVector();
        } else {
            aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.Iterator<com.android.internal.org.bouncycastle.cms.SignerInformation> it = signerInformationStore.getSigners().iterator();
        while (it.hasNext()) {
            aSN1EncodableVector2.add(it.next().toASN1Structure());
        }
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.cms.Attribute(com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes.counterSignature, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2)));
        return new com.android.internal.org.bouncycastle.cms.SignerInformation(new com.android.internal.org.bouncycastle.asn1.cms.SignerInfo(signerInfo.getSID(), signerInfo.getDigestAlgorithm(), signerInfo.getAuthenticatedAttributes(), signerInfo.getDigestEncryptionAlgorithm(), signerInfo.getEncryptedDigest(), new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector)), signerInformation.contentType, signerInformation.content, null);
    }
}
