package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

/* loaded from: classes4.dex */
public class BCPBEKey implements javax.crypto.interfaces.PBEKey, javax.security.auth.Destroyable {
    java.lang.String algorithm;
    int digest;
    private final java.util.concurrent.atomic.AtomicBoolean hasBeenDestroyed;
    private final int iterationCount;
    int ivSize;
    int keySize;
    com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier oid;
    private final com.android.internal.org.bouncycastle.crypto.CipherParameters param;
    private final char[] password;
    private final byte[] salt;
    boolean tryWrong;
    int type;

    public BCPBEKey(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, int i, int i2, int i3, int i4, javax.crypto.spec.PBEKeySpec pBEKeySpec, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.hasBeenDestroyed = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.tryWrong = false;
        this.algorithm = str;
        this.oid = aSN1ObjectIdentifier;
        this.type = i;
        this.digest = i2;
        this.keySize = i3;
        this.ivSize = i4;
        this.password = pBEKeySpec.getPassword();
        this.iterationCount = pBEKeySpec.getIterationCount();
        this.salt = pBEKeySpec.getSalt();
        this.param = cipherParameters;
    }

    public BCPBEKey(java.lang.String str, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.hasBeenDestroyed = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.tryWrong = false;
        this.algorithm = str;
        this.param = cipherParameters;
        this.password = null;
        this.iterationCount = -1;
        this.salt = null;
    }

    @Override // java.security.Key
    public java.lang.String getAlgorithm() {
        checkDestroyed(this);
        return this.algorithm;
    }

    @Override // java.security.Key
    public java.lang.String getFormat() {
        return "RAW";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter;
        checkDestroyed(this);
        if (this.param != null) {
            if (this.param instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) {
                keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithIV) this.param).getParameters();
            } else {
                keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) this.param;
            }
            return keyParameter.getKey();
        }
        if (this.type == 2) {
            return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS12PasswordToBytes(this.password);
        }
        if (this.type == 5) {
            return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.password);
        }
        return com.android.internal.org.bouncycastle.crypto.PBEParametersGenerator.PKCS5PasswordToBytes(this.password);
    }

    int getType() {
        checkDestroyed(this);
        return this.type;
    }

    int getDigest() {
        checkDestroyed(this);
        return this.digest;
    }

    int getKeySize() {
        checkDestroyed(this);
        return this.keySize;
    }

    public int getIvSize() {
        checkDestroyed(this);
        return this.ivSize;
    }

    public com.android.internal.org.bouncycastle.crypto.CipherParameters getParam() {
        checkDestroyed(this);
        return this.param;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public char[] getPassword() {
        checkDestroyed(this);
        if (this.password == null) {
            throw new java.lang.IllegalStateException("no password available");
        }
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.password);
    }

    @Override // javax.crypto.interfaces.PBEKey
    public byte[] getSalt() {
        checkDestroyed(this);
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.salt);
    }

    @Override // javax.crypto.interfaces.PBEKey
    public int getIterationCount() {
        checkDestroyed(this);
        return this.iterationCount;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getOID() {
        checkDestroyed(this);
        return this.oid;
    }

    public void setTryWrongPKCS12Zero(boolean z) {
        this.tryWrong = z;
    }

    boolean shouldTryWrongPKCS12() {
        return this.tryWrong;
    }

    @Override // javax.security.auth.Destroyable
    public void destroy() {
        if (!this.hasBeenDestroyed.getAndSet(true)) {
            if (this.password != null) {
                com.android.internal.org.bouncycastle.util.Arrays.fill(this.password, (char) 0);
            }
            if (this.salt != null) {
                com.android.internal.org.bouncycastle.util.Arrays.fill(this.salt, (byte) 0);
            }
        }
    }

    @Override // javax.security.auth.Destroyable
    public boolean isDestroyed() {
        return this.hasBeenDestroyed.get();
    }

    static void checkDestroyed(javax.security.auth.Destroyable destroyable) {
        if (destroyable.isDestroyed()) {
            throw new java.lang.IllegalStateException("key has been destroyed");
        }
    }
}
