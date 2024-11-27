package com.android.internal.org.bouncycastle.jcajce.provider.keystore;

/* loaded from: classes4.dex */
public class PKCS12 {
    private static final java.lang.String PREFIX = "com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.";

    public static class Mappings extends com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider {
        @Override // com.android.internal.org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(com.android.internal.org.bouncycastle.jcajce.provider.config.ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyStore.PKCS12", "com.android.internal.org.bouncycastle.jcajce.provider.keystore.pkcs12.PKCS12KeyStoreSpi$BCPKCS12KeyStore");
        }
    }
}
