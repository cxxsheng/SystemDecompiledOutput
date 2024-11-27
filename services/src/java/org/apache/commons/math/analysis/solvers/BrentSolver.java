package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class BrentSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    public static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;
    public static final int DEFAULT_MAXIMUM_ITERATIONS = 100;
    private static final long serialVersionUID = 7694577816772532779L;

    @java.lang.Deprecated
    public BrentSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 100, 1.0E-6d);
    }

    @java.lang.Deprecated
    public BrentSolver() {
        super(100, 1.0E-6d);
    }

    public BrentSolver(double d) {
        super(100, d);
    }

    public BrentSolver(int i, double d) {
        super(i, d);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        clearResult();
        if (d3 < d || d3 > d2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_INTERVAL_INITIAL_VALUE_PARAMETERS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d3), java.lang.Double.valueOf(d2));
        }
        double value = univariateRealFunction.value(d3);
        if (org.apache.commons.math.util.FastMath.abs(value) <= this.functionValueAccuracy) {
            setResult(d3, 0);
            return this.result;
        }
        double value2 = univariateRealFunction.value(d);
        if (org.apache.commons.math.util.FastMath.abs(value2) <= this.functionValueAccuracy) {
            setResult(d, 0);
            return this.result;
        }
        if (value * value2 < 0.0d) {
            return solve(univariateRealFunction, d, value2, d3, value, d, value2);
        }
        double value3 = univariateRealFunction.value(d2);
        if (org.apache.commons.math.util.FastMath.abs(value3) <= this.functionValueAccuracy) {
            setResult(d2, 0);
            return this.result;
        }
        if (value * value3 < 0.0d) {
            return solve(univariateRealFunction, d3, value, d2, value3, d3, value);
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SAME_SIGN_AT_ENDPOINTS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(value2), java.lang.Double.valueOf(value3));
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        clearResult();
        verifyInterval(d, d2);
        double value = univariateRealFunction.value(d);
        double value2 = univariateRealFunction.value(d2);
        double d3 = value * value2;
        if (d3 > 0.0d) {
            if (org.apache.commons.math.util.FastMath.abs(value) <= this.functionValueAccuracy) {
                setResult(d, 0);
                return d;
            }
            if (org.apache.commons.math.util.FastMath.abs(value2) <= this.functionValueAccuracy) {
                setResult(d2, 0);
            } else {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SAME_SIGN_AT_ENDPOINTS, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(value), java.lang.Double.valueOf(value2));
            }
        } else {
            if (d3 < 0.0d) {
                return solve(univariateRealFunction, d, value, d2, value2, d, value);
            }
            if (value == 0.0d) {
                return d;
            }
        }
        return d2;
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3, double d4, double d5, double d6) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        double d7;
        double d8;
        double d9;
        double value;
        double d10;
        double d11;
        double d12 = d2;
        double d13 = d3;
        double d14 = d4;
        double d15 = d5;
        double d16 = d6;
        double d17 = d3 - d;
        double d18 = d17;
        int i = 0;
        double d19 = d;
        while (true) {
            double d20 = d19;
            if (i >= this.maximalIterationCount) {
                throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
            }
            if (org.apache.commons.math.util.FastMath.abs(d16) >= org.apache.commons.math.util.FastMath.abs(d14)) {
                d19 = d13;
                d13 = d20;
                double d21 = d12;
                d12 = d14;
                d14 = d21;
            } else {
                d19 = d15;
                d12 = d16;
                d15 = d13;
                d16 = d14;
            }
            double d22 = d16;
            if (org.apache.commons.math.util.FastMath.abs(d12) <= this.functionValueAccuracy) {
                setResult(d19, i);
                return this.result;
            }
            double d23 = d15 - d19;
            double d24 = d13;
            double d25 = d15;
            double max = org.apache.commons.math.util.FastMath.max(this.relativeAccuracy * org.apache.commons.math.util.FastMath.abs(d19), this.absoluteAccuracy);
            if (org.apache.commons.math.util.FastMath.abs(d23) <= max) {
                setResult(d19, i);
                return this.result;
            }
            if (org.apache.commons.math.util.FastMath.abs(d17) < max) {
                d7 = d22;
                d8 = d25;
            } else if (org.apache.commons.math.util.FastMath.abs(d14) <= org.apache.commons.math.util.FastMath.abs(d12)) {
                d7 = d22;
                d8 = d25;
            } else {
                double d26 = d12 / d14;
                d8 = d25;
                if (d24 == d8) {
                    d10 = d23 * d26;
                    d7 = d22;
                    d11 = 1.0d - d26;
                } else {
                    d7 = d22;
                    double d27 = d14 / d7;
                    double d28 = d12 / d7;
                    double d29 = d23 * d27 * (d27 - d28);
                    double d30 = d28 - 1.0d;
                    double d31 = (d27 - 1.0d) * d30 * (d26 - 1.0d);
                    d10 = d26 * (d29 - ((d19 - d24) * d30));
                    d11 = d31;
                }
                if (d10 > 0.0d) {
                    d11 = -d11;
                } else {
                    d10 = -d10;
                }
                if (2.0d * d10 >= ((1.5d * d23) * d11) - org.apache.commons.math.util.FastMath.abs(max * d11) || d10 >= org.apache.commons.math.util.FastMath.abs(d17 * 0.5d * d11)) {
                    d18 = d23 * 0.5d;
                    d9 = d18;
                } else {
                    d9 = d10 / d11;
                }
                if (org.apache.commons.math.util.FastMath.abs(d9) <= max) {
                    d13 = d19 + d9;
                } else if (d23 > 0.0d) {
                    d13 = (max * 0.5d) + d19;
                } else if (d23 > 0.0d) {
                    d13 = d19;
                } else {
                    d13 = d19 - (max * 0.5d);
                }
                value = univariateRealFunction.value(d13);
                if ((value <= 0.0d) == (d7 > 0.0d)) {
                    d17 = d18;
                    d18 = d9;
                } else {
                    d8 = d19;
                    d7 = d12;
                    d17 = d13 - d19;
                    d18 = d17;
                }
                i++;
                d14 = value;
                d15 = d8;
                d16 = d7;
            }
            d18 = d23 * 0.5d;
            d9 = d18;
            if (org.apache.commons.math.util.FastMath.abs(d9) <= max) {
            }
            value = univariateRealFunction.value(d13);
            if ((value <= 0.0d) == (d7 > 0.0d)) {
            }
            i++;
            d14 = value;
            d15 = d8;
            d16 = d7;
        }
    }
}
