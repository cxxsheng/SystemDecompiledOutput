package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class PolynomialFitter {
    private final int degree;
    private final org.apache.commons.math.optimization.fitting.CurveFitter fitter;

    public PolynomialFitter(int i, org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer differentiableMultivariateVectorialOptimizer) {
        this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(differentiableMultivariateVectorialOptimizer);
        this.degree = i;
    }

    public void addObservedPoint(double d, double d2, double d3) {
        this.fitter.addObservedPoint(d, d2, d3);
    }

    public void clearObservations() {
        this.fitter.clearObservations();
    }

    public org.apache.commons.math.analysis.polynomials.PolynomialFunction fit() throws org.apache.commons.math.optimization.OptimizationException {
        try {
            return new org.apache.commons.math.analysis.polynomials.PolynomialFunction(this.fitter.fit(new org.apache.commons.math.optimization.fitting.PolynomialFitter.ParametricPolynomial(), new double[this.degree + 1]));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static class ParametricPolynomial implements org.apache.commons.math.optimization.fitting.ParametricRealFunction {
        private ParametricPolynomial() {
        }

        @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
        public double[] gradient(double d, double[] dArr) {
            double[] dArr2 = new double[dArr.length];
            double d2 = 1.0d;
            for (int i = 0; i < dArr.length; i++) {
                dArr2[i] = d2;
                d2 *= d;
            }
            return dArr2;
        }

        @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
        public double value(double d, double[] dArr) {
            double d2 = 0.0d;
            for (int length = dArr.length - 1; length >= 0; length--) {
                d2 = (d2 * d) + dArr[length];
            }
            return d2;
        }
    }
}
