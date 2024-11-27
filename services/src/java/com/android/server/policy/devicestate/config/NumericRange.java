package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class NumericRange {

    @android.annotation.Nullable
    private java.math.BigDecimal maxInclusive_optional;

    @android.annotation.Nullable
    private java.math.BigDecimal max_optional;

    @android.annotation.Nullable
    private java.math.BigDecimal minInclusive_optional;

    @android.annotation.Nullable
    private java.math.BigDecimal min_optional;

    @android.annotation.Nullable
    public java.math.BigDecimal getMin_optional() {
        return this.min_optional;
    }

    boolean hasMin_optional() {
        if (this.min_optional == null) {
            return false;
        }
        return true;
    }

    public void setMin_optional(@android.annotation.Nullable java.math.BigDecimal bigDecimal) {
        this.min_optional = bigDecimal;
    }

    @android.annotation.Nullable
    public java.math.BigDecimal getMinInclusive_optional() {
        return this.minInclusive_optional;
    }

    boolean hasMinInclusive_optional() {
        if (this.minInclusive_optional == null) {
            return false;
        }
        return true;
    }

    public void setMinInclusive_optional(@android.annotation.Nullable java.math.BigDecimal bigDecimal) {
        this.minInclusive_optional = bigDecimal;
    }

    @android.annotation.Nullable
    public java.math.BigDecimal getMax_optional() {
        return this.max_optional;
    }

    boolean hasMax_optional() {
        if (this.max_optional == null) {
            return false;
        }
        return true;
    }

    public void setMax_optional(@android.annotation.Nullable java.math.BigDecimal bigDecimal) {
        this.max_optional = bigDecimal;
    }

    @android.annotation.Nullable
    public java.math.BigDecimal getMaxInclusive_optional() {
        return this.maxInclusive_optional;
    }

    boolean hasMaxInclusive_optional() {
        if (this.maxInclusive_optional == null) {
            return false;
        }
        return true;
    }

    public void setMaxInclusive_optional(@android.annotation.Nullable java.math.BigDecimal bigDecimal) {
        this.maxInclusive_optional = bigDecimal;
    }

    static com.android.server.policy.devicestate.config.NumericRange read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.NumericRange numericRange = new com.android.server.policy.devicestate.config.NumericRange();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("min")) {
                    numericRange.setMin_optional(new java.math.BigDecimal(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("min-inclusive")) {
                    numericRange.setMinInclusive_optional(new java.math.BigDecimal(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("max")) {
                    numericRange.setMax_optional(new java.math.BigDecimal(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("max-inclusive")) {
                    numericRange.setMaxInclusive_optional(new java.math.BigDecimal(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("NumericRange is not closed");
        }
        return numericRange;
    }
}
