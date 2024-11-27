package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class MullerSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    @java.lang.Deprecated
    public MullerSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 100, 1.0E-6d);
    }

    @java.lang.Deprecated
    public MullerSolver() {
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
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction.value(d2);
        double d3 = (d + d2) * 0.5d;
        double value3 = univariateRealFunction.value(d3);
        if (value == 0.0d) {
            return d;
        }
        if (value2 == 0.0d) {
            return d2;
        }
        double d4 = d3;
        verifyBracketing(d, d2, univariateRealFunction);
        double d5 = value3;
        double d6 = Double.POSITIVE_INFINITY;
        int i = 1;
        double d7 = value2;
        double d8 = value;
        double d9 = d2;
        double d10 = d;
        while (i <= this.maximalIterationCount) {
            double d11 = d4 - d10;
            double d12 = (d5 - d8) / d11;
            double d13 = d9 - d4;
            double d14 = d9 - d10;
            double d15 = (((d7 - d5) / d13) - d12) / d14;
            double d16 = d12 + (d11 * d15);
            double d17 = (d16 * d16) - ((4.0d * d5) * d15);
            double d18 = (-2.0d) * d5;
            double sqrt = d4 + (d18 / (d16 + org.apache.commons.math.util.FastMath.sqrt(d17)));
            int i2 = i;
            double sqrt2 = isSequence(d10, sqrt, d9) ? sqrt : d4 + (d18 / (d16 - org.apache.commons.math.util.FastMath.sqrt(d17)));
            double value4 = univariateRealFunction.value(sqrt2);
            double d19 = d8;
            if (org.apache.commons.math.util.FastMath.abs(sqrt2 - d6) <= org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * org.apache.commons.math.util.FastMath.abs(sqrt2), this.absoluteAccuracy)) {
                setResult(sqrt2, i2);
                return this.result;
            }
            if (org.apache.commons.math.util.FastMath.abs(value4) <= this.functionValueAccuracy) {
                setResult(sqrt2, i2);
                return this.result;
            }
            if (!((sqrt2 < d4 && d11 > d14 * 0.95d) || (sqrt2 > d4 && d13 > d14 * 0.95d) || sqrt2 == d4)) {
                if (sqrt2 >= d4) {
                    d10 = d4;
                }
                d8 = sqrt2 < d4 ? d19 : d5;
                if (sqrt2 <= d4) {
                    d9 = d4;
                }
                if (sqrt2 <= d4) {
                    d7 = d5;
                }
                d4 = sqrt2;
                d6 = d4;
                d5 = value4;
                j = 4602678819172646912L;
            } else {
                double d20 = (d10 + d9) * 0.5d;
                double value5 = univariateRealFunction.value(d20);
                if (org.apache.commons.math.util.MathUtils.sign(d19) + org.apache.commons.math.util.MathUtils.sign(value5) == 0.0d) {
                    d8 = d19;
                    d9 = d20;
                    d7 = value5;
                } else {
                    d10 = d20;
                    d8 = value5;
                }
                j = 4602678819172646912L;
                double d21 = (d10 + d9) * 0.5d;
                d4 = d21;
                d5 = univariateRealFunction.value(d21);
                d6 = Double.POSITIVE_INFINITY;
            }
            i = i2 + 1;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }

    @java.lang.Deprecated
    public double solve2(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve2(this.f, d, d2);
    }

    @java.lang.Deprecated
    public double solve2(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        double sqrt;
        double random;
        org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction2 = univariateRealFunction;
        double d3 = d2;
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction2.value(d3);
        double d4 = (d + d3) * 0.5d;
        double value3 = univariateRealFunction2.value(d4);
        if (value == 0.0d) {
            return d;
        }
        if (value2 == 0.0d) {
            return d3;
        }
        verifyBracketing(d, d2, univariateRealFunction);
        int i = 1;
        double d5 = d;
        double d6 = value;
        double d7 = value2;
        double d8 = Double.POSITIVE_INFINITY;
        double d9 = d3;
        while (i <= this.maximalIterationCount) {
            double d10 = d4 - d9;
            double d11 = d10 / (d9 - d5);
            double d12 = d11 + 1.0d;
            double d13 = ((value3 - (d12 * d7)) + (d11 * d6)) * d11;
            double d14 = ((((d11 * 2.0d) + 1.0d) * value3) - ((d12 * d12) * d7)) + (d11 * d11 * d6);
            double d15 = d12 * value3;
            double d16 = d14 * d14;
            double d17 = d16 - ((d13 * 4.0d) * d15);
            if (d17 >= 0.0d) {
                sqrt = d14 + org.apache.commons.math.util.FastMath.sqrt(d17);
                double sqrt2 = d14 - org.apache.commons.math.util.FastMath.sqrt(d17);
                if (org.apache.commons.math.util.FastMath.abs(sqrt) <= org.apache.commons.math.util.FastMath.abs(sqrt2)) {
                    sqrt = sqrt2;
                }
            } else {
                sqrt = org.apache.commons.math.util.FastMath.sqrt(d16 - d17);
            }
            if (sqrt != 0.0d) {
                double d18 = d4 - (((d15 * 2.0d) * d10) / sqrt);
                while (true) {
                    if (d18 != d9 && d18 != d4) {
                        break;
                    }
                    d18 += this.absoluteAccuracy;
                }
                random = d18;
            } else {
                random = d + (org.apache.commons.math.util.FastMath.random() * (d3 - d));
                d8 = Double.POSITIVE_INFINITY;
            }
            double value4 = univariateRealFunction2.value(random);
            double d19 = d7;
            if (org.apache.commons.math.util.FastMath.abs(random - d8) <= org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * org.apache.commons.math.util.FastMath.abs(random), this.absoluteAccuracy)) {
                setResult(random, i);
                return this.result;
            }
            if (org.apache.commons.math.util.FastMath.abs(value4) > this.functionValueAccuracy) {
                i++;
                univariateRealFunction2 = univariateRealFunction;
                d3 = d2;
                d8 = random;
                d5 = d9;
                d9 = d4;
                d7 = value3;
                d4 = d8;
                value3 = value4;
                d6 = d19;
            } else {
                setResult(random, i);
                return this.result;
            }
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }
}
