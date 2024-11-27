package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Volume {
    private android.audio.policy.configuration.V7_0.DeviceCategory deviceCategory;
    private java.util.List<java.lang.String> point;
    private java.lang.String ref;
    private android.audio.policy.configuration.V7_0.AudioStreamType stream;

    public java.util.List<java.lang.String> getPoint() {
        if (this.point == null) {
            this.point = new java.util.ArrayList();
        }
        return this.point;
    }

    public android.audio.policy.configuration.V7_0.AudioStreamType getStream() {
        return this.stream;
    }

    boolean hasStream() {
        if (this.stream == null) {
            return false;
        }
        return true;
    }

    public void setStream(android.audio.policy.configuration.V7_0.AudioStreamType audioStreamType) {
        this.stream = audioStreamType;
    }

    public android.audio.policy.configuration.V7_0.DeviceCategory getDeviceCategory() {
        return this.deviceCategory;
    }

    boolean hasDeviceCategory() {
        if (this.deviceCategory == null) {
            return false;
        }
        return true;
    }

    public void setDeviceCategory(android.audio.policy.configuration.V7_0.DeviceCategory deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public java.lang.String getRef() {
        return this.ref;
    }

    boolean hasRef() {
        if (this.ref == null) {
            return false;
        }
        return true;
    }

    public void setRef(java.lang.String str) {
        this.ref = str;
    }

    static android.audio.policy.configuration.V7_0.Volume read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Volume volume = new android.audio.policy.configuration.V7_0.Volume();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "stream");
        if (attributeValue != null) {
            volume.setStream(android.audio.policy.configuration.V7_0.AudioStreamType.fromString(attributeValue));
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "deviceCategory");
        if (attributeValue2 != null) {
            volume.setDeviceCategory(android.audio.policy.configuration.V7_0.DeviceCategory.fromString(attributeValue2));
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "ref");
        if (attributeValue3 != null) {
            volume.setRef(attributeValue3);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    volume.getPoint().add(android.audio.policy.configuration.V7_0.XmlParser.readText(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Volume is not closed");
        }
        return volume;
    }
}
