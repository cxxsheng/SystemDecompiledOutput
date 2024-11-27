package org.apache.commons.math.analysis.integration;

/* loaded from: classes3.dex */
public class RombergIntegrator extends org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl {
    @java.lang.Deprecated
    public RombergIntegrator(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 32);
    }

    public RombergIntegrator() {
        super(32);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    @java.lang.Deprecated
    public double integrate(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        return integrate(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegrator
    public double integrate(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        int i;
        double[] dArr;
        int i2 = 1;
        int i3 = this.maximalIterationCount + 1;
        double[] dArr2 = new double[i3];
        double[] dArr3 = new double[i3];
        clearResult();
        verifyInterval(d, d2);
        verifyIterationCount();
        org.apache.commons.math.analysis.integration.TrapezoidIntegrator trapezoidIntegrator = new org.apache.commons.math.analysis.integration.TrapezoidIntegrator();
        dArr3[0] = trapezoidIntegrator.stage(univariateRealFunction, d, d2, 0);
        int i4 = 1;
        double d3 = dArr3[0];
        while (i4 <= this.maximalIterationCount) {
            int i5 = i4;
            dArr2[0] = trapezoidIntegrator.stage(univariateRealFunction, d, d2, i4);
            int i6 = i2;
            while (true) {
                i = i5;
                if (i6 > i) {
                    break;
                }
                int i7 = i6 - 1;
                double d4 = dArr2[i7];
                dArr2[i6] = d4 + ((d4 - dArr3[i7]) / ((1 << (i6 * 2)) - 1));
                i6++;
                i5 = i;
            }
            double d5 = dArr2[i];
            if (i < this.minimalIterationCount) {
                dArr = dArr2;
            } else {
                double abs = org.apache.commons.math.util.FastMath.abs(d5 - d3);
                dArr = dArr2;
                if (abs <= this.relativeAccuracy * (org.apache.commons.math.util.FastMath.abs(d3) + org.apache.commons.math.util.FastMath.abs(d5)) * 0.5d || abs <= this.absoluteAccuracy) {
                    setResult(d5, i);
                    return this.result;
                }
            }
            dArr2 = dArr3;
            d3 = d5;
            dArr3 = dArr;
            i4 = i + 1;
            i2 = 1;
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }

    @Override // org.apache.commons.math.analysis.integration.UnivariateRealIntegratorImpl
    protected void verifyIterationCount() throws java.lang.IllegalArgumentException {
        super.verifyIterationCount();
        if (this.maximalIterationCount > 32) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_ITERATIONS_LIMITS, 0, 32);
        }
    }
}
