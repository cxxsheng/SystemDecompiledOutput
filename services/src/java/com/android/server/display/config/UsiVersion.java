package com.android.server.display.config;

/* loaded from: classes.dex */
public class UsiVersion {
    private java.math.BigInteger majorVersion;
    private java.math.BigInteger minorVersion;

    public final java.math.BigInteger getMajorVersion() {
        return this.majorVersion;
    }

    boolean hasMajorVersion() {
        if (this.majorVersion == null) {
            return false;
        }
        return true;
    }

    public final void setMajorVersion(java.math.BigInteger bigInteger) {
        this.majorVersion = bigInteger;
    }

    public final java.math.BigInteger getMinorVersion() {
        return this.minorVersion;
    }

    boolean hasMinorVersion() {
        if (this.minorVersion == null) {
            return false;
        }
        return true;
    }

    public final void setMinorVersion(java.math.BigInteger bigInteger) {
        this.minorVersion = bigInteger;
    }

    static com.android.server.display.config.UsiVersion read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.UsiVersion usiVersion = new com.android.server.display.config.UsiVersion();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("majorVersion")) {
                    usiVersion.setMajorVersion(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("minorVersion")) {
                    usiVersion.setMinorVersion(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("UsiVersion is not closed");
        }
        return usiVersion;
    }
}
