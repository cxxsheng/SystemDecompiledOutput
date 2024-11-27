package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class ProperBigFractionFormat extends org.apache.commons.math.fraction.BigFractionFormat {
    private static final long serialVersionUID = -6337346779577272307L;
    private java.text.NumberFormat wholeFormat;

    public ProperBigFractionFormat() {
        this(org.apache.commons.math.fraction.AbstractFormat.getDefaultNumberFormat());
    }

    public ProperBigFractionFormat(java.text.NumberFormat numberFormat) {
        this(numberFormat, (java.text.NumberFormat) numberFormat.clone(), (java.text.NumberFormat) numberFormat.clone());
    }

    public ProperBigFractionFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2, java.text.NumberFormat numberFormat3) {
        super(numberFormat2, numberFormat3);
        setWholeFormat(numberFormat);
    }

    @Override // org.apache.commons.math.fraction.BigFractionFormat
    public java.lang.StringBuffer format(org.apache.commons.math.fraction.BigFraction bigFraction, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        java.math.BigInteger numerator = bigFraction.getNumerator();
        java.math.BigInteger denominator = bigFraction.getDenominator();
        java.math.BigInteger divide = numerator.divide(denominator);
        java.math.BigInteger remainder = numerator.remainder(denominator);
        if (!java.math.BigInteger.ZERO.equals(divide)) {
            getWholeFormat().format(divide, stringBuffer, fieldPosition);
            stringBuffer.append(' ');
            if (remainder.compareTo(java.math.BigInteger.ZERO) < 0) {
                remainder = remainder.negate();
            }
        }
        getNumeratorFormat().format(remainder, stringBuffer, fieldPosition);
        stringBuffer.append(" / ");
        getDenominatorFormat().format(denominator, stringBuffer, fieldPosition);
        return stringBuffer;
    }

    public java.text.NumberFormat getWholeFormat() {
        return this.wholeFormat;
    }

    @Override // org.apache.commons.math.fraction.BigFractionFormat, java.text.NumberFormat
    public org.apache.commons.math.fraction.BigFraction parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        org.apache.commons.math.fraction.BigFraction parse = super.parse(str, parsePosition);
        if (parse != null) {
            return parse;
        }
        int index = parsePosition.getIndex();
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.math.BigInteger parseNextBigInteger = parseNextBigInteger(str, parsePosition);
        if (parseNextBigInteger == null) {
            parsePosition.setIndex(index);
            return null;
        }
        org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
        java.math.BigInteger parseNextBigInteger2 = parseNextBigInteger(str, parsePosition);
        if (parseNextBigInteger2 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        if (parseNextBigInteger2.compareTo(java.math.BigInteger.ZERO) < 0) {
            parsePosition.setIndex(index);
            return null;
        }
        int index2 = parsePosition.getIndex();
        switch (org.apache.commons.math.fraction.AbstractFormat.parseNextCharacter(str, parsePosition)) {
            case 0:
                return new org.apache.commons.math.fraction.BigFraction(parseNextBigInteger2);
            case '/':
                org.apache.commons.math.fraction.AbstractFormat.parseAndIgnoreWhitespace(str, parsePosition);
                java.math.BigInteger parseNextBigInteger3 = parseNextBigInteger(str, parsePosition);
                if (parseNextBigInteger3 == null) {
                    parsePosition.setIndex(index);
                    return null;
                }
                if (parseNextBigInteger3.compareTo(java.math.BigInteger.ZERO) < 0) {
                    parsePosition.setIndex(index);
                    return null;
                }
                boolean z = parseNextBigInteger.compareTo(java.math.BigInteger.ZERO) < 0;
                if (z) {
                    parseNextBigInteger = parseNextBigInteger.negate();
                }
                java.math.BigInteger add = parseNextBigInteger.multiply(parseNextBigInteger3).add(parseNextBigInteger2);
                if (z) {
                    add = add.negate();
                }
                return new org.apache.commons.math.fraction.BigFraction(add, parseNextBigInteger3);
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
