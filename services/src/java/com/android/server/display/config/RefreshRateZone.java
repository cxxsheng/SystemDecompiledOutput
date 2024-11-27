package com.android.server.display.config;

/* loaded from: classes.dex */
public class RefreshRateZone {
    private java.lang.String id;
    private com.android.server.display.config.RefreshRateRange refreshRateRange;

    public final com.android.server.display.config.RefreshRateRange getRefreshRateRange() {
        return this.refreshRateRange;
    }

    boolean hasRefreshRateRange() {
        if (this.refreshRateRange == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRateRange(com.android.server.display.config.RefreshRateRange refreshRateRange) {
        this.refreshRateRange = refreshRateRange;
    }

    public java.lang.String getId() {
        return this.id;
    }

    boolean hasId() {
        if (this.id == null) {
            return false;
        }
        return true;
    }

    public void setId(java.lang.String str) {
        this.id = str;
    }

    static com.android.server.display.config.RefreshRateZone read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.RefreshRateZone refreshRateZone = new com.android.server.display.config.RefreshRateZone();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            refreshRateZone.setId(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("refreshRateRange")) {
                    refreshRateZone.setRefreshRateRange(com.android.server.display.config.RefreshRateRange.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateZone is not closed");
        }
        return refreshRateZone;
    }
}
