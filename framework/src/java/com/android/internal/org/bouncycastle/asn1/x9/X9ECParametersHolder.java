package com.android.internal.org.bouncycastle.asn1.x9;

/* loaded from: classes4.dex */
public abstract class X9ECParametersHolder {
    private com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters params;

    protected abstract com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters createParameters();

    public synchronized com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters getParameters() {
        if (this.params == null) {
            this.params = createParameters();
        }
        return this.params;
    }
}
