package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
class SimpleBigDecimal {
    private static final long serialVersionUID = 1;
    private final java.math.BigInteger bigInt;
    private final int scale;

    public static com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal getInstance(java.math.BigInteger bigInteger, int i) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(bigInteger.shiftLeft(i), i);
    }

    public SimpleBigDecimal(java.math.BigInteger bigInteger, int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("scale may not be negative");
        }
        this.bigInt = bigInteger;
        this.scale = i;
    }

    private void checkScale(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        if (this.scale != simpleBigDecimal.scale) {
            throw new java.lang.IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
        }
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal adjustScale(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("scale may not be negative");
        }
        if (i == this.scale) {
            return this;
        }
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.shiftLeft(i - this.scale), i);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        checkScale(simpleBigDecimal);
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.add(simpleBigDecimal.bigInt), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.add(bigInteger.shiftLeft(this.scale)), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal negate() {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.negate(), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        return add(simpleBigDecimal.negate());
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.subtract(bigInteger.shiftLeft(this.scale)), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal multiply(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        checkScale(simpleBigDecimal);
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.multiply(simpleBigDecimal.bigInt), this.scale + this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal multiply(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.multiply(bigInteger), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal divide(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        checkScale(simpleBigDecimal);
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.shiftLeft(this.scale).divide(simpleBigDecimal.bigInt), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal divide(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.divide(bigInteger), this.scale);
    }

    public com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal shiftLeft(int i) {
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(this.bigInt.shiftLeft(i), this.scale);
    }

    public int compareTo(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal) {
        checkScale(simpleBigDecimal);
        return this.bigInt.compareTo(simpleBigDecimal.bigInt);
    }

    public int compareTo(java.math.BigInteger bigInteger) {
        return this.bigInt.compareTo(bigInteger.shiftLeft(this.scale));
    }

    public java.math.BigInteger floor() {
        return this.bigInt.shiftRight(this.scale);
    }

    public java.math.BigInteger round() {
        return add(new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE, 1).adjustScale(this.scale)).floor();
    }

    public int intValue() {
        return floor().intValue();
    }

    public long longValue() {
        return floor().longValue();
    }

    public int getScale() {
        return this.scale;
    }

    public java.lang.String toString() {
        if (this.scale == 0) {
            return this.bigInt.toString();
        }
        java.math.BigInteger floor = floor();
        java.math.BigInteger subtract = this.bigInt.subtract(floor.shiftLeft(this.scale));
        if (this.bigInt.signum() == -1) {
            subtract = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.shiftLeft(this.scale).subtract(subtract);
        }
        if (floor.signum() == -1 && !subtract.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO)) {
            floor = floor.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
        }
        java.lang.String obj = floor.toString();
        char[] cArr = new char[this.scale];
        java.lang.String bigInteger = subtract.toString(2);
        int length = bigInteger.length();
        int i = this.scale - length;
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = '0';
        }
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i + i3] = bigInteger.charAt(i3);
        }
        java.lang.String str = new java.lang.String(cArr);
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(obj);
        stringBuffer.append(android.media.MediaMetrics.SEPARATOR);
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal)) {
            return false;
        }
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal = (com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal) obj;
        return this.bigInt.equals(simpleBigDecimal.bigInt) && this.scale == simpleBigDecimal.scale;
    }

    public int hashCode() {
        return this.bigInt.hashCode() ^ this.scale;
    }
}
