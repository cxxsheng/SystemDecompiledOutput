package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class RiddersSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    @java.lang.Deprecated
    public RiddersSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 100, 1.0E-6d);
    }

    @java.lang.Deprecated
    public RiddersSolver() {
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
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        long j;
        double d3;
        double d4 = d2;
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction.value(d4);
        if (value == 0.0d) {
            return d;
        }
        if (value2 == 0.0d) {
            return d4;
        }
        verifyBracketing(d, d2, univariateRealFunction);
        double d5 = Double.POSITIVE_INFINITY;
        int i = 1;
        double d6 = d;
        while (i <= this.maximalIterationCount) {
            double d7 = (d6 + d4) * 0.5d;
            double value3 = univariateRealFunction.value(d7);
            double d8 = d4;
            if (org.apache.commons.math.util.FastMath.abs(value3) <= this.functionValueAccuracy) {
                setResult(d7, i);
                return this.result;
            }
            double sign = ((org.apache.commons.math.util.MathUtils.sign(value2) * org.apache.commons.math.util.MathUtils.sign(value3)) * (d7 - d6)) / org.apache.commons.math.util.FastMath.sqrt(1.0d - ((value * value2) / (value3 * value3)));
            double d9 = d6;
            double d10 = d7 - sign;
            double value4 = univariateRealFunction.value(d10);
            double d11 = d7;
            double d12 = value2;
            if (org.apache.commons.math.util.FastMath.abs(d10 - d5) <= org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * org.apache.commons.math.util.FastMath.abs(d10), this.absoluteAccuracy)) {
                setResult(d10, i);
                return this.result;
            }
            if (org.apache.commons.math.util.FastMath.abs(value4) <= this.functionValueAccuracy) {
                setResult(d10, i);
                return this.result;
            }
            if (sign <= 0.0d) {
                j = 0;
                if (org.apache.commons.math.util.MathUtils.sign(d12) + org.apache.commons.math.util.MathUtils.sign(value4) == 0.0d) {
                    d3 = d8;
                    d11 = d10;
                    value = value4;
                    value2 = d12;
                } else {
                    d3 = d10;
                    value = value3;
                    value2 = value4;
                }
            } else if (org.apache.commons.math.util.MathUtils.sign(value) + org.apache.commons.math.util.MathUtils.sign(value4) == 0.0d) {
                d11 = d9;
                d3 = d10;
                value2 = value4;
                j = 0;
            } else {
                value2 = value3;
                value = value4;
                d3 = d11;
                j = 0;
                d11 = d10;
            }
            i++;
            d4 = d3;
            d5 = d10;
            d6 = d11;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }
}
