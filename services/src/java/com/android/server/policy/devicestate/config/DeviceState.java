package com.android.server.policy.devicestate.config;

/* loaded from: classes2.dex */
public class DeviceState {
    private com.android.server.policy.devicestate.config.Conditions conditions;
    private com.android.server.policy.devicestate.config.Flags flags;
    private java.math.BigInteger identifier;

    @android.annotation.Nullable
    private java.lang.String name;

    public java.math.BigInteger getIdentifier() {
        return this.identifier;
    }

    boolean hasIdentifier() {
        if (this.identifier == null) {
            return false;
        }
        return true;
    }

    public void setIdentifier(java.math.BigInteger bigInteger) {
        this.identifier = bigInteger;
    }

    @android.annotation.Nullable
    public java.lang.String getName() {
        return this.name;
    }

    boolean hasName() {
        if (this.name == null) {
            return false;
        }
        return true;
    }

    public void setName(@android.annotation.Nullable java.lang.String str) {
        this.name = str;
    }

    public com.android.server.policy.devicestate.config.Flags getFlags() {
        return this.flags;
    }

    boolean hasFlags() {
        if (this.flags == null) {
            return false;
        }
        return true;
    }

    public void setFlags(com.android.server.policy.devicestate.config.Flags flags) {
        this.flags = flags;
    }

    public com.android.server.policy.devicestate.config.Conditions getConditions() {
        return this.conditions;
    }

    boolean hasConditions() {
        if (this.conditions == null) {
            return false;
        }
        return true;
    }

    public void setConditions(com.android.server.policy.devicestate.config.Conditions conditions) {
        this.conditions = conditions;
    }

    static com.android.server.policy.devicestate.config.DeviceState read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.policy.devicestate.config.DeviceState deviceState = new com.android.server.policy.devicestate.config.DeviceState();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("identifier")) {
                    deviceState.setIdentifier(new java.math.BigInteger(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("name")) {
                    deviceState.setName(com.android.server.policy.devicestate.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("flags")) {
                    deviceState.setFlags(com.android.server.policy.devicestate.config.Flags.read(xmlPullParser));
                } else if (name.equals("conditions")) {
                    deviceState.setConditions(com.android.server.policy.devicestate.config.Conditions.read(xmlPullParser));
                } else {
                    com.android.server.policy.devicestate.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DeviceState is not closed");
        }
        return deviceState;
    }
}
