package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public interface Store<T> {
    java.util.Collection<T> getMatches(com.android.internal.org.bouncycastle.util.Selector<T> selector) throws com.android.internal.org.bouncycastle.util.StoreException;
}
