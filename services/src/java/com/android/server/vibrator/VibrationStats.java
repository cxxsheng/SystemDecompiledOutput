package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibrationStats {
    static final java.lang.String TAG = "VibrationStats";
    private long mEndTimeDebug;
    private long mEndUptimeMillis;
    private int mRepeatCount;
    private long mStartTimeDebug;
    private long mStartUptimeMillis;
    private int mVibrationCompositionTotalSize;
    private int mVibrationPwleTotalSize;
    private int mVibratorComposeCount;
    private int mVibratorComposePwleCount;
    private int mVibratorOffCount;
    private int mVibratorOnCount;
    private int mVibratorOnTotalDurationMillis;
    private int mVibratorPerformCount;
    private int mVibratorSetAmplitudeCount;
    private int mVibratorSetExternalControlCount;
    private android.util.SparseBooleanArray mVibratorEffectsUsed = new android.util.SparseBooleanArray();
    private android.util.SparseBooleanArray mVibratorPrimitivesUsed = new android.util.SparseBooleanArray();
    private long mCreateUptimeMillis = android.os.SystemClock.uptimeMillis();
    private long mCreateTimeDebug = java.lang.System.currentTimeMillis();
    private int mEndedByUid = -1;
    private int mEndedByUsage = -1;
    private int mInterruptedUsage = -1;

    VibrationStats() {
    }

    long getCreateUptimeMillis() {
        return this.mCreateUptimeMillis;
    }

    long getStartUptimeMillis() {
        return this.mStartUptimeMillis;
    }

    long getEndUptimeMillis() {
        return this.mEndUptimeMillis;
    }

    long getCreateTimeDebug() {
        return this.mCreateTimeDebug;
    }

    long getStartTimeDebug() {
        return this.mStartTimeDebug;
    }

    long getEndTimeDebug() {
        return this.mEndTimeDebug;
    }

    long getDurationDebug() {
        if (hasEnded()) {
            return this.mEndUptimeMillis - this.mCreateUptimeMillis;
        }
        return -1L;
    }

    boolean hasEnded() {
        return this.mEndUptimeMillis > 0;
    }

    boolean hasStarted() {
        return this.mStartUptimeMillis > 0;
    }

    void reportStarted() {
        if (hasEnded() || this.mStartUptimeMillis != 0) {
            return;
        }
        this.mStartUptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mStartTimeDebug = java.lang.System.currentTimeMillis();
    }

    boolean reportEnded(@android.annotation.Nullable com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        if (hasEnded()) {
            return false;
        }
        if (callerInfo != null) {
            this.mEndedByUid = callerInfo.uid;
            this.mEndedByUsage = callerInfo.attrs.getUsage();
        }
        this.mEndUptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mEndTimeDebug = java.lang.System.currentTimeMillis();
        return true;
    }

    void reportInterruptedAnotherVibration(@android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        if (this.mInterruptedUsage < 0) {
            this.mInterruptedUsage = callerInfo.attrs.getUsage();
        }
    }

    void reportRepetition(int i) {
        this.mRepeatCount += i;
    }

    void reportVibratorOn(long j) {
        this.mVibratorOnCount++;
        if (j > 0) {
            this.mVibratorOnTotalDurationMillis += (int) j;
        }
    }

    void reportVibratorOff() {
        this.mVibratorOffCount++;
    }

    void reportSetAmplitude() {
        this.mVibratorSetAmplitudeCount++;
    }

    void reportPerformEffect(long j, android.os.vibrator.PrebakedSegment prebakedSegment) {
        this.mVibratorPerformCount++;
        if (j > 0) {
            this.mVibratorEffectsUsed.put(prebakedSegment.getEffectId(), true);
            this.mVibratorOnTotalDurationMillis += (int) j;
        } else {
            this.mVibratorEffectsUsed.put(prebakedSegment.getEffectId(), false);
        }
    }

    void reportComposePrimitives(long j, android.os.vibrator.PrimitiveSegment[] primitiveSegmentArr) {
        this.mVibratorComposeCount++;
        this.mVibrationCompositionTotalSize += primitiveSegmentArr.length;
        if (j > 0) {
            for (android.os.vibrator.PrimitiveSegment primitiveSegment : primitiveSegmentArr) {
                j -= r5.getDelay();
                this.mVibratorPrimitivesUsed.put(primitiveSegment.getPrimitiveId(), true);
            }
            if (j > 0) {
                this.mVibratorOnTotalDurationMillis += (int) j;
                return;
            }
            return;
        }
        for (android.os.vibrator.PrimitiveSegment primitiveSegment2 : primitiveSegmentArr) {
            this.mVibratorPrimitivesUsed.put(primitiveSegment2.getPrimitiveId(), false);
        }
    }

    void reportComposePwle(long j, android.os.vibrator.RampSegment[] rampSegmentArr) {
        this.mVibratorComposePwleCount++;
        this.mVibrationPwleTotalSize += rampSegmentArr.length;
        if (j > 0) {
            for (android.os.vibrator.RampSegment rampSegment : rampSegmentArr) {
                if (rampSegment.getStartAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && rampSegment.getEndAmplitude() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    j -= rampSegment.getDuration();
                }
            }
            if (j > 0) {
                this.mVibratorOnTotalDurationMillis += (int) j;
            }
        }
    }

    void reportSetExternalControl() {
        this.mVibratorSetExternalControlCount++;
    }

    static final class StatsInfo {
        public final int endLatencyMillis;
        public final boolean endedBySameUid;
        public final int endedByUsage;
        public final int halComposeCount;
        public final int halComposePwleCount;
        public final int halCompositionSize;
        public final int halOffCount;
        public final int halOnCount;
        public final int halPerformCount;
        public final int halPwleSize;
        public final int halSetAmplitudeCount;
        public final int halSetExternalControlCount;
        public final int[] halSupportedCompositionPrimitivesUsed;
        public final int[] halSupportedEffectsUsed;
        public final int[] halUnsupportedCompositionPrimitivesUsed;
        public final int[] halUnsupportedEffectsUsed;
        public final int interruptedUsage;
        private boolean mIsWritten;
        public final int repeatCount;
        public final int startLatencyMillis;
        public final int status;
        public final int totalDurationMillis;
        public final int uid;
        public final int usage;
        public final int vibrationType;
        public final int vibratorOnMillis;

        StatsInfo(int i, int i2, int i3, com.android.server.vibrator.Vibration.Status status, com.android.server.vibrator.VibrationStats vibrationStats, long j) {
            this.uid = i;
            this.vibrationType = i2;
            this.usage = i3;
            this.status = status.getProtoEnumValue();
            this.endedBySameUid = i == vibrationStats.mEndedByUid;
            this.endedByUsage = vibrationStats.mEndedByUsage;
            this.interruptedUsage = vibrationStats.mInterruptedUsage;
            this.repeatCount = vibrationStats.mRepeatCount;
            this.totalDurationMillis = (int) java.lang.Math.max(0L, j - vibrationStats.mCreateUptimeMillis);
            this.vibratorOnMillis = vibrationStats.mVibratorOnTotalDurationMillis;
            if (vibrationStats.hasStarted()) {
                this.startLatencyMillis = (int) java.lang.Math.max(0L, vibrationStats.mStartUptimeMillis - vibrationStats.mCreateUptimeMillis);
                this.endLatencyMillis = (int) java.lang.Math.max(0L, j - vibrationStats.mEndUptimeMillis);
            } else {
                this.endLatencyMillis = 0;
                this.startLatencyMillis = 0;
            }
            this.halComposeCount = vibrationStats.mVibratorComposeCount;
            this.halComposePwleCount = vibrationStats.mVibratorComposePwleCount;
            this.halOnCount = vibrationStats.mVibratorOnCount;
            this.halOffCount = vibrationStats.mVibratorOffCount;
            this.halPerformCount = vibrationStats.mVibratorPerformCount;
            this.halSetAmplitudeCount = vibrationStats.mVibratorSetAmplitudeCount;
            this.halSetExternalControlCount = vibrationStats.mVibratorSetExternalControlCount;
            this.halCompositionSize = vibrationStats.mVibrationCompositionTotalSize;
            this.halPwleSize = vibrationStats.mVibrationPwleTotalSize;
            this.halSupportedCompositionPrimitivesUsed = filteredKeys(vibrationStats.mVibratorPrimitivesUsed, true);
            this.halSupportedEffectsUsed = filteredKeys(vibrationStats.mVibratorEffectsUsed, true);
            this.halUnsupportedCompositionPrimitivesUsed = filteredKeys(vibrationStats.mVibratorPrimitivesUsed, false);
            this.halUnsupportedEffectsUsed = filteredKeys(vibrationStats.mVibratorEffectsUsed, false);
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean isWritten() {
            return this.mIsWritten;
        }

        void writeVibrationReported() {
            if (this.mIsWritten) {
                android.util.Slog.wtf(com.android.server.vibrator.VibrationStats.TAG, "Writing same vibration stats multiple times for uid=" + this.uid);
            }
            this.mIsWritten = true;
            com.android.internal.util.FrameworkStatsLog.write_non_chained(com.android.internal.util.FrameworkStatsLog.VIBRATION_REPORTED, this.uid, null, this.vibrationType, this.usage, this.status, this.endedBySameUid, this.endedByUsage, this.interruptedUsage, this.repeatCount, this.totalDurationMillis, this.vibratorOnMillis, this.startLatencyMillis, this.endLatencyMillis, this.halComposeCount, this.halComposePwleCount, this.halOnCount, this.halOffCount, this.halPerformCount, this.halSetAmplitudeCount, this.halSetExternalControlCount, this.halSupportedCompositionPrimitivesUsed, this.halSupportedEffectsUsed, this.halUnsupportedCompositionPrimitivesUsed, this.halUnsupportedEffectsUsed, this.halCompositionSize, this.halPwleSize);
        }

        private static int[] filteredKeys(android.util.SparseBooleanArray sparseBooleanArray, boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < sparseBooleanArray.size(); i2++) {
                if (sparseBooleanArray.valueAt(i2) == z) {
                    i++;
                }
            }
            if (i == 0) {
                return null;
            }
            int[] iArr = new int[i];
            int i3 = 0;
            for (int i4 = 0; i4 < sparseBooleanArray.size(); i4++) {
                if (sparseBooleanArray.valueAt(i4) == z) {
                    iArr[i3] = sparseBooleanArray.keyAt(i4);
                    i3++;
                }
            }
            return iArr;
        }
    }
}
