package com.android.server.tare;

/* loaded from: classes2.dex */
class ChargingModifier extends com.android.server.tare.Modifier {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.ChargingModifier.class.getSimpleName();
    private final com.android.server.tare.ChargingModifier.ChargingTracker mChargingTracker = new com.android.server.tare.ChargingModifier.ChargingTracker();
    private final com.android.server.tare.InternalResourceService mIrs;

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    ChargingModifier(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
    }

    @Override // com.android.server.tare.Modifier
    public void setup() {
        this.mChargingTracker.startTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    public void tearDown() {
        this.mChargingTracker.stopTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    long getModifiedCostToProduce(long j) {
        return modifyValue(j);
    }

    @Override // com.android.server.tare.Modifier
    long getModifiedPrice(long j) {
        return modifyValue(j);
    }

    private long modifyValue(long j) {
        if (this.mChargingTracker.mCharging) {
            return 0L;
        }
        return j;
    }

    @Override // com.android.server.tare.Modifier
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("charging=");
        indentingPrintWriter.println(this.mChargingTracker.mCharging);
    }

    private final class ChargingTracker extends android.content.BroadcastReceiver {
        private volatile boolean mCharging;
        private boolean mIsSetup;

        private ChargingTracker() {
            this.mIsSetup = false;
        }

        public void startTracking(@android.annotation.NonNull android.content.Context context) {
            if (this.mIsSetup) {
                return;
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.os.action.CHARGING");
            intentFilter.addAction("android.os.action.DISCHARGING");
            context.registerReceiver(this, intentFilter);
            this.mCharging = ((android.os.BatteryManager) context.getSystemService(android.os.BatteryManager.class)).isCharging();
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
            java.lang.String action = intent.getAction();
            if ("android.os.action.CHARGING".equals(action)) {
                if (com.android.server.tare.ChargingModifier.DEBUG) {
                    android.util.Slog.d(com.android.server.tare.ChargingModifier.TAG, "Received charging intent, fired @ " + android.os.SystemClock.elapsedRealtime());
                }
                if (!this.mCharging) {
                    this.mCharging = true;
                    com.android.server.tare.ChargingModifier.this.mIrs.onDeviceStateChanged();
                    return;
                }
                return;
            }
            if ("android.os.action.DISCHARGING".equals(action)) {
                if (com.android.server.tare.ChargingModifier.DEBUG) {
                    android.util.Slog.d(com.android.server.tare.ChargingModifier.TAG, "Disconnected from power.");
                }
                if (this.mCharging) {
                    this.mCharging = false;
                    com.android.server.tare.ChargingModifier.this.mIrs.onDeviceStateChanged();
                }
            }
        }
    }
}
