package com.android.server.compat;

/* loaded from: classes.dex */
public class OverrideValidatorImpl extends com.android.internal.compat.IOverrideValidator.Stub {
    private com.android.internal.compat.AndroidBuildClassifier mAndroidBuildClassifier;
    private com.android.server.compat.CompatConfig mCompatConfig;
    private android.content.Context mContext;
    private boolean mForceNonDebuggableFinalBuild = false;

    private class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver() {
            super(new android.os.Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.compat.OverrideValidatorImpl.this.mForceNonDebuggableFinalBuild = android.provider.Settings.Global.getInt(com.android.server.compat.OverrideValidatorImpl.this.mContext.getContentResolver(), "force_non_debuggable_final_build_for_compat", 0) == 1;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    OverrideValidatorImpl(com.android.internal.compat.AndroidBuildClassifier androidBuildClassifier, android.content.Context context, com.android.server.compat.CompatConfig compatConfig) {
        this.mAndroidBuildClassifier = androidBuildClassifier;
        this.mContext = context;
        this.mCompatConfig = compatConfig;
    }

    com.android.internal.compat.OverrideAllowedState getOverrideAllowedStateForRecheck(long j, @android.annotation.NonNull java.lang.String str) {
        return getOverrideAllowedStateInternal(j, str, true);
    }

    public com.android.internal.compat.OverrideAllowedState getOverrideAllowedState(long j, java.lang.String str) {
        return getOverrideAllowedStateInternal(j, str, false);
    }

    private com.android.internal.compat.OverrideAllowedState getOverrideAllowedStateInternal(long j, java.lang.String str, boolean z) {
        if (this.mCompatConfig.isLoggingOnly(j)) {
            return new com.android.internal.compat.OverrideAllowedState(5, -1, -1);
        }
        boolean z2 = this.mAndroidBuildClassifier.isDebuggableBuild() && !this.mForceNonDebuggableFinalBuild;
        boolean z3 = this.mAndroidBuildClassifier.isFinalBuild() || this.mForceNonDebuggableFinalBuild;
        int maxTargetSdkForChangeIdOptIn = this.mCompatConfig.maxTargetSdkForChangeIdOptIn(j);
        boolean isDisabled = this.mCompatConfig.isDisabled(j);
        if (z2) {
            return new com.android.internal.compat.OverrideAllowedState(0, -1, -1);
        }
        if (maxTargetSdkForChangeIdOptIn >= this.mAndroidBuildClassifier.platformTargetSdk()) {
            return new com.android.internal.compat.OverrideAllowedState(6, -1, maxTargetSdkForChangeIdOptIn);
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            throw new java.lang.IllegalStateException("No PackageManager!");
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 4194304);
            if (this.mCompatConfig.isOverridable(j) && (z || this.mContext.checkCallingOrSelfPermission("android.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD") == 0)) {
                return new com.android.internal.compat.OverrideAllowedState(0, -1, -1);
            }
            int i = applicationInfo.targetSdkVersion;
            if ((applicationInfo.flags & 2) == 0) {
                return new com.android.internal.compat.OverrideAllowedState(1, -1, -1);
            }
            if (!z3) {
                return new com.android.internal.compat.OverrideAllowedState(0, i, maxTargetSdkForChangeIdOptIn);
            }
            if (maxTargetSdkForChangeIdOptIn == -1 && !isDisabled) {
                return new com.android.internal.compat.OverrideAllowedState(2, i, maxTargetSdkForChangeIdOptIn);
            }
            if (isDisabled || i <= maxTargetSdkForChangeIdOptIn) {
                return new com.android.internal.compat.OverrideAllowedState(0, i, maxTargetSdkForChangeIdOptIn);
            }
            return new com.android.internal.compat.OverrideAllowedState(3, i, maxTargetSdkForChangeIdOptIn);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return new com.android.internal.compat.OverrideAllowedState(4, -1, -1);
        }
    }

    void registerContentObserver() {
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("force_non_debuggable_final_build_for_compat"), false, new com.android.server.compat.OverrideValidatorImpl.SettingsObserver());
    }

    void forceNonDebuggableFinalForTest(boolean z) {
        this.mForceNonDebuggableFinalBuild = z;
    }
}
