package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
public class LinearConstraint implements java.io.Serializable {
    private static final long serialVersionUID = -764632794033034092L;
    private final transient org.apache.commons.math.linear.RealVector coefficients;
    private final org.apache.commons.math.optimization.linear.Relationship relationship;
    private final double value;

    public LinearConstraint(double[] dArr, org.apache.commons.math.optimization.linear.Relationship relationship, double d) {
        this(new org.apache.commons.math.linear.ArrayRealVector(dArr), relationship, d);
    }

    public LinearConstraint(org.apache.commons.math.linear.RealVector realVector, org.apache.commons.math.optimization.linear.Relationship relationship, double d) {
        this.coefficients = realVector;
        this.relationship = relationship;
        this.value = d;
    }

    public LinearConstraint(double[] dArr, double d, org.apache.commons.math.optimization.linear.Relationship relationship, double[] dArr2, double d2) {
        int length = dArr.length;
        double[] dArr3 = new double[length];
        for (int i = 0; i < length; i++) {
            dArr3[i] = dArr[i] - dArr2[i];
        }
        this.coefficients = new org.apache.commons.math.linear.ArrayRealVector(dArr3, false);
        this.relationship = relationship;
        this.value = d2 - d;
    }

    public LinearConstraint(org.apache.commons.math.linear.RealVector realVector, double d, org.apache.commons.math.optimization.linear.Relationship relationship, org.apache.commons.math.linear.RealVector realVector2, double d2) {
        this.coefficients = realVector.subtract(realVector2);
        this.relationship = relationship;
        this.value = d2 - d;
    }

    public org.apache.commons.math.linear.RealVector getCoefficients() {
        return this.coefficients;
    }

    public org.apache.commons.math.optimization.linear.Relationship getRelationship() {
        return this.relationship;
    }

    public double getValue() {
        return this.value;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.optimization.linear.LinearConstraint)) {
            return false;
        }
        org.apache.commons.math.optimization.linear.LinearConstraint linearConstraint = (org.apache.commons.math.optimization.linear.LinearConstraint) obj;
        return this.relationship == linearConstraint.relationship && this.value == linearConstraint.value && this.coefficients.equals(linearConstraint.coefficients);
    }

    public int hashCode() {
        return (this.relationship.hashCode() ^ java.lang.Double.valueOf(this.value).hashCode()) ^ this.coefficients.hashCode();
    }

    private void writeObject(java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        objectOutputStream.defaultWriteObject();
        org.apache.commons.math.linear.MatrixUtils.serializeRealVector(this.coefficients, objectOutputStream);
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.lang.ClassNotFoundException, java.io.IOException {
        objectInputStream.defaultReadObject();
        org.apache.commons.math.linear.MatrixUtils.deserializeRealVector(this, "coefficients", objectInputStream);
    }
}
