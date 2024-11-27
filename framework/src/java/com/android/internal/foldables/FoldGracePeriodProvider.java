package com.android.internal.foldables;

/* loaded from: classes4.dex */
public class FoldGracePeriodProvider {
    private static final java.lang.String TAG = "FoldGracePeriodProvider";
    private final java.util.function.Supplier<java.lang.Boolean> mFoldGracePeriodEnabled = new java.util.function.Supplier() { // from class: com.android.internal.foldables.FoldGracePeriodProvider$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return java.lang.Boolean.valueOf(com.android.internal.foldables.flags.Flags.foldGracePeriodEnabled());
        }
    };

    public boolean isEnabled() {
        if ((android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) && android.sysprop.FoldLockBehaviorProperties.fold_grace_period_enabled().orElse(false).booleanValue()) {
            return true;
        }
        try {
            return this.mFoldGracePeriodEnabled.get().booleanValue();
        } catch (java.lang.Throwable th) {
            android.util.Slog.i(TAG, "Flags not ready yet. Return false for com.android.internal.foldables.flags.fold_grace_period_enabled", th);
            return false;
        }
    }
}
