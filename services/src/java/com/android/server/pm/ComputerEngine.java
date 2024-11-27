package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
/* loaded from: classes2.dex */
public class ComputerEngine implements com.android.server.pm.Computer {
    private final com.android.server.pm.ApexManager mApexManager;
    private final java.lang.String mAppPredictionServicePackage;
    private final com.android.server.pm.AppsFilterSnapshot mAppsFilter;
    private final com.android.server.pm.BackgroundDexOptService mBackgroundDexOptService;
    private final com.android.server.pm.CompilerStats mCompilerStats;
    private final com.android.server.pm.resolution.ComponentResolverApi mComponentResolver;
    private final android.content.Context mContext;
    private final com.android.server.pm.CrossProfileIntentResolverEngine mCrossProfileIntentResolverEngine;
    private final com.android.server.pm.DefaultAppProvider mDefaultAppProvider;
    private final com.android.server.pm.dex.DexManager mDexManager;
    private final com.android.server.pm.verify.domain.DomainVerificationManagerInternal mDomainVerificationManager;
    private final android.content.pm.PackageManagerInternal.ExternalSourcesPolicy mExternalSourcesPolicy;
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer> mFrozenPackages;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final android.content.pm.ResolveInfo mInstantAppInstallerInfo;
    private final com.android.server.pm.InstantAppRegistry mInstantAppRegistry;
    private final com.android.server.pm.InstantAppResolverConnection mInstantAppResolverConnection;
    private final com.android.server.utils.WatchedArrayMap<android.content.ComponentName, com.android.internal.pm.pkg.component.ParsedInstrumentation> mInstrumentation;
    private final com.android.server.utils.WatchedSparseIntArray mIsolatedOwners;
    private final android.content.pm.ApplicationInfo mLocalAndroidApplication;
    private final android.content.pm.ActivityInfo mLocalInstantAppInstallerActivity;
    private final android.content.ComponentName mLocalResolveComponentName;
    private final com.android.server.pm.PackageDexOptimizer mPackageDexOptimizer;
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.pkg.AndroidPackage> mPackages;
    private final com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManager;
    private final android.content.pm.ActivityInfo mResolveActivity;
    protected final com.android.server.pm.PackageManagerService mService;
    protected final com.android.server.pm.ComputerEngine.Settings mSettings;
    private final com.android.server.pm.SharedLibrariesRead mSharedLibraries;
    private int mUsed = 0;
    private final com.android.server.pm.UserManagerService mUserManager;
    private final int mVersion;
    private final com.android.server.utils.WatchedSparseBooleanArray mWebInstantAppsDisabled;
    private static final java.util.Comparator<android.content.pm.ProviderInfo> sProviderInitOrderSorter = new java.util.Comparator() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda1
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$static$0;
            lambda$static$0 = com.android.server.pm.ComputerEngine.lambda$static$0((android.content.pm.ProviderInfo) obj, (android.content.pm.ProviderInfo) obj2);
            return lambda$static$0;
        }
    };
    private static final android.content.pm.Signature MICROG_FAKE_SIGNATURE = new android.content.pm.Signature("308204433082032ba003020102020900c2e08746644a308d300d06092a864886f70d01010405003074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964301e170d3038303832313233313333345a170d3336303130373233313333345a3074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f696430820120300d06092a864886f70d01010105000382010d00308201080282010100ab562e00d83ba208ae0a966f124e29da11f2ab56d08f58e2cca91303e9b754d372f640a71b1dcb130967624e4656a7776a92193db2e5bfb724a91e77188b0e6a47a43b33d9609b77183145ccdf7b2e586674c9e1565b1f4c6a5955bff251a63dabf9c55c27222252e875e4f8154a645f897168c0b1bfc612eabf785769bb34aa7984dc7e2ea2764cae8307d8c17154d7ee5f64a51a44a602c249054157dc02cd5f5c0e55fbef8519fbe327f0b1511692c5a06f19d18385f5c4dbc2d6b93f68cc2979c70e18ab93866b3bd5db8999552a0e3b4c99df58fb918bedc182ba35e003c1b4b10dd244a8ee24fffd333872ab5221985edab0fc0d0b145b6aa192858e79020103a381d93081d6301d0603551d0e04160414c77d8cc2211756259a7fd382df6be398e4d786a53081a60603551d2304819e30819b8014c77d8cc2211756259a7fd382df6be398e4d786a5a178a4763074310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e205669657731143012060355040a130b476f6f676c6520496e632e3110300e060355040b1307416e64726f69643110300e06035504031307416e64726f6964820900c2e08746644a308d300c0603551d13040530030101ff300d06092a864886f70d010104050003820101006dd252ceef85302c360aaace939bcff2cca904bb5d7a1661f8ae46b2994204d0ff4a68c7ed1a531ec4595a623ce60763b167297a7ae35712c407f208f0cb109429124d7b106219c084ca3eb3f9ad5fb871ef92269a8be28bf16d44c8d9a08e6cb2f005bb3fe2cb96447e868e731076ad45b33f6009ea19c161e62641aa99271dfd5228c5c587875ddb7f452758d661f6cc0cccb7352e424cc4365c523532f7325137593c4ae341f4db41edda0d0b1071a7c440f0fe9ea01cb627ca674369d084bd2fd911ff06cdbf2cfa10dc0f893ae35762919048c7efc64c7144178342f70581c9de573af55b390dd7fdb9418631895d5f759f30112687ff621410c069308a");
    private static final android.content.pm.Signature MICROG_REAL_SIGNATURE = new android.content.pm.Signature("308202ed308201d5a003020102020426ffa009300d06092a864886f70d01010b05003027310b300906035504061302444531183016060355040a130f4e4f47415050532050726f6a656374301e170d3132313030363132303533325a170d3337303933303132303533325a3027310b300906035504061302444531183016060355040a130f4e4f47415050532050726f6a65637430820122300d06092a864886f70d01010105000382010f003082010a02820101009a8d2a5336b0eaaad89ce447828c7753b157459b79e3215dc962ca48f58c2cd7650df67d2dd7bda0880c682791f32b35c504e43e77b43c3e4e541f86e35a8293a54fb46e6b16af54d3a4eda458f1a7c8bc1b7479861ca7043337180e40079d9cdccb7e051ada9b6c88c9ec635541e2ebf0842521c3024c826f6fd6db6fd117c74e859d5af4db04448965ab5469b71ce719939a06ef30580f50febf96c474a7d265bb63f86a822ff7b643de6b76e966a18553c2858416cf3309dd24278374bdd82b4404ef6f7f122cec93859351fc6e5ea947e3ceb9d67374fe970e593e5cd05c905e1d24f5a5484f4aadef766e498adf64f7cf04bddd602ae8137b6eea40722d0203010001a321301f301d0603551d0e04160414110b7aa9ebc840b20399f69a431f4dba6ac42a64300d06092a864886f70d01010b0500038201010007c32ad893349cf86952fb5a49cfdc9b13f5e3c800aece77b2e7e0e9c83e34052f140f357ec7e6f4b432dc1ed542218a14835acd2df2deea7efd3fd5e8f1c34e1fb39ec6a427c6e6f4178b609b369040ac1f8844b789f3694dc640de06e44b247afed11637173f36f5886170fafd74954049858c6096308fc93c1bc4dd5685fa7a1f982a422f2a3b36baa8c9500474cf2af91c39cbec1bc898d10194d368aa5e91f1137ec115087c31962d8f76cd120d28c249cf76f4c70f5baa08c70a7234ce4123be080cee789477401965cfe537b924ef36747e8caca62dfefdd1a6288dcb1c4fd2aaa6131a7ad254e9742022cfd597d2ca5c660ce9e41ff537e5a4041e37");

    private static native boolean isDebuggable();

    protected class Settings {

        @android.annotation.NonNull
        private final com.android.server.pm.Settings mSettings;

        public android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getPackages() {
            return this.mSettings.getPackagesLocked().untrackedStorage();
        }

        public android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getDisabledSystemPackages() {
            return this.mSettings.getDisabledSystemPackagesLocked().untrackedStorage();
        }

        public Settings(@android.annotation.NonNull com.android.server.pm.Settings settings) {
            this.mSettings = settings;
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageStateInternal getPackage(@android.annotation.NonNull java.lang.String str) {
            return this.mSettings.getPackageLPr(str);
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.PackageStateInternal getDisabledSystemPkg(@android.annotation.NonNull java.lang.String str) {
            return this.mSettings.getDisabledSystemPkgLPr(str);
        }

        public boolean isEnabledAndMatch(android.content.pm.ComponentInfo componentInfo, int i, int i2) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackage(componentInfo.packageName);
            if (packageStateInternal == null) {
                return false;
            }
            return com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i2), componentInfo, i);
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public boolean isEnabledAndMatch(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, long j, int i) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackage(parsedMainComponent.getPackageName());
            if (packageStateInternal == null) {
                return false;
            }
            return com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageStateInternal.getUserStateOrDefault(i), packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedMainComponent, j);
        }

        @android.annotation.Nullable
        public com.android.server.pm.CrossProfileIntentResolver getCrossProfileIntentResolver(int i) {
            return this.mSettings.getCrossProfileIntentResolver(i);
        }

        @android.annotation.Nullable
        public com.android.server.pm.SettingBase getSettingBase(int i) {
            return this.mSettings.getSettingLPr(i);
        }

        @android.annotation.Nullable
        public java.lang.String getRenamedPackageLPr(java.lang.String str) {
            return this.mSettings.getRenamedPackageLPr(str);
        }

        @android.annotation.Nullable
        public com.android.server.pm.PersistentPreferredIntentResolver getPersistentPreferredActivities(int i) {
            return this.mSettings.getPersistentPreferredActivities(i);
        }

        public void dumpVersionLPr(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            this.mSettings.dumpVersionLPr(indentingPrintWriter);
        }

        public void dumpPreferred(java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState, java.lang.String str) {
            this.mSettings.dumpPreferred(printWriter, dumpState, str);
        }

        public void writePreferredActivitiesLPr(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i, boolean z) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
            this.mSettings.writePreferredActivitiesLPr(typedXmlSerializer, i, z);
        }

        public com.android.server.pm.PreferredIntentResolver getPreferredActivities(int i) {
            return this.mSettings.getPreferredActivities(i);
        }

        @android.annotation.Nullable
        public com.android.server.pm.SharedUserSetting getSharedUserFromId(java.lang.String str) {
            try {
                return this.mSettings.getSharedUserLPw(str, 0, 0, false);
            } catch (com.android.server.pm.PackageManagerException e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public boolean getBlockUninstall(int i, @android.annotation.NonNull java.lang.String str) {
            return this.mSettings.getBlockUninstallLPr(i, str);
        }

        public int getApplicationEnabledSetting(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
            return this.mSettings.getApplicationEnabledSettingLPr(str, i);
        }

        public int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
            return this.mSettings.getComponentEnabledSettingLPr(componentName, i);
        }

        @android.annotation.NonNull
        public com.android.server.pm.KeySetManagerService getKeySetManagerService() {
            return this.mSettings.getKeySetManagerService();
        }

        @android.annotation.NonNull
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.SharedUserApi> getSharedUsers() {
            return this.mSettings.getSharedUsersLocked().untrackedStorage();
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.SharedUserApi getSharedUserFromPackageName(java.lang.String str) {
            return this.mSettings.getSharedUserSettingLPr(str);
        }

        @android.annotation.Nullable
        public com.android.server.pm.pkg.SharedUserApi getSharedUserFromAppId(int i) {
            return (com.android.server.pm.SharedUserSetting) this.mSettings.getSettingLPr(i);
        }

        @android.annotation.NonNull
        public android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> getSharedUserPackages(int i) {
            android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> arraySet = new android.util.ArraySet<>();
            com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) this.mSettings.getSettingLPr(i);
            if (sharedUserSetting != null) {
                java.util.Iterator<? extends com.android.server.pm.pkg.PackageStateInternal> it = sharedUserSetting.getPackageStates().iterator();
                while (it.hasNext()) {
                    arraySet.add(it.next());
                }
            }
            return arraySet;
        }

        public void dumpPackagesProto(android.util.proto.ProtoOutputStream protoOutputStream) {
            this.mSettings.dumpPackagesProto(protoOutputStream);
        }

        public void dumpPermissions(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState) {
            this.mSettings.dumpPermissions(printWriter, str, arraySet, dumpState);
        }

        public void dumpPackages(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState, boolean z) {
            this.mSettings.dumpPackagesLPr(printWriter, str, arraySet, dumpState, z);
        }

        public void dumpKeySet(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.pm.DumpState dumpState) {
            this.mSettings.getKeySetManagerService().dumpLPr(printWriter, str, dumpState);
        }

        public void dumpSharedUsers(java.io.PrintWriter printWriter, java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, com.android.server.pm.DumpState dumpState, boolean z) {
            this.mSettings.dumpSharedUsersLPr(printWriter, str, arraySet, dumpState, z);
        }

        public void dumpReadMessages(java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState) {
            this.mSettings.dumpReadMessages(printWriter, dumpState);
        }

        public void dumpSharedUsersProto(android.util.proto.ProtoOutputStream protoOutputStream) {
            this.mSettings.dumpSharedUsersProto(protoOutputStream);
        }

        public java.util.List<? extends com.android.server.pm.pkg.PackageStateInternal> getVolumePackages(@android.annotation.NonNull java.lang.String str) {
            return this.mSettings.getVolumePackagesLPr(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$0(android.content.pm.ProviderInfo providerInfo, android.content.pm.ProviderInfo providerInfo2) {
        int i = providerInfo.initOrder;
        int i2 = providerInfo2.initOrder;
        if (i > i2) {
            return -1;
        }
        return i < i2 ? 1 : 0;
    }

    private boolean safeMode() {
        return this.mService.getSafeMode();
    }

    protected android.content.ComponentName resolveComponentName() {
        return this.mLocalResolveComponentName;
    }

    protected android.content.pm.ActivityInfo instantAppInstallerActivity() {
        return this.mLocalInstantAppInstallerActivity;
    }

    protected android.content.pm.ApplicationInfo androidApplication() {
        return this.mLocalAndroidApplication;
    }

    ComputerEngine(com.android.server.pm.PackageManagerService.Snapshot snapshot, int i) {
        this.mVersion = i;
        this.mSettings = new com.android.server.pm.ComputerEngine.Settings(snapshot.settings);
        this.mIsolatedOwners = snapshot.isolatedOwners;
        this.mPackages = snapshot.packages;
        this.mSharedLibraries = snapshot.sharedLibraries;
        this.mInstrumentation = snapshot.instrumentation;
        this.mWebInstantAppsDisabled = snapshot.webInstantAppsDisabled;
        this.mLocalResolveComponentName = snapshot.resolveComponentName;
        this.mResolveActivity = snapshot.resolveActivity;
        this.mLocalInstantAppInstallerActivity = snapshot.instantAppInstallerActivity;
        this.mInstantAppInstallerInfo = snapshot.instantAppInstallerInfo;
        this.mInstantAppRegistry = snapshot.instantAppRegistry;
        this.mLocalAndroidApplication = snapshot.androidApplication;
        this.mAppsFilter = snapshot.appsFilter;
        this.mFrozenPackages = snapshot.frozenPackages;
        this.mComponentResolver = snapshot.componentResolver;
        this.mAppPredictionServicePackage = snapshot.appPredictionServicePackage;
        this.mPermissionManager = snapshot.service.mPermissionManager;
        this.mUserManager = snapshot.service.mUserManager;
        this.mContext = snapshot.service.mContext;
        this.mInjector = snapshot.service.mInjector;
        this.mApexManager = snapshot.service.mApexManager;
        this.mInstantAppResolverConnection = snapshot.service.mInstantAppResolverConnection;
        this.mDefaultAppProvider = snapshot.service.getDefaultAppProvider();
        this.mDomainVerificationManager = snapshot.service.mDomainVerificationManager;
        this.mPackageDexOptimizer = snapshot.service.mPackageDexOptimizer;
        this.mDexManager = snapshot.service.getDexManager();
        this.mCompilerStats = snapshot.service.mCompilerStats;
        this.mBackgroundDexOptService = snapshot.service.mBackgroundDexOptService;
        this.mExternalSourcesPolicy = snapshot.service.mExternalSourcesPolicy;
        this.mCrossProfileIntentResolverEngine = new com.android.server.pm.CrossProfileIntentResolverEngine(this.mUserManager, this.mDomainVerificationManager, this.mDefaultAppProvider, this.mContext);
        this.mService = snapshot.service;
    }

    @Override // com.android.server.pm.Computer
    public int getVersion() {
        return this.mVersion;
    }

    @Override // com.android.server.pm.Computer
    public final com.android.server.pm.Computer use() {
        this.mUsed++;
        return this;
    }

    @Override // com.android.server.pm.Computer
    public final int getUsed() {
        return this.mUsed;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, long j2, int i, int i2, boolean z, boolean z2) {
        android.content.Intent intent2;
        android.content.ComponentName componentName;
        android.content.Intent intent3;
        java.util.List<android.content.pm.ResolveInfo> list;
        if (!this.mUserManager.exists(i2)) {
            return java.util.Collections.emptyList();
        }
        long j3 = j | 8589934592L;
        java.lang.String instantAppPackageName = getInstantAppPackageName(i);
        enforceCrossUserPermission(android.os.Binder.getCallingUid(), i2, false, false, "query intent activities");
        java.lang.String str2 = intent.getPackage();
        android.content.ComponentName component = intent.getComponent();
        if (component == null && intent.getSelector() != null) {
            android.content.Intent selector = intent.getSelector();
            intent3 = intent;
            intent2 = selector;
            componentName = selector.getComponent();
        } else {
            intent2 = intent;
            componentName = component;
            intent3 = null;
        }
        boolean z3 = false;
        long updateFlagsForResolve = updateFlagsForResolve(j3, i2, i, z, (componentName == null && str2 == null) ? false : true, isImplicitImageCaptureIntentAndNotSetByDpc(intent2, i2, str, j3));
        java.util.List<android.content.pm.ResolveInfo> emptyList = java.util.Collections.emptyList();
        if (componentName != null) {
            android.content.pm.ActivityInfo activityInfo = getActivityInfo(componentName, updateFlagsForResolve, i2);
            if (activityInfo != null) {
                boolean z4 = (8388608 & updateFlagsForResolve) != 0;
                boolean z5 = (updateFlagsForResolve & 16777216) != 0;
                boolean z6 = (updateFlagsForResolve & 33554432) != 0;
                boolean z7 = instantAppPackageName != null;
                boolean equals = componentName.getPackageName().equals(instantAppPackageName);
                boolean z8 = (activityInfo.applicationInfo.privateFlags & 128) != 0;
                boolean z9 = (activityInfo.flags & 1048576) != 0;
                boolean z10 = !equals && (!(z4 || z7 || !z8) || (z5 && z7 && (!z9 || (z6 && !(z9 && (activityInfo.flags & 2097152) == 0)))));
                boolean z11 = (!z || (z && !activityInfo.exported && !isCallerSameApp(str2, i))) && !z8 && !z7 && shouldFilterApplication(getPackageStateInternal(activityInfo.applicationInfo.packageName, 1000), i, i2);
                if (!z10 && !z11) {
                    android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
                    resolveInfo.activityInfo = activityInfo;
                    resolveInfo.userHandle = android.os.UserHandle.of(i2);
                    java.util.ArrayList arrayList = new java.util.ArrayList(1);
                    arrayList.add(resolveInfo);
                    com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, arrayList, false, intent2, str, i);
                    emptyList = arrayList;
                }
            }
            list = emptyList;
        } else {
            com.android.server.pm.QueryIntentActivitiesResult queryIntentActivitiesInternalBody = queryIntentActivitiesInternalBody(intent2, str, updateFlagsForResolve, i, i2, z, z2, str2, instantAppPackageName);
            if (queryIntentActivitiesInternalBody.answer != null) {
                list = queryIntentActivitiesInternalBody.answer;
                z3 = true;
            } else {
                if (queryIntentActivitiesInternalBody.addInstant) {
                    queryIntentActivitiesInternalBody.result = maybeAddInstantAppInstaller(queryIntentActivitiesInternalBody.result, intent2, str, updateFlagsForResolve, i2, z, isInstantApp(getInstantAppPackageName(i), i2));
                }
                if (queryIntentActivitiesInternalBody.sortResult) {
                    queryIntentActivitiesInternalBody.result.sort(com.android.server.pm.resolution.ComponentResolver.RESOLVE_PRIORITY_SORTER);
                }
                list = queryIntentActivitiesInternalBody.result;
            }
        }
        if (intent3 != null) {
            com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, list, false, intent3, str, i);
        }
        return z3 ? list : applyPostResolutionFilter(list, instantAppPackageName, z2, i, z, i2, intent2);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        return queryIntentActivitiesInternal(intent, str, j, 0L, i, i2, false, true);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal(android.content.Intent intent, java.lang.String str, long j, int i) {
        return queryIntentActivitiesInternal(intent, str, j, 0L, android.os.Binder.getCallingUid(), i, false, true);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public final java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternal(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z) {
        android.content.Intent intent2;
        android.content.Intent intent3;
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternalBody;
        if (!this.mUserManager.exists(i)) {
            return java.util.Collections.emptyList();
        }
        enforceCrossUserOrProfilePermission(i2, i, false, false, "query intent receivers");
        java.lang.String instantAppPackageName = getInstantAppPackageName(i2);
        long updateFlagsForResolve = updateFlagsForResolve(j, i, i2, z, false);
        android.content.ComponentName component = intent.getComponent();
        if (component == null && intent.getSelector() != null) {
            android.content.Intent selector = intent.getSelector();
            intent3 = intent;
            intent2 = selector;
            component = selector.getComponent();
        } else {
            intent2 = intent;
            intent3 = null;
        }
        java.util.List<android.content.pm.ResolveInfo> emptyList = java.util.Collections.emptyList();
        if (component != null) {
            android.content.pm.ServiceInfo serviceInfo = getServiceInfo(component, updateFlagsForResolve, i);
            if (serviceInfo != null) {
                boolean z2 = false;
                boolean z3 = (8388608 & updateFlagsForResolve) != 0;
                boolean z4 = (updateFlagsForResolve & 16777216) != 0;
                boolean z5 = instantAppPackageName != null;
                boolean equals = component.getPackageName().equals(instantAppPackageName);
                boolean z6 = (serviceInfo.applicationInfo.privateFlags & 128) != 0;
                boolean z7 = !equals && (!(z3 || z5 || !z6) || (z4 && z5 && ((serviceInfo.flags & 1048576) == 0)));
                if (!z6 && !z5 && shouldFilterApplication(getPackageStateInternal(serviceInfo.applicationInfo.packageName, 1000), i2, i)) {
                    z2 = true;
                }
                if (!z7 && !z2) {
                    android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
                    resolveInfo.serviceInfo = serviceInfo;
                    java.util.ArrayList arrayList = new java.util.ArrayList(1);
                    arrayList.add(resolveInfo);
                    com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, arrayList, false, intent2, str, i2);
                    emptyList = arrayList;
                }
            }
            queryIntentServicesInternalBody = emptyList;
        } else {
            queryIntentServicesInternalBody = queryIntentServicesInternalBody(intent2, str, updateFlagsForResolve, i, i2, instantAppPackageName);
        }
        if (intent3 != null) {
            com.android.server.pm.PackageManagerServiceUtils.applyEnforceIntentFilterMatching(this.mInjector.getCompatibility(), this.mComponentResolver, queryIntentServicesInternalBody, false, intent3, str, i2);
        }
        return queryIntentServicesInternalBody;
    }

    @android.annotation.NonNull
    protected java.util.List<android.content.pm.ResolveInfo> queryIntentServicesInternalBody(android.content.Intent intent, java.lang.String str, long j, int i, int i2, java.lang.String str2) {
        java.lang.String str3 = intent.getPackage();
        if (str3 == null) {
            java.util.List<android.content.pm.ResolveInfo> queryServices = this.mComponentResolver.queryServices(this, intent, str, j, i);
            if (queryServices == null) {
                return java.util.Collections.emptyList();
            }
            return applyPostServiceResolutionFilter(queryServices, str2, i, i2);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str3);
        if (androidPackage != null) {
            java.util.List<android.content.pm.ResolveInfo> queryServices2 = this.mComponentResolver.queryServices(this, intent, str, j, androidPackage.getServices(), i);
            if (queryServices2 == null) {
                return java.util.Collections.emptyList();
            }
            return applyPostServiceResolutionFilter(queryServices2, str2, i, i2);
        }
        return java.util.Collections.emptyList();
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0095, code lost:
    
        if (shouldFilterApplication(r0, r25, r26) != false) goto L24;
     */
    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.pm.QueryIntentActivitiesResult queryIntentActivitiesInternalBody(android.content.Intent intent, java.lang.String str, long j, int i, int i2, boolean z, boolean z2, java.lang.String str2, java.lang.String str3) {
        boolean z3;
        boolean z4;
        boolean z5;
        java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        new java.util.ArrayList();
        if (str2 == null) {
            if (!this.mCrossProfileIntentResolverEngine.shouldSkipCurrentProfile(this, intent, str, i2)) {
                arrayList.addAll(filterIfNotSystemUser(this.mComponentResolver.queryActivities(this, intent, str, j, i2), i2));
            }
            boolean isInstantAppResolutionAllowed = isInstantAppResolutionAllowed(intent, arrayList, i2, false, j);
            boolean hasNonNegativePriority = hasNonNegativePriority(arrayList);
            com.android.server.pm.CrossProfileIntentResolverEngine crossProfileIntentResolverEngine = this.mCrossProfileIntentResolverEngine;
            final com.android.server.pm.ComputerEngine.Settings settings = this.mSettings;
            java.util.Objects.requireNonNull(settings);
            java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent2 = crossProfileIntentResolverEngine.resolveIntent(this, intent, str, i2, j, str2, hasNonNegativePriority, z, new java.util.function.Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.server.pm.ComputerEngine.Settings.this.getPackage((java.lang.String) obj);
                }
            });
            z4 = intent.hasWebURI() || !resolveIntent2.isEmpty();
            z5 = isInstantAppResolutionAllowed;
            resolveIntent = resolveIntent2;
        } else {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str2, 1000);
            if (packageStateInternal != null && packageStateInternal.getAndroidPackage() != null) {
                if (z) {
                }
                arrayList.addAll(filterIfNotSystemUser(this.mComponentResolver.queryActivities(this, intent, str, j, packageStateInternal.getAndroidPackage().getActivities(), i2), i2));
            }
            if (arrayList.size() != 0) {
                z3 = false;
            } else {
                z3 = isInstantAppResolutionAllowed(intent, null, i2, true, j);
            }
            com.android.server.pm.CrossProfileIntentResolverEngine crossProfileIntentResolverEngine2 = this.mCrossProfileIntentResolverEngine;
            final com.android.server.pm.ComputerEngine.Settings settings2 = this.mSettings;
            java.util.Objects.requireNonNull(settings2);
            z4 = false;
            z5 = z3;
            resolveIntent = crossProfileIntentResolverEngine2.resolveIntent(this, intent, str, i2, j, str2, false, z, new java.util.function.Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.server.pm.ComputerEngine.Settings.this.getPackage((java.lang.String) obj);
                }
            });
        }
        com.android.server.pm.CrossProfileIntentResolverEngine crossProfileIntentResolverEngine3 = this.mCrossProfileIntentResolverEngine;
        boolean areWebInstantAppsDisabled = areWebInstantAppsDisabled(i2);
        final com.android.server.pm.ComputerEngine.Settings settings3 = this.mSettings;
        java.util.Objects.requireNonNull(settings3);
        return crossProfileIntentResolverEngine3.combineFilterAndCreateQueryActivitiesResponse(this, intent, str, str3, str2, z2, j, i2, i, z, arrayList, resolveIntent, areWebInstantAppsDisabled, z5, z4, new java.util.function.Function() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.pm.ComputerEngine.Settings.this.getPackage((java.lang.String) obj);
            }
        });
    }

    @android.annotation.Nullable
    private android.content.ComponentName findInstallFailureActivity(java.lang.String str, int i, int i2) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.INSTALL_FAILURE");
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = queryIntentActivitiesInternal(intent, null, 0L, 0L, i, i2, false, false);
        int size = queryIntentActivitiesInternal.size();
        if (size > 0) {
            for (int i3 = 0; i3 < size; i3++) {
                android.content.pm.ResolveInfo resolveInfo = queryIntentActivitiesInternal.get(i3);
                if (resolveInfo.activityInfo.splitName == null) {
                    return new android.content.ComponentName(str, resolveInfo.activityInfo.name);
                }
            }
            return null;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ActivityInfo getActivityInfo(android.content.ComponentName componentName, long j, int i) {
        return getActivityInfoInternal(componentName, j, android.os.Binder.getCallingUid(), i);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ActivityInfo getActivityInfoCrossProfile(android.content.ComponentName componentName, long j, int i) {
        if (this.mUserManager.exists(i)) {
            return getActivityInfoInternalBody(componentName, updateFlagsForComponent(j, i), android.os.Binder.getCallingUid(), i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ActivityInfo getActivityInfoInternal(android.content.ComponentName componentName, long j, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, i2);
        if (!isRecentsAccessingChildProfiles(android.os.Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(android.os.Binder.getCallingUid(), i2, false, false, "get activity info");
        }
        return getActivityInfoInternalBody(componentName, updateFlagsForComponent, i, i2);
    }

    protected android.content.pm.ActivityInfo getActivityInfoInternalBody(android.content.ComponentName componentName, long j, int i, int i2) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        com.android.internal.pm.pkg.component.ParsedMainComponent activity = this.mComponentResolver.getActivity(componentName);
        long j2 = j | 8589934592L;
        if (activity != null) {
            androidPackage = this.mPackages.get(activity.getPackageName());
        } else {
            androidPackage = null;
        }
        if (androidPackage != null && this.mSettings.isEnabledAndMatch(androidPackage, activity, j2, i2)) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(componentName.getPackageName());
            if (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, componentName, 1, i2)) {
                return null;
            }
            return com.android.server.pm.parsing.PackageInfoUtils.generateActivityInfo(androidPackage, activity, j2, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
        }
        if (!resolveComponentName().equals(componentName)) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateDelegateActivityInfo(this.mResolveActivity, j2, com.android.server.pm.pkg.PackageUserStateInternal.DEFAULT, i2);
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.pkg.AndroidPackage getPackage(java.lang.String str) {
        return this.mPackages.get(resolveInternalPackageName(str, -1L));
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.pkg.AndroidPackage getPackage(int i) {
        java.lang.String[] packagesForUidInternal = getPackagesForUidInternal(i, 1000);
        int length = packagesForUidInternal == null ? 0 : packagesForUidInternal.length;
        com.android.server.pm.pkg.AndroidPackage androidPackage = null;
        for (int i2 = 0; androidPackage == null && i2 < length; i2++) {
            androidPackage = this.mPackages.get(packagesForUidInternal[i2]);
        }
        return androidPackage;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ApplicationInfo generateApplicationInfoFromSettings(java.lang.String str, long j, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        if (!this.mUserManager.exists(i2) || (packageStateInternal = this.mSettings.getPackage(str)) == null || filterSharedLibPackage(packageStateInternal, i, i2, j) || shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        if (packageStateInternal.getAndroidPackage() == null) {
            android.content.pm.PackageInfo generatePackageInfo = generatePackageInfo(packageStateInternal, j, i2);
            if (generatePackageInfo != null) {
                return generatePackageInfo.applicationInfo;
            }
            return null;
        }
        android.content.pm.ApplicationInfo generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
        if (generateApplicationInfo != null) {
            generateApplicationInfo.packageName = resolveExternalPackageName(packageStateInternal.getPkg());
        }
        return generateApplicationInfo;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, long j, int i) {
        return getApplicationInfoInternal(str, j, android.os.Binder.getCallingUid(), i);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ApplicationInfo getApplicationInfoInternal(java.lang.String str, long j, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForApplication = updateFlagsForApplication(j, i2);
        if (!isRecentsAccessingChildProfiles(android.os.Binder.getCallingUid(), i2)) {
            enforceCrossUserPermission(android.os.Binder.getCallingUid(), i2, false, false, "get application info");
        }
        return getApplicationInfoInternalBody(str, updateFlagsForApplication, i, i2);
    }

    protected android.content.pm.ApplicationInfo getApplicationInfoInternalBody(java.lang.String str, long j, int i, int i2) {
        java.lang.String resolveInternalPackageName = resolveInternalPackageName(str, -1L);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(resolveInternalPackageName);
        boolean z = (1073741824 & j) != 0;
        if (androidPackage != null) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInternalPackageName);
            if (packageStateInternal == null) {
                return null;
            }
            if ((!z && androidPackage.isApex()) || filterSharedLibPackage(packageStateInternal, i, i2, j) || shouldFilterApplication(packageStateInternal, i, i2)) {
                return null;
            }
            android.content.pm.ApplicationInfo generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(androidPackage, j, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
            if (generateApplicationInfo != null) {
                generateApplicationInfo.packageName = resolveExternalPackageName(androidPackage);
            }
            return generateApplicationInfo;
        }
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(resolveInternalPackageName) || "system".equals(resolveInternalPackageName)) {
            return androidApplication();
        }
        if ((4299169792L & j) == 0) {
            return null;
        }
        return generateApplicationInfoFromSettings(resolveInternalPackageName, j, i, i2);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.ComponentName getDefaultHomeActivity(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.content.ComponentName homeActivitiesAsUser = getHomeActivitiesAsUser(arrayList, i);
        if (homeActivitiesAsUser != null) {
            return homeActivitiesAsUser;
        }
        android.util.Slog.w("PackageManager", "Default package for ROLE_HOME is not set in RoleManager");
        int size = arrayList.size();
        int i2 = Integer.MIN_VALUE;
        android.content.ComponentName componentName = null;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ResolveInfo resolveInfo = arrayList.get(i3);
            if (resolveInfo.priority > i2) {
                componentName = resolveInfo.activityInfo.getComponentName();
                i2 = resolveInfo.priority;
            } else if (resolveInfo.priority == i2) {
                componentName = null;
            }
        }
        return componentName;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.ComponentName getHomeActivitiesAsUser(java.util.List<android.content.pm.ResolveInfo> list, int i) {
        android.content.Intent homeIntent = getHomeIntent();
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivitiesInternal = queryIntentActivitiesInternal(homeIntent, null, 128L, i);
        list.clear();
        if (queryIntentActivitiesInternal == null) {
            return null;
        }
        list.addAll(queryIntentActivitiesInternal);
        java.lang.String defaultHome = this.mDefaultAppProvider.getDefaultHome(i);
        if (defaultHome == null) {
            android.content.pm.ResolveInfo resolveInfo = findPreferredActivityInternal(homeIntent, null, 0L, queryIntentActivitiesInternal, true, false, false, i, android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) >= 10000).mPreferredResolveInfo;
            if (resolveInfo != null && resolveInfo.activityInfo != null) {
                defaultHome = resolveInfo.activityInfo.packageName;
            }
        }
        if (defaultHome == null) {
            return null;
        }
        int size = queryIntentActivitiesInternal.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo2 = queryIntentActivitiesInternal.get(i2);
            if (resolveInfo2.activityInfo != null && android.text.TextUtils.equals(resolveInfo2.activityInfo.packageName, defaultHome)) {
                return new android.content.ComponentName(resolveInfo2.activityInfo.packageName, resolveInfo2.activityInfo.name);
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final com.android.server.pm.CrossProfileDomainInfo getCrossProfileDomainPreferredLpr(android.content.Intent intent, java.lang.String str, long j, int i, int i2) {
        java.util.List<android.content.pm.ResolveInfo> queryActivities;
        if (!this.mUserManager.hasUserRestriction("allow_parent_profile_app_linking", i) || (queryActivities = this.mComponentResolver.queryActivities(this, intent, str, j, i2)) == null || queryActivities.isEmpty()) {
            return null;
        }
        int size = queryActivities.size();
        com.android.server.pm.CrossProfileDomainInfo crossProfileDomainInfo = null;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ResolveInfo resolveInfo = queryActivities.get(i3);
            if (!resolveInfo.handleAllWebDataURI) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInfo.activityInfo.packageName);
                if (packageStateInternal != null) {
                    int approvalLevelForDomain = this.mDomainVerificationManager.approvalLevelForDomain(packageStateInternal, intent, j, i2);
                    if (crossProfileDomainInfo == null) {
                        crossProfileDomainInfo = new com.android.server.pm.CrossProfileDomainInfo(createForwardingResolveInfoUnchecked(new com.android.server.pm.WatchedIntentFilter(), i, i2), approvalLevelForDomain, i2);
                    } else {
                        crossProfileDomainInfo.mHighestApprovalLevel = java.lang.Math.max(approvalLevelForDomain, crossProfileDomainInfo.mHighestApprovalLevel);
                    }
                }
            }
        }
        if (crossProfileDomainInfo == null || crossProfileDomainInfo.mHighestApprovalLevel > 0) {
            return crossProfileDomainInfo;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.Intent getHomeIntent() {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    @Override // com.android.server.pm.Computer
    public final java.util.List<com.android.server.pm.CrossProfileIntentFilter> getMatchingCrossProfileIntentFilters(android.content.Intent intent, java.lang.String str, int i) {
        com.android.server.pm.CrossProfileIntentResolver crossProfileIntentResolver = this.mSettings.getCrossProfileIntentResolver(i);
        if (crossProfileIntentResolver != null) {
            return crossProfileIntentResolver.queryIntent(this, intent, str, false, i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final java.util.List<android.content.pm.ResolveInfo> applyPostResolutionFilter(@android.annotation.NonNull java.util.List<android.content.pm.ResolveInfo> list, java.lang.String str, boolean z, int i, boolean z2, int i2, android.content.Intent intent) {
        boolean z3 = intent.isWebIntent() && areWebInstantAppsDisabled(i2);
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ResolveInfo resolveInfo = list.get(size);
            if (resolveInfo.isInstantAppAvailable && z3) {
                list.remove(size);
            } else {
                if (z && resolveInfo.activityInfo != null && resolveInfo.activityInfo.splitName != null) {
                    if (!com.android.internal.util.ArrayUtils.contains(resolveInfo.activityInfo.applicationInfo.splitNames, resolveInfo.activityInfo.splitName)) {
                        if (instantAppInstallerActivity() == null) {
                            list.remove(size);
                        } else if (z3 && isInstantAppInternal(resolveInfo.activityInfo.packageName, i2, 1000)) {
                            list.remove(size);
                        } else {
                            android.content.pm.ResolveInfo resolveInfo2 = new android.content.pm.ResolveInfo(this.mInstantAppInstallerInfo);
                            resolveInfo2.auxiliaryInfo = new android.content.pm.AuxiliaryResolveInfo(findInstallFailureActivity(resolveInfo.activityInfo.packageName, i, i2), resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.applicationInfo.longVersionCode, resolveInfo.activityInfo.splitName);
                            resolveInfo2.filter = new android.content.IntentFilter();
                            resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                            resolveInfo2.labelRes = resolveInfo.resolveLabelResId();
                            resolveInfo2.icon = resolveInfo.resolveIconResId();
                            resolveInfo2.isInstantAppAvailable = true;
                            list.set(size, resolveInfo2);
                        }
                    }
                }
                if (str == null) {
                    com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(resolveInfo.activityInfo.packageName, 0);
                    if (!z2) {
                        if (!this.mAppsFilter.shouldFilterApplication(this, i, settingBase, packageStateInternal, i2)) {
                        }
                        list.remove(size);
                    }
                } else if (!str.equals(resolveInfo.activityInfo.packageName)) {
                    if (z2) {
                        if ((intent.isWebIntent() || (intent.getFlags() & 2048) != 0) && intent.getPackage() == null && intent.getComponent() == null) {
                        }
                    }
                    if ((resolveInfo.activityInfo.flags & 1048576) != 0 && !resolveInfo.activityInfo.applicationInfo.isInstantApp()) {
                    }
                    list.remove(size);
                }
            }
        }
        return list;
    }

    private java.util.List<android.content.pm.ResolveInfo> applyPostServiceResolutionFilter(java.util.List<android.content.pm.ResolveInfo> list, java.lang.String str, int i, int i2) {
        for (int size = list.size() - 1; size >= 0; size--) {
            android.content.pm.ResolveInfo resolveInfo = list.get(size);
            if (str == null) {
                if (!this.mAppsFilter.shouldFilterApplication(this, i2, this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i2)), getPackageStateInternal(resolveInfo.serviceInfo.packageName, 0), i)) {
                }
            }
            boolean isInstantApp = resolveInfo.serviceInfo.applicationInfo.isInstantApp();
            if (isInstantApp && str.equals(resolveInfo.serviceInfo.packageName)) {
                if (resolveInfo.serviceInfo.splitName != null && !com.android.internal.util.ArrayUtils.contains(resolveInfo.serviceInfo.applicationInfo.splitNames, resolveInfo.serviceInfo.splitName)) {
                    if (instantAppInstallerActivity() == null) {
                        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                            android.util.Slog.v("PackageManager", "No installer - not adding it to the ResolveInfolist");
                        }
                        list.remove(size);
                    } else {
                        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                            android.util.Slog.v("PackageManager", "Adding ephemeral installer to the ResolveInfo list");
                        }
                        android.content.pm.ResolveInfo resolveInfo2 = new android.content.pm.ResolveInfo(this.mInstantAppInstallerInfo);
                        resolveInfo2.auxiliaryInfo = new android.content.pm.AuxiliaryResolveInfo((android.content.ComponentName) null, resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.applicationInfo.longVersionCode, resolveInfo.serviceInfo.splitName);
                        resolveInfo2.filter = new android.content.IntentFilter();
                        resolveInfo2.resolvePackageName = resolveInfo.getComponentInfo().packageName;
                        list.set(size, resolveInfo2);
                    }
                }
            } else if (isInstantApp || (resolveInfo.serviceInfo.flags & 1048576) == 0) {
                list.remove(size);
            }
        }
        return list;
    }

    private java.util.List<android.content.pm.ResolveInfo> filterIfNotSystemUser(java.util.List<android.content.pm.ResolveInfo> list, int i) {
        if (i == 0) {
            return list;
        }
        for (int size = com.android.internal.util.CollectionUtils.size(list) - 1; size >= 0; size--) {
            if ((list.get(size).activityInfo.flags & 536870912) != 0) {
                list.remove(size);
            }
        }
        return list;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.util.List<android.content.pm.ResolveInfo> maybeAddInstantAppInstaller(java.util.List<android.content.pm.ResolveInfo> list, android.content.Intent intent, java.lang.String str, long j, int i, boolean z, boolean z2) {
        boolean z3;
        android.content.pm.ResolveInfo resolveInfo;
        java.lang.String str2;
        int i2;
        int i3;
        android.content.pm.AuxiliaryResolveInfo auxiliaryResolveInfo;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        android.content.pm.ResolveInfo resolveInfo2;
        if (!((j & 8388608) != 0)) {
            java.util.List<android.content.pm.ResolveInfo> queryActivities = this.mComponentResolver.queryActivities(this, intent, str, 8388608 | j | 64 | 16777216, i);
            for (int size = queryActivities.size() - 1; size >= 0; size--) {
                resolveInfo = queryActivities.get(size);
                java.lang.String str3 = resolveInfo.activityInfo.packageName;
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str3);
                if (packageStateInternal2.getUserStateOrDefault(i).isInstantApp()) {
                    if (com.android.server.pm.PackageManagerServiceUtils.hasAnyDomainApproval(this.mDomainVerificationManager, packageStateInternal2, intent, j, i)) {
                        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                            android.util.Slog.v("PackageManager", "Instant app approved for intent; pkg: " + str3);
                        }
                        z3 = false;
                    } else {
                        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                            android.util.Slog.v("PackageManager", "Instant app not approved for intent; pkg: " + str3);
                        }
                        z3 = true;
                        resolveInfo = null;
                    }
                    if (!z3) {
                        str2 = "PackageManager";
                        i2 = 0;
                        i3 = i;
                        auxiliaryResolveInfo = null;
                    } else if (resolveInfo != null) {
                        str2 = "PackageManager";
                        i2 = 0;
                        i3 = i;
                        android.content.pm.ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
                        auxiliaryResolveInfo = new android.content.pm.AuxiliaryResolveInfo((android.content.ComponentName) null, applicationInfo.packageName, applicationInfo.longVersionCode, (java.lang.String) null);
                    } else {
                        android.os.Trace.traceBegin(262144L, "resolveEphemeral");
                        i2 = 0;
                        i3 = i;
                        auxiliaryResolveInfo = com.android.server.pm.InstantAppResolver.doInstantAppResolutionPhaseOne(this, this.mUserManager, this.mInstantAppResolverConnection, new android.content.pm.InstantAppRequest((android.content.pm.AuxiliaryResolveInfo) null, intent, str, (java.lang.String) null, (java.lang.String) null, z2, i, (android.os.Bundle) null, z, com.android.server.pm.InstantAppResolver.parseDigest(intent).getDigestPrefixSecure(), java.util.UUID.randomUUID().toString()));
                        android.os.Trace.traceEnd(262144L);
                        str2 = "PackageManager";
                    }
                    if ((!intent.isWebIntent() && auxiliaryResolveInfo == null) || (packageStateInternal = this.mSettings.getPackage(instantAppInstallerActivity().packageName)) == null || !com.android.server.pm.pkg.PackageUserStateUtils.isEnabled(packageStateInternal.getUserStateOrDefault(i3), instantAppInstallerActivity(), 0L)) {
                        return list;
                    }
                    resolveInfo2 = new android.content.pm.ResolveInfo(this.mInstantAppInstallerInfo);
                    resolveInfo2.activityInfo = com.android.server.pm.parsing.PackageInfoUtils.generateDelegateActivityInfo(instantAppInstallerActivity(), 0L, packageStateInternal.getUserStateOrDefault(i3), i3);
                    resolveInfo2.match = 5799936;
                    resolveInfo2.filter = new android.content.IntentFilter();
                    if (intent.getAction() != null) {
                        resolveInfo2.filter.addAction(intent.getAction());
                    }
                    if (intent.getData() != null && intent.getData().getPath() != null) {
                        resolveInfo2.filter.addDataPath(intent.getData().getPath(), i2);
                    }
                    resolveInfo2.isInstantAppAvailable = true;
                    resolveInfo2.isDefault = true;
                    resolveInfo2.auxiliaryInfo = auxiliaryResolveInfo;
                    if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                        android.util.Slog.v(str2, "Adding ephemeral installer to the ResolveInfo list");
                    }
                    list.add(resolveInfo2);
                    return list;
                }
            }
        }
        z3 = false;
        resolveInfo = null;
        if (!z3) {
        }
        if (!intent.isWebIntent()) {
        }
        resolveInfo2 = new android.content.pm.ResolveInfo(this.mInstantAppInstallerInfo);
        resolveInfo2.activityInfo = com.android.server.pm.parsing.PackageInfoUtils.generateDelegateActivityInfo(instantAppInstallerActivity(), 0L, packageStateInternal.getUserStateOrDefault(i3), i3);
        resolveInfo2.match = 5799936;
        resolveInfo2.filter = new android.content.IntentFilter();
        if (intent.getAction() != null) {
        }
        if (intent.getData() != null) {
            resolveInfo2.filter.addDataPath(intent.getData().getPath(), i2);
        }
        resolveInfo2.isInstantAppAvailable = true;
        resolveInfo2.isDefault = true;
        resolveInfo2.auxiliaryInfo = auxiliaryResolveInfo;
        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
        }
        list.add(resolveInfo2);
        return list;
    }

    public static boolean isMicrogSigned(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (!isDebuggable()) {
            return false;
        }
        if (androidPackage.getPackageName().equals("com.android.vending") || androidPackage.getPackageName().equals("com.google.android.gms")) {
            return android.content.pm.Signature.areExactMatch(androidPackage.getSigningDetails(), new android.content.pm.Signature[]{MICROG_REAL_SIGNATURE});
        }
        return false;
    }

    private static java.util.Optional<android.content.pm.Signature> generateFakeSignature(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (!isMicrogSigned(androidPackage)) {
            return java.util.Optional.empty();
        }
        android.os.Bundle metaData = androidPackage.getMetaData();
        if (metaData == null) {
            return java.util.Optional.empty();
        }
        java.lang.String string = metaData.getString("fake-signature");
        if (android.text.TextUtils.isEmpty(string)) {
            return java.util.Optional.empty();
        }
        android.content.pm.Signature signature = new android.content.pm.Signature(string);
        if (!signature.equals(MICROG_FAKE_SIGNATURE)) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(signature);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.PackageInfo generatePackageInfo(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, long j, int i) {
        long j2;
        java.util.Set<java.lang.String> emptySet;
        java.util.Set<java.lang.String> emptySet2;
        java.lang.String apexModuleName;
        if (!this.mUserManager.exists(i) || packageStateInternal == null || shouldFilterApplication(packageStateInternal, android.os.Binder.getCallingUid(), i)) {
            return null;
        }
        if ((j & 8192) != 0 && packageStateInternal.isSystem()) {
            j2 = j | 4194304;
        } else {
            j2 = j;
        }
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg != null) {
            int[] gidsForUid = (256 & j2) == 0 ? com.android.server.pm.PackageManagerService.EMPTY_INT_ARRAY : this.mPermissionManager.getGidsForUid(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
            long j3 = 4096 & j2;
            if (j3 == 0 || com.android.internal.util.ArrayUtils.isEmpty(pkg.getPermissions())) {
                emptySet = java.util.Collections.emptySet();
            } else {
                emptySet = this.mPermissionManager.getInstalledPermissions(packageStateInternal.getPackageName());
            }
            if (j3 == 0 || com.android.internal.util.ArrayUtils.isEmpty(pkg.getRequestedPermissions())) {
                emptySet2 = java.util.Collections.emptySet();
            } else {
                emptySet2 = this.mPermissionManager.getGrantedPermissions(packageStateInternal.getPackageName(), i);
            }
            final android.content.pm.PackageInfo generate = com.android.server.pm.parsing.PackageInfoUtils.generate(pkg, gidsForUid, j2, userStateOrDefault.getFirstInstallTimeMillis(), packageStateInternal.getLastUpdateTime(), emptySet, emptySet2, userStateOrDefault, i, packageStateInternal);
            if (generate == null) {
                return null;
            }
            android.content.pm.ApplicationInfo applicationInfo = generate.applicationInfo;
            java.lang.String resolveExternalPackageName = resolveExternalPackageName(pkg);
            applicationInfo.packageName = resolveExternalPackageName;
            generate.packageName = resolveExternalPackageName;
            if (android.content.pm.Flags.provideInfoOfApkInApex() && (apexModuleName = packageStateInternal.getApexModuleName()) != null) {
                generate.setApexPackageName(this.mApexManager.getActivePackageNameForApexModuleName(apexModuleName));
            }
            generateFakeSignature(pkg).ifPresent(new java.util.function.Consumer() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.pm.ComputerEngine.lambda$generatePackageInfo$1(generate, (android.content.pm.Signature) obj);
                }
            });
            return generate;
        }
        if ((4294975488L & j2) == 0 || !com.android.server.pm.pkg.PackageUserStateUtils.isAvailable(userStateOrDefault, j2)) {
            return null;
        }
        android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
        packageInfo.packageName = packageStateInternal.getPackageName();
        packageInfo.setLongVersionCode(packageStateInternal.getVersionCode());
        com.android.server.pm.pkg.SharedUserApi sharedUserFromPackageName = this.mSettings.getSharedUserFromPackageName(packageInfo.packageName);
        packageInfo.sharedUserId = sharedUserFromPackageName != null ? sharedUserFromPackageName.getName() : null;
        packageInfo.firstInstallTime = userStateOrDefault.getFirstInstallTimeMillis();
        packageInfo.lastUpdateTime = packageStateInternal.getLastUpdateTime();
        android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo();
        applicationInfo2.packageName = packageStateInternal.getPackageName();
        applicationInfo2.uid = android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
        applicationInfo2.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbiLegacy();
        applicationInfo2.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbiLegacy();
        applicationInfo2.volumeUuid = packageStateInternal.getVolumeUuid();
        applicationInfo2.storageUuid = android.os.storage.StorageManager.convert(applicationInfo2.volumeUuid);
        applicationInfo2.setVersionCode(packageStateInternal.getVersionCode());
        applicationInfo2.targetSdkVersion = packageStateInternal.getTargetSdkVersion();
        applicationInfo2.flags = packageStateInternal.getFlags();
        applicationInfo2.privateFlags = packageStateInternal.getPrivateFlags();
        packageInfo.applicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateDelegateApplicationInfo(applicationInfo2, j2, userStateOrDefault, i);
        packageInfo.signingInfo = packageStateInternal.getSigningInfo();
        packageInfo.signatures = com.android.server.pm.parsing.PackageInfoUtils.getDeprecatedSignatures(packageInfo.signingInfo.getSigningDetails(), j2);
        if (userStateOrDefault.getArchiveState() != null) {
            packageInfo.setArchiveTimeMillis(userStateOrDefault.getArchiveState().getArchiveTimeMillis());
        }
        return packageInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$generatePackageInfo$1(android.content.pm.PackageInfo packageInfo, android.content.pm.Signature signature) {
        packageInfo.signatures = new android.content.pm.Signature[]{signature};
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.PackageInfo getPackageInfo(java.lang.String str, long j, int i) {
        return getPackageInfoInternal(str, -1L, j, android.os.Binder.getCallingUid(), i);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.PackageInfo getPackageInfoInternal(java.lang.String str, long j, long j2, int i, int i2) {
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(j2, i2);
        enforceCrossUserPermission(android.os.Binder.getCallingUid(), i2, false, false, "get package info");
        return getPackageInfoInternalBody(str, j, updateFlagsForPackage, i, i2);
    }

    protected android.content.pm.PackageInfo getPackageInfoInternalBody(java.lang.String str, long j, long j2, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal disabledSystemPkg;
        java.lang.String resolveInternalPackageName = resolveInternalPackageName(str, j);
        boolean z = (2097152 & j2) != 0;
        boolean z2 = (1073741824 & j2) != 0;
        if (z && (disabledSystemPkg = this.mSettings.getDisabledSystemPkg(resolveInternalPackageName)) != null) {
            if ((!z2 && disabledSystemPkg.getPkg() != null && disabledSystemPkg.getPkg().isApex()) || filterSharedLibPackage(disabledSystemPkg, i, i2, j2) || shouldFilterApplication(disabledSystemPkg, i, i2)) {
                return null;
            }
            return generatePackageInfo(disabledSystemPkg, j2, i2);
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(resolveInternalPackageName);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(resolveInternalPackageName);
        if (z && androidPackage != null && !packageStateInternal2.isSystem()) {
            return null;
        }
        if (androidPackage != null) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = getPackageStateInternal(androidPackage.getPackageName());
            if ((!z2 && androidPackage.isApex()) || filterSharedLibPackage(packageStateInternal3, i, i2, j2)) {
                return null;
            }
            if (packageStateInternal3 != null && shouldFilterApplication(packageStateInternal3, i, i2)) {
                return null;
            }
            return generatePackageInfo(packageStateInternal3, j2, i2);
        }
        if (z || (4299169792L & j2) == 0 || (packageStateInternal = this.mSettings.getPackage(resolveInternalPackageName)) == null || filterSharedLibPackage(packageStateInternal, i, i2, j2) || shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        return generatePackageInfo(packageStateInternal, j2, i2);
    }

    @Override // com.android.server.pm.Computer
    public java.lang.String[] getAllAvailablePackageNames() {
        return (java.lang.String[]) this.mPackages.keySet().toArray(new java.lang.String[0]);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public final com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(java.lang.String str) {
        return getPackageStateInternal(str, android.os.Binder.getCallingUid());
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.pkg.PackageStateInternal getPackageStateInternal(java.lang.String str, int i) {
        return this.mSettings.getPackage(resolveInternalPackageNameInternalLocked(str, -1L, i));
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.pkg.PackageStateInternal getPackageStateFiltered(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(resolveInternalPackageNameInternalLocked(str, -1L, i));
        if (shouldFilterApplication(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getInstalledPackages(long j, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        if (!this.mUserManager.exists(i)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get installed packages");
        return getInstalledPackagesBody(updateFlagsForPackage, i, callingUid);
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x012a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0127 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getInstalledPackagesBody(long j, int i, int i2) {
        java.util.ArrayList arrayList;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        android.content.pm.PackageInfo generatePackageInfo;
        boolean z = (4202496 & j) != 0;
        boolean z2 = (1073741824 & j) != 0;
        boolean z3 = (2097152 & j) != 0;
        boolean z4 = (z || (4294967296L & j) == 0) ? false : true;
        if (z || z4) {
            java.util.ArrayList arrayList2 = new java.util.ArrayList(this.mSettings.getPackages().size());
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 : this.mSettings.getPackages().values()) {
                if (z3) {
                    if (packageStateInternal3.isSystem()) {
                        com.android.server.pm.pkg.PackageStateInternal disabledSystemPkg = this.mSettings.getDisabledSystemPkg(packageStateInternal3.getPackageName());
                        if (disabledSystemPkg != null) {
                            packageStateInternal = disabledSystemPkg;
                            if (!z2 || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isApex()) {
                                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                                if (z4 || userStateOrDefault.isInstalled() || userStateOrDefault.getArchiveState() != null) {
                                    boolean z5 = z2;
                                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal4 = packageStateInternal;
                                    if (!filterSharedLibPackage(packageStateInternal, i2, i, j)) {
                                        z2 = z5;
                                    } else if (shouldFilterApplication(packageStateInternal4, i2, i)) {
                                        z2 = z5;
                                    } else {
                                        android.content.pm.PackageInfo generatePackageInfo2 = generatePackageInfo(packageStateInternal4, j, i);
                                        if (generatePackageInfo2 != null) {
                                            arrayList2.add(generatePackageInfo2);
                                        }
                                        z2 = z5;
                                    }
                                }
                            }
                        }
                    }
                }
                packageStateInternal = packageStateInternal3;
                if (!z2) {
                }
                com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault2 = packageStateInternal.getUserStateOrDefault(i);
                if (z4) {
                }
                boolean z52 = z2;
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal42 = packageStateInternal;
                if (!filterSharedLibPackage(packageStateInternal, i2, i, j)) {
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = new java.util.ArrayList(this.mPackages.size());
            for (com.android.server.pm.pkg.AndroidPackage androidPackage : this.mPackages.values()) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal5 = getPackageStateInternal(androidPackage.getPackageName());
                if (z3) {
                    if (packageStateInternal5.isSystem()) {
                        com.android.server.pm.pkg.PackageStateInternal disabledSystemPkg2 = this.mSettings.getDisabledSystemPkg(packageStateInternal5.getPackageName());
                        if (disabledSystemPkg2 != null) {
                            packageStateInternal2 = disabledSystemPkg2;
                            if (!z2 || !androidPackage.isApex()) {
                                if (!filterSharedLibPackage(packageStateInternal2, i2, i, j) && !shouldFilterApplication(packageStateInternal2, i2, i) && (generatePackageInfo = generatePackageInfo(packageStateInternal2, j, i)) != null) {
                                    arrayList.add(generatePackageInfo);
                                }
                            }
                        }
                    }
                }
                packageStateInternal2 = packageStateInternal5;
                if (!z2) {
                }
                if (!filterSharedLibPackage(packageStateInternal2, i2, i, j)) {
                    arrayList.add(generatePackageInfo);
                }
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ResolveInfo createForwardingResolveInfoUnchecked(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, int i2) {
        java.lang.String str;
        android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean isManagedProfile = this.mUserManager.getUserInfo(i2).isManagedProfile();
            if (isManagedProfile) {
                str = com.android.internal.app.IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE;
            } else {
                str = com.android.internal.app.IntentForwarderActivity.FORWARD_INTENT_TO_PARENT;
            }
            android.content.pm.ActivityInfo activityInfoCrossProfile = getActivityInfoCrossProfile(new android.content.ComponentName(androidApplication().packageName, str), 0L, i);
            if (!isManagedProfile) {
                activityInfoCrossProfile.showUserIcon = i2;
                resolveInfo.noResourceId = true;
            }
            resolveInfo.activityInfo = activityInfoCrossProfile;
            resolveInfo.priority = 0;
            resolveInfo.preferredOrder = 0;
            resolveInfo.match = 0;
            resolveInfo.isDefault = true;
            resolveInfo.filter = new android.content.IntentFilter(watchedIntentFilter.getIntentFilter());
            resolveInfo.targetUserId = i2;
            resolveInfo.userHandle = android.os.UserHandle.of(i);
            return resolveInfo;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserOrProfilePermission(callingUid, i, false, false, "get service info");
        return getServiceInfoBody(componentName, updateFlagsForComponent, i, callingUid);
    }

    protected android.content.pm.ServiceInfo getServiceInfoBody(android.content.ComponentName componentName, long j, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.internal.pm.pkg.component.ParsedMainComponent service = this.mComponentResolver.getService(componentName);
        if (service == null) {
            return null;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(service.getPackageName());
        if (!this.mSettings.isEnabledAndMatch(androidPackage, service, j, i) || (packageStateInternal = this.mSettings.getPackage(componentName.getPackageName())) == null || shouldFilterApplication(packageStateInternal, i2, componentName, 3, i)) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateServiceInfo(androidPackage, service, j, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public final android.content.pm.SharedLibraryInfo getSharedLibraryInfo(java.lang.String str, long j) {
        return this.mSharedLibraries.getSharedLibraryInfo(str, j);
    }

    @Override // com.android.server.pm.Computer
    public java.lang.String getInstantAppPackageName(int i) {
        if (android.os.Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        com.android.server.utils.Watchable settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (!(settingBase instanceof com.android.server.pm.pkg.PackageStateInternal)) {
            return null;
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = (com.android.server.pm.pkg.PackageStateInternal) settingBase;
        if (packageStateInternal.getUserStateOrDefault(android.os.UserHandle.getUserId(i)).isInstantApp()) {
            return packageStateInternal.getPkg().getPackageName();
        }
        return null;
    }

    private int getIsolatedOwner(int i) {
        int i2 = this.mIsolatedOwners.get(i, -1);
        if (i2 == -1) {
            throw new java.lang.IllegalStateException("No owner UID found for isolated UID " + i);
        }
        return i2;
    }

    @Override // com.android.server.pm.Computer
    public final java.lang.String resolveExternalPackageName(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        if (androidPackage.getStaticSharedLibraryName() != null) {
            return androidPackage.getManifestPackageName();
        }
        return androidPackage.getPackageName();
    }

    private java.lang.String resolveInternalPackageNameInternalLocked(java.lang.String str, long j, int i) {
        android.util.LongSparseLongArray longSparseLongArray;
        java.lang.String renamedPackageLPr = this.mSettings.getRenamedPackageLPr(str);
        if (renamedPackageLPr != null) {
            str = renamedPackageLPr;
        }
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> staticLibraryInfos = this.mSharedLibraries.getStaticLibraryInfos(str);
        if (staticLibraryInfos == null || staticLibraryInfos.size() <= 0) {
            return str;
        }
        android.content.pm.SharedLibraryInfo sharedLibraryInfo = null;
        if (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.UserHandle.getAppId(i))) {
            longSparseLongArray = null;
        } else {
            longSparseLongArray = new android.util.LongSparseLongArray();
            java.lang.String name = staticLibraryInfos.valueAt(0).getName();
            java.lang.String[] packagesForUidInternal = getPackagesForUidInternal(i, i);
            if (packagesForUidInternal != null) {
                for (java.lang.String str2 : packagesForUidInternal) {
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str2);
                    int indexOf = com.android.internal.util.ArrayUtils.indexOf(packageStateInternal.getUsesStaticLibraries(), name);
                    if (indexOf >= 0) {
                        long j2 = packageStateInternal.getUsesStaticLibrariesVersions()[indexOf];
                        longSparseLongArray.append(j2, j2);
                    }
                }
            }
        }
        if (longSparseLongArray != null && longSparseLongArray.size() <= 0) {
            return str;
        }
        int size = staticLibraryInfos.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.SharedLibraryInfo valueAt = staticLibraryInfos.valueAt(i2);
            if (longSparseLongArray == null || longSparseLongArray.indexOfKey(valueAt.getLongVersion()) >= 0) {
                long longVersionCode = valueAt.getDeclaringPackage().getLongVersionCode();
                if (j != -1) {
                    if (longVersionCode == j) {
                        return valueAt.getPackageName();
                    }
                } else if (sharedLibraryInfo == null || longVersionCode > sharedLibraryInfo.getDeclaringPackage().getLongVersionCode()) {
                    sharedLibraryInfo = valueAt;
                }
            }
        }
        if (sharedLibraryInfo != null) {
            return sharedLibraryInfo.getPackageName();
        }
        return str;
    }

    @Override // com.android.server.pm.Computer
    public final java.lang.String resolveInternalPackageName(java.lang.String str, long j) {
        return resolveInternalPackageNameInternalLocked(str, j, android.os.Binder.getCallingUid());
    }

    @Override // com.android.server.pm.Computer
    public final java.lang.String[] getPackagesForUid(int i) {
        return getPackagesForUidInternal(i, android.os.Binder.getCallingUid());
    }

    private java.lang.String[] getPackagesForUidInternal(int i, int i2) {
        boolean z = getInstantAppPackageName(i2) != null;
        int userId = android.os.UserHandle.getUserId(i);
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        return getPackagesForUidInternalBody(i2, userId, android.os.UserHandle.getAppId(i), z);
    }

    protected java.lang.String[] getPackagesForUidInternalBody(int i, int i2, int i3, boolean z) {
        com.android.server.utils.Watchable settingBase = this.mSettings.getSettingBase(i3);
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            if (z) {
                return null;
            }
            android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = ((com.android.server.pm.SharedUserSetting) settingBase).getPackageStates();
            int size = packageStates.size();
            java.lang.String[] strArr = new java.lang.String[size];
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i5);
                if (valueAt.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(valueAt, i, i2)) {
                    strArr[i4] = valueAt.getPackageName();
                    i4++;
                }
            }
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.trimToSize(strArr, i4);
        }
        if (settingBase instanceof com.android.server.pm.pkg.PackageStateInternal) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = (com.android.server.pm.pkg.PackageStateInternal) settingBase;
            if (packageStateInternal.getUserStateOrDefault(i2).isInstalled() && !shouldFilterApplication(packageStateInternal, i, i2)) {
                return new java.lang.String[]{packageStateInternal.getPackageName()};
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.UserInfo getProfileParent(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mUserManager.getProfileParent(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean areWebInstantAppsDisabled(int i) {
        return this.mWebInstantAppsDisabled.get(i);
    }

    @Override // com.android.server.pm.Computer
    public final boolean canViewInstantApps(int i, int i2) {
        if (i < 10000 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_INSTANT_APPS") == 0) {
            return true;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.VIEW_INSTANT_APPS") != 0) {
            return false;
        }
        android.content.ComponentName defaultHomeActivity = getDefaultHomeActivity(i2);
        if (defaultHomeActivity == null || !isCallerSameApp(defaultHomeActivity.getPackageName(), i)) {
            return this.mAppPredictionServicePackage != null && isCallerSameApp(this.mAppPredictionServicePackage, i);
        }
        return true;
    }

    private boolean filterStaticSharedLibPackage(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2, long j) {
        android.content.pm.SharedLibraryInfo sharedLibraryInfo;
        int indexOf;
        if (((j & 67108864) != 0 && (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.UserHandle.getAppId(i)) || checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0)) || packageStateInternal == null || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isStaticSharedLibrary() || (sharedLibraryInfo = getSharedLibraryInfo(packageStateInternal.getPkg().getStaticSharedLibraryName(), packageStateInternal.getPkg().getStaticSharedLibraryVersion())) == null) {
            return false;
        }
        java.lang.String[] packagesForUid = getPackagesForUid(android.os.UserHandle.getUid(i2, android.os.UserHandle.getAppId(i)));
        if (packagesForUid == null) {
            return true;
        }
        for (java.lang.String str : packagesForUid) {
            if (packageStateInternal.getPackageName().equals(str)) {
                return false;
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str);
            if (packageStateInternal2 != null && (indexOf = com.android.internal.util.ArrayUtils.indexOf(packageStateInternal2.getUsesStaticLibraries(), sharedLibraryInfo.getName())) >= 0 && packageStateInternal2.getPkg().getUsesStaticLibrariesVersions()[indexOf] == sharedLibraryInfo.getLongVersion()) {
                return false;
            }
        }
        return true;
    }

    private boolean filterSdkLibPackage(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2, long j) {
        android.content.pm.SharedLibraryInfo sharedLibraryInfo;
        int indexOf;
        if (((j & 67108864) != 0 && (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(android.os.UserHandle.getAppId(i)) || checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0)) || packageStateInternal == null || packageStateInternal.getPkg() == null || !packageStateInternal.getPkg().isSdkLibrary() || (sharedLibraryInfo = getSharedLibraryInfo(packageStateInternal.getPkg().getSdkLibraryName(), packageStateInternal.getPkg().getSdkLibVersionMajor())) == null) {
            return false;
        }
        java.lang.String[] packagesForUid = getPackagesForUid(android.os.UserHandle.getUid(i2, android.os.UserHandle.getAppId(i)));
        if (packagesForUid == null) {
            return true;
        }
        for (java.lang.String str : packagesForUid) {
            if (packageStateInternal.getPackageName().equals(str)) {
                return false;
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(str);
            if (packageStateInternal2 != null && (indexOf = com.android.internal.util.ArrayUtils.indexOf(packageStateInternal2.getUsesSdkLibraries(), sharedLibraryInfo.getName())) >= 0 && packageStateInternal2.getPkg().getUsesSdkLibrariesVersionsMajor()[indexOf] == sharedLibraryInfo.getLongVersion()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public final boolean filterSharedLibPackage(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2, long j) {
        return filterStaticSharedLibPackage(packageStateInternal, i, i2, j) || filterSdkLibPackage(packageStateInternal, i, i2, j);
    }

    private boolean hasCrossUserPermission(int i, int i2, int i3, boolean z, boolean z2) {
        if ((!z2 && i3 == i2) || com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(i)) {
            return true;
        }
        if (z) {
            return hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL");
        }
        return hasPermission("android.permission.INTERACT_ACROSS_USERS_FULL") || hasPermission("android.permission.INTERACT_ACROSS_USERS");
    }

    private boolean hasNonNegativePriority(java.util.List<android.content.pm.ResolveInfo> list) {
        return list.size() > 0 && list.get(0).priority >= 0;
    }

    private boolean hasPermission(java.lang.String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(java.lang.String str, int i) {
        return isCallerSameApp(str, i, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isCallerSameApp(java.lang.String str, int i, boolean z) {
        if (android.os.Process.isSdkSandboxUid(i)) {
            return str != null && str.equals(this.mService.getSdkSandboxPackageName());
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (z && android.os.Process.isIsolated(i)) {
            i = getIsolatedOwner(i);
        }
        return androidPackage != null && android.os.UserHandle.getAppId(i) == androidPackage.getUid();
    }

    private boolean isCallerFromManagedUserOrProfile(int i) {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) this.mInjector.getLocalService(android.app.admin.DevicePolicyManagerInternal.class);
        return devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(i);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isComponentVisibleToInstantApp(@android.annotation.Nullable android.content.ComponentName componentName) {
        if (isComponentVisibleToInstantApp(componentName, 1) || isComponentVisibleToInstantApp(componentName, 3)) {
            return true;
        }
        return isComponentVisibleToInstantApp(componentName, 4);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isComponentVisibleToInstantApp(@android.annotation.Nullable android.content.ComponentName componentName, int i) {
        if (i == 1) {
            com.android.internal.pm.pkg.component.ParsedActivity activity = this.mComponentResolver.getActivity(componentName);
            if (activity == null) {
                return false;
            }
            return ((activity.getFlags() & 1048576) != 0) && ((activity.getFlags() & 2097152) == 0);
        }
        if (i == 2) {
            com.android.internal.pm.pkg.component.ParsedActivity receiver = this.mComponentResolver.getReceiver(componentName);
            if (receiver == null) {
                return false;
            }
            return ((receiver.getFlags() & 1048576) != 0) && !((receiver.getFlags() & 2097152) == 0);
        }
        if (i == 3) {
            com.android.internal.pm.pkg.component.ParsedService service = this.mComponentResolver.getService(componentName);
            return (service == null || (service.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i == 4) {
            com.android.internal.pm.pkg.component.ParsedProvider provider = this.mComponentResolver.getProvider(componentName);
            return (provider == null || (provider.getFlags() & 1048576) == 0) ? false : true;
        }
        if (i == 0) {
            return isComponentVisibleToInstantApp(componentName);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public final boolean isImplicitImageCaptureIntentAndNotSetByDpc(android.content.Intent intent, int i, java.lang.String str, long j) {
        return intent.isImplicitImageCaptureIntent() && !isPersistentPreferredActivitySetByDpm(intent, i, str, j);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantApp(java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "isInstantApp");
        return isInstantAppInternal(str, i, callingUid);
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstantAppInternal(java.lang.String str, int i, int i2) {
        return isInstantAppInternalBody(str, i, i2);
    }

    protected boolean isInstantAppInternalBody(java.lang.String str, int i, int i2) {
        if (android.os.Process.isIsolated(i2)) {
            i2 = getIsolatedOwner(i2);
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (!(packageStateInternal != null && (isCallerSameApp(str, i2) || canViewInstantApps(i2, i) || this.mInstantAppRegistry.isInstantAccessGranted(i, android.os.UserHandle.getAppId(i2), packageStateInternal.getAppId())))) {
            return false;
        }
        return packageStateInternal.getUserStateOrDefault(i).isInstantApp();
    }

    private boolean isInstantAppResolutionAllowed(android.content.Intent intent, java.util.List<android.content.pm.ResolveInfo> list, int i, boolean z, long j) {
        if (this.mInstantAppResolverConnection == null || instantAppInstallerActivity() == null || intent.getComponent() != null || (intent.getFlags() & Integer.MIN_VALUE) != 0 || (intent.getFlags() & 1024) != 0) {
            return false;
        }
        if (!z && intent.getPackage() != null) {
            return false;
        }
        if (!intent.isWebIntent()) {
            if ((list != null && list.size() != 0) || (intent.getFlags() & 2048) == 0) {
                return false;
            }
        } else if (intent.getData() == null || android.text.TextUtils.isEmpty(intent.getData().getHost()) || areWebInstantAppsDisabled(i)) {
            return false;
        }
        return isInstantAppResolutionAllowedBody(intent, list, i, z, j);
    }

    protected boolean isInstantAppResolutionAllowedBody(android.content.Intent intent, java.util.List<android.content.pm.ResolveInfo> list, int i, boolean z, long j) {
        int size = list == null ? 0 : list.size();
        boolean z2 = (intent.getFlags() & 8) != 0;
        if (z2) {
            android.util.Slog.d("PackageManager", "Checking if instant app resolution allowed, resolvedActivities = " + list);
        }
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ResolveInfo resolveInfo = list.get(i2);
            java.lang.String str = resolveInfo.activityInfo.packageName;
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
            if (packageStateInternal != null) {
                if (!resolveInfo.handleAllWebDataURI && com.android.server.pm.PackageManagerServiceUtils.hasAnyDomainApproval(this.mDomainVerificationManager, packageStateInternal, intent, j, i)) {
                    if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                        android.util.Slog.v("PackageManager", "DENY instant app; pkg: " + str + ", approved");
                    }
                    return false;
                }
                if (packageStateInternal.getUserStateOrDefault(i).isInstantApp()) {
                    if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT) {
                        android.util.Slog.v("PackageManager", "DENY instant app installed; pkg: " + str);
                    }
                    return false;
                }
            } else if (z2) {
                android.util.Slog.d("PackageManager", "Could not find package " + str);
            }
        }
        return true;
    }

    private boolean isPersistentPreferredActivitySetByDpm(android.content.Intent intent, int i, java.lang.String str, long j) {
        java.util.List arrayList;
        com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredActivities = this.mSettings.getPersistentPreferredActivities(i);
        if (persistentPreferredActivities != null) {
            arrayList = persistentPreferredActivities.queryIntent(this, intent, str, (j & 65536) != 0, i);
        } else {
            arrayList = new java.util.ArrayList();
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((com.android.server.pm.PersistentPreferredActivity) it.next()).mIsSetByDpm) {
                return true;
            }
        }
        return false;
    }

    private boolean isRecentsAccessingChildProfiles(int i, int i2) {
        if (!((com.android.server.wm.ActivityTaskManagerInternal) this.mInjector.getLocalService(com.android.server.wm.ActivityTaskManagerInternal.class)).isCallerRecents(i)) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int userId = android.os.UserHandle.getUserId(i);
            if (android.app.ActivityManager.getCurrentUser() != userId) {
                return false;
            }
            return this.mUserManager.isSameProfileGroup(userId, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    public final boolean isSameProfileGroup(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return com.android.server.pm.UserManagerService.getInstance().isSameProfileGroup(i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3, boolean z, boolean z2) {
        int i4;
        if (android.os.Process.isSdkSandboxUid(i)) {
            int appUidForSdkSandboxUid = android.os.Process.getAppUidForSdkSandboxUid(i);
            if (packageStateInternal != null && appUidForSdkSandboxUid == android.os.UserHandle.getUid(i3, packageStateInternal.getAppId())) {
                return false;
            }
        }
        if (!android.os.Process.isIsolated(i)) {
            i4 = i;
        } else {
            i4 = getIsolatedOwner(i);
        }
        boolean z3 = getInstantAppPackageName(i4) != null;
        boolean z4 = packageStateInternal != null && com.android.server.pm.PackageArchiver.isArchived(packageStateInternal.getUserStateOrDefault(i3));
        if (packageStateInternal == null || !(!z || com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i4) || packageStateInternal.isHiddenUntilInstalled() || packageStateInternal.getUserStateOrDefault(i3).isInstalled() || (z4 && !z2))) {
            return z3 || z || android.os.Process.isSdkSandboxUid(i4);
        }
        if (isCallerSameApp(packageStateInternal.getPackageName(), i4)) {
            return false;
        }
        if (z3) {
            if (packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
                return true;
            }
            if (componentName == null) {
                return !packageStateInternal.getPkg().isVisibleToInstantApps();
            }
            com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation = this.mInstrumentation.get(componentName);
            if (parsedInstrumentation == null || !isCallerSameApp(parsedInstrumentation.getTargetPackage(), i4)) {
                return !isComponentVisibleToInstantApp(componentName, i2);
            }
            return false;
        }
        if (packageStateInternal.getUserStateOrDefault(i3).isInstantApp()) {
            if (canViewInstantApps(i4, i3)) {
                return false;
            }
            if (componentName != null) {
                return true;
            }
            return !this.mInstantAppRegistry.isInstantAccessGranted(i3, android.os.UserHandle.getAppId(i4), packageStateInternal.getAppId());
        }
        return this.mAppsFilter.shouldFilterApplication(this, i4, this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i4)), packageStateInternal, i3);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3, boolean z) {
        return shouldFilterApplication(packageStateInternal, i, componentName, i2, i3, z, true);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, @android.annotation.Nullable android.content.ComponentName componentName, int i2, int i3) {
        return shouldFilterApplication(packageStateInternal, i, componentName, i2, i3, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplication(@android.annotation.NonNull com.android.server.pm.SharedUserSetting sharedUserSetting, int i, int i2) {
        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = sharedUserSetting.getPackageStates();
        boolean z = true;
        for (int size = packageStates.size() - 1; size >= 0 && z; size--) {
            z &= shouldFilterApplication(packageStates.valueAt(size), i, null, 0, i2, false);
        }
        return z;
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplicationIncludingUninstalled(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, true);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplicationIncludingUninstalledNotArchived(@android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i, int i2) {
        return shouldFilterApplication(packageStateInternal, i, null, 0, i2, true, false);
    }

    @Override // com.android.server.pm.Computer
    public final boolean shouldFilterApplicationIncludingUninstalled(@android.annotation.NonNull com.android.server.pm.SharedUserSetting sharedUserSetting, int i, int i2) {
        if (shouldFilterApplication(sharedUserSetting, i, i2)) {
            return true;
        }
        if (com.android.server.pm.PackageManagerServiceUtils.isSystemOrRootOrShell(i)) {
            return false;
        }
        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = sharedUserSetting.getPackageStates();
        for (int i3 = 0; i3 < packageStates.size(); i3++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i3);
            if (valueAt.getUserStateOrDefault(i2).isInstalled() || valueAt.isHiddenUntilInstalled()) {
                return false;
            }
        }
        return true;
    }

    private int bestDomainVerificationStatus(int i, int i2) {
        if (i == 3) {
            return i2;
        }
        if (i2 == 3) {
            return i;
        }
        return (int) android.util.MathUtils.max(i, i2);
    }

    @Override // com.android.server.pm.Computer
    public final int checkUidPermission(java.lang.String str, int i) {
        return this.mPermissionManager.checkUidPermission(i, str, 0);
    }

    @Override // com.android.server.pm.Computer
    public int getPackageUidInternal(java.lang.String str, long j, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = this.mSettings.getPackage(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (androidPackage != null && com.android.server.pm.parsing.pkg.AndroidPackageUtils.isMatchForSystemOnly(packageStateInternal3, j) && (packageStateInternal2 = getPackageStateInternal(androidPackage.getPackageName(), i2)) != null && packageStateInternal2.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal2, i2, i)) {
            return android.os.UserHandle.getUid(i, androidPackage.getUid());
        }
        if ((4299169792L & j) != 0 && (packageStateInternal = this.mSettings.getPackage(str)) != null && com.android.server.pm.pkg.PackageStateUtils.isMatch(packageStateInternal, j) && !shouldFilterApplication(packageStateInternal, i2, i)) {
            return android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
        }
        return -1;
    }

    private long updateFlags(long j, int i) {
        if ((j & 786432) == 0) {
            if (this.mInjector.getUserManagerInternal().isUserUnlockingOrUnlocked(i)) {
                return j | 786432;
            }
            return j | 524288;
        }
        return j;
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForApplication(long j, int i) {
        return updateFlagsForPackage(j, i);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForComponent(long j, int i) {
        return updateFlags(j, i);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForPackage(long j, int i) {
        long j2;
        boolean z = android.os.UserHandle.getCallingUserId() == 0;
        if ((j & 4194304) != 0) {
            enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, false, false, !isRecentsAccessingChildProfiles(android.os.Binder.getCallingUid(), i), "MATCH_ANY_USER flag requires INTERACT_ACROSS_USERS permission");
        } else if ((8192 & j) != 0 && z && this.mUserManager.hasProfile(0)) {
            j2 = j | 4194304;
            return updateFlags(j2, i);
        }
        j2 = j;
        return updateFlags(j2, i);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2) {
        return updateFlagsForResolve(j, i, i2, z, false, z2);
    }

    @Override // com.android.server.pm.Computer
    public final long updateFlagsForResolve(long j, int i, int i2, boolean z, boolean z2, boolean z3) {
        long j2;
        if (safeMode() || z3) {
            j |= 1048576;
        }
        if (getInstantAppPackageName(i2) != null) {
            if (z2) {
                j |= 33554432;
            }
            j2 = j | 16777216 | 8388608;
        } else {
            boolean z4 = false;
            boolean z5 = (j & 8388608) != 0;
            if (z || (z5 && canViewInstantApps(i2, i))) {
                z4 = true;
            }
            j2 = j & (-50331649);
            if (!z4) {
                j2 &= -8388609;
            }
        }
        return updateFlagsForComponent(j2, i);
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserOrProfilePermission(int i, int i2, boolean z, boolean z2, java.lang.String str) {
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            com.android.server.pm.PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", i, i2);
        }
        int userId = android.os.UserHandle.getUserId(i);
        if (hasCrossUserPermission(i, userId, i2, z, false)) {
            return;
        }
        boolean isSameProfileGroup = isSameProfileGroup(userId, i2);
        if (isSameProfileGroup && android.content.PermissionChecker.checkPermissionForPreflight(this.mContext, "android.permission.INTERACT_ACROSS_PROFILES", -1, i, getPackage(i).getPackageName()) == 0) {
            return;
        }
        java.lang.String buildInvalidCrossUserOrProfilePermissionMessage = buildInvalidCrossUserOrProfilePermissionMessage(i, i2, str, z, isSameProfileGroup);
        android.util.Slog.w("PackageManager", buildInvalidCrossUserOrProfilePermissionMessage);
        throw new java.lang.SecurityException(buildInvalidCrossUserOrProfilePermissionMessage);
    }

    private static java.lang.String buildInvalidCrossUserOrProfilePermissionMessage(int i, int i2, java.lang.String str, boolean z, boolean z2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
            if (z2) {
                sb.append(" or ");
                sb.append("android.permission.INTERACT_ACROSS_PROFILES");
            }
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, java.lang.String str) {
        enforceCrossUserPermission(i, i2, z, z2, false, str);
    }

    @Override // com.android.server.pm.Computer
    public final void enforceCrossUserPermission(int i, int i2, boolean z, boolean z2, boolean z3, java.lang.String str) {
        if (i2 < 0) {
            throw new java.lang.IllegalArgumentException("Invalid userId " + i2);
        }
        if (z2) {
            com.android.server.pm.PackageManagerServiceUtils.enforceShellRestriction(this.mInjector.getUserManagerInternal(), "no_debugging_features", i, i2);
        }
        if (hasCrossUserPermission(i, android.os.UserHandle.getUserId(i), i2, z, z3)) {
            return;
        }
        java.lang.String buildInvalidCrossUserPermissionMessage = buildInvalidCrossUserPermissionMessage(i, i2, str, z);
        android.util.Slog.w("PackageManager", buildInvalidCrossUserPermissionMessage);
        throw new java.lang.SecurityException(buildInvalidCrossUserPermissionMessage);
    }

    private static java.lang.String buildInvalidCrossUserPermissionMessage(int i, int i2, java.lang.String str, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str);
            sb.append(": ");
        }
        sb.append("UID ");
        sb.append(i);
        sb.append(" requires ");
        sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
        if (!z) {
            sb.append(" or ");
            sb.append("android.permission.INTERACT_ACROSS_USERS");
        }
        sb.append(" to access user ");
        sb.append(i2);
        sb.append(".");
        return sb.toString();
    }

    @Override // com.android.server.pm.Computer
    public android.content.pm.SigningDetails getSigningDetails(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (androidPackage == null) {
            return null;
        }
        return androidPackage.getSigningDetails();
    }

    @Override // com.android.server.pm.Computer
    public android.content.pm.SigningDetails getSigningDetails(int i) {
        com.android.server.utils.Watchable settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase != null) {
            if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
                return ((com.android.server.pm.SharedUserSetting) settingBase).signatures.mSigningDetails;
            }
            if (settingBase instanceof com.android.server.pm.pkg.PackageStateInternal) {
                return ((com.android.server.pm.pkg.PackageStateInternal) settingBase).getSigningDetails();
            }
        }
        return android.content.pm.SigningDetails.UNKNOWN;
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(com.android.server.pm.pkg.AndroidPackage androidPackage, int i, int i2) {
        return shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), i, i2);
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(java.lang.String str, int i, int i2, boolean z) {
        return shouldFilterApplication(getPackageStateInternal(str), i, null, 0, i2, z);
    }

    @Override // com.android.server.pm.Computer
    public boolean filterAppAccess(int i, int i2) {
        if (android.os.Process.isSdkSandboxUid(i)) {
            return (i2 == i || android.os.Process.getAppUidForSdkSandboxUid(i) == i) ? false : true;
        }
        int userId = android.os.UserHandle.getUserId(i);
        com.android.server.utils.Watchable settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase == null) {
            return true;
        }
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            return shouldFilterApplicationIncludingUninstalled((com.android.server.pm.SharedUserSetting) settingBase, i2, userId);
        }
        if (settingBase instanceof com.android.server.pm.pkg.PackageStateInternal) {
            return shouldFilterApplicationIncludingUninstalled((com.android.server.pm.pkg.PackageStateInternal) settingBase, i2, userId);
        }
        return true;
    }

    @Override // com.android.server.pm.Computer
    public void dump(int i, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, com.android.server.pm.DumpState dumpState) {
        java.util.Collection<? extends com.android.server.pm.pkg.PackageStateInternal> values;
        java.util.Collection<? extends com.android.server.pm.pkg.PackageStateInternal> values2;
        java.lang.String targetPackageName = dumpState.getTargetPackageName();
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(targetPackageName);
        dumpState.isCheckIn();
        if (targetPackageName != null && packageStateInternal == null && !isApexPackage(targetPackageName)) {
            return;
        }
        switch (i) {
            case 1:
                this.mSharedLibraries.dump(printWriter, dumpState);
                return;
            case 512:
                this.mSettings.dumpReadMessages(printWriter, dumpState);
                return;
            case 4096:
                this.mSettings.dumpPreferred(printWriter, dumpState, targetPackageName);
                return;
            case 8192:
                printWriter.flush();
                java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(fileDescriptor));
                com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
                try {
                    newFastSerializer.setOutput(bufferedOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                    newFastSerializer.startDocument((java.lang.String) null, true);
                    newFastSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    this.mSettings.writePreferredActivitiesLPr(newFastSerializer, 0, dumpState.isFullPreferred());
                    newFastSerializer.endDocument();
                    newFastSerializer.flush();
                    return;
                } catch (java.io.IOException e) {
                    printWriter.println("Failed writing: " + e);
                    return;
                } catch (java.lang.IllegalArgumentException e2) {
                    printWriter.println("Failed writing: " + e2);
                    return;
                } catch (java.lang.IllegalStateException e3) {
                    printWriter.println("Failed writing: " + e3);
                    return;
                }
            case 32768:
                if (dumpState.onTitlePrinted()) {
                    printWriter.println();
                }
                printWriter.println("Database versions:");
                this.mSettings.dumpVersionLPr(new com.android.internal.util.IndentingPrintWriter(printWriter, "  "));
                return;
            case 262144:
                android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
                if (dumpState.onTitlePrinted()) {
                    printWriter.println();
                }
                indentingPrintWriter.println("Domain verification status:");
                indentingPrintWriter.increaseIndent();
                try {
                    this.mDomainVerificationManager.printState(this, indentingPrintWriter, targetPackageName, -1);
                } catch (java.lang.Exception e4) {
                    printWriter.println("Failure printing domain verification information");
                    android.util.Slog.e("PackageManager", "Failure printing domain verification information", e4);
                }
                indentingPrintWriter.decreaseIndent();
                return;
            case 524288:
                if (dumpState.onTitlePrinted()) {
                    printWriter.println();
                }
                com.android.internal.util.IndentingPrintWriter indentingPrintWriter2 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
                indentingPrintWriter2.println();
                indentingPrintWriter2.println("Frozen packages:");
                indentingPrintWriter2.increaseIndent();
                if (this.mFrozenPackages.size() == 0) {
                    indentingPrintWriter2.println("(none)");
                } else {
                    for (int i2 = 0; i2 < this.mFrozenPackages.size(); i2++) {
                        indentingPrintWriter2.print("package=");
                        indentingPrintWriter2.print(this.mFrozenPackages.keyAt(i2));
                        indentingPrintWriter2.print(", refCounts=");
                        indentingPrintWriter2.println(this.mFrozenPackages.valueAt(i2));
                    }
                }
                indentingPrintWriter2.decreaseIndent();
                return;
            case 1048576:
                com.android.internal.util.IndentingPrintWriter indentingPrintWriter3 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                if (dumpState.onTitlePrinted()) {
                    printWriter.println();
                }
                indentingPrintWriter3.println("Dexopt state:");
                indentingPrintWriter3.increaseIndent();
                if (com.android.server.pm.DexOptHelper.useArtService()) {
                    com.android.server.pm.DexOptHelper.dumpDexoptState(indentingPrintWriter3, targetPackageName);
                } else {
                    if (packageStateInternal != null) {
                        values = java.util.Collections.singletonList(packageStateInternal);
                    } else {
                        values = this.mSettings.getPackages().values();
                    }
                    for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 : values) {
                        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal2.getPkg();
                        if (pkg != null && !pkg.isApex()) {
                            java.lang.String packageName = pkg.getPackageName();
                            indentingPrintWriter3.println("[" + packageName + "]");
                            indentingPrintWriter3.increaseIndent();
                            try {
                                this.mPackageDexOptimizer.dumpDexoptState(indentingPrintWriter3, pkg, packageStateInternal2, this.mDexManager.getPackageUseInfoOrDefault(packageName));
                                indentingPrintWriter3.decreaseIndent();
                            } catch (com.android.server.pm.Installer.LegacyDexoptDisabledException e5) {
                                throw new java.lang.RuntimeException(e5);
                            }
                        }
                    }
                    indentingPrintWriter3.println("BgDexopt state:");
                    indentingPrintWriter3.increaseIndent();
                    this.mBackgroundDexOptService.dump(indentingPrintWriter3);
                    indentingPrintWriter3.decreaseIndent();
                }
                indentingPrintWriter3.decreaseIndent();
                return;
            case 2097152:
                com.android.internal.util.IndentingPrintWriter indentingPrintWriter4 = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                if (dumpState.onTitlePrinted()) {
                    printWriter.println();
                }
                indentingPrintWriter4.println("Compiler stats:");
                indentingPrintWriter4.increaseIndent();
                if (packageStateInternal != null) {
                    values2 = java.util.Collections.singletonList(packageStateInternal);
                } else {
                    values2 = this.mSettings.getPackages().values();
                }
                java.util.Iterator<? extends com.android.server.pm.pkg.PackageStateInternal> it = values2.iterator();
                while (it.hasNext()) {
                    com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg2 = it.next().getPkg();
                    if (pkg2 != null) {
                        java.lang.String packageName2 = pkg2.getPackageName();
                        indentingPrintWriter4.println("[" + packageName2 + "]");
                        indentingPrintWriter4.increaseIndent();
                        com.android.server.pm.CompilerStats.PackageStats packageStats = this.mCompilerStats.getPackageStats(packageName2);
                        if (packageStats == null) {
                            indentingPrintWriter4.println("(No recorded stats)");
                        } else {
                            packageStats.dump(indentingPrintWriter4);
                        }
                        indentingPrintWriter4.decreaseIndent();
                    }
                }
                return;
            case 33554432:
                if (targetPackageName == null || isApexPackage(targetPackageName)) {
                    this.mApexManager.dump(printWriter);
                    dumpApex(printWriter, targetPackageName);
                    return;
                }
                return;
            case 67108864:
                this.mAppsFilter.dumpQueries(printWriter, packageStateInternal != null ? java.lang.Integer.valueOf(packageStateInternal.getAppId()) : null, dumpState, this.mUserManager.getUserIds(), new com.android.internal.util.function.QuadFunction() { // from class: com.android.server.pm.ComputerEngine$$ExternalSyntheticLambda3
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                        return com.android.server.pm.ComputerEngine.this.getPackagesForUidInternalBody(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue());
                    }
                });
                return;
            default:
                return;
        }
    }

    private void generateApexPackageInfo(@android.annotation.NonNull java.util.List<com.android.server.pm.pkg.PackageStateInternal> list, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.PackageStateInternal> list2, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.PackageStateInternal> list3, @android.annotation.NonNull java.util.List<com.android.server.pm.pkg.PackageStateInternal> list4) {
        for (com.android.server.pm.pkg.AndroidPackage androidPackage : this.mPackages.values()) {
            java.lang.String packageName = androidPackage.getPackageName();
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(packageName);
            if (androidPackage.isApex() && packageStateInternal != null) {
                list.add(packageStateInternal);
                if (!packageStateInternal.isUpdatedSystemApp()) {
                    list3.add(packageStateInternal);
                } else {
                    com.android.server.pm.pkg.PackageStateInternal disabledSystemPkg = this.mSettings.getDisabledSystemPkg(packageName);
                    list4.add(disabledSystemPkg);
                    list2.add(disabledSystemPkg);
                }
            }
        }
    }

    private void dumpApex(java.io.PrintWriter printWriter, java.lang.String str) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        generateApexPackageInfo(arrayList, arrayList2, arrayList3, arrayList4);
        indentingPrintWriter.println("Active APEX packages:");
        dumpApexPackageStates(arrayList, true, str, indentingPrintWriter);
        indentingPrintWriter.println("Inactive APEX packages:");
        dumpApexPackageStates(arrayList2, false, str, indentingPrintWriter);
        indentingPrintWriter.println("Factory APEX packages:");
        dumpApexPackageStates(arrayList3, true, str, indentingPrintWriter);
        dumpApexPackageStates(arrayList4, false, str, indentingPrintWriter);
    }

    private static void dumpApexPackageStates(java.util.List<com.android.server.pm.pkg.PackageStateInternal> list, boolean z, @android.annotation.Nullable java.lang.String str, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = list.get(i);
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
            if (str == null || str.equals(pkg.getPackageName())) {
                indentingPrintWriter.println(pkg.getPackageName());
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("Version: " + pkg.getLongVersionCode());
                indentingPrintWriter.println("Path: " + pkg.getBaseApkPath());
                indentingPrintWriter.println("IsActive: " + z);
                indentingPrintWriter.println("IsFactory: " + (packageStateInternal.isUpdatedSystemApp() ^ true));
                indentingPrintWriter.println("ApplicationInfo: ");
                indentingPrintWriter.increaseIndent();
                com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(pkg).dump(new android.util.PrintWriterPrinter(indentingPrintWriter), "");
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    protected com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityBody(android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, boolean z2, boolean z3, int i, boolean z4, int i2, boolean z5) {
        int i3;
        java.util.List list2;
        int i4;
        int i5;
        int i6;
        int i7;
        long j2;
        com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityBodyResult = new com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult();
        long updateFlagsForResolve = updateFlagsForResolve(j, i, i2, false, isImplicitImageCaptureIntentAndNotSetByDpc(intent, i, str, j));
        android.content.Intent updateIntentForResolve = com.android.server.pm.PackageManagerServiceUtils.updateIntentForResolve(intent);
        findPreferredActivityBodyResult.mPreferredResolveInfo = findPersistentPreferredActivity(updateIntentForResolve, str, updateFlagsForResolve, list, z3, i);
        if (findPreferredActivityBodyResult.mPreferredResolveInfo != null) {
            return findPreferredActivityBodyResult;
        }
        com.android.server.pm.PreferredIntentResolver preferredActivities = this.mSettings.getPreferredActivities(i);
        if (z3) {
            android.util.Slog.v("PackageManager", "Looking for preferred activities...");
        }
        if (preferredActivities != null) {
            i3 = 0;
            list2 = preferredActivities.queryIntent(this, updateIntentForResolve, str, (65536 & updateFlagsForResolve) != 0, i);
        } else {
            i3 = 0;
            list2 = null;
        }
        if (list2 != null && list2.size() > 0) {
            if (z3) {
                android.util.Slog.v("PackageManager", "Figuring out best match...");
            }
            int size = list.size();
            int i8 = i3;
            for (int i9 = i8; i9 < size; i9++) {
                android.content.pm.ResolveInfo resolveInfo = list.get(i9);
                if (z3) {
                    android.util.Slog.v("PackageManager", "Match for " + resolveInfo.activityInfo + ": 0x" + java.lang.Integer.toHexString(resolveInfo.match));
                }
                if (resolveInfo.match > i8) {
                    i8 = resolveInfo.match;
                }
            }
            if (z3) {
                android.util.Slog.v("PackageManager", "Best match: 0x" + java.lang.Integer.toHexString(i8));
            }
            int i10 = i8 & 268369920;
            int size2 = list2.size();
            int i11 = 0;
            while (i11 < size2) {
                com.android.server.pm.PreferredActivity preferredActivity = (com.android.server.pm.PreferredActivity) list2.get(i11);
                java.util.List list3 = list2;
                if (!z3) {
                    i4 = size2;
                    i5 = i11;
                } else {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    i4 = size2;
                    sb.append("Checking PreferredActivity ds=");
                    sb.append(preferredActivity.countDataSchemes() > 0 ? preferredActivity.getDataScheme(0) : "<none>");
                    sb.append("\n  component=");
                    sb.append(preferredActivity.mPref.mComponent);
                    android.util.Slog.v("PackageManager", sb.toString());
                    i5 = i11;
                    preferredActivity.dump(new android.util.LogPrinter(2, "PackageManager", 3), "  ");
                }
                if (preferredActivity.mPref.mMatch != i10) {
                    if (!z3) {
                        i7 = size;
                        i6 = i10;
                        j2 = updateFlagsForResolve;
                    } else {
                        android.util.Slog.v("PackageManager", "Skipping bad match " + java.lang.Integer.toHexString(preferredActivity.mPref.mMatch));
                        i7 = size;
                        i6 = i10;
                        j2 = updateFlagsForResolve;
                    }
                } else if (!z || preferredActivity.mPref.mAlways) {
                    int i12 = i10;
                    android.content.pm.ActivityInfo activityInfo = getActivityInfo(preferredActivity.mPref.mComponent, updateFlagsForResolve | 512 | 524288 | 262144, i);
                    if (z3) {
                        android.util.Slog.v("PackageManager", "Found preferred activity:");
                        if (activityInfo != null) {
                            i6 = i12;
                            activityInfo.dump(new android.util.LogPrinter(2, "PackageManager", 3), "  ");
                        } else {
                            i6 = i12;
                            android.util.Slog.v("PackageManager", "  null");
                        }
                    } else {
                        i6 = i12;
                    }
                    boolean z6 = isHomeIntent(updateIntentForResolve) && !z5;
                    boolean z7 = (z6 || z4) ? false : true;
                    if (activityInfo == null) {
                        if (z7) {
                            android.util.Slog.w("PackageManager", "Removing dangling preferred activity: " + preferredActivity.mPref.mComponent);
                            preferredActivities.removeFilter((com.android.server.pm.PreferredIntentResolver) preferredActivity);
                            findPreferredActivityBodyResult.mChanged = true;
                            i7 = size;
                            j2 = updateFlagsForResolve;
                        } else {
                            i7 = size;
                            j2 = updateFlagsForResolve;
                        }
                    } else {
                        int i13 = 0;
                        while (true) {
                            if (i13 >= size) {
                                i7 = size;
                                j2 = updateFlagsForResolve;
                                break;
                            }
                            android.content.pm.ResolveInfo resolveInfo2 = list.get(i13);
                            i7 = size;
                            j2 = updateFlagsForResolve;
                            if (!resolveInfo2.activityInfo.applicationInfo.packageName.equals(activityInfo.applicationInfo.packageName) || !resolveInfo2.activityInfo.name.equals(activityInfo.name)) {
                                i13++;
                                size = i7;
                                updateFlagsForResolve = j2;
                            } else if (z2 && z7) {
                                preferredActivities.removeFilter((com.android.server.pm.PreferredIntentResolver) preferredActivity);
                                findPreferredActivityBodyResult.mChanged = true;
                            } else {
                                if (z && !preferredActivity.mPref.sameSet(list, z6, i)) {
                                    if (preferredActivity.mPref.isSuperset(list, z6)) {
                                        if (z7) {
                                            com.android.server.pm.PreferredActivity preferredActivity2 = new com.android.server.pm.PreferredActivity(preferredActivity, preferredActivity.mPref.mMatch, preferredActivity.mPref.discardObsoleteComponents(list), preferredActivity.mPref.mComponent, preferredActivity.mPref.mAlways);
                                            preferredActivities.removeFilter((com.android.server.pm.PreferredIntentResolver) preferredActivity);
                                            preferredActivities.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) this, (com.android.server.pm.ComputerEngine) preferredActivity2);
                                            findPreferredActivityBodyResult.mChanged = true;
                                        }
                                    } else {
                                        boolean z8 = "android.intent.action.MAIN".equals(updateIntentForResolve.getAction()) && updateIntentForResolve.hasCategory("android.intent.category.HOME");
                                        if (!android.content.pm.Flags.improveHomeAppBehavior() || !z8) {
                                            if (z7) {
                                                android.util.Slog.i("PackageManager", "Result set changed, dropping preferred activity for " + updateIntentForResolve + " type " + str);
                                                preferredActivities.removeFilter((com.android.server.pm.PreferredIntentResolver) preferredActivity);
                                                preferredActivities.addFilter((com.android.server.pm.snapshot.PackageDataSnapshot) this, (com.android.server.pm.ComputerEngine) new com.android.server.pm.PreferredActivity((com.android.server.pm.WatchedIntentFilter) preferredActivity, preferredActivity.mPref.mMatch, (android.content.ComponentName[]) null, preferredActivity.mPref.mComponent, false));
                                                findPreferredActivityBodyResult.mChanged = true;
                                            }
                                            findPreferredActivityBodyResult.mPreferredResolveInfo = null;
                                            return findPreferredActivityBodyResult;
                                        }
                                    }
                                }
                                if (z3) {
                                    android.util.Slog.v("PackageManager", "Returning preferred activity: " + resolveInfo2.activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + resolveInfo2.activityInfo.name);
                                }
                                findPreferredActivityBodyResult.mPreferredResolveInfo = resolveInfo2;
                                return findPreferredActivityBodyResult;
                            }
                        }
                    }
                } else {
                    if (z3) {
                        android.util.Slog.v("PackageManager", "Skipping mAlways=false entry");
                    }
                    i7 = size;
                    i6 = i10;
                    j2 = updateFlagsForResolve;
                }
                i11 = i5 + 1;
                list2 = list3;
                i10 = i6;
                size = i7;
                size2 = i4;
                updateFlagsForResolve = j2;
            }
        }
        return findPreferredActivityBodyResult;
    }

    private static boolean isHomeIntent(android.content.Intent intent) {
        return "android.intent.action.MAIN".equals(intent.getAction()) && intent.hasCategory("android.intent.category.HOME") && intent.hasCategory("android.intent.category.DEFAULT");
    }

    @Override // com.android.server.pm.Computer
    public final com.android.server.pm.PackageManagerService.FindPreferredActivityBodyResult findPreferredActivityInternal(android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        return findPreferredActivityBody(intent, str, j, list, z, z2, z3, i, z4, android.os.Binder.getCallingUid(), android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 1);
    }

    @Override // com.android.server.pm.Computer
    public final android.content.pm.ResolveInfo findPersistentPreferredActivity(android.content.Intent intent, java.lang.String str, long j, java.util.List<android.content.pm.ResolveInfo> list, boolean z, int i) {
        java.util.List list2;
        int size = list.size();
        com.android.server.pm.PersistentPreferredIntentResolver persistentPreferredActivities = this.mSettings.getPersistentPreferredActivities(i);
        if (z) {
            android.util.Slog.v("PackageManager", "Looking for persistent preferred activities...");
        }
        if (persistentPreferredActivities != null) {
            list2 = persistentPreferredActivities.queryIntent(this, intent, str, (j & 65536) != 0, i);
        } else {
            list2 = null;
        }
        if (list2 != null && list2.size() > 0) {
            int size2 = list2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity = (com.android.server.pm.PersistentPreferredActivity) list2.get(i2);
                if (z) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("Checking PersistentPreferredActivity ds=");
                    sb.append(persistentPreferredActivity.countDataSchemes() > 0 ? persistentPreferredActivity.getDataScheme(0) : "<none>");
                    sb.append("\n  component=");
                    sb.append(persistentPreferredActivity.mComponent);
                    android.util.Slog.v("PackageManager", sb.toString());
                    persistentPreferredActivity.dump(new android.util.LogPrinter(2, "PackageManager", 3), "  ");
                }
                android.content.pm.ActivityInfo activityInfo = getActivityInfo(persistentPreferredActivity.mComponent, j | 512, i);
                if (z) {
                    android.util.Slog.v("PackageManager", "Found persistent preferred activity:");
                    if (activityInfo == null) {
                        android.util.Slog.v("PackageManager", "  null");
                    } else {
                        activityInfo.dump(new android.util.LogPrinter(2, "PackageManager", 3), "  ");
                    }
                }
                if (activityInfo != null) {
                    for (int i3 = 0; i3 < size; i3++) {
                        android.content.pm.ResolveInfo resolveInfo = list.get(i3);
                        if (resolveInfo.activityInfo.applicationInfo.packageName.equals(activityInfo.applicationInfo.packageName) && resolveInfo.activityInfo.name.equals(activityInfo.name)) {
                            if (z) {
                                android.util.Slog.v("PackageManager", "Returning persistent preferred activity: " + resolveInfo.activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + resolveInfo.activityInfo.name);
                            }
                            return resolveInfo;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.PreferredIntentResolver getPreferredActivities(int i) {
        return this.mSettings.getPreferredActivities(i);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates() {
        return this.mSettings.getPackages();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> getDisabledSystemPackageStates() {
        return this.mSettings.getDisabledSystemPackages();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public java.lang.String getRenamedPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mSettings.getRenamedPackageLPr(str);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> getSharedLibraries() {
        return this.mSharedLibraries.getAll();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.ArraySet<java.lang.String> getNotifyPackagesForReplacedReceived(@android.annotation.NonNull java.lang.String[] strArr) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        for (java.lang.String str : strArr) {
            if (!shouldFilterApplication(getPackageStateInternal(str), callingUid, userId)) {
                arraySet.add(str);
            }
        }
        return arraySet;
    }

    @Override // com.android.server.pm.Computer
    public int getPackageStartability(boolean z, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        boolean isCeStorageUnlocked = android.os.storage.StorageManager.isCeStorageUnlocked(i2);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, i2) || !packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
            return 1;
        }
        if (z && !packageStateInternal.isSystem()) {
            return 2;
        }
        if (this.mFrozenPackages.containsKey(str)) {
            return 3;
        }
        if (!isCeStorageUnlocked && !com.android.server.pm.parsing.pkg.AndroidPackageUtils.isEncryptionAware(packageStateInternal.getPkg())) {
            return 4;
        }
        return 0;
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageAvailable(java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault;
        if (!this.mUserManager.exists(i)) {
            return false;
        }
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "is package available");
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || shouldFilterApplication(packageStateInternal, callingUid, i) || (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)) == null) {
            return false;
        }
        return com.android.server.pm.pkg.PackageUserStateUtils.isAvailable(userStateOrDefault, 0L);
    }

    @Override // com.android.server.pm.Computer
    public boolean isApexPackage(java.lang.String str) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        return androidPackage != null && androidPackage.isApex();
    }

    @Override // com.android.server.pm.Computer
    public java.lang.String[] currentToCanonicalPackageNames(java.lang.String[] strArr) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return strArr;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length];
        int userId = android.os.UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        for (int length = strArr.length - 1; length >= 0; length--) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(strArr[length]);
            boolean z = false;
            if (packageStateInternal != null && packageStateInternal.getRealName() != null && (!packageStateInternal.getUserStateOrDefault(userId).isInstantApp() || canViewInstantApps || this.mInstantAppRegistry.isInstantAccessGranted(userId, android.os.UserHandle.getAppId(callingUid), packageStateInternal.getAppId()))) {
                z = true;
            }
            strArr2[length] = z ? packageStateInternal.getRealName() : strArr[length];
        }
        return strArr2;
    }

    @Override // com.android.server.pm.Computer
    public java.lang.String[] canonicalToCurrentPackageNames(java.lang.String[] strArr) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return strArr;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length];
        int userId = android.os.UserHandle.getUserId(callingUid);
        boolean canViewInstantApps = canViewInstantApps(callingUid, userId);
        for (int length = strArr.length - 1; length >= 0; length--) {
            java.lang.String renamedPackage = getRenamedPackage(strArr[length]);
            boolean z = false;
            if (renamedPackage != null) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(strArr[length]);
                if (!(packageStateInternal != null && packageStateInternal.getUserStateOrDefault(userId).isInstantApp()) || canViewInstantApps || this.mInstantAppRegistry.isInstantAccessGranted(userId, android.os.UserHandle.getAppId(callingUid), packageStateInternal.getAppId())) {
                    z = true;
                }
            }
            if (!z) {
                renamedPackage = strArr[length];
            }
            strArr2[length] = renamedPackage;
        }
        return strArr2;
    }

    @Override // com.android.server.pm.Computer
    public int[] getPackageGids(@android.annotation.NonNull java.lang.String str, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "getPackageGids");
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            return null;
        }
        if (packageStateInternal.getPkg() != null && com.android.server.pm.parsing.pkg.AndroidPackageUtils.isMatchForSystemOnly(packageStateInternal, updateFlagsForPackage) && packageStateInternal.getUserStateOrDefault(i).isInstalled() && !shouldFilterApplication(packageStateInternal, callingUid, i)) {
            return this.mPermissionManager.getGidsForUid(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
        }
        if ((4299169792L & updateFlagsForPackage) == 0 || !com.android.server.pm.pkg.PackageStateUtils.isMatch(packageStateInternal, updateFlagsForPackage) || shouldFilterApplication(packageStateInternal, callingUid, i)) {
            return null;
        }
        return this.mPermissionManager.getGidsForUid(android.os.UserHandle.getUid(i, packageStateInternal.getAppId()));
    }

    @Override // com.android.server.pm.Computer
    public int getTargetSdkVersion(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, android.os.Binder.getCallingUid(), android.os.UserHandle.getCallingUserId())) {
            return -1;
        }
        return packageStateInternal.getPkg().getTargetSdkVersion();
    }

    @Override // com.android.server.pm.Computer
    public boolean activitySupportsIntentAsUser(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.content.ComponentName componentName2, @android.annotation.NonNull android.content.Intent intent, java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "activitySupportsIntentAsUser");
        if (componentName2.equals(componentName)) {
            return true;
        }
        com.android.internal.pm.pkg.component.ParsedActivity activity = this.mComponentResolver.getActivity(componentName2);
        if (activity == null || (packageStateInternal = getPackageStateInternal(componentName2.getPackageName())) == null || shouldFilterApplication(packageStateInternal, callingUid, componentName2, 1, i, true)) {
            return false;
        }
        for (int i2 = 0; i2 < activity.getIntents().size(); i2++) {
            if (((com.android.internal.pm.pkg.component.ParsedIntentInfo) activity.getIntents().get(i2)).getIntentFilter().match(intent.getAction(), str, intent.getScheme(), intent.getData(), intent.getCategories(), "PackageManager") >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ActivityInfo getReceiverInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get receiver info");
        com.android.internal.pm.pkg.component.ParsedActivity receiver = this.mComponentResolver.getReceiver(componentName);
        if (receiver == null || (packageStateInternal = getPackageStateInternal(receiver.getPackageName())) == null || packageStateInternal.getPkg() == null || !com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, (com.android.internal.pm.pkg.component.ParsedMainComponent) receiver, updateFlagsForComponent, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 2, i)) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateActivityInfo(packageStateInternal.getPkg(), receiver, updateFlagsForComponent, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getSharedLibraries(@android.annotation.NonNull java.lang.String str, long j, int i) {
        int i2;
        int i3;
        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> watchedArrayMap;
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.DELETE_PACKAGES") == 0 || canRequestPackageInstalls(str, callingUid, i, false) || this.mContext.checkCallingOrSelfPermission("android.permission.REQUEST_DELETE_PACKAGES") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES") == 0;
        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> sharedLibraries = getSharedLibraries();
        int size = sharedLibraries.size();
        java.util.ArrayList arrayList3 = null;
        int i4 = 0;
        while (i4 < size) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> valueAt = sharedLibraries.valueAt(i4);
            if (valueAt == null) {
                i2 = i4;
                i3 = size;
                watchedArrayMap = sharedLibraries;
            } else {
                int size2 = valueAt.size();
                java.util.ArrayList arrayList4 = arrayList3;
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        i2 = i4;
                        i3 = size;
                        watchedArrayMap = sharedLibraries;
                        break;
                    }
                    android.content.pm.SharedLibraryInfo valueAt2 = valueAt.valueAt(i5);
                    if (!z) {
                        if (!valueAt2.isStatic()) {
                            if (valueAt2.isSdk()) {
                                i2 = i4;
                                i3 = size;
                                watchedArrayMap = sharedLibraries;
                                break;
                            }
                        } else {
                            i2 = i4;
                            i3 = size;
                            watchedArrayMap = sharedLibraries;
                            break;
                        }
                    }
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    android.content.pm.VersionedPackage declaringPackage = valueAt2.getDeclaringPackage();
                    try {
                        int i6 = size2;
                        int i7 = i5;
                        int i8 = i4;
                        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray = valueAt;
                        int i9 = size;
                        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> watchedArrayMap2 = sharedLibraries;
                        if (getPackageInfoInternal(declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), updateFlagsForPackage | 67108864, android.os.Binder.getCallingUid(), i) != null) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            java.lang.String path = valueAt2.getPath();
                            java.lang.String packageName = valueAt2.getPackageName();
                            java.util.List allCodePaths = valueAt2.getAllCodePaths();
                            java.lang.String name = valueAt2.getName();
                            long longVersion = valueAt2.getLongVersion();
                            int type = valueAt2.getType();
                            if (valueAt2.getDependencies() == null) {
                                arrayList = null;
                            } else {
                                arrayList = new java.util.ArrayList(valueAt2.getDependencies());
                            }
                            android.content.pm.SharedLibraryInfo sharedLibraryInfo = new android.content.pm.SharedLibraryInfo(path, packageName, allCodePaths, name, longVersion, type, declaringPackage, arrayList, valueAt2.isNative(), getPackagesUsingSharedLibrary(valueAt2, updateFlagsForPackage, callingUid, i));
                            if (arrayList4 != null) {
                                arrayList2 = arrayList4;
                            } else {
                                arrayList2 = new java.util.ArrayList();
                            }
                            arrayList2.add(sharedLibraryInfo);
                            arrayList4 = arrayList2;
                        }
                        i5 = i7 + 1;
                        size2 = i6;
                        i4 = i8;
                        valueAt = watchedLongSparseArray;
                        size = i9;
                        sharedLibraries = watchedArrayMap2;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                arrayList3 = arrayList4;
            }
            i4 = i2 + 1;
            size = i3;
            sharedLibraries = watchedArrayMap;
        }
        if (arrayList3 != null) {
            return new android.content.pm.ParceledListSlice<>(arrayList3);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public boolean canRequestPackageInstalls(@android.annotation.NonNull java.lang.String str, int i, int i2, boolean z) {
        com.android.server.pm.pkg.AndroidPackage androidPackage;
        if (i != getPackageUidInternal(str, 0L, i2, i) && !com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(i)) {
            throw new java.lang.SecurityException("Caller uid " + i + " does not own package " + str);
        }
        if (isInstantAppInternal(str, i2, 1000) || (androidPackage = this.mPackages.get(str)) == null || androidPackage.getTargetSdkVersion() < 26) {
            return false;
        }
        if (androidPackage.getRequestedPermissions().contains("android.permission.REQUEST_INSTALL_PACKAGES")) {
            return !isInstallDisabledForPackage(str, r0, i2);
        }
        if (!z) {
            android.util.Slog.e("PackageManager", "Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
            return false;
        }
        throw new java.lang.SecurityException("Need to declare android.permission.REQUEST_INSTALL_PACKAGES to call this api");
    }

    @Override // com.android.server.pm.Computer
    public final boolean isInstallDisabledForPackage(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        if (this.mUserManager.hasUserRestriction("no_install_unknown_sources", i2) || this.mUserManager.hasUserRestriction("no_install_unknown_sources_globally", i2)) {
            return true;
        }
        return (this.mExternalSourcesPolicy == null || this.mExternalSourcesPolicy.getPackageTrustedToInstallApps(str, i) == 0) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public android.util.Pair<java.util.List<android.content.pm.VersionedPackage>, java.util.List<java.lang.Boolean>> getPackagesUsingSharedLibrary(@android.annotation.NonNull android.content.pm.SharedLibraryInfo sharedLibraryInfo, long j, int i, int i2) {
        com.android.server.pm.ComputerEngine computerEngine = this;
        int i3 = i;
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = getPackageStates();
        int size = packageStates.size();
        int i4 = 0;
        java.util.ArrayList arrayList = null;
        java.util.ArrayList arrayList2 = null;
        while (i4 < size) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i4);
            if (valueAt != null && com.android.server.pm.pkg.PackageUserStateUtils.isAvailable(valueAt.getUserStateOrDefault(i2), j)) {
                java.lang.String name = sharedLibraryInfo.getName();
                if (sharedLibraryInfo.isStatic() || sharedLibraryInfo.isSdk()) {
                    java.lang.String[] usesStaticLibraries = sharedLibraryInfo.isStatic() ? valueAt.getUsesStaticLibraries() : valueAt.getUsesSdkLibraries();
                    long[] usesStaticLibrariesVersions = sharedLibraryInfo.isStatic() ? valueAt.getUsesStaticLibrariesVersions() : valueAt.getUsesSdkLibrariesVersionsMajor();
                    boolean[] usesSdkLibrariesOptional = sharedLibraryInfo.isSdk() ? valueAt.getUsesSdkLibrariesOptional() : null;
                    int indexOf = com.android.internal.util.ArrayUtils.indexOf(usesStaticLibraries, name);
                    if (indexOf >= 0 && usesStaticLibrariesVersions[indexOf] == sharedLibraryInfo.getLongVersion() && !computerEngine.shouldFilterApplication(valueAt, i3, i2)) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        if (arrayList2 == null) {
                            arrayList2 = new java.util.ArrayList();
                        }
                        java.lang.String packageName = valueAt.getPackageName();
                        if (valueAt.getPkg() != null && valueAt.getPkg().isStaticSharedLibrary()) {
                            packageName = valueAt.getPkg().getManifestPackageName();
                        }
                        arrayList.add(new android.content.pm.VersionedPackage(packageName, valueAt.getVersionCode()));
                        arrayList2.add(java.lang.Boolean.valueOf(usesSdkLibrariesOptional != null && usesSdkLibrariesOptional[indexOf]));
                    }
                } else if (valueAt.getPkg() != null && ((com.android.internal.util.ArrayUtils.contains(valueAt.getPkg().getUsesLibraries(), name) || com.android.internal.util.ArrayUtils.contains(valueAt.getPkg().getUsesOptionalLibraries(), name)) && !computerEngine.shouldFilterApplication(valueAt, i3, i2))) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(new android.content.pm.VersionedPackage(valueAt.getPackageName(), valueAt.getVersionCode()));
                }
            }
            i4++;
            computerEngine = this;
            i3 = i;
        }
        return new android.util.Pair<>(arrayList, arrayList2);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ParceledListSlice<android.content.pm.SharedLibraryInfo> getDeclaredSharedLibraries(@android.annotation.NonNull java.lang.String str, long j, int i) {
        int i2;
        int i3;
        int i4;
        com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> watchedLongSparseArray;
        int i5;
        java.util.ArrayList arrayList;
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_SHARED_LIBRARIES", "getDeclaredSharedLibraries");
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "getDeclaredSharedLibraries");
        com.android.internal.util.Preconditions.checkNotNull(str, "packageName cannot be null");
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "userId must be >= 0");
        if (!this.mUserManager.exists(i) || getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> sharedLibraries = getSharedLibraries();
        int size = sharedLibraries.size();
        java.util.ArrayList arrayList2 = null;
        int i6 = 0;
        while (i6 < size) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> valueAt = sharedLibraries.valueAt(i6);
            if (valueAt == null) {
                i2 = i6;
            } else {
                int size2 = valueAt.size();
                java.util.ArrayList arrayList3 = arrayList2;
                int i7 = 0;
                while (i7 < size2) {
                    android.content.pm.SharedLibraryInfo valueAt2 = valueAt.valueAt(i7);
                    android.content.pm.VersionedPackage declaringPackage = valueAt2.getDeclaringPackage();
                    if (!java.util.Objects.equals(declaringPackage.getPackageName(), str)) {
                        i3 = size2;
                        i4 = i7;
                        watchedLongSparseArray = valueAt;
                        i5 = i6;
                    } else {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            i3 = size2;
                            i4 = i7;
                            watchedLongSparseArray = valueAt;
                            i5 = i6;
                            if (getPackageInfoInternal(declaringPackage.getPackageName(), declaringPackage.getLongVersionCode(), j | 67108864, android.os.Binder.getCallingUid(), i) != null) {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                android.content.pm.SharedLibraryInfo sharedLibraryInfo = new android.content.pm.SharedLibraryInfo(valueAt2.getPath(), valueAt2.getPackageName(), valueAt2.getAllCodePaths(), valueAt2.getName(), valueAt2.getLongVersion(), valueAt2.getType(), valueAt2.getDeclaringPackage(), (java.util.List) getPackagesUsingSharedLibrary(valueAt2, j, callingUid, i).first, valueAt2.getDependencies() == null ? null : new java.util.ArrayList(valueAt2.getDependencies()), valueAt2.isNative());
                                if (arrayList3 != null) {
                                    arrayList = arrayList3;
                                } else {
                                    arrayList = new java.util.ArrayList();
                                }
                                arrayList.add(sharedLibraryInfo);
                                arrayList3 = arrayList;
                            }
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    i7 = i4 + 1;
                    valueAt = watchedLongSparseArray;
                    i6 = i5;
                    size2 = i3;
                }
                i2 = i6;
                arrayList2 = arrayList3;
            }
            i6 = i2 + 1;
        }
        if (arrayList2 != null) {
            return new android.content.pm.ParceledListSlice<>(arrayList2);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ProviderInfo getProviderInfo(@android.annotation.NonNull android.content.ComponentName componentName, long j, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault;
        android.content.pm.ApplicationInfo generateApplicationInfo;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "get provider info");
        com.android.internal.pm.pkg.component.ParsedProvider provider = this.mComponentResolver.getProvider(componentName);
        if (provider == null || (packageStateInternal = getPackageStateInternal(provider.getPackageName())) == null || packageStateInternal.getPkg() == null || !com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, (com.android.internal.pm.pkg.component.ParsedMainComponent) provider, updateFlagsForComponent, i) || shouldFilterApplication(packageStateInternal, callingUid, componentName, 4, i) || (generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), updateFlagsForComponent, (userStateOrDefault = packageStateInternal.getUserStateOrDefault(i)), i, packageStateInternal)) == null) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateProviderInfo(packageStateInternal.getPkg(), provider, updateFlagsForComponent, userStateOrDefault, generateApplicationInfo, i, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    public android.util.ArrayMap<java.lang.String, java.lang.String> getSystemSharedLibraryNamesAndPaths() {
        com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo>> sharedLibraries = getSharedLibraries();
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>();
        int size = sharedLibraries.size();
        for (int i = 0; i < size; i++) {
            com.android.server.utils.WatchedLongSparseArray<android.content.pm.SharedLibraryInfo> valueAt = sharedLibraries.valueAt(i);
            if (valueAt != null) {
                int size2 = valueAt.size();
                int i2 = 0;
                while (true) {
                    if (i2 < size2) {
                        android.content.pm.SharedLibraryInfo valueAt2 = valueAt.valueAt(i2);
                        if (!valueAt2.isStatic()) {
                            arrayMap.put(valueAt2.getName(), valueAt2.getPath());
                            break;
                        }
                        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(valueAt2.getPackageName());
                        if (packageStateInternal == null || filterSharedLibPackage(packageStateInternal, android.os.Binder.getCallingUid(), android.os.UserHandle.getUserId(android.os.Binder.getCallingUid()), 67108864L)) {
                            i2++;
                        } else {
                            arrayMap.put(valueAt2.getName(), valueAt2.getPath());
                            break;
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    @Override // com.android.server.pm.Computer
    public com.android.server.pm.pkg.PackageStateInternal getPackageStateForInstalledAndFiltered(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal;
    }

    @Override // com.android.server.pm.Computer
    public int checkSignatures(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "checkSignatures");
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage2 = this.mPackages.get(str2);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = androidPackage == null ? null : getPackageStateInternal(androidPackage.getPackageName());
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = androidPackage2 != null ? getPackageStateInternal(androidPackage2.getPackageName()) : null;
        if (androidPackage == null || packageStateInternal == null || androidPackage2 == null || packageStateInternal2 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i) || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i)) {
            return -4;
        }
        return checkSignaturesInternal(androidPackage.getSigningDetails(), androidPackage2.getSigningDetails());
    }

    @Override // com.android.server.pm.Computer
    public int checkUidSignatures(int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        android.content.pm.SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, userId);
        android.content.pm.SigningDetails signingDetailsAndFilterAccess2 = getSigningDetailsAndFilterAccess(i2, callingUid, userId);
        if (signingDetailsAndFilterAccess == null || signingDetailsAndFilterAccess2 == null) {
            return -4;
        }
        return checkSignaturesInternal(signingDetailsAndFilterAccess, signingDetailsAndFilterAccess2);
    }

    @Override // com.android.server.pm.Computer
    public int checkUidSignaturesForAllUsers(int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(i);
        int userId2 = android.os.UserHandle.getUserId(i2);
        enforceCrossUserPermission(callingUid, userId, false, false, "checkUidSignaturesForAllUsers");
        enforceCrossUserPermission(callingUid, userId2, false, false, "checkUidSignaturesForAllUsers");
        android.content.pm.SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, userId);
        android.content.pm.SigningDetails signingDetailsAndFilterAccess2 = getSigningDetailsAndFilterAccess(i2, callingUid, userId2);
        if (signingDetailsAndFilterAccess == null || signingDetailsAndFilterAccess2 == null) {
            return -4;
        }
        return checkSignaturesInternal(signingDetailsAndFilterAccess, signingDetailsAndFilterAccess2);
    }

    private android.content.pm.SigningDetails getSigningDetailsAndFilterAccess(int i, int i2, int i3) {
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase == null) {
            return null;
        }
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, i2, i3)) {
                return null;
            }
            return sharedUserSetting.signatures.mSigningDetails;
        }
        if (!(settingBase instanceof com.android.server.pm.PackageSetting)) {
            return null;
        }
        com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, i2, i3)) {
            return null;
        }
        return packageSetting.getSigningDetails();
    }

    private int checkSignaturesInternal(android.content.pm.SigningDetails signingDetails, android.content.pm.SigningDetails signingDetails2) {
        android.content.pm.Signature[] signatures;
        android.content.pm.Signature[] signatures2;
        if (signingDetails == null) {
            if (signingDetails2 == null) {
                return 1;
            }
            return -1;
        }
        if (signingDetails2 == null) {
            return -2;
        }
        int compareSignatures = com.android.server.pm.PackageManagerServiceUtils.compareSignatures(signingDetails, signingDetails2);
        if (compareSignatures == 0) {
            return compareSignatures;
        }
        if (signingDetails.hasPastSigningCertificates() || signingDetails2.hasPastSigningCertificates()) {
            if (signingDetails.hasPastSigningCertificates()) {
                signatures = new android.content.pm.Signature[]{signingDetails.getPastSigningCertificates()[0]};
            } else {
                signatures = signingDetails.getSignatures();
            }
            if (signingDetails2.hasPastSigningCertificates()) {
                signatures2 = new android.content.pm.Signature[]{signingDetails2.getPastSigningCertificates()[0]};
            } else {
                signatures2 = signingDetails2.getSignatures();
            }
            return com.android.server.pm.PackageManagerServiceUtils.compareSignatureArrays(signatures, signatures2);
        }
        return compareSignatures;
    }

    @Override // com.android.server.pm.Computer
    public boolean hasSigningCertificate(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr, int i) {
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (androidPackage == null) {
            return false;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(androidPackage.getPackageName());
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, userId)) {
            return false;
        }
        switch (i) {
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public boolean hasUidSigningCertificate(int i, @android.annotation.NonNull byte[] bArr, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        android.content.pm.SigningDetails signingDetailsAndFilterAccess = getSigningDetailsAndFilterAccess(i, callingUid, android.os.UserHandle.getUserId(callingUid));
        if (signingDetailsAndFilterAccess == null) {
            return false;
        }
        switch (i2) {
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public java.util.List<java.lang.String> getAllPackages() {
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRootOrShell("getAllPackages is limited to privileged callers");
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (canViewInstantApps(callingUid, userId)) {
            return new java.util.ArrayList(this.mPackages.keySet());
        }
        java.lang.String instantAppPackageName = getInstantAppPackageName(callingUid);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (instantAppPackageName != null) {
            for (com.android.server.pm.pkg.AndroidPackage androidPackage : this.mPackages.values()) {
                if (androidPackage.isVisibleToInstantApps()) {
                    arrayList.add(androidPackage.getPackageName());
                }
            }
        } else {
            for (com.android.server.pm.pkg.AndroidPackage androidPackage2 : this.mPackages.values()) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(androidPackage2.getPackageName());
                if (packageStateInternal == null || !packageStateInternal.getUserStateOrDefault(userId).isInstantApp() || this.mInstantAppRegistry.isInstantAccessGranted(userId, android.os.UserHandle.getAppId(callingUid), packageStateInternal.getAppId())) {
                    arrayList.add(androidPackage2.getPackageName());
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public java.lang.String getNameForUid(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        if (android.os.Process.isIsolatedUid(i) && this.mPermissionManager.getHotwordDetectionServiceProvider() != null && i == this.mPermissionManager.getHotwordDetectionServiceProvider().getUid()) {
            try {
                i = getIsolatedOwner(i);
            } catch (java.lang.IllegalStateException e) {
                android.util.Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
            }
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return null;
            }
            return sharedUserSetting.name + ":" + sharedUserSetting.mAppId;
        }
        if (!(settingBase instanceof com.android.server.pm.PackageSetting)) {
            return null;
        }
        com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return null;
        }
        return packageSetting.getPackageName();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public java.lang.String[] getNamesForUids(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return null;
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        java.lang.String[] strArr = new java.lang.String[iArr.length];
        for (int length = iArr.length - 1; length >= 0; length--) {
            int i = iArr[length];
            if (android.os.Process.isSdkSandboxUid(i)) {
                i = getBaseSdkSandboxUid();
            }
            if (android.os.Process.isIsolatedUid(i) && this.mPermissionManager.getHotwordDetectionServiceProvider() != null && i == this.mPermissionManager.getHotwordDetectionServiceProvider().getUid()) {
                try {
                    i = getIsolatedOwner(i);
                } catch (java.lang.IllegalStateException e) {
                    android.util.Slog.wtf("PackageManager", "Expected isolated uid " + i + " to have an owner", e);
                }
            }
            com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
            if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
                com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingBase;
                if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = "shared:" + sharedUserSetting.name;
                }
            } else if (settingBase instanceof com.android.server.pm.PackageSetting) {
                com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
                if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
                    strArr[length] = null;
                } else {
                    strArr[length] = packageSetting.getPackageName();
                }
            } else {
                strArr[length] = null;
            }
        }
        return strArr;
    }

    @Override // com.android.server.pm.Computer
    public int getUidForSharedUser(@android.annotation.NonNull java.lang.String str) {
        com.android.server.pm.SharedUserSetting sharedUserFromId;
        if (str == null) {
            return -1;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || (sharedUserFromId = this.mSettings.getSharedUserFromId(str)) == null || shouldFilterApplicationIncludingUninstalled(sharedUserFromId, callingUid, android.os.UserHandle.getUserId(callingUid))) {
            return -1;
        }
        return sharedUserFromId.mAppId;
    }

    @Override // com.android.server.pm.Computer
    public int getFlagsForUid(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.getFlags();
        }
        if (!(settingBase instanceof com.android.server.pm.PackageSetting)) {
            return 0;
        }
        com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.getFlags();
    }

    @Override // com.android.server.pm.Computer
    public int getPrivateFlagsForUid(int i) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null) {
            return 0;
        }
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            com.android.server.pm.SharedUserSetting sharedUserSetting = (com.android.server.pm.SharedUserSetting) settingBase;
            if (shouldFilterApplicationIncludingUninstalled(sharedUserSetting, callingUid, userId)) {
                return 0;
            }
            return sharedUserSetting.getPrivateFlags();
        }
        if (!(settingBase instanceof com.android.server.pm.PackageSetting)) {
            return 0;
        }
        com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
        if (shouldFilterApplicationIncludingUninstalled(packageSetting, callingUid, userId)) {
            return 0;
        }
        return packageSetting.getPrivateFlags();
    }

    @Override // com.android.server.pm.Computer
    public boolean isUidPrivileged(int i) {
        if (getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return false;
        }
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = ((com.android.server.pm.SharedUserSetting) settingBase).getPackageStates();
            int size = packageStates.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (packageStates.valueAt(i2).isPrivileged()) {
                    return true;
                }
            }
        } else if (settingBase instanceof com.android.server.pm.PackageSetting) {
            return ((com.android.server.pm.PackageSetting) settingBase).isPrivileged();
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.lang.String[] getAppOpPermissionPackages(@android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "getAppOpPermissionPackages");
        if (str == null || getInstantAppPackageName(callingUid) != null || !this.mUserManager.exists(i)) {
            return libcore.util.EmptyArray.STRING;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mPermissionManager.getAppOpPermissionPackages(str));
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            if (shouldFilterApplicationIncludingUninstalled(this.mSettings.getPackage((java.lang.String) arraySet.valueAt(size)), callingUid, i)) {
                arraySet.removeAt(size);
            }
        }
        return (java.lang.String[]) arraySet.toArray(new java.lang.String[arraySet.size()]);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.content.pm.PackageInfo> getPackagesHoldingPermissions(@android.annotation.NonNull java.lang.String[] strArr, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(android.os.Binder.getCallingUid(), i, true, false, "get packages holding permissions");
        boolean z = (4299169792L & updateFlagsForPackage) != 0;
        java.util.ArrayList<android.content.pm.PackageInfo> arrayList = new java.util.ArrayList<>();
        boolean[] zArr = new boolean[strArr.length];
        for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : getPackageStates().values()) {
            if (packageStateInternal.getPkg() != null || z) {
                addPackageHoldingPermissions(arrayList, packageStateInternal, strArr, zArr, updateFlagsForPackage, i);
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    private void addPackageHoldingPermissions(java.util.ArrayList<android.content.pm.PackageInfo> arrayList, com.android.server.pm.pkg.PackageStateInternal packageStateInternal, java.lang.String[] strArr, boolean[] zArr, long j, int i) {
        android.content.pm.PackageInfo generatePackageInfo;
        int i2 = 0;
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (this.mPermissionManager.checkPermission(packageStateInternal.getPackageName(), strArr[i3], "default:0", i) == 0) {
                zArr[i3] = true;
                i2++;
            } else {
                zArr[i3] = false;
            }
        }
        if (i2 != 0 && (generatePackageInfo = generatePackageInfo(packageStateInternal, j, i)) != null) {
            if ((j & 4096) == 0) {
                if (i2 == strArr.length) {
                    generatePackageInfo.requestedPermissions = strArr;
                } else {
                    generatePackageInfo.requestedPermissions = new java.lang.String[i2];
                    int i4 = 0;
                    for (int i5 = 0; i5 < strArr.length; i5++) {
                        if (zArr[i5]) {
                            generatePackageInfo.requestedPermissions[i4] = strArr[i5];
                            i4++;
                        }
                    }
                }
            }
            arrayList.add(generatePackageInfo);
        }
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.List<android.content.pm.ApplicationInfo> getInstalledApplications(long j, int i, int i2, boolean z) {
        java.util.ArrayList arrayList;
        long j2;
        android.content.pm.ApplicationInfo generateApplicationInfoFromSettings;
        android.content.pm.ApplicationInfo generateApplicationInfo;
        if (getInstantAppPackageName(i2) != null) {
            return java.util.Collections.emptyList();
        }
        if (!this.mUserManager.exists(i)) {
            return java.util.Collections.emptyList();
        }
        long updateFlagsForApplication = updateFlagsForApplication(j, i);
        boolean z2 = (4202496 & updateFlagsForApplication) != 0;
        boolean z3 = (1073741824 & updateFlagsForApplication) != 0;
        boolean z4 = (z2 || (4294967296L & updateFlagsForApplication) == 0) ? false : true;
        if (!z) {
            enforceCrossUserPermission(i2, i, false, false, "get installed application info");
        }
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = getPackageStates();
        if (z2 || z4) {
            arrayList = new java.util.ArrayList(packageStates.size());
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal : packageStates.values()) {
                if (!packageStateInternal.isSystem()) {
                    j2 = updateFlagsForApplication;
                } else {
                    j2 = 4194304 | updateFlagsForApplication;
                }
                if (packageStateInternal.getPkg() != null) {
                    if (z3 || !packageStateInternal.getPkg().isApex()) {
                        com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
                        if (!z4 || userStateOrDefault.isInstalled() || userStateOrDefault.getArchiveState() != null) {
                            if (!filterSharedLibPackage(packageStateInternal, i2, i, updateFlagsForApplication) && !shouldFilterApplication(packageStateInternal, i2, i)) {
                                generateApplicationInfoFromSettings = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(packageStateInternal.getPkg(), j2, packageStateInternal.getUserStateOrDefault(i), i, packageStateInternal);
                                if (generateApplicationInfoFromSettings != null) {
                                    generateApplicationInfoFromSettings.packageName = resolveExternalPackageName(packageStateInternal.getPkg());
                                }
                            }
                        }
                    }
                } else {
                    generateApplicationInfoFromSettings = generateApplicationInfoFromSettings(packageStateInternal.getPackageName(), j2, i2, i);
                }
                if (generateApplicationInfoFromSettings != null) {
                    arrayList.add(generateApplicationInfoFromSettings);
                }
            }
        } else {
            arrayList = new java.util.ArrayList(this.mPackages.size());
            for (com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 : packageStates.values()) {
                com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal2.getPkg();
                if (pkg != null && (z3 || !pkg.isApex())) {
                    if (!filterSharedLibPackage(packageStateInternal2, android.os.Binder.getCallingUid(), i, updateFlagsForApplication) && !shouldFilterApplication(packageStateInternal2, i2, i) && (generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(pkg, updateFlagsForApplication, packageStateInternal2.getUserStateOrDefault(i), i, packageStateInternal2)) != null) {
                        generateApplicationInfo.packageName = resolveExternalPackageName(pkg);
                        arrayList.add(generateApplicationInfo);
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ProviderInfo resolveContentProvider(@android.annotation.NonNull java.lang.String str, long j, int i, int i2) {
        boolean z;
        android.content.pm.UserInfo userInfo;
        if (!this.mUserManager.exists(i)) {
            return null;
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, i);
        android.content.pm.ProviderInfo queryProvider = this.mComponentResolver.queryProvider(this, str, updateFlagsForComponent, i);
        boolean z2 = true;
        if (queryProvider != null && i != android.os.UserHandle.getUserId(i2)) {
            z = ((com.android.server.uri.UriGrantsManagerInternal) this.mInjector.getLocalService(com.android.server.uri.UriGrantsManagerInternal.class)).checkAuthorityGrants(i2, queryProvider, i, true);
        } else {
            z = false;
        }
        if (!z) {
            if (android.content.ContentProvider.isAuthorityRedirectedForCloneProfile(str) && (userInfo = this.mInjector.getUserManagerInternal().getUserInfo(android.os.UserHandle.getUserId(i2))) != null && userInfo.isCloneProfile() && userInfo.profileGroupId == i) {
                z2 = false;
            }
            if (z2) {
                enforceCrossUserPermission(i2, i, false, false, "resolveContentProvider");
            }
        }
        if (queryProvider == null) {
            return null;
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(queryProvider.packageName);
        if (com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(packageStateInternal, queryProvider, updateFlagsForComponent, i) && !shouldFilterApplication(packageStateInternal, i2, new android.content.ComponentName(queryProvider.packageName, queryProvider.name), 4, i)) {
            return queryProvider;
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ProviderInfo getGrantImplicitAccessProviderInfo(int i, @android.annotation.NonNull java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(i);
        android.content.pm.ProviderInfo resolveContentProvider = resolveContentProvider("com.android.contacts", 0L, android.os.UserHandle.getUserId(callingUid), callingUid);
        if (resolveContentProvider == null || resolveContentProvider.applicationInfo == null || !android.os.UserHandle.isSameApp(resolveContentProvider.applicationInfo.uid, callingUid)) {
            throw new java.lang.SecurityException(callingUid + " is not allow to call grantImplicitAccess");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return resolveContentProvider(str, 0L, userId, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.pm.Computer
    @java.lang.Deprecated
    public void querySyncProviders(boolean z, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<android.content.pm.ProviderInfo> list2) {
        if (getInstantAppPackageName(android.os.Binder.getCallingUid()) != null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mComponentResolver.querySyncProviders(this, arrayList, arrayList2, z, callingUserId);
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            android.content.pm.ProviderInfo providerInfo = (android.content.pm.ProviderInfo) arrayList2.get(size);
            if (shouldFilterApplication(this.mSettings.getPackage(providerInfo.packageName), android.os.Binder.getCallingUid(), new android.content.ComponentName(providerInfo.packageName, providerInfo.name), 4, callingUserId)) {
                arrayList2.remove(size);
                arrayList.remove(size);
            }
        }
        if (!arrayList.isEmpty()) {
            list.addAll(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            list2.addAll(arrayList2);
        }
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.content.pm.ProviderInfo> queryContentProviders(@android.annotation.Nullable java.lang.String str, int i, long j, @android.annotation.Nullable java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        int userId = str != null ? android.os.UserHandle.getUserId(i) : android.os.UserHandle.getCallingUserId();
        enforceCrossUserPermission(callingUid, userId, false, false, "queryContentProviders");
        if (!this.mUserManager.exists(userId)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        long updateFlagsForComponent = updateFlagsForComponent(j, userId);
        java.util.List<android.content.pm.ProviderInfo> queryProviders = this.mComponentResolver.queryProviders(this, str, str2, i, updateFlagsForComponent, userId);
        int size = queryProviders == null ? 0 : queryProviders.size();
        java.util.ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.ProviderInfo providerInfo = queryProviders.get(i2);
            if (com.android.server.pm.pkg.PackageStateUtils.isEnabledAndMatches(this.mSettings.getPackage(providerInfo.packageName), providerInfo, updateFlagsForComponent, userId) && !shouldFilterApplication(this.mSettings.getPackage(providerInfo.packageName), callingUid, new android.content.ComponentName(providerInfo.packageName, providerInfo.name), 4, userId)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList(size - i2);
                }
                arrayList.add(providerInfo);
            }
        }
        if (arrayList != null) {
            arrayList.sort(sProviderInitOrderSorter);
            return new android.content.pm.ParceledListSlice<>(arrayList);
        }
        return android.content.pm.ParceledListSlice.emptyList();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.InstrumentationInfo getInstrumentationInfoAsUser(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i2, false, false, "getInstrumentationInfoAsUser");
        if (!this.mUserManager.exists(i2)) {
            return null;
        }
        java.lang.String packageName = componentName.getPackageName();
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(packageName);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(packageName);
        if (packageStateInternal == null || androidPackage == null || shouldFilterApplication(packageStateInternal, callingUid, componentName, 0, i2)) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateInstrumentationInfo(this.mInstrumentation.get(componentName), androidPackage, i, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.content.pm.InstrumentationInfo> queryInstrumentationAsUser(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        int i3;
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i2, false, false, "queryInstrumentationAsUser");
        if (!this.mUserManager.exists(i2)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mInstrumentation.size();
        int i4 = 0;
        while (i4 < size) {
            com.android.internal.pm.pkg.component.ParsedInstrumentation valueAt = this.mInstrumentation.valueAt(i4);
            if (str == null || str.equals(valueAt.getTargetPackage())) {
                java.lang.String packageName = valueAt.getPackageName();
                com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(packageName);
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(packageName);
                if (androidPackage == null || packageStateInternal == null) {
                    i3 = callingUid;
                } else if (shouldFilterApplication(packageStateInternal, callingUid, i2)) {
                    i3 = callingUid;
                } else {
                    i3 = callingUid;
                    android.content.pm.InstrumentationInfo generateInstrumentationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateInstrumentationInfo(valueAt, androidPackage, i, packageStateInternal.getUserStateOrDefault(i2), i2, packageStateInternal);
                    if (generateInstrumentationInfo != null) {
                        arrayList.add(generateInstrumentationInfo);
                    }
                }
            } else {
                i3 = callingUid;
            }
            i4++;
            callingUid = i3;
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.PackageStateInternal> findSharedNonSystemLibraries(@android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        java.util.List<android.content.pm.SharedLibraryInfo> findSharedLibraries = com.android.server.pm.SharedLibraryUtils.findSharedLibraries(packageStateInternal);
        if (!findSharedLibraries.isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.content.pm.SharedLibraryInfo> it = findSharedLibraries.iterator();
            while (it.hasNext()) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = getPackageStateInternal(it.next().getPackageName());
                if (packageStateInternal2 != null && packageStateInternal2.getPkg() != null) {
                    arrayList.add(packageStateInternal2);
                }
            }
            return arrayList;
        }
        return java.util.Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public boolean getApplicationHiddenSettingAsUser(@android.annotation.NonNull java.lang.String str, int i) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_USERS", null);
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "getApplicationHidden for user " + i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
            if (packageStateInternal == null) {
                return true;
            }
            if (shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
                return true;
            }
            return packageStateInternal.getUserStateOrDefault(i).isHidden();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private com.android.server.pm.pkg.PackageUserStateInternal getUserStateOrDefaultForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "when asking about packages for user " + i);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
            throw new android.content.pm.PackageManager.NameNotFoundException(str);
        }
        return packageStateInternal.getUserStateOrDefault(i);
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSuspendedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getUserStateOrDefaultForUser(str, i).isSuspended();
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageQuarantinedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getUserStateOrDefaultForUser(str, i).isQuarantined();
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageStoppedForUser(@android.annotation.NonNull java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return getUserStateOrDefaultForUser(str, i).isStopped();
    }

    @Override // com.android.server.pm.Computer
    public boolean isSuspendingAnyPackages(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        android.content.pm.UserPackage of = android.content.pm.UserPackage.of(i, str);
        java.util.Iterator<? extends com.android.server.pm.pkg.PackageStateInternal> it = getPackageStates().values().iterator();
        while (it.hasNext()) {
            com.android.server.pm.pkg.PackageUserStateInternal userStateOrDefault = it.next().getUserStateOrDefault(i2);
            if (userStateOrDefault.getSuspendParams() != null && userStateOrDefault.getSuspendParams().containsKey(of)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.content.IntentFilter> getAllIntentFilters(@android.annotation.NonNull java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        com.android.server.pm.pkg.AndroidPackage pkg = packageStateInternal == null ? null : packageStateInternal.getPkg();
        if (pkg == null || com.android.internal.util.ArrayUtils.isEmpty(pkg.getActivities())) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        if (shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, userId)) {
            return android.content.pm.ParceledListSlice.emptyList();
        }
        int size = com.android.internal.util.ArrayUtils.size(pkg.getActivities());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < size; i++) {
            java.util.List intents = ((com.android.internal.pm.pkg.component.ParsedActivity) pkg.getActivities().get(i)).getIntents();
            for (int i2 = 0; i2 < intents.size(); i2++) {
                arrayList.add(new android.content.IntentFilter(((com.android.internal.pm.pkg.component.ParsedIntentInfo) intents.get(i2)).getIntentFilter()));
            }
        }
        return new android.content.pm.ParceledListSlice<>(arrayList);
    }

    @Override // com.android.server.pm.Computer
    public boolean getBlockUninstallForUser(@android.annotation.NonNull java.lang.String str, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        int callingUid = android.os.Binder.getCallingUid();
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
            return false;
        }
        return this.mSettings.getBlockUninstall(i, str);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public java.lang.String getInstallerPackageName(@android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        com.android.server.pm.InstallSource installSource = getInstallSource(str, callingUid, i);
        if (installSource == null) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        java.lang.String str2 = installSource.mInstallerPackageName;
        if (str2 != null) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str2);
            if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalledNotArchived(packageStateInternal, callingUid, android.os.UserHandle.getUserId(callingUid))) {
                return null;
            }
            return str2;
        }
        return str2;
    }

    @android.annotation.Nullable
    private com.android.server.pm.InstallSource getInstallSource(@android.annotation.NonNull java.lang.String str, int i, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (isApexPackage(str)) {
            return com.android.server.pm.InstallSource.EMPTY;
        }
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalledNotArchived(packageStateInternal, i, i2)) {
            return null;
        }
        return packageStateInternal.getInstallSource();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x007b  */
    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.content.pm.InstallSourceInfo getInstallSourceInfo(@android.annotation.NonNull java.lang.String str, int i) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.String str6;
        android.content.pm.SigningInfo signingInfo;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        java.lang.String str7;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "getInstallSourceInfo");
        com.android.server.pm.InstallSource installSource = getInstallSource(str, callingUid, i);
        if (installSource == null) {
            return null;
        }
        java.lang.String str8 = installSource.mInstallerPackageName;
        if (str8 != null && ((packageStateInternal2 = this.mSettings.getPackage(str8)) == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal2, callingUid, i))) {
            str2 = null;
        } else {
            str2 = str8;
        }
        java.lang.String str9 = installSource.mUpdateOwnerPackageName;
        if (str9 != null) {
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal3 = this.mSettings.getPackage(str9);
            boolean z = callingUid == 1000 || isCallerSameApp(str9, callingUid);
            if (packageStateInternal3 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal3, callingUid, i) || (!z && isCallerFromManagedUserOrProfile(i))) {
                str3 = null;
                if (!installSource.mIsInitiatingPackageUninstalled) {
                    if (!(getInstantAppPackageName(callingUid) != null) && isCallerSameApp(str, callingUid)) {
                        str7 = installSource.mInitiatingPackageName;
                    } else {
                        str7 = null;
                    }
                    str4 = str7;
                } else if (java.util.Objects.equals(installSource.mInitiatingPackageName, installSource.mInstallerPackageName)) {
                    str4 = str2;
                } else {
                    java.lang.String str10 = installSource.mInitiatingPackageName;
                    com.android.server.pm.pkg.PackageStateInternal packageStateInternal4 = this.mSettings.getPackage(str10);
                    str4 = (packageStateInternal4 == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal4, callingUid, i)) ? null : str10;
                }
                str5 = installSource.mOriginatingPackageName;
                if (str5 != null && ((packageStateInternal = this.mSettings.getPackage(str5)) == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i))) {
                    str5 = null;
                }
                if (str5 == null && this.mContext.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") != 0) {
                    str6 = null;
                } else {
                    str6 = str5;
                }
                com.android.server.pm.PackageSignatures packageSignatures = installSource.mInitiatingPackageSignatures;
                if (str4 == null && packageSignatures != null && packageSignatures.mSigningDetails != android.content.pm.SigningDetails.UNKNOWN) {
                    signingInfo = new android.content.pm.SigningInfo(packageSignatures.mSigningDetails);
                } else {
                    signingInfo = null;
                }
                return new android.content.pm.InstallSourceInfo(str4, signingInfo, str6, str2, str3, installSource.mPackageSource);
            }
        }
        str3 = str9;
        if (!installSource.mIsInitiatingPackageUninstalled) {
        }
        str5 = installSource.mOriginatingPackageName;
        if (str5 != null) {
            str5 = null;
        }
        if (str5 == null) {
        }
        str6 = str5;
        com.android.server.pm.PackageSignatures packageSignatures2 = installSource.mInitiatingPackageSignatures;
        if (str4 == null) {
        }
        signingInfo = null;
        return new android.content.pm.InstallSourceInfo(str4, signingInfo, str6, str2, str3, installSource.mPackageSource);
    }

    @Override // com.android.server.pm.Computer
    public int getApplicationEnabledSetting(@android.annotation.NonNull java.lang.String str, int i) {
        if (!this.mUserManager.exists(i)) {
            return 2;
        }
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "get enabled");
        try {
            if (shouldFilterApplicationIncludingUninstalled(this.mSettings.getPackage(str), callingUid, i)) {
                throw new android.content.pm.PackageManager.NameNotFoundException(str);
            }
            return this.mSettings.getApplicationEnabledSetting(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
    }

    @Override // com.android.server.pm.Computer
    public int getComponentEnabledSetting(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        enforceCrossUserPermission(i, i2, false, false, "getComponentEnabled");
        return getComponentEnabledSettingInternal(componentName, i, i2);
    }

    @Override // com.android.server.pm.Computer
    public int getComponentEnabledSettingInternal(@android.annotation.NonNull android.content.ComponentName componentName, int i, int i2) {
        if (componentName == null) {
            return 0;
        }
        if (!this.mUserManager.exists(i2)) {
            return 2;
        }
        try {
            if (shouldFilterApplication(this.mSettings.getPackage(componentName.getPackageName()), i, componentName, 0, i2, true)) {
                throw new android.content.pm.PackageManager.NameNotFoundException(componentName.getPackageName());
            }
            return this.mSettings.getComponentEnabledSetting(componentName, i2);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("Unknown component: " + componentName);
        }
    }

    @Override // com.android.server.pm.Computer
    public boolean isComponentEffectivelyEnabled(@android.annotation.NonNull android.content.pm.ComponentInfo componentInfo, @android.annotation.NonNull android.os.UserHandle userHandle) {
        try {
            java.lang.String str = componentInfo.packageName;
            int identifier = userHandle.getIdentifier();
            int applicationEnabledSetting = this.mSettings.getApplicationEnabledSetting(str, identifier);
            if (applicationEnabledSetting == 0) {
                if (!componentInfo.applicationInfo.enabled) {
                    return false;
                }
            } else if (applicationEnabledSetting != 1) {
                return false;
            }
            int componentEnabledSetting = this.mSettings.getComponentEnabledSetting(componentInfo.getComponentName(), identifier);
            if (componentEnabledSetting == 0) {
                return componentInfo.isEnabled();
            }
            if (componentEnabledSetting != 1) {
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override // com.android.server.pm.Computer
    public boolean isApplicationEffectivelyEnabled(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
        try {
            int applicationEnabledSetting = this.mSettings.getApplicationEnabledSetting(str, userHandle.getIdentifier());
            if (applicationEnabledSetting == 0) {
                com.android.server.pm.pkg.AndroidPackage androidPackage = getPackage(str);
                if (androidPackage == null) {
                    return false;
                }
                return androidPackage.isEnabled();
            }
            if (applicationEnabledSetting != 1) {
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.KeySet getKeySetByAlias(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            android.util.Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        return new android.content.pm.KeySet(this.mSettings.getKeySetManagerService().getKeySetByAliasAndPackageNameLPr(str, str2));
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.KeySet getSigningKeySet(@android.annotation.NonNull java.lang.String str) {
        if (str == null) {
            return null;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            android.util.Slog.w("PackageManager", "KeySet requested for unknown package: " + str + ", uid:" + callingUid);
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        if (androidPackage.getUid() != callingUid && 1000 != callingUid) {
            throw new java.lang.SecurityException("May not access signing KeySet of other apps.");
        }
        return new android.content.pm.KeySet(this.mSettings.getKeySetManagerService().getSigningKeySetByPackageNameLPr(str));
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSignedByKeySet(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            android.util.Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        android.os.IBinder token = keySet.getToken();
        if (token instanceof com.android.server.pm.KeySetHandle) {
            return this.mSettings.getKeySetManagerService().packageIsSignedByLPr(str, (com.android.server.pm.KeySetHandle) token);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public boolean isPackageSignedByKeySetExactly(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.content.pm.KeySet keySet) {
        int callingUid = android.os.Binder.getCallingUid();
        if (getInstantAppPackageName(callingUid) != null || str == null || keySet == null) {
            return false;
        }
        com.android.server.pm.pkg.AndroidPackage androidPackage = this.mPackages.get(str);
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (androidPackage == null || shouldFilterApplicationIncludingUninstalled(getPackageStateInternal(androidPackage.getPackageName()), callingUid, userId)) {
            android.util.Slog.w("PackageManager", "KeySet requested for unknown package: " + str);
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        android.os.IBinder token = keySet.getToken();
        if (token instanceof com.android.server.pm.KeySetHandle) {
            return this.mSettings.getKeySetManagerService().packageIsSignedByExactlyLPr(str, (com.android.server.pm.KeySetHandle) token);
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.util.SparseArray<int[]> getVisibilityAllowLists(@android.annotation.NonNull java.lang.String str, int[] iArr) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str, 1000);
        if (packageStateInternal == null) {
            return null;
        }
        return this.mAppsFilter.getVisibilityAllowList(this, packageStateInternal, iArr, getPackageStates());
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public int[] getVisibilityAllowList(@android.annotation.NonNull java.lang.String str, int i) {
        android.util.SparseArray<int[]> visibilityAllowLists = getVisibilityAllowLists(str, new int[]{i});
        if (visibilityAllowLists != null) {
            return visibilityAllowLists.get(i);
        }
        return null;
    }

    @Override // com.android.server.pm.Computer
    public boolean canQueryPackage(int i, @android.annotation.Nullable java.lang.String str) {
        if (i == 0 || str == null) {
            return true;
        }
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase == null) {
            return false;
        }
        int appId = android.os.UserHandle.getAppId(getPackageUid(str, 0L, android.os.UserHandle.getUserId(i)));
        if (appId != -1) {
            return this.mSettings.getSettingBase(appId) instanceof com.android.server.pm.PackageSetting ? !shouldFilterApplication((com.android.server.pm.PackageSetting) r8, i, r3) : !shouldFilterApplication((com.android.server.pm.SharedUserSetting) r8, i, r3);
        }
        if (settingBase instanceof com.android.server.pm.PackageSetting) {
            com.android.server.pm.pkg.AndroidPackage pkg = ((com.android.server.pm.PackageSetting) settingBase).getPkg();
            if (pkg != null && this.mAppsFilter.canQueryPackage(pkg, str)) {
                return true;
            }
            return false;
        }
        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = ((com.android.server.pm.SharedUserSetting) settingBase).getPackageStates();
        for (int size = packageStates.size() - 1; size >= 0; size--) {
            com.android.server.pm.pkg.AndroidPackage pkg2 = packageStates.valueAt(size).getPkg();
            if (pkg2 != null && this.mAppsFilter.canQueryPackage(pkg2, str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.Computer
    public int getPackageUid(@android.annotation.NonNull java.lang.String str, long j, int i) {
        if (!this.mUserManager.exists(i)) {
            return -1;
        }
        int callingUid = android.os.Binder.getCallingUid();
        long updateFlagsForPackage = updateFlagsForPackage(j, i);
        enforceCrossUserPermission(callingUid, i, false, false, "getPackageUid");
        return getPackageUidInternal(str, updateFlagsForPackage, i, callingUid);
    }

    @Override // com.android.server.pm.Computer
    public boolean canAccessComponent(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(componentName.getPackageName());
        return (packageStateInternal == null || shouldFilterApplication(packageStateInternal, i, componentName, 0, i2, true)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public boolean isCallerInstallerOfRecord(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal2;
        return (androidPackage == null || (packageStateInternal = getPackageStateInternal(androidPackage.getPackageName())) == null || (packageStateInternal2 = getPackageStateInternal(packageStateInternal.getInstallSource().mInstallerPackageName)) == null || !android.os.UserHandle.isSameApp(packageStateInternal2.getAppId(), i)) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    public int getInstallReason(@android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, true, false, "get install reason");
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mSettings.getPackage(str);
        if (packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i)) {
            return 0;
        }
        return packageStateInternal.getUserStateOrDefault(i).getInstallReason();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public boolean[] canPackageQuery(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String[] strArr, int i) {
        int length = strArr.length;
        boolean[] zArr = new boolean[length];
        if (!this.mUserManager.exists(i)) {
            return zArr;
        }
        int callingUid = android.os.Binder.getCallingUid();
        enforceCrossUserPermission(callingUid, i, false, false, "can package query");
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        com.android.server.pm.pkg.PackageStateInternal[] packageStateInternalArr = new com.android.server.pm.pkg.PackageStateInternal[length];
        boolean z = packageStateInternal == null || shouldFilterApplicationIncludingUninstalled(packageStateInternal, callingUid, i);
        for (int i2 = 0; !z && i2 < length; i2++) {
            packageStateInternalArr[i2] = getPackageStateInternal(strArr[i2]);
            z = packageStateInternalArr[i2] == null || shouldFilterApplicationIncludingUninstalled(packageStateInternalArr[i2], callingUid, i);
        }
        if (z) {
            throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException("Package(s) " + str + " and/or " + java.util.Arrays.toString(strArr) + " not found."));
        }
        int uid = android.os.UserHandle.getUid(i, packageStateInternal.getAppId());
        for (int i3 = 0; i3 < length; i3++) {
            zArr[i3] = !shouldFilterApplication(packageStateInternalArr[i3], uid, i);
        }
        return zArr;
    }

    @Override // com.android.server.pm.Computer
    public boolean canForwardTo(@android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, int i, int i2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", null);
        if (this.mCrossProfileIntentResolverEngine.canReachTo(this, intent, str, i, i2)) {
            return true;
        }
        if (!intent.hasWebURI()) {
            return false;
        }
        int callingUid = android.os.Binder.getCallingUid();
        android.content.pm.UserInfo profileParent = getProfileParent(i);
        return (profileParent == null || getCrossProfileDomainPreferredLpr(intent, str, updateFlagsForResolve(0L, profileParent.id, callingUid, false, isImplicitImageCaptureIntentAndNotSetByDpc(intent, profileParent.id, str, 0L)) | 65536, i, profileParent.id) == null) ? false : true;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.List<android.content.pm.ApplicationInfo> getPersistentApplications(boolean z, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        android.content.pm.ApplicationInfo generateApplicationInfo;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int size = this.mPackages.size();
        int callingUserId = android.os.UserHandle.getCallingUserId();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.pm.pkg.AndroidPackage valueAt = this.mPackages.valueAt(i2);
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal2 = this.mSettings.getPackage(valueAt.getPackageName());
            boolean z2 = ((262144 & i) == 0 || valueAt.isDirectBootAware()) ? false : true;
            boolean z3 = (524288 & i) != 0 && valueAt.isDirectBootAware();
            if (valueAt.isPersistent() && ((!z || packageStateInternal2.isSystem()) && ((z2 || z3) && (packageStateInternal = this.mSettings.getPackage(valueAt.getPackageName())) != null && (generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(valueAt, i, packageStateInternal.getUserStateOrDefault(callingUserId), callingUserId, packageStateInternal)) != null))) {
                arrayList.add(generateApplicationInfo);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.SparseArray<java.lang.String> getAppsWithSharedUserIds() {
        android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>();
        for (com.android.server.pm.pkg.SharedUserApi sharedUserApi : this.mSettings.getSharedUsers().values()) {
            sparseArray.put(android.os.UserHandle.getAppId(sharedUserApi.getAppId()), sharedUserApi.getName());
        }
        return sparseArray;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.lang.String[] getSharedUserPackagesForPackage(@android.annotation.NonNull java.lang.String str, int i) {
        if (this.mSettings.getPackage(str) == null || this.mSettings.getSharedUserFromPackageName(str) == null) {
            return libcore.util.EmptyArray.STRING;
        }
        android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = this.mSettings.getSharedUserFromPackageName(str).getPackageStates();
        int size = packageStates.size();
        java.lang.String[] strArr = new java.lang.String[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i3);
            if (valueAt.getUserStateOrDefault(i).isInstalled()) {
                strArr[i2] = valueAt.getPackageName();
                i2++;
            }
        }
        java.lang.String[] strArr2 = (java.lang.String[]) com.android.internal.util.ArrayUtils.trimToSize(strArr, i2);
        return strArr2 != null ? strArr2 : libcore.util.EmptyArray.STRING;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.Set<java.lang.String> getUnusedPackages(long j) {
        int i;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.PackageStateInternal> packages = this.mSettings.getPackages();
        int i2 = 0;
        while (i2 < packages.size()) {
            com.android.server.pm.pkg.PackageStateInternal valueAt = packages.valueAt(i2);
            if (valueAt.getPkg() == null) {
                i = i2;
            } else {
                i = i2;
                if (com.android.server.pm.PackageManagerServiceUtils.isUnusedSinceTimeInMillis(com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(valueAt.getUserStates()), currentTimeMillis, j, this.mDexManager.getPackageUseInfoOrDefault(valueAt.getPackageName()), valueAt.getTransientState().getLatestPackageUseTimeInMills(), valueAt.getTransientState().getLatestForegroundPackageUseTimeInMills())) {
                    arraySet.add(valueAt.getPackageName());
                }
            }
            i2 = i + 1;
        }
        return arraySet;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public java.lang.CharSequence getHarmfulAppWarning(@android.annotation.NonNull java.lang.String str, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        int appId = android.os.UserHandle.getAppId(callingUid);
        enforceCrossUserPermission(callingUid, i, true, true, "getHarmfulAppInfo");
        if (!com.android.server.pm.PackageManagerServiceUtils.isSystemOrRoot(appId) && checkUidPermission("android.permission.SET_HARMFUL_APP_WARNINGS", callingUid) != 0) {
            throw new java.lang.SecurityException("Caller must have the android.permission.SET_HARMFUL_APP_WARNINGS permission.");
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
        if (packageStateInternal == null) {
            throw new java.lang.IllegalArgumentException("Unknown package: " + str);
        }
        return packageStateInternal.getUserStateOrDefault(i).getHarmfulAppWarning();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.lang.String[] filterOnlySystemPackages(@android.annotation.Nullable java.lang.String... strArr) {
        if (strArr == null) {
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(strArr.length);
        for (java.lang.String str : strArr) {
            if (str != null) {
                com.android.server.pm.pkg.PackageStateInternal packageStateInternal = getPackageStateInternal(str);
                if (packageStateInternal == null || packageStateInternal.getAndroidPackage() == null) {
                    android.util.Log.w("PackageManager", "Could not find package " + str);
                } else if (!packageStateInternal.isSystem()) {
                    android.util.Log.w("PackageManager", str + " is not system");
                } else {
                    arrayList.add(str);
                }
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackagesForAppId(int i) {
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(i);
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            return ((com.android.server.pm.SharedUserSetting) settingBase).getPackages();
        }
        if (settingBase instanceof com.android.server.pm.PackageSetting) {
            com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = ((com.android.server.pm.PackageSetting) settingBase).getPkg();
            if (pkg != null) {
                return java.util.Collections.singletonList(pkg);
            }
            return java.util.Collections.emptyList();
        }
        return java.util.Collections.emptyList();
    }

    @Override // com.android.server.pm.Computer
    public int getUidTargetSdkVersion(int i) {
        int targetSdkVersion;
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        int i2 = 10000;
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> packageStates = ((com.android.server.pm.SharedUserSetting) settingBase).getPackageStates();
            int size = packageStates.size();
            for (int i3 = 0; i3 < size; i3++) {
                com.android.server.pm.pkg.PackageStateInternal valueAt = packageStates.valueAt(i3);
                if (valueAt.getPkg() != null && (targetSdkVersion = valueAt.getPkg().getTargetSdkVersion()) < i2) {
                    i2 = targetSdkVersion;
                }
            }
            return i2;
        }
        if (settingBase instanceof com.android.server.pm.PackageSetting) {
            com.android.server.pm.PackageSetting packageSetting = (com.android.server.pm.PackageSetting) settingBase;
            if (packageSetting.getPkg() != null) {
                return packageSetting.getPkg().getTargetSdkVersion();
            }
        }
        return 10000;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> getProcessesForUid(int i) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg;
        if (android.os.Process.isSdkSandboxUid(i)) {
            i = getBaseSdkSandboxUid();
        }
        com.android.server.pm.SettingBase settingBase = this.mSettings.getSettingBase(android.os.UserHandle.getAppId(i));
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            return com.android.server.pm.parsing.PackageInfoUtils.generateProcessInfo(((com.android.server.pm.SharedUserSetting) settingBase).processes, 0L);
        }
        if (!(settingBase instanceof com.android.server.pm.PackageSetting) || (pkg = ((com.android.server.pm.PackageSetting) settingBase).getPkg()) == null) {
            return null;
        }
        return com.android.server.pm.parsing.PackageInfoUtils.generateProcessInfo(pkg.getProcesses(), 0L);
    }

    @Override // com.android.server.pm.Computer
    public boolean getBlockUninstall(int i, @android.annotation.NonNull java.lang.String str) {
        return this.mSettings.getBlockUninstall(i, str);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.util.Pair<com.android.server.pm.pkg.PackageStateInternal, com.android.server.pm.pkg.SharedUserApi> getPackageOrSharedUser(int i) {
        com.android.server.utils.Watchable settingBase = this.mSettings.getSettingBase(i);
        if (settingBase instanceof com.android.server.pm.SharedUserSetting) {
            return android.util.Pair.create(null, (com.android.server.pm.pkg.SharedUserApi) settingBase);
        }
        if (settingBase instanceof com.android.server.pm.PackageSetting) {
            return android.util.Pair.create((com.android.server.pm.pkg.PackageStateInternal) settingBase, null);
        }
        return null;
    }

    private int getBaseSdkSandboxUid() {
        return getPackage(this.mService.getSdkSandboxPackageName()).getUid();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public com.android.server.pm.pkg.SharedUserApi getSharedUser(int i) {
        return this.mSettings.getSharedUserFromAppId(i);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.ArraySet<com.android.server.pm.pkg.PackageStateInternal> getSharedUserPackages(int i) {
        return this.mSettings.getSharedUserPackages(i);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public com.android.server.pm.resolution.ComponentResolverApi getComponentResolver() {
        return this.mComponentResolver;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public com.android.server.pm.pkg.PackageStateInternal getDisabledSystemPackage(@android.annotation.NonNull java.lang.String str) {
        return this.mSettings.getDisabledSystemPkg(str);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.pm.ResolveInfo getInstantAppInstallerInfo() {
        return this.mInstantAppInstallerInfo;
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public com.android.server.utils.WatchedArrayMap<java.lang.String, java.lang.Integer> getFrozenPackages() {
        return this.mFrozenPackages;
    }

    @Override // com.android.server.pm.Computer
    public void checkPackageFrozen(@android.annotation.NonNull java.lang.String str) {
        if (!this.mFrozenPackages.containsKey(str)) {
            android.util.Slog.wtf("PackageManager", "Expected " + str + " to be frozen!", new java.lang.Throwable());
        }
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.Nullable
    public android.content.ComponentName getInstantAppInstallerComponent() {
        if (this.mLocalInstantAppInstallerActivity == null) {
            return null;
        }
        return this.mLocalInstantAppInstallerActivity.getComponentName();
    }

    @Override // com.android.server.pm.Computer
    public void dumpPermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        this.mSettings.dumpPermissions(printWriter, str, arraySet, dumpState);
    }

    @Override // com.android.server.pm.Computer
    public void dumpPackages(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, boolean z) {
        this.mSettings.dumpPackages(printWriter, str, arraySet, dumpState, z);
    }

    @Override // com.android.server.pm.Computer
    public void dumpKeySet(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.DumpState dumpState) {
        this.mSettings.dumpKeySet(printWriter, str, dumpState);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedUsers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, boolean z) {
        this.mSettings.dumpSharedUsers(printWriter, str, arraySet, dumpState, z);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedUsersProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mSettings.dumpSharedUsersProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    public void dumpPackagesProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mSettings.dumpPackagesProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    public void dumpSharedLibrariesProto(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream) {
        this.mSharedLibraries.dumpProto(protoOutputStream);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public java.util.List<? extends com.android.server.pm.pkg.PackageStateInternal> getVolumePackages(@android.annotation.NonNull java.lang.String str) {
        return this.mSettings.getVolumePackages(str);
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, ? extends com.android.server.pm.pkg.SharedUserApi> getSharedUsers() {
        return this.mSettings.getSharedUsers();
    }

    @Override // com.android.server.pm.Computer
    @android.annotation.NonNull
    public android.content.pm.UserInfo[] getUserInfos() {
        return this.mInjector.getUserManagerInternal().getUserInfos();
    }
}
