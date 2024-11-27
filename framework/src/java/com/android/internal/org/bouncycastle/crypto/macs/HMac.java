package com.android.internal.org.bouncycastle.crypto.macs;

/* loaded from: classes4.dex */
public class HMac implements com.android.internal.org.bouncycastle.crypto.Mac {
    private static final byte IPAD = 54;
    private static final byte OPAD = 92;
    private static java.util.Hashtable blockLengths = new java.util.Hashtable();
    private int blockLength;
    private com.android.internal.org.bouncycastle.crypto.Digest digest;
    private int digestSize;
    private byte[] inputPad;
    private com.android.internal.org.bouncycastle.util.Memoable ipadState;
    private com.android.internal.org.bouncycastle.util.Memoable opadState;
    private byte[] outputBuf;

    static {
        blockLengths.put(android.security.keystore.KeyProperties.DIGEST_MD5, com.android.internal.org.bouncycastle.util.Integers.valueOf(64));
        blockLengths.put("SHA-1", com.android.internal.org.bouncycastle.util.Integers.valueOf(64));
        blockLengths.put(android.security.keystore.KeyProperties.DIGEST_SHA224, com.android.internal.org.bouncycastle.util.Integers.valueOf(64));
        blockLengths.put("SHA-256", com.android.internal.org.bouncycastle.util.Integers.valueOf(64));
        blockLengths.put(android.security.keystore.KeyProperties.DIGEST_SHA384, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
        blockLengths.put(android.security.keystore.KeyProperties.DIGEST_SHA512, com.android.internal.org.bouncycastle.util.Integers.valueOf(128));
    }

    private static int getByteLength(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        if (digest instanceof com.android.internal.org.bouncycastle.crypto.ExtendedDigest) {
            return ((com.android.internal.org.bouncycastle.crypto.ExtendedDigest) digest).getByteLength();
        }
        java.lang.Integer num = (java.lang.Integer) blockLengths.get(digest.getAlgorithmName());
        if (num == null) {
            throw new java.lang.IllegalArgumentException("unknown digest passed: " + digest.getAlgorithmName());
        }
        return num.intValue();
    }

    public HMac(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this(digest, getByteLength(digest));
    }

    private HMac(com.android.internal.org.bouncycastle.crypto.Digest digest, int i) {
        this.digest = digest;
        this.digestSize = digest.getDigestSize();
        this.blockLength = i;
        this.inputPad = new byte[this.blockLength];
        this.outputBuf = new byte[this.blockLength + this.digestSize];
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public java.lang.String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "/HMAC";
    }

    public com.android.internal.org.bouncycastle.crypto.Digest getUnderlyingDigest() {
        return this.digest;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.digest.reset();
        byte[] key = ((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters).getKey();
        int length = key.length;
        if (length > this.blockLength) {
            this.digest.update(key, 0, length);
            this.digest.doFinal(this.inputPad, 0);
            length = this.digestSize;
        } else {
            java.lang.System.arraycopy(key, 0, this.inputPad, 0, length);
        }
        while (length < this.inputPad.length) {
            this.inputPad[length] = 0;
            length++;
        }
        java.lang.System.arraycopy(this.inputPad, 0, this.outputBuf, 0, this.blockLength);
        xorPad(this.inputPad, this.blockLength, IPAD);
        xorPad(this.outputBuf, this.blockLength, OPAD);
        if (this.digest instanceof com.android.internal.org.bouncycastle.util.Memoable) {
            this.opadState = ((com.android.internal.org.bouncycastle.util.Memoable) this.digest).copy();
            ((com.android.internal.org.bouncycastle.crypto.Digest) this.opadState).update(this.outputBuf, 0, this.blockLength);
        }
        this.digest.update(this.inputPad, 0, this.inputPad.length);
        if (this.digest instanceof com.android.internal.org.bouncycastle.util.Memoable) {
            this.ipadState = ((com.android.internal.org.bouncycastle.util.Memoable) this.digest).copy();
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.digestSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte b) {
        this.digest.update(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i) {
        this.digest.doFinal(this.outputBuf, this.blockLength);
        if (this.opadState != null) {
            ((com.android.internal.org.bouncycastle.util.Memoable) this.digest).reset(this.opadState);
            this.digest.update(this.outputBuf, this.blockLength, this.digest.getDigestSize());
        } else {
            this.digest.update(this.outputBuf, 0, this.outputBuf.length);
        }
        int doFinal = this.digest.doFinal(bArr, i);
        for (int i2 = this.blockLength; i2 < this.outputBuf.length; i2++) {
            this.outputBuf[i2] = 0;
        }
        if (this.ipadState != null) {
            ((com.android.internal.org.bouncycastle.util.Memoable) this.digest).reset(this.ipadState);
        } else {
            this.digest.update(this.inputPad, 0, this.inputPad.length);
        }
        return doFinal;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Mac
    public void reset() {
        this.digest.reset();
        this.digest.update(this.inputPad, 0, this.inputPad.length);
    }

    private static void xorPad(byte[] bArr, int i, byte b) {
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ b);
        }
    }
}
