package org.apache.commons.math.transform;

/* loaded from: classes3.dex */
public class FastFourierTransformer implements java.io.Serializable {
    static final long serialVersionUID = 5138259215438106000L;
    private org.apache.commons.math.transform.FastFourierTransformer.RootsOfUnity roots = new org.apache.commons.math.transform.FastFourierTransformer.RootsOfUnity();

    public org.apache.commons.math.complex.Complex[] transform(double[] dArr) throws java.lang.IllegalArgumentException {
        return fft(dArr, false);
    }

    public org.apache.commons.math.complex.Complex[] transform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return fft(sample(univariateRealFunction, d, d2, i), false);
    }

    public org.apache.commons.math.complex.Complex[] transform(org.apache.commons.math.complex.Complex[] complexArr) throws java.lang.IllegalArgumentException {
        this.roots.computeOmega(complexArr.length);
        return fft(complexArr);
    }

    public org.apache.commons.math.complex.Complex[] transform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return scaleArray(fft(dArr, false), 1.0d / org.apache.commons.math.util.FastMath.sqrt(dArr.length));
    }

    public org.apache.commons.math.complex.Complex[] transform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return scaleArray(fft(sample(univariateRealFunction, d, d2, i), false), 1.0d / org.apache.commons.math.util.FastMath.sqrt(i));
    }

    public org.apache.commons.math.complex.Complex[] transform2(org.apache.commons.math.complex.Complex[] complexArr) throws java.lang.IllegalArgumentException {
        this.roots.computeOmega(complexArr.length);
        return scaleArray(fft(complexArr), 1.0d / org.apache.commons.math.util.FastMath.sqrt(complexArr.length));
    }

    public org.apache.commons.math.complex.Complex[] inversetransform(double[] dArr) throws java.lang.IllegalArgumentException {
        return scaleArray(fft(dArr, true), 1.0d / dArr.length);
    }

    public org.apache.commons.math.complex.Complex[] inversetransform(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return scaleArray(fft(sample(univariateRealFunction, d, d2, i), true), 1.0d / i);
    }

    public org.apache.commons.math.complex.Complex[] inversetransform(org.apache.commons.math.complex.Complex[] complexArr) throws java.lang.IllegalArgumentException {
        this.roots.computeOmega(-complexArr.length);
        return scaleArray(fft(complexArr), 1.0d / complexArr.length);
    }

    public org.apache.commons.math.complex.Complex[] inversetransform2(double[] dArr) throws java.lang.IllegalArgumentException {
        return scaleArray(fft(dArr, true), 1.0d / org.apache.commons.math.util.FastMath.sqrt(dArr.length));
    }

    public org.apache.commons.math.complex.Complex[] inversetransform2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return scaleArray(fft(sample(univariateRealFunction, d, d2, i), true), 1.0d / org.apache.commons.math.util.FastMath.sqrt(i));
    }

    public org.apache.commons.math.complex.Complex[] inversetransform2(org.apache.commons.math.complex.Complex[] complexArr) throws java.lang.IllegalArgumentException {
        this.roots.computeOmega(-complexArr.length);
        return scaleArray(fft(complexArr), 1.0d / org.apache.commons.math.util.FastMath.sqrt(complexArr.length));
    }

    protected org.apache.commons.math.complex.Complex[] fft(double[] dArr, boolean z) throws java.lang.IllegalArgumentException {
        verifyDataSet(dArr);
        org.apache.commons.math.complex.Complex[] complexArr = new org.apache.commons.math.complex.Complex[dArr.length];
        if (dArr.length == 1) {
            complexArr[0] = new org.apache.commons.math.complex.Complex(dArr[0], 0.0d);
            return complexArr;
        }
        int length = dArr.length >> 1;
        org.apache.commons.math.complex.Complex[] complexArr2 = new org.apache.commons.math.complex.Complex[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            complexArr2[i] = new org.apache.commons.math.complex.Complex(dArr[i2], dArr[i2 + 1]);
        }
        this.roots.computeOmega(z ? -length : length);
        org.apache.commons.math.complex.Complex[] fft = fft(complexArr2);
        this.roots.computeOmega(z ? length * (-2) : length * 2);
        complexArr[0] = new org.apache.commons.math.complex.Complex((fft[0].getReal() + fft[0].getImaginary()) * 2.0d, 0.0d);
        complexArr[length] = new org.apache.commons.math.complex.Complex((fft[0].getReal() - fft[0].getImaginary()) * 2.0d, 0.0d);
        for (int i3 = 1; i3 < length; i3++) {
            org.apache.commons.math.complex.Complex conjugate = fft[length - i3].conjugate();
            complexArr[i3] = fft[i3].add(conjugate).subtract(fft[i3].subtract(conjugate).multiply(new org.apache.commons.math.complex.Complex(-this.roots.getOmegaImaginary(i3), this.roots.getOmegaReal(i3))));
            complexArr[(length * 2) - i3] = complexArr[i3].conjugate();
        }
        return scaleArray(complexArr, 0.5d);
    }

    protected org.apache.commons.math.complex.Complex[] fft(org.apache.commons.math.complex.Complex[] complexArr) throws java.lang.IllegalArgumentException {
        int length = complexArr.length;
        org.apache.commons.math.complex.Complex[] complexArr2 = new org.apache.commons.math.complex.Complex[length];
        verifyDataSet(complexArr);
        int i = 0;
        if (length == 1) {
            complexArr2[0] = complexArr[0];
            return complexArr2;
        }
        if (length == 2) {
            complexArr2[0] = complexArr[0].add(complexArr[1]);
            complexArr2[1] = complexArr[0].subtract(complexArr[1]);
            return complexArr2;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            complexArr2[i3] = complexArr[i2];
            int i4 = length >> 1;
            while (i2 >= i4 && i4 > 0) {
                i2 -= i4;
                i4 >>= 1;
            }
            i2 += i4;
        }
        for (int i5 = 0; i5 < length; i5 += 4) {
            int i6 = i5 + 1;
            org.apache.commons.math.complex.Complex add = complexArr2[i5].add(complexArr2[i6]);
            int i7 = i5 + 2;
            int i8 = i5 + 3;
            org.apache.commons.math.complex.Complex add2 = complexArr2[i7].add(complexArr2[i8]);
            org.apache.commons.math.complex.Complex subtract = complexArr2[i5].subtract(complexArr2[i6]);
            org.apache.commons.math.complex.Complex subtract2 = complexArr2[i7].subtract(complexArr2[i8]);
            org.apache.commons.math.complex.Complex add3 = subtract.add(subtract2.multiply(org.apache.commons.math.complex.Complex.I));
            org.apache.commons.math.complex.Complex subtract3 = subtract.subtract(subtract2.multiply(org.apache.commons.math.complex.Complex.I));
            complexArr2[i5] = add.add(add2);
            complexArr2[i7] = add.subtract(add2);
            complexArr2[i6] = this.roots.isForward() ? subtract3 : add3;
            if (!this.roots.isForward()) {
                add3 = subtract3;
            }
            complexArr2[i8] = add3;
        }
        int i9 = 4;
        while (i9 < length) {
            int i10 = i9 << 1;
            int i11 = length / i10;
            int i12 = i;
            while (i12 < length) {
                int i13 = i;
                while (i13 < i9) {
                    int i14 = i13 * i11;
                    double omegaReal = this.roots.getOmegaReal(i14);
                    double omegaImaginary = this.roots.getOmegaImaginary(i14);
                    int i15 = i9 + i12 + i13;
                    org.apache.commons.math.complex.Complex complex = new org.apache.commons.math.complex.Complex((complexArr2[i15].getReal() * omegaReal) - (complexArr2[i15].getImaginary() * omegaImaginary), (complexArr2[i15].getReal() * omegaImaginary) + (complexArr2[i15].getImaginary() * omegaReal));
                    int i16 = i12 + i13;
                    complexArr2[i15] = complexArr2[i16].subtract(complex);
                    complexArr2[i16] = complexArr2[i16].add(complex);
                    i13++;
                    i10 = i10;
                }
                i12 += i10;
                i = 0;
            }
            i9 = i10;
            i = 0;
        }
        return complexArr2;
    }

    public static double[] sample(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        if (i <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_NUMBER_OF_SAMPLES, java.lang.Integer.valueOf(i));
        }
        verifyInterval(d, d2);
        double[] dArr = new double[i];
        double d3 = (d2 - d) / i;
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = univariateRealFunction.value((i2 * d3) + d);
        }
        return dArr;
    }

    public static double[] scaleArray(double[] dArr, double d) {
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = dArr[i] * d;
        }
        return dArr;
    }

    public static org.apache.commons.math.complex.Complex[] scaleArray(org.apache.commons.math.complex.Complex[] complexArr, double d) {
        for (int i = 0; i < complexArr.length; i++) {
            complexArr[i] = new org.apache.commons.math.complex.Complex(complexArr[i].getReal() * d, complexArr[i].getImaginary() * d);
        }
        return complexArr;
    }

    public static boolean isPowerOf2(long j) {
        return j > 0 && (j & (j - 1)) == 0;
    }

    public static void verifyDataSet(double[] dArr) throws java.lang.IllegalArgumentException {
        if (!isPowerOf2(dArr.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, java.lang.Integer.valueOf(dArr.length));
        }
    }

    public static void verifyDataSet(java.lang.Object[] objArr) throws java.lang.IllegalArgumentException {
        if (!isPowerOf2(objArr.length)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, java.lang.Integer.valueOf(objArr.length));
        }
    }

    public static void verifyInterval(double d, double d2) throws java.lang.IllegalArgumentException {
        if (d >= d2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2));
        }
    }

    public java.lang.Object mdfft(java.lang.Object obj, boolean z) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix multiDimensionalComplexMatrix = (org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix) new org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix(obj).clone();
        int[] dimensionSizes = multiDimensionalComplexMatrix.getDimensionSizes();
        for (int i = 0; i < dimensionSizes.length; i++) {
            mdfft(multiDimensionalComplexMatrix, z, i, new int[0]);
        }
        return multiDimensionalComplexMatrix.getArray();
    }

    private void mdfft(org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix multiDimensionalComplexMatrix, boolean z, int i, int[] iArr) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.complex.Complex[] inversetransform2;
        int[] dimensionSizes = multiDimensionalComplexMatrix.getDimensionSizes();
        int i2 = 0;
        if (iArr.length == dimensionSizes.length) {
            org.apache.commons.math.complex.Complex[] complexArr = new org.apache.commons.math.complex.Complex[dimensionSizes[i]];
            for (int i3 = 0; i3 < dimensionSizes[i]; i3++) {
                iArr[i] = i3;
                complexArr[i3] = multiDimensionalComplexMatrix.get(iArr);
            }
            if (z) {
                inversetransform2 = transform2(complexArr);
            } else {
                inversetransform2 = inversetransform2(complexArr);
            }
            while (i2 < dimensionSizes[i]) {
                iArr[i] = i2;
                multiDimensionalComplexMatrix.set(inversetransform2[i2], iArr);
                i2++;
            }
            return;
        }
        int[] iArr2 = new int[iArr.length + 1];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        if (iArr.length == i) {
            iArr2[i] = 0;
            mdfft(multiDimensionalComplexMatrix, z, i, iArr2);
        } else {
            while (i2 < dimensionSizes[iArr.length]) {
                iArr2[iArr.length] = i2;
                mdfft(multiDimensionalComplexMatrix, z, i, iArr2);
                i2++;
            }
        }
    }

    private static class MultiDimensionalComplexMatrix implements java.lang.Cloneable {
        protected int[] dimensionSize;
        protected java.lang.Object multiDimensionalComplexArray;

        public MultiDimensionalComplexMatrix(java.lang.Object obj) {
            this.multiDimensionalComplexArray = obj;
            int i = 0;
            for (java.lang.Object obj2 = obj; obj2 instanceof java.lang.Object[]; obj2 = obj2[0]) {
                i++;
            }
            this.dimensionSize = new int[i];
            int i2 = 0;
            while (obj instanceof java.lang.Object[]) {
                java.lang.Object[] objArr = obj;
                this.dimensionSize[i2] = objArr.length;
                obj = objArr[0];
                i2++;
            }
        }

        public org.apache.commons.math.complex.Complex get(int... iArr) throws java.lang.IllegalArgumentException {
            if (iArr == null) {
                if (this.dimensionSize.length > 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, 0, java.lang.Integer.valueOf(this.dimensionSize.length));
                }
                return null;
            }
            if (iArr.length != this.dimensionSize.length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(iArr.length), java.lang.Integer.valueOf(this.dimensionSize.length));
            }
            java.lang.Object obj = this.multiDimensionalComplexArray;
            for (int i = 0; i < this.dimensionSize.length; i++) {
                obj = ((java.lang.Object[]) obj)[iArr[i]];
            }
            return (org.apache.commons.math.complex.Complex) obj;
        }

        public org.apache.commons.math.complex.Complex set(org.apache.commons.math.complex.Complex complex, int... iArr) throws java.lang.IllegalArgumentException {
            if (iArr == null) {
                if (this.dimensionSize.length > 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, 0, java.lang.Integer.valueOf(this.dimensionSize.length));
                }
                return null;
            }
            if (iArr.length != this.dimensionSize.length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(iArr.length), java.lang.Integer.valueOf(this.dimensionSize.length));
            }
            java.lang.Object[] objArr = (java.lang.Object[]) this.multiDimensionalComplexArray;
            for (int i = 0; i < this.dimensionSize.length - 1; i++) {
                objArr = (java.lang.Object[]) objArr[iArr[i]];
            }
            org.apache.commons.math.complex.Complex complex2 = (org.apache.commons.math.complex.Complex) objArr[iArr[this.dimensionSize.length - 1]];
            objArr[iArr[this.dimensionSize.length - 1]] = complex;
            return complex2;
        }

        public int[] getDimensionSizes() {
            return (int[]) this.dimensionSize.clone();
        }

        public java.lang.Object getArray() {
            return this.multiDimensionalComplexArray;
        }

        public java.lang.Object clone() {
            org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix multiDimensionalComplexMatrix = new org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix(java.lang.reflect.Array.newInstance((java.lang.Class<?>) org.apache.commons.math.complex.Complex.class, this.dimensionSize));
            clone(multiDimensionalComplexMatrix);
            return multiDimensionalComplexMatrix;
        }

        private void clone(org.apache.commons.math.transform.FastFourierTransformer.MultiDimensionalComplexMatrix multiDimensionalComplexMatrix) {
            int[] iArr = new int[this.dimensionSize.length];
            int i = 1;
            for (int i2 = 0; i2 < this.dimensionSize.length; i2++) {
                i *= this.dimensionSize[i2];
            }
            int[][] iArr2 = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, i, this.dimensionSize.length);
            for (int[] iArr3 : iArr2) {
                java.lang.System.arraycopy(iArr, 0, iArr3, 0, this.dimensionSize.length);
                for (int i3 = 0; i3 < this.dimensionSize.length; i3++) {
                    iArr[i3] = iArr[i3] + 1;
                    if (iArr[i3] < this.dimensionSize[i3]) {
                        break;
                    }
                    iArr[i3] = 0;
                }
            }
            for (int[] iArr4 : iArr2) {
                multiDimensionalComplexMatrix.set(get(iArr4), iArr4);
            }
        }
    }

    private static class RootsOfUnity implements java.io.Serializable {
        private static final long serialVersionUID = 6404784357747329667L;
        private int omegaCount = 0;
        private double[] omegaReal = null;
        private double[] omegaImaginaryForward = null;
        private double[] omegaImaginaryInverse = null;
        private boolean isForward = true;

        public synchronized boolean isForward() throws java.lang.IllegalStateException {
            if (this.omegaCount == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new java.lang.Object[0]);
            }
            return this.isForward;
        }

        public synchronized void computeOmega(int i) throws java.lang.IllegalArgumentException {
            boolean z;
            try {
                if (i == 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_COMPUTE_0TH_ROOT_OF_UNITY, new java.lang.Object[0]);
                }
                if (i <= 0) {
                    z = false;
                } else {
                    z = true;
                }
                this.isForward = z;
                int abs = org.apache.commons.math.util.FastMath.abs(i);
                if (abs == this.omegaCount) {
                    return;
                }
                double d = 6.283185307179586d / abs;
                double cos = org.apache.commons.math.util.FastMath.cos(d);
                double sin = org.apache.commons.math.util.FastMath.sin(d);
                this.omegaReal = new double[abs];
                this.omegaImaginaryForward = new double[abs];
                this.omegaImaginaryInverse = new double[abs];
                this.omegaReal[0] = 1.0d;
                this.omegaImaginaryForward[0] = 0.0d;
                this.omegaImaginaryInverse[0] = 0.0d;
                for (int i2 = 1; i2 < abs; i2++) {
                    int i3 = i2 - 1;
                    this.omegaReal[i2] = (this.omegaReal[i3] * cos) + (this.omegaImaginaryForward[i3] * sin);
                    this.omegaImaginaryForward[i2] = (this.omegaImaginaryForward[i3] * cos) - (this.omegaReal[i3] * sin);
                    this.omegaImaginaryInverse[i2] = -this.omegaImaginaryForward[i2];
                }
                this.omegaCount = abs;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        public synchronized double getOmegaReal(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
            if (this.omegaCount == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new java.lang.Object[0]);
            }
            if (i < 0 || i >= this.omegaCount) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(this.omegaCount - 1));
            }
            return this.omegaReal[i];
        }

        public synchronized double getOmegaImaginary(int i) throws java.lang.IllegalStateException, java.lang.IllegalArgumentException {
            try {
                if (this.omegaCount == 0) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new java.lang.Object[0]);
                }
                if (i < 0 || i >= this.omegaCount) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(this.omegaCount - 1));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return this.isForward ? this.omegaImaginaryForward[i] : this.omegaImaginaryInverse[i];
        }
    }
}
