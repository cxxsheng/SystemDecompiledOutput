package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Volumes {
    private java.util.List<android.audio.policy.configuration.V7_0.Reference> reference;
    private java.util.List<android.audio.policy.configuration.V7_0.Volume> volume;

    public java.util.List<android.audio.policy.configuration.V7_0.Volume> getVolume() {
        if (this.volume == null) {
            this.volume = new java.util.ArrayList();
        }
        return this.volume;
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Reference> getReference() {
        if (this.reference == null) {
            this.reference = new java.util.ArrayList();
        }
        return this.reference;
    }

    static android.audio.policy.configuration.V7_0.Volumes read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Volumes volumes = new android.audio.policy.configuration.V7_0.Volumes();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME)) {
                    volumes.getVolume().add(android.audio.policy.configuration.V7_0.Volume.read(xmlPullParser));
                } else if (name.equals("reference")) {
                    volumes.getReference().add(android.audio.policy.configuration.V7_0.Reference.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Volumes is not closed");
        }
        return volumes;
    }
}
