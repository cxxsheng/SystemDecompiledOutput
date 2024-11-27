package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedIntentInfoUtils {
    public static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PackageParsing";

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentInfo(java.lang.String str, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, boolean z2, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        android.content.pm.parsing.result.ParseResult<?> success;
        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl parsedIntentInfoImpl = new com.android.internal.pm.pkg.component.ParsedIntentInfoImpl();
        android.content.IntentFilter intentFilter = parsedIntentInfoImpl.getIntentFilter();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestIntentFilter);
        int i2 = 2;
        try {
            intentFilter.setPriority(obtainAttributes.getInt(2, 0));
            intentFilter.setOrder(obtainAttributes.getInt(3, 0));
            android.util.TypedValue peekValue = obtainAttributes.peekValue(0);
            if (peekValue != null) {
                parsedIntentInfoImpl.setLabelRes(peekValue.resourceId);
                if (peekValue.resourceId == 0) {
                    parsedIntentInfoImpl.setNonLocalizedLabel(peekValue.coerceToString());
                }
            }
            if (com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sUseRoundIcon) {
                parsedIntentInfoImpl.setIcon(obtainAttributes.getResourceId(7, 0));
            }
            if (parsedIntentInfoImpl.getIcon() == 0) {
                parsedIntentInfoImpl.setIcon(obtainAttributes.getResourceId(1, 0));
            }
            if (z2) {
                intentFilter.setAutoVerify(obtainAttributes.getBoolean(6, false));
            }
            obtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                    if (next == i2) {
                        java.lang.String name = xmlResourceParser.getName();
                        switch (name.hashCode()) {
                            case -1422950858:
                                if (name.equals("action")) {
                                    i = 0;
                                    break;
                                }
                                break;
                            case -1194267734:
                                if (name.equals("uri-relative-filter-group")) {
                                    i = 3;
                                    break;
                                }
                                break;
                            case 3076010:
                                if (name.equals("data")) {
                                    i = i2;
                                    break;
                                }
                                break;
                            case 50511102:
                                if (name.equals("category")) {
                                    i = 1;
                                    break;
                                }
                                break;
                        }
                        i = -1;
                        switch (i) {
                            case 0:
                                java.lang.String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue == null) {
                                    success = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue.isEmpty()) {
                                    intentFilter.addAction(attributeValue);
                                    success = parseInput.deferError("No value supplied for <android:name>", android.content.pm.parsing.result.ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                    break;
                                } else {
                                    intentFilter.addAction(attributeValue);
                                    success = parseInput.success(null);
                                    break;
                                }
                            case 1:
                                java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                                if (attributeValue2 == null) {
                                    success = parseInput.error("No value supplied for <android:name>");
                                    break;
                                } else if (attributeValue2.isEmpty()) {
                                    intentFilter.addCategory(attributeValue2);
                                    success = parseInput.deferError("No value supplied for <android:name>", android.content.pm.parsing.result.ParseInput.DeferredError.EMPTY_INTENT_ACTION_CATEGORY);
                                    break;
                                } else {
                                    intentFilter.addCategory(attributeValue2);
                                    success = parseInput.success(null);
                                    break;
                                }
                            case 2:
                                success = parseData(parsedIntentInfoImpl, resources, xmlResourceParser, z, parseInput);
                                break;
                            case 3:
                                if (android.content.pm.Flags.relativeReferenceIntentFilters()) {
                                    success = parseRelRefGroup(parsedIntentInfoImpl, parsingPackage, resources, xmlResourceParser, z, parseInput);
                                    break;
                                } else {
                                    success = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                    break;
                                }
                            default:
                                success = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<intent-filter>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                        }
                        if (success.isError()) {
                            return parseInput.error(success);
                        }
                        i2 = 2;
                    }
                }
            }
            parsedIntentInfoImpl.setHasDefault(intentFilter.hasCategory(android.content.Intent.CATEGORY_DEFAULT));
            return parseInput.success(parsedIntentInfoImpl);
        } catch (java.lang.Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0062 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0023 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseRelRefGroup(com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        char c;
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseRelRefGroupData;
        android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUriRelativeFilterGroup);
        try {
            if (obtainAttributes.getBoolean(0, true)) {
                i = 0;
            } else {
                i = 1;
            }
            android.content.UriRelativeFilterGroup uriRelativeFilterGroup = new android.content.UriRelativeFilterGroup(i);
            obtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                    if (next == 2) {
                        java.lang.String name = xmlResourceParser.getName();
                        switch (name.hashCode()) {
                            case 3076010:
                                if (name.equals("data")) {
                                    c = 0;
                                    switch (c) {
                                        case 0:
                                            parseRelRefGroupData = parseRelRefGroupData(uriRelativeFilterGroup, resources, xmlResourceParser, z, parseInput);
                                            break;
                                        default:
                                            parseRelRefGroupData = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<uri-relative-filter-group>", parsingPackage, xmlResourceParser, parseInput);
                                            break;
                                    }
                                    if (!parseRelRefGroupData.isError()) {
                                        return parseInput.error(parseRelRefGroupData);
                                    }
                                    break;
                                }
                            default:
                                c = 65535;
                                switch (c) {
                                }
                                if (!parseRelRefGroupData.isError()) {
                                }
                                break;
                        }
                    }
                }
            }
            if (uriRelativeFilterGroup.getUriRelativeFilters().size() > 0) {
                intentFilter.addUriRelativeFilterGroup(uriRelativeFilterGroup);
            }
            return parseInput.success(null);
        } catch (java.lang.Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseRelRefGroupData(android.content.UriRelativeFilterGroup uriRelativeFilterGroup, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestData);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(0, 0, nonConfigurationString));
            }
            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString2 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(0, 1, nonConfigurationString2));
            }
            java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(6, 0);
            if (nonConfigurationString3 != null) {
                if (!z) {
                    return parseInput.error("pathPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(0, 2, nonConfigurationString3));
            }
            java.lang.String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString4 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(0, 3, nonConfigurationString4));
            }
            java.lang.String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString5 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(0, 4, nonConfigurationString5));
            }
            java.lang.String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(7, 0);
            if (nonConfigurationString6 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(2, 0, nonConfigurationString6));
            }
            java.lang.String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(21, 0);
            if (nonConfigurationString7 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(2, 1, nonConfigurationString7));
            }
            java.lang.String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(22, 0);
            if (nonConfigurationString8 != null) {
                if (!z) {
                    return parseInput.error("fragmentPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(2, 2, nonConfigurationString8));
            }
            java.lang.String nonConfigurationString9 = obtainAttributes.getNonConfigurationString(23, 0);
            if (nonConfigurationString9 != null) {
                if (!z) {
                    return parseInput.error("fragmentAdvancedPattern not allowed here; fragment must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(2, 3, nonConfigurationString9));
            }
            java.lang.String nonConfigurationString10 = obtainAttributes.getNonConfigurationString(24, 0);
            if (nonConfigurationString10 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(2, 4, nonConfigurationString10));
            }
            java.lang.String nonConfigurationString11 = obtainAttributes.getNonConfigurationString(16, 0);
            if (nonConfigurationString11 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(1, 0, nonConfigurationString11));
            }
            java.lang.String nonConfigurationString12 = obtainAttributes.getNonConfigurationString(17, 0);
            if (nonConfigurationString12 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(1, 1, nonConfigurationString12));
            }
            java.lang.String nonConfigurationString13 = obtainAttributes.getNonConfigurationString(18, 0);
            if (nonConfigurationString13 != null) {
                if (!z) {
                    return parseInput.error("queryPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(1, 2, nonConfigurationString13));
            }
            java.lang.String nonConfigurationString14 = obtainAttributes.getNonConfigurationString(19, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("queryAdvancedPattern not allowed here; query must be literal");
                }
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(1, 3, nonConfigurationString14));
            }
            java.lang.String nonConfigurationString15 = obtainAttributes.getNonConfigurationString(20, 0);
            if (nonConfigurationString15 != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(1, 4, nonConfigurationString15));
            }
            return parseInput.success(null);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfo> parseData(com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestData);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null) {
                intentFilter.addDataType(nonConfigurationString);
            }
            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(11, 0);
            if (nonConfigurationString2 != null) {
                intentFilter.addMimeGroup(nonConfigurationString2);
            }
            java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString3 != null) {
                intentFilter.addDataScheme(nonConfigurationString3);
            }
            java.lang.String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(8, 0);
            if (nonConfigurationString4 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString4, 0);
            }
            java.lang.String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(9, 0);
            if (nonConfigurationString5 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString5, 1);
            }
            java.lang.String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(10, 0);
            if (nonConfigurationString6 != null) {
                if (!z) {
                    return parseInput.error("sspPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString6, 2);
            }
            java.lang.String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(15, 0);
            if (nonConfigurationString7 != null) {
                if (!z) {
                    return parseInput.error("sspAdvancedPattern not allowed here; ssp must be literal");
                }
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString7, 3);
            }
            java.lang.String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(13, 0);
            if (nonConfigurationString8 != null) {
                intentFilter.addDataSchemeSpecificPart(nonConfigurationString8, 4);
            }
            java.lang.String nonConfigurationString9 = obtainAttributes.getNonConfigurationString(2, 0);
            java.lang.String nonConfigurationString10 = obtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString9 != null) {
                intentFilter.addDataAuthority(nonConfigurationString9, nonConfigurationString10);
            }
            java.lang.String nonConfigurationString11 = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString11 != null) {
                intentFilter.addDataPath(nonConfigurationString11, 0);
            }
            java.lang.String nonConfigurationString12 = obtainAttributes.getNonConfigurationString(5, 0);
            if (nonConfigurationString12 != null) {
                intentFilter.addDataPath(nonConfigurationString12, 1);
            }
            java.lang.String nonConfigurationString13 = obtainAttributes.getNonConfigurationString(6, 0);
            if (nonConfigurationString13 != null) {
                if (!z) {
                    return parseInput.error("pathPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(nonConfigurationString13, 2);
            }
            java.lang.String nonConfigurationString14 = obtainAttributes.getNonConfigurationString(14, 0);
            if (nonConfigurationString14 != null) {
                if (!z) {
                    return parseInput.error("pathAdvancedPattern not allowed here; path must be literal");
                }
                intentFilter.addDataPath(nonConfigurationString14, 3);
            }
            java.lang.String nonConfigurationString15 = obtainAttributes.getNonConfigurationString(12, 0);
            if (nonConfigurationString15 != null) {
                intentFilter.addDataPath(nonConfigurationString15, 4);
            }
            return parseInput.success(null);
        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
            return parseInput.error(e.toString());
        } finally {
            obtainAttributes.recycle();
        }
    }
}
