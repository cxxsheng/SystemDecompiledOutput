package org.apache.commons.math.complex;

/* loaded from: classes3.dex */
public class Complex implements org.apache.commons.math.FieldElement<org.apache.commons.math.complex.Complex>, java.io.Serializable {
    private static final long serialVersionUID = -6195664516687396620L;
    private final double imaginary;
    private final transient boolean isInfinite;
    private final transient boolean isNaN;
    private final double real;
    public static final org.apache.commons.math.complex.Complex I = new org.apache.commons.math.complex.Complex(0.0d, 1.0d);
    public static final org.apache.commons.math.complex.Complex NaN = new org.apache.commons.math.complex.Complex(Double.NaN, Double.NaN);
    public static final org.apache.commons.math.complex.Complex INF = new org.apache.commons.math.complex.Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final org.apache.commons.math.complex.Complex ONE = new org.apache.commons.math.complex.Complex(1.0d, 0.0d);
    public static final org.apache.commons.math.complex.Complex ZERO = new org.apache.commons.math.complex.Complex(0.0d, 0.0d);

    public Complex(double d, double d2) {
        this.real = d;
        this.imaginary = d2;
        boolean z = true;
        this.isNaN = java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2);
        if (this.isNaN || (!java.lang.Double.isInfinite(d) && !java.lang.Double.isInfinite(d2))) {
            z = false;
        }
        this.isInfinite = z;
    }

    public double abs() {
        if (isNaN()) {
            return Double.NaN;
        }
        if (isInfinite()) {
            return Double.POSITIVE_INFINITY;
        }
        if (org.apache.commons.math.util.FastMath.abs(this.real) < org.apache.commons.math.util.FastMath.abs(this.imaginary)) {
            if (this.imaginary == 0.0d) {
                return org.apache.commons.math.util.FastMath.abs(this.real);
            }
            double d = this.real / this.imaginary;
            return org.apache.commons.math.util.FastMath.abs(this.imaginary) * org.apache.commons.math.util.FastMath.sqrt((d * d) + 1.0d);
        }
        if (this.real == 0.0d) {
            return org.apache.commons.math.util.FastMath.abs(this.imaginary);
        }
        double d2 = this.imaginary / this.real;
        return org.apache.commons.math.util.FastMath.abs(this.real) * org.apache.commons.math.util.FastMath.sqrt((d2 * d2) + 1.0d);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.complex.Complex add(org.apache.commons.math.complex.Complex complex) {
        return createComplex(this.real + complex.getReal(), this.imaginary + complex.getImaginary());
    }

    public org.apache.commons.math.complex.Complex conjugate() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(this.real, -this.imaginary);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.complex.Complex divide(org.apache.commons.math.complex.Complex complex) {
        if (isNaN() || complex.isNaN()) {
            return NaN;
        }
        double real = complex.getReal();
        double imaginary = complex.getImaginary();
        if (real == 0.0d && imaginary == 0.0d) {
            return NaN;
        }
        if (complex.isInfinite() && !isInfinite()) {
            return ZERO;
        }
        if (org.apache.commons.math.util.FastMath.abs(real) < org.apache.commons.math.util.FastMath.abs(imaginary)) {
            double d = real / imaginary;
            double d2 = (real * d) + imaginary;
            return createComplex(((this.real * d) + this.imaginary) / d2, ((this.imaginary * d) - this.real) / d2);
        }
        double d3 = imaginary / real;
        double d4 = (imaginary * d3) + real;
        return createComplex(((this.imaginary * d3) + this.real) / d4, (this.imaginary - (this.real * d3)) / d4);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.complex.Complex)) {
            return false;
        }
        org.apache.commons.math.complex.Complex complex = (org.apache.commons.math.complex.Complex) obj;
        if (complex.isNaN()) {
            return isNaN();
        }
        return this.real == complex.real && this.imaginary == complex.imaginary;
    }

    public int hashCode() {
        if (isNaN()) {
            return 7;
        }
        return ((org.apache.commons.math.util.MathUtils.hash(this.imaginary) * 17) + org.apache.commons.math.util.MathUtils.hash(this.real)) * 37;
    }

    public double getImaginary() {
        return this.imaginary;
    }

    public double getReal() {
        return this.real;
    }

    public boolean isNaN() {
        return this.isNaN;
    }

    public boolean isInfinite() {
        return this.isInfinite;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.complex.Complex multiply(org.apache.commons.math.complex.Complex complex) {
        if (isNaN() || complex.isNaN()) {
            return NaN;
        }
        if (java.lang.Double.isInfinite(this.real) || java.lang.Double.isInfinite(this.imaginary) || java.lang.Double.isInfinite(complex.real) || java.lang.Double.isInfinite(complex.imaginary)) {
            return INF;
        }
        return createComplex((this.real * complex.real) - (this.imaginary * complex.imaginary), (this.real * complex.imaginary) + (this.imaginary * complex.real));
    }

    public org.apache.commons.math.complex.Complex multiply(double d) {
        if (isNaN() || java.lang.Double.isNaN(d)) {
            return NaN;
        }
        if (java.lang.Double.isInfinite(this.real) || java.lang.Double.isInfinite(this.imaginary) || java.lang.Double.isInfinite(d)) {
            return INF;
        }
        return createComplex(this.real * d, this.imaginary * d);
    }

    public org.apache.commons.math.complex.Complex negate() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(-this.real, -this.imaginary);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.complex.Complex subtract(org.apache.commons.math.complex.Complex complex) {
        if (isNaN() || complex.isNaN()) {
            return NaN;
        }
        return createComplex(this.real - complex.getReal(), this.imaginary - complex.getImaginary());
    }

    public org.apache.commons.math.complex.Complex acos() {
        if (isNaN()) {
            return NaN;
        }
        return add(sqrt1z().multiply(I)).log().multiply(I.negate());
    }

    public org.apache.commons.math.complex.Complex asin() {
        if (isNaN()) {
            return NaN;
        }
        return sqrt1z().add(multiply(I)).log().multiply(I.negate());
    }

    public org.apache.commons.math.complex.Complex atan() {
        if (isNaN()) {
            return NaN;
        }
        return add(I).divide(I.subtract(this)).log().multiply(I.divide(createComplex(2.0d, 0.0d)));
    }

    public org.apache.commons.math.complex.Complex cos() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(org.apache.commons.math.util.FastMath.cos(this.real) * org.apache.commons.math.util.MathUtils.cosh(this.imaginary), (-org.apache.commons.math.util.FastMath.sin(this.real)) * org.apache.commons.math.util.MathUtils.sinh(this.imaginary));
    }

    public org.apache.commons.math.complex.Complex cosh() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(org.apache.commons.math.util.MathUtils.cosh(this.real) * org.apache.commons.math.util.FastMath.cos(this.imaginary), org.apache.commons.math.util.MathUtils.sinh(this.real) * org.apache.commons.math.util.FastMath.sin(this.imaginary));
    }

    public org.apache.commons.math.complex.Complex exp() {
        if (isNaN()) {
            return NaN;
        }
        double exp = org.apache.commons.math.util.FastMath.exp(this.real);
        return createComplex(org.apache.commons.math.util.FastMath.cos(this.imaginary) * exp, exp * org.apache.commons.math.util.FastMath.sin(this.imaginary));
    }

    public org.apache.commons.math.complex.Complex log() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(org.apache.commons.math.util.FastMath.log(abs()), org.apache.commons.math.util.FastMath.atan2(this.imaginary, this.real));
    }

    public org.apache.commons.math.complex.Complex pow(org.apache.commons.math.complex.Complex complex) {
        if (complex == null) {
            throw new java.lang.NullPointerException();
        }
        return log().multiply(complex).exp();
    }

    public org.apache.commons.math.complex.Complex sin() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(org.apache.commons.math.util.FastMath.sin(this.real) * org.apache.commons.math.util.MathUtils.cosh(this.imaginary), org.apache.commons.math.util.FastMath.cos(this.real) * org.apache.commons.math.util.MathUtils.sinh(this.imaginary));
    }

    public org.apache.commons.math.complex.Complex sinh() {
        if (isNaN()) {
            return NaN;
        }
        return createComplex(org.apache.commons.math.util.MathUtils.sinh(this.real) * org.apache.commons.math.util.FastMath.cos(this.imaginary), org.apache.commons.math.util.MathUtils.cosh(this.real) * org.apache.commons.math.util.FastMath.sin(this.imaginary));
    }

    public org.apache.commons.math.complex.Complex sqrt() {
        if (isNaN()) {
            return NaN;
        }
        if (this.real == 0.0d && this.imaginary == 0.0d) {
            return createComplex(0.0d, 0.0d);
        }
        double sqrt = org.apache.commons.math.util.FastMath.sqrt((org.apache.commons.math.util.FastMath.abs(this.real) + abs()) / 2.0d);
        if (this.real >= 0.0d) {
            return createComplex(sqrt, this.imaginary / (2.0d * sqrt));
        }
        return createComplex(org.apache.commons.math.util.FastMath.abs(this.imaginary) / (2.0d * sqrt), org.apache.commons.math.util.MathUtils.indicator(this.imaginary) * sqrt);
    }

    public org.apache.commons.math.complex.Complex sqrt1z() {
        return createComplex(1.0d, 0.0d).subtract(multiply(this)).sqrt();
    }

    public org.apache.commons.math.complex.Complex tan() {
        if (isNaN()) {
            return NaN;
        }
        double d = this.real * 2.0d;
        double d2 = this.imaginary * 2.0d;
        double cos = org.apache.commons.math.util.FastMath.cos(d) + org.apache.commons.math.util.MathUtils.cosh(d2);
        return createComplex(org.apache.commons.math.util.FastMath.sin(d) / cos, org.apache.commons.math.util.MathUtils.sinh(d2) / cos);
    }

    public org.apache.commons.math.complex.Complex tanh() {
        if (isNaN()) {
            return NaN;
        }
        double d = this.real * 2.0d;
        double d2 = this.imaginary * 2.0d;
        double cosh = org.apache.commons.math.util.MathUtils.cosh(d) + org.apache.commons.math.util.FastMath.cos(d2);
        return createComplex(org.apache.commons.math.util.MathUtils.sinh(d) / cosh, org.apache.commons.math.util.FastMath.sin(d2) / cosh);
    }

    public double getArgument() {
        return org.apache.commons.math.util.FastMath.atan2(getImaginary(), getReal());
    }

    public java.util.List<org.apache.commons.math.complex.Complex> nthRoot(int i) throws java.lang.IllegalArgumentException {
        if (i <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_COMPUTE_NTH_ROOT_FOR_NEGATIVE_N, java.lang.Integer.valueOf(i));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (isNaN()) {
            arrayList.add(NaN);
            return arrayList;
        }
        if (isInfinite()) {
            arrayList.add(INF);
            return arrayList;
        }
        double d = i;
        double pow = org.apache.commons.math.util.FastMath.pow(abs(), 1.0d / d);
        double argument = getArgument() / d;
        double d2 = 6.283185307179586d / d;
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(createComplex(org.apache.commons.math.util.FastMath.cos(argument) * pow, org.apache.commons.math.util.FastMath.sin(argument) * pow));
            argument += d2;
        }
        return arrayList;
    }

    protected org.apache.commons.math.complex.Complex createComplex(double d, double d2) {
        return new org.apache.commons.math.complex.Complex(d, d2);
    }

    protected final java.lang.Object readResolve() {
        return createComplex(this.real, this.imaginary);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.Field<org.apache.commons.math.complex.Complex> getField() {
        return org.apache.commons.math.complex.ComplexField.getInstance();
    }
}
