package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class FractionFormat extends org.apache.commons.math.fraction.AbstractFormat {
    private static final long serialVersionUID = 3008655719530972611L;

    public FractionFormat() {
    }

    public FractionFormat(java.text.NumberFormat numberFormat) {
        super(numberFormat);
    }

    public FractionFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2) {
        super(numberFormat, numberFormat2);
    }

    public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    public static java.lang.String formatFraction(org.apache.commons.math.fraction.Fraction fraction) {
        return getImproperInstance().format(fraction);
    }

    public static org.apache.commons.math.fraction.FractionFormat getImproperInstance() {
        return getImproperInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.fraction.FractionFormat getImproperInstance(java.util.Locale locale) {
        return new org.apache.commons.math.fraction.FractionFormat(org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat(locale));
    }

    public static org.apache.commons.math.fraction.FractionFormat getProperInstance() {
        return getProperInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.fraction.FractionFormat getProperInstance(java.util.Locale locale) {
        return new org.apache.commons.math.fraction.ProperFractionFormat(org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat(locale));
    }

    protected static java.text.NumberFormat getDefaultNumberFormat() {
        return org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat(java.util.Locale.getDefault());
    }

    public java.lang.StringBuffer format(org.apache.commons.math.fraction.Fraction fraction, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        getNumeratorFormat().format(fraction.getNumerator(), stringBuffer, fieldPosition);
        stringBuffer.append(" / ");
        getDenominatorFormat().format(fraction.getDenominator(), stringBuffer, fieldPosition);
        return stringBuffer;
    }

    @Override // java.text.NumberFormat, java.text.Format
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (obj instanceof org.apache.commons.math.fraction.Fraction) {
            return format((org.apache.commons.math.fraction.Fraction) obj, stringBuffer, fieldPosition);
        }
        if (obj instanceof java.lang.Number) {
            try {
                return format(new org.apache.commons.math.fraction.Fraction(((java.lang.Number) obj).doubleValue()), stringBuffer, fieldPosition);
            } catch (org.apache.commons.math.ConvergenceException e) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_CONVERT_OBJECT_TO_FRACTION, e.getLocalizedMessage());
            }
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_FORMAT_OBJECT_TO_FRACTION, new java.lang.Object[0]);
    }

    @Override // java.text.NumberFormat
    public org.apache.commons.math.fraction.Fraction parse(java.lang.String str) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.fraction.Fraction parse = parse(str, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(parsePosition.getErrorIndex(), org.apache.commons.math.exception.util.LocalizedFormats.UNPARSEABLE_FRACTION_NUMBER, str);
        }
        return parse;
    }

    @Override // java.text.NumberFormat
    public org.apache.commons.math.fraction.Fraction parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parse = getNumeratorFormat().parse(str, parsePosition);
        if (parse == null) {
            parsePosition.setIndex(index);
            return null;
        }
        int index2 = parsePosition.getIndex();
        switch (org.apache.commons.math.fraction.AbstractFormat.parseNextCharacter(str, parsePosition)) {
            case 0:
                break;
            case '/':
                org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
                java.lang.Number parse2 = getDenominatorFormat().parse(str, parsePosition);
                if (parse2 != null) {
                    break;
                } else {
                    parsePosition.setIndex(index);
                    break;
                }
            default:
                parsePosition.setIndex(index);
                parsePosition.setErrorIndex(index2);
                break;
        }
        return null;
    }
}
