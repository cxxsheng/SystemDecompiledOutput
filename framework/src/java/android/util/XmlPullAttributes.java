package android.util;

/* loaded from: classes3.dex */
class XmlPullAttributes implements android.util.AttributeSet {
    org.xmlpull.v1.XmlPullParser mParser;

    public XmlPullAttributes(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        this.mParser = xmlPullParser;
    }

    @Override // android.util.AttributeSet
    public int getAttributeCount() {
        return this.mParser.getAttributeCount();
    }

    @Override // android.util.AttributeSet
    public java.lang.String getAttributeNamespace(int i) {
        return this.mParser.getAttributeNamespace(i);
    }

    @Override // android.util.AttributeSet
    public java.lang.String getAttributeName(int i) {
        return this.mParser.getAttributeName(i);
    }

    @Override // android.util.AttributeSet
    public java.lang.String getAttributeValue(int i) {
        return this.mParser.getAttributeValue(i);
    }

    @Override // android.util.AttributeSet
    public java.lang.String getAttributeValue(java.lang.String str, java.lang.String str2) {
        return this.mParser.getAttributeValue(str, str2);
    }

    @Override // android.util.AttributeSet
    public java.lang.String getPositionDescription() {
        return this.mParser.getPositionDescription();
    }

    @Override // android.util.AttributeSet
    public int getAttributeNameResource(int i) {
        return 0;
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i) {
        return com.android.internal.util.XmlUtils.convertValueToList(getAttributeValue(str, str2), strArr, i);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(java.lang.String str, java.lang.String str2, boolean z) {
        return com.android.internal.util.XmlUtils.convertValueToBoolean(getAttributeValue(str, str2), z);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(java.lang.String str, java.lang.String str2, int i) {
        return com.android.internal.util.XmlUtils.convertValueToInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(java.lang.String str, java.lang.String str2, int i) {
        return com.android.internal.util.XmlUtils.convertValueToInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(java.lang.String str, java.lang.String str2, int i) {
        return com.android.internal.util.XmlUtils.convertValueToUnsignedInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(java.lang.String str, java.lang.String str2, float f) {
        java.lang.String attributeValue = getAttributeValue(str, str2);
        if (attributeValue != null) {
            return java.lang.Float.parseFloat(attributeValue);
        }
        return f;
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(int i, java.lang.String[] strArr, int i2) {
        return com.android.internal.util.XmlUtils.convertValueToList(getAttributeValue(i), strArr, i2);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(int i, boolean z) {
        return com.android.internal.util.XmlUtils.convertValueToBoolean(getAttributeValue(i), z);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(int i, int i2) {
        return com.android.internal.util.XmlUtils.convertValueToInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(int i, int i2) {
        return com.android.internal.util.XmlUtils.convertValueToInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(int i, int i2) {
        return com.android.internal.util.XmlUtils.convertValueToUnsignedInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(int i, float f) {
        java.lang.String attributeValue = getAttributeValue(i);
        if (attributeValue != null) {
            return java.lang.Float.parseFloat(attributeValue);
        }
        return f;
    }

    @Override // android.util.AttributeSet
    public java.lang.String getIdAttribute() {
        return getAttributeValue(null, "id");
    }

    @Override // android.util.AttributeSet
    public java.lang.String getClassAttribute() {
        return getAttributeValue(null, "class");
    }

    @Override // android.util.AttributeSet
    public int getIdAttributeResourceValue(int i) {
        return getAttributeResourceValue(null, "id", i);
    }

    @Override // android.util.AttributeSet
    public int getStyleAttribute() {
        return getAttributeResourceValue(null, "style", 0);
    }
}
