package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class FixedPointPreCompInfo implements com.android.internal.org.bouncycastle.math.ec.PreCompInfo {
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint offset = null;
    protected com.android.internal.org.bouncycastle.math.ec.ECLookupTable lookupTable = null;
    protected int width = -1;

    public com.android.internal.org.bouncycastle.math.ec.ECLookupTable getLookupTable() {
        return this.lookupTable;
    }

    public void setLookupTable(com.android.internal.org.bouncycastle.math.ec.ECLookupTable eCLookupTable) {
        this.lookupTable = eCLookupTable;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getOffset() {
        return this.offset;
    }

    public void setOffset(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        this.offset = eCPoint;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }
}
