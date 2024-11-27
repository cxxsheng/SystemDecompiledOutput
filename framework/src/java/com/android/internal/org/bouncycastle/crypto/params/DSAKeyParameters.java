package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter {
    private com.android.internal.org.bouncycastle.crypto.params.DSAParameters params;

    public DSAKeyParameters(boolean z, com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) {
        super(z);
        this.params = dSAParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DSAParameters getParameters() {
        return this.params;
    }
}
