package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseWrapCipher extends javax.crypto.CipherSpi implements com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE {
    private java.lang.Class[] availableSpecs;
    protected java.security.AlgorithmParameters engineParams;
    private boolean forWrapping;
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper;
    private byte[] iv;
    private int ivSize;
    protected int pbeHash;
    protected int pbeIvSize;
    protected int pbeKeySize;
    protected int pbeType;
    protected com.android.internal.org.bouncycastle.crypto.Wrapper wrapEngine;
    private com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.ErasableOutputStream wrapStream;

    protected BaseWrapCipher() {
        this.availableSpecs = new java.lang.Class[]{javax.crypto.spec.PBEParameterSpec.class, javax.crypto.spec.IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
    }

    protected BaseWrapCipher(com.android.internal.org.bouncycastle.crypto.Wrapper wrapper) {
        this(wrapper, 0);
    }

    protected BaseWrapCipher(com.android.internal.org.bouncycastle.crypto.Wrapper wrapper, int i) {
        this.availableSpecs = new java.lang.Class[]{javax.crypto.spec.PBEParameterSpec.class, javax.crypto.spec.IvParameterSpec.class};
        this.pbeType = 2;
        this.pbeHash = 1;
        this.engineParams = null;
        this.wrapEngine = null;
        this.wrapStream = null;
        this.helper = new com.android.internal.org.bouncycastle.jcajce.util.DefaultJcaJceHelper();
        this.wrapEngine = wrapper;
        this.ivSize = i;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.iv);
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetKeySize(java.security.Key key) {
        return key.getEncoded().length * 8;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return -1;
    }

    @Override // javax.crypto.CipherSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        if (this.engineParams == null && this.iv != null) {
            java.lang.String algorithmName = this.wrapEngine.getAlgorithmName();
            if (algorithmName.indexOf(47) >= 0) {
                algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
            }
            try {
                this.engineParams = createParametersInstance(algorithmName);
                this.engineParams.init(new javax.crypto.spec.IvParameterSpec(this.iv));
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }
        return this.engineParams;
    }

    protected final java.security.AlgorithmParameters createParametersInstance(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException {
        return this.helper.createAlgorithmParameters(str);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetMode(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        throw new java.security.NoSuchAlgorithmException("can't support mode " + str);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetPadding(java.lang.String str) throws javax.crypto.NoSuchPaddingException {
        throw new javax.crypto.NoSuchPaddingException("Padding " + str + " unknown.");
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters keyParameter;
        if (key instanceof com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) {
            com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey bCPBEKey = (com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey) key;
            if (algorithmParameterSpec instanceof javax.crypto.spec.PBEParameterSpec) {
                keyParameter = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBE.Util.makePBEParameters(bCPBEKey, algorithmParameterSpec, this.wrapEngine.getAlgorithmName());
            } else if (bCPBEKey.getParam() != null) {
                keyParameter = bCPBEKey.getParam();
            } else {
                throw new java.security.InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
            }
        } else {
            keyParameter = new com.android.internal.org.bouncycastle.crypto.params.KeyParameter(key.getEncoded());
        }
        if (algorithmParameterSpec instanceof javax.crypto.spec.IvParameterSpec) {
            this.iv = ((javax.crypto.spec.IvParameterSpec) algorithmParameterSpec).getIV();
            keyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(keyParameter, this.iv);
        }
        if ((keyParameter instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) && this.ivSize != 0 && (i == 3 || i == 1)) {
            this.iv = new byte[this.ivSize];
            secureRandom.nextBytes(this.iv);
            keyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV(keyParameter, this.iv);
        }
        if (secureRandom != null) {
            keyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(keyParameter, secureRandom);
        }
        try {
            switch (i) {
                case 1:
                    this.wrapEngine.init(true, keyParameter);
                    this.wrapStream = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.ErasableOutputStream();
                    this.forWrapping = true;
                    return;
                case 2:
                    this.wrapEngine.init(false, keyParameter);
                    this.wrapStream = new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.ErasableOutputStream();
                    this.forWrapping = false;
                    return;
                case 3:
                    this.wrapEngine.init(true, keyParameter);
                    this.wrapStream = null;
                    this.forWrapping = true;
                    return;
                case 4:
                    this.wrapEngine.init(false, keyParameter);
                    this.wrapStream = null;
                    this.forWrapping = false;
                    return;
                default:
                    throw new java.security.InvalidParameterException("Unknown mode parameter passed to init.");
            }
        } catch (java.lang.Exception e) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.AlgorithmParameters algorithmParameters, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        java.security.spec.AlgorithmParameterSpec algorithmParameterSpec;
        if (algorithmParameters == null) {
            algorithmParameterSpec = null;
        } else {
            algorithmParameterSpec = com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.SpecUtil.extractSpec(algorithmParameters, this.availableSpecs);
            if (algorithmParameterSpec == null) {
                throw new java.security.InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        this.engineParams = algorithmParameters;
        engineInit(i, key, algorithmParameterSpec, secureRandom);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        try {
            engineInit(i, key, (java.security.spec.AlgorithmParameterSpec) null, secureRandom);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            throw new com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseWrapCipher.InvalidKeyOrParametersException(e.getMessage(), e);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        if (this.wrapStream == null) {
            throw new java.lang.IllegalStateException("not supported in a wrapping mode");
        }
        this.wrapStream.write(bArr, i, i2);
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.ShortBufferException {
        if (this.wrapStream == null) {
            throw new java.lang.IllegalStateException("not supported in a wrapping mode");
        }
        this.wrapStream.write(bArr, i, i2);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        if (this.wrapStream == null) {
            throw new java.lang.IllegalStateException("not supported in a wrapping mode");
        }
        if (bArr != null) {
            this.wrapStream.write(bArr, i, i2);
        }
        try {
            if (this.forWrapping) {
                try {
                    return this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                } catch (java.lang.Exception e) {
                    throw new javax.crypto.IllegalBlockSizeException(e.getMessage());
                }
            }
            try {
                return this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
            } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e2) {
                throw new javax.crypto.BadPaddingException(e2.getMessage());
            }
        } finally {
            this.wrapStream.erase();
        }
        this.wrapStream.erase();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044 A[Catch: all -> 0x0063, TRY_LEAVE, TryCatch #0 {all -> 0x0063, blocks: (B:5:0x0009, B:23:0x000e, B:10:0x003f, B:12:0x0044, B:16:0x004f, B:17:0x0057, B:9:0x002c, B:26:0x0022, B:27:0x002b, B:20:0x0059, B:21:0x0062), top: B:4:0x0009, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004f A[Catch: all -> 0x0063, TRY_ENTER, TryCatch #0 {all -> 0x0063, blocks: (B:5:0x0009, B:23:0x000e, B:10:0x003f, B:12:0x0044, B:16:0x004f, B:17:0x0057, B:9:0x002c, B:26:0x0022, B:27:0x002b, B:20:0x0059, B:21:0x0062), top: B:4:0x0009, inners: #1, #2 }] */
    @Override // javax.crypto.CipherSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException, javax.crypto.ShortBufferException {
        byte[] wrap;
        if (this.wrapStream == null) {
            throw new java.lang.IllegalStateException("not supported in a wrapping mode");
        }
        this.wrapStream.write(bArr, i, i2);
        try {
            if (this.forWrapping) {
                try {
                    wrap = this.wrapEngine.wrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                    if (wrap.length + i3 <= bArr2.length) {
                        throw new javax.crypto.ShortBufferException("output buffer too short for input.");
                    }
                    java.lang.System.arraycopy(wrap, 0, bArr2, i3, wrap.length);
                    return wrap.length;
                } catch (java.lang.Exception e) {
                    throw new javax.crypto.IllegalBlockSizeException(e.getMessage());
                }
            }
            try {
                wrap = this.wrapEngine.unwrap(this.wrapStream.getBuf(), 0, this.wrapStream.size());
                if (wrap.length + i3 <= bArr2.length) {
                }
            } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e2) {
                throw new javax.crypto.BadPaddingException(e2.getMessage());
            }
        } finally {
            this.wrapStream.erase();
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineWrap(java.security.Key key) throws javax.crypto.IllegalBlockSizeException, java.security.InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null) {
            throw new java.security.InvalidKeyException("Cannot wrap key, null encoding.");
        }
        try {
            if (this.wrapEngine == null) {
                return engineDoFinal(encoded, 0, encoded.length);
            }
            return this.wrapEngine.wrap(encoded, 0, encoded.length);
        } catch (javax.crypto.BadPaddingException e) {
            throw new javax.crypto.IllegalBlockSizeException(e.getMessage());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected java.security.Key engineUnwrap(byte[] bArr, java.lang.String str, int i) throws java.security.InvalidKeyException, java.security.NoSuchAlgorithmException {
        byte[] unwrap;
        try {
            if (this.wrapEngine == null) {
                unwrap = engineDoFinal(bArr, 0, bArr.length);
            } else {
                unwrap = this.wrapEngine.unwrap(bArr, 0, bArr.length);
            }
            if (i == 3) {
                return new javax.crypto.spec.SecretKeySpec(unwrap, str);
            }
            if (str.equals("") && i == 2) {
                try {
                    com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo privateKeyInfo = com.android.internal.org.bouncycastle.asn1.pkcs.PrivateKeyInfo.getInstance(unwrap);
                    java.security.PrivateKey privateKey = com.android.internal.org.bouncycastle.jce.provider.BouncyCastleProvider.getPrivateKey(privateKeyInfo);
                    if (privateKey != null) {
                        return privateKey;
                    }
                    throw new java.security.InvalidKeyException("algorithm " + privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm() + " not supported");
                } catch (java.lang.Exception e) {
                    throw new java.security.InvalidKeyException("Invalid key encoding.");
                }
            }
            try {
                java.security.KeyFactory createKeyFactory = this.helper.createKeyFactory(str);
                if (i == 1) {
                    return createKeyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(unwrap));
                }
                if (i == 2) {
                    return createKeyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(unwrap));
                }
                throw new java.security.InvalidKeyException("Unknown key type " + i);
            } catch (java.security.NoSuchProviderException e2) {
                throw new java.security.InvalidKeyException("Unknown key type " + e2.getMessage());
            } catch (java.security.spec.InvalidKeySpecException e3) {
                throw new java.security.InvalidKeyException("Unknown key type " + e3.getMessage());
            }
        } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e4) {
            throw new java.security.InvalidKeyException(e4.getMessage());
        } catch (javax.crypto.BadPaddingException e5) {
            throw new java.security.InvalidKeyException(e5.getMessage());
        } catch (javax.crypto.IllegalBlockSizeException e6) {
            throw new java.security.InvalidKeyException(e6.getMessage());
        }
    }

    protected static final class ErasableOutputStream extends java.io.ByteArrayOutputStream {
        public byte[] getBuf() {
            return this.buf;
        }

        public void erase() {
            com.android.internal.org.bouncycastle.util.Arrays.fill(this.buf, (byte) 0);
            reset();
        }
    }

    protected static class InvalidKeyOrParametersException extends java.security.InvalidKeyException {
        private final java.lang.Throwable cause;

        InvalidKeyOrParametersException(java.lang.String str, java.lang.Throwable th) {
            super(str);
            this.cause = th;
        }

        @Override // java.lang.Throwable
        public java.lang.Throwable getCause() {
            return this.cause;
        }
    }
}
