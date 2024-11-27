package org.apache.commons.math.dfp;

/* loaded from: classes3.dex */
public class DfpDec extends org.apache.commons.math.dfp.Dfp {
    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField) {
        super(dfpField);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, byte b) {
        super(dfpField, b);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, int i) {
        super(dfpField, i);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, long j) {
        super(dfpField, j);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, double d) {
        super(dfpField, d);
        round(0);
    }

    public DfpDec(org.apache.commons.math.dfp.Dfp dfp) {
        super(dfp);
        round(0);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, java.lang.String str) {
        super(dfpField, str);
        round(0);
    }

    protected DfpDec(org.apache.commons.math.dfp.DfpField dfpField, byte b, byte b2) {
        super(dfpField, b, b2);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance() {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField());
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(byte b) {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField(), b);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(int i) {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField(), i);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(long j) {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField(), j);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(double d) {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField(), d);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r0v3, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(org.apache.commons.math.dfp.Dfp dfp) {
        if (getField().getRadixDigits() != dfp.getField().getRadixDigits()) {
            getField().setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, "newInstance", dfp, newInstance);
        }
        return new org.apache.commons.math.dfp.DfpDec(dfp);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(java.lang.String str) {
        return new org.apache.commons.math.dfp.DfpDec((org.apache.commons.math.dfp.DfpField) getField(), str);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp newInstance(byte b, byte b2) {
        return new org.apache.commons.math.dfp.DfpDec(getField(), b, b2);
    }

    protected int getDecimalDigits() {
        return (getRadixDigits() * 4) - 3;
    }

    /* JADX WARN: Type inference failed for: r12v4, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r12v5, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r12v6, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r7v3, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    protected int round(int i) {
        int i2;
        int i3;
        boolean z;
        int i4 = this.mant[this.mant.length - 1];
        if (i4 == 0) {
            return 0;
        }
        int length = this.mant.length * 4;
        int i5 = 1000;
        while (i5 > i4) {
            i5 /= 10;
            length--;
        }
        int decimalDigits = getDecimalDigits();
        int i6 = length - decimalDigits;
        int i7 = i6 / 4;
        int i8 = 1;
        for (int i9 = 0; i9 < i6 % 4; i9++) {
            i8 *= 10;
        }
        int i10 = this.mant[i7];
        if (i8 <= 1 && decimalDigits == (this.mant.length * 4) - 3) {
            return super.round(i);
        }
        if (i8 == 1) {
            int i11 = i7 - 1;
            i2 = (this.mant[i11] / 1000) % 10;
            int[] iArr = this.mant;
            iArr[i11] = iArr[i11] % 1000;
            i3 = i | this.mant[i11];
        } else {
            i2 = ((i10 * 10) / i8) % 10;
            i3 = i | (i10 % (i8 / 10));
        }
        for (int i12 = 0; i12 < i7; i12++) {
            i3 |= this.mant[i12];
            this.mant[i12] = 0;
        }
        int i13 = i10 / i8;
        this.mant[i7] = i13 * i8;
        switch (org.apache.commons.math.dfp.DfpDec.AnonymousClass1.$SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[getField().getRoundingMode().ordinal()]) {
            case 1:
                z = false;
                break;
            case 2:
                if (i2 != 0 || i3 != 0) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 3:
                if (i2 >= 5) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 4:
                if (i2 > 5) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 5:
                if (i2 > 5 || ((i2 == 5 && i3 != 0) || (i2 == 5 && i3 == 0 && (i13 & 1) == 1))) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 6:
                if (i2 > 5 || ((i2 == 5 && i3 != 0) || (i2 == 5 && i3 == 0 && (i13 & 1) == 0))) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 7:
                if (this.sign != 1 || (i2 == 0 && i3 == 0)) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            default:
                if (this.sign != -1 || (i2 == 0 && i3 == 0)) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
        }
        if (z) {
            while (i7 < this.mant.length) {
                int i14 = this.mant[i7] + i8;
                i8 = i14 / 10000;
                this.mant[i7] = i14 % 10000;
                i7++;
            }
            if (i8 != 0) {
                shiftRight();
                this.mant[this.mant.length - 1] = i8;
            }
        }
        if (this.exp < -32767) {
            getField().setIEEEFlagsBits(8);
            return 8;
        }
        if (this.exp > 32768) {
            getField().setIEEEFlagsBits(4);
            return 4;
        }
        if (i2 == 0 && i3 == 0) {
            return 0;
        }
        getField().setIEEEFlagsBits(16);
        return 16;
    }

    /* renamed from: org.apache.commons.math.dfp.DfpDec$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode = new int[org.apache.commons.math.dfp.DfpField.RoundingMode.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_DOWN.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_UP.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_UP.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_DOWN.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_EVEN.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_ODD.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_CEIL.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_FLOOR.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r0v43, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r1v20, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r1v22, types: [org.apache.commons.math.dfp.DfpField] */
    @Override // org.apache.commons.math.dfp.Dfp
    public org.apache.commons.math.dfp.Dfp nextAfter(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        org.apache.commons.math.dfp.Dfp divide;
        org.apache.commons.math.dfp.Dfp subtract;
        if (getField().getRadixDigits() != dfp.getField().getRadixDigits()) {
            getField().setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, "nextAfter", dfp, newInstance);
        }
        if (!lessThan(dfp)) {
            z = false;
        } else {
            z = true;
        }
        if (equals(dfp)) {
            return newInstance(dfp);
        }
        if (lessThan(getZero())) {
            z = !z;
        }
        if (z) {
            org.apache.commons.math.dfp.Dfp copysign = org.apache.commons.math.dfp.Dfp.copysign(power10((log10() - getDecimalDigits()) + 1), this);
            if (equals(getZero())) {
                copysign = power10K(((-32767) - this.mant.length) - 1);
            }
            if (copysign.equals(getZero())) {
                subtract = org.apache.commons.math.dfp.Dfp.copysign(newInstance(getZero()), this);
            } else {
                subtract = add(copysign);
            }
        } else {
            org.apache.commons.math.dfp.Dfp copysign2 = org.apache.commons.math.dfp.Dfp.copysign(power10(log10()), this);
            if (equals(copysign2)) {
                divide = copysign2.divide(power10(getDecimalDigits()));
            } else {
                divide = copysign2.divide(power10(getDecimalDigits() - 1));
            }
            if (equals(getZero())) {
                divide = power10K(((-32767) - this.mant.length) - 1);
            }
            if (divide.equals(getZero())) {
                subtract = org.apache.commons.math.dfp.Dfp.copysign(newInstance(getZero()), this);
            } else {
                subtract = subtract(divide);
            }
        }
        if (subtract.classify() == 1 && classify() != 1) {
            getField().setIEEEFlagsBits(16);
            subtract = dotrap(16, "nextAfter", dfp, subtract);
        }
        if (subtract.equals(getZero()) && !equals(getZero())) {
            getField().setIEEEFlagsBits(16);
            return dotrap(16, "nextAfter", dfp, subtract);
        }
        return subtract;
    }
}
