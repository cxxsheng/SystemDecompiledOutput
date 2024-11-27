package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class Flags {

    @android.annotation.Nullable
    private java.util.List<java.lang.String> flag;

    @android.annotation.Nullable
    public java.util.List<java.lang.String> getFlag() {
        if (this.flag == null) {
            this.flag = new java.util.ArrayList();
        }
        return this.flag;
    }

    static com.android.server.policy.devicestate.config.Flags read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.Flags flags = new com.android.server.policy.devicestate.config.Flags();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("flag")) {
                    flags.getFlag().add(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Flags is not closed");
        }
        return flags;
    }
}
