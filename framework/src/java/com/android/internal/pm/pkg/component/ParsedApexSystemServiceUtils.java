package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedApexSystemServiceUtils {
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedApexSystemService> parseApexSystemService(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl parsedApexSystemServiceImpl = new com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, android.R.styleable.AndroidManifestApexSystemService);
        try {
            java.lang.String string = obtainAttributes.getString(0);
            if (android.text.TextUtils.isEmpty(string)) {
                return parseInput.error("<apex-system-service> does not have name attribute");
            }
            java.lang.String string2 = obtainAttributes.getString(2);
            java.lang.String string3 = obtainAttributes.getString(3);
            java.lang.String string4 = obtainAttributes.getString(4);
            parsedApexSystemServiceImpl.setName(string).setMinSdkVersion(string3).setMaxSdkVersion(string4).setInitOrder(obtainAttributes.getInt(1, 0));
            if (!android.text.TextUtils.isEmpty(string2)) {
                parsedApexSystemServiceImpl.setJarPath(string2);
            }
            return parseInput.success(parsedApexSystemServiceImpl);
        } finally {
            obtainAttributes.recycle();
        }
    }
}
