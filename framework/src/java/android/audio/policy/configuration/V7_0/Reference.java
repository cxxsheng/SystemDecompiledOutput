package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Reference {
    private java.lang.String name;
    private java.util.List<java.lang.String> point;

    public java.util.List<java.lang.String> getPoint() {
        if (this.point == null) {
            this.point = new java.util.ArrayList();
        }
        return this.point;
    }

    public java.lang.String getName() {
        return this.name;
    }

    boolean hasName() {
        if (this.name == null) {
            return false;
        }
        return true;
    }

    public void setName(java.lang.String str) {
        this.name = str;
    }

    static android.audio.policy.configuration.V7_0.Reference read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Reference reference = new android.audio.policy.configuration.V7_0.Reference();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue != null) {
            reference.setName(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    reference.getPoint().add(android.audio.policy.configuration.V7_0.XmlParser.readText(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Reference is not closed");
        }
        return reference;
    }
}
