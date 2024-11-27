package android.service.credentials;

/* loaded from: classes3.dex */
public final class CredentialProviderInfoFactory {
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String TAG = "CredentialProviderInfoFactory";
    private static final java.lang.String TAG_CAPABILITIES = "capabilities";
    private static final java.lang.String TAG_CAPABILITY = "capability";
    private static final java.lang.String TAG_CREDENTIAL_PROVIDER = "credential-provider";

    public static android.credentials.CredentialProviderInfo create(android.content.Context context, android.content.ComponentName componentName, int i, boolean z) throws android.content.pm.PackageManager.NameNotFoundException {
        return create(context, getServiceInfoOrThrow(componentName, i), z, false, false, false);
    }

    public static android.credentials.CredentialProviderInfo create(android.content.Context context, android.content.pm.ServiceInfo serviceInfo, boolean z, boolean z2, boolean z3, boolean z4) throws java.lang.SecurityException {
        verifyProviderPermission(serviceInfo);
        if (z && !isValidSystemProvider(context, serviceInfo, z2)) {
            android.util.Slog.e(TAG, "Provider is not a valid system provider: " + serviceInfo);
            throw new java.lang.SecurityException("Provider is not a valid system provider: " + serviceInfo);
        }
        return populateMetadata(context, serviceInfo).setSystemProvider(z).setEnabled(z3).setPrimary(z4).build();
    }

    public static android.credentials.CredentialProviderInfo createForTests(android.content.pm.ServiceInfo serviceInfo, java.lang.CharSequence charSequence, boolean z, boolean z2, java.util.List<java.lang.String> list) {
        return new android.credentials.CredentialProviderInfo.Builder(serviceInfo).setEnabled(z2).setOverrideLabel(charSequence).setSystemProvider(z).addCapabilities(list).build();
    }

    private static void verifyProviderPermission(android.content.pm.ServiceInfo serviceInfo) throws java.lang.SecurityException {
        if (android.Manifest.permission.BIND_CREDENTIAL_PROVIDER_SERVICE.equals(serviceInfo.permission)) {
        } else {
            throw new java.lang.SecurityException("Service does not require the expected permission : android.permission.BIND_CREDENTIAL_PROVIDER_SERVICE");
        }
    }

    private static boolean isSystemProviderWithValidPermission(android.content.pm.ServiceInfo serviceInfo, android.content.Context context) {
        if (context == null) {
            android.util.Slog.w(TAG, "Context is null in isSystemProviderWithValidPermission");
            return false;
        }
        return android.service.credentials.PermissionUtils.hasPermission(context, serviceInfo.packageName, android.Manifest.permission.PROVIDE_DEFAULT_ENABLED_CREDENTIAL_SERVICE);
    }

    private static boolean isValidSystemProvider(android.content.Context context, android.content.pm.ServiceInfo serviceInfo, boolean z) {
        java.util.Objects.requireNonNull(context, "context must not be null");
        if (z) {
            android.os.Bundle bundle = serviceInfo.metaData;
            if (bundle == null) {
                android.util.Slog.w(TAG, "metadata is null while reading TEST_SYSTEM_PROVIDER_META_DATA_KEY: " + serviceInfo);
                return false;
            }
            return bundle.getBoolean(android.service.credentials.CredentialProviderService.TEST_SYSTEM_PROVIDER_META_DATA_KEY);
        }
        return isSystemProviderWithValidPermission(serviceInfo, context);
    }

    private static android.credentials.CredentialProviderInfo.Builder populateMetadata(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        android.content.res.Resources resources;
        java.util.Objects.requireNonNull(context, "context must not be null");
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.credentials.CredentialProviderInfo.Builder builder = new android.credentials.CredentialProviderInfo.Builder(serviceInfo);
        if (serviceInfo.metaData == null) {
            android.util.Slog.w(TAG, "Metadata is null for provider: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            resources = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Failed to get app resources", e);
            resources = null;
        }
        if (resources == null) {
            android.util.Slog.w(TAG, "Resources are null for the serviceInfo being processed: " + serviceInfo.getComponentName());
            return builder;
        }
        try {
            return extractXmlMetadata(context, serviceInfo, packageManager, resources);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Failed to get XML metadata", e2);
            return builder;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0046, code lost:
    
        if (r3 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0056, code lost:
    
        r5.addCapabilities(parseXmlProviderOuterCapabilities(r6, r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0053, code lost:
    
        r3.recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0051, code lost:
    
        if (r3 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.credentials.CredentialProviderInfo.Builder extractXmlMetadata(android.content.Context context, android.content.pm.ServiceInfo serviceInfo, android.content.pm.PackageManager packageManager, android.content.res.Resources resources) {
        android.credentials.CredentialProviderInfo.Builder builder = new android.credentials.CredentialProviderInfo.Builder(serviceInfo);
        android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.service.credentials.CredentialProviderService.SERVICE_META_DATA);
        if (loadXmlMetaData == null) {
            return builder;
        }
        for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            try {
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "Error parsing credential provider service meta-data", e);
            }
        }
        if (TAG_CREDENTIAL_PROVIDER.equals(loadXmlMetaData.getName())) {
            android.content.res.TypedArray typedArray = null;
            try {
                try {
                    typedArray = resources.obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.CredentialProvider);
                    builder.setSettingsSubtitle(getAfsAttributeSafe(typedArray, 1));
                    builder.setSettingsActivity(getAfsAttributeSafe(typedArray, 0));
                } catch (java.lang.Exception e2) {
                    android.util.Slog.w(TAG, "Failed to get XML attr for metadata", e2);
                }
            } catch (java.lang.Throwable th) {
                if (typedArray != null) {
                    typedArray.recycle();
                }
                throw th;
            }
        } else {
            android.util.Slog.w(TAG, "Meta-data does not start with credential-provider-service tag");
        }
        return builder;
    }

    private static java.lang.String getAfsAttributeSafe(android.content.res.TypedArray typedArray, int i) {
        if (typedArray == null) {
            return null;
        }
        try {
            return typedArray.getString(i);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Failed to get XML attr from afs attributes", e);
            return null;
        }
    }

    private static java.util.List<java.lang.String> parseXmlProviderOuterCapabilities(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && TAG_CAPABILITIES.equals(xmlPullParser.getName())) {
                arrayList.addAll(parseXmlProviderInnerCapabilities(xmlPullParser, resources));
            }
        }
        return arrayList;
    }

    private static java.util.List<java.lang.String> parseXmlProviderInnerCapabilities(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources resources) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && "capability".equals(xmlPullParser.getName()) && (attributeValue = xmlPullParser.getAttributeValue(null, "name")) != null && !android.text.TextUtils.isEmpty(attributeValue)) {
                arrayList.add(attributeValue);
            }
        }
        return arrayList;
    }

    private static android.content.pm.ServiceInfo getServiceInfoOrThrow(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to get serviceInfo", e);
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
    }

    private static java.util.List<android.content.pm.ServiceInfo> getAvailableSystemServiceInfos(android.content.Context context, int i, boolean z) {
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        arrayList2.addAll(context.getPackageManager().queryIntentServicesAsUser(new android.content.Intent(android.service.credentials.CredentialProviderService.SYSTEM_SERVICE_INTERFACE), android.content.pm.PackageManager.ResolveInfoFlags.of(128L), i));
        java.util.Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if (z) {
                if (serviceInfo != null) {
                    arrayList.add(serviceInfo);
                }
            } else {
                try {
                    if (context.getPackageManager().getApplicationInfo(serviceInfo.packageName, android.content.pm.PackageManager.ApplicationInfoFlags.of(1048576L)) != null && serviceInfo != null) {
                        arrayList.add(serviceInfo);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.SecurityException e) {
                    android.util.Slog.e(TAG, "Error getting info for " + serviceInfo, e);
                }
            }
        }
        return arrayList;
    }

    public static java.util.List<android.credentials.CredentialProviderInfo> getAvailableSystemServices(android.content.Context context, int i, boolean z, java.util.Set<android.content.ComponentName> set) {
        java.util.Objects.requireNonNull(context, "context must not be null");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.ServiceInfo serviceInfo : getAvailableSystemServiceInfos(context, i, z)) {
            try {
                android.credentials.CredentialProviderInfo create = create(context, serviceInfo, true, z, set.contains(serviceInfo.getComponentName()), false);
                if (!create.isSystemProvider()) {
                    android.util.Slog.e(TAG, "Non system provider was in system provider list.");
                } else {
                    arrayList.add(create);
                }
            } catch (java.lang.SecurityException e) {
                android.util.Slog.e(TAG, "Failed to create CredentialProviderInfo: " + e);
            }
        }
        return arrayList;
    }

    private static android.app.admin.PackagePolicy getDeviceManagerPolicy(android.content.Context context, int i) {
        try {
            return ((android.app.admin.DevicePolicyManager) context.createContextAsUser(android.os.UserHandle.of(i), 0).getSystemService(android.app.admin.DevicePolicyManager.class)).getCredentialManagerPolicy();
        } catch (java.lang.SecurityException e) {
            android.util.Slog.e(TAG, "Failed to get device policy: " + e);
            return null;
        }
    }

    public static java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServices(android.content.Context context, int i, int i2, java.util.Set<android.content.ComponentName> set, java.util.Set<android.content.ComponentName> set2) {
        java.util.Objects.requireNonNull(context, "context must not be null");
        android.service.credentials.CredentialProviderInfoFactory.ProviderGenerator providerGenerator = new android.service.credentials.CredentialProviderInfoFactory.ProviderGenerator(context, getDeviceManagerPolicy(context, i), false, i2);
        providerGenerator.addUserProviders(getUserProviders(context, i, false, set, set2));
        providerGenerator.addSystemProviders(getAvailableSystemServices(context, i, false, set));
        return providerGenerator.getProviders();
    }

    public static java.util.List<android.credentials.CredentialProviderInfo> getCredentialProviderServicesForTesting(android.content.Context context, int i, int i2, java.util.Set<android.content.ComponentName> set, java.util.Set<android.content.ComponentName> set2) {
        java.util.Objects.requireNonNull(context, "context must not be null");
        android.service.credentials.CredentialProviderInfoFactory.ProviderGenerator providerGenerator = new android.service.credentials.CredentialProviderInfoFactory.ProviderGenerator(context, getDeviceManagerPolicy(context, i), true, i2);
        providerGenerator.addUserProviders(getUserProviders(context, i, true, set, set2));
        providerGenerator.addSystemProviders(getAvailableSystemServices(context, i, true, set));
        return providerGenerator.getProviders();
    }

    private static class ProviderGenerator {
        private final android.content.Context mContext;
        private final boolean mDisableSystemAppVerificationForTests;
        private final android.app.admin.PackagePolicy mPp;
        private final int mProviderFilter;
        private final java.util.Map<java.lang.String, android.credentials.CredentialProviderInfo> mServices = new java.util.HashMap();

        ProviderGenerator(android.content.Context context, android.app.admin.PackagePolicy packagePolicy, boolean z, int i) {
            this.mContext = context;
            this.mPp = packagePolicy;
            this.mDisableSystemAppVerificationForTests = z;
            this.mProviderFilter = i;
        }

        private boolean isPackageAllowed(boolean z, java.lang.String str) {
            if (this.mPp == null) {
                return true;
            }
            if (z) {
                return this.mPp.getPolicyType() == 2;
            }
            return this.mPp.isPackageAllowed(str, new java.util.HashSet());
        }

        public java.util.List<android.credentials.CredentialProviderInfo> getProviders() {
            return new java.util.ArrayList(this.mServices.values());
        }

        public void addUserProviders(java.util.List<android.credentials.CredentialProviderInfo> list) {
            for (android.credentials.CredentialProviderInfo credentialProviderInfo : list) {
                if (!credentialProviderInfo.isSystemProvider()) {
                    addProvider(credentialProviderInfo);
                }
            }
        }

        public void addSystemProviders(java.util.List<android.credentials.CredentialProviderInfo> list) {
            for (android.credentials.CredentialProviderInfo credentialProviderInfo : list) {
                if (credentialProviderInfo.isSystemProvider()) {
                    addProvider(credentialProviderInfo);
                }
            }
        }

        private boolean isProviderAllowedWithFilter(android.credentials.CredentialProviderInfo credentialProviderInfo) {
            if (this.mProviderFilter == 0) {
                return true;
            }
            return credentialProviderInfo.isSystemProvider() ? this.mProviderFilter == 1 : this.mProviderFilter == 2;
        }

        private void addProvider(android.credentials.CredentialProviderInfo credentialProviderInfo) {
            java.lang.String flattenToString = credentialProviderInfo.getServiceInfo().getComponentName().flattenToString();
            if (!isProviderAllowedWithFilter(credentialProviderInfo) || !isPackageAllowed(credentialProviderInfo.isSystemProvider(), credentialProviderInfo.getServiceInfo().packageName)) {
                return;
            }
            this.mServices.put(flattenToString, credentialProviderInfo);
        }
    }

    private static java.util.List<android.credentials.CredentialProviderInfo> getUserProviders(android.content.Context context, int i, boolean z, java.util.Set<android.content.ComponentName> set, java.util.Set<android.content.ComponentName> set2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.content.pm.ResolveInfo> it = context.getPackageManager().queryIntentServicesAsUser(new android.content.Intent(android.service.credentials.CredentialProviderService.SERVICE_INTERFACE), android.content.pm.PackageManager.ResolveInfoFlags.of(128L), i).iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = it.next().serviceInfo;
            if (serviceInfo == null) {
                android.util.Slog.d(TAG, "No serviceInfo found for resolveInfo, so skipping provider");
            } else {
                try {
                    android.credentials.CredentialProviderInfo create = create(context, serviceInfo, false, z, set.contains(serviceInfo.getComponentName()), set2.contains(serviceInfo.getComponentName()));
                    if (!create.isSystemProvider()) {
                        arrayList.add(create);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "Error getting info for " + serviceInfo, e);
                }
            }
        }
        return arrayList;
    }
}
