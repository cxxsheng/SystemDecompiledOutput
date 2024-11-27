package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class DevicePorts {
    private java.util.List<android.audio.policy.configuration.V7_0.DevicePorts.DevicePort> devicePort;

    public static class DevicePort {
        private java.lang.Boolean _default;
        private java.lang.String address;
        private java.util.List<java.lang.String> encodedFormats;
        private android.audio.policy.configuration.V7_0.Gains gains;
        private java.util.List<android.audio.policy.configuration.V7_0.Profile> profile;
        private android.audio.policy.configuration.V7_0.Role role;
        private java.lang.String tagName;
        private java.lang.String type;

        public java.util.List<android.audio.policy.configuration.V7_0.Profile> getProfile() {
            if (this.profile == null) {
                this.profile = new java.util.ArrayList();
            }
            return this.profile;
        }

        public android.audio.policy.configuration.V7_0.Gains getGains() {
            return this.gains;
        }

        boolean hasGains() {
            if (this.gains == null) {
                return false;
            }
            return true;
        }

        public void setGains(android.audio.policy.configuration.V7_0.Gains gains) {
            this.gains = gains;
        }

        public java.lang.String getTagName() {
            return this.tagName;
        }

        boolean hasTagName() {
            if (this.tagName == null) {
                return false;
            }
            return true;
        }

        public void setTagName(java.lang.String str) {
            this.tagName = str;
        }

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

        public android.audio.policy.configuration.V7_0.Role getRole() {
            return this.role;
        }

        boolean hasRole() {
            if (this.role == null) {
                return false;
            }
            return true;
        }

        public void setRole(android.audio.policy.configuration.V7_0.Role role) {
            this.role = role;
        }

        public java.lang.String getAddress() {
            return this.address;
        }

        boolean hasAddress() {
            if (this.address == null) {
                return false;
            }
            return true;
        }

        public void setAddress(java.lang.String str) {
            this.address = str;
        }

        public boolean get_default() {
            if (this._default == null) {
                return false;
            }
            return this._default.booleanValue();
        }

        boolean has_default() {
            if (this._default == null) {
                return false;
            }
            return true;
        }

        public void set_default(boolean z) {
            this._default = java.lang.Boolean.valueOf(z);
        }

        public java.util.List<java.lang.String> getEncodedFormats() {
            if (this.encodedFormats == null) {
                this.encodedFormats = new java.util.ArrayList();
            }
            return this.encodedFormats;
        }

        boolean hasEncodedFormats() {
            if (this.encodedFormats == null) {
                return false;
            }
            return true;
        }

        public void setEncodedFormats(java.util.List<java.lang.String> list) {
            this.encodedFormats = list;
        }

        static android.audio.policy.configuration.V7_0.DevicePorts.DevicePort read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            android.audio.policy.configuration.V7_0.DevicePorts.DevicePort devicePort = new android.audio.policy.configuration.V7_0.DevicePorts.DevicePort();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "tagName");
            if (attributeValue != null) {
                devicePort.setTagName(attributeValue);
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue2 != null) {
                devicePort.setType(attributeValue2);
            }
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, android.content.Context.ROLE_SERVICE);
            if (attributeValue3 != null) {
                devicePort.setRole(android.audio.policy.configuration.V7_0.Role.fromString(attributeValue3));
            }
            java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "address");
            if (attributeValue4 != null) {
                devicePort.setAddress(attributeValue4);
            }
            java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "default");
            if (attributeValue5 != null) {
                devicePort.set_default(java.lang.Boolean.parseBoolean(attributeValue5));
            }
            java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "encodedFormats");
            if (attributeValue6 != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : attributeValue6.split("\\s+")) {
                    arrayList.add(str);
                }
                devicePort.setEncodedFormats(arrayList);
            }
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals(android.media.MediaFormat.KEY_PROFILE)) {
                        devicePort.getProfile().add(android.audio.policy.configuration.V7_0.Profile.read(xmlPullParser));
                    } else if (name.equals("gains")) {
                        devicePort.setGains(android.audio.policy.configuration.V7_0.Gains.read(xmlPullParser));
                    } else {
                        android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("DevicePorts.DevicePort is not closed");
            }
            return devicePort;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.DevicePorts.DevicePort> getDevicePort() {
        if (this.devicePort == null) {
            this.devicePort = new java.util.ArrayList();
        }
        return this.devicePort;
    }

    static android.audio.policy.configuration.V7_0.DevicePorts read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.DevicePorts devicePorts = new android.audio.policy.configuration.V7_0.DevicePorts();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("devicePort")) {
                    devicePorts.getDevicePort().add(android.audio.policy.configuration.V7_0.DevicePorts.DevicePort.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DevicePorts is not closed");
        }
        return devicePorts;
    }
}
