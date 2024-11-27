package org.apache.commons.math;

/* loaded from: classes3.dex */
public class MathRuntimeException extends java.lang.RuntimeException implements org.apache.commons.math.exception.MathThrowable {
    private static final long serialVersionUID = 9058794795027570002L;
    private final java.lang.Object[] arguments;
    private final org.apache.commons.math.exception.util.Localizable pattern;

    @java.lang.Deprecated
    public MathRuntimeException(java.lang.String str, java.lang.Object... objArr) {
        this(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathRuntimeException(org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        this.pattern = localizable;
        this.arguments = objArr == null ? new java.lang.Object[0] : (java.lang.Object[]) objArr.clone();
    }

    public MathRuntimeException(java.lang.Throwable th) {
        super(th);
        this.pattern = org.apache.commons.math.exception.util.LocalizedFormats.SIMPLE_MESSAGE;
        this.arguments = new java.lang.Object[]{th == null ? "" : th.getMessage()};
    }

    @java.lang.Deprecated
    public MathRuntimeException(java.lang.Throwable th, java.lang.String str, java.lang.Object... objArr) {
        this(th, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public MathRuntimeException(java.lang.Throwable th, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        super(th);
        this.pattern = localizable;
        this.arguments = objArr == null ? new java.lang.Object[0] : (java.lang.Object[]) objArr.clone();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String buildMessage(java.util.Locale locale, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        return new java.text.MessageFormat(localizable.getLocalizedString(locale), locale).format(objArr);
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
            return buildMessage(locale, this.pattern, this.arguments);
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

    @java.lang.Deprecated
    public static java.lang.ArithmeticException createArithmeticException(java.lang.String str, java.lang.Object... objArr) {
        return createArithmeticException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.lang.ArithmeticException createArithmeticException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.ArithmeticException() { // from class: org.apache.commons.math.MathRuntimeException.1
            private static final long serialVersionUID = 5305498554076846637L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.lang.ArrayIndexOutOfBoundsException createArrayIndexOutOfBoundsException(java.lang.String str, java.lang.Object... objArr) {
        return createArrayIndexOutOfBoundsException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.lang.ArrayIndexOutOfBoundsException createArrayIndexOutOfBoundsException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.ArrayIndexOutOfBoundsException() { // from class: org.apache.commons.math.MathRuntimeException.2
            private static final long serialVersionUID = 6718518191249632175L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.io.EOFException createEOFException(java.lang.String str, java.lang.Object... objArr) {
        return createEOFException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.io.EOFException createEOFException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.io.EOFException() { // from class: org.apache.commons.math.MathRuntimeException.3
            private static final long serialVersionUID = 6067985859347601503L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    public static java.io.IOException createIOException(java.lang.Throwable th) {
        java.io.IOException iOException = new java.io.IOException(th.getLocalizedMessage());
        iOException.initCause(th);
        return iOException;
    }

    @java.lang.Deprecated
    public static java.lang.IllegalArgumentException createIllegalArgumentException(java.lang.String str, java.lang.Object... objArr) {
        return createIllegalArgumentException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.lang.IllegalArgumentException createIllegalArgumentException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.IllegalArgumentException() { // from class: org.apache.commons.math.MathRuntimeException.4
            private static final long serialVersionUID = -4284649691002411505L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    public static java.lang.IllegalArgumentException createIllegalArgumentException(java.lang.Throwable th) {
        java.lang.IllegalArgumentException illegalArgumentException = new java.lang.IllegalArgumentException(th.getLocalizedMessage());
        illegalArgumentException.initCause(th);
        return illegalArgumentException;
    }

    @java.lang.Deprecated
    public static java.lang.IllegalStateException createIllegalStateException(java.lang.String str, java.lang.Object... objArr) {
        return createIllegalStateException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.lang.IllegalStateException createIllegalStateException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.IllegalStateException() { // from class: org.apache.commons.math.MathRuntimeException.5
            private static final long serialVersionUID = 6880901520234515725L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.util.ConcurrentModificationException createConcurrentModificationException(java.lang.String str, java.lang.Object... objArr) {
        return createConcurrentModificationException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.util.ConcurrentModificationException createConcurrentModificationException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.util.ConcurrentModificationException() { // from class: org.apache.commons.math.MathRuntimeException.6
            private static final long serialVersionUID = -1878427236170442052L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.util.NoSuchElementException createNoSuchElementException(java.lang.String str, java.lang.Object... objArr) {
        return createNoSuchElementException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.util.NoSuchElementException createNoSuchElementException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.util.NoSuchElementException() { // from class: org.apache.commons.math.MathRuntimeException.7
            private static final long serialVersionUID = 1632410088350355086L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.lang.UnsupportedOperationException createUnsupportedOperationException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.UnsupportedOperationException() { // from class: org.apache.commons.math.MathRuntimeException.8
            private static final long serialVersionUID = -4284649691002411505L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.lang.NullPointerException createNullPointerException(java.lang.String str, java.lang.Object... objArr) {
        return createNullPointerException(new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    @java.lang.Deprecated
    public static java.lang.NullPointerException createNullPointerException(final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.lang.NullPointerException() { // from class: org.apache.commons.math.MathRuntimeException.9
            private static final long serialVersionUID = 451965530686593945L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.Localizable.this, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.Localizable.this, objArr);
            }
        };
    }

    @java.lang.Deprecated
    public static java.text.ParseException createParseException(int i, java.lang.String str, java.lang.Object... objArr) {
        return createParseException(i, new org.apache.commons.math.exception.util.DummyLocalizable(str), objArr);
    }

    public static java.text.ParseException createParseException(int i, final org.apache.commons.math.exception.util.Localizable localizable, final java.lang.Object... objArr) {
        return new java.text.ParseException(null, i) { // from class: org.apache.commons.math.MathRuntimeException.10
            private static final long serialVersionUID = 8153587599409010120L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, localizable, objArr);
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), localizable, objArr);
            }
        };
    }

    public static java.lang.RuntimeException createInternalError(java.lang.Throwable th) {
        return new java.lang.RuntimeException(th) { // from class: org.apache.commons.math.MathRuntimeException.11
            private static final long serialVersionUID = -201865440834027016L;

            @Override // java.lang.Throwable
            public java.lang.String getMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.US, org.apache.commons.math.exception.util.LocalizedFormats.INTERNAL_ERROR, "https://issues.apache.org/jira/browse/MATH");
            }

            @Override // java.lang.Throwable
            public java.lang.String getLocalizedMessage() {
                return org.apache.commons.math.MathRuntimeException.buildMessage(java.util.Locale.getDefault(), org.apache.commons.math.exception.util.LocalizedFormats.INTERNAL_ERROR, "https://issues.apache.org/jira/browse/MATH");
            }
        };
    }
}
