package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public class PackageUserStateImpl extends com.android.server.utils.WatchableImpl implements com.android.server.pm.pkg.PackageUserStateInternal, com.android.server.utils.Snappable {

    @android.annotation.Nullable
    private com.android.server.pm.pkg.ArchiveState mArchiveState;
    private int mBooleans;
    private long mCeDataInode;

    @android.annotation.Nullable
    private com.android.server.utils.WatchedArrayMap<android.content.ComponentName, android.util.Pair<java.lang.String, java.lang.Integer>> mComponentLabelIconOverrideMap;
    private long mDeDataInode;

    @android.annotation.Nullable
    protected com.android.server.utils.WatchedArraySet<java.lang.String> mDisabledComponentsWatched;
    private int mDistractionFlags;

    @android.annotation.Nullable
    protected com.android.server.utils.WatchedArraySet<java.lang.String> mEnabledComponentsWatched;
    private int mEnabledState;
    private long mFirstInstallTimeMillis;

    @android.annotation.Nullable
    private java.lang.String mHarmfulAppWarning;
    private int mInstallReason;

    @android.annotation.Nullable
    private java.lang.String mLastDisableAppCaller;
    private int mMinAspectRatio;

    @android.annotation.Nullable
    private android.content.pm.overlay.OverlayPaths mOverlayPaths;

    @android.annotation.Nullable
    protected com.android.server.utils.WatchedArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> mSharedLibraryOverlayPaths;

    @android.annotation.NonNull
    final com.android.server.utils.SnapshotCache<com.android.server.pm.pkg.PackageUserStateImpl> mSnapshot;

    @android.annotation.Nullable
    private java.lang.String mSplashScreenTheme;

    @android.annotation.Nullable
    private com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> mSuspendParams;
    private int mUninstallReason;

    @android.annotation.Nullable
    private com.android.server.utils.Watchable mWatchable;

    private static class Booleans {
        private static final int HIDDEN = 8;
        private static final int INSTALLED = 1;
        private static final int INSTANT_APP = 16;
        private static final int NOT_LAUNCHED = 4;
        private static final int STOPPED = 2;
        private static final int VIRTUAL_PRELOADED = 32;

        public @interface Flags {
        }

        private Booleans() {
        }
    }

    private void setBoolean(@com.android.server.pm.pkg.PackageUserStateImpl.Booleans.Flags int i, boolean z) {
        if (z) {
            this.mBooleans = i | this.mBooleans;
        } else {
            this.mBooleans = (~i) & this.mBooleans;
        }
    }

    private boolean getBoolean(@com.android.server.pm.pkg.PackageUserStateImpl.Booleans.Flags int i) {
        return (i & this.mBooleans) != 0;
    }

    private com.android.server.utils.SnapshotCache<com.android.server.pm.pkg.PackageUserStateImpl> makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.pkg.PackageUserStateImpl>(this, this) { // from class: com.android.server.pm.pkg.PackageUserStateImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.pkg.PackageUserStateImpl createSnapshot() {
                return new com.android.server.pm.pkg.PackageUserStateImpl(com.android.server.pm.pkg.PackageUserStateImpl.this.mWatchable, (com.android.server.pm.pkg.PackageUserStateImpl) this.mSource);
            }
        };
    }

    public PackageUserStateImpl() {
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mMinAspectRatio = 0;
        this.mWatchable = null;
        this.mSnapshot = makeCache();
        setBoolean(1, true);
    }

    public PackageUserStateImpl(@android.annotation.NonNull com.android.server.utils.Watchable watchable) {
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mMinAspectRatio = 0;
        this.mWatchable = watchable;
        this.mSnapshot = makeCache();
        setBoolean(1, true);
    }

    public PackageUserStateImpl(@android.annotation.NonNull com.android.server.utils.Watchable watchable, com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl) {
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mMinAspectRatio = 0;
        this.mWatchable = watchable;
        this.mBooleans = packageUserStateImpl.mBooleans;
        this.mDisabledComponentsWatched = packageUserStateImpl.mDisabledComponentsWatched == null ? null : packageUserStateImpl.mDisabledComponentsWatched.snapshot();
        this.mEnabledComponentsWatched = packageUserStateImpl.mEnabledComponentsWatched == null ? null : packageUserStateImpl.mEnabledComponentsWatched.snapshot();
        this.mOverlayPaths = packageUserStateImpl.mOverlayPaths;
        this.mSharedLibraryOverlayPaths = packageUserStateImpl.mSharedLibraryOverlayPaths == null ? null : packageUserStateImpl.mSharedLibraryOverlayPaths.snapshot();
        this.mCeDataInode = packageUserStateImpl.mCeDataInode;
        this.mDeDataInode = packageUserStateImpl.mDeDataInode;
        this.mDistractionFlags = packageUserStateImpl.mDistractionFlags;
        this.mEnabledState = packageUserStateImpl.mEnabledState;
        this.mInstallReason = packageUserStateImpl.mInstallReason;
        this.mUninstallReason = packageUserStateImpl.mUninstallReason;
        this.mHarmfulAppWarning = packageUserStateImpl.mHarmfulAppWarning;
        this.mLastDisableAppCaller = packageUserStateImpl.mLastDisableAppCaller;
        this.mSplashScreenTheme = packageUserStateImpl.mSplashScreenTheme;
        this.mMinAspectRatio = packageUserStateImpl.mMinAspectRatio;
        this.mSuspendParams = packageUserStateImpl.mSuspendParams == null ? null : packageUserStateImpl.mSuspendParams.snapshot();
        this.mComponentLabelIconOverrideMap = packageUserStateImpl.mComponentLabelIconOverrideMap != null ? packageUserStateImpl.mComponentLabelIconOverrideMap.snapshot() : null;
        this.mFirstInstallTimeMillis = packageUserStateImpl.mFirstInstallTimeMillis;
        this.mArchiveState = packageUserStateImpl.mArchiveState;
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    private void onChanged() {
        if (this.mWatchable != null) {
            this.mWatchable.dispatchChange(this.mWatchable);
        }
        dispatchChange(this);
    }

    @Override // com.android.server.utils.Snappable
    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl snapshot() {
        return this.mSnapshot.snapshot();
    }

    @android.annotation.Nullable
    public boolean setOverlayPaths(@android.annotation.Nullable android.content.pm.overlay.OverlayPaths overlayPaths) {
        if (java.util.Objects.equals(overlayPaths, this.mOverlayPaths)) {
            return false;
        }
        if ((this.mOverlayPaths == null && overlayPaths.isEmpty()) || (overlayPaths == null && this.mOverlayPaths.isEmpty())) {
            return false;
        }
        this.mOverlayPaths = overlayPaths;
        onChanged();
        return true;
    }

    public boolean setSharedLibraryOverlayPaths(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.pm.overlay.OverlayPaths overlayPaths) {
        if (this.mSharedLibraryOverlayPaths == null) {
            this.mSharedLibraryOverlayPaths = new com.android.server.utils.WatchedArrayMap<>();
            this.mSharedLibraryOverlayPaths.registerObserver(this.mSnapshot);
        }
        if (java.util.Objects.equals(overlayPaths, this.mSharedLibraryOverlayPaths.get(str))) {
            return false;
        }
        if (overlayPaths == null || overlayPaths.isEmpty()) {
            boolean z = this.mSharedLibraryOverlayPaths.remove(str) != null;
            onChanged();
            return z;
        }
        this.mSharedLibraryOverlayPaths.put(str, overlayPaths);
        onChanged();
        return true;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getDisabledComponentsNoCopy() {
        return this.mDisabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getEnabledComponentsNoCopy() {
        return this.mEnabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    /* renamed from: getDisabledComponents, reason: merged with bridge method [inline-methods] */
    public android.util.ArraySet<java.lang.String> m6428getDisabledComponents() {
        return this.mDisabledComponentsWatched == null ? new android.util.ArraySet<>() : this.mDisabledComponentsWatched.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    /* renamed from: getEnabledComponents, reason: merged with bridge method [inline-methods] */
    public android.util.ArraySet<java.lang.String> m6429getEnabledComponents() {
        return this.mEnabledComponentsWatched == null ? new android.util.ArraySet<>() : this.mEnabledComponentsWatched.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentEnabled(java.lang.String str) {
        return this.mEnabledComponentsWatched != null && this.mEnabledComponentsWatched.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentDisabled(java.lang.String str) {
        return this.mDisabledComponentsWatched != null && this.mDisabledComponentsWatched.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public android.content.pm.overlay.OverlayPaths getAllOverlayPaths() {
        if (this.mOverlayPaths == null && this.mSharedLibraryOverlayPaths == null) {
            return null;
        }
        android.content.pm.overlay.OverlayPaths.Builder builder = new android.content.pm.overlay.OverlayPaths.Builder();
        builder.addAll(this.mOverlayPaths);
        if (this.mSharedLibraryOverlayPaths != null) {
            java.util.Iterator<android.content.pm.overlay.OverlayPaths> it = this.mSharedLibraryOverlayPaths.values().iterator();
            while (it.hasNext()) {
                builder.addAll(it.next());
            }
        }
        return builder.build();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public boolean overrideLabelAndIcon(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.Integer num) {
        java.lang.Integer num2;
        java.lang.String str2;
        android.util.Pair<java.lang.String, java.lang.Integer> pair;
        if (this.mComponentLabelIconOverrideMap != null && (pair = this.mComponentLabelIconOverrideMap.get(componentName)) != null) {
            str2 = (java.lang.String) pair.first;
            num2 = (java.lang.Integer) pair.second;
        } else {
            num2 = null;
            str2 = null;
        }
        boolean z = (android.text.TextUtils.equals(str2, str) && java.util.Objects.equals(num2, num)) ? false : true;
        if (z) {
            if (str == null && num == null) {
                this.mComponentLabelIconOverrideMap.remove(componentName);
                if (this.mComponentLabelIconOverrideMap.isEmpty()) {
                    this.mComponentLabelIconOverrideMap = null;
                }
            } else {
                if (this.mComponentLabelIconOverrideMap == null) {
                    this.mComponentLabelIconOverrideMap = new com.android.server.utils.WatchedArrayMap<>(1);
                    this.mComponentLabelIconOverrideMap.registerObserver(this.mSnapshot);
                }
                this.mComponentLabelIconOverrideMap.put(componentName, android.util.Pair.create(str, num));
            }
            onChanged();
        }
        return z;
    }

    public void resetOverrideComponentLabelIcon() {
        this.mComponentLabelIconOverrideMap = null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public android.util.Pair<java.lang.String, java.lang.Integer> getOverrideLabelIconForComponent(android.content.ComponentName componentName) {
        if (com.android.internal.util.ArrayUtils.isEmpty(this.mComponentLabelIconOverrideMap)) {
            return null;
        }
        return this.mComponentLabelIconOverrideMap.get(componentName);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isSuspended() {
        return !com.android.internal.util.CollectionUtils.isEmpty(this.mSuspendParams);
    }

    public com.android.server.pm.pkg.PackageUserStateImpl putSuspendParams(@android.annotation.NonNull android.content.pm.UserPackage userPackage, @android.annotation.Nullable com.android.server.pm.pkg.SuspendParams suspendParams) {
        if (this.mSuspendParams == null) {
            this.mSuspendParams = new com.android.server.utils.WatchedArrayMap<>();
            this.mSuspendParams.registerObserver(this.mSnapshot);
        }
        if (!this.mSuspendParams.containsKey(userPackage) || !java.util.Objects.equals(this.mSuspendParams.get(userPackage), suspendParams)) {
            this.mSuspendParams.put(userPackage, suspendParams);
            onChanged();
        }
        return this;
    }

    public com.android.server.pm.pkg.PackageUserStateImpl removeSuspension(@android.annotation.NonNull android.content.pm.UserPackage userPackage) {
        if (this.mSuspendParams != null) {
            this.mSuspendParams.remove(userPackage);
            onChanged();
        }
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setDisabledComponents(@android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) {
        if (this.mDisabledComponentsWatched == null) {
            this.mDisabledComponentsWatched = new com.android.server.utils.WatchedArraySet<>();
            this.mDisabledComponentsWatched.registerObserver(this.mSnapshot);
        }
        this.mDisabledComponentsWatched.clear();
        if (arraySet != null) {
            this.mDisabledComponentsWatched.addAll(arraySet);
        }
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setEnabledComponents(@android.annotation.Nullable android.util.ArraySet<java.lang.String> arraySet) {
        if (this.mEnabledComponentsWatched == null) {
            this.mEnabledComponentsWatched = new com.android.server.utils.WatchedArraySet<>();
            this.mEnabledComponentsWatched.registerObserver(this.mSnapshot);
        }
        this.mEnabledComponentsWatched.clear();
        if (arraySet != null) {
            this.mEnabledComponentsWatched.addAll(arraySet);
        }
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setEnabledComponents(@android.annotation.Nullable com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        this.mEnabledComponentsWatched = watchedArraySet;
        if (this.mEnabledComponentsWatched != null) {
            this.mEnabledComponentsWatched.registerObserver(this.mSnapshot);
        }
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setDisabledComponents(@android.annotation.Nullable com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        this.mDisabledComponentsWatched = watchedArraySet;
        if (this.mDisabledComponentsWatched != null) {
            this.mDisabledComponentsWatched.registerObserver(this.mSnapshot);
        }
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setCeDataInode(long j) {
        this.mCeDataInode = j;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setDeDataInode(long j) {
        this.mDeDataInode = j;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setInstalled(boolean z) {
        setBoolean(1, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setStopped(boolean z) {
        setBoolean(2, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setNotLaunched(boolean z) {
        setBoolean(4, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setHidden(boolean z) {
        setBoolean(8, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setDistractionFlags(int i) {
        this.mDistractionFlags = i;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setInstantApp(boolean z) {
        setBoolean(16, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setVirtualPreload(boolean z) {
        setBoolean(32, z);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setEnabledState(int i) {
        this.mEnabledState = i;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setInstallReason(int i) {
        this.mInstallReason = i;
        com.android.internal.util.AnnotationValidations.validate(android.content.pm.PackageManager.InstallReason.class, (java.lang.annotation.Annotation) null, this.mInstallReason);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setUninstallReason(int i) {
        this.mUninstallReason = i;
        com.android.internal.util.AnnotationValidations.validate(android.content.pm.PackageManager.UninstallReason.class, (java.lang.annotation.Annotation) null, this.mUninstallReason);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setHarmfulAppWarning(@android.annotation.NonNull java.lang.String str) {
        this.mHarmfulAppWarning = str;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setLastDisableAppCaller(@android.annotation.NonNull java.lang.String str) {
        this.mLastDisableAppCaller = str;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setSharedLibraryOverlayPaths(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> arrayMap) {
        if (arrayMap == null) {
            return this;
        }
        if (this.mSharedLibraryOverlayPaths == null) {
            this.mSharedLibraryOverlayPaths = new com.android.server.utils.WatchedArrayMap<>();
            registerObserver(this.mSnapshot);
        }
        this.mSharedLibraryOverlayPaths.clear();
        this.mSharedLibraryOverlayPaths.putAll(arrayMap);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setSplashScreenTheme(@android.annotation.NonNull java.lang.String str) {
        this.mSplashScreenTheme = str;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setMinAspectRatio(int i) {
        this.mMinAspectRatio = i;
        com.android.internal.util.AnnotationValidations.validate(android.content.pm.PackageManager.UserMinAspectRatio.class, (java.lang.annotation.Annotation) null, this.mMinAspectRatio);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setSuspendParams(@android.annotation.NonNull android.util.ArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> arrayMap) {
        if (arrayMap == null) {
            return this;
        }
        if (this.mSuspendParams == null) {
            this.mSuspendParams = new com.android.server.utils.WatchedArrayMap<>();
            registerObserver(this.mSnapshot);
        }
        this.mSuspendParams.clear();
        this.mSuspendParams.putAll(arrayMap);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setComponentLabelIconOverrideMap(@android.annotation.NonNull android.util.ArrayMap<android.content.ComponentName, android.util.Pair<java.lang.String, java.lang.Integer>> arrayMap) {
        if (arrayMap == null) {
            return this;
        }
        if (this.mComponentLabelIconOverrideMap == null) {
            this.mComponentLabelIconOverrideMap = new com.android.server.utils.WatchedArrayMap<>();
            registerObserver(this.mSnapshot);
        }
        this.mComponentLabelIconOverrideMap.clear();
        this.mComponentLabelIconOverrideMap.putAll(arrayMap);
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setFirstInstallTimeMillis(long j) {
        this.mFirstInstallTimeMillis = j;
        onChanged();
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setArchiveState(@android.annotation.NonNull com.android.server.pm.pkg.ArchiveState archiveState) {
        this.mArchiveState = archiveState;
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.NonNull
    public java.util.Map<java.lang.String, android.content.pm.overlay.OverlayPaths> getSharedLibraryOverlayPaths() {
        return this.mSharedLibraryOverlayPaths == null ? java.util.Collections.emptyMap() : this.mSharedLibraryOverlayPaths;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setWatchable(@android.annotation.NonNull com.android.server.utils.Watchable watchable) {
        this.mWatchable = watchable;
        return this;
    }

    private boolean watchableEquals(com.android.server.utils.Watchable watchable) {
        return true;
    }

    private int watchableHashCode() {
        return 0;
    }

    private boolean snapshotEquals(com.android.server.utils.SnapshotCache<com.android.server.pm.pkg.PackageUserStateImpl> snapshotCache) {
        return true;
    }

    private int snapshotHashCode() {
        return 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstalled() {
        return getBoolean(1);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isStopped() {
        return getBoolean(2);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isNotLaunched() {
        return getBoolean(4);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isHidden() {
        return getBoolean(8);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstantApp() {
        return getBoolean(16);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isVirtualPreload() {
        return getBoolean(32);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isQuarantined() {
        if (!isSuspended()) {
            return false;
        }
        com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> watchedArrayMap = this.mSuspendParams;
        int size = watchedArrayMap.size();
        for (int i = 0; i < size; i++) {
            if (watchedArrayMap.valueAt(i).isQuarantined()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean dataExists() {
        return getCeDataInode() > 0 || getDeDataInode() > 0;
    }

    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getDisabledComponentsWatched() {
        return this.mDisabledComponentsWatched;
    }

    @android.annotation.Nullable
    public com.android.server.utils.WatchedArraySet<java.lang.String> getEnabledComponentsWatched() {
        return this.mEnabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getCeDataInode() {
        return this.mCeDataInode;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getDeDataInode() {
        return this.mDeDataInode;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getDistractionFlags() {
        return this.mDistractionFlags;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getEnabledState() {
        return this.mEnabledState;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getInstallReason() {
        return this.mInstallReason;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getUninstallReason() {
        return this.mUninstallReason;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getHarmfulAppWarning() {
        return this.mHarmfulAppWarning;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getLastDisableAppCaller() {
        return this.mLastDisableAppCaller;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public android.content.pm.overlay.OverlayPaths getOverlayPaths() {
        return this.mOverlayPaths;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public java.lang.String getSplashScreenTheme() {
        return this.mSplashScreenTheme;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getMinAspectRatio() {
        return this.mMinAspectRatio;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    @android.annotation.Nullable
    public com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> getSuspendParams() {
        return this.mSuspendParams;
    }

    @android.annotation.Nullable
    public com.android.server.utils.WatchedArrayMap<android.content.ComponentName, android.util.Pair<java.lang.String, java.lang.Integer>> getComponentLabelIconOverrideMap() {
        return this.mComponentLabelIconOverrideMap;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getFirstInstallTimeMillis() {
        return this.mFirstInstallTimeMillis;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    @android.annotation.Nullable
    public com.android.server.pm.pkg.ArchiveState getArchiveState() {
        return this.mArchiveState;
    }

    @android.annotation.NonNull
    public com.android.server.utils.SnapshotCache<com.android.server.pm.pkg.PackageUserStateImpl> getSnapshot() {
        return this.mSnapshot;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setBooleans(int i) {
        this.mBooleans = i;
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setDisabledComponentsWatched(@android.annotation.NonNull com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        this.mDisabledComponentsWatched = watchedArraySet;
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setEnabledComponentsWatched(@android.annotation.NonNull com.android.server.utils.WatchedArraySet<java.lang.String> watchedArraySet) {
        this.mEnabledComponentsWatched = watchedArraySet;
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setSharedLibraryOverlayPaths(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, android.content.pm.overlay.OverlayPaths> watchedArrayMap) {
        this.mSharedLibraryOverlayPaths = watchedArrayMap;
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setSuspendParams(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> watchedArrayMap) {
        this.mSuspendParams = watchedArrayMap;
        return this;
    }

    @android.annotation.NonNull
    public com.android.server.pm.pkg.PackageUserStateImpl setComponentLabelIconOverrideMap(@android.annotation.NonNull com.android.server.utils.WatchedArrayMap<android.content.ComponentName, android.util.Pair<java.lang.String, java.lang.Integer>> watchedArrayMap) {
        this.mComponentLabelIconOverrideMap = watchedArrayMap;
        return this;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.android.server.pm.pkg.PackageUserStateImpl packageUserStateImpl = (com.android.server.pm.pkg.PackageUserStateImpl) obj;
        if (this.mBooleans == packageUserStateImpl.mBooleans && java.util.Objects.equals(this.mDisabledComponentsWatched, packageUserStateImpl.mDisabledComponentsWatched) && java.util.Objects.equals(this.mEnabledComponentsWatched, packageUserStateImpl.mEnabledComponentsWatched) && this.mCeDataInode == packageUserStateImpl.mCeDataInode && this.mDeDataInode == packageUserStateImpl.mDeDataInode && this.mDistractionFlags == packageUserStateImpl.mDistractionFlags && this.mEnabledState == packageUserStateImpl.mEnabledState && this.mInstallReason == packageUserStateImpl.mInstallReason && this.mUninstallReason == packageUserStateImpl.mUninstallReason && java.util.Objects.equals(this.mHarmfulAppWarning, packageUserStateImpl.mHarmfulAppWarning) && java.util.Objects.equals(this.mLastDisableAppCaller, packageUserStateImpl.mLastDisableAppCaller) && java.util.Objects.equals(this.mOverlayPaths, packageUserStateImpl.mOverlayPaths) && java.util.Objects.equals(this.mSharedLibraryOverlayPaths, packageUserStateImpl.mSharedLibraryOverlayPaths) && java.util.Objects.equals(this.mSplashScreenTheme, packageUserStateImpl.mSplashScreenTheme) && this.mMinAspectRatio == packageUserStateImpl.mMinAspectRatio && java.util.Objects.equals(this.mSuspendParams, packageUserStateImpl.mSuspendParams) && java.util.Objects.equals(this.mComponentLabelIconOverrideMap, packageUserStateImpl.mComponentLabelIconOverrideMap) && this.mFirstInstallTimeMillis == packageUserStateImpl.mFirstInstallTimeMillis && watchableEquals(packageUserStateImpl.mWatchable) && java.util.Objects.equals(this.mArchiveState, packageUserStateImpl.mArchiveState) && snapshotEquals(packageUserStateImpl.mSnapshot)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((this.mBooleans + 31) * 31) + java.util.Objects.hashCode(this.mDisabledComponentsWatched)) * 31) + java.util.Objects.hashCode(this.mEnabledComponentsWatched)) * 31) + java.lang.Long.hashCode(this.mCeDataInode)) * 31) + java.lang.Long.hashCode(this.mDeDataInode)) * 31) + this.mDistractionFlags) * 31) + this.mEnabledState) * 31) + this.mInstallReason) * 31) + this.mUninstallReason) * 31) + java.util.Objects.hashCode(this.mHarmfulAppWarning)) * 31) + java.util.Objects.hashCode(this.mLastDisableAppCaller)) * 31) + java.util.Objects.hashCode(this.mOverlayPaths)) * 31) + java.util.Objects.hashCode(this.mSharedLibraryOverlayPaths)) * 31) + java.util.Objects.hashCode(this.mSplashScreenTheme)) * 31) + this.mMinAspectRatio) * 31) + java.util.Objects.hashCode(this.mSuspendParams)) * 31) + java.util.Objects.hashCode(this.mComponentLabelIconOverrideMap)) * 31) + java.lang.Long.hashCode(this.mFirstInstallTimeMillis)) * 31) + watchableHashCode()) * 31) + java.util.Objects.hashCode(this.mArchiveState)) * 31) + snapshotHashCode();
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
