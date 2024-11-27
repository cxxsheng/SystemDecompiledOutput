package com.android.server.graphics.fonts;

/* loaded from: classes.dex */
class PersistentSystemFontConfig {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final java.lang.String TAG = "PersistentSystemFontConfig";
    private static final java.lang.String TAG_FAMILY = "family";
    private static final java.lang.String TAG_LAST_MODIFIED_DATE = "lastModifiedDate";
    private static final java.lang.String TAG_ROOT = "fontConfig";
    private static final java.lang.String TAG_UPDATED_FONT_DIR = "updatedFontDir";

    PersistentSystemFontConfig() {
    }

    static class Config {
        public long lastModifiedMillis;
        public final java.util.Set<java.lang.String> updatedFontDirs = new android.util.ArraySet();
        public final java.util.List<android.graphics.fonts.FontUpdateRequest.Family> fontFamilies = new java.util.ArrayList();

        Config() {
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
    
        if (r3.equals(com.android.server.graphics.fonts.PersistentSystemFontConfig.TAG_UPDATED_FONT_DIR) != false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void loadFromXml(@android.annotation.NonNull java.io.InputStream inputStream, @android.annotation.NonNull com.android.server.graphics.fonts.PersistentSystemFontConfig.Config config) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        while (true) {
            int next = resolvePullParser.next();
            char c = 1;
            if (next != 1) {
                if (next == 2) {
                    int depth = resolvePullParser.getDepth();
                    java.lang.String name = resolvePullParser.getName();
                    if (depth == 1) {
                        if (!TAG_ROOT.equals(name)) {
                            android.util.Slog.e(TAG, "Invalid root tag: " + name);
                            return;
                        }
                    } else if (depth == 2) {
                        switch (name.hashCode()) {
                            case -1540845619:
                                if (name.equals(TAG_LAST_MODIFIED_DATE)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1281860764:
                                if (name.equals(TAG_FAMILY)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -23402365:
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                config.lastModifiedMillis = parseLongAttribute(resolvePullParser, ATTR_VALUE, 0L);
                                break;
                            case 1:
                                config.updatedFontDirs.add(getAttribute(resolvePullParser, ATTR_VALUE));
                                break;
                            case 2:
                                config.fontFamilies.add(android.graphics.fonts.FontUpdateRequest.Family.readFromXml(resolvePullParser));
                                break;
                            default:
                                android.util.Slog.w(TAG, "Skipping unknown tag: " + name);
                                break;
                        }
                    }
                }
            } else {
                return;
            }
        }
    }

    public static void writeToXml(@android.annotation.NonNull java.io.OutputStream outputStream, @android.annotation.NonNull com.android.server.graphics.fonts.PersistentSystemFontConfig.Config config) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((java.lang.String) null, true);
        resolveSerializer.startTag((java.lang.String) null, TAG_ROOT);
        resolveSerializer.startTag((java.lang.String) null, TAG_LAST_MODIFIED_DATE);
        resolveSerializer.attribute((java.lang.String) null, ATTR_VALUE, java.lang.Long.toString(config.lastModifiedMillis));
        resolveSerializer.endTag((java.lang.String) null, TAG_LAST_MODIFIED_DATE);
        for (java.lang.String str : config.updatedFontDirs) {
            resolveSerializer.startTag((java.lang.String) null, TAG_UPDATED_FONT_DIR);
            resolveSerializer.attribute((java.lang.String) null, ATTR_VALUE, str);
            resolveSerializer.endTag((java.lang.String) null, TAG_UPDATED_FONT_DIR);
        }
        java.util.List<android.graphics.fonts.FontUpdateRequest.Family> list = config.fontFamilies;
        for (int i = 0; i < list.size(); i++) {
            android.graphics.fonts.FontUpdateRequest.Family family = list.get(i);
            resolveSerializer.startTag((java.lang.String) null, TAG_FAMILY);
            android.graphics.fonts.FontUpdateRequest.Family.writeFamilyToXml(resolveSerializer, family);
            resolveSerializer.endTag((java.lang.String) null, TAG_FAMILY);
        }
        resolveSerializer.endTag((java.lang.String) null, TAG_ROOT);
        resolveSerializer.endDocument();
    }

    private static long parseLongAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, long j) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return j;
        }
        try {
            return java.lang.Long.parseLong(attributeValue);
        } catch (java.lang.NumberFormatException e) {
            return j;
        }
    }

    @android.annotation.NonNull
    private static java.lang.String getAttribute(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, str);
        return attributeValue == null ? "" : attributeValue;
    }
}
