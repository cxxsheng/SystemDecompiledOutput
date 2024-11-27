package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ComponentParseUtils {
    public static boolean isImplicitlyExposedIntent(com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo) {
        android.content.IntentFilter intentFilter = parsedIntentInfo.getIntentFilter();
        return intentFilter.hasCategory(android.content.Intent.CATEGORY_BROWSABLE) || intentFilter.hasAction(android.content.Intent.ACTION_SEND) || intentFilter.hasAction(android.content.Intent.ACTION_SENDTO) || intentFilter.hasAction(android.content.Intent.ACTION_SEND_MULTIPLE);
    }

    static <Component extends com.android.internal.pm.pkg.component.ParsedComponentImpl> android.content.pm.parsing.result.ParseResult<Component> parseAllMetaData(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, Component component, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<android.os.Bundle> unknownTag;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                if ("meta-data".equals(xmlResourceParser.getName())) {
                    unknownTag = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(component, parsingPackage, resources, xmlResourceParser, parseInput);
                } else {
                    unknownTag = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(str, parsingPackage, xmlResourceParser, parseInput);
                }
                if (unknownTag.isError()) {
                    return parseInput.error(unknownTag);
                }
            }
        }
        return parseInput.success(component);
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.String> buildProcessName(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, int i, java.lang.String[] strArr, android.content.pm.parsing.result.ParseInput parseInput) {
        if ((i & 2) != 0 && !"system".contentEquals(charSequence)) {
            if (str2 != null) {
                str = str2;
            }
            return parseInput.success(str);
        }
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                java.lang.String str3 = strArr[length];
                if (str3.equals(str) || str3.equals(str2) || str3.contentEquals(charSequence)) {
                    return parseInput.success(str);
                }
            }
        }
        if (charSequence == null || charSequence.length() <= 0) {
            return parseInput.success(str2);
        }
        return parseInput.success(android.text.TextUtils.safeIntern(buildCompoundName(str, charSequence, "process", parseInput).getResult()));
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.String> buildTaskAffinityName(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, android.content.pm.parsing.result.ParseInput parseInput) {
        if (charSequence == null) {
            return parseInput.success(str2);
        }
        if (charSequence.length() <= 0) {
            return parseInput.success(null);
        }
        return buildCompoundName(str, charSequence, "taskAffinity", parseInput);
    }

    public static android.content.pm.parsing.result.ParseResult<java.lang.String> buildCompoundName(java.lang.String str, java.lang.CharSequence charSequence, java.lang.String str2, android.content.pm.parsing.result.ParseInput parseInput) {
        java.lang.String charSequence2 = charSequence.toString();
        char charAt = charSequence2.charAt(0);
        if (str != null && charAt == ':') {
            if (charSequence2.length() < 2) {
                return parseInput.error("Bad " + str2 + " name " + charSequence2 + " in package " + str + ": must be at least two characters");
            }
            android.content.pm.parsing.result.ParseResult validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, charSequence2.substring(1), false, false);
            if (validateName.isError()) {
                return parseInput.error("Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName.getErrorMessage());
            }
            return parseInput.success(str + charSequence2);
        }
        if (!"system".equals(charSequence2)) {
            android.content.pm.parsing.result.ParseResult validateName2 = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, charSequence2, true, false);
            if (validateName2.isError()) {
                return parseInput.error("Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName2.getErrorMessage());
            }
        }
        return parseInput.success(charSequence2);
    }

    public static int flag(int i, int i2, android.content.res.TypedArray typedArray) {
        if (typedArray.getBoolean(i2, false)) {
            return i;
        }
        return 0;
    }

    public static int flag(int i, int i2, boolean z, android.content.res.TypedArray typedArray) {
        if (typedArray.getBoolean(i2, z)) {
            return i;
        }
        return 0;
    }

    public static java.lang.CharSequence getNonLocalizedLabel(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        return parsedComponent.getNonLocalizedLabel();
    }

    public static int getIcon(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        return parsedComponent.getIcon();
    }
}
