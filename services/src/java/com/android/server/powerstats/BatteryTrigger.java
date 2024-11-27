package com.android.server.powerstats;

/* loaded from: classes2.dex */
public final class BatteryTrigger extends com.android.server.powerstats.PowerStatsLogTrigger {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = com.android.server.powerstats.BatteryTrigger.class.getSimpleName();
    private int mBatteryLevel;
    private final android.content.BroadcastReceiver mBatteryLevelReceiver;

    public BatteryTrigger(android.content.Context context, com.android.server.powerstats.PowerStatsLogger powerStatsLogger, boolean z) {
        super(context, powerStatsLogger);
        this.mBatteryLevel = 0;
        this.mBatteryLevelReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.powerstats.BatteryTrigger.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -1538406691:
                        if (action.equals("android.intent.action.BATTERY_CHANGED")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        int intExtra = intent.getIntExtra("level", 0);
                        if (intExtra < com.android.server.powerstats.BatteryTrigger.this.mBatteryLevel) {
                            com.android.server.powerstats.BatteryTrigger.this.logPowerStatsData(0);
                        }
                        com.android.server.powerstats.BatteryTrigger.this.mBatteryLevel = intExtra;
                        break;
                }
            }
        };
        if (z) {
            android.content.Intent registerReceiver = this.mContext.registerReceiver(this.mBatteryLevelReceiver, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (registerReceiver != null) {
                this.mBatteryLevel = registerReceiver.getIntExtra("level", 0);
            }
        }
    }
}
