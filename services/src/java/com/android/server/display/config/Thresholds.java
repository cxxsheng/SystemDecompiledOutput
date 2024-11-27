package com.android.server.display.config;

/* loaded from: classes.dex */
public class Thresholds {

    @android.annotation.NonNull
    private com.android.server.display.config.BrightnessThresholds brighteningThresholds;

    @android.annotation.NonNull
    private com.android.server.display.config.BrightnessThresholds darkeningThresholds;

    @android.annotation.NonNull
    public final com.android.server.display.config.BrightnessThresholds getBrighteningThresholds() {
        return this.brighteningThresholds;
    }

    boolean hasBrighteningThresholds() {
        if (this.brighteningThresholds == null) {
            return false;
        }
        return true;
    }

    public final void setBrighteningThresholds(@android.annotation.NonNull com.android.server.display.config.BrightnessThresholds brightnessThresholds) {
        this.brighteningThresholds = brightnessThresholds;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.BrightnessThresholds getDarkeningThresholds() {
        return this.darkeningThresholds;
    }

    boolean hasDarkeningThresholds() {
        if (this.darkeningThresholds == null) {
            return false;
        }
        return true;
    }

    public final void setDarkeningThresholds(@android.annotation.NonNull com.android.server.display.config.BrightnessThresholds brightnessThresholds) {
        this.darkeningThresholds = brightnessThresholds;
    }

    static com.android.server.display.config.Thresholds read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.Thresholds thresholds = new com.android.server.display.config.Thresholds();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("brighteningThresholds")) {
                    thresholds.setBrighteningThresholds(com.android.server.display.config.BrightnessThresholds.read(xmlPullParser));
                } else if (name.equals("darkeningThresholds")) {
                    thresholds.setDarkeningThresholds(com.android.server.display.config.BrightnessThresholds.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Thresholds is not closed");
        }
        return thresholds;
    }
}
