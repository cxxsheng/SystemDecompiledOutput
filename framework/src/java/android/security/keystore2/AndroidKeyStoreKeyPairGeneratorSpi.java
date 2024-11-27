package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreKeyPairGeneratorSpi extends java.security.KeyPairGeneratorSpi {
    private static final int EC_DEFAULT_KEY_SIZE = 256;
    private static final int RSA_DEFAULT_KEY_SIZE = 2048;
    private static final int RSA_MAX_KEY_SIZE = 8192;
    private static final int RSA_MIN_KEY_SIZE = 512;
    private static final java.lang.String TAG = "AndroidKeyStoreKeyPairGeneratorSpi";
    private android.system.keystore2.KeyDescriptor mAttestKeyDescriptor;
    private java.lang.String mEcCurveName;
    private java.lang.String mEntryAlias;
    private int mEntryNamespace;
    private java.lang.String mJcaKeyAlgorithm;
    private int mKeySizeBits;
    private android.security.KeyStore2 mKeyStore;
    private int mKeymasterAlgorithm = -1;
    private int[] mKeymasterBlockModes;
    private int[] mKeymasterDigests;
    private int[] mKeymasterEncryptionPaddings;
    private int[] mKeymasterMgf1Digests;
    private int[] mKeymasterPurposes;
    private int[] mKeymasterSignaturePaddings;
    private final int mOriginalKeymasterAlgorithm;
    private java.lang.Long mRSAPublicExponent;
    private java.security.SecureRandom mRng;
    private android.security.keystore.KeyGenParameterSpec mSpec;
    private static final java.util.Map<java.lang.String, java.lang.Integer> SUPPORTED_EC_CURVE_NAME_TO_SIZE = new java.util.HashMap();
    private static final java.util.List<java.lang.String> SUPPORTED_EC_CURVE_NAMES = new java.util.ArrayList();
    private static final java.util.List<java.lang.Integer> SUPPORTED_EC_CURVE_SIZES = new java.util.ArrayList();
    private static final java.lang.String CURVE_X_25519 = java.security.spec.NamedParameterSpec.X25519.getName();
    private static final java.lang.String CURVE_ED_25519 = java.security.spec.NamedParameterSpec.ED25519.getName();

    public static class RSA extends android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi {
        public RSA() {
            super(1);
        }
    }

    public static class EC extends android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi {
        public EC() {
            super(3);
        }
    }

    public static class XDH extends android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi {
        public XDH() {
            super(3);
        }
    }

    static {
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-224", 224);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp224r1", 224);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-256", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp256r1", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("prime256v1", 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put(CURVE_X_25519.toLowerCase(java.util.Locale.US), 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put(CURVE_ED_25519.toLowerCase(java.util.Locale.US), 256);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-384", 384);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp384r1", 384);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("p-521", 521);
        SUPPORTED_EC_CURVE_NAME_TO_SIZE.put("secp521r1", 521);
        SUPPORTED_EC_CURVE_NAMES.addAll(SUPPORTED_EC_CURVE_NAME_TO_SIZE.keySet());
        java.util.Collections.sort(SUPPORTED_EC_CURVE_NAMES);
        SUPPORTED_EC_CURVE_SIZES.addAll(new java.util.HashSet(SUPPORTED_EC_CURVE_NAME_TO_SIZE.values()));
        java.util.Collections.sort(SUPPORTED_EC_CURVE_SIZES);
    }

    protected AndroidKeyStoreKeyPairGeneratorSpi(int i) {
        this.mOriginalKeymasterAlgorithm = i;
    }

    private static int keySizeAndNameToEcCurve(int i, java.lang.String str) throws java.security.InvalidAlgorithmParameterException {
        switch (i) {
            case 224:
                return 0;
            case 256:
                if (isCurve25519(str)) {
                    return 4;
                }
                return 1;
            case 384:
                return 2;
            case 521:
                return 3;
            default:
                throw new java.security.InvalidAlgorithmParameterException("Unsupported EC curve keysize: " + i);
        }
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(int i, java.security.SecureRandom secureRandom) {
        throw new java.lang.IllegalArgumentException(android.security.keystore.KeyGenParameterSpec.class.getName() + " or " + android.security.KeyPairGeneratorSpec.class.getName() + " required to initialize this KeyPairGenerator");
    }

    @Override // java.security.KeyPairGeneratorSpi
    public void initialize(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        android.security.keystore.KeyGenParameterSpec buildKeyGenParameterSpecFromLegacy;
        resetAll();
        try {
            if (algorithmParameterSpec == null) {
                throw new java.security.InvalidAlgorithmParameterException("Must supply params of type " + android.security.keystore.KeyGenParameterSpec.class.getName() + " or " + android.security.KeyPairGeneratorSpec.class.getName());
            }
            int i = this.mOriginalKeymasterAlgorithm;
            if (algorithmParameterSpec instanceof android.security.keystore.KeyGenParameterSpec) {
                buildKeyGenParameterSpecFromLegacy = (android.security.keystore.KeyGenParameterSpec) algorithmParameterSpec;
            } else if (algorithmParameterSpec instanceof android.security.KeyPairGeneratorSpec) {
                android.security.KeyPairGeneratorSpec keyPairGeneratorSpec = (android.security.KeyPairGeneratorSpec) algorithmParameterSpec;
                try {
                    i = getKeymasterAlgorithmFromLegacy(i, keyPairGeneratorSpec);
                    buildKeyGenParameterSpecFromLegacy = buildKeyGenParameterSpecFromLegacy(keyPairGeneratorSpec, i);
                } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                    throw new java.security.InvalidAlgorithmParameterException(e);
                }
            } else {
                if (algorithmParameterSpec instanceof java.security.spec.NamedParameterSpec) {
                    java.security.spec.NamedParameterSpec namedParameterSpec = (java.security.spec.NamedParameterSpec) algorithmParameterSpec;
                    if (!namedParameterSpec.getName().equalsIgnoreCase(java.security.spec.NamedParameterSpec.X25519.getName()) && !namedParameterSpec.getName().equalsIgnoreCase(java.security.spec.NamedParameterSpec.ED25519.getName())) {
                        throw new java.security.InvalidAlgorithmParameterException("Unsupported algorithm specified via NamedParameterSpec: " + namedParameterSpec.getName());
                    }
                    throw new java.lang.IllegalArgumentException("This KeyPairGenerator cannot be initialized using NamedParameterSpec. use " + android.security.keystore.KeyGenParameterSpec.class.getName() + " or " + android.security.KeyPairGeneratorSpec.class.getName());
                }
                throw new java.security.InvalidAlgorithmParameterException("Unsupported params class: " + algorithmParameterSpec.getClass().getName() + ". Supported: " + android.security.keystore.KeyGenParameterSpec.class.getName() + ", " + android.security.KeyPairGeneratorSpec.class.getName());
            }
            this.mEntryAlias = buildKeyGenParameterSpecFromLegacy.getKeystoreAlias();
            this.mEntryNamespace = buildKeyGenParameterSpecFromLegacy.getNamespace();
            this.mSpec = buildKeyGenParameterSpecFromLegacy;
            this.mKeymasterAlgorithm = i;
            this.mKeySizeBits = buildKeyGenParameterSpecFromLegacy.getKeySize();
            initAlgorithmSpecificParameters();
            if (this.mKeySizeBits == -1) {
                this.mKeySizeBits = getDefaultKeySize(i);
            }
            checkValidKeySize(i, this.mKeySizeBits, this.mSpec.isStrongBoxBacked(), this.mEcCurveName);
            if (buildKeyGenParameterSpecFromLegacy.getKeystoreAlias() == null) {
                throw new java.security.InvalidAlgorithmParameterException("KeyStore entry alias not provided");
            }
            try {
                java.lang.String fromKeymasterAsymmetricKeyAlgorithm = android.security.keystore.KeyProperties.KeyAlgorithm.fromKeymasterAsymmetricKeyAlgorithm(i);
                this.mKeymasterPurposes = android.security.keystore.KeyProperties.Purpose.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getPurposes());
                this.mKeymasterBlockModes = android.security.keystore.KeyProperties.BlockMode.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getBlockModes());
                this.mKeymasterEncryptionPaddings = android.security.keystore.KeyProperties.EncryptionPadding.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getEncryptionPaddings());
                int i2 = 0;
                if ((buildKeyGenParameterSpecFromLegacy.getPurposes() & 1) != 0 && buildKeyGenParameterSpecFromLegacy.isRandomizedEncryptionRequired()) {
                    for (int i3 : this.mKeymasterEncryptionPaddings) {
                        if (!android.security.keystore2.KeymasterUtils.isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(i3)) {
                            throw new java.security.InvalidAlgorithmParameterException("Randomized encryption (IND-CPA) required but may be violated by padding scheme: " + android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(i3) + ". See " + android.security.keystore.KeyGenParameterSpec.class.getName() + " documentation.");
                        }
                    }
                }
                this.mKeymasterSignaturePaddings = android.security.keystore.KeyProperties.SignaturePadding.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getSignaturePaddings());
                if (buildKeyGenParameterSpecFromLegacy.isDigestsSpecified()) {
                    this.mKeymasterDigests = android.security.keystore.KeyProperties.Digest.allToKeymaster(buildKeyGenParameterSpecFromLegacy.getDigests());
                } else {
                    this.mKeymasterDigests = libcore.util.EmptyArray.INT;
                }
                if (buildKeyGenParameterSpecFromLegacy.isMgf1DigestsSpecified()) {
                    java.util.Set<java.lang.String> mgf1Digests = buildKeyGenParameterSpecFromLegacy.getMgf1Digests();
                    this.mKeymasterMgf1Digests = new int[mgf1Digests.size()];
                    java.util.Iterator<java.lang.String> it = mgf1Digests.iterator();
                    while (it.hasNext()) {
                        this.mKeymasterMgf1Digests[i2] = android.security.keystore.KeyProperties.Digest.toKeymaster(it.next());
                        i2++;
                    }
                } else {
                    this.mKeymasterMgf1Digests = new int[]{android.security.keystore.KeyProperties.Digest.toKeymaster("SHA-1")};
                }
                android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(new java.util.ArrayList(), this.mSpec);
                this.mJcaKeyAlgorithm = fromKeymasterAsymmetricKeyAlgorithm;
                this.mRng = secureRandom;
                this.mKeyStore = android.security.KeyStore2.getInstance();
                this.mAttestKeyDescriptor = buildAndCheckAttestKeyDescriptor(buildKeyGenParameterSpecFromLegacy);
                checkAttestKeyPurpose(buildKeyGenParameterSpecFromLegacy);
                checkCorrectKeyPurposeForCurve(buildKeyGenParameterSpecFromLegacy);
            } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e2) {
                throw new java.security.InvalidAlgorithmParameterException(e2);
            }
        } catch (java.lang.Throwable th) {
            resetAll();
            throw th;
        }
    }

    private void checkAttestKeyPurpose(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        if ((keyGenParameterSpec.getPurposes() & 128) != 0 && keyGenParameterSpec.getPurposes() != 128) {
            throw new java.security.InvalidAlgorithmParameterException("PURPOSE_ATTEST_KEY may not be specified with any other purposes");
        }
    }

    private void checkCorrectKeyPurposeForCurve(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        if (!isCurve25519(this.mEcCurveName)) {
            return;
        }
        if (this.mEcCurveName.equalsIgnoreCase(CURVE_X_25519) && keyGenParameterSpec.getPurposes() != 64) {
            throw new java.security.InvalidAlgorithmParameterException("x25519 may only be used for key agreement.");
        }
        if (this.mEcCurveName.equalsIgnoreCase(CURVE_ED_25519) && !hasOnlyAllowedPurposeForEd25519(keyGenParameterSpec.getPurposes())) {
            throw new java.security.InvalidAlgorithmParameterException("ed25519 may not be used for key agreement.");
        }
    }

    private static boolean isCurve25519(java.lang.String str) {
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase(CURVE_X_25519) || str.equalsIgnoreCase(CURVE_ED_25519);
    }

    private static boolean hasOnlyAllowedPurposeForEd25519(int i) {
        return ((i & 140) != 0) && !((i & (-141)) != 0);
    }

    private android.system.keystore2.KeyDescriptor buildAndCheckAttestKeyDescriptor(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        if (keyGenParameterSpec.getAttestKeyAlias() != null) {
            android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
            keyDescriptor.domain = 0;
            keyDescriptor.alias = keyGenParameterSpec.getAttestKeyAlias();
            try {
                android.system.keystore2.KeyEntryResponse keyEntry = this.mKeyStore.getKeyEntry(keyDescriptor);
                checkAttestKeyChallenge(keyGenParameterSpec);
                checkAttestKeyPurpose(keyEntry.metadata.authorizations);
                checkAttestKeySecurityLevel(keyGenParameterSpec, keyEntry);
                return keyDescriptor;
            } catch (android.security.KeyStoreException e) {
                throw new java.security.InvalidAlgorithmParameterException("Invalid attestKeyAlias", e);
            }
        }
        return null;
    }

    private void checkAttestKeyChallenge(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) throws java.security.InvalidAlgorithmParameterException {
        if (keyGenParameterSpec.getAttestationChallenge() == null) {
            throw new java.security.InvalidAlgorithmParameterException("AttestKey specified but no attestation challenge provided");
        }
    }

    private void checkAttestKeyPurpose(android.system.keystore2.Authorization[] authorizationArr) throws java.security.InvalidAlgorithmParameterException {
        if (java.util.Arrays.stream(authorizationArr).noneMatch(new java.util.function.Predicate() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi.lambda$checkAttestKeyPurpose$0((android.system.keystore2.Authorization) obj);
            }
        })) {
            throw new java.security.InvalidAlgorithmParameterException("Invalid attestKey, does not have PURPOSE_ATTEST_KEY");
        }
    }

    static /* synthetic */ boolean lambda$checkAttestKeyPurpose$0(android.system.keystore2.Authorization authorization) {
        return authorization.keyParameter.tag == 536870913 && authorization.keyParameter.value.getKeyPurpose() == 7;
    }

    private void checkAttestKeySecurityLevel(android.security.keystore.KeyGenParameterSpec keyGenParameterSpec, android.system.keystore2.KeyEntryResponse keyEntryResponse) throws java.security.InvalidAlgorithmParameterException {
        boolean z = keyEntryResponse.metadata.keySecurityLevel == 2;
        if (keyGenParameterSpec.isStrongBoxBacked() != z) {
            if (z) {
                throw new java.security.InvalidAlgorithmParameterException("Invalid security level: Cannot sign non-StrongBox key with StrongBox attestKey");
            }
            throw new java.security.InvalidAlgorithmParameterException("Invalid security level: Cannot sign StrongBox key with non-StrongBox attestKey");
        }
    }

    private int getKeymasterAlgorithmFromLegacy(int i, android.security.KeyPairGeneratorSpec keyPairGeneratorSpec) throws java.security.InvalidAlgorithmParameterException {
        java.lang.String keyType = keyPairGeneratorSpec.getKeyType();
        if (keyType != null) {
            try {
                return android.security.keystore.KeyProperties.KeyAlgorithm.toKeymasterAsymmetricKeyAlgorithm(keyType);
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.security.InvalidAlgorithmParameterException("Invalid key type in parameters", e);
            }
        }
        return i;
    }

    private android.security.keystore.KeyGenParameterSpec buildKeyGenParameterSpecFromLegacy(android.security.KeyPairGeneratorSpec keyPairGeneratorSpec, int i) {
        android.security.keystore.KeyGenParameterSpec.Builder builder;
        switch (i) {
            case 1:
                builder = new android.security.keystore.KeyGenParameterSpec.Builder(keyPairGeneratorSpec.getKeystoreAlias(), 15);
                builder.setDigests(android.security.keystore.KeyProperties.DIGEST_NONE, android.security.keystore.KeyProperties.DIGEST_MD5, "SHA-1", android.security.keystore.KeyProperties.DIGEST_SHA224, "SHA-256", android.security.keystore.KeyProperties.DIGEST_SHA384, android.security.keystore.KeyProperties.DIGEST_SHA512);
                builder.setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE, android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1, android.security.keystore.KeyProperties.ENCRYPTION_PADDING_RSA_OAEP);
                builder.setSignaturePaddings(android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PKCS1, android.security.keystore.KeyProperties.SIGNATURE_PADDING_RSA_PSS);
                builder.setRandomizedEncryptionRequired(false);
                break;
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                builder = new android.security.keystore.KeyGenParameterSpec.Builder(keyPairGeneratorSpec.getKeystoreAlias(), 12);
                builder.setDigests(android.security.keystore.KeyProperties.DIGEST_NONE, "SHA-1", android.security.keystore.KeyProperties.DIGEST_SHA224, "SHA-256", android.security.keystore.KeyProperties.DIGEST_SHA384, android.security.keystore.KeyProperties.DIGEST_SHA512);
                break;
        }
        if (keyPairGeneratorSpec.getKeySize() != -1) {
            builder.setKeySize(keyPairGeneratorSpec.getKeySize());
        }
        if (keyPairGeneratorSpec.getAlgorithmParameterSpec() != null) {
            builder.setAlgorithmParameterSpec(keyPairGeneratorSpec.getAlgorithmParameterSpec());
        }
        builder.setCertificateSubject(keyPairGeneratorSpec.getSubjectDN());
        builder.setCertificateSerialNumber(keyPairGeneratorSpec.getSerialNumber());
        builder.setCertificateNotBefore(keyPairGeneratorSpec.getStartDate());
        builder.setCertificateNotAfter(keyPairGeneratorSpec.getEndDate());
        builder.setUserAuthenticationRequired(false);
        return builder.build();
    }

    private void resetAll() {
        this.mEntryAlias = null;
        this.mEntryNamespace = -1;
        this.mJcaKeyAlgorithm = null;
        this.mKeymasterAlgorithm = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterBlockModes = null;
        this.mKeymasterEncryptionPaddings = null;
        this.mKeymasterSignaturePaddings = null;
        this.mKeymasterDigests = null;
        this.mKeymasterMgf1Digests = null;
        this.mKeySizeBits = 0;
        this.mSpec = null;
        this.mRSAPublicExponent = null;
        this.mRng = null;
        this.mKeyStore = null;
        this.mEcCurveName = null;
    }

    private void initAlgorithmSpecificParameters() throws java.security.InvalidAlgorithmParameterException {
        java.math.BigInteger bigInteger;
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec = this.mSpec.getAlgorithmParameterSpec();
        switch (this.mKeymasterAlgorithm) {
            case 1:
                if (algorithmParameterSpec instanceof java.security.spec.RSAKeyGenParameterSpec) {
                    java.security.spec.RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (java.security.spec.RSAKeyGenParameterSpec) algorithmParameterSpec;
                    if (this.mKeySizeBits == -1) {
                        this.mKeySizeBits = rSAKeyGenParameterSpec.getKeysize();
                    } else if (this.mKeySizeBits != rSAKeyGenParameterSpec.getKeysize()) {
                        throw new java.security.InvalidAlgorithmParameterException("RSA key size must match  between " + this.mSpec + " and " + algorithmParameterSpec + ": " + this.mKeySizeBits + " vs " + rSAKeyGenParameterSpec.getKeysize());
                    }
                    bigInteger = rSAKeyGenParameterSpec.getPublicExponent();
                } else {
                    if (algorithmParameterSpec != null) {
                        throw new java.security.InvalidAlgorithmParameterException("RSA may only use RSAKeyGenParameterSpec");
                    }
                    bigInteger = null;
                }
                if (bigInteger == null) {
                    bigInteger = java.security.spec.RSAKeyGenParameterSpec.F4;
                }
                if (bigInteger.compareTo(java.math.BigInteger.ZERO) < 1) {
                    throw new java.security.InvalidAlgorithmParameterException("RSA public exponent must be positive: " + bigInteger);
                }
                if (bigInteger.signum() == -1 || bigInteger.compareTo(android.security.keymaster.KeymasterArguments.UINT64_MAX_VALUE) > 0) {
                    throw new java.security.InvalidAlgorithmParameterException("Unsupported RSA public exponent: " + bigInteger + ". Maximum supported value: " + android.security.keymaster.KeymasterArguments.UINT64_MAX_VALUE);
                }
                this.mRSAPublicExponent = java.lang.Long.valueOf(bigInteger.longValue());
                return;
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                if (algorithmParameterSpec instanceof java.security.spec.ECGenParameterSpec) {
                    this.mEcCurveName = ((java.security.spec.ECGenParameterSpec) algorithmParameterSpec).getName();
                    java.lang.Integer num = SUPPORTED_EC_CURVE_NAME_TO_SIZE.get(this.mEcCurveName.toLowerCase(java.util.Locale.US));
                    if (num == null) {
                        throw new java.security.InvalidAlgorithmParameterException("Unsupported EC curve name: " + this.mEcCurveName + ". Supported: " + SUPPORTED_EC_CURVE_NAMES);
                    }
                    if (this.mKeySizeBits == -1) {
                        this.mKeySizeBits = num.intValue();
                        return;
                    } else {
                        if (this.mKeySizeBits != num.intValue()) {
                            throw new java.security.InvalidAlgorithmParameterException("EC key size must match  between " + this.mSpec + " and " + algorithmParameterSpec + ": " + this.mKeySizeBits + " vs " + num);
                        }
                        return;
                    }
                }
                if (algorithmParameterSpec != null) {
                    throw new java.security.InvalidAlgorithmParameterException("EC may only use ECGenParameterSpec");
                }
                return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.security.KeyPairGeneratorSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.security.KeyPair generateKeyPair() {
        int i;
        int i2;
        android.security.keystore2.AndroidKeyStorePublicKey makeAndroidKeyStorePublicKeyFromKeyEntryResponse;
        android.os.StrictMode.noteSlowCall("generateKeyPair");
        if (this.mKeyStore == null || this.mSpec == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        boolean z = true;
        if (this.mSpec.isStrongBoxBacked()) {
            i = 2;
        } else {
            i = 1;
        }
        if (this.mSpec.isCriticalToDeviceEncryption()) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        byte[] randomBytesToMixIntoKeystoreRng = android.security.keystore2.KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.alias = this.mEntryAlias;
        keyDescriptor.domain = this.mEntryNamespace == -1 ? 0 : 2;
        keyDescriptor.nspace = this.mEntryNamespace;
        keyDescriptor.blob = null;
        try {
            try {
                android.security.KeyStoreSecurityLevel securityLevel = this.mKeyStore.getSecurityLevel(i);
                makeAndroidKeyStorePublicKeyFromKeyEntryResponse = android.security.keystore2.AndroidKeyStoreProvider.makeAndroidKeyStorePublicKeyFromKeyEntryResponse(keyDescriptor, securityLevel.generateKey(keyDescriptor, this.mAttestKeyDescriptor, constructKeyGenerationArguments(), i2, randomBytesToMixIntoKeystoreRng), securityLevel, this.mKeymasterAlgorithm);
            } catch (android.security.KeyStoreException e) {
                e = e;
            } catch (android.security.keystore.DeviceIdAttestationException | java.lang.IllegalArgumentException | java.security.InvalidAlgorithmParameterException | java.security.UnrecoverableKeyException e2) {
                e = e2;
            } catch (java.lang.Throwable th) {
                th = th;
                z = false;
                if (!z) {
                }
                throw th;
            }
            try {
                return new java.security.KeyPair(makeAndroidKeyStorePublicKeyFromKeyEntryResponse, makeAndroidKeyStorePublicKeyFromKeyEntryResponse.getPrivateKey());
            } catch (android.security.KeyStoreException e3) {
                e = e3;
                switch (e.getErrorCode()) {
                    case -68:
                        throw new android.security.keystore.StrongBoxUnavailableException("Failed to generated key pair.", e);
                    default:
                        java.security.ProviderException providerException = new java.security.ProviderException("Failed to generate key pair.", e);
                        if ((this.mSpec.getPurposes() & 32) != 0) {
                            throw new android.security.keystore.SecureKeyImportUnavailableException(providerException);
                        }
                        throw providerException;
                }
            } catch (android.security.keystore.DeviceIdAttestationException | java.lang.IllegalArgumentException | java.security.InvalidAlgorithmParameterException | java.security.UnrecoverableKeyException e4) {
                e = e4;
                throw new java.security.ProviderException("Failed to construct key object from newly generated key pair.", e);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (!z) {
                try {
                    this.mKeyStore.deleteKey(keyDescriptor);
                } catch (android.security.KeyStoreException e5) {
                    if (e5.getErrorCode() != 7) {
                        android.util.Log.e(TAG, "Failed to delete newly generated key after generation failed unexpectedly.", e5);
                    }
                }
            }
            throw th;
        }
    }

    private void addAttestationParameters(java.util.List<android.hardware.security.keymint.KeyParameter> list) throws java.security.ProviderException, java.lang.IllegalArgumentException, android.security.keystore.DeviceIdAttestationException {
        android.telephony.TelephonyManager telephonyManager;
        byte[] attestationChallenge = this.mSpec.getAttestationChallenge();
        if (attestationChallenge != null) {
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047484, attestationChallenge));
            if (this.mSpec.isDevicePropertiesAttestationIncluded()) {
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047482, (isPropertyEmptyOrUnknown(android.os.Build.BRAND_FOR_ATTESTATION) ? android.os.Build.BRAND : android.os.Build.BRAND_FOR_ATTESTATION).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047481, (isPropertyEmptyOrUnknown(android.os.Build.DEVICE_FOR_ATTESTATION) ? android.os.Build.DEVICE : android.os.Build.DEVICE_FOR_ATTESTATION).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047480, (isPropertyEmptyOrUnknown(android.os.Build.PRODUCT_FOR_ATTESTATION) ? android.os.Build.PRODUCT : android.os.Build.PRODUCT_FOR_ATTESTATION).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047476, (isPropertyEmptyOrUnknown(android.os.Build.MANUFACTURER_FOR_ATTESTATION) ? android.os.Build.MANUFACTURER : android.os.Build.MANUFACTURER_FOR_ATTESTATION).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047475, (isPropertyEmptyOrUnknown(android.os.Build.MODEL_FOR_ATTESTATION) ? android.os.Build.MODEL : android.os.Build.MODEL_FOR_ATTESTATION).getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            }
            int[] attestationIds = this.mSpec.getAttestationIds();
            if (attestationIds.length == 0) {
                return;
            }
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet(attestationIds.length);
            for (int i : attestationIds) {
                arraySet.add(java.lang.Integer.valueOf(i));
            }
            if (arraySet.contains(2) || arraySet.contains(3)) {
                telephonyManager = (android.telephony.TelephonyManager) android.app.AppGlobals.getInitialApplication().getSystemService("phone");
                if (telephonyManager == null) {
                    throw new android.security.keystore.DeviceIdAttestationException("Unable to access telephony service");
                }
            } else {
                telephonyManager = null;
            }
            for (java.lang.Integer num : arraySet) {
                switch (num.intValue()) {
                    case 1:
                        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047479, android.os.Build.getSerial().getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                        break;
                    case 2:
                        java.lang.String imei = telephonyManager.getImei(0);
                        if (imei == null) {
                            throw new android.security.keystore.DeviceIdAttestationException("Unable to retrieve IMEI");
                        }
                        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047478, imei.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                        java.lang.String imei2 = telephonyManager.getImei(1);
                        if (android.text.TextUtils.isEmpty(imei2)) {
                            break;
                        } else {
                            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047469, imei2.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                            break;
                        }
                    case 3:
                        java.lang.String meid = telephonyManager.getMeid(0);
                        if (meid == null) {
                            throw new android.security.keystore.DeviceIdAttestationException("Unable to retrieve MEID");
                        }
                        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047477, meid.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
                        break;
                    case 4:
                        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeBool(1879048912));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown device ID type " + num);
                }
            }
        }
    }

    private java.util.Collection<android.hardware.security.keymint.KeyParameter> constructKeyGenerationArguments() throws android.security.keystore.DeviceIdAttestationException, java.lang.IllegalArgumentException, java.security.InvalidAlgorithmParameterException {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306371, this.mKeySizeBits));
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, this.mKeymasterAlgorithm));
        if (this.mKeymasterAlgorithm == 3) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435466, keySizeAndNameToEcCurve(this.mKeySizeBits, this.mEcCurveName)));
        }
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterPurposes, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterBlockModes, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterEncryptionPaddings, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi.this.lambda$constructKeyGenerationArguments$5(arrayList, (java.lang.Integer) obj);
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterSignaturePaddings, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterDigests, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(arrayList, this.mSpec);
        if (this.mSpec.getKeyValidityStart() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613136, this.mSpec.getKeyValidityStart()));
        }
        if (this.mSpec.getKeyValidityForOriginationEnd() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613137, this.mSpec.getKeyValidityForOriginationEnd()));
        }
        if (this.mSpec.getKeyValidityForConsumptionEnd() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613138, this.mSpec.getKeyValidityForConsumptionEnd()));
        }
        if (this.mSpec.getCertificateNotAfter() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613745, this.mSpec.getCertificateNotAfter()));
        }
        if (this.mSpec.getCertificateNotBefore() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613744, this.mSpec.getCertificateNotBefore()));
        }
        if (this.mSpec.getCertificateSerialNumber() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBignum(-2147482642, this.mSpec.getCertificateSerialNumber()));
        }
        if (this.mSpec.getCertificateSubject() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBytes(-1879047185, this.mSpec.getCertificateSubject().getEncoded()));
        }
        if (this.mSpec.getMaxUsageCount() != -1) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306773, this.mSpec.getMaxUsageCount()));
        }
        addAlgorithmSpecificParameters(arrayList);
        if (this.mSpec.isUniqueIdIncluded()) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBool(1879048394));
        }
        addAttestationParameters(arrayList);
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$constructKeyGenerationArguments$5(final java.util.List list, java.lang.Integer num) {
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, num.intValue()));
        if (num.intValue() == 2) {
            android.security.keystore.ArrayUtils.forEach(this.mKeymasterMgf1Digests, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, ((java.lang.Integer) obj).intValue()));
                }
            });
            if (!getMgf1DigestSetterFlag()) {
                final int keymaster = android.security.keystore.KeyProperties.Digest.toKeymaster("SHA-1");
                android.security.keystore.ArrayUtils.forEach(this.mKeymasterDigests, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.security.keystore2.AndroidKeyStoreKeyPairGeneratorSpi.lambda$constructKeyGenerationArguments$4(keymaster, list, (java.lang.Integer) obj);
                    }
                });
            }
        }
    }

    static /* synthetic */ void lambda$constructKeyGenerationArguments$4(int i, java.util.List list, java.lang.Integer num) {
        if (num.intValue() != i) {
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, num.intValue()));
        }
    }

    private static boolean getMgf1DigestSetterFlag() {
        try {
            return android.security.Flags.mgf1DigestSetterV2();
        } catch (java.lang.SecurityException e) {
            android.util.Log.w(TAG, "Cannot read MGF1 Digest setter flag value", e);
            return false;
        }
    }

    private void addAlgorithmSpecificParameters(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        switch (this.mKeymasterAlgorithm) {
            case 1:
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeLong(1342177480, this.mRSAPublicExponent.longValue()));
                return;
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + this.mKeymasterAlgorithm);
            case 3:
                return;
        }
    }

    private static int getDefaultKeySize(int i) {
        switch (i) {
            case 1:
                return 2048;
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + i);
            case 3:
                return 256;
        }
    }

    private static void checkValidKeySize(int i, int i2, boolean z, java.lang.String str) throws java.security.InvalidAlgorithmParameterException {
        switch (i) {
            case 1:
                if (i2 < 512 || i2 > 8192) {
                    throw new java.security.InvalidAlgorithmParameterException("RSA key size must be >= 512 and <= 8192");
                }
                return;
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + i);
            case 3:
                if (z && i2 != 256) {
                    throw new java.security.InvalidAlgorithmParameterException("Unsupported StrongBox EC key size: " + i2 + " bits. Supported: 256");
                }
                if (z && isCurve25519(str)) {
                    throw new java.security.InvalidAlgorithmParameterException("Unsupported StrongBox EC: " + str);
                }
                if (!SUPPORTED_EC_CURVE_SIZES.contains(java.lang.Integer.valueOf(i2))) {
                    throw new java.security.InvalidAlgorithmParameterException("Unsupported EC key size: " + i2 + " bits. Supported: " + SUPPORTED_EC_CURVE_SIZES);
                }
                return;
        }
    }

    private static java.lang.String getCertificateSignatureAlgorithm(int i, int i2, android.security.keystore.KeyGenParameterSpec keyGenParameterSpec) {
        if ((keyGenParameterSpec.getPurposes() & 4) == 0 || keyGenParameterSpec.isUserAuthenticationRequired() || !keyGenParameterSpec.isDigestsSpecified()) {
            return null;
        }
        switch (i) {
            case 1:
                if (!com.android.internal.util.ArrayUtils.contains(android.security.keystore.KeyProperties.SignaturePadding.allToKeymaster(keyGenParameterSpec.getSignaturePaddings()), 5)) {
                    return null;
                }
                int i3 = i2 - 240;
                java.util.Iterator<java.lang.Integer> it = getAvailableKeymasterSignatureDigests(keyGenParameterSpec.getDigests(), android.security.keystore2.AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests()).iterator();
                int i4 = -1;
                int i5 = -1;
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    int digestOutputSizeBits = android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(intValue);
                    if (digestOutputSizeBits <= i3 && (i4 == -1 || digestOutputSizeBits > i5)) {
                        i4 = intValue;
                        i5 = digestOutputSizeBits;
                    }
                }
                if (i4 == -1) {
                    return null;
                }
                return android.security.keystore.KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(i4) + "WithRSA";
            case 2:
            default:
                throw new java.security.ProviderException("Unsupported algorithm: " + i);
            case 3:
                java.util.Iterator<java.lang.Integer> it2 = getAvailableKeymasterSignatureDigests(keyGenParameterSpec.getDigests(), android.security.keystore2.AndroidKeyStoreBCWorkaroundProvider.getSupportedEcdsaSignatureDigests()).iterator();
                int i6 = -1;
                int i7 = -1;
                while (true) {
                    if (it2.hasNext()) {
                        int intValue2 = it2.next().intValue();
                        int digestOutputSizeBits2 = android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(intValue2);
                        if (digestOutputSizeBits2 == i2) {
                            i6 = intValue2;
                        } else {
                            if (i6 != -1) {
                                if (i7 < i2) {
                                    if (digestOutputSizeBits2 > i7) {
                                    }
                                } else if (digestOutputSizeBits2 < i7 && digestOutputSizeBits2 >= i2) {
                                }
                            }
                            i6 = intValue2;
                            i7 = digestOutputSizeBits2;
                        }
                    }
                }
                if (i6 == -1) {
                    return null;
                }
                return android.security.keystore.KeyProperties.Digest.fromKeymasterToSignatureAlgorithmDigest(i6) + "WithECDSA";
        }
    }

    private static java.util.Set<java.lang.Integer> getAvailableKeymasterSignatureDigests(java.lang.String[] strArr, java.lang.String[] strArr2) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : android.security.keystore.KeyProperties.Digest.allToKeymaster(strArr)) {
            hashSet.add(java.lang.Integer.valueOf(i));
        }
        java.util.HashSet hashSet2 = new java.util.HashSet();
        for (int i2 : android.security.keystore.KeyProperties.Digest.allToKeymaster(strArr2)) {
            hashSet2.add(java.lang.Integer.valueOf(i2));
        }
        java.util.HashSet hashSet3 = new java.util.HashSet(hashSet2);
        hashSet3.retainAll(hashSet);
        return hashSet3;
    }

    private boolean isPropertyEmptyOrUnknown(java.lang.String str) {
        return android.text.TextUtils.isEmpty(str) || str.equals("unknown");
    }
}
