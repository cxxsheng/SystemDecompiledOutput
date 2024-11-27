package com.android.server.display.config;

/* loaded from: classes.dex */
public class HbmTiming {

    @android.annotation.NonNull
    private java.math.BigInteger timeMaxSecs_all;

    @android.annotation.NonNull
    private java.math.BigInteger timeMinSecs_all;

    @android.annotation.NonNull
    private java.math.BigInteger timeWindowSecs_all;

    @android.annotation.NonNull
    public final java.math.BigInteger getTimeWindowSecs_all() {
        return this.timeWindowSecs_all;
    }

    boolean hasTimeWindowSecs_all() {
        if (this.timeWindowSecs_all == null) {
            return false;
        }
        return true;
    }

    public final void setTimeWindowSecs_all(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.timeWindowSecs_all = bigInteger;
    }

    @android.annotation.NonNull
    public final java.math.BigInteger getTimeMaxSecs_all() {
        return this.timeMaxSecs_all;
    }

    boolean hasTimeMaxSecs_all() {
        if (this.timeMaxSecs_all == null) {
            return false;
        }
        return true;
    }

    public final void setTimeMaxSecs_all(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.timeMaxSecs_all = bigInteger;
    }

    @android.annotation.NonNull
    public final java.math.BigInteger getTimeMinSecs_all() {
        return this.timeMinSecs_all;
    }

    boolean hasTimeMinSecs_all() {
        if (this.timeMinSecs_all == null) {
            return false;
        }
        return true;
    }

    public final void setTimeMinSecs_all(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.timeMinSecs_all = bigInteger;
    }

    static com.android.server.display.config.HbmTiming read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.HbmTiming hbmTiming = new com.android.server.display.config.HbmTiming();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("timeWindowSecs")) {
                    hbmTiming.setTimeWindowSecs_all(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("timeMaxSecs")) {
                    hbmTiming.setTimeMaxSecs_all(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("timeMinSecs")) {
                    hbmTiming.setTimeMinSecs_all(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("HbmTiming is not closed");
        }
        return hbmTiming;
    }
}
