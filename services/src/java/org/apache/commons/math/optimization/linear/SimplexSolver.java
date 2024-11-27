package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
public class SimplexSolver extends org.apache.commons.math.optimization.linear.AbstractLinearOptimizer {
    private static final double DEFAULT_EPSILON = 1.0E-6d;
    protected final double epsilon;

    public SimplexSolver() {
        this(1.0E-6d);
    }

    public SimplexSolver(double d) {
        this.epsilon = d;
    }

    private java.lang.Integer getPivotColumn(org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau) {
        double d = 0.0d;
        java.lang.Integer num = null;
        for (int numObjectiveFunctions = simplexTableau.getNumObjectiveFunctions(); numObjectiveFunctions < simplexTableau.getWidth() - 1; numObjectiveFunctions++) {
            if (org.apache.commons.math.util.MathUtils.compareTo(simplexTableau.getEntry(0, numObjectiveFunctions), d, this.epsilon) < 0) {
                d = simplexTableau.getEntry(0, numObjectiveFunctions);
                num = java.lang.Integer.valueOf(numObjectiveFunctions);
            }
        }
        return num;
    }

    private java.lang.Integer getPivotRow(org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau, int i) {
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList();
        double d = Double.MAX_VALUE;
        for (int numObjectiveFunctions = simplexTableau.getNumObjectiveFunctions(); numObjectiveFunctions < simplexTableau.getHeight(); numObjectiveFunctions++) {
            double entry = simplexTableau.getEntry(numObjectiveFunctions, simplexTableau.getWidth() - 1);
            double entry2 = simplexTableau.getEntry(numObjectiveFunctions, i);
            if (org.apache.commons.math.util.MathUtils.compareTo(entry2, 0.0d, this.epsilon) > 0) {
                double d2 = entry / entry2;
                if (org.apache.commons.math.util.MathUtils.equals(d2, d, this.epsilon)) {
                    arrayList.add(java.lang.Integer.valueOf(numObjectiveFunctions));
                } else if (d2 < d) {
                    arrayList = new java.util.ArrayList();
                    arrayList.add(java.lang.Integer.valueOf(numObjectiveFunctions));
                    d = d2;
                }
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        if (arrayList.size() > 1) {
            for (java.lang.Integer num : arrayList) {
                for (int i2 = 0; i2 < simplexTableau.getNumArtificialVariables(); i2++) {
                    int artificialVariableOffset = simplexTableau.getArtificialVariableOffset() + i2;
                    if (org.apache.commons.math.util.MathUtils.equals(simplexTableau.getEntry(num.intValue(), artificialVariableOffset), 1.0d, this.epsilon) && num.equals(simplexTableau.getBasicRow(artificialVariableOffset))) {
                        return num;
                    }
                }
            }
        }
        return (java.lang.Integer) arrayList.get(0);
    }

    protected void doIteration(org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau) throws org.apache.commons.math.optimization.OptimizationException {
        incrementIterationsCounter();
        java.lang.Integer pivotColumn = getPivotColumn(simplexTableau);
        java.lang.Integer pivotRow = getPivotRow(simplexTableau, pivotColumn.intValue());
        if (pivotRow == null) {
            throw new org.apache.commons.math.optimization.linear.UnboundedSolutionException();
        }
        simplexTableau.divideRow(pivotRow.intValue(), simplexTableau.getEntry(pivotRow.intValue(), pivotColumn.intValue()));
        for (int i = 0; i < simplexTableau.getHeight(); i++) {
            if (i != pivotRow.intValue()) {
                simplexTableau.subtractRow(i, pivotRow.intValue(), simplexTableau.getEntry(i, pivotColumn.intValue()));
            }
        }
    }

    protected void solvePhase1(org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau) throws org.apache.commons.math.optimization.OptimizationException {
        if (simplexTableau.getNumArtificialVariables() == 0) {
            return;
        }
        while (!simplexTableau.isOptimal()) {
            doIteration(simplexTableau);
        }
        if (!org.apache.commons.math.util.MathUtils.equals(simplexTableau.getEntry(0, simplexTableau.getRhsOffset()), 0.0d, this.epsilon)) {
            throw new org.apache.commons.math.optimization.linear.NoFeasibleSolutionException();
        }
    }

    @Override // org.apache.commons.math.optimization.linear.AbstractLinearOptimizer
    public org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.optimization.OptimizationException {
        org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau = new org.apache.commons.math.optimization.linear.SimplexTableau(this.function, this.linearConstraints, this.goal, this.nonNegative, this.epsilon);
        solvePhase1(simplexTableau);
        simplexTableau.dropPhase1Objective();
        while (!simplexTableau.isOptimal()) {
            doIteration(simplexTableau);
        }
        return simplexTableau.getSolution();
    }
}
