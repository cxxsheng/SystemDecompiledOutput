package org.apache.commons.math;

/* loaded from: classes3.dex */
public class FunctionEvaluationException extends org.apache.commons.math.MathException {
    private static final long serialVersionUID = 1384427981840836868L;
    private double[] argument;

    public FunctionEvaluationException(double d) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.EVALUATION_FAILED, java.lang.Double.valueOf(d));
        this.argument = new double[]{d};
    }

    public FunctionEvaluationException(double[] dArr) {
        super(org.apache.commons.math.exception.util.LocalizedFormats.EVALUATION_FAILED, new org.apache.commons.math.linear.ArrayRealVector(dArr));
        this.argument = (double[]) dArr.clone();
    }

    public FunctionEvaluationException(double d, java.lang.String str, java.lang.Object... objArr) {
        this(d, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public FunctionEvaluationException(double d, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
        this.argument = new double[]{d};
    }

    public FunctionEvaluationException(double[] dArr, java.lang.String str, java.lang.Object... objArr) {
        this(dArr, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public FunctionEvaluationException(double[] dArr, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
        this.argument = (double[]) dArr.clone();
    }

    public FunctionEvaluationException(java.lang.Throwable th, double d) {
        super(th);
        this.argument = new double[]{d};
    }

    public FunctionEvaluationException(java.lang.Throwable th, double[] dArr) {
        super(th);
        this.argument = (double[]) dArr.clone();
    }

    public FunctionEvaluationException(java.lang.Throwable th, double d, java.lang.String str, java.lang.Object... objArr) {
        this(th, d, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public FunctionEvaluationException(java.lang.Throwable th, double d, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th, localizable, objArr);
        this.argument = new double[]{d};
    }

    public FunctionEvaluationException(java.lang.Throwable th, double[] dArr, java.lang.String str, java.lang.Object... objArr) {
        this(th, dArr, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public FunctionEvaluationException(java.lang.Throwable th, double[] dArr, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th, localizable, objArr);
        this.argument = (double[]) dArr.clone();
    }

    public double[] getArgument() {
        return (double[]) this.argument.clone();
    }
}
