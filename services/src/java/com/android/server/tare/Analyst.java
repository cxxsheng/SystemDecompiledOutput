package com.android.server.tare;

/* loaded from: classes2.dex */
public class Analyst {
    private static final boolean DEBUG;

    @com.android.internal.annotations.VisibleForTesting
    static final long MIN_REPORT_DURATION_FOR_RESET = 86400000;
    private static final int NUM_PERIODS_TO_RETAIN = 8;
    private static final java.lang.String TAG = "TARE-" + com.android.server.tare.Analyst.class.getSimpleName();
    private final com.android.internal.app.IBatteryStats mIBatteryStats;
    private int mPeriodIndex;
    private final com.android.server.tare.Analyst.Report[] mReports;

    static {
        DEBUG = com.android.server.tare.InternalResourceService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    static final class Report {
        public int cumulativeBatteryDischarge = 0;
        public int currentBatteryLevel = 0;
        public long cumulativeProfit = 0;
        public int numProfitableActions = 0;
        public long cumulativeLoss = 0;
        public int numUnprofitableActions = 0;
        public long cumulativeRewards = 0;
        public int numRewards = 0;
        public long cumulativePositiveRegulations = 0;
        public int numPositiveRegulations = 0;
        public long cumulativeNegativeRegulations = 0;
        public int numNegativeRegulations = 0;
        public long screenOffDurationMs = 0;
        public long screenOffDischargeMah = 0;
        private long bsScreenOffRealtimeBase = 0;
        private long bsScreenOffDischargeMahBase = 0;

        Report() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.cumulativeBatteryDischarge = 0;
            this.currentBatteryLevel = 0;
            this.cumulativeProfit = 0L;
            this.numProfitableActions = 0;
            this.cumulativeLoss = 0L;
            this.numUnprofitableActions = 0;
            this.cumulativeRewards = 0L;
            this.numRewards = 0;
            this.cumulativePositiveRegulations = 0L;
            this.numPositiveRegulations = 0;
            this.cumulativeNegativeRegulations = 0L;
            this.numNegativeRegulations = 0;
            this.screenOffDurationMs = 0L;
            this.screenOffDischargeMah = 0L;
            this.bsScreenOffRealtimeBase = 0L;
            this.bsScreenOffDischargeMahBase = 0L;
        }
    }

    Analyst() {
        this(com.android.server.am.BatteryStatsService.getService());
    }

    @com.android.internal.annotations.VisibleForTesting
    Analyst(com.android.internal.app.IBatteryStats iBatteryStats) {
        this.mPeriodIndex = 0;
        this.mReports = new com.android.server.tare.Analyst.Report[8];
        this.mIBatteryStats = iBatteryStats;
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.tare.Analyst.Report> getReports() {
        java.util.ArrayList arrayList = new java.util.ArrayList(8);
        for (int i = 1; i <= 8; i++) {
            com.android.server.tare.Analyst.Report report = this.mReports[(this.mPeriodIndex + i) % 8];
            if (report != null) {
                arrayList.add(report);
            }
        }
        return arrayList;
    }

    long getBatteryScreenOffDischargeMah() {
        long j = 0;
        for (com.android.server.tare.Analyst.Report report : this.mReports) {
            if (report != null) {
                j += report.screenOffDischargeMah;
            }
        }
        return j;
    }

    long getBatteryScreenOffDurationMs() {
        long j = 0;
        for (com.android.server.tare.Analyst.Report report : this.mReports) {
            if (report != null) {
                j += report.screenOffDurationMs;
            }
        }
        return j;
    }

    void loadReports(@android.annotation.NonNull java.util.List<com.android.server.tare.Analyst.Report> list) {
        int size = list.size();
        this.mPeriodIndex = java.lang.Math.max(0, java.lang.Math.min(8, size) - 1);
        for (int i = 0; i < 8; i++) {
            if (i < size) {
                this.mReports[i] = list.get(i);
            } else {
                this.mReports[i] = null;
            }
        }
        com.android.server.tare.Analyst.Report report = this.mReports[this.mPeriodIndex];
        if (report != null) {
            report.bsScreenOffRealtimeBase = getLatestBatteryScreenOffRealtimeMs();
            report.bsScreenOffDischargeMahBase = getLatestScreenOffDischargeMah();
        }
    }

    void noteBatteryLevelChange(int i) {
        if ((this.mReports[this.mPeriodIndex] != null && i >= 90 && this.mReports[this.mPeriodIndex].currentBatteryLevel < i && this.mReports[this.mPeriodIndex].cumulativeBatteryDischarge >= 25) || (this.mReports[this.mPeriodIndex] != null && this.mReports[this.mPeriodIndex].currentBatteryLevel < i && (this.mReports[this.mPeriodIndex].screenOffDurationMs > 86400000L ? 1 : (this.mReports[this.mPeriodIndex].screenOffDurationMs == 86400000L ? 0 : -1)) >= 0)) {
            this.mPeriodIndex = (this.mPeriodIndex + 1) % 8;
            if (this.mReports[this.mPeriodIndex] != null) {
                com.android.server.tare.Analyst.Report report = this.mReports[this.mPeriodIndex];
                report.clear();
                report.currentBatteryLevel = i;
                report.bsScreenOffRealtimeBase = getLatestBatteryScreenOffRealtimeMs();
                report.bsScreenOffDischargeMahBase = getLatestScreenOffDischargeMah();
                return;
            }
        }
        if (this.mReports[this.mPeriodIndex] == null) {
            com.android.server.tare.Analyst.Report initializeReport = initializeReport();
            this.mReports[this.mPeriodIndex] = initializeReport;
            initializeReport.currentBatteryLevel = i;
            return;
        }
        com.android.server.tare.Analyst.Report report2 = this.mReports[this.mPeriodIndex];
        if (i < report2.currentBatteryLevel) {
            report2.cumulativeBatteryDischarge += report2.currentBatteryLevel - i;
            long latestBatteryScreenOffRealtimeMs = getLatestBatteryScreenOffRealtimeMs();
            long latestScreenOffDischargeMah = getLatestScreenOffDischargeMah();
            if (report2.bsScreenOffRealtimeBase > latestBatteryScreenOffRealtimeMs) {
                report2.bsScreenOffRealtimeBase = 0L;
                report2.bsScreenOffDischargeMahBase = 0L;
            }
            report2.screenOffDurationMs += latestBatteryScreenOffRealtimeMs - report2.bsScreenOffRealtimeBase;
            report2.screenOffDischargeMah += latestScreenOffDischargeMah - report2.bsScreenOffDischargeMahBase;
            report2.bsScreenOffRealtimeBase = latestBatteryScreenOffRealtimeMs;
            report2.bsScreenOffDischargeMahBase = latestScreenOffDischargeMah;
        }
        report2.currentBatteryLevel = i;
    }

    void noteTransaction(@android.annotation.NonNull com.android.server.tare.Ledger.Transaction transaction) {
        if (this.mReports[this.mPeriodIndex] == null) {
            this.mReports[this.mPeriodIndex] = initializeReport();
        }
        com.android.server.tare.Analyst.Report report = this.mReports[this.mPeriodIndex];
        switch (com.android.server.tare.EconomicPolicy.getEventType(transaction.eventId)) {
            case Integer.MIN_VALUE:
                if (transaction.delta != 0) {
                    report.cumulativeRewards += transaction.delta;
                    report.numRewards++;
                    break;
                }
                break;
            case 0:
                if (transaction.delta <= 0) {
                    if (transaction.delta < 0) {
                        report.cumulativeNegativeRegulations -= transaction.delta;
                        report.numNegativeRegulations++;
                        break;
                    }
                } else {
                    report.cumulativePositiveRegulations += transaction.delta;
                    report.numPositiveRegulations++;
                    break;
                }
                break;
            case 1073741824:
                if ((-transaction.delta) <= transaction.ctp) {
                    if ((-transaction.delta) < transaction.ctp) {
                        report.cumulativeLoss += transaction.ctp + transaction.delta;
                        report.numUnprofitableActions++;
                        break;
                    }
                } else {
                    report.cumulativeProfit += (-transaction.delta) - transaction.ctp;
                    report.numProfitableActions++;
                    break;
                }
                break;
        }
    }

    void tearDown() {
        for (int i = 0; i < this.mReports.length; i++) {
            this.mReports[i] = null;
        }
        this.mPeriodIndex = 0;
    }

    private long getLatestBatteryScreenOffRealtimeMs() {
        try {
            return this.mIBatteryStats.computeBatteryScreenOffRealtimeMs();
        } catch (android.os.RemoteException e) {
            return 0L;
        }
    }

    private long getLatestScreenOffDischargeMah() {
        try {
            return this.mIBatteryStats.getScreenOffDischargeMah();
        } catch (android.os.RemoteException e) {
            return 0L;
        }
    }

    @android.annotation.NonNull
    private com.android.server.tare.Analyst.Report initializeReport() {
        com.android.server.tare.Analyst.Report report = new com.android.server.tare.Analyst.Report();
        report.bsScreenOffRealtimeBase = getLatestBatteryScreenOffRealtimeMs();
        report.bsScreenOffDischargeMahBase = getLatestScreenOffDischargeMah();
        return report;
    }

    @android.annotation.NonNull
    private java.lang.String padStringWithSpaces(@android.annotation.NonNull java.lang.String str, int i) {
        int max = java.lang.Math.max(2, i - str.length()) >>> 1;
        return " ".repeat(max) + str + " ".repeat(max);
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        long batteryFullCharge = ((android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class)).getBatteryFullCharge() / 1000;
        indentingPrintWriter.println("Reports:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("      Total Discharge");
        indentingPrintWriter.print(padStringWithSpaces("Profit (avg/action : avg/discharge)", 47));
        indentingPrintWriter.print(padStringWithSpaces("Loss (avg/action : avg/discharge)", 47));
        indentingPrintWriter.print(padStringWithSpaces("Rewards (avg/reward : avg/discharge)", 47));
        indentingPrintWriter.print(padStringWithSpaces("+Regs (avg/reg : avg/discharge)", 47));
        indentingPrintWriter.print(padStringWithSpaces("-Regs (avg/reg : avg/discharge)", 47));
        indentingPrintWriter.print(padStringWithSpaces("Bg drain estimate", 47));
        indentingPrintWriter.println();
        for (int i = 0; i < 8; i++) {
            com.android.server.tare.Analyst.Report report = this.mReports[((this.mPeriodIndex - i) + 8) % 8];
            if (report != null) {
                indentingPrintWriter.print("t-");
                indentingPrintWriter.print(i);
                indentingPrintWriter.print(":  ");
                indentingPrintWriter.print(padStringWithSpaces(java.lang.Integer.toString(report.cumulativeBatteryDischarge), 15));
                if (report.numProfitableActions > 0) {
                    if (report.cumulativeBatteryDischarge > 0) {
                        str5 = com.android.server.tare.TareUtils.cakeToString(report.cumulativeProfit / report.cumulativeBatteryDischarge);
                    } else {
                        str5 = "N/A";
                    }
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%s (%s : %s)", com.android.server.tare.TareUtils.cakeToString(report.cumulativeProfit), com.android.server.tare.TareUtils.cakeToString(report.cumulativeProfit / report.numProfitableActions), str5), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                if (report.numUnprofitableActions > 0) {
                    if (report.cumulativeBatteryDischarge > 0) {
                        str4 = com.android.server.tare.TareUtils.cakeToString(report.cumulativeLoss / report.cumulativeBatteryDischarge);
                    } else {
                        str4 = "N/A";
                    }
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%s (%s : %s)", com.android.server.tare.TareUtils.cakeToString(report.cumulativeLoss), com.android.server.tare.TareUtils.cakeToString(report.cumulativeLoss / report.numUnprofitableActions), str4), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                if (report.numRewards > 0) {
                    if (report.cumulativeBatteryDischarge > 0) {
                        str3 = com.android.server.tare.TareUtils.cakeToString(report.cumulativeRewards / report.cumulativeBatteryDischarge);
                    } else {
                        str3 = "N/A";
                    }
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%s (%s : %s)", com.android.server.tare.TareUtils.cakeToString(report.cumulativeRewards), com.android.server.tare.TareUtils.cakeToString(report.cumulativeRewards / report.numRewards), str3), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                if (report.numPositiveRegulations > 0) {
                    if (report.cumulativeBatteryDischarge > 0) {
                        str2 = com.android.server.tare.TareUtils.cakeToString(report.cumulativePositiveRegulations / report.cumulativeBatteryDischarge);
                    } else {
                        str2 = "N/A";
                    }
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%s (%s : %s)", com.android.server.tare.TareUtils.cakeToString(report.cumulativePositiveRegulations), com.android.server.tare.TareUtils.cakeToString(report.cumulativePositiveRegulations / report.numPositiveRegulations), str2), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                if (report.numNegativeRegulations > 0) {
                    if (report.cumulativeBatteryDischarge > 0) {
                        str = com.android.server.tare.TareUtils.cakeToString(report.cumulativeNegativeRegulations / report.cumulativeBatteryDischarge);
                    } else {
                        str = "N/A";
                    }
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%s (%s : %s)", com.android.server.tare.TareUtils.cakeToString(report.cumulativeNegativeRegulations), com.android.server.tare.TareUtils.cakeToString(report.cumulativeNegativeRegulations / report.numNegativeRegulations), str), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                if (report.screenOffDurationMs > 0) {
                    indentingPrintWriter.print(padStringWithSpaces(java.lang.String.format("%d mAh (%.2f%%/hr)", java.lang.Long.valueOf(report.screenOffDischargeMah), java.lang.Double.valueOf(((report.screenOffDischargeMah * 100.0d) * 3600000.0d) / (report.screenOffDurationMs * batteryFullCharge))), 47));
                } else {
                    indentingPrintWriter.print(padStringWithSpaces("N/A", 47));
                }
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }
}
