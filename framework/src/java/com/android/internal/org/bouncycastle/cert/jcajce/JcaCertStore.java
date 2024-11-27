package com.android.internal.org.bouncycastle.cert.jcajce;

/* loaded from: classes4.dex */
public class JcaCertStore extends com.android.internal.org.bouncycastle.util.CollectionStore {
    public JcaCertStore(java.util.Collection collection) throws java.security.cert.CertificateEncodingException {
        super(convertCerts(collection));
    }

    private static java.util.Collection convertCerts(java.util.Collection collection) throws java.security.cert.CertificateEncodingException {
        java.util.ArrayList arrayList = new java.util.ArrayList(collection.size());
        for (java.lang.Object obj : collection) {
            if (obj instanceof java.security.cert.X509Certificate) {
                try {
                    arrayList.add(new com.android.internal.org.bouncycastle.cert.X509CertificateHolder(((java.security.cert.X509Certificate) obj).getEncoded()));
                } catch (java.io.IOException e) {
                    throw new java.security.cert.CertificateEncodingException("unable to read encoding: " + e.getMessage());
                }
            } else {
                arrayList.add((com.android.internal.org.bouncycastle.cert.X509CertificateHolder) obj);
            }
        }
        return arrayList;
    }
}
