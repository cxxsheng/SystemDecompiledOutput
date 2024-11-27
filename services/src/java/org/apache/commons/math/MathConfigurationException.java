package org.apache.commons.math;

/* loaded from: classes3.dex */
public class MathConfigurationException extends org.apache.commons.math.MathException implements java.io.Serializable {
    private static final long serialVersionUID = 5261476508226103366L;

    public MathConfigurationException() {
    }

    public MathConfigurationException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathConfigurationException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(localizable, objArr);
    }

    public MathConfigurationException(java.lang.Throwable th) {
        super(th);
    }

    public MathConfigurationException(java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        this(th, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathConfigurationException(java.lang.Throwable th, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th, localizable, objArr);
    }
}
