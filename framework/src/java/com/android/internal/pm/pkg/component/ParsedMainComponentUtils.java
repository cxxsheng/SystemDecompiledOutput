package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
class ParsedMainComponentUtils {
    private static final java.lang.String TAG = "PackageParsing";

    ParsedMainComponentUtils() {
    }

    static <Component extends com.android.internal.pm.pkg.component.ParsedMainComponentImpl> android.content.pm.parsing.result.ParseResult<Component> parseMainComponent(Component component, java.lang.String str, java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray, int i, boolean z, java.lang.String str2, android.content.pm.parsing.result.ParseInput parseInput, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        android.content.pm.parsing.result.ParseInput parseInput2;
        java.lang.String nonConfigurationString;
        java.lang.String nonResourceString;
        android.content.pm.parsing.result.ParseResult<Component> parseComponent = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(component, str, parsingPackage, typedArray, z, parseInput, i2, i3, i6, i7, i8, i9, i11);
        if (parseComponent.isError()) {
            return parseComponent;
        }
        if (i4 != -1) {
            component.setDirectBootAware(typedArray.getBoolean(i4, false));
            if (component.isDirectBootAware()) {
                parsingPackage.setPartiallyDirectBootAware(true);
            }
        }
        if (i5 != -1) {
            component.setEnabled(typedArray.getBoolean(i5, true));
        }
        if (i10 == -1) {
            parseInput2 = parseInput;
        } else {
            if (parsingPackage.getTargetSdkVersion() >= 8) {
                nonResourceString = typedArray.getNonConfigurationString(i10, 1024);
            } else {
                nonResourceString = typedArray.getNonResourceString(i10);
            }
            android.content.pm.parsing.result.ParseResult<java.lang.String> buildProcessName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(parsingPackage.getPackageName(), parsingPackage.getProcessName(), nonResourceString, i, strArr, parseInput);
            if (buildProcessName.isError()) {
                return parseInput.error(buildProcessName);
            }
            parseInput2 = parseInput;
            component.setProcessName(buildProcessName.getResult());
        }
        if (i12 != -1) {
            component.setSplitName(typedArray.getNonConfigurationString(i12, 0));
        }
        if (str2 != null && component.getSplitName() == null) {
            component.setSplitName(str2);
        }
        if (i13 != -1 && (nonConfigurationString = typedArray.getNonConfigurationString(i13, 0)) != null) {
            component.setAttributionTags(nonConfigurationString.split("\\|"));
        }
        return parseInput2.success(component);
    }

    static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter(com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i;
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentInfo = com.android.internal.pm.pkg.component.ParsedIntentInfoUtils.parseIntentInfo(parsedMainComponent.getName(), parsingPackage, resources, xmlResourceParser, z2, z3, parseInput);
        if (parseIntentInfo.isError()) {
            return parseInput.error(parseIntentInfo);
        }
        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result = parseIntentInfo.getResult();
        android.content.IntentFilter intentFilter = result.getIntentFilter();
        if (intentFilter.countActions() == 0 && z5) {
            android.util.Slog.w("PackageParsing", "No actions in " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
            return parseInput.success(null);
        }
        if (z) {
            i = 1;
        } else if (z4 && com.android.internal.pm.pkg.component.ComponentParseUtils.isImplicitlyExposedIntent(result)) {
            i = 2;
        } else {
            i = 0;
        }
        intentFilter.setVisibilityToInstantApp(i);
        return parseInput.success(parseIntentInfo.getResult());
    }
}
