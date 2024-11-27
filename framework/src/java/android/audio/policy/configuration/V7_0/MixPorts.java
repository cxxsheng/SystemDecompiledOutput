package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class MixPorts {
    private java.util.List<android.audio.policy.configuration.V7_0.MixPorts.MixPort> mixPort;

    public static class MixPort {
        private java.util.List<android.audio.policy.configuration.V7_0.AudioInOutFlag> flags;
        private android.audio.policy.configuration.V7_0.Gains gains;
        private java.lang.Long maxActiveCount;
        private java.lang.Long maxOpenCount;
        private java.lang.String name;
        private java.util.List<android.audio.policy.configuration.V7_0.AudioUsage> preferredUsage;
        private java.util.List<android.audio.policy.configuration.V7_0.Profile> profile;
        private android.audio.policy.configuration.V7_0.Role role;

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

        public java.util.List<android.audio.policy.configuration.V7_0.AudioInOutFlag> getFlags() {
            if (this.flags == null) {
                this.flags = new java.util.ArrayList();
            }
            return this.flags;
        }

        boolean hasFlags() {
            if (this.flags == null) {
                return false;
            }
            return true;
        }

        public void setFlags(java.util.List<android.audio.policy.configuration.V7_0.AudioInOutFlag> list) {
            this.flags = list;
        }

        public long getMaxOpenCount() {
            if (this.maxOpenCount == null) {
                return 0L;
            }
            return this.maxOpenCount.longValue();
        }

        boolean hasMaxOpenCount() {
            if (this.maxOpenCount == null) {
                return false;
            }
            return true;
        }

        public void setMaxOpenCount(long j) {
            this.maxOpenCount = java.lang.Long.valueOf(j);
        }

        public long getMaxActiveCount() {
            if (this.maxActiveCount == null) {
                return 0L;
            }
            return this.maxActiveCount.longValue();
        }

        boolean hasMaxActiveCount() {
            if (this.maxActiveCount == null) {
                return false;
            }
            return true;
        }

        public void setMaxActiveCount(long j) {
            this.maxActiveCount = java.lang.Long.valueOf(j);
        }

        public java.util.List<android.audio.policy.configuration.V7_0.AudioUsage> getPreferredUsage() {
            if (this.preferredUsage == null) {
                this.preferredUsage = new java.util.ArrayList();
            }
            return this.preferredUsage;
        }

        boolean hasPreferredUsage() {
            if (this.preferredUsage == null) {
                return false;
            }
            return true;
        }

        public void setPreferredUsage(java.util.List<android.audio.policy.configuration.V7_0.AudioUsage> list) {
            this.preferredUsage = list;
        }

        static android.audio.policy.configuration.V7_0.MixPorts.MixPort read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            android.audio.policy.configuration.V7_0.MixPorts.MixPort mixPort = new android.audio.policy.configuration.V7_0.MixPorts.MixPort();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                mixPort.setName(attributeValue);
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, android.content.Context.ROLE_SERVICE);
            if (attributeValue2 != null) {
                mixPort.setRole(android.audio.policy.configuration.V7_0.Role.fromString(attributeValue2));
            }
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "flags");
            if (attributeValue3 != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : attributeValue3.split("\\s+")) {
                    arrayList.add(android.audio.policy.configuration.V7_0.AudioInOutFlag.fromString(str));
                }
                mixPort.setFlags(arrayList);
            }
            java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "maxOpenCount");
            if (attributeValue4 != null) {
                mixPort.setMaxOpenCount(java.lang.Long.parseLong(attributeValue4));
            }
            java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "maxActiveCount");
            if (attributeValue5 != null) {
                mixPort.setMaxActiveCount(java.lang.Long.parseLong(attributeValue5));
            }
            java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "preferredUsage");
            if (attributeValue6 != null) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (java.lang.String str2 : attributeValue6.split("\\s+")) {
                    arrayList2.add(android.audio.policy.configuration.V7_0.AudioUsage.fromString(str2));
                }
                mixPort.setPreferredUsage(arrayList2);
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
                        mixPort.getProfile().add(android.audio.policy.configuration.V7_0.Profile.read(xmlPullParser));
                    } else if (name.equals("gains")) {
                        mixPort.setGains(android.audio.policy.configuration.V7_0.Gains.read(xmlPullParser));
                    } else {
                        android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("MixPorts.MixPort is not closed");
            }
            return mixPort;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.MixPorts.MixPort> getMixPort() {
        if (this.mixPort == null) {
            this.mixPort = new java.util.ArrayList();
        }
        return this.mixPort;
    }

    static android.audio.policy.configuration.V7_0.MixPorts read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.MixPorts mixPorts = new android.audio.policy.configuration.V7_0.MixPorts();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("mixPort")) {
                    mixPorts.getMixPort().add(android.audio.policy.configuration.V7_0.MixPorts.MixPort.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("MixPorts is not closed");
        }
        return mixPorts;
    }
}
