package com.android.internal.org.bouncycastle.crypto.digests;

/* loaded from: classes4.dex */
public class AndroidDigestFactoryBouncyCastle implements com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface {
    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getMD5() {
        return new com.android.internal.org.bouncycastle.crypto.digests.MD5Digest();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA1() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA224() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA224Digest();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA256() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA256Digest();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA384() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface
    public com.android.internal.org.bouncycastle.crypto.Digest getSHA512() {
        return new com.android.internal.org.bouncycastle.crypto.digests.SHA512Digest();
    }
}
