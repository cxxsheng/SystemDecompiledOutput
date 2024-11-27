package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreRSASignatureSpi extends android.security.keystore2.AndroidKeyStoreSignatureSpiBase {
    private final int mKeymasterDigest;
    private final int mKeymasterPadding;

    static abstract class PKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi {
        PKCS1Padding(int i) {
            super(i, 5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected final int getAdditionalEntropyAmountForSign() {
            return 0;
        }
    }

    public static final class NONEWithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public NONEWithPKCS1Padding() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "NONEwithRSA";
        }
    }

    public static final class MD5WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public MD5WithPKCS1Padding() {
            super(1);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "MD5withRSA";
        }
    }

    public static final class SHA1WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public SHA1WithPKCS1Padding() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA1withRSA";
        }
    }

    public static final class SHA224WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public SHA224WithPKCS1Padding() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA224withRSA";
        }
    }

    public static final class SHA256WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public SHA256WithPKCS1Padding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA256withRSA";
        }
    }

    public static final class SHA384WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public SHA384WithPKCS1Padding() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA384withRSA";
        }
    }

    public static final class SHA512WithPKCS1Padding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PKCS1Padding {
        public SHA512WithPKCS1Padding() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA512withRSA";
        }
    }

    static abstract class PSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi {
        private static final int SALT_LENGTH_BYTES = 20;

        PSSPadding(int i) {
            super(i, 3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected final int getAdditionalEntropyAmountForSign() {
            return 20;
        }
    }

    public static final class SHA1WithPSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PSSPadding {
        public SHA1WithPSSPadding() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA1withRSA/PSS";
        }
    }

    public static final class SHA224WithPSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PSSPadding {
        public SHA224WithPSSPadding() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA224withRSA/PSS";
        }
    }

    public static final class SHA256WithPSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PSSPadding {
        public SHA256WithPSSPadding() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA256withRSA/PSS";
        }
    }

    public static final class SHA384WithPSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PSSPadding {
        public SHA384WithPSSPadding() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA384withRSA/PSS";
        }
    }

    public static final class SHA512WithPSSPadding extends android.security.keystore2.AndroidKeyStoreRSASignatureSpi.PSSPadding {
        public SHA512WithPSSPadding() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA512withRSA/PSS";
        }
    }

    AndroidKeyStoreRSASignatureSpi(int i, int i2) {
        this.mKeymasterDigest = i;
        this.mKeymasterPadding = i2;
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void initKey(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) throws java.security.InvalidKeyException {
        if (!android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA.equalsIgnoreCase(androidKeyStoreKey.getAlgorithm())) {
            throw new java.security.InvalidKeyException("Unsupported key algorithm: " + androidKeyStoreKey.getAlgorithm() + ". Only " + android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA + " supported");
        }
        super.initKey(androidKeyStoreKey);
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetAll() {
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 1));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870918, this.mKeymasterPadding));
    }
}
