package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public interface Preconditioner {
    double[] precondition(double[] dArr, double[] dArr2) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException;
}
