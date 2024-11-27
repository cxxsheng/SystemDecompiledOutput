package org.apache.commons.math.analysis.integration;

/* loaded from: classes3.dex */
public class LegendreGaussIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {
    private final double[] abscissas;
    private final double[] weights;
    private static final double[] ABSCISSAS_2 = {(-1.0d) / org.apache.commons.math.util.FastMath.sqrt(3.0d), 1.0d / org.apache.commons.math.util.FastMath.sqrt(3.0d)};
    private static final double[] WEIGHTS_2 = {1.0d, 1.0d};
    private static final double[] ABSCISSAS_3 = {-org.apache.commons.math.util.FastMath.sqrt(0.6d), 0.0d, org.apache.commons.math.util.FastMath.sqrt(0.6d)};
    private static final double[] WEIGHTS_3 = {0.5555555555555556d, 0.8888888888888888d, 0.5555555555555556d};
    private static final double[] ABSCISSAS_4 = {-org.apache.commons.math.util.FastMath.sqrt(((org.apache.commons.math.util.FastMath.sqrt(30.0d) * 2.0d) + 15.0d) / 35.0d), -org.apache.commons.math.util.FastMath.sqrt((15.0d - (org.apache.commons.math.util.FastMath.sqrt(30.0d) * 2.0d)) / 35.0d), org.apache.commons.math.util.FastMath.sqrt((15.0d - (org.apache.commons.math.util.FastMath.sqrt(30.0d) * 2.0d)) / 35.0d), org.apache.commons.math.util.FastMath.sqrt(((org.apache.commons.math.util.FastMath.sqrt(30.0d) * 2.0d) + 15.0d) / 35.0d)};
    private static final double[] WEIGHTS_4 = {(90.0d - (org.apache.commons.math.util.FastMath.sqrt(30.0d) * 5.0d)) / 180.0d, ((org.apache.commons.math.util.FastMath.sqrt(30.0d) * 5.0d) + 90.0d) / 180.0d, ((org.apache.commons.math.util.FastMath.sqrt(30.0d) * 5.0d) + 90.0d) / 180.0d, (90.0d - (org.apache.commons.math.util.FastMath.sqrt(30.0d) * 5.0d)) / 180.0d};
    private static final double[] ABSCISSAS_5 = {-org.apache.commons.math.util.FastMath.sqrt(((org.apache.commons.math.util.FastMath.sqrt(70.0d) * 2.0d) + 35.0d) / 63.0d), -org.apache.commons.math.util.FastMath.sqrt((35.0d - (org.apache.commons.math.util.FastMath.sqrt(70.0d) * 2.0d)) / 63.0d), 0.0d, org.apache.commons.math.util.FastMath.sqrt((35.0d - (org.apache.commons.math.util.FastMath.sqrt(70.0d) * 2.0d)) / 63.0d), org.apache.commons.math.util.FastMath.sqrt(((org.apache.commons.math.util.FastMath.sqrt(70.0d) * 2.0d) + 35.0d) / 63.0d)};
    private static final double[] WEIGHTS_5 = {(322.0d - (org.apache.commons.math.util.FastMath.sqrt(70.0d) * 13.0d)) / 900.0d, ((org.apache.commons.math.util.FastMath.sqrt(70.0d) * 13.0d) + 322.0d) / 900.0d, 0.5688888888888889d, ((org.apache.commons.math.util.FastMath.sqrt(70.0d) * 13.0d) + 322.0d) / 900.0d, (322.0d - (org.apache.commons.math.util.FastMath.sqrt(70.0d) * 13.0d)) / 900.0d};

    public LegendreGaussIntegrator(int i, int i2) throws java.lang.IllegalArgumentException {
        super(i2);
        switch (i) {
            case 2:
                this.abscissas = ABSCISSAS_2;
                this.weights = WEIGHTS_2;
                return;
            case 3:
                this.abscissas = ABSCISSAS_3;
                this.weights = WEIGHTS_3;
                return;
            case 4:
                this.abscissas = ABSCISSAS_4;
                this.weights = WEIGHTS_4;
                return;
            case 5:
                this.abscissas = ABSCISSAS_5;
                this.weights = WEIGHTS_5;
                return;
            default:
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.N_POINTS_GAUSS_LEGENDRE_INTEGRATOR_NOT_SUPPORTED, java.lang.Integer.valueOf(i), 2, 5);
        }
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    @java.lang.Deprecated
    public double integrate(double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return integrate(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    public double integrate(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        clearResult();
        verifyInterval(d, d2);
        verifyIterationCount();
        double stage = stage(univariateRealFunction, d, d2, 1);
        int i = 2;
        int i2 = 0;
        while (i2 < this.maximalIterationCount) {
            double stage2 = stage(univariateRealFunction, d, d2, i);
            double abs = org.apache.commons.math.util.FastMath.abs(stage2 - stage);
            double max = org.apache.commons.math.util.FastMath.max(this.absoluteAccuracy, this.relativeAccuracy * (org.apache.commons.math.util.FastMath.abs(stage) + org.apache.commons.math.util.FastMath.abs(stage2)) * 0.5d);
            int i3 = i2 + 1;
            if (i3 >= this.minimalIterationCount && abs <= max) {
                setResult(stage2, i2);
                return this.result;
            }
            i = org.apache.commons.math.util.FastMath.max((int) (org.apache.commons.math.util.FastMath.min(4.0d, org.apache.commons.math.util.FastMath.pow(abs / max, 0.5d / this.abscissas.length)) * i), i + 1);
            stage = stage2;
            i2 = i3;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }

    private double stage(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException {
        double d3 = (d2 - d) / i;
        double d4 = d3 / 2.0d;
        double d5 = d + d4;
        double d6 = 0.0d;
        int i2 = 0;
        while (i2 < i) {
            int i3 = 0;
            while (i3 < this.abscissas.length) {
                d6 += this.weights[i3] * univariateRealFunction.value(d5 + (this.abscissas[i3] * d4));
                i3++;
                i2 = i2;
            }
            d5 += d3;
            i2++;
        }
        return d4 * d6;
    }
}
