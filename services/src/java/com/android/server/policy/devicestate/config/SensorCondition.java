package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class SensorCondition {
    private java.lang.String name;
    private java.lang.String type;
    private java.util.List<com.android.server.policy.devicestate.config.NumericRange> value;

    public java.lang.String getType() {
        return this.type;
    }

    boolean hasType() {
        if (this.type == null) {
            return false;
        }
        return true;
    }

    public void setType(java.lang.String str) {
        this.type = str;
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

    public java.util.List<com.android.server.policy.devicestate.config.NumericRange> getValue() {
        if (this.value == null) {
            this.value = new java.util.ArrayList();
        }
        return this.value;
    }

    static com.android.server.policy.devicestate.config.SensorCondition read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.SensorCondition sensorCondition = new com.android.server.policy.devicestate.config.SensorCondition();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_TYPE)) {
                    sensorCondition.setType(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("name")) {
                    sensorCondition.setName(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("value")) {
                    sensorCondition.getValue().add(com.android.server.policy.devicestate.config.NumericRange.read(xmlPullParser));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("SensorCondition is not closed");
        }
        return sensorCondition;
    }
}
