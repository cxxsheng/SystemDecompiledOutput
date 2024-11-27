package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter {
    private com.android.internal.org.bouncycastle.crypto.params.DHParameters params;

    protected DHKeyParameters(boolean z, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        super(z);
        this.params = dHParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DHParameters getParameters() {
        return this.params;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters dHKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters) obj;
        if (this.params == null) {
            return dHKeyParameters.getParameters() == null;
        }
        return this.params.equals(dHKeyParameters.getParameters());
    }

    public int hashCode() {
        int i = !isPrivate() ? 1 : 0;
        if (this.params != null) {
            return i ^ this.params.hashCode();
        }
        return i;
    }
}
