package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public abstract class AbstractFormat extends java.text.NumberFormat implements java.io.Serializable {
    private static final long serialVersionUID = -6981118387974191891L;
    protected java.text.NumberFormat denominatorFormat;
    protected java.text.NumberFormat numeratorFormat;

    protected AbstractFormat() {
        this(getDefaultNumberFormat());
    }

    protected AbstractFormat(java.text.NumberFormat numberFormat) {
        this(numberFormat, (java.text.NumberFormat) numberFormat.clone());
    }

    protected AbstractFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2) {
        this.numeratorFormat = numberFormat;
        this.denominatorFormat = numberFormat2;
    }

    protected static java.text.NumberFormat getDefaultNumberFormat() {
        return getDefaultNumberFormat(java.util.Locale.getDefault());
    }

    protected static java.text.NumberFormat getDefaultNumberFormat(java.util.Locale locale) {
        java.text.NumberFormat numberInstance = java.text.NumberFormat.getNumberInstance(locale);
        numberInstance.setMaximumFractionDigits(0);
        numberInstance.setParseIntegerOnly(true);
        return numberInstance;
    }

    public java.text.NumberFormat getDenominatorFormat() {
        return this.denominatorFormat;
    }

    public java.text.NumberFormat getNumeratorFormat() {
        return this.numeratorFormat;
    }

    public void setDenominatorFormat(java.text.NumberFormat numberFormat) {
        if (numberFormat == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DENOMINATOR_FORMAT);
        }
        this.denominatorFormat = numberFormat;
    }

    public void setNumeratorFormat(java.text.NumberFormat numberFormat) {
        if (numberFormat == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NUMERATOR_FORMAT);
        }
        this.numeratorFormat = numberFormat;
    }

    protected static void parseAndIgnoreWhitespace(java.lang.String str, java.text.ParsePosition parsePosition) {
        parseNextCharacter(str, parsePosition);
        parsePosition.setIndex(parsePosition.getIndex() - 1);
    }

    protected static char parseNextCharacter(java.lang.String str, java.text.ParsePosition parsePosition) {
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

    @Override // java.text.NumberFormat
    public java.lang.StringBuffer format(double d, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        return format(java.lang.Double.valueOf(d), stringBuffer, fieldPosition);
    }

    @Override // java.text.NumberFormat
    public java.lang.StringBuffer format(long j, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        return format(java.lang.Long.valueOf(j), stringBuffer, fieldPosition);
    }
}
