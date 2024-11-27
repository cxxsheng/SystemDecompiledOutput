package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class BundlePolicySerializer extends com.android.server.devicepolicy.PolicySerializer<android.os.Bundle> {
    private static final java.lang.String ATTR_KEY = "key";
    private static final java.lang.String ATTR_MULTIPLE = "m";
    private static final java.lang.String ATTR_TYPE_BOOLEAN = "b";
    private static final java.lang.String ATTR_TYPE_BUNDLE = "B";
    private static final java.lang.String ATTR_TYPE_BUNDLE_ARRAY = "BA";
    private static final java.lang.String ATTR_TYPE_INTEGER = "i";
    private static final java.lang.String ATTR_TYPE_STRING = "s";
    private static final java.lang.String ATTR_TYPE_STRING_ARRAY = "sa";
    private static final java.lang.String ATTR_VALUE_TYPE = "type";
    private static final java.lang.String TAG = "BundlePolicySerializer";
    private static final java.lang.String TAG_ENTRY = "entry";
    private static final java.lang.String TAG_VALUE = "value";

    BundlePolicySerializer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(@android.annotation.NonNull android.app.admin.PolicyKey policyKey, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.os.Bundle bundle) throws java.io.IOException {
        java.util.Objects.requireNonNull(bundle);
        java.util.Objects.requireNonNull(policyKey);
        if (!(policyKey instanceof android.app.admin.PackagePolicyKey)) {
            throw new java.lang.IllegalArgumentException("policyKey is not of type PackagePolicyKey");
        }
        writeBundle(bundle, typedXmlSerializer);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.devicepolicy.PolicySerializer
    public android.app.admin.BundlePolicyValue readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        android.os.Bundle bundle = new android.os.Bundle();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            int depth = typedXmlPullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                readBundle(bundle, arrayList, typedXmlPullParser);
            }
            return new android.app.admin.BundlePolicyValue(bundle);
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing Bundle policy.", e);
            return null;
        }
    }

    private static void readBundle(android.os.Bundle bundle, java.util.ArrayList<java.lang.String> arrayList, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (typedXmlPullParser.getEventType() == 2 && typedXmlPullParser.getName().equals(TAG_ENTRY)) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_KEY);
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "type");
            int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_MULTIPLE, -1);
            if (attributeInt != -1) {
                arrayList.clear();
                while (attributeInt > 0) {
                    int next = typedXmlPullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && typedXmlPullParser.getName().equals(TAG_VALUE)) {
                        arrayList.add(typedXmlPullParser.nextText().trim());
                        attributeInt--;
                    }
                }
                java.lang.String[] strArr = new java.lang.String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if (ATTR_TYPE_BUNDLE.equals(attributeValue2)) {
                bundle.putBundle(attributeValue, readBundleEntry(typedXmlPullParser, arrayList));
                return;
            }
            if (ATTR_TYPE_BUNDLE_ARRAY.equals(attributeValue2)) {
                int depth = typedXmlPullParser.getDepth();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    arrayList2.add(readBundleEntry(typedXmlPullParser, arrayList));
                }
                bundle.putParcelableArray(attributeValue, (android.os.Parcelable[]) arrayList2.toArray(new android.os.Bundle[arrayList2.size()]));
                return;
            }
            java.lang.String trim = typedXmlPullParser.nextText().trim();
            if (ATTR_TYPE_BOOLEAN.equals(attributeValue2)) {
                bundle.putBoolean(attributeValue, java.lang.Boolean.parseBoolean(trim));
            } else if (ATTR_TYPE_INTEGER.equals(attributeValue2)) {
                bundle.putInt(attributeValue, java.lang.Integer.parseInt(trim));
            } else {
                bundle.putString(attributeValue, trim);
            }
        }
    }

    private static android.os.Bundle readBundleEntry(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.util.ArrayList<java.lang.String> arrayList) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.os.Bundle bundle = new android.os.Bundle();
        int depth = typedXmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            readBundle(bundle, arrayList, typedXmlPullParser);
        }
        return bundle;
    }

    private static void writeBundle(android.os.Bundle bundle, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            typedXmlSerializer.startTag((java.lang.String) null, TAG_ENTRY);
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_KEY, str);
            if (obj instanceof java.lang.Boolean) {
                typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_BOOLEAN);
                typedXmlSerializer.text(obj.toString());
            } else if (obj instanceof java.lang.Integer) {
                typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_INTEGER);
                typedXmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof java.lang.String)) {
                typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_STRING);
                typedXmlSerializer.text(obj != null ? (java.lang.String) obj : "");
            } else if (obj instanceof android.os.Bundle) {
                typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_BUNDLE);
                writeBundle((android.os.Bundle) obj, typedXmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof android.os.Parcelable[]) {
                    typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_BUNDLE_ARRAY);
                    android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        android.os.Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof android.os.Bundle)) {
                            throw new java.lang.IllegalArgumentException("bundle-array can only hold Bundles");
                        }
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_ENTRY);
                        typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_BUNDLE);
                        writeBundle((android.os.Bundle) parcelable, typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_ENTRY);
                        i++;
                    }
                } else {
                    typedXmlSerializer.attribute((java.lang.String) null, "type", ATTR_TYPE_STRING_ARRAY);
                    java.lang.String[] strArr = (java.lang.String[]) obj;
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_MULTIPLE, strArr.length);
                    int length2 = strArr.length;
                    while (i < length2) {
                        java.lang.String str2 = strArr[i];
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_VALUE);
                        if (str2 == null) {
                            str2 = "";
                        }
                        typedXmlSerializer.text(str2);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_VALUE);
                        i++;
                    }
                }
            }
            typedXmlSerializer.endTag((java.lang.String) null, TAG_ENTRY);
        }
    }
}
