package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class GaussianFitter {
    private final org.apache.commons.math.optimization.fitting.CurveFitter fitter;

    public GaussianFitter(org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer differentiableMultivariateVectorialOptimizer) {
        this.fitter = new org.apache.commons.math.optimization.fitting.CurveFitter(differentiableMultivariateVectorialOptimizer);
    }

    public void addObservedPoint(double d, double d2) {
        addObservedPoint(1.0d, d, d2);
    }

    public void addObservedPoint(double d, double d2, double d3) {
        this.fitter.addObservedPoint(d, d2, d3);
    }

    public org.apache.commons.math.optimization.fitting.GaussianFunction fit() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        return new org.apache.commons.math.optimization.fitting.GaussianFunction(this.fitter.fit(new org.apache.commons.math.optimization.fitting.ParametricGaussianFunction(), createParametersGuesser(this.fitter.getObservations()).guess()));
    }

    protected org.apache.commons.math.optimization.fitting.GaussianParametersGuesser createParametersGuesser(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        return new org.apache.commons.math.optimization.fitting.GaussianParametersGuesser(weightedObservedPointArr);
    }
}
