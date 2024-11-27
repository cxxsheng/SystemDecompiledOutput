package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedActivityUtils {
    public static final boolean LOG_UNSAFE_BROADCASTS = false;
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    public static final java.util.Set<java.lang.String> SAFE_BROADCASTS = new android.util.ArraySet();
    private static final java.lang.String TAG = "PackageParsing";

    static {
        SAFE_BROADCASTS.add(android.content.Intent.ACTION_BOOT_COMPLETED);
    }

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityOrReceiver(java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z, java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray typedArray;
        java.lang.String str2;
        android.content.pm.parsing.result.ParseInput parseInput2;
        com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage2;
        java.lang.String packageName = parsingPackage.getPackageName();
        com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl = new com.android.internal.pm.pkg.component.ParsedActivityImpl();
        boolean equals = "receiver".equals(xmlResourceParser.getName());
        java.lang.String str3 = "<" + xmlResourceParser.getName() + ">";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestActivity);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseMainComponent = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseMainComponent(parsedActivityImpl, str3, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 30, 17, 42, 5, 2, 1, 23, 3, 7, 44, 48, 57);
            if (parseMainComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error = parseInput.error(parseMainComponent);
                obtainAttributes.recycle();
                return error;
            }
            if (equals && parsingPackage.isSaveStateDisallowed()) {
                str2 = packageName;
                if (java.util.Objects.equals(parsedActivityImpl.getProcessName(), str2)) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error2 = parseInput.error("Heavy-weight applications can not have receivers in main process");
                    obtainAttributes.recycle();
                    return error2;
                }
            } else {
                str2 = packageName;
            }
            typedArray = obtainAttributes;
            try {
                parsedActivityImpl.setTheme(typedArray.getResourceId(0, 0)).setUiOptions(typedArray.getInt(26, parsingPackage.getUiOptions()));
                int i2 = 4;
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(64, 19, parsingPackage.isTaskReparentingAllowed(), typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(8, 18, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(4, 11, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(32, 13, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(256, 22, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(2, 10, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(2048, 24, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1, 9, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(128, 21, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1024, 39, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1024, 29, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(16, 12, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(536870912, 65, typedArray));
                if (equals) {
                    parseInput2 = parseInput;
                    parsingPackage2 = parsingPackage;
                    parsedActivityImpl.setLaunchMode(0).setConfigChanges(0).setFlags(parsedActivityImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1073741824, 28, typedArray));
                } else {
                    parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(512, 25, parsingPackage.isHardwareAccelerated(), typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(Integer.MIN_VALUE, 31, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(262144, 64, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(8192, 35, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(4096, 36, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(16384, 37, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(8388608, 51, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(4194304, 41, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(16777216, 52, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(33554432, 56, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(268435456, 60, typedArray));
                    parsedActivityImpl.setPrivateFlags(parsedActivityImpl.getPrivateFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1, 54, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(2, 58, true, typedArray));
                    parsedActivityImpl.setColorMode(typedArray.getInt(49, 0)).setDocumentLaunchMode(typedArray.getInt(33, 0)).setLaunchMode(typedArray.getInt(14, 0)).setLockTaskLaunchMode(typedArray.getInt(38, 0)).setMaxRecents(typedArray.getInt(34, android.app.ActivityTaskManager.getDefaultAppRecentsLimitStatic())).setPersistableMode(typedArray.getInteger(32, 0)).setRequestedVrComponent(typedArray.getString(43)).setRotationAnimation(typedArray.getInt(46, -1)).setSoftInputMode(typedArray.getInt(20, 0)).setConfigChanges(getActivityConfigChanges(typedArray.getInt(16, 0), typedArray.getInt(47, 0)));
                    int i3 = typedArray.getInt(15, -1);
                    parseInput2 = parseInput;
                    parsingPackage2 = parsingPackage;
                    int activityResizeMode = getActivityResizeMode(parsingPackage2, typedArray, i3);
                    parsedActivityImpl.setScreenOrientation(i3).setResizeMode(activityResizeMode);
                    if (typedArray.hasValue(50) && typedArray.getType(50) == 4) {
                        parsedActivityImpl.setMaxAspectRatio(activityResizeMode, typedArray.getFloat(50, 0.0f));
                    }
                    if (typedArray.hasValue(53) && typedArray.getType(53) == 4) {
                        parsedActivityImpl.setMinAspectRatio(activityResizeMode, typedArray.getFloat(53, 0.0f));
                    }
                    if (typedArray.hasValue(62)) {
                        boolean z2 = typedArray.getBoolean(62, false);
                        int privateFlags = parsedActivityImpl.getPrivateFlags();
                        if (!z2) {
                            i2 = 8;
                        }
                        parsedActivityImpl.setPrivateFlags(privateFlags | i2);
                    }
                }
                android.content.pm.parsing.result.ParseResult<java.lang.String> buildTaskAffinityName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildTaskAffinityName(str2, parsingPackage.getTaskAffinity(), typedArray.getNonConfigurationString(8, 1024), parseInput2);
                if (buildTaskAffinityName.isError()) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error3 = parseInput2.error(buildTaskAffinityName);
                    typedArray.recycle();
                    return error3;
                }
                parsedActivityImpl.setTaskAffinity(buildTaskAffinityName.getResult());
                boolean z3 = typedArray.getBoolean(45, false);
                if (z3) {
                    parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
                    parsingPackage2.setVisibleToInstantApps(true);
                }
                java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(63, 0);
                if (nonConfigurationString != null && android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(nonConfigurationString, false, false) != null) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error4 = parseInput2.error("requiredDisplayCategory attribute can only consist of alphanumeric characters, '_', and '.'");
                    typedArray.recycle();
                    return error4;
                }
                parsedActivityImpl.setRequiredDisplayCategory(nonConfigurationString);
                parsedActivityImpl.setRequireContentUriPermissionFromCaller(typedArray.getInt(66, 0));
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityOrAlias = parseActivityOrAlias(parsedActivityImpl, parsingPackage, str3, xmlResourceParser, resources, typedArray, equals, false, z3, parseInput, 27, 4, 6);
                typedArray.recycle();
                return parseActivityOrAlias;
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

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityAlias(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray typedArray;
        com.android.internal.pm.pkg.component.ParsedActivity parsedActivity;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestActivityAlias);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(7, 1024);
            if (nonConfigurationString == null) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error = parseInput.error("<activity-alias> does not specify android:targetActivity");
                obtainAttributes.recycle();
                return error;
            }
            java.lang.String packageName = parsingPackage.getPackageName();
            java.lang.String buildClassName = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, nonConfigurationString);
            if (buildClassName == null) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error2 = parseInput.error("Empty class name in package " + packageName);
                obtainAttributes.recycle();
                return error2;
            }
            java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
            int size = com.android.internal.util.ArrayUtils.size(activities);
            int i = 0;
            while (true) {
                if (i >= size) {
                    parsedActivity = null;
                    break;
                }
                parsedActivity = activities.get(i);
                if (buildClassName.equals(parsedActivity.getName())) {
                    break;
                }
                i++;
            }
            if (parsedActivity == null) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error3 = parseInput.error("<activity-alias> target activity " + buildClassName + " not found in manifest with activities = " + parsingPackage.getActivities() + ", parsedActivities = " + activities);
                obtainAttributes.recycle();
                return error3;
            }
            com.android.internal.pm.pkg.component.ParsedActivityImpl makeAlias = com.android.internal.pm.pkg.component.ParsedActivityImpl.makeAlias(buildClassName, parsedActivity);
            java.lang.String str2 = "<" + xmlResourceParser.getName() + ">";
            typedArray = obtainAttributes;
            try {
                android.content.pm.parsing.result.ParseResult<?> parseMainComponent = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseMainComponent(makeAlias, str2, null, parsingPackage, obtainAttributes, 0, z, str, parseInput, 10, 6, -1, 4, 1, 0, 8, 2, -1, 11, -1, 12);
                if (parseMainComponent.isError()) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> error4 = parseInput.error(parseMainComponent);
                    typedArray.recycle();
                    return error4;
                }
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityOrAlias = parseActivityOrAlias(makeAlias, parsingPackage, str2, xmlResourceParser, resources, typedArray, false, true, (makeAlias.getFlags() & 1048576) != 0, parseInput, 9, 3, 5);
                typedArray.recycle();
                return parseActivityOrAlias;
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

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> parseActivityOrAlias(com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, java.lang.String str, android.content.res.XmlResourceParser xmlResourceParser, android.content.res.Resources resources, android.content.res.TypedArray typedArray, boolean z, boolean z2, boolean z3, android.content.pm.parsing.result.ParseInput parseInput, int i, int i2, int i3) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i4;
        java.lang.String string;
        android.content.pm.parsing.result.ParseResult<?> unknownTag;
        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result;
        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result2;
        java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(i, 1024);
        if (nonConfigurationString != null) {
            java.lang.String buildClassName = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(parsingPackage.getPackageName(), nonConfigurationString);
            if (buildClassName == null) {
                android.util.Log.e("PackageParsing", "Activity " + parsedActivityImpl.getName() + " specified invalid parentActivityName " + nonConfigurationString);
            } else {
                parsedActivityImpl.setParentActivityName(buildClassName);
            }
        }
        java.lang.String nonConfigurationString2 = typedArray.getNonConfigurationString(i2, 0);
        if (z2) {
            parsedActivityImpl.setPermission(nonConfigurationString2);
        } else {
            if (nonConfigurationString2 == null) {
                nonConfigurationString2 = parsingPackage.getPermission();
            }
            parsedActivityImpl.setPermission(nonConfigurationString2);
        }
        if (z2) {
            i4 = 14;
        } else {
            i4 = 61;
        }
        android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseKnownActivityEmbeddingCerts = com.android.internal.pm.pkg.parsing.ParsingUtils.parseKnownActivityEmbeddingCerts(typedArray, resources, i4, parseInput);
        if (parseKnownActivityEmbeddingCerts.isError()) {
            return parseInput.error(parseKnownActivityEmbeddingCerts);
        }
        java.util.Set<java.lang.String> result3 = parseKnownActivityEmbeddingCerts.getResult();
        if (result3 != null) {
            parsedActivityImpl.setKnownActivityEmbeddingCerts(result3);
        }
        boolean hasValue = typedArray.hasValue(i3);
        if (hasValue) {
            parsedActivityImpl.setExported(typedArray.getBoolean(i3, false));
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                if (xmlResourceParser.getName().equals("intent-filter")) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter = parseIntentFilter(parsingPackage, parsedActivityImpl, !z, z3, resources, xmlResourceParser, parseInput);
                    if (parseIntentFilter.isSuccess() && (result2 = parseIntentFilter.getResult()) != null) {
                        parsedActivityImpl.setOrder(java.lang.Math.max(result2.getIntentFilter().getOrder(), parsedActivityImpl.getOrder()));
                        parsedActivityImpl.addIntent(result2);
                    }
                    unknownTag = parseIntentFilter;
                } else if (xmlResourceParser.getName().equals("meta-data")) {
                    unknownTag = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                } else if (xmlResourceParser.getName().equals("property")) {
                    unknownTag = com.android.internal.pm.pkg.component.ParsedComponentUtils.addProperty(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                } else if (!z && !z2 && xmlResourceParser.getName().equals("preferred")) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter2 = parseIntentFilter(parsingPackage, parsedActivityImpl, true, z3, resources, xmlResourceParser, parseInput);
                    if (parseIntentFilter2.isSuccess() && (result = parseIntentFilter2.getResult()) != null) {
                        parsingPackage.addPreferredActivityFilter(parsedActivityImpl.getClassName(), result);
                    }
                    unknownTag = parseIntentFilter2;
                } else if (!z && !z2 && xmlResourceParser.getName().equals(android.media.TtmlUtils.TAG_LAYOUT)) {
                    android.content.pm.parsing.result.ParseResult<android.content.pm.ActivityInfo.WindowLayout> parseActivityWindowLayout = parseActivityWindowLayout(resources, xmlResourceParser, parseInput);
                    if (parseActivityWindowLayout.isSuccess()) {
                        parsedActivityImpl.setWindowLayout(parseActivityWindowLayout.getResult());
                    }
                    unknownTag = parseActivityWindowLayout;
                } else {
                    unknownTag = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                }
                if (unknownTag.isError()) {
                    return parseInput.error(unknownTag);
                }
            }
        }
        if (!z2 && parsedActivityImpl.getLaunchMode() != 4 && parsedActivityImpl.getMetaData().containsKey(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE) && (string = parsedActivityImpl.getMetaData().getString(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_ACTIVITY_LAUNCH_MODE)) != null && string.equals("singleInstancePerTask")) {
            parsedActivityImpl.setLaunchMode(4);
        }
        if (!z2) {
            boolean z4 = typedArray.getBoolean(59, true);
            if (!parsedActivityImpl.getMetaData().getBoolean(com.android.internal.pm.pkg.parsing.ParsingPackageUtils.METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES, true)) {
                z4 = false;
            }
            if (z4) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 65536);
            }
        }
        android.content.pm.parsing.result.ParseResult<android.content.pm.ActivityInfo.WindowLayout> resolveActivityWindowLayout = resolveActivityWindowLayout(parsedActivityImpl, parseInput);
        if (!resolveActivityWindowLayout.isError()) {
            parsedActivityImpl.setWindowLayout(resolveActivityWindowLayout.getResult());
            if (!hasValue) {
                boolean z5 = parsedActivityImpl.getIntents().size() > 0;
                if (z5) {
                    android.content.pm.parsing.result.ParseResult<?> deferError = parseInput.deferError(parsedActivityImpl.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
                    if (deferError.isError()) {
                        return parseInput.error(deferError);
                    }
                }
                parsedActivityImpl.setExported(z5);
            }
            return parseInput.success(parsedActivityImpl);
        }
        return parseInput.error(resolveActivityWindowLayout);
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl, boolean z, boolean z2, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseIntentFilter(parsedActivityImpl, parsingPackage, resources, xmlResourceParser, z2, true, true, z, true, parseInput);
        if (parseIntentFilter.isError()) {
            return parseInput.error(parseIntentFilter);
        }
        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result = parseIntentFilter.getResult();
        if (result != null) {
            android.content.IntentFilter intentFilter = result.getIntentFilter();
            if (intentFilter.isVisibleToInstantApp()) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 1048576);
            }
            if (intentFilter.isImplicitlyVisibleToInstantApp()) {
                parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 2097152);
            }
        }
        return parseInput.success(result);
    }

    private static int getActivityResizeMode(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray, int i) {
        java.lang.Boolean resizeableActivity = parsingPackage.getResizeableActivity();
        if (typedArray.hasValue(40) || resizeableActivity != null) {
            if (!typedArray.getBoolean(40, resizeableActivity != null && resizeableActivity.booleanValue())) {
                return 0;
            }
            return 2;
        }
        if (parsingPackage.isResizeableActivityViaSdkVersion()) {
            return 1;
        }
        if (android.content.pm.ActivityInfo.isFixedOrientationPortrait(i)) {
            return 6;
        }
        if (android.content.pm.ActivityInfo.isFixedOrientationLandscape(i)) {
            return 5;
        }
        if (i == 14) {
            return 7;
        }
        return 4;
    }

    private static android.content.pm.parsing.result.ParseResult<android.content.pm.ActivityInfo.WindowLayout> parseActivityWindowLayout(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.pm.parsing.result.ParseInput parseInput) {
        float f;
        int i;
        float f2;
        int i2;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestLayout);
        try {
            int type = obtainAttributes.getType(3);
            if (type == 6) {
                f = obtainAttributes.getFraction(3, 1, 1, -1.0f);
                i = -1;
            } else if (type != 5) {
                f = -1.0f;
                i = -1;
            } else {
                i = obtainAttributes.getDimensionPixelSize(3, -1);
                f = -1.0f;
            }
            int type2 = obtainAttributes.getType(4);
            if (type2 == 6) {
                f2 = obtainAttributes.getFraction(4, 1, 1, -1.0f);
                i2 = -1;
            } else if (type2 != 5) {
                f2 = -1.0f;
                i2 = -1;
            } else {
                f2 = -1.0f;
                i2 = obtainAttributes.getDimensionPixelSize(4, -1);
            }
            return parseInput.success(new android.content.pm.ActivityInfo.WindowLayout(i, f, i2, f2, obtainAttributes.getInt(0, 17), obtainAttributes.getDimensionPixelSize(1, -1), obtainAttributes.getDimensionPixelSize(2, -1), obtainAttributes.getNonConfigurationString(5, 0)));
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<android.content.pm.ActivityInfo.WindowLayout> resolveActivityWindowLayout(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, android.content.pm.parsing.result.ParseInput parseInput) {
        if (!parsedActivity.getMetaData().containsKey("android.activity_window_layout_affinity")) {
            return parseInput.success(parsedActivity.getWindowLayout());
        }
        if (parsedActivity.getWindowLayout() != null && parsedActivity.getWindowLayout().windowLayoutAffinity != null) {
            return parseInput.success(parsedActivity.getWindowLayout());
        }
        java.lang.String string = parsedActivity.getMetaData().getString("android.activity_window_layout_affinity");
        android.content.pm.ActivityInfo.WindowLayout windowLayout = parsedActivity.getWindowLayout();
        if (windowLayout == null) {
            windowLayout = new android.content.pm.ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1, string);
        } else {
            windowLayout.windowLayoutAffinity = string;
        }
        return parseInput.success(windowLayout);
    }

    public static int getActivityConfigChanges(int i, int i2) {
        return i | ((~i2) & 3);
    }
}
