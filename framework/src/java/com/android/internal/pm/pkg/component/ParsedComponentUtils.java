package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
class ParsedComponentUtils {
    ParsedComponentUtils() {
    }

    static <Component extends com.android.internal.pm.pkg.component.ParsedComponentImpl> android.content.pm.parsing.result.ParseResult<Component> parseComponent(Component component, java.lang.String str, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray, boolean z, android.content.pm.parsing.result.ParseInput parseInput, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(i6, 0);
        if (android.text.TextUtils.isEmpty(nonConfigurationString)) {
            return parseInput.error(str + " does not specify android:name");
        }
        java.lang.String packageName = parsingPackage.getPackageName();
        java.lang.String buildClassName = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, nonConfigurationString);
        if (android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
            return parseInput.error(str + " invalid android:name");
        }
        component.setName(buildClassName).setPackageName(packageName);
        int resourceId = z ? typedArray.getResourceId(i7, 0) : 0;
        if (resourceId != 0) {
            component.setIcon(resourceId).setNonLocalizedLabel(null);
        } else {
            int resourceId2 = typedArray.getResourceId(i3, 0);
            if (resourceId2 != 0) {
                component.setIcon(resourceId2);
                component.setNonLocalizedLabel(null);
            }
        }
        int resourceId3 = typedArray.getResourceId(i5, 0);
        if (resourceId3 != 0) {
            component.setLogo(resourceId3);
        }
        int resourceId4 = typedArray.getResourceId(i, 0);
        if (resourceId4 != 0) {
            component.setBanner(resourceId4);
        }
        if (i2 != -1) {
            component.setDescriptionRes(typedArray.getResourceId(i2, 0));
        }
        android.util.TypedValue peekValue = typedArray.peekValue(i4);
        if (peekValue != null) {
            component.setLabelRes(peekValue.resourceId);
            if (peekValue.resourceId == 0) {
                component.setNonLocalizedLabel(peekValue.coerceToString());
            }
        }
        return parseInput.success(component);
    }

    static android.content.pm.parsing.result.ParseResult<android.os.Bundle> addMetaData(com.android.internal.pm.pkg.component.ParsedComponentImpl parsedComponentImpl, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<meta-data>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        android.content.pm.PackageManager.Property result = parseMetaData.getResult();
        if (result != null) {
            parsedComponentImpl.setMetaData(result.toBundle(parsedComponentImpl.getMetaData()));
        }
        return parseInput.success(parsedComponentImpl.getMetaData());
    }

    static android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> addProperty(com.android.internal.pm.pkg.component.ParsedComponentImpl parsedComponentImpl, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.parseMetaData(parsingPackage, parsedComponentImpl, resources, xmlResourceParser, "<property>", parseInput);
        if (parseMetaData.isError()) {
            return parseInput.error(parseMetaData);
        }
        android.content.pm.PackageManager.Property result = parseMetaData.getResult();
        if (result != null) {
            parsedComponentImpl.addProperty(result);
        }
        return parseInput.success(result);
    }
}
