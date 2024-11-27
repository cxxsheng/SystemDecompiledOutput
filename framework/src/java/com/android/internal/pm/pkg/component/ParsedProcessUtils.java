package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedProcessUtils {
    private static android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseDenyPermission(java.util.Set<java.lang.String> set, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestDenyPermission);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(android.Manifest.permission.INTERNET)) {
                set = com.android.internal.util.CollectionUtils.add(set, nonConfigurationString);
            }
            obtainAttributes.recycle();
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (java.lang.Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseAllowPermission(java.util.Set<java.lang.String> set, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestAllowPermission);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (nonConfigurationString != null && nonConfigurationString.equals(android.Manifest.permission.INTERNET)) {
                set = com.android.internal.util.CollectionUtils.remove(set, nonConfigurationString);
            }
            obtainAttributes.recycle();
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            return parseInput.success(set);
        } catch (java.lang.Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedProcess> parseProcess(java.util.Set<java.lang.String> set, java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        boolean z;
        android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseDenyPermission;
        com.android.internal.pm.pkg.component.ParsedProcessImpl parsedProcessImpl = new com.android.internal.pm.pkg.component.ParsedProcessImpl();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProcess);
        if (set != null) {
            try {
                parsedProcessImpl.setDeniedPermissions(new android.util.ArraySet(set));
            } finally {
                obtainAttributes.recycle();
            }
        }
        android.content.pm.parsing.result.ParseResult<java.lang.String> buildProcessName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(parsingPackage.getPackageName(), parsingPackage.getPackageName(), obtainAttributes.getNonConfigurationString(1, 0), i, strArr, parseInput);
        if (buildProcessName.isError()) {
            return parseInput.error(buildProcessName);
        }
        java.lang.String packageName = parsingPackage.getPackageName();
        java.lang.String buildClassName = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, obtainAttributes.getNonConfigurationString(0, 0));
        parsedProcessImpl.setName(buildProcessName.getResult());
        parsedProcessImpl.putAppClassNameForPackage(packageName, buildClassName);
        parsedProcessImpl.setGwpAsanMode(obtainAttributes.getInt(3, -1));
        parsedProcessImpl.setMemtagMode(obtainAttributes.getInt(4, -1));
        if (obtainAttributes.hasValue(5)) {
            parsedProcessImpl.setNativeHeapZeroInitialized(obtainAttributes.getBoolean(5, false) ? 1 : 0);
        }
        if (com.android.internal.pm.pkg.component.flags.Flags.enablePerProcessUseEmbeddedDexAttr()) {
            parsedProcessImpl.setUseEmbeddedDex(obtainAttributes.getBoolean(2, false));
        } else {
            parsedProcessImpl.setUseEmbeddedDex(false);
        }
        obtainAttributes.recycle();
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next != 3 && next != 4) {
                    java.lang.String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -1239165229:
                            if (name.equals("allow-permission")) {
                                z = true;
                                break;
                            }
                            z = -1;
                            break;
                        case 1658008624:
                            if (name.equals("deny-permission")) {
                                z = false;
                                break;
                            }
                            z = -1;
                            break;
                        default:
                            z = -1;
                            break;
                    }
                    switch (z) {
                        case false:
                            parseDenyPermission = parseDenyPermission(parsedProcessImpl.getDeniedPermissions(), resources, xmlResourceParser, parseInput);
                            if (parseDenyPermission.isSuccess()) {
                                parsedProcessImpl.setDeniedPermissions(parseDenyPermission.getResult());
                                break;
                            }
                            break;
                        case true:
                            parseDenyPermission = parseAllowPermission(parsedProcessImpl.getDeniedPermissions(), resources, xmlResourceParser, parseInput);
                            if (parseDenyPermission.isSuccess()) {
                                parsedProcessImpl.setDeniedPermissions(parseDenyPermission.getResult());
                                break;
                            }
                            break;
                        default:
                            parseDenyPermission = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<process>", parsingPackage, xmlResourceParser, parseInput);
                            break;
                    }
                    if (parseDenyPermission.isError()) {
                        return parseInput.error(parseDenyPermission);
                    }
                }
            }
        }
        return parseInput.success(parsedProcessImpl);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0047, code lost:
    
        if (r3.equals("allow-permission") != false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.parsing.result.ParseResult<android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess>> parseProcesses(java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.parsing.result.ParseResult<?> parseDenyPermission;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int depth = xmlResourceParser.getDepth();
        java.util.Set set = null;
        while (true) {
            int next = xmlResourceParser.next();
            char c = 1;
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next != 3 && next != 4) {
                    java.lang.String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -1239165229:
                            break;
                        case -309518737:
                            if (name.equals("process")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1658008624:
                            if (name.equals("deny-permission")) {
                                c = 0;
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
                            parseDenyPermission = parseDenyPermission(set, resources, xmlResourceParser, parseInput);
                            if (parseDenyPermission.isSuccess()) {
                                set = (java.util.Set) parseDenyPermission.getResult();
                                break;
                            }
                            break;
                        case 1:
                            parseDenyPermission = parseAllowPermission(set, resources, xmlResourceParser, parseInput);
                            if (parseDenyPermission.isSuccess()) {
                                set = (java.util.Set) parseDenyPermission.getResult();
                                break;
                            }
                            break;
                        case 2:
                            parseDenyPermission = parseProcess(set, strArr, parsingPackage, resources, xmlResourceParser, i, parseInput);
                            if (parseDenyPermission.isSuccess()) {
                                com.android.internal.pm.pkg.component.ParsedProcess parsedProcess = (com.android.internal.pm.pkg.component.ParsedProcess) parseDenyPermission.getResult();
                                if (arrayMap.put(parsedProcess.getName(), parsedProcess) != null) {
                                    parseDenyPermission = parseInput.error("<process> specified existing name '" + parsedProcess.getName() + "'");
                                    break;
                                }
                            }
                            break;
                        default:
                            parseDenyPermission = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<processes>", parsingPackage, xmlResourceParser, parseInput);
                            break;
                    }
                    if (parseDenyPermission.isError()) {
                        return parseInput.error(parseDenyPermission);
                    }
                }
            }
        }
        return parseInput.success(arrayMap);
    }
}
