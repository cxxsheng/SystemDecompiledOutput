package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class SecantSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    @java.lang.Deprecated
    public SecantSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 100, 1.0E-6d);
    }

    @java.lang.Deprecated
    public SecantSolver() {
        super(100, 1.0E-6d);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        double d3;
        double d4;
        double d5 = d;
        clearResult();
        verifyInterval(d5, d2);
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction.value(d2);
        if (value * value2 >= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SAME_SIGN_AT_ENDPOINTS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(value), java.lang.Double.valueOf(value2));
        }
        double d6 = value2;
        double d7 = d5 - d2;
        int i = 0;
        double d8 = value;
        double d9 = d8;
        double d10 = d2;
        double d11 = d5;
        while (i < this.maximalIterationCount) {
            if (org.apache.commons.math.util.FastMath.abs(d8) >= org.apache.commons.math.util.FastMath.abs(d6)) {
                double d12 = d9;
                d9 = d6;
                d6 = d12;
                double d13 = d11;
                d11 = d10;
                d10 = d13;
            } else {
                d11 = d5;
                d5 = d10;
                d9 = d8;
                d8 = d6;
            }
            double d14 = d5;
            if (org.apache.commons.math.util.FastMath.abs(d9) <= this.functionValueAccuracy) {
                setResult(d11, i);
                return this.result;
            }
            double d15 = d8;
            if (org.apache.commons.math.util.FastMath.abs(d7) < org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * org.apache.commons.math.util.FastMath.abs(d11), this.absoluteAccuracy)) {
                setResult(d11, i);
                return this.result;
            }
            if (org.apache.commons.math.util.FastMath.abs(d9) > org.apache.commons.math.util.FastMath.abs(d6)) {
                d3 = d7 * 0.5d;
            } else {
                double d16 = (d10 - d11) / (1.0d - (d6 / d9));
                if (d16 / d7 <= 1.0d) {
                    d3 = d16;
                } else {
                    d3 = d7 * 0.5d;
                }
            }
            d10 = d11 + d3;
            d6 = univariateRealFunction.value(d10);
            double d17 = d15;
            if ((d6 > 0.0d) != (d17 > 0.0d)) {
                d4 = d14;
            } else {
                d4 = d11;
                d17 = d9;
            }
            d7 = d4 - d10;
            i++;
            double d18 = d4;
            d8 = d17;
            d5 = d18;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }
}
