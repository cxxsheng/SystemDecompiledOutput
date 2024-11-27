package com.android.server.powerstats;

/* loaded from: classes2.dex */
public abstract class PowerStatsLogTrigger {
    private static final java.lang.String TAG = com.android.server.powerstats.PowerStatsLogTrigger.class.getSimpleName();
    protected android.content.Context mContext;
    private com.android.server.powerstats.PowerStatsLogger mPowerStatsLogger;

    protected void logPowerStatsData(int i) {
        android.os.Message.obtain(this.mPowerStatsLogger, i).sendToTarget();
    }

    public PowerStatsLogTrigger(android.content.Context context, com.android.server.powerstats.PowerStatsLogger powerStatsLogger) {
        this.mContext = context;
        this.mPowerStatsLogger = powerStatsLogger;
    }
}
