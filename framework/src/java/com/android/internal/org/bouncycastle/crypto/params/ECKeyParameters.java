package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ECKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter {
    private final com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters;

    protected ECKeyParameters(boolean z, com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        super(z);
        if (eCDomainParameters == null) {
            throw new java.lang.NullPointerException("'parameters' cannot be null");
        }
        this.parameters = eCDomainParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters getParameters() {
        return this.parameters;
    }
}
