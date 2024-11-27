package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class ScaleXPointMap implements com.android.internal.org.bouncycastle.math.ec.ECPointMap {
    protected final com.android.internal.org.bouncycastle.math.ec.ECFieldElement scale;

    public ScaleXPointMap(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        this.scale = eCFieldElement;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPointMap
    public com.android.internal.org.bouncycastle.math.ec.ECPoint map(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return eCPoint.scaleX(this.scale);
    }
}
