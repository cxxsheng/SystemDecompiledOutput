package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
/* loaded from: classes2.dex */
public final class PackageManagerServiceTestParams {
    public android.util.DisplayMetrics Metrics;

    @android.annotation.Nullable
    public java.lang.String ambientContextDetectionPackage;
    public com.android.server.pm.ApexManager apexManager;
    public com.android.server.pm.AppDataHelper appDataHelper;
    public java.io.File appInstallDir;
    public java.io.File appLib32InstallDir;

    @android.annotation.Nullable
    public java.lang.String appPredictionServicePackage;
    public com.android.server.pm.dex.ArtManagerService artManagerService;
    public android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> availableFeatures;

    @android.annotation.Nullable
    public com.android.server.pm.BackgroundDexOptService backgroundDexOptService;
    public com.android.server.pm.BroadcastHelper broadcastHelper;

    @android.annotation.Nullable
    public java.lang.String configuratorPackage;
    public int defParseFlags;
    public com.android.server.pm.DefaultAppProvider defaultAppProvider;

    @android.annotation.Nullable
    public java.lang.String defaultTextClassifierPackage;
    public com.android.server.pm.DeletePackageHelper deletePackageHelper;
    public com.android.server.pm.dex.DexManager dexManager;
    public com.android.server.pm.DexOptHelper dexOptHelper;
    public java.util.List<com.android.server.pm.ScanPartition> dirsToScanAsSystem;
    public com.android.server.pm.DistractingPackageHelper distractingPackageHelper;
    public com.android.server.pm.dex.DynamicCodeLogger dynamicCodeLogger;
    public boolean factoryTest;
    public com.android.server.pm.FreeStorageHelper freeStorageHelper;
    public android.os.Handler handler;

    @android.annotation.Nullable
    public java.lang.String incidentReportApproverPackage;
    public android.os.incremental.IncrementalManager incrementalManager;
    public com.android.server.pm.InitAppsHelper initAndSystemPackageHelper;
    public com.android.server.pm.InstallPackageHelper installPackageHelper;
    public com.android.server.pm.PackageInstallerService installerService;
    public com.android.server.pm.InstantAppRegistry instantAppRegistry;
    public com.android.server.pm.InstantAppResolverConnection instantAppResolverConnection;
    public android.content.ComponentName instantAppResolverSettingsComponent;
    public boolean isEngBuild;
    public boolean isPreNmr1Upgrade;
    public boolean isPreQupgrade;
    public boolean isUpgrade;
    public boolean isUserDebugBuild;
    public com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal;
    public com.android.server.pm.ModuleInfoProvider moduleInfoProvider;
    public com.android.server.pm.MovePackageHelper.MoveCallbacks moveCallbacks;
    public boolean onlyCore;
    public com.android.internal.content.om.OverlayConfig overlayConfig;

    @android.annotation.Nullable
    public java.lang.String overlayConfigSignaturePackage;
    public com.android.server.pm.PackageDexOptimizer packageDexOptimizer;
    public com.android.server.pm.PackageMonitorCallbackHelper packageMonitorCallbackHelper;
    public com.android.internal.pm.parsing.PackageParser2.Callback packageParserCallback;
    public android.util.ArrayMap<java.lang.String, com.android.server.pm.pkg.AndroidPackage> packages;
    public com.android.server.pm.PendingPackageBroadcasts pendingPackageBroadcasts;
    public android.content.pm.PackageManagerInternal pmInternal;
    public com.android.server.pm.PreferredActivityHelper preferredActivityHelper;
    public com.android.server.pm.ProcessLoggingHandler processLoggingHandler;
    public com.android.server.pm.ProtectedPackages protectedPackages;

    @android.annotation.Nullable
    public java.lang.String recentsPackage;
    public com.android.server.pm.RemovePackageHelper removePackageHelper;

    @android.annotation.NonNull
    public java.lang.String requiredInstallerPackage;

    @android.annotation.NonNull
    public java.lang.String requiredPermissionControllerPackage;

    @android.annotation.NonNull
    public java.lang.String requiredSdkSandboxPackage;

    @android.annotation.NonNull
    public java.lang.String requiredUninstallerPackage;

    @android.annotation.NonNull
    public java.lang.String[] requiredVerifierPackages;
    public android.content.ComponentName resolveComponentName;
    public com.android.server.pm.ResolveIntentHelper resolveIntentHelper;

    @android.annotation.Nullable
    public java.lang.String retailDemoPackage;
    public int sdkVersion;
    public java.lang.String[] separateProcesses;

    @android.annotation.NonNull
    public java.lang.String servicesExtensionPackageName;

    @android.annotation.Nullable
    public java.lang.String setupWizardPackage;

    @android.annotation.NonNull
    public java.lang.String sharedSystemSharedLibraryPackageName;
    public boolean shouldStopSystemPackagesByDefault;
    public com.android.server.pm.StorageEventHelper storageEventHelper;

    @android.annotation.Nullable
    public java.lang.String storageManagerPackage;
    public com.android.server.pm.SuspendPackageHelper suspendPackageHelper;

    @android.annotation.Nullable
    public java.lang.String systemTextClassifierPackage;
    public android.content.pm.TestUtilityService testUtilityService;

    @android.annotation.Nullable
    public java.lang.String wearableSensingPackage;
    public final com.android.server.pm.ChangedPackagesTracker changedPackagesTracker = new com.android.server.pm.ChangedPackagesTracker();
    public int priorSdkVersion = -1;
    public int sdkInt = android.os.Build.VERSION.SDK_INT;
    public final java.lang.String incrementalVersion = android.os.Build.VERSION.INCREMENTAL;
    public final java.util.Set<java.lang.String> initialNonStoppedSystemPackages = new android.util.ArraySet();
}
