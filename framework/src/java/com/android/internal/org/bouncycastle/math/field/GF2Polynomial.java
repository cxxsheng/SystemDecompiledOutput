package com.android.internal.org.bouncycastle.math.field;

/* loaded from: classes4.dex */
class GF2Polynomial implements com.android.internal.org.bouncycastle.math.field.Polynomial {
    protected final int[] exponents;

    GF2Polynomial(int[] iArr) {
        this.exponents = com.android.internal.org.bouncycastle.util.Arrays.clone(iArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.field.Polynomial
    public int getDegree() {
        return this.exponents[this.exponents.length - 1];
    }

    @Override // com.android.internal.org.bouncycastle.math.field.Polynomial
    public int[] getExponentsPresent() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.exponents);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.field.GF2Polynomial)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.exponents, ((com.android.internal.org.bouncycastle.math.field.GF2Polynomial) obj).exponents);
    }

    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.exponents);
    }
}
