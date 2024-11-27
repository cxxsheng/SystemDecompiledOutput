package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public abstract class AbstractContinuousDistribution extends org.apache.commons.math.distribution.AbstractDistribution implements org.apache.commons.math.distribution.ContinuousDistribution, java.io.Serializable {
    private static final long serialVersionUID = -38038050983108802L;
    protected final org.apache.commons.math.random.RandomDataImpl randomData = new org.apache.commons.math.random.RandomDataImpl();
    private double solverAbsoluteAccuracy = 1.0E-6d;

    protected abstract double getDomainLowerBound(double d);

    protected abstract double getDomainUpperBound(double d);

    protected abstract double getInitialDomain(double d);

    protected AbstractContinuousDistribution() {
    }

    public double density(double d) throws org.apache.commons.math.MathRuntimeException {
        throw new org.apache.commons.math.MathRuntimeException(new java.lang.UnsupportedOperationException(), org.apache.commons.math.exception.util.LocalizedFormats.NO_DENSITY_FOR_THIS_DISTRIBUTION, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.distribution.ContinuousDistribution
    public double inverseCumulativeProbability(final double d) throws org.apache.commons.math.MathException {
        if (d < 0.0d || d > 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_RANGE_SIMPLE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction = new org.apache.commons.math.analysis.UnivariateRealFunction() { // from class: org.apache.commons.math.distribution.AbstractContinuousDistribution.1
            @Override // org.apache.commons.math.analysis.UnivariateRealFunction
            public double value(double d2) throws org.apache.commons.math.FunctionEvaluationException {
                try {
                    double cumulativeProbability = org.apache.commons.math.distribution.AbstractContinuousDistribution.this.cumulativeProbability(d2) - d;
                    if (java.lang.Double.isNaN(cumulativeProbability)) {
                        throw new org.apache.commons.math.FunctionEvaluationException(d2, org.apache.commons.math.exception.util.LocalizedFormats.CUMULATIVE_PROBABILITY_RETURNED_NAN, java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d));
                    }
                    return cumulativeProbability;
                } catch (org.apache.commons.math.MathException e) {
                    throw new org.apache.commons.math.FunctionEvaluationException(d2, e.getSpecificPattern(), e.getGeneralPattern(), e.getArguments());
                }
            }
        };
        double domainLowerBound = getDomainLowerBound(d);
        double domainUpperBound = getDomainUpperBound(d);
        try {
            double[] bracket = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.bracket(univariateRealFunction, getInitialDomain(d), domainLowerBound, domainUpperBound);
            return org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.solve(univariateRealFunction, bracket[0], bracket[1], getSolverAbsoluteAccuracy());
        } catch (org.apache.commons.math.ConvergenceException e) {
            if (org.apache.commons.math.util.FastMath.abs(univariateRealFunction.value(domainLowerBound)) < getSolverAbsoluteAccuracy()) {
                return domainLowerBound;
            }
            if (org.apache.commons.math.util.FastMath.abs(univariateRealFunction.value(domainUpperBound)) < getSolverAbsoluteAccuracy()) {
                return domainUpperBound;
            }
            throw new org.apache.commons.math.MathException(e);
        }
    }

    public void reseedRandomGenerator(long j) {
        this.randomData.reSeed(j);
    }

    public double sample() throws org.apache.commons.math.MathException {
        return this.randomData.nextInversionDeviate(this);
    }

    public double[] sample(int i) throws org.apache.commons.math.MathException {
        if (i <= 0) {
            org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_POSITIVE_SAMPLE_SIZE, java.lang.Integer.valueOf(i));
        }
        double[] dArr = new double[i];
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = sample();
        }
        return dArr;
    }

    protected double getSolverAbsoluteAccuracy() {
        return this.solverAbsoluteAccuracy;
    }
}
