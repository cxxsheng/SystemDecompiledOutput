package org.apache.commons.math.dfp;

/* loaded from: classes3.dex */
public class DfpField implements org.apache.commons.math.Field<org.apache.commons.math.dfp.Dfp> {
    public static final int FLAG_DIV_ZERO = 2;
    public static final int FLAG_INEXACT = 16;
    public static final int FLAG_INVALID = 1;
    public static final int FLAG_OVERFLOW = 4;
    public static final int FLAG_UNDERFLOW = 8;
    private static java.lang.String eString;
    private static java.lang.String ln10String;
    private static java.lang.String ln2String;
    private static java.lang.String ln5String;
    private static java.lang.String piString;
    private static java.lang.String sqr2ReciprocalString;
    private static java.lang.String sqr2String;
    private static java.lang.String sqr3ReciprocalString;
    private static java.lang.String sqr3String;
    private final org.apache.commons.math.dfp.Dfp e;
    private final org.apache.commons.math.dfp.Dfp[] eSplit;
    private int ieeeFlags;
    private final org.apache.commons.math.dfp.Dfp ln10;
    private final org.apache.commons.math.dfp.Dfp ln2;
    private final org.apache.commons.math.dfp.Dfp[] ln2Split;
    private final org.apache.commons.math.dfp.Dfp ln5;
    private final org.apache.commons.math.dfp.Dfp[] ln5Split;
    private final org.apache.commons.math.dfp.Dfp one;
    private final org.apache.commons.math.dfp.Dfp pi;
    private final org.apache.commons.math.dfp.Dfp[] piSplit;
    private org.apache.commons.math.dfp.DfpField.RoundingMode rMode;
    private final int radixDigits;
    private final org.apache.commons.math.dfp.Dfp sqr2;
    private final org.apache.commons.math.dfp.Dfp sqr2Reciprocal;
    private final org.apache.commons.math.dfp.Dfp[] sqr2Split;
    private final org.apache.commons.math.dfp.Dfp sqr3;
    private final org.apache.commons.math.dfp.Dfp sqr3Reciprocal;
    private final org.apache.commons.math.dfp.Dfp two;
    private final org.apache.commons.math.dfp.Dfp zero;

    public enum RoundingMode {
        ROUND_DOWN,
        ROUND_UP,
        ROUND_HALF_UP,
        ROUND_HALF_DOWN,
        ROUND_HALF_EVEN,
        ROUND_HALF_ODD,
        ROUND_CEIL,
        ROUND_FLOOR
    }

    public DfpField(int i) {
        this(i, true);
    }

    private DfpField(int i, boolean z) {
        this.radixDigits = i >= 13 ? (i + 3) / 4 : 4;
        this.rMode = org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_EVEN;
        this.ieeeFlags = 0;
        this.zero = new org.apache.commons.math.dfp.Dfp(this, 0);
        this.one = new org.apache.commons.math.dfp.Dfp(this, 1);
        this.two = new org.apache.commons.math.dfp.Dfp(this, 2);
        if (z) {
            synchronized (org.apache.commons.math.dfp.DfpField.class) {
                computeStringConstants(i < 67 ? 200 : i * 3);
                this.sqr2 = new org.apache.commons.math.dfp.Dfp(this, sqr2String);
                this.sqr2Split = split(sqr2String);
                this.sqr2Reciprocal = new org.apache.commons.math.dfp.Dfp(this, sqr2ReciprocalString);
                this.sqr3 = new org.apache.commons.math.dfp.Dfp(this, sqr3String);
                this.sqr3Reciprocal = new org.apache.commons.math.dfp.Dfp(this, sqr3ReciprocalString);
                this.pi = new org.apache.commons.math.dfp.Dfp(this, piString);
                this.piSplit = split(piString);
                this.e = new org.apache.commons.math.dfp.Dfp(this, eString);
                this.eSplit = split(eString);
                this.ln2 = new org.apache.commons.math.dfp.Dfp(this, ln2String);
                this.ln2Split = split(ln2String);
                this.ln5 = new org.apache.commons.math.dfp.Dfp(this, ln5String);
                this.ln5Split = split(ln5String);
                this.ln10 = new org.apache.commons.math.dfp.Dfp(this, ln10String);
            }
            return;
        }
        this.sqr2 = null;
        this.sqr2Split = null;
        this.sqr2Reciprocal = null;
        this.sqr3 = null;
        this.sqr3Reciprocal = null;
        this.pi = null;
        this.piSplit = null;
        this.e = null;
        this.eSplit = null;
        this.ln2 = null;
        this.ln2Split = null;
        this.ln5 = null;
        this.ln5Split = null;
        this.ln10 = null;
    }

    public int getRadixDigits() {
        return this.radixDigits;
    }

    public void setRoundingMode(org.apache.commons.math.dfp.DfpField.RoundingMode roundingMode) {
        this.rMode = roundingMode;
    }

    public org.apache.commons.math.dfp.DfpField.RoundingMode getRoundingMode() {
        return this.rMode;
    }

    public int getIEEEFlags() {
        return this.ieeeFlags;
    }

    public void clearIEEEFlags() {
        this.ieeeFlags = 0;
    }

    public void setIEEEFlags(int i) {
        this.ieeeFlags = i & 31;
    }

    public void setIEEEFlagsBits(int i) {
        this.ieeeFlags = (i & 31) | this.ieeeFlags;
    }

    public org.apache.commons.math.dfp.Dfp newDfp() {
        return new org.apache.commons.math.dfp.Dfp(this);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(byte b) {
        return new org.apache.commons.math.dfp.Dfp(this, b);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(int i) {
        return new org.apache.commons.math.dfp.Dfp(this, i);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(long j) {
        return new org.apache.commons.math.dfp.Dfp(this, j);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(double d) {
        return new org.apache.commons.math.dfp.Dfp(this, d);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(org.apache.commons.math.dfp.Dfp dfp) {
        return new org.apache.commons.math.dfp.Dfp(dfp);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(java.lang.String str) {
        return new org.apache.commons.math.dfp.Dfp(this, str);
    }

    public org.apache.commons.math.dfp.Dfp newDfp(byte b, byte b2) {
        return new org.apache.commons.math.dfp.Dfp(this, b, b2);
    }

    @Override // org.apache.commons.math.Field
    public org.apache.commons.math.dfp.Dfp getZero() {
        return this.zero;
    }

    @Override // org.apache.commons.math.Field
    public org.apache.commons.math.dfp.Dfp getOne() {
        return this.one;
    }

    public org.apache.commons.math.dfp.Dfp getTwo() {
        return this.two;
    }

    public org.apache.commons.math.dfp.Dfp getSqr2() {
        return this.sqr2;
    }

    public org.apache.commons.math.dfp.Dfp[] getSqr2Split() {
        return (org.apache.commons.math.dfp.Dfp[]) this.sqr2Split.clone();
    }

    public org.apache.commons.math.dfp.Dfp getSqr2Reciprocal() {
        return this.sqr2Reciprocal;
    }

    public org.apache.commons.math.dfp.Dfp getSqr3() {
        return this.sqr3;
    }

    public org.apache.commons.math.dfp.Dfp getSqr3Reciprocal() {
        return this.sqr3Reciprocal;
    }

    public org.apache.commons.math.dfp.Dfp getPi() {
        return this.pi;
    }

    public org.apache.commons.math.dfp.Dfp[] getPiSplit() {
        return (org.apache.commons.math.dfp.Dfp[]) this.piSplit.clone();
    }

    public org.apache.commons.math.dfp.Dfp getE() {
        return this.e;
    }

    public org.apache.commons.math.dfp.Dfp[] getESplit() {
        return (org.apache.commons.math.dfp.Dfp[]) this.eSplit.clone();
    }

    public org.apache.commons.math.dfp.Dfp getLn2() {
        return this.ln2;
    }

    public org.apache.commons.math.dfp.Dfp[] getLn2Split() {
        return (org.apache.commons.math.dfp.Dfp[]) this.ln2Split.clone();
    }

    public org.apache.commons.math.dfp.Dfp getLn5() {
        return this.ln5;
    }

    public org.apache.commons.math.dfp.Dfp[] getLn5Split() {
        return (org.apache.commons.math.dfp.Dfp[]) this.ln5Split.clone();
    }

    public org.apache.commons.math.dfp.Dfp getLn10() {
        return this.ln10;
    }

    private org.apache.commons.math.dfp.Dfp[] split(java.lang.String str) {
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
            if (i2 == (this.radixDigits / 2) * 4) {
                break;
            }
            if (cArr[i] >= '0' && cArr[i] <= '9' && !z) {
                i2++;
            }
            i++;
        }
        org.apache.commons.math.dfp.Dfp dfp = new org.apache.commons.math.dfp.Dfp(this, new java.lang.String(cArr, 0, i));
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3] = str.charAt(i3);
            if (cArr[i3] >= '0' && cArr[i3] <= '9' && i3 < i) {
                cArr[i3] = '0';
            }
        }
        return new org.apache.commons.math.dfp.Dfp[]{dfp, new org.apache.commons.math.dfp.Dfp(this, new java.lang.String(cArr))};
    }

    private static void computeStringConstants(int i) {
        if (sqr2String == null || sqr2String.length() < i - 3) {
            org.apache.commons.math.dfp.DfpField dfpField = new org.apache.commons.math.dfp.DfpField(i, false);
            org.apache.commons.math.dfp.Dfp dfp = new org.apache.commons.math.dfp.Dfp(dfpField, 1);
            org.apache.commons.math.dfp.Dfp dfp2 = new org.apache.commons.math.dfp.Dfp(dfpField, 2);
            org.apache.commons.math.dfp.Dfp dfp3 = new org.apache.commons.math.dfp.Dfp(dfpField, 3);
            org.apache.commons.math.dfp.Dfp sqrt = dfp2.sqrt();
            sqr2String = sqrt.toString();
            sqr2ReciprocalString = dfp.divide(sqrt).toString();
            org.apache.commons.math.dfp.Dfp sqrt2 = dfp3.sqrt();
            sqr3String = sqrt2.toString();
            sqr3ReciprocalString = dfp.divide(sqrt2).toString();
            piString = computePi(dfp, dfp2, dfp3).toString();
            eString = computeExp(dfp, dfp).toString();
            ln2String = computeLn(dfp2, dfp, dfp2).toString();
            ln5String = computeLn(new org.apache.commons.math.dfp.Dfp(dfpField, 5), dfp, dfp2).toString();
            ln10String = computeLn(new org.apache.commons.math.dfp.Dfp(dfpField, 10), dfp, dfp2).toString();
        }
    }

    private static org.apache.commons.math.dfp.Dfp computePi(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2, org.apache.commons.math.dfp.Dfp dfp3) {
        org.apache.commons.math.dfp.Dfp sqrt = dfp2.sqrt();
        org.apache.commons.math.dfp.Dfp subtract = sqrt.subtract(dfp);
        org.apache.commons.math.dfp.Dfp add = dfp2.add(dfp2);
        org.apache.commons.math.dfp.Dfp multiply = dfp2.multiply(dfp3.subtract(dfp2.multiply(sqrt)));
        int i = 1;
        while (i < 20) {
            org.apache.commons.math.dfp.Dfp multiply2 = subtract.multiply(subtract);
            org.apache.commons.math.dfp.Dfp sqrt2 = dfp.subtract(multiply2.multiply(multiply2)).sqrt().sqrt();
            org.apache.commons.math.dfp.Dfp divide = dfp.subtract(sqrt2).divide(dfp.add(sqrt2));
            dfp2 = dfp2.multiply(add);
            org.apache.commons.math.dfp.Dfp add2 = dfp.add(divide);
            org.apache.commons.math.dfp.Dfp multiply3 = add2.multiply(add2);
            multiply = multiply.multiply(multiply3.multiply(multiply3)).subtract(dfp2.multiply(divide).multiply(dfp.add(divide).add(divide.multiply(divide))));
            if (divide.equals(subtract)) {
                break;
            }
            i++;
            subtract = divide;
        }
        return dfp.divide(multiply);
    }

    public static org.apache.commons.math.dfp.Dfp computeExp(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2) {
        org.apache.commons.math.dfp.Dfp dfp3 = new org.apache.commons.math.dfp.Dfp(dfp2);
        org.apache.commons.math.dfp.Dfp dfp4 = new org.apache.commons.math.dfp.Dfp(dfp2);
        org.apache.commons.math.dfp.Dfp dfp5 = new org.apache.commons.math.dfp.Dfp(dfp2);
        org.apache.commons.math.dfp.Dfp dfp6 = new org.apache.commons.math.dfp.Dfp(dfp2);
        org.apache.commons.math.dfp.Dfp dfp7 = new org.apache.commons.math.dfp.Dfp(dfp2);
        for (int i = 0; i < 10000; i++) {
            dfp7 = dfp7.multiply(dfp);
            dfp3 = dfp3.add(dfp7.divide(dfp5));
            dfp6 = dfp6.add(dfp2);
            dfp5 = dfp5.multiply(dfp6);
            if (dfp3.equals(dfp4)) {
                break;
            }
            dfp4 = new org.apache.commons.math.dfp.Dfp(dfp3);
        }
        return dfp3;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public static org.apache.commons.math.dfp.Dfp computeLn(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2, org.apache.commons.math.dfp.Dfp dfp3) {
        org.apache.commons.math.dfp.Dfp divide = dfp.add(new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) dfp.getField(), -1)).divide(dfp.add(dfp2));
        org.apache.commons.math.dfp.Dfp dfp4 = new org.apache.commons.math.dfp.Dfp(divide);
        org.apache.commons.math.dfp.Dfp dfp5 = new org.apache.commons.math.dfp.Dfp(divide);
        org.apache.commons.math.dfp.Dfp dfp6 = new org.apache.commons.math.dfp.Dfp(dfp4);
        int i = 1;
        for (int i2 = 0; i2 < 10000; i2++) {
            dfp5 = dfp5.multiply(divide).multiply(divide);
            i += 2;
            dfp4 = dfp4.add(dfp5.divide(i));
            if (dfp4.equals(dfp6)) {
                break;
            }
            dfp6 = new org.apache.commons.math.dfp.Dfp(dfp4);
        }
        return dfp4.multiply(dfp3);
    }
}
