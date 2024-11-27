package com.android.server.power.batterysaver;

/* loaded from: classes2.dex */
public class BatterySavingStats {
    private static final boolean DEBUG = false;
    private static final int STATE_NOT_INITIALIZED = -1;
    private static final java.lang.String TAG = "BatterySavingStats";
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentState = -1;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArray<com.android.server.power.batterysaver.BatterySavingStats.Stat> mStats = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mBatterySaverEnabledCount = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastBatterySaverEnabledTime = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastBatterySaverDisabledTime = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mAdaptiveBatterySaverEnabledCount = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAdaptiveBatterySaverEnabledTime = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastAdaptiveBatterySaverDisabledTime = 0;
    private android.os.BatteryManagerInternal mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);

    interface BatterySaverState {
        public static final int ADAPTIVE = 2;
        public static final int BITS = 2;
        public static final int MASK = 3;
        public static final int OFF = 0;
        public static final int ON = 1;
        public static final int SHIFT = 0;

        static int fromIndex(int i) {
            return (i >> 0) & 3;
        }
    }

    interface InteractiveState {
        public static final int BITS = 1;
        public static final int INTERACTIVE = 1;
        public static final int MASK = 1;
        public static final int NON_INTERACTIVE = 0;
        public static final int SHIFT = 2;

        static int fromIndex(int i) {
            return (i >> 2) & 1;
        }
    }

    interface DozeState {
        public static final int BITS = 2;
        public static final int DEEP = 2;
        public static final int LIGHT = 1;
        public static final int MASK = 3;
        public static final int NOT_DOZING = 0;
        public static final int SHIFT = 3;

        static int fromIndex(int i) {
            return (i >> 3) & 3;
        }
    }

    interface PlugState {
        public static final int BITS = 1;
        public static final int MASK = 1;
        public static final int PLUGGED = 1;
        public static final int SHIFT = 5;
        public static final int UNPLUGGED = 0;

        static int fromIndex(int i) {
            return (i >> 5) & 1;
        }
    }

    static class Stat {
        public int endBatteryLevel;
        public int endBatteryPercent;
        public long endTime;
        public int startBatteryLevel;
        public int startBatteryPercent;
        public long startTime;
        public int totalBatteryDrain;
        public int totalBatteryDrainPercent;
        public long totalTimeMillis;

        Stat() {
        }

        public long totalMinutes() {
            return this.totalTimeMillis / 60000;
        }

        public double drainPerHour() {
            if (this.totalTimeMillis == 0) {
                return 0.0d;
            }
            return this.totalBatteryDrain / (this.totalTimeMillis / 3600000.0d);
        }

        public double drainPercentPerHour() {
            if (this.totalTimeMillis == 0) {
                return 0.0d;
            }
            return this.totalBatteryDrainPercent / (this.totalTimeMillis / 3600000.0d);
        }

        @com.android.internal.annotations.VisibleForTesting
        java.lang.String toStringForTest() {
            return "{" + totalMinutes() + "m," + this.totalBatteryDrain + "," + java.lang.String.format("%.2f", java.lang.Double.valueOf(drainPerHour())) + "uA/H," + java.lang.String.format("%.2f", java.lang.Double.valueOf(drainPercentPerHour())) + "%}";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public BatterySavingStats(java.lang.Object obj) {
        this.mLock = obj;
    }

    private android.os.BatteryManagerInternal getBatteryManagerInternal() {
        if (this.mBatteryManagerInternal == null) {
            this.mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);
            if (this.mBatteryManagerInternal == null) {
                android.util.Slog.wtf(TAG, "BatteryManagerInternal not initialized");
            }
        }
        return this.mBatteryManagerInternal;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int statesToIndex(int i, int i2, int i3, int i4) {
        return (i & 3) | ((i2 & 1) << 2) | ((i3 & 3) << 3) | ((i4 & 1) << 5);
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String stateToString(int i) {
        switch (i) {
            case -1:
                return "NotInitialized";
            default:
                return "BS=" + com.android.server.power.batterysaver.BatterySavingStats.BatterySaverState.fromIndex(i) + ",I=" + com.android.server.power.batterysaver.BatterySavingStats.InteractiveState.fromIndex(i) + ",D=" + com.android.server.power.batterysaver.BatterySavingStats.DozeState.fromIndex(i) + ",P=" + com.android.server.power.batterysaver.BatterySavingStats.PlugState.fromIndex(i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.power.batterysaver.BatterySavingStats.Stat getStat(int i) {
        com.android.server.power.batterysaver.BatterySavingStats.Stat stat;
        synchronized (this.mLock) {
            try {
                stat = this.mStats.get(i);
                if (stat == null) {
                    stat = new com.android.server.power.batterysaver.BatterySavingStats.Stat();
                    this.mStats.put(i, stat);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return stat;
    }

    private com.android.server.power.batterysaver.BatterySavingStats.Stat getStat(int i, int i2, int i3, int i4) {
        return getStat(statesToIndex(i, i2, i3, i4));
    }

    @com.android.internal.annotations.VisibleForTesting
    long injectCurrentTime() {
        return android.os.SystemClock.elapsedRealtime();
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectBatteryLevel() {
        android.os.BatteryManagerInternal batteryManagerInternal = getBatteryManagerInternal();
        if (batteryManagerInternal == null) {
            return 0;
        }
        return batteryManagerInternal.getBatteryChargeCounter();
    }

    @com.android.internal.annotations.VisibleForTesting
    int injectBatteryPercent() {
        android.os.BatteryManagerInternal batteryManagerInternal = getBatteryManagerInternal();
        if (batteryManagerInternal == null) {
            return 0;
        }
        return batteryManagerInternal.getBatteryLevel();
    }

    void transitionState(int i, int i2, int i3, int i4) {
        synchronized (this.mLock) {
            transitionStateLocked(statesToIndex(i, i2, i3, i4));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void transitionStateLocked(int i) {
        if (this.mCurrentState == i) {
            return;
        }
        long injectCurrentTime = injectCurrentTime();
        int injectBatteryLevel = injectBatteryLevel();
        int injectBatteryPercent = injectBatteryPercent();
        int fromIndex = this.mCurrentState < 0 ? 0 : com.android.server.power.batterysaver.BatterySavingStats.BatterySaverState.fromIndex(this.mCurrentState);
        int fromIndex2 = i >= 0 ? com.android.server.power.batterysaver.BatterySavingStats.BatterySaverState.fromIndex(i) : 0;
        if (fromIndex != fromIndex2) {
            switch (fromIndex2) {
                case 0:
                    if (fromIndex == 1) {
                        this.mLastBatterySaverDisabledTime = injectCurrentTime;
                        break;
                    } else {
                        this.mLastAdaptiveBatterySaverDisabledTime = injectCurrentTime;
                        break;
                    }
                case 1:
                    this.mBatterySaverEnabledCount++;
                    this.mLastBatterySaverEnabledTime = injectCurrentTime;
                    if (fromIndex == 2) {
                        this.mLastAdaptiveBatterySaverDisabledTime = injectCurrentTime;
                        break;
                    }
                    break;
                case 2:
                    this.mAdaptiveBatterySaverEnabledCount++;
                    this.mLastAdaptiveBatterySaverEnabledTime = injectCurrentTime;
                    if (fromIndex == 1) {
                        this.mLastBatterySaverDisabledTime = injectCurrentTime;
                        break;
                    }
                    break;
            }
        }
        endLastStateLocked(injectCurrentTime, injectBatteryLevel, injectBatteryPercent);
        startNewStateLocked(i, injectCurrentTime, injectBatteryLevel, injectBatteryPercent);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void endLastStateLocked(long j, int i, int i2) {
        if (this.mCurrentState >= 0) {
            com.android.server.power.batterysaver.BatterySavingStats.Stat stat = getStat(this.mCurrentState);
            stat.endBatteryLevel = i;
            stat.endBatteryPercent = i2;
            stat.endTime = j;
            long j2 = stat.endTime - stat.startTime;
            int i3 = stat.startBatteryLevel - stat.endBatteryLevel;
            int i4 = stat.startBatteryPercent - stat.endBatteryPercent;
            stat.totalTimeMillis += j2;
            stat.totalBatteryDrain += i3;
            stat.totalBatteryDrainPercent += i4;
            com.android.server.EventLogTags.writeBatterySavingStats(com.android.server.power.batterysaver.BatterySavingStats.BatterySaverState.fromIndex(this.mCurrentState), com.android.server.power.batterysaver.BatterySavingStats.InteractiveState.fromIndex(this.mCurrentState), com.android.server.power.batterysaver.BatterySavingStats.DozeState.fromIndex(this.mCurrentState), j2, i3, i4, stat.totalTimeMillis, stat.totalBatteryDrain, stat.totalBatteryDrainPercent);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startNewStateLocked(int i, long j, int i2, int i3) {
        this.mCurrentState = i;
        if (this.mCurrentState < 0) {
            return;
        }
        com.android.server.power.batterysaver.BatterySavingStats.Stat stat = getStat(this.mCurrentState);
        stat.startBatteryLevel = i2;
        stat.startBatteryPercent = i3;
        stat.startTime = j;
        stat.endTime = 0L;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Battery saving stats:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                long currentTimeMillis = java.lang.System.currentTimeMillis();
                long injectCurrentTime = injectCurrentTime();
                java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                indentingPrintWriter.print("Battery Saver is currently: ");
                switch (com.android.server.power.batterysaver.BatterySavingStats.BatterySaverState.fromIndex(this.mCurrentState)) {
                    case 0:
                        indentingPrintWriter.println("OFF");
                        break;
                    case 1:
                        indentingPrintWriter.println("ON");
                        break;
                    case 2:
                        indentingPrintWriter.println("ADAPTIVE");
                        break;
                }
                indentingPrintWriter.increaseIndent();
                if (this.mLastBatterySaverEnabledTime > 0) {
                    indentingPrintWriter.print("Last ON time: ");
                    indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date((currentTimeMillis - injectCurrentTime) + this.mLastBatterySaverEnabledTime)));
                    indentingPrintWriter.print(" ");
                    android.util.TimeUtils.formatDuration(this.mLastBatterySaverEnabledTime, injectCurrentTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.mLastBatterySaverDisabledTime > 0) {
                    indentingPrintWriter.print("Last OFF time: ");
                    indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date((currentTimeMillis - injectCurrentTime) + this.mLastBatterySaverDisabledTime)));
                    indentingPrintWriter.print(" ");
                    android.util.TimeUtils.formatDuration(this.mLastBatterySaverDisabledTime, injectCurrentTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Times full enabled: ");
                indentingPrintWriter.println(this.mBatterySaverEnabledCount);
                if (this.mLastAdaptiveBatterySaverEnabledTime > 0) {
                    indentingPrintWriter.print("Last ADAPTIVE ON time: ");
                    indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date((currentTimeMillis - injectCurrentTime) + this.mLastAdaptiveBatterySaverEnabledTime)));
                    indentingPrintWriter.print(" ");
                    android.util.TimeUtils.formatDuration(this.mLastAdaptiveBatterySaverEnabledTime, injectCurrentTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.mLastAdaptiveBatterySaverDisabledTime > 0) {
                    indentingPrintWriter.print("Last ADAPTIVE OFF time: ");
                    indentingPrintWriter.print(simpleDateFormat.format(new java.util.Date((currentTimeMillis - injectCurrentTime) + this.mLastAdaptiveBatterySaverDisabledTime)));
                    indentingPrintWriter.print(" ");
                    android.util.TimeUtils.formatDuration(this.mLastAdaptiveBatterySaverDisabledTime, injectCurrentTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Times adaptive enabled: ");
                indentingPrintWriter.println(this.mAdaptiveBatterySaverEnabledCount);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println("Drain stats:");
                indentingPrintWriter.println("                   Battery saver OFF                          ON");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 0, "NonDoze");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 0, "       ");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 2, "Deep   ");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 2, "       ");
                dumpLineLocked(indentingPrintWriter, 0, "NonIntr", 1, "Light  ");
                dumpLineLocked(indentingPrintWriter, 1, "   Intr", 1, "       ");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    private void dumpLineLocked(android.util.IndentingPrintWriter indentingPrintWriter, int i, java.lang.String str, int i2, java.lang.String str2) {
        indentingPrintWriter.print(str2);
        indentingPrintWriter.print(" ");
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(": ");
        com.android.server.power.batterysaver.BatterySavingStats.Stat stat = getStat(0, i, i2, 0);
        com.android.server.power.batterysaver.BatterySavingStats.Stat stat2 = getStat(1, i, i2, 0);
        indentingPrintWriter.println(java.lang.String.format("%6dm %6dmAh(%3d%%) %8.1fmAh/h     %6dm %6dmAh(%3d%%) %8.1fmAh/h", java.lang.Long.valueOf(stat.totalMinutes()), java.lang.Integer.valueOf(stat.totalBatteryDrain / 1000), java.lang.Integer.valueOf(stat.totalBatteryDrainPercent), java.lang.Double.valueOf(stat.drainPerHour() / 1000.0d), java.lang.Long.valueOf(stat2.totalMinutes()), java.lang.Integer.valueOf(stat2.totalBatteryDrain / 1000), java.lang.Integer.valueOf(stat2.totalBatteryDrainPercent), java.lang.Double.valueOf(stat2.drainPerHour() / 1000.0d)));
    }
}
