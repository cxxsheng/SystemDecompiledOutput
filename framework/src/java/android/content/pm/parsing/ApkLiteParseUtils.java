package android.content.pm.parsing;

/* loaded from: classes.dex */
public class ApkLiteParseUtils {
    public static final java.lang.String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final java.lang.String ANDROID_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    public static final java.lang.String APK_FILE_EXTENSION = ".apk";
    public static final int DEFAULT_MIN_SDK_VERSION = 1;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    private static final int PARSE_COLLECT_CERTIFICATES = 32;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_IS_SYSTEM_DIR = 16;
    private static final java.lang.String TAG = "ApkLiteParseUtils";
    private static final java.lang.String TAG_APPLICATION = "application";
    private static final java.lang.String TAG_MANIFEST = "manifest";
    private static final java.lang.String TAG_OVERLAY = "overlay";
    private static final java.lang.String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final java.lang.String TAG_PROCESS = "process";
    private static final java.lang.String TAG_PROCESSES = "processes";
    private static final java.lang.String TAG_PROFILEABLE = "profileable";
    private static final java.lang.String TAG_RECEIVER = "receiver";
    private static final java.lang.String TAG_SDK_LIBRARY = "sdk-library";
    private static final java.lang.String TAG_USES_SDK = "uses-sdk";
    private static final java.lang.String TAG_USES_SPLIT = "uses-split";
    private static final java.util.Comparator<java.lang.String> sSplitNameComparator = new android.content.pm.parsing.ApkLiteParseUtils.SplitNameComparator();
    private static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    private static final java.lang.String[] SDK_CODENAMES = android.os.Build.VERSION.ACTIVE_CODENAMES;

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parsePackageLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        if (file.isDirectory()) {
            return parseClusterPackageLite(parseInput, file, i);
        }
        return parseMonolithicPackageLite(parseInput, file, i);
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseMonolithicPackageLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        android.os.Trace.traceBegin(262144L, "parseApkLite");
        try {
            android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite = parseApkLite(parseInput, file, i);
            if (parseApkLite.isError()) {
                return parseInput.error(parseApkLite);
            }
            android.content.pm.parsing.ApkLite result = parseApkLite.getResult();
            return parseInput.success(new android.content.pm.parsing.PackageLite(file.getAbsolutePath(), result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseMonolithicPackageLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) {
        android.os.Trace.traceBegin(262144L, "parseApkLite");
        try {
            android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite = parseApkLite(parseInput, fileDescriptor, str, i);
            if (parseApkLite.isError()) {
                return parseInput.error(parseApkLite);
            }
            android.content.pm.parsing.ApkLite result = parseApkLite.getResult();
            return parseInput.success(new android.content.pm.parsing.PackageLite(str, result.getPath(), result, null, null, null, null, null, null, result.getTargetSdkVersion(), null, null));
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseClusterPackageLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        java.io.File[] listFiles = file.listFiles();
        if (com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
            return parseInput.error(-100, "No packages found in split");
        }
        if (listFiles.length == 1 && listFiles[0].isDirectory()) {
            return parseClusterPackageLite(parseInput, listFiles[0], i);
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.os.Trace.traceBegin(262144L, "parseApkLite");
        try {
            int i2 = 0;
            java.lang.String str = null;
            for (java.io.File file2 : listFiles) {
                if (isApkFile(file2)) {
                    android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite = parseApkLite(parseInput, file2, i);
                    if (parseApkLite.isError()) {
                        return parseInput.error(parseApkLite);
                    }
                    android.content.pm.parsing.ApkLite result = parseApkLite.getResult();
                    if (str == null) {
                        str = result.getPackageName();
                        i2 = result.getVersionCode();
                    } else {
                        if (!str.equals(result.getPackageName())) {
                            return parseInput.error(-101, "Inconsistent package " + result.getPackageName() + " in " + file2 + "; expected " + str);
                        }
                        if (i2 != result.getVersionCode()) {
                            return parseInput.error(-101, "Inconsistent version " + result.getVersionCode() + " in " + file2 + "; expected " + i2);
                        }
                    }
                    android.content.pm.parsing.ApkLite apkLite = (android.content.pm.parsing.ApkLite) arrayMap.put(result.getSplitName(), result);
                    if (apkLite != null) {
                        return parseInput.error(-101, "Split name " + result.getSplitName() + " defined more than once; most recent was " + file2 + ", previous was " + apkLite.getPath());
                    }
                }
            }
            android.content.pm.parsing.ApkLite apkLite2 = (android.content.pm.parsing.ApkLite) arrayMap.remove(null);
            android.os.Trace.traceEnd(262144L);
            return composePackageLiteFromApks(parseInput, file, apkLite2, arrayMap);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> composePackageLiteFromApks(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, android.content.pm.parsing.ApkLite apkLite, android.util.ArrayMap<java.lang.String, android.content.pm.parsing.ApkLite> arrayMap) {
        return composePackageLiteFromApks(parseInput, file, apkLite, arrayMap, false);
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> composePackageLiteFromApks(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, android.content.pm.parsing.ApkLite apkLite, android.util.ArrayMap<java.lang.String, android.content.pm.parsing.ApkLite> arrayMap, boolean z) {
        java.lang.String[] strArr;
        boolean[] zArr;
        java.lang.String[] strArr2;
        java.lang.String[] strArr3;
        java.lang.String[] strArr4;
        int[] iArr;
        java.util.Set[] setArr;
        java.util.Set[] setArr2;
        if (apkLite == null) {
            return parseInput.error(-101, "Missing base APK in " + file);
        }
        int size = com.android.internal.util.ArrayUtils.size(arrayMap);
        if (size <= 0) {
            strArr = null;
            zArr = null;
            strArr2 = null;
            strArr3 = null;
            strArr4 = null;
            iArr = null;
            setArr = null;
            setArr2 = null;
        } else {
            java.util.Set[] setArr3 = new java.util.Set[size];
            java.util.Set[] setArr4 = new java.util.Set[size];
            zArr = new boolean[size];
            strArr2 = new java.lang.String[size];
            strArr3 = new java.lang.String[size];
            strArr4 = new java.lang.String[size];
            iArr = new int[size];
            java.lang.String[] strArr5 = (java.lang.String[]) arrayMap.keySet().toArray(new java.lang.String[size]);
            java.util.Arrays.sort(strArr5, sSplitNameComparator);
            for (int i = 0; i < size; i++) {
                android.content.pm.parsing.ApkLite apkLite2 = arrayMap.get(strArr5[i]);
                setArr3[i] = apkLite2.getRequiredSplitTypes();
                setArr4[i] = apkLite2.getSplitTypes();
                strArr2[i] = apkLite2.getUsesSplitName();
                zArr[i] = apkLite2.isFeatureSplit();
                strArr3[i] = apkLite2.getConfigForSplit();
                strArr4[i] = z ? new java.io.File(file, splitNameToFileName(apkLite2)).getAbsolutePath() : apkLite2.getPath();
                iArr[i] = apkLite2.getRevisionCode();
            }
            setArr = setArr3;
            setArr2 = setArr4;
            strArr = strArr5;
        }
        return parseInput.success(new android.content.pm.parsing.PackageLite(file.getAbsolutePath(), z ? new java.io.File(file, splitNameToFileName(apkLite)).getAbsolutePath() : apkLite.getPath(), apkLite, strArr, zArr, strArr2, strArr3, strArr4, iArr, apkLite.getTargetSdkVersion(), setArr, setArr2));
    }

    public static java.lang.String splitNameToFileName(android.content.pm.parsing.ApkLite apkLite) {
        java.util.Objects.requireNonNull(apkLite);
        return (apkLite.getSplitName() == null ? "base" : "split_" + apkLite.getSplitName()) + ".apk";
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        return parseApkLiteInner(parseInput, file, null, null, i);
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite(android.content.pm.parsing.result.ParseInput parseInput, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) {
        return parseApkLiteInner(parseInput, null, fileDescriptor, str, i);
    }

    private static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLiteInner(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) {
        android.content.res.ApkAssets apkAssets;
        java.lang.Throwable th;
        android.content.res.XmlResourceParser openXml;
        android.content.pm.SigningDetails signingDetails;
        java.lang.String absolutePath = fileDescriptor != null ? str : file.getAbsolutePath();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    apkAssets = fileDescriptor != null ? android.content.res.ApkAssets.loadFromFd(fileDescriptor, str, 0, null) : android.content.res.ApkAssets.loadFromPath(absolutePath);
                    try {
                        try {
                            openXml = apkAssets.openXml("AndroidManifest.xml");
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                        try {
                            try {
                                if ((i & 32) != 0) {
                                    boolean z = (i & 16) != 0;
                                    android.os.Trace.traceBegin(262144L, "collectCertificates");
                                    try {
                                        android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> signingDetails2 = android.content.pm.parsing.FrameworkParsingPackageUtils.getSigningDetails(parseInput, file.getAbsolutePath(), z, false, android.content.pm.SigningDetails.UNKNOWN, 0);
                                        if (signingDetails2.isError()) {
                                            android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> error = parseInput.error(signingDetails2);
                                            libcore.io.IoUtils.closeQuietly(openXml);
                                            if (apkAssets != null) {
                                                try {
                                                    apkAssets.close();
                                                } catch (java.lang.Throwable th3) {
                                                }
                                            }
                                            return error;
                                        }
                                        signingDetails = signingDetails2.getResult();
                                        android.os.Trace.traceEnd(262144L);
                                    } finally {
                                        android.os.Trace.traceEnd(262144L);
                                    }
                                } else {
                                    signingDetails = android.content.pm.SigningDetails.UNKNOWN;
                                }
                                android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite = parseApkLite(parseInput, absolutePath, openXml, signingDetails, i);
                                libcore.io.IoUtils.closeQuietly(openXml);
                                if (apkAssets != null) {
                                    try {
                                        apkAssets.close();
                                    } catch (java.lang.Throwable th4) {
                                    }
                                }
                                return parseApkLite;
                            } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e) {
                                e = e;
                                xmlResourceParser = openXml;
                                android.util.Slog.w(TAG, "Failed to parse " + absolutePath, e);
                                android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> error2 = parseInput.error(-102, "Failed to parse " + absolutePath, e);
                                libcore.io.IoUtils.closeQuietly(xmlResourceParser);
                                if (apkAssets != null) {
                                    try {
                                        apkAssets.close();
                                    } catch (java.lang.Throwable th5) {
                                    }
                                }
                                return error2;
                            }
                        } catch (java.lang.Throwable th6) {
                            th = th6;
                            xmlResourceParser = openXml;
                            libcore.io.IoUtils.closeQuietly(xmlResourceParser);
                            if (apkAssets == null) {
                                throw th;
                            }
                            try {
                                apkAssets.close();
                                throw th;
                            } catch (java.lang.Throwable th7) {
                                throw th;
                            }
                        }
                    } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e2) {
                        e = e2;
                    }
                } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e3) {
                    e = e3;
                    apkAssets = null;
                }
            } catch (java.io.IOException e4) {
                android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> error3 = parseInput.error(-100, "Failed to parse " + absolutePath, e4);
                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
                return error3;
            }
        } catch (java.lang.Throwable th8) {
            th = th8;
            apkAssets = null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.ApkLite> parseApkLite(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.SigningDetails signingDetails, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList;
        java.lang.String str2;
        java.lang.String str3;
        int i2;
        java.util.ArrayList arrayList2;
        boolean z;
        boolean z2;
        java.lang.String str4;
        int i3;
        int i4;
        boolean z3;
        int i5;
        java.util.ArrayList arrayList3;
        char c;
        int i6;
        android.content.pm.parsing.result.ParseResult<android.util.Pair<java.lang.String, java.lang.String>> parsePackageSplitNames = parsePackageSplitNames(parseInput, xmlResourceParser);
        if (parsePackageSplitNames.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        android.util.Pair<java.lang.String, java.lang.String> result = parsePackageSplitNames.getResult();
        android.content.pm.parsing.result.ParseResult<android.util.Pair<java.util.Set<java.lang.String>, java.util.Set<java.lang.String>>> parseRequiredSplitTypes = parseRequiredSplitTypes(parseInput, xmlResourceParser);
        if (parseRequiredSplitTypes.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        android.util.Pair<java.util.Set<java.lang.String>, java.util.Set<java.lang.String>> result2 = parseRequiredSplitTypes.getResult();
        int attributeIntValue = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "installLocation", -1);
        int attributeIntValue2 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "versionCode", 0);
        int attributeIntValue3 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "versionCodeMajor", 0);
        int attributeIntValue4 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "revisionCode", 0);
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false);
        int i7 = 1;
        boolean attributeBooleanValue2 = xmlResourceParser.getAttributeBooleanValue(null, "updatableSystem", true);
        boolean attributeBooleanValue3 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isolatedSplits", false);
        boolean attributeBooleanValue4 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isFeatureSplit", false);
        boolean attributeBooleanValue5 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isSplitRequired", false);
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "configForSplit");
        java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue(null, "emergencyInstaller");
        int depth = xmlResourceParser.getDepth() + 1;
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
        boolean z9 = false;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        boolean z10 = false;
        boolean z11 = false;
        java.lang.String str5 = null;
        java.lang.String str6 = null;
        java.lang.String str7 = null;
        java.lang.String str8 = null;
        boolean z12 = true;
        int i11 = 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == i7) {
                arrayList = arrayList4;
            } else if (next != 3 || xmlResourceParser.getDepth() >= depth) {
                if (next == 3) {
                    i2 = depth;
                    arrayList2 = arrayList4;
                } else if (next == 4) {
                    i2 = depth;
                    arrayList2 = arrayList4;
                } else if (xmlResourceParser.getDepth() != depth) {
                    i2 = depth;
                    arrayList2 = arrayList4;
                } else if ("package-verifier".equals(xmlResourceParser.getName())) {
                    android.content.pm.VerifierInfo parseVerifier = parseVerifier(xmlResourceParser);
                    if (parseVerifier != null) {
                        arrayList4.add(parseVerifier);
                    }
                    i2 = depth;
                    arrayList2 = arrayList4;
                } else if ("application".equals(xmlResourceParser.getName())) {
                    z4 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "debuggable", false);
                    z6 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "multiArch", false);
                    z7 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "use32bitAbi", false);
                    z12 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "extractNativeLibs", true);
                    boolean attributeBooleanValue6 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "useEmbeddedDex", false);
                    i10 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "rollbackDataPolicy", 0);
                    boolean equals = android.Manifest.permission.BIND_DEVICE_ADMIN.equals(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "permission"));
                    int depth2 = xmlResourceParser.getDepth();
                    z8 = attributeBooleanValue6;
                    boolean z13 = z5;
                    while (true) {
                        i5 = depth;
                        int next2 = xmlResourceParser.next();
                        arrayList3 = arrayList4;
                        if (next2 != 1 && (next2 != 3 || xmlResourceParser.getDepth() > depth2)) {
                            if (next2 == 3 || next2 == 4 || xmlResourceParser.getDepth() != depth2 + 1) {
                                depth = i5;
                                arrayList4 = arrayList3;
                                depth2 = depth2;
                            } else {
                                java.lang.String name = xmlResourceParser.getName();
                                switch (name.hashCode()) {
                                    case -1803294168:
                                        if (name.equals(TAG_SDK_LIBRARY)) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -1094759587:
                                        if (name.equals(TAG_PROCESSES)) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -808719889:
                                        if (name.equals("receiver")) {
                                            c = 1;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 178070147:
                                        if (name.equals("profileable")) {
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
                                        i6 = depth2;
                                        z13 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "shell", z13);
                                        break;
                                    case 1:
                                        i6 = depth2;
                                        z10 |= isDeviceAdminReceiver(xmlResourceParser, equals);
                                        break;
                                    case 2:
                                        i6 = depth2;
                                        z11 = true;
                                        break;
                                    case 3:
                                        int depth3 = xmlResourceParser.getDepth();
                                        while (true) {
                                            int next3 = xmlResourceParser.next();
                                            i6 = depth2;
                                            if (next3 != 1 && (next3 != 3 || xmlResourceParser.getDepth() > depth3)) {
                                                if (next3 != 3 && next3 != 4 && xmlResourceParser.getDepth() == depth3 + 1 && xmlResourceParser.getName().equals(TAG_PROCESS) && com.android.internal.pm.pkg.component.flags.Flags.enablePerProcessUseEmbeddedDexAttr()) {
                                                    z8 |= xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "useEmbeddedDex", false);
                                                    depth2 = i6;
                                                } else {
                                                    depth2 = i6;
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        i6 = depth2;
                                        break;
                                }
                                depth = i5;
                                arrayList4 = arrayList3;
                                depth2 = i6;
                            }
                        }
                    }
                    z5 = z13;
                    depth = i5;
                    arrayList4 = arrayList3;
                    i7 = 1;
                } else {
                    i2 = depth;
                    arrayList2 = arrayList4;
                    if ("overlay".equals(xmlResourceParser.getName())) {
                        str7 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSystemPropertyName");
                        str8 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSystemPropertyValue");
                        str5 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "targetPackage");
                        z9 = xmlResourceParser.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "isStatic", false);
                        i8 = xmlResourceParser.getAttributeIntValue("http://schemas.android.com/apk/res/android", "priority", 0);
                        depth = i2;
                        arrayList4 = arrayList2;
                        i7 = 1;
                    } else if ("uses-split".equals(xmlResourceParser.getName())) {
                        if (str6 != null) {
                            android.util.Slog.w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                        } else {
                            str6 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                            if (str6 == null) {
                                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<uses-split> tag requires 'android:name' attribute");
                            }
                            depth = i2;
                            arrayList4 = arrayList2;
                            i7 = 1;
                        }
                    } else if ("uses-sdk".equals(xmlResourceParser.getName())) {
                        java.lang.String attributeValue3 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "minSdkVersion");
                        java.lang.String attributeValue4 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "targetSdkVersion");
                        if (android.text.TextUtils.isEmpty(attributeValue3)) {
                            z = true;
                            z2 = false;
                            i3 = 1;
                            str4 = null;
                        } else {
                            try {
                                i3 = java.lang.Integer.parseInt(attributeValue3);
                                z = true;
                                str4 = null;
                                z2 = true;
                            } catch (java.lang.NumberFormatException e) {
                                z = true;
                                z2 = !android.text.TextUtils.isEmpty(attributeValue3);
                                str4 = attributeValue3;
                                i3 = 1;
                            }
                        }
                        if (!android.text.TextUtils.isEmpty(attributeValue4)) {
                            try {
                                i4 = java.lang.Integer.parseInt(attributeValue4);
                                attributeValue4 = null;
                            } catch (java.lang.NumberFormatException e2) {
                                if (!z2) {
                                    str4 = attributeValue4;
                                }
                                i4 = 0;
                            }
                        } else {
                            i4 = i3;
                            attributeValue4 = str4;
                        }
                        if ((i & 512) == 0) {
                            z3 = false;
                        } else {
                            z3 = z;
                        }
                        android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeTargetSdkVersion = android.content.pm.parsing.FrameworkParsingPackageUtils.computeTargetSdkVersion(i4, attributeValue4, SDK_CODENAMES, parseInput, z3);
                        if (computeTargetSdkVersion.isError()) {
                            return parseInput.error(computeTargetSdkVersion);
                        }
                        i9 = computeTargetSdkVersion.getResult().intValue();
                        android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeMinSdkVersion = android.content.pm.parsing.FrameworkParsingPackageUtils.computeMinSdkVersion(i3, str4, SDK_VERSION, SDK_CODENAMES, parseInput);
                        if (computeMinSdkVersion.isError()) {
                            return parseInput.error(computeMinSdkVersion);
                        }
                        i11 = computeMinSdkVersion.getResult().intValue();
                        depth = i2;
                        arrayList4 = arrayList2;
                        i7 = 1;
                    }
                }
                depth = i2;
                arrayList4 = arrayList2;
                i7 = 1;
            } else {
                arrayList = arrayList4;
            }
        }
        if ((i & 128) == 0) {
            str2 = str7;
            str3 = str8;
            if (!android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(str2, str3)) {
                java.lang.String str9 = "Skipping target and overlay pair " + str5 + " and " + str + ": overlay ignored due to required system property: " + str2 + " with value: " + str3;
                android.util.Slog.i(TAG, str9);
                return parseInput.skip(str9);
            }
        } else {
            str2 = str7;
            str3 = str8;
        }
        return parseInput.success(new android.content.pm.parsing.ApkLite(str, result.first, result.second, attributeBooleanValue4, attributeValue, str6, attributeBooleanValue5, attributeIntValue2, attributeIntValue3, attributeIntValue4, attributeIntValue, arrayList, signingDetails, attributeBooleanValue, z4, z5, z6, z7, z8, z12, attributeBooleanValue3, str5, z9, i8, str2, str3, i11, i9, i10, result2.first, result2.second, z10, z11, attributeBooleanValue2, attributeValue2));
    }

    private static boolean isDeviceAdminReceiver(android.content.res.XmlResourceParser xmlResourceParser, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "permission");
        boolean z2 = false;
        if (!z && !android.Manifest.permission.BIND_DEVICE_ADMIN.equals(attributeValue)) {
            return false;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlResourceParser.getDepth() == depth + 1 && !z2 && "meta-data".equals(xmlResourceParser.getName()) && android.app.admin.DeviceAdminReceiver.DEVICE_ADMIN_META_DATA.equals(xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name"))) {
                z2 = true;
            }
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.parsing.result.ParseResult<android.util.Pair<java.lang.String, java.lang.String>> parsePackageSplitNames(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int next;
        do {
            next = xmlResourceParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No start tag found");
        }
        if (!xmlResourceParser.getName().equals("manifest")) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No <manifest> tag");
        }
        java.lang.String str = null;
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "package");
        if (!"android".equals(attributeValue)) {
            android.content.pm.parsing.result.ParseResult validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, attributeValue, true, true);
            if (validateName.isError()) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest package: " + validateName.getErrorMessage());
            }
        }
        java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue(null, "split");
        if (attributeValue2 != null) {
            if (attributeValue2.length() != 0) {
                android.content.pm.parsing.result.ParseResult validateName2 = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, attributeValue2, false, false);
                if (validateName2.isError()) {
                    return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest split: " + validateName2.getErrorMessage());
                }
            }
            java.lang.String intern = attributeValue.intern();
            if (str != null) {
                str = str.intern();
            }
            return parseInput.success(android.util.Pair.create(intern, str));
        }
        str = attributeValue2;
        java.lang.String intern2 = attributeValue.intern();
        if (str != null) {
        }
        return parseInput.success(android.util.Pair.create(intern2, str));
    }

    public static android.content.pm.parsing.result.ParseResult<android.util.Pair<java.util.Set<java.lang.String>, java.util.Set<java.lang.String>>> parseRequiredSplitTypes(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.XmlResourceParser xmlResourceParser) {
        java.util.Set<java.lang.String> set;
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "requiredSplitTypes");
        java.util.Set<java.lang.String> set2 = null;
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            set = null;
        } else {
            android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> separateAndValidateSplitTypes = separateAndValidateSplitTypes(parseInput, attributeValue);
            if (separateAndValidateSplitTypes.isError()) {
                return parseInput.error(separateAndValidateSplitTypes);
            }
            set = separateAndValidateSplitTypes.getResult();
        }
        java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "splitTypes");
        if (!android.text.TextUtils.isEmpty(attributeValue2)) {
            android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> separateAndValidateSplitTypes2 = separateAndValidateSplitTypes(parseInput, attributeValue2);
            if (separateAndValidateSplitTypes2.isError()) {
                return parseInput.error(separateAndValidateSplitTypes2);
            }
            set2 = separateAndValidateSplitTypes2.getResult();
        }
        return parseInput.success(android.util.Pair.create(set, set2));
    }

    private static android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> separateAndValidateSplitTypes(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (java.lang.String str2 : str.trim().split(",")) {
            java.lang.String trim = str2.trim();
            android.content.pm.parsing.result.ParseResult validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, trim, false, true);
            if (validateName.isError()) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Invalid manifest split types: " + validateName.getErrorMessage());
            }
            if (!arraySet.add(trim)) {
                android.util.Slog.w(TAG, trim + " was defined multiple times");
            }
        }
        return parseInput.success(arraySet);
    }

    public static android.content.pm.VerifierInfo parseVerifier(android.util.AttributeSet attributeSet) {
        java.lang.String attributeValue = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
        java.lang.String attributeValue2 = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "publicKey");
        if (attributeValue == null || attributeValue.length() == 0) {
            android.util.Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        java.security.PublicKey parsePublicKey = android.content.pm.parsing.FrameworkParsingPackageUtils.parsePublicKey(attributeValue2);
        if (parsePublicKey == null) {
            android.util.Slog.i(TAG, "Unable to parse verifier public key for " + attributeValue);
            return null;
        }
        return new android.content.pm.VerifierInfo(attributeValue, parsePublicKey);
    }

    private static class SplitNameComparator implements java.util.Comparator<java.lang.String> {
        private SplitNameComparator() {
        }

        @Override // java.util.Comparator
        public int compare(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                return -1;
            }
            if (str2 == null) {
                return 1;
            }
            return str.compareTo(str2);
        }
    }

    public static boolean isApkFile(java.io.File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(java.lang.String str) {
        return str.endsWith(".apk");
    }
}
