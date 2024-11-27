package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public interface ParametricRealFunction {
    double[] gradient(double d, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException;

    double value(double d, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException;
}
