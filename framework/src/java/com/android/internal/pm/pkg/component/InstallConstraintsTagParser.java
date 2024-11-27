package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class InstallConstraintsTagParser {
    private static final java.lang.String TAG_FINGERPRINT_PREFIX = "fingerprint-prefix";

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseInstallConstraints(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.util.Set<java.lang.String> set) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (!set.contains(parsingPackage.getPackageName())) {
            return parseInput.skip("install-constraints cannot be used by this package");
        }
        android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseFingerprintPrefixes = parseFingerprintPrefixes(parseInput, resources, xmlResourceParser);
        if (parseFingerprintPrefixes.isSuccess()) {
            if (validateFingerprintPrefixes(parseFingerprintPrefixes.getResult())) {
                return parseInput.success(parsingPackage);
            }
            return parseInput.skip("Install of this package is restricted on this device; device fingerprint does not start with one of the allowed prefixes");
        }
        return parseInput.skip(parseFingerprintPrefixes.getErrorMessage());
    }

    private static android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseFingerprintPrefixes(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 3) {
                if (arraySet.size() == 0) {
                    return parseInput.error("install-constraints must contain at least one constraint");
                }
                return parseInput.success(arraySet);
            }
            if (next == 2) {
                if (xmlResourceParser.getName().equals(TAG_FINGERPRINT_PREFIX)) {
                    android.content.pm.parsing.result.ParseResult<java.lang.String> readFingerprintPrefixValue = readFingerprintPrefixValue(parseInput, resources, xmlResourceParser);
                    if (readFingerprintPrefixValue.isSuccess()) {
                        arraySet.add(readFingerprintPrefixValue.getResult());
                        int next2 = xmlResourceParser.next();
                        if (next2 != 3) {
                            return parseInput.error("Expected end tag; instead got " + next2);
                        }
                    } else {
                        return parseInput.error(readFingerprintPrefixValue.getErrorMessage());
                    }
                } else {
                    return parseInput.error("Unexpected tag: " + xmlResourceParser.getName());
                }
            }
        }
    }

    private static android.content.pm.parsing.result.ParseResult<java.lang.String> readFingerprintPrefixValue(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestInstallConstraintsFingerprintPrefix);
        try {
            java.lang.String string = obtainAttributes.getString(0);
            if (string == null) {
                return parseInput.error("Failed to specify prefix value");
            }
            return parseInput.success(string);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static boolean validateFingerprintPrefixes(java.util.Set<java.lang.String> set) {
        java.lang.String str = android.os.Build.FINGERPRINT;
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            if (str.startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }
}
