package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public abstract class BaseCipherSpi extends javax.crypto.CipherSpi {
    private byte[] iv;
    private int ivSize;
    private java.lang.Class[] availableSpecs = {javax.crypto.spec.IvParameterSpec.class, javax.crypto.spec.PBEParameterSpec.class};
    private final com.android.internal.org.bouncycastle.jcajce.util.JcaJceHelper helper = new com.android.internal.org.bouncycastle.jcajce.util.BCJcaJceHelper();
    protected java.security.AlgorithmParameters engineParams = null;
    protected com.android.internal.org.bouncycastle.crypto.Wrapper wrapEngine = null;

    protected BaseCipherSpi() {
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetKeySize(java.security.Key key) {
        return key.getEncoded().length;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return -1;
    }

    @Override // javax.crypto.CipherSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        return null;
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
    protected java.security.Key engineUnwrap(byte[] bArr, java.lang.String str, int i) throws java.security.InvalidKeyException {
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
            } catch (java.security.NoSuchAlgorithmException e2) {
                throw new java.security.InvalidKeyException("Unknown key type " + e2.getMessage());
            } catch (java.security.NoSuchProviderException e3) {
                throw new java.security.InvalidKeyException("Unknown key type " + e3.getMessage());
            } catch (java.security.spec.InvalidKeySpecException e4) {
                throw new java.security.InvalidKeyException("Unknown key type " + e4.getMessage());
            }
        } catch (com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException e5) {
            throw new java.security.InvalidKeyException(e5.getMessage());
        } catch (javax.crypto.BadPaddingException e6) {
            throw new java.security.InvalidKeyException("unable to unwrap") { // from class: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseCipherSpi.1
                @Override // java.lang.Throwable
                public synchronized java.lang.Throwable getCause() {
                    return e6;
                }
            };
        } catch (javax.crypto.IllegalBlockSizeException e7) {
            throw new java.security.InvalidKeyException(e7.getMessage());
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
}
