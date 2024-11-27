package com.android.server;

/* loaded from: classes.dex */
public class CachedDeviceStateService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "CachedDeviceStateService";
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final com.android.internal.os.CachedDeviceState mDeviceState;

    public CachedDeviceStateService(android.content.Context context) {
        super(context);
        this.mDeviceState = new com.android.internal.os.CachedDeviceState();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.CachedDeviceStateService.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1538406691:
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1454123155:
                        if (action.equals("android.intent.action.SCREEN_ON")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        com.android.server.CachedDeviceStateService.this.mDeviceState.setCharging(intent.getIntExtra("plugged", 0) != 0);
                        break;
                    case 1:
                        com.android.server.CachedDeviceStateService.this.mDeviceState.setScreenInteractive(true);
                        break;
                    case 2:
                        com.android.server.CachedDeviceStateService.this.mDeviceState.setScreenInteractive(false);
                        break;
                }
            }
        };
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishLocalService(com.android.internal.os.CachedDeviceState.Readonly.class, this.mDeviceState.getReadonlyClient());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (500 == i) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.setPriority(1000);
            getContext().registerReceiver(this.mBroadcastReceiver, intentFilter);
            this.mDeviceState.setCharging(queryIsCharging());
            this.mDeviceState.setScreenInteractive(queryScreenInteractive(getContext()));
        }
    }

    private boolean queryIsCharging() {
        android.os.BatteryManagerInternal batteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);
        if (batteryManagerInternal != null) {
            return batteryManagerInternal.getPlugType() != 0;
        }
        android.util.Slog.wtf(TAG, "BatteryManager null while starting CachedDeviceStateService");
        return true;
    }

    private boolean queryScreenInteractive(android.content.Context context) {
        android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        if (powerManager == null) {
            android.util.Slog.wtf(TAG, "PowerManager null while starting CachedDeviceStateService");
            return false;
        }
        return powerManager.isInteractive();
    }
}
