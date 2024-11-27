package com.android.server.display.config;

/* loaded from: classes.dex */
public class BrightnessLimitMap {

    @android.annotation.NonNull
    private com.android.server.display.config.NonNegativeFloatToFloatMap map;

    @android.annotation.NonNull
    private com.android.server.display.config.PredefinedBrightnessLimitNames type;

    @android.annotation.NonNull
    public final com.android.server.display.config.PredefinedBrightnessLimitNames getType() {
        return this.type;
    }

    boolean hasType() {
        if (this.type == null) {
            return false;
        }
        return true;
    }

    public final void setType(@android.annotation.NonNull com.android.server.display.config.PredefinedBrightnessLimitNames predefinedBrightnessLimitNames) {
        this.type = predefinedBrightnessLimitNames;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.NonNegativeFloatToFloatMap getMap() {
        return this.map;
    }

    boolean hasMap() {
        if (this.map == null) {
            return false;
        }
        return true;
    }

    public final void setMap(@android.annotation.NonNull com.android.server.display.config.NonNegativeFloatToFloatMap nonNegativeFloatToFloatMap) {
        this.map = nonNegativeFloatToFloatMap;
    }

    static com.android.server.display.config.BrightnessLimitMap read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.BrightnessLimitMap brightnessLimitMap = new com.android.server.display.config.BrightnessLimitMap();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE)) {
                    brightnessLimitMap.setType(com.android.server.display.config.PredefinedBrightnessLimitNames.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("map")) {
                    brightnessLimitMap.setMap(com.android.server.display.config.NonNegativeFloatToFloatMap.read(xmlPullParser));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("BrightnessLimitMap is not closed");
        }
        return brightnessLimitMap;
    }
}
