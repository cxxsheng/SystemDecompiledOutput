package android.util;

/* loaded from: classes3.dex */
public interface AttributeSet {
    boolean getAttributeBooleanValue(int i, boolean z);

    boolean getAttributeBooleanValue(java.lang.String str, java.lang.String str2, boolean z);

    int getAttributeCount();

    float getAttributeFloatValue(int i, float f);

    float getAttributeFloatValue(java.lang.String str, java.lang.String str2, float f);

    int getAttributeIntValue(int i, int i2);

    int getAttributeIntValue(java.lang.String str, java.lang.String str2, int i);

    int getAttributeListValue(int i, java.lang.String[] strArr, int i2);

    int getAttributeListValue(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i);

    java.lang.String getAttributeName(int i);

    int getAttributeNameResource(int i);

    int getAttributeResourceValue(int i, int i2);

    int getAttributeResourceValue(java.lang.String str, java.lang.String str2, int i);

    int getAttributeUnsignedIntValue(int i, int i2);

    int getAttributeUnsignedIntValue(java.lang.String str, java.lang.String str2, int i);

    java.lang.String getAttributeValue(int i);

    java.lang.String getAttributeValue(java.lang.String str, java.lang.String str2);

    java.lang.String getClassAttribute();

    java.lang.String getIdAttribute();

    int getIdAttributeResourceValue(int i);

    java.lang.String getPositionDescription();

    int getStyleAttribute();

    default java.lang.String getAttributeNamespace(int i) {
        return null;
    }
}
