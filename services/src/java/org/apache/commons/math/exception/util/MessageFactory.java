package org.apache.commons.math.exception.util;

/* loaded from: classes3.dex */
public class MessageFactory {
    private MessageFactory() {
    }

    public static java.lang.String buildMessage(java.util.Locale locale, org.apache.commons.math.exception.util.Localizable localizable, java.lang.Object... objArr) {
        return buildMessage(locale, null, localizable, objArr);
    }

    public static java.lang.String buildMessage(java.util.Locale locale, org.apache.commons.math.exception.util.Localizable localizable, org.apache.commons.math.exception.util.Localizable localizable2, java.lang.Object... objArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (localizable2 != null) {
            sb.append(new java.text.MessageFormat(localizable2.getLocalizedString(locale), locale).format(objArr));
        }
        if (localizable != null) {
            if (localizable2 != null) {
                sb.append(": ");
            }
            sb.append(new java.text.MessageFormat(localizable.getLocalizedString(locale), locale).format(objArr));
        }
        return sb.toString();
    }
}
