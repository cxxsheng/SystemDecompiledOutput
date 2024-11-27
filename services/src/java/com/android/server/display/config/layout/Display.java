package com.android.server.display.config.layout;

/* loaded from: classes.dex */
public class Display {
    private java.math.BigInteger address_optional;
    private java.lang.String brightnessThrottlingMapId;
    private java.lang.Boolean defaultDisplay;
    private java.lang.String displayGroup;
    private java.lang.Boolean enabled;
    private java.math.BigInteger leadDisplayAddress;
    private java.math.BigInteger port_optional;
    private java.lang.String position;
    private java.lang.String powerThrottlingMapId;
    private java.lang.String refreshRateThermalThrottlingMapId;
    private java.lang.String refreshRateZoneId;

    public java.math.BigInteger getAddress_optional() {
        return this.address_optional;
    }

    boolean hasAddress_optional() {
        if (this.address_optional == null) {
            return false;
        }
        return true;
    }

    public void setAddress_optional(java.math.BigInteger bigInteger) {
        this.address_optional = bigInteger;
    }

    public java.math.BigInteger getPort_optional() {
        return this.port_optional;
    }

    boolean hasPort_optional() {
        if (this.port_optional == null) {
            return false;
        }
        return true;
    }

    public void setPort_optional(java.math.BigInteger bigInteger) {
        this.port_optional = bigInteger;
    }

    public java.lang.String getPosition() {
        return this.position;
    }

    boolean hasPosition() {
        if (this.position == null) {
            return false;
        }
        return true;
    }

    public void setPosition(java.lang.String str) {
        this.position = str;
    }

    public java.lang.String getBrightnessThrottlingMapId() {
        return this.brightnessThrottlingMapId;
    }

    boolean hasBrightnessThrottlingMapId() {
        if (this.brightnessThrottlingMapId == null) {
            return false;
        }
        return true;
    }

    public void setBrightnessThrottlingMapId(java.lang.String str) {
        this.brightnessThrottlingMapId = str;
    }

    public java.lang.String getPowerThrottlingMapId() {
        return this.powerThrottlingMapId;
    }

    boolean hasPowerThrottlingMapId() {
        if (this.powerThrottlingMapId == null) {
            return false;
        }
        return true;
    }

    public void setPowerThrottlingMapId(java.lang.String str) {
        this.powerThrottlingMapId = str;
    }

    public java.lang.String getRefreshRateThermalThrottlingMapId() {
        return this.refreshRateThermalThrottlingMapId;
    }

    boolean hasRefreshRateThermalThrottlingMapId() {
        if (this.refreshRateThermalThrottlingMapId == null) {
            return false;
        }
        return true;
    }

    public void setRefreshRateThermalThrottlingMapId(java.lang.String str) {
        this.refreshRateThermalThrottlingMapId = str;
    }

    public java.math.BigInteger getLeadDisplayAddress() {
        return this.leadDisplayAddress;
    }

    boolean hasLeadDisplayAddress() {
        if (this.leadDisplayAddress == null) {
            return false;
        }
        return true;
    }

    public void setLeadDisplayAddress(java.math.BigInteger bigInteger) {
        this.leadDisplayAddress = bigInteger;
    }

    public boolean isEnabled() {
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

    public boolean isDefaultDisplay() {
        if (this.defaultDisplay == null) {
            return false;
        }
        return this.defaultDisplay.booleanValue();
    }

    boolean hasDefaultDisplay() {
        if (this.defaultDisplay == null) {
            return false;
        }
        return true;
    }

    public void setDefaultDisplay(boolean z) {
        this.defaultDisplay = java.lang.Boolean.valueOf(z);
    }

    public java.lang.String getRefreshRateZoneId() {
        return this.refreshRateZoneId;
    }

    boolean hasRefreshRateZoneId() {
        if (this.refreshRateZoneId == null) {
            return false;
        }
        return true;
    }

    public void setRefreshRateZoneId(java.lang.String str) {
        this.refreshRateZoneId = str;
    }

    public java.lang.String getDisplayGroup() {
        return this.displayGroup;
    }

    boolean hasDisplayGroup() {
        if (this.displayGroup == null) {
            return false;
        }
        return true;
    }

    public void setDisplayGroup(java.lang.String str) {
        this.displayGroup = str;
    }

    static com.android.server.display.config.layout.Display read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.layout.Display display = new com.android.server.display.config.layout.Display();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        if (attributeValue != null) {
            display.setEnabled(java.lang.Boolean.parseBoolean(attributeValue));
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "defaultDisplay");
        if (attributeValue2 != null) {
            display.setDefaultDisplay(java.lang.Boolean.parseBoolean(attributeValue2));
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "refreshRateZoneId");
        if (attributeValue3 != null) {
            display.setRefreshRateZoneId(attributeValue3);
        }
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "displayGroup");
        if (attributeValue4 != null) {
            display.setDisplayGroup(attributeValue4);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("address")) {
                    display.setAddress_optional(new java.math.BigInteger(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("port")) {
                    display.setPort_optional(new java.math.BigInteger(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("position")) {
                    display.setPosition(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser));
                } else if (name.equals("brightnessThrottlingMapId")) {
                    display.setBrightnessThrottlingMapId(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser));
                } else if (name.equals("powerThrottlingMapId")) {
                    display.setPowerThrottlingMapId(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser));
                } else if (name.equals("refreshRateThermalThrottlingMapId")) {
                    display.setRefreshRateThermalThrottlingMapId(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser));
                } else if (name.equals("leadDisplayAddress")) {
                    display.setLeadDisplayAddress(new java.math.BigInteger(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.layout.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Display is not closed");
        }
        return display;
    }
}
