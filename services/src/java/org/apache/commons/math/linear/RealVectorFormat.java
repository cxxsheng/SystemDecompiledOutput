package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class RealVectorFormat extends org.apache.commons.math.util.CompositeFormat {
    private static final java.lang.String DEFAULT_PREFIX = "{";
    private static final java.lang.String DEFAULT_SEPARATOR = "; ";
    private static final java.lang.String DEFAULT_SUFFIX = "}";
    private static final long serialVersionUID = -708767813036157690L;
    private final java.text.NumberFormat format;
    private final java.lang.String prefix;
    private final java.lang.String separator;
    private final java.lang.String suffix;
    private final java.lang.String trimmedPrefix;
    private final java.lang.String trimmedSeparator;
    private final java.lang.String trimmedSuffix;

    public RealVectorFormat() {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public RealVectorFormat(java.text.NumberFormat numberFormat) {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, numberFormat);
    }

    public RealVectorFormat(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this(str, str2, str3, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public RealVectorFormat(java.lang.String str, java.lang.String str2, java.lang.String str3, java.text.NumberFormat numberFormat) {
        this.prefix = str;
        this.suffix = str2;
        this.separator = str3;
        this.trimmedPrefix = str.trim();
        this.trimmedSuffix = str2.trim();
        this.trimmedSeparator = str3.trim();
        this.format = numberFormat;
    }

    public static java.util.Locale[] getAvailableLocales() {
        return java.text.NumberFormat.getAvailableLocales();
    }

    public java.lang.String getPrefix() {
        return this.prefix;
    }

    public java.lang.String getSuffix() {
        return this.suffix;
    }

    public java.lang.String getSeparator() {
        return this.separator;
    }

    public java.text.NumberFormat getFormat() {
        return this.format;
    }

    public static org.apache.commons.math.linear.RealVectorFormat getInstance() {
        return getInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.linear.RealVectorFormat getInstance(java.util.Locale locale) {
        return new org.apache.commons.math.linear.RealVectorFormat(org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(locale));
    }

    public static java.lang.String formatRealVector(org.apache.commons.math.linear.RealVector realVector) {
        return getInstance().format(realVector);
    }

    public java.lang.StringBuffer format(org.apache.commons.math.linear.RealVector realVector, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        stringBuffer.append(this.prefix);
        for (int i = 0; i < realVector.getDimension(); i++) {
            if (i > 0) {
                stringBuffer.append(this.separator);
            }
            formatDouble(realVector.getEntry(i), this.format, stringBuffer, fieldPosition);
        }
        stringBuffer.append(this.suffix);
        return stringBuffer;
    }

    @Override // java.text.Format
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (obj instanceof org.apache.commons.math.linear.RealVector) {
            return format((org.apache.commons.math.linear.RealVector) obj, stringBuffer, fieldPosition);
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_REAL_VECTOR, obj.getClass().getName());
    }

    public org.apache.commons.math.linear.ArrayRealVector parse(java.lang.String str) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.linear.ArrayRealVector parse = parse(str, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(parsePosition.getErrorIndex(), org.apache.commons.math.exception.util.LocalizedFormats.UNPARSEABLE_REAL_VECTOR, str);
        }
        return parse;
    }

    public org.apache.commons.math.linear.ArrayRealVector parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        parseAndIgnoreWhitespace(str, parsePosition);
        if (!parseFixedstring(str, this.trimmedPrefix, parsePosition)) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z = true;
        while (z) {
            if (!arrayList.isEmpty()) {
                parseAndIgnoreWhitespace(str, parsePosition);
                if (!parseFixedstring(str, this.trimmedSeparator, parsePosition)) {
                    z = false;
                }
            }
            if (z) {
                parseAndIgnoreWhitespace(str, parsePosition);
                java.lang.Number parseNumber = parseNumber(str, this.format, parsePosition);
                if (parseNumber != null) {
                    arrayList.add(parseNumber);
                } else {
                    parsePosition.setIndex(index);
                    return null;
                }
            }
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        if (!parseFixedstring(str, this.trimmedSuffix, parsePosition)) {
            return null;
        }
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = ((java.lang.Number) arrayList.get(i)).doubleValue();
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // java.text.Format
    public java.lang.Object parseObject(java.lang.String str, java.text.ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }
}
