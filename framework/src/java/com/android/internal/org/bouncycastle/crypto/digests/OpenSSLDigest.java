package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class OpenSSLDigest implements com.android.internal.org.bouncycastle.crypto.ExtendedDigest {
    private final int byteSize;
    private final java.security.MessageDigest delegate;

    public OpenSSLDigest(java.lang.String str, int i) {
        try {
            this.delegate = java.security.MessageDigest.getInstance(str, "AndroidOpenSSL");
            this.byteSize = i;
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public java.lang.String getAlgorithmName() {
        return this.delegate.getAlgorithm();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.delegate.getDigestLength();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.byteSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void reset() {
        this.delegate.reset();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.delegate.update(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i, int i2) {
        this.delegate.update(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i) {
        try {
            return this.delegate.digest(bArr, i, bArr.length - i);
        } catch (java.security.DigestException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static class MD5 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public MD5() {
            super(android.security.keystore.KeyProperties.DIGEST_MD5, 64);
        }
    }

    public static class SHA1 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public SHA1() {
            super("SHA-1", 64);
        }
    }

    public static class SHA224 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public SHA224() {
            super(android.security.keystore.KeyProperties.DIGEST_SHA224, 64);
        }
    }

    public static class SHA256 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public SHA256() {
            super("SHA-256", 64);
        }
    }

    public static class SHA384 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public SHA384() {
            super(android.security.keystore.KeyProperties.DIGEST_SHA384, 128);
        }
    }

    public static class SHA512 extends com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest {
        public SHA512() {
            super(android.security.keystore.KeyProperties.DIGEST_SHA512, 128);
        }
    }
}
