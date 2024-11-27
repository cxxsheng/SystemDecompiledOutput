package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class SurroundSound {
    private android.audio.policy.configuration.V7_0.SurroundFormats formats;

    public android.audio.policy.configuration.V7_0.SurroundFormats getFormats() {
        return this.formats;
    }

    boolean hasFormats() {
        if (this.formats == null) {
            return false;
        }
        return true;
    }

    public void setFormats(android.audio.policy.configuration.V7_0.SurroundFormats surroundFormats) {
        this.formats = surroundFormats;
    }

    static android.audio.policy.configuration.V7_0.SurroundSound read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.SurroundSound surroundSound = new android.audio.policy.configuration.V7_0.SurroundSound();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("formats")) {
                    surroundSound.setFormats(android.audio.policy.configuration.V7_0.SurroundFormats.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("SurroundSound is not closed");
        }
        return surroundSound;
    }
}
