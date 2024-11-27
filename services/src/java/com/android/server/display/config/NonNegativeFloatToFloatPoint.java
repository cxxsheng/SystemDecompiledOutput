package com.android.server.display.config;

/* loaded from: classes.dex */
public class NonNegativeFloatToFloatPoint {

    @android.annotation.NonNull
    private java.math.BigDecimal first;

    @android.annotation.NonNull
    private java.math.BigDecimal second;

    @android.annotation.NonNull
    public final java.math.BigDecimal getFirst() {
        return this.first;
    }

    boolean hasFirst() {
        if (this.first == null) {
            return false;
        }
        return true;
    }

    public final void setFirst(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.first = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getSecond() {
        return this.second;
    }

    boolean hasSecond() {
        if (this.second == null) {
            return false;
        }
        return true;
    }

    public final void setSecond(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.second = bigDecimal;
    }

    static com.android.server.display.config.NonNegativeFloatToFloatPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint = new com.android.server.display.config.NonNegativeFloatToFloatPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("first")) {
                    nonNegativeFloatToFloatPoint.setFirst(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("second")) {
                    nonNegativeFloatToFloatPoint.setSecond(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("NonNegativeFloatToFloatPoint is not closed");
        }
        return nonNegativeFloatToFloatPoint;
    }
}
