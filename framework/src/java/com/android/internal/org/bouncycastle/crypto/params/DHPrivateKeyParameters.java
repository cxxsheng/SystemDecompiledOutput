package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHPrivateKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters {
    private java.math.BigInteger x;

    public DHPrivateKeyParameters(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        super(true, dHParameters);
        this.x = bigInteger;
    }

    public java.math.BigInteger getX() {
        return this.x;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return this.x.hashCode() ^ super.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(java.lang.Object obj) {
        return (obj instanceof com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters) && ((com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters) obj).getX().equals(this.x) && super.equals(obj);
    }
}
