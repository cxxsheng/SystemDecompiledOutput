package com.android.server.display.config;

/* loaded from: classes.dex */
public class BlockingZoneConfig {
    private com.android.server.display.config.BlockingZoneThreshold blockingZoneThreshold;
    private java.math.BigInteger defaultRefreshRate;

    @android.annotation.Nullable
    private java.lang.String refreshRateThermalThrottlingId;

    public final java.math.BigInteger getDefaultRefreshRate() {
        return this.defaultRefreshRate;
    }

    boolean hasDefaultRefreshRate() {
        if (this.defaultRefreshRate == null) {
            return false;
        }
        return true;
    }

    public final void setDefaultRefreshRate(java.math.BigInteger bigInteger) {
        this.defaultRefreshRate = bigInteger;
    }

    @android.annotation.Nullable
    public final java.lang.String getRefreshRateThermalThrottlingId() {
        return this.refreshRateThermalThrottlingId;
    }

    boolean hasRefreshRateThermalThrottlingId() {
        if (this.refreshRateThermalThrottlingId == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRateThermalThrottlingId(@android.annotation.Nullable java.lang.String str) {
        this.refreshRateThermalThrottlingId = str;
    }

    public final com.android.server.display.config.BlockingZoneThreshold getBlockingZoneThreshold() {
        return this.blockingZoneThreshold;
    }

    boolean hasBlockingZoneThreshold() {
        if (this.blockingZoneThreshold == null) {
            return false;
        }
        return true;
    }

    public final void setBlockingZoneThreshold(com.android.server.display.config.BlockingZoneThreshold blockingZoneThreshold) {
        this.blockingZoneThreshold = blockingZoneThreshold;
    }

    static com.android.server.display.config.BlockingZoneConfig read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BlockingZoneConfig blockingZoneConfig = new com.android.server.display.config.BlockingZoneConfig();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("defaultRefreshRate")) {
                    blockingZoneConfig.setDefaultRefreshRate(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("refreshRateThermalThrottlingId")) {
                    blockingZoneConfig.setRefreshRateThermalThrottlingId(com.android.server.display.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("blockingZoneThreshold")) {
                    blockingZoneConfig.setBlockingZoneThreshold(com.android.server.display.config.BlockingZoneThreshold.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BlockingZoneConfig is not closed");
        }
        return blockingZoneConfig;
    }
}
