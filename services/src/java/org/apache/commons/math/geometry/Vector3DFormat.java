package org.apache.commons.math.geometry;

/* loaded from: classes3.dex */
public class Vector3DFormat extends org.apache.commons.math.util.CompositeFormat {
    private static final java.lang.String DEFAULT_PREFIX = "{";
    private static final java.lang.String DEFAULT_SEPARATOR = "; ";
    private static final java.lang.String DEFAULT_SUFFIX = "}";
    private static final long serialVersionUID = -5447606608652576301L;
    private final java.text.NumberFormat format;
    private final java.lang.String prefix;
    private final java.lang.String separator;
    private final java.lang.String suffix;
    private final java.lang.String trimmedPrefix;
    private final java.lang.String trimmedSeparator;
    private final java.lang.String trimmedSuffix;

    public Vector3DFormat() {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public Vector3DFormat(java.text.NumberFormat numberFormat) {
        this(DEFAULT_PREFIX, DEFAULT_SUFFIX, DEFAULT_SEPARATOR, numberFormat);
    }

    public Vector3DFormat(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this(str, str2, str3, org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat());
    }

    public Vector3DFormat(java.lang.String str, java.lang.String str2, java.lang.String str3, java.text.NumberFormat numberFormat) {
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

    public static org.apache.commons.math.geometry.Vector3DFormat getInstance() {
        return getInstance(java.util.Locale.getDefault());
    }

    public static org.apache.commons.math.geometry.Vector3DFormat getInstance(java.util.Locale locale) {
        return new org.apache.commons.math.geometry.Vector3DFormat(org.apache.commons.math.util.CompositeFormat.getDefaultNumberFormat(locale));
    }

    public static java.lang.String formatVector3D(org.apache.commons.math.geometry.Vector3D vector3D) {
        return getInstance().format(vector3D);
    }

    public java.lang.StringBuffer format(org.apache.commons.math.geometry.Vector3D vector3D, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        stringBuffer.append(this.prefix);
        formatDouble(vector3D.getX(), this.format, stringBuffer, fieldPosition);
        stringBuffer.append(this.separator);
        formatDouble(vector3D.getY(), this.format, stringBuffer, fieldPosition);
        stringBuffer.append(this.separator);
        formatDouble(vector3D.getZ(), this.format, stringBuffer, fieldPosition);
        stringBuffer.append(this.suffix);
        return stringBuffer;
    }

    @Override // java.text.Format
    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer stringBuffer, java.text.FieldPosition fieldPosition) {
        if (obj instanceof org.apache.commons.math.geometry.Vector3D) {
            return format((org.apache.commons.math.geometry.Vector3D) obj, stringBuffer, fieldPosition);
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_FORMAT_INSTANCE_AS_3D_VECTOR, obj.getClass().getName());
    }

    public org.apache.commons.math.geometry.Vector3D parse(java.lang.String str) throws java.text.ParseException {
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        org.apache.commons.math.geometry.Vector3D parse = parse(str, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw org.apache.commons.math.MathRuntimeException.createParseException(parsePosition.getErrorIndex(), org.apache.commons.math.exception.util.LocalizedFormats.UNPARSEABLE_3D_VECTOR, str);
        }
        return parse;
    }

    public org.apache.commons.math.geometry.Vector3D parse(java.lang.String str, java.text.ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        parseAndIgnoreWhitespace(str, parsePosition);
        if (!parseFixedstring(str, this.trimmedPrefix, parsePosition)) {
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parseNumber = parseNumber(str, this.format, parsePosition);
        if (parseNumber == null) {
            parsePosition.setIndex(index);
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        if (!parseFixedstring(str, this.trimmedSeparator, parsePosition)) {
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parseNumber2 = parseNumber(str, this.format, parsePosition);
        if (parseNumber2 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        if (!parseFixedstring(str, this.trimmedSeparator, parsePosition)) {
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        java.lang.Number parseNumber3 = parseNumber(str, this.format, parsePosition);
        if (parseNumber3 == null) {
            parsePosition.setIndex(index);
            return null;
        }
        parseAndIgnoreWhitespace(str, parsePosition);
        if (parseFixedstring(str, this.trimmedSuffix, parsePosition)) {
            return new org.apache.commons.math.geometry.Vector3D(parseNumber.doubleValue(), parseNumber2.doubleValue(), parseNumber3.doubleValue());
        }
        return null;
    }

    @Override // java.text.Format
    public java.lang.Object parseObject(java.lang.String str, java.text.ParsePosition parsePosition) {
        return parse(str, parsePosition);
    }
}
