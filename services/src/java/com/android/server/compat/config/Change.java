package com.android.server.compat.config;

/* loaded from: classes.dex */
public class Change {
    private java.lang.String description;
    private java.lang.Boolean disabled;
    private java.lang.Integer enableAfterTargetSdk;
    private java.lang.Integer enableSinceTargetSdk;
    private java.lang.Long id;
    private java.lang.Boolean loggingOnly;
    private java.lang.String name;
    private java.lang.Boolean overridable;
    private java.lang.String value;

    public long getId() {
        if (this.id == null) {
            return 0L;
        }
        return this.id.longValue();
    }

    boolean hasId() {
        if (this.id == null) {
            return false;
        }
        return true;
    }

    public void setId(long j) {
        this.id = java.lang.Long.valueOf(j);
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

    public boolean getDisabled() {
        if (this.disabled == null) {
            return false;
        }
        return this.disabled.booleanValue();
    }

    boolean hasDisabled() {
        if (this.disabled == null) {
            return false;
        }
        return true;
    }

    public void setDisabled(boolean z) {
        this.disabled = java.lang.Boolean.valueOf(z);
    }

    public boolean getLoggingOnly() {
        if (this.loggingOnly == null) {
            return false;
        }
        return this.loggingOnly.booleanValue();
    }

    boolean hasLoggingOnly() {
        if (this.loggingOnly == null) {
            return false;
        }
        return true;
    }

    public void setLoggingOnly(boolean z) {
        this.loggingOnly = java.lang.Boolean.valueOf(z);
    }

    public int getEnableAfterTargetSdk() {
        if (this.enableAfterTargetSdk == null) {
            return 0;
        }
        return this.enableAfterTargetSdk.intValue();
    }

    boolean hasEnableAfterTargetSdk() {
        if (this.enableAfterTargetSdk == null) {
            return false;
        }
        return true;
    }

    public void setEnableAfterTargetSdk(int i) {
        this.enableAfterTargetSdk = java.lang.Integer.valueOf(i);
    }

    public int getEnableSinceTargetSdk() {
        if (this.enableSinceTargetSdk == null) {
            return 0;
        }
        return this.enableSinceTargetSdk.intValue();
    }

    boolean hasEnableSinceTargetSdk() {
        if (this.enableSinceTargetSdk == null) {
            return false;
        }
        return true;
    }

    public void setEnableSinceTargetSdk(int i) {
        this.enableSinceTargetSdk = java.lang.Integer.valueOf(i);
    }

    public java.lang.String getDescription() {
        return this.description;
    }

    boolean hasDescription() {
        if (this.description == null) {
            return false;
        }
        return true;
    }

    public void setDescription(java.lang.String str) {
        this.description = str;
    }

    public boolean getOverridable() {
        if (this.overridable == null) {
            return false;
        }
        return this.overridable.booleanValue();
    }

    boolean hasOverridable() {
        if (this.overridable == null) {
            return false;
        }
        return true;
    }

    public void setOverridable(boolean z) {
        this.overridable = java.lang.Boolean.valueOf(z);
    }

    public java.lang.String getValue() {
        return this.value;
    }

    boolean hasValue() {
        if (this.value == null) {
            return false;
        }
        return true;
    }

    public void setValue(java.lang.String str) {
        this.value = str;
    }

    static com.android.server.compat.config.Change read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        com.android.server.compat.config.Change change = new com.android.server.compat.config.Change();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        if (attributeValue != null) {
            change.setId(java.lang.Long.parseLong(attributeValue));
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue2 != null) {
            change.setName(attributeValue2);
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
        if (attributeValue3 != null) {
            change.setDisabled(java.lang.Boolean.parseBoolean(attributeValue3));
        }
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "loggingOnly");
        if (attributeValue4 != null) {
            change.setLoggingOnly(java.lang.Boolean.parseBoolean(attributeValue4));
        }
        java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "enableAfterTargetSdk");
        if (attributeValue5 != null) {
            change.setEnableAfterTargetSdk(java.lang.Integer.parseInt(attributeValue5));
        }
        java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "enableSinceTargetSdk");
        if (attributeValue6 != null) {
            change.setEnableSinceTargetSdk(java.lang.Integer.parseInt(attributeValue6));
        }
        java.lang.String attributeValue7 = xmlPullParser.getAttributeValue(null, "description");
        if (attributeValue7 != null) {
            change.setDescription(attributeValue7);
        }
        java.lang.String attributeValue8 = xmlPullParser.getAttributeValue(null, "overridable");
        if (attributeValue8 != null) {
            change.setOverridable(java.lang.Boolean.parseBoolean(attributeValue8));
        }
        java.lang.String readText = com.android.server.compat.config.XmlParser.readText(xmlPullParser);
        if (readText != null) {
            change.setValue(readText);
        }
        return change;
    }
}
