package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public class EndoPreCompInfo implements com.android.internal.org.bouncycastle.math.ec.PreCompInfo {
    protected com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism endomorphism;
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint mappedPoint;

    public com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism getEndomorphism() {
        return this.endomorphism;
    }

    public void setEndomorphism(com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism) {
        this.endomorphism = eCEndomorphism;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getMappedPoint() {
        return this.mappedPoint;
    }

    public void setMappedPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        this.mappedPoint = eCPoint;
    }
}
