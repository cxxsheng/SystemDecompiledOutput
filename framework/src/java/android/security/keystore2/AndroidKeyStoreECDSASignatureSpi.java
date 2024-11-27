package android.security.keystore2;

/* loaded from: classes3.dex */
abstract class AndroidKeyStoreECDSASignatureSpi extends android.security.keystore2.AndroidKeyStoreSignatureSpiBase {
    private static final java.util.Set<java.lang.String> ACCEPTED_SIGNING_SCHEMES = java.util.Set.of(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC.toLowerCase(), java.security.spec.NamedParameterSpec.ED25519.getName().toLowerCase(), "eddsa");
    private int mGroupSizeBits = -1;
    private final int mKeymasterDigest;

    public static final class NONE extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public NONE() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "NONEwithECDSA";
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected android.security.keystore2.KeyStoreCryptoOperationStreamer createMainDataStreamer(android.security.KeyStoreOperation keyStoreOperation) {
            return new android.security.keystore2.AndroidKeyStoreECDSASignatureSpi.NONE.TruncateToFieldSizeMessageStreamer(super.createMainDataStreamer(keyStoreOperation), getGroupSizeBits());
        }

        private static class TruncateToFieldSizeMessageStreamer implements android.security.keystore2.KeyStoreCryptoOperationStreamer {
            private long mConsumedInputSizeBytes;
            private final android.security.keystore2.KeyStoreCryptoOperationStreamer mDelegate;
            private final int mGroupSizeBits;
            private final java.io.ByteArrayOutputStream mInputBuffer;

            private TruncateToFieldSizeMessageStreamer(android.security.keystore2.KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer, int i) {
                this.mInputBuffer = new java.io.ByteArrayOutputStream();
                this.mDelegate = keyStoreCryptoOperationStreamer;
                this.mGroupSizeBits = i;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public byte[] update(byte[] bArr, int i, int i2) throws android.security.KeyStoreException {
                if (i2 > 0) {
                    this.mInputBuffer.write(bArr, i, i2);
                    this.mConsumedInputSizeBytes += i2;
                }
                return libcore.util.EmptyArray.BYTE;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public byte[] doFinal(byte[] bArr, int i, int i2, byte[] bArr2) throws android.security.KeyStoreException {
                if (i2 > 0) {
                    this.mConsumedInputSizeBytes += i2;
                    this.mInputBuffer.write(bArr, i, i2);
                }
                byte[] byteArray = this.mInputBuffer.toByteArray();
                this.mInputBuffer.reset();
                return this.mDelegate.doFinal(byteArray, 0, java.lang.Math.min(byteArray.length, (this.mGroupSizeBits + 7) / 8), bArr2);
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public long getConsumedInputSizeBytes() {
                return this.mConsumedInputSizeBytes;
            }

            @Override // android.security.keystore2.KeyStoreCryptoOperationStreamer
            public long getProducedOutputSizeBytes() {
                return this.mDelegate.getProducedOutputSizeBytes();
            }
        }
    }

    public static final class Ed25519 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public Ed25519() {
            super(0);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return java.security.spec.NamedParameterSpec.ED25519.getName();
        }
    }

    public static final class SHA1 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public SHA1() {
            super(2);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA1withECDSA";
        }
    }

    public static final class SHA224 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public SHA224() {
            super(3);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA224withECDSA";
        }
    }

    public static final class SHA256 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public SHA256() {
            super(4);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA256withECDSA";
        }
    }

    public static final class SHA384 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public SHA384() {
            super(5);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA384withECDSA";
        }
    }

    public static final class SHA512 extends android.security.keystore2.AndroidKeyStoreECDSASignatureSpi {
        public SHA512() {
            super(6);
        }

        @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
        protected java.lang.String getAlgorithm() {
            return "SHA512withECDSA";
        }
    }

    AndroidKeyStoreECDSASignatureSpi(int i) {
        this.mKeymasterDigest = i;
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void initKey(android.security.keystore2.AndroidKeyStoreKey androidKeyStoreKey) throws java.security.InvalidKeyException {
        long j;
        if (!ACCEPTED_SIGNING_SCHEMES.contains(androidKeyStoreKey.getAlgorithm().toLowerCase())) {
            throw new java.security.InvalidKeyException("Unsupported key algorithm: " + androidKeyStoreKey.getAlgorithm() + ". Only " + java.util.Arrays.toString(ACCEPTED_SIGNING_SCHEMES.stream().toArray()) + " supported");
        }
        android.system.keystore2.Authorization[] authorizations = androidKeyStoreKey.getAuthorizations();
        int length = authorizations.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                j = -1;
                break;
            }
            android.system.keystore2.Authorization authorization = authorizations[i];
            if (authorization.keyParameter.tag == 805306371) {
                j = android.security.keystore2.KeyStore2ParameterUtils.getUnsignedInt(authorization);
                break;
            } else if (authorization.keyParameter.tag != 268435466) {
                i++;
            } else {
                j = android.security.keystore.KeyProperties.EcCurve.fromKeymasterCurve(authorization.keyParameter.value.getEcCurve());
                break;
            }
        }
        if (j == -1) {
            throw new java.security.InvalidKeyException("Size of key not known");
        }
        if (j > 2147483647L) {
            throw new java.security.InvalidKeyException("Key too large: " + j + " bits");
        }
        this.mGroupSizeBits = (int) j;
        super.initKey(androidKeyStoreKey);
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetAll() {
        this.mGroupSizeBits = -1;
        super.resetAll();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final void addAlgorithmSpecificParametersToBegin(java.util.List<android.hardware.security.keymint.KeyParameter> list) {
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(268435458, 3));
        list.add(android.security.keystore2.KeyStore2ParameterUtils.makeEnum(536870917, this.mKeymasterDigest));
    }

    @Override // android.security.keystore2.AndroidKeyStoreSignatureSpiBase
    protected final int getAdditionalEntropyAmountForSign() {
        return (this.mGroupSizeBits + 7) / 8;
    }

    protected final int getGroupSizeBits() {
        if (this.mGroupSizeBits == -1) {
            throw new java.lang.IllegalStateException("Not initialized");
        }
        return this.mGroupSizeBits;
    }
}
