package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

/* loaded from: classes4.dex */
public class PKIXCertPath extends java.security.cert.CertPath {
    static final java.util.List certPathEncodings;
    private java.util.List certificates;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;

    static {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("PkiPath");
        arrayList.add("PKCS7");
        certPathEncodings = java.util.Collections.unmodifiableList(arrayList);
    }

    private java.util.List sortCerts(java.util.List list) {
        boolean z;
        boolean z2;
        if (list.size() < 2) {
            return list;
        }
        javax.security.auth.x500.X500Principal issuerX500Principal = ((java.security.cert.X509Certificate) list.get(0)).getIssuerX500Principal();
        int i = 1;
        while (true) {
            if (i == list.size()) {
                z = true;
                break;
            }
            if (issuerX500Principal.equals(((java.security.cert.X509Certificate) list.get(i)).getSubjectX500Principal())) {
                issuerX500Principal = ((java.security.cert.X509Certificate) list.get(i)).getIssuerX500Principal();
                i++;
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            return list;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.ArrayList arrayList2 = new java.util.ArrayList(list);
        for (int i2 = 0; i2 < list.size(); i2++) {
            java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) list.get(i2);
            javax.security.auth.x500.X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
            int i3 = 0;
            while (true) {
                if (i3 == list.size()) {
                    z2 = false;
                    break;
                }
                if (!((java.security.cert.X509Certificate) list.get(i3)).getIssuerX500Principal().equals(subjectX500Principal)) {
                    i3++;
                } else {
                    z2 = true;
                    break;
                }
            }
            if (!z2) {
                arrayList.add(x509Certificate);
                list.remove(i2);
            }
        }
        if (arrayList.size() > 1) {
            return arrayList2;
        }
        for (int i4 = 0; i4 != arrayList.size(); i4++) {
            javax.security.auth.x500.X500Principal issuerX500Principal2 = ((java.security.cert.X509Certificate) arrayList.get(i4)).getIssuerX500Principal();
            int i5 = 0;
            while (true) {
                if (i5 < list.size()) {
                    java.security.cert.X509Certificate x509Certificate2 = (java.security.cert.X509Certificate) list.get(i5);
                    if (!issuerX500Principal2.equals(x509Certificate2.getSubjectX500Principal())) {
                        i5++;
                    } else {
                        arrayList.add(x509Certificate2);
                        list.remove(i5);
                        break;
                    }
                }
            }
        }
        if (list.size() > 0) {
            return arrayList2;
        }
        return arrayList;
    }

    PKIXCertPath(java.util.List list) {
        super("X.509");
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
        this.certificates = sortCerts(new java.util.ArrayList(list));
    }

    PKIXCertPath(java.io.InputStream inputStream, java.lang.String str) throws java.security.cert.CertificateException {
        super("X.509");
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
        try {
            if (str.equalsIgnoreCase("PkiPath")) {
                com.android.internal.org.bouncycastle.asn1.ASN1Primitive readObject = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream).readObject();
                if (!(readObject instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence)) {
                    throw new java.security.cert.CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                }
                java.util.Enumeration objects = ((com.android.internal.org.bouncycastle.asn1.ASN1Sequence) readObject).getObjects();
                this.certificates = new java.util.ArrayList();
                java.security.cert.CertificateFactory createCertificateFactory = this.helper.createCertificateFactory("X.509");
                while (objects.hasMoreElements()) {
                    this.certificates.add(0, createCertificateFactory.generateCertificate(new java.io.ByteArrayInputStream(((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER))));
                }
            } else {
                if (!str.equalsIgnoreCase("PKCS7") && !str.equalsIgnoreCase("PEM")) {
                    throw new java.security.cert.CertificateException("unsupported encoding: " + str);
                }
                java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream);
                this.certificates = new java.util.ArrayList();
                java.security.cert.CertificateFactory createCertificateFactory2 = this.helper.createCertificateFactory("X.509");
                while (true) {
                    java.security.cert.Certificate generateCertificate = createCertificateFactory2.generateCertificate(bufferedInputStream);
                    if (generateCertificate == null) {
                        break;
                    } else {
                        this.certificates.add(generateCertificate);
                    }
                }
            }
            this.certificates = sortCerts(this.certificates);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateException("IOException throw while decoding CertPath:\n" + e.toString());
        } catch (java.security.NoSuchProviderException e2) {
            throw new java.security.cert.CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + e2.toString());
        }
    }

    @Override // java.security.cert.CertPath
    public java.util.Iterator getEncodings() {
        return certPathEncodings.iterator();
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() throws java.security.cert.CertificateEncodingException {
        java.util.Iterator encodings = getEncodings();
        if (encodings.hasNext()) {
            java.lang.Object next = encodings.next();
            if (next instanceof java.lang.String) {
                return getEncoded((java.lang.String) next);
            }
            return null;
        }
        return null;
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(java.lang.String str) throws java.security.cert.CertificateEncodingException {
        if (str.equalsIgnoreCase("PkiPath")) {
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            java.util.ListIterator listIterator = this.certificates.listIterator(this.certificates.size());
            while (listIterator.hasPrevious()) {
                aSN1EncodableVector.add(toASN1Object((java.security.cert.X509Certificate) listIterator.previous()));
            }
            return toDEREncoded(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
        }
        if (str.equalsIgnoreCase("PKCS7")) {
            com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo contentInfo = new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data, null);
            com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
            for (int i = 0; i != this.certificates.size(); i++) {
                aSN1EncodableVector2.add(toASN1Object((java.security.cert.X509Certificate) this.certificates.get(i)));
            }
            return toDEREncoded(new com.android.internal.org.bouncycastle.asn1.pkcs.ContentInfo(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.signedData, new com.android.internal.org.bouncycastle.asn1.pkcs.SignedData(new com.android.internal.org.bouncycastle.asn1.ASN1Integer(1L), new com.android.internal.org.bouncycastle.asn1.DERSet(), contentInfo, new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector2), null, new com.android.internal.org.bouncycastle.asn1.DERSet())));
        }
        throw new java.security.cert.CertificateEncodingException("unsupported encoding: " + str);
    }

    @Override // java.security.cert.CertPath
    public java.util.List getCertificates() {
        return java.util.Collections.unmodifiableList(new java.util.ArrayList(this.certificates));
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Object(java.security.cert.X509Certificate x509Certificate) throws java.security.cert.CertificateEncodingException {
        try {
            return new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(x509Certificate.getEncoded()).readObject();
        } catch (java.lang.Exception e) {
            throw new java.security.cert.CertificateEncodingException("Exception while encoding certificate: " + e.toString());
        }
    }

    private byte[] toDEREncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) throws java.security.cert.CertificateEncodingException {
        try {
            return aSN1Encodable.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
        } catch (java.io.IOException e) {
            throw new java.security.cert.CertificateEncodingException("Exception thrown: " + e);
        }
    }
}
