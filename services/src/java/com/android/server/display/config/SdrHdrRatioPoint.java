package com.android.server.display.config;

/* loaded from: classes.dex */
public class SdrHdrRatioPoint {

    @android.annotation.NonNull
    private java.math.BigDecimal hdrRatio;

    @android.annotation.NonNull
    private java.math.BigDecimal sdrNits;

    @android.annotation.NonNull
    public final java.math.BigDecimal getSdrNits() {
        return this.sdrNits;
    }

    boolean hasSdrNits() {
        if (this.sdrNits == null) {
            return false;
        }
        return true;
    }

    public final void setSdrNits(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.sdrNits = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getHdrRatio() {
        return this.hdrRatio;
    }

    boolean hasHdrRatio() {
        if (this.hdrRatio == null) {
            return false;
        }
        return true;
    }

    public final void setHdrRatio(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.hdrRatio = bigDecimal;
    }

    static com.android.server.display.config.SdrHdrRatioPoint read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.SdrHdrRatioPoint sdrHdrRatioPoint = new com.android.server.display.config.SdrHdrRatioPoint();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("sdrNits")) {
                    sdrHdrRatioPoint.setSdrNits(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("hdrRatio")) {
                    sdrHdrRatioPoint.setHdrRatio(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("SdrHdrRatioPoint is not closed");
        }
        return sdrHdrRatioPoint;
    }
}
