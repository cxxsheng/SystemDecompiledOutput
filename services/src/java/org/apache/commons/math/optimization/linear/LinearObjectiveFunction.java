package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
public class LinearObjectiveFunction implements java.io.Serializable {
    private static final long serialVersionUID = -4531815507568396090L;
    private final transient org.apache.commons.math.linear.RealVector coefficients;
    private final double constantTerm;

    public LinearObjectiveFunction(double[] dArr, double d) {
        this(new org.apache.commons.math.linear.ArrayRealVector(dArr), d);
    }

    public LinearObjectiveFunction(org.apache.commons.math.linear.RealVector realVector, double d) {
        this.coefficients = realVector;
        this.constantTerm = d;
    }

    public org.apache.commons.math.linear.RealVector getCoefficients() {
        return this.coefficients;
    }

    public double getConstantTerm() {
        return this.constantTerm;
    }

    public double getValue(double[] dArr) {
        return this.coefficients.dotProduct(dArr) + this.constantTerm;
    }

    public double getValue(org.apache.commons.math.linear.RealVector realVector) {
        return this.coefficients.dotProduct(realVector) + this.constantTerm;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.optimization.linear.LinearObjectiveFunction)) {
            return false;
        }
        org.apache.commons.math.optimization.linear.LinearObjectiveFunction linearObjectiveFunction = (org.apache.commons.math.optimization.linear.LinearObjectiveFunction) obj;
        return this.constantTerm == linearObjectiveFunction.constantTerm && this.coefficients.equals(linearObjectiveFunction.coefficients);
    }

    public int hashCode() {
        return java.lang.Double.valueOf(this.constantTerm).hashCode() ^ this.coefficients.hashCode();
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
