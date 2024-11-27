package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public class OverrideValue {
    private java.lang.Boolean enabled;
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

    static com.android.server.compat.overrides.OverrideValue read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        com.android.server.compat.overrides.OverrideValue overrideValue = new com.android.server.compat.overrides.OverrideValue();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
        if (attributeValue != null) {
            overrideValue.setPackageName(attributeValue);
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        if (attributeValue2 != null) {
            overrideValue.setEnabled(java.lang.Boolean.parseBoolean(attributeValue2));
        }
        com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
        return overrideValue;
    }

    void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        if (hasPackageName()) {
            xmlWriter.print(" packageName=\"");
            xmlWriter.print(getPackageName());
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
