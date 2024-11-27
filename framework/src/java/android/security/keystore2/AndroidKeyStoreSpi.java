package android.security.keystore2;

/* loaded from: classes3.dex */
public class AndroidKeyStoreSpi extends java.security.KeyStoreSpi {
    public static final java.lang.String NAME = "AndroidKeyStore";
    public static final java.lang.String TAG = "AndroidKeyStoreSpi";
    private android.security.KeyStore2 mKeyStore;
    private int mNamespace = -1;

    @Override // java.security.KeyStoreSpi
    public java.security.Key engineGetKey(java.lang.String str, char[] cArr) throws java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
        try {
            return android.security.keystore2.AndroidKeyStoreProvider.loadAndroidKeyStoreKeyFromKeystore(this.mKeyStore, str, this.mNamespace);
        } catch (android.security.keystore.KeyPermanentlyInvalidatedException e) {
            throw new java.security.UnrecoverableKeyException(e.getMessage());
        } catch (java.security.UnrecoverableKeyException e2) {
            java.lang.Throwable cause = e2.getCause();
            if ((cause instanceof android.security.KeyStoreException) && ((android.security.KeyStoreException) cause).getErrorCode() == 7) {
                return null;
            }
            throw e2;
        }
    }

    private android.system.keystore2.KeyDescriptor makeKeyDescriptor(java.lang.String str) {
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.domain = getTargetDomain();
        keyDescriptor.nspace = this.mNamespace;
        keyDescriptor.alias = str;
        keyDescriptor.blob = null;
        return keyDescriptor;
    }

    private int getTargetDomain() {
        if (this.mNamespace == -1) {
            return 0;
        }
        return 2;
    }

    private android.system.keystore2.KeyEntryResponse getKeyMetadata(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        android.system.keystore2.KeyDescriptor makeKeyDescriptor = makeKeyDescriptor(str);
        try {
            android.os.StrictMode.noteDiskRead();
            return this.mKeyStore.getKeyEntry(makeKeyDescriptor);
        } catch (android.security.KeyStoreException e) {
            if (e.getErrorCode() != 7) {
                android.util.Log.w(TAG, "Could not get key metadata from Keystore.", e);
                return null;
            }
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate[] engineGetCertificateChain(java.lang.String str) {
        java.security.cert.X509Certificate certificate;
        java.security.cert.Certificate[] certificateArr;
        android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null || keyMetadata.metadata.certificate == null || (certificate = toCertificate(keyMetadata.metadata.certificate)) == null) {
            return null;
        }
        byte[] bArr = keyMetadata.metadata.certificateChain;
        int i = 1;
        if (bArr != null) {
            java.util.Collection<java.security.cert.X509Certificate> certificates = toCertificates(bArr);
            certificateArr = new java.security.cert.Certificate[certificates.size() + 1];
            java.util.Iterator<java.security.cert.X509Certificate> it = certificates.iterator();
            while (it.hasNext()) {
                certificateArr[i] = it.next();
                i++;
            }
        } else {
            certificateArr = new java.security.cert.Certificate[1];
        }
        certificateArr[0] = certificate;
        return certificateArr;
    }

    @Override // java.security.KeyStoreSpi
    public java.security.cert.Certificate engineGetCertificate(java.lang.String str) {
        android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null) {
            return null;
        }
        byte[] bArr = keyMetadata.metadata.certificate;
        if (bArr != null) {
            return toCertificate(bArr);
        }
        byte[] bArr2 = keyMetadata.metadata.certificateChain;
        if (bArr2 == null) {
            return null;
        }
        return toCertificate(bArr2);
    }

    static java.security.cert.X509Certificate toCertificate(byte[] bArr) {
        try {
            return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.cert.CertificateException e) {
            android.util.Log.w(NAME, "Couldn't parse certificate in keystore", e);
            return null;
        }
    }

    private static java.util.Collection<java.security.cert.X509Certificate> toCertificates(byte[] bArr) {
        try {
            return java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(new java.io.ByteArrayInputStream(bArr));
        } catch (java.security.cert.CertificateException e) {
            android.util.Log.w(NAME, "Couldn't parse certificates in keystore", e);
            return new java.util.ArrayList();
        }
    }

    private static boolean getMgf1DigestSetterFlag() {
        try {
            return android.security.Flags.mgf1DigestSetterV2();
        } catch (java.lang.SecurityException e) {
            android.util.Log.w(NAME, "Cannot read MGF1 Digest setter flag value", e);
            return false;
        }
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Date engineGetCreationDate(java.lang.String str) {
        android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(str);
        if (keyMetadata == null || keyMetadata.metadata.modificationTimeMs == -1) {
            return null;
        }
        return new java.util.Date(keyMetadata.metadata.modificationTimeMs);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, java.security.Key key, char[] cArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        if (cArr != null && cArr.length > 0) {
            throw new java.security.KeyStoreException("entries cannot be protected with passwords");
        }
        if (key instanceof java.security.PrivateKey) {
            setPrivateKeyEntry(str, (java.security.PrivateKey) key, certificateArr, null);
        } else {
            if (key instanceof javax.crypto.SecretKey) {
                setSecretKeyEntry(str, (javax.crypto.SecretKey) key, null);
                return;
            }
            throw new java.security.KeyStoreException("Only PrivateKey and SecretKey are supported");
        }
    }

    private static android.security.keystore.KeyProtection getLegacyKeyProtectionParameter(java.security.PrivateKey privateKey) throws java.security.KeyStoreException {
        android.security.keystore.KeyProtection.Builder builder;
        java.lang.String algorithm = privateKey.getAlgorithm();
        if (android.security.keystore.KeyProperties.KEY_ALGORITHM_EC.equalsIgnoreCase(algorithm)) {
            builder = new android.security.keystore.KeyProtection.Builder(12);
            builder.setDigests(android.security.keystore.KeyProperties.DIGEST_NONE, "SHA-1", android.security.keystore.KeyProperties.DIGEST_SHA224, "SHA-256", android.security.keystore.KeyProperties.DIGEST_SHA384, android.security.keystore.KeyProperties.DIGEST_SHA512);
        } else if (android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(algorithm)) {
            builder = new android.security.keystore.KeyProtection.Builder(15);
            builder.setDigests(android.security.keystore.KeyProperties.DIGEST_NONE, android.security.keystore.KeyProperties.DIGEST_MD5, "SHA-1", android.security.keystore.KeyProperties.DIGEST_SHA224, "SHA-256", android.security.keystore.KeyProperties.DIGEST_SHA384, android.security.keystore.KeyProperties.DIGEST_SHA512);
            builder.setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE, android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
            builder.setSignaturePaddings(android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PSS);
            builder.setRandomizedEncryptionRequired(false);
        } else {
            throw new java.security.KeyStoreException("Unsupported key algorithm: " + algorithm);
        }
        builder.setUserAuthenticationRequired(false);
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setPrivateKeyEntry(java.lang.String str, java.security.PrivateKey privateKey, java.security.cert.Certificate[] certificateArr, java.security.KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        android.security.keystore.KeyProtection keyProtection;
        int i;
        int i2;
        int i3;
        byte[] bArr;
        java.lang.String str2;
        int i4;
        java.lang.String str3 = "SHA-1";
        if (protectionParameter == null) {
            keyProtection = getLegacyKeyProtectionParameter(privateKey);
            i3 = 1;
            i2 = 0;
        } else if (protectionParameter instanceof android.security.KeyStoreParameter) {
            i2 = 0;
            keyProtection = getLegacyKeyProtectionParameter(privateKey);
            i3 = 1;
        } else if (protectionParameter instanceof android.security.keystore.KeyProtection) {
            keyProtection = (android.security.keystore.KeyProtection) protectionParameter;
            if (!keyProtection.isCriticalToDeviceEncryption()) {
                i = 0;
            } else {
                i = 1;
            }
            if (!keyProtection.isStrongBoxBacked()) {
                i2 = i;
                i3 = 1;
            } else {
                i2 = i;
                i3 = 2;
            }
        } else {
            throw new java.security.KeyStoreException("Unsupported protection parameter class:" + protectionParameter.getClass().getName() + ". Supported: " + android.security.keystore.KeyProtection.class.getName() + ", " + android.security.KeyStoreParameter.class.getName());
        }
        if (certificateArr == null || certificateArr.length == 0) {
            throw new java.security.KeyStoreException("Must supply at least one Certificate with PrivateKey");
        }
        int length = certificateArr.length;
        java.security.cert.X509Certificate[] x509CertificateArr = new java.security.cert.X509Certificate[length];
        for (int i5 = 0; i5 < certificateArr.length; i5++) {
            if (!"X.509".equals(certificateArr[i5].getType())) {
                throw new java.security.KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i5);
            }
            if (!(certificateArr[i5] instanceof java.security.cert.X509Certificate)) {
                throw new java.security.KeyStoreException("Certificates must be in X.509 format: invalid cert #" + i5);
            }
            x509CertificateArr[i5] = (java.security.cert.X509Certificate) certificateArr[i5];
        }
        try {
            byte[] encoded = x509CertificateArr[0].getEncoded();
            if (certificateArr.length > 1) {
                int i6 = length - 1;
                byte[][] bArr2 = new byte[i6][];
                int i7 = 0;
                int i8 = 0;
                while (i7 < i6) {
                    int i9 = i7 + 1;
                    try {
                        bArr2[i7] = x509CertificateArr[i9].getEncoded();
                        i8 += bArr2[i7].length;
                        i7 = i9;
                    } catch (java.security.cert.CertificateEncodingException e) {
                        throw new java.security.KeyStoreException("Failed to encode certificate #" + i7, e);
                    }
                }
                bArr = new byte[i8];
                int i10 = 0;
                for (int i11 = 0; i11 < i6; i11++) {
                    int length2 = bArr2[i11].length;
                    java.lang.System.arraycopy(bArr2[i11], 0, bArr, i10, length2);
                    i10 += length2;
                    bArr2[i11] = null;
                }
            } else {
                bArr = null;
            }
            int targetDomain = getTargetDomain();
            if (privateKey instanceof android.security.keystore2.AndroidKeyStorePrivateKey) {
                assertCanReplace(str, targetDomain, this.mNamespace, ((android.security.keystore2.AndroidKeyStoreKey) privateKey).getUserKeyDescriptor());
                try {
                    android.os.StrictMode.noteDiskWrite();
                    this.mKeyStore.updateSubcomponents(((android.security.keystore2.AndroidKeyStorePrivateKey) privateKey).getKeyIdDescriptor(), encoded, bArr);
                    return;
                } catch (android.security.KeyStoreException e2) {
                    throw new java.security.KeyStoreException("Failed to store certificate and certificate chain", e2);
                }
            }
            java.lang.String format = privateKey.getFormat();
            if (format == null || !"PKCS#8".equals(format)) {
                throw new java.security.KeyStoreException("Unsupported private key export format: " + format + ". Only private keys which export their key material in PKCS#8 format are supported.");
            }
            byte[] encoded2 = privateKey.getEncoded();
            if (encoded2 == null) {
                throw new java.security.KeyStoreException("Private key did not export any key material");
            }
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(privateKey.getAlgorithm())));
                android.security.keystore2.KeyStore2ParameterUtils.forEachSetFlag(keyProtection.getPurposes(), new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreSpi$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, android.security.keystore.KeyProperties.Purpose.toKeymaster(((java.lang.Integer) obj).intValue())));
                    }
                });
                if (keyProtection.isDigestsSpecified()) {
                    java.lang.String[] digests = keyProtection.getDigests();
                    int length3 = digests.length;
                    int i12 = 0;
                    while (i12 < length3) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, android.security.keystore.KeyProperties.Digest.toKeymaster(digests[i12])));
                        i12++;
                        digests = digests;
                    }
                }
                java.lang.String[] blockModes = keyProtection.getBlockModes();
                int length4 = blockModes.length;
                int i13 = 0;
                while (i13 < length4) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, android.security.keystore.KeyProperties.BlockMode.toKeymaster(blockModes[i13])));
                    i13++;
                    blockModes = blockModes;
                }
                int[] allToKeymaster = android.security.keystore.KeyProperties.EncryptionPadding.allToKeymaster(keyProtection.getEncryptionPaddings());
                if ((keyProtection.getPurposes() & 1) != 0 && keyProtection.isRandomizedEncryptionRequired()) {
                    for (int i14 : allToKeymaster) {
                        if (!android.security.keystore2.KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(i14)) {
                            throw new java.security.KeyStoreException("Randomized encryption (IND-CPA) required but is violated by encryption padding mode: " + android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(i14) + ". See KeyProtection documentation.");
                        }
                    }
                }
                int length5 = allToKeymaster.length;
                int i15 = 0;
                while (i15 < length5) {
                    int i16 = allToKeymaster[i15];
                    int[] iArr = allToKeymaster;
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, i16));
                    if (i16 != 2) {
                        str2 = str3;
                    } else if (keyProtection.isMgf1DigestsSpecified()) {
                        java.util.Iterator<java.lang.String> it = keyProtection.getMgf1Digests().iterator();
                        while (it.hasNext()) {
                            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, android.security.keystore.KeyProperties.Digest.toKeymaster(it.next())));
                        }
                        str2 = str3;
                    } else {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, android.security.keystore.KeyProperties.Digest.toKeymaster(str3)));
                        if (getMgf1DigestSetterFlag()) {
                            str2 = str3;
                        } else {
                            int keymaster = android.security.keystore.KeyProperties.Digest.toKeymaster(str3);
                            java.lang.String[] digests2 = keyProtection.getDigests();
                            int length6 = digests2.length;
                            str2 = str3;
                            int i17 = 0;
                            while (i17 < length6) {
                                java.lang.String[] strArr = digests2;
                                int keymaster2 = android.security.keystore.KeyProperties.Digest.toKeymaster(digests2[i17]);
                                if (keymaster2 == keymaster) {
                                    i4 = keymaster;
                                } else {
                                    i4 = keymaster;
                                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, keymaster2));
                                }
                                i17++;
                                keymaster = i4;
                                digests2 = strArr;
                            }
                        }
                    }
                    i15++;
                    allToKeymaster = iArr;
                    str3 = str2;
                }
                for (java.lang.String str4 : keyProtection.getSignaturePaddings()) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, android.security.keystore.KeyProperties.SignaturePadding.toKeymaster(str4)));
                }
                android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(arrayList, keyProtection);
                if (keyProtection.getKeyValidityStart() != null) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613136, keyProtection.getKeyValidityStart()));
                }
                if (keyProtection.getKeyValidityForOriginationEnd() != null) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613137, keyProtection.getKeyValidityForOriginationEnd()));
                }
                if (keyProtection.getKeyValidityForConsumptionEnd() != null) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613138, keyProtection.getKeyValidityForConsumptionEnd()));
                }
                if (keyProtection.getMaxUsageCount() != -1) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306773, keyProtection.getMaxUsageCount()));
                }
                if (3 == android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(privateKey.getAlgorithm())) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435466, getKeymasterEcCurve(privateKey)));
                }
                try {
                    android.system.keystore2.KeyMetadata importKey = this.mKeyStore.getSecurityLevel(i3).importKey(makeKeyDescriptor(str), null, arrayList, i2, encoded2);
                    try {
                        android.os.StrictMode.noteDiskWrite();
                        this.mKeyStore.updateSubcomponents(importKey.key, encoded, bArr);
                    } catch (android.security.KeyStoreException e3) {
                        this.mKeyStore.deleteKey(importKey.key);
                        throw new java.security.KeyStoreException("Failed to store certificate and certificate chain", e3);
                    }
                } catch (android.security.KeyStoreException e4) {
                    throw new java.security.KeyStoreException("Failed to store private key", e4);
                }
            } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e5) {
                throw new java.security.KeyStoreException(e5);
            }
        } catch (java.security.cert.CertificateEncodingException e6) {
            throw new java.security.KeyStoreException("Failed to encode certificate #0", e6);
        }
    }

    private int getKeymasterEcCurve(java.security.PrivateKey privateKey) {
        if (privateKey instanceof java.security.interfaces.ECKey) {
            int keymasterEcCurve = android.security.keystore2.KeymasterUtils.getKeymasterEcCurve(android.security.keystore2.KeymasterUtils.getCurveName(((java.security.interfaces.ECPrivateKey) privateKey).getParams()));
            if (keymasterEcCurve >= 0) {
                return keymasterEcCurve;
            }
        } else if (privateKey instanceof java.security.interfaces.XECKey) {
            if (((java.security.interfaces.XECPrivateKey) privateKey).getParams().equals(java.security.spec.NamedParameterSpec.X25519)) {
                return 4;
            }
        } else {
            if (privateKey.getAlgorithm().equals(android.security.keystore.KeyProperties.KEY_ALGORITHM_XDH)) {
                return 4;
            }
            if ((privateKey instanceof java.security.interfaces.EdECKey) && ((java.security.interfaces.EdECPrivateKey) privateKey).getParams().equals(java.security.spec.NamedParameterSpec.ED25519)) {
                return 4;
            }
        }
        throw new java.lang.IllegalArgumentException("Unexpected Key " + privateKey.getClass().getName());
    }

    private static void assertCanReplace(java.lang.String str, int i, int i2, android.system.keystore2.KeyDescriptor keyDescriptor) throws java.security.KeyStoreException {
        if (!str.equals(keyDescriptor.alias) || keyDescriptor.domain != i || (keyDescriptor.domain == 2 && keyDescriptor.nspace != i2)) {
            throw new java.security.KeyStoreException("Can only replace keys with same alias: " + str + " != " + keyDescriptor.alias + " in the same target domain: " + i + " != " + keyDescriptor.domain + (i == 2 ? " in the same target namespace: " + i2 + " != " + keyDescriptor.nspace : ""));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x01a3 A[Catch: IllegalArgumentException | IllegalStateException -> 0x02ab, TryCatch #1 {IllegalArgumentException | IllegalStateException -> 0x02ab, blocks: (B:25:0x0085, B:27:0x00a5, B:29:0x00af, B:31:0x00b6, B:33:0x00c1, B:36:0x00c6, B:37:0x0102, B:38:0x0103, B:40:0x0109, B:41:0x0171, B:43:0x0185, B:46:0x019b, B:48:0x01a3, B:50:0x01ab, B:54:0x01b2, B:55:0x01d0, B:59:0x01d7, B:61:0x01e0, B:65:0x01ed, B:67:0x01f4, B:69:0x01fc, B:71:0x0210, B:73:0x021a, B:74:0x0228, B:76:0x022f, B:77:0x023d, B:79:0x0244, B:80:0x0252, B:82:0x0259, B:83:0x0267, B:85:0x026d, B:99:0x02a3, B:99:0x02a3, B:100:0x02aa, B:100:0x02aa, B:101:0x0190, B:103:0x0118, B:104:0x0134, B:105:0x0135, B:106:0x0151, B:107:0x0152, B:109:0x0158, B:111:0x0160), top: B:24:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01f4 A[Catch: IllegalArgumentException | IllegalStateException -> 0x02ab, TryCatch #1 {IllegalArgumentException | IllegalStateException -> 0x02ab, blocks: (B:25:0x0085, B:27:0x00a5, B:29:0x00af, B:31:0x00b6, B:33:0x00c1, B:36:0x00c6, B:37:0x0102, B:38:0x0103, B:40:0x0109, B:41:0x0171, B:43:0x0185, B:46:0x019b, B:48:0x01a3, B:50:0x01ab, B:54:0x01b2, B:55:0x01d0, B:59:0x01d7, B:61:0x01e0, B:65:0x01ed, B:67:0x01f4, B:69:0x01fc, B:71:0x0210, B:73:0x021a, B:74:0x0228, B:76:0x022f, B:77:0x023d, B:79:0x0244, B:80:0x0252, B:82:0x0259, B:83:0x0267, B:85:0x026d, B:99:0x02a3, B:99:0x02a3, B:100:0x02aa, B:100:0x02aa, B:101:0x0190, B:103:0x0118, B:104:0x0134, B:105:0x0135, B:106:0x0151, B:107:0x0152, B:109:0x0158, B:111:0x0160), top: B:24:0x0085 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02a3 A[Catch: IllegalArgumentException | IllegalStateException -> 0x02ab, IllegalArgumentException | IllegalStateException -> 0x02ab, TRY_ENTER, TryCatch #1 {IllegalArgumentException | IllegalStateException -> 0x02ab, blocks: (B:25:0x0085, B:27:0x00a5, B:29:0x00af, B:31:0x00b6, B:33:0x00c1, B:36:0x00c6, B:37:0x0102, B:38:0x0103, B:40:0x0109, B:41:0x0171, B:43:0x0185, B:46:0x019b, B:48:0x01a3, B:50:0x01ab, B:54:0x01b2, B:55:0x01d0, B:59:0x01d7, B:61:0x01e0, B:65:0x01ed, B:67:0x01f4, B:69:0x01fc, B:71:0x0210, B:73:0x021a, B:74:0x0228, B:76:0x022f, B:77:0x023d, B:79:0x0244, B:80:0x0252, B:82:0x0259, B:83:0x0267, B:85:0x026d, B:99:0x02a3, B:99:0x02a3, B:100:0x02aa, B:100:0x02aa, B:101:0x0190, B:103:0x0118, B:104:0x0134, B:105:0x0135, B:106:0x0151, B:107:0x0152, B:109:0x0158, B:111:0x0160), top: B:24:0x0085 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setSecretKeyEntry(java.lang.String str, javax.crypto.SecretKey secretKey, java.security.KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        boolean z;
        if (protectionParameter != null && !(protectionParameter instanceof android.security.keystore.KeyProtection)) {
            throw new java.security.KeyStoreException("Unsupported protection parameter class: " + protectionParameter.getClass().getName() + ". Supported: " + android.security.keystore.KeyProtection.class.getName());
        }
        android.security.keystore.KeyProtection keyProtection = (android.security.keystore.KeyProtection) protectionParameter;
        int targetDomain = getTargetDomain();
        if (secretKey instanceof android.security.keystore2.AndroidKeyStoreSecretKey) {
            android.security.keystore2.AndroidKeyStoreSecretKey androidKeyStoreSecretKey = (android.security.keystore2.AndroidKeyStoreSecretKey) secretKey;
            java.lang.String str2 = androidKeyStoreSecretKey.getUserKeyDescriptor().alias;
            assertCanReplace(str, targetDomain, this.mNamespace, androidKeyStoreSecretKey.getUserKeyDescriptor());
            if (keyProtection != null) {
                throw new java.security.KeyStoreException("Modifying KeyStore-backed key using protection parameters not supported");
            }
            return;
        }
        if (keyProtection == null) {
            throw new java.security.KeyStoreException("Protection parameters must be specified when importing a symmetric key");
        }
        java.lang.String format = secretKey.getFormat();
        if (format == null) {
            throw new java.security.KeyStoreException("Only secret keys that export their key material are supported");
        }
        if (!"RAW".equals(format)) {
            throw new java.security.KeyStoreException("Unsupported secret key material export format: " + format);
        }
        byte[] encoded = secretKey.getEncoded();
        if (encoded == null) {
            throw new java.security.KeyStoreException("Key did not export its key material despite supporting RAW format export");
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            int keymasterSecretKeyAlgorithm = android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterSecretKeyAlgorithm(secretKey.getAlgorithm());
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, keymasterSecretKeyAlgorithm));
            if (keymasterSecretKeyAlgorithm == 128) {
                int keymasterDigest = android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterDigest(secretKey.getAlgorithm());
                if (keymasterDigest == -1) {
                    throw new java.security.ProviderException("HMAC key algorithm digest unknown for key algorithm " + secretKey.getAlgorithm());
                }
                if (keyProtection.isDigestsSpecified()) {
                    int[] allToKeymaster = android.security.keystore.KeyProperties.Digest.allToKeymaster(keyProtection.getDigests());
                    if (allToKeymaster.length != 1 || allToKeymaster[0] != keymasterDigest) {
                        throw new java.security.KeyStoreException("Unsupported digests specification: " + java.util.Arrays.asList(keyProtection.getDigests()) + ". Only " + android.security.keystore.KeyProperties.Digest.fromKeymaster(keymasterDigest) + " supported for HMAC key algorithm " + secretKey.getAlgorithm());
                    }
                }
                int digestOutputSizeBits = android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(keymasterDigest);
                if (digestOutputSizeBits == -1) {
                    throw new java.security.ProviderException("HMAC key authorized for unsupported digest: " + android.security.keystore.KeyProperties.Digest.fromKeymaster(keymasterDigest));
                }
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, keymasterDigest));
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306376, digestOutputSizeBits));
            } else if (keyProtection.isDigestsSpecified()) {
                for (java.lang.String str3 : keyProtection.getDigests()) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, android.security.keystore.KeyProperties.Digest.toKeymaster(str3)));
                }
            }
            android.security.keystore2.KeyStore2ParameterUtils.forEachSetFlag(keyProtection.getPurposes(), new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreSpi$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, android.security.keystore.KeyProperties.Purpose.toKeymaster(((java.lang.Integer) obj).intValue())));
                }
            });
            if ((keyProtection.getPurposes() & 1) != 0) {
                if (((android.security.keystore.KeyProtection) protectionParameter).isRandomizedEncryptionRequired()) {
                    z = true;
                    for (java.lang.String str4 : keyProtection.getBlockModes()) {
                        int keymaster = android.security.keystore.KeyProperties.BlockMode.toKeymaster(str4);
                        if (z && !android.security.keystore2.KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(keymaster)) {
                            throw new java.security.KeyStoreException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + str4 + ". See KeyProtection documentation.");
                        }
                        if (keymasterSecretKeyAlgorithm == 32 && keymaster == 32) {
                            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306376, 96));
                        }
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, keymaster));
                    }
                    if (keyProtection.getSignaturePaddings().length <= 0) {
                        throw new java.security.KeyStoreException("Signature paddings not supported for symmetric keys");
                    }
                    for (java.lang.String str5 : keyProtection.getEncryptionPaddings()) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, android.security.keystore.KeyProperties.EncryptionPadding.toKeymaster(str5)));
                    }
                    android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(arrayList, keyProtection);
                    if (keyProtection.getKeyValidityStart() != null) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613136, keyProtection.getKeyValidityStart()));
                    }
                    if (keyProtection.getKeyValidityForOriginationEnd() != null) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613137, keyProtection.getKeyValidityForOriginationEnd()));
                    }
                    if (keyProtection.getKeyValidityForConsumptionEnd() != null) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613138, keyProtection.getKeyValidityForConsumptionEnd()));
                    }
                    if (keyProtection.getMaxUsageCount() != -1) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306773, keyProtection.getMaxUsageCount()));
                    }
                    if (keyProtection.isRollbackResistant()) {
                        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBool(1879048495));
                    }
                    try {
                        this.mKeyStore.getSecurityLevel(keyProtection.isStrongBoxBacked() ? 2 : 1).importKey(makeKeyDescriptor(str), null, arrayList, keyProtection.isCriticalToDeviceEncryption() ? 1 : 0, encoded);
                        return;
                    } catch (android.security.KeyStoreException e) {
                        throw new java.security.KeyStoreException("Failed to import secret key.", e);
                    }
                }
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBool(1879048199));
            }
            z = false;
            while (r8 < r6) {
            }
            if (keyProtection.getSignaturePaddings().length <= 0) {
            }
        } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e2) {
            throw new java.security.KeyStoreException(e2);
        }
    }

    private void setWrappedKeyEntry(java.lang.String str, android.security.keystore.WrappedKeyEntry wrappedKeyEntry, java.security.KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        int keymaster;
        int keymaster2;
        if (protectionParameter != null) {
            throw new java.security.KeyStoreException("Protection parameters are specified inside wrapped keys");
        }
        java.lang.String[] split = wrappedKeyEntry.getTransformation().split("/");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String str2 = split[0];
        if (android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(str2)) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 1));
            if (split.length > 1) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, android.security.keystore.KeyProperties.BlockMode.toKeymaster(split[1])));
            }
            if (split.length > 2 && (keymaster2 = android.security.keystore.KeyProperties.EncryptionPadding.toKeymaster(split[2])) != 1) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, keymaster2));
            }
            android.security.keystore.KeyGenParameterSpec keyGenParameterSpec = (android.security.keystore.KeyGenParameterSpec) wrappedKeyEntry.getAlgorithmParameterSpec();
            if (keyGenParameterSpec.isDigestsSpecified() && (keymaster = android.security.keystore.KeyProperties.Digest.toKeymaster(keyGenParameterSpec.getDigests()[0])) != 0) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, keymaster));
            }
            android.system.keystore2.KeyDescriptor makeKeyDescriptor = makeKeyDescriptor(wrappedKeyEntry.getWrappingKeyAlias());
            try {
                android.os.StrictMode.noteDiskRead();
                android.system.keystore2.KeyEntryResponse keyEntry = this.mKeyStore.getKeyEntry(makeKeyDescriptor);
                android.system.keystore2.KeyDescriptor makeKeyDescriptor2 = makeKeyDescriptor(str);
                android.security.KeyStoreSecurityLevel keyStoreSecurityLevel = new android.security.KeyStoreSecurityLevel(keyEntry.iSecurityLevel);
                long[] authenticatorIds = ((android.hardware.biometrics.BiometricManager) android.app.AppGlobals.getInitialApplication().getSystemService(android.hardware.biometrics.BiometricManager.class)).getAuthenticatorIds();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                android.system.keystore2.AuthenticatorSpec authenticatorSpec = new android.system.keystore2.AuthenticatorSpec();
                authenticatorSpec.authenticatorType = 1;
                authenticatorSpec.authenticatorId = android.security.GateKeeper.getSecureUserId();
                arrayList2.add(authenticatorSpec);
                for (long j : authenticatorIds) {
                    android.system.keystore2.AuthenticatorSpec authenticatorSpec2 = new android.system.keystore2.AuthenticatorSpec();
                    authenticatorSpec2.authenticatorType = 2;
                    authenticatorSpec2.authenticatorId = j;
                    arrayList2.add(authenticatorSpec2);
                }
                if (split.length > 2 && android.security.keystore.KeyProperties.EncryptionPadding.toKeymaster(split[2]) == 2 && keyEntry.metadata != null && keyEntry.metadata.authorizations != null) {
                    for (android.system.keystore2.Authorization authorization : keyEntry.metadata.authorizations) {
                        if (authorization.keyParameter.tag == 536871115) {
                            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, android.security.keystore.KeyProperties.Digest.toKeymaster("SHA-1")));
                            break;
                        }
                    }
                }
                try {
                    android.os.StrictMode.noteDiskWrite();
                    keyStoreSecurityLevel.importWrappedKey(makeKeyDescriptor2, makeKeyDescriptor, wrappedKeyEntry.getWrappedKeyBytes(), null, arrayList, (android.system.keystore2.AuthenticatorSpec[]) arrayList2.toArray(new android.system.keystore2.AuthenticatorSpec[0]));
                    return;
                } catch (android.security.KeyStoreException e) {
                    switch (e.getErrorCode()) {
                        case -100:
                            throw new android.security.keystore.SecureKeyImportUnavailableException("Could not import wrapped key");
                        default:
                            throw new java.security.KeyStoreException("Failed to import wrapped key. Keystore error code: " + e.getErrorCode(), e);
                    }
                }
            } catch (android.security.KeyStoreException e2) {
                throw new java.security.KeyStoreException("Failed to import wrapped key. Keystore error code: " + e2.getErrorCode(), e2);
            }
        }
        throw new java.security.KeyStoreException("Algorithm \"" + str2 + "\" not supported for wrapping. Only RSA wrapping keys are supported.");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(java.lang.String str, byte[] bArr, java.security.cert.Certificate[] certificateArr) throws java.security.KeyStoreException {
        throw new java.security.KeyStoreException("Operation not supported because key encoding is unknown");
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(java.lang.String str, java.security.cert.Certificate certificate) throws java.security.KeyStoreException {
        if (isKeyEntry(str)) {
            throw new java.security.KeyStoreException("Entry exists and is not a trusted certificate");
        }
        if (certificate == null) {
            throw new java.lang.NullPointerException("cert == null");
        }
        try {
            byte[] encoded = certificate.getEncoded();
            try {
                android.os.StrictMode.noteDiskWrite();
                this.mKeyStore.updateSubcomponents(makeKeyDescriptor(str), null, encoded);
            } catch (android.security.KeyStoreException e) {
                throw new java.security.KeyStoreException("Couldn't insert certificate.", e);
            }
        } catch (java.security.cert.CertificateEncodingException e2) {
            throw new java.security.KeyStoreException(e2);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(java.lang.String str) throws java.security.KeyStoreException {
        android.system.keystore2.KeyDescriptor makeKeyDescriptor = makeKeyDescriptor(str);
        try {
            android.os.StrictMode.noteDiskWrite();
            this.mKeyStore.deleteKey(makeKeyDescriptor);
        } catch (android.security.KeyStoreException e) {
            if (e.getErrorCode() != 7) {
                throw new java.security.KeyStoreException("Failed to delete entry: " + str, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.system.keystore2.KeyDescriptor[] getAliasesBatch(java.lang.String str) {
        try {
            android.os.StrictMode.noteDiskRead();
            return this.mKeyStore.listBatch(getTargetDomain(), this.mNamespace, str);
        } catch (android.security.KeyStoreException e) {
            android.util.Log.e(TAG, "Failed to list keystore entries.", e);
            return new android.system.keystore2.KeyDescriptor[0];
        }
    }

    @Override // java.security.KeyStoreSpi
    public java.util.Enumeration<java.lang.String> engineAliases() {
        return new android.security.keystore2.AndroidKeyStoreSpi.KeyEntriesEnumerator();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(java.lang.String str) {
        if (str != null) {
            return getKeyMetadata(str) != null;
        }
        throw new java.lang.NullPointerException("alias == null");
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        try {
            android.os.StrictMode.noteDiskRead();
            return this.mKeyStore.getNumberOfEntries(getTargetDomain(), this.mNamespace);
        } catch (android.security.KeyStoreException e) {
            android.util.Log.e(TAG, "Failed to get the number of keystore entries.", e);
            return 0;
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(java.lang.String str) {
        return isKeyEntry(str);
    }

    private boolean isKeyEntry(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(str);
        return (keyMetadata == null || keyMetadata.iSecurityLevel == null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("alias == null");
        }
        android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(str);
        return (keyMetadata == null || keyMetadata.metadata.certificateChain == null || keyMetadata.iSecurityLevel != null) ? false : true;
    }

    @Override // java.security.KeyStoreSpi
    public java.lang.String engineGetCertificateAlias(java.security.cert.Certificate certificate) {
        android.system.keystore2.KeyDescriptor[] keyDescriptorArr;
        java.lang.String str = null;
        if (certificate == null) {
            return null;
        }
        if (!"X.509".equalsIgnoreCase(certificate.getType())) {
            android.util.Log.e(TAG, "In engineGetCertificateAlias: only X.509 certificates are supported.");
            return null;
        }
        try {
            byte[] encoded = certificate.getEncoded();
            if (encoded == null) {
                return null;
            }
            try {
                android.os.StrictMode.noteDiskRead();
                keyDescriptorArr = this.mKeyStore.list(getTargetDomain(), this.mNamespace);
            } catch (android.security.KeyStoreException e) {
                android.util.Log.w(TAG, "Failed to get list of keystore entries.", e);
                keyDescriptorArr = null;
            }
            for (android.system.keystore2.KeyDescriptor keyDescriptor : keyDescriptorArr) {
                android.system.keystore2.KeyEntryResponse keyMetadata = getKeyMetadata(keyDescriptor.alias);
                if (keyMetadata != null) {
                    if (keyMetadata.metadata.certificate != null) {
                        if (java.util.Arrays.equals(keyMetadata.metadata.certificate, encoded)) {
                            return keyDescriptor.alias;
                        }
                    } else if (keyMetadata.metadata.certificateChain != null && str == null && java.util.Arrays.equals(keyMetadata.metadata.certificateChain, encoded)) {
                        str = keyDescriptor.alias;
                    }
                }
            }
            return str;
        } catch (java.security.cert.CertificateEncodingException e2) {
            android.util.Log.e(TAG, "While trying to get the alias for a certificate.", e2);
            return null;
        }
    }

    public void initForTesting(android.security.KeyStore2 keyStore2) {
        this.mKeyStore = keyStore2;
        this.mNamespace = -1;
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(java.io.OutputStream outputStream, char[] cArr) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.cert.CertificateException {
        throw new java.lang.UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream");
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(java.io.InputStream inputStream, char[] cArr) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.cert.CertificateException {
        if (inputStream != null) {
            throw new java.lang.IllegalArgumentException("InputStream not supported");
        }
        if (cArr != null) {
            throw new java.lang.IllegalArgumentException("password not supported");
        }
        this.mKeyStore = android.security.KeyStore2.getInstance();
        this.mNamespace = -1;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(java.security.KeyStore.LoadStoreParameter loadStoreParameter) throws java.io.IOException, java.security.NoSuchAlgorithmException, java.security.cert.CertificateException {
        int i;
        if (loadStoreParameter == null) {
            i = -1;
        } else if (loadStoreParameter instanceof android.security.keystore2.AndroidKeyStoreLoadStoreParameter) {
            i = ((android.security.keystore2.AndroidKeyStoreLoadStoreParameter) loadStoreParameter).getNamespace();
        } else {
            throw new java.lang.IllegalArgumentException("Unsupported param type: " + loadStoreParameter.getClass());
        }
        this.mKeyStore = android.security.KeyStore2.getInstance();
        this.mNamespace = i;
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetEntry(java.lang.String str, java.security.KeyStore.Entry entry, java.security.KeyStore.ProtectionParameter protectionParameter) throws java.security.KeyStoreException {
        if (entry == null) {
            throw new java.security.KeyStoreException("entry == null");
        }
        if (entry instanceof java.security.KeyStore.TrustedCertificateEntry) {
            engineDeleteEntry(str);
            engineSetCertificateEntry(str, ((java.security.KeyStore.TrustedCertificateEntry) entry).getTrustedCertificate());
        } else if (entry instanceof java.security.KeyStore.PrivateKeyEntry) {
            java.security.KeyStore.PrivateKeyEntry privateKeyEntry = (java.security.KeyStore.PrivateKeyEntry) entry;
            setPrivateKeyEntry(str, privateKeyEntry.getPrivateKey(), privateKeyEntry.getCertificateChain(), protectionParameter);
        } else if (entry instanceof java.security.KeyStore.SecretKeyEntry) {
            setSecretKeyEntry(str, ((java.security.KeyStore.SecretKeyEntry) entry).getSecretKey(), protectionParameter);
        } else {
            if (entry instanceof android.security.keystore.WrappedKeyEntry) {
                setWrappedKeyEntry(str, (android.security.keystore.WrappedKeyEntry) entry, protectionParameter);
                return;
            }
            throw new java.security.KeyStoreException("Entry must be a PrivateKeyEntry, SecretKeyEntry, WrappedKeyEntry or TrustedCertificateEntry; was " + entry);
        }
    }

    private class KeyEntriesEnumerator implements java.util.Enumeration<java.lang.String> {
        private android.system.keystore2.KeyDescriptor[] mCurrentBatch;
        private int mCurrentEntry;
        private java.lang.String mLastAlias;

        private KeyEntriesEnumerator() {
            this.mCurrentEntry = 0;
            this.mLastAlias = null;
            getAndValidateNextBatch();
        }

        private void getAndValidateNextBatch() {
            this.mCurrentBatch = android.security.keystore2.AndroidKeyStoreSpi.this.getAliasesBatch(this.mLastAlias);
            this.mCurrentEntry = 0;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.mCurrentBatch != null && this.mCurrentBatch.length > 0;
        }

        @Override // java.util.Enumeration
        public java.lang.String nextElement() {
            if (this.mCurrentBatch == null || this.mCurrentBatch.length == 0) {
                throw new java.util.NoSuchElementException("Error while fetching entries.");
            }
            this.mLastAlias = this.mCurrentBatch[this.mCurrentEntry].alias;
            this.mCurrentEntry++;
            if (this.mCurrentEntry >= this.mCurrentBatch.length) {
                getAndValidateNextBatch();
            }
            return this.mLastAlias;
        }
    }
}
