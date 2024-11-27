package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public abstract class AbstractRealVector implements org.apache.commons.math.linear.RealVector {
    @Override // org.apache.commons.math.linear.RealVector
    public abstract org.apache.commons.math.linear.AbstractRealVector copy();

    protected void checkVectorDimensions(org.apache.commons.math.linear.RealVector realVector) {
        checkVectorDimensions(realVector.getDimension());
    }

    protected void checkVectorDimensions(int i) throws org.apache.commons.math.exception.DimensionMismatchException {
        int dimension = getDimension();
        if (dimension != i) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dimension, i);
        }
    }

    protected void checkIndex(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        if (i < 0 || i >= getDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(getDimension() - 1));
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((realVector.getDimension() + i) - 1);
        setSubVector(i, realVector.getData());
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((dArr.length + i) - 1);
        for (int i2 = 0; i2 < dArr.length; i2++) {
            setEntry(i2 + i, dArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector add(double[] dArr) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        double[] dArr2 = (double[]) dArr.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            dArr2[index] = dArr2[index] + next.getValue();
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector add(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return add(((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef());
        }
        org.apache.commons.math.linear.RealVector copy = realVector.copy();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            copy.setEntry(index, next.getValue() + copy.getEntry(index));
        }
        return copy;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector subtract(double[] dArr) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        double[] dArr2 = (double[]) dArr.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            dArr2[index] = next.getValue() - dArr2[index];
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector subtract(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return add(((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef());
        }
        org.apache.commons.math.linear.RealVector copy = realVector.copy();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            realVector.setEntry(index, next.getValue() - copy.getEntry(index));
        }
        return copy;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAdd(double d) {
        return copy().mapAddToSelf(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAddToSelf(double d) {
        if (d != 0.0d) {
            try {
                return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.ADD.fix1stArgument(d));
            } catch (org.apache.commons.math.FunctionEvaluationException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double dotProduct(double[] dArr) throws java.lang.IllegalArgumentException {
        return dotProduct(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double dotProduct(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(realVector);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        double d = 0.0d;
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            d += next.getValue() * realVector.getEntry(next.getIndex());
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeDivide(double[] dArr) throws java.lang.IllegalArgumentException {
        return ebeDivide(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeMultiply(double[] dArr) throws java.lang.IllegalArgumentException {
        return ebeMultiply(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(realVector);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        double d = 0.0d;
        while (it.hasNext() && (next = it.next()) != null) {
            double value = next.getValue() - realVector.getEntry(next.getIndex());
            d += value * value;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getNorm() {
        org.apache.commons.math.linear.RealVector.Entry next;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        double d = 0.0d;
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            double value = next.getValue();
            d += value * value;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getL1Norm() {
        org.apache.commons.math.linear.RealVector.Entry next;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        double d = 0.0d;
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            d += org.apache.commons.math.util.FastMath.abs(next.getValue());
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getLInfNorm() {
        org.apache.commons.math.linear.RealVector.Entry next;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        double d = 0.0d;
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            d = org.apache.commons.math.util.FastMath.max(d, org.apache.commons.math.util.FastMath.abs(next.getValue()));
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        return getDistance(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getL1Distance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(realVector);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        double d = 0.0d;
        while (it.hasNext() && (next = it.next()) != null) {
            d += org.apache.commons.math.util.FastMath.abs(next.getValue() - realVector.getEntry(next.getIndex()));
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getL1Distance(double[] dArr) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(dArr.length);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        double d = 0.0d;
        while (it.hasNext() && (next = it.next()) != null) {
            d += org.apache.commons.math.util.FastMath.abs(next.getValue() - dArr[next.getIndex()]);
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getLInfDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(realVector);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        double d = 0.0d;
        while (it.hasNext() && (next = it.next()) != null) {
            d = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(next.getValue() - realVector.getEntry(next.getIndex())), d);
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getLInfDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        checkVectorDimensions(dArr.length);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        double d = 0.0d;
        while (it.hasNext() && (next = it.next()) != null) {
            d = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(next.getValue() - dArr[next.getIndex()]), d);
        }
        return d;
    }

    public int getMinIndex() {
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        int i = -1;
        double d = Double.POSITIVE_INFINITY;
        while (it.hasNext()) {
            org.apache.commons.math.linear.RealVector.Entry next = it.next();
            if (next.getValue() <= d) {
                i = next.getIndex();
                d = next.getValue();
            }
        }
        return i;
    }

    public double getMinValue() {
        int minIndex = getMinIndex();
        if (minIndex < 0) {
            return Double.NaN;
        }
        return getEntry(minIndex);
    }

    public int getMaxIndex() {
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        int i = -1;
        double d = Double.NEGATIVE_INFINITY;
        while (it.hasNext()) {
            org.apache.commons.math.linear.RealVector.Entry next = it.next();
            if (next.getValue() >= d) {
                i = next.getIndex();
                d = next.getValue();
            }
        }
        return i;
    }

    public double getMaxValue() {
        int maxIndex = getMaxIndex();
        if (maxIndex < 0) {
            return Double.NaN;
        }
        return getEntry(maxIndex);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAbs() {
        return copy().mapAbsToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAbsToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ABS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAcos() {
        return copy().mapAcosToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAcosToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ACOS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAsin() {
        return copy().mapAsinToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAsinToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ASIN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAtan() {
        return copy().mapAtanToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAtanToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ATAN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCbrt() {
        return copy().mapCbrtToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCbrtToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.CBRT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCeil() {
        return copy().mapCeilToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCeilToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.CEIL);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCos() {
        return copy().mapCosToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCosToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.COS);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCosh() {
        return copy().mapCoshToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCoshToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.COSH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapDivide(double d) {
        return copy().mapDivideToSelf(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapDivideToSelf(double d) {
        try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.DIVIDE.fix2ndArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExp() {
        return copy().mapExpToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExpToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.EXP);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExpm1() {
        return copy().mapExpm1ToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExpm1ToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.EXPM1);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapFloor() {
        return copy().mapFloorToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapFloorToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.FLOOR);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapInv() {
        return copy().mapInvToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapInvToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.INVERT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog() {
        return copy().mapLogToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLogToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog10() {
        return copy().mapLog10ToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog10ToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG10);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog1p() {
        return copy().mapLog1pToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog1pToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.LOG1P);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapMultiply(double d) {
        return copy().mapMultiplyToSelf(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapMultiplyToSelf(double d) {
        try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.MULTIPLY.fix1stArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapPow(double d) {
        return copy().mapPowToSelf(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapPowToSelf(double d) {
        try {
            return mapToSelf(org.apache.commons.math.analysis.BinaryFunction.POW.fix2ndArgument(d));
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapRint() {
        return copy().mapRintToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapRintToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.RINT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSignum() {
        return copy().mapSignumToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSignumToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SIGNUM);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSin() {
        return copy().mapSinToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSinToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SIN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSinh() {
        return copy().mapSinhToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSinhToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SINH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSqrt() {
        return copy().mapSqrtToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSqrtToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.SQRT);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSubtract(double d) {
        return copy().mapSubtractToSelf(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSubtractToSelf(double d) {
        return mapAddToSelf(-d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTan() {
        return copy().mapTanToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTanToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.TAN);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTanh() {
        return copy().mapTanhToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTanhToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.TANH);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapUlp() {
        return copy().mapUlpToSelf();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapUlpToSelf() {
        try {
            return mapToSelf(org.apache.commons.math.analysis.ComposableFunction.ULP);
        } catch (org.apache.commons.math.FunctionEvaluationException e) {
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealMatrix outerProduct(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealMatrix openMapRealMatrix;
        org.apache.commons.math.linear.RealVector.Entry next;
        org.apache.commons.math.linear.RealVector.Entry next2;
        if ((realVector instanceof org.apache.commons.math.linear.SparseRealVector) || (this instanceof org.apache.commons.math.linear.SparseRealVector)) {
            openMapRealMatrix = new org.apache.commons.math.linear.OpenMapRealMatrix(getDimension(), realVector.getDimension());
        } else {
            openMapRealMatrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(getDimension(), realVector.getDimension());
        }
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator2 = realVector.sparseIterator();
            while (sparseIterator2.hasNext() && (next2 = sparseIterator2.next()) != null) {
                openMapRealMatrix.setEntry(next.getIndex(), next2.getIndex(), next.getValue() * next2.getValue());
            }
        }
        return openMapRealMatrix;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealMatrix outerProduct(double[] dArr) throws java.lang.IllegalArgumentException {
        return outerProduct(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector projection(double[] dArr) throws java.lang.IllegalArgumentException {
        return projection(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void set(double d) {
        org.apache.commons.math.linear.RealVector.Entry next;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> it = iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            next.setValue(d);
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double[] toArray() {
        int dimension = getDimension();
        double[] dArr = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            dArr[i] = getEntry(i);
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double[] getData() {
        return toArray();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector unitVector() {
        org.apache.commons.math.linear.AbstractRealVector copy = copy();
        copy.unitize();
        return copy;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void unitize() {
        mapDivideToSelf(getNorm());
    }

    @Override // org.apache.commons.math.linear.RealVector
    public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator() {
        return new org.apache.commons.math.linear.AbstractRealVector.SparseEntryIterator();
    }

    @Override // org.apache.commons.math.linear.RealVector
    public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> iterator() {
        final int dimension = getDimension();
        return new java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry>() { // from class: org.apache.commons.math.linear.AbstractRealVector.1
            private org.apache.commons.math.linear.AbstractRealVector.EntryImpl e;
            private int i = 0;

            {
                this.e = org.apache.commons.math.linear.AbstractRealVector.this.new EntryImpl();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.i < dimension;
            }

            @Override // java.util.Iterator
            public org.apache.commons.math.linear.RealVector.Entry next() {
                org.apache.commons.math.linear.AbstractRealVector.EntryImpl entryImpl = this.e;
                int i = this.i;
                this.i = i + 1;
                entryImpl.setIndex(i);
                return this.e;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new org.apache.commons.math.exception.MathUnsupportedOperationException(new java.lang.Object[0]);
            }
        };
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector map(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) throws org.apache.commons.math.FunctionEvaluationException {
        return copy().mapToSelf(univariateRealFunction);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapToSelf(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) throws org.apache.commons.math.FunctionEvaluationException {
        org.apache.commons.math.linear.RealVector.Entry next;
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = univariateRealFunction.value(0.0d) == 0.0d ? sparseIterator() : iterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            next.setValue(univariateRealFunction.value(next.getValue()));
        }
        return this;
    }

    protected class EntryImpl extends org.apache.commons.math.linear.RealVector.Entry {
        public EntryImpl() {
            setIndex(0);
        }

        @Override // org.apache.commons.math.linear.RealVector.Entry
        public double getValue() {
            return org.apache.commons.math.linear.AbstractRealVector.this.getEntry(getIndex());
        }

        @Override // org.apache.commons.math.linear.RealVector.Entry
        public void setValue(double d) {
            org.apache.commons.math.linear.AbstractRealVector.this.setEntry(getIndex(), d);
        }
    }

    protected class SparseEntryIterator implements java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> {
        private org.apache.commons.math.linear.AbstractRealVector.EntryImpl current;
        private final int dim;
        private org.apache.commons.math.linear.AbstractRealVector.EntryImpl next;

        protected SparseEntryIterator() {
            this.dim = org.apache.commons.math.linear.AbstractRealVector.this.getDimension();
            this.current = org.apache.commons.math.linear.AbstractRealVector.this.new EntryImpl();
            this.next = org.apache.commons.math.linear.AbstractRealVector.this.new EntryImpl();
            if (this.next.getValue() == 0.0d) {
                advance(this.next);
            }
        }

        protected void advance(org.apache.commons.math.linear.AbstractRealVector.EntryImpl entryImpl) {
            if (entryImpl == null) {
                return;
            }
            do {
                entryImpl.setIndex(entryImpl.getIndex() + 1);
                if (entryImpl.getIndex() >= this.dim) {
                    break;
                }
            } while (entryImpl.getValue() == 0.0d);
            if (entryImpl.getIndex() >= this.dim) {
                entryImpl.setIndex(-1);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next.getIndex() >= 0;
        }

        @Override // java.util.Iterator
        public org.apache.commons.math.linear.RealVector.Entry next() {
            int index = this.next.getIndex();
            if (index < 0) {
                throw new java.util.NoSuchElementException();
            }
            this.current.setIndex(index);
            advance(this.next);
            return this.current;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new org.apache.commons.math.exception.MathUnsupportedOperationException(new java.lang.Object[0]);
        }
    }
}
