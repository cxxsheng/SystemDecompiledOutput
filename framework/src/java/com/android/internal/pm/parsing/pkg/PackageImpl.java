package com.android.internal.pm.parsing.pkg;

/* loaded from: classes5.dex */
public class PackageImpl implements com.android.internal.pm.parsing.pkg.ParsedPackage, com.android.internal.pm.parsing.pkg.AndroidPackageInternal, com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackage, com.android.internal.pm.pkg.parsing.ParsingPackageHidden, android.os.Parcelable {
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities;
    protected java.util.List<java.lang.String> adoptPermissions;
    private java.lang.Boolean anyDensity;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedApexSystemService> apexSystemServices;
    private java.lang.String appComponentFactory;
    private java.util.List<com.android.internal.pm.pkg.component.ParsedAttribution> attributions;
    private int autoRevokePermissions;
    private java.lang.String backupAgentName;
    private int banner;
    private int baseRevisionCode;
    private int category;
    private java.lang.String classLoaderName;
    private java.lang.String className;
    private int compatibleWidthLimitDp;
    private int compileSdkVersion;
    private java.lang.String compileSdkVersionCodeName;
    private java.util.List<android.content.pm.ConfigurationInfo> configPreferences;
    private int dataExtractionRules;
    private int descriptionRes;
    private java.util.List<android.content.pm.FeatureGroupInfo> featureGroups;
    private int fullBackupContent;
    private int gwpAsanMode;
    private int iconRes;
    private java.util.Set<java.lang.String> implicitPermissions;
    private int installLocation;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedInstrumentation> instrumentations;
    private java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> keySetMapping;
    private int labelRes;
    private int largestWidthLimitDp;
    private java.util.List<java.lang.String> libraryNames;
    private int logo;
    private boolean mAllowCrossUidActivitySwitchFromBelow;
    private boolean mAppMetadataFileInApk;
    protected java.lang.String mBaseApkPath;
    private java.lang.String mBaseAppDataCredentialProtectedDirForSystemUser;
    private java.lang.String mBaseAppDataDeviceProtectedDirForSystemUser;
    private int mBaseAppInfoFlags;
    private int mBaseAppInfoPrivateFlags;
    private int mBaseAppInfoPrivateFlagsExt;
    private long mBooleans;
    private long mBooleans2;
    com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback mCallback;
    private java.lang.String mEmergencyInstaller;
    private java.util.Set<java.lang.String> mKnownActivityEmbeddingCerts;
    private int mLocaleConfigRes;
    private long mLongVersionCode;
    protected java.lang.String mPath;
    private java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> mProperties;
    private java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> mSplits;
    protected java.util.UUID mStorageUuid;
    private java.lang.String[] mUsesLibrariesSorted;
    private java.lang.String[] mUsesOptionalLibrariesSorted;
    private java.lang.String[] mUsesSdkLibrariesSorted;
    private java.lang.String[] mUsesStaticLibrariesSorted;
    private java.lang.String manageSpaceActivityName;
    private final java.lang.String manifestPackageName;
    private float maxAspectRatio;
    private int maxSdkVersion;
    private int memtagMode;
    private android.os.Bundle metaData;
    private java.util.Set<java.lang.String> mimeGroups;
    private float minAspectRatio;
    private android.util.SparseIntArray minExtensionVersions;
    private int minSdkVersion;
    private int nativeHeapZeroInitialized;
    protected java.lang.String nativeLibraryDir;
    protected java.lang.String nativeLibraryRootDir;
    private boolean nativeLibraryRootRequiresIsa;
    private int networkSecurityConfigRes;
    private java.lang.CharSequence nonLocalizedLabel;
    protected java.util.List<java.lang.String> originalPackages;
    private java.lang.String overlayCategory;
    private int overlayPriority;
    private java.lang.String overlayTarget;
    private java.lang.String overlayTargetOverlayableName;
    private java.util.Map<java.lang.String, java.lang.String> overlayables;
    protected java.lang.String packageName;
    private java.lang.String permission;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedPermissionGroup> permissionGroups;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedPermission> permissions;
    private java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>> preferredActivityFilters;
    protected java.lang.String primaryCpuAbi;
    private java.lang.String processName;
    private java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> processes;
    protected java.util.List<java.lang.String> protectedBroadcasts;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> providers;
    private java.util.List<android.content.Intent> queriesIntents;
    private java.util.List<java.lang.String> queriesPackages;
    private java.util.Set<java.lang.String> queriesProviders;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> receivers;
    private java.util.List<android.content.pm.FeatureInfo> reqFeatures;
    private java.lang.Boolean requestRawExternalStorageAccess;

    @java.lang.Deprecated
    protected java.util.Set<java.lang.String> requestedPermissions;
    private java.lang.String requiredAccountType;
    private int requiresSmallestWidthDp;
    private java.lang.Boolean resizeable;
    private java.lang.Boolean resizeableActivity;
    private byte[] restrictUpdateHash;
    private java.lang.String restrictedAccountType;
    private int roundIconRes;
    private int sdkLibVersionMajor;
    private java.lang.String sdkLibraryName;
    protected java.lang.String secondaryCpuAbi;
    protected java.lang.String secondaryNativeLibraryDir;
    protected java.util.List<com.android.internal.pm.pkg.component.ParsedService> services;
    private java.lang.String sharedUserId;
    private int sharedUserLabel;
    private android.content.pm.SigningDetails signingDetails;
    private java.lang.String[] splitClassLoaderNames;
    protected java.lang.String[] splitCodePaths;
    private android.util.SparseArray<int[]> splitDependencies;
    private int[] splitFlags;
    private java.lang.String[] splitNames;
    private int[] splitRevisionCodes;
    private long staticSharedLibVersion;
    private java.lang.String staticSharedLibraryName;
    private java.lang.Boolean supportsExtraLargeScreens;
    private java.lang.Boolean supportsLargeScreens;
    private java.lang.Boolean supportsNormalScreens;
    private java.lang.Boolean supportsSmallScreens;
    private int targetSandboxVersion;
    private int targetSdkVersion;
    private java.lang.String taskAffinity;
    private int theme;
    private int uiOptions;
    private int uid;
    private java.util.Set<java.lang.String> upgradeKeySets;
    protected java.util.List<java.lang.String> usesLibraries;
    protected java.util.List<java.lang.String> usesNativeLibraries;
    protected java.util.List<java.lang.String> usesOptionalLibraries;
    protected java.util.List<java.lang.String> usesOptionalNativeLibraries;
    private java.util.List<com.android.internal.pm.pkg.component.ParsedUsesPermission> usesPermissions;
    private java.util.List<java.lang.String> usesSdkLibraries;
    private java.lang.String[][] usesSdkLibrariesCertDigests;
    private boolean[] usesSdkLibrariesOptional;
    private long[] usesSdkLibrariesVersionsMajor;
    private java.util.List<java.lang.String> usesStaticLibraries;
    private java.lang.String[][] usesStaticLibrariesCertDigests;
    private long[] usesStaticLibrariesVersions;
    protected int versionCode;
    protected int versionCodeMajor;
    private java.lang.String versionName;
    protected java.lang.String volumeUuid;
    private java.lang.String zygotePreloadName;
    private static final android.util.SparseArray<int[]> EMPTY_INT_ARRAY_SPARSE_ARRAY = new android.util.SparseArray<>();
    private static final java.util.Comparator<com.android.internal.pm.pkg.component.ParsedMainComponent> ORDER_COMPARATOR = new java.util.Comparator() { // from class: com.android.internal.pm.parsing.pkg.PackageImpl$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int compare;
            compare = java.lang.Integer.compare(((com.android.internal.pm.pkg.component.ParsedMainComponent) obj2).getOrder(), ((com.android.internal.pm.pkg.component.ParsedMainComponent) obj).getOrder());
            return compare;
        }
    };
    public static final com.android.internal.util.Parcelling.BuiltIn.ForBoolean sForBoolean = (com.android.internal.util.Parcelling.BuiltIn.ForBoolean) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForBoolean.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForInternedString sForInternedString = (com.android.internal.util.Parcelling.BuiltIn.ForInternedString) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArray sForInternedStringArray = (com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArray) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArray.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForInternedStringList sForInternedStringList = (com.android.internal.util.Parcelling.BuiltIn.ForInternedStringList) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringList.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForInternedStringValueMap sForInternedStringValueMap = (com.android.internal.util.Parcelling.BuiltIn.ForInternedStringValueMap) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringValueMap.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForStringSet sForStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
    public static final com.android.internal.util.Parcelling.BuiltIn.ForInternedStringSet sForInternedStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForInternedStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringSet.class);
    protected static final com.android.internal.pm.pkg.parsing.ParsingUtils.StringPairListParceler sForIntentInfoPairs = new com.android.internal.pm.pkg.parsing.ParsingUtils.StringPairListParceler();
    public static final android.os.Parcelable.Creator<com.android.internal.pm.parsing.pkg.PackageImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.parsing.pkg.PackageImpl>() { // from class: com.android.internal.pm.parsing.pkg.PackageImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.parsing.pkg.PackageImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.parsing.pkg.PackageImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.parsing.pkg.PackageImpl[] newArray(int i) {
            return new com.android.internal.pm.parsing.pkg.PackageImpl[i];
        }
    };

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ com.android.internal.pm.pkg.parsing.ParsingPackage asSplit(java.lang.String[] strArr, java.lang.String[] strArr2, int[] iArr, android.util.SparseArray sparseArray) {
        return asSplit(strArr, strArr2, iArr, (android.util.SparseArray<int[]>) sparseArray);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ com.android.internal.pm.pkg.parsing.ParsingPackage setProcesses(java.util.Map map) {
        return setProcesses((java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess>) map);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public /* bridge */ /* synthetic */ com.android.internal.pm.pkg.parsing.ParsingPackage setUpgradeKeySets(java.util.Set set) {
        return setUpgradeKeySets((java.util.Set<java.lang.String>) set);
    }

    public static com.android.internal.pm.parsing.pkg.PackageImpl forParsing(java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.res.TypedArray typedArray, boolean z, com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        return new com.android.internal.pm.parsing.pkg.PackageImpl(str, str2, str3, typedArray, z, callback);
    }

    public static com.android.server.pm.pkg.AndroidPackage buildFakeForDeletion(java.lang.String str, java.lang.String str2) {
        return forTesting(str).setVolumeUuid(str2).hideAsParsed().hideAsFinal();
    }

    public static com.android.internal.pm.pkg.parsing.ParsingPackage forTesting(java.lang.String str) {
        return forTesting(str, "");
    }

    public static com.android.internal.pm.pkg.parsing.ParsingPackage forTesting(java.lang.String str, java.lang.String str2) {
        return new com.android.internal.pm.parsing.pkg.PackageImpl(str, str2, str2, null, false, null);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addActivity(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity) {
        this.activities = com.android.internal.util.CollectionUtils.add(this.activities, parsedActivity);
        addMimeGroupsFromComponent(parsedActivity);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addAdoptPermission(java.lang.String str) {
        this.adoptPermissions = com.android.internal.util.CollectionUtils.add(this.adoptPermissions, android.text.TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final com.android.internal.pm.parsing.pkg.PackageImpl addApexSystemService(com.android.internal.pm.pkg.component.ParsedApexSystemService parsedApexSystemService) {
        this.apexSystemServices = com.android.internal.util.CollectionUtils.add(this.apexSystemServices, parsedApexSystemService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addAttribution(com.android.internal.pm.pkg.component.ParsedAttribution parsedAttribution) {
        this.attributions = com.android.internal.util.CollectionUtils.add(this.attributions, parsedAttribution);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addConfigPreference(android.content.pm.ConfigurationInfo configurationInfo) {
        this.configPreferences = com.android.internal.util.CollectionUtils.add(this.configPreferences, configurationInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addFeatureGroup(android.content.pm.FeatureGroupInfo featureGroupInfo) {
        this.featureGroups = com.android.internal.util.CollectionUtils.add(this.featureGroups, featureGroupInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addImplicitPermission(java.lang.String str) {
        addUsesPermission((com.android.internal.pm.pkg.component.ParsedUsesPermission) new com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl(str, 0));
        this.implicitPermissions = com.android.internal.util.CollectionUtils.add(this.implicitPermissions, android.text.TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addInstrumentation(com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation) {
        this.instrumentations = com.android.internal.util.CollectionUtils.add(this.instrumentations, parsedInstrumentation);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addKeySet(java.lang.String str, java.security.PublicKey publicKey) {
        android.util.ArraySet<java.security.PublicKey> arraySet = this.keySetMapping.get(str);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
        }
        arraySet.add(publicKey);
        this.keySetMapping = com.android.internal.util.CollectionUtils.add(this.keySetMapping, str, arraySet);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addLibraryName(java.lang.String str) {
        this.libraryNames = com.android.internal.util.CollectionUtils.add(this.libraryNames, android.text.TextUtils.safeIntern(str));
        return this;
    }

    private void addMimeGroupsFromComponent(com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        for (int size = parsedComponent.getIntents().size() - 1; size >= 0; size--) {
            android.content.IntentFilter intentFilter = parsedComponent.getIntents().get(size).getIntentFilter();
            for (int countMimeGroups = intentFilter.countMimeGroups() - 1; countMimeGroups >= 0; countMimeGroups--) {
                if (this.mimeGroups != null && this.mimeGroups.size() > 500) {
                    throw new java.lang.IllegalStateException("Max limit on number of MIME Groups reached");
                }
                this.mimeGroups = com.android.internal.util.CollectionUtils.add(this.mimeGroups, intentFilter.getMimeGroup(countMimeGroups));
            }
        }
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addOriginalPackage(java.lang.String str) {
        this.originalPackages = com.android.internal.util.CollectionUtils.add(this.originalPackages, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage addOverlayable(java.lang.String str, java.lang.String str2) {
        this.overlayables = com.android.internal.util.CollectionUtils.add(this.overlayables, str, android.text.TextUtils.safeIntern(str2));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addPermission(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission) {
        this.permissions = com.android.internal.util.CollectionUtils.add(this.permissions, parsedPermission);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addPermissionGroup(com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup) {
        this.permissionGroups = com.android.internal.util.CollectionUtils.add(this.permissionGroups, parsedPermissionGroup);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addPreferredActivityFilter(java.lang.String str, com.android.internal.pm.pkg.component.ParsedIntentInfo parsedIntentInfo) {
        this.preferredActivityFilters = com.android.internal.util.CollectionUtils.add(this.preferredActivityFilters, android.util.Pair.create(str, parsedIntentInfo));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addProperty(android.content.pm.PackageManager.Property property) {
        if (property == null) {
            return this;
        }
        this.mProperties = com.android.internal.util.CollectionUtils.add(this.mProperties, property.getName(), property);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addProtectedBroadcast(java.lang.String str) {
        if (!this.protectedBroadcasts.contains(str)) {
            this.protectedBroadcasts = com.android.internal.util.CollectionUtils.add(this.protectedBroadcasts, android.text.TextUtils.safeIntern(str));
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addProvider(com.android.internal.pm.pkg.component.ParsedProvider parsedProvider) {
        this.providers = com.android.internal.util.CollectionUtils.add(this.providers, parsedProvider);
        addMimeGroupsFromComponent(parsedProvider);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addQueriesIntent(android.content.Intent intent) {
        this.queriesIntents = com.android.internal.util.CollectionUtils.add(this.queriesIntents, intent);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addQueriesPackage(java.lang.String str) {
        this.queriesPackages = com.android.internal.util.CollectionUtils.add(this.queriesPackages, android.text.TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addQueriesProvider(java.lang.String str) {
        this.queriesProviders = com.android.internal.util.CollectionUtils.add(this.queriesProviders, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addReceiver(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity) {
        this.receivers = com.android.internal.util.CollectionUtils.add(this.receivers, parsedActivity);
        addMimeGroupsFromComponent(parsedActivity);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addReqFeature(android.content.pm.FeatureInfo featureInfo) {
        this.reqFeatures = com.android.internal.util.CollectionUtils.add(this.reqFeatures, featureInfo);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addService(com.android.internal.pm.pkg.component.ParsedService parsedService) {
        this.services = com.android.internal.util.CollectionUtils.add(this.services, parsedService);
        addMimeGroupsFromComponent(parsedService);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesLibrary(java.lang.String str) {
        java.lang.String safeIntern = android.text.TextUtils.safeIntern(str);
        if (!com.android.internal.util.ArrayUtils.contains(this.usesLibraries, safeIntern)) {
            this.usesLibraries = com.android.internal.util.CollectionUtils.add(this.usesLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final com.android.internal.pm.parsing.pkg.PackageImpl addUsesNativeLibrary(java.lang.String str) {
        java.lang.String safeIntern = android.text.TextUtils.safeIntern(str);
        if (!com.android.internal.util.ArrayUtils.contains(this.usesNativeLibraries, safeIntern)) {
            this.usesNativeLibraries = com.android.internal.util.CollectionUtils.add(this.usesNativeLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesOptionalLibrary(java.lang.String str) {
        java.lang.String safeIntern = android.text.TextUtils.safeIntern(str);
        if (!com.android.internal.util.ArrayUtils.contains(this.usesOptionalLibraries, safeIntern)) {
            this.usesOptionalLibraries = com.android.internal.util.CollectionUtils.add(this.usesOptionalLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public final com.android.internal.pm.parsing.pkg.PackageImpl addUsesOptionalNativeLibrary(java.lang.String str) {
        java.lang.String safeIntern = android.text.TextUtils.safeIntern(str);
        if (!com.android.internal.util.ArrayUtils.contains(this.usesOptionalNativeLibraries, safeIntern)) {
            this.usesOptionalNativeLibraries = com.android.internal.util.CollectionUtils.add(this.usesOptionalNativeLibraries, safeIntern);
        }
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesPermission(com.android.internal.pm.pkg.component.ParsedUsesPermission parsedUsesPermission) {
        this.usesPermissions = com.android.internal.util.CollectionUtils.add(this.usesPermissions, parsedUsesPermission);
        this.requestedPermissions = com.android.internal.util.CollectionUtils.add(this.requestedPermissions, parsedUsesPermission.getName());
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesSdkLibrary(java.lang.String str, long j, java.lang.String[] strArr, boolean z) {
        this.usesSdkLibraries = com.android.internal.util.CollectionUtils.add(this.usesSdkLibraries, android.text.TextUtils.safeIntern(str));
        this.usesSdkLibrariesVersionsMajor = com.android.internal.util.ArrayUtils.appendLong(this.usesSdkLibrariesVersionsMajor, j, true);
        this.usesSdkLibrariesCertDigests = (java.lang.String[][]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String[].class, this.usesSdkLibrariesCertDigests, strArr, true);
        this.usesSdkLibrariesOptional = com.android.internal.util.ArrayUtils.appendBoolean(this.usesSdkLibrariesOptional, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesStaticLibrary(java.lang.String str, long j, java.lang.String[] strArr) {
        this.usesStaticLibraries = com.android.internal.util.CollectionUtils.add(this.usesStaticLibraries, android.text.TextUtils.safeIntern(str));
        this.usesStaticLibrariesVersions = com.android.internal.util.ArrayUtils.appendLong(this.usesStaticLibrariesVersions, j, true);
        this.usesStaticLibrariesCertDigests = (java.lang.String[][]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String[].class, this.usesStaticLibrariesCertDigests, strArr, true);
        return this;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAttributionsUserVisible() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_96_180);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl asSplit(java.lang.String[] strArr, java.lang.String[] strArr2, int[] iArr, android.util.SparseArray<int[]> sparseArray) {
        this.splitNames = strArr;
        this.splitCodePaths = strArr2;
        this.splitRevisionCodes = iArr;
        this.splitDependencies = sparseArray;
        int length = strArr.length;
        this.splitFlags = new int[length];
        this.splitClassLoaderNames = new java.lang.String[length];
        return this;
    }

    protected void assignDerivedFields() {
        this.mStorageUuid = android.os.storage.StorageManager.convert(this.volumeUuid);
        this.mLongVersionCode = android.content.pm.PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    private android.util.ArrayMap<java.lang.String, java.lang.String> buildAppClassNamesByProcess() {
        if (com.android.internal.util.ArrayUtils.size(this.processes) == 0) {
            return null;
        }
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>(4);
        for (java.lang.String str : this.processes.keySet()) {
            android.util.ArrayMap<java.lang.String, java.lang.String> appClassNamesByPackage = this.processes.get(str).getAppClassNamesByPackage();
            for (int i = 0; i < appClassNamesByPackage.size(); i++) {
                if (this.packageName.equals(appClassNamesByPackage.keyAt(i))) {
                    java.lang.String valueAt = appClassNamesByPackage.valueAt(i);
                    if (!android.text.TextUtils.isEmpty(valueAt)) {
                        arrayMap.put(str, valueAt);
                    }
                }
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<com.android.server.pm.pkg.AndroidPackageSplit> getSplits() {
        if (this.mSplits == null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(new com.android.internal.pm.pkg.AndroidPackageSplitImpl(null, getBaseApkPath(), getBaseRevisionCode(), isDeclaredHavingCode() ? 4 : 0, getClassLoaderName()));
            if (this.splitNames != null) {
                for (int i = 0; i < this.splitNames.length; i++) {
                    arrayList.add(new com.android.internal.pm.pkg.AndroidPackageSplitImpl(this.splitNames[i], this.splitCodePaths[i], this.splitRevisionCodes[i], this.splitFlags[i], this.splitClassLoaderNames[i]));
                }
            }
            if (this.splitDependencies != null) {
                for (int i2 = 0; i2 < this.splitDependencies.size(); i2++) {
                    int keyAt = this.splitDependencies.keyAt(i2);
                    int[] valueAt = this.splitDependencies.valueAt(i2);
                    java.util.ArrayList arrayList2 = new java.util.ArrayList();
                    for (int i3 : valueAt) {
                        if (i3 >= 0) {
                            arrayList2.add((com.android.server.pm.pkg.AndroidPackageSplit) arrayList.get(i3));
                        }
                    }
                    ((com.android.internal.pm.pkg.AndroidPackageSplitImpl) arrayList.get(keyAt)).fillDependencies(java.util.Collections.unmodifiableList(arrayList2));
                }
            }
            this.mSplits = java.util.Collections.unmodifiableList(arrayList);
        }
        return this.mSplits;
    }

    public java.lang.String toString() {
        return "Package{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getActivities() {
        return this.activities;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getAdoptPermissions() {
        return this.adoptPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedApexSystemService> getApexSystemServices() {
        return this.apexSystemServices;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getAppComponentFactory() {
        return this.appComponentFactory;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedAttribution> getAttributions() {
        return this.attributions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getAutoRevokePermissions() {
        return this.autoRevokePermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getBackupAgentName() {
        return this.backupAgentName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getBannerResourceId() {
        return this.banner;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getBaseApkPath() {
        return this.mBaseApkPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getBaseRevisionCode() {
        return this.baseRevisionCode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCategory() {
        return this.category;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getClassLoaderName() {
        return this.classLoaderName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getApplicationClassName() {
        return this.className;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCompatibleWidthLimitDp() {
        return this.compatibleWidthLimitDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getCompileSdkVersion() {
        return this.compileSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getCompileSdkVersionCodeName() {
        return this.compileSdkVersionCodeName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<android.content.pm.ConfigurationInfo> getConfigPreferences() {
        return this.configPreferences;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getDataExtractionRulesResourceId() {
        return this.dataExtractionRules;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getDescriptionResourceId() {
        return this.descriptionRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<android.content.pm.FeatureGroupInfo> getFeatureGroups() {
        return this.featureGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getFullBackupContentResourceId() {
        return this.fullBackupContent;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getIconResourceId() {
        return this.iconRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Set<java.lang.String> getImplicitPermissions() {
        return this.implicitPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getInstallLocation() {
        return this.installLocation;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedInstrumentation> getInstrumentations() {
        return this.instrumentations;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> getKeySetMapping() {
        return this.keySetMapping;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Set<java.lang.String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLabelResourceId() {
        return this.labelRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLargestWidthLimitDp() {
        return this.largestWidthLimitDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<java.lang.String> getLibraryNames() {
        return this.libraryNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLocaleConfigResourceId() {
        return this.mLocaleConfigRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getLogoResourceId() {
        return this.logo;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getManageSpaceActivityName() {
        return this.manageSpaceActivityName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMaxAspectRatio() {
        return this.maxAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getMemtagMode() {
        return this.memtagMode;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public boolean isAppMetadataFileInApk() {
        return this.mAppMetadataFileInApk;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public android.os.Bundle getMetaData() {
        return this.metaData;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Set<java.lang.String> getMimeGroups() {
        return this.mimeGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public float getMinAspectRatio() {
        return this.minAspectRatio;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public android.util.SparseIntArray getMinExtensionVersions() {
        return this.minExtensionVersions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getNetworkSecurityConfigResourceId() {
        return this.networkSecurityConfigRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.CharSequence getNonLocalizedLabel() {
        return this.nonLocalizedLabel;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getOriginalPackages() {
        return this.originalPackages;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getOverlayCategory() {
        return this.overlayCategory;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public int getOverlayPriority() {
        return this.overlayPriority;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public java.lang.String getOverlayTarget() {
        return this.overlayTarget;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getOverlayTargetOverlayableName() {
        return this.overlayTargetOverlayableName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Map<java.lang.String, java.lang.String> getOverlayables() {
        return this.overlayables;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getPackageName() {
        return this.packageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getPath() {
        return this.mPath;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getPermission() {
        return this.permission;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedPermissionGroup> getPermissionGroups() {
        return this.permissionGroups;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedPermission> getPermissions() {
        return this.permissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<android.util.Pair<java.lang.String, com.android.internal.pm.pkg.component.ParsedIntentInfo>> getPreferredActivityFilters() {
        return this.preferredActivityFilters;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getProcessName() {
        return this.processName != null ? this.processName : this.packageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> getProcesses() {
        return this.processes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Map<java.lang.String, android.content.pm.PackageManager.Property> getProperties() {
        return this.mProperties;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getProtectedBroadcasts() {
        return this.protectedBroadcasts;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> getProviders() {
        return this.providers;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<android.content.Intent> getQueriesIntents() {
        return this.queriesIntents;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getQueriesPackages() {
        return this.queriesPackages;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Set<java.lang.String> getQueriesProviders() {
        return this.queriesProviders;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> getReceivers() {
        return this.receivers;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<android.content.pm.FeatureInfo> getRequestedFeatures() {
        return this.reqFeatures;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    @java.lang.Deprecated
    public java.util.Set<java.lang.String> getRequestedPermissions() {
        return this.requestedPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getRequiredAccountType() {
        return this.requiredAccountType;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getRequiresSmallestWidthDp() {
        return this.requiresSmallestWidthDp;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.Boolean getResizeableActivity() {
        return this.resizeableActivity;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public byte[] getRestrictUpdateHash() {
        return this.restrictUpdateHash;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getRestrictedAccountType() {
        return this.restrictedAccountType;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getEmergencyInstaller() {
        return this.mEmergencyInstaller;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getRoundIconResourceId() {
        return this.roundIconRes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getSdkLibraryName() {
        return this.sdkLibraryName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getSdkLibVersionMajor() {
        return this.sdkLibVersionMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedService> getServices() {
        return this.services;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getSharedUserId() {
        return this.sharedUserId;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getSharedUserLabelResourceId() {
        return this.sharedUserLabel;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public android.content.pm.SigningDetails getSigningDetails() {
        return this.signingDetails;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String[] getSplitClassLoaderNames() {
        return this.splitClassLoaderNames == null ? libcore.util.EmptyArray.STRING : this.splitClassLoaderNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String[] getSplitCodePaths() {
        return this.splitCodePaths == null ? libcore.util.EmptyArray.STRING : this.splitCodePaths;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public android.util.SparseArray<int[]> getSplitDependencies() {
        return this.splitDependencies == null ? EMPTY_INT_ARRAY_SPARSE_ARRAY : this.splitDependencies;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitFlags() {
        return this.splitFlags;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String[] getSplitNames() {
        return this.splitNames == null ? libcore.util.EmptyArray.STRING : this.splitNames;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int[] getSplitRevisionCodes() {
        return this.splitRevisionCodes == null ? libcore.util.EmptyArray.INT : this.splitRevisionCodes;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getStaticSharedLibraryName() {
        return this.staticSharedLibraryName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long getStaticSharedLibraryVersion() {
        return this.staticSharedLibVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.UUID getStorageUuid() {
        return this.mStorageUuid;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getTargetSandboxVersion() {
        return this.targetSandboxVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getTargetSdkVersion() {
        return this.targetSdkVersion;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getTaskAffinity() {
        return this.taskAffinity;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getThemeResourceId() {
        return this.theme;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public int getUiOptions() {
        return this.uiOptions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.Set<java.lang.String> getUpgradeKeySets() {
        return this.upgradeKeySets;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<java.lang.String> getUsesLibraries() {
        return this.usesLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public java.lang.String[] getUsesLibrariesSorted() {
        if (this.mUsesLibrariesSorted == null) {
            this.mUsesLibrariesSorted = sortLibraries(this.usesLibraries);
        }
        return this.mUsesLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<java.lang.String> getUsesNativeLibraries() {
        return this.usesNativeLibraries;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getUsesOptionalLibraries() {
        return this.usesOptionalLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public java.lang.String[] getUsesOptionalLibrariesSorted() {
        if (this.mUsesOptionalLibrariesSorted == null) {
            this.mUsesOptionalLibrariesSorted = sortLibraries(this.usesOptionalLibraries);
        }
        return this.mUsesOptionalLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.util.List<java.lang.String> getUsesOptionalNativeLibraries() {
        return this.usesOptionalNativeLibraries;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<com.android.internal.pm.pkg.component.ParsedUsesPermission> getUsesPermissions() {
        return this.usesPermissions;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<java.lang.String> getUsesSdkLibraries() {
        return this.usesSdkLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public java.lang.String[] getUsesSdkLibrariesSorted() {
        if (this.mUsesSdkLibrariesSorted == null) {
            this.mUsesSdkLibrariesSorted = sortLibraries(this.usesSdkLibraries);
        }
        return this.mUsesSdkLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String[][] getUsesSdkLibrariesCertDigests() {
        return this.usesSdkLibrariesCertDigests;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public long[] getUsesSdkLibrariesVersionsMajor() {
        return this.usesSdkLibrariesVersionsMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean[] getUsesSdkLibrariesOptional() {
        return this.usesSdkLibrariesOptional;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.util.List<java.lang.String> getUsesStaticLibraries() {
        return this.usesStaticLibraries;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageInternal
    public java.lang.String[] getUsesStaticLibrariesSorted() {
        if (this.mUsesStaticLibrariesSorted == null) {
            this.mUsesStaticLibrariesSorted = sortLibraries(this.usesStaticLibraries);
        }
        return this.mUsesStaticLibrariesSorted;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String[][] getUsesStaticLibrariesCertDigests() {
        return this.usesStaticLibrariesCertDigests;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long[] getUsesStaticLibrariesVersions() {
        return this.usesStaticLibrariesVersions;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public int getVersionCode() {
        return this.versionCode;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public int getVersionCodeMajor() {
        return this.versionCodeMajor;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getVersionName() {
        return this.versionName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getVolumeUuid() {
        return this.volumeUuid;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public java.lang.String getZygotePreloadName() {
        return this.zygotePreloadName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isAllowCrossUidActivitySwitchFromBelow() {
        return this.mAllowCrossUidActivitySwitchFromBelow;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean hasPreserveLegacyExternalStorage() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_3_15);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean hasRequestForegroundServiceExemption() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_90_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.Boolean hasRequestRawExternalStorageAccess() {
        return this.requestRawExternalStorageAccess;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAllowAudioPlaybackCapture() {
        return getBoolean(2147483648L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isBackupAllowed() {
        return getBoolean(4L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isClearUserDataAllowed() {
        return getBoolean(2048L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isClearUserDataOnFailedRestoreAllowed() {
        return getBoolean(1073741824L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isAllowNativeHeapPointerTagging() {
        return getBoolean(68719476736L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isTaskReparentingAllowed() {
        return getBoolean(1024L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isAnyDensity() {
        if (this.anyDensity == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.anyDensity.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isBackupInForeground() {
        return getBoolean(16777216L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isHardwareAccelerated() {
        return getBoolean(2L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isSaveStateDisallowed() {
        return getBoolean(34359738368L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCrossProfile() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_13_15);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDebuggable() {
        return getBoolean(128L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDefaultToDeviceProtectedStorage() {
        return getBoolean(67108864L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDirectBootAware() {
        return getBoolean(134217728L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isEnabled() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_18_30);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isExternalStorage() {
        return getBoolean(1L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isExtractNativeLibrariesRequested() {
        return getBoolean(131072L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isForceQueryable() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_12_15);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isFullBackupOnly() {
        return getBoolean(32L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isGame() {
        return getBoolean(262144L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isDeclaredHavingCode() {
        return getBoolean(512L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isHasDomainUrls() {
        return getBoolean(4194304L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUserDataFragile() {
        return getBoolean(17179869184L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isIsolatedSplitLoading() {
        return getBoolean(2097152L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isKillAfterRestoreAllowed() {
        return getBoolean(8L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isLargeHeap() {
        return getBoolean(4096L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isLeavingSharedUser() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_135_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isMultiArch() {
        return getBoolean(65536L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isOnBackInvokedCallbackEnabled() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_132_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isResourceOverlay() {
        return getBoolean(1048576L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.content.om.OverlayConfig.PackageProvider.Package
    public boolean isOverlayIsStatic() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_6_15);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isPartiallyDirectBootAware() {
        return getBoolean(268435456L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isPersistent() {
        return getBoolean(64L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isProfileable() {
        return !getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_20_30);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isProfileableByShell() {
        return isProfileable() && getBoolean(8388608L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRequestLegacyExternalStorage() {
        return getBoolean(4294967296L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRequiredForAllUsers() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_5_15);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isResetEnabledSettingsOnAppDataCleared() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_104_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isResizeable() {
        if (this.resizeable == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.resizeable.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isResizeableActivityViaSdkVersion() {
        return getBoolean(536870912L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRestoreAnyVersion() {
        return getBoolean(16L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isSdkLibrary() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_128_180);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isStaticSharedLibrary() {
        return getBoolean(524288L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isExtraLargeScreensSupported() {
        if (this.supportsExtraLargeScreens == null) {
            return this.targetSdkVersion >= 9;
        }
        return this.supportsExtraLargeScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isLargeScreensSupported() {
        if (this.supportsLargeScreens == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.supportsLargeScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isNormalScreensSupported() {
        return this.supportsNormalScreens == null || this.supportsNormalScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isRtlSupported() {
        return getBoolean(16384L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage, com.android.internal.pm.pkg.parsing.ParsingPackage
    public boolean isSmallScreensSupported() {
        if (this.supportsSmallScreens == null) {
            return this.targetSdkVersion >= 4;
        }
        return this.supportsSmallScreens.booleanValue();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isTestOnly() {
        return getBoolean(32768L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean is32BitAbiPreferred() {
        return getBoolean(1099511627776L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUseEmbeddedDex() {
        return getBoolean(33554432L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCleartextTrafficAllowed() {
        return getBoolean(8192L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isNonSdkApiRequested() {
        return getBoolean(8589934592L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isVisibleToInstantApps() {
        return getBoolean(2199023255552L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isVmSafeMode() {
        return getBoolean(256L);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl removeUsesOptionalNativeLibrary(java.lang.String str) {
        this.usesOptionalNativeLibraries = com.android.internal.util.CollectionUtils.remove(this.usesOptionalNativeLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAllowAudioPlaybackCapture(boolean z) {
        return setBoolean(2147483648L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBackupAllowed(boolean z) {
        return setBoolean(4L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setClearUserDataAllowed(boolean z) {
        return setBoolean(2048L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setClearUserDataOnFailedRestoreAllowed(boolean z) {
        return setBoolean(1073741824L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAllowNativeHeapPointerTagging(boolean z) {
        return setBoolean(68719476736L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setTaskReparentingAllowed(boolean z) {
        return setBoolean(1024L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAnyDensity(int i) {
        if (i == 1) {
            return this;
        }
        this.anyDensity = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAppComponentFactory(java.lang.String str) {
        this.appComponentFactory = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setAttributionsAreUserVisible(boolean z) {
        setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_96_180, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAutoRevokePermissions(int i) {
        this.autoRevokePermissions = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBackupAgentName(java.lang.String str) {
        this.backupAgentName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBackupInForeground(boolean z) {
        return setBoolean(16777216L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBannerResourceId(int i) {
        this.banner = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setHardwareAccelerated(boolean z) {
        return setBoolean(2L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBaseRevisionCode(int i) {
        this.baseRevisionCode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSaveStateDisallowed(boolean z) {
        return setBoolean(34359738368L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCategory(int i) {
        this.category = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setClassLoaderName(java.lang.String str) {
        this.classLoaderName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setApplicationClassName(java.lang.String str) {
        this.className = str == null ? null : str.trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCompatibleWidthLimitDp(int i) {
        this.compatibleWidthLimitDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCompileSdkVersion(int i) {
        this.compileSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setCompileSdkVersionCodeName(java.lang.String str) {
        this.compileSdkVersionCodeName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCrossProfile(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_13_15, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDataExtractionRulesResourceId(int i) {
        this.dataExtractionRules = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDebuggable(boolean z) {
        return setBoolean(128L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDescriptionResourceId(int i) {
        this.descriptionRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setEnabled(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_18_30, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setExternalStorage(boolean z) {
        return setBoolean(1L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setExtractNativeLibrariesRequested(boolean z) {
        return setBoolean(131072L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setForceQueryable(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_12_15, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setFullBackupContentResourceId(int i) {
        this.fullBackupContent = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setFullBackupOnly(boolean z) {
        return setBoolean(32L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setGame(boolean z) {
        return setBoolean(262144L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setGwpAsanMode(int i) {
        this.gwpAsanMode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDeclaredHavingCode(boolean z) {
        return setBoolean(512L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setHasDomainUrls(boolean z) {
        return setBoolean(4194304L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUserDataFragile(boolean z) {
        return setBoolean(17179869184L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setIconResourceId(int i) {
        this.iconRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setInstallLocation(int i) {
        this.installLocation = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setIsolatedSplitLoading(boolean z) {
        return setBoolean(2097152L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setKillAfterRestoreAllowed(boolean z) {
        return setBoolean(8L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setKnownActivityEmbeddingCerts(java.util.Set<java.lang.String> set) {
        this.mKnownActivityEmbeddingCerts = set;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLabelResourceId(int i) {
        this.labelRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLargeHeap(boolean z) {
        return setBoolean(4096L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLargestWidthLimitDp(int i) {
        this.largestWidthLimitDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLeavingSharedUser(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_135_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLocaleConfigResourceId(int i) {
        this.mLocaleConfigRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLogoResourceId(int i) {
        this.logo = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setManageSpaceActivityName(java.lang.String str) {
        this.manageSpaceActivityName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMaxAspectRatio(float f) {
        this.maxAspectRatio = f;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMaxSdkVersion(int i) {
        this.maxSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMemtagMode(int i) {
        this.memtagMode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAppMetadataFileInApk(boolean z) {
        this.mAppMetadataFileInApk = z;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMetaData(android.os.Bundle bundle) {
        this.metaData = bundle;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMinAspectRatio(float f) {
        this.minAspectRatio = f;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMinExtensionVersions(android.util.SparseIntArray sparseIntArray) {
        this.minExtensionVersions = sparseIntArray;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMinSdkVersion(int i) {
        this.minSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setMultiArch(boolean z) {
        return setBoolean(65536L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNativeHeapZeroInitialized(int i) {
        this.nativeHeapZeroInitialized = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNetworkSecurityConfigResourceId(int i) {
        this.networkSecurityConfigRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNonLocalizedLabel(java.lang.CharSequence charSequence) {
        this.nonLocalizedLabel = charSequence == null ? null : charSequence.toString().trim();
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setOnBackInvokedCallbackEnabled(boolean z) {
        setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_132_180, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setAllowCrossUidActivitySwitchFromBelow(boolean z) {
        this.mAllowCrossUidActivitySwitchFromBelow = z;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setResourceOverlay(boolean z) {
        return setBoolean(1048576L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOverlayCategory(java.lang.String str) {
        this.overlayCategory = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOverlayIsStatic(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_6_15, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOverlayPriority(int i) {
        this.overlayPriority = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOverlayTarget(java.lang.String str) {
        this.overlayTarget = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOverlayTargetOverlayableName(java.lang.String str) {
        this.overlayTargetOverlayableName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPartiallyDirectBootAware(boolean z) {
        return setBoolean(268435456L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPermission(java.lang.String str) {
        this.permission = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPreserveLegacyExternalStorage(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_3_15, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setProcessName(java.lang.String str) {
        this.processName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setProcesses(java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> map) {
        this.processes = map;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setProfileable(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_20_30, !z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setProfileableByShell(boolean z) {
        return setBoolean(8388608L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequestForegroundServiceExemption(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_90_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequestLegacyExternalStorage(boolean z) {
        return setBoolean(4294967296L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequestRawExternalStorageAccess(java.lang.Boolean bool) {
        this.requestRawExternalStorageAccess = bool;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequiredAccountType(java.lang.String str) {
        this.requiredAccountType = android.text.TextUtils.nullIfEmpty(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequiredForAllUsers(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_5_15, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRequiresSmallestWidthDp(int i) {
        this.requiresSmallestWidthDp = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.pkg.parsing.ParsingPackage setResetEnabledSettingsOnAppDataCleared(boolean z) {
        setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_104_180, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setResizeable(int i) {
        if (i == 1) {
            return this;
        }
        this.resizeable = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setResizeableActivity(java.lang.Boolean bool) {
        this.resizeableActivity = bool;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setResizeableActivityViaSdkVersion(boolean z) {
        return setBoolean(536870912L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRestoreAnyVersion(boolean z) {
        return setBoolean(16L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRestrictedAccountType(java.lang.String str) {
        this.restrictedAccountType = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setEmergencyInstaller(java.lang.String str) {
        this.mEmergencyInstaller = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRoundIconResourceId(int i) {
        this.roundIconRes = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSdkLibraryName(java.lang.String str) {
        this.sdkLibraryName = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSdkLibVersionMajor(int i) {
        this.sdkLibVersionMajor = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSdkLibrary(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_128_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSharedUserId(java.lang.String str) {
        this.sharedUserId = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSharedUserLabelResourceId(int i) {
        this.sharedUserLabel = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSplitClassLoaderName(int i, java.lang.String str) {
        this.splitClassLoaderNames[i] = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSplitHasCode(int i, boolean z) {
        int i2;
        int[] iArr = this.splitFlags;
        if (z) {
            i2 = this.splitFlags[i] | 4;
        } else {
            i2 = this.splitFlags[i] & (-5);
        }
        iArr[i] = i2;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setStaticSharedLibraryName(java.lang.String str) {
        this.staticSharedLibraryName = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setStaticSharedLibraryVersion(long j) {
        this.staticSharedLibVersion = j;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setStaticSharedLibrary(boolean z) {
        return setBoolean(524288L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setExtraLargeScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsExtraLargeScreens = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setLargeScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsLargeScreens = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNormalScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsNormalScreens = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRtlSupported(boolean z) {
        return setBoolean(16384L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSmallScreensSupported(int i) {
        if (i == 1) {
            return this;
        }
        this.supportsSmallScreens = java.lang.Boolean.valueOf(i < 0);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setTargetSandboxVersion(int i) {
        this.targetSandboxVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setTargetSdkVersion(int i) {
        this.targetSdkVersion = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setTaskAffinity(java.lang.String str) {
        this.taskAffinity = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setTestOnly(boolean z) {
        return setBoolean(32768L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setThemeResourceId(int i) {
        this.theme = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUiOptions(int i) {
        this.uiOptions = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUpgradeKeySets(java.util.Set<java.lang.String> set) {
        this.upgradeKeySets = set;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl set32BitAbiPreferred(boolean z) {
        return setBoolean(1099511627776L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUseEmbeddedDex(boolean z) {
        return setBoolean(33554432L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCleartextTrafficAllowed(boolean z) {
        return setBoolean(8192L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNonSdkApiRequested(boolean z) {
        return setBoolean(8589934592L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVersionName(java.lang.String str) {
        this.versionName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVisibleToInstantApps(boolean z) {
        return setBoolean(2199023255552L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVmSafeMode(boolean z) {
        return setBoolean(256L, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVolumeUuid(java.lang.String str) {
        this.volumeUuid = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setZygotePreloadName(java.lang.String str) {
        this.zygotePreloadName = str;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl sortActivities() {
        java.util.Collections.sort(this.activities, ORDER_COMPARATOR);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl sortReceivers() {
        java.util.Collections.sort(this.receivers, ORDER_COMPARATOR);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl sortServices() {
        java.util.Collections.sort(this.services, ORDER_COMPARATOR);
        return this;
    }

    public android.content.pm.ApplicationInfo toAppInfoWithoutStateWithoutFlags() {
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.appComponentFactory = this.appComponentFactory;
        applicationInfo.backupAgentName = this.backupAgentName;
        applicationInfo.banner = this.banner;
        applicationInfo.category = this.category;
        applicationInfo.classLoaderName = this.classLoaderName;
        applicationInfo.className = this.className;
        applicationInfo.compatibleWidthLimitDp = this.compatibleWidthLimitDp;
        applicationInfo.compileSdkVersion = this.compileSdkVersion;
        applicationInfo.compileSdkVersionCodename = this.compileSdkVersionCodeName;
        applicationInfo.crossProfile = isCrossProfile();
        applicationInfo.descriptionRes = this.descriptionRes;
        applicationInfo.enabled = getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_18_30);
        applicationInfo.fullBackupContent = this.fullBackupContent;
        applicationInfo.dataExtractionRulesRes = this.dataExtractionRules;
        applicationInfo.icon = (!com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sUseRoundIcon || this.roundIconRes == 0) ? this.iconRes : this.roundIconRes;
        applicationInfo.iconRes = this.iconRes;
        applicationInfo.roundIconRes = this.roundIconRes;
        applicationInfo.installLocation = this.installLocation;
        applicationInfo.labelRes = this.labelRes;
        applicationInfo.largestWidthLimitDp = this.largestWidthLimitDp;
        applicationInfo.logo = this.logo;
        applicationInfo.manageSpaceActivityName = this.manageSpaceActivityName;
        applicationInfo.maxAspectRatio = this.maxAspectRatio;
        applicationInfo.metaData = this.metaData;
        applicationInfo.minAspectRatio = this.minAspectRatio;
        applicationInfo.minSdkVersion = this.minSdkVersion;
        applicationInfo.name = this.className;
        applicationInfo.networkSecurityConfigRes = this.networkSecurityConfigRes;
        applicationInfo.nonLocalizedLabel = this.nonLocalizedLabel;
        applicationInfo.packageName = this.packageName;
        applicationInfo.permission = this.permission;
        applicationInfo.processName = getProcessName();
        applicationInfo.requiresSmallestWidthDp = this.requiresSmallestWidthDp;
        applicationInfo.splitClassLoaderNames = this.splitClassLoaderNames;
        applicationInfo.splitDependencies = (this.splitDependencies == null || this.splitDependencies.size() == 0) ? null : this.splitDependencies;
        applicationInfo.splitNames = this.splitNames;
        applicationInfo.storageUuid = this.mStorageUuid;
        applicationInfo.targetSandboxVersion = this.targetSandboxVersion;
        applicationInfo.targetSdkVersion = this.targetSdkVersion;
        applicationInfo.taskAffinity = this.taskAffinity;
        applicationInfo.theme = this.theme;
        applicationInfo.uiOptions = this.uiOptions;
        applicationInfo.volumeUuid = this.volumeUuid;
        applicationInfo.zygotePreloadName = this.zygotePreloadName;
        applicationInfo.setGwpAsanMode(this.gwpAsanMode);
        applicationInfo.setMemtagMode(this.memtagMode);
        applicationInfo.setNativeHeapZeroInitialized(this.nativeHeapZeroInitialized);
        applicationInfo.setRequestRawExternalStorageAccess(this.requestRawExternalStorageAccess);
        applicationInfo.setBaseCodePath(this.mBaseApkPath);
        applicationInfo.setBaseResourcePath(this.mBaseApkPath);
        applicationInfo.setCodePath(this.mPath);
        applicationInfo.setResourcePath(this.mPath);
        applicationInfo.setSplitCodePaths(com.android.internal.util.ArrayUtils.size(this.splitCodePaths) == 0 ? null : this.splitCodePaths);
        applicationInfo.setSplitResourcePaths(com.android.internal.util.ArrayUtils.size(this.splitCodePaths) != 0 ? this.splitCodePaths : null);
        applicationInfo.setVersionCode(this.mLongVersionCode);
        applicationInfo.setAppClassNamesByProcess(buildAppClassNamesByProcess());
        applicationInfo.setLocaleConfigRes(this.mLocaleConfigRes);
        if (!this.mKnownActivityEmbeddingCerts.isEmpty()) {
            applicationInfo.setKnownActivityEmbeddingCerts(this.mKnownActivityEmbeddingCerts);
        }
        applicationInfo.allowCrossUidActivitySwitchFromBelow = this.mAllowCrossUidActivitySwitchFromBelow;
        return applicationInfo;
    }

    private com.android.internal.pm.parsing.pkg.PackageImpl setBoolean(long j, boolean z) {
        if (z) {
            this.mBooleans = j | this.mBooleans;
        } else {
            this.mBooleans = (~j) & this.mBooleans;
        }
        return this;
    }

    private boolean getBoolean(long j) {
        return (j & this.mBooleans) != 0;
    }

    private com.android.internal.pm.parsing.pkg.PackageImpl setBoolean2(long j, boolean z) {
        if (z) {
            this.mBooleans2 = j | this.mBooleans2;
        } else {
            this.mBooleans2 = (~j) & this.mBooleans2;
        }
        return this;
    }

    private boolean getBoolean2(long j) {
        return (j & this.mBooleans2) != 0;
    }

    public PackageImpl(java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.res.TypedArray typedArray, boolean z, com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        this.usesLibraries = java.util.Collections.emptyList();
        this.usesOptionalLibraries = java.util.Collections.emptyList();
        this.usesNativeLibraries = java.util.Collections.emptyList();
        this.usesOptionalNativeLibraries = java.util.Collections.emptyList();
        this.originalPackages = java.util.Collections.emptyList();
        this.adoptPermissions = java.util.Collections.emptyList();
        this.requestedPermissions = java.util.Collections.emptySet();
        this.protectedBroadcasts = java.util.Collections.emptyList();
        this.activities = java.util.Collections.emptyList();
        this.apexSystemServices = java.util.Collections.emptyList();
        this.receivers = java.util.Collections.emptyList();
        this.services = java.util.Collections.emptyList();
        this.providers = java.util.Collections.emptyList();
        this.permissions = java.util.Collections.emptyList();
        this.permissionGroups = java.util.Collections.emptyList();
        this.instrumentations = java.util.Collections.emptyList();
        this.overlayables = java.util.Collections.emptyMap();
        this.libraryNames = java.util.Collections.emptyList();
        this.usesStaticLibraries = java.util.Collections.emptyList();
        this.usesSdkLibraries = java.util.Collections.emptyList();
        this.configPreferences = java.util.Collections.emptyList();
        this.reqFeatures = java.util.Collections.emptyList();
        this.featureGroups = java.util.Collections.emptyList();
        this.usesPermissions = java.util.Collections.emptyList();
        this.implicitPermissions = java.util.Collections.emptySet();
        this.upgradeKeySets = java.util.Collections.emptySet();
        this.keySetMapping = java.util.Collections.emptyMap();
        this.attributions = java.util.Collections.emptyList();
        this.preferredActivityFilters = java.util.Collections.emptyList();
        this.processes = java.util.Collections.emptyMap();
        this.mProperties = java.util.Collections.emptyMap();
        this.signingDetails = android.content.pm.SigningDetails.UNKNOWN;
        this.queriesIntents = java.util.Collections.emptyList();
        this.queriesPackages = java.util.Collections.emptyList();
        this.queriesProviders = java.util.Collections.emptySet();
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mimeGroups = java.util.Collections.emptySet();
        this.mBooleans = android.hardware.tv.tuner.FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = java.util.Collections.emptySet();
        this.mAppMetadataFileInApk = false;
        this.uid = -1;
        this.packageName = android.text.TextUtils.safeIntern(str);
        this.mBaseApkPath = str2;
        this.mPath = str3;
        this.mCallback = callback;
        if (typedArray != null) {
            this.versionCode = typedArray.getInteger(1, 0);
            this.versionCodeMajor = typedArray.getInteger(11, 0);
            setBaseRevisionCode(typedArray.getInteger(5, 0));
            setVersionName(typedArray.getNonConfigurationString(2, 0));
            setCompileSdkVersion(typedArray.getInteger(9, 0));
            setCompileSdkVersionCodeName(typedArray.getNonConfigurationString(10, 0));
            setIsolatedSplitLoading(typedArray.getBoolean(6, false));
        }
        this.manifestPackageName = this.packageName;
        setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_140_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl hideAsParsed() {
        assignDerivedFields();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.AndroidPackageInternal hideAsFinal() {
        if (this.mStorageUuid == null) {
            assignDerivedFields();
        }
        assignDerivedFields2();
        makeImmutable();
        return this;
    }

    private static java.lang.String[] sortLibraries(java.util.List<java.lang.String> list) {
        if (list.size() == 0) {
            return libcore.util.EmptyArray.STRING;
        }
        java.lang.String[] strArr = (java.lang.String[]) list.toArray(libcore.util.EmptyArray.STRING);
        java.util.Arrays.sort(strArr);
        return strArr;
    }

    private void assignDerivedFields2() {
        this.mBaseAppInfoFlags = com.android.internal.pm.parsing.AppInfoUtils.appInfoFlags(this);
        this.mBaseAppInfoPrivateFlags = com.android.internal.pm.parsing.AppInfoUtils.appInfoPrivateFlags(this);
        this.mBaseAppInfoPrivateFlagsExt = com.android.internal.pm.parsing.AppInfoUtils.appInfoPrivateFlagsExt(this, this.mCallback == null ? false : this.mCallback.getHiddenApiWhitelistedApps().contains(this.packageName));
        java.lang.String str = android.os.Environment.getDataDirectoryPath(getVolumeUuid()) + java.io.File.separator;
        java.lang.String str2 = java.io.File.separator + 0 + java.io.File.separator;
        this.mBaseAppDataCredentialProtectedDirForSystemUser = android.text.TextUtils.safeIntern(str + "user" + str2);
        this.mBaseAppDataDeviceProtectedDirForSystemUser = android.text.TextUtils.safeIntern(str + android.os.Environment.DIR_USER_DE + str2);
    }

    private void makeImmutable() {
        this.usesLibraries = java.util.Collections.unmodifiableList(this.usesLibraries);
        this.usesOptionalLibraries = java.util.Collections.unmodifiableList(this.usesOptionalLibraries);
        this.usesNativeLibraries = java.util.Collections.unmodifiableList(this.usesNativeLibraries);
        this.usesOptionalNativeLibraries = java.util.Collections.unmodifiableList(this.usesOptionalNativeLibraries);
        this.originalPackages = java.util.Collections.unmodifiableList(this.originalPackages);
        this.adoptPermissions = java.util.Collections.unmodifiableList(this.adoptPermissions);
        this.requestedPermissions = java.util.Collections.unmodifiableSet(this.requestedPermissions);
        this.protectedBroadcasts = java.util.Collections.unmodifiableList(this.protectedBroadcasts);
        this.apexSystemServices = java.util.Collections.unmodifiableList(this.apexSystemServices);
        this.activities = java.util.Collections.unmodifiableList(this.activities);
        this.receivers = java.util.Collections.unmodifiableList(this.receivers);
        this.services = java.util.Collections.unmodifiableList(this.services);
        this.providers = java.util.Collections.unmodifiableList(this.providers);
        this.permissions = java.util.Collections.unmodifiableList(this.permissions);
        this.permissionGroups = java.util.Collections.unmodifiableList(this.permissionGroups);
        this.instrumentations = java.util.Collections.unmodifiableList(this.instrumentations);
        this.overlayables = java.util.Collections.unmodifiableMap(this.overlayables);
        this.libraryNames = java.util.Collections.unmodifiableList(this.libraryNames);
        this.usesStaticLibraries = java.util.Collections.unmodifiableList(this.usesStaticLibraries);
        this.usesSdkLibraries = java.util.Collections.unmodifiableList(this.usesSdkLibraries);
        this.configPreferences = java.util.Collections.unmodifiableList(this.configPreferences);
        this.reqFeatures = java.util.Collections.unmodifiableList(this.reqFeatures);
        this.featureGroups = java.util.Collections.unmodifiableList(this.featureGroups);
        this.usesPermissions = java.util.Collections.unmodifiableList(this.usesPermissions);
        this.usesSdkLibraries = java.util.Collections.unmodifiableList(this.usesSdkLibraries);
        this.implicitPermissions = java.util.Collections.unmodifiableSet(this.implicitPermissions);
        this.upgradeKeySets = java.util.Collections.unmodifiableSet(this.upgradeKeySets);
        this.keySetMapping = java.util.Collections.unmodifiableMap(this.keySetMapping);
        this.attributions = java.util.Collections.unmodifiableList(this.attributions);
        this.preferredActivityFilters = java.util.Collections.unmodifiableList(this.preferredActivityFilters);
        this.processes = java.util.Collections.unmodifiableMap(this.processes);
        this.mProperties = java.util.Collections.unmodifiableMap(this.mProperties);
        this.queriesIntents = java.util.Collections.unmodifiableList(this.queriesIntents);
        this.queriesPackages = java.util.Collections.unmodifiableList(this.queriesPackages);
        this.queriesProviders = java.util.Collections.unmodifiableSet(this.queriesProviders);
        this.mimeGroups = java.util.Collections.unmodifiableSet(this.mimeGroups);
        this.mKnownActivityEmbeddingCerts = java.util.Collections.unmodifiableSet(this.mKnownActivityEmbeddingCerts);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public long getLongVersionCode() {
        return android.content.pm.PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl removePermission(int i) {
        this.permissions.remove(i);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesOptionalLibrary(int i, java.lang.String str) {
        this.usesOptionalLibraries = com.android.internal.util.CollectionUtils.add(this.usesOptionalLibraries, i, android.text.TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl addUsesLibrary(int i, java.lang.String str) {
        this.usesLibraries = com.android.internal.util.CollectionUtils.add(this.usesLibraries, i, android.text.TextUtils.safeIntern(str));
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl removeUsesLibrary(java.lang.String str) {
        this.usesLibraries = com.android.internal.util.CollectionUtils.remove(this.usesLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl removeUsesOptionalLibrary(java.lang.String str) {
        this.usesOptionalLibraries = com.android.internal.util.CollectionUtils.remove(this.usesOptionalLibraries, str);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSigningDetails(android.content.pm.SigningDetails signingDetails) {
        this.signingDetails = signingDetails;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setRestrictUpdateHash(byte... bArr) {
        this.restrictUpdateHash = bArr;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPersistent(boolean z) {
        setBoolean(64L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDefaultToDeviceProtectedStorage(boolean z) {
        setBoolean(67108864L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setDirectBootAware(boolean z) {
        setBoolean(134217728L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl clearProtectedBroadcasts() {
        this.protectedBroadcasts.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl clearOriginalPackages() {
        this.originalPackages.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl clearAdoptPermissions() {
        this.adoptPermissions.clear();
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPath(java.lang.String str) {
        this.mPath = str;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPackageName(java.lang.String str) {
        this.packageName = android.text.TextUtils.safeIntern(str);
        int size = this.permissions.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.permissions.get(i), this.packageName);
        }
        int size2 = this.permissionGroups.size();
        for (int i2 = 0; i2 < size2; i2++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.permissionGroups.get(i2), this.packageName);
        }
        int size3 = this.activities.size();
        for (int i3 = 0; i3 < size3; i3++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.activities.get(i3), this.packageName);
        }
        int size4 = this.receivers.size();
        for (int i4 = 0; i4 < size4; i4++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.receivers.get(i4), this.packageName);
        }
        int size5 = this.providers.size();
        for (int i5 = 0; i5 < size5; i5++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.providers.get(i5), this.packageName);
        }
        int size6 = this.services.size();
        for (int i6 = 0; i6 < size6; i6++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.services.get(i6), this.packageName);
        }
        int size7 = this.instrumentations.size();
        for (int i7 = 0; i7 < size7; i7++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPackageName(this.instrumentations.get(i7), this.packageName);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setAllComponentsDirectBootAware(boolean z) {
        int size = this.activities.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setDirectBootAware(this.activities.get(i), z);
        }
        int size2 = this.receivers.size();
        for (int i2 = 0; i2 < size2; i2++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setDirectBootAware(this.receivers.get(i2), z);
        }
        int size3 = this.providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setDirectBootAware(this.providers.get(i3), z);
        }
        int size4 = this.services.size();
        for (int i4 = 0; i4 < size4; i4++) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setDirectBootAware(this.services.get(i4), z);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setBaseApkPath(java.lang.String str) {
        this.mBaseApkPath = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNativeLibraryDir(java.lang.String str) {
        this.nativeLibraryDir = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNativeLibraryRootDir(java.lang.String str) {
        this.nativeLibraryRootDir = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPrimaryCpuAbi(java.lang.String str) {
        this.primaryCpuAbi = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSecondaryCpuAbi(java.lang.String str) {
        this.secondaryCpuAbi = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSecondaryNativeLibraryDir(java.lang.String str) {
        this.secondaryNativeLibraryDir = android.text.TextUtils.safeIntern(str);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSplitCodePaths(java.lang.String[] strArr) {
        this.splitCodePaths = strArr;
        if (strArr != null) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                this.splitCodePaths[i] = android.text.TextUtils.safeIntern(this.splitCodePaths[i]);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl capPermissionPriorities() {
        for (int size = this.permissionGroups.size() - 1; size >= 0; size--) {
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setPriority(this.permissionGroups.get(size), 0);
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl markNotActivitiesAsNotExportedIfSingleUser() {
        int size = this.receivers.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = this.receivers.get(i);
            if ((1073741824 & parsedActivity.getFlags()) != 0) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setExported(parsedActivity, false);
            }
        }
        int size2 = this.services.size();
        for (int i2 = 0; i2 < size2; i2++) {
            com.android.internal.pm.pkg.component.ParsedService parsedService = this.services.get(i2);
            if ((parsedService.getFlags() & 1073741824) != 0) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setExported(parsedService, false);
            }
        }
        int size3 = this.providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = this.providers.get(i3);
            if ((parsedProvider.getFlags() & 1073741824) != 0) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setExported(parsedProvider, false);
            }
        }
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setCoreApp(boolean z) {
        return setBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_140_180, z);
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVersionCode(int i) {
        this.versionCode = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVersionCodeMajor(int i) {
        this.versionCodeMajor = i;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden, com.android.internal.pm.pkg.parsing.ParsingPackageHidden
    public android.content.pm.ApplicationInfo toAppInfoWithoutState() {
        android.content.pm.ApplicationInfo appInfoWithoutStateWithoutFlags = toAppInfoWithoutStateWithoutFlags();
        appInfoWithoutStateWithoutFlags.flags = this.mBaseAppInfoFlags;
        appInfoWithoutStateWithoutFlags.privateFlags = this.mBaseAppInfoPrivateFlags;
        appInfoWithoutStateWithoutFlags.privateFlagsExt = this.mBaseAppInfoPrivateFlagsExt;
        appInfoWithoutStateWithoutFlags.nativeLibraryDir = this.nativeLibraryDir;
        appInfoWithoutStateWithoutFlags.nativeLibraryRootDir = this.nativeLibraryRootDir;
        appInfoWithoutStateWithoutFlags.nativeLibraryRootRequiresIsa = this.nativeLibraryRootRequiresIsa;
        appInfoWithoutStateWithoutFlags.primaryCpuAbi = this.primaryCpuAbi;
        appInfoWithoutStateWithoutFlags.secondaryCpuAbi = this.secondaryCpuAbi;
        appInfoWithoutStateWithoutFlags.secondaryNativeLibraryDir = this.secondaryNativeLibraryDir;
        appInfoWithoutStateWithoutFlags.seInfoUser = com.android.internal.pm.pkg.SEInfoUtil.COMPLETE_STR;
        appInfoWithoutStateWithoutFlags.uid = this.uid;
        return appInfoWithoutStateWithoutFlags;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        sForBoolean.parcel(this.supportsSmallScreens, parcel, i);
        sForBoolean.parcel(this.supportsNormalScreens, parcel, i);
        sForBoolean.parcel(this.supportsLargeScreens, parcel, i);
        sForBoolean.parcel(this.supportsExtraLargeScreens, parcel, i);
        sForBoolean.parcel(this.resizeable, parcel, i);
        sForBoolean.parcel(this.anyDensity, parcel, i);
        parcel.writeInt(this.versionCode);
        parcel.writeInt(this.versionCodeMajor);
        parcel.writeInt(this.baseRevisionCode);
        sForInternedString.parcel(this.versionName, parcel, i);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString(this.compileSdkVersionCodeName);
        sForInternedString.parcel(this.packageName, parcel, i);
        parcel.writeString(this.mBaseApkPath);
        parcel.writeString(this.restrictedAccountType);
        parcel.writeString(this.requiredAccountType);
        parcel.writeString(this.mEmergencyInstaller);
        sForInternedString.parcel(this.overlayTarget, parcel, i);
        parcel.writeString(this.overlayTargetOverlayableName);
        parcel.writeString(this.overlayCategory);
        parcel.writeInt(this.overlayPriority);
        sForInternedStringValueMap.parcel(this.overlayables, parcel, i);
        sForInternedString.parcel(this.sdkLibraryName, parcel, i);
        parcel.writeInt(this.sdkLibVersionMajor);
        sForInternedString.parcel(this.staticSharedLibraryName, parcel, i);
        parcel.writeLong(this.staticSharedLibVersion);
        sForInternedStringList.parcel(this.libraryNames, parcel, i);
        sForInternedStringList.parcel(this.usesLibraries, parcel, i);
        sForInternedStringList.parcel(this.usesOptionalLibraries, parcel, i);
        sForInternedStringList.parcel(this.usesNativeLibraries, parcel, i);
        sForInternedStringList.parcel(this.usesOptionalNativeLibraries, parcel, i);
        sForInternedStringList.parcel(this.usesStaticLibraries, parcel, i);
        parcel.writeLongArray(this.usesStaticLibrariesVersions);
        if (this.usesStaticLibrariesCertDigests == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(this.usesStaticLibrariesCertDigests.length);
            for (int i2 = 0; i2 < this.usesStaticLibrariesCertDigests.length; i2++) {
                parcel.writeStringArray(this.usesStaticLibrariesCertDigests[i2]);
            }
        }
        sForInternedStringList.parcel(this.usesSdkLibraries, parcel, i);
        parcel.writeLongArray(this.usesSdkLibrariesVersionsMajor);
        if (this.usesSdkLibrariesCertDigests == null) {
            parcel.writeInt(-1);
        } else {
            parcel.writeInt(this.usesSdkLibrariesCertDigests.length);
            for (int i3 = 0; i3 < this.usesSdkLibrariesCertDigests.length; i3++) {
                parcel.writeStringArray(this.usesSdkLibrariesCertDigests[i3]);
            }
        }
        parcel.writeBooleanArray(this.usesSdkLibrariesOptional);
        sForInternedString.parcel(this.sharedUserId, parcel, i);
        parcel.writeInt(this.sharedUserLabel);
        parcel.writeTypedList(this.configPreferences);
        parcel.writeTypedList(this.reqFeatures);
        parcel.writeTypedList(this.featureGroups);
        parcel.writeByteArray(this.restrictUpdateHash);
        parcel.writeStringList(this.originalPackages);
        sForInternedStringList.parcel(this.adoptPermissions, parcel, i);
        sForInternedStringSet.parcel(this.requestedPermissions, parcel, i);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.usesPermissions);
        sForInternedStringSet.parcel(this.implicitPermissions, parcel, i);
        sForStringSet.parcel(this.upgradeKeySets, parcel, i);
        com.android.internal.pm.pkg.parsing.ParsingPackageUtils.writeKeySetMapping(parcel, this.keySetMapping);
        sForInternedStringList.parcel(this.protectedBroadcasts, parcel, i);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.activities);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.apexSystemServices);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.receivers);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.services);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.providers);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.attributions);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.permissions);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.permissionGroups);
        com.android.internal.pm.pkg.parsing.ParsingUtils.writeParcelableList(parcel, this.instrumentations);
        sForIntentInfoPairs.parcel(this.preferredActivityFilters, parcel, i);
        parcel.writeMap(this.processes);
        parcel.writeBundle(this.metaData);
        sForInternedString.parcel(this.volumeUuid, parcel, i);
        parcel.writeParcelable(this.signingDetails, i);
        parcel.writeString(this.mPath);
        parcel.writeTypedList(this.queriesIntents, i);
        sForInternedStringList.parcel(this.queriesPackages, parcel, i);
        sForInternedStringSet.parcel(this.queriesProviders, parcel, i);
        parcel.writeString(this.appComponentFactory);
        parcel.writeString(this.backupAgentName);
        parcel.writeInt(this.banner);
        parcel.writeInt(this.category);
        parcel.writeString(this.classLoaderName);
        parcel.writeString(this.className);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.dataExtractionRules);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.installLocation);
        parcel.writeInt(this.labelRes);
        parcel.writeInt(this.largestWidthLimitDp);
        parcel.writeInt(this.logo);
        parcel.writeString(this.manageSpaceActivityName);
        parcel.writeFloat(this.maxAspectRatio);
        parcel.writeFloat(this.minAspectRatio);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.maxSdkVersion);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeCharSequence(this.nonLocalizedLabel);
        parcel.writeString(this.permission);
        parcel.writeString(this.processName);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeString(this.taskAffinity);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.uiOptions);
        parcel.writeString(this.zygotePreloadName);
        parcel.writeStringArray(this.splitClassLoaderNames);
        parcel.writeStringArray(this.splitCodePaths);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeIntArray(this.splitFlags);
        parcel.writeStringArray(this.splitNames);
        parcel.writeIntArray(this.splitRevisionCodes);
        sForBoolean.parcel(this.resizeableActivity, parcel, i);
        parcel.writeInt(this.autoRevokePermissions);
        sForInternedStringSet.parcel(this.mimeGroups, parcel, i);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeSparseIntArray(this.minExtensionVersions);
        parcel.writeMap(this.mProperties);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
        sForBoolean.parcel(this.requestRawExternalStorageAccess, parcel, i);
        parcel.writeInt(this.mLocaleConfigRes);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, i);
        sForInternedString.parcel(this.manifestPackageName, parcel, i);
        parcel.writeString(this.nativeLibraryDir);
        parcel.writeString(this.nativeLibraryRootDir);
        parcel.writeBoolean(this.nativeLibraryRootRequiresIsa);
        sForInternedString.parcel(this.primaryCpuAbi, parcel, i);
        sForInternedString.parcel(this.secondaryCpuAbi, parcel, i);
        parcel.writeString(this.secondaryNativeLibraryDir);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.mBooleans);
        parcel.writeLong(this.mBooleans2);
        parcel.writeBoolean(this.mAllowCrossUidActivitySwitchFromBelow);
        parcel.writeBoolean(this.mAppMetadataFileInApk);
    }

    public PackageImpl(android.os.Parcel parcel) {
        this(parcel, null);
    }

    public PackageImpl(android.os.Parcel parcel, com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        this.usesLibraries = java.util.Collections.emptyList();
        this.usesOptionalLibraries = java.util.Collections.emptyList();
        this.usesNativeLibraries = java.util.Collections.emptyList();
        this.usesOptionalNativeLibraries = java.util.Collections.emptyList();
        this.originalPackages = java.util.Collections.emptyList();
        this.adoptPermissions = java.util.Collections.emptyList();
        this.requestedPermissions = java.util.Collections.emptySet();
        this.protectedBroadcasts = java.util.Collections.emptyList();
        this.activities = java.util.Collections.emptyList();
        this.apexSystemServices = java.util.Collections.emptyList();
        this.receivers = java.util.Collections.emptyList();
        this.services = java.util.Collections.emptyList();
        this.providers = java.util.Collections.emptyList();
        this.permissions = java.util.Collections.emptyList();
        this.permissionGroups = java.util.Collections.emptyList();
        this.instrumentations = java.util.Collections.emptyList();
        this.overlayables = java.util.Collections.emptyMap();
        this.libraryNames = java.util.Collections.emptyList();
        this.usesStaticLibraries = java.util.Collections.emptyList();
        this.usesSdkLibraries = java.util.Collections.emptyList();
        this.configPreferences = java.util.Collections.emptyList();
        this.reqFeatures = java.util.Collections.emptyList();
        this.featureGroups = java.util.Collections.emptyList();
        this.usesPermissions = java.util.Collections.emptyList();
        this.implicitPermissions = java.util.Collections.emptySet();
        this.upgradeKeySets = java.util.Collections.emptySet();
        this.keySetMapping = java.util.Collections.emptyMap();
        this.attributions = java.util.Collections.emptyList();
        this.preferredActivityFilters = java.util.Collections.emptyList();
        this.processes = java.util.Collections.emptyMap();
        this.mProperties = java.util.Collections.emptyMap();
        this.signingDetails = android.content.pm.SigningDetails.UNKNOWN;
        this.queriesIntents = java.util.Collections.emptyList();
        this.queriesPackages = java.util.Collections.emptyList();
        this.queriesProviders = java.util.Collections.emptySet();
        this.category = -1;
        this.installLocation = -1;
        this.minSdkVersion = 1;
        this.maxSdkVersion = Integer.MAX_VALUE;
        this.targetSdkVersion = 0;
        this.mimeGroups = java.util.Collections.emptySet();
        this.mBooleans = android.hardware.tv.tuner.FrontendInnerFec.FEC_18_30;
        this.mBooleans2 = 4L;
        this.mKnownActivityEmbeddingCerts = java.util.Collections.emptySet();
        this.mAppMetadataFileInApk = false;
        this.uid = -1;
        this.mCallback = callback;
        java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
        this.supportsSmallScreens = sForBoolean.unparcel(parcel);
        this.supportsNormalScreens = sForBoolean.unparcel(parcel);
        this.supportsLargeScreens = sForBoolean.unparcel(parcel);
        this.supportsExtraLargeScreens = sForBoolean.unparcel(parcel);
        this.resizeable = sForBoolean.unparcel(parcel);
        this.anyDensity = sForBoolean.unparcel(parcel);
        this.versionCode = parcel.readInt();
        this.versionCodeMajor = parcel.readInt();
        this.baseRevisionCode = parcel.readInt();
        this.versionName = sForInternedString.unparcel(parcel);
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodeName = parcel.readString();
        this.packageName = sForInternedString.unparcel(parcel);
        this.mBaseApkPath = parcel.readString();
        this.restrictedAccountType = parcel.readString();
        this.requiredAccountType = parcel.readString();
        this.mEmergencyInstaller = parcel.readString();
        this.overlayTarget = sForInternedString.unparcel(parcel);
        this.overlayTargetOverlayableName = parcel.readString();
        this.overlayCategory = parcel.readString();
        this.overlayPriority = parcel.readInt();
        this.overlayables = sForInternedStringValueMap.unparcel(parcel);
        this.sdkLibraryName = sForInternedString.unparcel(parcel);
        this.sdkLibVersionMajor = parcel.readInt();
        this.staticSharedLibraryName = sForInternedString.unparcel(parcel);
        this.staticSharedLibVersion = parcel.readLong();
        this.libraryNames = sForInternedStringList.unparcel(parcel);
        this.usesLibraries = sForInternedStringList.unparcel(parcel);
        this.usesOptionalLibraries = sForInternedStringList.unparcel(parcel);
        this.usesNativeLibraries = sForInternedStringList.unparcel(parcel);
        this.usesOptionalNativeLibraries = sForInternedStringList.unparcel(parcel);
        this.usesStaticLibraries = sForInternedStringList.unparcel(parcel);
        this.usesStaticLibrariesVersions = parcel.createLongArray();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.usesStaticLibrariesCertDigests = new java.lang.String[readInt][];
            for (int i = 0; i < readInt; i++) {
                this.usesStaticLibrariesCertDigests[i] = sForInternedStringArray.unparcel(parcel);
            }
        }
        this.usesSdkLibraries = sForInternedStringList.unparcel(parcel);
        this.usesSdkLibrariesVersionsMajor = parcel.createLongArray();
        int readInt2 = parcel.readInt();
        if (readInt2 >= 0) {
            this.usesSdkLibrariesCertDigests = new java.lang.String[readInt2][];
            for (int i2 = 0; i2 < readInt2; i2++) {
                this.usesSdkLibrariesCertDigests[i2] = sForInternedStringArray.unparcel(parcel);
            }
        }
        this.usesSdkLibrariesOptional = parcel.createBooleanArray();
        this.sharedUserId = sForInternedString.unparcel(parcel);
        this.sharedUserLabel = parcel.readInt();
        this.configPreferences = parcel.createTypedArrayList(android.content.pm.ConfigurationInfo.CREATOR);
        this.reqFeatures = parcel.createTypedArrayList(android.content.pm.FeatureInfo.CREATOR);
        this.featureGroups = parcel.createTypedArrayList(android.content.pm.FeatureGroupInfo.CREATOR);
        this.restrictUpdateHash = parcel.createByteArray();
        this.originalPackages = parcel.createStringArrayList();
        this.adoptPermissions = sForInternedStringList.unparcel(parcel);
        this.requestedPermissions = sForInternedStringSet.unparcel(parcel);
        this.usesPermissions = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl.CREATOR);
        this.implicitPermissions = sForInternedStringSet.unparcel(parcel);
        this.upgradeKeySets = sForStringSet.unparcel(parcel);
        this.keySetMapping = com.android.internal.pm.pkg.parsing.ParsingPackageUtils.readKeySetMapping(parcel);
        this.protectedBroadcasts = sForInternedStringList.unparcel(parcel);
        this.activities = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedActivityImpl.CREATOR);
        this.apexSystemServices = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl.CREATOR);
        this.receivers = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedActivityImpl.CREATOR);
        this.services = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedServiceImpl.CREATOR);
        this.providers = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedProviderImpl.CREATOR);
        this.attributions = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedAttributionImpl.CREATOR);
        this.permissions = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedPermissionImpl.CREATOR);
        this.permissionGroups = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl.CREATOR);
        this.instrumentations = com.android.internal.pm.pkg.parsing.ParsingUtils.createTypedInterfaceList(parcel, com.android.internal.pm.pkg.component.ParsedInstrumentationImpl.CREATOR);
        this.preferredActivityFilters = sForIntentInfoPairs.unparcel(parcel);
        this.processes = parcel.readHashMap(com.android.internal.pm.pkg.component.ParsedProcessImpl.class.getClassLoader());
        this.metaData = parcel.readBundle(classLoader);
        this.volumeUuid = sForInternedString.unparcel(parcel);
        this.signingDetails = (android.content.pm.SigningDetails) parcel.readParcelable(classLoader, android.content.pm.SigningDetails.class);
        this.mPath = parcel.readString();
        this.queriesIntents = parcel.createTypedArrayList(android.content.Intent.CREATOR);
        this.queriesPackages = sForInternedStringList.unparcel(parcel);
        this.queriesProviders = sForInternedStringSet.unparcel(parcel);
        this.appComponentFactory = parcel.readString();
        this.backupAgentName = parcel.readString();
        this.banner = parcel.readInt();
        this.category = parcel.readInt();
        this.classLoaderName = parcel.readString();
        this.className = parcel.readString();
        this.compatibleWidthLimitDp = parcel.readInt();
        this.descriptionRes = parcel.readInt();
        this.fullBackupContent = parcel.readInt();
        this.dataExtractionRules = parcel.readInt();
        this.iconRes = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.labelRes = parcel.readInt();
        this.largestWidthLimitDp = parcel.readInt();
        this.logo = parcel.readInt();
        this.manageSpaceActivityName = parcel.readString();
        this.maxAspectRatio = parcel.readFloat();
        this.minAspectRatio = parcel.readFloat();
        this.minSdkVersion = parcel.readInt();
        this.maxSdkVersion = parcel.readInt();
        this.networkSecurityConfigRes = parcel.readInt();
        this.nonLocalizedLabel = parcel.readCharSequence();
        this.permission = parcel.readString();
        this.processName = parcel.readString();
        this.requiresSmallestWidthDp = parcel.readInt();
        this.roundIconRes = parcel.readInt();
        this.targetSandboxVersion = parcel.readInt();
        this.targetSdkVersion = parcel.readInt();
        this.taskAffinity = parcel.readString();
        this.theme = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.zygotePreloadName = parcel.readString();
        this.splitClassLoaderNames = parcel.createStringArray();
        this.splitCodePaths = parcel.createStringArray();
        this.splitDependencies = parcel.readSparseArray(classLoader);
        this.splitFlags = parcel.createIntArray();
        this.splitNames = parcel.createStringArray();
        this.splitRevisionCodes = parcel.createIntArray();
        this.resizeableActivity = sForBoolean.unparcel(parcel);
        this.autoRevokePermissions = parcel.readInt();
        this.mimeGroups = sForInternedStringSet.unparcel(parcel);
        this.gwpAsanMode = parcel.readInt();
        this.minExtensionVersions = parcel.readSparseIntArray();
        this.mProperties = parcel.readHashMap(classLoader);
        this.memtagMode = parcel.readInt();
        this.nativeHeapZeroInitialized = parcel.readInt();
        this.requestRawExternalStorageAccess = sForBoolean.unparcel(parcel);
        this.mLocaleConfigRes = parcel.readInt();
        this.mKnownActivityEmbeddingCerts = sForStringSet.unparcel(parcel);
        this.manifestPackageName = sForInternedString.unparcel(parcel);
        this.nativeLibraryDir = parcel.readString();
        this.nativeLibraryRootDir = parcel.readString();
        this.nativeLibraryRootRequiresIsa = parcel.readBoolean();
        this.primaryCpuAbi = sForInternedString.unparcel(parcel);
        this.secondaryCpuAbi = sForInternedString.unparcel(parcel);
        this.secondaryNativeLibraryDir = parcel.readString();
        this.uid = parcel.readInt();
        this.mBooleans = parcel.readLong();
        this.mBooleans2 = parcel.readLong();
        this.mAllowCrossUidActivitySwitchFromBelow = parcel.readBoolean();
        this.mAppMetadataFileInApk = parcel.readBoolean();
        assignDerivedFields();
        assignDerivedFields2();
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getManifestPackageName() {
        return this.manifestPackageName;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isStub() {
        return getBoolean2(1L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getNativeLibraryDir() {
        return this.nativeLibraryDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getNativeLibraryRootDir() {
        return this.nativeLibraryRootDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isNativeLibraryRootRequiresIsa() {
        return this.nativeLibraryRootRequiresIsa;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public java.lang.String getPrimaryCpuAbi() {
        return this.primaryCpuAbi;
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public java.lang.String getSecondaryCpuAbi() {
        return this.secondaryCpuAbi;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public java.lang.String getSecondaryNativeLibraryDir() {
        return this.secondaryNativeLibraryDir;
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isCoreApp() {
        return getBoolean(android.hardware.tv.tuner.FrontendInnerFec.FEC_140_180);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isSystem() {
        return getBoolean(9007199254740992L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isUpdatableSystem() {
        return getBoolean2(4L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isFactoryTest() {
        return getBoolean(18014398509481984L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isApex() {
        return getBoolean2(2L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isSystemExt() {
        return getBoolean(72057594037927936L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isPrivileged() {
        return getBoolean(144115188075855872L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isOem() {
        return getBoolean(288230376151711744L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isVendor() {
        return getBoolean(576460752303423488L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isProduct() {
        return getBoolean(1152921504606846976L);
    }

    @Override // com.android.internal.pm.parsing.pkg.AndroidPackageHidden
    public boolean isOdm() {
        return getBoolean(2305843009213693952L);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public boolean isSignedWithPlatformKey() {
        return getBoolean(android.content.Context.BIND_EXTERNAL_SERVICE_LONG);
    }

    @Override // com.android.server.pm.pkg.AndroidPackage
    public int getUid() {
        return this.uid;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setStub(boolean z) {
        setBoolean2(1L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setNativeLibraryRootRequiresIsa(boolean z) {
        this.nativeLibraryRootRequiresIsa = z;
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSystem(boolean z) {
        setBoolean(9007199254740992L, z);
        return this;
    }

    @Override // com.android.internal.pm.pkg.parsing.ParsingPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUpdatableSystem(boolean z) {
        return setBoolean2(4L, z);
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setFactoryTest(boolean z) {
        setBoolean(18014398509481984L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setApex(boolean z) {
        setBoolean2(2L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSystemExt(boolean z) {
        setBoolean(72057594037927936L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setPrivileged(boolean z) {
        setBoolean(144115188075855872L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOem(boolean z) {
        setBoolean(288230376151711744L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setVendor(boolean z) {
        setBoolean(576460752303423488L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setProduct(boolean z) {
        setBoolean(1152921504606846976L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setOdm(boolean z) {
        setBoolean(2305843009213693952L, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setSignedWithPlatformKey(boolean z) {
        setBoolean(android.content.Context.BIND_EXTERNAL_SERVICE_LONG, z);
        return this;
    }

    @Override // com.android.internal.pm.parsing.pkg.ParsedPackage
    public com.android.internal.pm.parsing.pkg.PackageImpl setUid(int i) {
        this.uid = i;
        return this;
    }

    public java.lang.String getBaseAppDataCredentialProtectedDirForSystemUser() {
        return this.mBaseAppDataCredentialProtectedDirForSystemUser;
    }

    public java.lang.String getBaseAppDataDeviceProtectedDirForSystemUser() {
        return this.mBaseAppDataDeviceProtectedDirForSystemUser;
    }

    private static class Booleans {
        private static final long ALLOW_AUDIO_PLAYBACK_CAPTURE = 2147483648L;
        private static final long ALLOW_BACKUP = 4;
        private static final long ALLOW_CLEAR_USER_DATA = 2048;
        private static final long ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 1073741824;
        private static final long ALLOW_NATIVE_HEAP_POINTER_TAGGING = 68719476736L;
        private static final long ALLOW_TASK_REPARENTING = 1024;
        private static final long ATTRIBUTIONS_ARE_USER_VISIBLE = 140737488355328L;
        private static final long BACKUP_IN_FOREGROUND = 16777216;
        private static final long CANT_SAVE_STATE = 34359738368L;
        private static final long CORE_APP = 4503599627370496L;
        private static final long CROSS_PROFILE = 8796093022208L;
        private static final long DEBUGGABLE = 128;
        private static final long DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 67108864;
        private static final long DIRECT_BOOT_AWARE = 134217728;
        private static final long DISALLOW_PROFILING = 35184372088832L;
        private static final long ENABLED = 17592186044416L;
        private static final long ENABLE_ON_BACK_INVOKED_CALLBACK = 1125899906842624L;
        private static final long EXTERNAL_STORAGE = 1;
        private static final long EXTRACT_NATIVE_LIBS = 131072;
        private static final long FACTORY_TEST = 18014398509481984L;
        private static final long FORCE_QUERYABLE = 4398046511104L;
        private static final long FULL_BACKUP_ONLY = 32;
        private static final long GAME = 262144;
        private static final long HARDWARE_ACCELERATED = 2;
        private static final long HAS_CODE = 512;
        private static final long HAS_DOMAIN_URLS = 4194304;
        private static final long HAS_FRAGILE_USER_DATA = 17179869184L;
        private static final long ISOLATED_SPLIT_LOADING = 2097152;
        private static final long KILL_AFTER_RESTORE = 8;
        private static final long LARGE_HEAP = 4096;
        private static final long LEAVING_SHARED_UID = 2251799813685248L;
        private static final long MULTI_ARCH = 65536;
        private static final long NATIVE_LIBRARY_ROOT_REQUIRES_ISA = Long.MIN_VALUE;
        private static final long ODM = 2305843009213693952L;
        private static final long OEM = 288230376151711744L;
        private static final long OVERLAY = 1048576;
        private static final long OVERLAY_IS_STATIC = 549755813888L;
        private static final long PARTIALLY_DIRECT_BOOT_AWARE = 268435456;
        private static final long PERSISTENT = 64;
        private static final long PRESERVE_LEGACY_EXTERNAL_STORAGE = 137438953472L;
        private static final long PRIVILEGED = 144115188075855872L;
        private static final long PRODUCT = 1152921504606846976L;
        private static final long PROFILEABLE_BY_SHELL = 8388608;
        private static final long REQUEST_FOREGROUND_SERVICE_EXEMPTION = 70368744177664L;
        private static final long REQUEST_LEGACY_EXTERNAL_STORAGE = 4294967296L;
        private static final long REQUIRED_FOR_ALL_USERS = 274877906944L;
        private static final long RESET_ENABLED_SETTINGS_ON_APP_DATA_CLEARED = 281474976710656L;
        private static final long RESIZEABLE_ACTIVITY_VIA_SDK_VERSION = 536870912;
        private static final long RESTORE_ANY_VERSION = 16;
        private static final long SDK_LIBRARY = 562949953421312L;
        private static final long SIGNED_WITH_PLATFORM_KEY = 4611686018427387904L;
        private static final long STATIC_SHARED_LIBRARY = 524288;
        private static final long SUPPORTS_RTL = 16384;
        private static final long SYSTEM = 9007199254740992L;
        private static final long SYSTEM_EXT = 72057594037927936L;
        private static final long TEST_ONLY = 32768;
        private static final long USES_CLEARTEXT_TRAFFIC = 8192;
        private static final long USES_NON_SDK_API = 8589934592L;
        private static final long USE_32_BIT_ABI = 1099511627776L;
        private static final long USE_EMBEDDED_DEX = 33554432;
        private static final long VENDOR = 576460752303423488L;
        private static final long VISIBLE_TO_INSTANT_APPS = 2199023255552L;
        private static final long VM_SAFE_MODE = 256;

        public @interface Flags {
        }

        private Booleans() {
        }
    }

    private static class Booleans2 {
        private static final long APEX = 2;
        private static final long STUB = 1;
        private static final long UPDATABLE_SYSTEM = 4;

        public @interface Flags {
        }

        private Booleans2() {
        }
    }
}
