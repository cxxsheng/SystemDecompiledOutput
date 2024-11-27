package android.graphics;

/* loaded from: classes.dex */
public class FontListParser {
    public static final java.lang.String ATTR_FALLBACK_FOR = "fallbackFor";
    public static final java.lang.String ATTR_INDEX = "index";
    private static final java.lang.String ATTR_LANG = "lang";
    private static final java.lang.String ATTR_NAME = "name";
    public static final java.lang.String ATTR_POSTSCRIPT_NAME = "postScriptName";
    public static final java.lang.String ATTR_STYLE = "style";
    public static final java.lang.String ATTR_STYLEVALUE = "stylevalue";
    public static final java.lang.String ATTR_SUPPORTED_AXES = "supportedAxes";
    public static final java.lang.String ATTR_TAG = "tag";
    private static final java.lang.String ATTR_VARIANT = "variant";
    public static final java.lang.String ATTR_WEIGHT = "weight";
    private static final java.util.regex.Pattern FILENAME_WHITESPACE_PATTERN = java.util.regex.Pattern.compile("^[ \\n\\r\\t]+|[ \\n\\r\\t]+$");
    public static final java.lang.String STYLE_ITALIC = "italic";
    public static final java.lang.String STYLE_NORMAL = "normal";
    private static final java.lang.String TAG = "FontListParser";
    public static final java.lang.String TAG_AXIS = "axis";
    private static final java.lang.String TAG_FONT = "font";
    private static final java.lang.String TAG_ITAL = "ital";
    private static final java.lang.String TAG_WGHT = "wght";
    private static final java.lang.String VARIANT_COMPACT = "compact";
    private static final java.lang.String VARIANT_ELEGANT = "elegant";

    public static android.text.FontConfig parse(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
        newPullParser.setInput(inputStream, null);
        newPullParser.nextTag();
        return readFamilies(newPullParser, android.graphics.fonts.SystemFonts.SYSTEM_FONT_DIR, new android.graphics.fonts.FontCustomizationParser.Result(), null, 0L, 0, true);
    }

    public static android.text.FontConfig parse(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.Map<java.lang.String, java.io.File> map, long j, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.graphics.fonts.FontCustomizationParser.Result result;
        android.graphics.fonts.FontCustomizationParser.Result result2;
        if (str3 != null) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str3);
                try {
                    result = android.graphics.fonts.FontCustomizationParser.parse(fileInputStream, str4, map);
                    fileInputStream.close();
                } finally {
                }
            } catch (java.io.IOException e) {
                result = new android.graphics.fonts.FontCustomizationParser.Result();
            }
            result2 = result;
        } else {
            result2 = new android.graphics.fonts.FontCustomizationParser.Result();
        }
        java.io.FileInputStream fileInputStream2 = new java.io.FileInputStream(str);
        try {
            org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
            newPullParser.setInput(fileInputStream2, null);
            newPullParser.nextTag();
            android.text.FontConfig readFamilies = readFamilies(newPullParser, str2, result2, map, j, i, false);
            fileInputStream2.close();
            return readFamilies;
        } catch (java.lang.Throwable th) {
            try {
                fileInputStream2.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static android.text.FontConfig readFamilies(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, android.graphics.fonts.FontCustomizationParser.Result result, java.util.Map<java.lang.String, java.io.File> map, long j, int i, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList(result.getAdditionalAliases());
        java.util.Map<java.lang.String, android.text.FontConfig.NamedFamilyList> additionalNamedFamilies = result.getAdditionalNamedFamilies();
        xmlPullParser.require(2, null, "familyset");
        boolean z2 = true;
        while (true) {
            if (!keepReading(xmlPullParser)) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("family")) {
                    java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue == null) {
                        android.text.FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
                        if (readFamily != null) {
                            arrayList.add(readFamily);
                            z2 = false;
                        }
                    } else {
                        android.text.FontConfig.NamedFamilyList readNamedFamily = readNamedFamily(xmlPullParser, str, map, z);
                        if (readNamedFamily != null) {
                            if (!additionalNamedFamilies.containsKey(attributeValue)) {
                                arrayList2.add(readNamedFamily);
                            }
                            if (z2) {
                                arrayList.addAll(readNamedFamily.getFamilies());
                            }
                            z2 = false;
                        }
                    }
                } else if (name.equals("family-list")) {
                    android.text.FontConfig.NamedFamilyList readNamedFamilyList = readNamedFamilyList(xmlPullParser, str, map, z);
                    if (readNamedFamilyList != null) {
                        if (!additionalNamedFamilies.containsKey(readNamedFamilyList.getName())) {
                            arrayList2.add(readNamedFamilyList);
                        }
                        if (z2) {
                            arrayList.addAll(readNamedFamilyList.getFamilies());
                        }
                        z2 = false;
                    }
                } else if (name.equals("alias")) {
                    arrayList3.add(readAlias(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        arrayList2.addAll(additionalNamedFamilies.values());
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            java.lang.String name2 = ((android.text.FontConfig.NamedFamilyList) arrayList2.get(i3)).getName();
            if (name2 != null) {
                arraySet.add(name2);
            }
        }
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        for (i2 = 0; i2 < arrayList3.size(); i2++) {
            android.text.FontConfig.Alias alias = (android.text.FontConfig.Alias) arrayList3.get(i2);
            if (arraySet.contains(alias.getOriginal())) {
                arrayList4.add(alias);
            }
        }
        return new android.text.FontConfig(arrayList, arrayList4, arrayList2, result.getLocaleFamilyCustomizations(), j, i);
    }

    private static boolean keepReading(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next = xmlPullParser.next();
        return (next == 3 || next == 1) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0058, code lost:
    
        if (r1.equals(android.graphics.FontListParser.VARIANT_ELEGANT) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.text.FontConfig.FontFamily readFamily(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue("", ATTR_LANG);
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "variant");
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "ignore");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            i = 2;
            if (!keepReading(xmlPullParser)) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    android.text.FontConfig.Font readFont = readFont(xmlPullParser, str, map, z);
                    if (readFont != null) {
                        arrayList.add(readFont);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        boolean z2 = true;
        if (attributeValue2 != null) {
            if (attributeValue2.equals(VARIANT_COMPACT)) {
                i = 1;
            }
            if (attributeValue3 != null || (!attributeValue3.equals("true") && !attributeValue3.equals("1"))) {
                z2 = false;
            }
            if (!z2 || arrayList.isEmpty()) {
                return null;
            }
            return new android.text.FontConfig.FontFamily(arrayList, android.os.LocaleList.forLanguageTags(attributeValue), i);
        }
        i = 0;
        if (attributeValue3 != null) {
        }
        z2 = false;
        if (z2) {
        }
        return null;
    }

    private static void throwIfAttributeExists(java.lang.String str, org.xmlpull.v1.XmlPullParser xmlPullParser) {
        if (xmlPullParser.getAttributeValue(null, str) != null) {
            throw new java.lang.IllegalArgumentException(str + " cannot be used in FontFamily inside  family or family-list with name attribute.");
        }
    }

    public static android.text.FontConfig.NamedFamilyList readNamedFamily(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        throwIfAttributeExists(ATTR_LANG, xmlPullParser);
        throwIfAttributeExists("variant", xmlPullParser);
        throwIfAttributeExists("ignore", xmlPullParser);
        android.text.FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
        if (readFamily == null) {
            return null;
        }
        return new android.text.FontConfig.NamedFamilyList(java.util.Collections.singletonList(readFamily), attributeValue);
    }

    public static android.text.FontConfig.NamedFamilyList readNamedFamilyList(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (keepReading(xmlPullParser)) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    throwIfAttributeExists("name", xmlPullParser);
                    throwIfAttributeExists(ATTR_LANG, xmlPullParser);
                    throwIfAttributeExists("variant", xmlPullParser);
                    throwIfAttributeExists("ignore", xmlPullParser);
                    android.text.FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
                    if (readFamily != null) {
                        arrayList.add(readFamily);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new android.text.FontConfig.NamedFamilyList(arrayList, attributeValue);
    }

    private static android.text.FontConfig.Font readFont(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Map<java.lang.String, java.io.File> map, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String fontVariationSettings;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, ATTR_INDEX);
        int parseInt = attributeValue == null ? 0 : java.lang.Integer.parseInt(attributeValue);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "weight");
        int parseInt2 = attributeValue2 == null ? 400 : java.lang.Integer.parseInt(attributeValue2);
        boolean equals = STYLE_ITALIC.equals(xmlPullParser.getAttributeValue(null, "style"));
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, ATTR_FALLBACK_FOR);
        java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, ATTR_POSTSCRIPT_NAME);
        java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, ATTR_SUPPORTED_AXES);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        while (keepReading(xmlPullParser)) {
            if (xmlPullParser.getEventType() == 4) {
                sb.append(xmlPullParser.getText());
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(TAG_AXIS)) {
                    arrayList.add(readAxis(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        java.lang.String replaceAll = FILENAME_WHITESPACE_PATTERN.matcher(sb).replaceAll("");
        if (attributeValue5 == null) {
            i = 0;
        } else {
            i = 0;
            for (java.lang.String str4 : attributeValue5.split(",")) {
                java.lang.String strip = str4.strip();
                if (strip.equals(TAG_WGHT)) {
                    i |= 1;
                } else if (strip.equals(TAG_ITAL)) {
                    i |= 2;
                }
            }
        }
        if (attributeValue4 != null) {
            str2 = attributeValue4;
        } else {
            str2 = replaceAll.substring(0, replaceAll.length() - 4);
        }
        java.lang.String findUpdatedFontFile = findUpdatedFontFile(str2, map);
        if (findUpdatedFontFile != null) {
            str3 = str + replaceAll;
        } else {
            findUpdatedFontFile = str + replaceAll;
            str3 = null;
        }
        if (arrayList.isEmpty()) {
            fontVariationSettings = "";
        } else {
            fontVariationSettings = android.graphics.fonts.FontVariationAxis.toFontVariationSettings((android.graphics.fonts.FontVariationAxis[]) arrayList.toArray(new android.graphics.fonts.FontVariationAxis[0]));
        }
        java.io.File file = new java.io.File(findUpdatedFontFile);
        if (!z && !file.isFile()) {
            return null;
        }
        return new android.text.FontConfig.Font(file, str3 != null ? new java.io.File(str3) : null, str2, new android.graphics.fonts.FontStyle(parseInt2, equals ? 1 : 0), parseInt, fontVariationSettings, attributeValue3, i);
    }

    private static java.lang.String findUpdatedFontFile(java.lang.String str, java.util.Map<java.lang.String, java.io.File> map) {
        java.io.File file;
        if (map != null && (file = map.get(str)) != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    private static android.graphics.fonts.FontVariationAxis readAxis(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "tag");
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTR_STYLEVALUE);
        skip(xmlPullParser);
        return new android.graphics.fonts.FontVariationAxis(attributeValue, java.lang.Float.parseFloat(attributeValue2));
    }

    public static android.text.FontConfig.Alias readAlias(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int parseInt;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "to");
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "weight");
        if (attributeValue3 == null) {
            parseInt = 400;
        } else {
            parseInt = java.lang.Integer.parseInt(attributeValue3);
        }
        skip(xmlPullParser);
        return new android.text.FontConfig.Alias(attributeValue, attributeValue2, parseInt);
    }

    public static void skip(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i = 1;
        while (i > 0) {
            switch (xmlPullParser.next()) {
                case 1:
                    return;
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
            }
        }
    }
}
