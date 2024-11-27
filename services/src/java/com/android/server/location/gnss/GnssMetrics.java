package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssMetrics {
    private static final int CONVERT_MILLI_TO_MICRO = 1000;
    private static final int DEFAULT_TIME_BETWEEN_FIXES_MILLISECS = 1000;
    private static final double L5_CARRIER_FREQ_RANGE_HIGH_HZ = 1.189E9d;
    private static final double L5_CARRIER_FREQ_RANGE_LOW_HZ = 1.164E9d;
    private static final java.lang.String TAG = "GnssMetrics";
    private static final int VENDOR_SPECIFIC_POWER_MODES_SIZE = 10;
    private boolean[] mConstellationTypes;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    com.android.server.location.gnss.GnssMetrics.GnssPowerMetrics mGnssPowerMetrics;
    long mL5SvStatusReports;
    long mL5SvStatusReportsUsedInFix;
    com.android.server.location.gnss.GnssMetrics.Statistics mL5TopFourAverageCn0DbmHzReportsStatistics;
    com.android.server.location.gnss.GnssMetrics.Statistics mLocationFailureReportsStatistics;
    private long mLogStartInElapsedRealtimeMs;
    private int mNumL5SvStatus;
    private int mNumL5SvStatusUsedInFix;
    private int mNumSvStatus;
    private int mNumSvStatusUsedInFix;
    com.android.server.location.gnss.GnssMetrics.Statistics mPositionAccuracyMetersReportsStatistics;
    private final android.app.StatsManager mStatsManager;
    long mSvStatusReports;
    long mSvStatusReportsUsedInFix;
    com.android.server.location.gnss.GnssMetrics.Statistics mTimeToFirstFixMilliSReportsStatistics;
    com.android.server.location.gnss.GnssMetrics.Statistics mTopFourAverageCn0DbmHzReportsStatistics;
    private final com.android.server.location.gnss.GnssMetrics.Statistics mLocationFailureStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
    private final com.android.server.location.gnss.GnssMetrics.Statistics mTimeToFirstFixSecStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
    private final com.android.server.location.gnss.GnssMetrics.Statistics mPositionAccuracyMeterStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
    private final com.android.server.location.gnss.GnssMetrics.Statistics mTopFourAverageCn0Statistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
    private final com.android.server.location.gnss.GnssMetrics.Statistics mTopFourAverageCn0StatisticsL5 = new com.android.server.location.gnss.GnssMetrics.Statistics();

    public GnssMetrics(android.content.Context context, com.android.internal.app.IBatteryStats iBatteryStats, com.android.server.location.gnss.hal.GnssNative gnssNative) {
        this.mGnssNative = gnssNative;
        this.mGnssPowerMetrics = new com.android.server.location.gnss.GnssMetrics.GnssPowerMetrics(iBatteryStats);
        reset();
        this.mLocationFailureReportsStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
        this.mTimeToFirstFixMilliSReportsStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
        this.mPositionAccuracyMetersReportsStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
        this.mTopFourAverageCn0DbmHzReportsStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
        this.mL5TopFourAverageCn0DbmHzReportsStatistics = new com.android.server.location.gnss.GnssMetrics.Statistics();
        this.mStatsManager = (android.app.StatsManager) context.getSystemService("stats");
        registerGnssStats();
    }

    public void logReceivedLocationStatus(boolean z) {
        if (!z) {
            this.mLocationFailureStatistics.addItem(1.0d);
            this.mLocationFailureReportsStatistics.addItem(1.0d);
        } else {
            this.mLocationFailureStatistics.addItem(0.0d);
            this.mLocationFailureReportsStatistics.addItem(0.0d);
        }
    }

    public void logMissedReports(int i, int i2) {
        int max = (i2 / java.lang.Math.max(1000, i)) - 1;
        if (max > 0) {
            for (int i3 = 0; i3 < max; i3++) {
                this.mLocationFailureStatistics.addItem(1.0d);
                this.mLocationFailureReportsStatistics.addItem(1.0d);
            }
        }
    }

    public void logTimeToFirstFixMilliSecs(int i) {
        double d = i;
        this.mTimeToFirstFixSecStatistics.addItem(d / 1000.0d);
        this.mTimeToFirstFixMilliSReportsStatistics.addItem(d);
    }

    public void logPositionAccuracyMeters(float f) {
        double d = f;
        this.mPositionAccuracyMeterStatistics.addItem(d);
        this.mPositionAccuracyMetersReportsStatistics.addItem(d);
    }

    public void logCn0(android.location.GnssStatus gnssStatus) {
        logCn0L5(gnssStatus);
        if (gnssStatus.getSatelliteCount() == 0) {
            this.mGnssPowerMetrics.reportSignalQuality(null);
            return;
        }
        int satelliteCount = gnssStatus.getSatelliteCount();
        float[] fArr = new float[satelliteCount];
        for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
            fArr[i] = gnssStatus.getCn0DbHz(i);
        }
        java.util.Arrays.sort(fArr);
        this.mGnssPowerMetrics.reportSignalQuality(fArr);
        if (satelliteCount < 4) {
            return;
        }
        int i2 = satelliteCount - 4;
        double d = 0.0d;
        if (fArr[i2] > 0.0d) {
            while (i2 < satelliteCount) {
                d += fArr[i2];
                i2++;
            }
            double d2 = d / 4.0d;
            this.mTopFourAverageCn0Statistics.addItem(d2);
            this.mTopFourAverageCn0DbmHzReportsStatistics.addItem(d2 * 1000.0d);
        }
    }

    private static boolean isL5Sv(float f) {
        double d = f;
        return d >= L5_CARRIER_FREQ_RANGE_LOW_HZ && d <= L5_CARRIER_FREQ_RANGE_HIGH_HZ;
    }

    public void logSvStatus(android.location.GnssStatus gnssStatus) {
        for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
            if (gnssStatus.hasCarrierFrequencyHz(i)) {
                this.mNumSvStatus++;
                this.mSvStatusReports++;
                boolean isL5Sv = isL5Sv(gnssStatus.getCarrierFrequencyHz(i));
                if (isL5Sv) {
                    this.mNumL5SvStatus++;
                    this.mL5SvStatusReports++;
                }
                if (gnssStatus.usedInFix(i)) {
                    this.mNumSvStatusUsedInFix++;
                    this.mSvStatusReportsUsedInFix++;
                    if (isL5Sv) {
                        this.mNumL5SvStatusUsedInFix++;
                        this.mL5SvStatusReportsUsedInFix++;
                    }
                }
            }
        }
    }

    private void logCn0L5(android.location.GnssStatus gnssStatus) {
        if (gnssStatus.getSatelliteCount() == 0) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(gnssStatus.getSatelliteCount());
        for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
            if (isL5Sv(gnssStatus.getCarrierFrequencyHz(i))) {
                arrayList.add(java.lang.Float.valueOf(gnssStatus.getCn0DbHz(i)));
            }
        }
        if (arrayList.size() < 4) {
            return;
        }
        java.util.Collections.sort(arrayList);
        double d = 0.0d;
        if (((java.lang.Float) arrayList.get(arrayList.size() - 4)).floatValue() > 0.0d) {
            for (int size = arrayList.size() - 4; size < arrayList.size(); size++) {
                d += ((java.lang.Float) arrayList.get(size)).floatValue();
            }
            double d2 = d / 4.0d;
            this.mTopFourAverageCn0StatisticsL5.addItem(d2);
            this.mL5TopFourAverageCn0DbmHzReportsStatistics.addItem(d2 * 1000.0d);
        }
    }

    public void logConstellationType(int i) {
        if (i >= this.mConstellationTypes.length) {
            android.util.Log.e(TAG, "Constellation type " + i + " is not valid.");
            return;
        }
        this.mConstellationTypes[i] = true;
    }

    public java.lang.String dumpGnssMetricsAsProtoString() {
        com.android.internal.location.nano.GnssLogsProto.GnssLog gnssLog = new com.android.internal.location.nano.GnssLogsProto.GnssLog();
        if (this.mLocationFailureStatistics.getCount() > 0) {
            gnssLog.numLocationReportProcessed = this.mLocationFailureStatistics.getCount();
            gnssLog.percentageLocationFailure = (int) (this.mLocationFailureStatistics.getMean() * 100.0d);
        }
        if (this.mTimeToFirstFixSecStatistics.getCount() > 0) {
            gnssLog.numTimeToFirstFixProcessed = this.mTimeToFirstFixSecStatistics.getCount();
            gnssLog.meanTimeToFirstFixSecs = (int) this.mTimeToFirstFixSecStatistics.getMean();
            gnssLog.standardDeviationTimeToFirstFixSecs = (int) this.mTimeToFirstFixSecStatistics.getStandardDeviation();
        }
        if (this.mPositionAccuracyMeterStatistics.getCount() > 0) {
            gnssLog.numPositionAccuracyProcessed = this.mPositionAccuracyMeterStatistics.getCount();
            gnssLog.meanPositionAccuracyMeters = (int) this.mPositionAccuracyMeterStatistics.getMean();
            gnssLog.standardDeviationPositionAccuracyMeters = (int) this.mPositionAccuracyMeterStatistics.getStandardDeviation();
        }
        if (this.mTopFourAverageCn0Statistics.getCount() > 0) {
            gnssLog.numTopFourAverageCn0Processed = this.mTopFourAverageCn0Statistics.getCount();
            gnssLog.meanTopFourAverageCn0DbHz = this.mTopFourAverageCn0Statistics.getMean();
            gnssLog.standardDeviationTopFourAverageCn0DbHz = this.mTopFourAverageCn0Statistics.getStandardDeviation();
        }
        if (this.mNumSvStatus > 0) {
            gnssLog.numSvStatusProcessed = this.mNumSvStatus;
        }
        if (this.mNumL5SvStatus > 0) {
            gnssLog.numL5SvStatusProcessed = this.mNumL5SvStatus;
        }
        if (this.mNumSvStatusUsedInFix > 0) {
            gnssLog.numSvStatusUsedInFix = this.mNumSvStatusUsedInFix;
        }
        if (this.mNumL5SvStatusUsedInFix > 0) {
            gnssLog.numL5SvStatusUsedInFix = this.mNumL5SvStatusUsedInFix;
        }
        if (this.mTopFourAverageCn0StatisticsL5.getCount() > 0) {
            gnssLog.numL5TopFourAverageCn0Processed = this.mTopFourAverageCn0StatisticsL5.getCount();
            gnssLog.meanL5TopFourAverageCn0DbHz = this.mTopFourAverageCn0StatisticsL5.getMean();
            gnssLog.standardDeviationL5TopFourAverageCn0DbHz = this.mTopFourAverageCn0StatisticsL5.getStandardDeviation();
        }
        gnssLog.powerMetrics = this.mGnssPowerMetrics.buildProto();
        gnssLog.hardwareRevision = android.os.SystemProperties.get("ro.boot.revision", "");
        java.lang.String encodeToString = android.util.Base64.encodeToString(com.android.internal.location.nano.GnssLogsProto.GnssLog.toByteArray(gnssLog), 0);
        reset();
        return encodeToString;
    }

    public java.lang.String dumpGnssMetricsAsText() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("GNSS_KPI_START");
        sb.append('\n');
        sb.append("  KPI logging start time: ");
        android.util.TimeUtils.formatDuration(this.mLogStartInElapsedRealtimeMs, sb);
        sb.append("\n");
        sb.append("  KPI logging end time: ");
        android.util.TimeUtils.formatDuration(android.os.SystemClock.elapsedRealtime(), sb);
        sb.append("\n");
        sb.append("  Number of location reports: ");
        sb.append(this.mLocationFailureStatistics.getCount());
        sb.append("\n");
        if (this.mLocationFailureStatistics.getCount() > 0) {
            sb.append("  Percentage location failure: ");
            sb.append(this.mLocationFailureStatistics.getMean() * 100.0d);
            sb.append("\n");
        }
        sb.append("  Number of TTFF reports: ");
        sb.append(this.mTimeToFirstFixSecStatistics.getCount());
        sb.append("\n");
        if (this.mTimeToFirstFixSecStatistics.getCount() > 0) {
            sb.append("  TTFF mean (sec): ");
            sb.append(this.mTimeToFirstFixSecStatistics.getMean());
            sb.append("\n");
            sb.append("  TTFF standard deviation (sec): ");
            sb.append(this.mTimeToFirstFixSecStatistics.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Number of position accuracy reports: ");
        sb.append(this.mPositionAccuracyMeterStatistics.getCount());
        sb.append("\n");
        if (this.mPositionAccuracyMeterStatistics.getCount() > 0) {
            sb.append("  Position accuracy mean (m): ");
            sb.append(this.mPositionAccuracyMeterStatistics.getMean());
            sb.append("\n");
            sb.append("  Position accuracy standard deviation (m): ");
            sb.append(this.mPositionAccuracyMeterStatistics.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Number of CN0 reports: ");
        sb.append(this.mTopFourAverageCn0Statistics.getCount());
        sb.append("\n");
        if (this.mTopFourAverageCn0Statistics.getCount() > 0) {
            sb.append("  Top 4 Avg CN0 mean (dB-Hz): ");
            sb.append(this.mTopFourAverageCn0Statistics.getMean());
            sb.append("\n");
            sb.append("  Top 4 Avg CN0 standard deviation (dB-Hz): ");
            sb.append(this.mTopFourAverageCn0Statistics.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Total number of sv status messages processed: ");
        sb.append(this.mNumSvStatus);
        sb.append("\n");
        sb.append("  Total number of L5 sv status messages processed: ");
        sb.append(this.mNumL5SvStatus);
        sb.append("\n");
        sb.append("  Total number of sv status messages processed, where sv is used in fix: ");
        sb.append(this.mNumSvStatusUsedInFix);
        sb.append("\n");
        sb.append("  Total number of L5 sv status messages processed, where sv is used in fix: ");
        sb.append(this.mNumL5SvStatusUsedInFix);
        sb.append("\n");
        sb.append("  Number of L5 CN0 reports: ");
        sb.append(this.mTopFourAverageCn0StatisticsL5.getCount());
        sb.append("\n");
        if (this.mTopFourAverageCn0StatisticsL5.getCount() > 0) {
            sb.append("  L5 Top 4 Avg CN0 mean (dB-Hz): ");
            sb.append(this.mTopFourAverageCn0StatisticsL5.getMean());
            sb.append("\n");
            sb.append("  L5 Top 4 Avg CN0 standard deviation (dB-Hz): ");
            sb.append(this.mTopFourAverageCn0StatisticsL5.getStandardDeviation());
            sb.append("\n");
        }
        sb.append("  Used-in-fix constellation types: ");
        for (int i = 0; i < this.mConstellationTypes.length; i++) {
            if (this.mConstellationTypes[i]) {
                sb.append(android.location.GnssStatus.constellationTypeToString(i));
                sb.append(" ");
            }
        }
        sb.append("\n");
        sb.append("GNSS_KPI_END");
        sb.append("\n");
        android.os.connectivity.GpsBatteryStats gpsBatteryStats = this.mGnssPowerMetrics.getGpsBatteryStats();
        if (gpsBatteryStats != null) {
            sb.append("Power Metrics");
            sb.append("\n");
            sb.append("  Time on battery (min): ");
            sb.append(gpsBatteryStats.getLoggingDurationMs() / 60000.0d);
            sb.append("\n");
            long[] timeInGpsSignalQualityLevel = gpsBatteryStats.getTimeInGpsSignalQualityLevel();
            if (timeInGpsSignalQualityLevel != null && timeInGpsSignalQualityLevel.length == 2) {
                sb.append("  Amount of time (while on battery) Top 4 Avg CN0 > 20.0 dB-Hz (min): ");
                sb.append(timeInGpsSignalQualityLevel[1] / 60000.0d);
                sb.append("\n");
                sb.append("  Amount of time (while on battery) Top 4 Avg CN0 <= 20.0 dB-Hz (min): ");
                sb.append(timeInGpsSignalQualityLevel[0] / 60000.0d);
                sb.append("\n");
            }
            sb.append("  Energy consumed while on battery (mAh): ");
            sb.append(gpsBatteryStats.getEnergyConsumedMaMs() / 3600000.0d);
            sb.append("\n");
        }
        sb.append("Hardware Version: ");
        sb.append(android.os.SystemProperties.get("ro.boot.revision", ""));
        sb.append("\n");
        return sb.toString();
    }

    private void reset() {
        this.mLogStartInElapsedRealtimeMs = android.os.SystemClock.elapsedRealtime();
        this.mLocationFailureStatistics.reset();
        this.mTimeToFirstFixSecStatistics.reset();
        this.mPositionAccuracyMeterStatistics.reset();
        this.mTopFourAverageCn0Statistics.reset();
        resetConstellationTypes();
        this.mTopFourAverageCn0StatisticsL5.reset();
        this.mNumSvStatus = 0;
        this.mNumL5SvStatus = 0;
        this.mNumSvStatusUsedInFix = 0;
        this.mNumL5SvStatusUsedInFix = 0;
    }

    public void resetConstellationTypes() {
        this.mConstellationTypes = new boolean[8];
    }

    private static class Statistics {
        private int mCount;
        private long mLongSum;
        private double mSum;
        private double mSumSquare;

        Statistics() {
        }

        public synchronized void reset() {
            this.mCount = 0;
            this.mSum = 0.0d;
            this.mSumSquare = 0.0d;
            this.mLongSum = 0L;
        }

        public synchronized void addItem(double d) {
            this.mCount++;
            this.mSum += d;
            this.mSumSquare += d * d;
            this.mLongSum = (long) (this.mLongSum + d);
        }

        public synchronized int getCount() {
            return this.mCount;
        }

        public synchronized double getMean() {
            return this.mSum / this.mCount;
        }

        public synchronized double getStandardDeviation() {
            double d = this.mSum / this.mCount;
            double d2 = d * d;
            double d3 = this.mSumSquare / this.mCount;
            if (d3 <= d2) {
                return 0.0d;
            }
            return java.lang.Math.sqrt(d3 - d2);
        }

        public synchronized long getLongSum() {
            return this.mLongSum;
        }
    }

    private class GnssPowerMetrics {
        public static final double POOR_TOP_FOUR_AVG_CN0_THRESHOLD_DB_HZ = 20.0d;
        private static final double REPORTING_THRESHOLD_DB_HZ = 1.0d;
        private final com.android.internal.app.IBatteryStats mBatteryStats;
        private double mLastAverageCn0 = -100.0d;
        private int mLastSignalLevel = -1;

        GnssPowerMetrics(com.android.internal.app.IBatteryStats iBatteryStats) {
            this.mBatteryStats = iBatteryStats;
        }

        public com.android.internal.location.nano.GnssLogsProto.PowerMetrics buildProto() {
            com.android.internal.location.nano.GnssLogsProto.PowerMetrics powerMetrics = new com.android.internal.location.nano.GnssLogsProto.PowerMetrics();
            android.os.connectivity.GpsBatteryStats gpsBatteryStats = com.android.server.location.gnss.GnssMetrics.this.mGnssPowerMetrics.getGpsBatteryStats();
            if (gpsBatteryStats != null) {
                powerMetrics.loggingDurationMs = gpsBatteryStats.getLoggingDurationMs();
                powerMetrics.energyConsumedMah = gpsBatteryStats.getEnergyConsumedMaMs() / 3600000.0d;
                long[] timeInGpsSignalQualityLevel = gpsBatteryStats.getTimeInGpsSignalQualityLevel();
                powerMetrics.timeInSignalQualityLevelMs = new long[timeInGpsSignalQualityLevel.length];
                java.lang.System.arraycopy(timeInGpsSignalQualityLevel, 0, powerMetrics.timeInSignalQualityLevelMs, 0, timeInGpsSignalQualityLevel.length);
            }
            return powerMetrics;
        }

        public android.os.connectivity.GpsBatteryStats getGpsBatteryStats() {
            try {
                return this.mBatteryStats.getGpsBatteryStats();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.location.gnss.GnssMetrics.TAG, e);
                return null;
            }
        }

        public void reportSignalQuality(float[] fArr) {
            double d = 0.0d;
            if (fArr != null && fArr.length > 0) {
                for (int max = java.lang.Math.max(0, fArr.length - 4); max < fArr.length; max++) {
                    d += fArr[max];
                }
                d /= java.lang.Math.min(fArr.length, 4);
            }
            if (java.lang.Math.abs(d - this.mLastAverageCn0) < REPORTING_THRESHOLD_DB_HZ) {
                return;
            }
            int signalLevel = getSignalLevel(d);
            if (signalLevel != this.mLastSignalLevel) {
                com.android.internal.util.FrameworkStatsLog.write(69, signalLevel);
                this.mLastSignalLevel = signalLevel;
            }
            try {
                this.mBatteryStats.noteGpsSignalQuality(signalLevel);
                this.mLastAverageCn0 = d;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.location.gnss.GnssMetrics.TAG, e);
            }
        }

        private int getSignalLevel(double d) {
            if (d > 20.0d) {
                return 1;
            }
            return 0;
        }
    }

    private void registerGnssStats() {
        com.android.server.location.gnss.GnssMetrics.StatsPullAtomCallbackImpl statsPullAtomCallbackImpl = new com.android.server.location.gnss.GnssMetrics.StatsPullAtomCallbackImpl();
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GNSS_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.GNSS_POWER_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, statsPullAtomCallbackImpl);
    }

    private class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        StatsPullAtomCallbackImpl() {
        }

        public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
            if (i == 10074) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, com.android.server.location.gnss.GnssMetrics.this.mLocationFailureReportsStatistics.getCount(), com.android.server.location.gnss.GnssMetrics.this.mLocationFailureReportsStatistics.getLongSum(), com.android.server.location.gnss.GnssMetrics.this.mTimeToFirstFixMilliSReportsStatistics.getCount(), com.android.server.location.gnss.GnssMetrics.this.mTimeToFirstFixMilliSReportsStatistics.getLongSum(), com.android.server.location.gnss.GnssMetrics.this.mPositionAccuracyMetersReportsStatistics.getCount(), com.android.server.location.gnss.GnssMetrics.this.mPositionAccuracyMetersReportsStatistics.getLongSum(), com.android.server.location.gnss.GnssMetrics.this.mTopFourAverageCn0DbmHzReportsStatistics.getCount(), com.android.server.location.gnss.GnssMetrics.this.mTopFourAverageCn0DbmHzReportsStatistics.getLongSum(), com.android.server.location.gnss.GnssMetrics.this.mL5TopFourAverageCn0DbmHzReportsStatistics.getCount(), com.android.server.location.gnss.GnssMetrics.this.mL5TopFourAverageCn0DbmHzReportsStatistics.getLongSum(), com.android.server.location.gnss.GnssMetrics.this.mSvStatusReports, com.android.server.location.gnss.GnssMetrics.this.mSvStatusReportsUsedInFix, com.android.server.location.gnss.GnssMetrics.this.mL5SvStatusReports, com.android.server.location.gnss.GnssMetrics.this.mL5SvStatusReportsUsedInFix));
                return 0;
            }
            if (i == 10101) {
                com.android.server.location.gnss.GnssMetrics.this.mGnssNative.requestPowerStats();
                com.android.server.location.gnss.GnssPowerStats powerStats = com.android.server.location.gnss.GnssMetrics.this.mGnssNative.getPowerStats();
                if (powerStats == null) {
                    return 1;
                }
                double[] dArr = new double[10];
                double[] otherModesEnergyMilliJoule = powerStats.getOtherModesEnergyMilliJoule();
                if (otherModesEnergyMilliJoule.length >= 10) {
                    java.lang.System.arraycopy(otherModesEnergyMilliJoule, 0, dArr, 0, 10);
                } else {
                    java.lang.System.arraycopy(otherModesEnergyMilliJoule, 0, dArr, 0, otherModesEnergyMilliJoule.length);
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, (long) powerStats.getElapsedRealtimeUncertaintyNanos(), (long) (powerStats.getTotalEnergyMilliJoule() * 1000.0d), (long) (powerStats.getSinglebandTrackingModeEnergyMilliJoule() * 1000.0d), (long) (powerStats.getMultibandTrackingModeEnergyMilliJoule() * 1000.0d), (long) (powerStats.getSinglebandAcquisitionModeEnergyMilliJoule() * 1000.0d), (long) (powerStats.getMultibandAcquisitionModeEnergyMilliJoule() * 1000.0d), (long) (dArr[0] * 1000.0d), (long) (dArr[1] * 1000.0d), (long) (dArr[2] * 1000.0d), (long) (dArr[3] * 1000.0d), (long) (dArr[4] * 1000.0d), (long) (dArr[5] * 1000.0d), (long) (dArr[6] * 1000.0d), (long) (dArr[7] * 1000.0d), (long) (dArr[8] * 1000.0d), (long) (dArr[9] * 1000.0d)));
                return 0;
            }
            throw new java.lang.UnsupportedOperationException("Unknown tagId = " + i);
        }
    }
}
