package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class XmlParser {
    public static com.android.server.policy.devicestate.config.DeviceStateConfig read(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        org.xmlpull.v1.XmlPullParser newPullParser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        newPullParser.setInput(inputStream, null);
        newPullParser.nextTag();
        if (!newPullParser.getName().equals("device-state-config")) {
            return null;
        }
        return com.android.server.policy.devicestate.config.DeviceStateConfig.read(newPullParser);
    }

    public static java.lang.String readText(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (xmlPullParser.next() != 4) {
            return "";
        }
        java.lang.String text = xmlPullParser.getText();
        xmlPullParser.nextTag();
        return text;
    }

    public static void skip(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (xmlPullParser.getEventType() != 2) {
            throw new java.lang.IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
            }
        }
    }
}
