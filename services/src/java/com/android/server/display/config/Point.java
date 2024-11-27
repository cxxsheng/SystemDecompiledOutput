package com.android.server.display.config;

/* loaded from: classes.dex */
public class Point {

    @android.annotation.NonNull
    private java.math.BigDecimal nits;

    @android.annotation.NonNull
    private java.math.BigDecimal value;

    @android.annotation.NonNull
    public final java.math.BigDecimal getValue() {
        return this.value;
    }

    boolean hasValue() {
        if (this.value == null) {
            return false;
        }
        return true;
    }

    public final void setValue(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.value = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getNits() {
        return this.nits;
    }

    boolean hasNits() {
        if (this.nits == null) {
            return false;
        }
        return true;
    }

    public final void setNits(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.nits = bigDecimal;
    }

    static com.android.server.display.config.Point read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.Point point = new com.android.server.display.config.Point();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("value")) {
                    point.setValue(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("nits")) {
                    point.setNits(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Point is not closed");
        }
        return point;
    }
}
