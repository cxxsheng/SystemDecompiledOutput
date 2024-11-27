package org.apache.commons.math;

/* loaded from: classes3.dex */
public class MathException extends java.lang.Exception implements org.apache.commons.math.exception.MathThrowable {
    private static final long serialVersionUID = 7428019509644517071L;
    private final java.lang.Object[] arguments;
    private final org.apache.commons.math.exception.util.Localizable pattern;

    public MathException() {
        this.pattern = org.apache.commons.math.exception.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{""};
    }

    @java.lang.Deprecated
    public MathException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        this.pattern = localizable;
        this.arguments = objArr == null ? new java.lang.Object[0] : (java.lang.Object[]) objArr.clone();
    }

    public MathException(java.lang.Throwable th) {
        super(th);
        this.pattern = org.apache.commons.math.exception.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{th == null ? "" : th.getMessage()};
    }

    @java.lang.Deprecated
    public MathException(java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        this(th, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathException(java.lang.Throwable th, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th);
        this.pattern = localizable;
        this.arguments = objArr == null ? new java.lang.Object[0] : (java.lang.Object[]) objArr.clone();
    }

    @java.lang.Deprecated
    public java.lang.String getPattern() {
        return this.pattern.getSourceString();
    }

    @Override // org.apache.commons.math.exception.MathThrowable
    public org.apache.commons.math.exception.util.Localizable getSpecificPattern() {
        return null;
    }

    @Override // org.apache.commons.math.exception.MathThrowable
    public org.apache.commons.math.exception.util.Localizable getGeneralPattern() {
        return this.pattern;
    }

    @Override // org.apache.commons.math.exception.MathThrowable
    public java.lang.Object[] getArguments() {
        return (java.lang.Object[]) this.arguments.clone();
    }

    @Override // org.apache.commons.math.exception.MathThrowable
    public java.lang.String getMessage(java.util.Locale locale) {
        if (this.pattern != null) {
            return new java.text.MessageFormat(this.pattern.getLocalizedString(locale), locale).format(this.arguments);
        }
        return "";
    }

    @Override // java.lang.Throwable, org.apache.commons.math.exception.MathThrowable
    public java.lang.String getMessage() {
        return getMessage(java.util.Locale.US);
    }

    @Override // java.lang.Throwable, org.apache.commons.math.exception.MathThrowable
    public java.lang.String getLocalizedMessage() {
        return getMessage(java.util.Locale.getDefault());
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        printStackTrace(java.lang.System.err);
    }

    @Override // java.lang.Throwable
    public void printStackTrace(java.io.PrintStream printStream) {
        synchronized (printStream) {
            java.io.PrintWriter printWriter = new java.io.PrintWriter((java.io.OutputStream) printStream, false);
            printStackTrace(printWriter);
            printWriter.flush();
        }
    }
}
