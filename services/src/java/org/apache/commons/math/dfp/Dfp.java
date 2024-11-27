package org.apache.commons.math.dfp;

/* loaded from: classes3.dex */
public class Dfp implements org.apache.commons.math.FieldElement<org.apache.commons.math.dfp.Dfp> {
    private static final java.lang.String ADD_TRAP = "add";
    private static final java.lang.String ALIGN_TRAP = "align";
    private static final java.lang.String DIVIDE_TRAP = "divide";
    public static final int ERR_SCALE = 32760;
    public static final byte FINITE = 0;
    private static final java.lang.String GREATER_THAN_TRAP = "greaterThan";
    public static final byte INFINITE = 1;
    private static final java.lang.String LESS_THAN_TRAP = "lessThan";
    public static final int MAX_EXP = 32768;
    public static final int MIN_EXP = -32767;
    private static final java.lang.String MULTIPLY_TRAP = "multiply";
    private static final java.lang.String NAN_STRING = "NaN";
    private static final java.lang.String NEG_INFINITY_STRING = "-Infinity";
    private static final java.lang.String NEW_INSTANCE_TRAP = "newInstance";
    private static final java.lang.String NEXT_AFTER_TRAP = "nextAfter";
    private static final java.lang.String POS_INFINITY_STRING = "Infinity";
    public static final byte QNAN = 3;
    public static final int RADIX = 10000;
    public static final byte SNAN = 2;
    private static final java.lang.String SQRT_TRAP = "sqrt";
    private static final java.lang.String TRUNC_TRAP = "trunc";
    protected int exp;
    private final org.apache.commons.math.dfp.DfpField field;
    protected int[] mant;
    protected byte nans;
    protected byte sign;

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField) {
        this.mant = new int[dfpField.getRadixDigits()];
        this.sign = (byte) 1;
        this.exp = 0;
        this.nans = (byte) 0;
        this.field = dfpField;
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, byte b) {
        this(dfpField, b);
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, int i) {
        this(dfpField, i);
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, long j) {
        boolean z;
        this.mant = new int[dfpField.getRadixDigits()];
        this.nans = (byte) 0;
        this.field = dfpField;
        if (j != Long.MIN_VALUE) {
            z = false;
        } else {
            j++;
            z = true;
        }
        if (j < 0) {
            this.sign = (byte) -1;
            j = -j;
        } else {
            this.sign = (byte) 1;
        }
        this.exp = 0;
        while (j != 0) {
            java.lang.System.arraycopy(this.mant, this.mant.length - this.exp, this.mant, (this.mant.length - 1) - this.exp, this.exp);
            this.mant[this.mant.length - 1] = (int) (j % com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
            j /= com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
            this.exp++;
        }
        if (z) {
            for (int i = 0; i < this.mant.length - 1; i++) {
                if (this.mant[i] != 0) {
                    int[] iArr = this.mant;
                    iArr[i] = iArr[i] + 1;
                    return;
                }
            }
        }
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, double d) {
        this.mant = new int[dfpField.getRadixDigits()];
        this.sign = (byte) 1;
        this.exp = 0;
        this.nans = (byte) 0;
        this.field = dfpField;
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long j = doubleToLongBits & 4503599627370495L;
        int i = ((int) ((9218868437227405312L & doubleToLongBits) >> 52)) - 1023;
        if (i == -1023) {
            if (d == 0.0d) {
                return;
            }
            i++;
            while ((j & 4503599627370496L) == 0) {
                i--;
                j <<= 1;
            }
            j &= 4503599627370495L;
        }
        if (i != 1024) {
            org.apache.commons.math.dfp.Dfp multiply = new org.apache.commons.math.dfp.Dfp(dfpField, j).divide(new org.apache.commons.math.dfp.Dfp(dfpField, 4503599627370496L)).add(dfpField.getOne()).multiply(org.apache.commons.math.dfp.DfpMath.pow(dfpField.getTwo(), i));
            multiply = (doubleToLongBits & Long.MIN_VALUE) != 0 ? multiply.negate() : multiply;
            java.lang.System.arraycopy(multiply.mant, 0, this.mant, 0, this.mant.length);
            this.sign = multiply.sign;
            this.exp = multiply.exp;
            this.nans = multiply.nans;
            return;
        }
        if (d != d) {
            this.sign = (byte) 1;
            this.nans = (byte) 3;
        } else if (d < 0.0d) {
            this.sign = (byte) -1;
            this.nans = (byte) 1;
        } else {
            this.sign = (byte) 1;
            this.nans = (byte) 1;
        }
    }

    public Dfp(org.apache.commons.math.dfp.Dfp dfp) {
        this.mant = (int[]) dfp.mant.clone();
        this.sign = dfp.sign;
        this.exp = dfp.exp;
        this.nans = dfp.nans;
        this.field = dfp.field;
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, java.lang.String str) {
        int i;
        int i2;
        int i3;
        java.lang.String str2 = str;
        this.mant = new int[dfpField.getRadixDigits()];
        int i4 = 1;
        this.sign = (byte) 1;
        int i5 = 0;
        this.exp = 0;
        this.nans = (byte) 0;
        this.field = dfpField;
        int radixDigits = (getRadixDigits() * 4) + 8;
        char[] cArr = new char[radixDigits];
        if (str2.equals(POS_INFINITY_STRING)) {
            this.sign = (byte) 1;
            this.nans = (byte) 1;
            return;
        }
        if (str2.equals(NEG_INFINITY_STRING)) {
            this.sign = (byte) -1;
            this.nans = (byte) 1;
            return;
        }
        if (str2.equals(NAN_STRING)) {
            this.sign = (byte) 1;
            this.nans = (byte) 3;
            return;
        }
        int indexOf = str2.indexOf("e");
        indexOf = indexOf == -1 ? str2.indexOf("E") : indexOf;
        if (indexOf != -1) {
            java.lang.String substring = str2.substring(0, indexOf);
            java.lang.String substring2 = str2.substring(indexOf + 1);
            boolean z = false;
            i = 0;
            for (int i6 = 0; i6 < substring2.length(); i6++) {
                if (substring2.charAt(i6) == '-') {
                    z = true;
                } else if (substring2.charAt(i6) >= '0' && substring2.charAt(i6) <= '9') {
                    i = ((i * 10) + substring2.charAt(i6)) - 48;
                }
            }
            i = z ? -i : i;
            str2 = substring;
        } else {
            i = 0;
        }
        if (str2.indexOf("-") != -1) {
            this.sign = (byte) -1;
        }
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            if (str2.charAt(i7) >= '1' && str2.charAt(i7) <= '9') {
                break;
            }
            if (i8 != 0 && str2.charAt(i7) == '0') {
                i9--;
            }
            i8 = str2.charAt(i7) == '.' ? i4 : i8;
            i7++;
            if (i7 == str2.length()) {
                break;
            }
            i4 = 1;
            i5 = 0;
        }
        cArr[i5] = '0';
        cArr[i4] = '0';
        cArr[2] = '0';
        cArr[3] = '0';
        int i10 = i9;
        int i11 = 4;
        int i12 = i5;
        while (true) {
            if (i7 == str2.length()) {
                i2 = 4;
                break;
            }
            i2 = 4;
            if (i11 == (this.mant.length * 4) + 4 + i4) {
                break;
            }
            if (str2.charAt(i7) == '.') {
                i7++;
                i10 = i12;
                i4 = 1;
                i8 = 1;
            } else if (str2.charAt(i7) < '0' || str2.charAt(i7) > '9') {
                i7++;
                i4 = 1;
            } else {
                cArr[i11] = str2.charAt(i7);
                i11++;
                i7++;
                i12++;
                i4 = 1;
            }
        }
        if (i8 != 0 && i11 != i2) {
            while (true) {
                i11--;
                if (i11 == i2 || cArr[i11] != '0') {
                    break;
                }
                i12--;
                i2 = 4;
            }
        }
        if (i8 != 0 && i12 == 0) {
            i10 = 0;
        }
        if (i8 != 0) {
            i3 = 4;
        } else {
            i3 = 4;
            i10 = i11 - 4;
        }
        int i13 = (i12 - i4) + i3;
        while (i13 > i3 && cArr[i13] == '0') {
            i13--;
            i3 = 4;
        }
        int i14 = 4;
        int i15 = ((400 - i10) - (i % 4)) % 4;
        int i16 = 4 - i15;
        int i17 = i10 + i15;
        while (i13 - i16 < this.mant.length * i14) {
            int i18 = 0;
            while (i18 < i14) {
                i13++;
                cArr[i13] = '0';
                i18++;
                i14 = 4;
            }
            i14 = 4;
        }
        for (int length = this.mant.length - i4; length >= 0; length--) {
            this.mant[length] = ((cArr[i16] - '0') * 1000) + ((cArr[i16 + 1] - '0') * 100) + ((cArr[i16 + 2] - '0') * 10) + (cArr[i16 + 3] - '0');
            i16 += 4;
        }
        this.exp = (i17 + i) / 4;
        if (i16 < radixDigits) {
            round((cArr[i16] - '0') * 1000);
        }
    }

    protected Dfp(org.apache.commons.math.dfp.DfpField dfpField, byte b, byte b2) {
        this.field = dfpField;
        this.mant = new int[dfpField.getRadixDigits()];
        this.sign = b;
        this.exp = 0;
        this.nans = b2;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public org.apache.commons.math.dfp.Dfp newInstance() {
        return new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) getField());
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public org.apache.commons.math.dfp.Dfp newInstance(byte b) {
        return new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) getField(), b);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public org.apache.commons.math.dfp.Dfp newInstance(int i) {
        return new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) getField(), i);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public org.apache.commons.math.dfp.Dfp newInstance(long j) {
        return new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) getField(), j);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.apache.commons.math.dfp.DfpField] */
    public org.apache.commons.math.dfp.Dfp newInstance(double d) {
        return new org.apache.commons.math.dfp.Dfp((org.apache.commons.math.dfp.DfpField) getField(), d);
    }

    public org.apache.commons.math.dfp.Dfp newInstance(org.apache.commons.math.dfp.Dfp dfp) {
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, NEW_INSTANCE_TRAP, dfp, newInstance);
        }
        return new org.apache.commons.math.dfp.Dfp(dfp);
    }

    public org.apache.commons.math.dfp.Dfp newInstance(java.lang.String str) {
        return new org.apache.commons.math.dfp.Dfp(this.field, str);
    }

    public org.apache.commons.math.dfp.Dfp newInstance(byte b, byte b2) {
        return this.field.newDfp(b, b2);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.Field<org.apache.commons.math.dfp.Dfp> getField() {
        return this.field;
    }

    public int getRadixDigits() {
        return this.field.getRadixDigits();
    }

    public org.apache.commons.math.dfp.Dfp getZero() {
        return this.field.getZero();
    }

    public org.apache.commons.math.dfp.Dfp getOne() {
        return this.field.getOne();
    }

    public org.apache.commons.math.dfp.Dfp getTwo() {
        return this.field.getTwo();
    }

    protected void shiftLeft() {
        for (int length = this.mant.length - 1; length > 0; length--) {
            this.mant[length] = this.mant[length - 1];
        }
        this.mant[0] = 0;
        this.exp--;
    }

    protected void shiftRight() {
        int i = 0;
        while (i < this.mant.length - 1) {
            int i2 = i + 1;
            this.mant[i] = this.mant[i2];
            i = i2;
        }
        this.mant[this.mant.length - 1] = 0;
        this.exp++;
    }

    protected int align(int i) {
        int i2;
        int i3 = this.exp - i;
        if (i3 >= 0) {
            i2 = i3;
        } else {
            i2 = -i3;
        }
        if (i3 == 0) {
            return 0;
        }
        if (i2 > this.mant.length + 1) {
            java.util.Arrays.fill(this.mant, 0);
            this.exp = i;
            this.field.setIEEEFlagsBits(16);
            dotrap(16, ALIGN_TRAP, this, this);
            return 0;
        }
        boolean z = false;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            if (i3 < 0) {
                if (i4 != 0) {
                    z = true;
                }
                i4 = this.mant[0];
                shiftRight();
            } else {
                shiftLeft();
            }
        }
        if (z) {
            this.field.setIEEEFlagsBits(16);
            dotrap(16, ALIGN_TRAP, this, this);
        }
        return i4;
    }

    public boolean lessThan(org.apache.commons.math.dfp.Dfp dfp) {
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            dotrap(1, LESS_THAN_TRAP, dfp, newInstance);
            return false;
        }
        if (!isNaN() && !dfp.isNaN()) {
            return compare(this, dfp) < 0;
        }
        this.field.setIEEEFlagsBits(1);
        dotrap(1, LESS_THAN_TRAP, dfp, newInstance(getZero()));
        return false;
    }

    public boolean greaterThan(org.apache.commons.math.dfp.Dfp dfp) {
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            dotrap(1, GREATER_THAN_TRAP, dfp, newInstance);
            return false;
        }
        if (!isNaN() && !dfp.isNaN()) {
            return compare(this, dfp) > 0;
        }
        this.field.setIEEEFlagsBits(1);
        dotrap(1, GREATER_THAN_TRAP, dfp, newInstance(getZero()));
        return false;
    }

    public boolean isInfinite() {
        return this.nans == 1;
    }

    public boolean isNaN() {
        return this.nans == 3 || this.nans == 2;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof org.apache.commons.math.dfp.Dfp)) {
            return false;
        }
        org.apache.commons.math.dfp.Dfp dfp = (org.apache.commons.math.dfp.Dfp) obj;
        return !isNaN() && !dfp.isNaN() && this.field.getRadixDigits() == dfp.field.getRadixDigits() && compare(this, dfp) == 0;
    }

    public int hashCode() {
        return (this.sign << 8) + 17 + (this.nans << com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CAPABILITY) + this.exp + java.util.Arrays.hashCode(this.mant);
    }

    public boolean unequal(org.apache.commons.math.dfp.Dfp dfp) {
        if (isNaN() || dfp.isNaN() || this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            return false;
        }
        return greaterThan(dfp) || lessThan(dfp);
    }

    private static int compare(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2) {
        if (dfp.mant[dfp.mant.length - 1] == 0 && dfp2.mant[dfp2.mant.length - 1] == 0 && dfp.nans == 0 && dfp2.nans == 0) {
            return 0;
        }
        if (dfp.sign != dfp2.sign) {
            return dfp.sign == -1 ? -1 : 1;
        }
        if (dfp.nans == 1 && dfp2.nans == 0) {
            return dfp.sign;
        }
        if (dfp.nans == 0 && dfp2.nans == 1) {
            return -dfp2.sign;
        }
        if (dfp.nans == 1 && dfp2.nans == 1) {
            return 0;
        }
        if (dfp2.mant[dfp2.mant.length - 1] != 0 && dfp.mant[dfp2.mant.length - 1] != 0) {
            if (dfp.exp < dfp2.exp) {
                return -dfp.sign;
            }
            if (dfp.exp > dfp2.exp) {
                return dfp.sign;
            }
        }
        for (int length = dfp.mant.length - 1; length >= 0; length--) {
            if (dfp.mant[length] > dfp2.mant[length]) {
                return dfp.sign;
            }
            if (dfp.mant[length] < dfp2.mant[length]) {
                return -dfp.sign;
            }
        }
        return 0;
    }

    public org.apache.commons.math.dfp.Dfp rint() {
        return trunc(org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_EVEN);
    }

    public org.apache.commons.math.dfp.Dfp floor() {
        return trunc(org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_FLOOR);
    }

    public org.apache.commons.math.dfp.Dfp ceil() {
        return trunc(org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_CEIL);
    }

    public org.apache.commons.math.dfp.Dfp remainder(org.apache.commons.math.dfp.Dfp dfp) {
        org.apache.commons.math.dfp.Dfp subtract = subtract(divide(dfp).rint().multiply(dfp));
        if (subtract.mant[this.mant.length - 1] == 0) {
            subtract.sign = this.sign;
        }
        return subtract;
    }

    protected org.apache.commons.math.dfp.Dfp trunc(org.apache.commons.math.dfp.DfpField.RoundingMode roundingMode) {
        if (isNaN()) {
            return newInstance(this);
        }
        if (this.nans == 1) {
            return newInstance(this);
        }
        if (this.mant[this.mant.length - 1] == 0) {
            return newInstance(this);
        }
        if (this.exp < 0) {
            this.field.setIEEEFlagsBits(16);
            return dotrap(16, TRUNC_TRAP, this, newInstance(getZero()));
        }
        if (this.exp >= this.mant.length) {
            return newInstance(this);
        }
        org.apache.commons.math.dfp.Dfp newInstance = newInstance(this);
        boolean z = false;
        for (int i = 0; i < this.mant.length - newInstance.exp; i++) {
            z |= newInstance.mant[i] != 0;
            newInstance.mant[i] = 0;
        }
        if (z) {
            switch (org.apache.commons.math.dfp.Dfp.AnonymousClass1.$SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[roundingMode.ordinal()]) {
                case 1:
                    if (newInstance.sign == -1) {
                        newInstance = newInstance.add(newInstance(-1));
                        break;
                    }
                    break;
                case 2:
                    if (newInstance.sign == 1) {
                        newInstance = newInstance.add(getOne());
                        break;
                    }
                    break;
                default:
                    org.apache.commons.math.dfp.Dfp newInstance2 = newInstance("0.5");
                    org.apache.commons.math.dfp.Dfp subtract = subtract(newInstance);
                    subtract.sign = (byte) 1;
                    if (subtract.greaterThan(newInstance2)) {
                        subtract = newInstance(getOne());
                        subtract.sign = this.sign;
                        newInstance = newInstance.add(subtract);
                    }
                    if (subtract.equals(newInstance2) && newInstance.exp > 0 && (newInstance.mant[this.mant.length - newInstance.exp] & 1) != 0) {
                        org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(getOne());
                        newInstance3.sign = this.sign;
                        newInstance = newInstance.add(newInstance3);
                        break;
                    }
                    break;
            }
            this.field.setIEEEFlagsBits(16);
            return dotrap(16, TRUNC_TRAP, this, newInstance);
        }
        return newInstance;
    }

    /* renamed from: org.apache.commons.math.dfp.Dfp$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode = new int[org.apache.commons.math.dfp.DfpField.RoundingMode.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_FLOOR.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_CEIL.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_EVEN.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_DOWN.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_UP.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_UP.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_DOWN.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_HALF_ODD.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
        }
    }

    public int intValue() {
        org.apache.commons.math.dfp.Dfp rint = rint();
        if (rint.greaterThan(newInstance(Integer.MAX_VALUE))) {
            return Integer.MAX_VALUE;
        }
        if (rint.lessThan(newInstance(Integer.MIN_VALUE))) {
            return Integer.MIN_VALUE;
        }
        int i = 0;
        for (int length = this.mant.length - 1; length >= this.mant.length - rint.exp; length--) {
            i = (i * 10000) + rint.mant[length];
        }
        if (rint.sign == -1) {
            return -i;
        }
        return i;
    }

    public int log10K() {
        return this.exp - 1;
    }

    public org.apache.commons.math.dfp.Dfp power10K(int i) {
        org.apache.commons.math.dfp.Dfp newInstance = newInstance(getOne());
        newInstance.exp = i + 1;
        return newInstance;
    }

    public int log10() {
        if (this.mant[this.mant.length - 1] > 1000) {
            return (this.exp * 4) - 1;
        }
        if (this.mant[this.mant.length - 1] > 100) {
            return (this.exp * 4) - 2;
        }
        if (this.mant[this.mant.length - 1] > 10) {
            return (this.exp * 4) - 3;
        }
        return (this.exp * 4) - 4;
    }

    public org.apache.commons.math.dfp.Dfp power10(int i) {
        org.apache.commons.math.dfp.Dfp newInstance = newInstance(getOne());
        if (i >= 0) {
            newInstance.exp = (i / 4) + 1;
        } else {
            newInstance.exp = (i + 1) / 4;
        }
        switch (((i % 4) + 4) % 4) {
            case 0:
                return newInstance;
            case 1:
                return newInstance.multiply(10);
            case 2:
                return newInstance.multiply(100);
            default:
                return newInstance.multiply(1000);
        }
    }

    protected int complement(int i) {
        int i2 = 10000 - i;
        for (int i3 = 0; i3 < this.mant.length; i3++) {
            this.mant[i3] = (10000 - this.mant[i3]) - 1;
        }
        int i4 = i2 / 10000;
        int i5 = i2 - (i4 * 10000);
        for (int i6 = 0; i6 < this.mant.length; i6++) {
            int i7 = this.mant[i6] + i4;
            i4 = i7 / 10000;
            this.mant[i6] = i7 - (i4 * 10000);
        }
        return i5;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.dfp.Dfp add(org.apache.commons.math.dfp.Dfp dfp) {
        byte b;
        int align;
        int i;
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, ADD_TRAP, dfp, newInstance);
        }
        if (this.nans != 0 || dfp.nans != 0) {
            if (isNaN()) {
                return this;
            }
            if (dfp.isNaN()) {
                return dfp;
            }
            if (this.nans == 1 && dfp.nans == 0) {
                return this;
            }
            if (dfp.nans == 1 && this.nans == 0) {
                return dfp;
            }
            if (dfp.nans == 1 && this.nans == 1 && this.sign == dfp.sign) {
                return dfp;
            }
            if (dfp.nans == 1 && this.nans == 1 && this.sign != dfp.sign) {
                this.field.setIEEEFlagsBits(1);
                org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getZero());
                newInstance2.nans = (byte) 3;
                return dotrap(1, ADD_TRAP, dfp, newInstance2);
            }
        }
        org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(this);
        org.apache.commons.math.dfp.Dfp newInstance4 = newInstance(dfp);
        org.apache.commons.math.dfp.Dfp newInstance5 = newInstance(getZero());
        byte b2 = newInstance3.sign;
        byte b3 = newInstance4.sign;
        newInstance3.sign = (byte) 1;
        newInstance4.sign = (byte) 1;
        if (compare(newInstance3, newInstance4) <= 0) {
            b = b3;
        } else {
            b = b2;
        }
        if (newInstance4.mant[this.mant.length - 1] == 0) {
            newInstance4.exp = newInstance3.exp;
        }
        if (newInstance3.mant[this.mant.length - 1] == 0) {
            newInstance3.exp = newInstance4.exp;
        }
        if (newInstance3.exp < newInstance4.exp) {
            i = newInstance3.align(newInstance4.exp);
            align = 0;
        } else {
            align = newInstance4.align(newInstance3.exp);
            i = 0;
        }
        if (b2 != b3) {
            if (b2 == b) {
                align = newInstance4.complement(align);
            } else {
                i = newInstance3.complement(i);
            }
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.mant.length; i3++) {
            int i4 = newInstance3.mant[i3] + newInstance4.mant[i3] + i2;
            i2 = i4 / 10000;
            newInstance5.mant[i3] = i4 - (i2 * 10000);
        }
        newInstance5.exp = newInstance3.exp;
        newInstance5.sign = b;
        if (i2 != 0 && b2 == b3) {
            int i5 = newInstance5.mant[0];
            newInstance5.shiftRight();
            newInstance5.mant[this.mant.length - 1] = i2;
            int round = newInstance5.round(i5);
            if (round != 0) {
                newInstance5 = dotrap(round, ADD_TRAP, dfp, newInstance5);
            }
        }
        for (int i6 = 0; i6 < this.mant.length && newInstance5.mant[this.mant.length - 1] == 0; i6++) {
            newInstance5.shiftLeft();
            if (i6 == 0) {
                newInstance5.mant[0] = i + align;
                i = 0;
                align = 0;
            }
        }
        if (newInstance5.mant[this.mant.length - 1] == 0) {
            newInstance5.exp = 0;
            if (b2 != b3) {
                newInstance5.sign = (byte) 1;
            }
        }
        int round2 = newInstance5.round(i + align);
        if (round2 != 0) {
            return dotrap(round2, ADD_TRAP, dfp, newInstance5);
        }
        return newInstance5;
    }

    public org.apache.commons.math.dfp.Dfp negate() {
        org.apache.commons.math.dfp.Dfp newInstance = newInstance(this);
        newInstance.sign = (byte) (-newInstance.sign);
        return newInstance;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.dfp.Dfp subtract(org.apache.commons.math.dfp.Dfp dfp) {
        return add(dfp.negate());
    }

    protected int round(int i) {
        boolean z;
        switch (org.apache.commons.math.dfp.Dfp.AnonymousClass1.$SwitchMap$org$apache$commons$math$dfp$DfpField$RoundingMode[this.field.getRoundingMode().ordinal()]) {
            case 2:
                if (this.sign != 1 || i == 0) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            case 3:
                if (i > 5000 || (i == 5000 && (this.mant[0] & 1) == 1)) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 4:
                z = false;
                break;
            case 5:
                if (i != 0) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 6:
                if (i >= 5000) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 7:
                if (i > 5000) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            case 8:
                if (i > 5000 || (i == 5000 && (this.mant[0] & 1) == 0)) {
                    z = true;
                    break;
                } else {
                    z = false;
                    break;
                }
            default:
                if (this.sign != -1 || i == 0) {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
        }
        if (z) {
            int i2 = 1;
            for (int i3 = 0; i3 < this.mant.length; i3++) {
                int i4 = this.mant[i3] + i2;
                i2 = i4 / 10000;
                this.mant[i3] = i4 - (i2 * 10000);
            }
            if (i2 != 0) {
                shiftRight();
                this.mant[this.mant.length - 1] = i2;
            }
        }
        if (this.exp < -32767) {
            this.field.setIEEEFlagsBits(8);
            return 8;
        }
        if (this.exp > 32768) {
            this.field.setIEEEFlagsBits(4);
            return 4;
        }
        if (i == 0) {
            return 0;
        }
        this.field.setIEEEFlagsBits(16);
        return 16;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.dfp.Dfp multiply(org.apache.commons.math.dfp.Dfp dfp) {
        int round;
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, MULTIPLY_TRAP, dfp, newInstance);
        }
        org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getZero());
        if (this.nans != 0 || dfp.nans != 0) {
            if (isNaN()) {
                return this;
            }
            if (dfp.isNaN()) {
                return dfp;
            }
            if (this.nans == 1 && dfp.nans == 0 && dfp.mant[this.mant.length - 1] != 0) {
                org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(this);
                newInstance3.sign = (byte) (this.sign * dfp.sign);
                return newInstance3;
            }
            if (dfp.nans == 1 && this.nans == 0 && this.mant[this.mant.length - 1] != 0) {
                org.apache.commons.math.dfp.Dfp newInstance4 = newInstance(dfp);
                newInstance4.sign = (byte) (this.sign * dfp.sign);
                return newInstance4;
            }
            if (dfp.nans == 1 && this.nans == 1) {
                org.apache.commons.math.dfp.Dfp newInstance5 = newInstance(this);
                newInstance5.sign = (byte) (this.sign * dfp.sign);
                return newInstance5;
            }
            if ((dfp.nans == 1 && this.nans == 0 && this.mant[this.mant.length - 1] == 0) || (this.nans == 1 && dfp.nans == 0 && dfp.mant[this.mant.length - 1] == 0)) {
                this.field.setIEEEFlagsBits(1);
                org.apache.commons.math.dfp.Dfp newInstance6 = newInstance(getZero());
                newInstance6.nans = (byte) 3;
                return dotrap(1, MULTIPLY_TRAP, dfp, newInstance6);
            }
        }
        int[] iArr = new int[this.mant.length * 2];
        for (int i = 0; i < this.mant.length; i++) {
            int i2 = 0;
            for (int i3 = 0; i3 < this.mant.length; i3++) {
                int i4 = i + i3;
                int i5 = (this.mant[i] * dfp.mant[i3]) + iArr[i4] + i2;
                i2 = i5 / 10000;
                iArr[i4] = i5 - (i2 * 10000);
            }
            iArr[this.mant.length + i] = i2;
        }
        int length = (this.mant.length * 2) - 1;
        int length2 = (this.mant.length * 2) - 1;
        while (true) {
            if (length2 < 0) {
                break;
            }
            if (iArr[length2] != 0) {
                length = length2;
                break;
            }
            length2--;
        }
        for (int i6 = 0; i6 < this.mant.length; i6++) {
            newInstance2.mant[(this.mant.length - i6) - 1] = iArr[length - i6];
        }
        newInstance2.exp = (((this.exp + dfp.exp) + length) - (this.mant.length * 2)) + 1;
        newInstance2.sign = (byte) (this.sign == dfp.sign ? 1 : -1);
        if (newInstance2.mant[this.mant.length - 1] == 0) {
            newInstance2.exp = 0;
        }
        if (length > this.mant.length - 1) {
            round = newInstance2.round(iArr[length - this.mant.length]);
        } else {
            round = newInstance2.round(0);
        }
        if (round != 0) {
            return dotrap(round, MULTIPLY_TRAP, dfp, newInstance2);
        }
        return newInstance2;
    }

    public org.apache.commons.math.dfp.Dfp multiply(int i) {
        int i2;
        org.apache.commons.math.dfp.Dfp newInstance = newInstance(this);
        if (this.nans != 0) {
            if (isNaN()) {
                return this;
            }
            if (this.nans == 1 && i != 0) {
                return newInstance(this);
            }
            if (this.nans == 1 && i == 0) {
                this.field.setIEEEFlagsBits(1);
                org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getZero());
                newInstance2.nans = (byte) 3;
                return dotrap(1, MULTIPLY_TRAP, newInstance(getZero()), newInstance2);
            }
        }
        if (i < 0 || i >= 10000) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(getZero());
            newInstance3.nans = (byte) 3;
            return dotrap(1, MULTIPLY_TRAP, newInstance3, newInstance3);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.mant.length; i4++) {
            int i5 = (this.mant[i4] * i) + i3;
            i3 = i5 / 10000;
            newInstance.mant[i4] = i5 - (i3 * 10000);
        }
        if (i3 != 0) {
            i2 = newInstance.mant[0];
            newInstance.shiftRight();
            newInstance.mant[this.mant.length - 1] = i3;
        } else {
            i2 = 0;
        }
        if (newInstance.mant[this.mant.length - 1] == 0) {
            newInstance.exp = 0;
        }
        int round = newInstance.round(i2);
        if (round != 0) {
            return dotrap(round, MULTIPLY_TRAP, newInstance, newInstance);
        }
        return newInstance;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.dfp.Dfp divide(org.apache.commons.math.dfp.Dfp dfp) {
        int round;
        int i = 1;
        if (this.field.getRadixDigits() == dfp.field.getRadixDigits()) {
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            if (this.nans != 0 || dfp.nans != 0) {
                if (isNaN()) {
                    return this;
                }
                if (dfp.isNaN()) {
                    return dfp;
                }
                if (this.nans == 1 && dfp.nans == 0) {
                    org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(this);
                    newInstance2.sign = (byte) (this.sign * dfp.sign);
                    return newInstance2;
                }
                if (dfp.nans == 1 && this.nans == 0) {
                    org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(getZero());
                    newInstance3.sign = (byte) (this.sign * dfp.sign);
                    return newInstance3;
                }
                if (dfp.nans == 1 && this.nans == 1) {
                    this.field.setIEEEFlagsBits(1);
                    org.apache.commons.math.dfp.Dfp newInstance4 = newInstance(getZero());
                    newInstance4.nans = (byte) 3;
                    return dotrap(1, DIVIDE_TRAP, dfp, newInstance4);
                }
            }
            int i2 = 2;
            if (dfp.mant[this.mant.length - 1] == 0) {
                this.field.setIEEEFlagsBits(2);
                org.apache.commons.math.dfp.Dfp newInstance5 = newInstance(getZero());
                newInstance5.sign = (byte) (this.sign * dfp.sign);
                newInstance5.nans = (byte) 1;
                return dotrap(2, DIVIDE_TRAP, dfp, newInstance5);
            }
            int[] iArr = new int[this.mant.length + 1];
            int[] iArr2 = new int[this.mant.length + 2];
            int[] iArr3 = new int[this.mant.length + 1];
            iArr[this.mant.length] = 0;
            iArr2[this.mant.length] = 0;
            iArr2[this.mant.length + 1] = 0;
            iArr3[this.mant.length] = 0;
            for (int i3 = 0; i3 < this.mant.length; i3++) {
                iArr[i3] = this.mant[i3];
                iArr2[i3] = 0;
                iArr3[i3] = 0;
            }
            int length = this.mant.length + 1;
            int i4 = 0;
            int i5 = 0;
            while (length >= 0) {
                int i6 = (iArr[this.mant.length] * 10000) + iArr[this.mant.length - i];
                int i7 = i6 / (dfp.mant[this.mant.length - i] + i);
                int i8 = (i6 + i) / dfp.mant[this.mant.length - i];
                boolean z = false;
                while (!z) {
                    i5 = (i7 + i8) / i2;
                    int i9 = 0;
                    int i10 = 0;
                    while (i9 < this.mant.length + i) {
                        int i11 = ((i9 < this.mant.length ? dfp.mant[i9] : 0) * i5) + i10;
                        int i12 = i11 / 10000;
                        iArr3[i9] = i11 - (i12 * 10000);
                        i9++;
                        i10 = i12;
                        z = z;
                        i = 1;
                    }
                    boolean z2 = z;
                    int i13 = 1;
                    for (int i14 = 0; i14 < this.mant.length + 1; i14++) {
                        int i15 = (9999 - iArr3[i14]) + iArr[i14] + i13;
                        i13 = i15 / 10000;
                        iArr3[i14] = i15 - (i13 * 10000);
                    }
                    if (i13 != 0) {
                        int i16 = ((iArr3[this.mant.length] * 10000) + iArr3[this.mant.length - 1]) / (dfp.mant[this.mant.length - 1] + 1);
                        if (i16 >= 2) {
                            i7 = i5 + i16;
                            i2 = 2;
                            z = z2;
                            i = 1;
                        } else {
                            z = false;
                            for (int length2 = this.mant.length - 1; length2 >= 0; length2--) {
                                if (dfp.mant[length2] > iArr3[length2]) {
                                    z = true;
                                }
                                if (dfp.mant[length2] < iArr3[length2]) {
                                    break;
                                }
                            }
                            if (iArr3[this.mant.length] != 0) {
                                z = false;
                            }
                            if (!z) {
                                i7 = i5 + 1;
                            }
                            i2 = 2;
                            i = 1;
                        }
                    } else {
                        i8 = i5 - 1;
                        z = z2;
                        i2 = 2;
                        i = 1;
                    }
                }
                iArr2[length] = i5;
                if (i5 != 0 || i4 != 0) {
                    i4++;
                }
                if ((this.field.getRoundingMode() == org.apache.commons.math.dfp.DfpField.RoundingMode.ROUND_DOWN && i4 == this.mant.length) || i4 > this.mant.length) {
                    break;
                }
                iArr[0] = 0;
                int i17 = 0;
                while (i17 < this.mant.length) {
                    int i18 = i17 + 1;
                    iArr[i18] = iArr3[i17];
                    i17 = i18;
                }
                length--;
                i2 = 2;
                i = 1;
            }
            int length3 = this.mant.length;
            int length4 = this.mant.length + 1;
            while (true) {
                if (length4 < 0) {
                    break;
                }
                if (iArr2[length4] != 0) {
                    length3 = length4;
                    break;
                }
                length4--;
            }
            for (int i19 = 0; i19 < this.mant.length; i19++) {
                newInstance.mant[(this.mant.length - i19) - 1] = iArr2[length3 - i19];
            }
            newInstance.exp = ((this.exp - dfp.exp) + length3) - this.mant.length;
            newInstance.sign = (byte) (this.sign == dfp.sign ? 1 : -1);
            if (newInstance.mant[this.mant.length - 1] == 0) {
                newInstance.exp = 0;
            }
            if (length3 > this.mant.length - 1) {
                round = newInstance.round(iArr2[length3 - this.mant.length]);
            } else {
                round = newInstance.round(0);
            }
            if (round != 0) {
                return dotrap(round, DIVIDE_TRAP, dfp, newInstance);
            }
            return newInstance;
        }
        this.field.setIEEEFlagsBits(1);
        org.apache.commons.math.dfp.Dfp newInstance6 = newInstance(getZero());
        newInstance6.nans = (byte) 3;
        return dotrap(1, DIVIDE_TRAP, dfp, newInstance6);
    }

    public org.apache.commons.math.dfp.Dfp divide(int i) {
        if (this.nans != 0) {
            if (isNaN()) {
                return this;
            }
            if (this.nans == 1) {
                return newInstance(this);
            }
        }
        if (i == 0) {
            this.field.setIEEEFlagsBits(2);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.sign = this.sign;
            newInstance.nans = (byte) 1;
            return dotrap(2, DIVIDE_TRAP, getZero(), newInstance);
        }
        if (i < 0 || i >= 10000) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getZero());
            newInstance2.nans = (byte) 3;
            return dotrap(1, DIVIDE_TRAP, newInstance2, newInstance2);
        }
        org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(this);
        int i2 = 0;
        for (int length = this.mant.length - 1; length >= 0; length--) {
            int i3 = (i2 * 10000) + newInstance3.mant[length];
            int i4 = i3 / i;
            i2 = i3 - (i4 * i);
            newInstance3.mant[length] = i4;
        }
        if (newInstance3.mant[this.mant.length - 1] == 0) {
            newInstance3.shiftLeft();
            int i5 = i2 * 10000;
            int i6 = i5 / i;
            i2 = i5 - (i6 * i);
            newInstance3.mant[0] = i6;
        }
        int round = newInstance3.round((i2 * 10000) / i);
        if (round != 0) {
            return dotrap(round, DIVIDE_TRAP, newInstance3, newInstance3);
        }
        return newInstance3;
    }

    public org.apache.commons.math.dfp.Dfp sqrt() {
        if (this.nans == 0 && this.mant[this.mant.length - 1] == 0) {
            return newInstance(this);
        }
        if (this.nans != 0) {
            if (this.nans == 1 && this.sign == 1) {
                return newInstance(this);
            }
            if (this.nans == 3) {
                return newInstance(this);
            }
            if (this.nans == 2) {
                this.field.setIEEEFlagsBits(1);
                return dotrap(1, SQRT_TRAP, null, newInstance(this));
            }
        }
        if (this.sign == -1) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(this);
            newInstance.nans = (byte) 3;
            return dotrap(1, SQRT_TRAP, null, newInstance);
        }
        org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(this);
        if (newInstance2.exp < -1 || newInstance2.exp > 1) {
            newInstance2.exp = this.exp / 2;
        }
        switch (newInstance2.mant[this.mant.length - 1] / 2000) {
            case 0:
                newInstance2.mant[this.mant.length - 1] = (newInstance2.mant[this.mant.length - 1] / 2) + 1;
                break;
            case 1:
            default:
                newInstance2.mant[this.mant.length - 1] = 3000;
                break;
            case 2:
                newInstance2.mant[this.mant.length - 1] = 1500;
                break;
            case 3:
                newInstance2.mant[this.mant.length - 1] = 2200;
                break;
        }
        newInstance(newInstance2);
        org.apache.commons.math.dfp.Dfp zero = getZero();
        getZero();
        while (newInstance2.unequal(zero)) {
            org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(newInstance2);
            newInstance3.sign = (byte) -1;
            org.apache.commons.math.dfp.Dfp divide = newInstance3.add(divide(newInstance2)).divide(2);
            org.apache.commons.math.dfp.Dfp add = newInstance2.add(divide);
            if (!add.equals(zero) && divide.mant[this.mant.length - 1] != 0) {
                zero = newInstance2;
                newInstance2 = add;
            } else {
                return add;
            }
        }
        return newInstance2;
    }

    public java.lang.String toString() {
        if (this.nans != 0) {
            if (this.nans == 1) {
                return this.sign < 0 ? NEG_INFINITY_STRING : POS_INFINITY_STRING;
            }
            return NAN_STRING;
        }
        if (this.exp > this.mant.length || this.exp < -1) {
            return dfp2sci();
        }
        return dfp2string();
    }

    protected java.lang.String dfp2sci() {
        int i;
        int i2;
        int length = this.mant.length * 4;
        char[] cArr = new char[length];
        char[] cArr2 = new char[(this.mant.length * 4) + 20];
        int i3 = 0;
        for (int length2 = this.mant.length - 1; length2 >= 0; length2--) {
            int i4 = i3 + 1;
            cArr[i3] = (char) ((this.mant[length2] / 1000) + 48);
            int i5 = i4 + 1;
            cArr[i4] = (char) (((this.mant[length2] / 100) % 10) + 48);
            int i6 = i5 + 1;
            cArr[i5] = (char) (((this.mant[length2] / 10) % 10) + 48);
            i3 = i6 + 1;
            cArr[i6] = (char) ((this.mant[length2] % 10) + 48);
        }
        int i7 = 0;
        while (i7 < length && cArr[i7] == '0') {
            i7++;
        }
        if (this.sign != -1) {
            i = 0;
        } else {
            cArr2[0] = '-';
            i = 1;
        }
        if (i7 != length) {
            int i8 = i + 1;
            cArr2[i] = cArr[i7];
            int i9 = i8 + 1;
            cArr2[i8] = '.';
            for (int i10 = i7 + 1; i10 < length; i10++) {
                cArr2[i9] = cArr[i10];
                i9++;
            }
            int i11 = i9 + 1;
            cArr2[i9] = 'e';
            int i12 = ((this.exp * 4) - i7) - 1;
            if (i12 >= 0) {
                i2 = i12;
            } else {
                i2 = -i12;
            }
            int i13 = 1000000000;
            while (i13 > i2) {
                i13 /= 10;
            }
            if (i12 < 0) {
                cArr2[i11] = '-';
                i11++;
            }
            while (i13 > 0) {
                cArr2[i11] = (char) ((i2 / i13) + 48);
                i2 %= i13;
                i13 /= 10;
                i11++;
            }
            return new java.lang.String(cArr2, 0, i11);
        }
        int i14 = i + 1;
        cArr2[i] = '0';
        int i15 = i14 + 1;
        cArr2[i14] = '.';
        int i16 = i15 + 1;
        cArr2[i15] = '0';
        cArr2[i16] = 'e';
        cArr2[i16 + 1] = '0';
        return new java.lang.String(cArr2, 0, 5);
    }

    protected java.lang.String dfp2string() {
        boolean z;
        int i;
        char[] cArr = new char[(this.mant.length * 4) + 20];
        int i2 = this.exp;
        cArr[0] = ' ';
        int i3 = 1;
        if (i2 > 0) {
            z = false;
            i = 1;
        } else {
            cArr[1] = '0';
            cArr[2] = '.';
            i = 3;
            z = true;
        }
        while (i2 < 0) {
            int i4 = i + 1;
            cArr[i] = '0';
            int i5 = i4 + 1;
            cArr[i4] = '0';
            int i6 = i5 + 1;
            cArr[i5] = '0';
            i = i6 + 1;
            cArr[i6] = '0';
            i2++;
        }
        for (int length = this.mant.length - 1; length >= 0; length--) {
            int i7 = i + 1;
            cArr[i] = (char) ((this.mant[length] / 1000) + 48);
            int i8 = i7 + 1;
            cArr[i7] = (char) (((this.mant[length] / 100) % 10) + 48);
            int i9 = i8 + 1;
            cArr[i8] = (char) (((this.mant[length] / 10) % 10) + 48);
            i = i9 + 1;
            cArr[i9] = (char) ((this.mant[length] % 10) + 48);
            i2--;
            if (i2 == 0) {
                cArr[i] = '.';
                i++;
                z = true;
            }
        }
        while (i2 > 0) {
            int i10 = i + 1;
            cArr[i] = '0';
            int i11 = i10 + 1;
            cArr[i10] = '0';
            int i12 = i11 + 1;
            cArr[i11] = '0';
            i = i12 + 1;
            cArr[i12] = '0';
            i2--;
        }
        if (!z) {
            cArr[i] = '.';
            i++;
        }
        while (cArr[i3] == '0') {
            i3++;
        }
        if (cArr[i3] == '.') {
            i3--;
        }
        while (cArr[i - 1] == '0') {
            i--;
        }
        if (this.sign < 0) {
            i3--;
            cArr[i3] = '-';
        }
        return new java.lang.String(cArr, i3, i - i3);
    }

    public org.apache.commons.math.dfp.Dfp dotrap(int i, java.lang.String str, org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2) {
        org.apache.commons.math.dfp.Dfp dfp3;
        org.apache.commons.math.dfp.Dfp dfp4;
        org.apache.commons.math.dfp.Dfp newInstance;
        switch (i) {
            case 1:
                org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getZero());
                newInstance2.sign = dfp2.sign;
                newInstance2.nans = (byte) 3;
                dfp3 = newInstance2;
                break;
            case 2:
                if (this.nans == 0 && this.mant[this.mant.length - 1] != 0) {
                    dfp4 = newInstance(getZero());
                    dfp4.sign = (byte) (this.sign * dfp.sign);
                    dfp4.nans = (byte) 1;
                } else {
                    dfp4 = dfp2;
                }
                if (this.nans == 0 && this.mant[this.mant.length - 1] == 0) {
                    dfp4 = newInstance(getZero());
                    dfp4.nans = (byte) 3;
                }
                if (this.nans == 1 || this.nans == 3) {
                    dfp4 = newInstance(getZero());
                    dfp4.nans = (byte) 3;
                }
                if (this.nans != 1 && this.nans != 2) {
                    dfp3 = dfp4;
                    break;
                } else {
                    org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(getZero());
                    newInstance3.nans = (byte) 3;
                    dfp3 = newInstance3;
                    break;
                }
                break;
            case 4:
                dfp2.exp -= 32760;
                org.apache.commons.math.dfp.Dfp newInstance4 = newInstance(getZero());
                newInstance4.sign = dfp2.sign;
                newInstance4.nans = (byte) 1;
                dfp3 = newInstance4;
                break;
            case 8:
                if (dfp2.exp + this.mant.length < -32767) {
                    newInstance = newInstance(getZero());
                    newInstance.sign = dfp2.sign;
                } else {
                    newInstance = newInstance(dfp2);
                }
                dfp2.exp += ERR_SCALE;
                dfp3 = newInstance;
                break;
            default:
                dfp3 = dfp2;
                break;
        }
        return trap(i, str, dfp, dfp3, dfp2);
    }

    protected org.apache.commons.math.dfp.Dfp trap(int i, java.lang.String str, org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2, org.apache.commons.math.dfp.Dfp dfp3) {
        return dfp2;
    }

    public int classify() {
        return this.nans;
    }

    public static org.apache.commons.math.dfp.Dfp copysign(org.apache.commons.math.dfp.Dfp dfp, org.apache.commons.math.dfp.Dfp dfp2) {
        org.apache.commons.math.dfp.Dfp newInstance = dfp.newInstance(dfp);
        newInstance.sign = dfp2.sign;
        return newInstance;
    }

    public org.apache.commons.math.dfp.Dfp nextAfter(org.apache.commons.math.dfp.Dfp dfp) {
        boolean z;
        org.apache.commons.math.dfp.Dfp subtract;
        if (this.field.getRadixDigits() != dfp.field.getRadixDigits()) {
            this.field.setIEEEFlagsBits(1);
            org.apache.commons.math.dfp.Dfp newInstance = newInstance(getZero());
            newInstance.nans = (byte) 3;
            return dotrap(1, NEXT_AFTER_TRAP, dfp, newInstance);
        }
        if (!lessThan(dfp)) {
            z = false;
        } else {
            z = true;
        }
        if (compare(this, dfp) == 0) {
            return newInstance(dfp);
        }
        if (lessThan(getZero())) {
            z = !z;
        }
        if (z) {
            org.apache.commons.math.dfp.Dfp newInstance2 = newInstance(getOne());
            newInstance2.exp = (this.exp - this.mant.length) + 1;
            newInstance2.sign = this.sign;
            if (equals(getZero())) {
                newInstance2.exp = (-32767) - this.mant.length;
            }
            subtract = add(newInstance2);
        } else {
            org.apache.commons.math.dfp.Dfp newInstance3 = newInstance(getOne());
            newInstance3.exp = this.exp;
            newInstance3.sign = this.sign;
            if (equals(newInstance3)) {
                newInstance3.exp = this.exp - this.mant.length;
            } else {
                newInstance3.exp = (this.exp - this.mant.length) + 1;
            }
            if (equals(getZero())) {
                newInstance3.exp = (-32767) - this.mant.length;
            }
            subtract = subtract(newInstance3);
        }
        if (subtract.classify() == 1 && classify() != 1) {
            this.field.setIEEEFlagsBits(16);
            subtract = dotrap(16, NEXT_AFTER_TRAP, dfp, subtract);
        }
        if (subtract.equals(getZero()) && !equals(getZero())) {
            this.field.setIEEEFlagsBits(16);
            return dotrap(16, NEXT_AFTER_TRAP, dfp, subtract);
        }
        return subtract;
    }

    public double toDouble() {
        org.apache.commons.math.dfp.Dfp dfp;
        boolean z;
        if (isInfinite()) {
            return lessThan(getZero()) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        if (isNaN()) {
            return Double.NaN;
        }
        if (!lessThan(getZero())) {
            dfp = this;
            z = false;
        } else {
            dfp = negate();
            z = true;
        }
        int log10 = (int) (dfp.log10() * 3.32d);
        if (log10 < 0) {
            log10--;
        }
        org.apache.commons.math.dfp.Dfp pow = org.apache.commons.math.dfp.DfpMath.pow(getTwo(), log10);
        while (true) {
            if (!pow.lessThan(dfp) && !pow.equals(dfp)) {
                break;
            }
            pow = pow.multiply(2);
            log10++;
        }
        int i = log10 - 1;
        org.apache.commons.math.dfp.Dfp divide = dfp.divide(org.apache.commons.math.dfp.DfpMath.pow(getTwo(), i));
        if (i > -1023) {
            divide = divide.subtract(getOne());
        }
        if (i < -1074) {
            return 0.0d;
        }
        if (i > 1023) {
            return z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        java.lang.String dfp2 = divide.multiply(newInstance(4503599627370496L)).rint().toString();
        long parseLong = java.lang.Long.parseLong(dfp2.substring(0, dfp2.length() - 1));
        if (parseLong == 4503599627370496L) {
            i++;
            parseLong = 0;
        }
        if (i <= -1023) {
            i--;
        }
        while (i < -1023) {
            i++;
            parseLong >>>= 1;
        }
        double longBitsToDouble = java.lang.Double.longBitsToDouble(((i + 1023) << 52) | parseLong);
        if (z) {
            return -longBitsToDouble;
        }
        return longBitsToDouble;
    }

    public double[] toSplitDouble() {
        double[] dArr = new double[2];
        dArr[0] = java.lang.Double.longBitsToDouble(java.lang.Double.doubleToLongBits(toDouble()) & (-1073741824));
        dArr[1] = subtract(newInstance(dArr[0])).toDouble();
        return dArr;
    }
}
