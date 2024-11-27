package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class SurroundFormats {
    private java.util.List<android.audio.policy.configuration.V7_0.SurroundFormats.Format> format;

    public static class Format {
        private java.lang.String name;
        private java.util.List<java.lang.String> subformats;

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

        public java.util.List<java.lang.String> getSubformats() {
            if (this.subformats == null) {
                this.subformats = new java.util.ArrayList();
            }
            return this.subformats;
        }

        boolean hasSubformats() {
            if (this.subformats == null) {
                return false;
            }
            return true;
        }

        public void setSubformats(java.util.List<java.lang.String> list) {
            this.subformats = list;
        }

        static android.audio.policy.configuration.V7_0.SurroundFormats.Format read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            android.audio.policy.configuration.V7_0.SurroundFormats.Format format = new android.audio.policy.configuration.V7_0.SurroundFormats.Format();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                format.setName(attributeValue);
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "subformats");
            if (attributeValue2 != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : attributeValue2.split("\\s+")) {
                    arrayList.add(str);
                }
                format.setSubformats(arrayList);
            }
            android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
            return format;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.SurroundFormats.Format> getFormat() {
        if (this.format == null) {
            this.format = new java.util.ArrayList();
        }
        return this.format;
    }

    static android.audio.policy.configuration.V7_0.SurroundFormats read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.SurroundFormats surroundFormats = new android.audio.policy.configuration.V7_0.SurroundFormats();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT)) {
                    surroundFormats.getFormat().add(android.audio.policy.configuration.V7_0.SurroundFormats.Format.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("SurroundFormats is not closed");
        }
        return surroundFormats;
    }
}
