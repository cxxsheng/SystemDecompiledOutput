package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class AbstractECMultiplier implements com.android.internal.org.bouncycastle.math.ec.ECMultiplier {
    protected abstract com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger);

    @Override // com.android.internal.org.bouncycastle.math.ec.ECMultiplier
    public com.android.internal.org.bouncycastle.math.ec.ECPoint multiply(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        int signum = bigInteger.signum();
        if (signum == 0 || eCPoint.isInfinity()) {
            return eCPoint.getCurve().getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive = multiplyPositive(eCPoint, bigInteger.abs());
        if (signum <= 0) {
            multiplyPositive = multiplyPositive.negate();
        }
        return checkResult(multiplyPositive);
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECPoint checkResult(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.implCheckResult(eCPoint);
    }
}
