package com.android.server.vibrator;

/* loaded from: classes2.dex */
final class VibratorControlService extends android.frameworks.vibrator.IVibratorControlService.Stub {
    private static final java.text.SimpleDateFormat DEBUG_DATE_TIME_FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private static final int NO_SCALE = -1;
    private static final java.lang.String TAG = "VibratorControlService";
    private static final int UNRECOGNIZED_VIBRATION_TYPE = -1;
    private final java.lang.Object mLock;
    private final int[] mRequestVibrationParamsForUsages;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.concurrent.CompletableFuture<java.lang.Void> mRequestVibrationParamsFuture = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.IBinder mRequestVibrationParamsToken;
    private final com.android.server.vibrator.VibratorControlService.VibrationParamsRecords mVibrationParamsRecords;
    private final com.android.server.vibrator.VibrationScaler mVibrationScaler;
    private final com.android.server.vibrator.VibratorControllerHolder mVibratorControllerHolder;

    VibratorControlService(android.content.Context context, com.android.server.vibrator.VibratorControllerHolder vibratorControllerHolder, com.android.server.vibrator.VibrationScaler vibrationScaler, com.android.server.vibrator.VibrationSettings vibrationSettings, java.lang.Object obj) {
        this.mVibratorControllerHolder = vibratorControllerHolder;
        this.mVibrationScaler = vibrationScaler;
        this.mLock = obj;
        this.mRequestVibrationParamsForUsages = vibrationSettings.getRequestVibrationParamsForUsages();
        this.mVibrationParamsRecords = new com.android.server.vibrator.VibratorControlService.VibrationParamsRecords(context.getResources().getInteger(android.R.integer.config_pictureInPictureMaxNumberOfActions), context.getResources().getInteger(android.R.integer.config_phonenumber_compare_min_match));
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public void registerVibratorController(android.frameworks.vibrator.IVibratorController iVibratorController) {
        synchronized (this.mLock) {
            this.mVibratorControllerHolder.setVibratorController(iVibratorController);
        }
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public void unregisterVibratorController(@android.annotation.NonNull android.frameworks.vibrator.IVibratorController iVibratorController) {
        java.util.Objects.requireNonNull(iVibratorController);
        synchronized (this.mLock) {
            try {
                if (this.mVibratorControllerHolder.getVibratorController() == null) {
                    android.util.Slog.w(TAG, "Received request to unregister IVibratorController = " + iVibratorController + ", but no controller was previously registered. Request Ignored.");
                    return;
                }
                if (!java.util.Objects.equals(this.mVibratorControllerHolder.getVibratorController().asBinder(), iVibratorController.asBinder())) {
                    android.util.Slog.wtf(TAG, "Failed to unregister IVibratorController. The provided controller doesn't match the registered one. " + this);
                    return;
                }
                this.mVibrationScaler.clearAdaptiveHapticsScales();
                this.mVibratorControllerHolder.setVibratorController(null);
                endOngoingRequestVibrationParamsLocked(true);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public void setVibrationParams(@android.annotation.SuppressLint({"ArrayReturn"}) android.frameworks.vibrator.VibrationParam[] vibrationParamArr, @android.annotation.NonNull android.frameworks.vibrator.IVibratorController iVibratorController) {
        java.util.Objects.requireNonNull(iVibratorController);
        synchronized (this.mLock) {
            try {
                if (this.mVibratorControllerHolder.getVibratorController() == null) {
                    android.util.Slog.w(TAG, "Received request to set VibrationParams for IVibratorController = " + iVibratorController + ", but no controller was previously registered. Request Ignored.");
                    return;
                }
                if (!java.util.Objects.equals(this.mVibratorControllerHolder.getVibratorController().asBinder(), iVibratorController.asBinder())) {
                    android.util.Slog.wtf(TAG, "Failed to set new VibrationParams. The provided controller doesn't match the registered one. " + this);
                    return;
                }
                updateAdaptiveHapticsScales(vibrationParamArr);
                recordUpdateVibrationParams(vibrationParamArr, false);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public void clearVibrationParams(int i, @android.annotation.NonNull android.frameworks.vibrator.IVibratorController iVibratorController) {
        java.util.Objects.requireNonNull(iVibratorController);
        synchronized (this.mLock) {
            try {
                if (this.mVibratorControllerHolder.getVibratorController() == null) {
                    android.util.Slog.w(TAG, "Received request to clear VibrationParams for IVibratorController = " + iVibratorController + ", but no controller was previously registered. Request Ignored.");
                    return;
                }
                if (!java.util.Objects.equals(this.mVibratorControllerHolder.getVibratorController().asBinder(), iVibratorController.asBinder())) {
                    android.util.Slog.wtf(TAG, "Failed to clear VibrationParams. The provided controller doesn't match the registered one. " + this);
                    return;
                }
                updateAdaptiveHapticsScales(i, -1.0f);
                recordClearVibrationParams(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public void onRequestVibrationParamsComplete(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.SuppressLint({"ArrayReturn"}) android.frameworks.vibrator.VibrationParam[] vibrationParamArr) {
        java.util.Objects.requireNonNull(iBinder);
        synchronized (this.mLock) {
            try {
                if (this.mRequestVibrationParamsToken == null) {
                    android.util.Slog.wtf(TAG, "New vibration params received but no token was cached in the service. New vibration params ignored.");
                } else {
                    if (!java.util.Objects.equals(iBinder, this.mRequestVibrationParamsToken)) {
                        android.util.Slog.w(TAG, "New vibration params received but the provided token does not match the cached one. New vibration params ignored.");
                        return;
                    }
                    updateAdaptiveHapticsScales(vibrationParamArr);
                    endOngoingRequestVibrationParamsLocked(false);
                    recordUpdateVibrationParams(vibrationParamArr, true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public int getInterfaceVersion() {
        return 1;
    }

    @Override // android.frameworks.vibrator.IVibratorControlService
    public java.lang.String getInterfaceHash() {
        return "eb095ed3034973273898ca9e37bbc72566392b8a";
    }

    @android.annotation.Nullable
    public java.util.concurrent.CompletableFuture<java.lang.Void> triggerVibrationParamsRequest(int i, int i2) {
        synchronized (this.mLock) {
            try {
                android.frameworks.vibrator.IVibratorController vibratorController = this.mVibratorControllerHolder.getVibratorController();
                if (vibratorController == null) {
                    android.util.Slog.d(TAG, "Unable to request vibration params. There is no registered IVibrationController.");
                    return null;
                }
                int mapToAdaptiveVibrationType = mapToAdaptiveVibrationType(i);
                if (mapToAdaptiveVibrationType == -1) {
                    android.util.Slog.d(TAG, "Unable to request vibration params. The provided usage " + i + " is unrecognized.");
                    return null;
                }
                try {
                    endOngoingRequestVibrationParamsLocked(true);
                    this.mRequestVibrationParamsFuture = new java.util.concurrent.CompletableFuture<>();
                    this.mRequestVibrationParamsToken = new android.os.Binder();
                    vibratorController.requestVibrationParams(mapToAdaptiveVibrationType, i2, this.mRequestVibrationParamsToken);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to request vibration params.", e);
                    endOngoingRequestVibrationParamsLocked(true);
                }
                return this.mRequestVibrationParamsFuture;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean shouldRequestVibrationParams(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mVibratorControllerHolder.getVibratorController() == null) {
                    return false;
                }
                return com.android.internal.util.ArrayUtils.contains(this.mRequestVibrationParamsForUsages, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public android.os.IBinder getRequestVibrationParamsToken() {
        android.os.IBinder iBinder;
        synchronized (this.mLock) {
            iBinder = this.mRequestVibrationParamsToken;
        }
        return iBinder;
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        boolean z2;
        synchronized (this.mLock) {
            z = this.mVibratorControllerHolder.getVibratorController() != null;
            z2 = this.mRequestVibrationParamsFuture != null;
        }
        indentingPrintWriter.println("VibratorControlService:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("isVibratorControllerRegistered = " + z);
        indentingPrintWriter.println("hasPendingVibrationParamsRequest = " + z2);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Vibration parameters update history:");
        indentingPrintWriter.increaseIndent();
        this.mVibrationParamsRecords.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mVibratorControllerHolder.getVibratorController() != null;
        }
        protoOutputStream.write(1120986464283L, z);
        this.mVibrationParamsRecords.dump(protoOutputStream);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void endOngoingRequestVibrationParamsLocked(boolean z) {
        this.mRequestVibrationParamsToken = null;
        if (this.mRequestVibrationParamsFuture == null) {
            return;
        }
        if (!z) {
            this.mRequestVibrationParamsFuture.complete(null);
        } else {
            this.mRequestVibrationParamsFuture.cancel(true);
        }
        this.mRequestVibrationParamsFuture = null;
    }

    private static int mapToAdaptiveVibrationType(int i) {
        switch (i) {
            case 0:
            case 19:
                return 16;
            case 17:
                return 1;
            case 18:
            case 34:
            case 50:
            case 66:
                return 8;
            case 33:
                return 4;
            case 49:
            case 65:
                return 2;
            default:
                android.util.Slog.w(TAG, "Unrecognized vibration usage " + i);
                return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[] mapFromAdaptiveVibrationTypeToVibrationUsages(int i) {
        android.util.IntArray intArray = new android.util.IntArray(15);
        if ((i & 1) != 0) {
            intArray.add(17);
        }
        if ((i & 2) != 0) {
            intArray.add(49);
            intArray.add(65);
        }
        if ((i & 4) != 0) {
            intArray.add(33);
        }
        if ((i & 16) != 0) {
            intArray.add(19);
            intArray.add(0);
        }
        if ((i & 8) != 0) {
            intArray.add(18);
            intArray.add(50);
        }
        return intArray.toArray();
    }

    private void updateAdaptiveHapticsScales(@android.annotation.Nullable android.frameworks.vibrator.VibrationParam[] vibrationParamArr) {
        if (vibrationParamArr == null) {
            return;
        }
        for (android.frameworks.vibrator.VibrationParam vibrationParam : vibrationParamArr) {
            if (vibrationParam.getTag() != 0) {
                android.util.Slog.e(TAG, "Unsupported vibration param: " + vibrationParam);
            } else {
                android.frameworks.vibrator.ScaleParam scale = vibrationParam.getScale();
                updateAdaptiveHapticsScales(scale.typesMask, scale.scale);
            }
        }
    }

    private void updateAdaptiveHapticsScales(int i, float f) {
        for (int i2 : mapFromAdaptiveVibrationTypeToVibrationUsages(i)) {
            updateOrRemoveAdaptiveHapticsScale(i2, f);
        }
    }

    private void updateOrRemoveAdaptiveHapticsScale(int i, float f) {
        if (f == -1.0f) {
            this.mVibrationScaler.removeAdaptiveHapticsScale(i);
        } else {
            this.mVibrationScaler.updateAdaptiveHapticsScale(i, f);
        }
    }

    private void recordUpdateVibrationParams(@android.annotation.Nullable android.frameworks.vibrator.VibrationParam[] vibrationParamArr, boolean z) {
        if (vibrationParamArr == null) {
            return;
        }
        com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation operation = z ? com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation.PULL : com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation.PUSH;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        for (android.frameworks.vibrator.VibrationParam vibrationParam : vibrationParamArr) {
            if (vibrationParam.getTag() != 0) {
                android.util.Slog.w(TAG, "Unsupported vibration param ignored from dumpsys records: " + vibrationParam);
            } else {
                android.frameworks.vibrator.ScaleParam scale = vibrationParam.getScale();
                this.mVibrationParamsRecords.add(new com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord(operation, uptimeMillis, scale.typesMask, scale.scale));
            }
        }
    }

    private void recordClearVibrationParams(int i) {
        this.mVibrationParamsRecords.add(new com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord(com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation.CLEAR, android.os.SystemClock.uptimeMillis(), i, -1.0f));
    }

    private static final class VibrationParamsRecords extends com.android.server.vibrator.GroupedAggregatedLogRecords<com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord> {

        enum Operation {
            PULL,
            PUSH,
            CLEAR
        }

        VibrationParamsRecords(int i, int i2) {
            super(i, i2);
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        synchronized void dumpGroupHeader(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
            try {
                if (i == 0) {
                    indentingPrintWriter.println("SCALE:");
                } else {
                    indentingPrintWriter.println("UNKNOWN:");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords
        synchronized long findGroupKeyProtoFieldId(int i) {
            return 2246267895836L;
        }
    }

    private static final class VibrationScaleParamRecord implements com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord {
        private final long mCreateTime;
        private final com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation mOperation;
        private final float mScale;
        private final int mTypesMask;

        VibrationScaleParamRecord(com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation operation, long j, int i, float f) {
            this.mOperation = operation;
            this.mCreateTime = j;
            this.mTypesMask = i;
            this.mScale = f;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public int getGroupKey() {
            return 0;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public long getCreateUptimeMs() {
            return this.mCreateTime;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public boolean mayAggregate(com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord singleLogRecord) {
            if (!(singleLogRecord instanceof com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord)) {
                return false;
            }
            com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord vibrationScaleParamRecord = (com.android.server.vibrator.VibratorControlService.VibrationScaleParamRecord) singleLogRecord;
            return this.mTypesMask == vibrationScaleParamRecord.mTypesMask && this.mOperation == vibrationScaleParamRecord.mOperation;
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println(java.lang.String.format(java.util.Locale.ROOT, "%s | %6s | scale: %5s | typesMask: %6s | usages: %s", com.android.server.vibrator.VibratorControlService.DEBUG_DATE_TIME_FORMAT.format(new java.util.Date(this.mCreateTime)), this.mOperation.name().toLowerCase(java.util.Locale.ROOT), this.mScale == -1.0f ? "" : java.lang.String.format(java.util.Locale.ROOT, "%.2f", java.lang.Float.valueOf(this.mScale)), java.lang.Long.toBinaryString(this.mTypesMask), createVibrationUsagesString()));
        }

        @Override // com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord
        public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529666L, this.mCreateTime);
            protoOutputStream.write(1133871366147L, this.mOperation == com.android.server.vibrator.VibratorControlService.VibrationParamsRecords.Operation.PULL);
            long start2 = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1120986464257L, this.mTypesMask);
            protoOutputStream.write(1108101562370L, this.mScale);
            protoOutputStream.end(start2);
            protoOutputStream.end(start);
        }

        private java.lang.String createVibrationUsagesString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int[] mapFromAdaptiveVibrationTypeToVibrationUsages = com.android.server.vibrator.VibratorControlService.mapFromAdaptiveVibrationTypeToVibrationUsages(this.mTypesMask);
            for (int i = 0; i < mapFromAdaptiveVibrationTypeToVibrationUsages.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(android.os.VibrationAttributes.usageToString(mapFromAdaptiveVibrationTypeToVibrationUsages[i]));
            }
            return sb.toString();
        }
    }
}
