package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class CertStoreCollectionSpi extends java.security.cert.CertStoreSpi {
    private java.security.cert.CollectionCertStoreParameters params;

    public CertStoreCollectionSpi(java.security.cert.CertStoreParameters certStoreParameters) throws java.security.InvalidAlgorithmParameterException {
        super(certStoreParameters);
        if (!(certStoreParameters instanceof java.security.cert.CollectionCertStoreParameters)) {
            throw new java.security.InvalidAlgorithmParameterException("com.android.internal.org.bouncycastle.jce.provider.CertStoreCollectionSpi: parameter must be a CollectionCertStoreParameters object\n" + certStoreParameters.toString());
        }
        this.params = (java.security.cert.CollectionCertStoreParameters) certStoreParameters;
    }

    @Override // java.security.cert.CertStoreSpi
    public java.util.Collection engineGetCertificates(java.security.cert.CertSelector certSelector) throws java.security.cert.CertStoreException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<?> it = this.params.getCollection().iterator();
        if (certSelector == null) {
            while (it.hasNext()) {
                java.lang.Object next = it.next();
                if (next instanceof java.security.cert.Certificate) {
                    arrayList.add(next);
                }
            }
        } else {
            while (it.hasNext()) {
                java.lang.Object next2 = it.next();
                if ((next2 instanceof java.security.cert.Certificate) && certSelector.match((java.security.cert.Certificate) next2)) {
                    arrayList.add(next2);
                }
            }
        }
        return arrayList;
    }

    @Override // java.security.cert.CertStoreSpi
    public java.util.Collection engineGetCRLs(java.security.cert.CRLSelector cRLSelector) throws java.security.cert.CertStoreException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<?> it = this.params.getCollection().iterator();
        if (cRLSelector == null) {
            while (it.hasNext()) {
                java.lang.Object next = it.next();
                if (next instanceof java.security.cert.CRL) {
                    arrayList.add(next);
                }
            }
        } else {
            while (it.hasNext()) {
                java.lang.Object next2 = it.next();
                if ((next2 instanceof java.security.cert.CRL) && cRLSelector.match((java.security.cert.CRL) next2)) {
                    arrayList.add(next2);
                }
            }
        }
        return arrayList;
    }
}
