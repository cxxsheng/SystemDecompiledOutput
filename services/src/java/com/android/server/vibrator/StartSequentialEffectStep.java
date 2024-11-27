package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class StartSequentialEffectStep extends com.android.server.vibrator.Step {
    public final int currentIndex;
    private long mVibratorsOnMaxDuration;
    public final android.os.CombinedVibration.Sequential sequentialEffect;

    StartSequentialEffectStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, android.os.CombinedVibration.Sequential sequential) {
        this(vibrationStepConductor, android.os.SystemClock.uptimeMillis() + ((java.lang.Integer) sequential.getDelays().get(0)).intValue(), sequential, 0);
    }

    private StartSequentialEffectStep(com.android.server.vibrator.VibrationStepConductor vibrationStepConductor, long j, android.os.CombinedVibration.Sequential sequential, int i) {
        super(vibrationStepConductor, j);
        this.sequentialEffect = sequential;
        this.currentIndex = i;
    }

    @Override // com.android.server.vibrator.Step
    public long getVibratorOnDuration() {
        return this.mVibratorsOnMaxDuration;
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> play() {
        android.os.Trace.traceBegin(8388608L, "StartSequentialEffectStep");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mVibratorsOnMaxDuration = -1L;
        try {
            com.android.server.vibrator.StartSequentialEffectStep.DeviceEffectMap createEffectToVibratorMapping = createEffectToVibratorMapping((android.os.CombinedVibration) this.sequentialEffect.getEffects().get(this.currentIndex));
            if (createEffectToVibratorMapping == null) {
                return arrayList;
            }
            this.mVibratorsOnMaxDuration = startVibrating(createEffectToVibratorMapping, arrayList);
            this.conductor.vibratorManagerHooks.noteVibratorOn(this.conductor.getVibration().callerInfo.uid, this.mVibratorsOnMaxDuration);
            if (this.mVibratorsOnMaxDuration >= 0) {
                com.android.server.vibrator.Step finishSequentialEffectStep = this.mVibratorsOnMaxDuration > 0 ? new com.android.server.vibrator.FinishSequentialEffectStep(this) : nextStep();
                if (finishSequentialEffectStep != null) {
                    arrayList.add(finishSequentialEffectStep);
                }
            }
            android.os.Trace.traceEnd(8388608L);
            return arrayList;
        } finally {
            if (this.mVibratorsOnMaxDuration >= 0) {
                com.android.server.vibrator.Step finishSequentialEffectStep2 = this.mVibratorsOnMaxDuration > 0 ? new com.android.server.vibrator.FinishSequentialEffectStep(this) : nextStep();
                if (finishSequentialEffectStep2 != null) {
                    arrayList.add(finishSequentialEffectStep2);
                }
            }
            android.os.Trace.traceEnd(8388608L);
        }
    }

    @Override // com.android.server.vibrator.Step
    public java.util.List<com.android.server.vibrator.Step> cancel() {
        return com.android.server.vibrator.VibrationStepConductor.EMPTY_STEP_LIST;
    }

    @Override // com.android.server.vibrator.Step
    public void cancelImmediately() {
    }

    @android.annotation.Nullable
    com.android.server.vibrator.Step nextStep() {
        int i = this.currentIndex + 1;
        if (i >= this.sequentialEffect.getEffects().size()) {
            return null;
        }
        return new com.android.server.vibrator.StartSequentialEffectStep(this.conductor, android.os.SystemClock.uptimeMillis() + ((java.lang.Integer) this.sequentialEffect.getDelays().get(i)).intValue(), this.sequentialEffect, i);
    }

    @android.annotation.Nullable
    private com.android.server.vibrator.StartSequentialEffectStep.DeviceEffectMap createEffectToVibratorMapping(android.os.CombinedVibration combinedVibration) {
        if (combinedVibration instanceof android.os.CombinedVibration.Mono) {
            return new com.android.server.vibrator.StartSequentialEffectStep.DeviceEffectMap((android.os.CombinedVibration.Mono) combinedVibration);
        }
        if (combinedVibration instanceof android.os.CombinedVibration.Stereo) {
            return new com.android.server.vibrator.StartSequentialEffectStep.DeviceEffectMap((android.os.CombinedVibration.Stereo) combinedVibration);
        }
        return null;
    }

    private long startVibrating(com.android.server.vibrator.StartSequentialEffectStep.DeviceEffectMap deviceEffectMap, java.util.List<com.android.server.vibrator.Step> list) {
        boolean z;
        int size = deviceEffectMap.size();
        if (size == 0) {
            return 0L;
        }
        com.android.server.vibrator.AbstractVibratorStep[] abstractVibratorStepArr = new com.android.server.vibrator.AbstractVibratorStep[size];
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        boolean z2 = false;
        int i = 0;
        while (i < size) {
            int i2 = i;
            abstractVibratorStepArr[i2] = this.conductor.nextVibrateStep(uptimeMillis, this.conductor.getVibrators().get(deviceEffectMap.vibratorIdAt(i)), deviceEffectMap.effectAt(i), 0, 0L);
            i = i2 + 1;
        }
        if (size == 1) {
            return startVibrating(abstractVibratorStepArr[0], list);
        }
        boolean prepareSyncedVibration = this.conductor.vibratorManagerHooks.prepareSyncedVibration(deviceEffectMap.getRequiredSyncCapabilities(), deviceEffectMap.getVibratorIds());
        long j = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                z = false;
                break;
            }
            long startVibrating = startVibrating(abstractVibratorStepArr[i3], list);
            if (startVibrating < 0) {
                z = true;
                break;
            }
            j = java.lang.Math.max(j, startVibrating);
            i3++;
        }
        if (prepareSyncedVibration && !z && j > 0) {
            z2 = this.conductor.vibratorManagerHooks.triggerSyncedVibration(getVibration().id);
            z &= z2;
        }
        if (z) {
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                list.remove(size2).cancelImmediately();
            }
        }
        if (prepareSyncedVibration && !z2) {
            this.conductor.vibratorManagerHooks.cancelSyncedVibration();
        }
        if (z) {
            return -1L;
        }
        return j;
    }

    private long startVibrating(com.android.server.vibrator.AbstractVibratorStep abstractVibratorStep, java.util.List<com.android.server.vibrator.Step> list) {
        list.addAll(abstractVibratorStep.play());
        long vibratorOnDuration = abstractVibratorStep.getVibratorOnDuration();
        if (vibratorOnDuration < 0) {
            return vibratorOnDuration;
        }
        return java.lang.Math.max(vibratorOnDuration, abstractVibratorStep.effect.getDuration());
    }

    final class DeviceEffectMap {
        private final long mRequiredSyncCapabilities;
        private final android.util.SparseArray<android.os.VibrationEffect.Composed> mVibratorEffects;
        private final int[] mVibratorIds;

        DeviceEffectMap(android.os.CombinedVibration.Mono mono) {
            android.util.SparseArray<com.android.server.vibrator.VibratorController> vibrators = com.android.server.vibrator.StartSequentialEffectStep.this.conductor.getVibrators();
            android.os.VibrationEffect.Composed effect = mono.getEffect();
            if (effect instanceof android.os.VibrationEffect.Composed) {
                this.mVibratorEffects = new android.util.SparseArray<>(vibrators.size());
                this.mVibratorIds = new int[vibrators.size()];
                android.os.VibrationEffect.Composed composed = effect;
                for (int i = 0; i < vibrators.size(); i++) {
                    int keyAt = vibrators.keyAt(i);
                    this.mVibratorEffects.put(keyAt, composed);
                    this.mVibratorIds[i] = keyAt;
                }
            } else {
                android.util.Slog.wtf("VibrationThread", "Unable to map device vibrators to unexpected effect: " + effect);
                this.mVibratorEffects = new android.util.SparseArray<>();
                this.mVibratorIds = new int[0];
            }
            this.mRequiredSyncCapabilities = calculateRequiredSyncCapabilities(this.mVibratorEffects);
        }

        DeviceEffectMap(android.os.CombinedVibration.Stereo stereo) {
            android.util.SparseArray<com.android.server.vibrator.VibratorController> vibrators = com.android.server.vibrator.StartSequentialEffectStep.this.conductor.getVibrators();
            android.util.SparseArray effects = stereo.getEffects();
            this.mVibratorEffects = new android.util.SparseArray<>();
            for (int i = 0; i < effects.size(); i++) {
                int keyAt = effects.keyAt(i);
                if (vibrators.contains(keyAt)) {
                    android.os.VibrationEffect.Composed composed = (android.os.VibrationEffect) effects.valueAt(i);
                    if (composed instanceof android.os.VibrationEffect.Composed) {
                        this.mVibratorEffects.put(keyAt, composed);
                    } else {
                        android.util.Slog.wtf("VibrationThread", "Unable to map device vibrators to unexpected effect: " + composed);
                    }
                }
            }
            this.mVibratorIds = new int[this.mVibratorEffects.size()];
            for (int i2 = 0; i2 < this.mVibratorEffects.size(); i2++) {
                this.mVibratorIds[i2] = this.mVibratorEffects.keyAt(i2);
            }
            this.mRequiredSyncCapabilities = calculateRequiredSyncCapabilities(this.mVibratorEffects);
        }

        public int size() {
            return this.mVibratorIds.length;
        }

        public long getRequiredSyncCapabilities() {
            return this.mRequiredSyncCapabilities;
        }

        public int[] getVibratorIds() {
            return this.mVibratorIds;
        }

        public int vibratorIdAt(int i) {
            return this.mVibratorEffects.keyAt(i);
        }

        public android.os.VibrationEffect.Composed effectAt(int i) {
            return this.mVibratorEffects.valueAt(i);
        }

        private long calculateRequiredSyncCapabilities(android.util.SparseArray<android.os.VibrationEffect.Composed> sparseArray) {
            long j = 0;
            for (int i = 0; i < sparseArray.size(); i++) {
                android.os.vibrator.VibrationEffectSegment vibrationEffectSegment = (android.os.vibrator.VibrationEffectSegment) sparseArray.valueAt(i).getSegments().get(0);
                if (vibrationEffectSegment instanceof android.os.vibrator.StepSegment) {
                    j |= 2;
                } else if (vibrationEffectSegment instanceof android.os.vibrator.PrebakedSegment) {
                    j |= 4;
                } else if (vibrationEffectSegment instanceof android.os.vibrator.PrimitiveSegment) {
                    j |= 8;
                }
            }
            int i2 = requireMixedTriggerCapability(j, 2L) ? 16 : 0;
            if (requireMixedTriggerCapability(j, 4L)) {
                i2 |= 32;
            }
            if (requireMixedTriggerCapability(j, 8L)) {
                i2 |= 64;
            }
            return j | 1 | i2;
        }

        private boolean requireMixedTriggerCapability(long j, long j2) {
            return ((j & j2) == 0 || (j & (~j2)) == 0) ? false : true;
        }
    }
}
