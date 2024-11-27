package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class GlobalConfiguration {
    private java.lang.Boolean call_screen_mode_supported;
    private android.audio.policy.configuration.V7_0.EngineSuffix engine_library;
    private java.lang.Boolean speaker_drc_enabled;

    public boolean getSpeaker_drc_enabled() {
        if (this.speaker_drc_enabled == null) {
            return false;
        }
        return this.speaker_drc_enabled.booleanValue();
    }

    boolean hasSpeaker_drc_enabled() {
        if (this.speaker_drc_enabled == null) {
            return false;
        }
        return true;
    }

    public void setSpeaker_drc_enabled(boolean z) {
        this.speaker_drc_enabled = java.lang.Boolean.valueOf(z);
    }

    public boolean getCall_screen_mode_supported() {
        if (this.call_screen_mode_supported == null) {
            return false;
        }
        return this.call_screen_mode_supported.booleanValue();
    }

    boolean hasCall_screen_mode_supported() {
        if (this.call_screen_mode_supported == null) {
            return false;
        }
        return true;
    }

    public void setCall_screen_mode_supported(boolean z) {
        this.call_screen_mode_supported = java.lang.Boolean.valueOf(z);
    }

    public android.audio.policy.configuration.V7_0.EngineSuffix getEngine_library() {
        return this.engine_library;
    }

    boolean hasEngine_library() {
        if (this.engine_library == null) {
            return false;
        }
        return true;
    }

    public void setEngine_library(android.audio.policy.configuration.V7_0.EngineSuffix engineSuffix) {
        this.engine_library = engineSuffix;
    }

    static android.audio.policy.configuration.V7_0.GlobalConfiguration read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        android.audio.policy.configuration.V7_0.GlobalConfiguration globalConfiguration = new android.audio.policy.configuration.V7_0.GlobalConfiguration();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "speaker_drc_enabled");
        if (attributeValue != null) {
            globalConfiguration.setSpeaker_drc_enabled(java.lang.Boolean.parseBoolean(attributeValue));
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "call_screen_mode_supported");
        if (attributeValue2 != null) {
            globalConfiguration.setCall_screen_mode_supported(java.lang.Boolean.parseBoolean(attributeValue2));
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "engine_library");
        if (attributeValue3 != null) {
            globalConfiguration.setEngine_library(android.audio.policy.configuration.V7_0.EngineSuffix.fromString(attributeValue3));
        }
        android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
        return globalConfiguration;
    }
}
