package com.android.server.locksettings.recoverablekeystore.certificate;

/* loaded from: classes2.dex */
public final class SigXml {
    private static final java.lang.String INTERMEDIATE_CERT_ITEM_TAG = "cert";
    private static final java.lang.String INTERMEDIATE_CERT_LIST_TAG = "intermediates";
    private static final java.lang.String SIGNATURE_NODE_TAG = "value";
    private static final java.lang.String SIGNER_CERT_NODE_TAG = "certificate";
    private final java.util.List<java.security.cert.X509Certificate> intermediateCerts;
    private final byte[] signature;
    private final java.security.cert.X509Certificate signerCert;

    private SigXml(java.util.List<java.security.cert.X509Certificate> list, java.security.cert.X509Certificate x509Certificate, byte[] bArr) {
        this.intermediateCerts = list;
        this.signerCert = x509Certificate;
        this.signature = bArr;
    }

    public void verifyFileSignature(java.security.cert.X509Certificate x509Certificate, byte[] bArr, @android.annotation.Nullable java.util.Date date) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.validateCert(date, x509Certificate, this.intermediateCerts, this.signerCert);
        com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.verifyRsaSha256Signature(this.signerCert.getPublicKey(), this.signature, bArr);
    }

    public static com.android.server.locksettings.recoverablekeystore.certificate.SigXml parse(byte[] bArr) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        org.w3c.dom.Element xmlRootNode = com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlRootNode(bArr);
        return new com.android.server.locksettings.recoverablekeystore.certificate.SigXml(parseIntermediateCerts(xmlRootNode), parseSignerCert(xmlRootNode), parseFileSignature(xmlRootNode));
    }

    private static java.util.List<java.security.cert.X509Certificate> parseIntermediateCerts(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        java.util.List<java.lang.String> xmlNodeContents = com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(0, element, INTERMEDIATE_CERT_LIST_TAG, INTERMEDIATE_CERT_ITEM_TAG);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = xmlNodeContents.iterator();
        while (it.hasNext()) {
            arrayList.add(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeCert(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeBase64(it.next())));
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    private static java.security.cert.X509Certificate parseSignerCert(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        return com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeCert(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeBase64(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(1, element, SIGNER_CERT_NODE_TAG).get(0)));
    }

    private static byte[] parseFileSignature(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        return com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeBase64(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(1, element, SIGNATURE_NODE_TAG).get(0));
    }
}
