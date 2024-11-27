package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
class CMSUtils {
    private static final java.util.Set<java.lang.String> des = new java.util.HashSet();
    private static final java.util.Set mqvAlgs = new java.util.HashSet();
    private static final java.util.Set ecAlgs = new java.util.HashSet();
    private static final java.util.Set gostAlgs = new java.util.HashSet();

    CMSUtils() {
    }

    static {
        des.add("DES");
        des.add("DESEDE");
    }

    static boolean isMQV(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return mqvAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isEC(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ecAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isGOST(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return gostAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isDES(java.lang.String str) {
        return des.contains(com.android.internal.org.bouncycastle.util.Strings.toUpperCase(str));
    }

    static boolean isEquivalent(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null || !algorithmIdentifier.getAlgorithm().equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters = algorithmIdentifier.getParameters();
        com.android.internal.org.bouncycastle.asn1.ASN1Encodable parameters2 = algorithmIdentifier2.getParameters();
        if (parameters != null) {
            if (!parameters.equals(parameters2) && (!parameters.equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE) || parameters2 != null)) {
                return false;
            }
            return true;
        }
        if (parameters2 != null && !parameters2.equals(com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE)) {
            return false;
        }
        return true;
    }

    static com.android.internal.org.bouncycastle.asn1.cms.ContentInfo readContentInfo(byte[] bArr) throws com.android.internal.org.bouncycastle.cms.CMSException {
        return readContentInfo(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bArr));
    }

    static com.android.internal.org.bouncycastle.asn1.cms.ContentInfo readContentInfo(java.io.InputStream inputStream) throws com.android.internal.org.bouncycastle.cms.CMSException {
        return readContentInfo(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(inputStream));
    }

    static java.util.List getCertificatesFromStore(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.util.Iterator it = store.getMatches(null).iterator();
            while (it.hasNext()) {
                arrayList.add(((com.android.internal.org.bouncycastle.cert.X509CertificateHolder) it.next()).toASN1Structure());
            }
            return arrayList;
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("error processing certs", e);
        }
    }

    static java.util.List getAttributeCertificatesFromStore(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.util.Iterator it = store.getMatches(null).iterator();
            while (it.hasNext()) {
                arrayList.add(new com.android.internal.org.bouncycastle.asn1.DERTaggedObject(false, 2, ((com.android.internal.org.bouncycastle.cert.X509AttributeCertificateHolder) it.next()).toASN1Structure()));
            }
            return arrayList;
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("error processing certs", e);
        }
    }

    static java.util.List getCRLsFromStore(com.android.internal.org.bouncycastle.util.Store store) throws com.android.internal.org.bouncycastle.cms.CMSException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            for (java.lang.Object obj : store.getMatches(null)) {
                if (obj instanceof com.android.internal.org.bouncycastle.cert.X509CRLHolder) {
                    arrayList.add(((com.android.internal.org.bouncycastle.cert.X509CRLHolder) obj).toASN1Structure());
                } else if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        } catch (java.lang.ClassCastException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("error processing certs", e);
        }
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Set createBerSetFromList(java.util.List list) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) it.next());
        }
        return new com.android.internal.org.bouncycastle.asn1.BERSet(aSN1EncodableVector);
    }

    static com.android.internal.org.bouncycastle.asn1.ASN1Set createDerSetFromList(java.util.List list) {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        java.util.Iterator it = list.iterator();
        while (it.hasNext()) {
            aSN1EncodableVector.add((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) it.next());
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSet(aSN1EncodableVector);
    }

    static java.io.OutputStream createBEROctetOutputStream(java.io.OutputStream outputStream, int i, boolean z, int i2) throws java.io.IOException {
        com.android.internal.org.bouncycastle.asn1.BEROctetStringGenerator bEROctetStringGenerator = new com.android.internal.org.bouncycastle.asn1.BEROctetStringGenerator(outputStream, i, z);
        if (i2 != 0) {
            return bEROctetStringGenerator.getOctetOutputStream(new byte[i2]);
        }
        return bEROctetStringGenerator.getOctetOutputStream();
    }

    private static com.android.internal.org.bouncycastle.asn1.cms.ContentInfo readContentInfo(com.android.internal.org.bouncycastle.asn1.ASN1InputStream aSN1InputStream) throws com.android.internal.org.bouncycastle.cms.CMSException {
        try {
            com.android.internal.org.bouncycastle.asn1.cms.ContentInfo contentInfo = com.android.internal.org.bouncycastle.asn1.cms.ContentInfo.getInstance(aSN1InputStream.readObject());
            if (contentInfo == null) {
                throw new com.android.internal.org.bouncycastle.cms.CMSException("No content found.");
            }
            return contentInfo;
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("IOException reading content.", e);
        } catch (java.lang.ClassCastException e2) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("Malformed content.", e2);
        } catch (java.lang.IllegalArgumentException e3) {
            throw new com.android.internal.org.bouncycastle.cms.CMSException("Malformed content.", e3);
        }
    }

    public static byte[] streamToByteArray(java.io.InputStream inputStream) throws java.io.IOException {
        return com.android.internal.org.bouncycastle.util.io.Streams.readAll(inputStream);
    }

    public static byte[] streamToByteArray(java.io.InputStream inputStream, int i) throws java.io.IOException {
        return com.android.internal.org.bouncycastle.util.io.Streams.readAllLimited(inputStream, i);
    }

    static java.io.InputStream attachDigestsToInputStream(java.util.Collection collection, java.io.InputStream inputStream) {
        java.util.Iterator it = collection.iterator();
        while (it.hasNext()) {
            inputStream = new com.android.internal.org.bouncycastle.util.io.TeeInputStream(inputStream, ((com.android.internal.org.bouncycastle.operator.DigestCalculator) it.next()).getOutputStream());
        }
        return inputStream;
    }

    static java.io.OutputStream attachSignersToOutputStream(java.util.Collection collection, java.io.OutputStream outputStream) {
        java.util.Iterator it = collection.iterator();
        while (it.hasNext()) {
            outputStream = getSafeTeeOutputStream(outputStream, ((com.android.internal.org.bouncycastle.cms.SignerInfoGenerator) it.next()).getCalculatingOutputStream());
        }
        return outputStream;
    }

    static java.io.OutputStream getSafeOutputStream(java.io.OutputStream outputStream) {
        return outputStream == null ? new com.android.internal.org.bouncycastle.cms.NullOutputStream() : outputStream;
    }

    static java.io.OutputStream getSafeTeeOutputStream(java.io.OutputStream outputStream, java.io.OutputStream outputStream2) {
        return outputStream == null ? getSafeOutputStream(outputStream2) : outputStream2 == null ? getSafeOutputStream(outputStream) : new com.android.internal.org.bouncycastle.util.io.TeeOutputStream(outputStream, outputStream2);
    }
}
