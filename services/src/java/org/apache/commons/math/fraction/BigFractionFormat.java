package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class BigFractionFormat extends org.apache.commons.math.fraction.AbstractFormat implements java.io.Serializable {
    private static final long serialVersionUID = -2932167925527338976L;

    public BigFractionFormat() {
    }

    public BigFractionFormat(java.text.NumberFormat numberFormat) {
        super(numberFormat);
    }

    public BigFractionFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2) {
        super(numberFormat, numberFormat2);
    }

    public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    public static java.lang.String formatBigFraction(org.apache.commons.math.fraction.BigFraction bigFraction) {
        return getImproperInstance().format(bigFraction);
    }

    public static org.apache.commons.math.fraction.BigFractionFormat getImproperInstance() {
        return getImproperInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.fraction.BigFractionFormat getImproperInstance(java.util.Locale locale) {
        return new org.apache.commons.math.fraction.BigFractionFormat(org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat(locale));
    }

    public static org.apache.commons.math.fraction.BigFractionFormat getProperInstance() {
        return getProperInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.fraction.BigFractionFormat getProperInstance(java.util.Locale locale) {
        return new org.apache.commons.math.fraction.ProperBigFractionFormat(org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat(locale));
    }

    public java.lang.StringBuffer format(org.apache.commons.math.fraction.BigFraction bigFraction, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        getNumeratorFormat().format(bigFraction.getNumerator(), stringBuffer, fieldPosition);
        stringBuffer.append(" / ");
        getDenominatorFormat().format(bigFraction.getDenominator(), stringBuffer, fieldPosition);
        return stringBuffer;
    }

    @Override // java.text.NumberFormat, java.text.Format
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (obj instanceof org.apache.commons.math.fraction.BigFraction) {
            return format((org.apache.commons.math.fraction.BigFraction) obj, stringBuffer, fieldPosition);
        }
        if (obj instanceof java.math.BigInteger) {
            return format(new org.apache.commons.math.fraction.BigFraction((java.math.BigInteger) obj), stringBuffer, fieldPosition);
        }
        if (obj instanceof java.lang.Number) {
            return format(new org.apache.commons.math.fraction.BigFraction(((java.lang.Number) obj).doubleValue()), stringBuffer, fieldPosition);
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_FORMAT_OBJECT_TO_FRACTION, new java.lang.Object[0]);
    }

    @Override // java.text.NumberFormat
    public org.apache.commons.math.fraction.BigFraction parse(java.lang.String str) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.fraction.BigFraction parse = parse(str, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(parsePosition.getErrorIndex(), org.apache.commons.math.exception.util.LocalizedFormats.UNPARSEABLE_FRACTION_NUMBER, str);
        }
        return parse;
    }

    @Override // java.text.NumberFormat
    public org.apache.commons.math.fraction.BigFraction parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.math.BigInteger parseNextBigInteger = parseNextBigInteger(str, parsePosition);
        if (parseNextBigInteger == null) {
            parsePosition.setIndex(index);
            return null;
        }
        int index2 = parsePosition.getIndex();
        switch (org.apache.commons.math.fraction.AbstractFormat.parseNextCharacter(str, parsePosition)) {
            case 0:
                break;
            case '/':
                org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
                java.math.BigInteger parseNextBigInteger2 = parseNextBigInteger(str, parsePosition);
                if (parseNextBigInteger2 != null) {
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

    protected java.math.BigInteger parseNextBigInteger(java.lang.String str, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        int i = str.charAt(index) == '-' ? index + 1 : index;
        while (i < str.length() && java.lang.Character.isDigit(str.charAt(i))) {
            i++;
        }
        try {
            java.math.BigInteger bigInteger = new java.math.BigInteger(str.substring(index, i));
            parsePosition.setIndex(i);
            return bigInteger;
        } catch (java.lang.NumberFormatException e) {
            parsePosition.setErrorIndex(index);
            return null;
        }
    }
}
