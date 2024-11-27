package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class WTauNafPreCompInfo implements com.android.internal.org.bouncycastle.math.ec.PreCompInfo {
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] preComp = null;

    public com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] getPreComp() {
        return this.preComp;
    }

    public void setPreComp(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] abstractF2mArr) {
        this.preComp = abstractF2mArr;
    }
}
