package com.android.server.display.config;

/* loaded from: classes.dex */
public class RefreshRateConfigs {
    private java.math.BigInteger defaultPeakRefreshRate;
    private java.math.BigInteger defaultRefreshRate;
    private java.math.BigInteger defaultRefreshRateInHbmHdr;
    private java.math.BigInteger defaultRefreshRateInHbmSunlight;
    private com.android.server.display.config.BlockingZoneConfig higherBlockingZoneConfigs;
    private com.android.server.display.config.BlockingZoneConfig lowerBlockingZoneConfigs;
    private com.android.server.display.config.RefreshRateZoneProfiles refreshRateZoneProfiles;

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

    public final java.math.BigInteger getDefaultPeakRefreshRate() {
        return this.defaultPeakRefreshRate;
    }

    boolean hasDefaultPeakRefreshRate() {
        if (this.defaultPeakRefreshRate == null) {
            return false;
        }
        return true;
    }

    public final void setDefaultPeakRefreshRate(java.math.BigInteger bigInteger) {
        this.defaultPeakRefreshRate = bigInteger;
    }

    public final com.android.server.display.config.RefreshRateZoneProfiles getRefreshRateZoneProfiles() {
        return this.refreshRateZoneProfiles;
    }

    boolean hasRefreshRateZoneProfiles() {
        if (this.refreshRateZoneProfiles == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRateZoneProfiles(com.android.server.display.config.RefreshRateZoneProfiles refreshRateZoneProfiles) {
        this.refreshRateZoneProfiles = refreshRateZoneProfiles;
    }

    public final java.math.BigInteger getDefaultRefreshRateInHbmHdr() {
        return this.defaultRefreshRateInHbmHdr;
    }

    boolean hasDefaultRefreshRateInHbmHdr() {
        if (this.defaultRefreshRateInHbmHdr == null) {
            return false;
        }
        return true;
    }

    public final void setDefaultRefreshRateInHbmHdr(java.math.BigInteger bigInteger) {
        this.defaultRefreshRateInHbmHdr = bigInteger;
    }

    public final java.math.BigInteger getDefaultRefreshRateInHbmSunlight() {
        return this.defaultRefreshRateInHbmSunlight;
    }

    boolean hasDefaultRefreshRateInHbmSunlight() {
        if (this.defaultRefreshRateInHbmSunlight == null) {
            return false;
        }
        return true;
    }

    public final void setDefaultRefreshRateInHbmSunlight(java.math.BigInteger bigInteger) {
        this.defaultRefreshRateInHbmSunlight = bigInteger;
    }

    public final com.android.server.display.config.BlockingZoneConfig getLowerBlockingZoneConfigs() {
        return this.lowerBlockingZoneConfigs;
    }

    boolean hasLowerBlockingZoneConfigs() {
        if (this.lowerBlockingZoneConfigs == null) {
            return false;
        }
        return true;
    }

    public final void setLowerBlockingZoneConfigs(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        this.lowerBlockingZoneConfigs = blockingZoneConfig;
    }

    public final com.android.server.display.config.BlockingZoneConfig getHigherBlockingZoneConfigs() {
        return this.higherBlockingZoneConfigs;
    }

    boolean hasHigherBlockingZoneConfigs() {
        if (this.higherBlockingZoneConfigs == null) {
            return false;
        }
        return true;
    }

    public final void setHigherBlockingZoneConfigs(com.android.server.display.config.BlockingZoneConfig blockingZoneConfig) {
        this.higherBlockingZoneConfigs = blockingZoneConfig;
    }

    static com.android.server.display.config.RefreshRateConfigs read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.RefreshRateConfigs refreshRateConfigs = new com.android.server.display.config.RefreshRateConfigs();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("defaultRefreshRate")) {
                    refreshRateConfigs.setDefaultRefreshRate(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("defaultPeakRefreshRate")) {
                    refreshRateConfigs.setDefaultPeakRefreshRate(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("refreshRateZoneProfiles")) {
                    refreshRateConfigs.setRefreshRateZoneProfiles(com.android.server.display.config.RefreshRateZoneProfiles.read(xmlPullParser));
                } else if (name.equals("defaultRefreshRateInHbmHdr")) {
                    refreshRateConfigs.setDefaultRefreshRateInHbmHdr(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("defaultRefreshRateInHbmSunlight")) {
                    refreshRateConfigs.setDefaultRefreshRateInHbmSunlight(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("lowerBlockingZoneConfigs")) {
                    refreshRateConfigs.setLowerBlockingZoneConfigs(com.android.server.display.config.BlockingZoneConfig.read(xmlPullParser));
                } else if (name.equals("higherBlockingZoneConfigs")) {
                    refreshRateConfigs.setHigherBlockingZoneConfigs(com.android.server.display.config.BlockingZoneConfig.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("RefreshRateConfigs is not closed");
        }
        return refreshRateConfigs;
    }
}
