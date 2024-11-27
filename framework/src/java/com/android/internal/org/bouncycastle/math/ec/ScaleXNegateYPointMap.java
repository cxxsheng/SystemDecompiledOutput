package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class ScaleXNegateYPointMap implements com.android.internal.org.bouncycastle.math.ec.ECPointMap {
    protected final com.android.internal.org.bouncycastle.math.ec.ECFieldElement scale;

    public ScaleXNegateYPointMap(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        this.scale = eCFieldElement;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPointMap
    public com.android.internal.org.bouncycastle.math.ec.ECPoint map(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return eCPoint.scaleXNegateY(this.scale);
    }
}
