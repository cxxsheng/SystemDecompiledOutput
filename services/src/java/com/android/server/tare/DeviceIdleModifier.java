package com.android.server.tare;

/* loaded from: classes2.dex */
class DeviceIdleModifier extends com.android.server.tare.Modifier {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.DeviceIdleModifier.class.getSimpleName();
    private final com.android.server.tare.DeviceIdleModifier.DeviceIdleTracker mDeviceIdleTracker = new com.android.server.tare.DeviceIdleModifier.DeviceIdleTracker();
    private final com.android.server.tare.InternalResourceService mIrs;
    private final android.os.PowerManager mPowerManager;

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    DeviceIdleModifier(@android.annotation.NonNull com.android.server.tare.InternalResourceService internalResourceService) {
        this.mIrs = internalResourceService;
        this.mPowerManager = (android.os.PowerManager) internalResourceService.getContext().getSystemService(android.os.PowerManager.class);
    }

    @Override // com.android.server.tare.Modifier
    public void setup() {
        this.mDeviceIdleTracker.startTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    public void tearDown() {
        this.mDeviceIdleTracker.stopTracking(this.mIrs.getContext());
    }

    @Override // com.android.server.tare.Modifier
    long getModifiedCostToProduce(long j) {
        if (this.mDeviceIdleTracker.mDeviceIdle) {
            return (long) (j * 1.2d);
        }
        if (this.mDeviceIdleTracker.mDeviceLightIdle) {
            return (long) (j * 1.1d);
        }
        return j;
    }

    @Override // com.android.server.tare.Modifier
    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("idle=");
        indentingPrintWriter.println(this.mDeviceIdleTracker.mDeviceIdle);
        indentingPrintWriter.print("lightIdle=");
        indentingPrintWriter.println(this.mDeviceIdleTracker.mDeviceLightIdle);
    }

    private final class DeviceIdleTracker extends android.content.BroadcastReceiver {
        private volatile boolean mDeviceIdle;
        private volatile boolean mDeviceLightIdle;
        private boolean mIsSetup = false;

        DeviceIdleTracker() {
        }

        void startTracking(@android.annotation.NonNull android.content.Context context) {
            if (this.mIsSetup) {
                return;
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
            intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
            context.registerReceiver(this, intentFilter);
            this.mDeviceIdle = com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isDeviceIdleMode();
            this.mDeviceLightIdle = com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isLightDeviceIdleMode();
            this.mIsSetup = true;
        }

        void stopTracking(@android.annotation.NonNull android.content.Context context) {
            if (!this.mIsSetup) {
                return;
            }
            context.unregisterReceiver(this);
            this.mIsSetup = false;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if ("android.os.action.DEVICE_IDLE_MODE_CHANGED".equals(action)) {
                if (this.mDeviceIdle != com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isDeviceIdleMode()) {
                    this.mDeviceIdle = com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isDeviceIdleMode();
                    com.android.server.tare.DeviceIdleModifier.this.mIrs.onDeviceStateChanged();
                    return;
                }
                return;
            }
            if ("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED".equals(action) && this.mDeviceLightIdle != com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isLightDeviceIdleMode()) {
                this.mDeviceLightIdle = com.android.server.tare.DeviceIdleModifier.this.mPowerManager.isLightDeviceIdleMode();
                com.android.server.tare.DeviceIdleModifier.this.mIrs.onDeviceStateChanged();
            }
        }
    }
}
