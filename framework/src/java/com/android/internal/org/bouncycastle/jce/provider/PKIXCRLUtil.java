package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
abstract class PKIXCRLUtil {
    PKIXCRLUtil() {
    }

    static java.util.Set findCRLs(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector pKIXCRLStoreSelector, java.util.Date date, java.util.List list, java.util.List list2) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.security.cert.X509Certificate certificateChecking;
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            findCRLs(hashSet, pKIXCRLStoreSelector, list2);
            findCRLs(hashSet, pKIXCRLStoreSelector, list);
            java.util.HashSet hashSet2 = new java.util.HashSet();
            java.util.Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                java.security.cert.X509CRL x509crl = (java.security.cert.X509CRL) it.next();
                if (x509crl.getNextUpdate().after(date) && ((certificateChecking = pKIXCRLStoreSelector.getCertificateChecking()) == null || x509crl.getThisUpdate().before(certificateChecking.getNotAfter()))) {
                    hashSet2.add(x509crl);
                }
            }
            return hashSet2;
        } catch (com.android.internal.org.bouncycastle.jce.provider.AnnotatedException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Exception obtaining complete CRLs.", e);
        }
    }

    private static void findCRLs(java.util.HashSet hashSet, com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector pKIXCRLStoreSelector, java.util.List list) throws com.android.internal.org.bouncycastle.jce.provider.AnnotatedException {
        java.util.Iterator it = list.iterator();
        com.android.internal.org.bouncycastle.jce.provider.AnnotatedException annotatedException = null;
        boolean z = false;
        while (it.hasNext()) {
            try {
                hashSet.addAll(com.android.internal.org.bouncycastle.jcajce.PKIXCRLStoreSelector.getCRLs(pKIXCRLStoreSelector, (java.security.cert.CertStore) it.next()));
                z = true;
            } catch (java.security.cert.CertStoreException e) {
                annotatedException = new com.android.internal.org.bouncycastle.jce.provider.AnnotatedException("Exception searching in X.509 CRL store.", e);
            }
        }
        if (!z && annotatedException != null) {
            throw annotatedException;
        }
    }
}
