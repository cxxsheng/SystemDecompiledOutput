package com.android.server.display.config;

/* loaded from: classes.dex */
public class AutoBrightness {
    private java.math.BigInteger brighteningLightDebounceIdleMillis;
    private java.math.BigInteger brighteningLightDebounceMillis;
    private java.math.BigInteger darkeningLightDebounceIdleMillis;
    private java.math.BigInteger darkeningLightDebounceMillis;
    private java.lang.Boolean enabled;
    private java.util.List<com.android.server.display.config.LuxToBrightnessMapping> luxToBrightnessMapping;

    public final java.math.BigInteger getBrighteningLightDebounceMillis() {
        return this.brighteningLightDebounceMillis;
    }

    boolean hasBrighteningLightDebounceMillis() {
        if (this.brighteningLightDebounceMillis == null) {
            return false;
        }
        return true;
    }

    public final void setBrighteningLightDebounceMillis(java.math.BigInteger bigInteger) {
        this.brighteningLightDebounceMillis = bigInteger;
    }

    public final java.math.BigInteger getDarkeningLightDebounceMillis() {
        return this.darkeningLightDebounceMillis;
    }

    boolean hasDarkeningLightDebounceMillis() {
        if (this.darkeningLightDebounceMillis == null) {
            return false;
        }
        return true;
    }

    public final void setDarkeningLightDebounceMillis(java.math.BigInteger bigInteger) {
        this.darkeningLightDebounceMillis = bigInteger;
    }

    public final java.math.BigInteger getBrighteningLightDebounceIdleMillis() {
        return this.brighteningLightDebounceIdleMillis;
    }

    boolean hasBrighteningLightDebounceIdleMillis() {
        if (this.brighteningLightDebounceIdleMillis == null) {
            return false;
        }
        return true;
    }

    public final void setBrighteningLightDebounceIdleMillis(java.math.BigInteger bigInteger) {
        this.brighteningLightDebounceIdleMillis = bigInteger;
    }

    public final java.math.BigInteger getDarkeningLightDebounceIdleMillis() {
        return this.darkeningLightDebounceIdleMillis;
    }

    boolean hasDarkeningLightDebounceIdleMillis() {
        if (this.darkeningLightDebounceIdleMillis == null) {
            return false;
        }
        return true;
    }

    public final void setDarkeningLightDebounceIdleMillis(java.math.BigInteger bigInteger) {
        this.darkeningLightDebounceIdleMillis = bigInteger;
    }

    public final java.util.List<com.android.server.display.config.LuxToBrightnessMapping> getLuxToBrightnessMapping() {
        if (this.luxToBrightnessMapping == null) {
            this.luxToBrightnessMapping = new java.util.ArrayList();
        }
        return this.luxToBrightnessMapping;
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

    static com.android.server.display.config.AutoBrightness read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.AutoBrightness autoBrightness = new com.android.server.display.config.AutoBrightness();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        if (attributeValue != null) {
            autoBrightness.setEnabled(java.lang.Boolean.parseBoolean(attributeValue));
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("brighteningLightDebounceMillis")) {
                    autoBrightness.setBrighteningLightDebounceMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("darkeningLightDebounceMillis")) {
                    autoBrightness.setDarkeningLightDebounceMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("brighteningLightDebounceIdleMillis")) {
                    autoBrightness.setBrighteningLightDebounceIdleMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("darkeningLightDebounceIdleMillis")) {
                    autoBrightness.setDarkeningLightDebounceIdleMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("luxToBrightnessMapping")) {
                    autoBrightness.getLuxToBrightnessMapping().add(com.android.server.display.config.LuxToBrightnessMapping.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("AutoBrightness is not closed");
        }
        return autoBrightness;
    }
}
