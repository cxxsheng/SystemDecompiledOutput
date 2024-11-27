package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedServiceUtils {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01f5, code lost:
    
        if (r12 != false) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01ff, code lost:
    
        if (r14.getIntents().size() <= 0) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0201, code lost:
    
        r13 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0205, code lost:
    
        if (r13 == 0) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0207, code lost:
    
        r0 = r33.deferError(r14.getName() + ": Targeting S+ (version 31 and above) requires that an explicit value for android:exported be defined when intent filters are present", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_EXPORTED_FLAG);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0235, code lost:
    
        if (r0.isError() == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x023b, code lost:
    
        return r33.error(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x023c, code lost:
    
        r14.setExported(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0204, code lost:
    
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0243, code lost:
    
        return r33.success(r14);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedService> parseService(java.lang.String[] strArr, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z, java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray typedArray;
        com.android.internal.pm.pkg.component.ParsedServiceImpl parsedServiceImpl;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        android.content.pm.parsing.result.ParseResult<?> parseResult;
        java.lang.String str2;
        java.lang.String packageName = parsingPackage.getPackageName();
        com.android.internal.pm.pkg.component.ParsedServiceImpl parsedServiceImpl2 = new com.android.internal.pm.pkg.component.ParsedServiceImpl();
        java.lang.String name = xmlResourceParser.getName();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestService);
        java.lang.String str3 = name;
        try {
            android.content.pm.parsing.result.ParseResult<?> parseMainComponent = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseMainComponent(parsedServiceImpl2, name, strArr, parsingPackage, obtainAttributes, i, z, str, parseInput, 12, 7, 13, 4, 1, 0, 8, 2, 6, 15, 17, 20);
            if (parseMainComponent.isError()) {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedService> error = parseInput.error(parseMainComponent);
                obtainAttributes.recycle();
                return error;
            }
            typedArray = obtainAttributes;
            try {
                boolean hasValue = typedArray.hasValue(5);
                int i7 = 0;
                if (!hasValue) {
                    parsedServiceImpl = parsedServiceImpl2;
                } else {
                    com.android.internal.pm.pkg.component.ParsedServiceImpl parsedServiceImpl3 = parsedServiceImpl2;
                    parsedServiceImpl3.setExported(typedArray.getBoolean(5, false));
                    parsedServiceImpl = parsedServiceImpl3;
                }
                java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(3, 0);
                if (nonConfigurationString == null) {
                    nonConfigurationString = parsingPackage.getPermission();
                }
                parsedServiceImpl.setPermission(nonConfigurationString);
                int i8 = 1;
                int i9 = 2;
                parsedServiceImpl.setForegroundServiceType(typedArray.getInt(19, 0)).setFlags(parsedServiceImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1, 9, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(2, 10, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(4, 14, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(8, 18, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(16, 21, typedArray) | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(1073741824, 11, typedArray));
                if (android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders()) {
                    parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | com.android.internal.pm.pkg.component.ComponentParseUtils.flag(536870912, 22, typedArray));
                }
                boolean z2 = typedArray.getBoolean(16, false);
                if (z2) {
                    parsedServiceImpl.setFlags(parsedServiceImpl.getFlags() | 1048576);
                    parsingPackage.setVisibleToInstantApps(true);
                }
                typedArray.recycle();
                if (parsingPackage.isSaveStateDisallowed() && java.util.Objects.equals(parsedServiceImpl.getProcessName(), packageName)) {
                    return parseInput.error("Heavy-weight applications can not have services in main process");
                }
                int depth = xmlResourceParser.getDepth();
                while (true) {
                    int next = xmlResourceParser.next();
                    if (next != i8) {
                        if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                            i2 = i8;
                        } else if (next == i9) {
                            java.lang.String name2 = xmlResourceParser.getName();
                            switch (name2.hashCode()) {
                                case -1115949454:
                                    if (name2.equals("meta-data")) {
                                        i3 = i8;
                                        break;
                                    }
                                    i3 = -1;
                                    break;
                                case -1029793847:
                                    if (name2.equals("intent-filter")) {
                                        i3 = i7;
                                        break;
                                    }
                                    i3 = -1;
                                    break;
                                case -993141291:
                                    if (name2.equals("property")) {
                                        i3 = i9;
                                        break;
                                    }
                                    i3 = -1;
                                    break;
                                default:
                                    i3 = -1;
                                    break;
                            }
                            switch (i3) {
                                case 0:
                                    i4 = depth;
                                    i5 = i9;
                                    i6 = i8;
                                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentFilter = com.android.internal.pm.pkg.component.ParsedMainComponentUtils.parseIntentFilter(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, z2, true, false, false, false, parseInput);
                                    if (parseIntentFilter.isSuccess()) {
                                        com.android.internal.pm.pkg.component.ParsedIntentInfoImpl result = parseIntentFilter.getResult();
                                        parsedServiceImpl.setOrder(java.lang.Math.max(result.getIntentFilter().getOrder(), parsedServiceImpl.getOrder()));
                                        parsedServiceImpl.addIntent(result);
                                    }
                                    parseResult = parseIntentFilter;
                                    str2 = str3;
                                    break;
                                case 1:
                                    parseResult = com.android.internal.pm.pkg.component.ParsedComponentUtils.addMetaData(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                                    i4 = depth;
                                    i5 = i9;
                                    i6 = i8;
                                    str2 = str3;
                                    break;
                                case 2:
                                    parseResult = com.android.internal.pm.pkg.component.ParsedComponentUtils.addProperty(parsedServiceImpl, parsingPackage, resources, xmlResourceParser, parseInput);
                                    i4 = depth;
                                    i5 = i9;
                                    i6 = i8;
                                    str2 = str3;
                                    break;
                                default:
                                    i4 = depth;
                                    i5 = i9;
                                    i6 = i8;
                                    str2 = str3;
                                    parseResult = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag(str2, parsingPackage, xmlResourceParser, parseInput);
                                    break;
                            }
                            if (parseResult.isError()) {
                                return parseInput.error(parseResult);
                            }
                            str3 = str2;
                            depth = i4;
                            i9 = i5;
                            i8 = i6;
                            i7 = 0;
                        }
                    } else {
                        i2 = i8;
                    }
                }
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
