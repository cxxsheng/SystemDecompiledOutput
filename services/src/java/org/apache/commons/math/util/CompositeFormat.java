package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public abstract class CompositeFormat extends java.text.Format {
    private static final long serialVersionUID = 5358685519349262494L;

    protected static java.text.NumberFormat getDefaultNumberFormat() {
        return getDefaultNumberFormat(java.util.Locale.getDefault());
    }

    protected static java.text.NumberFormat getDefaultNumberFormat(java.util.Locale locale) {
        java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(locale);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    protected void parseAndIgnoreWhitespace(java.lang.String str, java.text.ParsePosition parsePosition) {
        parseNextCharacter(str, parsePosition);
        parsePosition.setIndex(parsePosition.getIndex() - 1);
    }

    protected char parseNextCharacter(java.lang.String str, java.text.ParsePosition parsePosition) {
        int i;
        char charAt;
        int index = parsePosition.getIndex();
        int length = str.length();
        if (index < length) {
            while (true) {
                i = index + 1;
                charAt = str.charAt(index);
                if (!java.lang.Character.isWhitespace(charAt) || i >= length) {
                    break;
                }
                index = i;
            }
            parsePosition.setIndex(i);
            if (i < length) {
                return charAt;
            }
        }
        return (char) 0;
    }

    private java.lang.Number parseNumber(java.lang.String str, double d, java.text.ParsePosition parsePosition) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('(');
        sb.append(d);
        sb.append(')');
        int length = sb.length();
        int index = parsePosition.getIndex();
        int i = length + index;
        if (i < str.length() && str.substring(index, i).compareTo(sb.toString()) == 0) {
            java.lang.Double valueOf = java.lang.Double.valueOf(d);
            parsePosition.setIndex(i);
            return valueOf;
        }
        return null;
    }

    protected java.lang.Number parseNumber(java.lang.String str, java.text.NumberFormat numberFormat, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        java.lang.Number parse = numberFormat.parse(str, parsePosition);
        if (index == parsePosition.getIndex()) {
            double[] dArr = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
            for (int i = 0; i < 3; i++) {
                parse = parseNumber(str, dArr[i], parsePosition);
                if (parse != null) {
                    break;
                }
            }
        }
        return parse;
    }

    protected boolean parseFixedstring(java.lang.String str, java.lang.String str2, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        int length = str2.length() + index;
        if (index >= str.length() || length > str.length() || str.substring(index, length).compareTo(str2) != 0) {
            parsePosition.setIndex(index);
            parsePosition.setErrorIndex(index);
            return false;
        }
        parsePosition.setIndex(length);
        return true;
    }

    protected java.lang.StringBuffer formatDouble(double d, java.text.NumberFormat numberFormat, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isInfinite(d)) {
            stringBuffer.append('(');
            stringBuffer.append(d);
            stringBuffer.append(')');
        } else {
            numberFormat.format(d, stringBuffer, fieldPosition);
        }
        return stringBuffer;
    }
}
