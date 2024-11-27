package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DHBasicKeyPairGenerator implements com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator {
    private com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters param;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters keyGenerationParameters) {
        this.param = (com.android.internal.org.bouncycastle.crypto.params.DHKeyGenerationParameters) keyGenerationParameters;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair generateKeyPair() {
        com.android.internal.org.bouncycastle.crypto.generators.DHKeyGeneratorHelper dHKeyGeneratorHelper = com.android.internal.org.bouncycastle.crypto.generators.DHKeyGeneratorHelper.INSTANCE;
        com.android.internal.org.bouncycastle.crypto.params.DHParameters parameters = this.param.getParameters();
        java.math.BigInteger calculatePrivate = dHKeyGeneratorHelper.calculatePrivate(parameters, this.param.getRandom());
        return new com.android.internal.org.bouncycastle.crypto.AsymmetricCipherKeyPair((com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(dHKeyGeneratorHelper.calculatePublic(parameters, calculatePrivate), parameters), (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(calculatePrivate, parameters));
    }
}
