package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class AdditionalSubtypeUtils {
    private static final java.lang.String ADDITIONAL_SUBTYPES_FILE_NAME = "subtypes.xml";
    private static final java.lang.String ATTR_ICON = "icon";
    private static final java.lang.String ATTR_ID = "id";
    private static final java.lang.String ATTR_IME_SUBTYPE_EXTRA_VALUE = "imeSubtypeExtraValue";
    private static final java.lang.String ATTR_IME_SUBTYPE_ID = "subtypeId";
    private static final java.lang.String ATTR_IME_SUBTYPE_LANGUAGE_TAG = "languageTag";
    private static final java.lang.String ATTR_IME_SUBTYPE_LOCALE = "imeSubtypeLocale";
    private static final java.lang.String ATTR_IME_SUBTYPE_MODE = "imeSubtypeMode";
    private static final java.lang.String ATTR_IS_ASCII_CAPABLE = "isAsciiCapable";
    private static final java.lang.String ATTR_IS_AUXILIARY = "isAuxiliary";
    private static final java.lang.String ATTR_LABEL = "label";
    private static final java.lang.String ATTR_NAME_OVERRIDE = "nameOverride";
    private static final java.lang.String ATTR_NAME_PK_LANGUAGE_TAG = "pkLanguageTag";
    private static final java.lang.String ATTR_NAME_PK_LAYOUT_TYPE = "pkLayoutType";
    private static final java.lang.String INPUT_METHOD_PATH = "inputmethod";
    private static final java.lang.String NODE_IMI = "imi";
    private static final java.lang.String NODE_SUBTYPE = "subtype";
    private static final java.lang.String NODE_SUBTYPES = "subtypes";
    private static final java.lang.String SYSTEM_PATH = "system";
    private static final java.lang.String TAG = "AdditionalSubtypeUtils";

    private AdditionalSubtypeUtils() {
    }

    @android.annotation.NonNull
    private static java.io.File getInputMethodDir(int i) {
        java.io.File userSystemDirectory;
        if (i == 0) {
            userSystemDirectory = new java.io.File(android.os.Environment.getDataDirectory(), "system");
        } else {
            userSystemDirectory = android.os.Environment.getUserSystemDirectory(i);
        }
        return new java.io.File(userSystemDirectory, INPUT_METHOD_PATH);
    }

    @android.annotation.NonNull
    private static android.util.AtomicFile getAdditionalSubtypeFile(java.io.File file) {
        return new android.util.AtomicFile(new java.io.File(file, ADDITIONAL_SUBTYPES_FILE_NAME), "input-subtypes");
    }

    static void save(com.android.server.inputmethod.AdditionalSubtypeMap additionalSubtypeMap, com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        java.io.File inputMethodDir = getInputMethodDir(i);
        if (additionalSubtypeMap.isEmpty()) {
            if (!inputMethodDir.exists()) {
                return;
            }
            android.util.AtomicFile additionalSubtypeFile = getAdditionalSubtypeFile(inputMethodDir);
            if (additionalSubtypeFile.exists()) {
                additionalSubtypeFile.delete();
            }
            if (android.os.FileUtils.listFilesOrEmpty(inputMethodDir).length == 0 && !inputMethodDir.delete()) {
                android.util.Slog.e(TAG, "Failed to delete the empty parent directory " + inputMethodDir);
                return;
            }
            return;
        }
        if (!inputMethodDir.exists() && !inputMethodDir.mkdirs()) {
            android.util.Slog.e(TAG, "Failed to create a parent directory " + inputMethodDir);
            return;
        }
        saveToFile(additionalSubtypeMap, inputMethodMap, getAdditionalSubtypeFile(inputMethodDir));
    }

    @com.android.internal.annotations.VisibleForTesting
    static void saveToFile(com.android.server.inputmethod.AdditionalSubtypeMap additionalSubtypeMap, com.android.server.inputmethod.InputMethodMap inputMethodMap, android.util.AtomicFile atomicFile) {
        boolean z = inputMethodMap != null && inputMethodMap.size() > 0;
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                java.io.FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((java.lang.String) null, NODE_SUBTYPES);
                    for (java.lang.String str : additionalSubtypeMap.keySet()) {
                        if (!z || inputMethodMap.containsKey(str)) {
                            java.util.List<android.view.inputmethod.InputMethodSubtype> list = additionalSubtypeMap.get(str);
                            if (list == null) {
                                android.util.Slog.e(TAG, "Null subtype list for IME " + str);
                            } else {
                                resolveSerializer.startTag((java.lang.String) null, NODE_IMI);
                                resolveSerializer.attribute((java.lang.String) null, ATTR_ID, str);
                                for (android.view.inputmethod.InputMethodSubtype inputMethodSubtype : list) {
                                    resolveSerializer.startTag((java.lang.String) null, NODE_SUBTYPE);
                                    if (inputMethodSubtype.hasSubtypeId()) {
                                        resolveSerializer.attributeInt((java.lang.String) null, ATTR_IME_SUBTYPE_ID, inputMethodSubtype.getSubtypeId());
                                    }
                                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_ICON, inputMethodSubtype.getIconResId());
                                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_LABEL, inputMethodSubtype.getNameResId());
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_NAME_OVERRIDE, inputMethodSubtype.getNameOverride().toString());
                                    android.icu.util.ULocale physicalKeyboardHintLanguageTag = inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
                                    if (physicalKeyboardHintLanguageTag != null) {
                                        resolveSerializer.attribute((java.lang.String) null, ATTR_NAME_PK_LANGUAGE_TAG, physicalKeyboardHintLanguageTag.toLanguageTag());
                                    }
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_NAME_PK_LAYOUT_TYPE, inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_IME_SUBTYPE_LOCALE, inputMethodSubtype.getLocale());
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_IME_SUBTYPE_LANGUAGE_TAG, inputMethodSubtype.getLanguageTag());
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_IME_SUBTYPE_MODE, inputMethodSubtype.getMode());
                                    resolveSerializer.attribute((java.lang.String) null, ATTR_IME_SUBTYPE_EXTRA_VALUE, inputMethodSubtype.getExtraValue());
                                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_IS_AUXILIARY, inputMethodSubtype.isAuxiliary() ? 1 : 0);
                                    resolveSerializer.attributeInt((java.lang.String) null, ATTR_IS_ASCII_CAPABLE, inputMethodSubtype.isAsciiCapable() ? 1 : 0);
                                    resolveSerializer.endTag((java.lang.String) null, NODE_SUBTYPE);
                                }
                                resolveSerializer.endTag((java.lang.String) null, NODE_IMI);
                            }
                        } else {
                            android.util.Slog.w(TAG, "IME uninstalled or not valid.: " + str);
                        }
                    }
                    resolveSerializer.endTag((java.lang.String) null, NODE_SUBTYPES);
                    resolveSerializer.endDocument();
                    atomicFile.finishWrite(startWrite);
                    libcore.io.IoUtils.closeQuietly(startWrite);
                } catch (java.io.IOException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    android.util.Slog.w(TAG, "Error writing subtypes", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    libcore.io.IoUtils.closeQuietly(fileOutputStream);
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    libcore.io.IoUtils.closeQuietly(fileOutputStream);
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e2) {
            e = e2;
        }
    }

    static com.android.server.inputmethod.AdditionalSubtypeMap load(int i) {
        android.util.AtomicFile additionalSubtypeFile = getAdditionalSubtypeFile(getInputMethodDir(i));
        if (additionalSubtypeFile.exists()) {
            return loadFromFile(additionalSubtypeFile);
        }
        return com.android.server.inputmethod.AdditionalSubtypeMap.EMPTY_MAP;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0198, code lost:
    
        r1 = r2;
        r8 = r5;
        r5 = r16;
        r2 = r17;
        r3 = r18;
        r6 = 1;
        r7 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01a7, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0026, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0178, code lost:
    
        r19 = r1;
        r17 = r2;
        r18 = r3;
        r16 = r5;
        r5 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01ac, code lost:
    
        r17 = r2;
        r18 = r3;
        r16 = r5;
        r5 = r8;
        r2 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0057, code lost:
    
        r17 = r2;
        r18 = r3;
        r16 = r5;
        r5 = r8;
        r2 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        if (r11 == r7) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0061, code lost:
    
        r11 = r4.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006b, code lost:
    
        if (com.android.server.inputmethod.AdditionalSubtypeUtils.NODE_IMI.equals(r11) == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006d, code lost:
    
        r9 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_ID);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0077, code lost:
    
        if (android.text.TextUtils.isEmpty(r9) == false) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0079, code lost:
    
        android.util.Slog.w(r1, "Invalid imi id found in subtypes.xml");
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x007f, code lost:
    
        r10 = new java.util.ArrayList();
        r2.put(r9, r10);
        r17 = r2;
        r18 = r3;
        r16 = r5;
        r5 = r8;
        r2 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01b4, code lost:
    
        r1 = r2;
        r8 = r5;
        r5 = r16;
        r2 = r17;
        r3 = r18;
        r6 = 1;
        r7 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0098, code lost:
    
        if (com.android.server.inputmethod.AdditionalSubtypeUtils.NODE_SUBTYPE.equals(r11) == false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x009e, code lost:
    
        if (android.text.TextUtils.isEmpty(r9) != false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a0, code lost:
    
        if (r10 != null) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ad, code lost:
    
        r11 = r4.getAttributeInt(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_ICON);
        r12 = r4.getAttributeInt(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_LABEL);
        r13 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_NAME_OVERRIDE);
        r14 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_NAME_PK_LANGUAGE_TAG);
        r15 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_NAME_PK_LAYOUT_TYPE);
        r6 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IME_SUBTYPE_LOCALE);
        r7 = r4.getAttributeValue(r8, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IME_SUBTYPE_LANGUAGE_TAG);
        r16 = r5;
        r8 = r4.getAttributeValue((java.lang.String) null, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IME_SUBTYPE_MODE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e5, code lost:
    
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e8, code lost:
    
        r5 = r4.getAttributeValue((java.lang.String) null, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IME_SUBTYPE_EXTRA_VALUE);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ee, code lost:
    
        r18 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f1, code lost:
    
        r2 = "1".equals(java.lang.String.valueOf(r4.getAttributeValue((java.lang.String) null, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IS_AUXILIARY)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ff, code lost:
    
        r19 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0102, code lost:
    
        r1 = "1".equals(java.lang.String.valueOf(r4.getAttributeValue((java.lang.String) null, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IS_ASCII_CAPABLE)));
        r3 = new android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0117, code lost:
    
        if (r14 != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0119, code lost:
    
        r12 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0120, code lost:
    
        if (r15 != null) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0122, code lost:
    
        r15 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x012b, code lost:
    
        r1 = r3.setPhysicalKeyboardHint(r12, r15).setSubtypeIconResId(r11).setSubtypeLocale(r6).setLanguageTag(r7).setSubtypeMode(r8).setSubtypeExtraValue(r5).setIsAuxiliary(r2).setIsAsciiCapable(r1);
        r5 = null;
        r2 = r4.getAttributeInt((java.lang.String) null, com.android.server.inputmethod.AdditionalSubtypeUtils.ATTR_IME_SUBTYPE_ID, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0154, code lost:
    
        if (r2 == 0) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0156, code lost:
    
        r1.setSubtypeId(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0159, code lost:
    
        if (r13 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x015b, code lost:
    
        r1.setSubtypeNameOverride(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x015e, code lost:
    
        r10.add(r1.build());
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0165, code lost:
    
        r2 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x011b, code lost:
    
        r12 = new android.icu.util.ULocale(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0125, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0126, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01db, code lost:
    
        if (r18 != null) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01dd, code lost:
    
        r18.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:?, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01e1, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01e3, code lost:
    
        r1.addSuppressed(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01e6, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:?, code lost:
    
        throw r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0168, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0169, code lost:
    
        r19 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x016c, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x016d, code lost:
    
        r19 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x016f, code lost:
    
        r18 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0172, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0173, code lost:
    
        r19 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a2, code lost:
    
        r19 = r1;
        r17 = r2;
        r18 = r3;
        r16 = r5;
        r5 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0192, code lost:
    
        r2 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0194, code lost:
    
        android.util.Slog.w(r2, "IME uninstalled or not valid.: " + r9);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[Catch: IOException | NumberFormatException | XmlPullParserException -> 0x01cb, IOException | NumberFormatException | XmlPullParserException -> 0x01cb, IOException | NumberFormatException | XmlPullParserException -> 0x01cb, SYNTHETIC, TRY_LEAVE, TryCatch #7 {IOException | NumberFormatException | XmlPullParserException -> 0x01cb, blocks: (B:23:0x01c7, B:82:0x01e6, B:82:0x01e6, B:82:0x01e6, B:81:0x01e3, B:81:0x01e3, B:81:0x01e3), top: B:2:0x0009 }] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.util.ArrayMap] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.util.ArrayMap] */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static com.android.server.inputmethod.AdditionalSubtypeMap loadFromFile(android.util.AtomicFile atomicFile) {
        android.util.ArrayMap arrayMap;
        java.io.FileInputStream openRead;
        java.io.FileInputStream fileInputStream;
        java.lang.String str = TAG;
        ?? arrayMap2 = new android.util.ArrayMap();
        try {
            try {
                openRead = atomicFile.openRead();
            } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
                e = e;
                arrayMap = arrayMap2;
                arrayMap2 = TAG;
                android.util.Slog.w((java.lang.String) arrayMap2, "Error reading subtypes", e);
                return com.android.server.inputmethod.AdditionalSubtypeMap.of(arrayMap);
            }
        } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
            android.util.Slog.w((java.lang.String) arrayMap2, "Error reading subtypes", e);
            return com.android.server.inputmethod.AdditionalSubtypeMap.of(arrayMap);
        }
        try {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
            int next = resolvePullParser.next();
            while (true) {
                int i = 1;
                int i2 = 2;
                if (next == 2 || next == 1) {
                    break;
                }
                next = resolvePullParser.next();
            }
            try {
                if (!NODE_SUBTYPES.equals(resolvePullParser.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Xml doesn't start with subtypes");
                }
                int depth = resolvePullParser.getDepth();
                java.lang.String str2 = null;
                java.lang.String str3 = null;
                java.util.ArrayList arrayList = null;
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 3 && resolvePullParser.getDepth() <= depth) {
                        arrayMap = arrayMap2;
                        fileInputStream = openRead;
                        break;
                    }
                    arrayMap = arrayMap2;
                    fileInputStream = openRead;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return com.android.server.inputmethod.AdditionalSubtypeMap.of(arrayMap);
            } catch (java.lang.Throwable th) {
                th = th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            java.io.FileInputStream fileInputStream2 = openRead;
        }
    }
}
