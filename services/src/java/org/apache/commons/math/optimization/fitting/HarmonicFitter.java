package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class HarmonicFitter {
    private final org.apache.commons.math.optimization.fitting.CurveFitter fitter;
    private double[] parameters;

    public HarmonicFitter(org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer differentiableMultivariateVectorialOptimizer) {
        this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(differentiableMultivariateVectorialOptimizer);
        this.parameters = null;
    }

    public HarmonicFitter(org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer differentiableMultivariateVectorialOptimizer, double[] dArr) {
        this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(differentiableMultivariateVectorialOptimizer);
        this.parameters = (double[]) dArr.clone();
    }

    public void addObservedPoint(double d, double d2, double d3) {
        this.fitter.addObservedPoint(d, d2, d3);
    }

    public org.apache.commons.math.optimization.fitting.HarmonicFunction fit() throws org.apache.commons.math.optimization.OptimizationException {
        if (this.parameters == null) {
            org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] observations = this.fitter.getObservations();
            if (observations.length < 4) {
                throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, java.lang.Integer.valueOf(observations.length), 4);
            }
            org.apache.commons.math.optimization.fitting.HarmonicCoefficientsGuesser harmonicCoefficientsGuesser = new org.apache.commons.math.optimization.fitting.HarmonicCoefficientsGuesser(observations);
            harmonicCoefficientsGuesser.guess();
            this.parameters = new double[]{harmonicCoefficientsGuesser.getGuessedAmplitude(), harmonicCoefficientsGuesser.getGuessedPulsation(), harmonicCoefficientsGuesser.getGuessedPhase()};
        }
        try {
            double[] fit = this.fitter.fit(new org.apache.commons.math.optimization.fitting.HarmonicFitter.ParametricHarmonicFunction(), this.parameters);
            return new org.apache.commons.math.optimization.fitting.HarmonicFunction(fit[0], fit[1], fit[2]);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static class ParametricHarmonicFunction implements org.apache.commons.math.optimization.fitting.ParametricRealFunction {
        private ParametricHarmonicFunction() {
        }

        @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
        public double value(double d, double[] dArr) {
            return dArr[0] * org.apache.commons.math.util.FastMath.cos((dArr[1] * d) + dArr[2]);
        }

        @Override // org.apache.commons.math.optimization.fitting.ParametricRealFunction
        public double[] gradient(double d, double[] dArr) {
            double d2 = dArr[0];
            double d3 = (dArr[1] * d) + dArr[2];
            double cos = org.apache.commons.math.util.FastMath.cos(d3);
            double sin = org.apache.commons.math.util.FastMath.sin(d3);
            double d4 = -d2;
            return new double[]{cos, d * d4 * sin, d4 * sin};
        }
    }
}
