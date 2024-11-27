package com.android.internal.org.bouncycastle.jcajce.provider.digest;

/* loaded from: classes4.dex */
public class SHA384 {
    private SHA384() {
    }

    public static class Digest extends com.android.internal.org.bouncycastle.jcajce.provider.digest.BCMessageDigest implements java.lang.Cloneable {
        public Digest() {
            super(new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest());
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
            com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA384.Digest digest = (com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA384.Digest) super.clone();
            digest.digest = new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest((com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public HashMac() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.HMac(new com.android.internal.org.bouncycastle.crypto.digests.SHA384Digest()));
        }
    }

    public static class KeyGenerator extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA384", 384, new com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator());
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider {
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA384.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addPrivateAlgorithm("Mac", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, PREFIX + "$HashMac");
        }
    }
}
