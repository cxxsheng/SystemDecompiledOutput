package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class LidSwitchCondition {
    private java.lang.Boolean open;

    public boolean getOpen() {
        if (this.open == null) {
            return false;
        }
        return this.open.booleanValue();
    }

    boolean hasOpen() {
        if (this.open == null) {
            return false;
        }
        return true;
    }

    public void setOpen(boolean z) {
        this.open = java.lang.Boolean.valueOf(z);
    }

    static com.android.server.policy.devicestate.config.LidSwitchCondition read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.LidSwitchCondition lidSwitchCondition = new com.android.server.policy.devicestate.config.LidSwitchCondition();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("open")) {
                    lidSwitchCondition.setOpen(java.lang.Boolean.parseBoolean(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("LidSwitchCondition is not closed");
        }
        return lidSwitchCondition;
    }
}
