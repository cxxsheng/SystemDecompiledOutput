package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public class BiometricLogger {
    public static final boolean DEBUG = false;
    public static final java.lang.String TAG = "BiometricLogger";

    @android.annotation.NonNull
    private final com.android.server.biometrics.log.ALSProbe mALSProbe;

    @android.annotation.Nullable
    private final com.android.server.biometrics.AuthenticationStatsCollector mAuthenticationStatsCollector;
    private long mFirstAcquireTimeMs;
    private boolean mShouldLogMetrics;
    private final com.android.server.biometrics.log.BiometricFrameworkStatsLogger mSink;
    private final int mStatsAction;
    private final int mStatsClient;
    private final int mStatsModality;

    public static com.android.server.biometrics.log.BiometricLogger ofUnknown(@android.annotation.NonNull android.content.Context context) {
        return new com.android.server.biometrics.log.BiometricLogger(context, 0, 0, 0, null);
    }

    public BiometricLogger(@android.annotation.NonNull android.content.Context context, int i, int i2, int i3, @android.annotation.Nullable com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector) {
        this(i, i2, i3, com.android.server.biometrics.log.BiometricFrameworkStatsLogger.getInstance(), authenticationStatsCollector, (android.hardware.SensorManager) context.getSystemService(android.hardware.SensorManager.class));
    }

    @com.android.internal.annotations.VisibleForTesting
    BiometricLogger(int i, int i2, int i3, com.android.server.biometrics.log.BiometricFrameworkStatsLogger biometricFrameworkStatsLogger, @android.annotation.Nullable com.android.server.biometrics.AuthenticationStatsCollector authenticationStatsCollector, android.hardware.SensorManager sensorManager) {
        this.mShouldLogMetrics = true;
        this.mStatsModality = i;
        this.mStatsAction = i2;
        this.mStatsClient = i3;
        this.mSink = biometricFrameworkStatsLogger;
        this.mAuthenticationStatsCollector = authenticationStatsCollector;
        this.mALSProbe = new com.android.server.biometrics.log.ALSProbe(sensorManager);
    }

    public com.android.server.biometrics.log.BiometricLogger swapAction(@android.annotation.NonNull android.content.Context context, int i) {
        return new com.android.server.biometrics.log.BiometricLogger(context, this.mStatsModality, i, this.mStatsClient, null);
    }

    public void disableMetrics() {
        this.mShouldLogMetrics = false;
        this.mALSProbe.destroy();
    }

    public int getStatsClient() {
        return this.mStatsClient;
    }

    private boolean shouldSkipLogging() {
        boolean z = this.mStatsModality == 0 || this.mStatsAction == 0;
        if (this.mStatsModality == 0) {
            android.util.Slog.w(TAG, "Unknown field detected: MODALITY_UNKNOWN, will not report metric");
        }
        if (this.mStatsAction == 0) {
            android.util.Slog.w(TAG, "Unknown field detected: ACTION_UNKNOWN, will not report metric");
        }
        if (this.mStatsClient == 0) {
            android.util.Slog.w(TAG, "Unknown field detected: CLIENT_UNKNOWN");
        }
        return z;
    }

    public void logOnAcquired(android.content.Context context, com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3) {
        if (!this.mShouldLogMetrics) {
            return;
        }
        boolean z = this.mStatsModality == 4;
        boolean z2 = this.mStatsModality == 1;
        if (z || z2) {
            if ((z2 && i == 7) || (z && i == 20)) {
                this.mFirstAcquireTimeMs = java.lang.System.currentTimeMillis();
            }
        } else if (i == 0 && this.mFirstAcquireTimeMs == 0) {
            this.mFirstAcquireTimeMs = java.lang.System.currentTimeMillis();
        }
        if (shouldSkipLogging()) {
            return;
        }
        this.mSink.acquired(operationContextExt, this.mStatsModality, this.mStatsAction, this.mStatsClient, com.android.server.biometrics.Utils.isDebugEnabled(context, i3), i, i2, i3);
    }

    public void logOnError(android.content.Context context, com.android.server.biometrics.log.OperationContextExt operationContextExt, int i, int i2, int i3) {
        if (this.mShouldLogMetrics) {
            long currentTimeMillis = this.mFirstAcquireTimeMs != 0 ? java.lang.System.currentTimeMillis() - this.mFirstAcquireTimeMs : -1L;
            android.util.Slog.v(TAG, "Error latency: " + currentTimeMillis);
            if (shouldSkipLogging()) {
                return;
            }
            this.mSink.error(operationContextExt, this.mStatsModality, this.mStatsAction, this.mStatsClient, com.android.server.biometrics.Utils.isDebugEnabled(context, i3), currentTimeMillis, i, i2, i3);
        }
    }

    public void logOnAuthenticated(android.content.Context context, com.android.server.biometrics.log.OperationContextExt operationContextExt, boolean z, boolean z2, int i, boolean z3) {
        int i2;
        long j;
        if (!this.mShouldLogMetrics) {
            return;
        }
        if (this.mAuthenticationStatsCollector != null) {
            this.mAuthenticationStatsCollector.authenticate(i, z);
        }
        if (!z) {
            i2 = 1;
        } else if (z3 && z2) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        if (this.mFirstAcquireTimeMs != 0) {
            j = java.lang.System.currentTimeMillis() - this.mFirstAcquireTimeMs;
        } else {
            j = -1;
        }
        android.util.Slog.v(TAG, "Authentication latency: " + j);
        if (shouldSkipLogging()) {
            return;
        }
        this.mSink.authenticate(operationContextExt, this.mStatsModality, this.mStatsAction, this.mStatsClient, com.android.server.biometrics.Utils.isDebugEnabled(context, i), j, i2, z2, i, this.mALSProbe);
    }

    public void logOnEnrolled(int i, long j, boolean z, int i2) {
        if (!this.mShouldLogMetrics) {
            return;
        }
        android.util.Slog.v(TAG, "Enroll latency: " + j);
        if (shouldSkipLogging()) {
            return;
        }
        this.mSink.enroll(this.mStatsModality, this.mStatsAction, this.mStatsClient, i, j, z, this.mALSProbe.getMostRecentLux(), i2);
    }

    public void logUnknownEnrollmentInHal() {
        if (shouldSkipLogging()) {
            return;
        }
        this.mSink.reportUnknownTemplateEnrolledHal(this.mStatsModality);
    }

    public void logUnknownEnrollmentInFramework() {
        if (shouldSkipLogging()) {
            return;
        }
        this.mSink.reportUnknownTemplateEnrolledFramework(this.mStatsModality);
    }

    @android.annotation.NonNull
    public com.android.server.biometrics.log.CallbackWithProbe<com.android.server.biometrics.log.Probe> getAmbientLightProbe(boolean z) {
        return new com.android.server.biometrics.log.CallbackWithProbe<>(this.mALSProbe, z);
    }
}
