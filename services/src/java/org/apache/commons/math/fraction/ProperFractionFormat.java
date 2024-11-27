package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class ProperFractionFormat extends org.apache.commons.math.fraction.FractionFormat {
    private static final long serialVersionUID = 760934726031766749L;
    private java.text.NumberFormat wholeFormat;

    public ProperFractionFormat() {
        this(org.apache.commons.math.fraction.FractionFormat.getDefaultNumberFormat());
    }

    public ProperFractionFormat(java.text.NumberFormat numberFormat) {
        this(numberFormat, (java.text.NumberFormat) numberFormat.clone(), (java.text.NumberFormat) numberFormat.clone());
    }

    public ProperFractionFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2, java.text.NumberFormat numberFormat3) {
        super(numberFormat2, numberFormat3);
        setWholeFormat(numberFormat);
    }

    @Override // org.apache.commons.math.fraction.FractionFormat
    public java.lang.StringBuffer format(org.apache.commons.math.fraction.Fraction fraction, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        int i = numerator / denominator;
        int i2 = numerator % denominator;
        if (i != 0) {
            getWholeFormat().format(i, stringBuffer, fieldPosition);
            stringBuffer.append(' ');
            i2 = java.lang.Math.abs(i2);
        }
        getNumeratorFormat().format(i2, stringBuffer, fieldPosition);
        stringBuffer.append(" / ");
        getDenominatorFormat().format(denominator, stringBuffer, fieldPosition);
        return stringBuffer;
    }

    public java.text.NumberFormat getWholeFormat() {
        return this.wholeFormat;
    }

    @Override // org.apache.commons.math.fraction.FractionFormat, java.text.NumberFormat
    public org.apache.commons.math.fraction.Fraction parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        org.apache.commons.math.fraction.Fraction parse = super.parse(str, parsePosition);
        if (parse != null) {
            return parse;
        }
        int index = parsePosition.getIndex();
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parse2 = getWholeFormat().parse(str, parsePosition);
        if (parse2 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parse3 = getNumeratorFormat().parse(str, parsePosition);
        if (parse3 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        if (parse3.intValue() < 0) {
            parsePosition.setIndex(index);
            return null;
        }
        int index2 = parsePosition.getIndex();
        switch (org.apache.commons.math.fraction.AbstractFormat.parseNextCharacter(str, parsePosition)) {
            case 0:
                return new org.apache.commons.math.fraction.Fraction(parse3.intValue(), 1);
            case '/':
                org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
                java.lang.Number parse4 = getDenominatorFormat().parse(str, parsePosition);
                if (parse4 == null) {
                    parsePosition.setIndex(index);
                    return null;
                }
                if (parse4.intValue() < 0) {
                    parsePosition.setIndex(index);
                    return null;
                }
                int intValue = parse2.intValue();
                int intValue2 = parse3.intValue();
                int intValue3 = parse4.intValue();
                return new org.apache.commons.math.fraction.Fraction(((java.lang.Math.abs(intValue) * intValue3) + intValue2) * org.apache.commons.math.util.MathUtils.sign(intValue), intValue3);
            default:
                parsePosition.setIndex(index);
                parsePosition.setErrorIndex(index2);
                return null;
        }
    }

    public void setWholeFormat(java.text.NumberFormat numberFormat) {
        if (numberFormat == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.WHOLE_FORMAT);
        }
        this.wholeFormat = numberFormat;
    }
}
