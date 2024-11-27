package com.android.server.powerstats;

/* loaded from: classes2.dex */
public final class TimerTrigger extends com.android.server.powerstats.PowerStatsLogTrigger {
    private static final boolean DEBUG = false;
    private static final long LOG_PERIOD_MS_HIGH_FREQUENCY = 120000;
    private static final long LOG_PERIOD_MS_LOW_FREQUENCY = 3600000;
    private static final java.lang.String TAG = com.android.server.powerstats.TimerTrigger.class.getSimpleName();
    private final android.os.Handler mHandler;
    private java.lang.Runnable mLogDataHighFrequency;
    private java.lang.Runnable mLogDataLowFrequency;

    public TimerTrigger(android.content.Context context, com.android.server.powerstats.PowerStatsLogger powerStatsLogger, boolean z) {
        super(context, powerStatsLogger);
        this.mLogDataLowFrequency = new java.lang.Runnable() { // from class: com.android.server.powerstats.TimerTrigger.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.powerstats.TimerTrigger.this.mHandler.postDelayed(com.android.server.powerstats.TimerTrigger.this.mLogDataLowFrequency, 3600000L);
                com.android.server.powerstats.TimerTrigger.this.logPowerStatsData(1);
            }
        };
        this.mLogDataHighFrequency = new java.lang.Runnable() { // from class: com.android.server.powerstats.TimerTrigger.2
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.powerstats.TimerTrigger.this.mHandler.postDelayed(com.android.server.powerstats.TimerTrigger.this.mLogDataHighFrequency, 120000L);
                com.android.server.powerstats.TimerTrigger.this.logPowerStatsData(2);
            }
        };
        this.mHandler = this.mContext.getMainThreadHandler();
        if (z) {
            this.mLogDataLowFrequency.run();
            this.mLogDataHighFrequency.run();
        }
    }
}
