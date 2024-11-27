package com.android.server.power.feature;

/* loaded from: classes2.dex */
public class PowerManagerFlags {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PowerManagerFlags";
    private final com.android.server.power.feature.PowerManagerFlags.FlagState mEarlyScreenTimeoutDetectorFlagState = new com.android.server.power.feature.PowerManagerFlags.FlagState("com.android.server.power.feature.flags.enable_early_screen_timeout_detector", new java.util.function.Supplier() { // from class: com.android.server.power.feature.PowerManagerFlags$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return java.lang.Boolean.valueOf(com.android.server.power.feature.flags.Flags.enableEarlyScreenTimeoutDetector());
        }
    });

    public boolean isEarlyScreenTimeoutDetectorEnabled() {
        return this.mEarlyScreenTimeoutDetectorFlagState.isEnabled();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("PowerManagerFlags:");
        printWriter.println(" " + this.mEarlyScreenTimeoutDetectorFlagState);
    }

    private static class FlagState {
        private boolean mEnabled;
        private boolean mEnabledSet;
        private final java.util.function.Supplier<java.lang.Boolean> mFlagFunction;
        private final java.lang.String mName;

        private FlagState(java.lang.String str, java.util.function.Supplier<java.lang.Boolean> supplier) {
            this.mName = str;
            this.mFlagFunction = supplier;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabled() {
            if (this.mEnabledSet) {
                return this.mEnabled;
            }
            this.mEnabled = this.mFlagFunction.get().booleanValue();
            this.mEnabledSet = true;
            return this.mEnabled;
        }

        public java.lang.String toString() {
            int length = this.mName.length();
            return android.text.TextUtils.substring(this.mName, 39, length) + ": " + android.text.TextUtils.formatSimple("%" + (91 - length) + "s%s", new java.lang.Object[]{" ", java.lang.Boolean.valueOf(isEnabled())}) + " (def:" + this.mFlagFunction.get() + ")";
        }
    }
}
