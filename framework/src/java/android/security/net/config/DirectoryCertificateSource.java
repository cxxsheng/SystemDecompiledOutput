package android.security.net.config;

/* loaded from: classes3.dex */
abstract class DirectoryCertificateSource implements android.security.net.config.CertificateSource {
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.AM_PM, 'b', 'c', android.text.format.DateFormat.DATE, 'e', 'f'};
    private static final java.lang.String LOG_TAG = "DirectoryCertificateSrc";
    private final java.security.cert.CertificateFactory mCertFactory;
    private java.util.Set<java.security.cert.X509Certificate> mCertificates;
    private final java.io.File mDir;
    private final java.lang.Object mLock = new java.lang.Object();

    private interface CertSelector {
        boolean match(java.security.cert.X509Certificate x509Certificate);
    }

    protected abstract boolean isCertMarkedAsRemoved(java.lang.String str);

    protected DirectoryCertificateSource(java.io.File file) {
        this.mDir = file;
        try {
            this.mCertFactory = java.security.cert.CertificateFactory.getInstance("X.509");
        } catch (java.security.cert.CertificateException e) {
            throw new java.lang.RuntimeException("Failed to obtain X.509 CertificateFactory", e);
        }
    }

    @Override // android.security.net.config.CertificateSource
    public java.util.Set<java.security.cert.X509Certificate> getCertificates() {
        java.security.cert.X509Certificate readCertificate;
        synchronized (this.mLock) {
            if (this.mCertificates != null) {
                return this.mCertificates;
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            if (this.mDir.isDirectory()) {
                for (java.lang.String str : this.mDir.list()) {
                    if (!isCertMarkedAsRemoved(str) && (readCertificate = readCertificate(str)) != null) {
                        arraySet.add(readCertificate);
                    }
                }
            }
            this.mCertificates = arraySet;
            return this.mCertificates;
        }
    }

    @Override // android.security.net.config.CertificateSource
    public java.security.cert.X509Certificate findBySubjectAndPublicKey(final java.security.cert.X509Certificate x509Certificate) {
        return findCert(x509Certificate.getSubjectX500Principal(), new android.security.net.config.DirectoryCertificateSource.CertSelector() { // from class: android.security.net.config.DirectoryCertificateSource.1
            @Override // android.security.net.config.DirectoryCertificateSource.CertSelector
            public boolean match(java.security.cert.X509Certificate x509Certificate2) {
                return x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
            }
        });
    }

    @Override // android.security.net.config.CertificateSource
    public java.security.cert.X509Certificate findByIssuerAndSignature(final java.security.cert.X509Certificate x509Certificate) {
        return findCert(x509Certificate.getIssuerX500Principal(), new android.security.net.config.DirectoryCertificateSource.CertSelector() { // from class: android.security.net.config.DirectoryCertificateSource.2
            @Override // android.security.net.config.DirectoryCertificateSource.CertSelector
            public boolean match(java.security.cert.X509Certificate x509Certificate2) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return true;
                } catch (java.lang.Exception e) {
                    return false;
                }
            }
        });
    }

    @Override // android.security.net.config.CertificateSource
    public java.util.Set<java.security.cert.X509Certificate> findAllByIssuerAndSignature(final java.security.cert.X509Certificate x509Certificate) {
        return findCerts(x509Certificate.getIssuerX500Principal(), new android.security.net.config.DirectoryCertificateSource.CertSelector() { // from class: android.security.net.config.DirectoryCertificateSource.3
            @Override // android.security.net.config.DirectoryCertificateSource.CertSelector
            public boolean match(java.security.cert.X509Certificate x509Certificate2) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return true;
                } catch (java.lang.Exception e) {
                    return false;
                }
            }
        });
    }

    @Override // android.security.net.config.CertificateSource
    public void handleTrustStorageUpdate() {
        synchronized (this.mLock) {
            this.mCertificates = null;
        }
    }

    private java.util.Set<java.security.cert.X509Certificate> findCerts(javax.security.auth.x500.X500Principal x500Principal, android.security.net.config.DirectoryCertificateSource.CertSelector certSelector) {
        java.security.cert.X509Certificate readCertificate;
        java.lang.String hash = getHash(x500Principal);
        android.util.ArraySet arraySet = null;
        for (int i = 0; i >= 0; i++) {
            java.lang.String str = hash + android.media.MediaMetrics.SEPARATOR + i;
            if (!new java.io.File(this.mDir, str).exists()) {
                break;
            }
            if (!isCertMarkedAsRemoved(str) && (readCertificate = readCertificate(str)) != null && x500Principal.equals(readCertificate.getSubjectX500Principal()) && certSelector.match(readCertificate)) {
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet();
                }
                arraySet.add(readCertificate);
            }
        }
        return arraySet != null ? arraySet : java.util.Collections.emptySet();
    }

    private java.security.cert.X509Certificate findCert(javax.security.auth.x500.X500Principal x500Principal, android.security.net.config.DirectoryCertificateSource.CertSelector certSelector) {
        java.security.cert.X509Certificate readCertificate;
        java.lang.String hash = getHash(x500Principal);
        for (int i = 0; i >= 0; i++) {
            java.lang.String str = hash + android.media.MediaMetrics.SEPARATOR + i;
            if (new java.io.File(this.mDir, str).exists()) {
                if (!isCertMarkedAsRemoved(str) && (readCertificate = readCertificate(str)) != null && x500Principal.equals(readCertificate.getSubjectX500Principal()) && certSelector.match(readCertificate)) {
                    return readCertificate;
                }
            } else {
                return null;
            }
        }
        return null;
    }

    private java.lang.String getHash(javax.security.auth.x500.X500Principal x500Principal) {
        return intToHexString(hashName(x500Principal), 8);
    }

    private static java.lang.String intToHexString(int i, int i2) {
        int i3;
        int i4 = 8;
        char[] cArr = new char[8];
        while (true) {
            i4--;
            cArr[i4] = DIGITS[i & 15];
            i >>>= 4;
            if (i == 0 && (i3 = 8 - i4) >= i2) {
                return new java.lang.String(cArr, i4, i3);
            }
        }
    }

    private static int hashName(javax.security.auth.x500.X500Principal x500Principal) {
        try {
            byte[] digest = java.security.MessageDigest.getInstance(android.security.keystore.KeyProperties.DIGEST_MD5).digest(x500Principal.getEncoded());
            return ((digest[3] & 255) << 24) | ((digest[0] & 255) << 0) | ((digest[1] & 255) << 8) | ((digest[2] & 255) << 16);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0043: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:16:0x0043 */
    private java.security.cert.X509Certificate readCertificate(java.lang.String str) {
        java.io.BufferedInputStream bufferedInputStream;
        java.lang.AutoCloseable autoCloseable;
        java.lang.AutoCloseable autoCloseable2 = null;
        try {
            try {
                bufferedInputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(new java.io.File(this.mDir, str)));
            } catch (java.io.IOException | java.security.cert.CertificateException e) {
                e = e;
                bufferedInputStream = null;
            } catch (java.lang.Throwable th) {
                th = th;
                libcore.io.IoUtils.closeQuietly(autoCloseable2);
                throw th;
            }
            try {
                java.security.cert.X509Certificate x509Certificate = (java.security.cert.X509Certificate) this.mCertFactory.generateCertificate(bufferedInputStream);
                libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                return x509Certificate;
            } catch (java.io.IOException | java.security.cert.CertificateException e2) {
                e = e2;
                android.util.Log.e(LOG_TAG, "Failed to read certificate from " + str, e);
                libcore.io.IoUtils.closeQuietly(bufferedInputStream);
                return null;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            autoCloseable2 = autoCloseable;
            libcore.io.IoUtils.closeQuietly(autoCloseable2);
            throw th;
        }
    }
}
