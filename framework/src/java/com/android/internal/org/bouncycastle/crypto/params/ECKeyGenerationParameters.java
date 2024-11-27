package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ECKeyGenerationParameters extends com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters {
    private com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters domainParams;

    public ECKeyGenerationParameters(com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters, java.security.SecureRandom secureRandom) {
        super(secureRandom, eCDomainParameters.getN().bitLength());
        this.domainParams = eCDomainParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters getDomainParameters() {
        return this.domainParams;
    }
}
