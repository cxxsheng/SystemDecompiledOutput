package com.android.server.display.config;

/* loaded from: classes.dex */
public class RefreshRateRange {
    private java.math.BigInteger maximum;
    private java.math.BigInteger minimum;

    public final java.math.BigInteger getMinimum() {
        return this.minimum;
    }

    boolean hasMinimum() {
        if (this.minimum == null) {
            return false;
        }
        return true;
    }

    public final void setMinimum(java.math.BigInteger bigInteger) {
        this.minimum = bigInteger;
    }

    public final java.math.BigInteger getMaximum() {
        return this.maximum;
    }

    boolean hasMaximum() {
        if (this.maximum == null) {
            return false;
        }
        return true;
    }

    public final void setMaximum(java.math.BigInteger bigInteger) {
        this.maximum = bigInteger;
    }

    static com.android.server.display.config.RefreshRateRange read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.RefreshRateRange refreshRateRange = new com.android.server.display.config.RefreshRateRange();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("minimum")) {
                    refreshRateRange.setMinimum(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("maximum")) {
                    refreshRateRange.setMaximum(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateRange is not closed");
        }
        return refreshRateRange;
    }
}
