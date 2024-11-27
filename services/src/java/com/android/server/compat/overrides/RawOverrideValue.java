package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public class RawOverrideValue {
    private java.lang.Boolean enabled;
    private java.lang.Long maxVersionCode;
    private java.lang.Long minVersionCode;
    private java.lang.String packageName;

    public java.lang.String getPackageName() {
        return this.packageName;
    }

    boolean hasPackageName() {
        if (this.packageName == null) {
            return false;
        }
        return true;
    }

    public void setPackageName(java.lang.String str) {
        this.packageName = str;
    }

    public long getMinVersionCode() {
        if (this.minVersionCode == null) {
            return 0L;
        }
        return this.minVersionCode.longValue();
    }

    boolean hasMinVersionCode() {
        if (this.minVersionCode == null) {
            return false;
        }
        return true;
    }

    public void setMinVersionCode(long j) {
        this.minVersionCode = java.lang.Long.valueOf(j);
    }

    public long getMaxVersionCode() {
        if (this.maxVersionCode == null) {
            return 0L;
        }
        return this.maxVersionCode.longValue();
    }

    boolean hasMaxVersionCode() {
        if (this.maxVersionCode == null) {
            return false;
        }
        return true;
    }

    public void setMaxVersionCode(long j) {
        this.maxVersionCode = java.lang.Long.valueOf(j);
    }

    public boolean getEnabled() {
        if (this.enabled == null) {
            return false;
        }
        return this.enabled.booleanValue();
    }

    boolean hasEnabled() {
        if (this.enabled == null) {
            return false;
        }
        return true;
    }

    public void setEnabled(boolean z) {
        this.enabled = java.lang.Boolean.valueOf(z);
    }

    static com.android.server.compat.overrides.RawOverrideValue read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        com.android.server.compat.overrides.RawOverrideValue rawOverrideValue = new com.android.server.compat.overrides.RawOverrideValue();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        if (attributeValue != null) {
            rawOverrideValue.setPackageName(attributeValue);
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "minVersionCode");
        if (attributeValue2 != null) {
            rawOverrideValue.setMinVersionCode(java.lang.Long.parseLong(attributeValue2));
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "maxVersionCode");
        if (attributeValue3 != null) {
            rawOverrideValue.setMaxVersionCode(java.lang.Long.parseLong(attributeValue3));
        }
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        if (attributeValue4 != null) {
            rawOverrideValue.setEnabled(java.lang.Boolean.parseBoolean(attributeValue4));
        }
        com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
        return rawOverrideValue;
    }

    void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        if (hasPackageName()) {
            xmlWriter.print(" packageName=\"");
            xmlWriter.print(getPackageName());
            xmlWriter.print("\"");
        }
        if (hasMinVersionCode()) {
            xmlWriter.print(" minVersionCode=\"");
            xmlWriter.print(java.lang.Long.toString(getMinVersionCode()));
            xmlWriter.print("\"");
        }
        if (hasMaxVersionCode()) {
            xmlWriter.print(" maxVersionCode=\"");
            xmlWriter.print(java.lang.Long.toString(getMaxVersionCode()));
            xmlWriter.print("\"");
        }
        if (hasEnabled()) {
            xmlWriter.print(" enabled=\"");
            xmlWriter.print(java.lang.Boolean.toString(getEnabled()));
            xmlWriter.print("\"");
        }
        xmlWriter.print(">\n");
        xmlWriter.print("</" + str + ">\n");
    }
}
