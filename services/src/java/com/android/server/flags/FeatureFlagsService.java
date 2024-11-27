package com.android.server.flags;

/* loaded from: classes.dex */
public class FeatureFlagsService extends com.android.server.SystemService {
    static final java.lang.String TAG = "FeatureFlagsService";
    private final com.android.server.flags.FlagOverrideStore mFlagStore;
    private final com.android.server.flags.FlagsShellCommand mShellCommand;

    public FeatureFlagsService(android.content.Context context) {
        super(context);
        this.mFlagStore = new com.android.server.flags.FlagOverrideStore(new com.android.server.flags.GlobalSettingsProxy(context.getContentResolver()));
        this.mShellCommand = new com.android.server.flags.FlagsShellCommand(this.mFlagStore);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.util.Slog.d(TAG, "Started Feature Flag Service");
        android.flags.IFeatureFlags.Stub featureFlagsBinder = new com.android.server.flags.FeatureFlagsBinder(this.mFlagStore, this.mShellCommand, new com.android.server.flags.FeatureFlagsService.PermissionsChecker(getContext()));
        publishBinderService("feature_flags", featureFlagsBinder);
        publishLocalService(android.flags.FeatureFlags.class, new android.flags.FeatureFlags(featureFlagsBinder));
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 500) {
            android.flags.FeatureFlags.getInstance().sync();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class PermissionsChecker {
        private final android.content.Context mContext;

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public PermissionsChecker(android.content.Context context) {
            this.mContext = context;
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void assertSyncPermission() {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.SYNC_FLAGS") != 0) {
                throw new java.lang.SecurityException("Non-core flag queried. Requires SYNC_FLAGS permission!");
            }
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void assertWritePermission() {
            if (this.mContext.checkCallingPermission("android.permission.WRITE_FLAGS") != 0) {
                throw new java.lang.SecurityException("Requires WRITE_FLAGS permission!");
            }
        }
    }
}
