package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class Conditions {

    @android.annotation.Nullable
    private com.android.server.policy.devicestate.config.LidSwitchCondition lidSwitch;

    @android.annotation.Nullable
    private java.util.List<com.android.server.policy.devicestate.config.SensorCondition> sensor;

    @android.annotation.Nullable
    public com.android.server.policy.devicestate.config.LidSwitchCondition getLidSwitch() {
        return this.lidSwitch;
    }

    boolean hasLidSwitch() {
        if (this.lidSwitch == null) {
            return false;
        }
        return true;
    }

    public void setLidSwitch(@android.annotation.Nullable com.android.server.policy.devicestate.config.LidSwitchCondition lidSwitchCondition) {
        this.lidSwitch = lidSwitchCondition;
    }

    @android.annotation.Nullable
    public java.util.List<com.android.server.policy.devicestate.config.SensorCondition> getSensor() {
        if (this.sensor == null) {
            this.sensor = new java.util.ArrayList();
        }
        return this.sensor;
    }

    static com.android.server.policy.devicestate.config.Conditions read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.Conditions conditions = new com.android.server.policy.devicestate.config.Conditions();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("lid-switch")) {
                    conditions.setLidSwitch(com.android.server.policy.devicestate.config.LidSwitchCondition.read(xmlPullParser));
                } else if (name.equals("sensor")) {
                    conditions.getSensor().add(com.android.server.policy.devicestate.config.SensorCondition.read(xmlPullParser));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Conditions is not closed");
        }
        return conditions;
    }
}
