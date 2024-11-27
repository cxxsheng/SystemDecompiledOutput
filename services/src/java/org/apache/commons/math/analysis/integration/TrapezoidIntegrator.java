package org.apache.commons.math.analysis.integration;

/* loaded from: classes3.dex */
public class TrapezoidIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {
    private double s;

    @java.lang.Deprecated
    public TrapezoidIntegrator(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 64);
    }

    public TrapezoidIntegrator() {
        super(64);
    }

    double stage(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, int i) throws org.apache.commons.math.FunctionEvaluationException {
        if (i == 0) {
            this.s = (d2 - d) * 0.5d * (univariateRealFunction.value(d) + univariateRealFunction.value(d2));
            return this.s;
        }
        long j = 1 << (i - 1);
        double d3 = (d2 - d) / j;
        double d4 = d + (d3 * 0.5d);
        double d5 = 0.0d;
        for (long j2 = 0; j2 < j; j2++) {
            d5 += univariateRealFunction.value(d4);
            d4 += d3;
        }
        this.s = (this.s + (d5 * d3)) * 0.5d;
        return this.s;
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    @java.lang.Deprecated
    public double integrate(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return integrate(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    public double integrate(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        clearResult();
        verifyInterval(d, d2);
        verifyIterationCount();
        double stage = stage(univariateRealFunction, d, d2, 0);
        int i = 1;
        while (i <= this.maximalIterationCount) {
            double stage2 = stage(univariateRealFunction, d, d2, i);
            if (i >= this.minimalIterationCount) {
                double abs = org.apache.commons.math.util.FastMath.abs(stage2 - stage);
                if (abs <= this.relativeAccuracy * (org.apache.commons.math.util.FastMath.abs(stage) + org.apache.commons.math.util.FastMath.abs(stage2)) * 0.5d || abs <= this.absoluteAccuracy) {
                    setResult(stage2, i);
                    return this.result;
                }
            }
            i++;
            stage = stage2;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl
    protected void verifyIterationCount() throws java.lang.IllegalArgumentException {
        super.verifyIterationCount();
        if (this.maximalIterationCount > 64) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_ITERATIONS_LIMITS, 0, 64);
        }
    }
}
