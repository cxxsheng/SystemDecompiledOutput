package com.android.server.vibrator;

/* loaded from: classes2.dex */
abstract class Vibration {
    public final com.android.server.vibrator.Vibration.CallerInfo callerInfo;
    public final android.os.IBinder callerToken;
    public final long id;
    public final com.android.server.vibrator.VibrationStats stats = new com.android.server.vibrator.VibrationStats();
    private static final java.text.SimpleDateFormat DEBUG_TIME_FORMAT = new java.text.SimpleDateFormat("HH:mm:ss.SSS");
    private static final java.text.SimpleDateFormat DEBUG_DATE_TIME_FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static final java.util.concurrent.atomic.AtomicInteger sNextVibrationId = new java.util.concurrent.atomic.AtomicInteger(1);

    abstract boolean isRepeating();

    enum Status {
        UNKNOWN(0),
        RUNNING(1),
        FINISHED(2),
        FINISHED_UNEXPECTED(3),
        FORWARDED_TO_INPUT_DEVICES(4),
        CANCELLED_BINDER_DIED(5),
        CANCELLED_BY_SCREEN_OFF(6),
        CANCELLED_BY_SETTINGS_UPDATE(7),
        CANCELLED_BY_USER(8),
        CANCELLED_BY_UNKNOWN_REASON(9),
        CANCELLED_SUPERSEDED(10),
        IGNORED_ERROR_APP_OPS(11),
        IGNORED_ERROR_CANCELLING(12),
        IGNORED_ERROR_SCHEDULING(13),
        IGNORED_ERROR_TOKEN(14),
        IGNORED_APP_OPS(15),
        IGNORED_BACKGROUND(16),
        IGNORED_UNKNOWN_VIBRATION(17),
        IGNORED_UNSUPPORTED(18),
        IGNORED_FOR_EXTERNAL(19),
        IGNORED_FOR_HIGHER_IMPORTANCE(20),
        IGNORED_FOR_ONGOING(21),
        IGNORED_FOR_POWER(22),
        IGNORED_FOR_RINGER_MODE(23),
        IGNORED_FOR_SETTINGS(24),
        IGNORED_SUPERSEDED(25),
        IGNORED_FROM_VIRTUAL_DEVICE(26),
        IGNORED_ON_WIRELESS_CHARGER(27);

        private final int mProtoEnumValue;

        Status(int i) {
            this.mProtoEnumValue = i;
        }

        public int getProtoEnumValue() {
            return this.mProtoEnumValue;
        }
    }

    Vibration(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(callerInfo);
        this.id = sNextVibrationId.getAndIncrement();
        this.callerToken = iBinder;
        this.callerInfo = callerInfo;
    }

    static final class CallerInfo {
        public final android.os.VibrationAttributes attrs;
        public final int deviceId;
        public final java.lang.String opPkg;
        public final java.lang.String reason;
        public final int uid;

        CallerInfo(@android.annotation.NonNull android.os.VibrationAttributes vibrationAttributes, int i, int i2, java.lang.String str, java.lang.String str2) {
            java.util.Objects.requireNonNull(vibrationAttributes);
            this.attrs = vibrationAttributes;
            this.uid = i;
            this.deviceId = i2;
            this.opPkg = str;
            this.reason = str2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.vibrator.Vibration.CallerInfo)) {
                return false;
            }
            com.android.server.vibrator.Vibration.CallerInfo callerInfo = (com.android.server.vibrator.Vibration.CallerInfo) obj;
            return java.util.Objects.equals(this.attrs, callerInfo.attrs) && this.uid == callerInfo.uid && this.deviceId == callerInfo.deviceId && java.util.Objects.equals(this.opPkg, callerInfo.opPkg) && java.util.Objects.equals(this.reason, callerInfo.reason);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.attrs, java.lang.Integer.valueOf(this.uid), java.lang.Integer.valueOf(this.deviceId), this.opPkg, this.reason);
        }

        public java.lang.String toString() {
            return "CallerInfo{ uid=" + this.uid + ", opPkg=" + this.opPkg + ", deviceId=" + this.deviceId + ", attrs=" + this.attrs + ", reason=" + this.reason + '}';
        }
    }

    static final class EndInfo {
        public final com.android.server.vibrator.Vibration.CallerInfo endedBy;

        @android.annotation.NonNull
        public final com.android.server.vibrator.Vibration.Status status;

        EndInfo(@android.annotation.NonNull com.android.server.vibrator.Vibration.Status status) {
            this(status, null);
        }

        EndInfo(@android.annotation.NonNull com.android.server.vibrator.Vibration.Status status, @android.annotation.Nullable com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
            this.status = status;
            this.endedBy = callerInfo;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.vibrator.Vibration.EndInfo)) {
                return false;
            }
            com.android.server.vibrator.Vibration.EndInfo endInfo = (com.android.server.vibrator.Vibration.EndInfo) obj;
            return java.util.Objects.equals(this.endedBy, endInfo.endedBy) && this.status == endInfo.status;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.status, this.endedBy);
        }

        public java.lang.String toString() {
            return "EndInfo{status=" + this.status + ", endedBy=" + this.endedBy + '}';
        }
    }

    static final class DebugInfo {
        private final float mAdaptiveScale;
        final com.android.server.vibrator.Vibration.CallerInfo mCallerInfo;
        final long mCreateTime;
        private final long mDurationMs;
        private final long mEndTime;

        @android.annotation.Nullable
        private final android.os.CombinedVibration mOriginalEffect;

        @android.annotation.Nullable
        final android.os.CombinedVibration mPlayedEffect;
        private final int mScaleLevel;
        private final long mStartTime;
        private final com.android.server.vibrator.Vibration.Status mStatus;

        DebugInfo(com.android.server.vibrator.Vibration.Status status, com.android.server.vibrator.VibrationStats vibrationStats, @android.annotation.Nullable android.os.CombinedVibration combinedVibration, @android.annotation.Nullable android.os.CombinedVibration combinedVibration2, int i, float f, @android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
            java.util.Objects.requireNonNull(callerInfo);
            this.mCreateTime = vibrationStats.getCreateTimeDebug();
            this.mStartTime = vibrationStats.getStartTimeDebug();
            this.mEndTime = vibrationStats.getEndTimeDebug();
            this.mDurationMs = vibrationStats.getDurationDebug();
            this.mPlayedEffect = combinedVibration;
            this.mOriginalEffect = combinedVibration2;
            this.mScaleLevel = i;
            this.mAdaptiveScale = f;
            this.mCallerInfo = callerInfo;
            this.mStatus = status;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("createTime: ");
            sb.append(com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mCreateTime)));
            sb.append(", startTime: ");
            sb.append(com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mStartTime)));
            sb.append(", endTime: ");
            sb.append(this.mEndTime == 0 ? null : com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mEndTime)));
            sb.append(", durationMs: ");
            sb.append(this.mDurationMs);
            sb.append(", status: ");
            sb.append(this.mStatus.name().toLowerCase(java.util.Locale.ROOT));
            sb.append(", playedEffect: ");
            sb.append(this.mPlayedEffect);
            sb.append(", originalEffect: ");
            sb.append(this.mOriginalEffect);
            sb.append(", scaleLevel: ");
            sb.append(com.android.server.vibrator.VibrationScaler.scaleLevelToString(this.mScaleLevel));
            sb.append(", adaptiveScale: ");
            sb.append(java.lang.String.format(java.util.Locale.ROOT, "%.2f", java.lang.Float.valueOf(this.mAdaptiveScale)));
            sb.append(", callerInfo: ");
            sb.append(this.mCallerInfo);
            return sb.toString();
        }

        void dumpCompact(android.util.IndentingPrintWriter indentingPrintWriter) {
            java.lang.String str;
            java.lang.String str2 = "";
            java.lang.String format = java.lang.String.format(java.util.Locale.ROOT, "%s | %8s | %20s | duration: %5dms | start: %12s | end: %12s", com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mCreateTime)), this.mPlayedEffect == null ? "external" : "effect", this.mStatus.name().toLowerCase(java.util.Locale.ROOT), java.lang.Long.valueOf(this.mDurationMs), this.mStartTime == 0 ? "" : com.android.server.vibrator.Vibration.DEBUG_TIME_FORMAT.format(new java.util.Date(this.mStartTime)), this.mEndTime == 0 ? "" : com.android.server.vibrator.Vibration.DEBUG_TIME_FORMAT.format(new java.util.Date(this.mEndTime)));
            java.lang.String format2 = java.lang.String.format(java.util.Locale.ROOT, " | scale: %8s (adaptive=%.2f) | flags: %4s | usage: %s", com.android.server.vibrator.VibrationScaler.scaleLevelToString(this.mScaleLevel), java.lang.Float.valueOf(this.mAdaptiveScale), java.lang.Long.toBinaryString(this.mCallerInfo.attrs.getFlags()), this.mCallerInfo.attrs.usageToString());
            if (this.mCallerInfo.attrs.getCategory() != 0) {
                str = " | category=" + android.os.VibrationAttributes.categoryToString(this.mCallerInfo.attrs.getCategory());
            } else {
                str = "";
            }
            if (this.mCallerInfo.attrs.getOriginalAudioUsage() != 0) {
                str2 = " | audioUsage=" + android.media.AudioAttributes.usageToString(this.mCallerInfo.attrs.getOriginalAudioUsage());
            }
            java.lang.String format3 = java.lang.String.format(java.util.Locale.ROOT, " | %s (uid=%d, deviceId=%d) | reason: %s", this.mCallerInfo.opPkg, java.lang.Integer.valueOf(this.mCallerInfo.uid), java.lang.Integer.valueOf(this.mCallerInfo.deviceId), this.mCallerInfo.reason);
            indentingPrintWriter.println(format + format2 + str + str2 + format3 + java.lang.String.format(java.util.Locale.ROOT, " | played: %s | original: %s", this.mPlayedEffect == null ? null : this.mPlayedEffect.toDebugString(), this.mOriginalEffect != null ? this.mOriginalEffect.toDebugString() : null));
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Vibration:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("status = " + this.mStatus.name().toLowerCase(java.util.Locale.ROOT));
            indentingPrintWriter.println("durationMs = " + this.mDurationMs);
            indentingPrintWriter.println("createTime = " + com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mCreateTime)));
            indentingPrintWriter.println("startTime = " + com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mStartTime)));
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("endTime = ");
            sb.append(this.mEndTime == 0 ? null : com.android.server.vibrator.Vibration.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mEndTime)));
            indentingPrintWriter.println(sb.toString());
            indentingPrintWriter.println("playedEffect = " + this.mPlayedEffect);
            indentingPrintWriter.println("originalEffect = " + this.mOriginalEffect);
            indentingPrintWriter.println("scale = " + com.android.server.vibrator.VibrationScaler.scaleLevelToString(this.mScaleLevel));
            indentingPrintWriter.println("adaptiveScale = " + java.lang.String.format(java.util.Locale.ROOT, "%.2f", java.lang.Float.valueOf(this.mAdaptiveScale)));
            indentingPrintWriter.println("callerInfo = " + this.mCallerInfo);
            indentingPrintWriter.decreaseIndent();
        }

        void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, this.mStartTime);
            protoOutputStream.write(1112396529666L, this.mEndTime);
            protoOutputStream.write(1112396529671L, this.mDurationMs);
            protoOutputStream.write(1159641169928L, this.mStatus.ordinal());
            long start2 = protoOutputStream.start(1146756268037L);
            android.os.VibrationAttributes vibrationAttributes = this.mCallerInfo.attrs;
            protoOutputStream.write(1120986464257L, vibrationAttributes.getUsage());
            protoOutputStream.write(1120986464258L, vibrationAttributes.getAudioUsage());
            protoOutputStream.write(1120986464260L, vibrationAttributes.getCategory());
            protoOutputStream.write(1120986464259L, vibrationAttributes.getFlags());
            protoOutputStream.end(start2);
            if (this.mPlayedEffect != null) {
                dumpEffect(protoOutputStream, 1146756268035L, this.mPlayedEffect);
            }
            if (this.mOriginalEffect != null) {
                dumpEffect(protoOutputStream, 1146756268036L, this.mOriginalEffect);
            }
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.CombinedVibration combinedVibration) {
            dumpEffect(protoOutputStream, j, (android.os.CombinedVibration.Sequential) android.os.CombinedVibration.startSequential().addNext(combinedVibration).combine());
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.CombinedVibration.Sequential sequential) {
            long start = protoOutputStream.start(j);
            for (int i = 0; i < sequential.getEffects().size(); i++) {
                android.os.CombinedVibration combinedVibration = (android.os.CombinedVibration) sequential.getEffects().get(i);
                if (combinedVibration instanceof android.os.CombinedVibration.Mono) {
                    dumpEffect(protoOutputStream, 2246267895809L, (android.os.CombinedVibration.Mono) combinedVibration);
                } else if (combinedVibration instanceof android.os.CombinedVibration.Stereo) {
                    dumpEffect(protoOutputStream, 2246267895809L, (android.os.CombinedVibration.Stereo) combinedVibration);
                }
                protoOutputStream.write(2220498092034L, ((java.lang.Integer) sequential.getDelays().get(i)).intValue());
            }
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.CombinedVibration.Mono mono) {
            long start = protoOutputStream.start(j);
            dumpEffect(protoOutputStream, 2246267895809L, mono.getEffect());
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.CombinedVibration.Stereo stereo) {
            long start = protoOutputStream.start(j);
            for (int i = 0; i < stereo.getEffects().size(); i++) {
                protoOutputStream.write(2220498092034L, stereo.getEffects().keyAt(i));
                dumpEffect(protoOutputStream, 2246267895809L, (android.os.VibrationEffect) stereo.getEffects().valueAt(i));
            }
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.VibrationEffect vibrationEffect) {
            long start = protoOutputStream.start(j);
            android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect.Composed) vibrationEffect;
            java.util.Iterator it = composed.getSegments().iterator();
            while (it.hasNext()) {
                dumpEffect(protoOutputStream, 1146756268033L, (android.os.vibrator.VibrationEffectSegment) it.next());
            }
            protoOutputStream.write(1120986464258L, composed.getRepeatIndex());
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.vibrator.VibrationEffectSegment vibrationEffectSegment) {
            long start = protoOutputStream.start(j);
            if (vibrationEffectSegment instanceof android.os.vibrator.StepSegment) {
                dumpEffect(protoOutputStream, 1146756268035L, (android.os.vibrator.StepSegment) vibrationEffectSegment);
            } else if (vibrationEffectSegment instanceof android.os.vibrator.RampSegment) {
                dumpEffect(protoOutputStream, 1146756268036L, (android.os.vibrator.RampSegment) vibrationEffectSegment);
            } else if (vibrationEffectSegment instanceof android.os.vibrator.PrebakedSegment) {
                dumpEffect(protoOutputStream, 1146756268033L, (android.os.vibrator.PrebakedSegment) vibrationEffectSegment);
            } else if (vibrationEffectSegment instanceof android.os.vibrator.PrimitiveSegment) {
                dumpEffect(protoOutputStream, 1146756268034L, (android.os.vibrator.PrimitiveSegment) vibrationEffectSegment);
            }
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.vibrator.StepSegment stepSegment) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, stepSegment.getDuration());
            protoOutputStream.write(1108101562370L, stepSegment.getAmplitude());
            protoOutputStream.write(1108101562371L, stepSegment.getFrequencyHz());
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.vibrator.RampSegment rampSegment) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, rampSegment.getDuration());
            protoOutputStream.write(1108101562370L, rampSegment.getStartAmplitude());
            protoOutputStream.write(1108101562371L, rampSegment.getEndAmplitude());
            protoOutputStream.write(1108101562372L, rampSegment.getStartFrequencyHz());
            protoOutputStream.write(1108101562373L, rampSegment.getEndFrequencyHz());
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.vibrator.PrebakedSegment prebakedSegment) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, prebakedSegment.getEffectId());
            protoOutputStream.write(1120986464258L, prebakedSegment.getEffectStrength());
            protoOutputStream.write(1120986464259L, prebakedSegment.shouldFallback());
            protoOutputStream.end(start);
        }

        private void dumpEffect(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.vibrator.PrimitiveSegment primitiveSegment) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, primitiveSegment.getPrimitiveId());
            protoOutputStream.write(1108101562370L, primitiveSegment.getScale());
            protoOutputStream.write(1120986464259L, primitiveSegment.getDelay());
            protoOutputStream.end(start);
        }
    }
}
