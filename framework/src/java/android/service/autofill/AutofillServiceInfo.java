package android.service.autofill;

/* loaded from: classes3.dex */
public final class AutofillServiceInfo {
    private static final android.content.ComponentName CREDMAN_SERVICE_COMPONENT_NAME = new android.content.ComponentName("com.android.credentialmanager", "com.android.credentialmanager.autofill.CredentialAutofillService");
    private static final java.lang.String TAG = "AutofillServiceInfo";
    private static final java.lang.String TAG_AUTOFILL_SERVICE = "autofill-service";
    private static final java.lang.String TAG_COMPATIBILITY_PACKAGE = "compatibility-package";
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mCompatibilityPackages;
    private final boolean mInlineSuggestionsEnabled;
    private final java.lang.String mPasswordsActivity;
    private final android.content.pm.ServiceInfo mServiceInfo;
    private final java.lang.String mSettingsActivity;

    private static android.content.pm.ServiceInfo getServiceInfoOrThrow(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (android.os.RemoteException e) {
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
    }

    public AutofillServiceInfo(android.content.Context context, android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(componentName, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ef A[Catch: NameNotFoundException | IOException | XmlPullParserException -> 0x00f3, TryCatch #2 {NameNotFoundException | IOException | XmlPullParserException -> 0x00f3, blocks: (B:37:0x00d7, B:38:0x00da, B:43:0x00ef, B:44:0x00f2), top: B:26:0x00be }] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AutofillServiceInfo(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        ?? r4;
        java.lang.String str;
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap;
        java.lang.String str2;
        android.content.res.TypedArray typedArray;
        java.lang.Object obj;
        java.lang.String string;
        java.lang.String string2;
        if (!android.Manifest.permission.BIND_AUTOFILL_SERVICE.equals(serviceInfo.permission)) {
            if (android.Manifest.permission.BIND_AUTOFILL.equals(serviceInfo.permission)) {
                android.util.Log.w(TAG, "AutofillService from '" + serviceInfo.packageName + "' uses unsupported permission " + android.Manifest.permission.BIND_AUTOFILL + ". It works for now, but might not be supported on future releases");
                new com.android.internal.logging.MetricsLogger().write(new android.metrics.LogMaker(com.android.internal.logging.nano.MetricsProto.MetricsEvent.AUTOFILL_INVALID_PERMISSION).setPackageName(serviceInfo.packageName));
            } else {
                android.util.Log.w(TAG, "AutofillService from '" + serviceInfo.packageName + "' does not require permission " + android.Manifest.permission.BIND_AUTOFILL_SERVICE);
                throw new java.lang.SecurityException("Service does not require permission android.permission.BIND_AUTOFILL_SERVICE");
            }
        }
        this.mServiceInfo = serviceInfo;
        android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(context.getPackageManager(), android.service.autofill.AutofillService.SERVICE_META_DATA);
        boolean z = false;
        java.lang.String str3 = null;
        if (loadXmlMetaData == null) {
            this.mSettingsActivity = null;
            this.mPasswordsActivity = null;
            this.mCompatibilityPackages = null;
            this.mInlineSuggestionsEnabled = false;
            return;
        }
        try {
            android.content.res.Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo);
            int i = 0;
            while (true) {
                r4 = 2;
                if (i == 1 || i == 2) {
                    break;
                } else {
                    i = loadXmlMetaData.next();
                }
            }
            str = loadXmlMetaData.getName();
            if (TAG_AUTOFILL_SERVICE.equals(str)) {
                try {
                    try {
                        typedArray = resourcesForApplication.obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.AutofillService);
                    } catch (java.lang.Throwable th) {
                        th = th;
                        typedArray = null;
                        obj = null;
                    }
                    try {
                        string = typedArray.getString(0);
                        try {
                            string2 = typedArray.getString(2);
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        obj = null;
                        if (typedArray != null) {
                        }
                        throw th;
                    }
                    try {
                        z = typedArray.getBoolean(1, false);
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        arrayMap = parseCompatibilityPackages(loadXmlMetaData, resourcesForApplication);
                        str3 = string;
                        str2 = string2;
                    } catch (java.lang.Throwable th4) {
                        th = th4;
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                    android.util.Log.e(TAG, "Error parsing auto fill service meta-data", e);
                    arrayMap = null;
                    str3 = str;
                    str2 = r4;
                    this.mSettingsActivity = str3;
                    this.mPasswordsActivity = str2;
                    this.mCompatibilityPackages = arrayMap;
                    this.mInlineSuggestionsEnabled = z;
                }
            } else {
                android.util.Log.e(TAG, "Meta-data does not start with autofill-service tag");
                arrayMap = null;
                str2 = null;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
            r4 = 0;
            str = null;
        }
        this.mSettingsActivity = str3;
        this.mPasswordsActivity = str2;
        this.mCompatibilityPackages = arrayMap;
        this.mInlineSuggestionsEnabled = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0092, code lost:
    
        if (r4 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ad, code lost:
    
        if (r4 == null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x005b, code lost:
    
        if (r4 != null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.util.ArrayMap<java.lang.String, java.lang.Long> parseCompatibilityPackages(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray obtainAttributes;
        java.lang.Long valueOf;
        int depth = xmlPullParser.getDepth();
        android.content.res.TypedArray typedArray = null;
        android.util.ArrayMap<java.lang.String, java.lang.Long> arrayMap = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && TAG_COMPATIBILITY_PACKAGE.equals(xmlPullParser.getName())) {
                try {
                    obtainAttributes = resources.obtainAttributes(android.util.Xml.asAttributeSet(xmlPullParser), com.android.internal.R.styleable.AutofillService_CompatibilityPackage);
                    try {
                        java.lang.String string = obtainAttributes.getString(0);
                        if (android.text.TextUtils.isEmpty(string)) {
                            android.util.Log.e(TAG, "Invalid compatibility package:" + string);
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                        } else {
                            java.lang.String string2 = obtainAttributes.getString(1);
                            if (string2 != null) {
                                try {
                                    valueOf = java.lang.Long.valueOf(java.lang.Long.parseLong(string2));
                                    if (valueOf.longValue() < 0) {
                                        android.util.Log.e(TAG, "Invalid compatibility max version code:" + valueOf);
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                    }
                                } catch (java.lang.NumberFormatException e) {
                                    android.util.Log.e(TAG, "Invalid compatibility max version code:" + string2);
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                }
                            } else {
                                valueOf = Long.MAX_VALUE;
                            }
                            if (arrayMap == null) {
                                arrayMap = new android.util.ArrayMap<>();
                            }
                            arrayMap.put(string, valueOf);
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                            if (obtainAttributes != null) {
                                obtainAttributes.recycle();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        th = th;
                        typedArray = obtainAttributes;
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                        if (typedArray != null) {
                            typedArray.recycle();
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                }
            }
        }
        obtainAttributes.recycle();
        return arrayMap;
    }

    private AutofillServiceInfo(java.lang.String str) {
        this.mServiceInfo = new android.content.pm.ServiceInfo();
        this.mServiceInfo.applicationInfo = new android.content.pm.ApplicationInfo();
        this.mServiceInfo.packageName = "com.android.test";
        this.mSettingsActivity = null;
        this.mPasswordsActivity = str;
        this.mCompatibilityPackages = null;
        this.mInlineSuggestionsEnabled = false;
    }

    public static final class TestDataBuilder {
        private java.lang.String mPasswordsActivity;

        public android.service.autofill.AutofillServiceInfo.TestDataBuilder setPasswordsActivity(java.lang.String str) {
            this.mPasswordsActivity = str;
            return this;
        }

        public android.service.autofill.AutofillServiceInfo build() {
            return new android.service.autofill.AutofillServiceInfo(this.mPasswordsActivity);
        }
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public java.lang.String getPasswordsActivity() {
        return this.mPasswordsActivity;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Long> getCompatibilityPackages() {
        return this.mCompatibilityPackages;
    }

    public boolean isInlineSuggestionsEnabled() {
        return this.mInlineSuggestionsEnabled;
    }

    public static java.util.List<android.service.autofill.AutofillServiceInfo> getAvailableServices(android.content.Context context, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.content.pm.ResolveInfo> it = context.getPackageManager().queryIntentServicesAsUser(new android.content.Intent(android.service.autofill.AutofillService.SERVICE_INTERFACE), 128, i).iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo != null) {
                try {
                } catch (java.lang.SecurityException e) {
                    android.util.Log.w(TAG, "Error getting info for " + serviceInfo + ": " + e);
                }
                if (isCredentialManagerAutofillService(serviceInfo.getComponentName())) {
                }
            }
            arrayList.add(new android.service.autofill.AutofillServiceInfo(context, serviceInfo));
        }
        return arrayList;
    }

    private static boolean isCredentialManagerAutofillService(android.content.ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        return componentName.equals(CREDMAN_SERVICE_COMPONENT_NAME);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START).append(this.mServiceInfo);
        sb.append(", settings:").append(this.mSettingsActivity);
        sb.append(", passwords activity:").append(this.mPasswordsActivity);
        sb.append(", hasCompatPckgs:").append((this.mCompatibilityPackages == null || this.mCompatibilityPackages.isEmpty()) ? false : true).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        sb.append(", inline suggestions enabled:").append(this.mInlineSuggestionsEnabled);
        return sb.toString();
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("Component: ");
        printWriter.println(getServiceInfo().getComponentName());
        printWriter.print(str);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
        printWriter.print(str);
        printWriter.print("Passwords activity: ");
        printWriter.println(this.mPasswordsActivity);
        printWriter.print(str);
        printWriter.print("Compat packages: ");
        printWriter.println(this.mCompatibilityPackages);
        printWriter.print(str);
        printWriter.print("Inline Suggestions Enabled: ");
        printWriter.println(this.mInlineSuggestionsEnabled);
    }
}
