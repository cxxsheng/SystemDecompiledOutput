package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreRSACipherSpi extends android.security.keystore2.AndroidKeyStoreCipherSpiBase {
    private final int mKeymasterPadding;
    private int mKeymasterPaddingOverride;
    private int mModulusSizeBytes = -1;

    public static final class NoPadding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public NoPadding() {
            super(1);
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            setKeymasterPurposeOverride(2);
            return true;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException {
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameterSpec != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameterSpec + ". No parameters supported");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameters != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameters + ". No parameters supported");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected java.security.AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }
    }

    public static final class PKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public PKCS1Padding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            setKeymasterPurposeOverride(2);
            setKeymasterPaddingOverride(5);
            return true;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException {
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameterSpec != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameterSpec + ". No parameters supported");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameters != null) {
                throw new java.security.InvalidAlgorithmParameterException("Unexpected parameters: " + algorithmParameters + ". No parameters supported");
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected java.security.AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            if (isEncrypting()) {
                return getModulusSizeBytes();
            }
            return 0;
        }
    }

    static abstract class OAEPWithMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi {
        private static final java.lang.String MGF_ALGORITHM_MGF1 = "MGF1";
        private int mDigestOutputSizeBytes;
        private int mKeymasterDigest;
        private int mKeymasterMgf1Digest;

        OAEPWithMGF1Padding(int i) {
            super(2);
            this.mKeymasterDigest = -1;
            this.mKeymasterMgf1Digest = 2;
            this.mKeymasterDigest = i;
            this.mDigestOutputSizeBytes = (android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(i) + 7) / 8;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters() throws java.security.InvalidKeyException {
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            if (!(algorithmParameterSpec instanceof javax.crypto.spec.OAEPParameterSpec)) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported parameter spec: " + algorithmParameterSpec + ". Only OAEPParameterSpec supported");
            }
            javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec = (javax.crypto.spec.OAEPParameterSpec) algorithmParameterSpec;
            if (!MGF_ALGORITHM_MGF1.equalsIgnoreCase(oAEPParameterSpec.getMGFAlgorithm())) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported MGF: " + oAEPParameterSpec.getMGFAlgorithm() + ". Only " + MGF_ALGORITHM_MGF1 + " supported");
            }
            java.lang.String digestAlgorithm = oAEPParameterSpec.getDigestAlgorithm();
            try {
                int keymaster = android.security.keystore.KeyProperties.Digest.toKeymaster(digestAlgorithm);
                switch (keymaster) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        java.security.spec.AlgorithmParameterSpec mGFParameters = oAEPParameterSpec.getMGFParameters();
                        if (mGFParameters == null) {
                            throw new java.security.InvalidAlgorithmParameterException("MGF parameters must be provided");
                        }
                        if (!(mGFParameters instanceof java.security.spec.MGF1ParameterSpec)) {
                            throw new java.security.InvalidAlgorithmParameterException("Unsupported MGF parameters: " + mGFParameters + ". Only MGF1ParameterSpec supported");
                        }
                        java.lang.String digestAlgorithm2 = ((java.security.spec.MGF1ParameterSpec) mGFParameters).getDigestAlgorithm();
                        javax.crypto.spec.PSource pSource = oAEPParameterSpec.getPSource();
                        if (!(pSource instanceof javax.crypto.spec.PSource.PSpecified)) {
                            throw new java.security.InvalidAlgorithmParameterException("Unsupported source of encoding input P: " + pSource + ". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                        }
                        byte[] value = ((javax.crypto.spec.PSource.PSpecified) pSource).getValue();
                        if (value != null && value.length > 0) {
                            throw new java.security.InvalidAlgorithmParameterException("Unsupported source of encoding input P: " + pSource + ". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                        }
                        this.mKeymasterDigest = keymaster;
                        this.mKeymasterMgf1Digest = android.security.keystore.KeyProperties.Digest.toKeymaster(digestAlgorithm2);
                        this.mDigestOutputSizeBytes = (android.security.keystore2.KeymasterUtils.getDigestOutputSizeBits(keymaster) + 7) / 8;
                        return;
                    default:
                        throw new java.security.InvalidAlgorithmParameterException("Unsupported digest: " + digestAlgorithm);
                }
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.security.InvalidAlgorithmParameterException("Unsupported digest: " + digestAlgorithm, e);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void initAlgorithmSpecificParameters(java.security.AlgorithmParameters algorithmParameters) throws java.security.InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            try {
                javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec = (javax.crypto.spec.OAEPParameterSpec) algorithmParameters.getParameterSpec(javax.crypto.spec.OAEPParameterSpec.class);
                if (oAEPParameterSpec == null) {
                    throw new java.security.InvalidAlgorithmParameterException("OAEP parameters required, but not provided in parameters: " + algorithmParameters);
                }
                initAlgorithmSpecificParameters(oAEPParameterSpec);
            } catch (java.security.spec.InvalidParameterSpecException e) {
                throw new java.security.InvalidAlgorithmParameterException("OAEP parameters required, but not found in parameters: " + algorithmParameters, e);
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase, javax.crypto.CipherSpi
        protected final java.security.AlgorithmParameters engineGetParameters() {
            javax.crypto.spec.OAEPParameterSpec oAEPParameterSpec = new javax.crypto.spec.OAEPParameterSpec(android.security.keystore.KeyProperties.Digest.fromKeymaster(this.mKeymasterDigest), MGF_ALGORITHM_MGF1, android.security.keystore.KeyProperties.Digest.fromKeymasterToMGF1ParameterSpec(this.mKeymasterMgf1Digest), javax.crypto.spec.PSource.PSpecified.DEFAULT);
            try {
                java.security.AlgorithmParameters algorithmParameters = java.security.AlgorithmParameters.getInstance("OAEP");
                algorithmParameters.init(oAEPParameterSpec);
                return algorithmParameters;
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.security.ProviderException("Failed to obtain OAEP AlgorithmParameters", e);
            } catch (java.security.spec.InvalidParameterSpecException e2) {
                throw new java.security.ProviderException("Failed to initialize OAEP AlgorithmParameters with an IV", e2);
            }
        }

        private static boolean isMgfDigestTagPresentInKeyProperties(android.system.keystore2.Authorization[] authorizationArr) {
            for (android.system.keystore2.Authorization authorization : authorizationArr) {
                if (authorization.keyParameter.tag == 536871115) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list, android.system.keystore2.Authorization[] authorizationArr) {
            super.addAlgorithmSpecificParametersToBegin(list, authorizationArr);
            list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
            if (isMgfDigestTagPresentInKeyProperties(authorizationArr)) {
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536871115, this.mKeymasterMgf1Digest));
            }
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final void loadAlgorithmSpecificParametersFromBeginResult(android.hardware.security.keymint.KeyParameter[] keyParameterArr) {
            super.loadAlgorithmSpecificParametersFromBeginResult(keyParameterArr);
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final int getAdditionalEntropyAmountForFinish() {
            if (isEncrypting()) {
                return this.mDigestOutputSizeBytes;
            }
            return 0;
        }

        @Override // android.security.keystore2.AndroidKeyStoreRSACipherSpi, android.security.keystore2.AndroidKeyStoreCipherSpiBase
        protected final java.lang.String getTransform() {
            switch (this.mKeymasterDigest) {
                case 2:
                    return "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
                case 3:
                    return "RSA/ECB/OAEPWithSHA-224AndMGF1Padding";
                case 4:
                    return "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
                case 5:
                    return "RSA/ECB/OAEPWithSHA-384AndMGF1Padding";
                case 6:
                    return "RSA/ECB/OAEPWithSHA-512AndMGF1Padding";
                default:
                    return "RSA/ECB/OAEPPadding";
            }
        }
    }

    public static class OAEPWithSHA1AndMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi.OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public OAEPWithSHA1AndMGF1Padding() {
            super(2);
        }
    }

    public static class OAEPWithSHA224AndMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi.OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public OAEPWithSHA224AndMGF1Padding() {
            super(3);
        }
    }

    public static class OAEPWithSHA256AndMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi.OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public OAEPWithSHA256AndMGF1Padding() {
            super(4);
        }
    }

    public static class OAEPWithSHA384AndMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi.OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public OAEPWithSHA384AndMGF1Padding() {
            super(5);
        }
    }

    public static class OAEPWithSHA512AndMGF1Padding extends android.security.keystore2.AndroidKeyStoreRSACipherSpi.OAEPWithMGF1Padding {
        @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
        public /* bridge */ /* synthetic */ void finalize() throws java.lang.Throwable {
            super.finalize();
        }

        public OAEPWithSHA512AndMGF1Padding() {
            super(6);
        }
    }

    AndroidKeyStoreRSACipherSpi(int i) {
        this.mKeymasterPadding = i;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected java.lang.String getTransform() {
        return "RSA/ECB/" + android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding);
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void initKey(int i, java.security.Key key) throws java.security.InvalidKeyException {
        android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey;
        if (key == null) {
            throw new java.security.InvalidKeyException("Unsupported key: null");
        }
        if (!android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(key.getAlgorithm())) {
            throw new java.security.InvalidKeyException("Unsupported key algorithm: " + key.getAlgorithm() + ". Only " + android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA + " supported");
        }
        if (key instanceof android.security.keystore2.AndroidKeyStorePrivateKey) {
            androidKeyStoreKey = (android.security.keystore2.AndroidKeyStoreKey) key;
        } else if (key instanceof android.security.keystore2.AndroidKeyStorePublicKey) {
            androidKeyStoreKey = (android.security.keystore2.AndroidKeyStoreKey) key;
        } else {
            throw new java.security.InvalidKeyException("Unsupported key type: " + key);
        }
        if (androidKeyStoreKey instanceof java.security.PrivateKey) {
            switch (i) {
                case 1:
                case 3:
                    if (!adjustConfigForEncryptingWithPrivateKey()) {
                        throw new java.security.InvalidKeyException("RSA private keys cannot be used with " + opmodeToString(i) + " and padding " + android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding) + ". Only RSA public keys supported for this mode");
                    }
                    break;
                case 2:
                case 4:
                    break;
                default:
                    throw new java.security.InvalidKeyException("RSA private keys cannot be used with opmode: " + i);
            }
        } else {
            switch (i) {
                case 1:
                case 3:
                    break;
                case 2:
                case 4:
                    throw new java.security.InvalidKeyException("RSA public keys cannot be used with " + opmodeToString(i) + " and padding " + android.security.keystore.KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding) + ". Only RSA private keys supported for this opmode.");
                default:
                    throw new java.security.InvalidKeyException("RSA public keys cannot be used with " + opmodeToString(i));
            }
        }
        long j = -1;
        for (android.system.keystore2.Authorization authorization : androidKeyStoreKey.getAuthorizations()) {
            if (authorization.keyParameter.tag == 805306371) {
                j = android.security.keystore2.KeyStore2ParameterUtils.getUnsignedInt(authorization);
            }
        }
        if (j == -1) {
            throw new java.security.InvalidKeyException("Size of key not known");
        }
        if (j > 2147483647L) {
            throw new java.security.InvalidKeyException("Key too large: " + j + " bits");
        }
        this.mModulusSizeBytes = (int) ((j + 7) / 8);
        setKey(androidKeyStoreKey);
    }

    protected boolean adjustConfigForEncryptingWithPrivateKey() {
        return false;
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetAll() {
        this.mModulusSizeBytes = -1;
        this.mKeymasterPaddingOverride = -1;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 1));
        int keymasterPaddingOverride = getKeymasterPaddingOverride();
        if (keymasterPaddingOverride == -1) {
            keymasterPaddingOverride = this.mKeymasterPadding;
        }
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, keymasterPaddingOverride));
        int keymasterPurposeOverride = getKeymasterPurposeOverride();
        if (keymasterPurposeOverride != -1) {
            if (keymasterPurposeOverride == 2 || keymasterPurposeOverride == 3) {
                list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, 0));
            }
        }
    }

    @Override // android.security.keystore2.AndroidKeyStoreCipherSpiBase
    protected void loadAlgorithmSpecificParametersFromBeginResult(android.hardware.security.keymint.KeyParameter[] keyParameterArr) {
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected final byte[] engineGetIV() {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected final int engineGetOutputSize(int i) {
        return getModulusSizeBytes();
    }

    protected final int getModulusSizeBytes() {
        if (this.mModulusSizeBytes == -1) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        return this.mModulusSizeBytes;
    }

    protected final void setKeymasterPaddingOverride(int i) {
        this.mKeymasterPaddingOverride = i;
    }

    protected final int getKeymasterPaddingOverride() {
        return this.mKeymasterPaddingOverride;
    }
}
