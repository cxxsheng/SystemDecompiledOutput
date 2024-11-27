package com.android.server.display.config;

/* loaded from: classes.dex */
public class LuxToBrightnessMapping {

    @android.annotation.NonNull
    private com.android.server.display.config.NonNegativeFloatToFloatMap map;
    private com.android.server.display.config.AutoBrightnessModeName mode;
    private com.android.server.display.config.AutoBrightnessSettingName setting;

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

    public com.android.server.display.config.AutoBrightnessModeName getMode() {
        return this.mode;
    }

    boolean hasMode() {
        if (this.mode == null) {
            return false;
        }
        return true;
    }

    public void setMode(com.android.server.display.config.AutoBrightnessModeName autoBrightnessModeName) {
        this.mode = autoBrightnessModeName;
    }

    public com.android.server.display.config.AutoBrightnessSettingName getSetting() {
        return this.setting;
    }

    boolean hasSetting() {
        if (this.setting == null) {
            return false;
        }
        return true;
    }

    public void setSetting(com.android.server.display.config.AutoBrightnessSettingName autoBrightnessSettingName) {
        this.setting = autoBrightnessSettingName;
    }

    static com.android.server.display.config.LuxToBrightnessMapping read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.LuxToBrightnessMapping luxToBrightnessMapping = new com.android.server.display.config.LuxToBrightnessMapping();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("map")) {
                    luxToBrightnessMapping.setMap(com.android.server.display.config.NonNegativeFloatToFloatMap.read(xmlPullParser));
                } else if (name.equals(com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY)) {
                    luxToBrightnessMapping.setMode(com.android.server.display.config.AutoBrightnessModeName.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("setting")) {
                    luxToBrightnessMapping.setSetting(com.android.server.display.config.AutoBrightnessSettingName.fromString(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("LuxToBrightnessMapping is not closed");
        }
        return luxToBrightnessMapping;
    }
}
