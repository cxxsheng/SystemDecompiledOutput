package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class AndroidDigestFactoryOpenSSL implements com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface {
    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getMD5() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.MD5();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA1() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.SHA1();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA224() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.SHA224();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA256() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.SHA256();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA384() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.SHA384();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA512() {
        return new com.android.internal.org.bouncycastle.crypto.digests.OpenSSLDigest.SHA512();
    }
}
