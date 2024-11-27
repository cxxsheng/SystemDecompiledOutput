package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class MultiStartUnivariateRealOptimizer implements org.apache.commons.math.optimization.UnivariateRealOptimizer {
    private static final long serialVersionUID = 5983375963110961019L;
    private org.apache.commons.math.random.RandomGenerator generator;
    private int maxEvaluations;
    private int maxIterations;
    private double[] optimaValues;
    private final org.apache.commons.math.optimization.UnivariateRealOptimizer optimizer;
    private int starts;
    private int totalEvaluations;
    private int totalIterations = 0;
    private double[] optima = null;

    public MultiStartUnivariateRealOptimizer(org.apache.commons.math.optimization.UnivariateRealOptimizer univariateRealOptimizer, int i, org.apache.commons.math.random.RandomGenerator randomGenerator) {
        this.optimizer = univariateRealOptimizer;
        this.starts = i;
        this.generator = randomGenerator;
        setMaximalIterationCount(Integer.MAX_VALUE);
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double getFunctionValue() {
        return this.optimaValues[0];
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double getResult() {
        return this.optima[0];
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public double getAbsoluteAccuracy() {
        return this.optimizer.getAbsoluteAccuracy();
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public int getIterationCount() {
        return this.totalIterations;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public int getMaximalIterationCount() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public int getEvaluations() {
        return this.totalEvaluations;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public double getRelativeAccuracy() {
        return this.optimizer.getRelativeAccuracy();
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetAbsoluteAccuracy() {
        this.optimizer.resetAbsoluteAccuracy();
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetMaximalIterationCount() {
        this.optimizer.resetMaximalIterationCount();
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetRelativeAccuracy() {
        this.optimizer.resetRelativeAccuracy();
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setAbsoluteAccuracy(double d) {
        this.optimizer.setAbsoluteAccuracy(d);
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setMaximalIterationCount(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setRelativeAccuracy(double d) {
        this.optimizer.setRelativeAccuracy(d);
    }

    public double[] getOptima() throws java.lang.IllegalStateException {
        if (this.optima == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new java.lang.Object[0]);
        }
        return (double[]) this.optima.clone();
    }

    public double[] getOptimaValues() throws java.lang.IllegalStateException {
        if (this.optimaValues == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new java.lang.Object[0]);
        }
        return (double[]) this.optimaValues.clone();
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        this.optima = new double[this.starts];
        this.optimaValues = new double[this.starts];
        this.totalIterations = 0;
        this.totalEvaluations = 0;
        int i = 0;
        while (i < this.starts) {
            try {
                this.optimizer.setMaximalIterationCount(this.maxIterations - this.totalIterations);
                this.optimizer.setMaxEvaluations(this.maxEvaluations - this.totalEvaluations);
                double nextDouble = i == 0 ? d : d + (this.generator.nextDouble() * (d2 - d));
                double nextDouble2 = i == 0 ? d2 : d + (this.generator.nextDouble() * (d2 - d));
                this.optima[i] = this.optimizer.optimize(univariateRealFunction, goalType, org.apache.commons.math.util.FastMath.min(nextDouble, nextDouble2), org.apache.commons.math.util.FastMath.max(nextDouble, nextDouble2));
                this.optimaValues[i] = this.optimizer.getFunctionValue();
            } catch (org.apache.commons.math.ConvergenceException e) {
                this.optima[i] = Double.NaN;
                this.optimaValues[i] = Double.NaN;
            } catch (org.apache.commons.math.FunctionEvaluationException e2) {
                this.optima[i] = Double.NaN;
                this.optimaValues[i] = Double.NaN;
            }
            this.totalIterations += this.optimizer.getIterationCount();
            this.totalEvaluations += this.optimizer.getEvaluations();
            i++;
        }
        int length = this.optima.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (java.lang.Double.isNaN(this.optima[i2])) {
                int i3 = length - 1;
                this.optima[i2] = this.optima[i3];
                this.optima[i3 + 1] = Double.NaN;
                length = i3 - 1;
                this.optimaValues[i2] = this.optimaValues[length];
                this.optimaValues[length + 1] = Double.NaN;
            }
        }
        double d3 = this.optima[0];
        double d4 = this.optimaValues[0];
        for (int i4 = 1; i4 < length; i4++) {
            double d5 = this.optima[i4];
            double d6 = this.optimaValues[i4];
            if (goalType != org.apache.commons.math.optimization.GoalType.MAXIMIZE) {
                z = false;
            } else {
                z = true;
            }
            if (d6 >= d4) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (!(z2 ^ z)) {
                d4 = d6;
            } else {
                int i5 = i4 - 1;
                double d7 = this.optima[i5];
                double d8 = this.optimaValues[i5];
                while (i5 >= 0) {
                    if (goalType != org.apache.commons.math.optimization.GoalType.MAXIMIZE) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (d6 >= d8) {
                        z4 = false;
                    } else {
                        z4 = true;
                    }
                    if (!(z3 ^ z4)) {
                        break;
                    }
                    int i6 = i5 + 1;
                    this.optima[i6] = d7;
                    this.optimaValues[i6] = d8;
                    int i7 = i5 - 1;
                    if (i5 != 0) {
                        d7 = this.optima[i7];
                        d8 = this.optimaValues[i7];
                        i5 = i7;
                    } else {
                        i5 = i7;
                        d7 = Double.NaN;
                        d8 = Double.NaN;
                    }
                }
                int i8 = i5 + 1;
                this.optima[i8] = d5;
                this.optimaValues[i8] = d6;
                double d9 = this.optima[i4];
                d4 = this.optimaValues[i4];
            }
        }
        if (!java.lang.Double.isNaN(this.optima[0])) {
            return this.optima[0];
        }
        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.NO_CONVERGENCE_WITH_ANY_START_POINT, java.lang.Integer.valueOf(this.starts));
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException {
        return optimize(univariateRealFunction, goalType, d, d2);
    }
}
