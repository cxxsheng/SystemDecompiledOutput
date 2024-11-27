package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedInstrumentationUtils {
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedInstrumentation> parseInstrumentation(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray typedArray;
        com.android.internal.pm.pkg.component.ParsedInstrumentationImpl parsedInstrumentationImpl = new com.android.internal.pm.pkg.component.ParsedInstrumentationImpl();
        java.lang.String str = "<" + xmlResourceParser.getName() + ">";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestInstrumentation);
        try {
            android.content.pm.parsing.result.ParseResult<?> parseComponent = com.android.internal.pm.pkg.component.ParsedComponentUtils.parseComponent(parsedInstrumentationImpl, str, parsingPackage, obtainAttributes, z, parseInput, 7, -1, 1, 0, 6, 2, 8);
            if (parseComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedInstrumentation> error = parseInput.error(parseComponent);
                obtainAttributes.recycle();
                return error;
            }
            typedArray = obtainAttributes;
            try {
                parsedInstrumentationImpl.setTargetPackage(typedArray.getNonResourceString(3)).setTargetProcesses(typedArray.getNonResourceString(9)).setHandleProfiling(typedArray.getBoolean(4, false)).setFunctionalTest(typedArray.getBoolean(5, false));
                typedArray.recycle();
                android.content.pm.parsing.result.ParseResult<?> parseAllMetaData = com.android.internal.pm.pkg.component.ComponentParseUtils.parseAllMetaData(parsingPackage, resources, xmlResourceParser, str, parsedInstrumentationImpl, parseInput);
                if (!parseAllMetaData.isError()) {
                    return parseInput.success((com.android.internal.pm.pkg.component.ParsedInstrumentation) parseAllMetaData.getResult());
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
}
