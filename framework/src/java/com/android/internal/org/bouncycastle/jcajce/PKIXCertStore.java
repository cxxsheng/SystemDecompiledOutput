package com.android.internal.org.bouncycastle.jcajce;

/* loaded from: classes4.dex */
public interface PKIXCertStore<T extends java.security.cert.Certificate> extends com.android.internal.org.bouncycastle.util.Store<T> {
    @Override // com.android.internal.org.bouncycastle.util.Store
    java.util.Collection<T> getMatches(com.android.internal.org.bouncycastle.util.Selector<T> selector) throws com.android.internal.org.bouncycastle.util.StoreException;
}
