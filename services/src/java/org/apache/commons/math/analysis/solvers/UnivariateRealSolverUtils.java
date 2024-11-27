package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class UnivariateRealSolverUtils {
    private UnivariateRealSolverUtils() {
    }

    public static double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        setup(univariateRealFunction);
        return org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.LazyHolder.FACTORY.newDefaultSolver().solve(univariateRealFunction, d, d2);
    }

    public static double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        setup(univariateRealFunction);
        org.apache.commons.math.analysis.solvers.UnivariateRealSolver newDefaultSolver = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.LazyHolder.FACTORY.newDefaultSolver();
        newDefaultSolver.setAbsoluteAccuracy(d3);
        return newDefaultSolver.solve(univariateRealFunction, d, d2);
    }

    public static double[] bracket(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return bracket(univariateRealFunction, d, d2, d3, Integer.MAX_VALUE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
    
        return new double[]{r8, r10};
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static double[] bracket(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3, int i) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        double value;
        double value2;
        if (univariateRealFunction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION);
        }
        if (i <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_MAX_ITERATIONS, java.lang.Integer.valueOf(i));
        }
        if (d < d2 || d > d3 || d2 >= d3) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_BRACKETING_PARAMETERS, java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d), java.lang.Double.valueOf(d3));
        }
        double d4 = d;
        double d5 = d4;
        int i2 = 0;
        while (true) {
            d4 = org.apache.commons.math.util.FastMath.max(d4 - 1.0d, d2);
            d5 = org.apache.commons.math.util.FastMath.min(d5 + 1.0d, d3);
            value = univariateRealFunction.value(d4);
            value2 = univariateRealFunction.value(d5);
            i2++;
            double d6 = value * value2;
            if (d6 <= 0.0d || i2 >= i || (d4 <= d2 && d5 >= d3)) {
                break;
            }
        }
        throw new org.apache.commons.math.ConvergenceException(org.apache.commons.math.exception.util.LocalizedFormats.FAILED_BRACKETING, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i), java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3), java.lang.Double.valueOf(d4), java.lang.Double.valueOf(d5), java.lang.Double.valueOf(value), java.lang.Double.valueOf(value2));
    }

    public static double midpoint(double d, double d2) {
        return (d + d2) * 0.5d;
    }

    private static void setup(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        if (univariateRealFunction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION);
        }
    }

    private static class LazyHolder {
        private static final org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory FACTORY = org.apache.commons.math.analysis.solvers.UnivariateRealSolverFactory.newInstance();

        private LazyHolder() {
        }
    }
}
