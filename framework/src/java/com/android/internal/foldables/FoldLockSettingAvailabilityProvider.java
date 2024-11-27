package com.android.internal.foldables;

/* loaded from: classes4.dex */
public class FoldLockSettingAvailabilityProvider {
    private static final java.lang.String TAG = "FoldLockSettingAvailabilityProvider";
    private final boolean mFoldLockBehaviorResourceValue;
    private final java.util.function.Supplier<java.lang.Boolean> mFoldLockSettingEnabled = new java.util.function.Supplier() { // from class: com.android.internal.foldables.FoldLockSettingAvailabilityProvider$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return java.lang.Boolean.valueOf(com.android.internal.foldables.flags.Flags.foldLockSettingEnabled());
        }
    };

    public FoldLockSettingAvailabilityProvider(android.content.res.Resources resources) {
        this.mFoldLockBehaviorResourceValue = resources.getBoolean(com.android.internal.R.bool.config_fold_lock_behavior);
    }

    public boolean isFoldLockBehaviorAvailable() {
        return this.mFoldLockBehaviorResourceValue && flagOrSystemProperty();
    }

    private boolean flagOrSystemProperty() {
        if ((android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) && android.sysprop.FoldLockBehaviorProperties.fold_lock_setting_enabled().orElse(false).booleanValue()) {
            return true;
        }
        try {
            return this.mFoldLockSettingEnabled.get().booleanValue();
        } catch (java.lang.Throwable th) {
            android.util.Slog.i(TAG, "Flags not ready yet. Return false for com.android.internal.foldables.flags.fold_lock_setting_enabled", th);
            return false;
        }
    }
}
