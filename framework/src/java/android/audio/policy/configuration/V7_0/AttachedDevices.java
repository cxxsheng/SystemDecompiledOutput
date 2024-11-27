package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class AttachedDevices {
    private java.util.List<java.lang.String> item;

    public java.util.List<java.lang.String> getItem() {
        if (this.item == null) {
            this.item = new java.util.ArrayList();
        }
        return this.item;
    }

    static android.audio.policy.configuration.V7_0.AttachedDevices read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.AttachedDevices attachedDevices = new android.audio.policy.configuration.V7_0.AttachedDevices();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                    attachedDevices.getItem().add(android.audio.policy.configuration.V7_0.XmlParser.readText(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("AttachedDevices is not closed");
        }
        return attachedDevices;
    }
}
