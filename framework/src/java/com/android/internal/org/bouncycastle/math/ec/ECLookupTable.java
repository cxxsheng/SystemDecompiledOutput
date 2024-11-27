package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public interface ECLookupTable {
    int getSize();

    com.android.internal.org.bouncycastle.math.ec.ECPoint lookup(int i);

    com.android.internal.org.bouncycastle.math.ec.ECPoint lookupVar(int i);
}
