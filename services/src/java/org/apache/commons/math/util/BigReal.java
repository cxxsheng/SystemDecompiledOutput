package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class BigReal implements org.apache.commons.math.FieldElement<org.apache.commons.math.util.BigReal>, java.lang.Comparable<org.apache.commons.math.util.BigReal>, java.io.Serializable {
    private static final long serialVersionUID = 4984534880991310382L;
    private final java.math.BigDecimal d;
    private java.math.RoundingMode roundingMode = java.math.RoundingMode.HALF_UP;
    private int scale = 64;
    public static final org.apache.commons.math.util.BigReal ZERO = new org.apache.commons.math.util.BigReal(java.math.BigDecimal.ZERO);
    public static final org.apache.commons.math.util.BigReal ONE = new org.apache.commons.math.util.BigReal(java.math.BigDecimal.ONE);

    public BigReal(java.math.BigDecimal bigDecimal) {
        this.d = bigDecimal;
    }

    public BigReal(java.math.BigInteger bigInteger) {
        this.d = new java.math.BigDecimal(bigInteger);
    }

    public BigReal(java.math.BigInteger bigInteger, int i) {
        this.d = new java.math.BigDecimal(bigInteger, i);
    }

    public BigReal(java.math.BigInteger bigInteger, int i, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(bigInteger, i, mathContext);
    }

    public BigReal(java.math.BigInteger bigInteger, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(bigInteger, mathContext);
    }

    public BigReal(char[] cArr) {
        this.d = new java.math.BigDecimal(cArr);
    }

    public BigReal(char[] cArr, int i, int i2) {
        this.d = new java.math.BigDecimal(cArr, i, i2);
    }

    public BigReal(char[] cArr, int i, int i2, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(cArr, i, i2, mathContext);
    }

    public BigReal(char[] cArr, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(cArr, mathContext);
    }

    public BigReal(double d) {
        this.d = new java.math.BigDecimal(d);
    }

    public BigReal(double d, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(d, mathContext);
    }

    public BigReal(int i) {
        this.d = new java.math.BigDecimal(i);
    }

    public BigReal(int i, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(i, mathContext);
    }

    public BigReal(long j) {
        this.d = new java.math.BigDecimal(j);
    }

    public BigReal(long j, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(j, mathContext);
    }

    public BigReal(java.lang.String str) {
        this.d = new java.math.BigDecimal(str);
    }

    public BigReal(java.lang.String str, java.math.MathContext mathContext) {
        this.d = new java.math.BigDecimal(str, mathContext);
    }

    public java.math.RoundingMode getRoundingMode() {
        return this.roundingMode;
    }

    public void setRoundingMode(java.math.RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int i) {
        this.scale = i;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.util.BigReal add(org.apache.commons.math.util.BigReal bigReal) {
        return new org.apache.commons.math.util.BigReal(this.d.add(bigReal.d));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.util.BigReal subtract(org.apache.commons.math.util.BigReal bigReal) {
        return new org.apache.commons.math.util.BigReal(this.d.subtract(bigReal.d));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.util.BigReal divide(org.apache.commons.math.util.BigReal bigReal) throws java.lang.ArithmeticException {
        return new org.apache.commons.math.util.BigReal(this.d.divide(bigReal.d, this.scale, this.roundingMode));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.util.BigReal multiply(org.apache.commons.math.util.BigReal bigReal) {
        return new org.apache.commons.math.util.BigReal(this.d.multiply(bigReal.d));
    }

    @Override // java.lang.Comparable
    public int compareTo(org.apache.commons.math.util.BigReal bigReal) {
        return this.d.compareTo(bigReal.d);
    }

    public double doubleValue() {
        return this.d.doubleValue();
    }

    public java.math.BigDecimal bigDecimalValue() {
        return this.d;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof org.apache.commons.math.util.BigReal) {
            return this.d.equals(((org.apache.commons.math.util.BigReal) obj).d);
        }
        return false;
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.Field<org.apache.commons.math.util.BigReal> getField() {
        return org.apache.commons.math.util.BigRealField.getInstance();
    }
}
