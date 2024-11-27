package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedProviderUtils {
    private static final java.lang.String TAG = "PackageParsing";

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> parseProvider(java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z, java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray typedArray;
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        java.lang.String packageName = parsingPackage.getPackageName();
        com.android.internal.pm.pkg.component.ParsedProviderImpl parsedProviderImpl = new com.android.internal.pm.pkg.component.ParsedProviderImpl();
        java.lang.String name = xmlResourceParser.getName();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProvider);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseMainComponent = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseMainComponent(parsedProviderImpl, name, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 17, 14, 18, 6, 1, 0, 15, 2, 8, 19, 21, 23);
            if (parseMainComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> error = parseInput.error(parseMainComponent);
                obtainAttributes.recycle();
                return error;
            }
            typedArray = obtainAttributes;
            try {
                java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(10, 0);
                parsedProviderImpl.setSyncable(typedArray.getBoolean(11, false)).setExported(typedArray.getBoolean(7, targetSdkVersion < 17));
                java.lang.String nonConfigurationString2 = typedArray.getNonConfigurationString(3, 0);
                java.lang.String nonConfigurationString3 = typedArray.getNonConfigurationString(4, 0);
                if (nonConfigurationString3 == null) {
                    nonConfigurationString3 = nonConfigurationString2;
                }
                if (nonConfigurationString3 == null) {
                    parsedProviderImpl.setReadPermission(parsingPackage.getPermission());
                } else {
                    parsedProviderImpl.setReadPermission(nonConfigurationString3);
                }
                java.lang.String nonConfigurationString4 = typedArray.getNonConfigurationString(5, 0);
                if (nonConfigurationString4 != null) {
                    nonConfigurationString2 = nonConfigurationString4;
                }
                if (nonConfigurationString2 == null) {
                    parsedProviderImpl.setWritePermission(parsingPackage.getPermission());
                } else {
                    parsedProviderImpl.setWritePermission(nonConfigurationString2);
                }
                parsedProviderImpl.setGrantUriPermissions(typedArray.getBoolean(13, false)).setForceUriPermissions(typedArray.getBoolean(22, false)).setMultiProcess(typedArray.getBoolean(9, false)).setInitOrder(typedArray.getInt(12, 0)).setFlags(parsedProviderImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1073741824, 16, typedArray));
                if (android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders()) {
                    parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(536870912, 24, typedArray));
                }
                boolean z2 = typedArray.getBoolean(20, false);
                if (z2) {
                    parsedProviderImpl.setFlags(parsedProviderImpl.getFlags() | 1048576);
                    parsingPackage.setVisibleToInstantApps(true);
                }
                typedArray.recycle();
                if (parsingPackage.isSaveStateDisallowed() && java.util.Objects.equals(parsedProviderImpl.getProcessName(), packageName)) {
                    return parseInput.error("Heavy-weight applications can not have providers in main process");
                }
                if (nonConfigurationString == null) {
                    return parseInput.error("<provider> does not include authorities attribute");
                }
                if (nonConfigurationString.length() <= 0) {
                    return parseInput.error("<provider> has empty authorities attribute");
                }
                parsedProviderImpl.setAuthority(nonConfigurationString);
                return parseProviderTags(parsingPackage, name, resources, xmlResourceParser, z2, parsedProviderImpl, parseInput);
            } catch (java.lang.Throwable th) {
                th = th;
                typedArray.recycle();
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            typedArray = obtainAttributes;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0052, code lost:
    
        if (r0.equals("meta-data") != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> parseProviderTags(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, java.lang.String str, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, com.android.internal.pm.pkg.component.ParsedProviderImpl parsedProviderImpl, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<?> parseResult;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            char c = 1;
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next == 2) {
                    java.lang.String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -1814617695:
                            if (name.equals("grant-uri-permission")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1115949454:
                            break;
                        case -1029793847:
                            if (name.equals("intent-filter")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -993141291:
                            if (name.equals("property")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 636171383:
                            if (name.equals("path-permission")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseIntentFilter(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, z, true, false, false, false, parseInput);
                            if (parseIntentFilter.isSuccess()) {
                                com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result = parseIntentFilter.getResult();
                                parsedProviderImpl.setOrder(java.lang.Math.max(result.getIntentFilter().getOrder(), parsedProviderImpl.getOrder()));
                                parsedProviderImpl.addIntent(result);
                            }
                            parseResult = parseIntentFilter;
                            break;
                        case 1:
                            parseResult = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        case 2:
                            parseResult = com.android.internal.pm.pkg.component.ParsedComponentUtils.addProperty(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        case 3:
                            parseResult = parseGrantUriPermission(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        case 4:
                            parseResult = parsePathPermission(parsedProviderImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                            break;
                        default:
                            parseResult = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                            break;
                    }
                    if (parseResult.isError()) {
                        return parseInput.error(parseResult);
                    }
                }
            }
        }
        return parseInput.success(parsedProviderImpl);
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> parseGrantUriPermission(com.android.internal.pm.pkg.component.ParsedProviderImpl parsedProviderImpl, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.os.PatternMatcher patternMatcher;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestGrantUriPermission);
        try {
            java.lang.String name = xmlResourceParser.getName();
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString != null) {
                patternMatcher = new android.os.PatternMatcher(nonConfigurationString, 3);
            } else {
                java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(2, 0);
                if (nonConfigurationString2 != null) {
                    patternMatcher = new android.os.PatternMatcher(nonConfigurationString2, 2);
                } else {
                    java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(1, 0);
                    if (nonConfigurationString3 != null) {
                        patternMatcher = new android.os.PatternMatcher(nonConfigurationString3, 1);
                    } else {
                        java.lang.String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(3, 0);
                        if (nonConfigurationString4 != null) {
                            patternMatcher = new android.os.PatternMatcher(nonConfigurationString4, 4);
                        } else {
                            java.lang.String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(0, 0);
                            if (nonConfigurationString5 == null) {
                                patternMatcher = null;
                            } else {
                                patternMatcher = new android.os.PatternMatcher(nonConfigurationString5, 0);
                            }
                        }
                    }
                }
            }
            if (patternMatcher != null) {
                parsedProviderImpl.addUriPermissionPattern(patternMatcher);
                parsedProviderImpl.setGrantUriPermissions(true);
            } else {
                android.util.Slog.w("PackageParsing", "Unknown element under <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            }
            return parseInput.success(parsedProviderImpl);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProvider> parsePathPermission(com.android.internal.pm.pkg.component.ParsedProviderImpl parsedProviderImpl, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        boolean z;
        android.content.pm.PathPermission pathPermission;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPathPermission);
        try {
            java.lang.String name = xmlResourceParser.getName();
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString2 == null) {
                nonConfigurationString2 = nonConfigurationString;
            }
            java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(2, 0);
            if (nonConfigurationString3 != null) {
                nonConfigurationString = nonConfigurationString3;
            }
            if (nonConfigurationString2 == null) {
                z = false;
            } else {
                nonConfigurationString2 = nonConfigurationString2.intern();
                z = true;
            }
            if (nonConfigurationString != null) {
                nonConfigurationString = nonConfigurationString.intern();
                z = true;
            }
            if (z) {
                java.lang.String nonConfigurationString4 = obtainAttributes.getNonConfigurationString(7, 0);
                if (nonConfigurationString4 == null) {
                    java.lang.String nonConfigurationString5 = obtainAttributes.getNonConfigurationString(5, 0);
                    if (nonConfigurationString5 == null) {
                        java.lang.String nonConfigurationString6 = obtainAttributes.getNonConfigurationString(4, 0);
                        if (nonConfigurationString6 == null) {
                            java.lang.String nonConfigurationString7 = obtainAttributes.getNonConfigurationString(6, 0);
                            if (nonConfigurationString7 != null) {
                                pathPermission = new android.content.pm.PathPermission(nonConfigurationString7, 4, nonConfigurationString2, nonConfigurationString);
                            } else {
                                java.lang.String nonConfigurationString8 = obtainAttributes.getNonConfigurationString(3, 0);
                                if (nonConfigurationString8 == null) {
                                    pathPermission = null;
                                } else {
                                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString8, 0, nonConfigurationString2, nonConfigurationString);
                                }
                            }
                        } else {
                            pathPermission = new android.content.pm.PathPermission(nonConfigurationString6, 1, nonConfigurationString2, nonConfigurationString);
                        }
                    } else {
                        pathPermission = new android.content.pm.PathPermission(nonConfigurationString5, 2, nonConfigurationString2, nonConfigurationString);
                    }
                } else {
                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString4, 3, nonConfigurationString2, nonConfigurationString);
                }
                if (pathPermission != null) {
                    parsedProviderImpl.addPathPermission(pathPermission);
                } else {
                    android.util.Slog.w("PackageParsing", "No path, pathPrefix, or pathPattern for <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                }
                return parseInput.success(parsedProviderImpl);
            }
            android.util.Slog.w("PackageParsing", "No readPermission or writePermission for <path-permission>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            return parseInput.success(parsedProviderImpl);
        } finally {
            obtainAttributes.recycle();
        }
    }
}
