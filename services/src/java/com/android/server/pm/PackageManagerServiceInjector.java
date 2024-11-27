package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
/* loaded from: classes2.dex */
public class PackageManagerServiceInjector {
    private final com.android.server.pm.PackageAbiHelper mAbiHelper;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.ApexManager> mApexManagerProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.AppsFilterImpl> mAppsFilterProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.dex.ArtManagerService> mArtManagerServiceProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.BackgroundDexOptService> mBackgroundDexOptService;
    private final java.util.concurrent.Executor mBackgroundExecutor;
    private final android.os.Handler mBackgroundHandler;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.resolution.ComponentResolver> mComponentResolverProducer;
    private final android.content.Context mContext;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.CrossProfileIntentFilterHelper> mCrossProfileIntentFilterHelperProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.DefaultAppProvider> mDefaultAppProviderProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.dex.DexManager> mDexManagerProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<android.util.DisplayMetrics> mDisplayMetricsProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.verify.domain.DomainVerificationManagerInternal> mDomainVerificationManagerInternalProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.dex.DynamicCodeLogger> mDynamicCodeLoggerProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.ServiceProducer mGetLocalServiceProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.ServiceProducer mGetSystemServiceProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<android.os.Handler> mHandlerProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<android.app.backup.IBackupManager> mIBackupManager;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<android.os.incremental.IncrementalManager> mIncrementalManagerProducer;
    private final java.lang.Object mInstallLock;
    private final com.android.server.pm.Installer mInstaller;
    private final com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument<com.android.server.pm.InstantAppResolverConnection, android.content.ComponentName> mInstantAppResolverConnectionProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.permission.LegacyPermissionManagerInternal> mLegacyPermissionManagerInternalProducer;
    private final com.android.server.pm.PackageManagerTracedLock mLock;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.ModuleInfoProvider> mModuleInfoProviderProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.PackageDexOptimizer> mPackageDexOptimizerProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.PackageInstallerService> mPackageInstallerServiceProducer;
    private com.android.server.pm.PackageManagerService mPackageManager;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.PackageMonitorCallbackHelper> mPackageMonitorCallbackHelper;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.permission.PermissionManagerServiceInternal> mPermissionManagerServiceProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.compat.PlatformCompat> mPlatformCompatProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> mPreparingPackageParserProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> mScanningCachingPackageParserProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> mScanningPackageParserProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.Settings> mSettingsProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.SharedLibrariesImpl> mSharedLibrariesProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.SystemConfig> mSystemConfigProducer;
    private final java.util.List<com.android.server.pm.ScanPartition> mSystemPartitions;
    private final com.android.server.pm.PackageManagerServiceInjector.SystemWrapper mSystemWrapper;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.UpdateOwnershipHelper> mUpdateOwnershipHelperProducer;
    private final com.android.server.pm.PackageManagerServiceInjector.Singleton<com.android.server.pm.UserManagerService> mUserManagerProducer;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    interface Producer<T> {
        T produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService);
    }

    interface ProducerWithArgument<T, R> {
        T produce(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService, R r);
    }

    interface ServiceProducer {
        <T> T produce(java.lang.Class<T> cls);
    }

    public interface SystemWrapper {
        void disablePackageCaches();

        void enablePackageCaches();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    static class Singleton<T> {
        private volatile T mInstance = null;
        private final com.android.server.pm.PackageManagerServiceInjector.Producer<T> mProducer;

        Singleton(com.android.server.pm.PackageManagerServiceInjector.Producer<T> producer) {
            this.mProducer = producer;
        }

        T get(com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, com.android.server.pm.PackageManagerService packageManagerService) {
            if (this.mInstance == null) {
                this.mInstance = this.mProducer.produce(packageManagerServiceInjector, packageManagerService);
            }
            return this.mInstance;
        }
    }

    PackageManagerServiceInjector(android.content.Context context, com.android.server.pm.PackageManagerTracedLock packageManagerTracedLock, com.android.server.pm.Installer installer, java.lang.Object obj, com.android.server.pm.PackageAbiHelper packageAbiHelper, android.os.Handler handler, java.util.List<com.android.server.pm.ScanPartition> list, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.resolution.ComponentResolver> producer, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.permission.PermissionManagerServiceInternal> producer2, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.UserManagerService> producer3, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.Settings> producer4, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.AppsFilterImpl> producer5, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.compat.PlatformCompat> producer6, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.SystemConfig> producer7, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.PackageDexOptimizer> producer8, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.dex.DexManager> producer9, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.dex.DynamicCodeLogger> producer10, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.dex.ArtManagerService> producer11, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.ApexManager> producer12, com.android.server.pm.PackageManagerServiceInjector.Producer<android.os.incremental.IncrementalManager> producer13, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.DefaultAppProvider> producer14, com.android.server.pm.PackageManagerServiceInjector.Producer<android.util.DisplayMetrics> producer15, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> producer16, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> producer17, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.internal.pm.parsing.PackageParser2> producer18, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.PackageInstallerService> producer19, com.android.server.pm.PackageManagerServiceInjector.ProducerWithArgument<com.android.server.pm.InstantAppResolverConnection, android.content.ComponentName> producerWithArgument, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.ModuleInfoProvider> producer20, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.permission.LegacyPermissionManagerInternal> producer21, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.verify.domain.DomainVerificationManagerInternal> producer22, com.android.server.pm.PackageManagerServiceInjector.Producer<android.os.Handler> producer23, com.android.server.pm.PackageManagerServiceInjector.SystemWrapper systemWrapper, com.android.server.pm.PackageManagerServiceInjector.ServiceProducer serviceProducer, com.android.server.pm.PackageManagerServiceInjector.ServiceProducer serviceProducer2, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.BackgroundDexOptService> producer24, com.android.server.pm.PackageManagerServiceInjector.Producer<android.app.backup.IBackupManager> producer25, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.SharedLibrariesImpl> producer26, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.CrossProfileIntentFilterHelper> producer27, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.UpdateOwnershipHelper> producer28, com.android.server.pm.PackageManagerServiceInjector.Producer<com.android.server.pm.PackageMonitorCallbackHelper> producer29) {
        this.mContext = context;
        this.mLock = packageManagerTracedLock;
        this.mInstaller = installer;
        this.mAbiHelper = packageAbiHelper;
        this.mInstallLock = obj;
        this.mBackgroundHandler = handler;
        this.mBackgroundExecutor = new android.os.HandlerExecutor(handler);
        this.mSystemPartitions = list;
        this.mComponentResolverProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer);
        this.mPermissionManagerServiceProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer2);
        this.mUserManagerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer3);
        this.mSettingsProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer4);
        this.mAppsFilterProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer5);
        this.mPlatformCompatProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer6);
        this.mSystemConfigProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer7);
        this.mPackageDexOptimizerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer8);
        this.mDexManagerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer9);
        this.mDynamicCodeLoggerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer10);
        this.mArtManagerServiceProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer11);
        this.mApexManagerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer12);
        this.mIncrementalManagerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer13);
        this.mDefaultAppProviderProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer14);
        this.mDisplayMetricsProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer15);
        this.mScanningCachingPackageParserProducer = producer16;
        this.mScanningPackageParserProducer = producer17;
        this.mPreparingPackageParserProducer = producer18;
        this.mPackageInstallerServiceProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer19);
        this.mInstantAppResolverConnectionProducer = producerWithArgument;
        this.mModuleInfoProviderProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer20);
        this.mLegacyPermissionManagerInternalProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer21);
        this.mSystemWrapper = systemWrapper;
        this.mGetLocalServiceProducer = serviceProducer;
        this.mGetSystemServiceProducer = serviceProducer2;
        this.mDomainVerificationManagerInternalProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer22);
        this.mHandlerProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer23);
        this.mBackgroundDexOptService = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer24);
        this.mIBackupManager = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer25);
        this.mSharedLibrariesProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer26);
        this.mCrossProfileIntentFilterHelperProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer27);
        this.mUpdateOwnershipHelperProducer = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer28);
        this.mPackageMonitorCallbackHelper = new com.android.server.pm.PackageManagerServiceInjector.Singleton<>(producer29);
    }

    public void bootstrap(com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPackageManager = packageManagerService;
    }

    public com.android.server.pm.UserManagerInternal getUserManagerInternal() {
        return getUserManagerService().getInternalForInjectorOnly();
    }

    public com.android.server.pm.PackageAbiHelper getAbiHelper() {
        return this.mAbiHelper;
    }

    public java.lang.Object getInstallLock() {
        return this.mInstallLock;
    }

    public java.util.List<com.android.server.pm.ScanPartition> getSystemPartitions() {
        return this.mSystemPartitions;
    }

    public com.android.server.pm.UserManagerService getUserManagerService() {
        return this.mUserManagerProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.PackageManagerTracedLock getLock() {
        return this.mLock;
    }

    public com.android.server.pm.CrossProfileIntentFilterHelper getCrossProfileIntentFilterHelper() {
        return this.mCrossProfileIntentFilterHelperProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.Installer getInstaller() {
        return this.mInstaller;
    }

    public com.android.server.pm.resolution.ComponentResolver getComponentResolver() {
        return this.mComponentResolverProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManagerServiceInternal() {
        return this.mPermissionManagerServiceProducer.get(this, this.mPackageManager);
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    public com.android.server.pm.Settings getSettings() {
        return this.mSettingsProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.AppsFilterImpl getAppsFilter() {
        return this.mAppsFilterProducer.get(this, this.mPackageManager);
    }

    public com.android.server.compat.PlatformCompat getCompatibility() {
        return this.mPlatformCompatProducer.get(this, this.mPackageManager);
    }

    public com.android.server.SystemConfig getSystemConfig() {
        return this.mSystemConfigProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.PackageDexOptimizer getPackageDexOptimizer() {
        return this.mPackageDexOptimizerProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.dex.DexManager getDexManager() {
        return this.mDexManagerProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.dex.DynamicCodeLogger getDynamicCodeLogger() {
        return this.mDynamicCodeLoggerProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.dex.ArtManagerService getArtManagerService() {
        return this.mArtManagerServiceProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.ApexManager getApexManager() {
        return this.mApexManagerProducer.get(this, this.mPackageManager);
    }

    public android.os.Handler getBackgroundHandler() {
        return this.mBackgroundHandler;
    }

    public java.util.concurrent.Executor getBackgroundExecutor() {
        return this.mBackgroundExecutor;
    }

    public android.util.DisplayMetrics getDisplayMetrics() {
        return this.mDisplayMetricsProducer.get(this, this.mPackageManager);
    }

    public <T> T getLocalService(java.lang.Class<T> cls) {
        return (T) this.mGetLocalServiceProducer.produce(cls);
    }

    public <T> T getSystemService(java.lang.Class<T> cls) {
        return (T) this.mGetSystemServiceProducer.produce(cls);
    }

    public com.android.server.pm.PackageManagerServiceInjector.SystemWrapper getSystemWrapper() {
        return this.mSystemWrapper;
    }

    public android.os.incremental.IncrementalManager getIncrementalManager() {
        return this.mIncrementalManagerProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.DefaultAppProvider getDefaultAppProvider() {
        return this.mDefaultAppProviderProducer.get(this, this.mPackageManager);
    }

    public com.android.internal.pm.parsing.PackageParser2 getScanningCachingPackageParser() {
        return this.mScanningCachingPackageParserProducer.produce(this, this.mPackageManager);
    }

    public com.android.internal.pm.parsing.PackageParser2 getScanningPackageParser() {
        return this.mScanningPackageParserProducer.produce(this, this.mPackageManager);
    }

    public com.android.internal.pm.parsing.PackageParser2 getPreparingPackageParser() {
        return this.mPreparingPackageParserProducer.produce(this, this.mPackageManager);
    }

    public com.android.server.pm.PackageInstallerService getPackageInstallerService() {
        return this.mPackageInstallerServiceProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.InstantAppResolverConnection getInstantAppResolverConnection(android.content.ComponentName componentName) {
        return this.mInstantAppResolverConnectionProducer.produce(this, this.mPackageManager, componentName);
    }

    public com.android.server.pm.ModuleInfoProvider getModuleInfoProvider() {
        return this.mModuleInfoProviderProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.permission.LegacyPermissionManagerInternal getLegacyPermissionManagerInternal() {
        return this.mLegacyPermissionManagerInternalProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.verify.domain.DomainVerificationManagerInternal getDomainVerificationManagerInternal() {
        return this.mDomainVerificationManagerInternalProducer.get(this, this.mPackageManager);
    }

    public android.os.Handler getHandler() {
        return this.mHandlerProducer.get(this, this.mPackageManager);
    }

    public android.app.ActivityManagerInternal getActivityManagerInternal() {
        return (android.app.ActivityManagerInternal) getLocalService(android.app.ActivityManagerInternal.class);
    }

    @android.annotation.Nullable
    public com.android.server.pm.BackgroundDexOptService getBackgroundDexOptService() {
        return this.mBackgroundDexOptService.get(this, this.mPackageManager);
    }

    public android.app.backup.IBackupManager getIBackupManager() {
        return this.mIBackupManager.get(this, this.mPackageManager);
    }

    public com.android.server.pm.SharedLibrariesImpl getSharedLibrariesImpl() {
        return this.mSharedLibrariesProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.UpdateOwnershipHelper getUpdateOwnershipHelper() {
        return this.mUpdateOwnershipHelperProducer.get(this, this.mPackageManager);
    }

    public com.android.server.pm.PackageMonitorCallbackHelper getPackageMonitorCallbackHelper() {
        return this.mPackageMonitorCallbackHelper.get(this, this.mPackageManager);
    }
}
