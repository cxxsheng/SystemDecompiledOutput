package com.android.internal.org.bouncycastle.cert;

/* loaded from: classes4.dex */
class CertUtils {
    private static java.util.Set EMPTY_SET = java.util.Collections.unmodifiableSet(new java.util.HashSet());
    private static java.util.List EMPTY_LIST = java.util.Collections.unmodifiableList(new java.util.ArrayList());

    CertUtils() {
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Primitive parseNonEmptyASN1(byte[] bArr) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive fromByteArray = com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray(bArr);
        if (fromByteArray == null) {
            throw new java.io.IOException("no content found");
        }
        return fromByteArray;
    }

    static com.android.internal.org.bouncycastle.cert.X509CertificateHolder generateFullCert(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tBSCertificate) {
        try {
            return new com.android.internal.org.bouncycastle.cert.X509CertificateHolder(generateStructure(tBSCertificate, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, tBSCertificate)));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("cannot produce certificate signature");
        }
    }

    static com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder generateFullAttrCert(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo attributeCertificateInfo) {
        try {
            return new com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder(generateAttrStructure(attributeCertificateInfo, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, attributeCertificateInfo)));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("cannot produce attribute certificate signature");
        }
    }

    static com.android.internal.org.bouncycastle.cert.X509CRLHolder generateFullCRL(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.x509.TBSCertList tBSCertList) {
        try {
            return new com.android.internal.org.bouncycastle.cert.X509CRLHolder(generateCRLStructure(tBSCertList, contentSigner.getAlgorithmIdentifier(), generateSig(contentSigner, tBSCertList)));
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("cannot produce certificate signature");
        }
    }

    private static byte[] generateSig(com.android.internal.org.bouncycastle.operator.ContentSigner contentSigner, com.android.internal.org.bouncycastle.asn1.ASN1Object aSN1Object) throws java.io.IOException {
        java.io.OutputStream outputStream = contentSigner.getOutputStream();
        aSN1Object.encodeTo(outputStream, com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        outputStream.close();
        return contentSigner.getSignature();
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.Certificate generateStructure(com.android.internal.org.bouncycastle.asn1.x509.TBSCertificate tBSCertificate, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertificate);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr));
        return com.android.internal.org.bouncycastle.asn1.x509.Certificate.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate generateAttrStructure(com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificateInfo attributeCertificateInfo, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(attributeCertificateInfo);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr));
        return com.android.internal.org.bouncycastle.asn1.x509.AttributeCertificate.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }

    private static com.android.internal.org.bouncycastle.asn1.x509.CertificateList generateCRLStructure(com.android.internal.org.bouncycastle.asn1.x509.TBSCertList tBSCertList, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(tBSCertList);
        aSN1EncodableVector.add(algorithmIdentifier);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr));
        return com.android.internal.org.bouncycastle.asn1.x509.CertificateList.getInstance(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
    }

    static java.util.Set getCriticalExtensionOIDs(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        if (extensions == null) {
            return EMPTY_SET;
        }
        return java.util.Collections.unmodifiableSet(new java.util.HashSet(java.util.Arrays.asList(extensions.getCriticalExtensionOIDs())));
    }

    static java.util.Set getNonCriticalExtensionOIDs(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        if (extensions == null) {
            return EMPTY_SET;
        }
        return java.util.Collections.unmodifiableSet(new java.util.HashSet(java.util.Arrays.asList(extensions.getNonCriticalExtensionOIDs())));
    }

    static java.util.List getExtensionOIDs(com.android.internal.org.bouncycastle.asn1.x509.Extensions extensions) {
        if (extensions == null) {
            return EMPTY_LIST;
        }
        return java.util.Collections.unmodifiableList(java.util.Arrays.asList(extensions.getExtensionOIDs()));
    }

    static void addExtension(com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extensionsGenerator, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws com.android.internal.org.bouncycastle.cert.CertIOException {
        try {
            extensionsGenerator.addExtension(aSN1ObjectIdentifier, z, aSN1Encodable);
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.cert.CertIOException("cannot encode extension: " + e.getMessage(), e);
        }
    }

    static com.android.internal.org.bouncycastle.asn1.DERBitString booleanToBitString(boolean[] zArr) {
        byte[] bArr = new byte[(zArr.length + 7) / 8];
        for (int i = 0; i != zArr.length; i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (bArr[i2] | (zArr[i] ? 1 << (7 - (i % 8)) : 0));
        }
        int length = zArr.length % 8;
        if (length == 0) {
            return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERBitString(bArr, 8 - length);
    }

    static boolean[] bitStringToBoolean(com.android.internal.org.bouncycastle.asn1.DERBitString dERBitString) {
        if (dERBitString != null) {
            byte[] bytes = dERBitString.getBytes();
            int length = (bytes.length * 8) - dERBitString.getPadBits();
            boolean[] zArr = new boolean[length];
            for (int i = 0; i != length; i++) {
                zArr[i] = (bytes[i / 8] & (128 >>> (i % 8))) != 0;
            }
            return zArr;
        }
        return null;
    }

    static java.util.Date recoverDate(com.android.internal.org.bouncycastle.asn1.ASN1GeneralizedTime aSN1GeneralizedTime) {
        try {
            return aSN1GeneralizedTime.getDate();
        } catch (java.text.ParseException e) {
            throw new java.lang.IllegalStateException("unable to recover date: " + e.getMessage());
        }
    }

    static boolean isAlgIdEqual(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        if (!algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.x509.allow_absent_equiv_NULL")) {
            if (algorithmIdentifier.getParameters() == null) {
                return algorithmIdentifier2.getParameters() == null || algorithmIdentifier2.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            }
            if (algorithmIdentifier2.getParameters() == null) {
                return algorithmIdentifier.getParameters() == null || algorithmIdentifier.getParameters().equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
            }
        }
        if (algorithmIdentifier.getParameters() != null) {
            return algorithmIdentifier.getParameters().equals(algorithmIdentifier2.getParameters());
        }
        if (algorithmIdentifier2.getParameters() != null) {
            return algorithmIdentifier2.getParameters().equals(algorithmIdentifier.getParameters());
        }
        return true;
    }

    static com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator doReplaceExtension(com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extensionsGenerator, com.android.internal.org.bouncycastle.asn1.x509.Extension extension) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions generate = extensionsGenerator.generate();
        com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extensionsGenerator2 = new com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator();
        java.util.Enumeration oids = generate.oids();
        boolean z = false;
        while (oids.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
            if (aSN1ObjectIdentifier.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) extension.getExtnId())) {
                extensionsGenerator2.addExtension(extension);
                z = true;
            } else {
                extensionsGenerator2.addExtension(generate.getExtension(aSN1ObjectIdentifier));
            }
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("replace - original extension (OID = " + extension.getExtnId() + ") not found");
        }
        return extensionsGenerator2;
    }

    static com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator doRemoveExtension(com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extensionsGenerator, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        com.android.internal.org.bouncycastle.asn1.x509.Extensions generate = extensionsGenerator.generate();
        com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator extensionsGenerator2 = new com.android.internal.org.bouncycastle.asn1.x509.ExtensionsGenerator();
        java.util.Enumeration oids = generate.oids();
        boolean z = false;
        while (oids.hasMoreElements()) {
            com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oids.nextElement();
            if (aSN1ObjectIdentifier2.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) aSN1ObjectIdentifier)) {
                z = true;
            } else {
                extensionsGenerator2.addExtension(generate.getExtension(aSN1ObjectIdentifier2));
            }
        }
        if (!z) {
            throw new java.lang.IllegalArgumentException("remove - extension (OID = " + aSN1ObjectIdentifier + ") not found");
        }
        return extensionsGenerator2;
    }
}
