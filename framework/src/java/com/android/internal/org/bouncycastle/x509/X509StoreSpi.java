package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public abstract class X509StoreSpi {
    public abstract java.util.Collection engineGetMatches(com.android.internal.org.bouncycastle.util.Selector selector);

    public abstract void engineInit(com.android.internal.org.bouncycastle.x509.X509StoreParameters x509StoreParameters);
}
