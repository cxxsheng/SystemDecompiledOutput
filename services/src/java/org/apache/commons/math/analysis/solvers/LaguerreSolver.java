package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class LaguerreSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {

    @java.lang.Deprecated
    private final org.apache.commons.math.analysis.polynomials.PolynomialFunction p;

    @java.lang.Deprecated
    public LaguerreSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) throws java.lang.IllegalArgumentException {
        super(univariateRealFunction, 100, 1.0E-6d);
        if (univariateRealFunction instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction) {
            this.p = (org.apache.commons.math.analysis.polynomials.PolynomialFunction) univariateRealFunction;
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION_NOT_POLYNOMIAL, new java.lang.Object[0]);
    }

    @java.lang.Deprecated
    public LaguerreSolver() {
        super(100, 1.0E-6d);
        this.p = null;
    }

    @java.lang.Deprecated
    public org.apache.commons.math.analysis.polynomials.PolynomialFunction getPolynomialFunction() {
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(this.p.getCoefficients());
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.p, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.p, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        if (univariateRealFunction.value(d) == 0.0d) {
            return d;
        }
        if (univariateRealFunction.value(d2) == 0.0d) {
            return d2;
        }
        if (univariateRealFunction.value(d3) == 0.0d) {
            return d3;
        }
        verifyBracketing(d, d2, univariateRealFunction);
        verifySequence(d, d3, d2);
        if (isBracketing(d, d3, univariateRealFunction)) {
            return solve(univariateRealFunction, d, d3);
        }
        return solve(univariateRealFunction, d3, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        if (!(univariateRealFunction instanceof org.apache.commons.math.analysis.polynomials.PolynomialFunction)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION_NOT_POLYNOMIAL, new java.lang.Object[0]);
        }
        if (univariateRealFunction.value(d) == 0.0d) {
            return d;
        }
        if (univariateRealFunction.value(d2) == 0.0d) {
            return d2;
        }
        verifyBracketing(d, d2, univariateRealFunction);
        double[] coefficients = ((org.apache.commons.math.analysis.polynomials.PolynomialFunction) univariateRealFunction).getCoefficients();
        org.apache.commons.math.complex.Complex[] complexArr = new org.apache.commons.math.complex.Complex[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            complexArr[i] = new org.apache.commons.math.complex.Complex(coefficients[i], 0.0d);
        }
        org.apache.commons.math.complex.Complex complex = new org.apache.commons.math.complex.Complex((d + d2) * 0.5d, 0.0d);
        org.apache.commons.math.complex.Complex solve = solve(complexArr, complex);
        if (isRootOK(d, d2, solve)) {
            setResult(solve.getReal(), this.iterationCount);
            return this.result;
        }
        org.apache.commons.math.complex.Complex[] solveAll = solveAll(complexArr, complex);
        for (int i2 = 0; i2 < solveAll.length; i2++) {
            if (isRootOK(d, d2, solveAll[i2])) {
                setResult(solveAll[i2].getReal(), this.iterationCount);
                return this.result;
            }
        }
        throw new org.apache.commons.math.ConvergenceException();
    }

    protected boolean isRootOK(double d, double d2, org.apache.commons.math.complex.Complex complex) {
        return isSequence(d, complex.getReal(), d2) && (org.apache.commons.math.util.FastMath.abs(complex.getImaginary()) <= org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * complex.abs(), this.absoluteAccuracy) || complex.abs() <= this.functionValueAccuracy);
    }

    @java.lang.Deprecated
    public org.apache.commons.math.complex.Complex[] solveAll(double[] dArr, double d) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        int length = dArr.length;
        org.apache.commons.math.complex.Complex[] complexArr = new org.apache.commons.math.complex.Complex[length];
        org.apache.commons.math.complex.Complex complex = new org.apache.commons.math.complex.Complex(d, 0.0d);
        for (int i = 0; i < length; i++) {
            complexArr[i] = new org.apache.commons.math.complex.Complex(dArr[i], 0.0d);
        }
        return solveAll(complexArr, complex);
    }

    @java.lang.Deprecated
    public org.apache.commons.math.complex.Complex[] solveAll(org.apache.commons.math.complex.Complex[] complexArr, org.apache.commons.math.complex.Complex complex) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        int length = complexArr.length - 1;
        if (length < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NON_POSITIVE_POLYNOMIAL_DEGREE, java.lang.Integer.valueOf(length));
        }
        org.apache.commons.math.complex.Complex[] complexArr2 = new org.apache.commons.math.complex.Complex[length + 1];
        for (int i = 0; i <= length; i++) {
            complexArr2[i] = complexArr[i];
        }
        org.apache.commons.math.complex.Complex[] complexArr3 = new org.apache.commons.math.complex.Complex[length];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = length - i3;
            int i5 = i4 + 1;
            org.apache.commons.math.complex.Complex[] complexArr4 = new org.apache.commons.math.complex.Complex[i5];
            java.lang.System.arraycopy(complexArr2, 0, complexArr4, 0, i5);
            complexArr3[i3] = solve(complexArr4, complex);
            org.apache.commons.math.complex.Complex complex2 = complexArr2[i4];
            for (int i6 = i4 - 1; i6 >= 0; i6--) {
                org.apache.commons.math.complex.Complex complex3 = complexArr2[i6];
                complexArr2[i6] = complex2;
                complex2 = complex3.add(complex2.multiply(complexArr3[i3]));
            }
            i2 += this.iterationCount;
        }
        this.resultComputed = true;
        this.iterationCount = i2;
        return complexArr3;
    }

    @java.lang.Deprecated
    public org.apache.commons.math.complex.Complex solve(org.apache.commons.math.complex.Complex[] complexArr, org.apache.commons.math.complex.Complex complex) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        long j;
        org.apache.commons.math.complex.Complex[] complexArr2 = complexArr;
        int length = complexArr2.length - 1;
        if (length < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NON_POSITIVE_POLYNOMIAL_DEGREE, java.lang.Integer.valueOf(length));
        }
        double d = 0.0d;
        org.apache.commons.math.complex.Complex complex2 = new org.apache.commons.math.complex.Complex(length, 0.0d);
        int i = length - 1;
        org.apache.commons.math.complex.Complex complex3 = new org.apache.commons.math.complex.Complex(i, 0.0d);
        int i2 = 1;
        org.apache.commons.math.complex.Complex complex4 = new org.apache.commons.math.complex.Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        org.apache.commons.math.complex.Complex complex5 = complex;
        while (i2 <= this.maximalIterationCount) {
            org.apache.commons.math.complex.Complex complex6 = complexArr2[length];
            org.apache.commons.math.complex.Complex complex7 = org.apache.commons.math.complex.Complex.ZERO;
            org.apache.commons.math.complex.Complex complex8 = org.apache.commons.math.complex.Complex.ZERO;
            for (int i3 = i; i3 >= 0; i3--) {
                complex8 = complex7.add(complex5.multiply(complex8));
                complex7 = complex6.add(complex5.multiply(complex7));
                complex6 = complexArr2[i3].add(complex5.multiply(complex6));
            }
            int i4 = length;
            org.apache.commons.math.complex.Complex multiply = complex8.multiply(new org.apache.commons.math.complex.Complex(2.0d, d));
            if (complex5.subtract(complex4).abs() <= org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * complex5.abs(), this.absoluteAccuracy)) {
                this.resultComputed = true;
                this.iterationCount = i2;
                return complex5;
            }
            if (complex6.abs() <= this.functionValueAccuracy) {
                this.resultComputed = true;
                this.iterationCount = i2;
                return complex5;
            }
            org.apache.commons.math.complex.Complex divide = complex7.divide(complex6);
            org.apache.commons.math.complex.Complex multiply2 = divide.multiply(divide);
            org.apache.commons.math.complex.Complex sqrt = complex3.multiply(complex2.multiply(multiply2.subtract(multiply.divide(complex6))).subtract(multiply2)).sqrt();
            org.apache.commons.math.complex.Complex add = divide.add(sqrt);
            org.apache.commons.math.complex.Complex subtract = divide.subtract(sqrt);
            if (add.abs() <= subtract.abs()) {
                add = subtract;
            }
            if (add.equals(new org.apache.commons.math.complex.Complex(0.0d, 0.0d))) {
                complex5 = complex5.add(new org.apache.commons.math.complex.Complex(this.absoluteAccuracy, this.absoluteAccuracy));
                complex4 = new org.apache.commons.math.complex.Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
                j = 9218868437227405312L;
            } else {
                j = 9218868437227405312L;
                complex4 = complex5;
                complex5 = complex5.subtract(complex2.divide(add));
            }
            i2++;
            d = 0.0d;
            complexArr2 = complexArr;
            length = i4;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }
}
