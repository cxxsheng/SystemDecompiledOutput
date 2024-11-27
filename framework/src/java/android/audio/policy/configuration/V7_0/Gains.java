package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Gains {
    private java.util.List<android.audio.policy.configuration.V7_0.Gains.Gain> gain;

    public static class Gain {
        private android.audio.policy.configuration.V7_0.AudioChannelMask channel_mask;
        private java.lang.Integer defaultValueMB;
        private java.lang.Integer maxRampMs;
        private java.lang.Integer maxValueMB;
        private java.lang.Integer minRampMs;
        private java.lang.Integer minValueMB;
        private java.util.List<android.audio.policy.configuration.V7_0.AudioGainMode> mode;
        private java.lang.String name;
        private java.lang.Integer stepValueMB;
        private java.lang.Boolean useForVolume;

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

        public java.util.List<android.audio.policy.configuration.V7_0.AudioGainMode> getMode() {
            if (this.mode == null) {
                this.mode = new java.util.ArrayList();
            }
            return this.mode;
        }

        boolean hasMode() {
            if (this.mode == null) {
                return false;
            }
            return true;
        }

        public void setMode(java.util.List<android.audio.policy.configuration.V7_0.AudioGainMode> list) {
            this.mode = list;
        }

        public android.audio.policy.configuration.V7_0.AudioChannelMask getChannel_mask() {
            return this.channel_mask;
        }

        boolean hasChannel_mask() {
            if (this.channel_mask == null) {
                return false;
            }
            return true;
        }

        public void setChannel_mask(android.audio.policy.configuration.V7_0.AudioChannelMask audioChannelMask) {
            this.channel_mask = audioChannelMask;
        }

        public int getMinValueMB() {
            if (this.minValueMB == null) {
                return 0;
            }
            return this.minValueMB.intValue();
        }

        boolean hasMinValueMB() {
            if (this.minValueMB == null) {
                return false;
            }
            return true;
        }

        public void setMinValueMB(int i) {
            this.minValueMB = java.lang.Integer.valueOf(i);
        }

        public int getMaxValueMB() {
            if (this.maxValueMB == null) {
                return 0;
            }
            return this.maxValueMB.intValue();
        }

        boolean hasMaxValueMB() {
            if (this.maxValueMB == null) {
                return false;
            }
            return true;
        }

        public void setMaxValueMB(int i) {
            this.maxValueMB = java.lang.Integer.valueOf(i);
        }

        public int getDefaultValueMB() {
            if (this.defaultValueMB == null) {
                return 0;
            }
            return this.defaultValueMB.intValue();
        }

        boolean hasDefaultValueMB() {
            if (this.defaultValueMB == null) {
                return false;
            }
            return true;
        }

        public void setDefaultValueMB(int i) {
            this.defaultValueMB = java.lang.Integer.valueOf(i);
        }

        public int getStepValueMB() {
            if (this.stepValueMB == null) {
                return 0;
            }
            return this.stepValueMB.intValue();
        }

        boolean hasStepValueMB() {
            if (this.stepValueMB == null) {
                return false;
            }
            return true;
        }

        public void setStepValueMB(int i) {
            this.stepValueMB = java.lang.Integer.valueOf(i);
        }

        public int getMinRampMs() {
            if (this.minRampMs == null) {
                return 0;
            }
            return this.minRampMs.intValue();
        }

        boolean hasMinRampMs() {
            if (this.minRampMs == null) {
                return false;
            }
            return true;
        }

        public void setMinRampMs(int i) {
            this.minRampMs = java.lang.Integer.valueOf(i);
        }

        public int getMaxRampMs() {
            if (this.maxRampMs == null) {
                return 0;
            }
            return this.maxRampMs.intValue();
        }

        boolean hasMaxRampMs() {
            if (this.maxRampMs == null) {
                return false;
            }
            return true;
        }

        public void setMaxRampMs(int i) {
            this.maxRampMs = java.lang.Integer.valueOf(i);
        }

        public boolean getUseForVolume() {
            if (this.useForVolume == null) {
                return false;
            }
            return this.useForVolume.booleanValue();
        }

        boolean hasUseForVolume() {
            if (this.useForVolume == null) {
                return false;
            }
            return true;
        }

        public void setUseForVolume(boolean z) {
            this.useForVolume = java.lang.Boolean.valueOf(z);
        }

        static android.audio.policy.configuration.V7_0.Gains.Gain read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            android.audio.policy.configuration.V7_0.Gains.Gain gain = new android.audio.policy.configuration.V7_0.Gains.Gain();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                gain.setName(attributeValue);
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "mode");
            if (attributeValue2 != null) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (java.lang.String str : attributeValue2.split("\\s+")) {
                    arrayList.add(android.audio.policy.configuration.V7_0.AudioGainMode.fromString(str));
                }
                gain.setMode(arrayList);
            }
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "channel_mask");
            if (attributeValue3 != null) {
                gain.setChannel_mask(android.audio.policy.configuration.V7_0.AudioChannelMask.fromString(attributeValue3));
            }
            java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "minValueMB");
            if (attributeValue4 != null) {
                gain.setMinValueMB(java.lang.Integer.parseInt(attributeValue4));
            }
            java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "maxValueMB");
            if (attributeValue5 != null) {
                gain.setMaxValueMB(java.lang.Integer.parseInt(attributeValue5));
            }
            java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "defaultValueMB");
            if (attributeValue6 != null) {
                gain.setDefaultValueMB(java.lang.Integer.parseInt(attributeValue6));
            }
            java.lang.String attributeValue7 = xmlPullParser.getAttributeValue(null, "stepValueMB");
            if (attributeValue7 != null) {
                gain.setStepValueMB(java.lang.Integer.parseInt(attributeValue7));
            }
            java.lang.String attributeValue8 = xmlPullParser.getAttributeValue(null, "minRampMs");
            if (attributeValue8 != null) {
                gain.setMinRampMs(java.lang.Integer.parseInt(attributeValue8));
            }
            java.lang.String attributeValue9 = xmlPullParser.getAttributeValue(null, "maxRampMs");
            if (attributeValue9 != null) {
                gain.setMaxRampMs(java.lang.Integer.parseInt(attributeValue9));
            }
            java.lang.String attributeValue10 = xmlPullParser.getAttributeValue(null, "useForVolume");
            if (attributeValue10 != null) {
                gain.setUseForVolume(java.lang.Boolean.parseBoolean(attributeValue10));
            }
            android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
            return gain;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Gains.Gain> getGain() {
        if (this.gain == null) {
            this.gain = new java.util.ArrayList();
        }
        return this.gain;
    }

    static android.audio.policy.configuration.V7_0.Gains read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Gains gains = new android.audio.policy.configuration.V7_0.Gains();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("gain")) {
                    gains.getGain().add(android.audio.policy.configuration.V7_0.Gains.Gain.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Gains is not closed");
        }
        return gains;
    }
}
