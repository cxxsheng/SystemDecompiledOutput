package android.graphics.fonts;

/* loaded from: classes.dex */
public class FontCustomizationParser {
    private static final java.lang.String TAG = "FontCustomizationParser";

    public static class Result {
        private final java.util.List<android.text.FontConfig.Alias> mAdditionalAliases;
        private final java.util.Map<java.lang.String, android.text.FontConfig.NamedFamilyList> mAdditionalNamedFamilies;
        private final java.util.List<android.text.FontConfig.Customization.LocaleFallback> mLocaleFamilyCustomizations;

        public Result() {
            this.mAdditionalNamedFamilies = java.util.Collections.emptyMap();
            this.mLocaleFamilyCustomizations = java.util.Collections.emptyList();
            this.mAdditionalAliases = java.util.Collections.emptyList();
        }

        public Result(java.util.Map<java.lang.String, android.text.FontConfig.NamedFamilyList> map, java.util.List<android.text.FontConfig.Customization.LocaleFallback> list, java.util.List<android.text.FontConfig.Alias> list2) {
            this.mAdditionalNamedFamilies = map;
            this.mLocaleFamilyCustomizations = list;
            this.mAdditionalAliases = list2;
        }

        public java.util.Map<java.lang.String, android.text.FontConfig.NamedFamilyList> getAdditionalNamedFamilies() {
            return this.mAdditionalNamedFamilies;
        }

        public java.util.List<android.text.FontConfig.Alias> getAdditionalAliases() {
            return this.mAdditionalAliases;
        }

        public java.util.List<android.text.FontConfig.Customization.LocaleFallback> getLocaleFamilyCustomizations() {
            return this.mLocaleFamilyCustomizations;
        }
    }

    public static android.graphics.fonts.FontCustomizationParser.Result parse(java.io.InputStream inputStream, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
        newPullParser.setInput(inputStream, null);
        newPullParser.nextTag();
        return readFamilies(newPullParser, str, map);
    }

    private static android.graphics.fonts.FontCustomizationParser.Result validateAndTransformToResult(java.util.List<android.text.FontConfig.NamedFamilyList> list, java.util.List<android.text.FontConfig.Customization.LocaleFallback> list2, java.util.List<android.text.FontConfig.Alias> list3) {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (int i = 0; i < list.size(); i++) {
            android.text.FontConfig.NamedFamilyList namedFamilyList = list.get(i);
            java.lang.String name = namedFamilyList.getName();
            if (name != null) {
                if (hashMap.put(name, namedFamilyList) != null) {
                    throw new java.lang.IllegalArgumentException("new-named-family requires unique name attribute");
                }
            } else {
                throw new java.lang.IllegalArgumentException("new-named-family requires name attribute or new-default-fallback-familyrequires fallackTarget attribute");
            }
        }
        return new android.graphics.fonts.FontCustomizationParser.Result(hashMap, list2, list3);
    }

    private static android.graphics.fonts.FontCustomizationParser.Result readFamilies(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        xmlPullParser.require(2, null, "fonts-modification");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("family")) {
                    readFamily(xmlPullParser, str, arrayList, arrayList3, map);
                } else if (name.equals("family-list")) {
                    readFamilyList(xmlPullParser, str, arrayList, map);
                } else if (name.equals("alias")) {
                    arrayList2.add(android.graphics.FontListParser.readAlias(xmlPullParser));
                } else {
                    android.graphics.FontListParser.skip(xmlPullParser);
                }
            }
        }
        return validateAndTransformToResult(arrayList, arrayList3, arrayList2);
    }

    private static void readFamily(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.List<android.text.FontConfig.NamedFamilyList> list, java.util.List<android.text.FontConfig.Customization.LocaleFallback> list2, java.util.Map<java.lang.String, java.io.File> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "customizationType");
        if (attributeValue == null) {
            throw new java.lang.IllegalArgumentException("customizationType must be specified");
        }
        if (attributeValue.equals("new-named-family")) {
            android.text.FontConfig.NamedFamilyList readNamedFamily = android.graphics.FontListParser.readNamedFamily(xmlPullParser, str, map, false);
            if (readNamedFamily != null) {
                list.add(readNamedFamily);
                return;
            }
            return;
        }
        if (attributeValue.equals("new-locale-family")) {
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "lang");
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "operation");
            if (attributeValue3.equals("append")) {
                i = 1;
            } else if (attributeValue3.equals("prepend")) {
                i = 0;
            } else if (attributeValue3.equals("replace")) {
                i = 2;
            } else {
                throw new java.lang.IllegalArgumentException("Unknown operation=" + attributeValue3);
            }
            android.text.FontConfig.FontFamily readFamily = android.graphics.FontListParser.readFamily(xmlPullParser, str, map, false);
            if (com.android.text.flags.Flags.vendorCustomLocaleFallback()) {
                list2.add(new android.text.FontConfig.Customization.LocaleFallback(java.util.Locale.forLanguageTag(attributeValue2), i, readFamily));
                return;
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("Unknown customizationType=" + attributeValue);
    }

    private static void readFamilyList(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.List<android.text.FontConfig.NamedFamilyList> list, java.util.Map<java.lang.String, java.io.File> map) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "customizationType");
        if (attributeValue == null) {
            throw new java.lang.IllegalArgumentException("customizationType must be specified");
        }
        if (attributeValue.equals("new-named-family")) {
            android.text.FontConfig.NamedFamilyList readNamedFamilyList = android.graphics.FontListParser.readNamedFamilyList(xmlPullParser, str, map, false);
            if (readNamedFamilyList != null) {
                list.add(readNamedFamilyList);
                return;
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("Unknown customizationType=" + attributeValue);
    }
}
