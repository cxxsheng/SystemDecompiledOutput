package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedPermissionUtils {
    private static final java.lang.String TAG = "PackageParsing";

    /* JADX WARN: Code restructure failed: missing block: B:55:0x017d, code lost:
    
        r1.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> parsePermission(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray typedArray;
        com.android.internal.pm.pkg.component.ParsedPermissionImpl parsedPermissionImpl;
        android.content.pm.parsing.result.ParseResult<?> parseAllMetaData;
        java.lang.String packageName = parsingPackage.getPackageName();
        com.android.internal.pm.pkg.component.ParsedPermissionImpl parsedPermissionImpl2 = new com.android.internal.pm.pkg.component.ParsedPermissionImpl();
        java.lang.String str = "<" + xmlResourceParser.getName() + ">";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermission);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseComponent = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(parsedPermissionImpl2, str, parsingPackage, obtainAttributes, z, parseInput, 9, 5, 1, 0, 7, 2, 10);
            if (parseComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> error = parseInput.error(parseComponent);
                if (obtainAttributes != null) {
                    obtainAttributes.close();
                }
                return error;
            }
            typedArray = obtainAttributes;
            try {
                int i = typedArray.getInt(6, -1);
                if (i != -1 && i < android.os.Build.VERSION.SDK_INT) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> success = parseInput.success(null);
                    if (typedArray != null) {
                        typedArray.close();
                    }
                    return success;
                }
                if (typedArray.hasValue(12)) {
                    if ("android".equals(packageName)) {
                        parsedPermissionImpl2.setBackgroundPermission(typedArray.getNonResourceString(12));
                    } else {
                        android.util.Slog.w("PackageParsing", packageName + " defines a background permission. Only the 'android' package can do that.");
                    }
                }
                parsedPermissionImpl2.setGroup(typedArray.getNonResourceString(4)).setRequestRes(typedArray.getResourceId(13, 0)).setProtectionLevel(typedArray.getInt(3, 0)).setFlags(typedArray.getInt(8, 0));
                int resourceId = typedArray.getResourceId(11, 0);
                if (resourceId != 0) {
                    parsedPermissionImpl = parsedPermissionImpl2;
                    if (resources.getResourceTypeName(resourceId).equals("array")) {
                        java.lang.String[] stringArray = resources.getStringArray(resourceId);
                        if (stringArray != null) {
                            parsedPermissionImpl.setKnownCerts(stringArray);
                        }
                    } else {
                        java.lang.String string = resources.getString(resourceId);
                        if (string != null) {
                            parsedPermissionImpl.setKnownCert(string);
                        }
                    }
                    if (parsedPermissionImpl.getKnownCerts().isEmpty()) {
                        android.util.Slog.w("PackageParsing", packageName + " defines a knownSigner permission but the provided knownCerts resource is null");
                    }
                } else {
                    parsedPermissionImpl = parsedPermissionImpl2;
                    java.lang.String string2 = typedArray.getString(11);
                    if (string2 != null) {
                        parsedPermissionImpl.setKnownCert(string2);
                    }
                }
                if (isRuntime(parsedPermissionImpl) && "android".equals(parsedPermissionImpl.getPackageName())) {
                    if ((parsedPermissionImpl.getFlags() & 4) != 0 && (parsedPermissionImpl.getFlags() & 8) != 0) {
                        throw new java.lang.IllegalStateException("Permission cannot be both soft and hard restricted: " + parsedPermissionImpl.getName());
                    }
                    parsedPermissionImpl.setProtectionLevel(android.content.pm.PermissionInfo.fixProtectionLevel(parsedPermissionImpl.getProtectionLevel()));
                    if ((getProtectionFlags(parsedPermissionImpl) & (-12353)) == 0 && getProtection(parsedPermissionImpl) != 2 && getProtection(parsedPermissionImpl) != 4) {
                        return parseInput.error("<permission> protectionLevel specifies a non-instant, non-appop, non-runtimeOnly flag but is not based on signature or internal type");
                    }
                    parseAllMetaData = com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
                    if (!parseAllMetaData.isError()) {
                        return parseInput.error(parseAllMetaData);
                    }
                    return parseInput.success((com.android.internal.pm.pkg.component.ParsedPermission) parseAllMetaData.getResult());
                }
                parsedPermissionImpl.setFlags(parsedPermissionImpl.getFlags() & (-5));
                parsedPermissionImpl.setFlags(parsedPermissionImpl.getFlags() & (-9));
                parsedPermissionImpl.setProtectionLevel(android.content.pm.PermissionInfo.fixProtectionLevel(parsedPermissionImpl.getProtectionLevel()));
                if ((getProtectionFlags(parsedPermissionImpl) & (-12353)) == 0) {
                }
                parseAllMetaData = com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
                if (!parseAllMetaData.isError()) {
                }
            } catch (java.lang.Throwable th) {
                th = th;
                java.lang.Throwable th2 = th;
                if (typedArray == null) {
                    throw th2;
                }
                try {
                    typedArray.close();
                    throw th2;
                } catch (java.lang.Throwable th3) {
                    th2.addSuppressed(th3);
                    throw th2;
                }
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
            typedArray = obtainAttributes;
        }
    }

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> parsePermissionTree(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.internal.pm.pkg.component.ParsedPermissionImpl parsedPermissionImpl = new com.android.internal.pm.pkg.component.ParsedPermissionImpl();
        java.lang.String str = "<" + xmlResourceParser.getName() + ">";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermissionTree);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseComponent = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(parsedPermissionImpl, str, parsingPackage, obtainAttributes, z, parseInput, 4, -1, 1, 0, 3, 2, 5);
            if (parseComponent.isError()) {
                return parseInput.error(parseComponent);
            }
            obtainAttributes.recycle();
            int indexOf = parsedPermissionImpl.getName().indexOf(46);
            if (indexOf > 0) {
                indexOf = parsedPermissionImpl.getName().indexOf(46, indexOf + 1);
            }
            if (indexOf < 0) {
                return parseInput.error("<permission-tree> name has less than three segments: " + parsedPermissionImpl.getName());
            }
            parsedPermissionImpl.setProtectionLevel(0).setTree(true);
            android.content.pm.parsing.result.ParseResult<?> parseAllMetaData = com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionImpl, parseInput);
            if (!parseAllMetaData.isError()) {
                return parseInput.success((com.android.internal.pm.pkg.component.ParsedPermission) parseAllMetaData.getResult());
            }
            return parseInput.error(parseAllMetaData);
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermissionGroup> parsePermissionGroup(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray typedArray;
        com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl parsedPermissionGroupImpl = new com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl();
        java.lang.String str = "<" + xmlResourceParser.getName() + ">";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermissionGroup);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseComponent = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(parsedPermissionGroupImpl, str, parsingPackage, obtainAttributes, z, parseInput, 7, 4, 1, 0, 5, 2, 8);
            if (parseComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermissionGroup> error = parseInput.error(parseComponent);
                obtainAttributes.recycle();
                return error;
            }
            typedArray = obtainAttributes;
            try {
                parsedPermissionGroupImpl.setRequestDetailRes(typedArray.getResourceId(12, 0)).setBackgroundRequestRes(typedArray.getResourceId(9, 0)).setBackgroundRequestDetailRes(typedArray.getResourceId(10, 0)).setRequestRes(typedArray.getResourceId(11, 0)).setPriority(typedArray.getInt(3, 0)).setFlags(typedArray.getInt(6, 0));
                typedArray.recycle();
                android.content.pm.parsing.result.ParseResult<?> parseAllMetaData = com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedPermissionGroupImpl, parseInput);
                if (!parseAllMetaData.isError()) {
                    return parseInput.success((com.android.internal.pm.pkg.component.ParsedPermissionGroup) parseAllMetaData.getResult());
                }
                return parseInput.error(parseAllMetaData);
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

    public static boolean isRuntime(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        return getProtection(parsedPermission) == 1;
    }

    public static boolean isAppOp(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        return (parsedPermission.getProtectionLevel() & 64) != 0;
    }

    public static int getProtection(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        return parsedPermission.getProtectionLevel() & 15;
    }

    public static int getProtectionFlags(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        return parsedPermission.getProtectionLevel() & (-16);
    }

    public static int calculateFootprint(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        int length = parsedPermission.getName().length();
        java.lang.CharSequence nonLocalizedLabel = parsedPermission.getNonLocalizedLabel();
        if (nonLocalizedLabel != null) {
            return length + nonLocalizedLabel.length();
        }
        return length;
    }

    private static boolean isMalformedDuplicate(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission, com.android.internal.pm.pkg.component.ParsedPermission parsedPermission2) {
        if (parsedPermission == null || parsedPermission2 == null || parsedPermission.isTree() || parsedPermission2.isTree()) {
            return false;
        }
        return (parsedPermission.getProtectionLevel() == parsedPermission2.getProtectionLevel() && java.util.Objects.equals(parsedPermission.getGroup(), parsedPermission2.getGroup())) ? false : true;
    }

    public static boolean declareDuplicatePermission(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        java.util.List<com.android.internal.pm.pkg.component.ParsedPermission> permissions = parsingPackage.getPermissions();
        int size = permissions.size();
        if (size > 0) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
            for (int i = 0; i < size; i++) {
                com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = permissions.get(i);
                java.lang.String name = parsedPermission.getName();
                if (isMalformedDuplicate(parsedPermission, (com.android.internal.pm.pkg.component.ParsedPermission) arrayMap.get(name))) {
                    android.util.EventLog.writeEvent(1397638484, "213323615");
                    return true;
                }
                arrayMap.put(name, parsedPermission);
            }
        }
        return false;
    }
}
