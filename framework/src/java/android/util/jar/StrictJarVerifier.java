package android.util.jar;

/* loaded from: classes3.dex */
class StrictJarVerifier {
    private static final java.lang.String[] DIGEST_ALGORITHMS = {android.security.keystore.KeyProperties.DIGEST_SHA512, android.security.keystore.KeyProperties.DIGEST_SHA384, "SHA-256", "SHA1"};
    private static final int MAX_JAR_SIGNERS = 10;
    private static final java.lang.String SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME = "X-Android-APK-Signed";
    private final java.lang.String jarName;
    private final int mainAttributesEnd;
    private final android.util.jar.StrictJarManifest manifest;
    private final java.util.HashMap<java.lang.String, byte[]> metaEntries;
    private final boolean signatureSchemeRollbackProtectionsEnforced;
    private final java.util.Hashtable<java.lang.String, java.util.HashMap<java.lang.String, java.util.jar.Attributes>> signatures = new java.util.Hashtable<>(5);
    private final java.util.Hashtable<java.lang.String, java.security.cert.Certificate[]> certificates = new java.util.Hashtable<>(5);
    private final java.util.Hashtable<java.lang.String, java.security.cert.Certificate[][]> verifiedEntries = new java.util.Hashtable<>();

    static class VerifierEntry extends java.io.OutputStream {
        private final java.security.cert.Certificate[][] certChains;
        private final java.security.MessageDigest digest;
        private final byte[] hash;
        private final java.lang.String name;
        private final java.util.Hashtable<java.lang.String, java.security.cert.Certificate[][]> verifiedEntries;

        VerifierEntry(java.lang.String str, java.security.MessageDigest messageDigest, byte[] bArr, java.security.cert.Certificate[][] certificateArr, java.util.Hashtable<java.lang.String, java.security.cert.Certificate[][]> hashtable) {
            this.name = str;
            this.digest = messageDigest;
            this.hash = bArr;
            this.certChains = certificateArr;
            this.verifiedEntries = hashtable;
        }

        @Override // java.io.OutputStream
        public void write(int i) {
            this.digest.update((byte) i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) {
            this.digest.update(bArr, i, i2);
        }

        void verify() {
            if (!android.util.jar.StrictJarVerifier.verifyMessageDigest(this.digest.digest(), this.hash)) {
                throw android.util.jar.StrictJarVerifier.invalidDigest("META-INF/MANIFEST.MF", this.name, this.name);
            }
            this.verifiedEntries.put(this.name, this.certChains);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.SecurityException invalidDigest(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        throw new java.lang.SecurityException(str + " has invalid digest for " + str2 + " in " + str3);
    }

    private static java.lang.SecurityException failedVerification(java.lang.String str, java.lang.String str2) {
        throw new java.lang.SecurityException(str + " failed verification of " + str2);
    }

    private static java.lang.SecurityException failedVerification(java.lang.String str, java.lang.String str2, java.lang.Throwable th) {
        throw new java.lang.SecurityException(str + " failed verification of " + str2, th);
    }

    StrictJarVerifier(java.lang.String str, android.util.jar.StrictJarManifest strictJarManifest, java.util.HashMap<java.lang.String, byte[]> hashMap, boolean z) {
        this.jarName = str;
        this.manifest = strictJarManifest;
        this.metaEntries = hashMap;
        this.mainAttributesEnd = strictJarManifest.getMainAttributesEnd();
        this.signatureSchemeRollbackProtectionsEnforced = z;
    }

    android.util.jar.StrictJarVerifier.VerifierEntry initEntry(java.lang.String str) {
        java.util.jar.Attributes attributes;
        if (this.manifest == null || this.signatures.isEmpty() || (attributes = this.manifest.getAttributes(str)) == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.String, java.util.HashMap<java.lang.String, java.util.jar.Attributes>> entry : this.signatures.entrySet()) {
            if (entry.getValue().get(str) != null) {
                java.security.cert.Certificate[] certificateArr = this.certificates.get(entry.getKey());
                if (certificateArr != null) {
                    arrayList.add(certificateArr);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        java.security.cert.Certificate[][] certificateArr2 = (java.security.cert.Certificate[][]) arrayList.toArray(new java.security.cert.Certificate[arrayList.size()][]);
        for (int i = 0; i < DIGEST_ALGORITHMS.length; i++) {
            java.lang.String str2 = DIGEST_ALGORITHMS[i];
            java.lang.String value = attributes.getValue(str2 + "-Digest");
            if (value != null) {
                try {
                    return new android.util.jar.StrictJarVerifier.VerifierEntry(str, java.security.MessageDigest.getInstance(str2), value.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1), certificateArr2, this.verifiedEntries);
                } catch (java.security.NoSuchAlgorithmException e) {
                }
            }
        }
        return null;
    }

    void addMetaEntry(java.lang.String str, byte[] bArr) {
        this.metaEntries.put(str.toUpperCase(java.util.Locale.US), bArr);
    }

    synchronized boolean readCertificates() {
        int i = 0;
        if (this.metaEntries.isEmpty()) {
            return false;
        }
        java.util.Iterator<java.lang.String> it = this.metaEntries.keySet().iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (next.endsWith(".DSA") || next.endsWith(".RSA") || next.endsWith(".EC")) {
                i++;
                if (i > 10) {
                    throw new java.lang.SecurityException("APK Signature Scheme v1 only supports a maximum of 10 signers");
                }
                verifyCertificate(next);
                it.remove();
            }
        }
        return true;
    }

    static java.security.cert.Certificate[] verifyBytes(byte[] bArr, byte[] bArr2) throws java.security.GeneralSecurityException {
        try {
            try {
                java.lang.Object startJarVerification = sun.security.jca.Providers.startJarVerification();
                sun.security.pkcs.PKCS7 pkcs7 = new sun.security.pkcs.PKCS7(bArr);
                sun.security.pkcs.SignerInfo[] verify = pkcs7.verify(bArr2);
                if (verify == null || verify.length == 0) {
                    throw new java.security.GeneralSecurityException("Failed to verify signature: no verified SignerInfos");
                }
                java.util.ArrayList certificateChain = verify[0].getCertificateChain(pkcs7);
                if (certificateChain == null) {
                    throw new java.security.GeneralSecurityException("Failed to find verified SignerInfo certificate chain");
                }
                if (certificateChain.isEmpty()) {
                    throw new java.security.GeneralSecurityException("Verified SignerInfo certificate chain is emtpy");
                }
                java.security.cert.Certificate[] certificateArr = (java.security.cert.Certificate[]) certificateChain.toArray(new java.security.cert.X509Certificate[certificateChain.size()]);
                sun.security.jca.Providers.stopJarVerification(startJarVerification);
                return certificateArr;
            } catch (java.io.IOException e) {
                throw new java.security.GeneralSecurityException("IO exception verifying jar cert", e);
            }
        } catch (java.lang.Throwable th) {
            sun.security.jca.Providers.stopJarVerification((java.lang.Object) null);
            throw th;
        }
    }

    private void verifyCertificate(java.lang.String str) {
        byte[] bArr;
        java.lang.String value;
        boolean z;
        boolean z2;
        boolean z3 = false;
        java.lang.String str2 = str.substring(0, str.lastIndexOf(46)) + ".SF";
        byte[] bArr2 = this.metaEntries.get(str2);
        if (bArr2 == null || (bArr = this.metaEntries.get("META-INF/MANIFEST.MF")) == null) {
            return;
        }
        try {
            java.security.cert.Certificate[] verifyBytes = verifyBytes(this.metaEntries.get(str), bArr2);
            if (verifyBytes != null) {
                this.certificates.put(str2, verifyBytes);
            }
            java.util.jar.Attributes attributes = new java.util.jar.Attributes();
            java.util.HashMap<java.lang.String, java.util.jar.Attributes> hashMap = new java.util.HashMap<>();
            try {
                new android.util.jar.StrictJarManifestReader(bArr2, attributes).readEntries(hashMap, null);
                if (this.signatureSchemeRollbackProtectionsEnforced && (value = attributes.getValue(SF_ATTRIBUTE_ANDROID_APK_SIGNED_NAME)) != null) {
                    java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(value, ",");
                    while (true) {
                        if (!stringTokenizer.hasMoreTokens()) {
                            z = false;
                            z2 = false;
                            break;
                        }
                        java.lang.String trim = stringTokenizer.nextToken().trim();
                        if (!trim.isEmpty()) {
                            try {
                                int parseInt = java.lang.Integer.parseInt(trim);
                                if (parseInt == 2) {
                                    z2 = false;
                                    z = true;
                                    break;
                                } else if (parseInt == 3) {
                                    z = false;
                                    z2 = true;
                                    break;
                                }
                            } catch (java.lang.Exception e) {
                            }
                        }
                    }
                    if (z) {
                        throw new java.lang.SecurityException(str2 + " indicates " + this.jarName + " is signed using APK Signature Scheme v2, but no such signature was found. Signature stripped?");
                    }
                    if (z2) {
                        throw new java.lang.SecurityException(str2 + " indicates " + this.jarName + " is signed using APK Signature Scheme v3, but no such signature was found. Signature stripped?");
                    }
                }
                if (attributes.get(java.util.jar.Attributes.Name.SIGNATURE_VERSION) == null) {
                    return;
                }
                java.lang.String value2 = attributes.getValue("Created-By");
                if (value2 != null && value2.indexOf("signtool") != -1) {
                    z3 = true;
                }
                if (this.mainAttributesEnd > 0 && !z3 && !verify(attributes, "-Digest-Manifest-Main-Attributes", bArr, 0, this.mainAttributesEnd, false, true)) {
                    throw failedVerification(this.jarName, str2);
                }
                if (!verify(attributes, z3 ? "-Digest" : "-Digest-Manifest", bArr, 0, bArr.length, false, false)) {
                    for (java.util.Map.Entry<java.lang.String, java.util.jar.Attributes> entry : hashMap.entrySet()) {
                        android.util.jar.StrictJarManifest.Chunk chunk = this.manifest.getChunk(entry.getKey());
                        if (chunk == null) {
                            return;
                        }
                        if (!verify(entry.getValue(), "-Digest", bArr, chunk.start, chunk.end, z3, false)) {
                            throw invalidDigest(str2, entry.getKey(), this.jarName);
                        }
                    }
                }
                this.metaEntries.put(str2, null);
                this.signatures.put(str2, hashMap);
            } catch (java.io.IOException e2) {
            }
        } catch (java.security.GeneralSecurityException e3) {
            throw failedVerification(this.jarName, str2, e3);
        }
    }

    boolean isSignedJar() {
        return this.certificates.size() > 0;
    }

    private boolean verify(java.util.jar.Attributes attributes, java.lang.String str, byte[] bArr, int i, int i2, boolean z, boolean z2) {
        for (int i3 = 0; i3 < DIGEST_ALGORITHMS.length; i3++) {
            java.lang.String str2 = DIGEST_ALGORITHMS[i3];
            java.lang.String value = attributes.getValue(str2 + str);
            if (value != null) {
                try {
                    java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(str2);
                    if (z) {
                        int i4 = i2 - 1;
                        if (bArr[i4] == 10 && bArr[i2 - 2] == 10) {
                            messageDigest.update(bArr, i, i4 - i);
                            return verifyMessageDigest(messageDigest.digest(), value.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
                        }
                    }
                    messageDigest.update(bArr, i, i2 - i);
                    return verifyMessageDigest(messageDigest.digest(), value.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1));
                } catch (java.security.NoSuchAlgorithmException e) {
                }
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean verifyMessageDigest(byte[] bArr, byte[] bArr2) {
        try {
            return java.security.MessageDigest.isEqual(bArr, java.util.Base64.getDecoder().decode(bArr2));
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    java.security.cert.Certificate[][] getCertificateChains(java.lang.String str) {
        return this.verifiedEntries.get(str);
    }

    void removeMetaEntries() {
        this.metaEntries.clear();
    }
}
