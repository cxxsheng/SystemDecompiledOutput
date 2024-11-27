package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public class NoDataException extends org.apache.commons.math.exception.MathIllegalStateException {
    private static final long serialVersionUID = -3629324471511904459L;

    public NoDataException() {
        this(null);
    }

    public NoDataException(org.apache.commons.math.exception.util.Localizable localizable) {
        super(localizable, org.apache.commons.math.exception.util.LocalizedFormats.NO_DATA, (java.lang.Object[]) null);
    }
}
