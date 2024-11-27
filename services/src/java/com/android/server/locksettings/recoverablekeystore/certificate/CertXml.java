package com.android.server.locksettings.recoverablekeystore.certificate;

/* loaded from: classes2.dex */
public final class CertXml {
    private static final java.lang.String ENDPOINT_CERT_ITEM_TAG = "cert";
    private static final java.lang.String ENDPOINT_CERT_LIST_TAG = "endpoints";
    private static final java.lang.String INTERMEDIATE_CERT_ITEM_TAG = "cert";
    private static final java.lang.String INTERMEDIATE_CERT_LIST_TAG = "intermediates";
    private static final java.lang.String METADATA_NODE_TAG = "metadata";
    private static final java.lang.String METADATA_SERIAL_NODE_TAG = "serial";
    private final java.util.List<java.security.cert.X509Certificate> endpointCerts;
    private final java.util.List<java.security.cert.X509Certificate> intermediateCerts;
    private final long serial;

    private CertXml(long j, java.util.List<java.security.cert.X509Certificate> list, java.util.List<java.security.cert.X509Certificate> list2) {
        this.serial = j;
        this.intermediateCerts = list;
        this.endpointCerts = list2;
    }

    public long getSerial() {
        return this.serial;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.security.cert.X509Certificate> getAllIntermediateCerts() {
        return this.intermediateCerts;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.security.cert.X509Certificate> getAllEndpointCerts() {
        return this.endpointCerts;
    }

    public java.security.cert.CertPath getRandomEndpointCert(java.security.cert.X509Certificate x509Certificate, @android.annotation.Nullable java.util.Date date) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        return getEndpointCert(new java.security.SecureRandom().nextInt(this.endpointCerts.size()), date, x509Certificate);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.security.cert.CertPath getEndpointCert(int i, @android.annotation.Nullable java.util.Date date, java.security.cert.X509Certificate x509Certificate) throws com.android.server.locksettings.recoverablekeystore.certificate.CertValidationException {
        return com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.validateCert(date, x509Certificate, this.intermediateCerts, this.endpointCerts.get(i));
    }

    public static com.android.server.locksettings.recoverablekeystore.certificate.CertXml parse(byte[] bArr) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        org.w3c.dom.Element xmlRootNode = com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlRootNode(bArr);
        return new com.android.server.locksettings.recoverablekeystore.certificate.CertXml(parseSerial(xmlRootNode), parseIntermediateCerts(xmlRootNode), parseEndpointCerts(xmlRootNode));
    }

    private static long parseSerial(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        return java.lang.Long.parseLong(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(1, element, METADATA_NODE_TAG, METADATA_SERIAL_NODE_TAG).get(0));
    }

    private static java.util.List<java.security.cert.X509Certificate> parseIntermediateCerts(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        java.util.List<java.lang.String> xmlNodeContents = com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(0, element, INTERMEDIATE_CERT_LIST_TAG, "cert");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = xmlNodeContents.iterator();
        while (it.hasNext()) {
            arrayList.add(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeCert(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeBase64(it.next())));
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    private static java.util.List<java.security.cert.X509Certificate> parseEndpointCerts(org.w3c.dom.Element element) throws com.android.server.locksettings.recoverablekeystore.certificate.CertParsingException {
        java.util.List<java.lang.String> xmlNodeContents = com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.getXmlNodeContents(2, element, ENDPOINT_CERT_LIST_TAG, "cert");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.String> it = xmlNodeContents.iterator();
        while (it.hasNext()) {
            arrayList.add(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeCert(com.android.server.locksettings.recoverablekeystore.certificate.CertUtils.decodeBase64(it.next())));
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }
}
