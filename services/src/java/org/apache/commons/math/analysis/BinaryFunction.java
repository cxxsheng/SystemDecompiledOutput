package org.apache.commons.math.analysis;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class BinaryFunction implements org.apache.commons.math.analysis.BivariateRealFunction {
    public static final org.apache.commons.math.analysis.BinaryFunction ADD = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.1
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return d + d2;
        }
    };
    public static final org.apache.commons.math.analysis.BinaryFunction SUBTRACT = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.2
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return d - d2;
        }
    };
    public static final org.apache.commons.math.analysis.BinaryFunction MULTIPLY = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.3
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return d * d2;
        }
    };
    public static final org.apache.commons.math.analysis.BinaryFunction DIVIDE = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.4
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return d / d2;
        }
    };
    public static final org.apache.commons.math.analysis.BinaryFunction POW = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.5
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return org.apache.commons.math.util.FastMath.pow(d, d2);
        }
    };
    public static final org.apache.commons.math.analysis.BinaryFunction ATAN2 = new org.apache.commons.math.analysis.BinaryFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.6
        @Override // org.apache.commons.math.analysis.BinaryFunction, org.apache.commons.math.analysis.BivariateRealFunction
        public double value(double d, double d2) {
            return org.apache.commons.math.util.FastMath.atan2(d, d2);
        }
    };

    @Override // org.apache.commons.math.analysis.BivariateRealFunction
    public abstract double value(double d, double d2) throws org.apache.commons.math.FunctionEvaluationException;

    public org.apache.commons.math.analysis.ComposableFunction fix1stArgument(final double d) {
        return new org.apache.commons.math.analysis.ComposableFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.7
            @Override // org.apache.commons.math.analysis.ComposableFunction, org.apache.commons.math.analysis.UnivariateRealFunction
            public double value(double d2) throws org.apache.commons.math.FunctionEvaluationException {
                return org.apache.commons.math.analysis.BinaryFunction.this.value(d, d2);
            }
        };
    }

    public org.apache.commons.math.analysis.ComposableFunction fix2ndArgument(final double d) {
        return new org.apache.commons.math.analysis.ComposableFunction() { // from class: org.apache.commons.math.analysis.BinaryFunction.8
            @Override // org.apache.commons.math.analysis.ComposableFunction, org.apache.commons.math.analysis.UnivariateRealFunction
            public double value(double d2) throws org.apache.commons.math.FunctionEvaluationException {
                return org.apache.commons.math.analysis.BinaryFunction.this.value(d2, d);
            }
        };
    }
}
