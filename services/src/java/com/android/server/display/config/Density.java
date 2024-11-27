package com.android.server.display.config;

/* loaded from: classes.dex */
public class Density {

    @android.annotation.NonNull
    private java.math.BigInteger density;

    @android.annotation.NonNull
    private java.math.BigInteger height;

    @android.annotation.NonNull
    private java.math.BigInteger width;

    @android.annotation.NonNull
    public final java.math.BigInteger getWidth() {
        return this.width;
    }

    boolean hasWidth() {
        if (this.width == null) {
            return false;
        }
        return true;
    }

    public final void setWidth(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.width = bigInteger;
    }

    @android.annotation.NonNull
    public final java.math.BigInteger getHeight() {
        return this.height;
    }

    boolean hasHeight() {
        if (this.height == null) {
            return false;
        }
        return true;
    }

    public final void setHeight(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.height = bigInteger;
    }

    @android.annotation.NonNull
    public final java.math.BigInteger getDensity() {
        return this.density;
    }

    boolean hasDensity() {
        if (this.density == null) {
            return false;
        }
        return true;
    }

    public final void setDensity(@android.annotation.NonNull java.math.BigInteger bigInteger) {
        this.density = bigInteger;
    }

    static com.android.server.display.config.Density read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.Density density = new com.android.server.display.config.Density();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("width")) {
                    density.setWidth(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("height")) {
                    density.setHeight(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("density")) {
                    density.setDensity(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Density is not closed");
        }
        return density;
    }
}
