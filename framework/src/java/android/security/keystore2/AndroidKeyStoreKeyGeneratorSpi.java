package android.security.keystore2;

/* loaded from: classes3.dex */
public abstract class AndroidKeyStoreKeyGeneratorSpi extends javax.crypto.KeyGeneratorSpi {
    private static final java.lang.String TAG = "AndroidKeyStoreKeyGeneratorSpi";
    private final int mDefaultKeySizeBits;
    protected int mKeySizeBits;
    private final android.security.KeyStore2 mKeyStore;
    private final int mKeymasterAlgorithm;
    private int[] mKeymasterBlockModes;
    private final int mKeymasterDigest;
    private int[] mKeymasterDigests;
    private int[] mKeymasterPaddings;
    private int[] mKeymasterPurposes;
    private java.security.SecureRandom mRng;
    private android.security.keystore.KeyGenParameterSpec mSpec;

    public static class AES extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi {
        public AES() {
            super(32, 128);
        }

        @Override // android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi, javax.crypto.KeyGeneratorSpi
        protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
            super.engineInit(algorithmParameterSpec, secureRandom);
            if (this.mKeySizeBits != 128 && this.mKeySizeBits != 192 && this.mKeySizeBits != 256) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported key size: " + this.mKeySizeBits + ". Supported: 128, 192, 256.");
            }
        }
    }

    public static class DESede extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi {
        public DESede() {
            super(33, 168);
        }
    }

    protected static abstract class HmacBase extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi {
        protected HmacBase(int i) {
            super(128, i, android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(i));
        }
    }

    public static class HmacSHA1 extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.HmacBase {
        public HmacSHA1() {
            super(2);
        }
    }

    public static class HmacSHA224 extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.HmacBase {
        public HmacSHA224() {
            super(3);
        }
    }

    public static class HmacSHA256 extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.HmacBase {
        public HmacSHA256() {
            super(4);
        }
    }

    public static class HmacSHA384 extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.HmacBase {
        public HmacSHA384() {
            super(5);
        }
    }

    public static class HmacSHA512 extends android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.HmacBase {
        public HmacSHA512() {
            super(6);
        }
    }

    protected AndroidKeyStoreKeyGeneratorSpi(int i, int i2) {
        this(i, -1, i2);
    }

    protected AndroidKeyStoreKeyGeneratorSpi(int i, int i2, int i3) {
        this.mKeyStore = android.security.KeyStore2.getInstance();
        this.mKeymasterAlgorithm = i;
        this.mKeymasterDigest = i2;
        this.mDefaultKeySizeBits = i3;
        if (this.mDefaultKeySizeBits <= 0) {
            throw new java.lang.IllegalArgumentException("Default key size must be positive");
        }
        if (this.mKeymasterAlgorithm == 128 && this.mKeymasterDigest == -1) {
            throw new java.lang.IllegalArgumentException("Digest algorithm must be specified for HMAC key");
        }
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(java.security.SecureRandom secureRandom) {
        throw new java.lang.UnsupportedOperationException("Cannot initialize without a " + android.security.keystore.KeyGenParameterSpec.class.getName() + " parameter");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, java.security.SecureRandom secureRandom) {
        throw new java.lang.UnsupportedOperationException("Cannot initialize without a " + android.security.keystore.KeyGenParameterSpec.class.getName() + " parameter");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected void engineInit(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null) {
            try {
                if (algorithmParameterSpec instanceof android.security.keystore.KeyGenParameterSpec) {
                    android.security.keystore.KeyGenParameterSpec keyGenParameterSpec = (android.security.keystore.KeyGenParameterSpec) algorithmParameterSpec;
                    if (keyGenParameterSpec.getKeystoreAlias() == null) {
                        throw new java.security.InvalidAlgorithmParameterException("KeyStore entry alias not provided");
                    }
                    this.mRng = secureRandom;
                    this.mSpec = keyGenParameterSpec;
                    this.mKeySizeBits = keyGenParameterSpec.getKeySize() != -1 ? keyGenParameterSpec.getKeySize() : this.mDefaultKeySizeBits;
                    if (this.mKeySizeBits <= 0) {
                        throw new java.security.InvalidAlgorithmParameterException("Key size must be positive: " + this.mKeySizeBits);
                    }
                    if (this.mKeySizeBits % 8 != 0) {
                        throw new java.security.InvalidAlgorithmParameterException("Key size must be a multiple of 8: " + this.mKeySizeBits);
                    }
                    try {
                        this.mKeymasterPurposes = android.security.keystore.KeyProperties.Purpose.allToKeymaster(keyGenParameterSpec.getPurposes());
                        this.mKeymasterPaddings = android.security.keystore.KeyProperties.EncryptionPadding.allToKeymaster(keyGenParameterSpec.getEncryptionPaddings());
                        if (keyGenParameterSpec.getSignaturePaddings().length > 0) {
                            throw new java.security.InvalidAlgorithmParameterException("Signature paddings not supported for symmetric key algorithms");
                        }
                        this.mKeymasterBlockModes = android.security.keystore.KeyProperties.BlockMode.allToKeymaster(keyGenParameterSpec.getBlockModes());
                        if ((keyGenParameterSpec.getPurposes() & 1) != 0 && keyGenParameterSpec.isRandomizedEncryptionRequired()) {
                            for (int i : this.mKeymasterBlockModes) {
                                if (!android.security.keystore2.KeymasterUtils.isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(i)) {
                                    throw new java.security.InvalidAlgorithmParameterException("Randomized encryption (IND-CPA) required but may be violated by block mode: " + android.security.keystore.KeyProperties.BlockMode.fromKeymaster(i) + ". See " + android.security.keystore.KeyGenParameterSpec.class.getName() + " documentation.");
                                }
                            }
                        }
                        if (this.mKeymasterAlgorithm == 33 && this.mKeySizeBits != 168) {
                            throw new java.security.InvalidAlgorithmParameterException("3DES key size must be 168 bits.");
                        }
                        if (this.mKeymasterAlgorithm == 128) {
                            if (this.mKeySizeBits < 64 || this.mKeySizeBits > 512) {
                                throw new java.security.InvalidAlgorithmParameterException("HMAC key sizes must be within 64-512 bits, inclusive.");
                            }
                            this.mKeymasterDigests = new int[]{this.mKeymasterDigest};
                            if (keyGenParameterSpec.isDigestsSpecified()) {
                                int[] allToKeymaster = android.security.keystore.KeyProperties.Digest.allToKeymaster(keyGenParameterSpec.getDigests());
                                if (allToKeymaster.length != 1 || allToKeymaster[0] != this.mKeymasterDigest) {
                                    throw new java.security.InvalidAlgorithmParameterException("Unsupported digests specification: " + java.util.Arrays.asList(keyGenParameterSpec.getDigests()) + ". Only " + android.security.keystore.KeyProperties.Digest.fromKeymaster(this.mKeymasterDigest) + " supported for this HMAC key algorithm");
                                }
                            }
                        } else if (keyGenParameterSpec.isDigestsSpecified()) {
                            this.mKeymasterDigests = android.security.keystore.KeyProperties.Digest.allToKeymaster(keyGenParameterSpec.getDigests());
                        } else {
                            this.mKeymasterDigests = libcore.util.EmptyArray.INT;
                        }
                        android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(new java.util.ArrayList(), keyGenParameterSpec);
                        return;
                    } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException e) {
                        throw new java.security.InvalidAlgorithmParameterException(e);
                    }
                }
            } finally {
                resetAll();
            }
        }
        throw new java.security.InvalidAlgorithmParameterException("Cannot initialize without a " + android.security.keystore.KeyGenParameterSpec.class.getName() + " parameter");
    }

    private void resetAll() {
        this.mSpec = null;
        this.mRng = null;
        this.mKeySizeBits = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterPaddings = null;
        this.mKeymasterBlockModes = null;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    protected javax.crypto.SecretKey engineGenerateKey() {
        int i;
        int i2;
        android.os.StrictMode.noteSlowCall("engineGenerateKey");
        android.security.keystore.KeyGenParameterSpec keyGenParameterSpec = this.mSpec;
        if (keyGenParameterSpec == null) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306371, this.mKeySizeBits));
        arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, this.mKeymasterAlgorithm));
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterPurposes, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870913, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterBlockModes, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi.this.lambda$engineGenerateKey$1(arrayList, (java.lang.Integer) obj);
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterPaddings, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, ((java.lang.Integer) obj).intValue()));
            }
        });
        android.security.keystore.ArrayUtils.forEach(this.mKeymasterDigests, new java.util.function.Consumer() { // from class: android.security.keystore2.AndroidKeyStoreKeyGeneratorSpi$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, ((java.lang.Integer) obj).intValue()));
            }
        });
        if (this.mKeymasterAlgorithm == 128 && this.mKeymasterDigests.length != 0) {
            int digestOutputSizeBits = android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(this.mKeymasterDigests[0]);
            if (digestOutputSizeBits == -1) {
                throw new java.security.ProviderException("HMAC key authorized for unsupported digest: " + android.security.keystore.KeyProperties.Digest.fromKeymaster(this.mKeymasterDigests[0]));
            }
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306376, digestOutputSizeBits));
        }
        android.security.keystore2.KeyStore2ParameterUtils.addUserAuthArgs(arrayList, keyGenParameterSpec);
        if (keyGenParameterSpec.getKeyValidityStart() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613136, keyGenParameterSpec.getKeyValidityStart()));
        }
        if (keyGenParameterSpec.getKeyValidityForOriginationEnd() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613137, keyGenParameterSpec.getKeyValidityForOriginationEnd()));
        }
        if (keyGenParameterSpec.getKeyValidityForConsumptionEnd() != null) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeDate(1610613138, keyGenParameterSpec.getKeyValidityForConsumptionEnd()));
        }
        if ((keyGenParameterSpec.getPurposes() & 1) != 0 && !keyGenParameterSpec.isRandomizedEncryptionRequired()) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeBool(1879048199));
        }
        if (keyGenParameterSpec.getMaxUsageCount() != -1) {
            arrayList.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306773, keyGenParameterSpec.getMaxUsageCount()));
        }
        byte[] randomBytesToMixIntoKeystoreRng = android.security.keystore2.KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
        if (!keyGenParameterSpec.isStrongBoxBacked()) {
            i = 1;
        } else {
            i = 2;
        }
        if (!keyGenParameterSpec.isCriticalToDeviceEncryption()) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        android.system.keystore2.KeyDescriptor keyDescriptor = new android.system.keystore2.KeyDescriptor();
        keyDescriptor.alias = keyGenParameterSpec.getKeystoreAlias();
        keyDescriptor.nspace = keyGenParameterSpec.getNamespace();
        keyDescriptor.domain = keyDescriptor.nspace != -1 ? 2 : 0;
        keyDescriptor.blob = null;
        try {
            android.security.KeyStoreSecurityLevel securityLevel = this.mKeyStore.getSecurityLevel(i);
            try {
                return new android.security.keystore2.AndroidKeyStoreSecretKey(keyDescriptor, securityLevel.generateKey(keyDescriptor, null, arrayList, i2, randomBytesToMixIntoKeystoreRng), android.security.keystore.KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(this.mKeymasterAlgorithm, this.mKeymasterDigest), securityLevel);
            } catch (java.lang.IllegalArgumentException e) {
                try {
                    this.mKeyStore.deleteKey(keyDescriptor);
                } catch (android.security.KeyStoreException e2) {
                    android.util.Log.e(TAG, "Failed to delete key after generating successfully but failed to get the algorithm string.", e2);
                }
                throw new java.security.ProviderException("Failed to obtain JCA secret key algorithm name", e);
            }
        } catch (android.security.KeyStoreException e3) {
            switch (e3.getErrorCode()) {
                case -68:
                    throw new android.security.keystore.StrongBoxUnavailableException("Failed to generate key");
                default:
                    throw new java.security.ProviderException("Keystore key generation failed", e3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$engineGenerateKey$1(java.util.List list, java.lang.Integer num) {
        if (num.intValue() == 32 && this.mKeymasterAlgorithm == 32) {
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeInt(805306376, 96));
        }
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870916, num.intValue()));
    }
}
