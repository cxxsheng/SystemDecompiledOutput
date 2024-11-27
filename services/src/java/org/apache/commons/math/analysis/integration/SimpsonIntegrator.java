package org.apache.commons.math.analysis.integration;

/* loaded from: classes3.dex */
public class SimpsonIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {
    @java.lang.Deprecated
    public SimpsonIntegrator(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 64);
    }

    public SimpsonIntegrator() {
        super(64);
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
        org.apache.commons.math.analysis.integration.TrapezoidIntegrator trapezoidIntegrator = new org.apache.commons.math.analysis.integration.TrapezoidIntegrator();
        double d3 = 3.0d;
        if (this.minimalIterationCount == 1) {
            setResult(((4.0d * trapezoidIntegrator.stage(univariateRealFunction, d, d2, 1)) - trapezoidIntegrator.stage(univariateRealFunction, d, d2, 0)) / 3.0d, 1);
            return this.result;
        }
        double d4 = 0.0d;
        int i = 1;
        double stage = trapezoidIntegrator.stage(univariateRealFunction, d, d2, 0);
        while (i <= this.maximalIterationCount) {
            int i2 = i;
            double stage2 = trapezoidIntegrator.stage(univariateRealFunction, d, d2, i);
            double d5 = ((stage2 * 4.0d) - stage) / d3;
            if (i2 >= this.minimalIterationCount) {
                double abs = org.apache.commons.math.util.FastMath.abs(d5 - d4);
                if (abs <= this.relativeAccuracy * (org.apache.commons.math.util.FastMath.abs(d4) + org.apache.commons.math.util.FastMath.abs(d5)) * 0.5d || abs <= this.absoluteAccuracy) {
                    setResult(d5, i2);
                    return this.result;
                }
            }
            i = i2 + 1;
            stage = stage2;
            d4 = d5;
            d3 = 3.0d;
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
