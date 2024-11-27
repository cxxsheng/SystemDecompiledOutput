package com.android.internal.org.bouncycastle.math.field;

/* loaded from: classes4.dex */
class PrimeField implements com.android.internal.org.bouncycastle.math.field.FiniteField {
    protected final java.math.BigInteger characteristic;

    PrimeField(java.math.BigInteger bigInteger) {
        this.characteristic = bigInteger;
    }

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public java.math.BigInteger getCharacteristic() {
        return this.characteristic;
    }

    @Override // com.android.internal.org.bouncycastle.math.field.FiniteField
    public int getDimension() {
        return 1;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.field.PrimeField)) {
            return false;
        }
        return this.characteristic.equals(((com.android.internal.org.bouncycastle.math.field.PrimeField) obj).characteristic);
    }

    public int hashCode() {
        return this.characteristic.hashCode();
    }
}
