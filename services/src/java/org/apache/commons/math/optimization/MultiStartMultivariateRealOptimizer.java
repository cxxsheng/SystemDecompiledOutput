package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class MultiStartMultivariateRealOptimizer implements org.apache.commons.math.optimization.MultivariateRealOptimizer {
    private org.apache.commons.math.random.RandomVectorGenerator generator;
    private int maxEvaluations;
    private int maxIterations;
    private final org.apache.commons.math.optimization.MultivariateRealOptimizer optimizer;
    private int starts;
    private int totalIterations = 0;
    private int totalEvaluations = 0;
    private org.apache.commons.math.optimization.RealPointValuePair[] optima = null;

    public MultiStartMultivariateRealOptimizer(org.apache.commons.math.optimization.MultivariateRealOptimizer multivariateRealOptimizer, int i, org.apache.commons.math.random.RandomVectorGenerator randomVectorGenerator) {
        this.optimizer = multivariateRealOptimizer;
        this.starts = i;
        this.generator = randomVectorGenerator;
        setMaxIterations(Integer.MAX_VALUE);
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    public org.apache.commons.math.optimization.RealPointValuePair[] getOptima() throws java.lang.IllegalStateException {
        if (this.optima == null) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new java.lang.Object[0]);
        }
        return (org.apache.commons.math.optimization.RealPointValuePair[]) this.optima.clone();
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setMaxIterations(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getIterations() {
        return this.totalIterations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getEvaluations() {
        return this.totalEvaluations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker realConvergenceChecker) {
        this.optimizer.setConvergenceChecker(realConvergenceChecker);
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker() {
        return this.optimizer.getConvergenceChecker();
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealPointValuePair optimize(org.apache.commons.math.analysis.MultivariateRealFunction multivariateRealFunction, final org.apache.commons.math.optimization.GoalType goalType, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, org.apache.commons.math.FunctionEvaluationException {
        this.optima = new org.apache.commons.math.optimization.RealPointValuePair[this.starts];
        this.totalIterations = 0;
        this.totalEvaluations = 0;
        int i = 0;
        while (i < this.starts) {
            try {
                this.optimizer.setMaxIterations(this.maxIterations - this.totalIterations);
                this.optimizer.setMaxEvaluations(this.maxEvaluations - this.totalEvaluations);
                this.optima[i] = this.optimizer.optimize(multivariateRealFunction, goalType, i == 0 ? dArr : this.generator.nextVector());
            } catch (org.apache.commons.math.FunctionEvaluationException e) {
                this.optima[i] = null;
            } catch (org.apache.commons.math.optimization.OptimizationException e2) {
                this.optima[i] = null;
            }
            this.totalIterations += this.optimizer.getIterations();
            this.totalEvaluations += this.optimizer.getEvaluations();
            i++;
        }
        java.util.Arrays.sort(this.optima, new java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair>() { // from class: org.apache.commons.math.optimization.MultiStartMultivariateRealOptimizer.1
            @Override // java.util.Comparator
            public int compare(org.apache.commons.math.optimization.RealPointValuePair realPointValuePair, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2) {
                if (realPointValuePair == null) {
                    return realPointValuePair2 == null ? 0 : 1;
                }
                if (realPointValuePair2 == null) {
                    return -1;
                }
                double value = realPointValuePair.getValue();
                double value2 = realPointValuePair2.getValue();
                return goalType == org.apache.commons.math.optimization.GoalType.MINIMIZE ? java.lang.Double.compare(value, value2) : java.lang.Double.compare(value2, value);
            }
        });
        if (this.optima[0] != null) {
            return this.optima[0];
        }
        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.NO_CONVERGENCE_WITH_ANY_START_POINT, java.lang.Integer.valueOf(this.starts));
    }
}
