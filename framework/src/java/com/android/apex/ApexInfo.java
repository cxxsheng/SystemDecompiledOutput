package com.android.apex;

/* loaded from: classes4.dex */
public class ApexInfo {
    private java.lang.Boolean isActive;
    private java.lang.Boolean isFactory;
    private java.lang.Long lastUpdateMillis;
    private java.lang.String moduleName;
    private java.lang.String modulePath;
    private java.lang.String preinstalledModulePath;
    private java.lang.Boolean provideSharedApexLibs;
    private java.lang.Long versionCode;
    private java.lang.String versionName;

    public java.lang.String getModuleName() {
        return this.moduleName;
    }

    boolean hasModuleName() {
        if (this.moduleName == null) {
            return false;
        }
        return true;
    }

    public void setModuleName(java.lang.String str) {
        this.moduleName = str;
    }

    public java.lang.String getModulePath() {
        return this.modulePath;
    }

    boolean hasModulePath() {
        if (this.modulePath == null) {
            return false;
        }
        return true;
    }

    public void setModulePath(java.lang.String str) {
        this.modulePath = str;
    }

    public java.lang.String getPreinstalledModulePath() {
        return this.preinstalledModulePath;
    }

    boolean hasPreinstalledModulePath() {
        if (this.preinstalledModulePath == null) {
            return false;
        }
        return true;
    }

    public void setPreinstalledModulePath(java.lang.String str) {
        this.preinstalledModulePath = str;
    }

    public long getVersionCode() {
        if (this.versionCode == null) {
            return 0L;
        }
        return this.versionCode.longValue();
    }

    boolean hasVersionCode() {
        if (this.versionCode == null) {
            return false;
        }
        return true;
    }

    public void setVersionCode(long j) {
        this.versionCode = java.lang.Long.valueOf(j);
    }

    public java.lang.String getVersionName() {
        return this.versionName;
    }

    boolean hasVersionName() {
        if (this.versionName == null) {
            return false;
        }
        return true;
    }

    public void setVersionName(java.lang.String str) {
        this.versionName = str;
    }

    public boolean getIsFactory() {
        if (this.isFactory == null) {
            return false;
        }
        return this.isFactory.booleanValue();
    }

    boolean hasIsFactory() {
        if (this.isFactory == null) {
            return false;
        }
        return true;
    }

    public void setIsFactory(boolean z) {
        this.isFactory = java.lang.Boolean.valueOf(z);
    }

    public boolean getIsActive() {
        if (this.isActive == null) {
            return false;
        }
        return this.isActive.booleanValue();
    }

    boolean hasIsActive() {
        if (this.isActive == null) {
            return false;
        }
        return true;
    }

    public void setIsActive(boolean z) {
        this.isActive = java.lang.Boolean.valueOf(z);
    }

    public long getLastUpdateMillis() {
        if (this.lastUpdateMillis == null) {
            return 0L;
        }
        return this.lastUpdateMillis.longValue();
    }

    boolean hasLastUpdateMillis() {
        if (this.lastUpdateMillis == null) {
            return false;
        }
        return true;
    }

    public void setLastUpdateMillis(long j) {
        this.lastUpdateMillis = java.lang.Long.valueOf(j);
    }

    public boolean getProvideSharedApexLibs() {
        if (this.provideSharedApexLibs == null) {
            return false;
        }
        return this.provideSharedApexLibs.booleanValue();
    }

    boolean hasProvideSharedApexLibs() {
        if (this.provideSharedApexLibs == null) {
            return false;
        }
        return true;
    }

    public void setProvideSharedApexLibs(boolean z) {
        this.provideSharedApexLibs = java.lang.Boolean.valueOf(z);
    }

    static com.android.apex.ApexInfo read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        com.android.apex.ApexInfo apexInfo = new com.android.apex.ApexInfo();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "moduleName");
        if (attributeValue != null) {
            apexInfo.setModuleName(attributeValue);
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "modulePath");
        if (attributeValue2 != null) {
            apexInfo.setModulePath(attributeValue2);
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "preinstalledModulePath");
        if (attributeValue3 != null) {
            apexInfo.setPreinstalledModulePath(attributeValue3);
        }
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, "versionCode");
        if (attributeValue4 != null) {
            apexInfo.setVersionCode(java.lang.Long.parseLong(attributeValue4));
        }
        java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "versionName");
        if (attributeValue5 != null) {
            apexInfo.setVersionName(attributeValue5);
        }
        java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "isFactory");
        if (attributeValue6 != null) {
            apexInfo.setIsFactory(java.lang.Boolean.parseBoolean(attributeValue6));
        }
        java.lang.String attributeValue7 = xmlPullParser.getAttributeValue(null, "isActive");
        if (attributeValue7 != null) {
            apexInfo.setIsActive(java.lang.Boolean.parseBoolean(attributeValue7));
        }
        java.lang.String attributeValue8 = xmlPullParser.getAttributeValue(null, "lastUpdateMillis");
        if (attributeValue8 != null) {
            apexInfo.setLastUpdateMillis(java.lang.Long.parseLong(attributeValue8));
        }
        java.lang.String attributeValue9 = xmlPullParser.getAttributeValue(null, "provideSharedApexLibs");
        if (attributeValue9 != null) {
            apexInfo.setProvideSharedApexLibs(java.lang.Boolean.parseBoolean(attributeValue9));
        }
        com.android.apex.XmlParser.skip(xmlPullParser);
        return apexInfo;
    }

    void write(com.android.apex.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        if (hasModuleName()) {
            xmlWriter.print(" moduleName=\"");
            xmlWriter.print(getModuleName());
            xmlWriter.print("\"");
        }
        if (hasModulePath()) {
            xmlWriter.print(" modulePath=\"");
            xmlWriter.print(getModulePath());
            xmlWriter.print("\"");
        }
        if (hasPreinstalledModulePath()) {
            xmlWriter.print(" preinstalledModulePath=\"");
            xmlWriter.print(getPreinstalledModulePath());
            xmlWriter.print("\"");
        }
        if (hasVersionCode()) {
            xmlWriter.print(" versionCode=\"");
            xmlWriter.print(java.lang.Long.toString(getVersionCode()));
            xmlWriter.print("\"");
        }
        if (hasVersionName()) {
            xmlWriter.print(" versionName=\"");
            xmlWriter.print(getVersionName());
            xmlWriter.print("\"");
        }
        if (hasIsFactory()) {
            xmlWriter.print(" isFactory=\"");
            xmlWriter.print(java.lang.Boolean.toString(getIsFactory()));
            xmlWriter.print("\"");
        }
        if (hasIsActive()) {
            xmlWriter.print(" isActive=\"");
            xmlWriter.print(java.lang.Boolean.toString(getIsActive()));
            xmlWriter.print("\"");
        }
        if (hasLastUpdateMillis()) {
            xmlWriter.print(" lastUpdateMillis=\"");
            xmlWriter.print(java.lang.Long.toString(getLastUpdateMillis()));
            xmlWriter.print("\"");
        }
        if (hasProvideSharedApexLibs()) {
            xmlWriter.print(" provideSharedApexLibs=\"");
            xmlWriter.print(java.lang.Boolean.toString(getProvideSharedApexLibs()));
            xmlWriter.print("\"");
        }
        xmlWriter.print(">\n");
        xmlWriter.print("</" + str + ">\n");
    }
}
