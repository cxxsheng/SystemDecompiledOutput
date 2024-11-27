package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public class BiometricFrameworkStatsLogger {
    private static final java.lang.String TAG = "BiometricFrameworkStatsLogger";
    private static final com.android.server.biometrics.log.BiometricFrameworkStatsLogger sInstance = new com.android.server.biometrics.log.BiometricFrameworkStatsLogger();

    private BiometricFrameworkStatsLogger() {
    }

    public static com.android.server.biometrics.log.BiometricFrameworkStatsLogger getInstance() {
        return sInstance;
    }

    public void acquired(com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3, boolean z, int i4, int i5, int i6) {
        com.android.internal.util.FrameworkStatsLog.write(87, i, i6, operationContextExt.isCrypto(), i2, i3, i4, i5, z, -1, operationContextExt.getId(), sessionType(operationContextExt.getReason()), operationContextExt.isAod(), operationContextExt.isDisplayOn(), operationContextExt.getDockState(), orientationType(operationContextExt.getOrientation()), foldType(operationContextExt.getFoldState()), operationContextExt.getOrderAndIncrement(), toProtoWakeReason(operationContextExt));
    }

    public void authenticate(com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3, boolean z, long j, int i4, boolean z2, int i5, float f) {
        com.android.internal.util.FrameworkStatsLog.write(88, i, i5, operationContextExt.isCrypto(), i3, z2, i4, sanitizeLatency(j), z, -1, f, operationContextExt.getId(), sessionType(operationContextExt.getReason()), operationContextExt.isAod(), operationContextExt.isDisplayOn(), operationContextExt.getDockState(), orientationType(operationContextExt.getOrientation()), foldType(operationContextExt.getFoldState()), operationContextExt.getOrderAndIncrement(), toProtoWakeReason(operationContextExt), toProtoWakeReasonDetails(operationContextExt));
    }

    public void authenticate(final com.android.server.biometrics.log.OperationContextExt operationContextExt, final int i, final int i2, final int i3, final boolean z, final long j, final int i4, final boolean z2, final int i5, com.android.server.biometrics.log.ALSProbe aLSProbe) {
        aLSProbe.awaitNextLux(new java.util.function.Consumer() { // from class: com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.biometrics.log.BiometricFrameworkStatsLogger.this.lambda$authenticate$0(operationContextExt, i, i2, i3, z, j, i4, z2, i5, (java.lang.Float) obj);
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$authenticate$0(com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3, boolean z, long j, int i4, boolean z2, int i5, java.lang.Float f) {
        authenticate(operationContextExt, i, i2, i3, z, j, i4, z2, i5, f.floatValue());
    }

    public void enroll(int i, int i2, int i3, int i4, long j, boolean z, float f, int i5) {
        com.android.internal.util.FrameworkStatsLog.write(184, i, i4, sanitizeLatency(j), z, -1, f, i5);
    }

    public void error(com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3, boolean z, long j, int i4, int i5, int i6) {
        com.android.internal.util.FrameworkStatsLog.write(89, i, i6, operationContextExt.isCrypto(), i2, i3, i4, i5, z, sanitizeLatency(j), -1, operationContextExt.getId(), sessionType(operationContextExt.getReason()), operationContextExt.isAod(), operationContextExt.isDisplayOn(), operationContextExt.getDockState(), orientationType(operationContextExt.getOrientation()), foldType(operationContextExt.getFoldState()), operationContextExt.getOrderAndIncrement(), toProtoWakeReason(operationContextExt), toProtoWakeReasonDetails(operationContextExt));
    }

    @com.android.internal.annotations.VisibleForTesting
    static int[] toProtoWakeReasonDetails(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt) {
        return java.util.stream.Stream.of(java.lang.Integer.valueOf(toProtoWakeReasonDetails(operationContextExt.toAidlContext().authenticateReason))).mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).filter(new java.util.function.IntPredicate() { // from class: com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean lambda$toProtoWakeReasonDetails$2;
                lambda$toProtoWakeReasonDetails$2 = com.android.server.biometrics.log.BiometricFrameworkStatsLogger.lambda$toProtoWakeReasonDetails$2(i);
                return lambda$toProtoWakeReasonDetails$2;
            }
        }).toArray();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$toProtoWakeReasonDetails$2(int i) {
        return i != 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int toProtoWakeReason(@android.annotation.NonNull com.android.server.biometrics.log.OperationContextExt operationContextExt) {
        switch (operationContextExt.getWakeReason()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            default:
                return 0;
        }
    }

    private static int toProtoWakeReasonDetails(@android.annotation.Nullable android.hardware.biometrics.common.AuthenticateReason authenticateReason) {
        if (authenticateReason != null) {
            switch (authenticateReason.getTag()) {
                case 1:
                    return toProtoWakeReasonDetailsFromFace(authenticateReason.getFaceAuthenticateReason());
                default:
                    return 0;
            }
        }
        return 0;
    }

    private static int toProtoWakeReasonDetailsFromFace(@android.hardware.biometrics.common.AuthenticateReason.Face int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            default:
                return 0;
        }
    }

    public void reportUnknownTemplateEnrolledHal(int i) {
        com.android.internal.util.FrameworkStatsLog.write(148, i, 3, -1);
    }

    public void reportUnknownTemplateEnrolledFramework(int i) {
        com.android.internal.util.FrameworkStatsLog.write(148, i, 2, -1);
    }

    public void logFrameworkNotification(int i, int i2) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BIOMETRIC_FRR_NOTIFICATION, i, i2);
    }

    private long sanitizeLatency(long j) {
        if (j < 0) {
            android.util.Slog.w(TAG, "found a negative latency : " + j);
            return -1L;
        }
        return j;
    }

    private static int sessionType(@android.hardware.biometrics.common.OperationReason byte b) {
        if (b == 1) {
            return 2;
        }
        return b == 2 ? 1 : 0;
    }

    private static int orientationType(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    private static int foldType(int i) {
        switch (i) {
            case 1:
                return 3;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return 0;
        }
    }
}
