package com.android.internal.org.bouncycastle.jcajce.provider.digest;

/* loaded from: classes4.dex */
public class SHA1 {
    private SHA1() {
    }

    public static class Digest extends com.android.internal.org.bouncycastle.jcajce.provider.digest.BCMessageDigest implements java.lang.Cloneable {
        public Digest() {
            super(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest());
        }

        @Override // java.security.MessageDigest, java.security.MessageDigestSpi
        public java.lang.Object clone() throws java.lang.CloneNotSupportedException {
            com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA1.Digest digest = (com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA1.Digest) super.clone();
            digest.digest = new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest((com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest) this.digest);
            return digest;
        }
    }

    public static class HashMac extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public HashMac() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.HMac(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest()));
        }
    }

    public static class KeyGenerator extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA1", 160, new com.android.internal.org.bouncycastle.crypto.CipherKeyGenerator());
        }
    }

    public static class SHA1Mac extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac {
        public SHA1Mac() {
            super(new com.android.internal.org.bouncycastle.crypto.macs.HMac(new com.android.internal.org.bouncycastle.crypto.digests.SHA1Digest()));
        }
    }

    public static class PBEWithMacKeyFactory extends com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA", null, false, 2, 1, 160, 0);
        }
    }

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider {
        private static final java.lang.String PREFIX = com.android.internal.org.bouncycastle.jcajce.provider.digest.SHA1.class.getName();

        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA", PREFIX + "$SHA1Mac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA1", PREFIX + "$SHA1Mac");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory." + com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, "PBEWITHHMACSHA1");
            configurableProvider.addAlgorithm("Alg.Alias.Mac." + com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, "PBEWITHHMACSHA");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", PREFIX + "$PBEWithMacKeyFactory");
        }
    }
}
