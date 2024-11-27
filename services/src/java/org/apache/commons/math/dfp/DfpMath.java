package org.apache.commons.math.dfp;

/* loaded from: classes3.dex */
public class DfpMath {
    private static final java.lang.String POW_TRAP = "pow";

    private DfpMath() {
    }

    protected static org.apache.commons.math.dfp.Dfp[] split(org.apache.commons.math.dfp.DfpField dfpField, java.lang.String str) {
        int length = str.length();
        char[] cArr = new char[length];
        boolean z = true;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= length) {
                i = 0;
                break;
            }
            cArr[i] = str.charAt(i);
            if (cArr[i] >= '1' && cArr[i] <= '9') {
                z = false;
            }
            if (cArr[i] == '.') {
                i2 += (400 - i2) % 4;
                z = false;
            }
            if (i2 == (dfpField.getRadixDigits() / 2) * 4) {
                break;
            }
            if (cArr[i] >= '0' && cArr[i] <= '9' && !z) {
                i2++;
            }
            i++;
        }
        org.apache.commons.math.dfp.Dfp newDfp = dfpField.newDfp(new java.lang.String(cArr, 0, i));
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3] = str.charAt(i3);
            if (cArr[i3] >= '0' && cArr[i3] <= '9' && i3 < i) {
                cArr[i3] = '0';
            }
        }
        return new org.apache.commons.math.dfp.Dfp[]{newDfp, dfpField.newDfp(new java.lang.String(cArr))};
    }

    protected static org.apache.commons.math.dfp.Dfp[] split(org.apache.commons.math.dfp.Dfp dfp) {
        org.apache.commons.math.dfp.Dfp[] dfpArr = new org.apache.commons.math.dfp.Dfp[2];
        org.apache.commons.math.dfp.Dfp multiply = dfp.multiply(dfp.power10K(dfp.getRadixDigits() / 2));
        dfpArr[0] = dfp.add(multiply).subtract(multiply);
        dfpArr[1] = dfp.subtract(dfpArr[0]);
        return dfpArr;
    }

    protected static org.apache.commons.math.dfp.Dfp[] splitMult(org.apache.commons.math.dfp.Dfp[] dfpArr, org.apache.commons.math.dfp.Dfp[] dfpArr2) {
        org.apache.commons.math.dfp.Dfp[] dfpArr3 = {dfpArr[0].multiply(dfpArr2[0]), dfpArr[0].getZero()};
        if (dfpArr3[0].classify() != 1 && !dfpArr3[0].equals(dfpArr3[1])) {
            dfpArr3[1] = dfpArr[0].multiply(dfpArr2[1]).add(dfpArr[1].multiply(dfpArr2[0])).add(dfpArr[1].multiply(dfpArr2[1]));
            return dfpArr3;
        }
        return dfpArr3;
    }

    protected static org.apache.commons.math.dfp.Dfp[] splitDiv(org.apache.commons.math.dfp.Dfp[] dfpArr, org.apache.commons.math.dfp.Dfp[] dfpArr2) {
        org.apache.commons.math.dfp.Dfp[] dfpArr3 = {dfpArr[0].divide(dfpArr2[0]), dfpArr[1].multiply(dfpArr2[0]).subtract(dfpArr[0].multiply(dfpArr2[1]))};
        dfpArr3[1] = dfpArr3[1].divide(dfpArr2[0].multiply(dfpArr2[0]).add(dfpArr2[0].multiply(dfpArr2[1])));
        return dfpArr3;
    }

    protected static org.apache.commons.math.dfp.Dfp splitPow(org.apache.commons.math.dfp.Dfp[] dfpArr, int i) {
        boolean z;
        org.apache.commons.math.dfp.Dfp[] dfpArr2 = new org.apache.commons.math.dfp.Dfp[2];
        org.apache.commons.math.dfp.Dfp[] dfpArr3 = {dfpArr[0].getOne(), dfpArr[0].getZero()};
        if (i == 0) {
            return dfpArr3[0].add(dfpArr3[1]);
        }
        if (i >= 0) {
            z = false;
        } else {
            i = -i;
            z = true;
        }
        do {
            dfpArr2[0] = new org.apache.commons.math.dfp.Dfp(dfpArr[0]);
            dfpArr2[1] = new org.apache.commons.math.dfp.Dfp(dfpArr[1]);
            int i2 = 1;
            while (true) {
                int i3 = i2 * 2;
                if (i3 > i) {
                    break;
                }
                dfpArr2 = splitMult(dfpArr2, dfpArr2);
                i2 = i3;
            }
            i -= i2;
            dfpArr3 = splitMult(dfpArr3, dfpArr2);
        } while (i >= 1);
        dfpArr3[0] = dfpArr3[0].add(dfpArr3[1]);
        if (z) {
            dfpArr3[0] = dfpArr[0].getOne().divide(dfpArr3[0]);
        }
        return dfpArr3[0];
    }

    public static org.apache.commons.math.dfp.Dfp pow(org.apache.commons.math.dfp.Dfp dfp, int i) {
        boolean z;
        org.apache.commons.math.dfp.Dfp dfp2;
        org.apache.commons.math.dfp.Dfp one = dfp.getOne();
        if (i == 0) {
            return one;
        }
        if (i >= 0) {
            z = false;
        } else {
            i = -i;
            z = true;
        }
        do {
            org.apache.commons.math.dfp.Dfp dfp3 = new org.apache.commons.math.dfp.Dfp(dfp);
            int i2 = 1;
            while (true) {
                dfp2 = new org.apache.commons.math.dfp.Dfp(dfp3);
                dfp3 = dfp3.multiply(dfp3);
                int i3 = i2 * 2;
                if (i <= i3) {
                    break;
                }
                i2 = i3;
            }
            i -= i2;
            one = one.multiply(dfp2);
        } while (i >= 1);
        if (z) {
            one = dfp.getOne().divide(one);
        }
        return dfp.newInstance(one);
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp exp(org.apache.commons.math.dfp.Dfp dfp) {
        org.apache.commons.math.dfp.Dfp rint = dfp.rint();
        org.apache.commons.math.dfp.Dfp subtract = dfp.subtract(rint);
        int intValue = rint.intValue();
        if (intValue > 2147483646) {
            return dfp.newInstance((byte) 1, (byte) 1);
        }
        if (intValue < -2147483646) {
            return dfp.newInstance();
        }
        return splitPow(dfp.getField().getESplit(), intValue).multiply(expInternal(subtract));
    }

    protected static org.apache.commons.math.dfp.Dfp expInternal(org.apache.commons.math.dfp.Dfp dfp) {
        org.apache.commons.math.dfp.Dfp one = dfp.getOne();
        org.apache.commons.math.dfp.Dfp one2 = dfp.getOne();
        org.apache.commons.math.dfp.Dfp one3 = dfp.getOne();
        org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(one);
        for (int i = 1; i < 90; i++) {
            one2 = one2.multiply(dfp);
            one3 = one3.divide(i);
            one = one.add(one2.multiply(one3));
            if (one.equals(dfp2)) {
                break;
            }
            dfp2 = new org.apache.commons.math.dfp.Dfp(one);
        }
        return one;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r2v6, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r5v7, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp log(org.apache.commons.math.dfp.Dfp dfp) {
        if (dfp.equals(dfp.getZero()) || dfp.lessThan(dfp.getZero()) || dfp.isNaN()) {
            dfp.getField().setIEEEFlagsBits(1);
            return dfp.dotrap(1, "ln", dfp, dfp.newInstance((byte) 1, (byte) 3));
        }
        if (dfp.classify() == 1) {
            return dfp;
        }
        org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(dfp);
        int log10K = dfp2.log10K();
        org.apache.commons.math.dfp.Dfp divide = dfp2.divide(pow(dfp.newInstance(10000), log10K));
        int intValue = divide.floor().intValue();
        int i = 0;
        while (intValue > 2) {
            intValue >>= 1;
            i++;
        }
        org.apache.commons.math.dfp.Dfp[] split = split(divide);
        org.apache.commons.math.dfp.Dfp[] dfpArr = new org.apache.commons.math.dfp.Dfp[2];
        dfpArr[0] = pow(dfp.getTwo(), i);
        split[0] = split[0].divide(dfpArr[0]);
        split[1] = split[1].divide(dfpArr[0]);
        dfpArr[0] = dfp.newInstance("1.33333");
        while (split[0].add(split[1]).greaterThan(dfpArr[0])) {
            split[0] = split[0].divide(2);
            split[1] = split[1].divide(2);
            i++;
        }
        org.apache.commons.math.dfp.Dfp[] logInternal = logInternal(split);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i2 = log10K * 4;
        sb.append(i + i2);
        split[0] = dfp.newInstance(sb.toString());
        split[1] = dfp.getZero();
        org.apache.commons.math.dfp.Dfp[] splitMult = splitMult(dfp.getField().getLn2Split(), split);
        logInternal[0] = logInternal[0].add(splitMult[0]);
        logInternal[1] = logInternal[1].add(splitMult[1]);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(i2);
        split[0] = dfp.newInstance(sb2.toString());
        split[1] = dfp.getZero();
        org.apache.commons.math.dfp.Dfp[] splitMult2 = splitMult(dfp.getField().getLn5Split(), split);
        logInternal[0] = logInternal[0].add(splitMult2[0]);
        logInternal[1] = logInternal[1].add(splitMult2[1]);
        return dfp.newInstance(logInternal[0].add(logInternal[1]));
    }

    protected static org.apache.commons.math.dfp.Dfp[] logInternal(org.apache.commons.math.dfp.Dfp[] dfpArr) {
        int i = 1;
        org.apache.commons.math.dfp.Dfp add = dfpArr[0].divide(4).add(dfpArr[1].divide(4));
        org.apache.commons.math.dfp.Dfp divide = add.add(dfpArr[0].newInstance("-0.25")).divide(add.add(dfpArr[0].newInstance("0.25")));
        org.apache.commons.math.dfp.Dfp dfp = new org.apache.commons.math.dfp.Dfp(divide);
        org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(divide);
        org.apache.commons.math.dfp.Dfp dfp3 = new org.apache.commons.math.dfp.Dfp(dfp);
        for (int i2 = 0; i2 < 10000; i2++) {
            dfp2 = dfp2.multiply(divide).multiply(divide);
            i += 2;
            dfp = dfp.add(dfp2.divide(i));
            if (dfp.equals(dfp3)) {
                break;
            }
            dfp3 = new org.apache.commons.math.dfp.Dfp(dfp);
        }
        return split(dfp.multiply(dfpArr[0].getTwo()));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r10v1, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r10v13, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r10v24, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r10v25, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r10v5, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r3v3, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r4v1, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp pow(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2) {
        boolean z;
        org.apache.commons.math.dfp.Dfp exp;
        if (dfp.getField().getRadixDigits() != dfp2.getField().getRadixDigits()) {
            dfp.getField().setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = dfp.newInstance(dfp.getZero());
            newInstance.nans = (byte) 3;
            return dfp.dotrap(1, POW_TRAP, dfp, newInstance);
        }
        org.apache.commons.math.dfp.Dfp zero = dfp.getZero();
        org.apache.commons.math.dfp.Dfp one = dfp.getOne();
        org.apache.commons.math.dfp.Dfp two = dfp.getTwo();
        if (dfp2.equals(zero)) {
            return dfp.newInstance(one);
        }
        if (dfp2.equals(one)) {
            if (dfp.isNaN()) {
                dfp.getField().setIEEEFlagsBits(1);
                return dfp.dotrap(1, POW_TRAP, dfp, dfp);
            }
            return dfp;
        }
        if (dfp.isNaN() || dfp2.isNaN()) {
            dfp.getField().setIEEEFlagsBits(1);
            return dfp.dotrap(1, POW_TRAP, dfp, dfp.newInstance((byte) 1, (byte) 3));
        }
        if (dfp.equals(zero)) {
            if (org.apache.commons.math.dfp.Dfp.copysign(one, dfp).greaterThan(zero)) {
                if (dfp2.greaterThan(zero)) {
                    return dfp.newInstance(zero);
                }
                return dfp.newInstance(dfp.newInstance((byte) 1, (byte) 1));
            }
            if (dfp2.classify() == 0 && dfp2.rint().equals(dfp2) && !dfp2.remainder(two).equals(zero)) {
                if (dfp2.greaterThan(zero)) {
                    return dfp.newInstance(zero.negate());
                }
                return dfp.newInstance(dfp.newInstance((byte) -1, (byte) 1));
            }
            if (dfp2.greaterThan(zero)) {
                return dfp.newInstance(zero);
            }
            return dfp.newInstance(dfp.newInstance((byte) 1, (byte) 1));
        }
        if (!dfp.lessThan(zero)) {
            z = false;
        } else {
            dfp = dfp.negate();
            z = true;
        }
        if (dfp.greaterThan(one) && dfp2.classify() == 1) {
            if (dfp2.greaterThan(zero)) {
                return dfp2;
            }
            return dfp.newInstance(zero);
        }
        if (dfp.lessThan(one) && dfp2.classify() == 1) {
            if (dfp2.greaterThan(zero)) {
                return dfp.newInstance(zero);
            }
            return dfp.newInstance(org.apache.commons.math.dfp.Dfp.copysign(dfp2, one));
        }
        if (dfp.equals(one) && dfp2.classify() == 1) {
            dfp.getField().setIEEEFlagsBits(1);
            return dfp.dotrap(1, POW_TRAP, dfp, dfp.newInstance((byte) 1, (byte) 3));
        }
        if (dfp.classify() == 1) {
            if (z) {
                if (dfp2.classify() == 0 && dfp2.rint().equals(dfp2) && !dfp2.remainder(two).equals(zero)) {
                    if (dfp2.greaterThan(zero)) {
                        return dfp.newInstance(dfp.newInstance((byte) -1, (byte) 1));
                    }
                    return dfp.newInstance(zero.negate());
                }
                if (dfp2.greaterThan(zero)) {
                    return dfp.newInstance(dfp.newInstance((byte) 1, (byte) 1));
                }
                return dfp.newInstance(zero);
            }
            if (dfp2.greaterThan(zero)) {
                return dfp;
            }
            return dfp.newInstance(zero);
        }
        if (z && !dfp2.rint().equals(dfp2)) {
            dfp.getField().setIEEEFlagsBits(1);
            return dfp.dotrap(1, POW_TRAP, dfp, dfp.newInstance((byte) 1, (byte) 3));
        }
        if (dfp2.lessThan(dfp.newInstance(100000000)) && dfp2.greaterThan(dfp.newInstance(-100000000))) {
            org.apache.commons.math.dfp.Dfp rint = dfp2.rint();
            int intValue = rint.intValue();
            org.apache.commons.math.dfp.Dfp subtract = dfp2.subtract(rint);
            if (subtract.unequal(zero)) {
                org.apache.commons.math.dfp.Dfp multiply = subtract.multiply(log(dfp));
                org.apache.commons.math.dfp.Dfp rint2 = multiply.divide(dfp.getField().getLn2()).rint();
                exp = splitPow(split(dfp), intValue).multiply(pow(two, rint2.intValue())).multiply(exp(multiply.subtract(rint2.multiply(dfp.getField().getLn2()))));
            } else {
                exp = splitPow(split(dfp), intValue);
            }
        } else {
            exp = exp(log(dfp).multiply(dfp2));
        }
        if (z && dfp2.rint().equals(dfp2) && !dfp2.remainder(two).equals(zero)) {
            exp = exp.negate();
        }
        return dfp.newInstance(exp);
    }

    protected static org.apache.commons.math.dfp.Dfp sinInternal(org.apache.commons.math.dfp.Dfp[] dfpArr) {
        org.apache.commons.math.dfp.Dfp add = dfpArr[0].add(dfpArr[1]);
        org.apache.commons.math.dfp.Dfp multiply = add.multiply(add);
        org.apache.commons.math.dfp.Dfp one = dfpArr[0].getOne();
        org.apache.commons.math.dfp.Dfp dfp = new org.apache.commons.math.dfp.Dfp(add);
        org.apache.commons.math.dfp.Dfp dfp2 = add;
        for (int i = 3; i < 90; i += 2) {
            add = add.multiply(multiply).negate();
            one = one.divide((i - 1) * i);
            dfp2 = dfp2.add(add.multiply(one));
            if (dfp2.equals(dfp)) {
                break;
            }
            dfp = new org.apache.commons.math.dfp.Dfp(dfp2);
        }
        return dfp2;
    }

    protected static org.apache.commons.math.dfp.Dfp cosInternal(org.apache.commons.math.dfp.Dfp[] dfpArr) {
        org.apache.commons.math.dfp.Dfp one = dfpArr[0].getOne();
        org.apache.commons.math.dfp.Dfp add = dfpArr[0].add(dfpArr[1]);
        org.apache.commons.math.dfp.Dfp multiply = add.multiply(add);
        org.apache.commons.math.dfp.Dfp dfp = new org.apache.commons.math.dfp.Dfp(one);
        org.apache.commons.math.dfp.Dfp dfp2 = one;
        org.apache.commons.math.dfp.Dfp dfp3 = dfp2;
        for (int i = 2; i < 90; i += 2) {
            one = one.multiply(multiply).negate();
            dfp3 = dfp3.divide((i - 1) * i);
            dfp2 = dfp2.add(one.multiply(dfp3));
            if (dfp2.equals(dfp)) {
                break;
            }
            dfp = new org.apache.commons.math.dfp.Dfp(dfp2);
        }
        return dfp2;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp sin(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        org.apache.commons.math.dfp.Dfp cosInternal;
        org.apache.commons.math.dfp.Dfp pi = dfp.getField().getPi();
        org.apache.commons.math.dfp.Dfp zero = dfp.getField().getZero();
        org.apache.commons.math.dfp.Dfp remainder = dfp.remainder(pi.multiply(2));
        if (!remainder.lessThan(zero)) {
            z = false;
        } else {
            remainder = remainder.negate();
            z = true;
        }
        if (remainder.greaterThan(pi.divide(2))) {
            remainder = pi.subtract(remainder);
        }
        if (remainder.lessThan(pi.divide(4))) {
            cosInternal = sinInternal(split(remainder));
        } else {
            org.apache.commons.math.dfp.Dfp[] piSplit = dfp.getField().getPiSplit();
            cosInternal = cosInternal(new org.apache.commons.math.dfp.Dfp[]{piSplit[0].divide(2).subtract(remainder), piSplit[1].divide(2)});
        }
        if (z) {
            cosInternal = cosInternal.negate();
        }
        return dfp.newInstance(cosInternal);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r0v4, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp cos(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        org.apache.commons.math.dfp.Dfp sinInternal;
        org.apache.commons.math.dfp.Dfp pi = dfp.getField().getPi();
        org.apache.commons.math.dfp.Dfp zero = dfp.getField().getZero();
        org.apache.commons.math.dfp.Dfp remainder = dfp.remainder(pi.multiply(2));
        if (remainder.lessThan(zero)) {
            remainder = remainder.negate();
        }
        if (!remainder.greaterThan(pi.divide(2))) {
            z = false;
        } else {
            remainder = pi.subtract(remainder);
            z = true;
        }
        if (remainder.lessThan(pi.divide(4))) {
            sinInternal = cosInternal(new org.apache.commons.math.dfp.Dfp[]{remainder, zero});
        } else {
            org.apache.commons.math.dfp.Dfp[] piSplit = dfp.getField().getPiSplit();
            sinInternal = sinInternal(new org.apache.commons.math.dfp.Dfp[]{piSplit[0].divide(2).subtract(remainder), piSplit[1].divide(2)});
        }
        if (z) {
            sinInternal = sinInternal.negate();
        }
        return dfp.newInstance(sinInternal);
    }

    public static org.apache.commons.math.dfp.Dfp tan(org.apache.commons.math.dfp.Dfp dfp) {
        return sin(dfp).divide(cos(dfp));
    }

    protected static org.apache.commons.math.dfp.Dfp atanInternal(org.apache.commons.math.dfp.Dfp dfp) {
        org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(dfp);
        org.apache.commons.math.dfp.Dfp dfp3 = new org.apache.commons.math.dfp.Dfp(dfp2);
        org.apache.commons.math.dfp.Dfp dfp4 = new org.apache.commons.math.dfp.Dfp(dfp2);
        for (int i = 3; i < 90; i += 2) {
            dfp3 = dfp3.multiply(dfp).multiply(dfp).negate();
            dfp2 = dfp2.add(dfp3.divide(i));
            if (dfp2.equals(dfp4)) {
                break;
            }
            dfp4 = new org.apache.commons.math.dfp.Dfp(dfp2);
        }
        return dfp2;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [org.apache.commons.math.dfp.DfpField] */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp atan(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        boolean z2;
        boolean z3;
        org.apache.commons.math.dfp.Dfp zero = dfp.getField().getZero();
        org.apache.commons.math.dfp.Dfp one = dfp.getField().getOne();
        org.apache.commons.math.dfp.Dfp[] sqr2Split = dfp.getField().getSqr2Split();
        org.apache.commons.math.dfp.Dfp[] piSplit = dfp.getField().getPiSplit();
        org.apache.commons.math.dfp.Dfp add = sqr2Split[0].subtract(one).add(sqr2Split[1]);
        org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(dfp);
        if (!dfp2.lessThan(zero)) {
            z = false;
        } else {
            dfp2 = dfp2.negate();
            z = true;
        }
        if (!dfp2.greaterThan(one)) {
            z2 = false;
        } else {
            dfp2 = one.divide(dfp2);
            z2 = true;
        }
        if (!dfp2.greaterThan(add)) {
            z3 = false;
        } else {
            org.apache.commons.math.dfp.Dfp[] dfpArr = {sqr2Split[0].subtract(one), sqr2Split[1]};
            org.apache.commons.math.dfp.Dfp[] split = split(dfp2);
            org.apache.commons.math.dfp.Dfp[] splitMult = splitMult(split, dfpArr);
            splitMult[0] = splitMult[0].add(one);
            split[0] = split[0].subtract(dfpArr[0]);
            split[1] = split[1].subtract(dfpArr[1]);
            org.apache.commons.math.dfp.Dfp[] splitDiv = splitDiv(split, splitMult);
            dfp2 = splitDiv[0].add(splitDiv[1]);
            z3 = true;
        }
        org.apache.commons.math.dfp.Dfp atanInternal = atanInternal(dfp2);
        if (z3) {
            atanInternal = atanInternal.add(piSplit[0].divide(8)).add(piSplit[1].divide(8));
        }
        if (z2) {
            atanInternal = piSplit[0].divide(2).subtract(atanInternal).add(piSplit[1].divide(2));
        }
        if (z) {
            atanInternal = atanInternal.negate();
        }
        return dfp.newInstance(atanInternal);
    }

    public static org.apache.commons.math.dfp.Dfp asin(org.apache.commons.math.dfp.Dfp dfp) {
        return atan(dfp.divide(dfp.getOne().subtract(dfp.multiply(dfp)).sqrt()));
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp acos(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        if (!dfp.lessThan(dfp.getZero())) {
            z = false;
        } else {
            z = true;
        }
        org.apache.commons.math.dfp.Dfp copysign = org.apache.commons.math.dfp.Dfp.copysign(dfp, dfp.getOne());
        org.apache.commons.math.dfp.Dfp atan = atan(copysign.getOne().subtract(copysign.multiply(copysign)).sqrt().divide(copysign));
        if (z) {
            atan = copysign.getField().getPi().subtract(atan);
        }
        return copysign.newInstance(atan);
    }
}
