package android.security.identity;

/* loaded from: classes3.dex */
class CredstoreIdentityCredential extends android.security.identity.IdentityCredential {
    private static final java.lang.String TAG = "CredstoreIdentityCredential";
    private android.security.identity.ICredential mBinder;
    private int mCipherSuite;
    private android.content.Context mContext;
    private java.lang.String mCredentialName;
    private int mEphemeralCounter;
    private int mFeatureVersion;
    private int mReadersExpectedEphemeralCounter;
    private android.security.identity.CredstorePresentationSession mSession;
    private java.security.KeyPair mEphemeralKeyPair = null;
    private javax.crypto.SecretKey mSecretKey = null;
    private javax.crypto.SecretKey mReaderSecretKey = null;
    private boolean mAllowUsingExhaustedKeys = true;
    private boolean mAllowUsingExpiredKeys = false;
    private boolean mIncrementKeyUsageCount = true;
    private boolean mOperationHandleSet = false;
    private long mOperationHandle = 0;

    CredstoreIdentityCredential(android.content.Context context, java.lang.String str, int i, android.security.identity.ICredential iCredential, android.security.identity.CredstorePresentationSession credstorePresentationSession, int i2) {
        this.mContext = context;
        this.mCredentialName = str;
        this.mCipherSuite = i;
        this.mBinder = iCredential;
        this.mSession = credstorePresentationSession;
        this.mFeatureVersion = i2;
    }

    private void ensureEphemeralKeyPair() {
        if (this.mEphemeralKeyPair != null) {
            return;
        }
        try {
            byte[] createEphemeralKeyPair = this.mBinder.createEphemeralKeyPair();
            char[] cArr = new char[0];
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance(android.security.KeyChain.EXTRA_PKCS12);
            keyStore.load(new java.io.ByteArrayInputStream(createEphemeralKeyPair), cArr);
            this.mEphemeralKeyPair = new java.security.KeyPair(keyStore.getCertificate("ephemeralKey").getPublicKey(), (java.security.PrivateKey) keyStore.getKey("ephemeralKey", cArr));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (java.io.IOException | java.security.KeyStoreException | java.security.NoSuchAlgorithmException | java.security.UnrecoverableKeyException | java.security.cert.CertificateException e3) {
            throw new java.lang.RuntimeException("Unexpected exception ", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public java.security.KeyPair createEphemeralKeyPair() {
        ensureEphemeralKeyPair();
        return this.mEphemeralKeyPair;
    }

    @Override // android.security.identity.IdentityCredential
    public void setReaderEphemeralPublicKey(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        try {
            this.mBinder.setReaderEphemeralPublicKey(android.security.identity.Util.publicKeyEncodeUncompressedForm(publicKey));
            ensureEphemeralKeyPair();
            try {
                javax.crypto.KeyAgreement keyAgreement = javax.crypto.KeyAgreement.getInstance("ECDH");
                keyAgreement.init(this.mEphemeralKeyPair.getPrivate());
                keyAgreement.doPhase(publicKey, true);
                byte[] generateSecret = keyAgreement.generateSecret();
                byte[] bArr = new byte[0];
                byte[] bArr2 = {1};
                this.mSecretKey = new javax.crypto.spec.SecretKeySpec(android.security.identity.Util.computeHkdf("HmacSha256", generateSecret, bArr2, bArr, 32), android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
                bArr2[0] = 0;
                this.mReaderSecretKey = new javax.crypto.spec.SecretKeySpec(android.security.identity.Util.computeHkdf("HmacSha256", generateSecret, bArr2, bArr, 32), android.security.keystore.KeyProperties.KEY_ALGORITHM_AES);
                this.mEphemeralCounter = 1;
                this.mReadersExpectedEphemeralCounter = 1;
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.RuntimeException("Error performing key agreement", e);
            }
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e2);
        } catch (android.os.ServiceSpecificException e3) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] encryptMessageToReader(byte[] bArr) {
        try {
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(12);
            allocate.putInt(0, 0);
            allocate.putInt(4, 1);
            allocate.putInt(8, this.mEphemeralCounter);
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, this.mSecretKey, new javax.crypto.spec.GCMParameterSpec(128, allocate.array()));
            byte[] doFinal = cipher.doFinal(bArr);
            this.mEphemeralCounter++;
            return doFinal;
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            throw new java.lang.RuntimeException("Error encrypting message", e);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] decryptMessageFromReader(byte[] bArr) throws android.security.identity.MessageDecryptionException {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(12);
        allocate.putInt(0, 0);
        allocate.putInt(4, 0);
        allocate.putInt(8, this.mReadersExpectedEphemeralCounter);
        try {
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, this.mReaderSecretKey, new javax.crypto.spec.GCMParameterSpec(128, allocate.array()));
            byte[] doFinal = cipher.doFinal(bArr);
            this.mReadersExpectedEphemeralCounter++;
            return doFinal;
        } catch (java.security.InvalidAlgorithmParameterException | java.security.InvalidKeyException | java.security.NoSuchAlgorithmException | javax.crypto.BadPaddingException | javax.crypto.IllegalBlockSizeException | javax.crypto.NoSuchPaddingException e) {
            throw new android.security.identity.MessageDecryptionException("Error decrypting message", e);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public java.util.Collection<java.security.cert.X509Certificate> getCredentialKeyCertificateChain() {
        try {
            try {
                java.util.Collection<? extends java.security.cert.Certificate> generateCertificates = java.security.cert.CertificateFactory.getInstance("X.509").generateCertificates(new java.io.ByteArrayInputStream(this.mBinder.getCredentialKeyCertificateChain()));
                java.util.ArrayList arrayList = new java.util.ArrayList();
                java.util.Iterator<? extends java.security.cert.Certificate> it = generateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add((java.security.cert.X509Certificate) it.next());
                }
                return arrayList;
            } catch (java.security.cert.CertificateException e) {
                throw new java.lang.RuntimeException("Error decoding certificates", e);
            }
        } catch (android.os.RemoteException e2) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e2);
        } catch (android.os.ServiceSpecificException e3) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e3.errorCode, e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void setAllowUsingExhaustedKeys(boolean z) {
        this.mAllowUsingExhaustedKeys = z;
    }

    @Override // android.security.identity.IdentityCredential
    public void setAllowUsingExpiredKeys(boolean z) {
        this.mAllowUsingExpiredKeys = z;
    }

    @Override // android.security.identity.IdentityCredential
    public void setIncrementKeyUsageCount(boolean z) {
        this.mIncrementKeyUsageCount = z;
    }

    @Override // android.security.identity.IdentityCredential
    public long getCredstoreOperationHandle() {
        if (!this.mOperationHandleSet) {
            try {
                this.mOperationHandle = this.mBinder.selectAuthKey(this.mAllowUsingExhaustedKeys, this.mAllowUsingExpiredKeys, this.mIncrementKeyUsageCount);
                this.mOperationHandleSet = true;
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
            } catch (android.os.ServiceSpecificException e2) {
                int i = e2.errorCode;
                throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
            }
        }
        return this.mOperationHandle;
    }

    @Override // android.security.identity.IdentityCredential
    public android.security.identity.ResultData getEntries(byte[] bArr, java.util.Map<java.lang.String, java.util.Collection<java.lang.String>> map, byte[] bArr2, byte[] bArr3) throws android.security.identity.SessionTranscriptMismatchException, android.security.identity.NoAuthenticationKeyAvailableException, android.security.identity.InvalidReaderSignatureException, android.security.identity.EphemeralPublicKeyNotFoundException, android.security.identity.InvalidRequestMessageException {
        byte[] bArr4;
        byte[] bArr5;
        android.security.identity.RequestNamespaceParcel[] requestNamespaceParcelArr = new android.security.identity.RequestNamespaceParcel[map.size()];
        int i = 0;
        for (java.lang.String str : map.keySet()) {
            java.util.Collection<java.lang.String> collection = map.get(str);
            requestNamespaceParcelArr[i] = new android.security.identity.RequestNamespaceParcel();
            requestNamespaceParcelArr[i].namespaceName = str;
            requestNamespaceParcelArr[i].entries = new android.security.identity.RequestEntryParcel[collection.size()];
            int i2 = 0;
            for (java.lang.String str2 : collection) {
                requestNamespaceParcelArr[i].entries[i2] = new android.security.identity.RequestEntryParcel();
                requestNamespaceParcelArr[i].entries[i2].name = str2;
                i2++;
            }
            i++;
        }
        try {
            android.security.identity.ICredential iCredential = this.mBinder;
            if (bArr == null) {
                bArr = new byte[0];
            }
            byte[] bArr6 = bArr;
            if (bArr2 == null) {
                bArr2 = new byte[0];
            }
            byte[] bArr7 = bArr2;
            if (bArr3 == null) {
                bArr3 = new byte[0];
            }
            android.security.identity.GetEntriesResultParcel entries = iCredential.getEntries(bArr6, requestNamespaceParcelArr, bArr7, bArr3, this.mAllowUsingExhaustedKeys, this.mAllowUsingExpiredKeys, this.mIncrementKeyUsageCount);
            byte[] bArr8 = entries.signature;
            if (bArr8 != null && bArr8.length == 0) {
                bArr4 = null;
            } else {
                bArr4 = bArr8;
            }
            byte[] bArr9 = entries.mac;
            if (bArr9 != null && bArr9.length == 0) {
                bArr5 = null;
            } else {
                bArr5 = bArr9;
            }
            android.security.identity.CredstoreResultData.Builder builder = new android.security.identity.CredstoreResultData.Builder(this.mFeatureVersion, entries.staticAuthenticationData, entries.deviceNameSpaces, bArr5, bArr4);
            for (android.security.identity.ResultNamespaceParcel resultNamespaceParcel : entries.resultNamespaces) {
                for (android.security.identity.ResultEntryParcel resultEntryParcel : resultNamespaceParcel.entries) {
                    if (resultEntryParcel.status == 0) {
                        builder.addEntry(resultNamespaceParcel.namespaceName, resultEntryParcel.name, resultEntryParcel.value);
                    } else {
                        builder.addErrorStatus(resultNamespaceParcel.namespaceName, resultEntryParcel.name, resultEntryParcel.status);
                    }
                }
            }
            return builder.build();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 5) {
                throw new android.security.identity.EphemeralPublicKeyNotFoundException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 7) {
                throw new android.security.identity.InvalidReaderSignatureException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 6) {
                throw new android.security.identity.NoAuthenticationKeyAvailableException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 10) {
                throw new android.security.identity.InvalidRequestMessageException(e2.getMessage(), e2);
            }
            if (e2.errorCode == 11) {
                throw new android.security.identity.SessionTranscriptMismatchException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void setAvailableAuthenticationKeys(int i, int i2) {
        setAvailableAuthenticationKeys(i, i2, 0L);
    }

    @Override // android.security.identity.IdentityCredential
    public void setAvailableAuthenticationKeys(int i, int i2, long j) {
        try {
            this.mBinder.setAvailableAuthenticationKeys(i, i2, j);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public java.util.Collection<java.security.cert.X509Certificate> getAuthKeysNeedingCertification() {
        try {
            android.security.identity.AuthKeyParcel[] authKeysNeedingCertification = this.mBinder.getAuthKeysNeedingCertification();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.security.cert.CertificateFactory certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509");
            for (android.security.identity.AuthKeyParcel authKeyParcel : authKeysNeedingCertification) {
                java.util.Collection<? extends java.security.cert.Certificate> generateCertificates = certificateFactory.generateCertificates(new java.io.ByteArrayInputStream(authKeyParcel.x509cert));
                if (generateCertificates.size() != 1) {
                    throw new java.lang.RuntimeException("Returned blob yields more than one X509 cert");
                }
                arrayList.add((java.security.cert.X509Certificate) generateCertificates.iterator().next());
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (java.security.cert.CertificateException e3) {
            throw new java.lang.RuntimeException("Error decoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void storeStaticAuthenticationData(java.security.cert.X509Certificate x509Certificate, byte[] bArr) throws android.security.identity.UnknownAuthenticationKeyException {
        try {
            android.security.identity.AuthKeyParcel authKeyParcel = new android.security.identity.AuthKeyParcel();
            authKeyParcel.x509cert = x509Certificate.getEncoded();
            this.mBinder.storeStaticAuthenticationData(authKeyParcel, bArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 9) {
                throw new android.security.identity.UnknownAuthenticationKeyException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (java.security.cert.CertificateEncodingException e3) {
            throw new java.lang.RuntimeException("Error encoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public void storeStaticAuthenticationData(java.security.cert.X509Certificate x509Certificate, java.time.Instant instant, byte[] bArr) throws android.security.identity.UnknownAuthenticationKeyException {
        try {
            android.security.identity.AuthKeyParcel authKeyParcel = new android.security.identity.AuthKeyParcel();
            authKeyParcel.x509cert = x509Certificate.getEncoded();
            this.mBinder.storeStaticAuthenticationDataWithExpiration(authKeyParcel, (instant.getEpochSecond() * 1000) + (instant.getNano() / 1000000), bArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 12) {
                throw new java.lang.UnsupportedOperationException("Not supported", e2);
            }
            if (e2.errorCode == 9) {
                throw new android.security.identity.UnknownAuthenticationKeyException(e2.getMessage(), e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        } catch (java.security.cert.CertificateEncodingException e3) {
            throw new java.lang.RuntimeException("Error encoding authenticationKey", e3);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public int[] getAuthenticationDataUsageCount() {
        try {
            return this.mBinder.getAuthenticationDataUsageCount();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public java.util.List<android.security.identity.AuthenticationKeyMetadata> getAuthenticationKeyMetadata() {
        android.security.identity.AuthenticationKeyMetadata authenticationKeyMetadata;
        try {
            int[] authenticationDataUsageCount = this.mBinder.getAuthenticationDataUsageCount();
            long[] authenticationDataExpirations = this.mBinder.getAuthenticationDataExpirations();
            if (authenticationDataUsageCount.length != authenticationDataExpirations.length) {
                throw new java.lang.IllegalStateException("Size og usageCount and expirationMillis differ");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < authenticationDataExpirations.length; i++) {
                long j = authenticationDataExpirations[i];
                if (j == Long.MAX_VALUE) {
                    authenticationKeyMetadata = null;
                } else {
                    authenticationKeyMetadata = new android.security.identity.AuthenticationKeyMetadata(authenticationDataUsageCount[i], java.time.Instant.ofEpochMilli(j));
                }
                arrayList.add(authenticationKeyMetadata);
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.IllegalStateException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] proveOwnership(byte[] bArr) {
        try {
            return this.mBinder.proveOwnership(bArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            if (e2.errorCode == 12) {
                throw new java.lang.UnsupportedOperationException("Not supported", e2);
            }
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] delete(byte[] bArr) {
        try {
            return this.mBinder.deleteWithChallenge(bArr);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }

    @Override // android.security.identity.IdentityCredential
    public byte[] update(android.security.identity.PersonalizationData personalizationData) {
        try {
            return android.security.identity.CredstoreWritableIdentityCredential.personalize(this.mBinder.update(), personalizationData);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Unexpected RemoteException ", e);
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.lang.RuntimeException("Unexpected ServiceSpecificException with code " + e2.errorCode, e2);
        }
    }
}
