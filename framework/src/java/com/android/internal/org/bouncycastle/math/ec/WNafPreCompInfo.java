package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class WNafPreCompInfo implements com.android.internal.org.bouncycastle.math.ec.PreCompInfo {
    volatile int promotionCountdown = 4;
    protected int confWidth = -1;
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint[] preComp = null;
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint[] preCompNeg = null;
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint twice = null;
    protected int width = -1;

    int decrementPromotionCountdown() {
        int i = this.promotionCountdown;
        if (i > 0) {
            int i2 = i - 1;
            this.promotionCountdown = i2;
            return i2;
        }
        return i;
    }

    int getPromotionCountdown() {
        return this.promotionCountdown;
    }

    void setPromotionCountdown(int i) {
        this.promotionCountdown = i;
    }

    public boolean isPromoted() {
        return this.promotionCountdown <= 0;
    }

    public int getConfWidth() {
        return this.confWidth;
    }

    public void setConfWidth(int i) {
        this.confWidth = i;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint[] getPreComp() {
        return this.preComp;
    }

    public void setPreComp(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr) {
        this.preComp = eCPointArr;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint[] getPreCompNeg() {
        return this.preCompNeg;
    }

    public void setPreCompNeg(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr) {
        this.preCompNeg = eCPointArr;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getTwice() {
        return this.twice;
    }

    public void setTwice(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        this.twice = eCPoint;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }
}
