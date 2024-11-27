package org.apache.commons.math.complex;

/* loaded from: classes3.dex */
public class ComplexFormat extends org.apache.commons.math.util.CompositeFormat {
    private static final java.lang.String DEFAULT_IMAGINARY_CHARACTER = "i";
    private static final long serialVersionUID = -3343698360149467646L;
    private java.lang.String imaginaryCharacter;
    private java.text.NumberFormat imaginaryFormat;
    private java.text.NumberFormat realFormat;

    public ComplexFormat() {
        this(DEFAULT_IMAGINARY_CHARACTER, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public ComplexFormat(java.text.NumberFormat numberFormat) {
        this(DEFAULT_IMAGINARY_CHARACTER, numberFormat);
    }

    public ComplexFormat(java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2) {
        this(DEFAULT_IMAGINARY_CHARACTER, numberFormat, numberFormat2);
    }

    public ComplexFormat(java.lang.String str) {
        this(str, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public ComplexFormat(java.lang.String str, java.text.NumberFormat numberFormat) {
        this(str, numberFormat, (java.text.NumberFormat) numberFormat.clone());
    }

    public ComplexFormat(java.lang.String str, java.text.NumberFormat numberFormat, java.text.NumberFormat numberFormat2) {
        setImaginaryCharacter(str);
        setImaginaryFormat(numberFormat2);
        setRealFormat(numberFormat);
    }

    public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    public static java.lang.String formatComplex(org.apache.commons.math.complex.Complex complex) {
        return getInstance().format(complex);
    }

    public java.lang.StringBuffer format(org.apache.commons.math.complex.Complex complex, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        formatDouble(complex.getReal(), getRealFormat(), stringBuffer, fieldPosition);
        double imaginary = complex.getImaginary();
        if (imaginary < 0.0d) {
            stringBuffer.append(" - ");
            formatDouble(-imaginary, getImaginaryFormat(), stringBuffer, fieldPosition);
            stringBuffer.append(getImaginaryCharacter());
        } else if (imaginary > 0.0d || java.lang.Double.isNaN(imaginary)) {
            stringBuffer.append(" + ");
            formatDouble(imaginary, getImaginaryFormat(), stringBuffer, fieldPosition);
            stringBuffer.append(getImaginaryCharacter());
        }
        return stringBuffer;
    }

    @Override // java.text.Format
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (obj instanceof org.apache.commons.math.complex.Complex) {
            return format((org.apache.commons.math.complex.Complex) obj, stringBuffer, fieldPosition);
        }
        if (obj instanceof java.lang.Number) {
            return format(new org.apache.commons.math.complex.Complex(((java.lang.Number) obj).doubleValue(), 0.0d), stringBuffer, fieldPosition);
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_COMPLEX, obj.getClass().getName());
    }

    public java.lang.String getImaginaryCharacter() {
        return this.imaginaryCharacter;
    }

    public java.text.NumberFormat getImaginaryFormat() {
        return this.imaginaryFormat;
    }

    public static org.apache.commons.math.complex.ComplexFormat getInstance() {
        return getInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.complex.ComplexFormat getInstance(java.util.Locale locale) {
        return new org.apache.commons.math.complex.ComplexFormat(org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(locale));
    }

    public java.text.NumberFormat getRealFormat() {
        return this.realFormat;
    }

    public org.apache.commons.math.complex.Complex parse(java.lang.String str) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.complex.Complex parse = parse(str, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(parsePosition.getErrorIndex(), org.apache.commons.math.exception.util.LocalizedFormats.UNPARSEABLE_COMPLEX_NUMBER, str);
        }
        return parse;
    }

    public org.apache.commons.math.complex.Complex parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        int i;
        int index = parsePosition.getIndex();
        parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parseNumber = parseNumber(str, getRealFormat(), parsePosition);
        if (parseNumber == null) {
            parsePosition.setIndex(index);
            return null;
        }
        int index2 = parsePosition.getIndex();
        switch (parseNextCharacter(str, parsePosition)) {
            case 0:
                return new org.apache.commons.math.complex.Complex(parseNumber.doubleValue(), 0.0d);
            case '+':
                i = 1;
                break;
            case '-':
                i = -1;
                break;
            default:
                parsePosition.setIndex(index);
                parsePosition.setErrorIndex(index2);
                return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parseNumber2 = parseNumber(str, getRealFormat(), parsePosition);
        if (parseNumber2 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        if (!parseFixedstring(str, getImaginaryCharacter(), parsePosition)) {
            return null;
        }
        return new org.apache.commons.math.complex.Complex(parseNumber.doubleValue(), parseNumber2.doubleValue() * i);
    }

    @Override // java.text.Format
    public java.lang.Object parseObject(java.lang.String str, java.text.ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }

    public void setImaginaryCharacter(java.lang.String str) {
        if (str == null || str.length() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_STRING_FOR_IMAGINARY_CHARACTER, new java.lang.Object[0]);
        }
        this.imaginaryCharacter = str;
    }

    public void setImaginaryFormat(java.text.NumberFormat numberFormat) {
        if (numberFormat == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.IMAGINARY_FORMAT);
        }
        this.imaginaryFormat = numberFormat;
    }

    public void setRealFormat(java.text.NumberFormat numberFormat) {
        if (numberFormat == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.REAL_FORMAT);
        }
        this.realFormat = numberFormat;
    }
}
