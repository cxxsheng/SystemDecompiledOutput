package android.graphics.fonts;

/* loaded from: classes.dex */
public final class SystemFonts {
    private static final java.lang.String FONTS_XML = "/system/etc/font_fallback.xml";
    private static final java.lang.String LEGACY_FONTS_XML = "/system/etc/fonts.xml";
    private static final java.lang.Object LOCK = new java.lang.Object();
    public static final java.lang.String OEM_FONT_DIR = "/product/fonts/";
    private static final java.lang.String OEM_XML = "/product/etc/fonts_customization.xml";
    public static final java.lang.String SYSTEM_FONT_DIR = "/system/fonts/";
    private static final java.lang.String TAG = "SystemFonts";
    private static java.util.Set<android.graphics.fonts.Font> sAvailableFonts;

    private SystemFonts() {
    }

    public static java.util.Set<android.graphics.fonts.Font> getAvailableFonts() {
        java.util.Set<android.graphics.fonts.Font> set;
        synchronized (LOCK) {
            if (sAvailableFonts == null) {
                sAvailableFonts = android.graphics.fonts.Font.getAvailableFonts();
            }
            set = sAvailableFonts;
        }
        return set;
    }

    public static void resetAvailableFonts() {
        synchronized (LOCK) {
            sAvailableFonts = null;
        }
    }

    private static java.nio.ByteBuffer mmap(java.lang.String str) {
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str);
            try {
                java.nio.channels.FileChannel channel = fileInputStream.getChannel();
                java.nio.MappedByteBuffer map = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                fileInputStream.close();
                return map;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0035 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int resolveVarFamilyType(android.text.FontConfig.FontFamily fontFamily, java.lang.String str) {
        int varTypeAxes;
        java.util.List<android.text.FontConfig.Font> fontList = fontFamily.getFontList();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        for (int i4 = 0; i4 < fontList.size(); i4++) {
            android.text.FontConfig.Font font = fontList.get(i4);
            if (str == null) {
                if (font.getFontFamilyName() != null) {
                    continue;
                }
                varTypeAxes = font.getVarTypeAxes();
                if (varTypeAxes != 0) {
                    return 0;
                }
                if ((varTypeAxes & 1) != 0) {
                    i3++;
                }
                if ((varTypeAxes & 2) != 0) {
                    i++;
                }
                if (font.getStyle().getSlant() == 1) {
                    z = true;
                }
                i2++;
            } else {
                if (!str.equals(font.getFontFamilyName())) {
                    continue;
                }
                varTypeAxes = font.getVarTypeAxes();
                if (varTypeAxes != 0) {
                }
            }
        }
        if (i == 0) {
            if (i2 == 1 && i3 == 1) {
                return 1;
            }
            if (i2 == 2 && i3 == 2 && z) {
                return 3;
            }
        } else if (i == 1 && i3 == 1 && i2 == 1) {
            return 2;
        }
        return 0;
    }

    private static void pushFamilyToFallback(android.text.FontConfig.FontFamily fontFamily, android.util.ArrayMap<java.lang.String, android.graphics.fonts.SystemFonts.NativeFamilyListSet> arrayMap, java.util.Map<java.lang.String, java.nio.ByteBuffer> map) {
        java.lang.String languageTags = fontFamily.getLocaleList().toLanguageTags();
        int variant = fontFamily.getVariant();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        for (android.text.FontConfig.Font font : fontFamily.getFonts()) {
            java.lang.String fontFamilyName = font.getFontFamilyName();
            if (fontFamilyName == null) {
                arrayList.add(font);
            } else {
                java.util.ArrayList arrayList2 = (java.util.ArrayList) arrayMap2.get(fontFamilyName);
                if (arrayList2 == null) {
                    arrayList2 = new java.util.ArrayList();
                    arrayMap2.put(fontFamilyName, arrayList2);
                }
                arrayList2.add(font);
            }
        }
        android.graphics.fonts.FontFamily createFontFamily = arrayList.isEmpty() ? null : createFontFamily(arrayList, languageTags, variant, resolveVarFamilyType(fontFamily, null), false, map);
        for (int i = 0; i < arrayMap.size(); i++) {
            java.lang.String keyAt = arrayMap.keyAt(i);
            android.graphics.fonts.SystemFonts.NativeFamilyListSet valueAt = arrayMap.valueAt(i);
            int identityHashCode = java.lang.System.identityHashCode(fontFamily);
            if (valueAt.seenXmlFamilies.get(identityHashCode, -1) == -1) {
                valueAt.seenXmlFamilies.append(identityHashCode, 1);
                java.util.ArrayList arrayList3 = (java.util.ArrayList) arrayMap2.get(keyAt);
                if (arrayList3 == null) {
                    if (createFontFamily != null) {
                        valueAt.familyList.add(createFontFamily);
                    }
                } else {
                    android.graphics.fonts.FontFamily createFontFamily2 = createFontFamily(arrayList3, languageTags, variant, resolveVarFamilyType(fontFamily, keyAt), false, map);
                    if (createFontFamily2 != null) {
                        valueAt.familyList.add(createFontFamily2);
                    } else if (createFontFamily != null) {
                        valueAt.familyList.add(createFontFamily);
                    }
                }
            }
        }
    }

    private static android.graphics.fonts.FontFamily createFontFamily(java.util.List<android.text.FontConfig.Font> list, java.lang.String str, int i, int i2, boolean z, java.util.Map<java.lang.String, java.nio.ByteBuffer> map) {
        if (list.size() == 0) {
            return null;
        }
        android.graphics.fonts.FontFamily.Builder builder = null;
        for (int i3 = 0; i3 < list.size(); i3++) {
            android.text.FontConfig.Font font = list.get(i3);
            java.lang.String absolutePath = font.getFile().getAbsolutePath();
            java.nio.ByteBuffer byteBuffer = map.get(absolutePath);
            try {
                if (byteBuffer == null) {
                    if (map.containsKey(absolutePath)) {
                        continue;
                    } else {
                        byteBuffer = mmap(absolutePath);
                        map.put(absolutePath, byteBuffer);
                        if (byteBuffer == null) {
                            continue;
                        }
                    }
                }
                android.graphics.fonts.Font build = new android.graphics.fonts.Font.Builder(byteBuffer, new java.io.File(absolutePath), str).setWeight(font.getStyle().getWeight()).setSlant(font.getStyle().getSlant()).setTtcIndex(font.getTtcIndex()).setFontVariationSettings(font.getFontVariationSettings()).build();
                if (builder == null) {
                    builder = new android.graphics.fonts.FontFamily.Builder(build);
                } else {
                    builder.addFont(build);
                }
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        if (builder == null) {
            return null;
        }
        return builder.build(str, i, false, z, i2);
    }

    private static void appendNamedFamilyList(android.text.FontConfig.NamedFamilyList namedFamilyList, android.util.ArrayMap<java.lang.String, java.nio.ByteBuffer> arrayMap, android.util.ArrayMap<java.lang.String, android.graphics.fonts.SystemFonts.NativeFamilyListSet> arrayMap2) {
        java.lang.String name = namedFamilyList.getName();
        android.graphics.fonts.SystemFonts.NativeFamilyListSet nativeFamilyListSet = new android.graphics.fonts.SystemFonts.NativeFamilyListSet();
        java.util.List<android.text.FontConfig.FontFamily> families = namedFamilyList.getFamilies();
        for (int i = 0; i < families.size(); i++) {
            android.text.FontConfig.FontFamily fontFamily = families.get(i);
            android.graphics.fonts.FontFamily createFontFamily = createFontFamily(fontFamily.getFontList(), fontFamily.getLocaleList().toLanguageTags(), fontFamily.getVariant(), resolveVarFamilyType(fontFamily, null), true, arrayMap);
            if (createFontFamily == null) {
                return;
            }
            nativeFamilyListSet.familyList.add(createFontFamily);
            nativeFamilyListSet.seenXmlFamilies.append(java.lang.System.identityHashCode(fontFamily), 1);
        }
        arrayMap2.put(name, nativeFamilyListSet);
    }

    public static android.text.FontConfig getSystemFontConfig(java.util.Map<java.lang.String, java.io.File> map, long j, int i) {
        java.lang.String str;
        if (com.android.text.flags.Flags.newFontsFallbackXml()) {
            str = FONTS_XML;
        } else {
            str = LEGACY_FONTS_XML;
        }
        return getSystemFontConfigInternal(str, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, map, j, i);
    }

    public static android.text.FontConfig getSystemFontConfigForTesting(java.lang.String str, java.util.Map<java.lang.String, java.io.File> map, long j, int i) {
        return getSystemFontConfigInternal(str, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, map, j, i);
    }

    public static android.text.FontConfig getSystemPreinstalledFontConfig() {
        java.lang.String str;
        if (com.android.text.flags.Flags.newFontsFallbackXml()) {
            str = FONTS_XML;
        } else {
            str = LEGACY_FONTS_XML;
        }
        return getSystemFontConfigInternal(str, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    public static android.text.FontConfig getSystemPreinstalledFontConfigFromLegacyXml() {
        return getSystemFontConfigInternal(LEGACY_FONTS_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    static android.text.FontConfig getSystemFontConfigInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.Map<java.lang.String, java.io.File> map, long j, int i) {
        try {
            android.util.Log.i(TAG, "Loading font config from " + str);
            return android.graphics.FontListParser.parse(str, str2, str3, str4, map, j, i);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Failed to open/read system font configurations.", e);
            return new android.text.FontConfig(java.util.Collections.emptyList(), java.util.Collections.emptyList(), java.util.Collections.emptyList(), java.util.Collections.emptyList(), 0L, 0);
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Log.e(TAG, "Failed to parse the system font configuration.", e2);
            return new android.text.FontConfig(java.util.Collections.emptyList(), java.util.Collections.emptyList(), java.util.Collections.emptyList(), java.util.Collections.emptyList(), 0L, 0);
        }
    }

    public static java.util.Map<java.lang.String, android.graphics.fonts.FontFamily[]> buildSystemFallback(android.text.FontConfig fontConfig) {
        return buildSystemFallback(fontConfig, new android.util.ArrayMap());
    }

    private static final class NativeFamilyListSet {
        public java.util.List<android.graphics.fonts.FontFamily> familyList;
        public android.util.SparseIntArray seenXmlFamilies;

        private NativeFamilyListSet() {
            this.familyList = new java.util.ArrayList();
            this.seenXmlFamilies = new android.util.SparseIntArray();
        }
    }

    public static java.util.Map<java.lang.String, android.graphics.fonts.FontFamily[]> buildSystemFallback(android.text.FontConfig fontConfig, android.util.ArrayMap<java.lang.String, java.nio.ByteBuffer> arrayMap) {
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        java.util.List<android.text.FontConfig.Customization.LocaleFallback> localeFallbackCustomizations = fontConfig.getLocaleFallbackCustomizations();
        java.util.List<android.text.FontConfig.NamedFamilyList> namedFamilyLists = fontConfig.getNamedFamilyLists();
        for (int i = 0; i < namedFamilyLists.size(); i++) {
            appendNamedFamilyList(namedFamilyLists.get(i), arrayMap, arrayMap2);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.text.FontConfig.FontFamily> fontFamilies = fontConfig.getFontFamilies();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        for (int i2 = 0; i2 < fontFamilies.size(); i2++) {
            android.text.FontConfig.FontFamily fontFamily = fontFamilies.get(i2);
            arrayList.clear();
            for (int i3 = 0; i3 < localeFallbackCustomizations.size(); i3++) {
                if (sparseIntArray.get(i3, -1) == -1) {
                    android.text.FontConfig.Customization.LocaleFallback localeFallback = localeFallbackCustomizations.get(i3);
                    if (scriptMatch(fontFamily.getLocaleList(), localeFallback.getScript())) {
                        arrayList.add(localeFallback);
                        sparseIntArray.put(i3, 1);
                    }
                }
            }
            if (arrayList.isEmpty()) {
                pushFamilyToFallback(fontFamily, arrayMap2, arrayMap);
            } else {
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    android.text.FontConfig.Customization.LocaleFallback localeFallback2 = (android.text.FontConfig.Customization.LocaleFallback) arrayList.get(i4);
                    if (localeFallback2.getOperation() == 0) {
                        pushFamilyToFallback(localeFallback2.getFamily(), arrayMap2, arrayMap);
                    }
                }
                boolean z = false;
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    android.text.FontConfig.Customization.LocaleFallback localeFallback3 = (android.text.FontConfig.Customization.LocaleFallback) arrayList.get(i5);
                    if (localeFallback3.getOperation() == 2) {
                        pushFamilyToFallback(localeFallback3.getFamily(), arrayMap2, arrayMap);
                        z = true;
                    }
                }
                if (!z) {
                    pushFamilyToFallback(fontFamily, arrayMap2, arrayMap);
                }
                for (int i6 = 0; i6 < arrayList.size(); i6++) {
                    android.text.FontConfig.Customization.LocaleFallback localeFallback4 = (android.text.FontConfig.Customization.LocaleFallback) arrayList.get(i6);
                    if (localeFallback4.getOperation() == 1) {
                        pushFamilyToFallback(localeFallback4.getFamily(), arrayMap2, arrayMap);
                    }
                }
            }
        }
        android.util.ArrayMap arrayMap3 = new android.util.ArrayMap();
        for (int i7 = 0; i7 < arrayMap2.size(); i7++) {
            arrayMap3.put((java.lang.String) arrayMap2.keyAt(i7), (android.graphics.fonts.FontFamily[]) ((android.graphics.fonts.SystemFonts.NativeFamilyListSet) arrayMap2.valueAt(i7)).familyList.toArray(new android.graphics.fonts.FontFamily[0]));
        }
        return arrayMap3;
    }

    public static java.util.Map<java.lang.String, android.graphics.Typeface> buildSystemTypefaces(android.text.FontConfig fontConfig, java.util.Map<java.lang.String, android.graphics.fonts.FontFamily[]> map) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.graphics.Typeface.initSystemDefaultTypefaces(map, fontConfig.getAliases(), arrayMap);
        return arrayMap;
    }

    private static boolean scriptMatch(android.os.LocaleList localeList, java.lang.String str) {
        if (localeList == null || localeList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < localeList.size(); i++) {
            java.util.Locale locale = localeList.get(i);
            if (locale != null) {
                java.lang.String resolveScript = android.text.FontConfig.resolveScript(locale);
                if (resolveScript.equals(str)) {
                    return true;
                }
                if (str.equals("Bopo") && resolveScript.equals("Hanb")) {
                    return true;
                }
                if (str.equals("Hani")) {
                    if (resolveScript.equals("Hanb") || resolveScript.equals("Hans") || resolveScript.equals("Hant") || resolveScript.equals("Kore") || resolveScript.equals("Jpan")) {
                        return true;
                    }
                } else if ((str.equals("Hira") || str.equals("Hrkt") || str.equals("Kana")) && (resolveScript.equals("Jpan") || resolveScript.equals("Hrkt"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
