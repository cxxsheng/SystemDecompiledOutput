package org.apache.commons.math.exception;

/* loaded from: classes3.dex */
public interface MathThrowable {
    java.lang.Object[] getArguments();

    org.apache.commons.math.exception.util.Localizable getGeneralPattern();

    java.lang.String getLocalizedMessage();

    java.lang.String getMessage();

    java.lang.String getMessage(java.util.Locale locale);

    org.apache.commons.math.exception.util.Localizable getSpecificPattern();
}
