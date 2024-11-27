package com.android.server.locksettings.recoverablekeystore.certificate;

/* loaded from: classes2.dex */
public final class CertUtils {
    private static final java.lang.String CERT_FORMAT = "X.509";
    private static final java.lang.String CERT_PATH_ALG = "PKIX";
    private static final java.lang.String CERT_STORE_ALG = "Collection";
    static final int MUST_EXIST_AT_LEAST_ONE = 2;
    static final int MUST_EXIST_EXACTLY_ONE = 1;
    static final int MUST_EXIST_UNENFORCED = 0;
    private static final java.lang.String SIGNATURE_ALG = "SHA256withRSA";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface MustExist {
    }

    private CertUtils() {
    }

    static java.security.cert.X509Certificate decodeCert(byte[] bArr) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        return decodeCert(new java.io.ByteArrayInputStream(bArr));
    }

    static java.security.cert.X509Certificate decodeCert(java.io.InputStream inputStream) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        try {
            try {
                return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance(CERT_FORMAT).generateCertificate(inputStream);
            } catch (java.security.cert.CertificateException e) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException(e);
            }
        } catch (java.security.cert.CertificateException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    static org.w3c.dom.Element getXmlRootNode(byte[] bArr) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        try {
            org.w3c.dom.Document parse = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.ByteArrayInputStream(bArr));
            parse.getDocumentElement().normalize();
            return parse.getDocumentElement();
        } catch (java.io.IOException | javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException(e);
        }
    }

    static java.util.List<java.lang.String> getXmlNodeContents(int i, org.w3c.dom.Element element, java.lang.String... strArr) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        if (strArr.length == 0) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException("The tag list must not be empty");
        }
        for (int i2 = 0; i2 < strArr.length - 1; i2++) {
            java.lang.String str = strArr[i2];
            java.util.List<org.w3c.dom.Element> xmlDirectChildren = getXmlDirectChildren(element, str);
            if ((xmlDirectChildren.size() == 0 && i != 0) || xmlDirectChildren.size() > 1) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException("The XML file must contain exactly one path with the tag " + str);
            }
            if (xmlDirectChildren.size() == 0) {
                return new java.util.ArrayList();
            }
            element = xmlDirectChildren.get(0);
        }
        java.util.List<org.w3c.dom.Element> xmlDirectChildren2 = getXmlDirectChildren(element, strArr[strArr.length - 1]);
        if (i == 1 && xmlDirectChildren2.size() != 1) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException("The XML file must contain exactly one node with the path " + java.lang.String.join(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER, strArr));
        }
        if (i == 2 && xmlDirectChildren2.size() == 0) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException("The XML file must contain at least one node with the path " + java.lang.String.join(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER, strArr));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<org.w3c.dom.Element> it = xmlDirectChildren2.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getTextContent().replaceAll("\\s", ""));
        }
        return arrayList;
    }

    private static java.util.List<org.w3c.dom.Element> getXmlDirectChildren(org.w3c.dom.Element element, java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        org.w3c.dom.NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            org.w3c.dom.Node item = childNodes.item(i);
            if (item.getNodeType() == 1 && item.getNodeName().equals(str)) {
                arrayList.add((org.w3c.dom.Element) item);
            }
        }
        return arrayList;
    }

    public static byte[] decodeBase64(java.lang.String str) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        try {
            return java.util.Base64.getDecoder().decode(str);
        } catch (java.lang.IllegalArgumentException e) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException(e);
        }
    }

    static void verifyRsaSha256Signature(java.security.PublicKey publicKey, byte[] bArr, byte[] bArr2) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(SIGNATURE_ALG);
            try {
                signature.initVerify(publicKey);
                signature.update(bArr2);
                if (!signature.verify(bArr)) {
                    throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException("The signature is invalid");
                }
            } catch (java.security.InvalidKeyException | java.security.SignatureException e) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException(e);
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    static java.security.cert.CertPath validateCert(@android.annotation.Nullable java.util.Date date, java.security.cert.X509Certificate x509Certificate, java.util.List<java.security.cert.X509Certificate> list, java.security.cert.X509Certificate x509Certificate2) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        java.security.cert.PKIXParameters buildPkixParams = buildPkixParams(date, x509Certificate, list, x509Certificate2);
        java.security.cert.CertPath buildCertPath = buildCertPath(buildPkixParams);
        try {
            try {
                java.security.cert.CertPathValidator.getInstance(CERT_PATH_ALG).validate(buildCertPath, buildPkixParams);
                return buildCertPath;
            } catch (java.security.InvalidAlgorithmParameterException | java.security.cert.CertPathValidatorException e) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException(e);
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    public static void validateCertPath(java.security.cert.X509Certificate x509Certificate, java.security.cert.CertPath certPath, @android.annotation.Nullable java.util.Date date) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        validateCertPath(date, x509Certificate, certPath);
    }

    @com.android.internal.annotations.VisibleForTesting
    static void validateCertPath(@android.annotation.Nullable java.util.Date date, java.security.cert.X509Certificate x509Certificate, java.security.cert.CertPath certPath) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        if (certPath.getCertificates().isEmpty()) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException("The given certificate path is empty");
        }
        if (!(certPath.getCertificates().get(0) instanceof java.security.cert.X509Certificate)) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException("The given certificate path does not contain X509 certificates");
        }
        java.util.List<? extends java.security.cert.Certificate> certificates = certPath.getCertificates();
        validateCert(date, x509Certificate, certificates.subList(1, certificates.size()), (java.security.cert.X509Certificate) certificates.get(0));
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.security.cert.CertPath buildCertPath(java.security.cert.PKIXParameters pKIXParameters) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        try {
            try {
                return java.security.cert.CertPathBuilder.getInstance(CERT_PATH_ALG).build(pKIXParameters).getCertPath();
            } catch (java.security.InvalidAlgorithmParameterException | java.security.cert.CertPathBuilderException e) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException(e);
            }
        } catch (java.security.NoSuchAlgorithmException e2) {
            throw new java.lang.RuntimeException(e2);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.security.cert.PKIXParameters buildPkixParams(@android.annotation.Nullable java.util.Date date, java.security.cert.X509Certificate x509Certificate, java.util.List<java.security.cert.X509Certificate> list, java.security.cert.X509Certificate x509Certificate2) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        java.util.HashSet hashSet = new java.util.HashSet();
        hashSet.add(new java.security.cert.TrustAnchor(x509Certificate, null));
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        arrayList.add(x509Certificate2);
        try {
            java.security.cert.CertStore certStore = java.security.cert.CertStore.getInstance(CERT_STORE_ALG, new java.security.cert.CollectionCertStoreParameters(arrayList));
            java.security.cert.X509CertSelector x509CertSelector = new java.security.cert.X509CertSelector();
            x509CertSelector.setCertificate(x509Certificate2);
            try {
                java.security.cert.PKIXBuilderParameters pKIXBuilderParameters = new java.security.cert.PKIXBuilderParameters(hashSet, x509CertSelector);
                pKIXBuilderParameters.addCertStore(certStore);
                pKIXBuilderParameters.setDate(date);
                pKIXBuilderParameters.setRevocationEnabled(false);
                return pKIXBuilderParameters;
            } catch (java.security.InvalidAlgorithmParameterException e) {
                throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException(e);
            }
        } catch (java.security.InvalidAlgorithmParameterException e2) {
            throw new com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException(e2);
        } catch (java.security.NoSuchAlgorithmException e3) {
            throw new java.lang.RuntimeException(e3);
        }
    }
}
