package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Profile {
    private java.util.List<android.audio.policy.configuration.V7_0.AudioChannelMask> channelMasks;
    private android.audio.policy.configuration.V7_0.AudioEncapsulationType encapsulationType;
    private java.lang.String format;
    private java.lang.String name;
    private java.util.List<java.math.BigInteger> samplingRates;

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

    public java.lang.String getFormat() {
        return this.format;
    }

    boolean hasFormat() {
        if (this.format == null) {
            return false;
        }
        return true;
    }

    public void setFormat(java.lang.String str) {
        this.format = str;
    }

    public java.util.List<java.math.BigInteger> getSamplingRates() {
        if (this.samplingRates == null) {
            this.samplingRates = new java.util.ArrayList();
        }
        return this.samplingRates;
    }

    boolean hasSamplingRates() {
        if (this.samplingRates == null) {
            return false;
        }
        return true;
    }

    public void setSamplingRates(java.util.List<java.math.BigInteger> list) {
        this.samplingRates = list;
    }

    public java.util.List<android.audio.policy.configuration.V7_0.AudioChannelMask> getChannelMasks() {
        if (this.channelMasks == null) {
            this.channelMasks = new java.util.ArrayList();
        }
        return this.channelMasks;
    }

    boolean hasChannelMasks() {
        if (this.channelMasks == null) {
            return false;
        }
        return true;
    }

    public void setChannelMasks(java.util.List<android.audio.policy.configuration.V7_0.AudioChannelMask> list) {
        this.channelMasks = list;
    }

    public android.audio.policy.configuration.V7_0.AudioEncapsulationType getEncapsulationType() {
        return this.encapsulationType;
    }

    boolean hasEncapsulationType() {
        if (this.encapsulationType == null) {
            return false;
        }
        return true;
    }

    public void setEncapsulationType(android.audio.policy.configuration.V7_0.AudioEncapsulationType audioEncapsulationType) {
        this.encapsulationType = audioEncapsulationType;
    }

    static android.audio.policy.configuration.V7_0.Profile read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        android.audio.policy.configuration.V7_0.Profile profile = new android.audio.policy.configuration.V7_0.Profile();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue != null) {
            profile.setName(attributeValue);
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT);
        if (attributeValue2 != null) {
            profile.setFormat(attributeValue2);
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "samplingRates");
        if (attributeValue3 != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : attributeValue3.split("\\s+")) {
                arrayList.add(new java.math.BigInteger(str));
            }
            profile.setSamplingRates(arrayList);
        }
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "channelMasks");
        if (attributeValue4 != null) {
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            for (java.lang.String str2 : attributeValue4.split("\\s+")) {
                arrayList2.add(android.audio.policy.configuration.V7_0.AudioChannelMask.fromString(str2));
            }
            profile.setChannelMasks(arrayList2);
        }
        java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "encapsulationType");
        if (attributeValue5 != null) {
            profile.setEncapsulationType(android.audio.policy.configuration.V7_0.AudioEncapsulationType.fromString(attributeValue5));
        }
        android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
        return profile;
    }
}
