package com.android.server.display.config;

/* loaded from: classes.dex */
public class HighBrightnessMode {

    @android.annotation.NonNull
    private java.lang.Boolean allowInLowPowerMode_all;
    private java.lang.Boolean enabled;

    @android.annotation.Nullable
    private java.math.BigDecimal minimumHdrPercentOfScreen_all;

    @android.annotation.NonNull
    private java.math.BigDecimal minimumLux_all;

    @android.annotation.Nullable
    private com.android.server.display.config.RefreshRateRange refreshRate_all;

    @android.annotation.Nullable
    private com.android.server.display.config.SdrHdrRatioMap sdrHdrRatioMap_all;
    private com.android.server.display.config.HbmTiming timing_all;

    @android.annotation.NonNull
    private java.math.BigDecimal transitionPoint_all;

    @android.annotation.NonNull
    public final java.math.BigDecimal getTransitionPoint_all() {
        return this.transitionPoint_all;
    }

    boolean hasTransitionPoint_all() {
        if (this.transitionPoint_all == null) {
            return false;
        }
        return true;
    }

    public final void setTransitionPoint_all(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.transitionPoint_all = bigDecimal;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getMinimumLux_all() {
        return this.minimumLux_all;
    }

    boolean hasMinimumLux_all() {
        if (this.minimumLux_all == null) {
            return false;
        }
        return true;
    }

    public final void setMinimumLux_all(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.minimumLux_all = bigDecimal;
    }

    public com.android.server.display.config.HbmTiming getTiming_all() {
        return this.timing_all;
    }

    boolean hasTiming_all() {
        if (this.timing_all == null) {
            return false;
        }
        return true;
    }

    public void setTiming_all(com.android.server.display.config.HbmTiming hbmTiming) {
        this.timing_all = hbmTiming;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.RefreshRateRange getRefreshRate_all() {
        return this.refreshRate_all;
    }

    boolean hasRefreshRate_all() {
        if (this.refreshRate_all == null) {
            return false;
        }
        return true;
    }

    public final void setRefreshRate_all(@android.annotation.Nullable com.android.server.display.config.RefreshRateRange refreshRateRange) {
        this.refreshRate_all = refreshRateRange;
    }

    @android.annotation.NonNull
    public final boolean getAllowInLowPowerMode_all() {
        if (this.allowInLowPowerMode_all == null) {
            return false;
        }
        return this.allowInLowPowerMode_all.booleanValue();
    }

    boolean hasAllowInLowPowerMode_all() {
        if (this.allowInLowPowerMode_all == null) {
            return false;
        }
        return true;
    }

    public final void setAllowInLowPowerMode_all(@android.annotation.NonNull boolean z) {
        this.allowInLowPowerMode_all = java.lang.Boolean.valueOf(z);
    }

    @android.annotation.Nullable
    public final java.math.BigDecimal getMinimumHdrPercentOfScreen_all() {
        return this.minimumHdrPercentOfScreen_all;
    }

    boolean hasMinimumHdrPercentOfScreen_all() {
        if (this.minimumHdrPercentOfScreen_all == null) {
            return false;
        }
        return true;
    }

    public final void setMinimumHdrPercentOfScreen_all(@android.annotation.Nullable java.math.BigDecimal bigDecimal) {
        this.minimumHdrPercentOfScreen_all = bigDecimal;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.SdrHdrRatioMap getSdrHdrRatioMap_all() {
        return this.sdrHdrRatioMap_all;
    }

    boolean hasSdrHdrRatioMap_all() {
        if (this.sdrHdrRatioMap_all == null) {
            return false;
        }
        return true;
    }

    public final void setSdrHdrRatioMap_all(@android.annotation.Nullable com.android.server.display.config.SdrHdrRatioMap sdrHdrRatioMap) {
        this.sdrHdrRatioMap_all = sdrHdrRatioMap;
    }

    public boolean getEnabled() {
        if (this.enabled == null) {
            return false;
        }
        return this.enabled.booleanValue();
    }

    boolean hasEnabled() {
        if (this.enabled == null) {
            return false;
        }
        return true;
    }

    public void setEnabled(boolean z) {
        this.enabled = java.lang.Boolean.valueOf(z);
    }

    static com.android.server.display.config.HighBrightnessMode read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.HighBrightnessMode highBrightnessMode = new com.android.server.display.config.HighBrightnessMode();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        if (attributeValue != null) {
            highBrightnessMode.setEnabled(java.lang.Boolean.parseBoolean(attributeValue));
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("transitionPoint")) {
                    highBrightnessMode.setTransitionPoint_all(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("minimumLux")) {
                    highBrightnessMode.setMinimumLux_all(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("timing")) {
                    highBrightnessMode.setTiming_all(com.android.server.display.config.HbmTiming.read(xmlPullParser));
                } else if (name.equals("refreshRate")) {
                    highBrightnessMode.setRefreshRate_all(com.android.server.display.config.RefreshRateRange.read(xmlPullParser));
                } else if (name.equals("allowInLowPowerMode")) {
                    highBrightnessMode.setAllowInLowPowerMode_all(java.lang.Boolean.parseBoolean(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("minimumHdrPercentOfScreen")) {
                    highBrightnessMode.setMinimumHdrPercentOfScreen_all(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("sdrHdrRatioMap")) {
                    highBrightnessMode.setSdrHdrRatioMap_all(com.android.server.display.config.SdrHdrRatioMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("HighBrightnessMode is not closed");
        }
        return highBrightnessMode;
    }
}
