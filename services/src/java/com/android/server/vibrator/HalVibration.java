package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class HalVibration extends com.android.server.vibrator.Vibration {
    private float mAdaptiveScale;
    private final java.util.concurrent.CountDownLatch mCompletionLatch;

    @android.annotation.NonNull
    private volatile android.os.CombinedVibration mEffectToPlay;
    public final android.util.SparseArray<android.os.VibrationEffect> mFallbacks;

    @android.annotation.NonNull
    private final android.os.CombinedVibration mOriginalEffect;
    private int mScaleLevel;
    private com.android.server.vibrator.Vibration.Status mStatus;

    HalVibration(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.CombinedVibration combinedVibration, @android.annotation.NonNull com.android.server.vibrator.Vibration.CallerInfo callerInfo) {
        super(iBinder, callerInfo);
        this.mFallbacks = new android.util.SparseArray<>();
        this.mCompletionLatch = new java.util.concurrent.CountDownLatch(1);
        this.mOriginalEffect = combinedVibration;
        this.mEffectToPlay = combinedVibration;
        this.mStatus = com.android.server.vibrator.Vibration.Status.RUNNING;
        this.mScaleLevel = 0;
        this.mAdaptiveScale = 1.0f;
    }

    public void end(com.android.server.vibrator.Vibration.EndInfo endInfo) {
        if (hasEnded()) {
            return;
        }
        this.mStatus = endInfo.status;
        this.stats.reportEnded(endInfo.endedBy);
        this.mCompletionLatch.countDown();
    }

    public void waitForEnd() throws java.lang.InterruptedException {
        this.mCompletionLatch.await();
    }

    @android.annotation.Nullable
    public android.os.VibrationEffect getFallback(int i) {
        return this.mFallbacks.get(i);
    }

    public void addFallback(int i, android.os.VibrationEffect vibrationEffect) {
        this.mFallbacks.put(i, vibrationEffect);
    }

    public void resolveEffects(int i) {
        android.os.CombinedVibration transform = this.mEffectToPlay.transform(new android.os.VibrationEffect.Transformation() { // from class: com.android.server.vibrator.HalVibration$$ExternalSyntheticLambda0
            public final android.os.VibrationEffect transform(android.os.VibrationEffect vibrationEffect, java.lang.Object obj) {
                return vibrationEffect.resolve(((java.lang.Integer) obj).intValue());
            }
        }, java.lang.Integer.valueOf(i));
        if (!java.util.Objects.equals(this.mEffectToPlay, transform)) {
            this.mEffectToPlay = transform;
        }
        for (int i2 = 0; i2 < this.mFallbacks.size(); i2++) {
            this.mFallbacks.setValueAt(i2, this.mFallbacks.valueAt(i2).resolve(i));
        }
    }

    public void scaleEffects(final com.android.server.vibrator.VibrationScaler vibrationScaler) {
        int usage = this.callerInfo.attrs.getUsage();
        this.mScaleLevel = vibrationScaler.getScaleLevel(usage);
        this.mAdaptiveScale = vibrationScaler.getAdaptiveHapticsScale(usage);
        android.os.CombinedVibration combinedVibration = this.mEffectToPlay;
        java.util.Objects.requireNonNull(vibrationScaler);
        android.os.CombinedVibration transform = combinedVibration.transform(new android.os.VibrationEffect.Transformation() { // from class: com.android.server.vibrator.HalVibration$$ExternalSyntheticLambda1
            public final android.os.VibrationEffect transform(android.os.VibrationEffect vibrationEffect, java.lang.Object obj) {
                return com.android.server.vibrator.VibrationScaler.this.scale(vibrationEffect, ((java.lang.Integer) obj).intValue());
            }
        }, java.lang.Integer.valueOf(usage));
        if (!java.util.Objects.equals(this.mEffectToPlay, transform)) {
            this.mEffectToPlay = transform;
        }
        for (int i = 0; i < this.mFallbacks.size(); i++) {
            this.mFallbacks.setValueAt(i, vibrationScaler.scale(this.mFallbacks.valueAt(i), usage));
        }
    }

    public void adaptToDevice(android.os.CombinedVibration.VibratorAdapter vibratorAdapter) {
        android.os.CombinedVibration adapt = this.mEffectToPlay.adapt(vibratorAdapter);
        if (!java.util.Objects.equals(this.mEffectToPlay, adapt)) {
            this.mEffectToPlay = adapt;
        }
    }

    public boolean hasEnded() {
        return this.mStatus != com.android.server.vibrator.Vibration.Status.RUNNING;
    }

    @Override // com.android.server.vibrator.Vibration
    public boolean isRepeating() {
        return this.mOriginalEffect.getDuration() == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    public android.os.CombinedVibration getEffectToPlay() {
        return this.mEffectToPlay;
    }

    public com.android.server.vibrator.Vibration.DebugInfo getDebugInfo() {
        return new com.android.server.vibrator.Vibration.DebugInfo(this.mStatus, this.stats, this.mEffectToPlay, java.util.Objects.equals(this.mOriginalEffect, this.mEffectToPlay) ? null : this.mOriginalEffect, this.mScaleLevel, this.mAdaptiveScale, this.callerInfo);
    }

    public com.android.server.vibrator.VibrationStats.StatsInfo getStatsInfo(long j) {
        int i;
        if (isRepeating()) {
            i = 2;
        } else {
            i = 1;
        }
        return new com.android.server.vibrator.VibrationStats.StatsInfo(this.callerInfo.uid, i, this.callerInfo.attrs.getUsage(), this.mStatus, this.stats, j);
    }

    public boolean canPipelineWith(com.android.server.vibrator.HalVibration halVibration) {
        return this.callerInfo.uid == halVibration.callerInfo.uid && this.callerInfo.attrs.isFlagSet(8) && halVibration.callerInfo.attrs.isFlagSet(8) && !isRepeating();
    }
}
