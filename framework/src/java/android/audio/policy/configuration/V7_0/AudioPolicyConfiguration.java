package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class AudioPolicyConfiguration {
    private android.audio.policy.configuration.V7_0.GlobalConfiguration globalConfiguration;
    private java.util.List<android.audio.policy.configuration.V7_0.Modules> modules;
    private android.audio.policy.configuration.V7_0.SurroundSound surroundSound;
    private android.audio.policy.configuration.V7_0.Version version;
    private java.util.List<android.audio.policy.configuration.V7_0.Volumes> volumes;

    public android.audio.policy.configuration.V7_0.GlobalConfiguration getGlobalConfiguration() {
        return this.globalConfiguration;
    }

    boolean hasGlobalConfiguration() {
        if (this.globalConfiguration == null) {
            return false;
        }
        return true;
    }

    public void setGlobalConfiguration(android.audio.policy.configuration.V7_0.GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Modules> getModules() {
        if (this.modules == null) {
            this.modules = new java.util.ArrayList();
        }
        return this.modules;
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Volumes> getVolumes() {
        if (this.volumes == null) {
            this.volumes = new java.util.ArrayList();
        }
        return this.volumes;
    }

    public android.audio.policy.configuration.V7_0.SurroundSound getSurroundSound() {
        return this.surroundSound;
    }

    boolean hasSurroundSound() {
        if (this.surroundSound == null) {
            return false;
        }
        return true;
    }

    public void setSurroundSound(android.audio.policy.configuration.V7_0.SurroundSound surroundSound) {
        this.surroundSound = surroundSound;
    }

    public android.audio.policy.configuration.V7_0.Version getVersion() {
        return this.version;
    }

    boolean hasVersion() {
        if (this.version == null) {
            return false;
        }
        return true;
    }

    public void setVersion(android.audio.policy.configuration.V7_0.Version version) {
        this.version = version;
    }

    static android.audio.policy.configuration.V7_0.AudioPolicyConfiguration read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.AudioPolicyConfiguration audioPolicyConfiguration = new android.audio.policy.configuration.V7_0.AudioPolicyConfiguration();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "version");
        if (attributeValue != null) {
            audioPolicyConfiguration.setVersion(android.audio.policy.configuration.V7_0.Version.fromString(attributeValue));
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("globalConfiguration")) {
                    audioPolicyConfiguration.setGlobalConfiguration(android.audio.policy.configuration.V7_0.GlobalConfiguration.read(xmlPullParser));
                } else if (name.equals("modules")) {
                    audioPolicyConfiguration.getModules().add(android.audio.policy.configuration.V7_0.Modules.read(xmlPullParser));
                } else if (name.equals("volumes")) {
                    audioPolicyConfiguration.getVolumes().add(android.audio.policy.configuration.V7_0.Volumes.read(xmlPullParser));
                } else if (name.equals("surroundSound")) {
                    audioPolicyConfiguration.setSurroundSound(android.audio.policy.configuration.V7_0.SurroundSound.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("AudioPolicyConfiguration is not closed");
        }
        return audioPolicyConfiguration;
    }
}
