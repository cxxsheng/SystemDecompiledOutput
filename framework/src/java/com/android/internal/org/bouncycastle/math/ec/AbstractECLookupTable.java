package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class AbstractECLookupTable implements com.android.internal.org.bouncycastle.math.ec.ECLookupTable {
    @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
    public com.android.internal.org.bouncycastle.math.ec.ECPoint lookupVar(int i) {
        return lookup(i);
    }
}
