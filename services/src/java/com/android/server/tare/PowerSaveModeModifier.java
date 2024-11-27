package com.android.server.tare;

/* loaded from: classes2.dex */
class PowerSaveModeModifier extends com.android.server.tare.Modifier {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.PowerSaveModeModifier.class.getSimpleName();
    private final com.android.server.tare.InternalResourceService mIrs;
    private final com.android.server.tare.PowerSaveModeModifier.PowerSaveModeTracker mPowerSaveModeTracker = new com.android.server.tare.PowerSaveModeModifier.PowerSaveModeTracker();

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    PowerSaveModeModifier(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
    }

    @Override // com.android.server.tare.Modifier
    public void setup() {
        this.mPowerSaveModeTracker.startTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    public void tearDown() {
        this.mPowerSaveModeTracker.stopTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    long getModifiedCostToProduce(long j) {
        if (this.mPowerSaveModeTracker.mPowerSaveModeEnabled) {
            return (long) (j * 1.5d);
        }
        if (this.mPowerSaveModeTracker.mPowerSaveModeEnabled) {
            return (long) (j * 1.25d);
        }
        return j;
    }

    @Override // com.android.server.tare.Modifier
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("power save=");
        indentingPrintWriter.println(this.mPowerSaveModeTracker.mPowerSaveModeEnabled);
    }

    private final class PowerSaveModeTracker extends android.content.BroadcastReceiver {
        private boolean mIsSetup;
        private final android.os.PowerManager mPowerManager;
        private volatile boolean mPowerSaveModeEnabled;

        private PowerSaveModeTracker() {
            this.mIsSetup = false;
            this.mPowerManager = (android.os.PowerManager) com.android.server.tare.PowerSaveModeModifier.this.mIrs.getContext().getSystemService(android.os.PowerManager.class);
        }

        public void startTracking(@android.annotation.NonNull android.content.Context context) {
            if (this.mIsSetup) {
                return;
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            context.registerReceiver(this, intentFilter);
            this.mPowerSaveModeEnabled = this.mPowerManager.isPowerSaveMode();
            this.mIsSetup = true;
        }

        public void stopTracking(@android.annotation.NonNull android.content.Context context) {
            if (!this.mIsSetup) {
                return;
            }
            context.unregisterReceiver(this);
            this.mIsSetup = false;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(intent.getAction())) {
                boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
                if (com.android.server.tare.PowerSaveModeModifier.DEBUG) {
                    android.util.Slog.d(com.android.server.tare.PowerSaveModeModifier.TAG, "Power save mode changed to " + isPowerSaveMode + ", fired @ " + android.os.SystemClock.elapsedRealtime());
                }
                if (this.mPowerSaveModeEnabled != isPowerSaveMode) {
                    this.mPowerSaveModeEnabled = isPowerSaveMode;
                    com.android.server.tare.PowerSaveModeModifier.this.mIrs.onDeviceStateChanged();
                }
            }
        }
    }
}
