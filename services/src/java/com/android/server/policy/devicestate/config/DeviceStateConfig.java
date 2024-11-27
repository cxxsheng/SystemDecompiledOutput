package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class DeviceStateConfig {
    private java.util.List<com.android.server.policy.devicestate.config.DeviceState> deviceState;

    public java.util.List<com.android.server.policy.devicestate.config.DeviceState> getDeviceState() {
        if (this.deviceState == null) {
            this.deviceState = new java.util.ArrayList();
        }
        return this.deviceState;
    }

    static com.android.server.policy.devicestate.config.DeviceStateConfig read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.DeviceStateConfig deviceStateConfig = new com.android.server.policy.devicestate.config.DeviceStateConfig();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("device-state")) {
                    deviceStateConfig.getDeviceState().add(com.android.server.policy.devicestate.config.DeviceState.read(xmlPullParser));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DeviceStateConfig is not closed");
        }
        return deviceStateConfig;
    }
}
