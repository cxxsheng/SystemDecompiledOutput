package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
class SimplexTableau implements java.io.Serializable {
    private static final java.lang.String NEGATIVE_VAR_COLUMN_LABEL = "x-";
    private static final long serialVersionUID = -1369660067587938365L;
    private final java.util.List<org.apache.commons.math.optimization.linear.LinearConstraint> constraints;
    private final double epsilon;
    private final org.apache.commons.math.optimization.linear.LinearObjectiveFunction f;
    private final int numDecisionVariables;
    private final boolean restrictToNonNegative;
    private transient org.apache.commons.math.linear.RealMatrix tableau;
    private final java.util.List<java.lang.String> columnLabels = new java.util.ArrayList();
    private final int numSlackVariables = getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.LEQ) + getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.GEQ);
    private int numArtificialVariables = getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.EQ) + getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship.GEQ);

    SimplexTableau(org.apache.commons.math.optimization.linear.LinearObjectiveFunction linearObjectiveFunction, java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> collection, org.apache.commons.math.optimization.GoalType goalType, boolean z, double d) {
        this.f = linearObjectiveFunction;
        this.constraints = normalizeConstraints(collection);
        this.restrictToNonNegative = z;
        this.epsilon = d;
        this.numDecisionVariables = linearObjectiveFunction.getCoefficients().getDimension() + (!z ? 1 : 0);
        this.tableau = createTableau(goalType == org.apache.commons.math.optimization.GoalType.MAXIMIZE);
        initializeColumnLabels();
    }

    protected void initializeColumnLabels() {
        if (getNumObjectiveFunctions() == 2) {
            this.columnLabels.add("W");
        }
        this.columnLabels.add("Z");
        for (int i = 0; i < getOriginalNumDecisionVariables(); i++) {
            this.columnLabels.add("x" + i);
        }
        if (!this.restrictToNonNegative) {
            this.columnLabels.add(NEGATIVE_VAR_COLUMN_LABEL);
        }
        for (int i2 = 0; i2 < getNumSlackVariables(); i2++) {
            this.columnLabels.add("s" + i2);
        }
        for (int i3 = 0; i3 < getNumArtificialVariables(); i3++) {
            this.columnLabels.add(com.android.server.wm.ActivityTaskManagerService.DUMP_ACTIVITIES_SHORT_CMD + i3);
        }
        this.columnLabels.add("RHS");
    }

    protected org.apache.commons.math.linear.RealMatrix createTableau(boolean z) {
        int i;
        int i2 = 1;
        int numObjectiveFunctions = this.numDecisionVariables + this.numSlackVariables + this.numArtificialVariables + getNumObjectiveFunctions() + 1;
        org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(this.constraints.size() + getNumObjectiveFunctions(), numObjectiveFunctions);
        if (getNumObjectiveFunctions() == 2) {
            array2DRowRealMatrix.setEntry(0, 0, -1.0d);
        }
        int i3 = getNumObjectiveFunctions() == 1 ? 0 : 1;
        array2DRowRealMatrix.setEntry(i3, i3, z ? 1.0d : -1.0d);
        org.apache.commons.math.linear.RealVector coefficients = this.f.getCoefficients();
        if (z) {
            coefficients = coefficients.mapMultiply(-1.0d);
        }
        copyArray(coefficients.getData(), array2DRowRealMatrix.getDataRef()[i3]);
        int i4 = numObjectiveFunctions - 1;
        double constantTerm = this.f.getConstantTerm();
        if (!z) {
            constantTerm *= -1.0d;
        }
        array2DRowRealMatrix.setEntry(i3, i4, constantTerm);
        if (!this.restrictToNonNegative) {
            array2DRowRealMatrix.setEntry(i3, getSlackVariableOffset() - 1, getInvertedCoeffiecientSum(coefficients));
        }
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < this.constraints.size()) {
            org.apache.commons.math.optimization.linear.LinearConstraint linearConstraint = this.constraints.get(i5);
            int numObjectiveFunctions2 = getNumObjectiveFunctions() + i5;
            copyArray(linearConstraint.getCoefficients().getData(), array2DRowRealMatrix.getDataRef()[numObjectiveFunctions2]);
            if (this.restrictToNonNegative) {
                i = i5;
            } else {
                i = i5;
                array2DRowRealMatrix.setEntry(numObjectiveFunctions2, getSlackVariableOffset() - i2, getInvertedCoeffiecientSum(linearConstraint.getCoefficients()));
            }
            array2DRowRealMatrix.setEntry(numObjectiveFunctions2, i4, linearConstraint.getValue());
            if (linearConstraint.getRelationship() == org.apache.commons.math.optimization.linear.Relationship.LEQ) {
                array2DRowRealMatrix.setEntry(numObjectiveFunctions2, getSlackVariableOffset() + i6, 1.0d);
                i6++;
            } else if (linearConstraint.getRelationship() == org.apache.commons.math.optimization.linear.Relationship.GEQ) {
                array2DRowRealMatrix.setEntry(numObjectiveFunctions2, getSlackVariableOffset() + i6, -1.0d);
                i6++;
            }
            if (linearConstraint.getRelationship() == org.apache.commons.math.optimization.linear.Relationship.EQ || linearConstraint.getRelationship() == org.apache.commons.math.optimization.linear.Relationship.GEQ) {
                array2DRowRealMatrix.setEntry(0, getArtificialVariableOffset() + i7, 1.0d);
                array2DRowRealMatrix.setEntry(numObjectiveFunctions2, getArtificialVariableOffset() + i7, 1.0d);
                array2DRowRealMatrix.setRowVector(0, array2DRowRealMatrix.getRowVector(0).subtract(array2DRowRealMatrix.getRowVector(numObjectiveFunctions2)));
                i7++;
            }
            i5 = i + 1;
            i2 = 1;
        }
        return array2DRowRealMatrix;
    }

    public java.util.List<org.apache.commons.math.optimization.linear.LinearConstraint> normalizeConstraints(java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> collection) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<org.apache.commons.math.optimization.linear.LinearConstraint> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(normalize(it.next()));
        }
        return arrayList;
    }

    private org.apache.commons.math.optimization.linear.LinearConstraint normalize(org.apache.commons.math.optimization.linear.LinearConstraint linearConstraint) {
        if (linearConstraint.getValue() < 0.0d) {
            return new org.apache.commons.math.optimization.linear.LinearConstraint(linearConstraint.getCoefficients().mapMultiply(-1.0d), linearConstraint.getRelationship().oppositeRelationship(), linearConstraint.getValue() * (-1.0d));
        }
        return new org.apache.commons.math.optimization.linear.LinearConstraint(linearConstraint.getCoefficients(), linearConstraint.getRelationship(), linearConstraint.getValue());
    }

    protected final int getNumObjectiveFunctions() {
        return this.numArtificialVariables > 0 ? 2 : 1;
    }

    private int getConstraintTypeCounts(org.apache.commons.math.optimization.linear.Relationship relationship) {
        java.util.Iterator<org.apache.commons.math.optimization.linear.LinearConstraint> it = this.constraints.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().getRelationship() == relationship) {
                i++;
            }
        }
        return i;
    }

    protected static double getInvertedCoeffiecientSum(org.apache.commons.math.linear.RealVector realVector) {
        double d = 0.0d;
        for (double d2 : realVector.getData()) {
            d -= d2;
        }
        return d;
    }

    protected java.lang.Integer getBasicRow(int i) {
        java.lang.Integer num = null;
        for (int i2 = 0; i2 < getHeight(); i2++) {
            if (org.apache.commons.math.util.MathUtils.equals(getEntry(i2, i), 1.0d, this.epsilon) && num == null) {
                num = java.lang.Integer.valueOf(i2);
            } else if (!org.apache.commons.math.util.MathUtils.equals(getEntry(i2, i), 0.0d, this.epsilon)) {
                return null;
            }
        }
        return num;
    }

    protected void dropPhase1Objective() {
        if (getNumObjectiveFunctions() == 1) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(0);
        for (int numObjectiveFunctions = getNumObjectiveFunctions(); numObjectiveFunctions < getArtificialVariableOffset(); numObjectiveFunctions++) {
            if (org.apache.commons.math.util.MathUtils.compareTo(this.tableau.getEntry(0, numObjectiveFunctions), 0.0d, this.epsilon) > 0) {
                arrayList.add(java.lang.Integer.valueOf(numObjectiveFunctions));
            }
        }
        for (int i = 0; i < getNumArtificialVariables(); i++) {
            int artificialVariableOffset = getArtificialVariableOffset() + i;
            if (getBasicRow(artificialVariableOffset) == null) {
                arrayList.add(java.lang.Integer.valueOf(artificialVariableOffset));
            }
        }
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, getHeight() - 1, getWidth() - arrayList.size());
        for (int i2 = 1; i2 < getHeight(); i2++) {
            int i3 = 0;
            for (int i4 = 0; i4 < getWidth(); i4++) {
                if (!arrayList.contains(java.lang.Integer.valueOf(i4))) {
                    dArr[i2 - 1][i3] = this.tableau.getEntry(i2, i4);
                    i3++;
                }
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.columnLabels.remove(((java.lang.Integer) arrayList.get(size)).intValue());
        }
        this.tableau = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr);
        this.numArtificialVariables = 0;
    }

    private void copyArray(double[] dArr, double[] dArr2) {
        java.lang.System.arraycopy(dArr, 0, dArr2, getNumObjectiveFunctions(), dArr.length);
    }

    boolean isOptimal() {
        for (int numObjectiveFunctions = getNumObjectiveFunctions(); numObjectiveFunctions < getWidth() - 1; numObjectiveFunctions++) {
            if (org.apache.commons.math.util.MathUtils.compareTo(this.tableau.getEntry(0, numObjectiveFunctions), 0.0d, this.epsilon) < 0) {
                return false;
            }
        }
        return true;
    }

    protected org.apache.commons.math.optimization.RealPointValuePair getSolution() {
        int indexOf = this.columnLabels.indexOf(NEGATIVE_VAR_COLUMN_LABEL);
        java.lang.Integer basicRow = indexOf > 0 ? getBasicRow(indexOf) : null;
        double entry = basicRow == null ? 0.0d : getEntry(basicRow.intValue(), getRhsOffset());
        java.util.HashSet hashSet = new java.util.HashSet();
        int originalNumDecisionVariables = getOriginalNumDecisionVariables();
        double[] dArr = new double[originalNumDecisionVariables];
        for (int i = 0; i < originalNumDecisionVariables; i++) {
            int indexOf2 = this.columnLabels.indexOf("x" + i);
            if (indexOf2 < 0) {
                dArr[i] = 0.0d;
            } else {
                java.lang.Integer basicRow2 = getBasicRow(indexOf2);
                if (hashSet.contains(basicRow2)) {
                    dArr[i] = 0.0d;
                } else {
                    hashSet.add(basicRow2);
                    dArr[i] = (basicRow2 == null ? 0.0d : getEntry(basicRow2.intValue(), getRhsOffset())) - (this.restrictToNonNegative ? 0.0d : entry);
                }
            }
        }
        return new org.apache.commons.math.optimization.RealPointValuePair(dArr, this.f.getValue(dArr));
    }

    protected void divideRow(int i, double d) {
        for (int i2 = 0; i2 < getWidth(); i2++) {
            this.tableau.setEntry(i, i2, this.tableau.getEntry(i, i2) / d);
        }
    }

    protected void subtractRow(int i, int i2, double d) {
        this.tableau.setRowVector(i, this.tableau.getRowVector(i).subtract(this.tableau.getRowVector(i2).mapMultiply(d)));
    }

    protected final int getWidth() {
        return this.tableau.getColumnDimension();
    }

    protected final int getHeight() {
        return this.tableau.getRowDimension();
    }

    protected final double getEntry(int i, int i2) {
        return this.tableau.getEntry(i, i2);
    }

    protected final void setEntry(int i, int i2, double d) {
        this.tableau.setEntry(i, i2, d);
    }

    protected final int getSlackVariableOffset() {
        return getNumObjectiveFunctions() + this.numDecisionVariables;
    }

    protected final int getArtificialVariableOffset() {
        return getNumObjectiveFunctions() + this.numDecisionVariables + this.numSlackVariables;
    }

    protected final int getRhsOffset() {
        return getWidth() - 1;
    }

    protected final int getNumDecisionVariables() {
        return this.numDecisionVariables;
    }

    protected final int getOriginalNumDecisionVariables() {
        return this.f.getCoefficients().getDimension();
    }

    protected final int getNumSlackVariables() {
        return this.numSlackVariables;
    }

    protected final int getNumArtificialVariables() {
        return this.numArtificialVariables;
    }

    protected final double[][] getData() {
        return this.tableau.getData();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.optimization.linear.SimplexTableau)) {
            return false;
        }
        org.apache.commons.math.optimization.linear.SimplexTableau simplexTableau = (org.apache.commons.math.optimization.linear.SimplexTableau) obj;
        return this.restrictToNonNegative == simplexTableau.restrictToNonNegative && this.numDecisionVariables == simplexTableau.numDecisionVariables && this.numSlackVariables == simplexTableau.numSlackVariables && this.numArtificialVariables == simplexTableau.numArtificialVariables && this.epsilon == simplexTableau.epsilon && this.f.equals(simplexTableau.f) && this.constraints.equals(simplexTableau.constraints) && this.tableau.equals(simplexTableau.tableau);
    }

    public int hashCode() {
        return ((((((java.lang.Boolean.valueOf(this.restrictToNonNegative).hashCode() ^ this.numDecisionVariables) ^ this.numSlackVariables) ^ this.numArtificialVariables) ^ java.lang.Double.valueOf(this.epsilon).hashCode()) ^ this.f.hashCode()) ^ this.constraints.hashCode()) ^ this.tableau.hashCode();
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        org.apache.commons.math.linear.MatrixUtils.serializeRealMatrix(this.tableau, objectOutputStream);
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.lang.ClassNotFoundException, java.io.IOException {
        objectInputStream.defaultReadObject();
        org.apache.commons.math.linear.MatrixUtils.deserializeRealMatrix(this, "tableau", objectInputStream);
    }
}
